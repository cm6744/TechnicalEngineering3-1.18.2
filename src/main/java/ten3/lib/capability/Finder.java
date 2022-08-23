package ten3.lib.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import ten3.core.machine.cable.CableTile;
import ten3.util.DireUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Finder {

    @SuppressWarnings("all")
    public static <T> void find(Map<BlockPos, List<T>> nets, Map<BlockPos, Integer> caps,
                                GetHandler<T> getter, GetCap capper, IsType isType,
                                BlockEntity start,
                                List<T> init, HashMap<BlockPos, BlockEntity> fouInit,
                                Direction fr, int cap, boolean callFirst) {

        BlockPos pos = start.getBlockPos();

        List<T> result = init != null ? init : new ArrayList();
        HashMap<BlockPos, BlockEntity> found = fouInit != null ? fouInit : new HashMap<>();

        found.put(pos, start);

        for(Direction d : Direction.values()) {
            if(fr == d) continue;//if is the calling from dire, not run
            BlockPos p1 = pos.offset(d.getNormal());
            BlockEntity t = start.getLevel().getBlockEntity(p1);;
            if(t == null || found.get(p1) != null) continue;
            found.put(p1, t);
            T e = getter.get(t, DireUtil.safeOps(d));
            T e2 = getter.get(start, d);
            if(e == null || e2 == null) continue;//have no cap, next
            if(isType.is(t)) {
                cap = Math.min(capper.get(t), capper.get(start));
                find(nets, caps, getter, capper, isType, t, result, found, DireUtil.safeOps(d), cap, false);
            } else {
                result.add(e);
            }
        }

        if(callFirst) {
            nets.put(start.getBlockPos(), result);
            caps.put(start.getBlockPos(), cap);
        }

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
