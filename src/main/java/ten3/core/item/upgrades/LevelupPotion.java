package ten3.core.item.upgrades;

import ten3.core.machine.useenergy.beacon.BeaconTile;
import ten3.lib.tile.mac.CmTileMachine;

public class LevelupPotion extends UpgradeItem {

    public LevelupPotion() {
        super(0);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        return tile instanceof BeaconTile;
    }
}
