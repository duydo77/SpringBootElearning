package com.myclass.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myclass.dto.ImageDto;
import com.myclass.dto.PasswordDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.repository.RoleRepository;
import com.myclass.repository.UserRepository;
import com.myclass.service.UserService;

import javassist.compiler.ast.Pair;

@Service
@Scope("prototype")
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;

	UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
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
	public String update(UserDto dto) {
//		User entity = userRepository.findByEmail(dto.getEmail());
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails)principal).getUsername();
		User entity = userRepository.findByEmail(email);
		if (entity != null) {
			//if (!dto.getPassword().isEmpty())
			//	entity.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
			entity.setEmail(dto.getEmail());
			entity.setFullname(dto.getFullname());
			entity.setPhone(dto.getPhone());
			entity.setAddress(dto.getAddress());
//			if((roleRepository.getOne(id).getDesc()).equals("ROLE_ADMIN")) {
//				entity.setRoleId(dto.getRoleId());				
//			}
			userRepository.save(entity);
			return "Cập nhật thành công";
		}
		return "Tài khoản không tồn tại";
	}
	
	
	public String update(int id, UserDto dto) {
		return null;
	}
	
	@Override
	public void add(UserDto dto) {

		if (userRepository.findByEmail(dto.getEmail()) == null) {

			if (dto.getPassword().isEmpty())
				return;
			String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
			User entity = new User(dto.getEmail(), dto.getFullname(), hashed, "default_avatar.png", dto.getPhone(),
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
		// UserDetails userDetails = (UserDetails)principal;
		// String email = userDetails.getUsername();
		String email = ((UserDetails)principal).getUsername();
		User user = userRepository.findByEmail(email);
		String avatar = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/user/getAvatar/")
                .path(user.getAvatar())
                .toUriString();
		return new UserDto(
					user.getId(), 
					user.getEmail(), 
					user.getFullname(), 
					avatar, 
					user.getPhone(), 
					user.getAddress(), 
					user.getRoleId());
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
	
	@Override
	public String updateAvatar(MultipartFile file) throws IOException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails)principal).getUsername();
		User user = userRepository.findByEmail(email);
		String ext =  StringUtils.getFilenameExtension(file.getOriginalFilename());
		String imageName = String.valueOf(user.getId()) + "_avatar." + ext;
		Path targetLocation = Paths.get("src/main/resources/static/avatar");
		targetLocation = targetLocation.resolve(imageName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/user/getAvatar/")
                .path(imageName)
                .toUriString();
		user.setAvatar(imageName);
		userRepository.save(user);
		return fileDownloadUri;
	}
	
	@Override
	public ImageDto getAvatar(String imageName) throws IOException {
		// Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// System.out.println(((UserDetails)principal).getUsername());
		// String email = ((UserDetails)principal).getUsername();
		// String avatar = userRepository.findByEmail(email).getAvatar();
		Path filename = Paths.get("src/main/resources/static/avatar", imageName);
		System.out.println("usersserviceimpl " + filename.toString());
		byte[] buffer = Files.readAllBytes(filename);
		ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
		ImageDto dto = new ImageDto(buffer.length, byteArrayResource);
		return dto;
	}

}
