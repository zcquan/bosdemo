package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {
//	select r.* from t_user u ,t_user_role ur, t_role r where u.c_id=ur.c_user_id and ur.c_role_id=r.c_id 	 and u.c_id=?
	
//	@Query(value="select r.* from t_user u ,t_user_role ur, t_role r where u.c_id=ur.c_user_id and ur.c_role_id=r.c_id 	 and u.c_id=?",nativeQuery=true)
	@Query(value="select r from Role r join r.users u where u.id=?")
	List<Role> findByUser(Integer id);

}
