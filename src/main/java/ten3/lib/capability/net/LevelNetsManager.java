package ten3.lib.capability.net;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelNetsManager<T> {

    public static List<LevelNetsManager<?>> alManagers = new ArrayList<>();

    public LevelNetsManager() {
        alManagers.add(this);
    }

    public Map<Level, LevelNet<T>> nets = new HashMap<>();

    public LevelNet<T> getLevelNet(BlockEntity be) {
        return getLevelNet(be.getLevel());
    }

    public LevelNet<T> getLevelNet(Level level) {
        if(!nets.containsKey(level)) {
            nets.put(level, new LevelNet<>());
        }
        return nets.get(level);
    }

}
