package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao dao;
	@Autowired
	private PermissionDao pdao;
	@Autowired
	private MenuDao mdao;
	
	public List<Role> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(Role model, String permissionIds, String menuIds) {
//		角色表 role
		model = dao.save(model);
		
//		角色权限的关联表 role_permission
//		分析角色和权限 哪个一个是数据的维护方  
		if(permissionIds!=null&&!permissionIds.equals("")){
			String[] pIds = permissionIds.split(",");
			for (String pId : pIds) {
				Permission permission = pdao.findOne(Integer.parseInt(pId));
				model.getPermissions().add(permission);
			}
		}
	
		
//		角色菜单的关联表 role_menu
//		分析 角色和菜单哪个是维护方 
		if(menuIds!=null&&!menuIds.equals("")){
			String[] mIds = menuIds.split(",");
			for (String mId : mIds) {
				Menu menu = mdao.findOne(Integer.parseInt(mId));
				model.getMenus().add(menu);
			}
			
		}
		
		dao.save(model);
	}

	@Override
	public List<Role> findByUser(User user) {
		if(user.getUsername().equals("admin")){
			return dao.findAll();
		}else{
			return dao.findByUser(user.getId());
		}
		
		
	}

}
