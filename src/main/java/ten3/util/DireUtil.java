package ten3.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.Queue;

public class DireUtil {

    public static BlockPos offset(BlockPos p, Direction d) {
        return p.offset(d.getNormal());
    }

    public static Queue<Direction> newQueueOffer() {
        return Queues.newArrayDeque(Lists.newArrayList(Direction.values()));
    }

    public static int direToInt(Direction d) {

        if(d == null) return 0;

        return d.get3DDataValue();

    }

    public static Direction intToDire(int d) {

        return Direction.from3DDataValue(d);

    }

    public static Direction safeOps(Direction d) {

        return d == null ? null : d.getOpposite();

    }

}
