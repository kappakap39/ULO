package com.eaf.orig.ulo.service.followup.result.dao;

public class FollowUpResultFactory {
	public static FollowUpResultDAO getFollowUpResultDAO(String userId) {
		return new FollowUpResultDAOImpl(userId);
	}
}
