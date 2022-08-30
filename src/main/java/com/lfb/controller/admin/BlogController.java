package com.lfb.controller.admin;

import com.lfb.NotFoundException;
import com.lfb.po.Blog;
import com.lfb.po.Tag;
import com.lfb.po.User;
import com.lfb.service.BlogService;
import com.lfb.service.TagService;
import com.lfb.service.TypeService;
import com.lfb.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author lfb
 * @data 2022/8/18 9:07
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;

    //列表页
    @GetMapping("/blog")
    public String blog(@PageableDefault(size=3,sort={"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery bq, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listType());
        return "admin/blog";
    }
    //搜索模块
    @PostMapping("/blog/search")
    public String search(@PageableDefault(size=3,sort={"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blog :: blogList";
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    //新增模块
    @GetMapping("/blog/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "admin/blog_input";
    }
    //编辑模块
    @GetMapping("/blog/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlogById(id);
        blog.init();
        model.addAttribute("blog", blog);
        return "admin/blog_input";
    }
    @PostMapping("/blog")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlogById(blog.getId(), blog);
        }
        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blog";
    }
    //删除模块
    @GetMapping("/blog/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        blogService.deleteBlogById(id);
        ra.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blog";
    }
}
