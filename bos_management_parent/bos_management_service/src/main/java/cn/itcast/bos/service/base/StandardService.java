package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Standard;

public interface StandardService {
	
	public void save(Standard standard);

	public void updateName(String string, int i);

	public Page<Standard> findAll(Pageable pageAble);

	public List findAll();

}
