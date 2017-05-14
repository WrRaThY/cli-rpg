package priv.rdo.rpg.adapters.incoming;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.rdo.rpg.adapters.util.io.UserInputParseException;
import priv.rdo.rpg.common.context.DIContext;
import priv.rdo.rpg.domain.menu.MainMenuManager;
import priv.rdo.rpg.ports.exception.ConfigurationException;

public class CliGameLauncher {
    private static final Logger LOG = LogManager.getLogger(CliGameLauncher.class);

    private static MainMenuManager mainMenuManager = DIContext.getBean(MainMenuManager.class);

    public static void startGame() {
        try {
            LOG.debug("CLI calling MainMenuManager...");
            mainMenuManager.showMenu();
        } catch (UserInputParseException e) {
            shutdown(e.getMessage(), e);
        } catch (ConfigurationException e) {
            if (null != e.getMessage()) {
                shutdown(e.getMessage(), e);
            } else {
                String msg = "There was a problem with the configuration. Please ask your local IT for support.\n" +
                        "I'm sure they will come up with a solution to your problem (for example 'have you tried turning it off and on again')";
                shutdown(msg, e);
            }

        } catch (Throwable t) {
            String msg = "There was a general problem with the game. Pray to God that it will work next time.";
            shutdown(msg, t);
        }
    }

    private static void shutdown(String msg, Throwable e) {
        System.out.println(msg);
        LOG.fatal(msg, e);
        System.exit(1);
    }
}
