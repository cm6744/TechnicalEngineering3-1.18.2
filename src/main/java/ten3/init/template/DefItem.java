package ten3.init.template;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;
import ten3.init.tab.DefGroup;
import ten3.util.KeyUtil;
import ten3.util.ExcUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DefItem extends Item {

    public static Properties build(int stack, CreativeModeTab tab) {
        return new Properties().tab(tab).stacksTo(stack);
    }

    public DefItem() {
        this(build(64, DefGroup.ITEM));
    }

    public DefItem(Properties prp) {
        super(prp);
    }

    @Override
    public String getDescriptionId()
    {
        return KeyUtil.getKey(ExcUtil.regNameOf(this));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_)
    {
        List<Component> list = new ArrayList<>();

        for(int i = 0; true; i++) {
            //*getPATH!
            String k = "ten3."+ getRegistryName().getPath() +"."+i;
            Component ttc = KeyUtil.translated(KeyUtil.GOLD, k);
            if(ttc.getString().equals(k)) break;

            list.add(ttc);
        }

        if(shift()) {
            tooltip.addAll(list);
        } else if(list.size() > 0) {
            tooltip.add(KeyUtil.translated(KeyUtil.GOLD, "ten3.shift"));
        }
    }

    public static boolean shift() {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS;
    }

}
