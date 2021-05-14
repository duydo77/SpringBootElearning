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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myclass.dto.ImageDto;
import com.myclass.dto.LoginDto;
import com.myclass.dto.PasswordDto;
import com.myclass.dto.UpdateProfileReponseDto;
import com.myclass.dto.UserDto;
import com.myclass.dto.UserUpdateDto;
import com.myclass.entity.User;
import com.myclass.repository.RoleRepository;
import com.myclass.repository.UserRepository;
import com.myclass.service.AuthService;
import com.myclass.service.UserService;

@Service
@Scope("prototype")
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AuthService authService;

	UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthService authService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.authService = authService;
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
//		return dtos;
//	}

	@Override
	public List<UserDto> findAll() {
		return userRepository.findAllWithDesc();
	}
	
	@Override
	public List<User> findAllByCourseId(int courseId) {
		userRepository.findAllByCourseId(courseId).forEach(e -> {
			System.out.println(e.getId());
			});
		return userRepository.findAllByCourseId(courseId);
	}

	@Override
	public UserDto findById(int id) {
		User entity = userRepository.getOne(id);
		return new UserDto(entity.getId(), entity.getEmail(), entity.getFullname(), entity.getPassword(),
				entity.getAvatar(), entity.getPhone(), entity.getAddress(), entity.getRoleId());
	}

	@Override
	public UpdateProfileReponseDto update(UserUpdateDto dto) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails) principal).getUsername();
		User entity = userRepository.findByEmail(email);
		if (entity != null) {
			// if (!dto.getPassword().isEmpty())
			// entity.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
			entity.setEmail(dto.getEmail());
			entity.setFullname(dto.getFullname());
			entity.setPhone(dto.getPhone());
			entity.setAddress(dto.getAddress());
			// if((roleRepository.getOne(id).getDesc()).equals("ROLE_ADMIN")) {
			// entity.setRoleId(dto.getRoleId());
			// }
			userRepository.save(entity);
			String token = null;
			if (!email.equals(dto.getEmail())) {
				token = authService.login(new LoginDto(entity.getEmail(), entity.getPassword()));
			}
			return new UpdateProfileReponseDto("Cập nhật thành công", token);
		}
		return new UpdateProfileReponseDto("Cập nhật thất bại", null);
	}

	@Override
	public String update(int id, UserDto dto) {
		UserDto admin = this.getProfile();
		if (admin.getRoleDesc() != "ROLE_ADMIN") {
			User entity = userRepository.getOne(id);

			if (!dto.getPassword().isBlank())
				entity.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));

			entity.setEmail(dto.getEmail());
			entity.setFullname(dto.getFullname());
			entity.setPhone(dto.getPhone());
			entity.setAddress(dto.getAddress());
			entity.setRoleId(dto.getRoleId());
			userRepository.save(entity);

			return "SUCCESSED";
		}
		return "PERMISSION_DINIED";
	}

	@Override
	public UpdateProfileReponseDto add(UserDto dto) {
		/*
		 * 0 thêm mới thành công
		 * 1 email hoặc mật khẩu chưa nhập
		 * 2 tài khoản đã tồn tại
		 */
		if (userRepository.findByEmail(dto.getEmail()) == null) {

			if (dto.getPassword().isBlank() || dto.getEmail().isBlank()) {
				return new UpdateProfileReponseDto("1", "");
			}

			String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

			User entity = new User(
					dto.getEmail(), 
					dto.getFullname(), 
					hashed, "default_avatar.png", 
					dto.getPhone(),
					dto.getAddress(), 
					roleRepository.findByName("ROLE_STUDENT").getId());

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!(principal == "anonymousUser")) {
				String role = ((UserDetails) principal).getAuthorities().toArray()[0].toString();
				if (role.equals("ROLE_ADMIN")) {
					entity.setRoleId(dto.getRoleId());
				}
			};
			
			userRepository.save(entity);
			return new UpdateProfileReponseDto("0", "");
		}

		return new UpdateProfileReponseDto("2", "");
	}

	@Override
	public void addTeacher(UserDto dto) {

		if (userRepository.findByEmail(dto.getEmail()) == null) {

			if (dto.getPassword().isEmpty())
				return;
			String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
			User entity = new User(dto.getEmail(), dto.getFullname(), hashed, dto.getAvatar(), dto.getPhone(),
					dto.getAddress(), 2);
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
		String email = ((UserDetails) principal).getUsername();
		User user = userRepository.findByEmail(email);
		String avatar = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/getAvatar/")
				.path(user.getAvatar()).toUriString();
		return new UserDto(user.getId(), user.getEmail(), user.getFullname(), avatar, user.getPhone(),
				user.getAddress(), user.getRoleId());
	}

	@Override
	public UserDto getProfile2() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails) principal).getUsername();
		User user = userRepository.findByEmail(email);
		return new UserDto(user.getId(), user.getEmail(), user.getFullname(), user.getPhone(), user.getAddress(),
				user.getRoleId());
	}

	@Override
	public UpdateProfileReponseDto changePassword(PasswordDto passwordDto) {
		/*
		 * 0 thay đổi thành công
		 * 1 mật khẩu cũ khôgn đúng
		 * 2 tài khoản khôgn tồn tại
		 * 3 mật khẩu mới và cũ trùng nhau
		 */
		
		if (passwordDto.getNewPassword().equals(passwordDto.getOldPassword())) {
			return new UpdateProfileReponseDto("3", ""); // Mật khẩu mới và mật khẩu cũ không được trùng nhau
		}

		User user = userRepository.findByEmail(passwordDto.getEmail());
		if (user == null)
			return new UpdateProfileReponseDto("2", ""); // Tài khoản không tồn tại
		if (!BCrypt.checkpw(passwordDto.getOldPassword(), user.getPassword())) {
			System.out.println("userservice");
			return new UpdateProfileReponseDto("1", ""); // Mật khẩu không đúng
		}

		String hased = BCrypt.hashpw(passwordDto.getNewPassword(), BCrypt.gensalt());
		user.setPassword(hased);
		userRepository.save(user);
		System.out.println("thanh cong");
		return new UpdateProfileReponseDto("0",
				authService.login(new LoginDto(user.getEmail(), passwordDto.getNewPassword())));
	}

	@Override
	public String updateAvatar(MultipartFile file) throws IOException {
		System.out.println(file);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails) principal).getUsername();
		User user = userRepository.findByEmail(email);
		String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String imageName = String.valueOf(user.getId()) + "_avatar." + ext;
		Path targetLocation = Paths.get("src/main/resources/static/avatar");
		targetLocation = targetLocation.resolve(imageName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/getAvatar/")
				.path(imageName).toUriString();
		user.setAvatar(imageName);
		userRepository.save(user);
		return fileDownloadUri;
	}

	@Override
	public ImageDto getAvatar(String imageName) throws IOException {
		// Object principal =
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

	@Override
	public String changePassword2(PasswordDto passwordDto) {
		if (passwordDto.getNewPassword().equals(passwordDto.getOldPassword())) {
			return "Mật khẩu mới và cũ không được trùng nhau!";
		}
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails) principal).getUsername();
		User user = userRepository.findByEmail(email);
		if (user == null) {
			return null;
		} else {

			if (!BCrypt.checkpw(passwordDto.getOldPassword(), user.getPassword())) {
				return "Mật khẩu cũ không đúng!";
			}
			String hased = BCrypt.hashpw(passwordDto.getNewPassword(), BCrypt.gensalt());
			user.setPassword(hased);
			User a = userRepository.save(user);
			System.out.println(a.toString());
			return null;
		}

	}

	@Override
	public void update(UserDto dto) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails) principal).getUsername();
		User entity = userRepository.findByEmail(email);
		if (entity == null)
			return;
		entity.setId(dto.getId());
		entity.setEmail(dto.getEmail());
		entity.setFullname(dto.getFullname());
		entity.setPhone(dto.getPhone());
		entity.setAddress(dto.getAddress());
		userRepository.save(entity);
	}
	
	public String getRoleByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user.getRole().getName();
	}
}
