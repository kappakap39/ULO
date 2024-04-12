package com.eaf.inf.batch.ulo.kbmf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.jsefa.csv.annotation.CsvField;

public class CardlinkDataM implements Serializable, Cloneable{
	public CardlinkDataM () {
		super();
	}
	@CsvField(pos = 1)
	String	TRAN_OPERATOR;
	@CsvField(pos = 2)
	String	TRAN_S_AP_NUM;
	@CsvField(pos = 3)
	String	TRAN_S_SYS_DT;
	@CsvField(pos = 4)
	String	TRAN_S_SYS_TIME;
	@CsvField(pos = 5)
	String	APP_SOURCE;
	@CsvField(pos = 6)
	String	BRANCH_CD;
	@CsvField(pos = 7)
	String	BRANCH_NM;
	@CsvField(pos = 8)
	String	BRANCH_REG_CD;
	@CsvField(pos = 9)
	String	BRANCH_ZN_CD;
	@CsvField(pos = 10)
	String	HO_CUST_SERV_OFF_CD;
	@CsvField(pos = 11)
	String	HO_CUST_SERV_OFF_NM;
	@CsvField(pos = 12)
	String	PBSF_STFF_CD;
	@CsvField(pos = 13)
	String	PBSF_STFF_NM;
	@CsvField(pos = 14)
	String	PBSF_TM_CD;
	@CsvField(pos = 15)
	String	PBSF_TM_NM;
	@CsvField(pos = 16)
	String	PBSF_REG_CD;
	@CsvField(pos = 17)
	String	PBSF_ZN_CD;
	@CsvField(pos = 18)
	String	PBSF_SL_TY;
	@CsvField(pos = 19)
	String	PROD_TY;
	@CsvField(pos = 20)
	String	APP_TY;
	@CsvField(pos = 21)
	String	NO_MAIN_CRD_INC_UPGR;
	@CsvField(pos = 22)
	String	NO_SUPP_CRD_INC_UPGR;
	@CsvField(pos = 23)
	String	CC_APP_NO_STMP;
	@CsvField(pos = 24)
	String	CC_PROJECT_CD;
	@CsvField(pos = 25)
	String	CC_MEM_GET_MEM_NO;
	@CsvField(pos = 26)
	String	CC_AFF_NM;
	@CsvField(pos = 27)
	String	CC_SUBAFF_CD1;
	@CsvField(pos = 28)
	String	CC_SUBAFF_CD2;
	@CsvField(pos = 29)
	String	CC_NEW_APP_TY;
	@CsvField(pos = 30)
	String	CC_NO_CRDS_REQ;
	@CsvField(pos = 31)
	String	A1_NEW_CAT1;
	@CsvField(pos = 32)
	String	A1_NEW_LVL1;
	@CsvField(pos = 33)
	String	A1_NEW_IDV_CRD_TY1;
	@CsvField(pos = 34)
	String	A1_AFF_CRD_CD1;
	@CsvField(pos = 35)
	String	A1_NEW_CRD_PH_FL1;
	@CsvField(pos = 36)
	String	A1_NEW_CRD_LIM_REQ1;
	@CsvField(pos = 37)
	String	S1_NEW_CRD_REQ1;
	@CsvField(pos = 38)
	String	S1_NEW_CRD_PH_FL1;
	@CsvField(pos = 39)
	String	S1_USGE_LVL1;
	@CsvField(pos = 40)
	String	S1_NEW_CRD_LIM_REQ1;
	@CsvField(pos = 41)
	String	A1_CRDHLDR_NO1;
	@CsvField(pos = 42)
	String	S1_CRDHLDR_NO1;
	@CsvField(pos = 43)
	String	A1_NEW_CAT2;
	@CsvField(pos = 44)
	String	A1_NEW_LVL2;
	@CsvField(pos = 45)
	String	A1_NEW_IDV_CRD_TY2;
	@CsvField(pos = 46)
	String	A1_AFF_CRD_CD2;
	@CsvField(pos = 47)
	String	A1_NEW_CRD_PH_FL2;
	@CsvField(pos = 48)
	String	A1_NEW_CRD_LIM_REQ2;
	@CsvField(pos = 49)
	String	S1_NEW_CRD_REQ2;
	@CsvField(pos = 50)
	String	S1_NEW_CRD_PH_FL2;
	@CsvField(pos = 51)
	String	S1_USGE_LVL2;
	@CsvField(pos = 52)
	String	S1_NEW_CRD_LIM_REQ2;
	@CsvField(pos = 53)
	String	A1_CRDHLDR_NO2;
	@CsvField(pos = 54)
	String	S1_CRDHLDR_NO2;
	@CsvField(pos = 55)
	String	A1_NEW_CAT3;
	@CsvField(pos = 56)
	String	A1_NEW_LVL3;
	@CsvField(pos = 57)
	String	A1_NEW_IDV_CRD_TY3;
	@CsvField(pos = 58)
	String	A1_AFF_CRD_CD3;
	@CsvField(pos = 59)
	String	A1_NEW_CRD_PH_FL3;
	@CsvField(pos = 60)
	String	A1_NEW_CRD_LIM_REQ3;
	@CsvField(pos = 61)
	String	S1_NEW_CRD_REQ3;
	@CsvField(pos = 62)
	String	S1_NEW_CRD_PH_FL3;
	@CsvField(pos = 63)
	String	S1_USGE_LVL3;
	@CsvField(pos = 64)
	String	S1_NEW_CRD_LIM_REQ3;
	@CsvField(pos = 65)
	String	A1_CRDHLDR_NO3;
	@CsvField(pos = 66)
	String	S1_CRDHLDR_NO3;
	@CsvField(pos = 67)
	String	A1_FIN_INFO_TY;
	@CsvField(pos = 68)
	BigDecimal	A1_SAV_DEP;
	@CsvField(pos = 69)
	BigDecimal	A1_FIX_DEP_HLD;
	@CsvField(pos = 70)
	BigDecimal	A1_FIX_DEP_NOT_HLD;
	@CsvField(pos = 71)
	String	A1_TERT_BILL_CSH_INFLW;
	@CsvField(pos = 72)
	String	A1_TERT_BILL_TM_MAT_YY;
	@CsvField(pos = 73)
	String	A1_TERT_BILL_TM_MAT_MM;
	@CsvField(pos = 74)
	String	A1_TERT_BILL_MAT_TOT;
	@CsvField(pos = 75)
	String	A1_TER_BILL_RT;
	@CsvField(pos = 76)
	String	A1_CURR_SAV_DEP_FI;
	@CsvField(pos = 77)
	String	A1_CURR_FXDEP_HLD_FI;
	@CsvField(pos = 78)
	String	A1_CURR_FXDEP_NTHLD_FI;
	@CsvField(pos = 79)
	BigDecimal	S1_FIX_DEP_HLD;
	@CsvField(pos = 80)
	String	DT_REG_CENTRAL;
	@CsvField(pos = 81)
	String	A1_TITLE_TH;
	@CsvField(pos = 82)
	String	A1_OTH_TITLE_TH;
	@CsvField(pos = 83)
	String	A1_1ST_NM_TH;
	@CsvField(pos = 84)
	String	A1_SURNAME_TH;
	@CsvField(pos = 85)
	String	A1_TITLE_ENG;
	@CsvField(pos = 86)
	String	A1_OTH_TITLE_ENG;
	@CsvField(pos = 87)
	String	A1_1ST_NM_ENG;
	@CsvField(pos = 88)
	String	A1_SURNAME_ENG;
	@CsvField(pos = 89)
	String	A1_EMBOSS_NM_ENG;
	@CsvField(pos = 90)
	String	A1_DOB_CE;
	@CsvField(pos = 91)
	String	A1_MARITAL_STAT;
	@CsvField(pos = 92)
	String	A1_SEX_STAT;
	@CsvField(pos = 93)
	String	A1_NO_CHILDREN;
	@CsvField(pos = 94)
	String	A1_NO_DEPDNTS;
	@CsvField(pos = 95)
	String	A1_STAFF_FL;
	@CsvField(pos = 96)
	String	A1_VIP_CUST_FL;
	@CsvField(pos = 97)
	String	A1_ID_TY;
	@CsvField(pos = 98)
	String	A1_ID_NO;
	@CsvField(pos = 99)
	String	A1_WRK_PERM_ISS_NO;
	@CsvField(pos = 100)
	String	A1_CURR_RES_STAT;
	@CsvField(pos = 101)
	String	A1_CAD_RESIDE_PD_YRS;
	@CsvField(pos = 102)
	String	A1_CAD_RESIDE_PD_MTHS;
	@CsvField(pos = 103)
	String	A1_CAD_NO;
	@CsvField(pos = 104)
	String	A1_CAD_MOO;
	@CsvField(pos = 105)
	String	A1_CAD_SOI;
	@CsvField(pos = 106)
	String	A1_CAD_ST;
	@CsvField(pos = 107)
	String	A1_CAD_SUB_DIST;
	@CsvField(pos = 108)
	String	A1_CAD_DIST;
	@CsvField(pos = 109)
	String	A1_CAD_PROV;
	@CsvField(pos = 110)
	String	A1_CAD_ZIP_CD;
	@CsvField(pos = 111)
	String	A1_CAD_TEL_NO;
	@CsvField(pos = 112)
	String	A1_CAD_MOB_NO;
	@CsvField(pos = 113)
	String	A1_EMAIL;
	@CsvField(pos = 114)
	String	A1_RAD_SAME_CAD;
	@CsvField(pos = 115)
	String	A1_RAD_NO;
	@CsvField(pos = 116)
	String	A1_RAD_MOO;
	@CsvField(pos = 117)
	String	A1_RAD_SOI;
	@CsvField(pos = 118)
	String	A1_RAD_ST;
	@CsvField(pos = 119)
	String	A1_RAD_SUB_DIST;
	@CsvField(pos = 120)
	String	A1_RAD_DIST;
	@CsvField(pos = 121)
	String	A1_RAD_PROV;
	@CsvField(pos = 122)
	String	A1_RAD_ZIP_CD;
	@CsvField(pos = 123)
	String	A1_CURR_OCC;
	@CsvField(pos = 124)
	String	A1_CURR_CHAR;
	@CsvField(pos = 125)
	String	A1_CEMP_NM;
	@CsvField(pos = 126)
	String	A1_CEMP_YRS;
	@CsvField(pos = 127)
	String	A1_CEMP_MTHS;
	@CsvField(pos = 128)
	String	A1_CEMP_POS_NM;
	@CsvField(pos = 129)
	String	A1_CEMP_POS_LVL;
	@CsvField(pos = 130)
	String	A1_GROSS_MTHLY_SAL;
	@CsvField(pos = 131)
	String	A1_MTHLY_BUS_INC;
	@CsvField(pos = 132)
	String	A1_MTHLY_BONUS_INC;
	@CsvField(pos = 133)
	String	A1_MTHLY_ADD_INC;
	@CsvField(pos = 134)
	String	A1_HIGHEST_EDU;
	@CsvField(pos = 135)
	String	A1_CEMP_ADD_BUILD;
	@CsvField(pos = 136)
	String	A1_CEMP_FLR;
	@CsvField(pos = 137)
	String	A1_CEMP_DIV;
	@CsvField(pos = 138)
	String	A1_CEMP_DPTMNT;
	@CsvField(pos = 139)
	String	A1_CEMP_NO;
	@CsvField(pos = 140)
	String	A1_CEMP_MOO;
	@CsvField(pos = 141)
	String	A1_CEMP_SOI;
	@CsvField(pos = 142)
	String	A1_CEMP_ST;
	@CsvField(pos = 143)
	String	A1_CEMP_SUB_DIST;
	@CsvField(pos = 144)
	String	A1_CEMP_DIST;
	@CsvField(pos = 145)
	String	A1_CEMP_PROV;
	@CsvField(pos = 146)
	String	A1_CEMP_ZIP_CD;
	@CsvField(pos = 147)
	String	A1_CEMP_TEL_NO;
	@CsvField(pos = 148)
	String	A1_VERI_MTHLY_INC;
	@CsvField(pos = 149)
	String	S1_TITLE_TH;
	@CsvField(pos = 150)
	String	S1_OTHER_TITLE_TH;
	@CsvField(pos = 151)
	String	S1_1ST_NM_TH;
	@CsvField(pos = 152)
	String	S1_SURNAME_TH;
	@CsvField(pos = 153)
	String	S1_TITLE_ENG;
	@CsvField(pos = 154)
	String	S1_OTHER_TITLE_ENG;
	@CsvField(pos = 155)
	String	S1_1ST_NM_ENG;
	@CsvField(pos = 156)
	String	S1_SURNAME_ENG;
	@CsvField(pos = 157)
	String	S1_EMBOSS_NM_ENG;
	@CsvField(pos = 158)
	String	S1_DOB_CE;
	@CsvField(pos = 159)
	String	S1_SEX_STAT;
	@CsvField(pos = 160)
	String	S1_ID_TY;
	@CsvField(pos = 161)
	String	S1_ID_NO;
	@CsvField(pos = 162)
	String	S1_STAFF_FL;
	@CsvField(pos = 163)
	String	S1_VIP_CUST_FL;
	@CsvField(pos = 164)
	String	S1_CAD_NO;
	@CsvField(pos = 165)
	String	S1_CAD_MOO;
	@CsvField(pos = 166)
	String	S1_CAD_SOI;
	@CsvField(pos = 167)
	String	S1_CAD_ST;
	@CsvField(pos = 168)
	String	S1_CAD_SUB_DIST;
	@CsvField(pos = 169)
	String	S1_CAD_DIST;
	@CsvField(pos = 170)
	String	S1_CAD_PROV;
	@CsvField(pos = 171)
	String	S1_CAD_ZIP_CD;
	@CsvField(pos = 172)
	String	S1_CAD_TEL_NO;
	@CsvField(pos = 173)
	String	S1_CAD_MOB_NO;
	@CsvField(pos = 174)
	String	S1_EMAIL;
	@CsvField(pos = 175)
	String	S1_CEMP_NM;
	@CsvField(pos = 176)
	String	S1_CEMP_TEL_NO;
	@CsvField(pos = 177)
	String	A1_CC_MAIL_ADDR;
	@CsvField(pos = 178)
	String	A1_CC_PLC_REC_CRD;
	@CsvField(pos = 179)
	String	A1_CC_BRNCH_CD_REC_CRD;
	@CsvField(pos = 180)
	String	A1_CC_PY_COND;
	@CsvField(pos = 181)
	String	A1_CC_PY_METH;
	@CsvField(pos = 182)
	String	A1_CC_APA_TY;
	@CsvField(pos = 183)
	String	A1_CC_APA_NO;
	@CsvField(pos = 184)
	String	A1_CC_DA_TYP_ATM;
	@CsvField(pos = 185)
	String	A1_CC_SA_NO_ATM;
	@CsvField(pos = 186)
	String	A1_CC_CA_NO_ATM;
	@CsvField(pos = 187)
	String	S1_CC_MAIL_ADDR;
	@CsvField(pos = 188)
	String	S1_CC_PLC_REC_CRD;
	@CsvField(pos = 189)
	String	S1_CC_BRNCH_CD_REC_CRD;
	@CsvField(pos = 190)
	String	S1_CC_PY_COND;
	@CsvField(pos = 191)
	String	S1_CC_PY_METH;
	@CsvField(pos = 192)
	String	S1_CC_APA_TY;
	@CsvField(pos = 193)
	String	S1_CC_APA_NO;
	@CsvField(pos = 194)
	String	S1_CC_DA_TYP_ATM;
	@CsvField(pos = 195)
	String	S1_CC_SA_NO_ATM;
	@CsvField(pos = 196)
	String	S1_CC_CA_NO_ATM;
	@CsvField(pos = 197)
	String	A1_CITIZN_GOV_PASS_MET;
	@CsvField(pos = 198)
	String	A1_HM_REG_MET;
	@CsvField(pos = 199)
	String	A1_WRK_PERM_MET;
	@CsvField(pos = 200)
	String	A1_PASSPORT_MET;
	@CsvField(pos = 201)
	String	A1_PHOTO_MET;
	@CsvField(pos = 202)
	String	A1_BNK_STATMNT_MET;
	@CsvField(pos = 203)
	String	A1_COMP_SHOLDR_LST_MET;
	@CsvField(pos = 204)
	String	A1_TRAD_COM_REG_MET;
	@CsvField(pos = 205)
	String	A1_ORIG_INC_SAL_MET;
	@CsvField(pos = 206)
	String	S1_PHOTO_MET;
	@CsvField(pos = 207)
	String	S1_CITIZN_GOV_PASS_MET;
	@CsvField(pos = 208)
	String	S1_HM_REG_METD_REC_CRD;
	@CsvField(pos = 209)
	String	X_A1_EXT_CRDPK_CUST_FL;
	@CsvField(pos = 210)
	String	X_A1_NO_CC_LPM;
	@CsvField(pos = 211)
	String	X_A1_CC_CUSTNO_CPAC;
	@CsvField(pos = 212)
	String	X_S1_EXT_CRDPK_CUST_FL;
	@CsvField(pos = 213)
	String	X_S1_CC_CUSTNO_CPAC;
	@CsvField(pos = 214)
	String	R_DEC_ORG_RSN_CD;
	@CsvField(pos = 215)
	String	R_DEC_RSN_CD_TBL;
	@CsvField(pos = 216)
	String	R_APP_RISK_SCR;
	@CsvField(pos = 217)
	String	X_POL_DEC_1;
	@CsvField(pos = 218)
	String	X_POL_DEC_2;
	@CsvField(pos = 219)
	String	X_POL_DEC_3;
	@CsvField(pos = 220)
	String	X_POL_DEC_4;
	@CsvField(pos = 221)
	String	X_POL_DEC_5;
	@CsvField(pos = 222)
	String	X_POL_DEC_6;
	@CsvField(pos = 223)
	String	X_POL_DEC_7;
	@CsvField(pos = 224)
	String	X_POL_DEC_8;
	@CsvField(pos = 225)
	String	X_POL_DEC_9;
	@CsvField(pos = 226)
	String	X_POL_DEC_10;
	@CsvField(pos = 227)
	String	APP_FTHR_REV_RSN_CD;
	@CsvField(pos = 228)
	String	A1_FIN_CRD_LVL1;
	@CsvField(pos = 229)
	String	A1_FIN_CRD_TY1;
	@CsvField(pos = 230)
	BigDecimal	A1_FIN_CRD_LIM1;
	@CsvField(pos = 231)
	String	A1_FIN_BILL_CYC1;
	@CsvField(pos = 232)
	String	A1_FIN_CRD_LVL2;
	@CsvField(pos = 233)
	String	A1_FIN_CRD_TY2;
	@CsvField(pos = 234)
	BigDecimal	A1_FIN_CRD_LIM2;
	@CsvField(pos = 235)
	String	A1_FIN_BILL_CYC2;
	@CsvField(pos = 236)
	String	A1_FIN_CRD_LVL3;
	@CsvField(pos = 237)
	String	A1_FIN_CRD_TY3;
	@CsvField(pos = 238)
	BigDecimal	A1_FIN_CRD_LIM3;
	@CsvField(pos = 239)
	String	A1_FIN_BILL_CYC3;
	@CsvField(pos = 240)
	String	S1_FIN_CRD_LVL1;
	@CsvField(pos = 241)
	String	S1_FIN_CRD_TY1;
	@CsvField(pos = 242)
	BigDecimal	S1_FIN_CRD_LIM1;
	@CsvField(pos = 243)
	String	S1_FIN_BILL_CYC1;
	@CsvField(pos = 244)
	String	S1_FIN_CRD_LVL2;
	@CsvField(pos = 245)
	String	S1_FIN_CRD_TY2;
	@CsvField(pos = 246)
	BigDecimal	S1_FIN_CRD_LIM2;
	@CsvField(pos = 247)
	String	S1_FIN_BILL_CYC2;
	@CsvField(pos = 248)
	String	S1_FIN_CRD_LVL3;
	@CsvField(pos = 249)
	String	S1_FIN_CRD_TY3;
	@CsvField(pos = 250)
	BigDecimal	S1_FIN_CRD_LIM3;
	@CsvField(pos = 251)
	String	S1_FIN_BILL_CYC3;
	@CsvField(pos = 252)
	String	ANALYST_DEC;
	@CsvField(pos = 253)
	String	UNDWRT_DEC;
	@CsvField(pos = 254)
	String	G_FIN_DEC;
	@CsvField(pos = 255)
	String	G_ANLYST_REV_COMP_FL;
	@CsvField(pos = 256)
	String	G_UNDWRT_REV_COMP_FL;
	@CsvField(pos = 257)
	String	X_A1_CRD_CD1;
	@CsvField(pos = 258)
	String	X_A1_CRD_PLSTC_TY1;
	@CsvField(pos = 259)
	String	A1_CRD_ADD_TRCK1;
	@CsvField(pos = 260)
	String	X_A1_CRD_PREF_NO1;
	@CsvField(pos = 261)
	String	X_A1_2ND_LINE_EMB1;
	@CsvField(pos = 262)
	String	A1_PRIOR_PASS_CRD_NO1;
	@CsvField(pos = 263)
	String	A1_ACT_FL1;
	@CsvField(pos = 264)
	String	A1_CRD_FEE_DT1;
	@CsvField(pos = 265)
	String	X_A1_CRD_CD2;
	@CsvField(pos = 266)
	String	X_A1_CRD_PLSTC_TY2;
	@CsvField(pos = 267)
	String	A1_CRD_ADD_TRCK2;
	@CsvField(pos = 268)
	String	X_A1_CRD_PREF_NO2;
	@CsvField(pos = 269)
	String	X_A1_2ND_LINE_EMB2;
	@CsvField(pos = 270)
	String	A1_PRIOR_PASS_CRD_NO2;
	@CsvField(pos = 271)
	String	A1_ACT_FL2;
	@CsvField(pos = 272)
	String	A1_CRD_FEE_DT2;
	@CsvField(pos = 273)
	String	X_A1_CRD_CD3;
	@CsvField(pos = 274)
	String	X_A1_CRD_PLSTC_TY3;
	@CsvField(pos = 275)
	String	A1_CRD_ADD_TRCK3;
	@CsvField(pos = 276)
	String	X_A1_CRD_PREF_NO3;
	@CsvField(pos = 277)
	String	X_A1_2ND_LINE_EMB3;
	@CsvField(pos = 278)
	String	A1_PRIOR_PASS_CRD_NO3;
	@CsvField(pos = 279)
	String	A1_ACT_FL3;
	@CsvField(pos = 280)
	String	A1_CRD_FEE_DT3;
	@CsvField(pos = 281)
	String	X_S1_CRD_CD1;
	@CsvField(pos = 282)
	String	X_S1_CRD_PLSTC_TY1;
	@CsvField(pos = 283)
	String	S1_CRD_ADD_TRCK1;
	@CsvField(pos = 284)
	String	X_S1_CRD_PREF_NO1;
	@CsvField(pos = 285)
	String	X_S1_2ND_LINE_EMB1;
	@CsvField(pos = 286)
	String	S1_PRIOR_PASS_CRD_NO1;
	@CsvField(pos = 287)
	String	S1_ACT_FL1;
	@CsvField(pos = 288)
	String	S1_CRD_FEE_DT1;
	@CsvField(pos = 289)
	String	X_S1_CRD_CD2;
	@CsvField(pos = 290)
	String	X_S1_CRD_PLSTC_TY2;
	@CsvField(pos = 291)
	String	S1_CRD_ADD_TRCK2;
	@CsvField(pos = 292)
	String	X_S1_CRD_PREF_NO2;
	@CsvField(pos = 293)
	String	X_S1_2ND_LINE_EMB2;
	@CsvField(pos = 294)
	String	S1_PRIOR_PASS_CRD_NO2;
	@CsvField(pos = 295)
	String	S1_ACT_FL2;
	@CsvField(pos = 296)
	String	S1_CRD_FEE_DT2;
	@CsvField(pos = 297)
	String	X_S1_CRD_CD3;
	@CsvField(pos = 298)
	String	X_S1_CRD_PLSTC_TY3;
	@CsvField(pos = 299)
	String	S1_CRD_ADD_TRCK3;
	@CsvField(pos = 300)
	String	X_S1_CRD_PREF_NO3;
	@CsvField(pos = 301)
	String	X_S1_2ND_LINE_EMB3;
	@CsvField(pos = 302)
	String	S1_PRIOR_PASS_CRD_NO3;
	@CsvField(pos = 303)
	String	S1_ACT_FL3;
	@CsvField(pos = 304)
	String	S1_CRD_FEE_DT3;
	@CsvField(pos = 305)
	String	A1_CRD_ACT_STAT1;
	@CsvField(pos = 306)
	String	A1_CRD_ACT_DT1;
	@CsvField(pos = 307)
	String	A1_CRD_ACT_STAT2;
	@CsvField(pos = 308)
	String	A1_CRD_ACT_DT2;
	@CsvField(pos = 309)
	String	A1_CRD_ACT_STAT3;
	@CsvField(pos = 310)
	String	A1_CRD_ACT_DT3;
	@CsvField(pos = 311)
	String	S1_CRD_ACT_STAT1;
	@CsvField(pos = 312)
	String	S1_CRD_ACT_DT1;
	@CsvField(pos = 313)
	String	S1_CRD_ACT_STAT2;
	@CsvField(pos = 314)
	String	S1_CRD_ACT_DT2;
	@CsvField(pos = 315)
	String	S1_CRD_ACT_STAT3;
	@CsvField(pos = 316)
	String	S1_CRD_ACT_DT3;
	@CsvField(pos = 317)
	String	LAPSE_REAS_CD;
	@CsvField(pos = 318)
	String	PREM_CHKLST_COMP;
	@CsvField(pos = 319)
	String	ORRIDE_POSTAPP_SYS_DEC;
	@CsvField(pos = 320)
	String	POST_APP_ORRIDE_RCODE;
	@CsvField(pos = 321)
	String	ORRIDE_POSTINT_SYS_DEC;
	@CsvField(pos = 322)
	String	POST_INT_ORRIDE_RCODE;
	@CsvField(pos = 323)
	String	ORRIDE_POSTEXT_SYS_DEC;
	@CsvField(pos = 324)
	String	POST_EXT_ORRIDE_RCODE;
	@CsvField(pos = 325)
	String	G_ORG_CD;
	@CsvField(pos = 326)
	String	A1_CC_CONSENT_FLAG;
	@CsvField(pos = 327)
	String	A1_DATEOF_CONCENT_CE;
	@CsvField(pos = 328)
	String	S1_CC_CONSENT_FLAG;
	@CsvField(pos = 329)
	String	S1_DATEOF_CONCENT_CE;
	@CsvField(pos = 330)
	String	X1_CARD_NO1;
	@CsvField(pos = 331)
	String	X_S1_CARD_NO1;
	@CsvField(pos = 332)
	String	X1_CARD_NO2;
	@CsvField(pos = 333)
	String	X_S1_CARD_NO2;
	@CsvField(pos = 334)
	String	X1_CARD_NO3;
	@CsvField(pos = 335)
	String	X_S1_CARD_NO3;
	@CsvField(pos = 336)
	String	A1_DIRECT_MAIL_FLAG;
	@CsvField(pos = 337)
	String	S1_DIRECT_MAIL_FLAG;
	@CsvField(pos = 338)
	String	G_STAT12_OP_ID;
	@CsvField(pos = 339)
	String	A1_1ST_NM_ENG_NEW;
	@CsvField(pos = 340)
	String	A1_SURNAME_ENG_NEW;
	@CsvField(pos = 341)
	String	S1_1ST_NM_ENG_NEW;
	@CsvField(pos = 342)
	String	S1_SURNAME_ENG_NEW;
	@CsvField(pos = 343)
	String	A1_CARD_FEE_FLAG1;
	@CsvField(pos = 344)
	String	A1_CARD_FEE_FLAG2;
	@CsvField(pos = 345)
	String	A1_CARD_FEE_FLAG3;
	@CsvField(pos = 346)
	String	S1_CARD_FEE_FLAG1;
	@CsvField(pos = 347)
	String	S1_CARD_FEE_FLAG2;
	@CsvField(pos = 348)
	String	S1_CARD_FEE_FLAG3;
	@CsvField(pos = 349)
	String	A1_INSUR_FLAG1;
	@CsvField(pos = 350)
	String	A1_INSUR_FLAG2;
	@CsvField(pos = 351)
	String	A1_INSUR_FLAG3;
	@CsvField(pos = 352)
	String	S1_INSUR_FLAG1;
	@CsvField(pos = 353)
	String	S1_INSUR_FLAG2;
	@CsvField(pos = 354)
	String	S1_INSUR_FLAG3;
	@CsvField(pos = 355)
	String	A1_TOPS_ACCT_NO_1;
	@CsvField(pos = 356)
	String	A1_TOPS_ACCT_NO_2;
	@CsvField(pos = 357)
	String	A1_TOPS_ACCT_NO_3;
	@CsvField(pos = 358)
	String	S1_TOPS_ACCT_NO_1;
	@CsvField(pos = 359)
	String	S1_TOPS_ACCT_NO_2;
	@CsvField(pos = 360)
	String	S1_TOPS_ACCT_NO_3;
	@CsvField(pos = 361)
	String	A1_SME_GRP;
	@CsvField(pos = 362)
	String	A1_CC_CUSTNO_LINK_SME;
	@CsvField(pos = 363)
	String	S1_CC_CUSTNO_LINK_SME;
	@CsvField(pos = 364)
	String	APP_RISK_GR_STR_ID;
	@CsvField(pos = 365)
	String	DTAC_TEL_NO;
	@CsvField(pos = 366)
	String	TRAN_VAT_CODE;
	@CsvField(pos = 367)
	String	TRAN__SOURCE_FLAG;
	@CsvField(pos = 368)
	String	S1_AFF_CRD_CD1;
	@CsvField(pos = 369)
	String	S1_AFF_CRD_CD2;
	@CsvField(pos = 370)
	String	S1_AFF_CRD_CD3;
	@CsvField(pos = 371)
	String	FILLER;
	List<String>	ERROR_MSG;
	
