package com.lawencon.community.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.util.GenerateCodeUtil;
import com.lawencon.util.MailUtil;
import com.lawencon.util.VerificationCodeUtil;

@Service
public class UserVerificationService extends BaseCoreService {

	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private VerificationCodeUtil verificationCodeUtil;

	public InsertRes sendCode(final String email) {

		final Map<String, Object> template = new HashMap<>();
		final String code = GenerateCodeUtil.generateCode();
		template.put("email", email);
		template.put("code", code);
		final String subject = "Zenith Verification Code";
		try {
			mailUtil.sendMessageFreeMarker(email, subject, template, "MemberRegistration.ftl");
		} catch (final Exception e) {
			e.printStackTrace();
		}

		verificationCodeUtil.addVerificationCode(email, code);

		final InsertRes insertRes = new InsertRes();
		insertRes.setMessage("Code sent successfully");
		return insertRes;
	}

	public InsertRes validate(final String email, final String verificationCode) {
		verificationCodeUtil.validateVerificationCode(email, verificationCode);

		final InsertRes insertRes = new InsertRes();
		insertRes.setMessage("Validate Success");
		return insertRes;
	}
}
