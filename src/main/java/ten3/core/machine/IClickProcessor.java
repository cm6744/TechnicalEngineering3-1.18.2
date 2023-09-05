package ten3.core.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IClickProcessor
{

    boolean click(Player player, ItemStack mainHand, BlockPos pos);

}
