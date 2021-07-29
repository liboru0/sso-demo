package com.liboru.sso.client.controller.emp;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmpController {

    public static final String SSO_LOGIN_URL = "http://ssoserver.com:6001/login.html";

    public static final String SSO_GET_USER_URL = "http://ssoserver.com:6001/userInfo";

    @GetMapping("/empList")
    public String employees(Model model, HttpSession session
            , @RequestParam(value = "token",required = false) String token){

        if(StringUtils.hasText(token)){
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> forEntity = restTemplate.getForEntity(SSO_GET_USER_URL+"?token=" + token, String.class);
            String userInfo = forEntity.getBody();
            session.setAttribute("loginUser",userInfo);
        }

        Object loginUser = session.getAttribute("loginUser");

        if(loginUser==null){
            // 没登录，跳转到认证服务去登录
            // 跳转过去时，要通过url参数携带上本页面的地址
            return "redirect:"+SSO_LOGIN_URL+"?redirect_url=http://ssoclient2.com:6006/empList";
        }

        List<String> emps = new ArrayList<>();
        emps.add("张三");
        emps.add("李四");
        emps.add("王五");
        model.addAttribute("emps",emps);
        return "list";
    }

}