	public String getTRAN_OPERATOR() {
		return TRAN_OPERATOR;
	}
	public void setTRAN_OPERATOR(String tRAN_OPERATOR) {
		TRAN_OPERATOR = tRAN_OPERATOR;
	}
	public String getTRAN_S_AP_NUM() {
		return TRAN_S_AP_NUM;
	}
	public void setTRAN_S_AP_NUM(String tRAN_S_AP_NUM) {
		TRAN_S_AP_NUM = tRAN_S_AP_NUM;
	}
	public String getTRAN_S_SYS_DT() {
		return TRAN_S_SYS_DT;
	}
	public void setTRAN_S_SYS_DT(String tRAN_S_SYS_DT) {
		TRAN_S_SYS_DT = tRAN_S_SYS_DT;
	}
	public String getTRAN_S_SYS_TIME() {
		return TRAN_S_SYS_TIME;
	}
	public void setTRAN_S_SYS_TIME(String tRAN_S_SYS_TIME) {
		TRAN_S_SYS_TIME = tRAN_S_SYS_TIME;
	}
	public String getAPP_SOURCE() {
		return APP_SOURCE;
	}
	public void setAPP_SOURCE(String aPP_SOURCE) {
		APP_SOURCE = aPP_SOURCE;
	}
	public String getBRANCH_CD() {
		return BRANCH_CD;
	}
	public void setBRANCH_CD(String bRANCH_CD) {
		BRANCH_CD = bRANCH_CD;
	}
	public String getBRANCH_NM() {
		return BRANCH_NM;
	}
	public void setBRANCH_NM(String bRANCH_NM) {
		BRANCH_NM = bRANCH_NM;
	}
	public String getBRANCH_REG_CD() {
		return BRANCH_REG_CD;
	}
	public void setBRANCH_REG_CD(String bRANCH_REG_CD) {
		BRANCH_REG_CD = bRANCH_REG_CD;
	}
	public String getBRANCH_ZN_CD() {
		return BRANCH_ZN_CD;
	}
	public void setBRANCH_ZN_CD(String bRANCH_ZN_CD) {
		BRANCH_ZN_CD = bRANCH_ZN_CD;
	}
	public String getHO_CUST_SERV_OFF_CD() {
		return HO_CUST_SERV_OFF_CD;
	}
	public void setHO_CUST_SERV_OFF_CD(String hO_CUST_SERV_OFF_CD) {
		HO_CUST_SERV_OFF_CD = hO_CUST_SERV_OFF_CD;
	}
	public String getHO_CUST_SERV_OFF_NM() {
		return HO_CUST_SERV_OFF_NM;
	}
	public void setHO_CUST_SERV_OFF_NM(String hO_CUST_SERV_OFF_NM) {
		HO_CUST_SERV_OFF_NM = hO_CUST_SERV_OFF_NM;
	}
	public String getPBSF_STFF_CD() {
		return PBSF_STFF_CD;
	}
	public void setPBSF_STFF_CD(String pBSF_STFF_CD) {
		PBSF_STFF_CD = pBSF_STFF_CD;
	}
	public String getPBSF_STFF_NM() {
		return PBSF_STFF_NM;
	}
	public void setPBSF_STFF_NM(String pBSF_STFF_NM) {
		PBSF_STFF_NM = pBSF_STFF_NM;
	}
	public String getPBSF_TM_CD() {
		return PBSF_TM_CD;
	}
	public void setPBSF_TM_CD(String pBSF_TM_CD) {
		PBSF_TM_CD = pBSF_TM_CD;
	}
	public String getPBSF_TM_NM() {
		return PBSF_TM_NM;
	}
	public void setPBSF_TM_NM(String pBSF_TM_NM) {
		PBSF_TM_NM = pBSF_TM_NM;
	}
	public String getPBSF_REG_CD() {
		return PBSF_REG_CD;
	}
	public void setPBSF_REG_CD(String pBSF_REG_CD) {
		PBSF_REG_CD = pBSF_REG_CD;
	}
	public String getPBSF_ZN_CD() {
		return PBSF_ZN_CD;
	}
	public void setPBSF_ZN_CD(String pBSF_ZN_CD) {
		PBSF_ZN_CD = pBSF_ZN_CD;
	}
	public String getPBSF_SL_TY() {
		return PBSF_SL_TY;
	}
	public void setPBSF_SL_TY(String pBSF_SL_TY) {
		PBSF_SL_TY = pBSF_SL_TY;
	}
	public String getPROD_TY() {
		return PROD_TY;
	}
	public void setPROD_TY(String pROD_TY) {
		PROD_TY = pROD_TY;
	}
	public String getAPP_TY() {
		return APP_TY;
	}
	public void setAPP_TY(String aPP_TY) {
		APP_TY = aPP_TY;
	}
	public String getNO_MAIN_CRD_INC_UPGR() {
		return NO_MAIN_CRD_INC_UPGR;
	}
	public void setNO_MAIN_CRD_INC_UPGR(String nO_MAIN_CRD_INC_UPGR) {
		NO_MAIN_CRD_INC_UPGR = nO_MAIN_CRD_INC_UPGR;
	}
	public String getNO_SUPP_CRD_INC_UPGR() {
		return NO_SUPP_CRD_INC_UPGR;
	}
	public void setNO_SUPP_CRD_INC_UPGR(String nO_SUPP_CRD_INC_UPGR) {
		NO_SUPP_CRD_INC_UPGR = nO_SUPP_CRD_INC_UPGR;
	}
	public String getCC_APP_NO_STMP() {
		return CC_APP_NO_STMP;
	}
	public void setCC_APP_NO_STMP(String cC_APP_NO_STMP) {
		CC_APP_NO_STMP = cC_APP_NO_STMP;
	}
	public String getCC_PROJECT_CD() {
		return CC_PROJECT_CD;
	}
	public void setCC_PROJECT_CD(String cC_PROJECT_CD) {
		CC_PROJECT_CD = cC_PROJECT_CD;
	}
	public String getCC_MEM_GET_MEM_NO() {
		return CC_MEM_GET_MEM_NO;
	}
	public void setCC_MEM_GET_MEM_NO(String cC_MEM_GET_MEM_NO) {
		CC_MEM_GET_MEM_NO = cC_MEM_GET_MEM_NO;
	}
	public String getCC_AFF_NM() {
		return CC_AFF_NM;
	}
	public void setCC_AFF_NM(String cC_AFF_NM) {
		CC_AFF_NM = cC_AFF_NM;
	}
	public String getCC_SUBAFF_CD1() {
		return CC_SUBAFF_CD1;
	}
	public void setCC_SUBAFF_CD1(String cC_SUBAFF_CD1) {
		CC_SUBAFF_CD1 = cC_SUBAFF_CD1;
	}
	public String getCC_SUBAFF_CD2() {
		return CC_SUBAFF_CD2;
	}
	public void setCC_SUBAFF_CD2(String cC_SUBAFF_CD2) {
		CC_SUBAFF_CD2 = cC_SUBAFF_CD2;
	}
	public String getCC_NEW_APP_TY() {
		return CC_NEW_APP_TY;
	}
	public void setCC_NEW_APP_TY(String cC_NEW_APP_TY) {
		CC_NEW_APP_TY = cC_NEW_APP_TY;
	}
	public String getCC_NO_CRDS_REQ() {
		return CC_NO_CRDS_REQ;
	}
	public void setCC_NO_CRDS_REQ(String cC_NO_CRDS_REQ) {
		CC_NO_CRDS_REQ = cC_NO_CRDS_REQ;
	}
	public String getA1_NEW_CAT1() {
		return A1_NEW_CAT1;
	}
	public void setA1_NEW_CAT1(String a1_NEW_CAT1) {
		A1_NEW_CAT1 = a1_NEW_CAT1;
	}
	public String getA1_NEW_LVL1() {
		return A1_NEW_LVL1;
	}
	public void setA1_NEW_LVL1(String a1_NEW_LVL1) {
		A1_NEW_LVL1 = a1_NEW_LVL1;
	}
	public String getA1_NEW_IDV_CRD_TY1() {
		return A1_NEW_IDV_CRD_TY1;
	}
	public void setA1_NEW_IDV_CRD_TY1(String a1_NEW_IDV_CRD_TY1) {
		A1_NEW_IDV_CRD_TY1 = a1_NEW_IDV_CRD_TY1;
	}
	public String getA1_AFF_CRD_CD1() {
		return A1_AFF_CRD_CD1;
	}
	public void setA1_AFF_CRD_CD1(String a1_AFF_CRD_CD1) {
		A1_AFF_CRD_CD1 = a1_AFF_CRD_CD1;
	}
	public String getA1_NEW_CRD_PH_FL1() {
		return A1_NEW_CRD_PH_FL1;
	}
	public void setA1_NEW_CRD_PH_FL1(String a1_NEW_CRD_PH_FL1) {
		A1_NEW_CRD_PH_FL1 = a1_NEW_CRD_PH_FL1;
	}
	public String getA1_NEW_CRD_LIM_REQ1() {
		return A1_NEW_CRD_LIM_REQ1;
	}
	public void setA1_NEW_CRD_LIM_REQ1(String a1_NEW_CRD_LIM_REQ1) {
		A1_NEW_CRD_LIM_REQ1 = a1_NEW_CRD_LIM_REQ1;
	}
	public String getS1_NEW_CRD_REQ1() {
		return S1_NEW_CRD_REQ1;
	}
	public void setS1_NEW_CRD_REQ1(String s1_NEW_CRD_REQ1) {
		S1_NEW_CRD_REQ1 = s1_NEW_CRD_REQ1;
	}
	public String getS1_NEW_CRD_PH_FL1() {
		return S1_NEW_CRD_PH_FL1;
	}
	public void setS1_NEW_CRD_PH_FL1(String s1_NEW_CRD_PH_FL1) {
		S1_NEW_CRD_PH_FL1 = s1_NEW_CRD_PH_FL1;
	}
	public String getS1_USGE_LVL1() {
		return S1_USGE_LVL1;
	}
	public void setS1_USGE_LVL1(String s1_USGE_LVL1) {
		S1_USGE_LVL1 = s1_USGE_LVL1;
	}
	public String getS1_NEW_CRD_LIM_REQ1() {
		return S1_NEW_CRD_LIM_REQ1;
	}
	public void setS1_NEW_CRD_LIM_REQ1(String s1_NEW_CRD_LIM_REQ1) {
		S1_NEW_CRD_LIM_REQ1 = s1_NEW_CRD_LIM_REQ1;
	}
	public String getA1_CRDHLDR_NO1() {
		return A1_CRDHLDR_NO1;
	}
	public void setA1_CRDHLDR_NO1(String a1_CRDHLDR_NO1) {
		A1_CRDHLDR_NO1 = a1_CRDHLDR_NO1;
	}
	public String getS1_CRDHLDR_NO1() {
		return S1_CRDHLDR_NO1;
	}
	public void setS1_CRDHLDR_NO1(String s1_CRDHLDR_NO1) {
		S1_CRDHLDR_NO1 = s1_CRDHLDR_NO1;
	}
	public String getA1_NEW_CAT2() {
		return A1_NEW_CAT2;
	}
	public void setA1_NEW_CAT2(String a1_NEW_CAT2) {
		A1_NEW_CAT2 = a1_NEW_CAT2;
	}
	public String getA1_NEW_LVL2() {
		return A1_NEW_LVL2;
	}
	public void setA1_NEW_LVL2(String a1_NEW_LVL2) {
		A1_NEW_LVL2 = a1_NEW_LVL2;
	}
	public String getA1_NEW_IDV_CRD_TY2() {
		return A1_NEW_IDV_CRD_TY2;
	}
	public void setA1_NEW_IDV_CRD_TY2(String a1_NEW_IDV_CRD_TY2) {
		A1_NEW_IDV_CRD_TY2 = a1_NEW_IDV_CRD_TY2;
	}
	public String getA1_AFF_CRD_CD2() {
		return A1_AFF_CRD_CD2;
	}
	public void setA1_AFF_CRD_CD2(String a1_AFF_CRD_CD2) {
		A1_AFF_CRD_CD2 = a1_AFF_CRD_CD2;
	}
	public String getA1_NEW_CRD_PH_FL2() {
		return A1_NEW_CRD_PH_FL2;
	}
	public void setA1_NEW_CRD_PH_FL2(String a1_NEW_CRD_PH_FL2) {
		A1_NEW_CRD_PH_FL2 = a1_NEW_CRD_PH_FL2;
	}
	public String getA1_NEW_CRD_LIM_REQ2() {
		return A1_NEW_CRD_LIM_REQ2;
	}
	public void setA1_NEW_CRD_LIM_REQ2(String a1_NEW_CRD_LIM_REQ2) {
		A1_NEW_CRD_LIM_REQ2 = a1_NEW_CRD_LIM_REQ2;
	}
	public String getS1_NEW_CRD_REQ2() {
		return S1_NEW_CRD_REQ2;
	}
	public void setS1_NEW_CRD_REQ2(String s1_NEW_CRD_REQ2) {
		S1_NEW_CRD_REQ2 = s1_NEW_CRD_REQ2;
	}
	public String getS1_NEW_CRD_PH_FL2() {
		return S1_NEW_CRD_PH_FL2;
	}
	public void setS1_NEW_CRD_PH_FL2(String s1_NEW_CRD_PH_FL2) {
		S1_NEW_CRD_PH_FL2 = s1_NEW_CRD_PH_FL2;
	}
	public String getS1_USGE_LVL2() {
		return S1_USGE_LVL2;
	}
	public void setS1_USGE_LVL2(String s1_USGE_LVL2) {
		S1_USGE_LVL2 = s1_USGE_LVL2;
	}
	public String getS1_NEW_CRD_LIM_REQ2() {
		return S1_NEW_CRD_LIM_REQ2;
	}
	public void setS1_NEW_CRD_LIM_REQ2(String s1_NEW_CRD_LIM_REQ2) {
		S1_NEW_CRD_LIM_REQ2 = s1_NEW_CRD_LIM_REQ2;
	}
	public String getA1_CRDHLDR_NO2() {
		return A1_CRDHLDR_NO2;
	}
	public void setA1_CRDHLDR_NO2(String a1_CRDHLDR_NO2) {
		A1_CRDHLDR_NO2 = a1_CRDHLDR_NO2;
	}
	public String getS1_CRDHLDR_NO2() {
		return S1_CRDHLDR_NO2;
	}
	public void setS1_CRDHLDR_NO2(String s1_CRDHLDR_NO2) {
		S1_CRDHLDR_NO2 = s1_CRDHLDR_NO2;
	}
	public String getA1_NEW_CAT3() {
		return A1_NEW_CAT3;
	}
	public void setA1_NEW_CAT3(String a1_NEW_CAT3) {
		A1_NEW_CAT3 = a1_NEW_CAT3;
	}
	public String getA1_NEW_LVL3() {
		return A1_NEW_LVL3;
	}
	public void setA1_NEW_LVL3(String a1_NEW_LVL3) {
		A1_NEW_LVL3 = a1_NEW_LVL3;
	}
	public String getA1_NEW_IDV_CRD_TY3() {
		return A1_NEW_IDV_CRD_TY3;
	}
	public void setA1_NEW_IDV_CRD_TY3(String a1_NEW_IDV_CRD_TY3) {
		A1_NEW_IDV_CRD_TY3 = a1_NEW_IDV_CRD_TY3;
	}
	public String getA1_AFF_CRD_CD3() {
		return A1_AFF_CRD_CD3;
	}
	public void setA1_AFF_CRD_CD3(String a1_AFF_CRD_CD3) {
		A1_AFF_CRD_CD3 = a1_AFF_CRD_CD3;
	}
	public String getA1_NEW_CRD_PH_FL3() {
		return A1_NEW_CRD_PH_FL3;
	}
	public void setA1_NEW_CRD_PH_FL3(String a1_NEW_CRD_PH_FL3) {
		A1_NEW_CRD_PH_FL3 = a1_NEW_CRD_PH_FL3;
	}
	public String getA1_NEW_CRD_LIM_REQ3() {
		return A1_NEW_CRD_LIM_REQ3;
	}
	public void setA1_NEW_CRD_LIM_REQ3(String a1_NEW_CRD_LIM_REQ3) {
		A1_NEW_CRD_LIM_REQ3 = a1_NEW_CRD_LIM_REQ3;
	}
	public String getS1_NEW_CRD_REQ3() {
		return S1_NEW_CRD_REQ3;
	}
	public void setS1_NEW_CRD_REQ3(String s1_NEW_CRD_REQ3) {
		S1_NEW_CRD_REQ3 = s1_NEW_CRD_REQ3;
	}
	public String getS1_NEW_CRD_PH_FL3() {
		return S1_NEW_CRD_PH_FL3;
	}
	public void setS1_NEW_CRD_PH_FL3(String s1_NEW_CRD_PH_FL3) {
		S1_NEW_CRD_PH_FL3 = s1_NEW_CRD_PH_FL3;
	}
	public String getS1_USGE_LVL3() {
		return S1_USGE_LVL3;
	}
	public void setS1_USGE_LVL3(String s1_USGE_LVL3) {
		S1_USGE_LVL3 = s1_USGE_LVL3;
	}
	public String getS1_NEW_CRD_LIM_REQ3() {
		return S1_NEW_CRD_LIM_REQ3;
	}
	public void setS1_NEW_CRD_LIM_REQ3(String s1_NEW_CRD_LIM_REQ3) {
		S1_NEW_CRD_LIM_REQ3 = s1_NEW_CRD_LIM_REQ3;
	}
	public String getA1_CRDHLDR_NO3() {
		return A1_CRDHLDR_NO3;
	}
	public void setA1_CRDHLDR_NO3(String a1_CRDHLDR_NO3) {
		A1_CRDHLDR_NO3 = a1_CRDHLDR_NO3;
	}
	public String getS1_CRDHLDR_NO3() {
		return S1_CRDHLDR_NO3;
	}
	public void setS1_CRDHLDR_NO3(String s1_CRDHLDR_NO3) {
		S1_CRDHLDR_NO3 = s1_CRDHLDR_NO3;
	}
	public String getA1_FIN_INFO_TY() {
		return A1_FIN_INFO_TY;
	}
	public void setA1_FIN_INFO_TY(String a1_FIN_INFO_TY) {
		A1_FIN_INFO_TY = a1_FIN_INFO_TY;
	}
	public BigDecimal getA1_SAV_DEP() {
		return A1_SAV_DEP;
	}
	public void setA1_SAV_DEP(BigDecimal a1_SAV_DEP) {
		A1_SAV_DEP = a1_SAV_DEP;
	}
	public BigDecimal getA1_FIX_DEP_HLD() {
		return A1_FIX_DEP_HLD;
	}
	public void setA1_FIX_DEP_HLD(BigDecimal a1_FIX_DEP_HLD) {
		A1_FIX_DEP_HLD = a1_FIX_DEP_HLD;
	}
	public BigDecimal getA1_FIX_DEP_NOT_HLD() {
		return A1_FIX_DEP_NOT_HLD;
	}
	public void setA1_FIX_DEP_NOT_HLD(BigDecimal a1_FIX_DEP_NOT_HLD) {
		A1_FIX_DEP_NOT_HLD = a1_FIX_DEP_NOT_HLD;
	}
	public String getA1_TERT_BILL_CSH_INFLW() {
		return A1_TERT_BILL_CSH_INFLW;
	}
	public void setA1_TERT_BILL_CSH_INFLW(String a1_TERT_BILL_CSH_INFLW) {
		A1_TERT_BILL_CSH_INFLW = a1_TERT_BILL_CSH_INFLW;
	}
	public String getA1_TERT_BILL_TM_MAT_YY() {
		return A1_TERT_BILL_TM_MAT_YY;
	}
	public void setA1_TERT_BILL_TM_MAT_YY(String a1_TERT_BILL_TM_MAT_YY) {
		A1_TERT_BILL_TM_MAT_YY = a1_TERT_BILL_TM_MAT_YY;
	}
	public String getA1_TERT_BILL_TM_MAT_MM() {
		return A1_TERT_BILL_TM_MAT_MM;
	}
	public void setA1_TERT_BILL_TM_MAT_MM(String a1_TERT_BILL_TM_MAT_MM) {
		A1_TERT_BILL_TM_MAT_MM = a1_TERT_BILL_TM_MAT_MM;
	}
	public String getA1_TERT_BILL_MAT_TOT() {
		return A1_TERT_BILL_MAT_TOT;
	}
	public void setA1_TERT_BILL_MAT_TOT(String a1_TERT_BILL_MAT_TOT) {
		A1_TERT_BILL_MAT_TOT = a1_TERT_BILL_MAT_TOT;
	}
	public String getA1_TER_BILL_RT() {
		return A1_TER_BILL_RT;
	}
	public void setA1_TER_BILL_RT(String a1_TER_BILL_RT) {
		A1_TER_BILL_RT = a1_TER_BILL_RT;
	}
	public String getA1_CURR_SAV_DEP_FI() {
		return A1_CURR_SAV_DEP_FI;
	}
	public void setA1_CURR_SAV_DEP_FI(String a1_CURR_SAV_DEP_FI) {
		A1_CURR_SAV_DEP_FI = a1_CURR_SAV_DEP_FI;
	}
	public String getA1_CURR_FXDEP_HLD_FI() {
		return A1_CURR_FXDEP_HLD_FI;
	}
	public void setA1_CURR_FXDEP_HLD_FI(String a1_CURR_FXDEP_HLD_FI) {
		A1_CURR_FXDEP_HLD_FI = a1_CURR_FXDEP_HLD_FI;
	}
	public String getA1_CURR_FXDEP_NTHLD_FI() {
		return A1_CURR_FXDEP_NTHLD_FI;
	}
	public void setA1_CURR_FXDEP_NTHLD_FI(String a1_CURR_FXDEP_NTHLD_FI) {
		A1_CURR_FXDEP_NTHLD_FI = a1_CURR_FXDEP_NTHLD_FI;
	}
	public BigDecimal getS1_FIX_DEP_HLD() {
		return S1_FIX_DEP_HLD;
	}
	public void setS1_FIX_DEP_HLD(BigDecimal s1_FIX_DEP_HLD) {
		S1_FIX_DEP_HLD = s1_FIX_DEP_HLD;
	}
	public String getDT_REG_CENTRAL() {
		return DT_REG_CENTRAL;
	}
	public void setDT_REG_CENTRAL(String dT_REG_CENTRAL) {
		DT_REG_CENTRAL = dT_REG_CENTRAL;
	}
	public String getA1_TITLE_TH() {
		return A1_TITLE_TH;
	}
	public void setA1_TITLE_TH(String a1_TITLE_TH) {
		A1_TITLE_TH = a1_TITLE_TH;
	}
	public String getA1_OTH_TITLE_TH() {
		return A1_OTH_TITLE_TH;
	}
	public void setA1_OTH_TITLE_TH(String a1_OTH_TITLE_TH) {
		A1_OTH_TITLE_TH = a1_OTH_TITLE_TH;
	}
	public String getA1_1ST_NM_TH() {
		return A1_1ST_NM_TH;
	}
	public void setA1_1ST_NM_TH(String a1_1st_NM_TH) {
		A1_1ST_NM_TH = a1_1st_NM_TH;
	}
	public String getA1_SURNAME_TH() {
		return A1_SURNAME_TH;
	}
	public void setA1_SURNAME_TH(String a1_SURNAME_TH) {
		A1_SURNAME_TH = a1_SURNAME_TH;
	}
	public String getA1_TITLE_ENG() {
		return A1_TITLE_ENG;
	}
	public void setA1_TITLE_ENG(String a1_TITLE_ENG) {
		A1_TITLE_ENG = a1_TITLE_ENG;
	}
	public String getA1_OTH_TITLE_ENG() {
		return A1_OTH_TITLE_ENG;
	}
	public void setA1_OTH_TITLE_ENG(String a1_OTH_TITLE_ENG) {
		A1_OTH_TITLE_ENG = a1_OTH_TITLE_ENG;
	}
	public String getA1_1ST_NM_ENG() {
		return A1_1ST_NM_ENG;
	}
	public void setA1_1ST_NM_ENG(String a1_1st_NM_ENG) {
		A1_1ST_NM_ENG = a1_1st_NM_ENG;
	}
	public String getA1_SURNAME_ENG() {
		return A1_SURNAME_ENG;
	}
	public void setA1_SURNAME_ENG(String a1_SURNAME_ENG) {
		A1_SURNAME_ENG = a1_SURNAME_ENG;
	}
	public String getA1_EMBOSS_NM_ENG() {
		return A1_EMBOSS_NM_ENG;
	}
	public void setA1_EMBOSS_NM_ENG(String a1_EMBOSS_NM_ENG) {
		A1_EMBOSS_NM_ENG = a1_EMBOSS_NM_ENG;
	}
	public String getA1_DOB_CE() {
		return A1_DOB_CE;
	}
	public void setA1_DOB_CE(String a1_DOB_CE) {
		A1_DOB_CE = a1_DOB_CE;
	}
	public String getA1_MARITAL_STAT() {
		return A1_MARITAL_STAT;
	}
	public void setA1_MARITAL_STAT(String a1_MARITAL_STAT) {
		A1_MARITAL_STAT = a1_MARITAL_STAT;
	}
	public String getA1_SEX_STAT() {
		return A1_SEX_STAT;
	}
	public void setA1_SEX_STAT(String a1_SEX_STAT) {
		A1_SEX_STAT = a1_SEX_STAT;
	}
	public String getA1_NO_CHILDREN() {
		return A1_NO_CHILDREN;
	}
	public void setA1_NO_CHILDREN(String a1_NO_CHILDREN) {
		A1_NO_CHILDREN = a1_NO_CHILDREN;
	}
	public String getA1_NO_DEPDNTS() {
		return A1_NO_DEPDNTS;
	}
	public void setA1_NO_DEPDNTS(String a1_NO_DEPDNTS) {
		A1_NO_DEPDNTS = a1_NO_DEPDNTS;
	}
	public String getA1_STAFF_FL() {
		return A1_STAFF_FL;
	}
	public void setA1_STAFF_FL(String a1_STAFF_FL) {
		A1_STAFF_FL = a1_STAFF_FL;
	}
	public String getA1_VIP_CUST_FL() {
		return A1_VIP_CUST_FL;
	}
	public void setA1_VIP_CUST_FL(String a1_VIP_CUST_FL) {
		A1_VIP_CUST_FL = a1_VIP_CUST_FL;
	}
	public String getA1_ID_TY() {
		return A1_ID_TY;
	}
	public void setA1_ID_TY(String a1_ID_TY) {
		A1_ID_TY = a1_ID_TY;
	}
	public String getA1_ID_NO() {
		return A1_ID_NO;
	}
	public void setA1_ID_NO(String a1_ID_NO) {
		A1_ID_NO = a1_ID_NO;
	}
	public String getA1_WRK_PERM_ISS_NO() {
		return A1_WRK_PERM_ISS_NO;
	}
	public void setA1_WRK_PERM_ISS_NO(String a1_WRK_PERM_ISS_NO) {
		A1_WRK_PERM_ISS_NO = a1_WRK_PERM_ISS_NO;
	}
	public String getA1_CURR_RES_STAT() {
		return A1_CURR_RES_STAT;
	}
	public void setA1_CURR_RES_STAT(String a1_CURR_RES_STAT) {
		A1_CURR_RES_STAT = a1_CURR_RES_STAT;
	}
	public String getA1_CAD_RESIDE_PD_YRS() {
		return A1_CAD_RESIDE_PD_YRS;
	}
	public void setA1_CAD_RESIDE_PD_YRS(String a1_CAD_RESIDE_PD_YRS) {
		A1_CAD_RESIDE_PD_YRS = a1_CAD_RESIDE_PD_YRS;
	}
	public String getA1_CAD_RESIDE_PD_MTHS() {
		return A1_CAD_RESIDE_PD_MTHS;
	}
	public void setA1_CAD_RESIDE_PD_MTHS(String a1_CAD_RESIDE_PD_MTHS) {
		A1_CAD_RESIDE_PD_MTHS = a1_CAD_RESIDE_PD_MTHS;
	}
	public String getA1_CAD_NO() {
		return A1_CAD_NO;
	}
	public void setA1_CAD_NO(String a1_CAD_NO) {
		A1_CAD_NO = a1_CAD_NO;
	}
	public String getA1_CAD_MOO() {
		return A1_CAD_MOO;
	}
	public void setA1_CAD_MOO(String a1_CAD_MOO) {
		A1_CAD_MOO = a1_CAD_MOO;
	}
	public String getA1_CAD_SOI() {
		return A1_CAD_SOI;
	}
	public void setA1_CAD_SOI(String a1_CAD_SOI) {
		A1_CAD_SOI = a1_CAD_SOI;
	}
	public String getA1_CAD_ST() {
		return A1_CAD_ST;
	}
	public void setA1_CAD_ST(String a1_CAD_ST) {
		A1_CAD_ST = a1_CAD_ST;
	}
	public String getA1_CAD_SUB_DIST() {
		return A1_CAD_SUB_DIST;
	}
	public void setA1_CAD_SUB_DIST(String a1_CAD_SUB_DIST) {
		A1_CAD_SUB_DIST = a1_CAD_SUB_DIST;
	}
	public String getA1_CAD_DIST() {
		return A1_CAD_DIST;
	}
	public void setA1_CAD_DIST(String a1_CAD_DIST) {
		A1_CAD_DIST = a1_CAD_DIST;
	}
	public String getA1_CAD_PROV() {
		return A1_CAD_PROV;
	}
	public void setA1_CAD_PROV(String a1_CAD_PROV) {
		A1_CAD_PROV = a1_CAD_PROV;
	}
	public String getA1_CAD_ZIP_CD() {
		return A1_CAD_ZIP_CD;
	}
	public void setA1_CAD_ZIP_CD(String a1_CAD_ZIP_CD) {
		A1_CAD_ZIP_CD = a1_CAD_ZIP_CD;
	}
	public String getA1_CAD_TEL_NO() {
		return A1_CAD_TEL_NO;
	}
	public void setA1_CAD_TEL_NO(String a1_CAD_TEL_NO) {
		A1_CAD_TEL_NO = a1_CAD_TEL_NO;
	}
	public String getA1_CAD_MOB_NO() {
		return A1_CAD_MOB_NO;
	}
	public void setA1_CAD_MOB_NO(String a1_CAD_MOB_NO) {
		A1_CAD_MOB_NO = a1_CAD_MOB_NO;
	}
	public String getA1_EMAIL() {
		return A1_EMAIL;
	}
	public void setA1_EMAIL(String a1_EMAIL) {
		A1_EMAIL = a1_EMAIL;
	}
	public String getA1_RAD_SAME_CAD() {
		return A1_RAD_SAME_CAD;
	}
	public void setA1_RAD_SAME_CAD(String a1_RAD_SAME_CAD) {
		A1_RAD_SAME_CAD = a1_RAD_SAME_CAD;
	}
	public String getA1_RAD_NO() {
		return A1_RAD_NO;
	}
	public void setA1_RAD_NO(String a1_RAD_NO) {
		A1_RAD_NO = a1_RAD_NO;
	}
	public String getA1_RAD_MOO() {
		return A1_RAD_MOO;
	}
	public void setA1_RAD_MOO(String a1_RAD_MOO) {
		A1_RAD_MOO = a1_RAD_MOO;
	}
	public String getA1_RAD_SOI() {
		return A1_RAD_SOI;
	}
	public void setA1_RAD_SOI(String a1_RAD_SOI) {
		A1_RAD_SOI = a1_RAD_SOI;
	}
	public String getA1_RAD_ST() {
		return A1_RAD_ST;
	}
	public void setA1_RAD_ST(String a1_RAD_ST) {
		A1_RAD_ST = a1_RAD_ST;
	}
	public String getA1_RAD_SUB_DIST() {
		return A1_RAD_SUB_DIST;
	}
	public void setA1_RAD_SUB_DIST(String a1_RAD_SUB_DIST) {
		A1_RAD_SUB_DIST = a1_RAD_SUB_DIST;
	}
	public String getA1_RAD_DIST() {
		return A1_RAD_DIST;
	}
	public void setA1_RAD_DIST(String a1_RAD_DIST) {
		A1_RAD_DIST = a1_RAD_DIST;
	}
	public String getA1_RAD_PROV() {
		return A1_RAD_PROV;
	}
	public void setA1_RAD_PROV(String a1_RAD_PROV) {
		A1_RAD_PROV = a1_RAD_PROV;
	}
	public String getA1_RAD_ZIP_CD() {
		return A1_RAD_ZIP_CD;
	}
	public void setA1_RAD_ZIP_CD(String a1_RAD_ZIP_CD) {
		A1_RAD_ZIP_CD = a1_RAD_ZIP_CD;
	}
	public String getA1_CURR_OCC() {
		return A1_CURR_OCC;
	}
	public void setA1_CURR_OCC(String a1_CURR_OCC) {
		A1_CURR_OCC = a1_CURR_OCC;
	}
	public String getA1_CURR_CHAR() {
		return A1_CURR_CHAR;
	}
	public void setA1_CURR_CHAR(String a1_CURR_CHAR) {
		A1_CURR_CHAR = a1_CURR_CHAR;
	}
	public String getA1_CEMP_NM() {
		return A1_CEMP_NM;
	}
	public void setA1_CEMP_NM(String a1_CEMP_NM) {
		A1_CEMP_NM = a1_CEMP_NM;
	}
	public String getA1_CEMP_YRS() {
		return A1_CEMP_YRS;
	}
	public void setA1_CEMP_YRS(String a1_CEMP_YRS) {
		A1_CEMP_YRS = a1_CEMP_YRS;
	}
	public String getA1_CEMP_MTHS() {
		return A1_CEMP_MTHS;
	}
	public void setA1_CEMP_MTHS(String a1_CEMP_MTHS) {
		A1_CEMP_MTHS = a1_CEMP_MTHS;
	}
	public String getA1_CEMP_POS_NM() {
		return A1_CEMP_POS_NM;
	}
	public void setA1_CEMP_POS_NM(String a1_CEMP_POS_NM) {
		A1_CEMP_POS_NM = a1_CEMP_POS_NM;
	}
	public String getA1_CEMP_POS_LVL() {
		return A1_CEMP_POS_LVL;
	}
	public void setA1_CEMP_POS_LVL(String a1_CEMP_POS_LVL) {
		A1_CEMP_POS_LVL = a1_CEMP_POS_LVL;
	}
	public String getA1_GROSS_MTHLY_SAL() {
		return A1_GROSS_MTHLY_SAL;
	}
	public void setA1_GROSS_MTHLY_SAL(String a1_GROSS_MTHLY_SAL) {
		A1_GROSS_MTHLY_SAL = a1_GROSS_MTHLY_SAL;
	}
	public String getA1_MTHLY_BUS_INC() {
		return A1_MTHLY_BUS_INC;
	}
	public void setA1_MTHLY_BUS_INC(String a1_MTHLY_BUS_INC) {
		A1_MTHLY_BUS_INC = a1_MTHLY_BUS_INC;
	}
	public String getA1_MTHLY_BONUS_INC() {
		return A1_MTHLY_BONUS_INC;
	}
	public void setA1_MTHLY_BONUS_INC(String a1_MTHLY_BONUS_INC) {
		A1_MTHLY_BONUS_INC = a1_MTHLY_BONUS_INC;
	}
	public String getA1_MTHLY_ADD_INC() {
		return A1_MTHLY_ADD_INC;
	}
	public void setA1_MTHLY_ADD_INC(String a1_MTHLY_ADD_INC) {
		A1_MTHLY_ADD_INC = a1_MTHLY_ADD_INC;
	}
	public String getA1_HIGHEST_EDU() {
		return A1_HIGHEST_EDU;
	}
	public void setA1_HIGHEST_EDU(String a1_HIGHEST_EDU) {
		A1_HIGHEST_EDU = a1_HIGHEST_EDU;
	}
	public String getA1_CEMP_ADD_BUILD() {
		return A1_CEMP_ADD_BUILD;
	}
	public void setA1_CEMP_ADD_BUILD(String a1_CEMP_ADD_BUILD) {
		A1_CEMP_ADD_BUILD = a1_CEMP_ADD_BUILD;
	}
	public String getA1_CEMP_FLR() {
		return A1_CEMP_FLR;
	}
	public void setA1_CEMP_FLR(String a1_CEMP_FLR) {
		A1_CEMP_FLR = a1_CEMP_FLR;
	}
	public String getA1_CEMP_DIV() {
		return A1_CEMP_DIV;
	}
	public void setA1_CEMP_DIV(String a1_CEMP_DIV) {
		A1_CEMP_DIV = a1_CEMP_DIV;
	}
	public String getA1_CEMP_DPTMNT() {
		return A1_CEMP_DPTMNT;
	}
	public void setA1_CEMP_DPTMNT(String a1_CEMP_DPTMNT) {
		A1_CEMP_DPTMNT = a1_CEMP_DPTMNT;
	}
	public String getA1_CEMP_NO() {
		return A1_CEMP_NO;
	}
	public void setA1_CEMP_NO(String a1_CEMP_NO) {
		A1_CEMP_NO = a1_CEMP_NO;
	}
	public String getA1_CEMP_MOO() {
		return A1_CEMP_MOO;
	}
	public void setA1_CEMP_MOO(String a1_CEMP_MOO) {
		A1_CEMP_MOO = a1_CEMP_MOO;
	}
	public String getA1_CEMP_SOI() {
		return A1_CEMP_SOI;
	}
	public void setA1_CEMP_SOI(String a1_CEMP_SOI) {
		A1_CEMP_SOI = a1_CEMP_SOI;
	}
	public String getA1_CEMP_ST() {
		return A1_CEMP_ST;
	}
	public void setA1_CEMP_ST(String a1_CEMP_ST) {
		A1_CEMP_ST = a1_CEMP_ST;
	}
	public String getA1_CEMP_SUB_DIST() {
		return A1_CEMP_SUB_DIST;
	}
	public void setA1_CEMP_SUB_DIST(String a1_CEMP_SUB_DIST) {
		A1_CEMP_SUB_DIST = a1_CEMP_SUB_DIST;
	}
	public String getA1_CEMP_DIST() {
		return A1_CEMP_DIST;
	}
	public void setA1_CEMP_DIST(String a1_CEMP_DIST) {
		A1_CEMP_DIST = a1_CEMP_DIST;
	}
	public String getA1_CEMP_PROV() {
		return A1_CEMP_PROV;
	}
	public void setA1_CEMP_PROV(String a1_CEMP_PROV) {
		A1_CEMP_PROV = a1_CEMP_PROV;
	}
	public String getA1_CEMP_ZIP_CD() {
		return A1_CEMP_ZIP_CD;
	}
	public void setA1_CEMP_ZIP_CD(String a1_CEMP_ZIP_CD) {
		A1_CEMP_ZIP_CD = a1_CEMP_ZIP_CD;
	}
	public String getA1_CEMP_TEL_NO() {
		return A1_CEMP_TEL_NO;
	}
	public void setA1_CEMP_TEL_NO(String a1_CEMP_TEL_NO) {
		A1_CEMP_TEL_NO = a1_CEMP_TEL_NO;
	}
	public String getA1_VERI_MTHLY_INC() {
		return A1_VERI_MTHLY_INC;
	}
	public void setA1_VERI_MTHLY_INC(String a1_VERI_MTHLY_INC) {
		A1_VERI_MTHLY_INC = a1_VERI_MTHLY_INC;
	}
	public String getS1_TITLE_TH() {
		return S1_TITLE_TH;
	}
	public void setS1_TITLE_TH(String s1_TITLE_TH) {
		S1_TITLE_TH = s1_TITLE_TH;
	}
	public String getS1_OTHER_TITLE_TH() {
		return S1_OTHER_TITLE_TH;
	}
	public void setS1_OTHER_TITLE_TH(String s1_OTHER_TITLE_TH) {
		S1_OTHER_TITLE_TH = s1_OTHER_TITLE_TH;
	}
	public String getS1_1ST_NM_TH() {
		return S1_1ST_NM_TH;
	}
	public void setS1_1ST_NM_TH(String s1_1st_NM_TH) {
		S1_1ST_NM_TH = s1_1st_NM_TH;
	}
	public String getS1_SURNAME_TH() {
		return S1_SURNAME_TH;
	}
	public void setS1_SURNAME_TH(String s1_SURNAME_TH) {
		S1_SURNAME_TH = s1_SURNAME_TH;
	}
	public String getS1_TITLE_ENG() {
		return S1_TITLE_ENG;
	}
	public void setS1_TITLE_ENG(String s1_TITLE_ENG) {
		S1_TITLE_ENG = s1_TITLE_ENG;
	}
	public String getS1_OTHER_TITLE_ENG() {
		return S1_OTHER_TITLE_ENG;
	}
	public void setS1_OTHER_TITLE_ENG(String s1_OTHER_TITLE_ENG) {
		S1_OTHER_TITLE_ENG = s1_OTHER_TITLE_ENG;
	}
	public String getS1_1ST_NM_ENG() {
		return S1_1ST_NM_ENG;
	}
	public void setS1_1ST_NM_ENG(String s1_1st_NM_ENG) {
		S1_1ST_NM_ENG = s1_1st_NM_ENG;
	}
	public String getS1_SURNAME_ENG() {
		return S1_SURNAME_ENG;
	}
	public void setS1_SURNAME_ENG(String s1_SURNAME_ENG) {
		S1_SURNAME_ENG = s1_SURNAME_ENG;
	}
	public String getS1_EMBOSS_NM_ENG() {
		return S1_EMBOSS_NM_ENG;
	}
	public void setS1_EMBOSS_NM_ENG(String s1_EMBOSS_NM_ENG) {
		S1_EMBOSS_NM_ENG = s1_EMBOSS_NM_ENG;
	}
	public String getS1_DOB_CE() {
		return S1_DOB_CE;
	}
	public void setS1_DOB_CE(String s1_DOB_CE) {
		S1_DOB_CE = s1_DOB_CE;
	}
	public String getS1_SEX_STAT() {
		return S1_SEX_STAT;
	}
	public void setS1_SEX_STAT(String s1_SEX_STAT) {
		S1_SEX_STAT = s1_SEX_STAT;
	}
	public String getS1_ID_TY() {
		return S1_ID_TY;
	}
	public void setS1_ID_TY(String s1_ID_TY) {
		S1_ID_TY = s1_ID_TY;
	}
	public String getS1_ID_NO() {
		return S1_ID_NO;
	}
	public void setS1_ID_NO(String s1_ID_NO) {
		S1_ID_NO = s1_ID_NO;
	}
	public String getS1_STAFF_FL() {
		return S1_STAFF_FL;
	}
	public void setS1_STAFF_FL(String s1_STAFF_FL) {
		S1_STAFF_FL = s1_STAFF_FL;
	}
	public String getS1_VIP_CUST_FL() {
		return S1_VIP_CUST_FL;
	}
	public void setS1_VIP_CUST_FL(String s1_VIP_CUST_FL) {
		S1_VIP_CUST_FL = s1_VIP_CUST_FL;
	}
	public String getS1_CAD_NO() {
		return S1_CAD_NO;
	}
	public void setS1_CAD_NO(String s1_CAD_NO) {
		S1_CAD_NO = s1_CAD_NO;
	}
	public String getS1_CAD_MOO() {
		return S1_CAD_MOO;
	}
	public void setS1_CAD_MOO(String s1_CAD_MOO) {
		S1_CAD_MOO = s1_CAD_MOO;
	}
	public String getS1_CAD_SOI() {
		return S1_CAD_SOI;
	}
	public void setS1_CAD_SOI(String s1_CAD_SOI) {
		S1_CAD_SOI = s1_CAD_SOI;
	}
	public String getS1_CAD_ST() {
		return S1_CAD_ST;
	}
	public void setS1_CAD_ST(String s1_CAD_ST) {
		S1_CAD_ST = s1_CAD_ST;
	}
	public String getS1_CAD_SUB_DIST() {
		return S1_CAD_SUB_DIST;
	}
	public void setS1_CAD_SUB_DIST(String s1_CAD_SUB_DIST) {
		S1_CAD_SUB_DIST = s1_CAD_SUB_DIST;
	}
	public String getS1_CAD_DIST() {
		return S1_CAD_DIST;
	}
	public void setS1_CAD_DIST(String s1_CAD_DIST) {
		S1_CAD_DIST = s1_CAD_DIST;
	}
	public String getS1_CAD_PROV() {
		return S1_CAD_PROV;
	}
	public void setS1_CAD_PROV(String s1_CAD_PROV) {
		S1_CAD_PROV = s1_CAD_PROV;
	}
	public String getS1_CAD_ZIP_CD() {
		return S1_CAD_ZIP_CD;
	}
	public void setS1_CAD_ZIP_CD(String s1_CAD_ZIP_CD) {
		S1_CAD_ZIP_CD = s1_CAD_ZIP_CD;
	}
	public String getS1_CAD_TEL_NO() {
		return S1_CAD_TEL_NO;
	}
	public void setS1_CAD_TEL_NO(String s1_CAD_TEL_NO) {
		S1_CAD_TEL_NO = s1_CAD_TEL_NO;
	}
	public String getS1_CAD_MOB_NO() {
		return S1_CAD_MOB_NO;
	}
	public void setS1_CAD_MOB_NO(String s1_CAD_MOB_NO) {
		S1_CAD_MOB_NO = s1_CAD_MOB_NO;
	}
	public String getS1_EMAIL() {
		return S1_EMAIL;
	}
	public void setS1_EMAIL(String s1_EMAIL) {
		S1_EMAIL = s1_EMAIL;
	}
	public String getS1_CEMP_NM() {
		return S1_CEMP_NM;
	}
	public void setS1_CEMP_NM(String s1_CEMP_NM) {
		S1_CEMP_NM = s1_CEMP_NM;
	}
	public String getS1_CEMP_TEL_NO() {
		return S1_CEMP_TEL_NO;
	}
	public void setS1_CEMP_TEL_NO(String s1_CEMP_TEL_NO) {
		S1_CEMP_TEL_NO = s1_CEMP_TEL_NO;
	}
	public String getA1_CC_MAIL_ADDR() {
		return A1_CC_MAIL_ADDR;
	}
	public void setA1_CC_MAIL_ADDR(String a1_CC_MAIL_ADDR) {
		A1_CC_MAIL_ADDR = a1_CC_MAIL_ADDR;
	}
	public String getA1_CC_PLC_REC_CRD() {
		return A1_CC_PLC_REC_CRD;
	}
	public void setA1_CC_PLC_REC_CRD(String a1_CC_PLC_REC_CRD) {
		A1_CC_PLC_REC_CRD = a1_CC_PLC_REC_CRD;
	}
	public String getA1_CC_BRNCH_CD_REC_CRD() {
		return A1_CC_BRNCH_CD_REC_CRD;
	}
	public void setA1_CC_BRNCH_CD_REC_CRD(String a1_CC_BRNCH_CD_REC_CRD) {
		A1_CC_BRNCH_CD_REC_CRD = a1_CC_BRNCH_CD_REC_CRD;
	}
	public String getA1_CC_PY_COND() {
		return A1_CC_PY_COND;
	}
	public void setA1_CC_PY_COND(String a1_CC_PY_COND) {
		A1_CC_PY_COND = a1_CC_PY_COND;
	}
	public String getA1_CC_PY_METH() {
		return A1_CC_PY_METH;
	}
	public void setA1_CC_PY_METH(String a1_CC_PY_METH) {
		A1_CC_PY_METH = a1_CC_PY_METH;
	}
	public String getA1_CC_APA_TY() {
		return A1_CC_APA_TY;
	}
	public void setA1_CC_APA_TY(String a1_CC_APA_TY) {
		A1_CC_APA_TY = a1_CC_APA_TY;
	}
	public String getA1_CC_APA_NO() {
		return A1_CC_APA_NO;
	}
	public void setA1_CC_APA_NO(String a1_CC_APA_NO) {
		A1_CC_APA_NO = a1_CC_APA_NO;
	}
	public String getA1_CC_DA_TYP_ATM() {
		return A1_CC_DA_TYP_ATM;
	}
	public void setA1_CC_DA_TYP_ATM(String a1_CC_DA_TYP_ATM) {
		A1_CC_DA_TYP_ATM = a1_CC_DA_TYP_ATM;
	}
	public String getA1_CC_SA_NO_ATM() {
		return A1_CC_SA_NO_ATM;
	}
	public void setA1_CC_SA_NO_ATM(String a1_CC_SA_NO_ATM) {
		A1_CC_SA_NO_ATM = a1_CC_SA_NO_ATM;
	}
	public String getA1_CC_CA_NO_ATM() {
		return A1_CC_CA_NO_ATM;
	}
	public void setA1_CC_CA_NO_ATM(String a1_CC_CA_NO_ATM) {
		A1_CC_CA_NO_ATM = a1_CC_CA_NO_ATM;
	}
	public String getS1_CC_MAIL_ADDR() {
		return S1_CC_MAIL_ADDR;
	}
	public void setS1_CC_MAIL_ADDR(String s1_CC_MAIL_ADDR) {
		S1_CC_MAIL_ADDR = s1_CC_MAIL_ADDR;
	}
	public String getS1_CC_PLC_REC_CRD() {
		return S1_CC_PLC_REC_CRD;
	}
	public void setS1_CC_PLC_REC_CRD(String s1_CC_PLC_REC_CRD) {
		S1_CC_PLC_REC_CRD = s1_CC_PLC_REC_CRD;
	}
	public String getS1_CC_BRNCH_CD_REC_CRD() {
		return S1_CC_BRNCH_CD_REC_CRD;
	}
	public void setS1_CC_BRNCH_CD_REC_CRD(String s1_CC_BRNCH_CD_REC_CRD) {
		S1_CC_BRNCH_CD_REC_CRD = s1_CC_BRNCH_CD_REC_CRD;
	}
	public String getS1_CC_PY_COND() {
		return S1_CC_PY_COND;
	}
	public void setS1_CC_PY_COND(String s1_CC_PY_COND) {
		S1_CC_PY_COND = s1_CC_PY_COND;
	}
	public String getS1_CC_PY_METH() {
		return S1_CC_PY_METH;
	}
	public void setS1_CC_PY_METH(String s1_CC_PY_METH) {
		S1_CC_PY_METH = s1_CC_PY_METH;
	}
	public String getS1_CC_APA_TY() {
		return S1_CC_APA_TY;
	}
	public void setS1_CC_APA_TY(String s1_CC_APA_TY) {
		S1_CC_APA_TY = s1_CC_APA_TY;
	}
	public String getS1_CC_APA_NO() {
		return S1_CC_APA_NO;
	}
	public void setS1_CC_APA_NO(String s1_CC_APA_NO) {
		S1_CC_APA_NO = s1_CC_APA_NO;
	}
	public String getS1_CC_DA_TYP_ATM() {
		return S1_CC_DA_TYP_ATM;
	}
	public void setS1_CC_DA_TYP_ATM(String s1_CC_DA_TYP_ATM) {
		S1_CC_DA_TYP_ATM = s1_CC_DA_TYP_ATM;
	}
	public String getS1_CC_SA_NO_ATM() {
		return S1_CC_SA_NO_ATM;
	}
	public void setS1_CC_SA_NO_ATM(String s1_CC_SA_NO_ATM) {
		S1_CC_SA_NO_ATM = s1_CC_SA_NO_ATM;
	}
	public String getS1_CC_CA_NO_ATM() {
		return S1_CC_CA_NO_ATM;
	}
	public void setS1_CC_CA_NO_ATM(String s1_CC_CA_NO_ATM) {
		S1_CC_CA_NO_ATM = s1_CC_CA_NO_ATM;
	}
	public String getA1_CITIZN_GOV_PASS_MET() {
		return A1_CITIZN_GOV_PASS_MET;
	}
	public void setA1_CITIZN_GOV_PASS_MET(String a1_CITIZN_GOV_PASS_MET) {
		A1_CITIZN_GOV_PASS_MET = a1_CITIZN_GOV_PASS_MET;
	}
	public String getA1_HM_REG_MET() {
		return A1_HM_REG_MET;
	}
	public void setA1_HM_REG_MET(String a1_HM_REG_MET) {
		A1_HM_REG_MET = a1_HM_REG_MET;
	}
	public String getA1_WRK_PERM_MET() {
		return A1_WRK_PERM_MET;
	}
	public void setA1_WRK_PERM_MET(String a1_WRK_PERM_MET) {
		A1_WRK_PERM_MET = a1_WRK_PERM_MET;
	}
	public String getA1_PASSPORT_MET() {
		return A1_PASSPORT_MET;
	}
	public void setA1_PASSPORT_MET(String a1_PASSPORT_MET) {
		A1_PASSPORT_MET = a1_PASSPORT_MET;
	}
	public String getA1_PHOTO_MET() {
		return A1_PHOTO_MET;
	}
	public void setA1_PHOTO_MET(String a1_PHOTO_MET) {
		A1_PHOTO_MET = a1_PHOTO_MET;
	}
	public String getA1_BNK_STATMNT_MET() {
		return A1_BNK_STATMNT_MET;
	}
	public void setA1_BNK_STATMNT_MET(String a1_BNK_STATMNT_MET) {
		A1_BNK_STATMNT_MET = a1_BNK_STATMNT_MET;
	}
	public String getA1_COMP_SHOLDR_LST_MET() {
		return A1_COMP_SHOLDR_LST_MET;
	}
	public void setA1_COMP_SHOLDR_LST_MET(String a1_COMP_SHOLDR_LST_MET) {
		A1_COMP_SHOLDR_LST_MET = a1_COMP_SHOLDR_LST_MET;
	}
	public String getA1_TRAD_COM_REG_MET() {
		return A1_TRAD_COM_REG_MET;
	}
	public void setA1_TRAD_COM_REG_MET(String a1_TRAD_COM_REG_MET) {
		A1_TRAD_COM_REG_MET = a1_TRAD_COM_REG_MET;
	}
	public String getA1_ORIG_INC_SAL_MET() {
		return A1_ORIG_INC_SAL_MET;
	}
	public void setA1_ORIG_INC_SAL_MET(String a1_ORIG_INC_SAL_MET) {
		A1_ORIG_INC_SAL_MET = a1_ORIG_INC_SAL_MET;
	}
	public String getS1_PHOTO_MET() {
		return S1_PHOTO_MET;
	}
	public void setS1_PHOTO_MET(String s1_PHOTO_MET) {
		S1_PHOTO_MET = s1_PHOTO_MET;
	}
	public String getS1_CITIZN_GOV_PASS_MET() {
		return S1_CITIZN_GOV_PASS_MET;
	}
	public void setS1_CITIZN_GOV_PASS_MET(String s1_CITIZN_GOV_PASS_MET) {
		S1_CITIZN_GOV_PASS_MET = s1_CITIZN_GOV_PASS_MET;
	}
	public String getS1_HM_REG_METD_REC_CRD() {
		return S1_HM_REG_METD_REC_CRD;
	}
	public void setS1_HM_REG_METD_REC_CRD(String s1_HM_REG_METD_REC_CRD) {
		S1_HM_REG_METD_REC_CRD = s1_HM_REG_METD_REC_CRD;
	}
	public String getX_A1_EXT_CRDPK_CUST_FL() {
		return X_A1_EXT_CRDPK_CUST_FL;
	}
	public void setX_A1_EXT_CRDPK_CUST_FL(String x_A1_EXT_CRDPK_CUST_FL) {
		X_A1_EXT_CRDPK_CUST_FL = x_A1_EXT_CRDPK_CUST_FL;
	}
	public String getX_A1_NO_CC_LPM() {
		return X_A1_NO_CC_LPM;
	}
	public void setX_A1_NO_CC_LPM(String x_A1_NO_CC_LPM) {
		X_A1_NO_CC_LPM = x_A1_NO_CC_LPM;
	}
	public String getX_A1_CC_CUSTNO_CPAC() {
		return X_A1_CC_CUSTNO_CPAC;
	}
	public void setX_A1_CC_CUSTNO_CPAC(String x_A1_CC_CUSTNO_CPAC) {
		X_A1_CC_CUSTNO_CPAC = x_A1_CC_CUSTNO_CPAC;
	}
	public String getX_S1_EXT_CRDPK_CUST_FL() {
		return X_S1_EXT_CRDPK_CUST_FL;
	}
	public void setX_S1_EXT_CRDPK_CUST_FL(String x_S1_EXT_CRDPK_CUST_FL) {
		X_S1_EXT_CRDPK_CUST_FL = x_S1_EXT_CRDPK_CUST_FL;
	}
	public String getX_S1_CC_CUSTNO_CPAC() {
		return X_S1_CC_CUSTNO_CPAC;
	}
	public void setX_S1_CC_CUSTNO_CPAC(String x_S1_CC_CUSTNO_CPAC) {
		X_S1_CC_CUSTNO_CPAC = x_S1_CC_CUSTNO_CPAC;
	}
	public String getR_DEC_ORG_RSN_CD() {
		return R_DEC_ORG_RSN_CD;
	}
	public void setR_DEC_ORG_RSN_CD(String r_DEC_ORG_RSN_CD) {
		R_DEC_ORG_RSN_CD = r_DEC_ORG_RSN_CD;
	}
	public String getR_DEC_RSN_CD_TBL() {
		return R_DEC_RSN_CD_TBL;
	}
	public void setR_DEC_RSN_CD_TBL(String r_DEC_RSN_CD_TBL) {
		R_DEC_RSN_CD_TBL = r_DEC_RSN_CD_TBL;
	}
	public String getR_APP_RISK_SCR() {
		return R_APP_RISK_SCR;
	}
	public void setR_APP_RISK_SCR(String r_APP_RISK_SCR) {
		R_APP_RISK_SCR = r_APP_RISK_SCR;
	}
	public String getX_POL_DEC_1() {
		return X_POL_DEC_1;
	}
	public void setX_POL_DEC_1(String x_POL_DEC_1) {
		X_POL_DEC_1 = x_POL_DEC_1;
	}
	public String getX_POL_DEC_2() {
		return X_POL_DEC_2;
	}
	public void setX_POL_DEC_2(String x_POL_DEC_2) {
		X_POL_DEC_2 = x_POL_DEC_2;
	}
	public String getX_POL_DEC_3() {
		return X_POL_DEC_3;
	}
	public void setX_POL_DEC_3(String x_POL_DEC_3) {
		X_POL_DEC_3 = x_POL_DEC_3;
	}
	public String getX_POL_DEC_4() {
		return X_POL_DEC_4;
	}
	public void setX_POL_DEC_4(String x_POL_DEC_4) {
		X_POL_DEC_4 = x_POL_DEC_4;
	}
	public String getX_POL_DEC_5() {
		return X_POL_DEC_5;
	}
	public void setX_POL_DEC_5(String x_POL_DEC_5) {
		X_POL_DEC_5 = x_POL_DEC_5;
	}
	public String getX_POL_DEC_6() {
		return X_POL_DEC_6;
	}
	public void setX_POL_DEC_6(String x_POL_DEC_6) {
		X_POL_DEC_6 = x_POL_DEC_6;
	}
	public String getX_POL_DEC_7() {
		return X_POL_DEC_7;
	}
	public void setX_POL_DEC_7(String x_POL_DEC_7) {
		X_POL_DEC_7 = x_POL_DEC_7;
	}
	public String getX_POL_DEC_8() {
		return X_POL_DEC_8;
	}
	public void setX_POL_DEC_8(String x_POL_DEC_8) {
		X_POL_DEC_8 = x_POL_DEC_8;
	}
	public String getX_POL_DEC_9() {
		return X_POL_DEC_9;
	}
	public void setX_POL_DEC_9(String x_POL_DEC_9) {
		X_POL_DEC_9 = x_POL_DEC_9;
	}
	public String getX_POL_DEC_10() {
		return X_POL_DEC_10;
	}
	public void setX_POL_DEC_10(String x_POL_DEC_10) {
		X_POL_DEC_10 = x_POL_DEC_10;
	}
	public String getAPP_FTHR_REV_RSN_CD() {
		return APP_FTHR_REV_RSN_CD;
	}
	public void setAPP_FTHR_REV_RSN_CD(String aPP_FTHR_REV_RSN_CD) {
		APP_FTHR_REV_RSN_CD = aPP_FTHR_REV_RSN_CD;
	}
	public String getA1_FIN_CRD_LVL1() {
		return A1_FIN_CRD_LVL1;
	}
	public void setA1_FIN_CRD_LVL1(String a1_FIN_CRD_LVL1) {
		A1_FIN_CRD_LVL1 = a1_FIN_CRD_LVL1;
	}
	public String getA1_FIN_CRD_TY1() {
		return A1_FIN_CRD_TY1;
	}
	public void setA1_FIN_CRD_TY1(String a1_FIN_CRD_TY1) {
		A1_FIN_CRD_TY1 = a1_FIN_CRD_TY1;
	}
	public BigDecimal getA1_FIN_CRD_LIM1() {
		return A1_FIN_CRD_LIM1;
	}
	public void setA1_FIN_CRD_LIM1(BigDecimal a1_FIN_CRD_LIM1) {
		A1_FIN_CRD_LIM1 = a1_FIN_CRD_LIM1;
	}
	public String getA1_FIN_BILL_CYC1() {
		return A1_FIN_BILL_CYC1;
	}
	public void setA1_FIN_BILL_CYC1(String a1_FIN_BILL_CYC1) {
		A1_FIN_BILL_CYC1 = a1_FIN_BILL_CYC1;
	}
	public String getA1_FIN_CRD_LVL2() {
		return A1_FIN_CRD_LVL2;
	}
	public void setA1_FIN_CRD_LVL2(String a1_FIN_CRD_LVL2) {
		A1_FIN_CRD_LVL2 = a1_FIN_CRD_LVL2;
	}
	public String getA1_FIN_CRD_TY2() {
		return A1_FIN_CRD_TY2;
	}
	public void setA1_FIN_CRD_TY2(String a1_FIN_CRD_TY2) {
		A1_FIN_CRD_TY2 = a1_FIN_CRD_TY2;
	}
	public BigDecimal getA1_FIN_CRD_LIM2() {
		return A1_FIN_CRD_LIM2;
	}
	public void setA1_FIN_CRD_LIM2(BigDecimal a1_FIN_CRD_LIM2) {
		A1_FIN_CRD_LIM2 = a1_FIN_CRD_LIM2;
	}
	public String getA1_FIN_BILL_CYC2() {
		return A1_FIN_BILL_CYC2;
	}
	public void setA1_FIN_BILL_CYC2(String a1_FIN_BILL_CYC2) {
		A1_FIN_BILL_CYC2 = a1_FIN_BILL_CYC2;
	}
	public String getA1_FIN_CRD_LVL3() {
		return A1_FIN_CRD_LVL3;
	}
	public void setA1_FIN_CRD_LVL3(String a1_FIN_CRD_LVL3) {
		A1_FIN_CRD_LVL3 = a1_FIN_CRD_LVL3;
	}
	public String getA1_FIN_CRD_TY3() {
		return A1_FIN_CRD_TY3;
	}
	public void setA1_FIN_CRD_TY3(String a1_FIN_CRD_TY3) {
		A1_FIN_CRD_TY3 = a1_FIN_CRD_TY3;
	}
	public BigDecimal getA1_FIN_CRD_LIM3() {
		return A1_FIN_CRD_LIM3;
	}
	public void setA1_FIN_CRD_LIM3(BigDecimal a1_FIN_CRD_LIM3) {
		A1_FIN_CRD_LIM3 = a1_FIN_CRD_LIM3;
	}
	public String getA1_FIN_BILL_CYC3() {
		return A1_FIN_BILL_CYC3;
	}
	public void setA1_FIN_BILL_CYC3(String a1_FIN_BILL_CYC3) {
		A1_FIN_BILL_CYC3 = a1_FIN_BILL_CYC3;
	}
	public String getS1_FIN_CRD_LVL1() {
		return S1_FIN_CRD_LVL1;
	}
	public void setS1_FIN_CRD_LVL1(String s1_FIN_CRD_LVL1) {
		S1_FIN_CRD_LVL1 = s1_FIN_CRD_LVL1;
	}
	public String getS1_FIN_CRD_TY1() {
		return S1_FIN_CRD_TY1;
	}
	public void setS1_FIN_CRD_TY1(String s1_FIN_CRD_TY1) {
		S1_FIN_CRD_TY1 = s1_FIN_CRD_TY1;
	}
	public BigDecimal getS1_FIN_CRD_LIM1() {
		return S1_FIN_CRD_LIM1;
	}
	public void setS1_FIN_CRD_LIM1(BigDecimal s1_FIN_CRD_LIM1) {
		S1_FIN_CRD_LIM1 = s1_FIN_CRD_LIM1;
	}
	public String getS1_FIN_BILL_CYC1() {
		return S1_FIN_BILL_CYC1;
	}
	public void setS1_FIN_BILL_CYC1(String s1_FIN_BILL_CYC1) {
		S1_FIN_BILL_CYC1 = s1_FIN_BILL_CYC1;
	}
	public String getS1_FIN_CRD_LVL2() {
		return S1_FIN_CRD_LVL2;
	}
	public void setS1_FIN_CRD_LVL2(String s1_FIN_CRD_LVL2) {
		S1_FIN_CRD_LVL2 = s1_FIN_CRD_LVL2;
	}
	public String getS1_FIN_CRD_TY2() {
		return S1_FIN_CRD_TY2;
	}
	public void setS1_FIN_CRD_TY2(String s1_FIN_CRD_TY2) {
		S1_FIN_CRD_TY2 = s1_FIN_CRD_TY2;
	}
	public BigDecimal getS1_FIN_CRD_LIM2() {
		return S1_FIN_CRD_LIM2;
	}
	public void setS1_FIN_CRD_LIM2(BigDecimal s1_FIN_CRD_LIM2) {
		S1_FIN_CRD_LIM2 = s1_FIN_CRD_LIM2;
	}
	public String getS1_FIN_BILL_CYC2() {
		return S1_FIN_BILL_CYC2;
	}
	public void setS1_FIN_BILL_CYC2(String s1_FIN_BILL_CYC2) {
		S1_FIN_BILL_CYC2 = s1_FIN_BILL_CYC2;
	}
	public String getS1_FIN_CRD_LVL3() {
		return S1_FIN_CRD_LVL3;
	}
	public void setS1_FIN_CRD_LVL3(String s1_FIN_CRD_LVL3) {
		S1_FIN_CRD_LVL3 = s1_FIN_CRD_LVL3;
	}
	public String getS1_FIN_CRD_TY3() {
		return S1_FIN_CRD_TY3;
	}
	public void setS1_FIN_CRD_TY3(String s1_FIN_CRD_TY3) {
		S1_FIN_CRD_TY3 = s1_FIN_CRD_TY3;
	}
	public BigDecimal getS1_FIN_CRD_LIM3() {
		return S1_FIN_CRD_LIM3;
	}
	public void setS1_FIN_CRD_LIM3(BigDecimal s1_FIN_CRD_LIM3) {
		S1_FIN_CRD_LIM3 = s1_FIN_CRD_LIM3;
	}
	public String getS1_FIN_BILL_CYC3() {
		return S1_FIN_BILL_CYC3;
	}
	public void setS1_FIN_BILL_CYC3(String s1_FIN_BILL_CYC3) {
		S1_FIN_BILL_CYC3 = s1_FIN_BILL_CYC3;
	}
	public String getANALYST_DEC() {
		return ANALYST_DEC;
	}
	public void setANALYST_DEC(String aNALYST_DEC) {
		ANALYST_DEC = aNALYST_DEC;
	}
	public String getUNDWRT_DEC() {
		return UNDWRT_DEC;
	}
	public void setUNDWRT_DEC(String uNDWRT_DEC) {
		UNDWRT_DEC = uNDWRT_DEC;
	}
	public String getG_FIN_DEC() {
		return G_FIN_DEC;
	}
	public void setG_FIN_DEC(String g_FIN_DEC) {
		G_FIN_DEC = g_FIN_DEC;
	}
	public String getG_ANLYST_REV_COMP_FL() {
		return G_ANLYST_REV_COMP_FL;
	}
	public void setG_ANLYST_REV_COMP_FL(String g_ANLYST_REV_COMP_FL) {
		G_ANLYST_REV_COMP_FL = g_ANLYST_REV_COMP_FL;
	}
	public String getG_UNDWRT_REV_COMP_FL() {
		return G_UNDWRT_REV_COMP_FL;
	}
	public void setG_UNDWRT_REV_COMP_FL(String g_UNDWRT_REV_COMP_FL) {
		G_UNDWRT_REV_COMP_FL = g_UNDWRT_REV_COMP_FL;
	}
	public String getX_A1_CRD_CD1() {
		return X_A1_CRD_CD1;
	}
	public void setX_A1_CRD_CD1(String x_A1_CRD_CD1) {
		X_A1_CRD_CD1 = x_A1_CRD_CD1;
	}
	public String getX_A1_CRD_PLSTC_TY1() {
		return X_A1_CRD_PLSTC_TY1;
	}
	public void setX_A1_CRD_PLSTC_TY1(String x_A1_CRD_PLSTC_TY1) {
		X_A1_CRD_PLSTC_TY1 = x_A1_CRD_PLSTC_TY1;
	}
	public String getA1_CRD_ADD_TRCK1() {
		return A1_CRD_ADD_TRCK1;
	}
	public void setA1_CRD_ADD_TRCK1(String a1_CRD_ADD_TRCK1) {
		A1_CRD_ADD_TRCK1 = a1_CRD_ADD_TRCK1;
	}
	public String getX_A1_CRD_PREF_NO1() {
		return X_A1_CRD_PREF_NO1;
	}
	public void setX_A1_CRD_PREF_NO1(String x_A1_CRD_PREF_NO1) {
		X_A1_CRD_PREF_NO1 = x_A1_CRD_PREF_NO1;
	}
	public String getX_A1_2ND_LINE_EMB1() {
		return X_A1_2ND_LINE_EMB1;
	}
	public void setX_A1_2ND_LINE_EMB1(String x_A1_2ND_LINE_EMB1) {
		X_A1_2ND_LINE_EMB1 = x_A1_2ND_LINE_EMB1;
	}
	public String getA1_PRIOR_PASS_CRD_NO1() {
		return A1_PRIOR_PASS_CRD_NO1;
	}
	public void setA1_PRIOR_PASS_CRD_NO1(String a1_PRIOR_PASS_CRD_NO1) {
		A1_PRIOR_PASS_CRD_NO1 = a1_PRIOR_PASS_CRD_NO1;
	}
	public String getA1_ACT_FL1() {
		return A1_ACT_FL1;
	}
	public void setA1_ACT_FL1(String a1_ACT_FL1) {
		A1_ACT_FL1 = a1_ACT_FL1;
	}
	public String getA1_CRD_FEE_DT1() {
		return A1_CRD_FEE_DT1;
	}
	public void setA1_CRD_FEE_DT1(String a1_CRD_FEE_DT1) {
		A1_CRD_FEE_DT1 = a1_CRD_FEE_DT1;
	}
	public String getX_A1_CRD_CD2() {
		return X_A1_CRD_CD2;
	}
	public void setX_A1_CRD_CD2(String x_A1_CRD_CD2) {
		X_A1_CRD_CD2 = x_A1_CRD_CD2;
	}
	public String getX_A1_CRD_PLSTC_TY2() {
		return X_A1_CRD_PLSTC_TY2;
	}
	public void setX_A1_CRD_PLSTC_TY2(String x_A1_CRD_PLSTC_TY2) {
		X_A1_CRD_PLSTC_TY2 = x_A1_CRD_PLSTC_TY2;
	}
	public String getA1_CRD_ADD_TRCK2() {
		return A1_CRD_ADD_TRCK2;
	}
	public void setA1_CRD_ADD_TRCK2(String a1_CRD_ADD_TRCK2) {
		A1_CRD_ADD_TRCK2 = a1_CRD_ADD_TRCK2;
	}
	public String getX_A1_CRD_PREF_NO2() {
		return X_A1_CRD_PREF_NO2;
	}
	public void setX_A1_CRD_PREF_NO2(String x_A1_CRD_PREF_NO2) {
		X_A1_CRD_PREF_NO2 = x_A1_CRD_PREF_NO2;
	}
	public String getX_A1_2ND_LINE_EMB2() {
		return X_A1_2ND_LINE_EMB2;
	}
	public void setX_A1_2ND_LINE_EMB2(String x_A1_2ND_LINE_EMB2) {
		X_A1_2ND_LINE_EMB2 = x_A1_2ND_LINE_EMB2;
	}
	public String getA1_PRIOR_PASS_CRD_NO2() {
		return A1_PRIOR_PASS_CRD_NO2;
	}
	public void setA1_PRIOR_PASS_CRD_NO2(String a1_PRIOR_PASS_CRD_NO2) {
		A1_PRIOR_PASS_CRD_NO2 = a1_PRIOR_PASS_CRD_NO2;
	}
	public String getA1_ACT_FL2() {
		return A1_ACT_FL2;
	}
	public void setA1_ACT_FL2(String a1_ACT_FL2) {
		A1_ACT_FL2 = a1_ACT_FL2;
	}
	public String getA1_CRD_FEE_DT2() {
		return A1_CRD_FEE_DT2;
	}
	public void setA1_CRD_FEE_DT2(String a1_CRD_FEE_DT2) {
		A1_CRD_FEE_DT2 = a1_CRD_FEE_DT2;
	}
	public String getX_A1_CRD_CD3() {
		return X_A1_CRD_CD3;
	}
	public void setX_A1_CRD_CD3(String x_A1_CRD_CD3) {
		X_A1_CRD_CD3 = x_A1_CRD_CD3;
	}
	public String getX_A1_CRD_PLSTC_TY3() {
		return X_A1_CRD_PLSTC_TY3;
	}
	public void setX_A1_CRD_PLSTC_TY3(String x_A1_CRD_PLSTC_TY3) {
		X_A1_CRD_PLSTC_TY3 = x_A1_CRD_PLSTC_TY3;
	}
	public String getA1_CRD_ADD_TRCK3() {
		return A1_CRD_ADD_TRCK3;
	}
	public void setA1_CRD_ADD_TRCK3(String a1_CRD_ADD_TRCK3) {
		A1_CRD_ADD_TRCK3 = a1_CRD_ADD_TRCK3;
	}
	public String getX_A1_CRD_PREF_NO3() {
		return X_A1_CRD_PREF_NO3;
	}
	public void setX_A1_CRD_PREF_NO3(String x_A1_CRD_PREF_NO3) {
		X_A1_CRD_PREF_NO3 = x_A1_CRD_PREF_NO3;
	}
	public String getX_A1_2ND_LINE_EMB3() {
		return X_A1_2ND_LINE_EMB3;
	}
	public void setX_A1_2ND_LINE_EMB3(String x_A1_2ND_LINE_EMB3) {
		X_A1_2ND_LINE_EMB3 = x_A1_2ND_LINE_EMB3;
	}
	public String getA1_PRIOR_PASS_CRD_NO3() {
		return A1_PRIOR_PASS_CRD_NO3;
	}
	public void setA1_PRIOR_PASS_CRD_NO3(String a1_PRIOR_PASS_CRD_NO3) {
		A1_PRIOR_PASS_CRD_NO3 = a1_PRIOR_PASS_CRD_NO3;
	}
	public String getA1_ACT_FL3() {
		return A1_ACT_FL3;
	}
	public void setA1_ACT_FL3(String a1_ACT_FL3) {
		A1_ACT_FL3 = a1_ACT_FL3;
	}
	public String getA1_CRD_FEE_DT3() {
		return A1_CRD_FEE_DT3;
	}
	public void setA1_CRD_FEE_DT3(String a1_CRD_FEE_DT3) {
		A1_CRD_FEE_DT3 = a1_CRD_FEE_DT3;
	}
	public String getX_S1_CRD_CD1() {
		return X_S1_CRD_CD1;
	}
	public void setX_S1_CRD_CD1(String x_S1_CRD_CD1) {
		X_S1_CRD_CD1 = x_S1_CRD_CD1;
	}
	public String getX_S1_CRD_PLSTC_TY1() {
		return X_S1_CRD_PLSTC_TY1;
	}
	public void setX_S1_CRD_PLSTC_TY1(String x_S1_CRD_PLSTC_TY1) {
		X_S1_CRD_PLSTC_TY1 = x_S1_CRD_PLSTC_TY1;
	}
	public String getS1_CRD_ADD_TRCK1() {
		return S1_CRD_ADD_TRCK1;
	}
	public void setS1_CRD_ADD_TRCK1(String s1_CRD_ADD_TRCK1) {
		S1_CRD_ADD_TRCK1 = s1_CRD_ADD_TRCK1;
	}
	public String getX_S1_CRD_PREF_NO1() {
		return X_S1_CRD_PREF_NO1;
	}
	public void setX_S1_CRD_PREF_NO1(String x_S1_CRD_PREF_NO1) {
		X_S1_CRD_PREF_NO1 = x_S1_CRD_PREF_NO1;
	}
	public String getX_S1_2ND_LINE_EMB1() {
		return X_S1_2ND_LINE_EMB1;
	}
	public void setX_S1_2ND_LINE_EMB1(String x_S1_2ND_LINE_EMB1) {
		X_S1_2ND_LINE_EMB1 = x_S1_2ND_LINE_EMB1;
	}
	public String getS1_PRIOR_PASS_CRD_NO1() {
		return S1_PRIOR_PASS_CRD_NO1;
	}
	public void setS1_PRIOR_PASS_CRD_NO1(String s1_PRIOR_PASS_CRD_NO1) {
		S1_PRIOR_PASS_CRD_NO1 = s1_PRIOR_PASS_CRD_NO1;
	}
	public String getS1_ACT_FL1() {
		return S1_ACT_FL1;
	}
	public void setS1_ACT_FL1(String s1_ACT_FL1) {
		S1_ACT_FL1 = s1_ACT_FL1;
	}
	public String getS1_CRD_FEE_DT1() {
		return S1_CRD_FEE_DT1;
	}
	public void setS1_CRD_FEE_DT1(String s1_CRD_FEE_DT1) {
		S1_CRD_FEE_DT1 = s1_CRD_FEE_DT1;
	}
	public String getX_S1_CRD_CD2() {
		return X_S1_CRD_CD2;
	}
	public void setX_S1_CRD_CD2(String x_S1_CRD_CD2) {
		X_S1_CRD_CD2 = x_S1_CRD_CD2;
	}
	public String getX_S1_CRD_PLSTC_TY2() {
		return X_S1_CRD_PLSTC_TY2;
	}
	public void setX_S1_CRD_PLSTC_TY2(String x_S1_CRD_PLSTC_TY2) {
		X_S1_CRD_PLSTC_TY2 = x_S1_CRD_PLSTC_TY2;
	}
	public String getS1_CRD_ADD_TRCK2() {
		return S1_CRD_ADD_TRCK2;
	}
	public void setS1_CRD_ADD_TRCK2(String s1_CRD_ADD_TRCK2) {
		S1_CRD_ADD_TRCK2 = s1_CRD_ADD_TRCK2;
	}
	public String getX_S1_CRD_PREF_NO2() {
		return X_S1_CRD_PREF_NO2;
	}
	public void setX_S1_CRD_PREF_NO2(String x_S1_CRD_PREF_NO2) {
		X_S1_CRD_PREF_NO2 = x_S1_CRD_PREF_NO2;
	}
	public String getX_S1_2ND_LINE_EMB2() {
		return X_S1_2ND_LINE_EMB2;
	}
	public void setX_S1_2ND_LINE_EMB2(String x_S1_2ND_LINE_EMB2) {
		X_S1_2ND_LINE_EMB2 = x_S1_2ND_LINE_EMB2;
	}
	public String getS1_PRIOR_PASS_CRD_NO2() {
		return S1_PRIOR_PASS_CRD_NO2;
	}
	public void setS1_PRIOR_PASS_CRD_NO2(String s1_PRIOR_PASS_CRD_NO2) {
		S1_PRIOR_PASS_CRD_NO2 = s1_PRIOR_PASS_CRD_NO2;
	}
	public String getS1_ACT_FL2() {
		return S1_ACT_FL2;
	}
	public void setS1_ACT_FL2(String s1_ACT_FL2) {
		S1_ACT_FL2 = s1_ACT_FL2;
	}
	public String getS1_CRD_FEE_DT2() {
		return S1_CRD_FEE_DT2;
	}
	public void setS1_CRD_FEE_DT2(String s1_CRD_FEE_DT2) {
		S1_CRD_FEE_DT2 = s1_CRD_FEE_DT2;
	}
	public String getX_S1_CRD_CD3() {
		return X_S1_CRD_CD3;
	}
	public void setX_S1_CRD_CD3(String x_S1_CRD_CD3) {
		X_S1_CRD_CD3 = x_S1_CRD_CD3;
	}
	public String getX_S1_CRD_PLSTC_TY3() {
		return X_S1_CRD_PLSTC_TY3;
	}
	public void setX_S1_CRD_PLSTC_TY3(String x_S1_CRD_PLSTC_TY3) {
		X_S1_CRD_PLSTC_TY3 = x_S1_CRD_PLSTC_TY3;
	}
	public String getS1_CRD_ADD_TRCK3() {
		return S1_CRD_ADD_TRCK3;
	}
	public void setS1_CRD_ADD_TRCK3(String s1_CRD_ADD_TRCK3) {
		S1_CRD_ADD_TRCK3 = s1_CRD_ADD_TRCK3;
	}
	public String getX_S1_CRD_PREF_NO3() {
		return X_S1_CRD_PREF_NO3;
	}
	public void setX_S1_CRD_PREF_NO3(String x_S1_CRD_PREF_NO3) {
		X_S1_CRD_PREF_NO3 = x_S1_CRD_PREF_NO3;
	}
	public String getX_S1_2ND_LINE_EMB3() {
		return X_S1_2ND_LINE_EMB3;
	}
	public void setX_S1_2ND_LINE_EMB3(String x_S1_2ND_LINE_EMB3) {
		X_S1_2ND_LINE_EMB3 = x_S1_2ND_LINE_EMB3;
	}
	public String getS1_PRIOR_PASS_CRD_NO3() {
		return S1_PRIOR_PASS_CRD_NO3;
	}
	public void setS1_PRIOR_PASS_CRD_NO3(String s1_PRIOR_PASS_CRD_NO3) {
		S1_PRIOR_PASS_CRD_NO3 = s1_PRIOR_PASS_CRD_NO3;
	}
	public String getS1_ACT_FL3() {
		return S1_ACT_FL3;
	}
	public void setS1_ACT_FL3(String s1_ACT_FL3) {
		S1_ACT_FL3 = s1_ACT_FL3;
	}
	public String getS1_CRD_FEE_DT3() {
		return S1_CRD_FEE_DT3;
	}
	public void setS1_CRD_FEE_DT3(String s1_CRD_FEE_DT3) {
		S1_CRD_FEE_DT3 = s1_CRD_FEE_DT3;
	}
	public String getA1_CRD_ACT_STAT1() {
		return A1_CRD_ACT_STAT1;
	}
	public void setA1_CRD_ACT_STAT1(String a1_CRD_ACT_STAT1) {
		A1_CRD_ACT_STAT1 = a1_CRD_ACT_STAT1;
	}
	public String getA1_CRD_ACT_DT1() {
		return A1_CRD_ACT_DT1;
	}
	public void setA1_CRD_ACT_DT1(String a1_CRD_ACT_DT1) {
		A1_CRD_ACT_DT1 = a1_CRD_ACT_DT1;
	}
	public String getA1_CRD_ACT_STAT2() {
		return A1_CRD_ACT_STAT2;
	}
	public void setA1_CRD_ACT_STAT2(String a1_CRD_ACT_STAT2) {
		A1_CRD_ACT_STAT2 = a1_CRD_ACT_STAT2;
	}
	public String getA1_CRD_ACT_DT2() {
		return A1_CRD_ACT_DT2;
	}
	public void setA1_CRD_ACT_DT2(String a1_CRD_ACT_DT2) {
		A1_CRD_ACT_DT2 = a1_CRD_ACT_DT2;
	}
	public String getA1_CRD_ACT_STAT3() {
		return A1_CRD_ACT_STAT3;
	}
	public void setA1_CRD_ACT_STAT3(String a1_CRD_ACT_STAT3) {
		A1_CRD_ACT_STAT3 = a1_CRD_ACT_STAT3;
	}
	public String getA1_CRD_ACT_DT3() {
		return A1_CRD_ACT_DT3;
	}
	public void setA1_CRD_ACT_DT3(String a1_CRD_ACT_DT3) {
		A1_CRD_ACT_DT3 = a1_CRD_ACT_DT3;
	}
	public String getS1_CRD_ACT_STAT1() {
		return S1_CRD_ACT_STAT1;
	}
	public void setS1_CRD_ACT_STAT1(String s1_CRD_ACT_STAT1) {
		S1_CRD_ACT_STAT1 = s1_CRD_ACT_STAT1;
	}
	public String getS1_CRD_ACT_DT1() {
		return S1_CRD_ACT_DT1;
	}
	public void setS1_CRD_ACT_DT1(String s1_CRD_ACT_DT1) {
		S1_CRD_ACT_DT1 = s1_CRD_ACT_DT1;
	}
	public String getS1_CRD_ACT_STAT2() {
		return S1_CRD_ACT_STAT2;
	}
	public void setS1_CRD_ACT_STAT2(String s1_CRD_ACT_STAT2) {
		S1_CRD_ACT_STAT2 = s1_CRD_ACT_STAT2;
	}
	public String getS1_CRD_ACT_DT2() {
		return S1_CRD_ACT_DT2;
	}
	public void setS1_CRD_ACT_DT2(String s1_CRD_ACT_DT2) {
		S1_CRD_ACT_DT2 = s1_CRD_ACT_DT2;
	}
	public String getS1_CRD_ACT_STAT3() {
		return S1_CRD_ACT_STAT3;
	}
	public void setS1_CRD_ACT_STAT3(String s1_CRD_ACT_STAT3) {
		S1_CRD_ACT_STAT3 = s1_CRD_ACT_STAT3;
	}
	public String getS1_CRD_ACT_DT3() {
		return S1_CRD_ACT_DT3;
	}
	public void setS1_CRD_ACT_DT3(String s1_CRD_ACT_DT3) {
		S1_CRD_ACT_DT3 = s1_CRD_ACT_DT3;
	}
	public String getLAPSE_REAS_CD() {
		return LAPSE_REAS_CD;
	}
	public void setLAPSE_REAS_CD(String lAPSE_REAS_CD) {
		LAPSE_REAS_CD = lAPSE_REAS_CD;
	}
	public String getPREM_CHKLST_COMP() {
		return PREM_CHKLST_COMP;
	}
	public void setPREM_CHKLST_COMP(String pREM_CHKLST_COMP) {
		PREM_CHKLST_COMP = pREM_CHKLST_COMP;
	}
	public String getORRIDE_POSTAPP_SYS_DEC() {
		return ORRIDE_POSTAPP_SYS_DEC;
	}
	public void setORRIDE_POSTAPP_SYS_DEC(String oRRIDE_POSTAPP_SYS_DEC) {
		ORRIDE_POSTAPP_SYS_DEC = oRRIDE_POSTAPP_SYS_DEC;
	}
	public String getPOST_APP_ORRIDE_RCODE() {
		return POST_APP_ORRIDE_RCODE;
	}
	public void setPOST_APP_ORRIDE_RCODE(String pOST_APP_ORRIDE_RCODE) {
		POST_APP_ORRIDE_RCODE = pOST_APP_ORRIDE_RCODE;
	}
	public String getORRIDE_POSTINT_SYS_DEC() {
		return ORRIDE_POSTINT_SYS_DEC;
	}
	public void setORRIDE_POSTINT_SYS_DEC(String oRRIDE_POSTINT_SYS_DEC) {
		ORRIDE_POSTINT_SYS_DEC = oRRIDE_POSTINT_SYS_DEC;
	}
	public String getPOST_INT_ORRIDE_RCODE() {
		return POST_INT_ORRIDE_RCODE;
	}
	public void setPOST_INT_ORRIDE_RCODE(String pOST_INT_ORRIDE_RCODE) {
		POST_INT_ORRIDE_RCODE = pOST_INT_ORRIDE_RCODE;
	}
	public String getORRIDE_POSTEXT_SYS_DEC() {
		return ORRIDE_POSTEXT_SYS_DEC;
	}
	public void setORRIDE_POSTEXT_SYS_DEC(String oRRIDE_POSTEXT_SYS_DEC) {
		ORRIDE_POSTEXT_SYS_DEC = oRRIDE_POSTEXT_SYS_DEC;
	}
	public String getPOST_EXT_ORRIDE_RCODE() {
		return POST_EXT_ORRIDE_RCODE;
	}
	public void setPOST_EXT_ORRIDE_RCODE(String pOST_EXT_ORRIDE_RCODE) {
		POST_EXT_ORRIDE_RCODE = pOST_EXT_ORRIDE_RCODE;
	}
	public String getG_ORG_CD() {
		return G_ORG_CD;
	}
	public void setG_ORG_CD(String g_ORG_CD) {
		G_ORG_CD = g_ORG_CD;
	}
	public String getA1_CC_CONSENT_FLAG() {
		return A1_CC_CONSENT_FLAG;
	}
	public void setA1_CC_CONSENT_FLAG(String a1_CC_CONSENT_FLAG) {
		A1_CC_CONSENT_FLAG = a1_CC_CONSENT_FLAG;
	}
	public String getA1_DATEOF_CONCENT_CE() {
		return A1_DATEOF_CONCENT_CE;
	}
	public void setA1_DATEOF_CONCENT_CE(String a1_DATEOF_CONCENT_CE) {
		A1_DATEOF_CONCENT_CE = a1_DATEOF_CONCENT_CE;
	}
	public String getS1_CC_CONSENT_FLAG() {
		return S1_CC_CONSENT_FLAG;
	}
	public void setS1_CC_CONSENT_FLAG(String s1_CC_CONSENT_FLAG) {
		S1_CC_CONSENT_FLAG = s1_CC_CONSENT_FLAG;
	}
	public String getS1_DATEOF_CONCENT_CE() {
		return S1_DATEOF_CONCENT_CE;
	}
	public void setS1_DATEOF_CONCENT_CE(String s1_DATEOF_CONCENT_CE) {
		S1_DATEOF_CONCENT_CE = s1_DATEOF_CONCENT_CE;
	}
	public String getX1_CARD_NO1() {
		return X1_CARD_NO1;
	}
	public void setX1_CARD_NO1(String x1_CARD_NO1) {
		X1_CARD_NO1 = x1_CARD_NO1;
	}
	public String getX_S1_CARD_NO1() {
		return X_S1_CARD_NO1;
	}
	public void setX_S1_CARD_NO1(String x_S1_CARD_NO1) {
		X_S1_CARD_NO1 = x_S1_CARD_NO1;
	}
	public String getX1_CARD_NO2() {
		return X1_CARD_NO2;
	}
	public void setX1_CARD_NO2(String x1_CARD_NO2) {
		X1_CARD_NO2 = x1_CARD_NO2;
	}
	public String getX_S1_CARD_NO2() {
		return X_S1_CARD_NO2;
	}
	public void setX_S1_CARD_NO2(String x_S1_CARD_NO2) {
		X_S1_CARD_NO2 = x_S1_CARD_NO2;
	}
	public String getX1_CARD_NO3() {
		return X1_CARD_NO3;
	}
	public void setX1_CARD_NO3(String x1_CARD_NO3) {
		X1_CARD_NO3 = x1_CARD_NO3;
	}
	public String getX_S1_CARD_NO3() {
		return X_S1_CARD_NO3;
	}
	public void setX_S1_CARD_NO3(String x_S1_CARD_NO3) {
		X_S1_CARD_NO3 = x_S1_CARD_NO3;
	}
	public String getA1_DIRECT_MAIL_FLAG() {
		return A1_DIRECT_MAIL_FLAG;
	}
	public void setA1_DIRECT_MAIL_FLAG(String a1_DIRECT_MAIL_FLAG) {
		A1_DIRECT_MAIL_FLAG = a1_DIRECT_MAIL_FLAG;
	}
	public String getS1_DIRECT_MAIL_FLAG() {
		return S1_DIRECT_MAIL_FLAG;
	}
	public void setS1_DIRECT_MAIL_FLAG(String s1_DIRECT_MAIL_FLAG) {
		S1_DIRECT_MAIL_FLAG = s1_DIRECT_MAIL_FLAG;
	}
	public String getG_STAT12_OP_ID() {
		return G_STAT12_OP_ID;
	}
	public void setG_STAT12_OP_ID(String g_STAT12_OP_ID) {
		G_STAT12_OP_ID = g_STAT12_OP_ID;
	}
	public String getA1_1ST_NM_ENG_NEW() {
		return A1_1ST_NM_ENG_NEW;
	}
	public void setA1_1ST_NM_ENG_NEW(String a1_1st_NM_ENG_NEW) {
		A1_1ST_NM_ENG_NEW = a1_1st_NM_ENG_NEW;
	}
	public String getA1_SURNAME_ENG_NEW() {
		return A1_SURNAME_ENG_NEW;
	}
	public void setA1_SURNAME_ENG_NEW(String a1_SURNAME_ENG_NEW) {
		A1_SURNAME_ENG_NEW = a1_SURNAME_ENG_NEW;
	}
	public String getS1_1ST_NM_ENG_NEW() {
		return S1_1ST_NM_ENG_NEW;
	}
	public void setS1_1ST_NM_ENG_NEW(String s1_1st_NM_ENG_NEW) {
		S1_1ST_NM_ENG_NEW = s1_1st_NM_ENG_NEW;
	}
	public String getS1_SURNAME_ENG_NEW() {
		return S1_SURNAME_ENG_NEW;
	}
	public void setS1_SURNAME_ENG_NEW(String s1_SURNAME_ENG_NEW) {
		S1_SURNAME_ENG_NEW = s1_SURNAME_ENG_NEW;
	}
	public String getA1_CARD_FEE_FLAG1() {
		return A1_CARD_FEE_FLAG1;
	}
	public void setA1_CARD_FEE_FLAG1(String a1_CARD_FEE_FLAG1) {
		A1_CARD_FEE_FLAG1 = a1_CARD_FEE_FLAG1;
	}
	public String getA1_CARD_FEE_FLAG2() {
		return A1_CARD_FEE_FLAG2;
	}
	public void setA1_CARD_FEE_FLAG2(String a1_CARD_FEE_FLAG2) {
		A1_CARD_FEE_FLAG2 = a1_CARD_FEE_FLAG2;
	}
	public String getA1_CARD_FEE_FLAG3() {
		return A1_CARD_FEE_FLAG3;
	}
	public void setA1_CARD_FEE_FLAG3(String a1_CARD_FEE_FLAG3) {
		A1_CARD_FEE_FLAG3 = a1_CARD_FEE_FLAG3;
	}
	public String getS1_CARD_FEE_FLAG1() {
		return S1_CARD_FEE_FLAG1;
	}
	public void setS1_CARD_FEE_FLAG1(String s1_CARD_FEE_FLAG1) {
		S1_CARD_FEE_FLAG1 = s1_CARD_FEE_FLAG1;
	}
	public String getS1_CARD_FEE_FLAG2() {
		return S1_CARD_FEE_FLAG2;
	}
	public void setS1_CARD_FEE_FLAG2(String s1_CARD_FEE_FLAG2) {
		S1_CARD_FEE_FLAG2 = s1_CARD_FEE_FLAG2;
	}
	public String getS1_CARD_FEE_FLAG3() {
		return S1_CARD_FEE_FLAG3;
	}
	public void setS1_CARD_FEE_FLAG3(String s1_CARD_FEE_FLAG3) {
		S1_CARD_FEE_FLAG3 = s1_CARD_FEE_FLAG3;
	}
	public String getA1_INSUR_FLAG1() {
		return A1_INSUR_FLAG1;
	}
	public void setA1_INSUR_FLAG1(String a1_INSUR_FLAG1) {
		A1_INSUR_FLAG1 = a1_INSUR_FLAG1;
	}
	public String getA1_INSUR_FLAG2() {
		return A1_INSUR_FLAG2;
	}
	public void setA1_INSUR_FLAG2(String a1_INSUR_FLAG2) {
		A1_INSUR_FLAG2 = a1_INSUR_FLAG2;
	}
	public String getA1_INSUR_FLAG3() {
		return A1_INSUR_FLAG3;
	}
	public void setA1_INSUR_FLAG3(String a1_INSUR_FLAG3) {
		A1_INSUR_FLAG3 = a1_INSUR_FLAG3;
	}
	public String getS1_INSUR_FLAG1() {
		return S1_INSUR_FLAG1;
	}
	public void setS1_INSUR_FLAG1(String s1_INSUR_FLAG1) {
		S1_INSUR_FLAG1 = s1_INSUR_FLAG1;
	}
	public String getS1_INSUR_FLAG2() {
		return S1_INSUR_FLAG2;
	}
	public void setS1_INSUR_FLAG2(String s1_INSUR_FLAG2) {
		S1_INSUR_FLAG2 = s1_INSUR_FLAG2;
	}
	public String getS1_INSUR_FLAG3() {
		return S1_INSUR_FLAG3;
	}
	public void setS1_INSUR_FLAG3(String s1_INSUR_FLAG3) {
		S1_INSUR_FLAG3 = s1_INSUR_FLAG3;
	}
	public String getA1_TOPS_ACCT_NO_1() {
		return A1_TOPS_ACCT_NO_1;
	}
	public void setA1_TOPS_ACCT_NO_1(String a1_TOPS_ACCT_NO_1) {
		A1_TOPS_ACCT_NO_1 = a1_TOPS_ACCT_NO_1;
	}
	public String getA1_TOPS_ACCT_NO_2() {
		return A1_TOPS_ACCT_NO_2;
	}
	public void setA1_TOPS_ACCT_NO_2(String a1_TOPS_ACCT_NO_2) {
		A1_TOPS_ACCT_NO_2 = a1_TOPS_ACCT_NO_2;
	}
	public String getA1_TOPS_ACCT_NO_3() {
		return A1_TOPS_ACCT_NO_3;
	}
	public void setA1_TOPS_ACCT_NO_3(String a1_TOPS_ACCT_NO_3) {
		A1_TOPS_ACCT_NO_3 = a1_TOPS_ACCT_NO_3;
	}
	public String getS1_TOPS_ACCT_NO_1() {
		return S1_TOPS_ACCT_NO_1;
	}
	public void setS1_TOPS_ACCT_NO_1(String s1_TOPS_ACCT_NO_1) {
		S1_TOPS_ACCT_NO_1 = s1_TOPS_ACCT_NO_1;
	}
	public String getS1_TOPS_ACCT_NO_2() {
		return S1_TOPS_ACCT_NO_2;
	}
	public void setS1_TOPS_ACCT_NO_2(String s1_TOPS_ACCT_NO_2) {
		S1_TOPS_ACCT_NO_2 = s1_TOPS_ACCT_NO_2;
	}
	public String getS1_TOPS_ACCT_NO_3() {
		return S1_TOPS_ACCT_NO_3;
	}
	public void setS1_TOPS_ACCT_NO_3(String s1_TOPS_ACCT_NO_3) {
		S1_TOPS_ACCT_NO_3 = s1_TOPS_ACCT_NO_3;
	}
	public String getA1_SME_GRP() {
		return A1_SME_GRP;
	}
	public void setA1_SME_GRP(String a1_SME_GRP) {
		A1_SME_GRP = a1_SME_GRP;
	}
	public String getA1_CC_CUSTNO_LINK_SME() {
		return A1_CC_CUSTNO_LINK_SME;
	}
	public void setA1_CC_CUSTNO_LINK_SME(String a1_CC_CUSTNO_LINK_SME) {
		A1_CC_CUSTNO_LINK_SME = a1_CC_CUSTNO_LINK_SME;
	}
	public String getS1_CC_CUSTNO_LINK_SME() {
		return S1_CC_CUSTNO_LINK_SME;
	}
	public void setS1_CC_CUSTNO_LINK_SME(String s1_CC_CUSTNO_LINK_SME) {
		S1_CC_CUSTNO_LINK_SME = s1_CC_CUSTNO_LINK_SME;
	}
	public String getAPP_RISK_GR_STR_ID() {
		return APP_RISK_GR_STR_ID;
	}
	public void setAPP_RISK_GR_STR_ID(String aPP_RISK_GR_STR_ID) {
		APP_RISK_GR_STR_ID = aPP_RISK_GR_STR_ID;
	}
	public String getDTAC_TEL_NO() {
		return DTAC_TEL_NO;
	}
	public void setDTAC_TEL_NO(String dTAC_TEL_NO) {
		DTAC_TEL_NO = dTAC_TEL_NO;
	}
	public String getTRAN_VAT_CODE() {
		return TRAN_VAT_CODE;
	}
	public void setTRAN_VAT_CODE(String tRAN_VAT_CODE) {
		TRAN_VAT_CODE = tRAN_VAT_CODE;
	}
	public String getTRAN__SOURCE_FLAG() {
		return TRAN__SOURCE_FLAG;
	}
	public void setTRAN__SOURCE_FLAG(String tRAN__SOURCE_FLAG) {
		TRAN__SOURCE_FLAG = tRAN__SOURCE_FLAG;
	}
	public String getFILLER() {
		return FILLER;
	}
	public void setFILLER(String fILLER) {
		FILLER = fILLER;
	}
	public List<String> getERROR_MSG() {
		return ERROR_MSG;
	}
	public void setERROR_MSG(List<String> eRROR_MSG) {
		ERROR_MSG = eRROR_MSG;
	}
}
