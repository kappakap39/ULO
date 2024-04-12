package com.eaf.orig.ulo.pl.app.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.eaf.orig.shared.dao.EjbUtilException;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface PLOrigAppUtilDAO {
	public Date getDateExtendWorkingDay(Date startCalDate, int extendDay) throws PLOrigApplicationException;
	public String getDateExtendWorkingDay(String appRecID) throws PLOrigApplicationException;
	public String getDefaultCycleCut(String businessClass) throws PLOrigApplicationException;
    /*DLA*/
    public String getDLA(BigDecimal creditLimit,String jobType)throws EjbUtilException;
    /*20121213 Sankom  Add Parameter Credit Limit for CR*/
    public String getDLAPolicy(String policyCodes,BigDecimal creditLimit) throws EjbUtilException ;	 
}
