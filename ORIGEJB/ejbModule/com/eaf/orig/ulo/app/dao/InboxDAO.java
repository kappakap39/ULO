package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.orig.bpm.workflow.model.BPMInboxInstance;

public interface InboxDAO {
	public ArrayList<HashMap<String,Object>> search(List<BPMInboxInstance> instants) throws Exception;
	public HashMap<String,String> summaryInbox(String paramCode,String userName,String roleId) throws Exception;
	public String getInboxFlag(String userName)throws Exception;
	public void updateTableUserInboxInfo(String userName,String inboxFlag) throws Exception;
}
