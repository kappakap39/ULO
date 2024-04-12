package com.eaf.orig.ulo.model.dih;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class VcCardDataM implements Serializable, Cloneable {
	public VcCardDataM() {
		super();
	}
	private String ACH_F;
	private String ALT_CC_CST_NO;
	private int ALT_CC_CST_ORG_NO;
	private int AVL_BAL;
	private int BILL_CYC;
	private String BLC_CD;
	private Date BLC_DT;
	private String CARD_NO_HASH;
	private String CARD_NO_MASK;
	private int CARD_ORG_NO;
	private int CARD_TP;
	private String CARD_TP_CD;
	private int CASH_ADV_BAL;
	private String CC_CST_NO;
	private int CC_CST_ORG_NO;
	private int CR_LMT_AMT;
	private Date CR_LMT_DT;
	private int CRN_BAL;
	private String EMB_NM1;
	private String EMB_NM2;
	private String EMB_NM3;
	private String EXP_DT;
	private String HIST;
	private Date LAST_PYMT_DT;
	private String MAIN_CC_CST_NO;
	private int MAIN_CC_CST_ORG_NO;
	private Date OPN_DT;
	private int PERM_CR_LMT_AMT;
	private Timestamp PPN_TMS;
	private int SRC_STM_ID;
	private String ST_CD;
	private int TEMP_CR_LMT_AMT;
	private Date TEMP_CR_LMT_EFF_DT;
	private Date TEMP_CR_LMT_EXP_DT;
	private String USR_AC;

	public String getACH_F() {
		return ACH_F;
	}

	public void setACH_F(String aCH_F) {
		ACH_F = aCH_F;
	}

	public String getALT_CC_CST_NO() {
		return ALT_CC_CST_NO;
	}

	public void setALT_CC_CST_NO(String aLT_CC_CST_NO) {
		ALT_CC_CST_NO = aLT_CC_CST_NO;
	}

	public int getALT_CC_CST_ORG_NO() {
		return ALT_CC_CST_ORG_NO;
	}

	public void setALT_CC_CST_ORG_NO(int aLT_CC_CST_ORG_NO) {
		ALT_CC_CST_ORG_NO = aLT_CC_CST_ORG_NO;
	}

	public int getAVL_BAL() {
		return AVL_BAL;
	}

	public void setAVL_BAL(int aVL_BAL) {
		AVL_BAL = aVL_BAL;
	}

	public int getBILL_CYC() {
		return BILL_CYC;
	}

	public void setBILL_CYC(int bILL_CYC) {
		BILL_CYC = bILL_CYC;
	}

	public String getBLC_CD() {
		return BLC_CD;
	}

	public void setBLC_CD(String bLC_CD) {
		BLC_CD = bLC_CD;
	}

	public Date getBLC_DT() {
		return BLC_DT;
	}

	public void setBLC_DT(Date bLC_DT) {
		BLC_DT = bLC_DT;
	}

	public String getCARD_NO_HASH() {
		return CARD_NO_HASH;
	}

	public void setCARD_NO_HASH(String cARD_NO_HASH) {
		CARD_NO_HASH = cARD_NO_HASH;
	}

	public String getCARD_NO_MASK() {
		return CARD_NO_MASK;
	}

	public void setCARD_NO_MASK(String cARD_NO_MASK) {
		CARD_NO_MASK = cARD_NO_MASK;
	}

	public int getCARD_ORG_NO() {
		return CARD_ORG_NO;
	}

	public void setCARD_ORG_NO(int cARD_ORG_NO) {
		CARD_ORG_NO = cARD_ORG_NO;
	}

	public int getCARD_TP() {
		return CARD_TP;
	}

	public void setCARD_TP(int cARD_TP) {
		CARD_TP = cARD_TP;
	}

	public String getCARD_TP_CD() {
		return CARD_TP_CD;
	}

	public void setCARD_TP_CD(String cARD_TP_CD) {
		CARD_TP_CD = cARD_TP_CD;
	}

	public int getCASH_ADV_BAL() {
		return CASH_ADV_BAL;
	}

	public void setCASH_ADV_BAL(int cASH_ADV_BAL) {
		CASH_ADV_BAL = cASH_ADV_BAL;
	}

	public String getCC_CST_NO() {
		return CC_CST_NO;
	}

	public void setCC_CST_NO(String cC_CST_NO) {
		CC_CST_NO = cC_CST_NO;
	}

	public int getCC_CST_ORG_NO() {
		return CC_CST_ORG_NO;
	}

	public void setCC_CST_ORG_NO(int cC_CST_ORG_NO) {
		CC_CST_ORG_NO = cC_CST_ORG_NO;
	}

	public int getCR_LMT_AMT() {
		return CR_LMT_AMT;
	}

	public void setCR_LMT_AMT(int cR_LMT_AMT) {
		CR_LMT_AMT = cR_LMT_AMT;
	}

	public Date getCR_LMT_DT() {
		return CR_LMT_DT;
	}

	public void setCR_LMT_DT(Date cR_LMT_DT) {
		CR_LMT_DT = cR_LMT_DT;
	}

	public int getCRN_BAL() {
		return CRN_BAL;
	}

	public void setCRN_BAL(int cRN_BAL) {
		CRN_BAL = cRN_BAL;
	}

	public String getEMB_NM1() {
		return EMB_NM1;
	}

	public void setEMB_NM1(String eMB_NM1) {
		EMB_NM1 = eMB_NM1;
	}

	public String getEMB_NM2() {
		return EMB_NM2;
	}

	public void setEMB_NM2(String eMB_NM2) {
		EMB_NM2 = eMB_NM2;
	}

	public String getEMB_NM3() {
		return EMB_NM3;
	}

	public void setEMB_NM3(String eMB_NM3) {
		EMB_NM3 = eMB_NM3;
	}

	public String getEXP_DT() {
		return EXP_DT;
	}

	public void setEXP_DT(String eXP_DT) {
		EXP_DT = eXP_DT;
	}

	public String getHIST() {
		return HIST;
	}

	public void setHIST(String hIST) {
		HIST = hIST;
	}

	public Date getLAST_PYMT_DT() {
		return LAST_PYMT_DT;
	}

	public void setLAST_PYMT_DT(Date lAST_PYMT_DT) {
		LAST_PYMT_DT = lAST_PYMT_DT;
	}

	public String getMAIN_CC_CST_NO() {
		return MAIN_CC_CST_NO;
	}

	public void setMAIN_CC_CST_NO(String mAIN_CC_CST_NO) {
		MAIN_CC_CST_NO = mAIN_CC_CST_NO;
	}

	public int getMAIN_CC_CST_ORG_NO() {
		return MAIN_CC_CST_ORG_NO;
	}

	public void setMAIN_CC_CST_ORG_NO(int mAIN_CC_CST_ORG_NO) {
		MAIN_CC_CST_ORG_NO = mAIN_CC_CST_ORG_NO;
	}

	public Date getOPN_DT() {
		return OPN_DT;
	}

	public void setOPN_DT(Date oPN_DT) {
		OPN_DT = oPN_DT;
	}

	public int getPERM_CR_LMT_AMT() {
		return PERM_CR_LMT_AMT;
	}

	public void setPERM_CR_LMT_AMT(int pERM_CR_LMT_AMT) {
		PERM_CR_LMT_AMT = pERM_CR_LMT_AMT;
	}

	public Timestamp getPPN_TMS() {
		return PPN_TMS;
	}

	public void setPPN_TMS(Timestamp pPN_TMS) {
		PPN_TMS = pPN_TMS;
	}

	public int getSRC_STM_ID() {
		return SRC_STM_ID;
	}

	public void setSRC_STM_ID(int sRC_STM_ID) {
		SRC_STM_ID = sRC_STM_ID;
	}

	public String getST_CD() {
		return ST_CD;
	}

	public void setST_CD(String sT_CD) {
		ST_CD = sT_CD;
	}

	public int getTEMP_CR_LMT_AMT() {
		return TEMP_CR_LMT_AMT;
	}

	public void setTEMP_CR_LMT_AMT(int tEMP_CR_LMT_AMT) {
		TEMP_CR_LMT_AMT = tEMP_CR_LMT_AMT;
	}

	public Date getTEMP_CR_LMT_EFF_DT() {
		return TEMP_CR_LMT_EFF_DT;
	}

	public void setTEMP_CR_LMT_EFF_DT(Date tEMP_CR_LMT_EFF_DT) {
		TEMP_CR_LMT_EFF_DT = tEMP_CR_LMT_EFF_DT;
	}

	public Date getTEMP_CR_LMT_EXP_DT() {
		return TEMP_CR_LMT_EXP_DT;
	}

	public void setTEMP_CR_LMT_EXP_DT(Date tEMP_CR_LMT_EXP_DT) {
		TEMP_CR_LMT_EXP_DT = tEMP_CR_LMT_EXP_DT;
	}

	public String getUSR_AC() {
		return USR_AC;
	}

	public void setUSR_AC(String uSR_AC) {
		USR_AC = uSR_AC;
	}

}
