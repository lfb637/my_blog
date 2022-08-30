package com.lfb.service;

import com.lfb.po.Type;
import com.lfb.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author lfb
 * @data 2022/8/19 11:16
 */
public interface TypeService {
    Type saveType(Type type);  //新增
    Type getTypeById(Long id);
    Type getTypeByName(String name);
    Page<Type> listType(Pageable pageable);
    Type updateTypeById(Long id, Type type);
    void deleteTypeById(Long id);
    List<Type> listType();
    List<Type> listTypeTop(Integer size);
}
