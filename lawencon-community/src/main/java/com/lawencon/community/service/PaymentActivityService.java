package com.lawencon.community.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PaymentActivityDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.paymentactivity.PaymentActivitiesRes;
import com.lawencon.community.dto.paymentactivity.PaymentActivityData;
import com.lawencon.community.dto.paymentactivity.PaymentActivityInsertReq;
import com.lawencon.community.dto.paymentactivity.PaymentActivityRes;
import com.lawencon.community.dto.paymentactivity.PaymentActivityUpdateReq;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.File;
import com.lawencon.community.model.PaymentActivity;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PaymentActivityService extends BaseCoreService {
	@Autowired
	private PaymentActivityDao paymentActivityDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PrincipalService principalService;

	public InsertRes insert(final PaymentActivityInsertReq data) {
		valInsert(data);

		PaymentActivity paymentActivityInsert = new PaymentActivity();
		paymentActivityInsert.setNominal(data.getNominal());
		paymentActivityInsert.setIsApproved(false);

		final String userId = principalService.getAuthPrincipal();
		final User user = userDao.getById(User.class, userId);
		paymentActivityInsert.setUser(user);

		final Activity activity = activityDao.getById(Activity.class, data.getActivityId());
		paymentActivityInsert.setActivity(activity);

		try {
			begin();
			File fileInsert = new File();
			fileInsert.setFileCodes(data.getFileCodes());
			fileInsert.setExtensions(data.getExtensions());

			fileInsert = fileDao.save(fileInsert);
			paymentActivityInsert.setFile(fileInsert);

			paymentActivityInsert = paymentActivityDao.save(paymentActivityInsert);
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Failed to create Payment Activity");
		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(paymentActivityInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);

		return insertRes;
	}

	public UpdateRes approve(final PaymentActivityUpdateReq data) {
		valUpdate(data);
		PaymentActivity paymentActivity = paymentActivityDao.getByIdAndDetach(PaymentActivity.class, data.getId());
		paymentActivity.setIsApproved(true);
		paymentActivity.setVersion(data.getVersion());

		try {
			begin();
			paymentActivity = paymentActivityDao.saveAndFlush(paymentActivity);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Approve failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(paymentActivity.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Approve success");
		return res;
	}

	public UpdateRes reject(final PaymentActivityUpdateReq data) {
		valUpdate(data);
		PaymentActivity paymentActivity = paymentActivityDao.getByIdAndDetach(PaymentActivity.class, data.getId());
		paymentActivity.setIsActive(false);
		paymentActivity.setVersion(data.getVersion());

		try {
			begin();
			paymentActivity = paymentActivityDao.saveAndFlush(paymentActivity);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Reject failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(paymentActivity.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Reject success");
		return res;
	}

	public PaymentActivitiesRes getAllApproved(final Integer offset, final Integer limit) {
		final List<PaymentActivity> paymentActivities = paymentActivityDao.getAllApproved(offset, limit);
		final List<PaymentActivityData> paymentActivityDatas = new ArrayList<>();
		for (int i = 0; i < paymentActivities.size(); i++) {
			final PaymentActivity paymentActivity = paymentActivities.get(i);
			final PaymentActivityData paymentActivityData = new PaymentActivityData();
			paymentActivityData.setId(paymentActivity.getId());
			paymentActivityData.setVersion(paymentActivity.getVersion());
			paymentActivityData.setNominal(paymentActivity.getNominal());
			paymentActivityData.setIsApproved(paymentActivity.getIsApproved());
			paymentActivityData.setFileId(paymentActivity.getFile().getId());
			paymentActivityData.setUserId(paymentActivity.getUser().getId());
			paymentActivityData.setFullname(paymentActivity.getUser().getFullname());
			paymentActivityData.setEmail(paymentActivity.getUser().getEmail());
			paymentActivityData.setActivityId(paymentActivity.getActivity().getId());
			paymentActivityData.setActivityTitle(paymentActivity.getActivity().getActivityTitle());
			paymentActivityData.setActivityType(paymentActivity.getActivity().getActivityType().getActivityTypeName());
			paymentActivityData.setCreatedAt(paymentActivity.getCreatedAt());
			paymentActivityData.setUpdatedAt(paymentActivity.getUpdatedAt());

			paymentActivityDatas.add(paymentActivityData);
		}
		final PaymentActivitiesRes paymentActivitiesRes = new PaymentActivitiesRes();
		paymentActivitiesRes.setData(paymentActivityDatas);

		return paymentActivitiesRes;
	}

	public PaymentActivitiesRes getAllRejected(final Integer offset, final Integer limit) {
		final List<PaymentActivity> paymentActivities = paymentActivityDao.getAllRejected(offset, limit);
		final List<PaymentActivityData> paymentActivityDatas = new ArrayList<>();
		for (int i = 0; i < paymentActivities.size(); i++) {
			final PaymentActivity paymentActivity = paymentActivities.get(i);
			final PaymentActivityData paymentActivityData = new PaymentActivityData();
			paymentActivityData.setId(paymentActivity.getId());
			paymentActivityData.setVersion(paymentActivity.getVersion());
			paymentActivityData.setNominal(paymentActivity.getNominal());
			paymentActivityData.setIsApproved(paymentActivity.getIsApproved());
			paymentActivityData.setFileId(paymentActivity.getFile().getId());
			paymentActivityData.setUserId(paymentActivity.getUser().getId());
			paymentActivityData.setFullname(paymentActivity.getUser().getFullname());
			paymentActivityData.setEmail(paymentActivity.getUser().getEmail());
			paymentActivityData.setActivityId(paymentActivity.getActivity().getId());
			paymentActivityData.setActivityTitle(paymentActivity.getActivity().getActivityTitle());
			paymentActivityData.setActivityType(paymentActivity.getActivity().getActivityType().getActivityTypeName());
			paymentActivityData.setCreatedAt(paymentActivity.getCreatedAt());
			paymentActivityData.setUpdatedAt(paymentActivity.getUpdatedAt());

			paymentActivityDatas.add(paymentActivityData);
		}
		final PaymentActivitiesRes paymentActivitiesRes = new PaymentActivitiesRes();
		paymentActivitiesRes.setData(paymentActivityDatas);

		return paymentActivitiesRes;
	}

	public PaymentActivitiesRes getAllUnapproved(final Integer offset, final Integer limit) {
		final List<PaymentActivity> paymentActivities = paymentActivityDao.getAllUnapproved(offset, limit);
		final List<PaymentActivityData> paymentActivityDatas = new ArrayList<>();
		for (int i = 0; i < paymentActivities.size(); i++) {
			final PaymentActivity paymentActivity = paymentActivities.get(i);
			final PaymentActivityData paymentActivityData = new PaymentActivityData();
			paymentActivityData.setId(paymentActivity.getId());
			paymentActivityData.setVersion(paymentActivity.getVersion());
			paymentActivityData.setNominal(paymentActivity.getNominal());
			paymentActivityData.setIsApproved(paymentActivity.getIsApproved());
			paymentActivityData.setFileId(paymentActivity.getFile().getId());
			paymentActivityData.setUserId(paymentActivity.getUser().getId());
			paymentActivityData.setFullname(paymentActivity.getUser().getFullname());
			paymentActivityData.setEmail(paymentActivity.getUser().getEmail());
			paymentActivityData.setActivityId(paymentActivity.getActivity().getId());
			paymentActivityData.setActivityTitle(paymentActivity.getActivity().getActivityTitle());
			paymentActivityData.setActivityType(paymentActivity.getActivity().getActivityType().getActivityTypeName());
			paymentActivityData.setCreatedAt(paymentActivity.getCreatedAt());
			paymentActivityData.setUpdatedAt(paymentActivity.getUpdatedAt());

			paymentActivityDatas.add(paymentActivityData);
		}
		final PaymentActivitiesRes paymentActivitiesRes = new PaymentActivitiesRes();
		paymentActivitiesRes.setData(paymentActivityDatas);

		return paymentActivitiesRes;
	}

	public PaymentActivitiesRes getAllByCreatorId() {
		final String userId = principalService.getAuthPrincipal();
		final List<PaymentActivity> paymentActivities = paymentActivityDao.getAllByCreatorId(userId);
		final List<PaymentActivityData> paymentActivityDatas = new ArrayList<>();
		for (int i = 0; i < paymentActivities.size(); i++) {
			final PaymentActivity paymentActivity = paymentActivities.get(i);
			final PaymentActivityData paymentActivityData = new PaymentActivityData();
			paymentActivityData.setId(paymentActivity.getId());
			paymentActivityData.setVersion(paymentActivity.getVersion());
			paymentActivityData.setNominal(paymentActivity.getNominal());
			paymentActivityData.setIsApproved(paymentActivity.getIsApproved());
			paymentActivityData.setFileId(paymentActivity.getFile().getId());
			paymentActivityData.setUserId(paymentActivity.getUser().getId());
			paymentActivityData.setFullname(paymentActivity.getUser().getFullname());
			paymentActivityData.setEmail(paymentActivity.getUser().getEmail());
			paymentActivityData.setActivityId(paymentActivity.getActivity().getId());
			paymentActivityData.setActivityTitle(paymentActivity.getActivity().getActivityTitle());
			paymentActivityData.setActivityType(paymentActivity.getActivity().getActivityType().getActivityTypeName());
			paymentActivityData.setCreatedAt(paymentActivity.getCreatedAt());
			paymentActivityData.setUpdatedAt(paymentActivity.getUpdatedAt());

			paymentActivityDatas.add(paymentActivityData);
		}
		final PaymentActivitiesRes paymentActivitiesRes = new PaymentActivitiesRes();
		paymentActivitiesRes.setData(paymentActivityDatas);

		return paymentActivitiesRes;
	}

	public PaymentActivitiesRes getAllByMemberId(final Integer offset, final Integer limit) {
		final String userId = principalService.getAuthPrincipal();
		final List<PaymentActivity> paymentActivities = paymentActivityDao.getAllByMemberId(userId, offset, limit);
		final List<PaymentActivityData> paymentActivityDatas = new ArrayList<>();
		for (int i = 0; i < paymentActivities.size(); i++) {
			final PaymentActivity paymentActivity = paymentActivities.get(i);
			final PaymentActivityData paymentActivityData = new PaymentActivityData();
			paymentActivityData.setId(paymentActivity.getId());
			paymentActivityData.setVersion(paymentActivity.getVersion());
			paymentActivityData.setNominal(paymentActivity.getNominal());
			paymentActivityData.setIsApproved(paymentActivity.getIsApproved());
			paymentActivityData.setFileId(paymentActivity.getFile().getId());
			paymentActivityData.setUserId(paymentActivity.getUser().getId());
			paymentActivityData.setFullname(paymentActivity.getUser().getFullname());
			paymentActivityData.setEmail(paymentActivity.getUser().getEmail());
			paymentActivityData.setActivityId(paymentActivity.getActivity().getId());
			paymentActivityData.setActivityTitle(paymentActivity.getActivity().getActivityTitle());
			paymentActivityData.setActivityType(paymentActivity.getActivity().getActivityType().getActivityTypeName());
			paymentActivityData.setCreatedAt(paymentActivity.getCreatedAt());
			paymentActivityData.setUpdatedAt(paymentActivity.getUpdatedAt());

			paymentActivityDatas.add(paymentActivityData);
		}
		final PaymentActivitiesRes paymentActivitiesRes = new PaymentActivitiesRes();
		paymentActivitiesRes.setData(paymentActivityDatas);

		return paymentActivitiesRes;
	}

	public PaymentActivityRes getById(final String id) {
		final PaymentActivity paymentActivity = paymentActivityDao.getById(PaymentActivity.class, id);
		final PaymentActivityData paymentActivityData = new PaymentActivityData();
		paymentActivityData.setId(paymentActivity.getId());
		paymentActivityData.setVersion(paymentActivity.getVersion());
		paymentActivityData.setNominal(paymentActivity.getNominal());
		paymentActivityData.setIsApproved(paymentActivity.getIsApproved());
		paymentActivityData.setFileId(paymentActivity.getFile().getId());
		paymentActivityData.setUserId(paymentActivity.getUser().getId());
		paymentActivityData.setFullname(paymentActivity.getUser().getFullname());
		paymentActivityData.setEmail(paymentActivity.getUser().getEmail());
		paymentActivityData.setActivityId(paymentActivity.getActivity().getId());
		paymentActivityData.setActivityTitle(paymentActivity.getActivity().getActivityTitle());
		paymentActivityData.setActivityType(paymentActivity.getActivity().getActivityType().getActivityTypeName());
		paymentActivityData.setCreatedAt(paymentActivity.getCreatedAt());
		paymentActivityData.setUpdatedAt(paymentActivity.getUpdatedAt());

		final PaymentActivityRes paymentActivityRes = new PaymentActivityRes();
		paymentActivityRes.setData(paymentActivityData);

		return paymentActivityRes;
	}

	public Long countAllUnapproved() {
		return paymentActivityDao.countAllUnapproved();
	}

	public Long countAllApproved() {
		return paymentActivityDao.countAllApproved();
	}

	public Long countAllRejected() {
		return paymentActivityDao.countAllRejected();
	}

	public BigDecimal getCreatorIncome() {
		final String userId = principalService.getAuthPrincipal();
		final BigDecimal multiplier = new BigDecimal("0.9");
		final BigDecimal income = paymentActivityDao.getCreatorIncome(userId);
		if (income == null) {
			return new BigDecimal("0");
		} else {
			return income.multiply(multiplier);
		}
	}

	public BigDecimal getSystemIncome() {
		final BigDecimal multiplier = new BigDecimal("0.1");
		return paymentActivityDao.getSystemIncome().multiply(multiplier);
	}

	public Boolean checkApproved(final String activityId) {
		final String userId = principalService.getAuthPrincipal();
		Boolean status = false;
		if (paymentActivityDao.checkApproved(userId, activityId) > 0) {
			status = true;
		}
		return status;
	}

	public Boolean checkPaid(final String activityId) {
		final String userId = principalService.getAuthPrincipal();
		Boolean status = false;
		if (paymentActivityDao.checkPaid(userId, activityId) > 0) {
			status = true;
		}
		return status;
	}

	public Boolean checkRejected(final String activityId) {
		final String userId = principalService.getAuthPrincipal();
		Boolean status = false;
		if (paymentActivityDao.checkRejected(userId, activityId) > 0) {
			status = true;
		}
		return status;
	}

	private void valInsert(final PaymentActivityInsertReq data) {
		valContentNotNull(data);
		valIdFkNotNull(data);
		valFkFound(data);
	}

	private void valContentNotNull(final PaymentActivityInsertReq data) {
		if (data.getNominal() == null) {
			throw new RuntimeException("Nominal cannot be empty");
		}
		if (data.getFileCodes() == null || "".equals(data.getFileCodes())) {
			throw new RuntimeException("File Codes cannot be empty");
		}
		if (data.getExtensions() == null || "".equals(data.getExtensions())) {
			throw new RuntimeException("Extensions cannot be empty");
		}
	}

	private void valIdFkNotNull(final PaymentActivityInsertReq data) {
		if (data.getActivityId() == null) {
			throw new RuntimeException("Activity id cannot be empty");
		}
	}

	private void valFkFound(final PaymentActivityInsertReq data) {
		final Activity activity = activityDao.getByIdAndDetach(Activity.class, data.getActivityId());
		if (activity == null) {
			throw new RuntimeException("Post not found");
		}
	}

	private void valUpdate(final PaymentActivityUpdateReq data) {
		valIdNotNull(data);
		valIdFound(data);
		valContentNotNull(data);
	}

	private void valIdNotNull(final PaymentActivityUpdateReq data) {
		if (data.getId() == null) {
			throw new RuntimeException("id cannot be empty");
		}
	}

	private void valContentNotNull(final PaymentActivityUpdateReq data) {
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

	private void valIdFound(final PaymentActivityUpdateReq data) {
		final PaymentActivity paymentActivity = paymentActivityDao.getById(PaymentActivity.class, data.getId());
		if (paymentActivity == null) {
			throw new RuntimeException("PaymentActivity not found");
		}
	}
}
