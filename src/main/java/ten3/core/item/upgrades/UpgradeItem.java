package ten3.core.item.upgrades;

import ten3.init.template.DefItem;
import ten3.lib.tile.CmTileMachine;

public abstract class UpgradeItem extends DefItem {

    double percent;

    public UpgradeItem(double per) {

        super(1);
        percent = per;

    }

    public boolean effect(CmTileMachine tile) {
        tile.efficientIn += tile.initialEfficientIn * percent;
        tile.maxStorage += tile.initialStorage * percent;
        tile.maxReceive += tile.initialReceive * percent;
        tile.maxExtract += tile.initialExtract * percent;
        tile.maxReceiveItem += tile.initialItemReceive * percent;
        tile.maxExtractItem += tile.initialItemExtract * percent;
        tile.levelIn++;
        return true;
    }

}
