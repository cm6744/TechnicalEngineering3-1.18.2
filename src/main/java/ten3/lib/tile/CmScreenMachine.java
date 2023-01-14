package ten3.lib.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.compress.utils.Lists;
import ten3.core.client.ClientHolder;
import ten3.core.network.packets.PTSModeTransfPack;
import ten3.core.network.packets.PTSRedStatePack;
import ten3.core.network.Network;
import ten3.lib.client.element.*;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.RedstoneMode;
import ten3.util.DireUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ten3.lib.tile.mac.CmTileMachine.*;

public class CmScreenMachine extends CmScreen<CmContainerMachine> {

    public static Map<BlockPos, List<String>> upgrades = new HashMap<>();

    public CmScreenMachine(CmContainerMachine container, Inventory inv, Component titleIn, String path, int textureW, int textureH) {

        super(container, inv, titleIn, path, textureW, textureH);

    }

    protected ElementBarEnergy bar_energy;
    protected ElementBarIdeas bar_ideas;
    protected ElementBarControl bar_control;

    protected ElementButton rs_button_low;
    protected ElementButton rs_button_high;
    protected ElementButton rs_button_off;

    protected ElementButton upglock_1;
    protected ElementButton upglock_2;
    protected ElementButton upglock_3;
    protected ElementButton upglock_4;
    protected ElementButton upglock_5;
    protected ElementButton upglock_6;

    public ElementBurnLeft getDefaultEne() {
        return new ElementBurnLeft(9, 18, 14, 46, 0, 0, handler, true);
    }

    @Override
    public void addWidgets() {

        int w = 26;
        int w2 = 60;

        //bars
        //widgets.add(bar_redstone = new ElementBar(0, 8, 97, 21, handler));
        //widgets.add(bar_ideas = new ElementBarIdeas(0, 30, 97, 0, handler, container.name));
        //widgets.add(bar_energy = new ElementBarEnergy(0, 52, 97, 42, handler));
        widgets.add(bar_ideas = new ElementBarIdeas(-w-1, 0, w, w, 159, 211, handler, container.name));
        widgets.add(bar_energy = new ElementBarEnergy(-w-1, w+1, w, w, 132, 211, handler));
        widgets.add(bar_control = new ElementBarControl(-w-1, (w+1) * 3, w, w, 152, 40, handler));

        //bar_redstone.setTxt("ten3.info.bar_redstone");
        //redstone bar
        widgets.add(rs_button_high = new ElementButton(-w-1, (w+1)*2, w, w, 186, 211, handler,
                this::cycleModeRed).withNoChange());
        widgets.add(rs_button_low = new ElementButton(-w-1, (w+1)*2, w, w, 186, 184, handler,
                this::cycleModeRed).withNoChange());
        widgets.add(rs_button_off = new ElementButton(-w-1, (w+1)*2, w, w, 186, 157, handler,
                this::cycleModeRed).withNoChange());
        rs_button_low.setTxt("ten3.info.bar_redstone", "ten3.info.low");
        rs_button_high.setTxt("ten3.info.bar_redstone", "ten3.info.high");
        rs_button_off.setTxt("ten3.info.bar_redstone", "ten3.info.off");

        //upgrades

        //widgets.add(new ElementBarUpgrades(0, 74, 78, 206, 0, handler));

        //used to display effect when mouse on!

        //x/y = slot.x/y-1(slot has already -1 in #SlotCm!)
        widgets.add(new ElementImage(23, -37, 131, 36, 0, 211, handler));

        widgets.add(upglock_1 = new ElementButtonSlot(32, -28, 18, 18, 227, 0, handler, ()->{}));
        widgets.add(upglock_2 = new ElementButtonSlot(51, -28, 18, 18, 227, 0, handler, ()->{}));
        widgets.add(upglock_3 = new ElementButtonSlot(70, -28, 18, 18, 227, 0, handler, ()->{}));
        widgets.add(upglock_4 = new ElementButtonSlot(89, -28, 18, 18, 227, 0, handler, ()->{}));
        widgets.add(upglock_5 = new ElementButtonSlot(108, -28, 18, 18, 227, 0, handler, ()->{}));
        widgets.add(upglock_6 = new ElementButtonSlot(127, -28, 18, 18, 227, 0, handler, ()->{}));

        //control panel buttons
        int baseX = -w2-1;
        int baseY = (w+1)*3;
        widgets.add(energyCont = new ElementButton(baseX+7, baseY+64, 14, 14, 91, 126, handler, () -> {
            modeNow = 0;
        }));
        widgets.add(itemCont = new ElementButton(baseX+23, baseY+64, 14, 14, 106, 126, handler, () -> {
            modeNow = 1;
        }));
        widgets.add(fluidCont = new ElementButton(baseX+39, baseY+64, 14, 14, 76, 126, handler, () -> {
            modeNow = 2;
        }));
        widgets.add(front = new ElementButtonTransf(baseX+22, baseY+22, 12, 12, 121, 126, handler, () -> {
            cycleModeTs((d) -> d);
        }));
        widgets.add(back = new ElementButtonTransf(baseX+36, baseY+36, 12, 12, 121, 126, handler, () -> {
            cycleModeTs(Direction::getOpposite);
        }));
        widgets.add(left = new ElementButtonTransf(baseX+8, baseY+22, 12, 12, 121, 126, handler, () -> {
            cycleModeTs(Direction::getClockWise);
        }));
        widgets.add(right = new ElementButtonTransf(baseX+36, baseY+22, 12, 12, 121, 126, handler, () -> {
            cycleModeTs(Direction::getCounterClockWise);
        }));
        widgets.add(up = new ElementButtonTransf(baseX+22, baseY+8, 12, 12, 121, 126, handler, () -> {
            cycleModeTs((d) -> Direction.UP);
        }));
        widgets.add(down = new ElementButtonTransf(baseX+22, baseY+36, 12, 12, 121, 126, handler, () -> {
            cycleModeTs((d) -> Direction.DOWN);
        }));
        front.setTxt("dire.front");
        down.setTxt("dire.down");
        up.setTxt("dire.up");
        left.setTxt("dire.left");
        right.setTxt("dire.right");
        back.setTxt("dire.back");
    }

