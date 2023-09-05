package ten3.core.client;

import java.util.List;

public class ArrayCellIntMap<K> extends ArrayCellMap<K, Integer>
{

    public List<Integer> get(Object key)
    {
        List<Integer> itg = super.get(key);
        for(int i = 0; i < itg.size(); i++) {
            if(itg.get(i) == null) itg.set(i, 0);
        }
        return itg;
    }

    public List<Integer> getOrFill(Object key, int filSize)
    {
        List<Integer> itg = super.get(key);
        for(int i = 0; i < filSize; i++) {
            itg.add(0);
        }
        return itg;
    }

}
