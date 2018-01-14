package cn.itcast.bos.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.crm.service.ws.Customer;
import cn.itcast.crm.service.ws.CustomerService;

public class FixedAreaAction extends BaseAction<FixedArea> {

	@Autowired
	private FixedAreaService service;
	private String subAreaIds;//添加定区时选择的分区的ids
	public void setSubAreaIds(String subAreaIds) {
		this.subAreaIds = subAreaIds;
	}
	@Action("fixedAreaAction_save")
	public void save(){
		try {
			service.save(model,subAreaIds);
			ajaxReturn(true, "操作成功");
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
	}
	@Action("fixedAreaAction_findByPage")
	public void findByPage(){
	 Pageable pageable = new PageRequest(page-1, rows);	
	 Page<FixedArea> page = service.findAll(pageable);
	 Map<String, Object> map = new HashMap<String, Object>();
	 map.put("total", page.getTotalElements());
	 map.put("rows", page.getContent());
	 JavaToJSon(map);
	}
	
	
	@Autowired
	private CustomerService crmProxy;
	
	@Action("fixedAreaAction_findByFixedAreaIdIsNull")
	public void findByFixedAreaIdIsNull(){
		List<Customer> list = crmProxy.findByFixedAreaIdIsNull();
		JavaToJSon(list);
	}
	@Action("fixedAreaAction_findByFixedAreaId")
	public void findByFixedAreaId(){
		List<Customer> list = crmProxy.findByFixedAreaId(model.getId());
		JavaToJSon(list);
	}
	
	private String customerIds;
	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}
	@Action("fixedAreaAction_assigncustomerstodecidedzone")
	public void assigncustomerstodecidedzone(){
		try {
			crmProxy.updateFixedAreaIdById(model.getId(), customerIds);
			ajaxReturn(true, "操作成功");
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
	}
	/**
	 * 关联快递员
	 */
	private Integer courierId;
	private Integer takeTimeId;
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
	@Action("fixedAreaAction_associationCourierToFixedArea")
	public void associationCourierToFixedArea(){
		try {
			service.associationCourierToFixedArea(model.getId(),courierId,takeTimeId);
			ajaxReturn(true, "操作成功");
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
	}
}
