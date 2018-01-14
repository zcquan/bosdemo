package cn.itcast.rs;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import cn.itcast.crm.service.ws.Customer;

public class RSTest {

	public static void main(String[] args) {
//		String info = WebClient.create("http://localhost:8081/crm/ws/").
//				path("/customer/findByFixedAreaIdIsNull").accept(MediaType.APPLICATION_JSON).get(String.class);
//			System.out.println(info);
		 List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8081/crm/ws/").
			path("/customer/findByFixedAreaIdIsNull").accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		 System.out.println(list);
	}

}
