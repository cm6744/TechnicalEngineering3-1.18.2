package ten3.core.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyBindingMap;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import ten3.core.network.Network;
import ten3.core.network.packets.PTSChangeModePack;

import static ten3.core.client.TEKeyRegistry.C_CHANGE_MODE;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class TEKeys
{

    @SubscribeEvent
    public static void inputEvent(InputEvent.KeyInputEvent e)
    {
        if(C_CHANGE_MODE.consumeClick())
        {
            Network.sendToServer(new PTSChangeModePack());
        }
    }

}
