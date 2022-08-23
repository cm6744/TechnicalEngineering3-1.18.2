package ten3.lib.capability.energy;

import net.minecraft.core.Direction;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;

import static ten3.lib.tile.CmTileMachine.ENERGY;

public class FEStorageTile extends FEStorage {

    Direction di;
    CmTileMachine tile;

    public FEStorageTile(Direction d, CmTileMachine t) {

        super(t.maxStorage, t.maxReceive, t.maxExtract);

        di = d;
        tile = t;

    }

    public FEStorageTile with(Direction d) {
        di = d;
        return this;
    }

    @Override
    public int receiveEnergy(int receive, boolean simulate) {
        if(canReceive()) {
            return super.receiveEnergy(receive, simulate);
        }
        return 0;
    }

    @Override
    public int getMaxExtract()
    {
        return tile.maxExtract;
    }

    @Override
    public int getMaxReceive()
    {
        return tile.maxReceive;
    }

    @Override
    public int extractEnergy(int extract, boolean simulate) {
        if(canExtract()) {
            return super.extractEnergy(extract, simulate);
        }
        return 0;
    }

    @Override
    public void translateEnergy(int diff) {
        tile.data.translate(ENERGY, diff);
    }

    @Override
    public void setEnergy(int diff) {
        tile.data.set(ENERGY, diff);
    }

    @Override
    public int getEnergyStored() {
        return tile.data.get(ENERGY);
    }

    @Override
    public int getMaxEnergyStored() {
        return tile.maxStorage;
    }

    @Override
    public boolean canExtract() {
        return tile.maxExtract > 0
                &&
                (di == null
                        || tile.direCheckEnergy(di) == FaceOption.OUT
                        || tile.direCheckEnergy(di) == FaceOption.BE_OUT
                        || tile.direCheckEnergy(di) == FaceOption.BOTH
                )
                && tile.checkCanRun();
                //&& tile.openEnergy;
    }

    @Override
    public boolean canReceive() {
        return tile.maxReceive > 0
                &&
                (di == null
                        || tile.direCheckEnergy(di) == FaceOption.IN
                        || tile.direCheckEnergy(di) == FaceOption.BE_IN
                        || tile.direCheckEnergy(di) == FaceOption.BOTH
                )
                && tile.checkCanRun();
                //&& tile.openEnergy;
    }

}
