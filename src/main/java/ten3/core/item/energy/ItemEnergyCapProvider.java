package ten3.core.item.energy;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import ten3.lib.capability.energy.BatteryItem;
import ten3.util.ItemUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class ItemEnergyCapProvider implements ICapabilityProvider {

    ItemStack stack;

    public ItemEnergyCapProvider(ItemStack s) {
        stack = s;
    }

    @Nonnull
    @Override
    @SuppressWarnings("all")
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(Objects.equals(cap, CapabilityEnergy.ENERGY)) {
            return (LazyOptional<T>) LazyOptional.of(() -> new BatteryItem(
                    ItemUtil.getTag(stack, "maxEnergy"),
                    ItemUtil.getTag(stack, "receive"),
                    ItemUtil.getTag(stack, "extract"),
                    stack
            ));
        }
        return LazyOptional.empty();
    }

}
