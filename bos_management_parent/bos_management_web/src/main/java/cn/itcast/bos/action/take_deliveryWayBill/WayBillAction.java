package cn.itcast.bos.action.take_deliveryWayBill;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.service.take_delivery.WayBillService;

public class WayBillAction extends BaseAction<WayBill> {

	@Autowired
	private WayBillService service;
	
	@Action("wayBillAction_save")
	public void save(){
		try {
			service.save(model);
			ajaxReturn(true, "操作成功");
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
	}
}
