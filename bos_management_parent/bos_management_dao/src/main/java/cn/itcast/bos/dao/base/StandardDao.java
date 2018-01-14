package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Standard;

/**
 * 没有实现类
 * 也不需要写注解
 * @author syl
 *
 */
public interface StandardDao extends JpaRepository<Standard, Integer> {

	List<Standard> findByName(String string);

	List<Standard> findByOperator(String string);

	List<Standard> findByOperatorLike(String string);

	List<Standard> findByOperatorAndName(String string, String string2);

	List<Standard> findByOperatorLikeOrNameLike(String string, String string2);

//	@Query("from Standard where name=?")  //jpql
	@Query(value="select * from T_STANDARD where C_NAME=?",nativeQuery=true)  //sql
	List<Standard> findByXXXX(String string);

	@Query(value="update Standard set name=? where id=?")
	@Modifying   //如果涉及到数据修改需要加 modifying注解
	void updateName(String string, int i);

}
