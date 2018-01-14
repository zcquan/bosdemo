package cn.itcast.bos.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

public class StandardAction extends BaseAction<Standard> {

	@Autowired
	private StandardService service;

	@Action("standardAction_findByPage")
	public void  findByPage(){
		Pageable pageAble = new PageRequest(page-1, rows);
		Page<Standard> page = service.findAll(pageAble);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());  //总条数	
		map.put("rows", page.getContent());  //当前页的数据
		JavaToJSon(map);
	}
	
	
	@Action("standardAction_save")
	public void save(){
		try {
			service.save(model);
			ajaxReturn(true,"操作成功");
		} catch (Exception e) {
			ajaxReturn(false,"操作失败");
			e.printStackTrace();
		}
	}
	@Action("standardAction_findAll")
	public void findAll(){
		List list = service.findAll();
		JavaToJSon(list);
	}
	
}
