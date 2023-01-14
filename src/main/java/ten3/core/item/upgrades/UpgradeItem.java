package ten3.core.item.upgrades;

import ten3.init.tab.DefGroup;
import ten3.init.template.DefItem;
import ten3.lib.tile.mac.CmTileMachine;

public abstract class UpgradeItem extends DefItem {

    double percent;

    public UpgradeItem(double per) {

        super(build(1, DefGroup.TOOL));
        percent = per;

    }

    public boolean effect(CmTileMachine tile) {
        tile.efficientIn += tile.initialEfficientIn * percent;
        tile.info.maxStorageEnergy += tile.info.initialEnergyStorage * percent;
        tile.info.maxReceiveEnergy += tile.info.initialEnergyReceive * percent;
        tile.info.maxExtractEnergy += tile.info.initialEnergyExtract * percent;
        tile.info.maxReceiveItem += tile.info.initialItemReceive * percent;
        tile.info.maxExtractItem += tile.info.initialItemExtract * percent;
        return true;
    }

}
