package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;

public class UserAction extends BaseAction<User> {
	@Autowired
	private UserService service;
	
	@Action("userAction_login")
	public void login(){
//		1、创建令牌
		UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(),model.getPassword());
//		2、获取主题
		Subject subject = SecurityUtils.getSubject();
//		3、开始认证
		try {
			subject.login(token);
			ajaxReturn(true, "");
		} catch (AuthenticationException e) {
			ajaxReturn(false, "用户名或密码错误");
			e.printStackTrace();
		}
		
//		username  password
//		User user = service.findByUsernameAndPassword(model.getUsername(),model.getPassword());
//		if(user==null){
//			ajaxReturn(false, "用户名或密码错误");
//		}else{
//			ServletActionContext.getRequest().getSession().setAttribute("user", user);
//			ajaxReturn(true, "");
//		}
	}
	@Action("userAction_getName")
	public void getName(){
//		User user =  (User) ServletActionContext.getRequest().getSession().getAttribute("user");
//		从shiro框架的session管理中获取当前登录人
		
//		获取与shiro交互的入库  subject
		Subject subject = SecurityUtils.getSubject();
//		从subject中获取主角  此主角就是realm的认证方法放入的主角
		User user =  (User) subject.getPrincipal();
		if(user==null){
			ajaxReturn(false, "用户名或密码错误");
		}else{
			ajaxReturn(true, user.getUsername());
		}
	}
	@Action(value="userAction_logout",results={@Result(name="success",type="redirect",location="login.html")})
	public String logout(){
//		ServletActionContext.getRequest().getSession().removeAttribute("user");
		
//		使用shiro框架的session管理
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "success";
	}
	@Action("userAction_findAll")
	public void findAll(){
		List<User> list = service.findAll();
		JavaToJSon(list);
	}
	private String roleIds;
	
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	@Action("userAction_save")
	public void save(){
		try {
			service.save(model,roleIds);
			ajaxReturn(true, null);
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
		
	}
	
}
