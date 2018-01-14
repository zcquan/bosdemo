package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaDao extends JpaRepository<SubArea, String> {

//	select   a.c_district, count(*) from t_area a , t_sub_area sa where a.c_id=sa.c_area_id  
//			 group by   a.c_district
	@Query("select a.district,count(*)  from Area a join a.subareas sa group by a.district ")
	List showChart();

}
