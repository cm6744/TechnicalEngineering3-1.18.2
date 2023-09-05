package ten3.lib.capability.energy;

import net.minecraft.world.item.ItemStack;
import ten3.util.ItemNBTHelper;

public class BatteryItem extends Battery
{

    ItemStack stackIn;

    public BatteryItem(int capacity, int maxReceive, int maxExtract, ItemStack stack) {
        super(capacity, maxReceive, maxExtract);
        stackIn = stack;
    }

    @Override
    public int getEnergyStored() {
        return ItemNBTHelper.getTag(stackIn, "energy");
    }

    public void translateEnergy(int diff) {
        ItemNBTHelper.tranTag(stackIn, "energy", diff);
    }

    public void setEnergy(int diff) {
        ItemNBTHelper.setTag(stackIn, "energy", diff);
    }

}
