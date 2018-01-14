package cn.itcast.bos.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
public class CourierAction extends BaseAction<Courier> {

	
	@Autowired
	private CourierService service;
	
	@Action("courierAction_save")
	public void save(){
		try {
			service.save(model);
			ajaxReturn(true, "操作成功");
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
	}
	@Action("courierAction_findByPage")
	public void findByPage(){

		
		Specification<Courier> specification = new Specification<Courier>() {
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
//				Root<Courier> root, 相当于查询的主体  当做courier
//				CriteriaQuery<?> query,  用来构建查询条件 
//				CriteriaBuilder cb       用来构建查询条件 
				
//				model.getCourierNum()
				List<Predicate> list = new ArrayList<Predicate>();
//				工号	
				if(StringUtils.isNotBlank(model.getCourierNum())){
					Predicate predicate = cb.like(root.get("courierNum").as(String.class), "%"+model.getCourierNum()+"%");
					list.add(predicate);
//					return predicate;
				}
	
//				model.getCompany()
//				所属单位	
				if(StringUtils.isNotBlank(model.getCompany())){
					Predicate predicate = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					list.add(predicate);
//					return predicate;
				}
//				model.getType()
//				类型
				if(StringUtils.isNotBlank(model.getType())){
					Predicate predicate = cb.like(root.get("type").as(String.class), "%"+model.getType()+"%");
					list.add(predicate);
//					return predicate;
				}
//				model.getStandard().getName()
//				收派标准
				if(model.getStandard()!=null&&StringUtils.isNotBlank(model.getStandard().getName())){
//					select c.* from courier c inner join standard s    where  c.standardId=s.id  and s.name=?
					Join<Object, Object> join = root.join("standard");  //当做standard
					Predicate predicate = cb.like(join.get("name").as(String.class), "%"+model.getStandard().getName()+"%");
					list.add(predicate);
//					return predicate;
				}
				
//				list中没有任何对象
				if( list.size()==0){
					return null;
				}
//				list--->数组-->对象
				Predicate[] predicates = new Predicate[list.size()];
				predicates = list.toArray(predicates);
				return  cb.and(predicates);
			}
		};
		
		Pageable pageable  = new PageRequest(page-1, rows);
//		Page<Courier> page = service.findByPage(pageable);
		Page<Courier> page = service.findByPage(specification,pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		JavaToJSon(map);
	}
	
	private String courierIds; //需要作废的快递员的id：“1,2,3”
	public void setCourierIds(String courierIds) {
		this.courierIds = courierIds;
	}
	/**
	 * 批量作废/删除
	 */
	@Action("courierAction_deleteBatch")
	public void deleteBatch(){
		try {
			service.deleteBatch(courierIds);
			ajaxReturn(true, "操作成功");
		} catch (UnauthorizedException e) {
//			e.getMessage()
			ajaxReturn(false, "无此权限");
			e.printStackTrace();
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
	}
	/**
	 * 查询正常使用中的快递员
	 */
	@Action("courierAction_findByDeltagIsNull")
	public void findByDeltagIsNull(){
			List<Courier> list =service.findByDeltagIsNull();
			JavaToJSon(list);
	}
	
	
}
