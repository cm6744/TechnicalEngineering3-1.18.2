package ten3.lib.capability.net;

import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class LevelNet<T> {

    public Map<BlockPos, Net<T>> nets = new HashMap<>();

    public Net<T> getNet(BlockPos pos) {
        if(!nets.containsKey(pos)) {
            nets.put(pos, new Net<>());
        }
        return nets.get(pos);
    }

    public Net<T> updateNet(BlockPos pos, Net<T> net) {
        return nets.put(pos, net);
    }

}
