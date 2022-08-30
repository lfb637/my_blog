package com.lfb.service;

import com.lfb.po.Blog;
import com.lfb.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author lfb
 * @data 2022/8/19 11:16
 */
public interface BlogService {

    //局部查询列表
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);

    //查询所有列表
    Page<Blog> listBlog(Pageable pageable);

    //根据标签查询列表
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    //根据问句查询列表
    Page<Blog> listBlog(String query,Pageable pageable);

    //推荐top-N
    List<Blog> listRecommendBlogTop(Integer size);

    //按年份归档
    Map<String,List<Blog>> archiveBlog();

    //统计总量
    Long countBlog();

    //将markdown转为html
    Blog getAndConvert(Long id);

    Blog getBlogById(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlogById(Long id,Blog blog);

    void deleteBlogById(Long id);
}
