package cn.itcast.bos.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.TakeTimeService;

public class TakeTimeAction extends BaseAction<TakeTime> {
	
	
	@Autowired
	private TakeTimeService service;
	
	@Action("takeTimeAction_findAll")
	public void findAll(){
		List<TakeTime> list= service.findAll();
		JavaToJSon(list);
	}

}
