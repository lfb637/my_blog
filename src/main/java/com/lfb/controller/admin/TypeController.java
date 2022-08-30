package com.lfb.controller.admin;

import com.lfb.po.Type;
import com.lfb.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;


/**
 * @author lfb
 * @data 2022/8/18 9:07
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    public String types(@PageableDefault(size=3, sort={"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types_input";
    }
    @GetMapping("/types/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("type",typeService.getTypeById(id));
        return "admin/types_input";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        typeService.deleteTypeById(id);
        ra.addFlashAttribute("message","删除成功");
        /*增删改需使用重定向，不能直接返回到特定页面（数据有变化在特定页面查询数据时有误）*/
        return "redirect:/admin/types";
    }
    @PostMapping("/types")
    public String post(Type type, BindingResult result, RedirectAttributes ra){
        /*通过名称查询数据库中对象*/
        Type temp = typeService.getTypeByName(type.getName());
        if(temp != null) {
            result.rejectValue("name","nameError", "不能重复添加分类名称");
        }
        if(result.hasErrors()) {
            return "admin/types_input";
        }
        //新增记录
        Type t = typeService.saveType(type);
        if (t != null) {
            ra.addFlashAttribute("message","新增成功");
        } else {
            ra.addFlashAttribute("message","新增失败！！！");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(Type type, BindingResult result, @PathVariable Long id, RedirectAttributes ra){
        /*先通过名称查询数据库中对象*/
        Type temp = typeService.getTypeByName(type.getName());
        if(temp != null) {
            result.rejectValue("name","nameError", "不能重复添加分类名称");
        }
        if(result.hasErrors()) {
            return "admin/types_input";
        }
        //更新记录
        Type t = typeService.updateTypeById(id, type);
        if (t != null) {
            ra.addFlashAttribute("message","更新成功");
        } else {
            ra.addFlashAttribute("message","更新失败！！！");
        }
        return "redirect:/admin/types";
    }
}
