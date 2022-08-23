package ten3.lib.tile.recipe;

public class SlotInfo {
    int ins;
    int ots;
    int ine;
    int ote;
    ///              //input:start,end //output:start, end
    public SlotInfo(int is, int ie, int os, int oe) {
        ins = is; ots = os; ine = ie; ote = oe;
    }
}
