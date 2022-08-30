package com.lfb.service;

import com.lfb.NotFoundException;
import com.lfb.dao.BlogRepository;
import com.lfb.po.Blog;
import com.lfb.po.Type;
import com.lfb.util.MarkdownUtils;
import com.lfb.util.MyBeanUtils;
import com.lfb.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author lfb
 * @data 2022/8/19 18:47
 */
@Service
public class BlogServiceImpl implements BlogService{

    @Autowired BlogRepository blogRepository;

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                /*多条件组合查询*/
                List<Predicate> p = new ArrayList<>();
                // 标题查询
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    p.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                //类型查询
                if (blog.getTypeId() != null) {
                    p.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                //是否为推荐查询
                if(blog.isRecommend()) {
                    p.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                //执行查询
                cq.where(p.toArray(new Predicate[p.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Transactional
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        // 按更新时间降序
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for(String year: years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Transactional
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).get();
        if(blog.getId() == null) {
            throw new NotFoundException("博客不存在");
        }else {
            Blog temp = new Blog();
            BeanUtils.copyProperties(blog,temp);
            String content = temp.getContent();
            temp.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
            blogRepository.updateViews(id);
            return temp;
        }
    }

    @Transactional
    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlogById(Long id, Blog blog) {
        Blog b = blogRepository.getReferenceById(id);
        if (b == null) {
            throw new NotFoundException("博客不存在。。。。");
        }
        /*新建的blog中只对非空字段赋值*/
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlogById(Long id) {
        blogRepository.deleteById(id);
    }
}
