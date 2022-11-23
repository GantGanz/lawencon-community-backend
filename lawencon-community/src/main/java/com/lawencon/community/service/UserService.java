package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.RoleConstant;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.user.UserChangePasswordReq;
import com.lawencon.community.dto.user.UserData;
import com.lawencon.community.dto.user.UserInsertReq;
import com.lawencon.community.dto.user.UserRes;
import com.lawencon.community.dto.user.UserUpdateReq;
import com.lawencon.community.dto.user.UsersRes;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;
import com.lawencon.community.util.GenerateCodeUtil;
import com.lawencon.security.principal.PrincipalService;
import com.lawencon.util.MailUtil;

@Service
public class UserService extends BaseCoreService implements UserDetailsService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private IndustryDao industryDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private MailUtil mailUtil;

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		final Optional<User> userOptional = userDao.getByEmail(email);

		if (userOptional.isPresent()) {
			return new org.springframework.security.core.userdetails.User(email, userOptional.get().getPass(),
					new ArrayList<>());
		}
		throw new UsernameNotFoundException("Email or Password is Incorrect");
	}

	public Optional<User> getByEmail(final String email) {
		return userDao.getByEmail(email);
	}

	public InsertRes register(final UserInsertReq data) {
		valInsert(data);

		User userInsert = new User();
		userInsert.setFullname(data.getFullname());
		userInsert.setEmail(data.getEmail());

		final String hashPassword = passwordEncoder.encode(data.getPassword());
		userInsert.setPass(hashPassword);

		userInsert.setCompany(data.getCompany());
		userInsert.setIsPremium(false);

		final String roleId = roleDao.getByRoleCode(RoleConstant.MEMBER.getRoleCode());
		final Role role = roleDao.getById(Role.class, roleId);
		userInsert.setRole(role);

		final Industry industry = industryDao.getById(Industry.class, data.getIndustryId());
		userInsert.setIndustry(industry);

		final Position position = positionDao.getById(Position.class, data.getPositionId());
		userInsert.setPosition(position);

		final File file = fileDao.getById(File.class, "d45027a8-a2e2-46a9-b2c2-4f4fd2310d7c");
		userInsert.setFile(file);

		try {
			begin();
			userInsert = userDao.saveNoLogin(userInsert, () -> userDao.getSystemId());
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Registration failed");
		}

		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(userInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("Registration Success");

		return insertRes;
	}

	public InsertRes registerAdmin(final UserInsertReq data) {
		valInsert(data);

		User userInsert = new User();
		userInsert.setFullname(data.getFullname());
		userInsert.setEmail(data.getEmail());

		final String plainPassword = GenerateCodeUtil.generateCode();
		final String hashPassword = passwordEncoder.encode(plainPassword);
		userInsert.setPass(hashPassword);

		userInsert.setCompany(data.getCompany());
		userInsert.setIsPremium(false);

		final String roleId = roleDao.getByRoleCode(RoleConstant.ADMIN.getRoleCode());
		final Role role = roleDao.getById(Role.class, roleId);
		userInsert.setRole(role);

		final Industry industry = industryDao.getById(Industry.class, data.getIndustryId());
		userInsert.setIndustry(industry);

		final Position position = positionDao.getById(Position.class, data.getPositionId());
		userInsert.setPosition(position);

		final File file = fileDao.getById(File.class, "d45027a8-a2e2-46a9-b2c2-4f4fd2310d7c");
		userInsert.setFile(file);

		try {
			begin();
			userInsert = userDao.save(userInsert);
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Registration failed");
		}

		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(userInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("Registration Success");

		final Map<String, Object> template = new HashMap<>();
		template.put("email", userInsert.getEmail());
		template.put("code", plainPassword);
		final String subject = "Zenith Admin Registration";
		try {
			mailUtil.sendMessageFreeMarker(userInsert.getEmail(), subject, template, "AdminRegistration.ftl");
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return insertRes;
	}

	public UpdateRes update(final UserUpdateReq data) {
		valUpdate(data);
		User userUpdate = userDao.getByIdAndDetach(User.class, data.getId());
		try {
			begin();

			userUpdate.setFullname(data.getFullname());
			userUpdate.setCompany(data.getCompany());

			if (data.getFileCodes() != null) {
				File fileUpdate = new File();
				fileUpdate.setFileCodes(data.getFileCodes());
				fileUpdate.setExtensions(data.getExtension());

				fileUpdate = fileDao.save(fileUpdate);
				userUpdate.setFile(fileUpdate);
			}

			final Position position = positionDao.getById(Position.class, data.getPositionId());
			userUpdate.setPosition(position);

			final Industry industry = industryDao.getById(Industry.class, data.getIndustryId());
			userUpdate.setIndustry(industry);

			userUpdate.setIsActive(data.getIsActive());
			userUpdate.setVersion(data.getVersion());

			userUpdate = userDao.saveAndFlush(userUpdate);

			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Update failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(userUpdate.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Update success");

		return res;
	}

	public UpdateRes changePassword(final UserChangePasswordReq data) {
		valChangePassword(data);
		User user = userDao.getByIdAndDetach(User.class, principalService.getAuthPrincipal());
		if (passwordEncoder.matches(data.getOldPassword(), user.getPass())) {
			final String hashPassword = passwordEncoder.encode(data.getNewPassword());
			user.setPass(hashPassword);
		} else {
			throw new RuntimeException("Incorect Old Password");
		}
		user.setVersion(data.getVersion());
		try {
			begin();
			user = userDao.saveAndFlush(user);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Failed to change password");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(user.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Password changed");

		return res;
	}
  
	public UsersRes getAll(final Integer offset, final Integer limit) {
		final List<User> users = userDao.getAll(offset, limit);
		final List<UserData> userDatas = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			final User user = users.get(i);
			final UserData userData = new UserData();
			userData.setId(user.getId());
			userData.setFullname(user.getFullname());
			userData.setEmail(user.getEmail());
			userData.setCompany(user.getCompany());
			userData.setRoleId(user.getRole().getId());
			userData.setRoleName(user.getRole().getRoleName());
			userData.setIndustryId(user.getIndustry().getId());
			userData.setIndustryName(user.getIndustry().getIndustryName());
			userData.setPositionId(user.getPosition().getId());
			userData.setPositionName(user.getPosition().getPositionName());
			userData.setFileId(user.getFile().getId());
			userData.setIsPremium(user.getIsPremium());
			userData.setVersion(user.getVersion());
			userData.setIsActive(user.getIsActive());

			userDatas.add(userData);
		}
		final UsersRes usersRes = new UsersRes();
		usersRes.setData(userDatas);

		return usersRes;
	}

	public UserRes getById(final String userId) {
		final User user = userDao.getById(User.class, userId);
		final UserData userData = new UserData();
		userData.setId(user.getId());
		userData.setFullname(user.getFullname());
		userData.setEmail(user.getEmail());
		userData.setCompany(user.getCompany());
		userData.setRoleId(user.getRole().getId());
		userData.setRoleName(user.getRole().getRoleName());
		userData.setIndustryId(user.getIndustry().getId());
		userData.setIndustryName(user.getIndustry().getIndustryName());
		userData.setPositionId(user.getPosition().getId());
		userData.setPositionName(user.getPosition().getPositionName());
		userData.setFileId(user.getFile().getId());
		userData.setIsPremium(user.getIsPremium());
		userData.setVersion(user.getVersion());
		userData.setIsActive(user.getIsActive());

		final UserRes userRes = new UserRes();
		userRes.setData(userData);

		return userRes;
	}

	public Long countAllMember() {
		return userDao.countAllUser(RoleConstant.MEMBER.getRoleCode());
	}

	public Long countAllAdmin() {
		return userDao.countAllUser(RoleConstant.ADMIN.getRoleCode());
	}

	public Long countAllPremium() {
		return userDao.countAllPremium();
	}
	
	public Long countAllUser() {
		return userDao.countAll(User.class);
	}

	private void valInsert(final UserInsertReq data) {
		valContentNotNull(data);
		valBkNotDuplicate(data);
		valIdFkNotNull(data);
		valFkFound(data);
	}

	private void valContentNotNull(final UserInsertReq data) {
		if (data.getFullname() == null) {
			throw new RuntimeException("Fullname cannot be empty");
		}
		if (data.getEmail() == null) {
			throw new RuntimeException("Email cannot be empty");
		}
		if (data.getPassword() == null) {
			throw new RuntimeException("Password cannot be empty");
		}
		if (data.getCompany() == null) {
			throw new RuntimeException("Company cannot be empty");
		}
	}

	private void valIdFkNotNull(final UserInsertReq data) {
		if (data.getPositionId() == null) {
			throw new RuntimeException("Position id cannot be empty");
		}
		if (data.getIndustryId() == null) {
			throw new RuntimeException("Industry id cannot be empty");
		}
	}

	private void valFkFound(final UserInsertReq data) {
		final Position position = positionDao.getByIdAndDetach(Position.class, data.getPositionId());
		if (position == null) {
			throw new RuntimeException("Position not found");
		}
		final Industry industry = industryDao.getByIdAndDetach(Industry.class, data.getIndustryId());
		if (industry == null) {
			throw new RuntimeException("Industry not found");
		}
	}

	private void valBkNotDuplicate(final UserInsertReq data) {
		final Optional<User> userOptional = userDao.getByEmail(data.getEmail());
		if (userOptional.isPresent()) {
			throw new RuntimeException("Email already used");
		}
	}

	private void valUpdate(final UserUpdateReq data) {
		valIdFkNotNull(data);
		valFkFound(data);
		valContentNotNull(data);
	}

	private void valContentNotNull(final UserUpdateReq data) {
		if (data.getFullname() == null) {
			throw new RuntimeException("Fullname cannot be empty");
		}
		if (data.getCompany() == null) {
			throw new RuntimeException("Company cannot be empty");
		}
		if (data.getIsActive() == null) {
			throw new RuntimeException("isActive cannot be empty");
		}
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}
	
	private void valFkFound(final UserUpdateReq data) {
		final Position position = positionDao.getByIdAndDetach(Position.class, data.getPositionId());
		if (position == null) {
			throw new RuntimeException("Position not found");
		}
		final Industry industry = industryDao.getByIdAndDetach(Industry.class, data.getIndustryId());
		if (industry == null) {
			throw new RuntimeException("Industry not found");
		}
	}

	private void valIdFkNotNull(final UserUpdateReq data) {
		if (data.getPositionId() == null) {
			throw new RuntimeException("Position id cannot be empty");
		}
		if (data.getIndustryId() == null) {
			throw new RuntimeException("Industry id cannot be empty");
		}
	}

	private void valChangePassword(final UserChangePasswordReq data) {
		valContentNotNullPassword(data);
	}

	private void valContentNotNullPassword(final UserChangePasswordReq data) {
		if (data.getOldPassword() == null) {
			throw new RuntimeException("Old password cannot be empty");
		}
		if (data.getNewPassword() == null) {
			throw new RuntimeException("New password cannot be empty");
		}
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

}
