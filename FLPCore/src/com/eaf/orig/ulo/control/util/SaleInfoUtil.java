package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant.ErrorMsg;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankSaleInfoDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.ulo.cache.store.CacheExpireStoreManager;

public class SaleInfoUtil {
	private static transient Logger logger = Logger.getLogger(SaleInfoUtil.class);
	
	public static void mapSaleInfoDetails(SaleInfoDataM saleInfo){
		String CHANNEL_BRANCH = SystemConstant.getConstant("CHANNEL_BRANCH");
		String CHANNEL_TELESALE = SystemConstant.getConstant("CHANNEL_TELESALE");
		String CHANNEL_DSA = SystemConstant.getConstant("CHANNEL_DSA");
		String CACHE_DSA_SALE_INFO = SystemConstant.getConstant("CACHE_DSA_SALE_INFO");
		String CACHE_CHANNEL_MAPPING = SystemConstant.getConstant("CACHE_CHANNEL_MAPPING");
		String CACHE_EXP_SALE_INFO = SystemConstant.getConstant("CACHE_EXP_SALE_INFO");
		String CACHE_EXP_SALE_BRANCH = SystemConstant.getConstant("CACHE_EXP_SALE_BRANCH");
		List<String> teleSaleTeam = Arrays.asList(SystemConfig.getGeneralParam("TELESALE_TEAM").split(","));
		if(!Util.empty(saleInfo)){
			saleInfo.clear();	
			if(!Util.empty(saleInfo.getSalesId())){
				logger.debug( "saleInfo.getSalesId() : " + saleInfo.getSalesId() );
				KbankSaleInfoDataM kbankSaleInfo = CacheExpireStoreManager.getCacheProperties( CACHE_EXP_SALE_INFO, saleInfo.getSalesId() );
				if ( null == kbankSaleInfo ) {
					DIHQueryResult<KbankSaleInfoDataM> saleInfoResult;
					try {
						saleInfoResult = DIHProxy.getKbankSaleInfo(saleInfo.getSalesId());
						kbankSaleInfo = saleInfoResult.getResult();
						if ( kbankSaleInfo.isFoundResult() ) {
							CacheExpireStoreManager.putCache( CACHE_EXP_SALE_INFO, "", saleInfo.getSalesId(), kbankSaleInfo);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				}
				boolean dihFoundResult = kbankSaleInfo.isFoundResult();
				logger.debug("dihFoundResult : "+dihFoundResult);
				if(dihFoundResult){
					saleInfo.setSalesName(kbankSaleInfo.getSaleName());
					saleInfo.setSalesPhoneNo(kbankSaleInfo.getOfficePhone());
					saleInfo.setZone(kbankSaleInfo.getZone());
					saleInfo.setRegion(kbankSaleInfo.getRegion());
					saleInfo.setSalesBranchCode(kbankSaleInfo.getBranchCode());
					saleInfo.setSalesDeptName(kbankSaleInfo.getThBnsDeptName());
					saleInfo.setSalesRCCode(kbankSaleInfo.getRcCode());
					
					if(!Util.empty(kbankSaleInfo.getBranchCode())){
						logger.debug( "kbankSaleInfo.getBranchCode() : " + kbankSaleInfo.getBranchCode() );
						KbankBranchInfoDataM kbankBranchInfo = CacheExpireStoreManager.getCacheProperties( CACHE_EXP_SALE_BRANCH, kbankSaleInfo.getBranchCode() );
						if ( null == kbankBranchInfo ) {
							DIHQueryResult<KbankBranchInfoDataM> kbankBranchResult = DIHProxy.getKbankBranchData(kbankSaleInfo.getBranchCode());
							kbankBranchInfo = kbankBranchResult.getResult();
							CacheExpireStoreManager.putCache( CACHE_EXP_SALE_BRANCH, "", kbankSaleInfo.getBranchCode(), kbankBranchInfo);
							logger.debug("get kbank brance from database 1 : " + kbankBranchInfo.toString() );
						}
						saleInfo.setSalesBranchName(kbankBranchInfo.getBranchName());
						saleInfo.setSaleChannel(CHANNEL_BRANCH);
					}else if(!Util.empty(saleInfo.getSalesRCCode())){
						saleInfo.setSaleChannel(CacheControl.getName(CACHE_CHANNEL_MAPPING, saleInfo.getSalesRCCode()));
					}
				}else{
					HashMap<String, Object> dsaSaleResult = ListBoxControl.get(CACHE_DSA_SALE_INFO,"CODE",saleInfo.getSalesId());			
					if(null != dsaSaleResult) {
						String saleChannel = CHANNEL_DSA;
						String teamId = SQLQueryEngine.display(dsaSaleResult,"TEAM_ID");
						if(!Util.empty(teamId) && teleSaleTeam.contains(teamId)){
							saleChannel = CHANNEL_TELESALE;
						}
						saleInfo.setSaleChannel(saleChannel);
					    saleInfo.setSalesName(SQLQueryEngine.display(dsaSaleResult,"SALE_NAME"));
						saleInfo.setZone(SQLQueryEngine.display(dsaSaleResult,"TEAM_ZONE"));
						saleInfo.setRegion(SQLQueryEngine.display(dsaSaleResult,"REGION"));
						saleInfo.setSalesBranchCode(SQLQueryEngine.display(dsaSaleResult,"TEAM_ID"));
						saleInfo.setSalesDeptName(SQLQueryEngine.display(dsaSaleResult,"TEAM_NAME"));
						saleInfo.setSalesRCCode(teamId);
					}
				}
			}
		}
		logger.debug("saleInfo : "+saleInfo);
	}
	
	public static void mapSaleInfoDetails(SaleInfoDataM saleInfo,FormErrorUtil formError){
		String CHANNEL_BRANCH = SystemConstant.getConstant("CHANNEL_BRANCH");
		String CHANNEL_TELESALE = SystemConstant.getConstant("CHANNEL_TELESALE");
		String CHANNEL_DSA = SystemConstant.getConstant("CHANNEL_DSA");
		String CACHE_DSA_SALE_INFO = SystemConstant.getConstant("CACHE_DSA_SALE_INFO");
		String CACHE_CHANNEL_MAPPING = SystemConstant.getConstant("CACHE_CHANNEL_MAPPING");
		String CACHE_EXP_SALE_INFO = SystemConstant.getConstant("CACHE_EXP_SALE_INFO");
		String CACHE_EXP_SALE_BRANCH = SystemConstant.getConstant("CACHE_EXP_SALE_BRANCH");
		List<String> teleSaleTeam = Arrays.asList(SystemConfig.getGeneralParam("TELESALE_TEAM").split(","));
		if(!Util.empty(saleInfo)){
			saleInfo.clear();	
			if(!Util.empty(saleInfo.getSalesId())){
				logger.debug( "saleInfo.getSalesId() : " + saleInfo.getSalesId() );
				KbankSaleInfoDataM kbankSaleInfo = CacheExpireStoreManager.getCacheProperties( CACHE_EXP_SALE_INFO, saleInfo.getSalesId() );
				if ( null == kbankSaleInfo ) {
					try{
						DIHQueryResult<KbankSaleInfoDataM> saleInfoResult = DIHProxy.getKbankSaleInfo(saleInfo.getSalesId());
						kbankSaleInfo = saleInfoResult.getResult();
						if ( kbankSaleInfo.isFoundResult() ) {
							CacheExpireStoreManager.putCache( CACHE_EXP_SALE_INFO, "", saleInfo.getSalesId(), kbankSaleInfo);
						}
						//((List)formError.getFormError().get("ax")).get(1);
					}catch(Exception e){
						formError.error(MessageErrorUtil.getText("ERROR_DIH_CONNECTION_FAILED_ERROR"));
						return ;
					}	
				}
				boolean dihFoundResult = kbankSaleInfo.isFoundResult();
				logger.debug("dihFoundResult : "+dihFoundResult);
				if(dihFoundResult){
					saleInfo.setSalesName(kbankSaleInfo.getSaleName());
					saleInfo.setSalesPhoneNo(kbankSaleInfo.getOfficePhone());
					saleInfo.setZone(kbankSaleInfo.getZone());
					saleInfo.setRegion(kbankSaleInfo.getRegion());
					saleInfo.setSalesBranchCode(kbankSaleInfo.getBranchCode());
					saleInfo.setSalesDeptName(kbankSaleInfo.getThBnsDeptName());
					saleInfo.setSalesRCCode(kbankSaleInfo.getRcCode());
					
					if(!Util.empty(kbankSaleInfo.getBranchCode())){
						logger.debug( "kbankSaleInfo.getBranchCode() : " + kbankSaleInfo.getBranchCode() );
						KbankBranchInfoDataM kbankBranchInfo = CacheExpireStoreManager.getCacheProperties( CACHE_EXP_SALE_BRANCH, kbankSaleInfo.getBranchCode() );
						if ( null == kbankBranchInfo ) {
							DIHQueryResult<KbankBranchInfoDataM> kbankBranchResult = DIHProxy.getKbankBranchData(kbankSaleInfo.getBranchCode());
							kbankBranchInfo = kbankBranchResult.getResult();
							CacheExpireStoreManager.putCache( CACHE_EXP_SALE_BRANCH, "", kbankSaleInfo.getBranchCode(), kbankBranchInfo);
							logger.debug("get kbank brance from database 1 : " + kbankBranchInfo.toString() );
						}
						saleInfo.setSalesBranchName(kbankBranchInfo.getBranchName());
						saleInfo.setSaleChannel(CHANNEL_BRANCH);
					}else if(!Util.empty(saleInfo.getSalesRCCode())){
						saleInfo.setSaleChannel(CacheControl.getName(CACHE_CHANNEL_MAPPING, saleInfo.getSalesRCCode()));
					}
				}else{
					HashMap<String, Object> dsaSaleResult = ListBoxControl.get(CACHE_DSA_SALE_INFO,"CODE",saleInfo.getSalesId());			
					if(null != dsaSaleResult) {
						String saleChannel = CHANNEL_DSA;
						String teamId = SQLQueryEngine.display(dsaSaleResult,"TEAM_ID");
						if(!Util.empty(teamId) && teleSaleTeam.contains(teamId)){
							saleChannel = CHANNEL_TELESALE;
						}
						saleInfo.setSaleChannel(saleChannel);
					    saleInfo.setSalesName(SQLQueryEngine.display(dsaSaleResult,"SALE_NAME"));
						saleInfo.setZone(SQLQueryEngine.display(dsaSaleResult,"TEAM_ZONE"));
						saleInfo.setRegion(SQLQueryEngine.display(dsaSaleResult,"REGION"));
						saleInfo.setSalesBranchCode(SQLQueryEngine.display(dsaSaleResult,"TEAM_ID"));
						saleInfo.setSalesDeptName(SQLQueryEngine.display(dsaSaleResult,"TEAM_NAME"));
						saleInfo.setSalesRCCode(teamId);
					}
				}
			}
		}
		logger.debug("saleInfo : "+saleInfo);
	}	
	public static String showBranchDetp(SaleInfoDataM saleInfoM){
		List<String> texts = new ArrayList<>();
		if(!Util.empty(saleInfoM)){
			if(!Util.empty(saleInfoM.getSalesBranchCode())){
				texts.add(saleInfoM.getSalesBranchCode());
			}
			if(!Util.empty(saleInfoM.getSalesDeptName())){
				texts.add(saleInfoM.getSalesDeptName());
			}
		}
		return StringUtils.join(texts,"/");
	}
	
}
