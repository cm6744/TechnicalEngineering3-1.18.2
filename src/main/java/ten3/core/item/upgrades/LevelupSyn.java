package ten3.core.item.upgrades;

import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.recipe.CmTileEngine;
import ten3.lib.tile.recipe.CmTileMachineRadiused;

public class LevelupSyn extends UpgradeItem {

    public LevelupSyn() {
        super(0);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        boolean a = tile.typeOf() == Type.MACHINE_PROCESS || tile.typeOf() == Type.MACHINE_EFFECT;
        if(a) {
            tile.upgSize += 1;
            tile.efficientIn -= tile.initialEfficientIn * 0.1;
            tile.levelIn++;
            return true;
        }
        return false;
    }

}
