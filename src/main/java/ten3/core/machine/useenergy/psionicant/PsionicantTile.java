package ten3.core.machine.useenergy.psionicant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.recipe.IBaseRecipeCm;
import ten3.init.RecipeInit;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.recipe.CmTileMachineProcessed;
import ten3.lib.tile.recipe.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ExcUtil;
import ten3.util.KeyUtil;

import java.util.Collection;
import java.util.List;

import static ten3.util.ExcUtil.hasRcpUseThisItem;

public class PsionicantTile extends CmTileMachineProcessed {

    public PsionicantTile(BlockPos pos, BlockState state) {

        super(pos, state, false, new SlotInfo(0, 1, 2, 2));

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 50);

        addSlot(new SlotCustomCm(inventory, 0, 33, 20, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 1, 51, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 2, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot)
    {
        List<ItemStack> lst = inventory.getStackInRange(0, 1);
        for(ItemStack s2 : lst) {
            if(s2.getItem() == s.getItem()) return false;
        }
        if(lst.size() == 0) {
            return hasRcpUseThisItem(world, recipeType, s);
        }
        Collection<Recipe<Container>> recs = ExcUtil.getRcpUseThisItem(world, recipeType, lst.get(0));
        return ExcUtil.hasRcpUseThisItem(world, recipeType, recs, s);
    }

    @Override
    public int getTimeCook() {
        return ((IBaseRecipeCm<?>) recipeNow).time();
    }

}
