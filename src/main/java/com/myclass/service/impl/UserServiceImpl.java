package com.myclass.service.impl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.myclass.dto.PasswordDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;
import com.myclass.service.UserService;

@Service
@Scope("prototype")
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;

	UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

//	@Override
//	public List<UserDto> findAll() {
//		List<User> entities = userRepository.findAll();
//		List<UserDto> dtos = new ArrayList<UserDto>();
//
//		for (User entity : entities) {
//			dtos.add(new UserDto(entity.getId(), entity.getEmail(), entity.getFullname(), entity.getPassword(),
//					entity.getAvatar(), entity.getPhone(), entity.getAddress(), entity.getRoleId()));
//		}
//
//		return dtos;
//	}
//	
	@Override
	public List<UserDto> findAll() {
		return userRepository.findAllWithDesc();
	}

	@Override
	public UserDto findById(int id) {
		User entity = userRepository.getOne(id);
		return new UserDto(
				entity.getId(), 
				entity.getEmail(), 
				entity.getFullname(), 
				entity.getPassword(),
				entity.getAvatar(), 
				entity.getPhone(), 
				entity.getAddress(), 
				entity.getRoleId() );
	}

	@Override
	public void update(int id, UserDto dto) {
		if (userRepository.existsById(id)) {
			User entity = userRepository.getOne(dto.getId());
			if (entity == null)
				return;
			if (!dto.getPassword().isEmpty())
				entity.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
			entity.setId(dto.getId());
			entity.setEmail(dto.getEmail());
			entity.setFullname(dto.getFullname());
			entity.setAvatar(dto.getAvatar());
			entity.setPhone(dto.getPhone());
			entity.setAddress(dto.getAddress());
			entity.setRoleId(dto.getRoleId());
			userRepository.save(entity);
		}

	}

//	@Override
//	public void add(UserDto dto) {
//		User entity = new User(dto.getEmail(), dto.getFullname(), dto.getPassword(),
//				dto.getAvatar(), dto.getPhone(), dto.getAddress(), dto.getRoleId());
//		userRepository.save(entity);
//	}
	
	@Override
	public void add(UserDto dto) {
		
		if (userRepository.findByEmail(dto.getEmail()) == null) {

			if (dto.getPassword().isEmpty())
				return;
			String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
			User entity = new User(dto.getEmail(), dto.getFullname(), hashed, dto.getAvatar(), dto.getPhone(),
					dto.getAddress(), dto.getRoleId());
			userRepository.save(entity);
		}
	}

	@Override
	public void delete(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDto getProfile() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		UserDetails userDetails = (UserDetails)principal;
//		String email = userDetails.getUsername();
		String email = ((UserDetails)principal).getUsername();
		User user = userRepository.findByEmail(email);
		return new UserDto(user.getId(), user.getEmail(), user.getFullname(), user.getRoleId());
	}

	@Override
	public String changePassword(PasswordDto passwordDto) {
		if (passwordDto.getNewPassword().equals(passwordDto.getOldPassword())){
			return "Mật khẩu mới và cũ không được trùng nhau!";
		}
		
		User user = userRepository.findByEmail(passwordDto.getEmail());
		if (!BCrypt.checkpw(passwordDto.getOldPassword(), user.getPassword())) {
			return "Mật khẩu cũ không đúng!";
		}
		
		String hased = BCrypt.hashpw(passwordDto.getNewPassword(), BCrypt.gensalt());
		user.setPassword(hased);
		userRepository.save(user);
		return null;
	}
}
