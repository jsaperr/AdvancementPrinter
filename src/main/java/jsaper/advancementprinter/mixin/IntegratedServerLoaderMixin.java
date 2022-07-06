package jsaper.advancementprinter.mixin;

import jsaper.advancementprinter.AdvancementPrinter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.server.DataPackContents;
import net.minecraft.server.integrated.IntegratedServerLoader;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Mixin(IntegratedServerLoader.class)
public class IntegratedServerLoaderMixin {

    @Inject(at = @At("HEAD"), method = "start(Lnet/minecraft/world/level/storage/LevelStorage$Session;Lnet/minecraft/server/DataPackContents;Lnet/minecraft/util/registry/DynamicRegistryManager$Immutable;Lnet/minecraft/world/SaveProperties;)V")
    public void onCreate(LevelStorage.Session session, DataPackContents dataPackContents, DynamicRegistryManager.Immutable dynamicRegistryManager, SaveProperties saveProperties, CallbackInfo ci) {
        Path worldPath = MinecraftClient.getInstance().getLevelStorage().getSavesDirectory().resolve(session.getDirectoryName());
        File worldDirectory = worldPath.toFile();
        if (worldDirectory.exists()) {
            worldDirectory.mkdirs();
        }

        File file = new File(worldDirectory, "advancement_tracker.txt");
        if (!file.exists()) {
            try {
                FileUtils.writeStringToFile(new File(worldDirectory, "advancement_tracker.txt"), "Advancement Tracker", StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            AdvancementPrinter.FILE_DIR = file;
        }

    }

    @Inject(at = @At("HEAD"), method = "start(Lnet/minecraft/client/gui/screen/Screen;Ljava/lang/String;)V")
    public void onWorldOpen(Screen parent, String levelName, CallbackInfo ci) {

        Path worldPath = MinecraftClient.getInstance().getLevelStorage().getSavesDirectory().resolve(levelName);
        File worldDirectory = worldPath.toFile();
        if (worldDirectory.exists()) {
            worldDirectory.mkdirs();
        }

        File file = new File(worldDirectory, "advancement_tracker.txt");
        if (!file.exists()) {
            try {
                FileUtils.writeStringToFile(new File(worldDirectory, "advancement_tracker.txt"), "Advancement Tracker", StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            AdvancementPrinter.FILE_DIR = file;
        } else {
            AdvancementPrinter.LOGGER.log(Level.INFO,AdvancementPrinter.MOD_ID + ": File already exists.");
        }

    }

    }
