package ten3.core.item.upgrades;

import ten3.lib.tile.mac.CmTileMachine;

public class LevelupAug extends UpgradeItem {

    public LevelupAug() {
        super(0.2);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        tile.upgradeSlots.upgSize += 1;
        return super.effect(tile);
    }
}
