package com.lfb.controller.admin;

import com.lfb.po.Tag;
import com.lfb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * @author lfb
 * @data 2022/8/18 9:07
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    public String tags(@PageableDefault(size=3, sort={"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags_input";
    }
    @GetMapping("/tags/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("tag",tagService.getTagById(id));
        return "admin/tags_input";
    }
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        tagService.deleteTagById(id);
        ra.addFlashAttribute("message","删除成功");
        /*增删改需使用重定向，不能直接返回到特定页面（数据有变化在特定页面查询数据时有误）*/
        return "redirect:/admin/tags";
    }
    @PostMapping("/tags")
    public String post(Tag tag, BindingResult result, RedirectAttributes ra){
        /*通过名称查询数据库中对象*/
        Tag temp = tagService.getTagByName(tag.getName());
        if(temp != null) {
            result.rejectValue("name","nameError", "不能重复添加分类名称");
        }
        if(result.hasErrors()) {
            return "admin/tags_input";
        }
        //新增记录
        Tag t = tagService.saveTag(tag);
        if (t != null) {
            ra.addFlashAttribute("message","新增成功");
        } else {
            ra.addFlashAttribute("message","新增失败！！！");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes ra){
        /*先通过名称查询数据库中对象*/
        Tag temp = tagService.getTagByName(tag.getName());
        if(temp != null) {
            result.rejectValue("name","nameError", "不能重复添加分类名称");
        }
        if(result.hasErrors()) {
            return "admin/tags_input";
        }
        //更新记录
        Tag t = tagService.updateTagById(id, tag);
        if (t != null) {
            ra.addFlashAttribute("message","更新成功");
        } else {
            ra.addFlashAttribute("message","更新失败！！！");
        }
        return "redirect:/admin/tags";
    }
}
