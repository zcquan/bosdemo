package cn.itcast.bos.service.take_delivery.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.WorkBillDao;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.service.take_delivery.WorkBillService;

@Service
@Transactional
public class WorkBillServiceImpl implements WorkBillService {

	@Autowired
	private WorkBillDao dao;
	@Override
	public List<WorkBill> getCurrentMonthWorkBill() {
		return dao.getCurrentMonthWorkBill();
	}

}
