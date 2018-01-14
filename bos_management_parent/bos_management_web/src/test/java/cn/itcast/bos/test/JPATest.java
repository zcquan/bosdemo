package cn.itcast.bos.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class JPATest {
   
	@Autowired
	private StandardDao dao;
	
	@Test
	public void testSave(){
		Standard standard = new Standard();
		standard.setMaxLength(50);
		standard.setMaxWeight(50);
		standard.setMinLength(11);
		standard.setMinWeight(11);
		standard.setName("标准11-50");
		standard.setOperatingCompany("顺义分公司");
		standard.setOperatingTime(new Date());
		standard.setOperator("admin");
		dao.save(standard);
	}
	
	
	@Test
	public void testFindAll(){
		List<Standard> list = dao.findAll();
		System.out.println(list);
		
	}
	
	@Test
	public void testFindByName(){
		List<Standard> list = dao.findByName("标准1-10");
		System.out.println(list);
	}
	
	@Test
	public void testFindByOperator(){
		List<Standard> list = dao.findByOperator("admin");
		System.out.println(list);
	}
	@Test
	public void testFindByOperatorLike(){
		List<Standard> list = dao.findByOperatorLike("%admin%");
		System.out.println(list);
	}
	
	@Test
	public void testFindByOperatorAndName(){
		List<Standard> list = dao.findByOperatorAndName("admin","标准1-10");
		System.out.println(list);
	}
	@Test
	public void testFindByOperatorLikeOrNameLike(){
		List<Standard> list = dao.findByOperatorLikeOrNameLike("%admin%","%标准%");
		System.out.println(list);
	}
	@Test
	public void testFindXXXX(){
		List<Standard> list = dao.findByXXXX("标准1-10");
		System.out.println(list);
	}
	
	@Autowired
	private StandardService service;
	
	
	@Test
	public void testUpdateName(){
		service.updateName("1-10",1);
	}
}
