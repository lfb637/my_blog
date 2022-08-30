package com.lfb.service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.lfb.NotFoundException;
import com.lfb.dao.TypeRepository;
import com.lfb.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author lfb
 * @data 2022/8/19 18:47
 */
@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getTypeById(Long id) {
         return typeRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateTypeById(Long id, Type type) {
        Type t = getTypeById(id);
        if(t == null) {
            throw new NotFoundException("类型为空");
        }else {
            BeanUtils.copyProperties(type,t);
            return typeRepository.save(t);
        }
    }

    @Transactional
    @Override
    public void deleteTypeById(Long id) {
        typeRepository.deleteById(id);
    }
}
