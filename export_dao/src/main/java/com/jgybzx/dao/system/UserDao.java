package com.jgybzx.dao.system;

import com.jgybzx.domain.system.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;


public interface UserDao {

	/**
	 * 根据企业id查询全部
	 * @param companyId
	 * @return
	 */
	List<User> findAll(String companyId);

	/**
	 * 根据id查询
	 * @param userId
	 * @return
	 */
    User findById(String userId);

	/**
	 * 根据id删除
	 * @param userId
	 * @return
	 */
	int delete(String userId);

	/**
	 * 保存
	 * @param user
	 * @return
	 */
	int save(User user);

	/**
	 * 更新
	 * @param user
	 * @return
	 */
	int update(User user);

	/**
	 * 根据 id 删除pe_role_user 表中的信息
	 * @param userId
	 */
    void deleteRoleById(String userId);

	/**
	 * 向 pe_role_user 表中循环插入数据
	 * @param userId
	 * @param roleId
	 */
	void saveUserRole(@Param("userId") String userId,
					  @Param("roleId") String roleId);

	/**
	 * 根据email 查询用户
	 * @param email
	 * @return
	 */
    User login( String email );
}