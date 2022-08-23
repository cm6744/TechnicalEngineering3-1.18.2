package ten3.core.item.upgrades;

import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Level;

public class LevelupPower extends UpgradeItem {

    public LevelupPower() {
        super(0.35);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        tile.upgSize += 2;
        return super.effect(tile);
    }

}
