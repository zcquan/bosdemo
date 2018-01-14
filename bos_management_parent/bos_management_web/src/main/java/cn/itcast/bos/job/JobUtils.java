package cn.itcast.bos.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.service.take_delivery.WorkBillService;
import cn.itcast.bos.utils.MailUtils;

public class JobUtils {
	
	@Autowired
	private WorkBillService service;
	
	public void sendWorkBillInfo(){
//		查询当月工单数据
		List<WorkBill> list = service.getCurrentMonthWorkBill();
		String content ="当月工单信息是";
		for (WorkBill workBill : list) {
			content+=workBill.toString();
		}
		System.out.println(content);
		MailUtils.sendMail("当月工单信息", content, "shiyilong_test@126.com");
//		把当月工单数据转成字符串（邮件的内容）
		
	}

}
