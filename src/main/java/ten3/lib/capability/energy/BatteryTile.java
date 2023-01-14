package ten3.lib.capability.energy;

import net.minecraft.core.Direction;
import ten3.lib.capability.UtilCap;
import ten3.lib.tile.mac.CmTileMachine;

import static ten3.lib.tile.mac.CmTileMachine.ENERGY;

public class BatteryTile extends Battery
{

    Direction di;
    public CmTileMachine tile;

    public BatteryTile(Direction d, CmTileMachine t) {

        super(0, 0, 0);

        di = d;
        tile = t;

    }

    public BatteryTile with(Direction d) {
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
        return tile.info.maxExtractEnergy;
    }

    @Override
    public int getMaxReceive()
    {
        return tile.info.maxReceiveEnergy;
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
        return tile.info.maxStorageEnergy;
    }

    @Override
    public boolean canExtract() {
        return UtilCap.canOut(di, tile);
    }

    @Override
    public boolean canReceive() {
        return UtilCap.canIn(di, tile);
    }

}
