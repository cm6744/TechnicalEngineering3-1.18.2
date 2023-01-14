package ten3.core.item.upgrades;

import ten3.lib.tile.mac.CmTileMachine;

public class LevelupKnow extends UpgradeItem {

    public LevelupKnow() {
        super(0);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        tile.upgradeSlots.upgSize = 6;
        return true;
    }
}
