package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.dto.CourseDto;
import com.myclass.entity.Course;
import com.myclass.entity.User;
 
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>, CourseRepositoryCustom {

	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.hourCount, c.viewCount, c.price, c.discount,"
			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
			+ " ca.icon) FROM Course c JOIN Category ca ON c.cateId = ca.id")
//	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.lectureCount, c.hourCount, c.viewCount, c.price, c.discount,"
//			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
//			+ " ca.icon, uc.id.userId) "
//			+ "FROM Course c JOIN Category ca JOIN UserCourse uc "
//			+ "ON c.cateId=ca.id AND uc.id.courseId=c.id AND uc.roleId=2")
	public List<CourseDto> findAllWithCate();
	
	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.hourCount, c.viewCount, c.price, c.discount,"
			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name, ca.icon) "
			+ "FROM Course 		c "
			+ "JOIN Category 	ca ON c.cateId = ca.id "
			+ "JOIN UserCourse 	uc ON uc.id.courseId = c.id "
			+ "JOIN User 		u ON uc.id.userId = u.id "
			+ "AND 	u.id = :userid1")
	public List<CourseDto> findAllOfTeacher(@Param("userid1") Integer userId);
	
	// test
	@Query("SELECT c "
			+ "FROM Course c "
			+ "INNER JOIN FETCH c.targets t "
			+ "INNER JOIN UserCourse uc ON uc.id.courseId = c.id "
			+ "INNER JOIN User u ON uc.id.userId = u.id "
			+ "AND 	u.id = :userid "
			+ "AND 	c.id = :courseid")
	public Course test(@Param("userid") Integer userId, @Param("courseid") Integer courseId);
	
	@Query("SELECT u FROM UserCourse uc JOIN User u ON uc.id.courseId=:courseId AND uc.id.userId=u.id AND uc.roleId=2")
	public User findTeacher(@Param("courseId") int courseId);
	
	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.lectureCount, c.hourCount, c.viewCount, c.price, c.discount,"
			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
			+ " ca.icon) FROM Course c JOIN Category ca ON c.id=:id AND c.cateId = ca.id")
	public CourseDto findById(@Param("id") int id);
	
	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.hourCount, c.viewCount, c.price, c.discount,"
			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
			+ " ca.icon) FROM Course c JOIN Category ca ON c.discount != 0 AND c.cateId = ca.id order by c.discount DESC")
	public List<CourseDto> findPromotion();
	
	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.hourCount, c.viewCount, c.price, c.discount,"
			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
			+ " ca.icon) FROM Course c JOIN Category ca ON c.discount = 0 AND c.cateId = ca.id")
	public List<CourseDto> findNormal();
//	@Query("SELECT new com.myclass.dto.CourseDto "
//			+ "FROM Course c "
//			+ "JOIN FETCH Target t ON t.course_id = c.id "
//			+ "JOIN FETCH Video v ON v.course_id = c.id ")
//	public CourseDto findDetailById(@Param("id") int id);
	
	@Query("SELECT c "
			+ "FROM Course c "
//			+ "LEFT JOIN FETCH Target t ON t.courseId = c.id "
//			+ "LEFT JOIN FETCH Video v ON v.courseId = c.id "
			+ "JOIN UserCourse uc ON uc.id.courseId = c.id "
			+ "JOIN User u ON uc.id.userId = u.id "	
			+ "WHERE u.id = :userId "
			+ "AND c.id = :courseId ")
//			+ "WHERE c.id = :courseId "
//			+ "AND :userId != 0")
	public Course findDetailById(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

	@Query("SELECT new com.myclass.dto.CourseDto("
			+ "c.id, c.title, c.image, c.lectureCount, c.hourCount, c.viewCount, c.price, c.discount, "
			+ "c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, "
			+ "uc.id.userId, u.fullname) "
			+ "FROM Course c "
			+ "JOIN UserCourse uc ON (uc.id.courseId = c.id AND uc.roleId=2) "
			+ "JOIN User u On u.id = uc.id.userId "
			+ "WHERE c.cateId = :cateId")
	public List<CourseDto> findbyCateId(@Param("cateId") int cateId);
	
	@Query("SELECT new com.myclass.dto.CourseDto( "
			+ "c.id, c.title, c.image, c.lectureCount, c.hourCount, c.viewCount, c.price, c.discount, "
			+ "c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name, ca.icon, u.id, u.fullname ) "
			+ "FROM Course c "
			+ "JOIN UserCourse uc ON uc.id.courseId = c.id AND uc.roleId = 2 "
			+ "JOIN User u ON u.id = uc.id.userId "
			+ "JOIN Category ca ON c.cateId = ca.id "
			+ "WHERE (c.title LIKE :key "
			+ "OR c.content LIKE :key "
			+ "OR u.fullname LIKE :key "
			+ ")")
	public List<CourseDto> search(@Param("key") String key);
	
	@Query("SELECT new com.myclass.dto.CourseDto( "
			+ "c.id, c.title, c.image, c.lectureCount, c.hourCount, c.viewCount, c.price, c.discount, "
			+ "c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name, ca.icon, u.id, u.fullname ) "
			+ "FROM User u "
			+ "JOIN UserCourse uc ON uc.id.userId = u.id AND uc.roleId = 2 AND (u.fullname LIKE :teacherName)"
			+ "JOIN Course c ON c.id = uc.id.courseId "
			+ "JOIN Category ca ON ca.id = c.cateId")
	public List<CourseDto> searchByTeacherName(@Param("teacherName") String teacherName);
}
