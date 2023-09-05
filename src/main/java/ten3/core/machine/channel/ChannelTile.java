package ten3.core.machine.channel;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import ten3.core.item.Connector;
import ten3.core.machine.IClickProcessor;
import ten3.core.network.Network;
import ten3.core.network.packets.PTCInfoChannelPack;
import ten3.init.ItemInit;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.option.Type;
import ten3.util.DisplayHelper;
import ten3.util.ItemNBTHelper;
import ten3.util.ComponentHelper;

import java.util.ArrayList;
import java.util.List;

public class ChannelTile extends CmTileMachine implements IClickProcessor
{
    public List<BlockPos> outputs = new ArrayList<>();
    public List<BlockPos> inputs = new ArrayList<>();
    public int oMode;
    public int iMode;
    protected int nowOutputIndex, nowInputIndex;

    public void readTileData(CompoundTag nbt)
    {
        super.readTileData(nbt);
        iMode = nbt.getInt("imode");
        oMode = nbt.getInt("omode");
        int is = nbt.getInt("isiz");
        int os = nbt.getInt("osiz");
        for(int i = 0; i < is; i++)
            inputs.add(BlockPos.of(nbt.getLong("ipos" + i)));
        for(int i = 0; i < os; i++)
            outputs.add(BlockPos.of(nbt.getLong("opos" + i)));
    }

    public void writeTileData(CompoundTag nbt)
    {
        super.writeTileData(nbt);
        nbt.putInt("imode", iMode);
        nbt.putInt("omode", oMode);
        nbt.putInt("isiz", inputs.size());
        nbt.putInt("osiz", outputs.size());
        for(int i = 0; i < inputs.size(); i++)
            nbt.putLong("ipos" + i, inputs.get(i).asLong());
        for(int i = 0; i < outputs.size(); i++)
            nbt.putLong("opos" + i, outputs.get(i).asLong());
    }

    public void linkOut(BlockPos p)
    {
        outputs.add(p);
        nowOutputIndex = 0;
    }

    public void linkIn(BlockPos p)
    {
        inputs.add(p);
        nowInputIndex = 0;
    }

    public void spiltOut(BlockPos p)
    {
        outputs.remove(p);
        nowOutputIndex = 0;
    }

    public void spiltIn(BlockPos p)
    {
        inputs.remove(p);
        nowInputIndex = 0;
    }

    public ChannelTile(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    public boolean click(Player player, ItemStack mainHand, BlockPos pos)
    {
        if(mainHand.is(ItemInit.getItem("channel_connector"))) {
            long lastClick = (long) ItemNBTHelper.getTagD(mainHand, "last");
            int hasLast = ItemNBTHelper.getTag(mainHand, "hasLast");
            if(hasLast == 1) {
                BlockPos last = BlockPos.of(lastClick);
                BlockEntity be = level.getBlockEntity(last);
                if(!(be instanceof ChannelTile)) {
                    player.sendMessage(
                            ComponentHelper.translated("ten3.channel.not_found")
                                    .append(DisplayHelper.toString(pos))
                                    .withStyle(ChatFormatting.RED)
                            , player.getUUID());
                    return false;
                }
                Connector.Modes mode = Connector.Modes.parse(mainHand);
                Component linkMes = ComponentHelper.translated("ten3.channel.bind")
                        .append(DisplayHelper.toString(last))
                        .append(ComponentHelper.translated("ten3.channel.to"))
                        .append(DisplayHelper.toString(pos))
                        .withStyle(ChatFormatting.GREEN);
                Component remMes = ComponentHelper.translated("ten3.channel.remove")
                        .append(DisplayHelper.toString(last))
                        .append(ComponentHelper.translated("ten3.channel.from"))
                        .append(DisplayHelper.toString(pos))
                        .withStyle(ChatFormatting.GREEN);
                if(mode == Connector.Modes.OUT) {
                    ((ChannelTile) be).linkOut(pos);
                    player.sendMessage(linkMes, player.getUUID());
                }
                if(mode == Connector.Modes.IN) {
                    ((ChannelTile) be).linkIn(pos);
                    player.sendMessage(linkMes, player.getUUID());
                }
                if(mode == Connector.Modes.REMOVE) {
                    ChannelTile c = ((ChannelTile) be);
                    if(c.outputs.contains(pos)) {
                        c.spiltOut(pos);
                    }
                    else if(c.inputs.contains(pos)) {
                        c.spiltIn(pos);
                    }
                    player.sendMessage(remMes, player.getUUID());
                }
                ItemNBTHelper.setTag(mainHand, "hasLast", 0);
                return false;
            }
            else {
                ItemNBTHelper.setTagD(mainHand, "last", pos.asLong());
                ItemNBTHelper.setTag(mainHand, "hasLast", 1);
                player.sendMessage(
                        ComponentHelper.translated("ten3.channel.first_click")
                                .append(DisplayHelper.toString(pos))
                                .withStyle(ChatFormatting.GOLD)
                        , player.getUUID());
            }

            return false;
        }
        Network.sendToClient(new PTCInfoChannelPack(this));
        return true;
    }

    public int inventorySize()
    {
        return 0;
    }

    public boolean hasUpgrade()
    {
        return true;
    }

    public boolean hasSideBar()
    {
        return true;
    }

    @Override
    public Type typeOf()
    {
        return Type.NON_MAC;
    }

    public IngredientType slotType(int slot)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return false;
    }

    public IngredientType tankType(int tank)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return false;
    }

    public int getCapacity()
    {
        return 0;
    }

    @Override
    public void update() {
    }

    public void cycle()
    {
        nowOutputIndex++;
        if(nowOutputIndex >= outputs.size()) {
            nowOutputIndex = 0;
        }
        nowInputIndex++;
        if(nowInputIndex >= inputs.size()) {
            nowInputIndex = 0;
        }
    }

    @Override
    public void initHandlers()
    {
    }

    @Override
    protected boolean hasFaceCapability(Capability<?> cap, Direction d)
    {
        return false;
    }

}
