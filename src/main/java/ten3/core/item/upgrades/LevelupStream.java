package ten3.core.item.upgrades;

import ten3.lib.tile.mac.CmTileMachine;

public class LevelupStream extends UpgradeItem {

    public LevelupStream() {
        super(0);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        tile.info.maxReceiveEnergy = 1000000000;
        tile.info.maxExtractEnergy = 1000000000;
        tile.info.maxReceiveItem = 64;
        tile.info.maxExtractItem = 64;
        tile.info.maxReceiveFluid = 10000;
        tile.info.maxExtractFluid = 10000;
        return true;
    }
}
