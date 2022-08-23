package ten3.lib.tile;

public class PacketCapData {

    public int ext;
    public int rec;
    public int sto;
    public double eff;
    public int lv;

    public static PacketCapData of(int e, int r, int s, double ef, int l) {
        PacketCapData data = new PacketCapData();
        data.sto = s;
        data.ext = e;
        data.rec = r;
        data.eff = ef;
        data.lv = l;
        return data;
    }

}
