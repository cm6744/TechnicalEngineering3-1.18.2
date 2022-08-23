package ten3.lib.tile;

import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.IntArrayCm;

import static ten3.lib.tile.CmTileMachine.*;

public class EfficientCalculator {

    public static double gen(Type type, IntArrayCm data) {

        if(type == Type.GENERATOR) {
            if(data.get(MAX_FUEL) == 0 || data.get(FUEL) == 0) {
                data.set(EFF_AUC, 0);
            } else {
                return  (data.get(FUEL) / (double) data.get(MAX_FUEL) * 0.9 + 0.1);
            }
        } else if(type == Type.MACHINE_PROCESS || type == Type.MACHINE_EFFECT || type == Type.CELL) {
            if(data.get(MAX_ENERGY) == 0 || data.get(ENERGY) == 0) {
                data.set(EFF_AUC, 0);
            } else {
                return  (data.get(ENERGY) / (double) data.get(MAX_ENERGY) * 0.9 + 0.1);
            }
        }

        return 0;

    }

}
