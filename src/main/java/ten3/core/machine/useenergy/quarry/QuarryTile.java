package ten3.core.machine.useenergy.quarry;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.state.BlockState;
import ten3.TConst;
import ten3.core.item.upgrades.LevelupIce;
import ten3.core.item.upgrades.LevelupMagma;
import ten3.core.item.upgrades.LevelupMineral;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.extension.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ItemUtil;
import ten3.util.TagUtil;
import ten3.util.WorkUtil;

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

        List<Item> v1 = SlotCm.RECEIVE_ALL_INPUT;

        addSlot(new SlotCustomCm(inventory, 0, 43, 34,
                                 (s) -> s.getItem() instanceof PickaxeItem, false, false));
        addSlot(new SlotCm(inventory, 1, 79, 16, v1, true, false));
        addSlot(new SlotCm(inventory, 2, 97, 16, v1, true, false));
        addSlot(new SlotCm(inventory, 5, 79, 34, v1, true, false));
        addSlot(new SlotCm(inventory, 6, 97, 34, v1, true, false));
        addSlot(new SlotCm(inventory, 9, 79, 52, v1, true, false));
        addSlot(new SlotCm(inventory, 10, 97, 52, v1, true, false));
        addSlot(new SlotCm(inventory, 3, 115, 16, v1, true, false));
        addSlot(new SlotCm(inventory, 4, 133, 16, v1, true, false));
        addSlot(new SlotCm(inventory, 7, 115, 34, v1, true, false));
        addSlot(new SlotCm(inventory, 8, 133, 34, v1, true, false));
        addSlot(new SlotCm(inventory, 11, 115, 52, v1, true, false));
        addSlot(new SlotCm(inventory, 12, 133, 52, v1, true, false));

    }

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
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
                    List<ItemStack> ss = bs.getDrops(WorkUtil.getLoot(level, pos2, inventory.getItem(0)));
                    if(itr.selfGiveList(ss, true)) {
                        itr.selfGiveList(ss, false);
                        level.destroyBlock(pos2, false);
                        ItemUtil.damage(inventory.getItem(0), level, 1);
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
        return TagUtil.containsBlock(s.getBlock(), "ten3:quarry_valids")
                && inventory.getItem(0).isCorrectToolForDrops(s);
        if(mode == 3)
            return TagUtil.containsBlock(s.getBlock(), "forge:ores")
                    && inventory.getItem(0).isCorrectToolForDrops(s);
        return false;
    }

}
