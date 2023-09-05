package ten3.core.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TEKeyRegistry
{

    public static final KeyMapping C_CHANGE_MODE =
            new KeyMapping("ten3.key.c",
                           KeyConflictContext.IN_GAME,
                           KeyModifier.NONE,
                           InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_C),
                           "key.categories.misc"
            );

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent e)
    {
        ClientRegistry.registerKeyBinding(C_CHANGE_MODE);
    }

}
