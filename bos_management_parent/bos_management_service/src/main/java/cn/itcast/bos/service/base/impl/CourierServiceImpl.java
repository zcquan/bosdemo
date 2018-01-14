package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {

	@Autowired
	private CourierDao dao;
	
	@Override
	public void save(Courier courier) {
		dao.save(courier);
	}

	@Override
	public Page<Courier> findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	@RequiresPermissions("courier-delete")
	public void deleteBatch(String courierIds) {
		
//		代码级别的控制方式
		
//		Subject subject = SecurityUtils.getSubject();
//		if(!subject.isPermitted("courierManage-delete")){
////			没有courierManage-delete权限
//			throw new UnauthorizedException("无此权限");
//			
//		}
		
//		courierIds="1,2,3"
		String[] ids = courierIds.split(",");
		for (String id : ids) {
//			update T_COURIER set c_deltag='1' where c_id=?
			dao.updateDeltag(Integer.parseInt(id));
//			dao.delete(Integer.parseInt(id));  //物理删除
		}
	}

	@Override
	public Page<Courier> findByPage(Specification<Courier> specification, Pageable pageable) {
		
		return dao.findAll(specification, pageable);
	}

	@Override
	public List<Courier> findByDeltagIsNull() {
		return dao.findByDeltagIsNull();
	}

}
