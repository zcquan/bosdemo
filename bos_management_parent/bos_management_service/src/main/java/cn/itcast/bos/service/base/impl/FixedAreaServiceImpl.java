package cn.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.dao.base.TakeTimeDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaDao dao;
	@Autowired
	private SubAreaDao sdao;
	@Autowired
	private CourierDao cdao;
	@Autowired
	private TakeTimeDao tdao;
	public void save(FixedArea model) {
		dao.save(model);
	}
	@Override
	public Page<FixedArea> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}
	@Override
	public void save(FixedArea model, String subAreaIds) {
		
//		1、定区表中新增一条数据
		model = dao.save(model);
//		2、修改关联分区的外键
		String[] ids = subAreaIds.split(",");
		for (String id : ids) {
			SubArea subArea = sdao.findOne(id);
			subArea.setFixedArea(model);
		}
	}
	@Override
	public void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId) {
//		1、关联表  定区和快递员   找 维护方是  fixedArea
//		insert into t_fixedarea_courier values  (?,?);--定区id  快递员id
		FixedArea fixedArea = dao.findOne(id);
		Courier courier = cdao.findOne(courierId);
		fixedArea.getCouriers().add(courier);

//		2、快递员
//		update t_courier set c_taketime_id=? where c_id=? --收派时间的id 快递员的id
		TakeTime takeTime = tdao.findOne(takeTimeId);
		courier.setTakeTime(takeTime);
	}

}
