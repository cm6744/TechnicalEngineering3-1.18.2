package ten3.core.machine.useenergy.farm;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.extension.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCm;
import ten3.util.WorkingHelper;

import java.util.List;

public class FarmTile extends CmTileMachineRadiused {

    public FarmTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(20));
        setEfficiency(10);
        initialRadius = 4;

        addSlot(new SlotCm(this, 0, 43, 16));
        addSlot(new SlotCm(this, 1, 61, 16));
        addSlot(new SlotCm(this, 2, 43, 34));
        addSlot(new SlotCm(this, 3, 61, 34));
        addSlot(new SlotCm(this, 4, 43, 52));
        addSlot(new SlotCm(this, 5, 61, 52));

        addSlot(new SlotCm(this, 6, 97, 16));
        addSlot(new SlotCm(this, 7, 115, 16));
        addSlot(new SlotCm(this, 8, 97, 34));
        addSlot(new SlotCm(this, 9, 115, 34));
        addSlot(new SlotCm(this, 10, 97, 52));
        addSlot(new SlotCm(this, 11, 115, 52));

    }

    public int inventorySize()
    {
        return 12;
    }

    public IngredientType slotType(int slot)
    {
        if(slot <= 5) {
            return IngredientType.INPUT;
        }
        else {
            return IngredientType.OUTPUT;
        }
    }

    public boolean valid(int slot, ItemStack stack)
    {
        if(slot <= 5) {
            if(stack.getItem() instanceof BlockItem s) {
                return s.getBlock() instanceof CropBlock;
            }
            else {
                return false;
            }
        }
        return true;
    }

    public IngredientType tankType(int tank)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return true;
    }

    public void effect()
    {
        WorkingHelper.runIn(radius, worldPosition, (pin) -> {
            BlockPos pd = pin.below();
            BlockState s = level.getBlockState(pin);
            BlockState sd = level.getBlockState(pd);
            boolean ret = false;

            if(s.getBlock() instanceof CropBlock cr) {
                int age = s.getValue(cr.getAgeProperty());
                if(age >= cr.getMaxAge()) {
                    List<ItemStack> ss = s.getDrops(WorkingHelper.getLoot(level, worldPosition, ItemStack.EMPTY));
                    if(itr.selfGiveList(ss, true)) {
                        itr.selfGiveList(ss, false);
                        level.destroyBlock(pin, false);
                        ret = true;
                    }
                }
            }

            if(sd.isFertile(level, worldPosition) && s.isAir()) {
                ItemStack seedSim = itr.selfGet(1, 0, 5, true);
                if(seedSim.getItem() instanceof BlockItem) {
                    Block plant = ((BlockItem) seedSim.getItem()).getBlock();
                    if(plant instanceof CropBlock) {
                        level.setBlock(pin, plant.defaultBlockState(), 3);
                        itr.selfGet(1, 0, 5, false);
                        ret = true;
                    }
                }
            }
            //end
            return ret;
        });
    }

    public double seconds()
    {
        return 20;
    }

}
