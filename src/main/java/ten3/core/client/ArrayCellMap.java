package ten3.core.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArrayCellMap<K, V> extends HashMap<K, List<V>>
{

    public void set(K key, V value, int index, int filSize)
    {
        getOrFill(key, filSize).set(index, value);
    }

    public void append(K key, V value)
    {
        List<V> l = get(key);
        if(l == null) l = new ArrayList<>();
        l.add(value);
        put(key, l);
    }

    public List<V> get(Object key)
    {
        List<V> l = super.get(key);
        if(l == null) l = new ArrayList<>();
        put((K)key, l);
        return l;
    }

    public List<V> getOrFill(Object key, int filSize)
    {
        List<V> itg = super.get(key);
        for(int i = 0; i < filSize; i++) {
            itg.add(null);
        }
        return itg;
    }

}
