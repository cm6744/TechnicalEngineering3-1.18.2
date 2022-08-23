package ten3.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class DireUtil {

    public static BlockPos offset(BlockPos p, Direction d) {
        return p.offset(d.getNormal());
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
