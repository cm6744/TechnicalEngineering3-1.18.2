package ten3.core.machine.useenergy.quarry;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import ten3.TConst;
import ten3.core.item.upgrades.LevelupIce;
import ten3.core.item.upgrades.LevelupMagma;
import ten3.core.item.upgrades.LevelupMineral;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.extension.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCm;
import ten3.util.ItemNBTHelper;
import ten3.util.TagHelper;
import ten3.util.WorkingHelper;

import java.util.List;

public class QuarryTile extends CmTileMachineRadiused {

    int mode;

    public void checkMode()
    {
        if(upgradeSlots.countUpgrade(LevelupIce.class) > 0) {
            mode = 1;
            return;
        }
        if(upgradeSlots.countUpgrade(LevelupMagma.class) > 0) {
            mode = 2;
            return;
        }
        if(upgradeSlots.countUpgrade(LevelupMineral.class) > 0) {
            mode = 3;
            return;
        }
        mode = 0;
    }

    public QuarryTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(20));
        setEfficiency(10);
        initialRadius = 3;

        addSlot(new SlotCm(this, 0, 43, 34));
        addSlot(new SlotCm(this, 1, 79, 16));
        addSlot(new SlotCm(this, 2, 97, 16));
        addSlot(new SlotCm(this, 3, 115, 16));
        addSlot(new SlotCm(this, 4, 133, 16));
        addSlot(new SlotCm(this, 5, 79, 34));
        addSlot(new SlotCm(this, 6, 97, 34));
        addSlot(new SlotCm(this, 7, 115, 34));
        addSlot(new SlotCm(this, 8, 133, 34));
        addSlot(new SlotCm(this, 9, 79, 52));
        addSlot(new SlotCm(this, 10, 97, 52));
        addSlot(new SlotCm(this, 11, 115, 52));
        addSlot(new SlotCm(this, 12, 133, 52));

    }

    public int inventorySize()
    {
        return 13;
    }

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    public IngredientType slotType(int slot)
    {
        if(slot != 0) {
            return IngredientType.OUTPUT;
        }
        return IngredientType.INPUT;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        if(slot == 0) {
            return stack.getItem() instanceof TieredItem;
        }
        return true;
    }

    public IngredientType tankType(int tank)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return true;
    }

    public void update() {
        super.update();
        if(globalTimer % 10 == 0) checkMode();
    }

    public double seconds()
    {
        switch(mode) {
            case 0:
                return 3;
            case 1:
                return 15;
            case 2:
                return 30;
            case 3:
                return 0.4;
        }
        return Integer.MAX_VALUE;
    }

    public void effect()
    {
        switch(mode) {
            case 0:
            case 3:
                BlockPos pos2 = worldPosition.offset(
                        Math.random() * radius - (radius - 1) / 2D,
                        0,
                        Math.random() * radius - (radius - 1) / 2D
                );
                pos2 = pos2.atY(Mth.randomBetweenInclusive(level.getRandom(), TConst.WORLD_MIN, worldPosition.getY() - 1));
                BlockState bs = level.getBlockState(pos2);
                if(canBreak(bs)) {
                    List<ItemStack> ss = bs.getDrops(WorkingHelper.getLoot(level, pos2, inventory.getItem(0)));
                    if(itr.selfGiveList(ss, true)) {
                        itr.selfGiveList(ss, false);
                        level.destroyBlock(pos2, false);
                        ItemNBTHelper.damage(inventory.getItem(0), level, 1);
                    }
                }
                break;
            case 1:
                ItemStack s;
                if(Math.random() < 0.75) {
                    s = Items.ICE.getDefaultInstance();
                }
                else if(Math.random() < 0.75) {
                    s = Items.PACKED_ICE.getDefaultInstance();
                }
                else {
                    s = Items.BLUE_ICE.getDefaultInstance();
                }
                if(itr.selfGive(s, true)) {
                    itr.selfGive(s, false);
                }
                break;
            case 2:
                if(Math.random() < 0.75) {
                    s = Items.MAGMA_BLOCK.getDefaultInstance();
                }
                else {
                    s = Items.MAGMA_CREAM.getDefaultInstance();
                }
                if(itr.selfGive(s, true)) {
                    itr.selfGive(s, false);
                }
                break;
        }
    }

    private boolean canBreak(BlockState s)
    {
        if(mode == 0)
        return TagHelper.containsBlock(s.getBlock(), TagHelper.keyBlock("ten3:quarry_valids"))
                && inventory.getItem(0).isCorrectToolForDrops(s);
        if(mode == 3)
            return TagHelper.containsBlock(s.getBlock(), TagHelper.keyBlock("forge:ores"))
                    && inventory.getItem(0).isCorrectToolForDrops(s);
        return false;
    }

}
