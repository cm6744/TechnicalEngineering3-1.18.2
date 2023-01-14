package ten3.core.machine.useenergy.farm;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.extension.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.WorkUtil;

import java.util.List;

public class FarmTile extends CmTileMachineRadiused {

    public FarmTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(20));
        setEfficiency(10);
        initialRadius = 4;

        SlotCustomCm.Condition onlySeed = (s) -> {
            if(s.getItem() instanceof BlockItem) {
                return ((BlockItem) s.getItem()).getBlock() instanceof CropBlock;
            }
            return false;
        };

        addSlot(new SlotCustomCm(inventory, 0, 43, 16, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 1, 61, 16, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 2, 43, 34, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 3, 61, 34, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 4, 43, 52, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 5, 61, 52, onlySeed, false, true));

        addSlot(new SlotCm(inventory, 6, 97, 16, null, true, false));
        addSlot(new SlotCm(inventory, 7, 115, 16, null, true, false));
        addSlot(new SlotCm(inventory, 8, 97, 34, null, true, false));
        addSlot(new SlotCm(inventory, 9, 115, 34, null, true, false));
        addSlot(new SlotCm(inventory, 10, 97, 52, null, true, false));
        addSlot(new SlotCm(inventory, 11, 115, 52, null, true, false));

    }

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    public void effect()
    {
        WorkUtil.runIn(radius, worldPosition, (pin) -> {
            BlockPos pd = pin.below();
            BlockState s = level.getBlockState(pin);
            BlockState sd = level.getBlockState(pd);
            boolean ret = false;

            if(s.getBlock() instanceof CropBlock cr) {
                int age = s.getValue(cr.getAgeProperty());
                if(age >= cr.getMaxAge()) {
                    List<ItemStack> ss = s.getDrops(WorkUtil.getLoot(level, worldPosition, ItemStack.EMPTY));
                    if(itr.selfGiveList(ss, true)) {
                        itr.selfGiveList(ss, false);
                        level.destroyBlock(pin, false);
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
