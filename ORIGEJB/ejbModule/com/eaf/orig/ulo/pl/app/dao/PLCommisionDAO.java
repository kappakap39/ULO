package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.ulo.pl.app.dao.exception.PLCommisssionException;
import com.eaf.orig.ulo.pl.model.app.SATeamDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionAppDetialDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionDetialsDataM;

public interface PLCommisionDAO {
	public Vector<SAUserCommissionAppDetialDataM> selectApplicaionCalulateCommisson(java.util.Date cardLinkDate) throws PLCommisssionException;
	public int createUserCommissionAppDetial(Vector<SAUserCommissionAppDetialDataM> vectorUserComision) throws PLCommisssionException;
	public Vector<SAUserCommissionDataM> selectCommisionPeriod(String period)throws PLCommisssionException;	 
	public Vector<SAUserCommissionDetialsDataM> selectCommisionPeriodDetail(String period)throws PLCommisssionException;
	public int createUserCommissionData(Vector<SAUserCommissionDataM> vectorUserComission) throws PLCommisssionException;
	public int createUserCommissionDetailsData(Vector<SAUserCommissionDetialsDataM> vectorUserComission) throws PLCommisssionException;
	public SATeamDataM getTeamData(String period, String userId) throws PLCommisssionException;
	public int deleteUserCommissionDetailData(String periodno)throws PLCommisssionException;
}
