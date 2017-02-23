package com.core.op.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/11
 */
public class PreferenceUtil {

    private static final String SEPARATOR = "#";
    private static final String TAG = PreferenceUtil.class.getName();

    public static final String fileName = "APP_PREFERENCE";

    public static void write(Context context, String k, int v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(k, v);
        editor.commit();
    }

    public static void write(Context context, String k,
                             boolean v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(k, v);
        editor.commit();
    }

    public static void write(Context context, String k,
                             String v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(k, v);
        editor.commit();
    }


    public static void write(Context context, String fileName, String k, int v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(k, v);
        editor.commit();
    }

    public static void write(Context context, String fileName, String k,
                             boolean v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(k, v);
        editor.commit();
    }

    public static void write(Context context, String fileName, String k,
                             String v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(k, v);
        editor.commit();
    }

    public static int readInt(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getInt(k, 0);
    }

    public static int readInt(Context context, String fileName, String k,
                              int defv) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getInt(k, defv);
    }

    public static boolean readBoolean(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getBoolean(k, false);
    }

    public static boolean readBoolean(Context context, String fileName,
                                      String k, boolean defBool) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getBoolean(k, defBool);
    }

    public static String readString(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getString(k, null);
    }

    public static String readString(Context context, String fileName, String k,
                                    String defV) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getString(k, defV);
    }

    public static int readInt(Context context, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getInt(k, 0);
    }

    public static int readInt(Context context, String k,
                              int defv) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getInt(k, defv);
    }

    public static boolean readBoolean(Context context, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getBoolean(k, false);
    }

    public static boolean readBoolean(Context context,
                                      String k, boolean defBool) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getBoolean(k, defBool);
    }

    public static String readString(Context context, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getString(k, null);
    }

    public static String readStringWithDefV(Context context, String k,
                                            String defV) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getString(k, defV);
    }

    public static void remove(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(k);
        editor.commit();
    }

    public static void remove(Context context, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(k);
        editor.commit();
    }

    public static void remove(Context context, String... k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        for (String key : k) {
            editor.remove(key);
        }
        editor.commit();
    }

    public static void clean(Context cxt, String fileName) {
        SharedPreferences preference = cxt.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 通过Bean.class得到{@link SharedPreferences} 接口，
     * 该SharedPreferences对应文件名为Bean的包名信息
     *
     * @param context Context
     * @param clx     Bean'class
     * @param <T>     Any Bean
     * @return {@link SharedPreferences}
     */
    public static <T> SharedPreferences getSharedPreferences(Context context, Class<T> clx) {
        return context.getSharedPreferences(clx.getName(), Context.MODE_PRIVATE);
    }

    /**
     * 保存一个Bean到{@link SharedPreferences}文件中，
     * 该方法会调用{@link #getSharedPreferences(Context, Class)}方法，
     * 创建SharedPreferences文件，如果文件不存在会进行新建操作
     *
     * @param context Context
     * @param t       Bean
     * @param <T>     Any Bean
     * @return 返回是否保存成功
     */
    public static <T> boolean save(Context context, T t) {
        final Class<?> clx = t.getClass();

        // We should remove all data before save data
        remove(context, clx);

        // Get all data form t
        Map<String, Data> map = new ArrayMap<>();
        buildValuesToMap(clx, t, "", map);

        SharedPreferences sp = getSharedPreferences(context, clx);
        SharedPreferences.Editor editor = sp.edit();

        // Get all existing key
        Set<String> existKeys = sp.getAll().keySet();

        // Foreach the sava data
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Data data = map.get(key);

            final Class<?> type = data.type;
            final Object value = data.value;

            try {
                if (value == null) {
                    removeKeyFamily(editor, existKeys, key);
                } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                    editor.putInt(key, (Byte) value);
                } else if (type.equals(Short.class) || type.equals(short.class)) {
                    editor.putInt(key, (Short) value);
                } else if (type.equals(Integer.class) || type.equals(int.class)) {
                    editor.putInt(key, (Integer) value);
                } else if (type.equals(Long.class) || type.equals(long.class)) {
                    editor.putLong(key, (Long) value);
                } else if (type.equals(Float.class) || type.equals(float.class)) {
                    editor.putFloat(key, (Float) value);
                } else if (type.equals(Double.class) || type.equals(double.class)) {
                    editor.putString(key, (String.valueOf(value)));
                } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                    editor.putBoolean(key, (Boolean) value);
                } else if (type.equals(Character.class) || type.equals(char.class)) {
                    editor.putString(key, value.toString());
                } else if (type.equals(String.class)) {
                    editor.putString(key, value.toString());
                } else {
                    Log.e(TAG, String.format("Con't support save this type:%s, value:%s, key:%s",
                            type, value, key));
                }
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Save error:" + e.getMessage());
            }
        }

        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        return true;
    }

    /**
     * 从{@link SharedPreferences}文件中加载数据到Bean中，
     * 如果SharedPreferences文件不存在或者未存储任何信息则返回NULL
     *
     * @param context Context
     * @param clx     Bean'class
     * @param <T>     Any Bean
     * @return 加载成功则返回Bean的实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T load(Context context, Class<T> clx) {
        SharedPreferences sp = getSharedPreferences(context, clx);
        // Get all existing key
        Set<String> existKeys = sp.getAll().keySet();
        if (existKeys.size() == 0)
            return null;
        return (T) buildTargetFromSource(clx, null, "", existKeys, sp);
    }

    /**
     * 从{@link SharedPreferences}文件中加载数据到Bean中，
     * 如果SharedPreferences文件不存在或者未存储任何信息则返回NULL
     * 与 {@link #load(Context, Class)} 方法的区别在于，该加载会从原始文件刷新一次缓存，
     * 以保证任何情况下都从原始文件获取最新信息，可解决{@link SharedPreferences}跨进程问题
     *
     * @param context Context
     * @param clx     Bean'class
     * @param <T>     Any Bean
     * @return 加载成功则返回Bean的实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadFormSource(Context context, Class<T> clx) {
        SharedPreferences sp = getSharedPreferences(context, clx);

//        // Use reflection to force refresh data
//        try {
//            Reflector.with(sp).call("startReloadIfChangedUnexpectedly");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // Get all existing key
        Set<String> existKeys = sp.getAll().keySet();
        if (existKeys.size() == 0)
            return null;
        return (T) buildTargetFromSource(clx, null, "", existKeys, sp);
    }

    /**
     * 清空一个Bean存储的{@link SharedPreferences}信息，
     * 之后调用{@link #load(Context, Class)}, {@link #loadFormSource(Context, Class)} 都返回NULL
     *
     * @param context Context
     * @param clx     Bean'class
     * @param <T>     Any Bean
     */
    public static <T> void remove(Context context, Class<T> clx) {
        SharedPreferences sp = getSharedPreferences(context, clx);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    private static <T> void buildValuesToMap(Class<?> clx, T t, String preFix,
                                             Map<String, Data> map) {
        if (clx == null || clx.equals(Object.class) || t == null) {
            return;
        }
        final Field[] fields = clx.getDeclaredFields();
        if (fields == null || fields.length == 0)
            return;

        // Foreach fields
        for (Field field : fields) {
            if (isContSupport(field))
                continue;

            final String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            // Change the Field accessible status
            boolean isAccessible = field.isAccessible();
            if (!isAccessible)
                field.setAccessible(true);

            Object value;
            try {
                value = field.get(t);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                Log.e(TAG, "buildValuesToMap error:" + e.getMessage());
                continue;
            }

            if (isBasicType(fieldType)) {
                String key = preFix + fieldName;
                if (!map.containsKey(key)) {
                    map.put(key, new Data(fieldType, value));
                }
            } else {
                buildValuesToMap(fieldType, value, preFix + fieldName + SEPARATOR, map);
            }
        }

        // Get super class fields
        buildValuesToMap(clx.getSuperclass(), t, preFix, map);
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    private static <T> Object buildTargetFromSource(Class<T> clx, T target, String preFix,
                                                    Set<String> existKeys, SharedPreferences sp) {
        // Each to Object
        if (clx == null || clx.equals(Object.class)) {
            return target;
        }

        // Create instance
        if (target == null) {
            try {
                target = clx.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        // Get the class fields
        Field[] fields = clx.getDeclaredFields();
        if (fields == null || fields.length == 0)
            return target;

        // Foreach fields
        for (Field field : fields) {
            if (isContSupport(field))
                continue;

            final String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            // Change the Field accessible status
            boolean isAccessible = field.isAccessible();
            if (!isAccessible)
                field.setAccessible(true);

            // Build the key
            String key = preFix + fieldName;
            // Get target value
            Object value = null;
            if (isBasicType(fieldType)) {
                if (existKeys.contains(key)) {
                    // From the share map
                    if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
                        value = (byte) sp.getInt(key, 0);
                    } else if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
                        value = (short) sp.getInt(key, 0);
                    } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                        value = sp.getInt(key, 0);
                    } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                        value = sp.getLong(key, 0);
                    } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                        value = sp.getFloat(key, 0);
                    } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                        value = Double.valueOf(sp.getString(key, "0.00"));
                    } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                        value = sp.getBoolean(key, false);
                    } else if (fieldType.equals(Character.class) || fieldType.equals(char.class)) {
                        value = sp.getString(key, "").charAt(0);
                    } else if (fieldType.equals(String.class)) {
                        value = sp.getString(key, "");
                    }
                }
            } else {
                value = buildTargetFromSource(fieldType, null, preFix + fieldName + SEPARATOR, existKeys, sp);
            }

            // Set the field value
            if (value != null) {
                try {
                    field.set(target, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Log.e(TAG, String.format("Set field error, Key:%s, type:%s, value:%s",
                            key, fieldType, value));
                }
            } else {
                Log.e(TAG, String.format("Get field value error, Key:%s, type:%s",
                        key, fieldType));
            }
        }

        // Get super class fields
        return buildTargetFromSource(clx.getSuperclass(), target, preFix, existKeys, sp);
    }

    private static void removeKeyFamily(SharedPreferences.Editor editor, Set<String> existKeys,
                                        String removeKey) {
        String preFix = removeKey + SEPARATOR;
        for (String str : existKeys) {
            if (str.equals(removeKey) || str.startsWith(preFix))
                editor.remove(str);
        }
    }

    private static boolean isContSupport(Field field) {
        return (Modifier.isStatic(field.getModifiers())
                || Modifier.isFinal(field.getModifiers())
                || Modifier.isAbstract(field.getModifiers()));
    }

    private static boolean isBasicType(Class<?> clx) {
        return clx.equals(Byte.class) || clx.equals(byte.class)
                || clx.equals(Short.class) || clx.equals(short.class)
                || clx.equals(Integer.class) || clx.equals(int.class)
                || clx.equals(Long.class) || clx.equals(long.class)
                || clx.equals(Float.class) || clx.equals(float.class)
                || clx.equals(Double.class) || clx.equals(double.class)
                || clx.equals(Boolean.class) || clx.equals(boolean.class)
                || clx.equals(Character.class) || clx.equals(char.class)
                || clx.equals(String.class);

    }

    private static class Data {
        Class<?> type;
        Object value;

        Data(Class<?> type, Object value) {
            this.type = type;
            this.value = value;
        }
    }
}
