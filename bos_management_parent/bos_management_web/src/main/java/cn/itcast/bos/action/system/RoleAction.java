package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;

public class RoleAction extends BaseAction<Role> {

	@Autowired
	private RoleService service;
	
	@Action("roleAction_findAll")
	public void findAll(){
		List<Role> list = service.findAll();
		JavaToJSon(list);
	}
	
	
	private String permissionIds;//接收的是选择的权限id
	private String menuIds;//接收的是选择的菜单id
	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	@Action("roleAction_save")
	public void save(){
		try {
			service.save(model,permissionIds,menuIds);
			ajaxReturn(true, "");
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
	}
}
