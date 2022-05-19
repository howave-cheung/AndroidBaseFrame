package com.bobo.baseframe.widget.hepler;

import com.tencent.mmkv.MMKV;

import java.util.prefs.Preferences;

/**
 * @Class: MMKVUtil
 * @Remark: 使用MMKV替代SharedPreferences 对使用进行简单封装
 */
public class MMKVUtil {
    public final String TOKEN_CODE = "token";
    public final String BASE_API = "baseApi";

    private static MMKVUtil instance;

    public static MMKVUtil get() {
        if (instance == null) {
            synchronized (MMKVUtil.class) {
                if (instance == null) {
                    instance = new MMKVUtil();
                }
            }
        }
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    public String getToken() {
        return getString(TOKEN_CODE);
    }

    public void setToken(String token) {
        put(TOKEN_CODE,token);
    }

    public String getBaseApi() {
        return getString(BASE_API);
    }

    public void setBaseApi(String baseApi) {
        put(BASE_API,baseApi);
    }

    public void put(String key, Object value) {
        if (value instanceof String) {
            putString(key, (String) value);
        } else if (value instanceof Integer) {
            putInt(key, (int) value);
        } else if (value instanceof Long) {
            putLong(key, (long) value);
        } else if (value instanceof Float) {
            putFloat(key, (float) value);
        } else if (value instanceof Double) {
            putDouble(key, (double) value);
        } else if (value instanceof Boolean) {
            putBoolean(key, (boolean) value);
        }
    }

    public void putString(String key, String value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public String getString(String key) {
        return MMKV.defaultMMKV().decodeString(key, "");
    }

    public String getString(String key, String s) {
        return MMKV.defaultMMKV().decodeString(key, s);
    }

    public void putInt(String key, int value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public int getInt(String key) {
        return MMKV.defaultMMKV().decodeInt(key, -1);
    }

    public int getInt(String key, int def) {
        return MMKV.defaultMMKV().decodeInt(key, def);
    }

    public void putLong(String key, long value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public long getLong(String key) {
        return MMKV.defaultMMKV().decodeLong(key, -1L);
    }

    public long getLong(String key, long defValue) {
        return MMKV.defaultMMKV().decodeLong(key, defValue);
    }

    public void putFloat(String key, float value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public float getFloat(String key) {
        return MMKV.defaultMMKV().decodeFloat(key, -1.0f);
    }

    public float getFloat(String key, float def) {
        return MMKV.defaultMMKV().decodeFloat(key, def);
    }

    public void putDouble(String key, double value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public double getDouble(String key) {
        return MMKV.defaultMMKV().decodeDouble(key, -1.0D);
    }

    public double getDouble(String key, double def) {
        return MMKV.defaultMMKV().decodeDouble(key, def);
    }

    public void putBoolean(String key, boolean value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public boolean getBoolean(String key) {
        return MMKV.defaultMMKV().decodeBool(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return MMKV.defaultMMKV().decodeBool(key, defaultValue);
    }
}
