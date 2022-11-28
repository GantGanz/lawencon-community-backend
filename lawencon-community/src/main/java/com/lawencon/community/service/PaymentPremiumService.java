package com.lawencon.community.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PaymentPremiumDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.paymentpremium.PaymentPremiumData;
import com.lawencon.community.dto.paymentpremium.PaymentPremiumInsertReq;
import com.lawencon.community.dto.paymentpremium.PaymentPremiumRes;
import com.lawencon.community.dto.paymentpremium.PaymentPremiumUpdateReq;
import com.lawencon.community.dto.paymentpremium.PaymentPremiumsRes;
import com.lawencon.community.model.File;
import com.lawencon.community.model.PaymentPremium;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PaymentPremiumService extends BaseCoreService {
	@Autowired
	private PaymentPremiumDao paymentPremiumDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PrincipalService principalService;

	public InsertRes insert(final PaymentPremiumInsertReq data) {
		valInsert(data);

		PaymentPremium paymentPremiumInsert = new PaymentPremium();
		paymentPremiumInsert.setNominal(data.getNominal());
		paymentPremiumInsert.setIsApproved(false);

		final String userId = principalService.getAuthPrincipal();
		final User user = userDao.getById(User.class, userId);
		paymentPremiumInsert.setUser(user);

		try {
			begin();
			final File fileInsert = new File();
			fileInsert.setFileCodes(data.getFileCodes());
			fileInsert.setExtensions(data.getExtensions());
			
			final File file = fileDao.save(fileInsert);
			paymentPremiumInsert.setFile(file);
			
			paymentPremiumInsert = paymentPremiumDao.save(paymentPremiumInsert);
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Failed to create Payment Premium");
		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(paymentPremiumInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("Payment Premium created");

		return insertRes;
	}

	public UpdateRes approve(final PaymentPremiumUpdateReq data) {
		valUpdate(data);
		PaymentPremium paymentPremium = paymentPremiumDao.getByIdAndDetach(PaymentPremium.class, data.getId());
		paymentPremium.setIsApproved(true);
		paymentPremium.setVersion(data.getVersion());

		final User userUpdate = userDao.getByIdAndDetach(User.class, paymentPremium.getUser().getId());
		userUpdate.setIsPremium(true);
		try {
			begin();
			paymentPremium = paymentPremiumDao.saveAndFlush(paymentPremium);
			userDao.saveAndFlush(userUpdate);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Approve failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(paymentPremium.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Approve success");
		return res;
	}

	public PaymentPremiumsRes getAllApproved(final Integer offset, final Integer limit) {
		final List<PaymentPremium> paymentPremiums = paymentPremiumDao.getAllApproved(offset, limit);
		final List<PaymentPremiumData> paymentPremiumDatas = new ArrayList<>();
		for (int i = 0; i < paymentPremiums.size(); i++) {
			final PaymentPremium paymentPremium = paymentPremiums.get(i);
			final PaymentPremiumData paymentPremiumData = new PaymentPremiumData();
			paymentPremiumData.setId(paymentPremium.getId());
			paymentPremiumData.setVersion(paymentPremium.getVersion());
			paymentPremiumData.setNominal(paymentPremium.getNominal());
			paymentPremiumData.setIsApproved(paymentPremium.getIsApproved());
			paymentPremiumData.setFileId(paymentPremium.getFile().getId());
			paymentPremiumData.setUserId(paymentPremium.getUser().getId());
			paymentPremiumData.setFullname(paymentPremium.getUser().getFullname());
			paymentPremiumData.setEmail(paymentPremium.getUser().getEmail());
			paymentPremiumData.setCreatedAt(paymentPremium.getCreatedAt());
			paymentPremiumData.setUpdatedAt(paymentPremium.getUpdatedAt());

			paymentPremiumDatas.add(paymentPremiumData);
		}
		final PaymentPremiumsRes paymentPremiumsRes = new PaymentPremiumsRes();
		paymentPremiumsRes.setData(paymentPremiumDatas);

		return paymentPremiumsRes;
	}
	
	public PaymentPremiumsRes getAllUnapproved(final Integer offset, final Integer limit) {
		final List<PaymentPremium> paymentPremiums = paymentPremiumDao.getAllUnapproved(offset, limit);
		final List<PaymentPremiumData> paymentPremiumDatas = new ArrayList<>();
		for (int i = 0; i < paymentPremiums.size(); i++) {
			final PaymentPremium paymentPremium = paymentPremiums.get(i);
			final PaymentPremiumData paymentPremiumData = new PaymentPremiumData();
			paymentPremiumData.setId(paymentPremium.getId());
			paymentPremiumData.setVersion(paymentPremium.getVersion());
			paymentPremiumData.setNominal(paymentPremium.getNominal());
			paymentPremiumData.setIsApproved(paymentPremium.getIsApproved());
			paymentPremiumData.setFileId(paymentPremium.getFile().getId());
			paymentPremiumData.setUserId(paymentPremium.getUser().getId());
			paymentPremiumData.setFullname(paymentPremium.getUser().getFullname());
			paymentPremiumData.setEmail(paymentPremium.getUser().getEmail());
			paymentPremiumData.setCreatedAt(paymentPremium.getCreatedAt());
			paymentPremiumData.setUpdatedAt(paymentPremium.getUpdatedAt());

			paymentPremiumDatas.add(paymentPremiumData);
		}
		final PaymentPremiumsRes paymentPremiumsRes = new PaymentPremiumsRes();
		paymentPremiumsRes.setData(paymentPremiumDatas);

		return paymentPremiumsRes;
	}
	
	public PaymentPremiumRes getById(final String id) {
		final PaymentPremium paymentPremium = paymentPremiumDao.getById(PaymentPremium.class, id);
		final PaymentPremiumData paymentPremiumData = new PaymentPremiumData();
		paymentPremiumData.setId(paymentPremium.getId());
		paymentPremiumData.setVersion(paymentPremium.getVersion());
		paymentPremiumData.setNominal(paymentPremium.getNominal());
		paymentPremiumData.setIsApproved(paymentPremium.getIsApproved());
		paymentPremiumData.setFileId(paymentPremium.getFile().getId());
		paymentPremiumData.setUserId(paymentPremium.getUser().getId());
		paymentPremiumData.setFullname(paymentPremium.getUser().getFullname());
		paymentPremiumData.setEmail(paymentPremium.getUser().getEmail());
		paymentPremiumData.setCreatedAt(paymentPremium.getCreatedAt());
		paymentPremiumData.setUpdatedAt(paymentPremium.getUpdatedAt());

		final PaymentPremiumRes paymentPremiumRes = new PaymentPremiumRes();
		paymentPremiumRes.setData(paymentPremiumData);

		return paymentPremiumRes;
	}

	public Long countAllUnapproved() {
		return paymentPremiumDao.countAllUnapproved();
	}
	
	public Long countAllApproved() {
		return paymentPremiumDao.countAllApproved();
	}
	
	public Boolean checkStatus() {
		final String userId = principalService.getAuthPrincipal();
		Boolean status = false; 
		if(paymentPremiumDao.checkStatus(userId) != null) {
			status = true;
		}
		return status;
	}
	
	private void valInsert(final PaymentPremiumInsertReq data) {
		valContentNotNull(data);
	}

	private void valContentNotNull(final PaymentPremiumInsertReq data) {
		if (data.getNominal() == null) {
			throw new RuntimeException("Nominal cannot be empty");
		}
		if (data.getFileCodes() == null) {
			throw new RuntimeException("File Codes cannot be empty");
		}
		if (data.getExtensions() == null) {
			throw new RuntimeException("Extensions cannot be empty");
		}
	}

	private void valUpdate(final PaymentPremiumUpdateReq data) {
		valIdNotNull(data);
		valIdFound(data);
		valContentNotNull(data);
	}

	private void valIdNotNull(final PaymentPremiumUpdateReq data) {
		if (data.getId() == null) {
			throw new RuntimeException("id cannot be empty");
		}
	}

	private void valContentNotNull(final PaymentPremiumUpdateReq data) {
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

	private void valIdFound(final PaymentPremiumUpdateReq data) {
		final PaymentPremium paymentPremium = paymentPremiumDao.getById(PaymentPremium.class, data.getId());
		if (paymentPremium == null) {
			throw new RuntimeException("PaymentPremium not found");
		}
	}
}
