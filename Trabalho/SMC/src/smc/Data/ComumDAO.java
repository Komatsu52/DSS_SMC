package smc.Data;

import smc.Business.Comum;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ComumDAO implements Map<String, Comum> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Comum get(Object key) {
        return null;
    }

    @Override
    public Comum put(String key, Comum value) {
        return null;
    }

    @Override
    public Comum remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Comum> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Comum> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Comum>> entrySet() {
        return null;
    }
}
