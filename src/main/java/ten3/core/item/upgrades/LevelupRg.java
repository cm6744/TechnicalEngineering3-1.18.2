package ten3.core.item.upgrades;

import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.recipe.CmTileMachineRadiused;

public class LevelupRg extends UpgradeItem {

    public LevelupRg() {
        super(0);
    }

    @Override
    public boolean effect(CmTileMachine tile)
    {
        boolean a = tile instanceof CmTileMachineRadiused;
        if(a) {
            ((CmTileMachineRadiused) tile).radius += ((CmTileMachineRadiused) tile).initialRadius * 0.5;
            tile.levelIn++;
        }
        return a;
    }

}
