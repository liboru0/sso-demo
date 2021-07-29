package com.liboru.sso.server.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    public static final String SSO_TOKEN_PRE = "sso_test:";

    @Autowired
    StringRedisTemplate redisTemplate;

    @ResponseBody
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("token") String token){
        return redisTemplate.opsForValue().get(SSO_TOKEN_PRE+token);
    }

    @GetMapping("/login.html")
    public String loginPage(@RequestParam("redirect_url") String redirectUrl, Model model,
                            @CookieValue(value = "sso_token",required = false) String ssoToken){
        if(StringUtils.hasText(ssoToken)){
            // 说明之前有人登录过，浏览器留下了痕迹
            return "redirect:"+redirectUrl+"?token="+ssoToken;
        }
        model.addAttribute("url",redirectUrl);
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(String username, String password, String url, HttpServletResponse response){

        // 登录成功，跳回到之前的页面
        if(StringUtils.hasText(username)&&StringUtils.hasText(password)){
            String uuid = UUID.randomUUID().toString().replace("-","");
            redisTemplate.opsForValue().set(SSO_TOKEN_PRE+uuid,username);
            Cookie cookie = new Cookie("sso_token",uuid);
            response.addCookie(cookie);
            return "redirect:"+url+"?token="+uuid;
        }

        // 登录失败，展示登录页
        return "login";
    }

}
