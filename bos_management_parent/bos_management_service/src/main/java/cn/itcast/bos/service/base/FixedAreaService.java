package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.FixedArea;

public interface FixedAreaService {

	public void save(FixedArea model);

	public Page<FixedArea> findAll(Pageable pageable);

	public void save(FixedArea model, String subAreaIds);

	public void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId);
}
