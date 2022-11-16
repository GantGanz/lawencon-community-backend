package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.user.UserData;
import com.lawencon.community.dto.user.UsersRes;
import com.lawencon.community.model.User;

@Service
public class UserService extends BaseCoreService implements UserDetailsService {
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
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
