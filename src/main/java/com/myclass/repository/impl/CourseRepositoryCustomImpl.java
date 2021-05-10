package com.myclass.repository.impl;

import java.util.List;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.myclass.dto.CourseDto;
import com.myclass.dto.SearchDto;
import com.myclass.repository.CourseRepositoryCustom;

@Transactional(rollbackFor = Exception.class)
public class CourseRepositoryCustomImpl implements CourseRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

//	public CourseRepositoryCustomImpl(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

	@Override
	public List<CourseDto> searchCourse(SearchDto dto) {
		
		String hql = "SELECT new com.myclass.dto.CourseDto("
										+ "c.id, "
										+ "c.title, "
										+ "c.image, "
										+ "c.lectureCount, "
										+ "c.hourCount, "
										+ "c.viewCount, "
										+ "c.price, "
										+ "c.discount, "
										+ "c.promotionPrice, "
										+ "c.desc, "
										+ "c.content, "
										+ "c.cateId, "
										+ "c.lastUpdate, "
										+ "ca.name,"
										+ "ca.icon,"
										+ "u.id,"
										+ "u.fullname) "
				     + "FROM Course c "
				     + "JOIN Category ca ON c.cateId = ca.id "
				     + "JOIN UserCourse uc ON (uc.id.courseId = c.id AND uc.roleId = 2) "
				     + "JOIN User u ON u.id = uc.id.userId ";
		
		String filter = "WHERE (c.title LIKE '%" + dto.getKey() + "%' "
					+ "OR c.content LIKE '%" + dto.getKey() + "%' "
					+ "OR u.fullname LIKE '%" + dto.getKey() + "%' "
					+ ") ";

		String[] cateFilter =  dto.getCategory();
		for (int i = 0; i < cateFilter.length; i++) {
			if (i == 0) filter += "AND ( ";
			filter += "ca.name = '" + cateFilter[i] + "' ";
			if (i != cateFilter.length - 1) {
				filter += "OR ";
			}
			else {
				filter += ") ";
			}
		}
		
		String[] duraFilter =  dto.getDuration();
		for (int i = 0; i < duraFilter.length; i++) {
			if (i == 0) filter += "AND ( ";
			switch (duraFilter[i]) {
			case "1": // 0-3
				filter += "c.hourCount <= 3600 ";
				break;
			case "2": // 3-6
				filter += "c.hourCount > 3600 AND c.hourCount <= 10800 ";
				break;
			case "3": // 6-17
				filter += "c.hourCount > 10800 AND c.hourCount <= 61200 ";
				break;
			case "4": // 17+
				filter += "c.hourCount > 61200 ";
				break;
			default:
				break;
			} 
			
			if (i != duraFilter.length - 1) {
				filter += "OR ";
			}
			else {
				filter += ") ";
			}
		}
		
		String[] priceFilter =  dto.getPrice();
		for (int i = 0; i < priceFilter.length; i++) {
			if (i == 0) filter += "AND ( ";
			switch (priceFilter[i]) {
			case "1": // free
				filter += "c.promotionPrice = 0 ";
				break;
			case "2": // paid
				filter += "c.promotionPrice != 0 ";
				break;
			case "3": // promotion
				filter += "c.discount != 0 ";
				break;
			default:
				break;
			} 
			
			if (i != priceFilter.length - 1) {
				filter += "OR ";
			}
			else {
				filter += ") ";
			}
		}
		
		hql = hql + filter;
		
		System.out.println("++++++++++++++++= " + hql);
		
		Query query = entityManager.createQuery(hql); 
		List<CourseDto> dtos = query.getResultList();
		return dtos;
	}
}
