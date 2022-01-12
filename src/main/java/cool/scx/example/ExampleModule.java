package cool.scx.example;

import cool.scx.Scx;
import cool.scx.ScxModule;
import cool.scx.enumeration.ScxFeature;
import cool.scx.example.auth.ScxAuth;
import cool.scx.example.chat_room.ChatRoomHandler;
import cool.scx.ext.cms.CMSModule;
import cool.scx.ext.core.CoreModule;
import cool.scx.ext.crud.CRUDModule;
import cool.scx.ext.fixtable.FixTableModule;
import cool.scx.ext.fss.FSSModule;

/**
 * <p>TestModule class.</p>
 *
 * @author scx567888
 * @version 1.3.14
 * @since 1.3.14
 */
public class ExampleModule implements ScxModule {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link String} objects
     */
    public static void main(String[] args) {
        Scx.builder()
                .setMainClass(ExampleModule.class)
                .configure(ScxFeature.USE_DEVELOPMENT_ERROR_PAGE, true)
                .addModules(
                        new CMSModule(),
                        new CoreModule(),
                        new CRUDModule(),
                        new FixTableModule(),
                        new FSSModule(),
                        new ExampleModule())
                .setArgs(args)
                .build().run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        ChatRoomHandler.registerAllHandler();
        ScxAuth.initAuth();
        ScxAuth.readSessionFromFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        ScxAuth.writeSessionToFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return "SCX_EXT-" + ScxModule.super.name();
    }

}
