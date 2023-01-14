package ten3.core.machine.useenergy.psionicant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import ten3.lib.recipe.IBaseRecipeCm;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.lib.tile.extension.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ExcUtil;

import java.util.Collection;
import java.util.List;

import static ten3.util.ExcUtil.hasRcpUseThisItem;

public class PsionicantTile extends CmTileMachineRecipe
{

    public PsionicantTile(BlockPos pos, BlockState state) {

        super(pos, state, new SlotInfo(0, 1, 2, 2));

        info.setCap(kFE(20));
        setEfficiency(50);

        addSlot(new SlotCustomCm(inventory, 0, 34, 20, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 1, 52, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 2, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    public RecipeCheckType slotType(int slot)
    {
        if(slot == 0 || slot == 1) return RecipeCheckType.INPUT;
        if(slot == 2) return RecipeCheckType.OUTPUT;
        return RecipeCheckType.IGNORE;
    }

    public RecipeCheckType tankType(int tank)
    {
        return RecipeCheckType.IGNORE;
    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot)
    {
        List<ItemStack> lst = inventory.getStackInRange(0, 1);
        for(ItemStack s2 : lst) {
            if(s2.getItem() == s.getItem()) return false;
        }
        if(lst.size() == 0) {
            return hasRcpUseThisItem(level, recipeType, s);
        }
        Collection<Recipe<Container>> recs = ExcUtil.getRcpUseThisItem(level, recipeType, lst.get(0));
        return ExcUtil.hasRcpUseThisItem(level, recipeType, recs, s);
    }

    @Override
    public int ticks() {
        return ((IBaseRecipeCm<?>) recipeNow).time();
    }

}
