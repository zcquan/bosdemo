package cn.itcast.bos.action.system;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class MenuAction extends BaseAction<Menu> {

	@Autowired
	private MenuService service;
	
	@Action("menuAction_findAll")
	public void findAll(){
		List<Menu> list = service.findAll();
		JavaToJSon(list);
	}
	
	@Action("menuAction_findByUser")
	public void findByUser(){
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Menu> list = service.findByUser(user);
//		使用json-lib方式转json
		JsonConfig jsonconfig = new JsonConfig();
//		设置需要排除的属性，即不需要转json的字段   ，因为前端使用的是简单的json数据构建的ztree，不需要children，需要排除
		jsonconfig.setExcludes(new String[]{"roles","childrenMenus","parentMenu","children"});
//		JSONArray 把集合转json需要使用JSONArray   把对象转json 需要使用JSONObject
		 String string = JSONArray.fromObject(list, jsonconfig).toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		try {
			response.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Action("menuAction_findByPidIsNull")
	public void findByPidIsNull(){
		List<Menu> list = service.findByPidIsNull();
		JavaToJSon(list);
	}
	@Action("menuAction_save")
	public void save(){
		try {
			service.save(model);
			ajaxReturn(true, "");
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
		 
	}
	
}
