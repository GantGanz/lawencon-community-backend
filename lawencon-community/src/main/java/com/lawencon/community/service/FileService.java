package com.lawencon.community.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.File;

@Service
public class FileService extends BaseCoreService {
	@Autowired
	private FileDao fileDao;

	public File getById(final String id) {
		return fileDao.getById(File.class, id);
	}
}
