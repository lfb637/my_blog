package com.lfb.service;

import com.lfb.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author lfb
 * @data 2022/8/19 11:16
 */
public interface TagService {
    Tag saveTag(Tag Tag);  //新增
    Tag getTagById(Long id);
    Tag getTagByName(String name);
    Tag updateTagById(Long id, Tag Tag);
    void deleteTagById(Long id);
    Page<Tag> listTag(Pageable pageable);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    List<Tag> listTagTop(Integer size);
}
