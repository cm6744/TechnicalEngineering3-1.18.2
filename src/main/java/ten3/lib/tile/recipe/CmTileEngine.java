package ten3.lib.tile.recipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.SlotCustomCm;

public abstract class CmTileEngine extends CmTileMachine {

    public CmTileEngine(BlockPos pos, BlockState state) {
        super(pos, state);
        addSlot(new SlotCustomCm(inventory, 0, 42, 36,
                (s) -> matchFuelAndShrink(s, true) > 0, false, true));
    }

    @Override
    public Type typeOf() {
        return Type.GENERATOR;
    }

    @Override
    public void update() {

        super.update();

        if(!checkCanRun()) return;

        if(energySupportRun()) {
            ItemStack ext = inventory.getItem(0);

            if(data.get(FUEL) > 0) {
                data.translate(ENERGY, getActual(), maxStorage);//up energy
                data.translate(FUEL, -getActual(), 0);//down fuel
            }
            else {
                int fuel_p = matchFuelAndShrink(ext, false);
                if(fuel_p > 0) {
                    data.set(FUEL, fuel_p);
                    data.set(MAX_FUEL, fuel_p);
                }
            }
        }

    }

    @Override
    protected boolean can(Capability<?> cap, Direction d) {
        if(cap == CapabilityEnergy.ENERGY) {
            return d == null || d == Direction.UP;
        }
        return d != Direction.UP;
    }

    public abstract int matchFuelAndShrink(ItemStack stack, boolean simulate);

}
