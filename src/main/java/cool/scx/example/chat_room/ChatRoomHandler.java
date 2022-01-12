package cool.scx.example.chat_room;

import cool.scx.ScxConstant;
import cool.scx.ScxContext;
import cool.scx.example.auth.AlreadyLoginClient;
import cool.scx.example.auth.ScxAuth;
import cool.scx.ext.core.CoreWebSocketHandler;
import cool.scx.ext.core.WSBody;
import cool.scx.ext.core.WSParam;
import cool.scx.ext.core.WSParamHandlerRegister;
import cool.scx.util.ObjectUtils;
import io.vertx.core.http.ServerWebSocket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>ChatRoomHandler class.</p>
 *
 * @author scx567888
 * @version $Id: $Id
 */
public class ChatRoomHandler {

    /**
     * 发送消息
     *
     * @param wsParam a {@link io.vertx.core.json.JsonObject} object
     */
    private static void sendMessage(WSParam wsParam) {
        //待接收的用户 id
        var sendMessageBody = ObjectUtils.convertValue(wsParam.data(), SendMessageBody.class);

        for (Long userID : sendMessageBody.userIDs) {
            List<AlreadyLoginClient> alreadyLoginClients = ScxAuth.alreadyLoginClients().stream().filter(c -> c.user().id.equals(userID)).toList();
            for (AlreadyLoginClient alreadyLoginClient : alreadyLoginClients) {
                ServerWebSocket webSocket = CoreWebSocketHandler.getWebSocket(alreadyLoginClient.webSocketBinaryHandlerID());
                //连接不为空并且没有关闭
                if (webSocket != null && !webSocket.isClosed()) {
                    webSocket.writeTextMessage(new WSBody("writeMessage", sendMessageBody.message).toJson());
                }
            }
        }

    }

    /**
     * 注册所有的 handler 请在模块 start 中调用
     */
    public static void registerAllHandler() {
        //注册事件
        WSParamHandlerRegister.addHandler("sendMessage", ChatRoomHandler::sendMessage);
        ScxContext.scheduler().scheduleAtFixedRate((state) -> {
            var onlineItemList = CoreWebSocketHandler.getAllWebSockets();
            for (var onlineItem : onlineItemList) {
                onlineItem.writeTextMessage(new WSBody("writeTime", ScxConstant.DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now())).toJson());
            }
        }, Duration.ofSeconds(1));
    }

    public static class SendMessageBody {
        public List<Long> userIDs;
        public Object message;
    }

}
