package ten3.util;

import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class WorkUtil {

    public static void runIn(int radius, BlockPos center, RunWithPos r) {

        int x = center.getX();
        int y = center.getY();
        int z = center.getZ();

        int rr = radius % 2 == 0 ? radius - 1 : radius;

        for(int i = -rr; i < radius; i++) {
            for(int j = -rr; j < radius; j++) {
                for(int k = -rr; k < radius; k++) {
                    BlockPos pin = new BlockPos(x+i, y+j, z+k);
                    if(center.closerThan(pin, radius)) {
                        if(r.run(pin)) {
                            return;
                        }
                    }
                }
            }
        }

    }

    public static void runInFlat(int radius, BlockPos center, RunWithPos r) {

        int x = center.getX();
        int y = center.getY();
        int z = center.getZ();

        int rr = radius % 2 == 0 ? radius - 1 : radius;

        for(int i = -rr; i < radius; i++) {
            for(int k = -rr; k < radius; k++) {
                BlockPos pin = new BlockPos(x+i, y, z+k);
                if(center.closerThan(pin, radius)) {
                    if(r.run(pin)) {
                        return;
                    }
                }
            }
        }

    }

    public static LootContext.Builder getLoot(Level world, BlockPos pos) {
        return new LootContext.Builder((ServerLevel) world)
                .withRandom(world.getRandom())
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY);
    }

    public interface RunWithPos {
        //is stopping
        boolean run(BlockPos pos);
    }

}
