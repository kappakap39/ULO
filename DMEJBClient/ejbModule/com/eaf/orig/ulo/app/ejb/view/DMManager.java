package com.eaf.orig.ulo.app.ejb.view;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public interface DMManager {
    public void saveDMStore(DocumentManagementDataM documentManage,UserDetailM userM);
    public void saveDMBorrow(DocumentManagementDataM documentManage,UserDetailM userM);
}
