package com.android.collect.library.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonUtil {

    private static final String TAG = "Gson";
    private static Gson mGson = new Gson();

    /**
     * 将JsonObject格式的String转成对象
     *
     * @param jsonData
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        T result = null;
        try {
            result = mGson.fromJson(jsonData, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            LogUtils.e(TAG, e.getMessage());
        }
        return result;
    }

    /**
     * 将JsonArray格式的String转成对象列表
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> getListFromJSON(String json, Class<T[]> type) {
        T[] list = null;
        try {
            list = mGson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            LogUtils.e(TAG, e.getMessage());
        }
        return new ArrayList<T>(Arrays.asList(list));
    }

    /**
     * 将对象(Bean或者List<Bean></>)准换为json字符串
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String objectToString(T object) {
        return mGson.toJson(object);
    }

    /**
     * 将JsonArray格式的String转化成对象(Bean)list
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */

    public static <T> List<T> stringToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(mGson.fromJson(elem, cls));
        }
        return list;
    }

}
