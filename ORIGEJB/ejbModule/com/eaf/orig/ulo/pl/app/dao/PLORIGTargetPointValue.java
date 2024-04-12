package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.model.app.TrackingDataM;

public interface PLORIGTargetPointValue {
	public String getCurrentTargetPointValue(String User);
	public String getFinishTargetPointValue(String User, String Role);
	public String getFinishTargetPointValue(String User, String Role, String tdid);
	public String getTotolJobWorking(TrackingDataM trackM);
}
