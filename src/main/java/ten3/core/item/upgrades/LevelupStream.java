package ten3.core.item.upgrades;

import ten3.lib.tile.mac.CmTileMachine;

public class LevelupStream extends UpgradeItem {

    static int cached = 10000;

    public LevelupStream() {
        super(0);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        tile.info.maxReceiveEnergy = Integer.MAX_VALUE - cached;
        tile.info.maxExtractEnergy = Integer.MAX_VALUE - cached;
        tile.info.maxReceiveItem = Integer.MAX_VALUE - cached;
        tile.info.maxExtractItem = Integer.MAX_VALUE - cached;
        return true;
    }
}
