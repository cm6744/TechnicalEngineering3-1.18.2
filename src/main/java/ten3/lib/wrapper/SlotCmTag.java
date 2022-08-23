package ten3.lib.wrapper;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import ten3.util.TagUtil;

import java.util.List;

public class SlotCmTag extends SlotCm {

    String tag;

    public SlotCmTag(Container i, int id, int x, int y, String valid, boolean ext, boolean in) {

        //bug fixed
        super(i, id, x, y, null, ext, in);

        tag =valid;

        this.ext = ext;
        this.in = in;

    }

    @Override
    public boolean isItemValidInHandler(ItemStack stack) {

        if(tag == null) {
            return true;
        }

        return TagUtil.containsItem(stack.getItem(), tag);

    }

}
