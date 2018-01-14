package cn.itcast.bos.service.take_delivery;

import java.util.List;

import cn.itcast.bos.domain.take_delivery.WorkBill;

public interface WorkBillService {

	public List<WorkBill> getCurrentMonthWorkBill();
}