    interface Rotation {
        Direction rot(Direction of);
    }

    private void cycleModeTs(Rotation d) {
        Network.sendToServer(new PTSModeTransfPack(container.pos, modeNow, d.rot(DireUtil.intToDire(container.data.get(FACE)))));
    }

    ElementButton energyCont;
    ElementButton fluidCont;
    int modeNow;
    //0-energy, 1-item
    ElementButton itemCont;
    ElementButtonTransf front;
    ElementButtonTransf back;
    ElementButtonTransf left;
    ElementButtonTransf right;
    ElementButtonTransf up;
    ElementButtonTransf down;

    private void cycleModeRed() {

        int m = container.data.get(RED_MODE);

        m++;
        if(m > RedstoneMode.HIGH) {
            m = RedstoneMode.OFF;
        }

        sendRedPack(m);
        ClientHolder.redstone.put(container.pos, m);

    }

    @Override
    public void containerTick() {

        super.containerTick();

        rs_button_high.setVisible(container.data.get(RED_MODE) == RedstoneMode.HIGH);
        rs_button_low.setVisible(container.data.get(RED_MODE) == RedstoneMode.LOW);
        rs_button_off.setVisible(container.data.get(RED_MODE) == RedstoneMode.OFF);

        //rs_button_off.setVisible(bar_redstone.isOpen());
        //rs_button_low.setVisible(bar_redstone.isOpen());
        //rs_button_high.setVisible(bar_redstone.isOpen());

        bar_energy.update(
                container.data.get(EFF_AUC),
                container.data.get(EFF),
                container.data.get(E_REC),
                container.data.get(E_EXT),
                container.data.get(I_REC),
                container.data.get(I_EXT)
        );

        upglock_1.state = container.data.get(UPGSIZE) >= 1;
        upglock_2.state = container.data.get(UPGSIZE) >= 2;
        upglock_3.state = container.data.get(UPGSIZE) >= 3;
        upglock_4.state = container.data.get(UPGSIZE) >= 4;
        upglock_5.state = container.data.get(UPGSIZE) >= 5;
        upglock_6.state = container.data.get(UPGSIZE) >= 6;

        energyCont.state = modeNow == 0;
        itemCont.state = modeNow == 1;
        fluidCont.state = modeNow == 2;

        front.mode = getModeClient((d) -> d);
        back.mode = getModeClient(Direction::getOpposite);
        left.mode = getModeClient(Direction::getClockWise);
        right.mode = getModeClient(Direction::getCounterClockWise);
        up.mode = getModeClient((d) -> Direction.UP);
        down.mode = getModeClient((d) -> Direction.DOWN);

        front.setVisible(bar_control.show);
        back.setVisible(bar_control.show);
        left.setVisible(bar_control.show);
        right.setVisible(bar_control.show);
        up.setVisible(bar_control.show);
        down.setVisible(bar_control.show);
        energyCont.setVisible(bar_control.show);
        itemCont.setVisible(bar_control.show);
        fluidCont.setVisible(bar_control.show);

        update();

    }

    private int getModeClient(Rotation d)
    {
        ArrayList<Integer> lst = getMapClient().getOrDefault(container.pos, Lists.newArrayList());
        Direction direction = DireUtil.intToDire(container.data.get(FACE));
        if(direction == Direction.DOWN || direction == Direction.UP) {
            return 0;
        }
        int ca = DireUtil.direToInt(d.rot(direction));
        if(lst.size() <= ca) {
            return FaceOption.OFF;
        }
        return lst.get(ca);
    }

    private Map<BlockPos, ArrayList<Integer>> getMapClient() {
        switch(modeNow) {
            case 0:
                return ClientHolder.energy;
            case 1:
                return ClientHolder.item;
            case 2:
                return ClientHolder.fluid;
        }
        return new HashMap<>();
    }

    public void update() {}

    public void sendRedPack(int mode) {

        Network.sendToServer(new PTSRedStatePack(mode, container.pos));

    }

}
