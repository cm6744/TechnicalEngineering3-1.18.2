package ten3.core.client;


import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientHolder {
    public static ArrayCellIntMap<BlockPos> energy = new ArrayCellIntMap<>();
    public static ArrayCellIntMap<BlockPos> item = new ArrayCellIntMap<>();
    public static ArrayCellIntMap<BlockPos> fluid = new ArrayCellIntMap<>();
    public static IntMap<BlockPos> redstone = new IntMap<>();
    public static IntMap<BlockPos> radius = new IntMap<>();
    public static Map<BlockPos, BlockPos> binds = new HashMap<>();
    public static ArrayCellMap<BlockPos, BlockPos> channelInputs = new ArrayCellMap<>();
    public static ArrayCellMap<BlockPos, BlockPos> channelOutputs = new ArrayCellMap<>();
    public static IntMap<BlockPos> channelModeInput = new IntMap<>();
    public static IntMap<BlockPos> channelModeOutput = new IntMap<>();
}
