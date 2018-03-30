package com.it.bos.service.realms;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.it.bos.dao.system.PermissionRepository;
import com.it.bos.dao.system.RoleRepository;
import com.it.bos.dao.system.UserRepository;
import com.it.bos.domain.system.Permission;
import com.it.bos.domain.system.Role;
import com.it.bos.domain.system.User;

/**
 * ClassName:UserRealm <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午5:32:45 <br/>
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    
    // 授权的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();        
     // 需要根据当前的用户去查询对应的权限和角色
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if ("admin".equals(user.getUsername())) {
            
            // 内置管理员的权限和角色是写死的
            // 角色其实是权限的集合,并不是所有的权限都会包含在某一个角色中
            List<Role> list = roleRepository.findAll();
            for (Role role : list) {
                info.addRole(role.getKeyword());
            }
            
            List<Permission> list2 = permissionRepository.findAll();
               for (Permission permission : list2) {
                info.addStringPermission(permission.getKeyword());
            }
        }else{
            
            List<Role> list = roleRepository.findbyUid(user.getId());
            for (Role role : list) {
                info.addRole(role.getKeyword());
            }
            List<Permission> list2 = permissionRepository.findbyUid(user.getId());
            for (Permission permission : list2) {
                info.addStringPermission(permission.getKeyword());
            }
        }
        
        
        return info;
    }
    
    
    
    // 认证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
       // 根据用户名查找用户
        User user = userRepository.findByUsername(username);
        if (user!=null) {
         // 找到 ->比对密码
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    user, user.getPassword(), getName());
            return info;
        }
        
        
        return null;
    }

}
