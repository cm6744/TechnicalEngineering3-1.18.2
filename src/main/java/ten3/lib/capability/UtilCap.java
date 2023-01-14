package ten3.lib.capability;

import net.minecraft.core.Direction;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.FaceOption;

public class UtilCap {

    public static boolean canIn(Direction di, CmTileMachine tile) {
        return (di == null
                || tile.info.direCheckEnergy(di) == FaceOption.IN
                || tile.info.direCheckEnergy(di) == FaceOption.BE_IN
                || tile.info.direCheckEnergy(di) == FaceOption.BOTH
        )
                && tile.signalAllowRun();
    }

    public static boolean canInITM(Direction di, CmTileMachine tile) {
        return (di == null
                || tile.info.direCheckItem(di) == FaceOption.IN
                || tile.info.direCheckItem(di) == FaceOption.BE_IN
                || tile.info.direCheckItem(di) == FaceOption.BOTH
        )
                && tile.signalAllowRun();
    }

    public static boolean canInFLU(Direction di, CmTileMachine tile) {
        return (di == null
                || tile.info.direCheckFluid(di) == FaceOption.IN
                || tile.info.direCheckFluid(di) == FaceOption.BE_IN
                || tile.info.direCheckFluid(di) == FaceOption.BOTH
        )
                && tile.signalAllowRun();
    }

    public static boolean canOut(Direction di, CmTileMachine tile) {
        return (di == null
                || tile.info.direCheckEnergy(di) == FaceOption.OUT
                || tile.info.direCheckEnergy(di) == FaceOption.BE_OUT
                || tile.info.direCheckEnergy(di) == FaceOption.BOTH
        )
                && tile.signalAllowRun();
    }

    public static boolean canOutITM(Direction di, CmTileMachine tile) {
        return (di == null
                || tile.info.direCheckItem(di) == FaceOption.OUT
                || tile.info.direCheckItem(di) == FaceOption.BE_OUT
                || tile.info.direCheckItem(di) == FaceOption.BOTH
        )
                && tile.signalAllowRun();
    }

    public static boolean canOutFLU(Direction di, CmTileMachine tile) {
        return (di == null
                || tile.info.direCheckFluid(di) == FaceOption.OUT
                || tile.info.direCheckFluid(di) == FaceOption.BE_OUT
                || tile.info.direCheckFluid(di) == FaceOption.BOTH
        )
                && tile.signalAllowRun();
    }

}
