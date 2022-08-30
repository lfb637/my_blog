package com.lfb.dao;

import com.lfb.po.Tag;
import com.lfb.po.Type;
import com.lfb.po.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author lfb
 * @data 2022/8/19 11:20
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
