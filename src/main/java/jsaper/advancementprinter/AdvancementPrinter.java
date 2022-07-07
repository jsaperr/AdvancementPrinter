package jsaper.advancementprinter;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class AdvancementPrinter implements ModInitializer {
    public static final  String MOD_ID = "advancementprinter";
    public static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
    public static final String MOD_NAME = MOD_CONTAINER.getMetadata().getName();
    public static File FILE_DIR;
    public static Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }
    
    @Override
    public void onInitialize() {
        log("Initializing");
    }
}
