package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.user.UserData;
import com.lawencon.community.dto.user.UserInsertReq;
import com.lawencon.community.dto.user.UsersRes;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;

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

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		final Optional<User> userOptional = userDao.getByEmail(email);
		
		if(userOptional.isPresent()) {
			return new org.springframework.security.core.userdetails.User(email, userOptional.get().getPass(), new ArrayList<>()); 
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
		userInsert.setIsPremium(data.getIsPremium());

		final Role role = roleDao.getById(Role.class, data.getRoleId());
		userInsert.setRole(role);

		final Industry industry = industryDao.getById(Industry.class, data.getIndustryId());
		userInsert.setIndustry(industry);

		final Position position = positionDao.getById(Position.class, data.getPositionId());
		userInsert.setPosition(position);

		final File file = new File();
		file.setFileCodes(data.getFileCodes());
		file.setExtensions(data.getExtension());

		try {
			begin();
			final File fileInsert = fileDao.saveNoLogin(file, () -> userDao.getSystemId());
			userInsert.setFile(fileInsert);
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

	private void valInsert(final UserInsertReq data) {
		bkNotDuplicate(data);
		idNull(data);
		fkFound(data);
	}

	private void idNull(final UserInsertReq data) {
		if (data.getRoleId() == null) {
			throw new RuntimeException("Role id must not null");
		}
		if (data.getPositionId() == null) {
			throw new RuntimeException("Position id must not null");
		}
		if (data.getIndustryId() == null) {
			throw new RuntimeException("Industry id must not null");
		}
	}

	private void fkFound(final UserInsertReq data) {
		final Role role = roleDao.getByIdAndDetach(Role.class, data.getRoleId());
		if (role == null) {
			throw new RuntimeException("Role not found");
		}
		final Position position = positionDao.getByIdAndDetach(Position.class, data.getPositionId());
		if (position == null) {
			throw new RuntimeException("Position not found");
		}
		final Industry industry = industryDao.getByIdAndDetach(Industry.class, data.getIndustryId());
		if (industry == null) {
			throw new RuntimeException("Industry not found");
		}
	}

	private void bkNotDuplicate(final UserInsertReq data) {
		final Optional<User> userOptional = userDao.getByEmail(data.getEmail());
		if (userOptional.isPresent()) {
			throw new RuntimeException("Email already used");
		}
	}

	public UsersRes getAll() {
		final List<User> users = userDao.getAll(User.class);
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
}
