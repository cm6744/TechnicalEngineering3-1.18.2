package ten3.core.item.upgrades;

import ten3.lib.tile.mac.CmTileMachine;

public class LevelupShulker extends UpgradeItem {

    public LevelupShulker() {
        super(0.75);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        tile.upgradeSlots.upgSize += 3;
        return super.effect(tile);
    }

}
