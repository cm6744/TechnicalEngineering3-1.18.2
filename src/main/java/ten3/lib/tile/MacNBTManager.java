package ten3.lib.tile;

import net.minecraft.nbt.CompoundTag;
import ten3.lib.wrapper.IntArrayCm;

import static ten3.lib.tile.CmTileMachine.*;

public class MacNBTManager {

    CmTileMachine t;
    IntArrayCm data;
    IntArrayCm energyAllow;
    IntArrayCm itemAllow;

    public MacNBTManager(CmTileMachine tile) {
        t = tile;
        data = t.data;
        energyAllow = t.energyAllow;
        itemAllow = t.itemAllow;
    }

    public void rdt(CompoundTag nbt) {

        data.set(PROGRESS, nbt.getInt("progress"));
        data.set(MAX_PROGRESS, nbt.getInt("maxProgress"));
        data.set(ENERGY, nbt.getInt("energy"));
        data.set(FUEL, nbt.getInt("fuel"));
        data.set(MAX_FUEL, nbt.getInt("maxFuel"));

        data.set(RED_MODE, nbt.getInt("redstoneMode"));

        readNBTUpg(nbt);

        for(int i = 0; i < energyAllow.size(); i++)
            energyAllow.set(i, nbt.getInt("direEnergy" + i));
        for(int i = 0; i < itemAllow.size(); i++)
            itemAllow.set(i, nbt.getInt("direItem" + i));

        t.initialFacing = nbt.getInt("face");

    }

    public void wdt(CompoundTag nbt) {

        nbt.putInt("progress", data.get(PROGRESS));
        nbt.putInt("maxProgress", data.get(MAX_PROGRESS));
        nbt.putInt("energy", data.get(ENERGY));
        nbt.putInt("fuel", data.get(FUEL));
        nbt.putInt("maxFuel", data.get(MAX_FUEL));

        nbt.putInt("redstoneMode", data.get(RED_MODE));

        writeNBTUpg(nbt);

        for(int i = 0; i < energyAllow.size(); i++)
            nbt.putInt("direEnergy" + i, energyAllow.get(i));
        for(int i = 0; i < itemAllow.size(); i++)
            nbt.putInt("direItem" + i, itemAllow.get(i));

        nbt.putInt("face", t.initialFacing);

    }

    public void writeNBTUpg(CompoundTag nbt) {

        nbt.putInt("level", t.levelIn);

    }

    public void readNBTUpg(CompoundTag nbt) {

        t.levelIn = nbt.getInt("level");

    }

}
