package com.eaf.orig.ulo.pl.app.dao;

import java.util.HashMap;
import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLTrackingDataM;
import com.eaf.orig.ulo.pl.model.app.TrackingDataM;

public interface PLOrigTrackingDAO {

	public Vector<PLTrackingDataM> queryTracking(String owner) throws PLOrigApplicationException;
	public int countUser(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException;
	public int countLogOnStatus(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException;
	public int countOnjobStatus(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException;
	public int countPreviousJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException;
	public int countPreviousJobEdit(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException;
	public int countNewJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException;
	public int countSubmitJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException;
	public int countSubmitEditJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException;
	public int countRemainJob(String role, String firstName, String lastName, String logOn, String onJob) throws PLOrigApplicationException;
	public int countAutoQueue(String role ,String roleWf) throws PLOrigApplicationException;
	public int countAutoQueue(TrackingDataM trackM) throws PLOrigApplicationException; 
	public int countInboxAutoQueueSup(String menuId, String role ,String roleWf) throws PLOrigApplicationException;
	public Vector<PLTrackingDataM> trackingAction (String owner, String role) throws PLOrigApplicationException;
	public Vector<String[]> loadRenderOnjobFlag (String userName) throws PLOrigApplicationException;
		
	public int countUser(TrackingDataM trackM)throws PLOrigApplicationException;
	public String countLogOn(TrackingDataM trackM)throws PLOrigApplicationException;
	public String countOnJob(TrackingDataM trackM)throws PLOrigApplicationException;
	public HashMap<String, Integer> countWfJob(TrackingDataM trackM)throws PLOrigApplicationException;
}
