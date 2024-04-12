package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface PLOrigAddressDAO {
	public String getProvince(String provinceCode) throws PLOrigApplicationException;
	public String getDistrict(String DistrictCode, String optionalCode) throws PLOrigApplicationException;
	public String getSubDistrict(String SubDistrictCode, String optionalCode) throws PLOrigApplicationException;
	public String getZipcode(String zipCode) throws PLOrigApplicationException;
	
	public String getSubDistrictID(String subDistrictDesc, String districtId) throws PLOrigApplicationException;
	public String getDistrictID(String districtDesc, String provinceId) throws PLOrigApplicationException;
	public String getProvinceID(String provinceDesc) throws PLOrigApplicationException;
}
