package ten3.init.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import ten3.TConst;
import ten3.init.ItemInit;

public class DefGroup extends CreativeModeTab {

    public static DefGroup BLOCK = new DefGroup("block");
    public static DefGroup ITEM = new DefGroup("item");

    public DefGroup(String id) {

        super(TConst.modid + "." + id);

    }

    public ItemStack makeIcon() {

        if(this == BLOCK) {
            return ItemInit.getItem("technical_block").getDefaultInstance();
        }
        if(this == ITEM) {
            return ItemInit.getItem("technical_item").getDefaultInstance();
        }
        return ItemStack.EMPTY;

    }

}
