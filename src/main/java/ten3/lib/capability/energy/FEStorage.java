package ten3.lib.capability.energy;

import net.minecraftforge.energy.IEnergyStorage;

public class FEStorage implements IEnergyStorage {

    protected int pro_energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public FEStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {

        if (!canReceive()) {
            return 0;
        }
        int energyReceived = Math.min(getMaxEnergyStored() - getEnergyStored(), Math.min(getMaxReceive(), maxReceive));
        if (!simulate) {
            translateEnergy(energyReceived);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {

        if (!canExtract()) {
            return 0;
        }
        int energyExtracted = Math.min(getEnergyStored(), Math.min(getMaxExtract(), maxExtract));
        if (!simulate) {
            translateEnergy(-energyExtracted);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return pro_energy;
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public void translateEnergy(int diff) {
        pro_energy += diff;
    }
    public void setEnergy(int diff) {
        pro_energy = diff;
    }

    @Override
    public int getMaxEnergyStored() {

        return capacity;
    }

    @Override
    public boolean canExtract() {

        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {

        return this.maxReceive > 0;
    }

}
