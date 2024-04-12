package com.eaf.orig.ulo.app.ejb.view;

import com.eaf.orig.profile.model.LogonDataM;

public interface ControlLogManager {
	public void saveLog(LogonDataM logonM);
	public void updateLogonFlag(String userName,String flag);
}
