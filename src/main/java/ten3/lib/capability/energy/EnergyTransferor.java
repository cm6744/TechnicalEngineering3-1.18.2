package ten3.lib.capability.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.DireUtil;

import static ten3.lib.tile.CmTileMachine.ENERGY;

@SuppressWarnings("all")
public class EnergyTransferor {

    final CmTileMachine t;

    public EnergyTransferor(CmTileMachine t) {
        this.t = t;
    }

    public static IEnergyStorage handlerOf(BlockEntity e, Direction d) {

        return e.getCapability(CapabilityEnergy.ENERGY, d).orElse(null);

    }

    private BlockEntity checkTile(Direction d) {

        return checkTile(t.pos.offset(d.getNormal()));

    }

    private BlockEntity checkTile(BlockPos pos) {

        return t.getLevel().getBlockEntity(pos);

    }

    public void transferTo(BlockPos p, Direction d, int v) {

        if(FaceOption.isPassive(t.direCheckEnergy(d))) return;
        if(!FaceOption.isOut(t.direCheckEnergy(d))) return;

        BlockEntity tile = checkTile(p);

        if(tile != null) {
            IEnergyStorage e = handlerOf(tile, DireUtil.safeOps(d));
            if(e == null) return;
            if(e.canReceive()) {
                int diff = e.receiveEnergy(Math.min(v, t.data.get(ENERGY)), false);
                if(diff != 0) {
                    t.data.translate(ENERGY, -diff);
                }
            }
        }

    }

    public void transferFrom(BlockPos p, Direction d, int v) {

        if(FaceOption.isPassive(t.direCheckEnergy(d))) return;
        if(!FaceOption.isIn(t.direCheckEnergy(d))) return;

        BlockEntity tile = checkTile(p);

        if(tile != null) {
            IEnergyStorage e = handlerOf(tile, DireUtil.safeOps(d));
            if(e == null) return;
            if(e.canExtract()) {
                int diff = e.extractEnergy(Math.min(v, t.maxStorage - t.data.get(ENERGY)), false);
                if(diff != 0) {
                    t.data.translate(ENERGY, diff);
                }
            }
        }

    }

    public void transferTo(Direction d, int v) {

        transferTo(t.pos.offset(d.getNormal()), d, v);

    }

    public void transferFrom(Direction d, int v) {

        transferFrom(t.pos.offset(d.getNormal()), d, v);

    }

    public int getSizeCan() {

        int size = 0;

        BlockEntity tile;

        for(Direction d : Direction.values()) {
            tile = checkTile(d);
            if(tile != null) {
                if(handlerOf(tile, DireUtil.safeOps(d)) != null) {
                    size++;
                }
            }
        }

        return size;

    }

    public void loopTransferTo(int v) {

        int size = getSizeCan();

        int k = Math.min(v, t.data.get(ENERGY));
        if(v < size || size == 0) return;

        for(Direction d : Direction.values()) {
            if(checkTile(d) != null) {
                transferTo(d, k / size);
            }
        }

    }

    public void loopTransferFrom(int v) {

        int size = getSizeCan();

        int k = Math.min(v, t.maxStorage - t.data.get(ENERGY));
        if(v < size || size == 0) return;

        for(Direction d : Direction.values()) {
            if(checkTile(d) != null) {
                transferFrom(d, k / size);
            }
        }

    }

}
