package com.eaf.service.common.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CISCustomerDataM implements Serializable, Cloneable{
	private String ipId;
	private String vldFmDt;
	private String actStrtDt;
	private String actEndDt;
	private String prvnF;
	private String mrgStCd;
	private String ipStCd;
	private String ipTpCd;
	private String ipLglStcTpCd;
	private String brthEstbDt;
	private String docItmCd;
	private String ipTaxId;
	private String identNo;
	private String issuDt;
	private String expDt;
	private String govtIssur;
	private String domcBrNo;
	private String ipPerfStCd;
	private String consndDt;
	private String consndF;
	private String consndSrcStmCd;
	private String cstLke;
	private String cstDslke;
	private String favMediaCd;
	private String favMediaOthrInf;
	private String prefCtcMthCd;
	private String prefCtcTm;
	private String astExcldLand;
	private String thTtl;
	private String thFrstNm;
	private String thMdlNm;
	private String thSurnm;
	private String enTtl;
	private String enFrstNm;
	private String enMdlNm;
	private String enSurnm;
	private String deathDt;
	private String deathF;
	private String gndCd;
	private String rlgCd;
	private String natCd;
	private String raceCd;
	private String marStCd;
	private String edInstCd;
	private String edInstOthrInf;
	private String ctfTpCd;
	private String ocpCd;
	private String ocpDt;
	private String posCd;
	private String profCd;
	private String emprNm;
	private String idvIncmSegCd;
	private String famIncmSegCd;
	private String yngstDpndBrthDt;
	private String oldstDpndBrthDt;
	private String noOfDpndChl;
	private String noOfDpndChlDt;
	private String orgLgltyEndDt;
	private String noOfEmp;
	private String orgRevSegCd;
	private String orgRgstCptl;
	private String orgIncmCashF;
	private String orgLglBsnF;
	private String orgProfF;
	private String bsnDsc;
	private String primSegCd;
	private String primSubSegCd;
	private String dualSegCd;
	private String dualSubSegCd;
	private String dualSegDt;
	private String kbnkIdyClCd;
	private String coIdyClCd;
	private String cstRefrNm1;
	private String cstRelTpCd1;
	private String telHmNo1;
	private String telHmExnNo1;
	private String telOffcNo1;
	private String telOffcExnNo1;
	private String telMblNo1;
	private String cstRefrNm2;
	private String cstRelTpCd2;
	private String telHmNo2;
	private String telHmExnNo2;
	private String telOffcNo2;
	private String telOffcExnNo2;
	private String telMblNo2;
	private String lastVrsnF;
	private String vldToDt;
	private String srcStmId;
	private String brthEstbDate;
	private String vldToDate;
	ArrayList<CISAddressDataM>  addresses;
	
	public String getIpId() {
		return ipId;
	}
	public void setIpId(String ipId) {
		this.ipId = ipId;
	}
	public String getVldFmDt() {
		return vldFmDt;
	}
	public void setVldFmDt(String vldFmDt) {
		this.vldFmDt = vldFmDt;
	}
	public String getActStrtDt() {
		return actStrtDt;
	}
	public void setActStrtDt(String actStrtDt) {
		this.actStrtDt = actStrtDt;
	}
	public String getActEndDt() {
		return actEndDt;
	}
	public void setActEndDt(String actEndDt) {
		this.actEndDt = actEndDt;
	}
	public String getPrvnF() {
		return prvnF;
	}
	public void setPrvnF(String prvnF) {
		this.prvnF = prvnF;
	}
	public String getMrgStCd() {
		return mrgStCd;
	}
	public void setMrgStCd(String mrgStCd) {
		this.mrgStCd = mrgStCd;
	}
	public String getIpStCd() {
		return ipStCd;
	}
	public void setIpStCd(String ipStCd) {
		this.ipStCd = ipStCd;
	}
	public String getIpTpCd() {
		return ipTpCd;
	}
	public void setIpTpCd(String ipTpCd) {
		this.ipTpCd = ipTpCd;
	}
	public String getIpLglStcTpCd() {
		return ipLglStcTpCd;
	}
	public void setIpLglStcTpCd(String ipLglStcTpCd) {
		this.ipLglStcTpCd = ipLglStcTpCd;
	}
	public String getBrthEstbDt() {
		return brthEstbDt;
	}
	public void setBrthEstbDt(String brthEstbDt) {
		this.brthEstbDt = brthEstbDt;
	}
	public String getDocItmCd() {
		return docItmCd;
	}
	public void setDocItmCd(String docItmCd) {
		this.docItmCd = docItmCd;
	}
	public String getIpTaxId() {
		return ipTaxId;
	}
	public void setIpTaxId(String ipTaxId) {
		this.ipTaxId = ipTaxId;
	}
	public String getIdentNo() {
		return identNo;
	}
	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}
	public String getIssuDt() {
		return issuDt;
	}
	public void setIssuDt(String issuDt) {
		this.issuDt = issuDt;
	}
	public String getExpDt() {
		return expDt;
	}
	public void setExpDt(String expDt) {
		this.expDt = expDt;
	}
	public String getGovtIssur() {
		return govtIssur;
	}
	public void setGovtIssur(String govtIssur) {
		this.govtIssur = govtIssur;
	}
	public String getDomcBrNo() {
		return domcBrNo;
	}
	public void setDomcBrNo(String domcBrNo) {
		this.domcBrNo = domcBrNo;
	}
	public String getIpPerfStCd() {
		return ipPerfStCd;
	}
	public void setIpPerfStCd(String ipPerfStCd) {
		this.ipPerfStCd = ipPerfStCd;
	}
	public String getConsndDt() {
		return consndDt;
	}
	public void setConsndDt(String consndDt) {
		this.consndDt = consndDt;
	}
	public String getConsndF() {
		return consndF;
	}
	public void setConsndF(String consndF) {
		this.consndF = consndF;
	}
	public String getConsndSrcStmCd() {
		return consndSrcStmCd;
	}
	public void setConsndSrcStmCd(String consndSrcStmCd) {
		this.consndSrcStmCd = consndSrcStmCd;
	}
	public String getCstLke() {
		return cstLke;
	}
	public void setCstLke(String cstLke) {
		this.cstLke = cstLke;
	}
	public String getCstDslke() {
		return cstDslke;
	}
	public void setCstDslke(String cstDslke) {
		this.cstDslke = cstDslke;
	}
	public String getFavMediaCd() {
		return favMediaCd;
	}
	public void setFavMediaCd(String favMediaCd) {
		this.favMediaCd = favMediaCd;
	}
	public String getFavMediaOthrInf() {
		return favMediaOthrInf;
	}
	public void setFavMediaOthrInf(String favMediaOthrInf) {
		this.favMediaOthrInf = favMediaOthrInf;
	}
	public String getPrefCtcMthCd() {
		return prefCtcMthCd;
	}
	public void setPrefCtcMthCd(String prefCtcMthCd) {
		this.prefCtcMthCd = prefCtcMthCd;
	}
	public String getPrefCtcTm() {
		return prefCtcTm;
	}
	public void setPrefCtcTm(String prefCtcTm) {
		this.prefCtcTm = prefCtcTm;
	}
	public String getAstExcldLand() {
		return astExcldLand;
	}
	public void setAstExcldLand(String astExcldLand) {
		this.astExcldLand = astExcldLand;
	}
	public String getThTtl() {
		return thTtl;
	}
	public void setThTtl(String thTtl) {
		this.thTtl = thTtl;
	}
	public String getThFrstNm() {
		return thFrstNm;
	}
	public void setThFrstNm(String thFrstNm) {
		this.thFrstNm = thFrstNm;
	}
	public String getThMdlNm() {
		return thMdlNm;
	}
	public void setThMdlNm(String thMdlNm) {
		this.thMdlNm = thMdlNm;
	}
	public String getThSurnm() {
		return thSurnm;
	}
	public void setThSurnm(String thSurnm) {
		this.thSurnm = thSurnm;
	}
	public String getEnTtl() {
		return enTtl;
	}
	public void setEnTtl(String enTtl) {
		this.enTtl = enTtl;
	}
	public String getEnFrstNm() {
		return enFrstNm;
	}
	public void setEnFrstNm(String enFrstNm) {
		this.enFrstNm = enFrstNm;
	}
	public String getEnMdlNm() {
		return enMdlNm;
	}
	public void setEnMdlNm(String enMdlNm) {
		this.enMdlNm = enMdlNm;
	}
	public String getEnSurnm() {
		return enSurnm;
	}
	public void setEnSurnm(String enSurnm) {
		this.enSurnm = enSurnm;
	}
	public String getDeathDt() {
		return deathDt;
	}
	public void setDeathDt(String deathDt) {
		this.deathDt = deathDt;
	}
	public String getDeathF() {
		return deathF;
	}
	public void setDeathF(String deathF) {
		this.deathF = deathF;
	}
	public String getGndCd() {
		return gndCd;
	}
	public void setGndCd(String gndCd) {
		this.gndCd = gndCd;
	}
	public String getRlgCd() {
		return rlgCd;
	}
	public void setRlgCd(String rlgCd) {
		this.rlgCd = rlgCd;
	}
	public String getNatCd() {
		return natCd;
	}
	public void setNatCd(String natCd) {
		this.natCd = natCd;
	}
	public String getRaceCd() {
		return raceCd;
	}
	public void setRaceCd(String raceCd) {
		this.raceCd = raceCd;
	}
	public String getMarStCd() {
		return marStCd;
	}
	public void setMarStCd(String marStCd) {
		this.marStCd = marStCd;
	}
	public String getEdInstCd() {
		return edInstCd;
	}
	public void setEdInstCd(String edInstCd) {
		this.edInstCd = edInstCd;
	}
	public String getEdInstOthrInf() {
		return edInstOthrInf;
	}
	public void setEdInstOthrInf(String edInstOthrInf) {
		this.edInstOthrInf = edInstOthrInf;
	}
	public String getCtfTpCd() {
		return ctfTpCd;
	}
	public void setCtfTpCd(String ctfTpCd) {
		this.ctfTpCd = ctfTpCd;
	}
	public String getOcpCd() {
		return ocpCd;
	}
	public void setOcpCd(String ocpCd) {
		this.ocpCd = ocpCd;
	}
	public String getOcpDt() {
		return ocpDt;
	}
	public void setOcpDt(String ocpDt) {
		this.ocpDt = ocpDt;
	}
	public String getPosCd() {
		return posCd;
	}
	public void setPosCd(String posCd) {
		this.posCd = posCd;
	}
	public String getProfCd() {
		return profCd;
	}
	public void setProfCd(String profCd) {
		this.profCd = profCd;
	}
	public String getEmprNm() {
		return emprNm;
	}
	public void setEmprNm(String emprNm) {
		this.emprNm = emprNm;
	}
	public String getIdvIncmSegCd() {
		return idvIncmSegCd;
	}
	public void setIdvIncmSegCd(String idvIncmSegCd) {
		this.idvIncmSegCd = idvIncmSegCd;
	}
	public String getFamIncmSegCd() {
		return famIncmSegCd;
	}
	public void setFamIncmSegCd(String famIncmSegCd) {
		this.famIncmSegCd = famIncmSegCd;
	}
	public String getYngstDpndBrthDt() {
		return yngstDpndBrthDt;
	}
	public void setYngstDpndBrthDt(String yngstDpndBrthDt) {
		this.yngstDpndBrthDt = yngstDpndBrthDt;
	}
	public String getOldstDpndBrthDt() {
		return oldstDpndBrthDt;
	}
	public void setOldstDpndBrthDt(String oldstDpndBrthDt) {
		this.oldstDpndBrthDt = oldstDpndBrthDt;
	}
	public String getNoOfDpndChl() {
		return noOfDpndChl;
	}
	public void setNoOfDpndChl(String noOfDpndChl) {
		this.noOfDpndChl = noOfDpndChl;
	}
	public String getNoOfDpndChlDt() {
		return noOfDpndChlDt;
	}
	public void setNoOfDpndChlDt(String noOfDpndChlDt) {
		this.noOfDpndChlDt = noOfDpndChlDt;
	}
	public String getOrgLgltyEndDt() {
		return orgLgltyEndDt;
	}
	public void setOrgLgltyEndDt(String orgLgltyEndDt) {
		this.orgLgltyEndDt = orgLgltyEndDt;
	}
	public String getNoOfEmp() {
		return noOfEmp;
	}
	public void setNoOfEmp(String noOfEmp) {
		this.noOfEmp = noOfEmp;
	}
	public String getOrgRevSegCd() {
		return orgRevSegCd;
	}
	public void setOrgRevSegCd(String orgRevSegCd) {
		this.orgRevSegCd = orgRevSegCd;
	}
	public String getOrgRgstCptl() {
		return orgRgstCptl;
	}
	public void setOrgRgstCptl(String orgRgstCptl) {
		this.orgRgstCptl = orgRgstCptl;
	}
	public String getOrgIncmCashF() {
		return orgIncmCashF;
	}
	public void setOrgIncmCashF(String orgIncmCashF) {
		this.orgIncmCashF = orgIncmCashF;
	}
	public String getOrgLglBsnF() {
		return orgLglBsnF;
	}
	public void setOrgLglBsnF(String orgLglBsnF) {
		this.orgLglBsnF = orgLglBsnF;
	}
	public String getOrgProfF() {
		return orgProfF;
	}
	public void setOrgProfF(String orgProfF) {
		this.orgProfF = orgProfF;
	}
	public String getBsnDsc() {
		return bsnDsc;
	}
	public void setBsnDsc(String bsnDsc) {
		this.bsnDsc = bsnDsc;
	}
	public String getPrimSegCd() {
		return primSegCd;
	}
	public void setPrimSegCd(String primSegCd) {
		this.primSegCd = primSegCd;
	}
	public String getPrimSubSegCd() {
		return primSubSegCd;
	}
	public void setPrimSubSegCd(String primSubSegCd) {
		this.primSubSegCd = primSubSegCd;
	}
	public String getDualSegCd() {
		return dualSegCd;
	}
	public void setDualSegCd(String dualSegCd) {
		this.dualSegCd = dualSegCd;
	}
	public String getDualSubSegCd() {
		return dualSubSegCd;
	}
	public void setDualSubSegCd(String dualSubSegCd) {
		this.dualSubSegCd = dualSubSegCd;
	}
	public String getDualSegDt() {
		return dualSegDt;
	}
	public void setDualSegDt(String dualSegDt) {
		this.dualSegDt = dualSegDt;
	}
	public String getKbnkIdyClCd() {
		return kbnkIdyClCd;
	}
	public void setKbnkIdyClCd(String kbnkIdyClCd) {
		this.kbnkIdyClCd = kbnkIdyClCd;
	}
	public String getCoIdyClCd() {
		return coIdyClCd;
	}
	public void setCoIdyClCd(String coIdyClCd) {
		this.coIdyClCd = coIdyClCd;
	}
	public String getCstRefrNm1() {
		return cstRefrNm1;
	}
	public void setCstRefrNm1(String cstRefrNm1) {
		this.cstRefrNm1 = cstRefrNm1;
	}
	public String getCstRelTpCd1() {
		return cstRelTpCd1;
	}
	public void setCstRelTpCd1(String cstRelTpCd1) {
		this.cstRelTpCd1 = cstRelTpCd1;
	}
	public String getTelHmNo1() {
		return telHmNo1;
	}
	public void setTelHmNo1(String telHmNo1) {
		this.telHmNo1 = telHmNo1;
	}
	public String getTelHmExnNo1() {
		return telHmExnNo1;
	}
	public void setTelHmExnNo1(String telHmExnNo1) {
		this.telHmExnNo1 = telHmExnNo1;
	}
	public String getTelOffcNo1() {
		return telOffcNo1;
	}
	public void setTelOffcNo1(String telOffcNo1) {
		this.telOffcNo1 = telOffcNo1;
	}
	public String getTelOffcExnNo1() {
		return telOffcExnNo1;
	}
	public void setTelOffcExnNo1(String telOffcExnNo1) {
		this.telOffcExnNo1 = telOffcExnNo1;
	}
	public String getTelMblNo1() {
		return telMblNo1;
	}
	public void setTelMblNo1(String telMblNo1) {
		this.telMblNo1 = telMblNo1;
	}
	public String getCstRefrNm2() {
		return cstRefrNm2;
	}
	public void setCstRefrNm2(String cstRefrNm2) {
		this.cstRefrNm2 = cstRefrNm2;
	}
	public String getCstRelTpCd2() {
		return cstRelTpCd2;
	}
	public void setCstRelTpCd2(String cstRelTpCd2) {
		this.cstRelTpCd2 = cstRelTpCd2;
	}
	public String getTelHmNo2() {
		return telHmNo2;
	}
	public void setTelHmNo2(String telHmNo2) {
		this.telHmNo2 = telHmNo2;
	}
	public String getTelHmExnNo2() {
		return telHmExnNo2;
	}
	public void setTelHmExnNo2(String telHmExnNo2) {
		this.telHmExnNo2 = telHmExnNo2;
	}
	public String getTelOffcNo2() {
		return telOffcNo2;
	}
	public void setTelOffcNo2(String telOffcNo2) {
		this.telOffcNo2 = telOffcNo2;
	}
	public String getTelOffcExnNo2() {
		return telOffcExnNo2;
	}
	public void setTelOffcExnNo2(String telOffcExnNo2) {
		this.telOffcExnNo2 = telOffcExnNo2;
	}
	public String getTelMblNo2() {
		return telMblNo2;
	}
	public void setTelMblNo2(String telMblNo2) {
		this.telMblNo2 = telMblNo2;
	}
	public String getLastVrsnF() {
		return lastVrsnF;
	}
	public void setLastVrsnF(String lastVrsnF) {
		this.lastVrsnF = lastVrsnF;
	}
	public String getVldToDt() {
		return vldToDt;
	}
	public void setVldToDt(String vldToDt) {
		this.vldToDt = vldToDt;
	}
	public String getSrcStmId() {
		return srcStmId;
	}
	public void setSrcStmId(String srcStmId) {
		this.srcStmId = srcStmId;
	}
	public String getBrthEstbDate() {
		return brthEstbDate;
	}
	public void setBrthEstbDate(String brthEstbDate) {
		this.brthEstbDate = brthEstbDate;
	}
	public String getVldToDate() {
		return vldToDate;
	}
	public void setVldToDate(String vldToDate) {
		this.vldToDate = vldToDate;
	}
	public ArrayList<CISAddressDataM> getAddresses() {
		return addresses;
	}
	public void setAddresses(ArrayList<CISAddressDataM> addresses) {
		this.addresses = addresses;
	}
	
}
