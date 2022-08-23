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
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import ten3.core.machine.Cell;
import ten3.init.template.DefItemBlock;
import ten3.init.TileInit;
import ten3.lib.tile.CmTileEntity;
import ten3.lib.tile.CmTileMachine;
import ten3.util.ExcUtil;
import ten3.util.ItemUtil;

import javax.annotation.Nullable;
import java.util.List;

public class BlockItemFEStorage extends DefItemBlock {

    //in game item do

    private CmTileMachine getBind() {
        return (CmTileMachine) CmTileEntity.ofType(TileInit.getType(ExcUtil.regNameOf(this)));
    }

    public BlockItemFEStorage(Block b) {
        super(b, 1);
    }

    @Override
    public Component getName(ItemStack stack)
    {
        int level = ItemUtil.getTag(stack, "level");
        CmTileMachine t = getBind();
        t.levelIn = level;
        return t.getDisplayWith();
    }

    //in init item do(Tab or crafting...)

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemEnergyCapProvider(stack);
    }

    @Override
    public ItemStack getDefaultInstance() {
        CmTileMachine t = getBind();
        return EnergyItemHelper.getState(this, t.maxStorage, t.maxReceive, t.maxExtract);
    }

    @Override
    public boolean isBarVisible(ItemStack stack)
    {
        return ItemUtil.getTag(stack, "energy") != 0;
    }

    @Override
    public int getBarWidth(ItemStack stack)
    {
        if(ItemUtil.getTag(stack, "maxEnergy") == 0) return 0;
        return (int)(13 * (ItemUtil.getTag(stack, "energy") / (double) ItemUtil.getTag(stack, "maxEnergy")));
    }

    @Override
    public int getBarColor(ItemStack p_150901_)
    {
        return Mth.color(1f, 0.1f, 0.1f);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks)
    {
        if(allowdedIn(tab)) {
            CmTileMachine t = getBind();
            EnergyItemHelper.fillEmpty(this, stacks, t.maxStorage, t.maxReceive, t.maxExtract);

            if(getBlock() instanceof Cell) {
                EnergyItemHelper.fillFull(this, stacks, t.maxStorage, t.maxReceive, t.maxExtract);
            }
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
        CmTileMachine t = getBind();
        EnergyItemHelper.setState(stack, t.maxStorage, t.maxReceive, t.maxExtract);
    }

}
