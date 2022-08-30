package com.lfb.controller.admin;

import com.lfb.po.User;
import com.lfb.service.UserService;
import com.lfb.service.UserServiceImpl;
import com.lfb.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author lfb
 * @data 2022/8/19 11:27
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    /*登录页面*/
    @GetMapping("")
    public String loginPage(){
        return "admin/login";
    }
    @GetMapping("login")
    public String _loginPage(){
        return "admin/login";
    }
    @GetMapping("index")   //logo链接
    public String indexPage(){
        return "admin/index";
    }
    /*执行登录*/
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam  String password, HttpSession httpSession, RedirectAttributes ra){

        User user = userService.checkUser(username, MD5.code(password));
        if(user != null) {
            user.setPassword(null);
            httpSession.setAttribute("user", user);      //登录用户存入信息，方便后续其它模块无需再验证登录
            return "admin/index";
        } else {
            /*采用重定向*/
            ra.addFlashAttribute("message","用户名或密码错误！！！");
            return "redirect:/admin";
        }
    }
    /*登出*/
    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("user");
        return "redirect:/admin";
    }
}
