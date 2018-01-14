package cn.itcast.bos.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.service.system.UserService;

public class BOSRealm extends AuthorizingRealm {

	@Autowired
	private UserService service;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	/**
	 * 授权  判断当前登录人有哪些权限
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("执行了授权方法");
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
//		动态获取当前登录人拥有的角色
		User user = (User) arg0.getPrimaryPrincipal();
		List<Role> roles  = roleService.findByUser(user);
//		放入角色
		for (Role role : roles) {
			info.addRole(role.getKeyword());
		}
		
//		动态获取当前登录人拥有的权限
		List<Permission> permission = permissionService.findByUser(user);
		
		for (Permission p : permission) {
//			放入权限
			info.addStringPermission(p.getKeyword());
		}
		return info;
	}

	/**
	 * 认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行了认证方法");
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		String username = token.getUsername();
		String password = new String(token.getPassword());
		User user = service.findByUsernameAndPassword(username, password);
//		User user = service.findByUsername(username);
		if(user==null){
			return null;
		}else{
//			principal 主角 当前登录人, credentials 密码, realmName 当前realm类名
			return new SimpleAuthenticationInfo(user, password, getName());
		}
		
	}

}
