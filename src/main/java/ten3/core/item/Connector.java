package ten3.core.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import ten3.init.tab.DefGroup;
import ten3.init.template.DefItem;
import ten3.util.ItemNBTHelper;

public class Connector extends DefItem implements IModeChangable {

    public Connector() {

        super(build(1, DefGroup.TOOL));

    }

    public void change(Player player)
    {
        if(player != null) {
            if(!player.level.isClientSide()) {
                ItemStack stack = player.getMainHandItem();
                if(!player.isShiftKeyDown()) {
                    int i = ItemNBTHelper.getTag(stack, "mode");
                    i++;
                    if(i >= Modes.size()) {
                        i = 0;
                    }
                    ItemNBTHelper.setTag(stack, "mode", i);
                }
                else {
                    ItemNBTHelper.setTag(stack, "hasLast", 0);
                }
            }
        }
    }

    public enum Modes {
        NONE(-1),
        OUT(0),
        IN(1),
        REMOVE(2);

        int index;
        Modes(int index) {
            this.index = index;
        }

        public static Modes parse(ItemStack s)
        {
            if(!(s.getItem() instanceof Connector)) return NONE;
            switch(ItemNBTHelper.getTag(s, "mode")) {
                case 0:
                    return OUT;
                case 1:
                    return IN;
                case 2:
                    return REMOVE;
            };
            return NONE;
        }

        public int getIndex() {
            return index;
        }

        public static int size() {
            return 3;
        }
    }

}
