package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

@Service
@Transactional
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardDao dao;
	
	@Override
	public void save(Standard standard) {
		dao.save(standard);
	}

	@Override
	public void updateName(String string, int i) {
		dao.updateName(string, i);		
	}

	@Override
	public Page<Standard> findAll(Pageable pageAble) {
		return dao.findAll(pageAble);
	}

	@Override
	public List findAll() {
		return dao.findAll();
	}


}
