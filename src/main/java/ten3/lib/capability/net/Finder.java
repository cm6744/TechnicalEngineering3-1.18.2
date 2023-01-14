package ten3.lib.capability.net;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;
import ten3.util.DireUtil;
import ten3.util.ExcUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Finder {

    @SuppressWarnings("all")
    public static <T> void find(LevelNetsManager<T> manager,
                                GetHandler<T> getter, IsType isType,
                                GetCap capGetter,
                                BlockEntity start,
                                HashMap<BlockPos, BlockEntity> fouInit,
                                Net<T> init, boolean first) {

        BlockPos pos = start.getBlockPos();

        HashMap<BlockPos, BlockEntity> found = fouInit != null ? fouInit : new HashMap<>();
        Net<T> result = init != null ? init : new Net<>();

        found.put(pos, start);//do not find start! or make a exception
        result.netPoses.add(start);

        for(Direction d : Direction.values()) {
            BlockPos p1 = pos.offset(d.getNormal());
            BlockEntity offset = start.getLevel().getBlockEntity(p1);
            if(offset == null || found.get(p1) != null) continue;
            found.put(p1, offset);
            T e = getter.get(offset, DireUtil.safeOps(d));//e:offset
            T e2 = getter.get(start, d);//e2:start
            if(e == null || e2 == null) continue;//have no cap, next
            if(isType.is(offset)) {
                result.netPoses.add(offset);
                find(manager, getter, isType, capGetter, offset, found, result, false);
            } else {
                result.elements.add(e);
            }
        }

        if(first) {
            manager.getLevelNet(start).updateNet(pos, result);
        }

    }

    public static boolean isNoNeedToPassBy(boolean findGoal, BlockPos goal, BlockEntity start, HashMap<BlockPos, BlockEntity> fouInit,
                                            IsType whenTrueSkip) {

        BlockPos pos = start.getBlockPos();

        HashMap<BlockPos, BlockEntity> found = fouInit != null ? fouInit : new HashMap<>();

        found.put(pos, start);

        for(Direction d : Direction.values()) {
            BlockPos p1 = pos.offset(d.getNormal());
            BlockEntity offset = start.getLevel().getBlockEntity(p1);
            if(offset == null || found.get(p1) != null) continue;
            found.put(p1, offset);
            if(whenTrueSkip.is(offset)) {
                continue;
            }
            else {
                findGoal = isNoNeedToPassBy(findGoal, goal, offset, found, whenTrueSkip);
                if(findGoal) {
                    break;
                }
            }
        }

        if(pos.equals(goal)) {
            findGoal = true;
        }

        return findGoal;

    }

    public static <T> void find(LevelNetsManager<T> manager,
                                GetHandler<T> getter, IsType isType,
                                GetCap capGetter,
                                BlockEntity start
                                ) {

        find(manager, getter, isType, capGetter, start, null, null, true);

    }

    public static boolean isNoNeedToPassBy(BlockPos goal, BlockEntity start,
                                           IsType whenTrueSkip) {

        return isNoNeedToPassBy(false, goal, start, null, whenTrueSkip);

    }

    public interface GetHandler<T> {
        T get(BlockEntity t, Direction d);
    }

    public interface GetCap {
        int get(BlockEntity tile);
    }

    public interface IsType {
        boolean is(BlockEntity tile);
    }

}
