package com.lawencon.community.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PaymentPremiumDao;

@Service
public class PaymentPremiumService extends BaseCoreService {
	@Autowired
	private PaymentPremiumDao paymentPremiumDao;
	
	public Long countAllUnapproved() {
		return paymentPremiumDao.countAllUnapproved();
	}
}
