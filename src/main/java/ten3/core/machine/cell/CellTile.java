package ten3.core.machine.cell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.SlotCm;

public class CellTile extends CmTileMachine {

    public CellTile(BlockPos pos, BlockState state) {

        super(pos, state);

        setCap(getCapacity(), FaceOption.BE_IN, FaceOption.OFF, 0);

        addSlot(new SlotCm(inventory, 0, 42, 32, null, true, true));
        addSlot(new SlotCm(inventory, 1, 115, 32, null, true, true));

    }

    @Override
    public Type typeOf() {
        return Type.CELL;
    }

    public int getCapacity() {

        return kFE(500);

    }

    public void update() {

        super.update();

        if(!checkCanRun()) {
            return;
        }

        ItemStack stack0 = inventory.getItem(0);
        ItemStack stack1 = inventory.getItem(1);

        if(stack0.getCount() == 1) {
            stack0.getCapability(CapabilityEnergy.ENERGY).ifPresent(
                    (e) -> {
                        if(e.canExtract()) {
                            int diff = e.extractEnergy(Math.min(maxReceive, maxStorage - data.get(ENERGY)), false);
                            if(diff != 0) {
                                data.translate(ENERGY, diff);
                            }
                        }
                    }
            );
        }

        if(stack1.getCount() == 1) {
            stack1.getCapability(CapabilityEnergy.ENERGY).ifPresent(
                    (e) -> {
                        if(e.canReceive()) {
                            int diff = e.receiveEnergy(Math.min(maxExtract, data.get(ENERGY)), false);
                            if(diff != 0) {
                                data.translate(ENERGY, -diff);
                            }
                        }
                    }
            );
        }

    }

}
