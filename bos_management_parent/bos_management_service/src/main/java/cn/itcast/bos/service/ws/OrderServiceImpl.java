package cn.itcast.bos.service.ws;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.dao.take_delivery.OrderDao;
import cn.itcast.bos.dao.take_delivery.WorkBillDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.utils.SmsUtils;
import cn.itcast.crm.service.ws.CustomerService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao dao;
	@Autowired
	private AreaDao adao;
	@Autowired
	private FixedAreaDao fdao;
	@Autowired
	private WorkBillDao wdao;
	@Autowired
	private SubAreaDao sdao;
	
	@Autowired
	private CustomerService crmProxy;
	@Override
	public void save(Order order) {
		Area recArea = order.getRecArea();
		recArea = adao.findByProvinceAndCityAndDistrict(recArea.getProvince(),recArea.getCity(),recArea.getDistrict());
		Area sendArea = order.getSendArea();
		sendArea = adao.findByProvinceAndCityAndDistrict(sendArea.getProvince(),sendArea.getCity(),sendArea.getDistrict());
//		select * from area where province=? and city=? and district=?
		order.setRecArea(recArea);
		order.setSendArea(sendArea);
		order = dao.save(order);
		
//		保存工单
//		策略一：基于CRM地址库完全匹配实现自动分单
		String sendAddress = order.getSendAddress();//寄件人的详细地址
		String fixedAreaId = crmProxy.findByAddress(sendAddress);
		if(fixedAreaId!=null){
			FixedArea fixedArea = fdao.findOne(fixedAreaId);
			Set<Courier> couriers = fixedArea.getCouriers();
//			1、随机分配
//			2、GPS系统
//			3、上下班时间
			for (Courier courier : couriers) {
//				模拟获取到了一个快递员
//				String smsNumber = SmsUtils.sendSmsByWebService(courier.getTelephone(), "您有一个客户需要寄件，取件地址是，"+sendAddress+",客户的联系电话是："+order.getSendMobile());
				WorkBill workbill = new WorkBill();
				workbill.setAttachbilltimes(0);  // 追单次数
				workbill.setBuildtime(new Date()); //工单生成时间
				workbill.setCourier(courier);
				workbill.setOrder(order);
				workbill.setPickstate("新单");
				workbill.setRemark(order.getRemark());
				workbill.setSmsNumber("121122121");
				workbill.setType("新"); //工单类型 新,追,销
				wdao.save(workbill);
				return;
			}
			
		}else{
//			基于分区关键字匹配实现自动分单
//			判断发件人的详细地址和每个分区关键字或辅助关键字是否有包含关系，如果有获取到了此分区，根据分区获取定区
			List<SubArea> subAreas = sdao.findAll();
			for (SubArea subArea : subAreas) {
				if(sendAddress.contains(subArea.getKeyWords())||sendAddress.contains(subArea.getAssistKeyWords())){
					FixedArea fixedArea = subArea.getFixedArea();
					Set<Courier> couriers = fixedArea.getCouriers();
//					1、随机分配
//					2、GPS系统
//					3、上下班时间
					for (Courier courier : couriers) {
//						模拟获取到了一个快递员
//						String smsNumber = SmsUtils.sendSmsByWebService(courier.getTelephone(), "您有一个客户需要寄件，取件地址是，"+sendAddress+",客户的联系电话是："+order.getSendMobile());
						WorkBill workbill = new WorkBill();
						workbill.setAttachbilltimes(0);  // 追单次数
						workbill.setBuildtime(new Date()); //工单生成时间
						workbill.setCourier(courier);
						workbill.setOrder(order);
						workbill.setPickstate("新单");
						workbill.setRemark(order.getRemark());
						workbill.setSmsNumber("121122121");
						workbill.setType("新"); //工单类型 新,追,销
						wdao.save(workbill);
						return;
					}
				}
			}
			
		}
		
	}

}
