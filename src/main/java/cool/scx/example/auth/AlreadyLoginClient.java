package cool.scx.example.auth;

import cool.scx.example.user.User;

import java.io.Serializable;

/**
 * 已经登录的客户端
 *
 * @author scx567888
 * @version $Id: $Id
 */
public final class AlreadyLoginClient implements Serializable {

    /**
     * 唯一 ID 用于标识用户
     */
    private final User user;

    /**
     * 登陆的设备类型
     */
    private final DeviceType loginDevice;

    /**
     * 本质上一个是一个随机字符串
     * <p>
     * 前端 通过此值获取登录用户
     * <p>
     * 来源可以多种 header , cookie , url 等
     */
    private final String token;

    /**
     * 对应的 webSocketBinaryHandlerID
     */
    private String webSocketBinaryHandlerID;

    /**
     * 构造函数
     *
     * @param loginDevice {@link #loginDevice}
     * @param token       {@link #token}
     * @param user        {@link #user}
     */
    public AlreadyLoginClient(String token, User user, DeviceType loginDevice) {
        this.token = token;
        this.user = user;
        this.loginDevice = loginDevice;
    }

    /**
     * <p>webSocketBinaryHandlerID.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String webSocketBinaryHandlerID() {
        return webSocketBinaryHandlerID;
    }

    /**
     * <p>webSocketBinaryHandlerID.</p>
     *
     * @param webSocketBinaryHandlerID a {@link java.lang.String} object
     */
    public void webSocketBinaryHandlerID(String webSocketBinaryHandlerID) {
        this.webSocketBinaryHandlerID = webSocketBinaryHandlerID;
    }

    /**
     * <p>user.</p>
     *
     * @return a {@link cool.scx.example.user.User} object
     */
    public User user() {
        return user;
    }

    /**
     * <p>loginDevice.</p>
     *
     * @return a {@link cool.scx.example.auth.DeviceType} object
     */
    public DeviceType loginDevice() {
        return loginDevice;
    }

    /**
     * <p>token.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String token() {
        return token;
    }

}
