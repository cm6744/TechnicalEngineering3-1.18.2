package ten3.lib.tile.mac;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import ten3.lib.wrapper.IntArrayCm;

import static ten3.lib.tile.mac.CmTileMachine.*;

public class MacNBTManager {

    CmTileMachine t;
    IntArrayCm data;
    IntArrayCm energyAllow;
    IntArrayCm itemAllow;
    IntArrayCm fluidAllow;

    public MacNBTManager(CmTileMachine tile) {
        t = tile;
        data = t.data;
        energyAllow = t.info.energyAllow;
        itemAllow = t.info.itemAllow;
        fluidAllow = t.info.fluidAllow;
    }

    public void rdt(CompoundTag nbt) {

        for(int i = 0; i < t.inventory.getContainerSize(); i++) {
            t.inventory.setItem(i, ItemStack.of(nbt.getCompound("item" + i)));
        }
        for(int i = 0; i < t.tanks.size(); i++) {
            t.tanks.get(i).readFromNBT(nbt);
        }
        t.init = nbt.getBoolean("whenPlaceToWorld");

        data.set(PROGRESS, nbt.getInt("progress"));
        data.set(MAX_PROGRESS, nbt.getInt("seconds"));
        data.set(ENERGY, nbt.getInt("energy"));
        data.set(FUEL, nbt.getInt("fuel"));
        data.set(MAX_FUEL, nbt.getInt("maxFuel"));

        data.set(RED_MODE, nbt.getInt("redstoneMode"));

        readNBTUpg(nbt);

        for(int i = 0; i < energyAllow.size(); i++)
            energyAllow.set(i, nbt.getInt("direEnergy" + i));
        for(int i = 0; i < itemAllow.size(); i++)
            itemAllow.set(i, nbt.getInt("direItem" + i));
        for(int i = 0; i < fluidAllow.size(); i++)
            fluidAllow.set(i, nbt.getInt("direFluid" + i));

        t.initialFacing = nbt.getInt("face");

    }

    public void wdt(CompoundTag nbt) {

        for(int i = 0; i < t.inventory.getContainerSize(); i++) {
            nbt.put("item" + i, t.inventory.getItem(i).copy().serializeNBT());
        }
        for(int i = 0; i < t.tanks.size(); i++) {
            t.tanks.get(i).writeToNBT(nbt);
        }
        nbt.putBoolean("whenPlaceToWorld", t.init);

        nbt.putInt("progress", data.get(PROGRESS));
        nbt.putInt("seconds", data.get(MAX_PROGRESS));
        nbt.putInt("energy", data.get(ENERGY));
        nbt.putInt("fuel", data.get(FUEL));
        nbt.putInt("maxFuel", data.get(MAX_FUEL));

        nbt.putInt("redstoneMode", data.get(RED_MODE));

        writeNBTUpg(nbt);

        for(int i = 0; i < energyAllow.size(); i++)
            nbt.putInt("direEnergy" + i, energyAllow.get(i));
        for(int i = 0; i < itemAllow.size(); i++)
            nbt.putInt("direItem" + i, itemAllow.get(i));
        for(int i = 0; i < fluidAllow.size(); i++)
            nbt.putInt("direFluid" + i, fluidAllow.get(i));

        nbt.putInt("face", t.initialFacing);

    }

    public void writeNBTUpg(CompoundTag nbt) {

    }

    public void readNBTUpg(CompoundTag nbt) {

    }

}
