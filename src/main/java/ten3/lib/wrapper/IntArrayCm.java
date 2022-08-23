package ten3.lib.wrapper;

import net.minecraft.world.inventory.SimpleContainerData;

public class IntArrayCm extends SimpleContainerData {

    public IntArrayCm(int cap) {
        super(cap);
    }

    public void translate(int i, int m) {
        set(i, get(i) + m);
    }

    public void translate(int i, int m, int minOrMax) {
        set(i, get(i) + m);
        if(m < 0) {
            if(get(i) < minOrMax) {
                set(i, minOrMax);
            }
        }
        else if(m > 0) {
            if(get(i) > minOrMax) {
                set(i, minOrMax);
            }
        }
    }

    public void translateCycle(int i, int max) {
        int p = get(i) + 1;

        set(i, p > max ? 0 : p);
    }

    //transfer the array members index by 1
    public void teleport() {

        IntArrayCm c1 = copy();

        //1234567
        //--------------
        //1234567

        for(int i = 0; i < getCount() - 1; i++) {
            set(i + 1, c1.get(i));
        }

        set(0, c1.get(getCount() - 1));

    }

    public IntArrayCm copy() {

        IntArrayCm c1 = new IntArrayCm(getCount());

        for(int i = 0; i < getCount(); i++) {
            c1.set(i, get(i));
        }

        return c1;

    }

    public int size() {
        return getCount();
    }

}
