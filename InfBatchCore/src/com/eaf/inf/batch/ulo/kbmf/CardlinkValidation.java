package com.eaf.inf.batch.ulo.kbmf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.google.gson.Gson;

public class CardlinkValidation {
	private static transient Logger logger = Logger.getLogger(CardlinkValidation.class);
	public static boolean validateCardlinkData(CardlinkDataM cardlink){
		if(!InfBatchUtil.empty(cardlink)){
				cardlink.ERROR_MSG = new ArrayList<String>();
				validateNull(cardlink);
				validateLength(cardlink);
				validatePossibleValue(cardlink);
		}				
		if(!InfBatchUtil.empty(cardlink.ERROR_MSG)){
			return false;
		}
		return true;
	}
	private static void validateLength(CardlinkDataM cardlink){
		String CARDLINK_LENGTH_ERROR_MSG = InfBatchProperty.getInfBatchConfig("CARDLINK_LENGTH_ERROR_MSG");
		if(cardlink.TRAN_OPERATOR.length()>8){
			logger.debug("TRAN_OPERATOR "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("TRAN_OPERATOR "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.TRAN_S_AP_NUM.length()>9){
			logger.debug("TRAN_S_AP_NUM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("TRAN_S_AP_NUM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.TRAN_S_SYS_DT.length()>8){
			logger.debug("TRAN_S_SYS_DT "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("TRAN_S_SYS_DT "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.TRAN_S_SYS_TIME.length()>8){
			logger.debug("TRAN_S_SYS_TIME "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("TRAN_S_SYS_TIME "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.BRANCH_CD.length()>4){
			logger.debug("BRANCH_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("BRANCH_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.BRANCH_NM.length()>50){
			logger.debug("BRANCH_NM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("BRANCH_NM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.BRANCH_REG_CD.length()>2){
			logger.debug("BRANCH_REG_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("BRANCH_REG_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.BRANCH_ZN_CD.length()>3){
			logger.debug("BRANCH_ZN_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("BRANCH_ZN_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.HO_CUST_SERV_OFF_CD.length()>6){
			logger.debug("HO_CUST_SERV_OFF_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("HO_CUST_SERV_OFF_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.HO_CUST_SERV_OFF_NM.length()>50){
			logger.debug("HO_CUST_SERV_OFF_NM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("HO_CUST_SERV_OFF_NM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.PBSF_STFF_CD.length()>8){
			logger.debug("PBSF_STFF_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("PBSF_STFF_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.PBSF_STFF_NM.length()>50){
			logger.debug("PBSF_STFF_NM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("PBSF_STFF_NM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.PBSF_TM_CD.length()>4){
			logger.debug("PBSF_TM_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("PBSF_TM_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.PBSF_TM_NM.length()>40){
			logger.debug("PBSF_TM_NM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("PBSF_TM_NM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.PBSF_REG_CD.length()>2){
			logger.debug("PBSF_REG_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("PBSF_REG_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.PBSF_ZN_CD.length()>3){
			logger.debug("PBSF_ZN_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("PBSF_ZN_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.NO_MAIN_CRD_INC_UPGR.length()>1){
			logger.debug("NO_MAIN_CRD_INC_UPGR "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("NO_MAIN_CRD_INC_UPGR "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.NO_SUPP_CRD_INC_UPGR.length()>1){
			logger.debug("NO_SUPP_CRD_INC_UPGR "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("NO_SUPP_CRD_INC_UPGR "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.CC_APP_NO_STMP.length()>8){
			logger.debug("CC_APP_NO_STMP "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("CC_APP_NO_STMP "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.CC_PROJECT_CD.length()>4){
			logger.debug("CC_PROJECT_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("CC_PROJECT_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.CC_MEM_GET_MEM_NO.length()>16){
			logger.debug("CC_MEM_GET_MEM_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("CC_MEM_GET_MEM_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.CC_NO_CRDS_REQ.length()>1){
			logger.debug("CC_NO_CRDS_REQ "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("CC_NO_CRDS_REQ "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_NEW_CRD_LIM_REQ1.length()>9){
			logger.debug("A1_NEW_CRD_LIM_REQ1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_NEW_CRD_LIM_REQ1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_NEW_CRD_LIM_REQ1.length()>9){
			logger.debug("S1_NEW_CRD_LIM_REQ1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_NEW_CRD_LIM_REQ1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRDHLDR_NO1.length()>16){
			logger.debug("A1_CRDHLDR_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRDHLDR_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRDHLDR_NO1.length()>16){
			logger.debug("S1_CRDHLDR_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRDHLDR_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_NEW_CRD_LIM_REQ2.length()>9){
			logger.debug("A1_NEW_CRD_LIM_REQ2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_NEW_CRD_LIM_REQ2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_NEW_CRD_LIM_REQ2.length()>9){
			logger.debug("S1_NEW_CRD_LIM_REQ2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_NEW_CRD_LIM_REQ2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRDHLDR_NO2.length()>16){
			logger.debug("A1_CRDHLDR_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRDHLDR_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRDHLDR_NO2.length()>16){
			logger.debug("S1_CRDHLDR_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRDHLDR_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_NEW_CRD_LIM_REQ3.length()>9){
			logger.debug("A1_NEW_CRD_LIM_REQ3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_NEW_CRD_LIM_REQ3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_NEW_CRD_LIM_REQ3.length()>9){
			logger.debug("S1_NEW_CRD_LIM_REQ3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_NEW_CRD_LIM_REQ3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRDHLDR_NO3.length()>16){
			logger.debug("A1_CRDHLDR_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRDHLDR_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRDHLDR_NO3.length()>16){
			logger.debug("S1_CRDHLDR_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRDHLDR_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
		}
//		if(cardlink.A1_SAV_DEP.length()>9){
//			logger.debug("A1_SAV_DEP "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("A1_SAV_DEP "+CARDLINK_LENGTH_ERROR_MSG);
//		}if(cardlink.A1_FIX_DEP_HLD.length()>9){
//			logger.debug("A1_FIX_DEP_HLD "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("A1_FIX_DEP_HLD "+CARDLINK_LENGTH_ERROR_MSG);
//		}if(cardlink.A1_FIX_DEP_NOT_HLD.length()>9){
//			logger.debug("A1_FIX_DEP_NOT_HLD "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("A1_FIX_DEP_NOT_HLD "+CARDLINK_LENGTH_ERROR_MSG);
//		}
		if(cardlink.A1_TERT_BILL_CSH_INFLW.length()>7){
			logger.debug("A1_TERT_BILL_CSH_INFLW "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_TERT_BILL_CSH_INFLW "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_TERT_BILL_TM_MAT_YY.length()>2){
			logger.debug("A1_TERT_BILL_TM_MAT_YY "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_TERT_BILL_TM_MAT_YY "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_TERT_BILL_TM_MAT_MM.length()>2){
			logger.debug("A1_TERT_BILL_TM_MAT_MM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_TERT_BILL_TM_MAT_MM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_TERT_BILL_MAT_TOT.length()>3){
			logger.debug("A1_TERT_BILL_MAT_TOT "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_TERT_BILL_MAT_TOT "+CARDLINK_LENGTH_ERROR_MSG);
		}
//		if(cardlink.S1_FIX_DEP_HLD.length()>9){
//			logger.debug("S1_FIX_DEP_HLD "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("S1_FIX_DEP_HLD "+CARDLINK_LENGTH_ERROR_MSG);
//		}
		if(cardlink.DT_REG_CENTRAL.length()>8){
			logger.debug("DT_REG_CENTRAL "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("DT_REG_CENTRAL "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_OTH_TITLE_TH.length()>18){
			logger.debug("A1_OTH_TITLE_TH "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_OTH_TITLE_TH "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_1ST_NM_TH.length()>30){
			logger.debug("A1_1ST_NM_TH "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_1ST_NM_TH "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_SURNAME_TH.length()>30){
			logger.debug("A1_SURNAME_TH "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_SURNAME_TH "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_OTH_TITLE_ENG.length()>5){
			logger.debug("A1_OTH_TITLE_ENG "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_OTH_TITLE_ENG "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_1ST_NM_ENG.length()>15){
			logger.debug("A1_1ST_NM_ENG "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_1ST_NM_ENG "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_SURNAME_ENG.length()>15){
			logger.debug("A1_SURNAME_ENG "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_SURNAME_ENG "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_EMBOSS_NM_ENG.length()>26){
			logger.debug("A1_EMBOSS_NM_ENG "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_EMBOSS_NM_ENG "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_DOB_CE.length()>8){
			logger.debug("A1_DOB_CE "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_DOB_CE "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_NO_CHILDREN.length()>2){
			logger.debug("A1_NO_CHILDREN "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_NO_CHILDREN "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_NO_DEPDNTS.length()>2){
			logger.debug("A1_NO_DEPDNTS "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_NO_DEPDNTS "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_ID_NO.length()>13){
			logger.debug("A1_ID_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_ID_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_WRK_PERM_ISS_NO.length()>11){
			logger.debug("A1_WRK_PERM_ISS_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_WRK_PERM_ISS_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_RESIDE_PD_YRS.length()>2){
			logger.debug("A1_CAD_RESIDE_PD_YRS "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_RESIDE_PD_YRS "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_RESIDE_PD_MTHS.length()>2){
			logger.debug("A1_CAD_RESIDE_PD_MTHS "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_RESIDE_PD_MTHS "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_NO.length()>8){
			logger.debug("A1_CAD_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_MOO.length()>2){
			logger.debug("A1_CAD_MOO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_MOO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_SOI.length()>15){
			logger.debug("A1_CAD_SOI "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_SOI "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_ST.length()>20){
			logger.debug("A1_CAD_ST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_ST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_SUB_DIST.length()>15){
			logger.debug("A1_CAD_SUB_DIST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_SUB_DIST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_DIST.length()>15){
			logger.debug("A1_CAD_DIST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_DIST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_PROV.length()>15){
			logger.debug("A1_CAD_PROV "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_PROV "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_ZIP_CD.length()>5){
			logger.debug("A1_CAD_ZIP_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_ZIP_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_TEL_NO.length()>19){
			logger.debug("A1_CAD_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CAD_MOB_NO.length()>10){
			logger.debug("A1_CAD_MOB_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CAD_MOB_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_EMAIL.length()>30){
			logger.debug("A1_EMAIL "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_EMAIL "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_RAD_NO.length()>8){
			logger.debug("A1_RAD_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_RAD_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_RAD_MOO.length()>2){
			logger.debug("A1_RAD_MOO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_RAD_MOO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_RAD_SOI.length()>15){
			logger.debug("A1_RAD_SOI "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_RAD_SOI "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_RAD_ST.length()>20){
			logger.debug("A1_RAD_ST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_RAD_ST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_RAD_SUB_DIST.length()>15){
			logger.debug("A1_RAD_SUB_DIST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_RAD_SUB_DIST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_RAD_DIST.length()>15){
			logger.debug("A1_RAD_DIST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_RAD_DIST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_RAD_PROV.length()>15){
			logger.debug("A1_RAD_PROV "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_RAD_PROV "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_RAD_ZIP_CD.length()>5){
			logger.debug("A1_RAD_ZIP_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_RAD_ZIP_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_NM.length()>30){
			logger.debug("A1_CEMP_NM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_NM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_YRS.length()>2){
			logger.debug("A1_CEMP_YRS "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_YRS "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_MTHS.length()>2){
			logger.debug("A1_CEMP_MTHS "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_MTHS "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_POS_NM.length()>30){
			logger.debug("A1_CEMP_POS_NM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_POS_NM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_GROSS_MTHLY_SAL.length()>7){
			logger.debug("A1_GROSS_MTHLY_SAL "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_GROSS_MTHLY_SAL "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_MTHLY_BUS_INC.length()>7){
			logger.debug("A1_MTHLY_BUS_INC "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_MTHLY_BUS_INC "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_MTHLY_BONUS_INC.length()>7){
			logger.debug("A1_MTHLY_BONUS_INC "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_MTHLY_BONUS_INC "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_MTHLY_ADD_INC.length()>7){
			logger.debug("A1_MTHLY_ADD_INC "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_MTHLY_ADD_INC "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_ADD_BUILD.length()>30){
			logger.debug("A1_CEMP_ADD_BUILD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_ADD_BUILD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_FLR.length()>2){
			logger.debug("A1_CEMP_FLR "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_FLR "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_DIV.length()>20){
			logger.debug("A1_CEMP_DIV "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_DIV "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_DPTMNT.length()>20){
			logger.debug("A1_CEMP_DPTMNT "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_DPTMNT "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_NO.length()>8){
			logger.debug("A1_CEMP_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_MOO.length()>2){
			logger.debug("A1_CEMP_MOO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_MOO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_SOI.length()>15){
			logger.debug("A1_CEMP_SOI "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_SOI "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_ST.length()>20){
			logger.debug("A1_CEMP_ST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_ST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_SUB_DIST.length()>15){
			logger.debug("A1_CEMP_SUB_DIST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_SUB_DIST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_DIST.length()>15){
			logger.debug("A1_CEMP_DIST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_DIST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_PROV.length()>15){
			logger.debug("A1_CEMP_PROV "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_PROV "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_ZIP_CD.length()>5){
			logger.debug("A1_CEMP_ZIP_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_ZIP_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CEMP_TEL_NO.length()>19){
			logger.debug("A1_CEMP_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CEMP_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_VERI_MTHLY_INC.length()>7){
			logger.debug("A1_VERI_MTHLY_INC "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_VERI_MTHLY_INC "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_OTHER_TITLE_TH.length()>18){
			logger.debug("S1_OTHER_TITLE_TH "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_OTHER_TITLE_TH "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_1ST_NM_TH.length()>30){
			logger.debug("S1_1ST_NM_TH "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_1ST_NM_TH "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_SURNAME_TH.length()>30){
			logger.debug("S1_SURNAME_TH "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_SURNAME_TH "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_OTHER_TITLE_ENG.length()>5){
			logger.debug("S1_OTHER_TITLE_ENG "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_OTHER_TITLE_ENG "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_1ST_NM_ENG.length()>15){
			logger.debug("S1_1ST_NM_ENG "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_1ST_NM_ENG "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_SURNAME_ENG.length()>15){
			logger.debug("S1_SURNAME_ENG "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_SURNAME_ENG "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_EMBOSS_NM_ENG.length()>26){
			logger.debug("S1_EMBOSS_NM_ENG "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_EMBOSS_NM_ENG "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_DOB_CE.length()>8){
			logger.debug("S1_DOB_CE "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_DOB_CE "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_ID_NO.length()>13){
			logger.debug("S1_ID_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_ID_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_NO.length()>8){
			logger.debug("S1_CAD_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_MOO.length()>2){
			logger.debug("S1_CAD_MOO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_MOO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_SOI.length()>15){
			logger.debug("S1_CAD_SOI "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_SOI "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_ST.length()>20){
			logger.debug("S1_CAD_ST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_ST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_SUB_DIST.length()>15){
			logger.debug("S1_CAD_SUB_DIST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_SUB_DIST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_DIST.length()>15){
			logger.debug("S1_CAD_DIST "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_DIST "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_PROV.length()>15){
			logger.debug("S1_CAD_PROV "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_PROV "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_ZIP_CD.length()>5){
			logger.debug("S1_CAD_ZIP_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_ZIP_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_TEL_NO.length()>19){
			logger.debug("S1_CAD_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CAD_MOB_NO.length()>10){
			logger.debug("S1_CAD_MOB_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CAD_MOB_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_EMAIL.length()>30){
			logger.debug("S1_EMAIL "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_EMAIL "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CEMP_NM.length()>30){
			logger.debug("S1_CEMP_NM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CEMP_NM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CEMP_TEL_NO.length()>19){
			logger.debug("S1_CEMP_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CEMP_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CC_BRNCH_CD_REC_CRD.length()>4){
			logger.debug("A1_CC_BRNCH_CD_REC_CRD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CC_BRNCH_CD_REC_CRD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CC_APA_NO.length()>10){
			logger.debug("A1_CC_APA_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CC_APA_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CC_SA_NO_ATM.length()>10){
			logger.debug("A1_CC_SA_NO_ATM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CC_SA_NO_ATM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CC_CA_NO_ATM.length()>10){
			logger.debug("A1_CC_CA_NO_ATM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CC_CA_NO_ATM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CC_BRNCH_CD_REC_CRD.length()>4){
			logger.debug("S1_CC_BRNCH_CD_REC_CRD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CC_BRNCH_CD_REC_CRD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CC_APA_NO.length()>10){
			logger.debug("S1_CC_APA_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CC_APA_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CC_SA_NO_ATM.length()>10){
			logger.debug("S1_CC_SA_NO_ATM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CC_SA_NO_ATM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CC_CA_NO_ATM.length()>10){
			logger.debug("S1_CC_CA_NO_ATM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CC_CA_NO_ATM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_A1_NO_CC_LPM.length()>2){
			logger.debug("X_A1_NO_CC_LPM "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_A1_NO_CC_LPM "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_A1_CC_CUSTNO_CPAC.length()>16){
			logger.debug("X_A1_CC_CUSTNO_CPAC "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_A1_CC_CUSTNO_CPAC "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_CC_CUSTNO_CPAC.length()>16){
			logger.debug("X_S1_CC_CUSTNO_CPAC "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_CC_CUSTNO_CPAC "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.R_DEC_ORG_RSN_CD.length()>4){
			logger.debug("R_DEC_ORG_RSN_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("R_DEC_ORG_RSN_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.R_DEC_RSN_CD_TBL.length()>80){
			logger.debug("R_DEC_RSN_CD_TBL "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("R_DEC_RSN_CD_TBL "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.R_APP_RISK_SCR.length()>7){
			logger.debug("R_APP_RISK_SCR "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("R_APP_RISK_SCR "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_1.length()>32){
			logger.debug("X_POL_DEC_1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_2.length()>32){
			logger.debug("X_POL_DEC_2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_3.length()>32){
			logger.debug("X_POL_DEC_3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_4.length()>32){
			logger.debug("X_POL_DEC_4 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_4 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_5.length()>32){
			logger.debug("X_POL_DEC_5 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_5 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_6.length()>32){
			logger.debug("X_POL_DEC_6 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_6 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_7.length()>32){
			logger.debug("X_POL_DEC_7 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_7 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_8.length()>32){
			logger.debug("X_POL_DEC_8 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_8 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_9.length()>32){
			logger.debug("X_POL_DEC_9 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_9 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_POL_DEC_10.length()>32){
			logger.debug("X_POL_DEC_10 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_POL_DEC_10 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.APP_FTHR_REV_RSN_CD.length()>5){
			logger.debug("APP_FTHR_REV_RSN_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("APP_FTHR_REV_RSN_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}
//		if(cardlink.A1_FIN_CRD_LIM1.length()>9){
//			logger.debug("A1_FIN_CRD_LIM1 "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("A1_FIN_CRD_LIM1 "+CARDLINK_LENGTH_ERROR_MSG);
//		}
//		if(cardlink.A1_FIN_CRD_LIM2.length()>9){
//			logger.debug("A1_FIN_CRD_LIM2 "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("A1_FIN_CRD_LIM2 "+CARDLINK_LENGTH_ERROR_MSG);
//		}
//		if(cardlink.A1_FIN_CRD_LIM3.length()>9){
//			logger.debug("A1_FIN_CRD_LIM3 "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("A1_FIN_CRD_LIM3 "+CARDLINK_LENGTH_ERROR_MSG);
//		}
//		if(cardlink.S1_FIN_CRD_LIM1.length()>9){
//			logger.debug("S1_FIN_CRD_LIM1 "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("S1_FIN_CRD_LIM1 "+CARDLINK_LENGTH_ERROR_MSG);
//		}
//		if(cardlink.S1_FIN_CRD_LIM2.length()>9){
//			logger.debug("S1_FIN_CRD_LIM2 "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("S1_FIN_CRD_LIM2 "+CARDLINK_LENGTH_ERROR_MSG);
//		}
//		if(cardlink.S1_FIN_CRD_LIM3.length()>9){
//			logger.debug("S1_FIN_CRD_LIM3 "+CARDLINK_LENGTH_ERROR_MSG);
//			cardlink.ERROR_MSG.add("S1_FIN_CRD_LIM3 "+CARDLINK_LENGTH_ERROR_MSG);
//		}
		if(cardlink.A1_CRD_ADD_TRCK1.length()>15){
			logger.debug("A1_CRD_ADD_TRCK1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRD_ADD_TRCK1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_A1_CRD_PREF_NO1.length()>6){
			logger.debug("X_A1_CRD_PREF_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_A1_CRD_PREF_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_A1_2ND_LINE_EMB1.length()>26){
			logger.debug("X_A1_2ND_LINE_EMB1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_A1_2ND_LINE_EMB1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_PRIOR_PASS_CRD_NO1.length()>16){
			logger.debug("A1_PRIOR_PASS_CRD_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_PRIOR_PASS_CRD_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_ACT_FL1.length()>1){
			logger.debug("A1_ACT_FL1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_ACT_FL1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRD_FEE_DT1.length()>8){
			logger.debug("A1_CRD_FEE_DT1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRD_FEE_DT1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRD_ADD_TRCK2.length()>15){
			logger.debug("A1_CRD_ADD_TRCK2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRD_ADD_TRCK2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_A1_CRD_PREF_NO2.length()>6){
			logger.debug("X_A1_CRD_PREF_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_A1_CRD_PREF_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_A1_2ND_LINE_EMB2.length()>26){
			logger.debug("X_A1_2ND_LINE_EMB2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_A1_2ND_LINE_EMB2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_PRIOR_PASS_CRD_NO2.length()>16){
			logger.debug("A1_PRIOR_PASS_CRD_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_PRIOR_PASS_CRD_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRD_FEE_DT2.length()>8){
			logger.debug("A1_CRD_FEE_DT2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRD_FEE_DT2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRD_ADD_TRCK3.length()>15){
			logger.debug("A1_CRD_ADD_TRCK3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRD_ADD_TRCK3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_A1_CRD_PREF_NO3.length()>6){
			logger.debug("X_A1_CRD_PREF_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_A1_CRD_PREF_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_A1_2ND_LINE_EMB3.length()>26){
			logger.debug("X_A1_2ND_LINE_EMB3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_A1_2ND_LINE_EMB3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_PRIOR_PASS_CRD_NO3.length()>16){
			logger.debug("A1_PRIOR_PASS_CRD_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_PRIOR_PASS_CRD_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRD_FEE_DT3.length()>8){
			logger.debug("A1_CRD_FEE_DT3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRD_FEE_DT3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRD_ADD_TRCK1.length()>15){
			logger.debug("S1_CRD_ADD_TRCK1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRD_ADD_TRCK1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_CRD_PREF_NO1.length()>6){
			logger.debug("X_S1_CRD_PREF_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_CRD_PREF_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_2ND_LINE_EMB1.length()>26){
			logger.debug("X_S1_2ND_LINE_EMB1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_2ND_LINE_EMB1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_PRIOR_PASS_CRD_NO1.length()>16){
			logger.debug("S1_PRIOR_PASS_CRD_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_PRIOR_PASS_CRD_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRD_FEE_DT1.length()>8){
			logger.debug("S1_CRD_FEE_DT1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRD_FEE_DT1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRD_ADD_TRCK2.length()>15){
			logger.debug("S1_CRD_ADD_TRCK2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRD_ADD_TRCK2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_CRD_PREF_NO2.length()>6){
			logger.debug("X_S1_CRD_PREF_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_CRD_PREF_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_2ND_LINE_EMB2.length()>26){
			logger.debug("X_S1_2ND_LINE_EMB2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_2ND_LINE_EMB2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_PRIOR_PASS_CRD_NO2.length()>16){
			logger.debug("S1_PRIOR_PASS_CRD_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_PRIOR_PASS_CRD_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRD_FEE_DT2.length()>8){
			logger.debug("S1_CRD_FEE_DT2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRD_FEE_DT2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRD_ADD_TRCK3.length()>15){
			logger.debug("S1_CRD_ADD_TRCK3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRD_ADD_TRCK3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_CRD_PREF_NO3.length()>6){
			logger.debug("X_S1_CRD_PREF_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_CRD_PREF_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_2ND_LINE_EMB3.length()>26){
			logger.debug("X_S1_2ND_LINE_EMB3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_2ND_LINE_EMB3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_PRIOR_PASS_CRD_NO3.length()>16){
			logger.debug("S1_PRIOR_PASS_CRD_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_PRIOR_PASS_CRD_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRD_FEE_DT3.length()>8){
			logger.debug("S1_CRD_FEE_DT3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRD_FEE_DT3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRD_ACT_DT1.length()>8){
			logger.debug("A1_CRD_ACT_DT1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRD_ACT_DT1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRD_ACT_DT2.length()>8){
			logger.debug("A1_CRD_ACT_DT2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRD_ACT_DT2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CRD_ACT_DT3.length()>8){
			logger.debug("A1_CRD_ACT_DT3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CRD_ACT_DT3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRD_ACT_DT1.length()>8){
			logger.debug("S1_CRD_ACT_DT1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRD_ACT_DT1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRD_ACT_DT2.length()>8){
			logger.debug("S1_CRD_ACT_DT2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRD_ACT_DT2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CRD_ACT_DT3.length()>8){
			logger.debug("S1_CRD_ACT_DT3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CRD_ACT_DT3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.G_ORG_CD.length()>3){
			logger.debug("G_ORG_CD "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("G_ORG_CD "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_DATEOF_CONCENT_CE.length()>8){
			logger.debug("A1_DATEOF_CONCENT_CE "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_DATEOF_CONCENT_CE "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_DATEOF_CONCENT_CE.length()>8){
			logger.debug("S1_DATEOF_CONCENT_CE "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_DATEOF_CONCENT_CE "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X1_CARD_NO1.length()>16){
			logger.debug("X1_CARD_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X1_CARD_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_CARD_NO1.length()>16){
			logger.debug("X_S1_CARD_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_CARD_NO1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X1_CARD_NO2.length()>16){
			logger.debug("X1_CARD_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X1_CARD_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_CARD_NO2.length()>16){
			logger.debug("X_S1_CARD_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_CARD_NO2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X1_CARD_NO3.length()>16){
			logger.debug("X1_CARD_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X1_CARD_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.X_S1_CARD_NO3.length()>16){
			logger.debug("X_S1_CARD_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("X_S1_CARD_NO3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.G_STAT12_OP_ID.length()>8){
			logger.debug("G_STAT12_OP_ID "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("G_STAT12_OP_ID "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_1ST_NM_ENG_NEW.length()>25){
			logger.debug("A1_1ST_NM_ENG_NEW "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_1ST_NM_ENG_NEW "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_SURNAME_ENG_NEW.length()>30){
			logger.debug("A1_SURNAME_ENG_NEW "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_SURNAME_ENG_NEW "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_1ST_NM_ENG_NEW.length()>25){
			logger.debug("S1_1ST_NM_ENG_NEW "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_1ST_NM_ENG_NEW "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_SURNAME_ENG_NEW.length()>30){
			logger.debug("S1_SURNAME_ENG_NEW "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_SURNAME_ENG_NEW "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_TOPS_ACCT_NO_1.length()>10){
			logger.debug("A1_TOPS_ACCT_NO_1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_TOPS_ACCT_NO_1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_TOPS_ACCT_NO_2.length()>10){
			logger.debug("A1_TOPS_ACCT_NO_2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_TOPS_ACCT_NO_2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_TOPS_ACCT_NO_3.length()>10){
			logger.debug("A1_TOPS_ACCT_NO_3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_TOPS_ACCT_NO_3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_TOPS_ACCT_NO_1.length()>10){
			logger.debug("S1_TOPS_ACCT_NO_1 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_TOPS_ACCT_NO_1 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_TOPS_ACCT_NO_2.length()>10){
			logger.debug("S1_TOPS_ACCT_NO_2 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_TOPS_ACCT_NO_2 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_TOPS_ACCT_NO_3.length()>10){
			logger.debug("S1_TOPS_ACCT_NO_3 "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_TOPS_ACCT_NO_3 "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_SME_GRP.length()>1){
			logger.debug("A1_SME_GRP "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_SME_GRP "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.A1_CC_CUSTNO_LINK_SME.length()>16){
			logger.debug("A1_CC_CUSTNO_LINK_SME "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("A1_CC_CUSTNO_LINK_SME "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.S1_CC_CUSTNO_LINK_SME.length()>16){
			logger.debug("S1_CC_CUSTNO_LINK_SME "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("S1_CC_CUSTNO_LINK_SME "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.APP_RISK_GR_STR_ID.length()>8){
			logger.debug("APP_RISK_GR_STR_ID "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("APP_RISK_GR_STR_ID "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.DTAC_TEL_NO.length()>10){
			logger.debug("DTAC_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("DTAC_TEL_NO "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.TRAN_VAT_CODE.length()>10){
			logger.debug("TRAN_VAT_CODE "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("TRAN_VAT_CODE "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.TRAN__SOURCE_FLAG.length()>1){
			logger.debug("TRAN__SOURCE_FLAG "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("TRAN__SOURCE_FLAG "+CARDLINK_LENGTH_ERROR_MSG);
		}if(cardlink.FILLER.length()>316){
			logger.debug("FILLER "+CARDLINK_LENGTH_ERROR_MSG);
			cardlink.ERROR_MSG.add("FILLER "+CARDLINK_LENGTH_ERROR_MSG);
		}
	}
	private static void validateNull(CardlinkDataM cardlink){
		String CARDLINK_NULL_ERROR_MSG  = InfBatchProperty.getInfBatchConfig("CARDLINK_NULL_ERROR_MSG");
		if(InfBatchUtil.empty(cardlink.TRAN_S_AP_NUM)){
			cardlink.ERROR_MSG.add("TRAN_S_AP_NUM "+CARDLINK_NULL_ERROR_MSG);
		}
		if(InfBatchUtil.empty(cardlink.APP_SOURCE)){
			cardlink.ERROR_MSG.add("APP_SOURCE "+CARDLINK_NULL_ERROR_MSG);
		}
		if(InfBatchUtil.empty(cardlink.BRANCH_CD)){
			cardlink.ERROR_MSG.add("BRANCH_CD "+CARDLINK_NULL_ERROR_MSG);
		}
		if(InfBatchUtil.empty(cardlink.PROD_TY)){
			cardlink.ERROR_MSG.add("PROD_TY "+CARDLINK_NULL_ERROR_MSG);
		}
		if(InfBatchUtil.empty(cardlink.APP_TY)){
			cardlink.ERROR_MSG.add("APP_TY "+CARDLINK_NULL_ERROR_MSG);
		}
		
		if(InfBatchUtil.empty(cardlink.A1_AFF_CRD_CD1) && InfBatchUtil.empty(cardlink.S1_AFF_CRD_CD1)){
			cardlink.ERROR_MSG.add("A1_AFF_CRD_CD1 or S1_AFF_CRD_CD1 "+CARDLINK_NULL_ERROR_MSG);
		}
		if(InfBatchUtil.empty(cardlink.X_A1_EXT_CRDPK_CUST_FL) && InfBatchUtil.empty(cardlink.X_S1_EXT_CRDPK_CUST_FL)){
			cardlink.ERROR_MSG.add("X_A1_EXT_CRDPK_CUST_FL or X_S1_EXT_CRDPK_CUST_FL "+CARDLINK_NULL_ERROR_MSG);
		}
		if(InfBatchUtil.empty(cardlink.X_A1_CC_CUSTNO_CPAC) && InfBatchUtil.empty(cardlink.X_S1_CC_CUSTNO_CPAC)){
			cardlink.ERROR_MSG.add("X_A1_CC_CUSTNO_CPAC or X_S1_CC_CUSTNO_CPAC "+CARDLINK_NULL_ERROR_MSG);
		}
		if(InfBatchUtil.empty(cardlink.X1_CARD_NO1) && InfBatchUtil.empty(cardlink.X_S1_CARD_NO1) 
				&& InfBatchUtil.empty(cardlink.A1_CRDHLDR_NO1) ){
			cardlink.ERROR_MSG.add("X1_CARD_NO1 or X_S1_CARD_NO1 or A1_CRDHLDR_NO1 "+CARDLINK_NULL_ERROR_MSG);
		}
	}
	private static void validatePossibleValue(CardlinkDataM cardlink){
		String CARDLINK_POSSIBLE_VALUE_ERROR_MSG = InfBatchProperty.getInfBatchConfig("CARDLINK_POSSIBLE_VALUE_ERROR_MSG");
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_APP_SOURCE", cardlink.APP_SOURCE)){
			cardlink.ERROR_MSG.add("APP_SOURCE "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"0".equals(cardlink.PBSF_SL_TY)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_PBSF_SL_TY", cardlink.PBSF_SL_TY)){
			cardlink.ERROR_MSG.add("PBSF_SL_TY "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_PROD_TY", cardlink.PROD_TY)){
			cardlink.ERROR_MSG.add("PROD_TY "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_APP_TY", cardlink.APP_TY)){
			cardlink.ERROR_MSG.add("APP_TY "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"0".equals(cardlink.CC_AFF_NM)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_CC_AFF_NM", cardlink.CC_AFF_NM)){
			cardlink.ERROR_MSG.add("CC_AFF_NM "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"00".equals(cardlink.CC_SUBAFF_CD1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_CC_SUBAFF_CD1", cardlink.CC_SUBAFF_CD1)){
			cardlink.ERROR_MSG.add("CC_SUBAFF_CD1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"00".equals(cardlink.CC_SUBAFF_CD2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_CC_SUBAFF_CD2", cardlink.CC_SUBAFF_CD2)){
			cardlink.ERROR_MSG.add("CC_SUBAFF_CD2"+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.CC_NEW_APP_TY)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_CC_NEW_APP_TY", cardlink.CC_NEW_APP_TY)){
			cardlink.ERROR_MSG.add("CC_NEW_APP_TY "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_NEW_CAT1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_CAT1", cardlink.A1_NEW_CAT1)){
			cardlink.ERROR_MSG.add("A1_NEW_CAT1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_NEW_LVL1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_LVL1", cardlink.A1_NEW_LVL1)){
			cardlink.ERROR_MSG.add("A1_NEW_LVL1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_NEW_IDV_CRD_TY1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_IDV_CRD_TY1", cardlink.A1_NEW_IDV_CRD_TY1)){
			cardlink.ERROR_MSG.add("A1_NEW_IDV_CRD_TY1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.A1_AFF_CRD_CD1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_AFF_CRD_CD1", cardlink.A1_AFF_CRD_CD1)){
//			cardlink.ERROR_MSG.add("A1_AFF_CRD_CD1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_CRD_PH_FL1", cardlink.A1_NEW_CRD_PH_FL1)){
			cardlink.ERROR_MSG.add("A1_NEW_CRD_PH_FL1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_NEW_CRD_REQ1", cardlink.S1_NEW_CRD_REQ1)){
			cardlink.ERROR_MSG.add("S1_NEW_CRD_REQ1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_NEW_CRD_PH_FL1", cardlink.S1_NEW_CRD_PH_FL1)){
			cardlink.ERROR_MSG.add("S1_NEW_CRD_PH_FL1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_USGE_LVL1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_USGE_LVL1", cardlink.S1_USGE_LVL1)){
			cardlink.ERROR_MSG.add("S1_USGE_LVL1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_NEW_CAT2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_CAT2", cardlink.A1_NEW_CAT2)){
			cardlink.ERROR_MSG.add("A1_NEW_CAT2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_NEW_LVL2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_LVL2", cardlink.A1_NEW_LVL2)){
			cardlink.ERROR_MSG.add("A1_NEW_LVL2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_NEW_IDV_CRD_TY2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_IDV_CRD_TY2", cardlink.A1_NEW_IDV_CRD_TY2)){
			cardlink.ERROR_MSG.add("A1_NEW_IDV_CRD_TY2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.A1_AFF_CRD_CD2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_AFF_CRD_CD2", cardlink.A1_AFF_CRD_CD2)){
//			cardlink.ERROR_MSG.add("A1_AFF_CRD_CD2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_CRD_PH_FL2", cardlink.A1_NEW_CRD_PH_FL2)){
			cardlink.ERROR_MSG.add("A1_NEW_CRD_PH_FL2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_NEW_CRD_REQ2", cardlink.S1_NEW_CRD_REQ2)){
			cardlink.ERROR_MSG.add("S1_NEW_CRD_REQ2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_NEW_CRD_PH_FL2", cardlink.S1_NEW_CRD_PH_FL2)){
			cardlink.ERROR_MSG.add("S1_NEW_CRD_PH_FL2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_USGE_LVL2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_USGE_LVL2", cardlink.S1_USGE_LVL2)){
			cardlink.ERROR_MSG.add("S1_USGE_LVL2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_NEW_CAT3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_CAT3", cardlink.A1_NEW_CAT3)){
			cardlink.ERROR_MSG.add("A1_NEW_CAT3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_NEW_LVL3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_LVL3", cardlink.A1_NEW_LVL3)){
			cardlink.ERROR_MSG.add("A1_NEW_LVL3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_NEW_IDV_CRD_TY3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_IDV_CRD_TY3", cardlink.A1_NEW_IDV_CRD_TY3)){
			cardlink.ERROR_MSG.add("A1_NEW_IDV_CRD_TY3"+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.A1_AFF_CRD_CD3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_AFF_CRD_CD3", cardlink.A1_AFF_CRD_CD3)){
//			cardlink.ERROR_MSG.add("A1_AFF_CRD_CD3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_NEW_CRD_PH_FL3", cardlink.A1_NEW_CRD_PH_FL3)){
			cardlink.ERROR_MSG.add("A1_NEW_CRD_PH_FL3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_NEW_CRD_REQ3", cardlink.S1_NEW_CRD_REQ3)){
			cardlink.ERROR_MSG.add("S1_NEW_CRD_REQ3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_NEW_CRD_PH_FL3", cardlink.S1_NEW_CRD_PH_FL3)){
			cardlink.ERROR_MSG.add("S1_NEW_CRD_PH_FL3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_USGE_LVL3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_USGE_LVL3", cardlink.S1_USGE_LVL3)){
			cardlink.ERROR_MSG.add("S1_USGE_LVL3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.A1_FIN_INFO_TY)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_INFO_TY", cardlink.A1_FIN_INFO_TY)){
//			cardlink.ERROR_MSG.add("A1_FIN_INFO_TY "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!Util.empty(cardlink.A1_TER_BILL_RT)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_TER_BILL_RT", cardlink.A1_TER_BILL_RT)){
			cardlink.ERROR_MSG.add("A1_TER_BILL_RT "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CURR_SAV_DEP_FI)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CURR_SAV_DEP_FI", cardlink.A1_CURR_SAV_DEP_FI)){
			cardlink.ERROR_MSG.add("A1_CURR_SAV_DEP_FI "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CURR_FXDEP_HLD_FI)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CURR_FXDEP_HLD_FI", cardlink.A1_CURR_FXDEP_HLD_FI)){
			cardlink.ERROR_MSG.add("A1_CURR_FXDEP_HLD_FI "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"00".equals(cardlink.A1_CURR_FXDEP_NTHLD_FI)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CURR_FXDEP_NTHLD_FI", cardlink.A1_CURR_FXDEP_NTHLD_FI)){
			cardlink.ERROR_MSG.add("A1_CURR_FXDEP_NTHLD_FI "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_TITLE_TH", cardlink.A1_TITLE_TH)){
			cardlink.ERROR_MSG.add("A1_TITLE_TH "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_TITLE_ENG", cardlink.A1_TITLE_ENG)){
			cardlink.ERROR_MSG.add("A1_TITLE_ENG "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_MARITAL_STAT)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_MARITAL_STAT", cardlink.A1_MARITAL_STAT)){
			cardlink.ERROR_MSG.add("A1_MARITAL_STAT "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_SEX_STAT)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_SEX_STAT", cardlink.A1_SEX_STAT)){
			cardlink.ERROR_MSG.add("A1_SEX_STAT "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_STAFF_FL)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_STAFF_FL", cardlink.A1_STAFF_FL)){
			cardlink.ERROR_MSG.add("A1_STAFF_FL "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_VIP_CUST_FL)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_VIP_CUST_FL", cardlink.A1_VIP_CUST_FL)){
			cardlink.ERROR_MSG.add("A1_VIP_CUST_FL "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_ID_TY)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_ID_TY", cardlink.A1_ID_TY)){
			cardlink.ERROR_MSG.add("A1_ID_TY "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CURR_RES_STAT)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CURR_RES_STAT", cardlink.A1_CURR_RES_STAT)){
			cardlink.ERROR_MSG.add("A1_CURR_RES_STAT "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_RAD_SAME_CAD)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_RAD_SAME_CAD", cardlink.A1_RAD_SAME_CAD)){
			cardlink.ERROR_MSG.add("A1_RAD_SAME_CAD "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CURR_OCC)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CURR_OCC", cardlink.A1_CURR_OCC)){
			cardlink.ERROR_MSG.add("A1_CURR_OCC "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CURR_CHAR)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CURR_CHAR", cardlink.A1_CURR_CHAR)){
			cardlink.ERROR_MSG.add("A1_CURR_CHAR "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CEMP_POS_LVL)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CEMP_POS_LVL", cardlink.A1_CEMP_POS_LVL)){
			cardlink.ERROR_MSG.add("A1_CEMP_POS_LVL "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_HIGHEST_EDU)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_HIGHEST_EDU", cardlink.A1_HIGHEST_EDU)){
			cardlink.ERROR_MSG.add("A1_HIGHEST_EDU "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_TITLE_TH", cardlink.S1_TITLE_TH)){
			cardlink.ERROR_MSG.add("S1_TITLE_TH "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_TITLE_ENG", cardlink.S1_TITLE_ENG)){
			cardlink.ERROR_MSG.add("S1_TITLE_ENG "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_SEX_STAT)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_SEX_STAT", cardlink.S1_SEX_STAT)){
			cardlink.ERROR_MSG.add("S1_SEX_STAT "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_ID_TY)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_ID_TY", cardlink.S1_ID_TY)){
			cardlink.ERROR_MSG.add("S1_ID_TY "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_STAFF_FL)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_STAFF_FL", cardlink.S1_STAFF_FL)){
			cardlink.ERROR_MSG.add("S1_STAFF_FL "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_VIP_CUST_FL)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_VIP_CUST_FL", cardlink.S1_VIP_CUST_FL)){
			cardlink.ERROR_MSG.add("S1_VIP_CUST_FL "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CC_MAIL_ADDR)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CC_MAIL_ADDR", cardlink.A1_CC_MAIL_ADDR)){
			cardlink.ERROR_MSG.add("A1_CC_MAIL_ADDR "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CC_PLC_REC_CRD", cardlink.A1_CC_PLC_REC_CRD)){
			cardlink.ERROR_MSG.add("A1_CC_PLC_REC_CRD "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CC_PY_COND", cardlink.A1_CC_PY_COND)){
			cardlink.ERROR_MSG.add("A1_CC_PY_COND "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CC_PY_METH", cardlink.A1_CC_PY_METH)){
			cardlink.ERROR_MSG.add("A1_CC_PY_METH "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CC_APA_TY)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CC_APA_TY", cardlink.A1_CC_APA_TY)){
			cardlink.ERROR_MSG.add("A1_CC_APA_TY "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CC_DA_TYP_ATM)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CC_DA_TYP_ATM", cardlink.A1_CC_DA_TYP_ATM)){
			cardlink.ERROR_MSG.add("A1_CC_DA_TYP_ATM "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CC_MAIL_ADDR)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CC_MAIL_ADDR", cardlink.S1_CC_MAIL_ADDR)){
			cardlink.ERROR_MSG.add("S1_CC_MAIL_ADDR "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CC_PLC_REC_CRD)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CC_PLC_REC_CRD", cardlink.S1_CC_PLC_REC_CRD)){
			cardlink.ERROR_MSG.add("S1_CC_PLC_REC_CRD "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CC_PY_COND", cardlink.S1_CC_PY_COND)){
			cardlink.ERROR_MSG.add("S1_CC_PY_COND "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CC_PY_METH", cardlink.S1_CC_PY_METH)){
			cardlink.ERROR_MSG.add("S1_CC_PY_METH "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CC_APA_TY)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CC_APA_TY", cardlink.S1_CC_APA_TY)){
			cardlink.ERROR_MSG.add("S1_CC_APA_TY "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CC_DA_TYP_ATM)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CC_DA_TYP_ATM", cardlink.S1_CC_DA_TYP_ATM)){
			cardlink.ERROR_MSG.add("S1_CC_DA_TYP_ATM "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CITIZN_GOV_PASS_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CITIZN_GOV_PASS_MET", cardlink.A1_CITIZN_GOV_PASS_MET)){
			cardlink.ERROR_MSG.add("A1_CITIZN_GOV_PASS_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_HM_REG_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_HM_REG_MET", cardlink.A1_HM_REG_MET)){
			cardlink.ERROR_MSG.add("A1_HM_REG_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_WRK_PERM_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_WRK_PERM_MET", cardlink.A1_WRK_PERM_MET)){
			cardlink.ERROR_MSG.add("A1_WRK_PERM_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_PASSPORT_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_PASSPORT_MET", cardlink.A1_PASSPORT_MET)){
			cardlink.ERROR_MSG.add("A1_PASSPORT_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_PHOTO_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_PHOTO_MET", cardlink.A1_PHOTO_MET)){
			cardlink.ERROR_MSG.add("A1_PHOTO_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_BNK_STATMNT_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_BNK_STATMNT_MET", cardlink.A1_BNK_STATMNT_MET)){
			cardlink.ERROR_MSG.add("A1_BNK_STATMNT_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_COMP_SHOLDR_LST_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_COMP_SHOLDR_LST_MET", cardlink.A1_COMP_SHOLDR_LST_MET)){
			cardlink.ERROR_MSG.add("A1_COMP_SHOLDR_LST_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_TRAD_COM_REG_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_TRAD_COM_REG_MET", cardlink.A1_TRAD_COM_REG_MET)){
			cardlink.ERROR_MSG.add("A1_TRAD_COM_REG_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_ORIG_INC_SAL_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_ORIG_INC_SAL_MET", cardlink.A1_ORIG_INC_SAL_MET)){
			cardlink.ERROR_MSG.add("A1_ORIG_INC_SAL_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_PHOTO_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_PHOTO_MET", cardlink.S1_PHOTO_MET)){
			cardlink.ERROR_MSG.add("S1_PHOTO_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CITIZN_GOV_PASS_MET)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CITIZN_GOV_PASS_MET", cardlink.S1_CITIZN_GOV_PASS_MET)){
			cardlink.ERROR_MSG.add("S1_CITIZN_GOV_PASS_MET "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_HM_REG_METD_REC_CRD)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_HM_REG_METD_REC_CRD", cardlink.S1_HM_REG_METD_REC_CRD)){
			cardlink.ERROR_MSG.add("S1_HM_REG_METD_REC_CRD "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.X_A1_EXT_CRDPK_CUST_FL)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_A1_EXT_CRDPK_CUST_FL", cardlink.X_A1_EXT_CRDPK_CUST_FL)){
			cardlink.ERROR_MSG.add("X_A1_EXT_CRDPK_CUST_FL "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.X_S1_EXT_CRDPK_CUST_FL)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_S1_EXT_CRDPK_CUST_FL", cardlink.X_S1_EXT_CRDPK_CUST_FL)){
			cardlink.ERROR_MSG.add("X_S1_EXT_CRDPK_CUST_FL "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_FIN_CRD_LVL1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_CRD_LVL1", cardlink.A1_FIN_CRD_LVL1)){
			cardlink.ERROR_MSG.add("A1_FIN_CRD_LVL1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_FIN_CRD_TY1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_CRD_TY1", cardlink.A1_FIN_CRD_TY1)){
			cardlink.ERROR_MSG.add("A1_FIN_CRD_TY1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"00".equals(cardlink.A1_FIN_BILL_CYC1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_BILL_CYC1", cardlink.A1_FIN_BILL_CYC1)){
			cardlink.ERROR_MSG.add("A1_FIN_BILL_CYC1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_FIN_CRD_LVL2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_CRD_LVL2", cardlink.A1_FIN_CRD_LVL2)){
			cardlink.ERROR_MSG.add("A1_FIN_CRD_LVL2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_FIN_CRD_TY2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_CRD_TY2", cardlink.A1_FIN_CRD_TY2)){
			cardlink.ERROR_MSG.add("A1_FIN_CRD_TY2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"00".equals(cardlink.A1_FIN_BILL_CYC2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_BILL_CYC2", cardlink.A1_FIN_BILL_CYC2)){
			cardlink.ERROR_MSG.add("A1_FIN_BILL_CYC2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_FIN_CRD_LVL3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_CRD_LVL3", cardlink.A1_FIN_CRD_LVL3)){
			cardlink.ERROR_MSG.add("A1_FIN_CRD_LVL3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_FIN_CRD_TY3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_CRD_TY3", cardlink.A1_FIN_CRD_TY3)){
			cardlink.ERROR_MSG.add("A1_FIN_CRD_TY3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"00".equals(cardlink.A1_FIN_BILL_CYC3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_FIN_BILL_CYC3", cardlink.A1_FIN_BILL_CYC3)){
			cardlink.ERROR_MSG.add("A1_FIN_BILL_CYC3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_FIN_CRD_LVL1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_FIN_CRD_LVL1", cardlink.S1_FIN_CRD_LVL1)){
			cardlink.ERROR_MSG.add("S1_FIN_CRD_LVL1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_FIN_CRD_TY1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_FIN_CRD_TY1", cardlink.S1_FIN_CRD_TY1)){
			cardlink.ERROR_MSG.add("S1_FIN_CRD_TY1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"00".equals(cardlink.S1_FIN_BILL_CYC1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_FIN_BILL_CYC1", cardlink.S1_FIN_BILL_CYC1)){
			cardlink.ERROR_MSG.add("S1_FIN_BILL_CYC1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_FIN_CRD_LVL2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_FIN_CRD_LVL2", cardlink.S1_FIN_CRD_LVL2)){
			cardlink.ERROR_MSG.add("S1_FIN_CRD_LVL2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_FIN_CRD_TY2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_FIN_CRD_TY2", cardlink.S1_FIN_CRD_TY2)){
			cardlink.ERROR_MSG.add("S1_FIN_CRD_TY2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"00".equals(cardlink.S1_FIN_BILL_CYC2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_FIN_BILL_CYC2", cardlink.S1_FIN_BILL_CYC2)){
			cardlink.ERROR_MSG.add("S1_FIN_BILL_CYC2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_FIN_CRD_LVL3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_FIN_CRD_LVL3", cardlink.S1_FIN_CRD_LVL3)){
			cardlink.ERROR_MSG.add("S1_FIN_CRD_LVL3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"0".equals(cardlink.S1_FIN_CRD_TY3)&&!Util.empty(cardlink.S1_FIN_CRD_TY3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_FIN_CRD_TY3", cardlink.S1_FIN_CRD_TY3)){
			cardlink.ERROR_MSG.add("S1_FIN_CRD_TY3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!"00".equals(cardlink.S1_FIN_BILL_CYC3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_FIN_BILL_CYC3", cardlink.S1_FIN_BILL_CYC3)){
			cardlink.ERROR_MSG.add("S1_FIN_BILL_CYC3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_ANALYST_DEC", cardlink.ANALYST_DEC)){
			cardlink.ERROR_MSG.add("ANALYST_DEC "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_UNDWRT_DEC", cardlink.UNDWRT_DEC)){
			cardlink.ERROR_MSG.add("UNDWRT_DEC "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_G_FIN_DEC", cardlink.G_FIN_DEC)){
			cardlink.ERROR_MSG.add("G_FIN_DEC "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_G_ANLYST_REV_COMP_FL", cardlink.G_ANLYST_REV_COMP_FL)){
			cardlink.ERROR_MSG.add("G_ANLYST_REV_COMP_FL "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_G_UNDWRT_REV_COMP_FL", cardlink.G_UNDWRT_REV_COMP_FL)){
			cardlink.ERROR_MSG.add("G_UNDWRT_REV_COMP_FL "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.X_A1_CRD_CD1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_A1_CRD_CD1", cardlink.X_A1_CRD_CD1)){
//			cardlink.ERROR_MSG.add("X_A1_CRD_CD1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!"00".equals(cardlink.S1_FIN_BILL_CYC3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_A1_CRD_PLSTC_TY1", cardlink.X_A1_CRD_PLSTC_TY1)){
			cardlink.ERROR_MSG.add("S1_FIN_BILL_CYC3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.X_A1_CRD_CD2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_A1_CRD_CD2", cardlink.X_A1_CRD_CD2)){
//			cardlink.ERROR_MSG.add("X_A1_CRD_CD2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!"00".equals(cardlink.X_A1_CRD_PLSTC_TY2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_A1_CRD_PLSTC_TY2", cardlink.X_A1_CRD_PLSTC_TY2)){
			cardlink.ERROR_MSG.add("X_A1_CRD_PLSTC_TY2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_ACT_FL2", cardlink.A1_ACT_FL2)){
			cardlink.ERROR_MSG.add("A1_ACT_FL2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.X_A1_CRD_CD3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_A1_CRD_CD3", cardlink.X_A1_CRD_CD3)){
//			cardlink.ERROR_MSG.add("X_A1_CRD_CD3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!"00".equals(cardlink.X_A1_CRD_PLSTC_TY3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_A1_CRD_PLSTC_TY3", cardlink.X_A1_CRD_PLSTC_TY3)){
			cardlink.ERROR_MSG.add("X_A1_CRD_PLSTC_TY3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_ACT_FL3", cardlink.A1_ACT_FL3)){
			cardlink.ERROR_MSG.add("A1_ACT_FL3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.X_S1_CRD_CD1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_S1_CRD_CD1", cardlink.X_S1_CRD_CD1)){
//			cardlink.ERROR_MSG.add("X_S1_CRD_CD1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!Util.empty(cardlink.X_S1_CRD_PLSTC_TY1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_S1_CRD_PLSTC_TY1", cardlink.X_S1_CRD_PLSTC_TY1)){
			cardlink.ERROR_MSG.add("X_S1_CRD_PLSTC_TY1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_ACT_FL1", cardlink.S1_ACT_FL1)){
			cardlink.ERROR_MSG.add("S1_ACT_FL1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.X_S1_CRD_CD2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_S1_CRD_CD2", cardlink.X_S1_CRD_CD2)){
//			cardlink.ERROR_MSG.add("X_S1_CRD_CD2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!Util.empty(cardlink.X_S1_CRD_PLSTC_TY2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_S1_CRD_PLSTC_TY2", cardlink.X_S1_CRD_PLSTC_TY2)){
			cardlink.ERROR_MSG.add("X_S1_CRD_PLSTC_TY2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_ACT_FL2", cardlink.S1_ACT_FL2)){
			cardlink.ERROR_MSG.add("S1_ACT_FL2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
//		if(!Util.empty(cardlink.X_S1_CRD_CD3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_S1_CRD_CD3", cardlink.X_S1_CRD_CD3)){
//			cardlink.ERROR_MSG.add("X_S1_CRD_CD3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
//		}
		if(!Util.empty(cardlink.X_S1_CRD_PLSTC_TY3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_X_S1_CRD_PLSTC_TY3", cardlink.X_S1_CRD_PLSTC_TY3)){
			cardlink.ERROR_MSG.add("X_S1_CRD_PLSTC_TY3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_ACT_FL3", cardlink.S1_ACT_FL3)){
			cardlink.ERROR_MSG.add("S1_ACT_FL3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CRD_ACT_STAT1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CRD_ACT_STAT1", cardlink.A1_CRD_ACT_STAT1)){
			cardlink.ERROR_MSG.add("A1_CRD_ACT_STAT1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CRD_ACT_STAT2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CRD_ACT_STAT2", cardlink.A1_CRD_ACT_STAT2)){
			cardlink.ERROR_MSG.add("A1_CRD_ACT_STAT2"+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CRD_ACT_STAT3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CRD_ACT_STAT3", cardlink.A1_CRD_ACT_STAT3)){
			cardlink.ERROR_MSG.add("A1_CRD_ACT_STAT3"+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CRD_ACT_STAT1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CRD_ACT_STAT1", cardlink.S1_CRD_ACT_STAT1)){
			cardlink.ERROR_MSG.add("S1_CRD_ACT_STAT1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CRD_ACT_STAT2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CRD_ACT_STAT2", cardlink.S1_CRD_ACT_STAT2)){
			cardlink.ERROR_MSG.add("S1_CRD_ACT_STAT2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CRD_ACT_STAT3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CRD_ACT_STAT3", cardlink.S1_CRD_ACT_STAT3)){
			cardlink.ERROR_MSG.add("S1_CRD_ACT_STAT3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.LAPSE_REAS_CD)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_LAPSE_REAS_CD", cardlink.LAPSE_REAS_CD)){
			cardlink.ERROR_MSG.add("LAPSE_REAS_CD "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.PREM_CHKLST_COMP)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_PREM_CHKLST_COMP", cardlink.PREM_CHKLST_COMP)){
			cardlink.ERROR_MSG.add("PREM_CHKLST_COMP "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.ORRIDE_POSTAPP_SYS_DEC)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_ORRIDE_POSTAPP_SYS_DEC", cardlink.ORRIDE_POSTAPP_SYS_DEC)){
			cardlink.ERROR_MSG.add("ORRIDE_POSTAPP_SYS_DEC "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.POST_APP_ORRIDE_RCODE)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_POST_APP_ORRIDE_RCODE", cardlink.POST_APP_ORRIDE_RCODE)){
			cardlink.ERROR_MSG.add("POST_APP_ORRIDE_RCODE "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.ORRIDE_POSTINT_SYS_DEC)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_ORRIDE_POSTINT_SYS_DEC", cardlink.ORRIDE_POSTINT_SYS_DEC)){
			cardlink.ERROR_MSG.add("ORRIDE_POSTINT_SYS_DEC "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.POST_INT_ORRIDE_RCODE)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_POST_INT_ORRIDE_RCODE", cardlink.POST_INT_ORRIDE_RCODE)){
			cardlink.ERROR_MSG.add("POST_INT_ORRIDE_RCODE "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.ORRIDE_POSTEXT_SYS_DEC)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_ORRIDE_POSTEXT_SYS_DEC", cardlink.ORRIDE_POSTEXT_SYS_DEC)){
			cardlink.ERROR_MSG.add("ORRIDE_POSTEXT_SYS_DEC "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.POST_EXT_ORRIDE_RCODE)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_POST_EXT_ORRIDE_RCODE", cardlink.POST_EXT_ORRIDE_RCODE)){
			cardlink.ERROR_MSG.add("POST_EXT_ORRIDE_RCODE "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CC_CONSENT_FLAG", cardlink.A1_CC_CONSENT_FLAG)){
			cardlink.ERROR_MSG.add("A1_CC_CONSENT_FLAG "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CC_CONSENT_FLAG", cardlink.S1_CC_CONSENT_FLAG)){
			cardlink.ERROR_MSG.add("S1_CC_CONSENT_FLAG "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_DIRECT_MAIL_FLAG)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_DIRECT_MAIL_FLAG", cardlink.A1_DIRECT_MAIL_FLAG)){
			cardlink.ERROR_MSG.add("A1_DIRECT_MAIL_FLAG "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_DIRECT_MAIL_FLAG)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_DIRECT_MAIL_FLAG", cardlink.S1_DIRECT_MAIL_FLAG)){
			cardlink.ERROR_MSG.add("S1_DIRECT_MAIL_FLAG "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CARD_FEE_FLAG1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CARD_FEE_FLAG1", cardlink.A1_CARD_FEE_FLAG1)){
			cardlink.ERROR_MSG.add("A1_CARD_FEE_FLAG1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CARD_FEE_FLAG2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CARD_FEE_FLAG2", cardlink.A1_CARD_FEE_FLAG2)){
			cardlink.ERROR_MSG.add("A1_CARD_FEE_FLAG2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.A1_CARD_FEE_FLAG3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_CARD_FEE_FLAG3", cardlink.A1_CARD_FEE_FLAG3)){
			cardlink.ERROR_MSG.add("A1_CARD_FEE_FLAG3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CARD_FEE_FLAG1)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CARD_FEE_FLAG1", cardlink.S1_CARD_FEE_FLAG1)){
			cardlink.ERROR_MSG.add("S1_CARD_FEE_FLAG1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CARD_FEE_FLAG2)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CARD_FEE_FLAG2", cardlink.S1_CARD_FEE_FLAG2)){
			cardlink.ERROR_MSG.add("S1_CARD_FEE_FLAG2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!Util.empty(cardlink.S1_CARD_FEE_FLAG3)&&!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_CARD_FEE_FLAG3", cardlink.S1_CARD_FEE_FLAG3)){
			cardlink.ERROR_MSG.add("S1_CARD_FEE_FLAG3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_INSUR_FLAG1", cardlink.A1_INSUR_FLAG1)){
			cardlink.ERROR_MSG.add("A1_INSUR_FLAG1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_INSUR_FLAG2", cardlink.A1_INSUR_FLAG2)){
			cardlink.ERROR_MSG.add("A1_INSUR_FLAG2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_A1_INSUR_FLAG3", cardlink.A1_INSUR_FLAG3)){
			cardlink.ERROR_MSG.add("A1_INSUR_FLAG3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_INSUR_FLAG1", cardlink.S1_INSUR_FLAG1)){
			cardlink.ERROR_MSG.add("S1_INSUR_FLAG1 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_INSUR_FLAG2", cardlink.S1_INSUR_FLAG2)){
			cardlink.ERROR_MSG.add("S1_INSUR_FLAG2 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
		if(!InfBatchProperty.lookup("CARDLINK_ACCOUNT_SETUP_S1_INSUR_FLAG3", cardlink.S1_INSUR_FLAG3)){
			cardlink.ERROR_MSG.add("S1_INSUR_FLAG3 "+CARDLINK_POSSIBLE_VALUE_ERROR_MSG);
		}
	}
	public static String validateContent(String contentText){
		String[] lines = contentText.split("\\r?\\n");
		String validateContent = new String();
		for (String line : lines) {
			CardlinkDataM csvDataM = new CardlinkDataM();
			csvDataM.TRAN_OPERATOR = line.substring(0,8);
			csvDataM.TRAN_S_AP_NUM = line.substring(8,17);
			csvDataM.TRAN_S_SYS_DT = line.substring(17,25);
			csvDataM.TRAN_S_SYS_TIME = line.substring(25,31);
			csvDataM.APP_SOURCE = line.substring(31,33);
			csvDataM.BRANCH_CD = line.substring(33,37);
			csvDataM.BRANCH_NM = line.substring(37,87);
			csvDataM.BRANCH_REG_CD = line.substring(87,89);
			csvDataM.BRANCH_ZN_CD = line.substring(89,92);
			csvDataM.HO_CUST_SERV_OFF_CD = line.substring(92,98);
			csvDataM.HO_CUST_SERV_OFF_NM = line.substring(98,148);
			csvDataM.PBSF_STFF_CD = line.substring(148,156);
			csvDataM.PBSF_STFF_NM = line.substring(156,206);
			csvDataM.PBSF_TM_CD = line.substring(206,210);
			csvDataM.PBSF_TM_NM = line.substring(210,250);
			csvDataM.PBSF_REG_CD = line.substring(250,252);
			csvDataM.PBSF_ZN_CD = line.substring(252,255);
			csvDataM.PBSF_SL_TY = line.substring(255,256);
			csvDataM.PROD_TY = line.substring(256,258);
			csvDataM.APP_TY = line.substring(258,259);
			csvDataM.NO_MAIN_CRD_INC_UPGR = line.substring(259,260);
			csvDataM.NO_SUPP_CRD_INC_UPGR = line.substring(260,261);
			csvDataM.CC_APP_NO_STMP = line.substring(261,269);
			csvDataM.CC_PROJECT_CD = line.substring(269,273);
			csvDataM.CC_MEM_GET_MEM_NO = line.substring(273,289);
			csvDataM.CC_AFF_NM = line.substring(289,290);
			csvDataM.CC_SUBAFF_CD1 = line.substring(290,292);
			csvDataM.CC_SUBAFF_CD2 = line.substring(292,294);
			csvDataM.CC_NEW_APP_TY = line.substring(294,295);
			csvDataM.CC_NO_CRDS_REQ = line.substring(295,296);
			csvDataM.A1_NEW_CAT1 = line.substring(296,297);
			csvDataM.A1_NEW_LVL1 = line.substring(297,298);
			csvDataM.A1_NEW_IDV_CRD_TY1 = line.substring(298,299);
			csvDataM.A1_AFF_CRD_CD1 = line.substring(299,302);
			csvDataM.A1_NEW_CRD_PH_FL1 = line.substring(302,304);
			csvDataM.A1_NEW_CRD_LIM_REQ1 = line.substring(304,313);
			csvDataM.S1_NEW_CRD_REQ1 = line.substring(313,314);
			csvDataM.S1_NEW_CRD_PH_FL1 = line.substring(314,316);
			csvDataM.S1_USGE_LVL1 = line.substring(316,317);
			csvDataM.S1_NEW_CRD_LIM_REQ1 = line.substring(317,326);
			csvDataM.A1_CRDHLDR_NO1 = line.substring(326,342);
			csvDataM.S1_CRDHLDR_NO1 = line.substring(342,358);
			csvDataM.A1_NEW_CAT2 = line.substring(358,359);
			csvDataM.A1_NEW_LVL2 = line.substring(359,360);
			csvDataM.A1_NEW_IDV_CRD_TY2 = line.substring(360,361);
			csvDataM.A1_AFF_CRD_CD2 = line.substring(361,364);
			csvDataM.A1_NEW_CRD_PH_FL2 = line.substring(364,366);
			csvDataM.A1_NEW_CRD_LIM_REQ2 = line.substring(366,375);
			csvDataM.S1_NEW_CRD_REQ2 = line.substring(375,376);
			csvDataM.S1_NEW_CRD_PH_FL2 = line.substring(376,378);
			csvDataM.S1_USGE_LVL2 = line.substring(378,379);
			csvDataM.S1_NEW_CRD_LIM_REQ2 = line.substring(379,388);
			csvDataM.A1_CRDHLDR_NO2 = line.substring(388,404);
			csvDataM.S1_CRDHLDR_NO2 = line.substring(404,420);
			csvDataM.A1_NEW_CAT3 = line.substring(420,421);
			csvDataM.A1_NEW_LVL3 = line.substring(421,422);
			csvDataM.A1_NEW_IDV_CRD_TY3 = line.substring(422,423);
			csvDataM.A1_AFF_CRD_CD3 = line.substring(423,426);
			csvDataM.A1_NEW_CRD_PH_FL3 = line.substring(426,428);
			csvDataM.A1_NEW_CRD_LIM_REQ3 = line.substring(428,437);
			csvDataM.S1_NEW_CRD_REQ3 = line.substring(437,438);
			csvDataM.S1_NEW_CRD_PH_FL3 = line.substring(438,440);
			csvDataM.S1_USGE_LVL3 = line.substring(440,441);
			csvDataM.S1_NEW_CRD_LIM_REQ3 = line.substring(441,450);
			csvDataM.A1_CRDHLDR_NO3 = line.substring(450,466);
			csvDataM.S1_CRDHLDR_NO3 = line.substring(466,482);
			csvDataM.A1_FIN_INFO_TY = line.substring(482,483);
			if(line.substring(483,492).matches("[0-9]+")){
				csvDataM.A1_SAV_DEP = new BigDecimal(line.substring(483,492));
			}
			if(line.substring(492,501).matches("[0-9]+")){
				csvDataM.A1_FIX_DEP_HLD = new BigDecimal(line.substring(492,501));
			}
			if(line.substring(501,510).matches("[0-9]+")){
				csvDataM.A1_FIX_DEP_NOT_HLD = new BigDecimal(line.substring(501,510));
			}
			csvDataM.A1_TERT_BILL_CSH_INFLW = line.substring(510,517);
			csvDataM.A1_TERT_BILL_TM_MAT_YY = line.substring(517,519);
			csvDataM.A1_TERT_BILL_TM_MAT_MM = line.substring(519,521);
			csvDataM.A1_TERT_BILL_MAT_TOT = line.substring(521,524);
			csvDataM.A1_TER_BILL_RT = line.substring(524,525);
			csvDataM.A1_CURR_SAV_DEP_FI = line.substring(525,527);
			csvDataM.A1_CURR_FXDEP_HLD_FI = line.substring(527,529);
			csvDataM.A1_CURR_FXDEP_NTHLD_FI = line.substring(529,531);
			if(line.substring(531,540).matches("[0-9]+")){
				csvDataM.S1_FIX_DEP_HLD = new BigDecimal(line.substring(531,540));
			}
			csvDataM.DT_REG_CENTRAL = line.substring(540,548);
			csvDataM.A1_TITLE_TH = line.substring(548,549);
			csvDataM.A1_OTH_TITLE_TH = line.substring(549,567);
			csvDataM.A1_1ST_NM_TH = line.substring(567,597);
			csvDataM.A1_SURNAME_TH = line.substring(597,627);
			csvDataM.A1_TITLE_ENG = line.substring(627,628);
			csvDataM.A1_OTH_TITLE_ENG = line.substring(628,633);
			csvDataM.A1_1ST_NM_ENG = line.substring(633,648);
			csvDataM.A1_SURNAME_ENG = line.substring(648,663);
			csvDataM.A1_EMBOSS_NM_ENG = line.substring(663,689);
			csvDataM.A1_DOB_CE = line.substring(689,697);
			csvDataM.A1_MARITAL_STAT = line.substring(697,698);
			csvDataM.A1_SEX_STAT = line.substring(698,699);
			csvDataM.A1_NO_CHILDREN = line.substring(699,701);
			csvDataM.A1_NO_DEPDNTS = line.substring(701,703);
			csvDataM.A1_STAFF_FL = line.substring(703,704);
			csvDataM.A1_VIP_CUST_FL = line.substring(704,705);
			csvDataM.A1_ID_TY = line.substring(705,706);
			csvDataM.A1_ID_NO = line.substring(706,719);
			csvDataM.A1_WRK_PERM_ISS_NO = line.substring(719,730);
			csvDataM.A1_CURR_RES_STAT = line.substring(730,731);
			csvDataM.A1_CAD_RESIDE_PD_YRS = line.substring(731,733);
			csvDataM.A1_CAD_RESIDE_PD_MTHS = line.substring(733,735);
			csvDataM.A1_CAD_NO = line.substring(735,743);
			csvDataM.A1_CAD_MOO = line.substring(743,745);
			csvDataM.A1_CAD_SOI = line.substring(745,760);
			csvDataM.A1_CAD_ST = line.substring(760,780);
			csvDataM.A1_CAD_SUB_DIST = line.substring(780,795);
			csvDataM.A1_CAD_DIST = line.substring(795,810);
			csvDataM.A1_CAD_PROV = line.substring(810,825);
			csvDataM.A1_CAD_ZIP_CD = line.substring(825,830);
			csvDataM.A1_CAD_TEL_NO = line.substring(830,849);
			csvDataM.A1_CAD_MOB_NO = line.substring(849,859);
			csvDataM.A1_EMAIL = line.substring(859,889);
			csvDataM.A1_RAD_SAME_CAD = line.substring(889,890);
			csvDataM.A1_RAD_NO = line.substring(890,898);
			csvDataM.A1_RAD_MOO = line.substring(898,900);
			csvDataM.A1_RAD_SOI = line.substring(900,915);
			csvDataM.A1_RAD_ST = line.substring(915,935);
			csvDataM.A1_RAD_SUB_DIST = line.substring(935,950);
			csvDataM.A1_RAD_DIST = line.substring(950,965);
			csvDataM.A1_RAD_PROV = line.substring(965,980);
			csvDataM.A1_RAD_ZIP_CD = line.substring(980,985);
			csvDataM.A1_CURR_OCC = line.substring(985,987);
			csvDataM.A1_CURR_CHAR = line.substring(987,989);
			csvDataM.A1_CEMP_NM = line.substring(989,1019);
			csvDataM.A1_CEMP_YRS = line.substring(1019,1021);
			csvDataM.A1_CEMP_MTHS = line.substring(1021,1023);
			csvDataM.A1_CEMP_POS_NM = line.substring(1023,1053);
			csvDataM.A1_CEMP_POS_LVL = line.substring(1053,1055);
			csvDataM.A1_GROSS_MTHLY_SAL = line.substring(1055,1062);
			csvDataM.A1_MTHLY_BUS_INC = line.substring(1062,1069);
			csvDataM.A1_MTHLY_BONUS_INC = line.substring(1069,1076);
			csvDataM.A1_MTHLY_ADD_INC = line.substring(1076,1083);
			csvDataM.A1_HIGHEST_EDU = line.substring(1083,1084);
			csvDataM.A1_CEMP_ADD_BUILD = line.substring(1084,1114);
			csvDataM.A1_CEMP_FLR = line.substring(1114,1116);
			csvDataM.A1_CEMP_DIV = line.substring(1116,1136);
			csvDataM.A1_CEMP_DPTMNT = line.substring(1136,1156);
			csvDataM.A1_CEMP_NO = line.substring(1156,1164);
			csvDataM.A1_CEMP_MOO = line.substring(1164,1166);
			csvDataM.A1_CEMP_SOI = line.substring(1166,1181);
			csvDataM.A1_CEMP_ST = line.substring(1181,1201);
			csvDataM.A1_CEMP_SUB_DIST = line.substring(1201,1216);
			csvDataM.A1_CEMP_DIST = line.substring(1216,1231);
			csvDataM.A1_CEMP_PROV = line.substring(1231,1246);
			csvDataM.A1_CEMP_ZIP_CD = line.substring(1246,1251);
			csvDataM.A1_CEMP_TEL_NO = line.substring(1251,1270);
			csvDataM.A1_VERI_MTHLY_INC = line.substring(1270,1277);
			csvDataM.S1_TITLE_TH = line.substring(1277,1278);
			csvDataM.S1_OTHER_TITLE_TH = line.substring(1278,1296);
			csvDataM.S1_1ST_NM_TH = line.substring(1296,1326);
			csvDataM.S1_SURNAME_TH = line.substring(1326,1356);
			csvDataM.S1_TITLE_ENG = line.substring(1356,1357);
			csvDataM.S1_OTHER_TITLE_ENG = line.substring(1357,1362);
			csvDataM.S1_1ST_NM_ENG = line.substring(1362,1377);
			csvDataM.S1_SURNAME_ENG = line.substring(1377,1392);
			csvDataM.S1_EMBOSS_NM_ENG = line.substring(1392,1418);
			csvDataM.S1_DOB_CE = line.substring(1418,1426);
			csvDataM.S1_SEX_STAT = line.substring(1426,1427);
			csvDataM.S1_ID_TY = line.substring(1427,1428);
			csvDataM.S1_ID_NO = line.substring(1428,1441);
			csvDataM.S1_STAFF_FL = line.substring(1441,1442);
			csvDataM.S1_VIP_CUST_FL = line.substring(1442,1443);
			csvDataM.S1_CAD_NO = line.substring(1443,1451);
			csvDataM.S1_CAD_MOO = line.substring(1451,1453);
			csvDataM.S1_CAD_SOI = line.substring(1453,1468);
			csvDataM.S1_CAD_ST = line.substring(1468,1488);
			csvDataM.S1_CAD_SUB_DIST = line.substring(1488,1503);
			csvDataM.S1_CAD_DIST = line.substring(1503,1518);
			csvDataM.S1_CAD_PROV = line.substring(1518,1533);
			csvDataM.S1_CAD_ZIP_CD = line.substring(1533,1538);
			csvDataM.S1_CAD_TEL_NO = line.substring(1538,1557);
			csvDataM.S1_CAD_MOB_NO = line.substring(1557,1567);
			csvDataM.S1_EMAIL = line.substring(1567,1597);
			csvDataM.S1_CEMP_NM = line.substring(1597,1627);
			csvDataM.S1_CEMP_TEL_NO = line.substring(1627,1646);
			csvDataM.A1_CC_MAIL_ADDR = line.substring(1646,1647);
			csvDataM.A1_CC_PLC_REC_CRD = line.substring(1647,1648);
			csvDataM.A1_CC_BRNCH_CD_REC_CRD = line.substring(1648,1652);
			csvDataM.A1_CC_PY_COND = line.substring(1652,1653);
			csvDataM.A1_CC_PY_METH = line.substring(1653,1654);
			csvDataM.A1_CC_APA_TY = line.substring(1654,1655);
			csvDataM.A1_CC_APA_NO = line.substring(1655,1665);
			csvDataM.A1_CC_DA_TYP_ATM = line.substring(1665,1666);
			csvDataM.A1_CC_SA_NO_ATM = line.substring(1666,1676);
			csvDataM.A1_CC_CA_NO_ATM = line.substring(1676,1686);
			csvDataM.S1_CC_MAIL_ADDR = line.substring(1686,1687);
			csvDataM.S1_CC_PLC_REC_CRD = line.substring(1687,1688);
			csvDataM.S1_CC_BRNCH_CD_REC_CRD = line.substring(1688,1692);
			csvDataM.S1_CC_PY_COND = line.substring(1692,1693);
			csvDataM.S1_CC_PY_METH = line.substring(1693,1694);
			csvDataM.S1_CC_APA_TY = line.substring(1694,1695);
			csvDataM.S1_CC_APA_NO = line.substring(1695,1705);
			csvDataM.S1_CC_DA_TYP_ATM = line.substring(1705,1706);
			csvDataM.S1_CC_SA_NO_ATM = line.substring(1706,1716);
			csvDataM.S1_CC_CA_NO_ATM = line.substring(1716,1726);
			csvDataM.A1_CITIZN_GOV_PASS_MET = line.substring(1726,1727);
			csvDataM.A1_HM_REG_MET = line.substring(1727,1728);
			csvDataM.A1_WRK_PERM_MET = line.substring(1728,1729);
			csvDataM.A1_PASSPORT_MET = line.substring(1729,1730);
			csvDataM.A1_PHOTO_MET = line.substring(1730,1731);
			csvDataM.A1_BNK_STATMNT_MET = line.substring(1731,1732);
			csvDataM.A1_COMP_SHOLDR_LST_MET = line.substring(1732,1733);
			csvDataM.A1_TRAD_COM_REG_MET = line.substring(1733,1734);
			csvDataM.A1_ORIG_INC_SAL_MET = line.substring(1734,1735);
			csvDataM.S1_PHOTO_MET = line.substring(1735,1736);
			csvDataM.S1_CITIZN_GOV_PASS_MET = line.substring(1736,1737);
			csvDataM.S1_HM_REG_METD_REC_CRD = line.substring(1737,1738);
			csvDataM.X_A1_EXT_CRDPK_CUST_FL = line.substring(1738,1739);
			csvDataM.X_A1_NO_CC_LPM = line.substring(1739,1741);
			csvDataM.X_A1_CC_CUSTNO_CPAC = line.substring(1741,1757);
			csvDataM.X_S1_EXT_CRDPK_CUST_FL = line.substring(1757,1758);
			csvDataM.X_S1_CC_CUSTNO_CPAC = line.substring(1758,1774);
			csvDataM.R_DEC_ORG_RSN_CD = line.substring(1774,1778);
			csvDataM.R_DEC_RSN_CD_TBL = line.substring(1778,1858);
			csvDataM.R_APP_RISK_SCR = line.substring(1858,1865);
			csvDataM.X_POL_DEC_1 = line.substring(1865,1897);
			csvDataM.X_POL_DEC_2 = line.substring(1897,1929);
			csvDataM.X_POL_DEC_3 = line.substring(1929,1961);
			csvDataM.X_POL_DEC_4 = line.substring(1961,1993);
			csvDataM.X_POL_DEC_5 = line.substring(1993,2025);
			csvDataM.X_POL_DEC_6 = line.substring(2025,2057);
			csvDataM.X_POL_DEC_7 = line.substring(2057,2089);
			csvDataM.X_POL_DEC_8 = line.substring(2089,2121);
			csvDataM.X_POL_DEC_9 = line.substring(2121,2153);
			csvDataM.X_POL_DEC_10 = line.substring(2153,2185);
			csvDataM.APP_FTHR_REV_RSN_CD = line.substring(2185,2190);
			csvDataM.A1_FIN_CRD_LVL1 = line.substring(2190,2191);
			csvDataM.A1_FIN_CRD_TY1 = line.substring(2191,2192);
			if(line.substring(2192,2201).matches("[0-9]+")){
				csvDataM.A1_FIN_CRD_LIM1 = new BigDecimal(line.substring(2192,2201));
			}
			csvDataM.A1_FIN_BILL_CYC1 = line.substring(2201,2203);
			csvDataM.A1_FIN_CRD_LVL2 = line.substring(2203,2204);
			csvDataM.A1_FIN_CRD_TY2 = line.substring(2204,2205);
			if(line.substring(2205,2214).matches("[0-9]+")){
				csvDataM.A1_FIN_CRD_LIM2 = new BigDecimal(line.substring(2205,2214));
			}
			csvDataM.A1_FIN_BILL_CYC2 = line.substring(2214,2216);
			csvDataM.A1_FIN_CRD_LVL3 = line.substring(2216,2217);
			csvDataM.A1_FIN_CRD_TY3 = line.substring(2217,2218);
			if(line.substring(2218,2227).matches("[0-9]+")){
				csvDataM.A1_FIN_CRD_LIM3 = new BigDecimal(line.substring(2218,2227));
			}
			csvDataM.A1_FIN_BILL_CYC3 = line.substring(2227,2229);
			csvDataM.S1_FIN_CRD_LVL1 = line.substring(2229,2230);
			csvDataM.S1_FIN_CRD_TY1 = line.substring(2230,2231);
			if(line.substring(2231,2240).matches("[0-9]+")){
				csvDataM.S1_FIN_CRD_LIM1 = new BigDecimal(line.substring(2231,2240));
			}
			csvDataM.S1_FIN_BILL_CYC1 = line.substring(2240,2242);
			csvDataM.S1_FIN_CRD_LVL2 = line.substring(2242,2243);
			csvDataM.S1_FIN_CRD_TY2 = line.substring(2243,2244);
			if(line.substring(2244,2253).matches("[0-9]+")){
				csvDataM.S1_FIN_CRD_LIM2 = new BigDecimal(line.substring(2244,2253));
			}
			csvDataM.S1_FIN_BILL_CYC2 = line.substring(2253,2255);
			csvDataM.S1_FIN_CRD_LVL3 = line.substring(2255,2256);
			csvDataM.S1_FIN_CRD_TY3 = line.substring(2256,2257);
			if(line.substring(2257,2266).matches("[0-9]+")){
				csvDataM.S1_FIN_CRD_LIM3 = new BigDecimal(line.substring(2257,2266));
			}
			csvDataM.S1_FIN_BILL_CYC3 = line.substring(2266,2268);
			csvDataM.ANALYST_DEC = line.substring(2268,2269);
			csvDataM.UNDWRT_DEC = line.substring(2269,2270);
			csvDataM.G_FIN_DEC = line.substring(2270,2271);
			csvDataM.G_ANLYST_REV_COMP_FL = line.substring(2271,2272);
			csvDataM.G_UNDWRT_REV_COMP_FL = line.substring(2272,2273);
			csvDataM.X_A1_CRD_CD1 = line.substring(2273,2276);
			csvDataM.X_A1_CRD_PLSTC_TY1 = line.substring(2276,2278);
			csvDataM.A1_CRD_ADD_TRCK1 = line.substring(2278,2293);
			csvDataM.X_A1_CRD_PREF_NO1 = line.substring(2293,2299);
			csvDataM.X_A1_2ND_LINE_EMB1 = line.substring(2299,2325);
			csvDataM.A1_PRIOR_PASS_CRD_NO1 = line.substring(2325,2341);
			csvDataM.A1_ACT_FL1 = line.substring(2341,2342);
			csvDataM.A1_CRD_FEE_DT1 = line.substring(2342,2350);
			csvDataM.X_A1_CRD_CD2 = line.substring(2350,2353);
			csvDataM.X_A1_CRD_PLSTC_TY2 = line.substring(2353,2355);
			csvDataM.A1_CRD_ADD_TRCK2 = line.substring(2355,2370);
			csvDataM.X_A1_CRD_PREF_NO2 = line.substring(2370,2376);
			csvDataM.X_A1_2ND_LINE_EMB2 = line.substring(2376,2402);
			csvDataM.A1_PRIOR_PASS_CRD_NO2 = line.substring(2402,2418);
			csvDataM.A1_ACT_FL2 = line.substring(2418,2419);
			csvDataM.A1_CRD_FEE_DT2 = line.substring(2419,2427);
			csvDataM.X_A1_CRD_CD3 = line.substring(2427,2430);
			csvDataM.X_A1_CRD_PLSTC_TY3 = line.substring(2430,2432);
			csvDataM.A1_CRD_ADD_TRCK3 = line.substring(2432,2447);
			csvDataM.X_A1_CRD_PREF_NO3 = line.substring(2447,2453);
			csvDataM.X_A1_2ND_LINE_EMB3 = line.substring(2453,2479);
			csvDataM.A1_PRIOR_PASS_CRD_NO3 = line.substring(2479,2495);
			csvDataM.A1_ACT_FL3 = line.substring(2495,2496);
			csvDataM.A1_CRD_FEE_DT3 = line.substring(2496,2504);
			csvDataM.X_S1_CRD_CD1 = line.substring(2504,2507);
			csvDataM.X_S1_CRD_PLSTC_TY1 = line.substring(2507,2509);
			csvDataM.S1_CRD_ADD_TRCK1 = line.substring(2509,2524);
			csvDataM.X_S1_CRD_PREF_NO1 = line.substring(2524,2530);
			csvDataM.X_S1_2ND_LINE_EMB1 = line.substring(2530,2556);
			csvDataM.S1_PRIOR_PASS_CRD_NO1 = line.substring(2556,2572);
			csvDataM.S1_ACT_FL1 = line.substring(2572,2573);
			csvDataM.S1_CRD_FEE_DT1 = line.substring(2573,2581);
			csvDataM.X_S1_CRD_CD2 = line.substring(2581,2584);
			csvDataM.X_S1_CRD_PLSTC_TY2 = line.substring(2584,2586);
			csvDataM.S1_CRD_ADD_TRCK2 = line.substring(2586,2601);
			csvDataM.X_S1_CRD_PREF_NO2 = line.substring(2601,2607);
			csvDataM.X_S1_2ND_LINE_EMB2 = line.substring(2607,2633);
			csvDataM.S1_PRIOR_PASS_CRD_NO2 = line.substring(2633,2649);
			csvDataM.S1_ACT_FL2 = line.substring(2649,2650);
			csvDataM.S1_CRD_FEE_DT2 = line.substring(2650,2658);
			csvDataM.X_S1_CRD_CD3 = line.substring(2658,2661);
			csvDataM.X_S1_CRD_PLSTC_TY3 = line.substring(2661,2663);
			csvDataM.S1_CRD_ADD_TRCK3 = line.substring(2663,2678);
			csvDataM.X_S1_CRD_PREF_NO3 = line.substring(2678,2684);
			csvDataM.X_S1_2ND_LINE_EMB3 = line.substring(2684,2710);
			csvDataM.S1_PRIOR_PASS_CRD_NO3 = line.substring(2710,2726);
			csvDataM.S1_ACT_FL3 = line.substring(2726,2727);
			csvDataM.S1_CRD_FEE_DT3 = line.substring(2727,2735);
			csvDataM.A1_CRD_ACT_STAT1 = line.substring(2735,2736);
			csvDataM.A1_CRD_ACT_DT1 = line.substring(2736,2744);
			csvDataM.A1_CRD_ACT_STAT2 = line.substring(2744,2745);
			csvDataM.A1_CRD_ACT_DT2 = line.substring(2745,2753);
			csvDataM.A1_CRD_ACT_STAT3 = line.substring(2753,2754);
			csvDataM.A1_CRD_ACT_DT3 = line.substring(2754,2762);
			csvDataM.S1_CRD_ACT_STAT1 = line.substring(2762,2763);
			csvDataM.S1_CRD_ACT_DT1 = line.substring(2763,2771);
			csvDataM.S1_CRD_ACT_STAT2 = line.substring(2771,2772);
			csvDataM.S1_CRD_ACT_DT2 = line.substring(2772,2780);
			csvDataM.S1_CRD_ACT_STAT3 = line.substring(2780,2781);
			csvDataM.S1_CRD_ACT_DT3 = line.substring(2781,2789);
			csvDataM.LAPSE_REAS_CD = line.substring(2789,2794);
			csvDataM.PREM_CHKLST_COMP = line.substring(2794,2795);
			csvDataM.ORRIDE_POSTAPP_SYS_DEC = line.substring(2795,2796);
			csvDataM.POST_APP_ORRIDE_RCODE = line.substring(2796,2801);
			csvDataM.ORRIDE_POSTINT_SYS_DEC = line.substring(2801,2802);
			csvDataM.POST_INT_ORRIDE_RCODE = line.substring(2802,2807);
			csvDataM.ORRIDE_POSTEXT_SYS_DEC = line.substring(2807,2808);
			csvDataM.POST_EXT_ORRIDE_RCODE = line.substring(2808,2813);
			csvDataM.G_ORG_CD = line.substring(2813,2816);
			csvDataM.A1_CC_CONSENT_FLAG = line.substring(2816,2817);
			csvDataM.A1_DATEOF_CONCENT_CE = line.substring(2817,2825);
			csvDataM.S1_CC_CONSENT_FLAG = line.substring(2825,2826);
			csvDataM.S1_DATEOF_CONCENT_CE = line.substring(2826,2834);
			csvDataM.X1_CARD_NO1 = line.substring(2834,2850);
			csvDataM.X_S1_CARD_NO1 = line.substring(2850,2866);
			csvDataM.X1_CARD_NO2 = line.substring(2866,2882);
			csvDataM.X_S1_CARD_NO2 = line.substring(2882,2898);
			csvDataM.X1_CARD_NO3 = line.substring(2898,2914);
			csvDataM.X_S1_CARD_NO3 = line.substring(2914,2930);
			csvDataM.A1_DIRECT_MAIL_FLAG = line.substring(2930,2931);
			csvDataM.S1_DIRECT_MAIL_FLAG = line.substring(2931,2932);
			csvDataM.G_STAT12_OP_ID = line.substring(2932,2940);
			csvDataM.A1_1ST_NM_ENG_NEW = line.substring(2940,2965);
			csvDataM.A1_SURNAME_ENG_NEW = line.substring(2965,2995);
			csvDataM.S1_1ST_NM_ENG_NEW = line.substring(2995,3020);
			csvDataM.S1_SURNAME_ENG_NEW = line.substring(3020,3050);
			csvDataM.A1_CARD_FEE_FLAG1 = line.substring(3050,3051);
			csvDataM.A1_CARD_FEE_FLAG2 = line.substring(3051,3052);
			csvDataM.A1_CARD_FEE_FLAG3 = line.substring(3052,3053);
			csvDataM.S1_CARD_FEE_FLAG1 = line.substring(3053,3054);
			csvDataM.S1_CARD_FEE_FLAG2 = line.substring(3054,3055);
			csvDataM.S1_CARD_FEE_FLAG3 = line.substring(3055,3056);
			csvDataM.A1_INSUR_FLAG1 = line.substring(3056,3057);
			csvDataM.A1_INSUR_FLAG2 = line.substring(3057,3058);
			csvDataM.A1_INSUR_FLAG3 = line.substring(3058,3059);
			csvDataM.S1_INSUR_FLAG1 = line.substring(3059,3060);
			csvDataM.S1_INSUR_FLAG2 = line.substring(3060,3061);
			csvDataM.S1_INSUR_FLAG3 = line.substring(3061,3062);
			csvDataM.A1_TOPS_ACCT_NO_1 = line.substring(3062,3072);
			csvDataM.A1_TOPS_ACCT_NO_2 = line.substring(3072,3082);
			csvDataM.A1_TOPS_ACCT_NO_3 = line.substring(3082,3092);
			csvDataM.S1_TOPS_ACCT_NO_1 = line.substring(3092,3102);
			csvDataM.S1_TOPS_ACCT_NO_2 = line.substring(3102,3112);
			csvDataM.S1_TOPS_ACCT_NO_3 = line.substring(3112,3122);
			csvDataM.A1_SME_GRP = line.substring(3122,3123);
			csvDataM.A1_CC_CUSTNO_LINK_SME = line.substring(3123,3139);
			csvDataM.S1_CC_CUSTNO_LINK_SME = line.substring(3139,3155);
			csvDataM.APP_RISK_GR_STR_ID = line.substring(3155,3163);
			csvDataM.DTAC_TEL_NO = line.substring(3163,3173);
			csvDataM.TRAN_VAT_CODE = line.substring(3173,3183);
			csvDataM.TRAN__SOURCE_FLAG = line.substring(3183,3184);
			csvDataM.S1_AFF_CRD_CD1 = line.substring(3184,3187);
			csvDataM.S1_AFF_CRD_CD2 = line.substring(3187,3190);
			csvDataM.S1_AFF_CRD_CD3 = line.substring(3190,3193);
			csvDataM.FILLER = line.substring(3194,3500);
			
			if(validateCardlinkData(csvDataM)){
				if(!Util.empty(validateContent)){
					validateContent = addNewLine(validateContent,line);
				}else{
					validateContent = line;
				}
			}
		}
		return validateContent;
	}
	
	public static String notValidateContent(String contentText){
		String[] lines = contentText.split("\\r?\\n");
		String notValidateContent = new String();
		for (String line : lines) {
			CardlinkDataM csvDataM = new CardlinkDataM();
			csvDataM.TRAN_OPERATOR = line.substring(0,8);
			csvDataM.TRAN_S_AP_NUM = line.substring(8,17);
			csvDataM.TRAN_S_SYS_DT = line.substring(17,25);
			csvDataM.TRAN_S_SYS_TIME = line.substring(25,31);
			csvDataM.APP_SOURCE = line.substring(31,33);
			csvDataM.BRANCH_CD = line.substring(33,37);
			csvDataM.BRANCH_NM = line.substring(37,87);
			csvDataM.BRANCH_REG_CD = line.substring(87,89);
			csvDataM.BRANCH_ZN_CD = line.substring(89,92);
			csvDataM.HO_CUST_SERV_OFF_CD = line.substring(92,98);
			csvDataM.HO_CUST_SERV_OFF_NM = line.substring(98,148);
			csvDataM.PBSF_STFF_CD = line.substring(148,156);
			csvDataM.PBSF_STFF_NM = line.substring(156,206);
			csvDataM.PBSF_TM_CD = line.substring(206,210);
			csvDataM.PBSF_TM_NM = line.substring(210,250);
			csvDataM.PBSF_REG_CD = line.substring(250,252);
			csvDataM.PBSF_ZN_CD = line.substring(252,255);
			csvDataM.PBSF_SL_TY = line.substring(255,256);
			csvDataM.PROD_TY = line.substring(256,258);
			csvDataM.APP_TY = line.substring(258,259);
			csvDataM.NO_MAIN_CRD_INC_UPGR = line.substring(259,260);
			csvDataM.NO_SUPP_CRD_INC_UPGR = line.substring(260,261);
			csvDataM.CC_APP_NO_STMP = line.substring(261,269);
			csvDataM.CC_PROJECT_CD = line.substring(269,273);
			csvDataM.CC_MEM_GET_MEM_NO = line.substring(273,289);
			csvDataM.CC_AFF_NM = line.substring(289,290);
			csvDataM.CC_SUBAFF_CD1 = line.substring(290,292);
			csvDataM.CC_SUBAFF_CD2 = line.substring(292,294);
			csvDataM.CC_NEW_APP_TY = line.substring(294,295);
			csvDataM.CC_NO_CRDS_REQ = line.substring(295,296);
			csvDataM.A1_NEW_CAT1 = line.substring(296,297);
			csvDataM.A1_NEW_LVL1 = line.substring(297,298);
			csvDataM.A1_NEW_IDV_CRD_TY1 = line.substring(298,299);
			csvDataM.A1_AFF_CRD_CD1 = line.substring(299,302);
			csvDataM.A1_NEW_CRD_PH_FL1 = line.substring(302,304);
			csvDataM.A1_NEW_CRD_LIM_REQ1 = line.substring(304,313);
			csvDataM.S1_NEW_CRD_REQ1 = line.substring(313,314);
			csvDataM.S1_NEW_CRD_PH_FL1 = line.substring(314,316);
			csvDataM.S1_USGE_LVL1 = line.substring(316,317);
			csvDataM.S1_NEW_CRD_LIM_REQ1 = line.substring(317,326);
			csvDataM.A1_CRDHLDR_NO1 = line.substring(326,342);
			csvDataM.S1_CRDHLDR_NO1 = line.substring(342,358);
			csvDataM.A1_NEW_CAT2 = line.substring(358,359);
			csvDataM.A1_NEW_LVL2 = line.substring(359,360);
			csvDataM.A1_NEW_IDV_CRD_TY2 = line.substring(360,361);
			csvDataM.A1_AFF_CRD_CD2 = line.substring(361,364);
			csvDataM.A1_NEW_CRD_PH_FL2 = line.substring(364,366);
			csvDataM.A1_NEW_CRD_LIM_REQ2 = line.substring(366,375);
			csvDataM.S1_NEW_CRD_REQ2 = line.substring(375,376);
			csvDataM.S1_NEW_CRD_PH_FL2 = line.substring(376,378);
			csvDataM.S1_USGE_LVL2 = line.substring(378,379);
			csvDataM.S1_NEW_CRD_LIM_REQ2 = line.substring(379,388);
			csvDataM.A1_CRDHLDR_NO2 = line.substring(388,404);
			csvDataM.S1_CRDHLDR_NO2 = line.substring(404,420);
			csvDataM.A1_NEW_CAT3 = line.substring(420,421);
			csvDataM.A1_NEW_LVL3 = line.substring(421,422);
			csvDataM.A1_NEW_IDV_CRD_TY3 = line.substring(422,423);
			csvDataM.A1_AFF_CRD_CD3 = line.substring(423,426);
			csvDataM.A1_NEW_CRD_PH_FL3 = line.substring(426,428);
			csvDataM.A1_NEW_CRD_LIM_REQ3 = line.substring(428,437);
			csvDataM.S1_NEW_CRD_REQ3 = line.substring(437,438);
			csvDataM.S1_NEW_CRD_PH_FL3 = line.substring(438,440);
			csvDataM.S1_USGE_LVL3 = line.substring(440,441);
			csvDataM.S1_NEW_CRD_LIM_REQ3 = line.substring(441,450);
			csvDataM.A1_CRDHLDR_NO3 = line.substring(450,466);
			csvDataM.S1_CRDHLDR_NO3 = line.substring(466,482);
			csvDataM.A1_FIN_INFO_TY = line.substring(482,483);
			if(line.substring(483,492).matches("[0-9]+")){
				csvDataM.A1_SAV_DEP = new BigDecimal(line.substring(483,492));
			}
			if(line.substring(492,501).matches("[0-9]+")){
				csvDataM.A1_FIX_DEP_HLD = new BigDecimal(line.substring(492,501));
			}
			if(line.substring(501,510).matches("[0-9]+")){
				csvDataM.A1_FIX_DEP_NOT_HLD = new BigDecimal(line.substring(501,510));
			}
			csvDataM.A1_TERT_BILL_CSH_INFLW = line.substring(510,517);
			csvDataM.A1_TERT_BILL_TM_MAT_YY = line.substring(517,519);
			csvDataM.A1_TERT_BILL_TM_MAT_MM = line.substring(519,521);
			csvDataM.A1_TERT_BILL_MAT_TOT = line.substring(521,524);
			csvDataM.A1_TER_BILL_RT = line.substring(524,525);
			csvDataM.A1_CURR_SAV_DEP_FI = line.substring(525,527);
			csvDataM.A1_CURR_FXDEP_HLD_FI = line.substring(527,529);
			csvDataM.A1_CURR_FXDEP_NTHLD_FI = line.substring(529,531);
			if(line.substring(531,540).matches("[0-9]+")){
				csvDataM.S1_FIX_DEP_HLD = new BigDecimal(line.substring(531,540));
			}
			csvDataM.DT_REG_CENTRAL = line.substring(540,548);
			csvDataM.A1_TITLE_TH = line.substring(548,549);
			csvDataM.A1_OTH_TITLE_TH = line.substring(549,567);
			csvDataM.A1_1ST_NM_TH = line.substring(567,597);
			csvDataM.A1_SURNAME_TH = line.substring(597,627);
			csvDataM.A1_TITLE_ENG = line.substring(627,628);
			csvDataM.A1_OTH_TITLE_ENG = line.substring(628,633);
			csvDataM.A1_1ST_NM_ENG = line.substring(633,648);
			csvDataM.A1_SURNAME_ENG = line.substring(648,663);
			csvDataM.A1_EMBOSS_NM_ENG = line.substring(663,689);
			csvDataM.A1_DOB_CE = line.substring(689,697);
			csvDataM.A1_MARITAL_STAT = line.substring(697,698);
			csvDataM.A1_SEX_STAT = line.substring(698,699);
			csvDataM.A1_NO_CHILDREN = line.substring(699,701);
			csvDataM.A1_NO_DEPDNTS = line.substring(701,703);
			csvDataM.A1_STAFF_FL = line.substring(703,704);
			csvDataM.A1_VIP_CUST_FL = line.substring(704,705);
			csvDataM.A1_ID_TY = line.substring(705,706);
			csvDataM.A1_ID_NO = line.substring(706,719);
			csvDataM.A1_WRK_PERM_ISS_NO = line.substring(719,730);
			csvDataM.A1_CURR_RES_STAT = line.substring(730,731);
			csvDataM.A1_CAD_RESIDE_PD_YRS = line.substring(731,733);
			csvDataM.A1_CAD_RESIDE_PD_MTHS = line.substring(733,735);
			csvDataM.A1_CAD_NO = line.substring(735,743);
			csvDataM.A1_CAD_MOO = line.substring(743,745);
			csvDataM.A1_CAD_SOI = line.substring(745,760);
			csvDataM.A1_CAD_ST = line.substring(760,780);
			csvDataM.A1_CAD_SUB_DIST = line.substring(780,795);
			csvDataM.A1_CAD_DIST = line.substring(795,810);
			csvDataM.A1_CAD_PROV = line.substring(810,825);
			csvDataM.A1_CAD_ZIP_CD = line.substring(825,830);
			csvDataM.A1_CAD_TEL_NO = line.substring(830,849);
			csvDataM.A1_CAD_MOB_NO = line.substring(849,859);
			csvDataM.A1_EMAIL = line.substring(859,889);
			csvDataM.A1_RAD_SAME_CAD = line.substring(889,890);
			csvDataM.A1_RAD_NO = line.substring(890,898);
			csvDataM.A1_RAD_MOO = line.substring(898,900);
			csvDataM.A1_RAD_SOI = line.substring(900,915);
			csvDataM.A1_RAD_ST = line.substring(915,935);
			csvDataM.A1_RAD_SUB_DIST = line.substring(935,950);
			csvDataM.A1_RAD_DIST = line.substring(950,965);
			csvDataM.A1_RAD_PROV = line.substring(965,980);
			csvDataM.A1_RAD_ZIP_CD = line.substring(980,985);
			csvDataM.A1_CURR_OCC = line.substring(985,987);
			csvDataM.A1_CURR_CHAR = line.substring(987,989);
			csvDataM.A1_CEMP_NM = line.substring(989,1019);
			csvDataM.A1_CEMP_YRS = line.substring(1019,1021);
			csvDataM.A1_CEMP_MTHS = line.substring(1021,1023);
			csvDataM.A1_CEMP_POS_NM = line.substring(1023,1053);
			csvDataM.A1_CEMP_POS_LVL = line.substring(1053,1055);
			csvDataM.A1_GROSS_MTHLY_SAL = line.substring(1055,1062);
			csvDataM.A1_MTHLY_BUS_INC = line.substring(1062,1069);
			csvDataM.A1_MTHLY_BONUS_INC = line.substring(1069,1076);
			csvDataM.A1_MTHLY_ADD_INC = line.substring(1076,1083);
			csvDataM.A1_HIGHEST_EDU = line.substring(1083,1084);
			csvDataM.A1_CEMP_ADD_BUILD = line.substring(1084,1114);
			csvDataM.A1_CEMP_FLR = line.substring(1114,1116);
			csvDataM.A1_CEMP_DIV = line.substring(1116,1136);
			csvDataM.A1_CEMP_DPTMNT = line.substring(1136,1156);
			csvDataM.A1_CEMP_NO = line.substring(1156,1164);
			csvDataM.A1_CEMP_MOO = line.substring(1164,1166);
			csvDataM.A1_CEMP_SOI = line.substring(1166,1181);
			csvDataM.A1_CEMP_ST = line.substring(1181,1201);
			csvDataM.A1_CEMP_SUB_DIST = line.substring(1201,1216);
			csvDataM.A1_CEMP_DIST = line.substring(1216,1231);
			csvDataM.A1_CEMP_PROV = line.substring(1231,1246);
			csvDataM.A1_CEMP_ZIP_CD = line.substring(1246,1251);
			csvDataM.A1_CEMP_TEL_NO = line.substring(1251,1270);
			csvDataM.A1_VERI_MTHLY_INC = line.substring(1270,1277);
			csvDataM.S1_TITLE_TH = line.substring(1277,1278);
			csvDataM.S1_OTHER_TITLE_TH = line.substring(1278,1296);
			csvDataM.S1_1ST_NM_TH = line.substring(1296,1326);
			csvDataM.S1_SURNAME_TH = line.substring(1326,1356);
			csvDataM.S1_TITLE_ENG = line.substring(1356,1357);
			csvDataM.S1_OTHER_TITLE_ENG = line.substring(1357,1362);
			csvDataM.S1_1ST_NM_ENG = line.substring(1362,1377);
			csvDataM.S1_SURNAME_ENG = line.substring(1377,1392);
			csvDataM.S1_EMBOSS_NM_ENG = line.substring(1392,1418);
			csvDataM.S1_DOB_CE = line.substring(1418,1426);
			csvDataM.S1_SEX_STAT = line.substring(1426,1427);
			csvDataM.S1_ID_TY = line.substring(1427,1428);
			csvDataM.S1_ID_NO = line.substring(1428,1441);
			csvDataM.S1_STAFF_FL = line.substring(1441,1442);
			csvDataM.S1_VIP_CUST_FL = line.substring(1442,1443);
			csvDataM.S1_CAD_NO = line.substring(1443,1451);
			csvDataM.S1_CAD_MOO = line.substring(1451,1453);
			csvDataM.S1_CAD_SOI = line.substring(1453,1468);
			csvDataM.S1_CAD_ST = line.substring(1468,1488);
			csvDataM.S1_CAD_SUB_DIST = line.substring(1488,1503);
			csvDataM.S1_CAD_DIST = line.substring(1503,1518);
			csvDataM.S1_CAD_PROV = line.substring(1518,1533);
			csvDataM.S1_CAD_ZIP_CD = line.substring(1533,1538);
			csvDataM.S1_CAD_TEL_NO = line.substring(1538,1557);
			csvDataM.S1_CAD_MOB_NO = line.substring(1557,1567);
			csvDataM.S1_EMAIL = line.substring(1567,1597);
			csvDataM.S1_CEMP_NM = line.substring(1597,1627);
			csvDataM.S1_CEMP_TEL_NO = line.substring(1627,1646);
			csvDataM.A1_CC_MAIL_ADDR = line.substring(1646,1647);
			csvDataM.A1_CC_PLC_REC_CRD = line.substring(1647,1648);
			csvDataM.A1_CC_BRNCH_CD_REC_CRD = line.substring(1648,1652);
			csvDataM.A1_CC_PY_COND = line.substring(1652,1653);
			csvDataM.A1_CC_PY_METH = line.substring(1653,1654);
			csvDataM.A1_CC_APA_TY = line.substring(1654,1655);
			csvDataM.A1_CC_APA_NO = line.substring(1655,1665);
			csvDataM.A1_CC_DA_TYP_ATM = line.substring(1665,1666);
			csvDataM.A1_CC_SA_NO_ATM = line.substring(1666,1676);
			csvDataM.A1_CC_CA_NO_ATM = line.substring(1676,1686);
			csvDataM.S1_CC_MAIL_ADDR = line.substring(1686,1687);
			csvDataM.S1_CC_PLC_REC_CRD = line.substring(1687,1688);
			csvDataM.S1_CC_BRNCH_CD_REC_CRD = line.substring(1688,1692);
			csvDataM.S1_CC_PY_COND = line.substring(1692,1693);
			csvDataM.S1_CC_PY_METH = line.substring(1693,1694);
			csvDataM.S1_CC_APA_TY = line.substring(1694,1695);
			csvDataM.S1_CC_APA_NO = line.substring(1695,1705);
			csvDataM.S1_CC_DA_TYP_ATM = line.substring(1705,1706);
			csvDataM.S1_CC_SA_NO_ATM = line.substring(1706,1716);
			csvDataM.S1_CC_CA_NO_ATM = line.substring(1716,1726);
			csvDataM.A1_CITIZN_GOV_PASS_MET = line.substring(1726,1727);
			csvDataM.A1_HM_REG_MET = line.substring(1727,1728);
			csvDataM.A1_WRK_PERM_MET = line.substring(1728,1729);
			csvDataM.A1_PASSPORT_MET = line.substring(1729,1730);
			csvDataM.A1_PHOTO_MET = line.substring(1730,1731);
			csvDataM.A1_BNK_STATMNT_MET = line.substring(1731,1732);
			csvDataM.A1_COMP_SHOLDR_LST_MET = line.substring(1732,1733);
			csvDataM.A1_TRAD_COM_REG_MET = line.substring(1733,1734);
			csvDataM.A1_ORIG_INC_SAL_MET = line.substring(1734,1735);
			csvDataM.S1_PHOTO_MET = line.substring(1735,1736);
			csvDataM.S1_CITIZN_GOV_PASS_MET = line.substring(1736,1737);
			csvDataM.S1_HM_REG_METD_REC_CRD = line.substring(1737,1738);
			csvDataM.X_A1_EXT_CRDPK_CUST_FL = line.substring(1738,1739);
			csvDataM.X_A1_NO_CC_LPM = line.substring(1739,1741);
			csvDataM.X_A1_CC_CUSTNO_CPAC = line.substring(1741,1757);
			csvDataM.X_S1_EXT_CRDPK_CUST_FL = line.substring(1757,1758);
			csvDataM.X_S1_CC_CUSTNO_CPAC = line.substring(1758,1774);
			csvDataM.R_DEC_ORG_RSN_CD = line.substring(1774,1778);
			csvDataM.R_DEC_RSN_CD_TBL = line.substring(1778,1858);
			csvDataM.R_APP_RISK_SCR = line.substring(1858,1865);
			csvDataM.X_POL_DEC_1 = line.substring(1865,1897);
			csvDataM.X_POL_DEC_2 = line.substring(1897,1929);
			csvDataM.X_POL_DEC_3 = line.substring(1929,1961);
			csvDataM.X_POL_DEC_4 = line.substring(1961,1993);
			csvDataM.X_POL_DEC_5 = line.substring(1993,2025);
			csvDataM.X_POL_DEC_6 = line.substring(2025,2057);
			csvDataM.X_POL_DEC_7 = line.substring(2057,2089);
			csvDataM.X_POL_DEC_8 = line.substring(2089,2121);
			csvDataM.X_POL_DEC_9 = line.substring(2121,2153);
			csvDataM.X_POL_DEC_10 = line.substring(2153,2185);
			csvDataM.APP_FTHR_REV_RSN_CD = line.substring(2185,2190);
			csvDataM.A1_FIN_CRD_LVL1 = line.substring(2190,2191);
			csvDataM.A1_FIN_CRD_TY1 = line.substring(2191,2192);
			if(line.substring(2192,2201).matches("[0-9]+")){
				csvDataM.A1_FIN_CRD_LIM1 = new BigDecimal(line.substring(2192,2201));
			}
			csvDataM.A1_FIN_BILL_CYC1 = line.substring(2201,2203);
			csvDataM.A1_FIN_CRD_LVL2 = line.substring(2203,2204);
			csvDataM.A1_FIN_CRD_TY2 = line.substring(2204,2205);
			if(line.substring(2205,2214).matches("[0-9]+")){
				csvDataM.A1_FIN_CRD_LIM2 = new BigDecimal(line.substring(2205,2214));
			}
			csvDataM.A1_FIN_BILL_CYC2 = line.substring(2214,2216);
			csvDataM.A1_FIN_CRD_LVL3 = line.substring(2216,2217);
			csvDataM.A1_FIN_CRD_TY3 = line.substring(2217,2218);
			if(line.substring(2218,2227).matches("[0-9]+")){
				csvDataM.A1_FIN_CRD_LIM3 = new BigDecimal(line.substring(2218,2227));
			}
			csvDataM.A1_FIN_BILL_CYC3 = line.substring(2227,2229);
			csvDataM.S1_FIN_CRD_LVL1 = line.substring(2229,2230);
			csvDataM.S1_FIN_CRD_TY1 = line.substring(2230,2231);
			if(line.substring(2231,2240).matches("[0-9]+")){
				csvDataM.S1_FIN_CRD_LIM1 = new BigDecimal(line.substring(2231,2240));
			}
			csvDataM.S1_FIN_BILL_CYC1 = line.substring(2240,2242);
			csvDataM.S1_FIN_CRD_LVL2 = line.substring(2242,2243);
			csvDataM.S1_FIN_CRD_TY2 = line.substring(2243,2244);
			if(line.substring(2244,2253).matches("[0-9]+")){
				csvDataM.S1_FIN_CRD_LIM2 = new BigDecimal(line.substring(2244,2253));
			}
			csvDataM.S1_FIN_BILL_CYC2 = line.substring(2253,2255);
			csvDataM.S1_FIN_CRD_LVL3 = line.substring(2255,2256);
			csvDataM.S1_FIN_CRD_TY3 = line.substring(2256,2257);
			if(line.substring(2257,2266).matches("[0-9]+")){
				csvDataM.S1_FIN_CRD_LIM3 = new BigDecimal(line.substring(2257,2266));
			}
			csvDataM.S1_FIN_BILL_CYC3 = line.substring(2266,2268);
			csvDataM.ANALYST_DEC = line.substring(2268,2269);
			csvDataM.UNDWRT_DEC = line.substring(2269,2270);
			csvDataM.G_FIN_DEC = line.substring(2270,2271);
			csvDataM.G_ANLYST_REV_COMP_FL = line.substring(2271,2272);
			csvDataM.G_UNDWRT_REV_COMP_FL = line.substring(2272,2273);
			csvDataM.X_A1_CRD_CD1 = line.substring(2273,2276);
			csvDataM.X_A1_CRD_PLSTC_TY1 = line.substring(2276,2278);
			csvDataM.A1_CRD_ADD_TRCK1 = line.substring(2278,2293);
			csvDataM.X_A1_CRD_PREF_NO1 = line.substring(2293,2299);
			csvDataM.X_A1_2ND_LINE_EMB1 = line.substring(2299,2325);
			csvDataM.A1_PRIOR_PASS_CRD_NO1 = line.substring(2325,2341);
			csvDataM.A1_ACT_FL1 = line.substring(2341,2342);
			csvDataM.A1_CRD_FEE_DT1 = line.substring(2342,2350);
			csvDataM.X_A1_CRD_CD2 = line.substring(2350,2353);
			csvDataM.X_A1_CRD_PLSTC_TY2 = line.substring(2353,2355);
			csvDataM.A1_CRD_ADD_TRCK2 = line.substring(2355,2370);
			csvDataM.X_A1_CRD_PREF_NO2 = line.substring(2370,2376);
			csvDataM.X_A1_2ND_LINE_EMB2 = line.substring(2376,2402);
			csvDataM.A1_PRIOR_PASS_CRD_NO2 = line.substring(2402,2418);
			csvDataM.A1_ACT_FL2 = line.substring(2418,2419);
			csvDataM.A1_CRD_FEE_DT2 = line.substring(2419,2427);
			csvDataM.X_A1_CRD_CD3 = line.substring(2427,2430);
			csvDataM.X_A1_CRD_PLSTC_TY3 = line.substring(2430,2432);
			csvDataM.A1_CRD_ADD_TRCK3 = line.substring(2432,2447);
			csvDataM.X_A1_CRD_PREF_NO3 = line.substring(2447,2453);
			csvDataM.X_A1_2ND_LINE_EMB3 = line.substring(2453,2479);
			csvDataM.A1_PRIOR_PASS_CRD_NO3 = line.substring(2479,2495);
			csvDataM.A1_ACT_FL3 = line.substring(2495,2496);
			csvDataM.A1_CRD_FEE_DT3 = line.substring(2496,2504);
			csvDataM.X_S1_CRD_CD1 = line.substring(2504,2507);
			csvDataM.X_S1_CRD_PLSTC_TY1 = line.substring(2507,2509);
			csvDataM.S1_CRD_ADD_TRCK1 = line.substring(2509,2524);
			csvDataM.X_S1_CRD_PREF_NO1 = line.substring(2524,2530);
			csvDataM.X_S1_2ND_LINE_EMB1 = line.substring(2530,2556);
			csvDataM.S1_PRIOR_PASS_CRD_NO1 = line.substring(2556,2572);
			csvDataM.S1_ACT_FL1 = line.substring(2572,2573);
			csvDataM.S1_CRD_FEE_DT1 = line.substring(2573,2581);
			csvDataM.X_S1_CRD_CD2 = line.substring(2581,2584);
			csvDataM.X_S1_CRD_PLSTC_TY2 = line.substring(2584,2586);
			csvDataM.S1_CRD_ADD_TRCK2 = line.substring(2586,2601);
			csvDataM.X_S1_CRD_PREF_NO2 = line.substring(2601,2607);
			csvDataM.X_S1_2ND_LINE_EMB2 = line.substring(2607,2633);
			csvDataM.S1_PRIOR_PASS_CRD_NO2 = line.substring(2633,2649);
			csvDataM.S1_ACT_FL2 = line.substring(2649,2650);
			csvDataM.S1_CRD_FEE_DT2 = line.substring(2650,2658);
			csvDataM.X_S1_CRD_CD3 = line.substring(2658,2661);
			csvDataM.X_S1_CRD_PLSTC_TY3 = line.substring(2661,2663);
			csvDataM.S1_CRD_ADD_TRCK3 = line.substring(2663,2678);
			csvDataM.X_S1_CRD_PREF_NO3 = line.substring(2678,2684);
			csvDataM.X_S1_2ND_LINE_EMB3 = line.substring(2684,2710);
			csvDataM.S1_PRIOR_PASS_CRD_NO3 = line.substring(2710,2726);
			csvDataM.S1_ACT_FL3 = line.substring(2726,2727);
			csvDataM.S1_CRD_FEE_DT3 = line.substring(2727,2735);
			csvDataM.A1_CRD_ACT_STAT1 = line.substring(2735,2736);
			csvDataM.A1_CRD_ACT_DT1 = line.substring(2736,2744);
			csvDataM.A1_CRD_ACT_STAT2 = line.substring(2744,2745);
			csvDataM.A1_CRD_ACT_DT2 = line.substring(2745,2753);
			csvDataM.A1_CRD_ACT_STAT3 = line.substring(2753,2754);
			csvDataM.A1_CRD_ACT_DT3 = line.substring(2754,2762);
			csvDataM.S1_CRD_ACT_STAT1 = line.substring(2762,2763);
			csvDataM.S1_CRD_ACT_DT1 = line.substring(2763,2771);
			csvDataM.S1_CRD_ACT_STAT2 = line.substring(2771,2772);
			csvDataM.S1_CRD_ACT_DT2 = line.substring(2772,2780);
			csvDataM.S1_CRD_ACT_STAT3 = line.substring(2780,2781);
			csvDataM.S1_CRD_ACT_DT3 = line.substring(2781,2789);
			csvDataM.LAPSE_REAS_CD = line.substring(2789,2794);
			csvDataM.PREM_CHKLST_COMP = line.substring(2794,2795);
			csvDataM.ORRIDE_POSTAPP_SYS_DEC = line.substring(2795,2796);
			csvDataM.POST_APP_ORRIDE_RCODE = line.substring(2796,2801);
			csvDataM.ORRIDE_POSTINT_SYS_DEC = line.substring(2801,2802);
			csvDataM.POST_INT_ORRIDE_RCODE = line.substring(2802,2807);
			csvDataM.ORRIDE_POSTEXT_SYS_DEC = line.substring(2807,2808);
			csvDataM.POST_EXT_ORRIDE_RCODE = line.substring(2808,2813);
			csvDataM.G_ORG_CD = line.substring(2813,2816);
			csvDataM.A1_CC_CONSENT_FLAG = line.substring(2816,2817);
			csvDataM.A1_DATEOF_CONCENT_CE = line.substring(2817,2825);
			csvDataM.S1_CC_CONSENT_FLAG = line.substring(2825,2826);
			csvDataM.S1_DATEOF_CONCENT_CE = line.substring(2826,2834);
			csvDataM.X1_CARD_NO1 = line.substring(2834,2850);
			csvDataM.X_S1_CARD_NO1 = line.substring(2850,2866);
			csvDataM.X1_CARD_NO2 = line.substring(2866,2882);
			csvDataM.X_S1_CARD_NO2 = line.substring(2882,2898);
			csvDataM.X1_CARD_NO3 = line.substring(2898,2914);
			csvDataM.X_S1_CARD_NO3 = line.substring(2914,2930);
			csvDataM.A1_DIRECT_MAIL_FLAG = line.substring(2930,2931);
			csvDataM.S1_DIRECT_MAIL_FLAG = line.substring(2931,2932);
			csvDataM.G_STAT12_OP_ID = line.substring(2932,2940);
			csvDataM.A1_1ST_NM_ENG_NEW = line.substring(2940,2965);
			csvDataM.A1_SURNAME_ENG_NEW = line.substring(2965,2995);
			csvDataM.S1_1ST_NM_ENG_NEW = line.substring(2995,3020);
			csvDataM.S1_SURNAME_ENG_NEW = line.substring(3020,3050);
			csvDataM.A1_CARD_FEE_FLAG1 = line.substring(3050,3051);
			csvDataM.A1_CARD_FEE_FLAG2 = line.substring(3051,3052);
			csvDataM.A1_CARD_FEE_FLAG3 = line.substring(3052,3053);
			csvDataM.S1_CARD_FEE_FLAG1 = line.substring(3053,3054);
			csvDataM.S1_CARD_FEE_FLAG2 = line.substring(3054,3055);
			csvDataM.S1_CARD_FEE_FLAG3 = line.substring(3055,3056);
			csvDataM.A1_INSUR_FLAG1 = line.substring(3056,3057);
			csvDataM.A1_INSUR_FLAG2 = line.substring(3057,3058);
			csvDataM.A1_INSUR_FLAG3 = line.substring(3058,3059);
			csvDataM.S1_INSUR_FLAG1 = line.substring(3059,3060);
			csvDataM.S1_INSUR_FLAG2 = line.substring(3060,3061);
			csvDataM.S1_INSUR_FLAG3 = line.substring(3061,3062);
			csvDataM.A1_TOPS_ACCT_NO_1 = line.substring(3062,3072);
			csvDataM.A1_TOPS_ACCT_NO_2 = line.substring(3072,3082);
			csvDataM.A1_TOPS_ACCT_NO_3 = line.substring(3082,3092);
			csvDataM.S1_TOPS_ACCT_NO_1 = line.substring(3092,3102);
			csvDataM.S1_TOPS_ACCT_NO_2 = line.substring(3102,3112);
			csvDataM.S1_TOPS_ACCT_NO_3 = line.substring(3112,3122);
			csvDataM.A1_SME_GRP = line.substring(3122,3123);
			csvDataM.A1_CC_CUSTNO_LINK_SME = line.substring(3123,3139);
			csvDataM.S1_CC_CUSTNO_LINK_SME = line.substring(3139,3155);
			csvDataM.APP_RISK_GR_STR_ID = line.substring(3155,3163);
			csvDataM.DTAC_TEL_NO = line.substring(3163,3173);
			csvDataM.TRAN_VAT_CODE = line.substring(3173,3183);
			csvDataM.TRAN__SOURCE_FLAG = line.substring(3183,3184);
			csvDataM.S1_AFF_CRD_CD1 = line.substring(3184,3187);
			csvDataM.S1_AFF_CRD_CD2 = line.substring(3187,3190);
			csvDataM.S1_AFF_CRD_CD3 = line.substring(3190,3193);
			csvDataM.FILLER = line.substring(3194,3500);
			
			if(!validateCardlinkData(csvDataM)){
				if(!Util.empty(notValidateContent)){
					notValidateContent = addNewLine(notValidateContent,line,csvDataM.ERROR_MSG);
					if(csvDataM.ERROR_MSG.contains("A1_CURR_OCC not validate")){
						logger.debug("csvDataM : "+new Gson().toJson(csvDataM));
					}
				}else{
					notValidateContent = line+csvDataM.ERROR_MSG;
				}
			}
		}
		return notValidateContent;
	}
	
	public static String addNewLine(String text,String textNextLine) {
		String data = new String();
		if(null!=text&&null!=textNextLine){
			data = text+System.lineSeparator()+textNextLine;
		}
		return data;
	}
	
	public static String addNewLine(String text,String textNextLine,List<String> errorMsg) {
		String data = new String();
		if(null!=text&&null!=textNextLine){
			data = text+System.lineSeparator()+textNextLine+errorMsg;
		}
		return data;
	}
}
