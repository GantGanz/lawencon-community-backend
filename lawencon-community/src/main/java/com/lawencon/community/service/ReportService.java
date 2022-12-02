package com.lawencon.community.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.PaymentActivityDao;
import com.lawencon.community.dto.report.ReportData;
import com.lawencon.community.dto.report.ReportsRes;

@Service
public class ReportService extends BaseCoreService {

	@Autowired
	private PaymentActivityDao paymentActivityDao;
	@Autowired
	private ActivityDao activityDao;
	
	public Long countMemberIncome(final String userId, final String startDate, final String endDate) {
		return paymentActivityDao.countMemberIncome(userId, startDate, endDate);
	}
	
	public ReportsRes getAllMemberIncome(final String userId, final String startDate, final String endDate) {
		final List<ReportData> reportDatas = paymentActivityDao.getMemberIncome(userId, startDate, endDate);
		final ReportsRes reportsRes = new ReportsRes();
		reportsRes.setData(reportDatas);

		return reportsRes;
	}
	
	public List<ReportData> getMemberIncome(final String userId, final String startDate, final String endDate){
		return paymentActivityDao.getMemberIncome(userId, startDate, endDate);
	}
	
	public List<ReportData> getSystemIncome(final String startDate, final String endDate){
		return paymentActivityDao.getSystemIncome(startDate, endDate);
	}
	
	public Long countActivityMember(final String userId, final String startDate, final String endDate) {
		return activityDao.countActivityMember(userId, startDate, endDate);
	}
	
	public ReportsRes getAllActivityMember(final String userId, final String startDate, final String endDate) {
		final List<ReportData> reportDatas = activityDao.getMemberActivity(userId, startDate, endDate);
		final ReportsRes reportsRes = new ReportsRes();
		reportsRes.setData(reportDatas);

		return reportsRes;
	}
	
	public List<ReportData> getMemberActivity(final String userId, final String startDate, final String endDate){
		return activityDao.getMemberActivity(userId, startDate, endDate);
	}
	
	public Long countActivitySuperAdmin(final String startDate, final String endDate) {
		return activityDao.countActivitySuperAdmin(startDate, endDate);
	}
	
	public ReportsRes getAllActivitySuperAdmin(final String startDate, final String endDate) {
		final List<ReportData> reportDatas = activityDao.getActivitySuperAdmin(startDate, endDate);
		final ReportsRes reportsRes = new ReportsRes();
		reportsRes.setData(reportDatas);

		return reportsRes;
	}
	
	public List<ReportData> getActivitySuperAdmin(final String startDate, final String endDate){
		return activityDao.getActivitySuperAdmin(startDate, endDate);
	}
	
	public String formatDateRange(final String startDate, final String endDate) throws Exception{
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		final SimpleDateFormat display = new SimpleDateFormat("dd MMM yyyy");
		final StringBuilder dateRange = new StringBuilder()
				.append(display.format(formatter.parse(startDate)))
				.append(" - ")
				.append(display.format(formatter.parse(endDate)));
		return dateRange.toString();
	}

}
