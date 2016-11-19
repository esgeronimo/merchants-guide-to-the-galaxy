package com.thoughtworks.problems.merchantsguidetogalaxy.information;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by esgeronimo on 9/19/2015.
 */
public abstract class Memory<Key, Info> {

    private Map<Key, Info> map;

    public Memory() {
        map = new HashMap<Key, Info>();
    }

    public void gain(Key key, Info info) {
        map.put(key, info);
    }
    public Info recall(Key key) {
        return map.get(key);
    }
}
