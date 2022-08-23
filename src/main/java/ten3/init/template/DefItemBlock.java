package ten3.init.template;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import ten3.TConst;
import ten3.init.tab.DefGroup;
import ten3.util.ExcUtil;
import ten3.util.KeyUtil;

public class DefItemBlock extends BlockItem {

    public DefItemBlock(Block b) {

        this(b, DefGroup.BLOCK, 64);

    }

    public DefItemBlock(Block b, int size) {

        this(b, DefGroup.BLOCK, size);

    }

    public DefItemBlock(Block b, CreativeModeTab g, int size) {

        super(b, new Properties().tab(g).stacksTo(size));

    }

    @Override
    public Component getName(ItemStack p_41458_)
    {
        return KeyUtil.getKey(ExcUtil.regNameOf(this));
    }

}
