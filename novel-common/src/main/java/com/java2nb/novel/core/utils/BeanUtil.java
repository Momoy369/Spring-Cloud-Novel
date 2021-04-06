package com.java2nb.novel.core.utils;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean kelas alat operasi
 * @author Administrator
 */
public class BeanUtil {

    /**
     * Salin nilai atribut dari objek koleksi untuk menghasilkan koleksi tipe baru
     * @param source Koleksi sumber
     * @param targetClass Jenis koleksi target
     * @return Koleksi baru
     * */
    @SneakyThrows
    public static <T> List<T> copyList(List source,Class<T> targetClass){
        List<T> target = new ArrayList<>(source.size());
        for( int i = 0 ; i < source.size() ; i++){
            Object sourceItem = source.get(i);
            T targetItem = targetClass.newInstance();
            BeanUtils.copyProperties(sourceItem,targetItem);
            target.add(targetItem);
        }
        return target;

    }
}
