package eam_soft.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SuperCache {

    private final Map<String, Map<String, Map<String, String>>> cacheData;
    private static SuperCache instance;

    private SuperCache() {
        this.cacheData = Collections.synchronizedMap(new HashMap<>());
    }

    public static synchronized SuperCache getInstance() {
        if (instance == null) {
            instance = new SuperCache();
        }
        return instance;
    }

    public Map<String, Map<String, String>> getCache(String fileName) {
        return cacheData.get(fileName);
    }

    public void addCache(String fileName, Map<String, Map<String, String>> data) {
        if (fileName == null || data == null) {
            throw new IllegalArgumentException("El nombre del archivo y los datos no pueden ser nulos");
        }
        cacheData.put(fileName, data);
    }

    public void delete(String fileName) {
        cacheData.remove(fileName);
    }

    public void clearAll() {
        cacheData.clear();
    }

    public int size() {
        return cacheData.size();
    }
}
