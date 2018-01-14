package cn.itcast.bos.service.base;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Area;

public interface AreaService {

	void importXls(File myfile);

	Page<Area> findAll(Pageable pageable);

	List<Area> findAll();

	List<Area> findAll(String string, String string2);

}
