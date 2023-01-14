package ten3.core.item.upgrades;

import ten3.lib.tile.mac.CmTileMachine;

public class LevelupPower extends UpgradeItem {

    public LevelupPower() {
        super(0.35);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        tile.upgradeSlots.upgSize += 2;
        return super.effect(tile);
    }

}
