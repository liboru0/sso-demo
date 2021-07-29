# sso-demo
单点登录

- hosts配置
```
127.0.0.1 ssoserver.com
127.0.0.1 ssoclient1.com ssoclient2.com
```

- 测试流程
1. 访问 http://ssoclient1.com:6005/empList
2. 重定向到 ：http://ssoserver.com:6001/login.html?redirect_url=http://ssoclient1.com:6005/empList
3. 登录后重定向回 http://ssoclient1.com:6005/empList
4. 此时访问 http://ssoclient2.com:6006/empList 为登录状态