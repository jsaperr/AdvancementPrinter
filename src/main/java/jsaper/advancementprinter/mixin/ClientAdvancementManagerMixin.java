package jsaper.advancementprinter.mixin;


import jsaper.advancementprinter.AdvancementPrinter;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import net.minecraft.util.Identifier;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Mixin(ClientAdvancementManager.class)
public class ClientAdvancementManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("RETURN"), method = "onAdvancements")
    public void onComplete(AdvancementUpdateS2CPacket packet, CallbackInfo ci) throws IOException {

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


        Map<Identifier, Advancement.Builder> advancementsToEarn = packet.getAdvancementsToEarn();
        for (Map.Entry<Identifier, Advancement.Builder> advancementEntry: advancementsToEarn.entrySet()) {
            Advancement advancement = advancementEntry.getValue().build(advancementEntry.getKey());

            String advancementName = advancement.getDisplay().getTitle().getString();

            FileUtils.writeStringToFile(AdvancementPrinter.FILE_DIR, "\n" + dateTime.format(formatter)+": "+ advancementName + "\n", StandardCharsets.UTF_8, true);


        }
    }
}
