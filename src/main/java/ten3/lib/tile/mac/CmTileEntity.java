package ten3.lib.tile.mac;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import ten3.TConst;
import ten3.core.network.Network;
import ten3.core.network.check.PTCCheckPack;
import ten3.core.network.check.PTSCheckPack;
import ten3.init.BlockInit;
import ten3.init.ContInit;
import ten3.init.TileInit;
import ten3.lib.wrapper.*;
import ten3.util.ExcUtil;
import ten3.util.KeyUtil;

import javax.annotation.Nonnull;

public abstract class CmTileEntity extends BlockEntity implements MenuProvider {

    public static CmTileEntity ofType(BlockEntityType<?> type, BlockPos... pos)
    {
        return (CmTileEntity) type.create(pos.length > 0 ? pos[0] : BlockPos.ZERO,
                BlockInit.getBlock(ExcUtil.regNameOf(type)).defaultBlockState());
    }

    public IntArrayCm data = ContInit.createDefaultIntArr();
    public Component component;
    public String id;

    boolean init;

    public CmTileEntity(String key, BlockPos pos, BlockState state)
    {
        super(TileInit.getType(key), pos, state);
        component = KeyUtil.translated(TConst.modid + "." + key);
        id = key;
    }

    public void rdt(CompoundTag nbt) {}

    public void wdt(CompoundTag nbt) {}

    public void load(CompoundTag nbt)
    {
        rdt(nbt);
        super.load(nbt);
    }

    public void saveAdditional(CompoundTag compound)
    {
        wdt(compound);
        super.saveAdditional(compound);
    }

    boolean loaded;

    @Override
    public void onLoad()
    {
        super.onLoad();
        loaded = true;
    }

    protected int globalTimer = 0;

    public int getTileAliveTime() {
        return globalTimer;
    }

    public void serverTick() {

        if(level == null) return;
        
        if(!level.isClientSide()) {
            globalTimer++;
            if(!init) {
                init = true;
                whenPlaceToWorld();
            }
            update();
            endTick();
        }

        updateRemote();

    }

    public void endTick() {}

    public void whenPlaceToWorld() {}

    public void updateRemote() {}

    public void update() {}

    public Component getDisplayName()
    {
        return component;
    }

}
