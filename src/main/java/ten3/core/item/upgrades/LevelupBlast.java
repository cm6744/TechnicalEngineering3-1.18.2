package ten3.core.item.upgrades;

import ten3.core.machine.useenergy.smelter.FurnaceTile;
import ten3.lib.tile.mac.CmTileMachine;

public class LevelupBlast extends UpgradeItem {

    public LevelupBlast() {
        super(0);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        return tile instanceof FurnaceTile;
    }

}
