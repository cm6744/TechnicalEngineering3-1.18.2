package ten3.init.template;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import ten3.init.FluidInit;
import ten3.init.tab.DefGroup;
import ten3.util.ComponentHelper;
import ten3.util.SafeOperationHelper;

import javax.annotation.Nullable;

import static ten3.init.template.DefItem.build;

public class Bucket extends BucketItem
{

    public Bucket(String id)
    {
        super(() -> FluidInit.getSource(id), build(1, DefGroup.ITEM).craftRemainder(Items.BUCKET));
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        return new FluidBucketWrapper(stack);
    }

    @Override
    public String getDescriptionId()
    {
        return ComponentHelper.getKey(SafeOperationHelper.regNameOf(this));
    }

}
