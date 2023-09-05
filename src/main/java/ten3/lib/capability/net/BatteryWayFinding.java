package ten3.lib.capability.net;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.IEnergyStorage;
import ten3.core.machine.cable.CableTile;
import ten3.lib.capability.energy.EnergyTransferor;
import ten3.lib.capability.energy.BatteryTile;
import ten3.util.SafeOperationHelper;

import java.util.*;

//connect generator with machine.
//#find - return a list of all connections
public class BatteryWayFinding extends BatteryTile
{

    public static LevelNetsManager<IEnergyStorage> manager = new LevelNetsManager<>();

    public static void updateNet(CableTile tile) {
        find(tile);
    }

    private static void find(CableTile start) {
        Finder.find(manager,
                (t, d) -> EnergyTransferor.handlerOf(t, d),
                (t) -> t instanceof CableTile,
                (t) -> ((CableTile) t).getCapacity(),
                start);
    }

    private int cap() {
        return manager.getLevelNet(tile).getNet(tile.getBlockPos()).getCap((e) -> {
            if(e instanceof CableTile) return ((CableTile) e).getCapacity();
            return Integer.MAX_VALUE;
        });
    }

    private void hangPoses() {
        Set<BlockEntity> tiles = manager.getLevelNet(tile).getNet(tile.getBlockPos()).netPoses;
        for(BlockEntity t : tiles) {
            if(t instanceof CableTile) {
                ((CableTile) t).hangUp();
            }
        }
    }

    IEnergyStorage object;

    public BatteryWayFinding(Direction d, CableTile t) {

        super(d, t);
        object = SafeOperationHelper.randomInCollection(hand());

    }

    private List<IEnergyStorage> hand() {
        return manager.getLevelNet(tile).getNet(tile.getBlockPos()).elements;
    }

    @Override
    public int receiveEnergy(int receive, boolean simulate) {
        if(canReceive()) {
            return listTransferTo(hand(), receive, simulate);
        }
        return 0;
    }

    @Override
    public int extractEnergy(int extract, boolean simulate) {
        if(canExtract()) {
            return listTransferFrom(hand(), extract, simulate);
        }
        return 0;
    }

    @Override
    public boolean canReceive() {
        return tile.signalAllowRun();
    }

    @Override
    public boolean canExtract() {
        return tile.signalAllowRun();
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return tile.info.maxStorageEnergy;
    }

    public int listTransferTo(List<IEnergyStorage> es, int v, boolean s) {

        int size = getSizeCanTrsIn(es);

        if(es == null) return 0;
        if(size == 0) return 0;

        if(v < size) {
            return SafeOperationHelper.randomInCollection(es).receiveEnergy(Math.min(cap(), v), s);
        }

        int total = 0;

        for(IEnergyStorage e : es) {
            if(!e.canReceive()) continue;
            int diff = e.receiveEnergy(Math.min(cap(), v / size), s);
            total += diff;
        }

        if(total > 0) {
            hangPoses();
        }

        return total;

    }

    public int listTransferFrom(List<IEnergyStorage> es, int v, boolean s) {

        int size = getSizeCanTrsOut(es);

        if(es == null) return 0;
        if(size == 0) return 0;

        if(v < size) {
            return SafeOperationHelper.randomInCollection(es).extractEnergy(Math.min(cap(), v), s);
        }

        int total = 0;

        for(IEnergyStorage e : es) {
            if(!e.canExtract()) continue;
            int diff = e.extractEnergy(Math.min(cap(), v / size), s);
            total += diff;
        }

        return total;

    }


    public int getSizeCanTrsOut(List<IEnergyStorage> es) {
        int size = 0;
        if(es == null) return 0;
        for(IEnergyStorage e : es) {
            if(e.canExtract()) {
                size++;
            }
        }
        return size;
    }

    public int getSizeCanTrsIn(List<IEnergyStorage> es) {
        int size = 0;
        if(es == null) return 0;
        for(IEnergyStorage e : es) {
            if(e.canReceive()) {
                size++;
            }
        }
        return size;
    }

}
