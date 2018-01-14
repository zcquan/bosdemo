package cn.itcast.bos.service.ws;

import javax.jws.WebService;

import cn.itcast.bos.domain.take_delivery.Order;

@WebService
public interface OrderService {
	public void save(Order order);
}
