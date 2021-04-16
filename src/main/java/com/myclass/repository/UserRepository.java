package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.dto.UserDto;
import com.myclass.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT new com.myclass.dto.UserDto(u.id, u.email, u.fullname, u.password,u.avatar, u.phone, u.address, " + 
			"u.roleId, r.name, r.desc) FROM User u JOIN Role r ON u.roleId = r.id")
	public List<UserDto> findAllWithDesc();
	
//	@Query("SELECT new com.myclass.dto.UserDto(u.id, u.email, u.fullname, u.password,u.avatar, u.phone, u.address, " + 
//			"u.roleId, r.name, r.desc) FROM User u JOIN Role r ON u.roleId = r.id WHERE u.id = :id")
//	public List<UserDto> findAllWithDesc(@Param("id") int id);
//	public UserDto findById();
//	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User findByEmail(@Param("email") String email);
	
	/*Hoặc đơn giản
	 * public User findByEmail(String email);
	 */
	
}
