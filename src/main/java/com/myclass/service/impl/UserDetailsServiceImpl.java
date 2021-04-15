package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myclass.dto.UserDetailsDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;

@Service
@Transactional(rollbackOn = Exception.class )
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Gọi hàm kiểm tra email
		User user = userRepository.findByEmail(email);
		if (user == null) throw new UsernameNotFoundException("Email không tồn tại");
		// Trả về đối tượng có kiểu dữ liệu UserDetails
		String roleName = user.getRole().getName();
		List<GrantedAuthority> authotiries = new ArrayList<GrantedAuthority>();
		authotiries.add(new SimpleGrantedAuthority(roleName));
		UserDetails userDetails =  new UserDetailsDto(user.getId(), user.getRoleId(), user.getEmail(), user.getPassword(), authotiries);
		return userDetails;
	}
	
}
