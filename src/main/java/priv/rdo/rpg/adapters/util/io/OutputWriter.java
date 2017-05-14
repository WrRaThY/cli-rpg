package priv.rdo.rpg.adapters.util.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

public class OutputWriter {
    private static final Logger LOG = LogManager.getLogger(OutputWriter.class);
    private final PrintStream out;

    public OutputWriter(PrintStream out) {
        this.out = out;
    }

    public void showMessageWithSpace(String msg) {
        out.println();
        showMessage(msg);
    }

    public void showMessage(String msg) {
        out.println(msg);
        LOG.trace("User got the following message:\n{}", msg);
    }
}
