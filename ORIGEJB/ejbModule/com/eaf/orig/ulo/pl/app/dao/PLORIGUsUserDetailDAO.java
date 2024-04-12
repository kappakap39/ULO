package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface PLORIGUsUserDetailDAO {	
	public UserDetailM loadUsUserDetail(String userName) throws PLOrigApplicationException;
}
