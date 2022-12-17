package com.tying.utils;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用于 PO、BO、VO 之间的转换
 * @author Tying
 * @version 1.0
 */
public class BeanCopyUtils {

    public BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source, Class<V> clazz) {

        V result = null;

        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 如果 List 泛型使用 Object 类（List<Object>），那么传入参数的类型就应该是 Object
     * 所以应该使用泛型，或者是用通配符，如 List<? extends Object>
     * @param sourceList
     * @param clazz
     * @return
     * @param <O>
     * @param <V>
     */
    public static <O,V> List<V> copyBeanList(List<O> sourceList, Class<V> clazz) {
        return sourceList.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
