package com.example.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {

    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("name","李白不愧是酒仙");
        //返回到test.html页面
        return "test";
    }

    @RequestMapping("/add")
    public String add(){
        return "add";
    }

    @RequestMapping("/update")
    public String update(){
        return "update";
    }

    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String name,String password,Model model){
        System.out.println(name+"-"+password);
        //使用shiro编写认证逻辑
        //1.获取subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token=new UsernamePasswordToken(name,password);
        //3.执行登录方法
        try{
        subject.login(token);
            //如果登录成功，跳转到主页
            System.out.println("登录成功");
            return "test";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","账号不存在");
            System.out.println("账号不存在");
            //账号不存在，则返回到登录页面
            return "login";
        }catch (IncorrectCredentialsException e){
            //密码不对，则返回到登录页面
            model.addAttribute("msg","密码错误");
            System.out.println("密码错误");
            return "login";
        }

    }

    @RequestMapping("/unAuth")
    public String unAuth(){
        return "unAuth";
    }


}
