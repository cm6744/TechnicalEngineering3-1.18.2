package ten3.lib.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.apache.commons.compress.utils.Lists;
import ten3.init.ContInit;
import ten3.lib.capability.item.InventoryCm;
import ten3.lib.wrapper.IntArrayCm;
import ten3.lib.wrapper.SlotCm;

public abstract class CmContainer extends AbstractContainerMenu {

    public final IntArrayCm data;
    public String name;
    public BlockPos pos;
    public InventoryCm tileInv;
    public CmTileEntity tile;

    public CmContainer(MenuType<?> c, int id, String name, CmTileEntity tile, Inventory pi, BlockPos pos, IntArrayCm data) {

        super(c, id);

        this.data = data;
        this.pos = pos;

        layoutInventorySlots(pi, 141, 0);
        layoutInventorySlots(pi, 83, 9);
        layoutInventorySlots(pi, 101, 18);
        layoutInventorySlots(pi, 119, 27);

        //after player, care!
        if(tile != null) {
            this.tile = tile;
            tileInv = tile.inventory;
            for(Slot slot : tile.slots) {
                addSlot(slot);
            }
        }
        else {
            tileInv = ContInit.createDefaultInv(Lists.newArrayList());
        }

        if(data != null) {
            addDataSlots(data);
        }

        this.name = name;

    }

    //from: BrewingStandMenu
    public void layoutInventorySlots(Container ct, int y, int from) {

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(ct, k + from, 7 + 1 + k * 18, y + 1));
        }

    }

    public IntArrayCm getData() {
        return data;
    }

}
