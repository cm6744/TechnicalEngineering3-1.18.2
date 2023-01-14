package ten3.core.item.upgrades;

import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.Type;

public class LevelupSyn extends UpgradeItem {

    public LevelupSyn() {
        super(0);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        boolean a = tile.typeOf() == Type.MACHINE_PROCESS || tile.typeOf() == Type.MACHINE_EFFECT;
        if(a) {
            tile.upgradeSlots.upgSize += 1;
            tile.efficientIn -= tile.initialEfficientIn * 0.1;
            return true;
        }
        return false;
    }

}
