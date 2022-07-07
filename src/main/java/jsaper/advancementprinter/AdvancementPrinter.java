package jsaper.advancementprinter;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class AdvancementPrinter implements ModInitializer {

    public static final  String MOD_ID = "advancementprinter";
    public static final String MOD_NAME = "Advancement Printer";
    public static File FILE_DIR;
    public static Logger LOGGER = LogManager.getLogger();

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }
    @Override
    public void onInitialize() {
    }
}
