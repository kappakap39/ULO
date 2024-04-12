package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.FollowDocHistoryDataM;

public interface OrigFollowDocHistoryDAO {
	public void createOrigFollowDocHistoryM(FollowDocHistoryDataM followDocHistoryDataM)throws ApplicationException;
	public void createOrigFollowDocHistoryM(ArrayList<FollowDocHistoryDataM> followDocHistoryList)throws ApplicationException;
	public void deleteOrigFollowDocHistoryM(FollowDocHistoryDataM followDocHistoryDataM)throws ApplicationException;
	public ArrayList<FollowDocHistoryDataM> loadOrigFollowDocHistoryM(String applicationGroupId)throws ApplicationException;	 
	public void saveUpdateOrigFollowDocHistoryM(FollowDocHistoryDataM followDocHistoryDataM)throws ApplicationException;
	public int selectMaxFollowupSeq(String applicationGroupId)throws ApplicationException;
}
