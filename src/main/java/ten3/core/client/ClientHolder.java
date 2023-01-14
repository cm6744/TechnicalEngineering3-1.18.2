package ten3.core.client;


import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientHolder {
    public static Map<BlockPos, ArrayList<Integer>> energy = new HashMap<>();
    public static Map<BlockPos, ArrayList<Integer>> item = new HashMap<>();
    public static Map<BlockPos, ArrayList<Integer>> fluid = new HashMap<>();
    public static Map<BlockPos, Integer> redstone = new HashMap<>();
    public static Map<BlockPos, Integer> radius = new HashMap<>();
    public static Map<BlockPos, BlockPos> binds = new HashMap<>();
}
