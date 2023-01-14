package ten3.lib.tile.extension;

public class SlotInfo {
    public int ins;
    public int ots;
    public int ine;
    public int ote;
    ///              //input:start,end //output:start, end
    public SlotInfo(int is, int ie, int os, int oe) {
        ins = is; ots = os; ine = ie; ote = oe;
    }
}
