package ten3.core.machine.useenergy.encflu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.state.BlockState;
import ten3.lib.tile.extension.CmTileMachineProcess;
import ten3.lib.wrapper.SlotChangableCm;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;

import java.util.Map;

public class EncfluTile extends CmTileMachineProcess
{

    public boolean customFitStackIn(ItemStack s, int slot)
    {
        if(slot == 1) {
            return s.isEnchantable() || s.is(Items.BOOK);
        }
        else if(slot == 0) {
            return s.isEnchanted();
        }
        return false;
    }

    public EncfluTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(20));
        setEfficiency(100);

        addSlot(new SlotChangableCm(inventory, 0, 43, 15, ItemStack::isEnchanted, (s) -> {
            return !s.isEnchanted();
        }, (s) -> true));
        addSlot(new SlotCustomCm(inventory, 1, 43, 51, (s) -> {
            return s.isEnchantable() || s.is(Items.BOOK);
        }, false, true));
        addSlot(new SlotCm(inventory, 2, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    public boolean conditionStart()
    {
        ItemStack tool = inventory.getItem(0);
        ItemStack expectedBook = inventory.getItem(1);
        return tool.isEnchanted() && (expectedBook.isEnchantable() || expectedBook.is(Items.BOOK))
                && inventory.getItem(2).isEmpty();
    }

    public void cookEnd()
    {
        ItemStack tool = inventory.getItem(0);
        ItemStack expectedBook = inventory.getItem(1);
        if(expectedBook.is(Items.BOOK)) {
            ItemStack eb;
            inventory.setItem(2, eb = Items.ENCHANTED_BOOK.getDefaultInstance());
            for(Map.Entry<Enchantment, Integer> e : EnchantmentHelper.getEnchantments(tool).entrySet()) {
                EnchantedBookItem.addEnchantment(eb, new EnchantmentInstance(e.getKey(), e.getValue()));
            }
        }
        else {
            inventory.setItem(2, expectedBook.copy());
            for(Map.Entry<Enchantment, Integer> e : EnchantmentHelper.getEnchantments(tool).entrySet()) {
                inventory.getItem(2).enchant(e.getKey(), e.getValue());
            }
            expectedBook.shrink(1);
        }
        expectedBook.shrink(1);
        tool.removeTagKey("Enchantments");
        tool.removeTagKey("StoredEnchantments");
    }

    @Override
    public int ticks()
    {
        return 800;
    }

}
