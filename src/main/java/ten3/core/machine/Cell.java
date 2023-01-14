package ten3.core.machine;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class Cell extends MatedMac {

    public Cell(String name) {

        this(false, name);

    }

    public Cell(boolean solid, String name) {

        this(Material.METAL, SoundType.METAL, name, solid);

    }

    public Cell(Material m, SoundType s, String name, boolean solid) {

        super(m, s, name, solid);

    }

}
