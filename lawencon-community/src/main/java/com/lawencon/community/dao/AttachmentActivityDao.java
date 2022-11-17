package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.AttachmentActivity;
import com.lawencon.community.model.File;

@Repository
public class AttachmentActivityDao extends AbstractJpaDao {

	public List<AttachmentActivity> getAllById(final String activityId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.file_id , a.activity_id ").append("FROM t_attachment_activity a ")
				.append("WHERE a.activity_id = :activityId").append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter(":activityId", activityId).getResultList();

		final List<AttachmentActivity> attachmentActivities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final AttachmentActivity attachmentActivity = new AttachmentActivity();
				attachmentActivity.setId(objArr[0].toString());

				final File file = new File();
				file.setId(objArr[1].toString());
				attachmentActivity.setFile(file);
				
				final Activity activity = new Activity();
				activity.setId(objArr[2].toString());
				attachmentActivity.setActivity(activity);

				attachmentActivities.add(attachmentActivity);
			});
		}

		return attachmentActivities;
	}
}