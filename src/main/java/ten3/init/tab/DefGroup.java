package ten3.init.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import ten3.TConst;
import ten3.init.ItemInit;

public class DefGroup extends CreativeModeTab {

    public static DefGroup BLOCK = new DefGroup("block");
    public static DefGroup MAC = new DefGroup("machine");
    public static DefGroup ITEM = new DefGroup("item");
    public static DefGroup TOOL = new DefGroup("tool");

    public DefGroup(String id) {

        super(TConst.modid + "." + id);

    }

    public ItemStack makeIcon() {

        if(this == BLOCK) {
            return ItemInit.getItem("tin_ore").getDefaultInstance();
        }
        if(this == ITEM) {
            return ItemInit.getItem("tin_ingot").getDefaultInstance();
        }
        if(this == MAC) {
            return ItemInit.getItem("machine_pulverizer").getDefaultInstance();
        }
        if(this == TOOL) {
            return ItemInit.getItem("photosyn_levelup").getDefaultInstance();
        }
        return ItemStack.EMPTY;

    }

}
