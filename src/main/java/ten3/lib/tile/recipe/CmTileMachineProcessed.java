package ten3.lib.tile.recipe;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.recipe.IBaseRecipeCm;
import ten3.core.recipe.OpportunityRecipe;
import ten3.init.RecipeInit;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Type;
import ten3.util.KeyUtil;

public abstract class CmTileMachineProcessed extends CmTileMachine {

    @Override
    public boolean hasRecipe() {
        return true;
    }

    public RecipeType<? extends Recipe<Container>> recipeType;

    public Recipe<Container> recipeNow;
    Recipe<Container> last;

    @Override
    public Type typeOf() {
        return Type.MACHINE_PROCESS;
    }

    SlotInfo slotInfo;
    boolean hasAdt;

    public CmTileMachineProcessed(BlockPos pos, BlockState state, boolean hasAddition, SlotInfo info) {

        super(pos, state);

        slotInfo = info;
        hasAdt = hasAddition;

    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot)
    {
        return getRcp(recipeType, s) != null;
    }

    public abstract int getTimeCook();

    //need to override sometimes
    public void cacheRcp() {
        recipeNow = getRcp(recipeType, inventory);
    }

    public void shrinkItems() {
        for(int i = slotInfo.ins; i <= slotInfo.ine; i++) {
            ItemStack stack = inventory.getItem(i);
            int c1 = ((IBaseRecipeCm<?>) recipeNow).inputLimit(stack);
            if(!stack.getContainerItem().isEmpty()) {
                ItemStack s2 = stack.getContainerItem();
                s2.setCount(c1);
                Block.popResource(world, pos, s2);
            }
            stack.shrink(c1);
        }
    }

    public void initialRecipeType() {
        recipeType = (RecipeType<? extends Recipe<Container>>) RecipeInit.getRcpType(KeyUtil.exceptMachineOrGiveCell(id));
    }

    @Override
    public void update() {

        super.update();

        if(!checkCanRun()) return;

        if(recipeType == null) {
            initialRecipeType();
        }
        cacheRcp();
        if(last != recipeNow || recipeNow == null) data.set(PROGRESS, 0);
        last = recipeNow;

        if(recipeNow == null) {
            setActive(false);
            return;
        }

        if(energySupportRun()) {

            ItemStack result = recipeNow.assemble(inventory);
            ItemStack addition = ItemStack.EMPTY;
            ItemStack fullAdt = ItemStack.EMPTY;
            if(hasAdt && recipeNow instanceof OpportunityRecipe) {
                addition = ((OpportunityRecipe<Container>) recipeNow).generateAddition();
                fullAdt = ((OpportunityRecipe<Container>) recipeNow).getAdditionOutput();
            }
            data.set(MAX_PROGRESS, getTimeCook());

            if(!itr.selfGive(result, slotInfo.ots, slotInfo.ote, true)
                    || !itr.selfGive(fullAdt, slotInfo.ots, slotInfo.ote, true)) {
                setActive(false);
                return;
            }

            data.translate(ENERGY, -getActual());
            postProgressUp();

            if(data.get(PROGRESS) > getTimeCook()) {
                itr.selfGive(result, slotInfo.ots, slotInfo.ote, false);
                itr.selfGive(addition, slotInfo.ots, slotInfo.ote, false);
                shrinkItems();
                data.set(PROGRESS, 0);
            }
            setActive(true);
        }
        else {
            setActive(false);
        }

    }

    protected boolean canUseRecipeNow() {
        return true;
    }

}
