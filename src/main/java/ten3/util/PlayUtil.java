package ten3.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayUtil {

    public static void giveAdvancement(String name, Player player) {
        if(!(player instanceof ServerPlayer)) return;
        Advancement adv = ((ServerPlayer) player).server.getAdvancements()
                .getAdvancement(new ResourceLocation("ten3", name));
        AdvancementProgress ap = ((ServerPlayer) player).getAdvancements().getOrStartProgress(adv);
        if (!ap.isDone()) {
            for(String criterion : ap.getCompletedCriteria()) {
                ((ServerPlayer) player).getAdvancements().award(adv, criterion);
            }
        }
    }

}
