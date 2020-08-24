package com.example.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class UserRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权了 doGetAuthorizationInfo");
        //在这里进行授权逻辑的编写
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //info.addStringPermission("add");
        //在实际开发中，取数据库查该用户的权限
        Subject subject= SecurityUtils.getSubject();
        //用户实体 User,请先定义好
        UserEntity user=(UserEntity)subject.getPrincipal();
        UserEntity dbUser=usermapper.findUser();
        info.addStringPermission(dbUser.getPerm());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证了 doGetAuthenticationInfo");
        //假设下面的账号密码是从数据库获得的
        String name="zbc";
        String password="123";
        UsernamePasswordToken token=( UsernamePasswordToken)authenticationToken;
        if (!token.getUsername().equals(name)){
            //用户名不存在，只需要返回null，shiro会自动抛出账户错误
            return null;
        }
//        if (!token.getPassword().equals(password)){
//            return null;
//        }
        //判断输入的密码与数据库中查询到的密码
        //return new SimpleAuthenticationInfo("",password,"");
//       else
//           if (!token.getPassword().equals(password)){
//            return null;
//        }
        //这里的user参数时从数据库里查出来的user信息
        return new SimpleAuthenticationInfo(user,"",password,"");
    }
}
