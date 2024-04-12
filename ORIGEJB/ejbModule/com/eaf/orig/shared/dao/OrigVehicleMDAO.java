/*
 * Created on Sep 20, 2007
 * Created by Sankom Sanpunya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao;

import java.util.Vector;

import com.eaf.orig.shared.dao.exceptions.OrigVehicleMException;
import com.eaf.orig.shared.model.VehicleDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigVehicleMDAO
 */
public interface OrigVehicleMDAO {
	public void createModelVehicleDataM(VehicleDataM prmVehicleDataM)throws OrigVehicleMException;
	public void deleteModelVehicleDataM(VehicleDataM prmVehicleDataM)throws OrigVehicleMException;
	//public boolean findByPrimaryKey(VehicleDataM prmVehicleDataM)throws OrigVehicleMException;
	public VehicleDataM loadModelVehicleDataM(String applicationRecordId)throws OrigVehicleMException;
	//public void saveModelVehicleDataM(VehicleDataM prmVehicleDataM)throws OrigVehicleMException;
	public void saveUpdateModelVehicleDataM(VehicleDataM prmVehicleDataM)throws OrigVehicleMException;
	
	public Vector loadModelVehicleDataMByCoClientID(String coClientID)throws OrigVehicleMException;
	public void saveUpdateModelVehicleDataMByVehicleId(VehicleDataM prmVehicleDataM)throws OrigVehicleMException;
	public void deleteNewVehicleDataMByIdNo(String IdNo,String vehicleId)throws OrigVehicleMException;
	public VehicleDataM loadModelVehicleDataMByVehicleID(String vehicleID)throws OrigVehicleMException;
	public void saveUpdateModelVehicleDataMForCreateProposal(VehicleDataM prmVehicleDataM)throws OrigVehicleMException;
	public String selectAppRecordIdByVehicleId(int idNo)throws OrigVehicleMException;
	public int updateVehicleForChangeCar(VehicleDataM vehicleDataM)throws OrigVehicleMException;
	public String loandCarModelDesc(String carModel,String carBrand);
	public Vector loadVehicleDetailByCustomerId(String idNo)throws OrigVehicleMException;
}
