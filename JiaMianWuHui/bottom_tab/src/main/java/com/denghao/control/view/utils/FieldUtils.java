package com.denghao.control.view.utils;

import java.lang.reflect.Field;

/**
 * Created by yudenghao on 2018/6/19.
 */

public class FieldUtils {
    public FieldUtils() {
        super();
    }

    public <T> Object getField(Class<T> tClass, T fragment, String filedName) {
        try {
            Field field = tClass.getDeclaredField(filedName);
            if (field != null) {
                field.setAccessible(true);
                return field.get(fragment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return null;

    }

}
