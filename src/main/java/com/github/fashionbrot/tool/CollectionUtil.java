package com.github.fashionbrot.tool;

import java.util.Collection;
import java.util.Map;

/**
 * @author fashionbrot
 * @date 2020/12/07
 */
public class CollectionUtil {

    private CollectionUtil(){

    }

    /**
     * Is empty boolean.
     *
     * @param array the array
     * @return the boolean
     */
    public static boolean isEmpty(Object[] array) {
        return !isNotEmpty(array);
    }

    /**
     * Is not empty boolean.
     *
     * @param array the array
     * @return the boolean
     */
    public static boolean isNotEmpty(Object[] array) {
        return array != null && array.length > 0;
    }


    public static boolean isEmpty( Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty( Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }

    public static boolean isNotEmpty( Collection<?> collection) {
        return !isEmpty(collection);
    }
}
