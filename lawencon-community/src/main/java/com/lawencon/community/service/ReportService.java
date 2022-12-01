package com.lawencon.community.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.PaymentActivityDao;
import com.lawencon.community.dto.report.ReportData;

@Service
public class ReportService extends BaseCoreService {

	@Autowired
	private PaymentActivityDao paymentActivityDao;
	@Autowired
	private ActivityDao activityDao;
	
	public List<ReportData> getMemberIncome(final String userId, final String startDate, final String endDate){
		return paymentActivityDao.getMemberIncome(userId, startDate, endDate);
	}
	
	public List<ReportData> getSystemIncome(final String startDate, final String endDate){
		return paymentActivityDao.getSystemIncome(startDate, endDate);
	}
	
	public List<ReportData> getMemberActivity(final String userId, final String startDate, final String endDate){
		return activityDao.getMemberActivity(userId, startDate, endDate);
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
