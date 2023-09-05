package ten3.lib.tile.mac;

import net.minecraft.core.Direction;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.IntArrayCm;
import ten3.util.DirectionHelper;

public class TransferManager {

    CmTileMachine tile;

    public int maxReceiveEnergy;
    public int maxExtractEnergy;
    public int maxReceiveItem;
    public int maxExtractItem;
    public int maxReceiveFluid;
    public int maxExtractFluid;
    public int maxStorageEnergy;

    public int initialEnergyStorage;//initial vars won't be change!
    public int initialEnergyReceive;//initial vars won't be change!
    public int initialEnergyExtract;//initial vars won't be change!
    public int initialItemReceive;
    public int initialItemExtract;
    public int initialFluidReceive;
    public int initialFluidExtract;

    public IntArrayCm energyAllow = new IntArrayCm(6);
    public IntArrayCm itemAllow = new IntArrayCm(6);
    public IntArrayCm fluidAllow = new IntArrayCm(6);

    public TransferManager(CmTileMachine t) {
        tile = t;
    }

    public void setCap(int store, int itm, int fld) {

        initialEnergyReceive = maxReceiveEnergy = tile.typeOf() == Type.NON_MAC ? store : store / 200;
        initialEnergyExtract = maxExtractEnergy = tile.typeOf() == Type.NON_MAC ? store : store / 200;
        initialEnergyStorage = maxStorageEnergy = store;
        initialItemExtract = maxExtractItem = itm;
        initialItemReceive = maxReceiveItem = itm;
        initialFluidExtract = maxExtractFluid = fld;
        initialFluidReceive = maxReceiveFluid = fld;

    }

    public void setCap(int store) {
        setCap(store, 8, 100);
    }

    public void resetAll() {
        maxExtractItem = initialItemExtract;
        maxReceiveItem = initialItemReceive;
        maxExtractFluid = initialFluidExtract;
        maxReceiveFluid = initialFluidReceive;
        maxExtractEnergy = initialEnergyExtract;
        maxReceiveEnergy = initialEnergyReceive;
        maxStorageEnergy = initialEnergyStorage;//reset all, but data storage them to client!
    }

    public int direCheckFluid(Direction d) {
        if(!tile.hasFaceCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, d)) {
            return FaceOption.NONE;
        }
        if(d == null) return FaceOption.BOTH;
        return fluidAllow.get(DirectionHelper.direToInt(d));
    }

    public void setOpenFluid(Direction d, int mode) {
        if(tile.hasFaceCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, d))
            fluidAllow.set(DirectionHelper.direToInt(d), mode);
    }

    public int direCheckItem(Direction d) {
        if(!tile.hasFaceCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, d)) {
            return FaceOption.NONE;
        }
        if(d == null) return FaceOption.BOTH;
        return itemAllow.get(DirectionHelper.direToInt(d));
    }

    public void setOpenItem(Direction d, int mode) {
        if(tile.hasFaceCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, d))
            itemAllow.set(DirectionHelper.direToInt(d), mode);
    }

    public int direCheckEnergy(Direction d) {
        if(!tile.hasFaceCapability(CapabilityEnergy.ENERGY, d)) {
            return FaceOption.NONE;
        }
        if(d == null) return FaceOption.BOTH;
        return energyAllow.get(DirectionHelper.direToInt(d));
    }

    public void setOpenEnergy(Direction d, int mode) {
        if(tile.hasFaceCapability(CapabilityEnergy.ENERGY, d))
            energyAllow.set(DirectionHelper.direToInt(d), mode);
    }

}
