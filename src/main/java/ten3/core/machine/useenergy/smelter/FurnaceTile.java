package ten3.core.machine.useenergy.smelter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.item.upgrades.LevelupBlast;
import ten3.core.item.upgrades.LevelupSmoke;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.lib.tile.extension.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;

public class FurnaceTile extends CmTileMachineRecipe
{

    public FurnaceTile(BlockPos pos, BlockState state) {

        super(pos, state, new SlotInfo(0, 0, 1, 1));

        info.setCap(kFE(20));
        setEfficiency(15);

        recipeType = RecipeType.SMELTING;

        addSlot(new SlotCustomCm(inventory, 0, 43, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 1, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public void initialRecipeType()
    {
        recipeType = RecipeType.SMELTING;
    }

    public RecipeCheckType slotType(int slot)
    {
        if(slot == 0) return RecipeCheckType.INPUT;
        if(slot == 1) return RecipeCheckType.OUTPUT;
        return RecipeCheckType.IGNORE;
    }

    public RecipeCheckType tankType(int tank)
    {
        return RecipeCheckType.IGNORE;
    }

    @Override
    public void shrinkItems() {
        for(int i = slotInfo.ins; i <= slotInfo.ine; i++) {
            ItemStack stack = inventory.getItem(i);
            int c1 = 1;
            if(!stack.getContainerItem().isEmpty()) {
                ItemStack s2 = stack.getContainerItem();
                s2.setCount(c1);
                Block.popResource(level, worldPosition, s2);
            }
            stack.shrink(c1);
        }
    }

    @Override
    public int ticks() {
        if((upgradeSlots.countUpgrade(LevelupSmoke.class) > 0 && getRecipe(RecipeType.SMOKING, inventory) != null)
                || (upgradeSlots.countUpgrade(LevelupBlast.class) > 0 && getRecipe(RecipeType.BLASTING, inventory) != null)) {
            return ((SmeltingRecipe) recipeNow).getCookingTime() / 4;
        }
        return ((SmeltingRecipe) recipeNow).getCookingTime() / 2;
    }

}
