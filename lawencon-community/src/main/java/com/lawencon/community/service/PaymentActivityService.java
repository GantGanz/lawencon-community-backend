package com.lawencon.community.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PaymentActivityDao;

@Service
public class PaymentActivityService extends BaseCoreService {
	@Autowired
	private PaymentActivityDao paymentActivityDao;
	
	public Long countAllUnapproved() {
		return paymentActivityDao.countAllUnapproved();
	}
}
