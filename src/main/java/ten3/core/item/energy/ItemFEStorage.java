package ten3.core.item.energy;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import ten3.init.tab.DefGroup;
import ten3.init.template.DefItem;
import ten3.util.ItemNBTHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFEStorage extends DefItem {

    int sto, rec, ext;

    public ItemFEStorage(int s, int r, int e) {
        super(build(1, DefGroup.TOOL));
        sto = s; rec = r; ext = e;
    }

    @Override
    public ItemStack getDefaultInstance() {
        return EnergyItemHelper.getState(this, sto, rec, ext);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemEnergyCapProvider(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack)
    {
        return ItemNBTHelper.getTag(stack, "energy") != 0;
    }

    @Override
    public int getBarWidth(ItemStack stack)
    {
        if(ItemNBTHelper.getTag(stack, "maxEnergy") == 0) return 0;
        return (int)(13 * (ItemNBTHelper.getTag(stack, "energy") / (double) sto));
    }

    @Override
    public int getBarColor(ItemStack p_150901_)
    {
        return Mth.color(1f, 0.1f, 0.1f);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items)
    {
        if(allowdedIn(tab)) {
            EnergyItemHelper.fillEmpty(this, items, sto, rec, ext);
            EnergyItemHelper.fillFull(this, items, sto, rec, ext);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level p_40573_, List<Component> tooltip, TooltipFlag p_40575_)
    {
        EnergyItemHelper.addTooltip(tooltip, stack);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level p_41448_, Player p_41449_)
    {
        EnergyItemHelper.setState(stack, sto, rec, ext);
    }

}
