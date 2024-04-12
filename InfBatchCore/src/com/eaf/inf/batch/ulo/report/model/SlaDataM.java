package com.eaf.inf.batch.ulo.report.model;

import java.io.Serializable;

public class SlaDataM implements Serializable, Cloneable{
	private String month;
	private String appIn;
	private String wipSLA;
	private String wipOLA;
	private String capacitySLA;
	private String slaInfinite;
	private String slaWisdom;
	private String slaPremier;
	private String slaPlatinum;
	private String slaGerneric;
	private String slaKEC;
	private String slaKPL;
	private String capacityOLA;
	private String olaInfinite;
	private String olaWisdom;
	private String olaPremier;
	private String olaPlatinum;
	private String olaGerneric;
	private String olaKEC;
	private String olaKPL;
	private String percentSlaTarget;
	private String period;
	private String noAppSlaInf;
	private String targetSlaInf;
	private String noAppSlaWis;
	private String targetSlaWis;
	private String noAppSlaPre;
	private String targetSlaPre;
	private String noAppSlaPlt;
	private String targetSlaPlt;
	private String noAppSlaGen;
	private String targetSlaGen;
	private String noAppSlaKEC;
	private String targetSlaKEC;
	private String noAppSlaKPL;
	private String targetSlaKPL;
	private String noAppOlaInf;
	private String targetOlaInf;
	private String noAppOlaWis;
	private String targetOlaWis;
	private String noAppOlaPre;
	private String targetOlaPre;
	private String noAppOlaPlt;
	private String targetOlaPlt;
	private String noAppOlaGen;
	private String targetOlaGen;
	private String noAppOlaKEC;
	private String targetOlaKEC;
	private String noAppOlaKPL;
	private String targetOlaKPL;
	private String wipPeriod;
	private String ccInfinite;
	private String ccWisdom;
	private String ccPremier;
	private String ccPlatinum;
	private String ccGeneric;
	private String ccGrandTotal;
	private String kec;
	private String kpl;
	
	private String slaType;
	private String e2eAcheive;
	private String e2eTarget;
	private String e2eCountApp;
	private String leg1Acheive;
	private String leg1Target;
	private String leg1CountApp;
	private String leg2Acheive;
	private String leg2Target;
	private String leg2CountApp;
	private String leg3Acheive;
	private String leg3Target;
	private String leg3CountApp;
	private String leg4Acheive;
	private String leg4Target;
	private String leg4CountApp;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getAppIn() {
		return appIn;
	}
	public void setAppIn(String appIn) {
		this.appIn = appIn;
	}
	public String getCapacitySLA() {
		return capacitySLA;
	}
	public void setCapacitySLA(String capacitySLA) {
		this.capacitySLA = capacitySLA;
	}
	public String getWipSLA() {
		return wipSLA;
	}
	public void setWipSLA(String wipSLA) {
		this.wipSLA = wipSLA;
	}
	public String getWipOLA() {
		return wipOLA;
	}
	public void setWipOLA(String wipOLA) {
		this.wipOLA = wipOLA;
	}
	public String getSlaInfinite() {
		return slaInfinite;
	}
	public void setSlaInfinite(String slaInfinite) {
		this.slaInfinite = slaInfinite;
	}
	public String getSlaWisdom() {
		return slaWisdom;
	}
	public void setSlaWisdom(String slaWisdom) {
		this.slaWisdom = slaWisdom;
	}
	public String getSlaPremier() {
		return slaPremier;
	}
	public void setSlaPremier(String slaPremier) {
		this.slaPremier = slaPremier;
	}
	public String getSlaPlatinum() {
		return slaPlatinum;
	}
	public void setSlaPlatinum(String slaPlatinum) {
		this.slaPlatinum = slaPlatinum;
	}
	public String getSlaGerneric() {
		return slaGerneric;
	}
	public void setSlaGerneric(String slaGerneric) {
		this.slaGerneric = slaGerneric;
	}
	public String getSlaKEC() {
		return slaKEC;
	}
	public void setSlaKEC(String slaKEC) {
		this.slaKEC = slaKEC;
	}
	public String getSlaKPL() {
		return slaKPL;
	}
	public void setSlaKPL(String slaKPL) {
		this.slaKPL = slaKPL;
	}
	public String getCapacityOLA() {
		return capacityOLA;
	}
	public void setCapacityOLA(String capacityOLA) {
		this.capacityOLA = capacityOLA;
	}
	public String getOlaInfinite() {
		return olaInfinite;
	}
	public void setOlaInfinite(String olaInfinite) {
		this.olaInfinite = olaInfinite;
	}
	public String getOlaWisdom() {
		return olaWisdom;
	}
	public void setOlaWisdom(String olaWisdom) {
		this.olaWisdom = olaWisdom;
	}
	public String getOlaPremier() {
		return olaPremier;
	}
	public void setOlaPremier(String olaPremier) {
		this.olaPremier = olaPremier;
	}
	public String getOlaPlatinum() {
		return olaPlatinum;
	}
	public void setOlaPlatinum(String olaPlatinum) {
		this.olaPlatinum = olaPlatinum;
	}
	public String getOlaGerneric() {
		return olaGerneric;
	}
	public void setOlaGerneric(String olaGerneric) {
		this.olaGerneric = olaGerneric;
	}
	public String getOlaKEC() {
		return olaKEC;
	}
	public void setOlaKEC(String olaKEC) {
		this.olaKEC = olaKEC;
	}
	public String getOlaKPL() {
		return olaKPL;
	}
	public void setOlaKPL(String olaKPL) {
		this.olaKPL = olaKPL;
	}
	public String getPercentSlaTarget() {
		return percentSlaTarget;
	}
	public void setPercentSlaTarget(String percentSlaTarget) {
		this.percentSlaTarget = percentSlaTarget;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getNoAppSlaInf() {
		return noAppSlaInf;
	}
	public void setNoAppSlaInf(String noAppSlaInf) {
		this.noAppSlaInf = noAppSlaInf;
	}
	public String getTargetSlaInf() {
		return targetSlaInf;
	}
	public void setTargetSlaInf(String targetSlaInf) {
		this.targetSlaInf = targetSlaInf;
	}
	public String getNoAppSlaWis() {
		return noAppSlaWis;
	}
	public void setNoAppSlaWis(String noAppSlaWis) {
		this.noAppSlaWis = noAppSlaWis;
	}
	public String getTargetSlaWis() {
		return targetSlaWis;
	}
	public void setTargetSlaWis(String targetSlaWis) {
		this.targetSlaWis = targetSlaWis;
	}
	public String getNoAppSlaPre() {
		return noAppSlaPre;
	}
	public void setNoAppSlaPre(String noAppSlaPre) {
		this.noAppSlaPre = noAppSlaPre;
	}
	public String getTargetSlaPre() {
		return targetSlaPre;
	}
	public void setTargetSlaPre(String targetSlaPre) {
		this.targetSlaPre = targetSlaPre;
	}
	public String getNoAppSlaPlt() {
		return noAppSlaPlt;
	}
	public void setNoAppSlaPlt(String noAppSlaPlt) {
		this.noAppSlaPlt = noAppSlaPlt;
	}
	public String getTargetSlaPlt() {
		return targetSlaPlt;
	}
	public void setTargetSlaPlt(String targetSlaPlt) {
		this.targetSlaPlt = targetSlaPlt;
	}
	public String getNoAppSlaGen() {
		return noAppSlaGen;
	}
	public void setNoAppSlaGen(String noAppSlaGen) {
		this.noAppSlaGen = noAppSlaGen;
	}
	public String getTargetSlaGen() {
		return targetSlaGen;
	}
	public void setTargetSlaGen(String targetSlaGen) {
		this.targetSlaGen = targetSlaGen;
	}
	public String getNoAppSlaKEC() {
		return noAppSlaKEC;
	}
	public void setNoAppSlaKEC(String noAppSlaKEC) {
		this.noAppSlaKEC = noAppSlaKEC;
	}
	public String getTargetSlaKEC() {
		return targetSlaKEC;
	}
	public void setTargetSlaKEC(String targetSlaKEC) {
		this.targetSlaKEC = targetSlaKEC;
	}
	public String getNoAppSlaKPL() {
		return noAppSlaKPL;
	}
	public void setNoAppSlaKPL(String noAppSlaKPL) {
		this.noAppSlaKPL = noAppSlaKPL;
	}
	public String getTargetSlaKPL() {
		return targetSlaKPL;
	}
	public void setTargetSlaKPL(String targetSlaKPL) {
		this.targetSlaKPL = targetSlaKPL;
	}
	public String getNoAppOlaInf() {
		return noAppOlaInf;
	}
	public void setNoAppOlaInf(String noAppOlaInf) {
		this.noAppOlaInf = noAppOlaInf;
	}
	public String getTargetOlaInf() {
		return targetOlaInf;
	}
	public void setTargetOlaInf(String targetOlaInf) {
		this.targetOlaInf = targetOlaInf;
	}
	public String getNoAppOlaWis() {
		return noAppOlaWis;
	}
	public void setNoAppOlaWis(String noAppOlaWis) {
		this.noAppOlaWis = noAppOlaWis;
	}
	public String getTargetOlaWis() {
		return targetOlaWis;
	}
	public void setTargetOlaWis(String targetOlaWis) {
		this.targetOlaWis = targetOlaWis;
	}
	public String getNoAppOlaPre() {
		return noAppOlaPre;
	}
	public void setNoAppOlaPre(String noAppOlaPre) {
		this.noAppOlaPre = noAppOlaPre;
	}
	public String getTargetOlaPre() {
		return targetOlaPre;
	}
	public void setTargetOlaPre(String targetOlaPre) {
		this.targetOlaPre = targetOlaPre;
	}
	public String getNoAppOlaPlt() {
		return noAppOlaPlt;
	}
	public void setNoAppOlaPlt(String noAppOlaPlt) {
		this.noAppOlaPlt = noAppOlaPlt;
	}
	public String getTargetOlaPlt() {
		return targetOlaPlt;
	}
	public void setTargetOlaPlt(String targetOlaPlt) {
		this.targetOlaPlt = targetOlaPlt;
	}
	public String getNoAppOlaGen() {
		return noAppOlaGen;
	}
	public void setNoAppOlaGen(String noAppOlaGen) {
		this.noAppOlaGen = noAppOlaGen;
	}
	public String getTargetOlaGen() {
		return targetOlaGen;
	}
	public void setTargetOlaGen(String targetOlaGen) {
		this.targetOlaGen = targetOlaGen;
	}
	public String getNoAppOlaKEC() {
		return noAppOlaKEC;
	}
	public void setNoAppOlaKEC(String noAppOlaKEC) {
		this.noAppOlaKEC = noAppOlaKEC;
	}
	public String getTargetOlaKEC() {
		return targetOlaKEC;
	}
	public void setTargetOlaKEC(String targetOlaKEC) {
		this.targetOlaKEC = targetOlaKEC;
	}
	public String getNoAppOlaKPL() {
		return noAppOlaKPL;
	}
	public void setNoAppOlaKPL(String noAppOlaKPL) {
		this.noAppOlaKPL = noAppOlaKPL;
	}
	public String getTargetOlaKPL() {
		return targetOlaKPL;
	}
	public void setTargetOlaKPL(String targetOlaKPL) {
		this.targetOlaKPL = targetOlaKPL;
	}
	public String getWipPeriod() {
		return wipPeriod;
	}
	public void setWipPeriod(String wipPeriod) {
		this.wipPeriod = wipPeriod;
	}
	public String getCcInfinite() {
		return ccInfinite;
	}
	public void setCcInfinite(String ccInfinite) {
		this.ccInfinite = ccInfinite;
	}
	public String getCcWisdom() {
		return ccWisdom;
	}
	public void setCcWisdom(String ccWisdom) {
		this.ccWisdom = ccWisdom;
	}
	public String getCcPremier() {
		return ccPremier;
	}
	public void setCcPremier(String ccPremier) {
		this.ccPremier = ccPremier;
	}
	
	public String getCcPlatinum() {
		return ccPlatinum;
	}
	public void setCcPlatinum(String ccPlatinum) {
		this.ccPlatinum = ccPlatinum;
	}
	public String getCcGeneric() {
		return ccGeneric;
	}
	public void setCcGeneric(String ccGeneric) {
		this.ccGeneric = ccGeneric;
	}
	public String getCcGrandTotal() {
		return ccGrandTotal;
	}
	public void setCcGrandTotal(String ccGrandTotal) {
		this.ccGrandTotal = ccGrandTotal;
	}
	public String getKec() {
		return kec;
	}
	public void setKec(String kec) {
		this.kec = kec;
	}
	public String getKpl() {
		return kpl;
	}
	public void setKpl(String kpl) {
		this.kpl = kpl;
	}
	public String getSlaType() {
		return slaType;
	}
	public void setSlaType(String slaType) {
		this.slaType = slaType;
	}
	public String getE2eAcheive() {
		return e2eAcheive;
	}
	public void setE2eAcheive(String e2eAcheive) {
		this.e2eAcheive = e2eAcheive;
	}
	public String getE2eTarget() {
		return e2eTarget;
	}
	public void setE2eTarget(String e2eTarget) {
		this.e2eTarget = e2eTarget;
	}
	public String getE2eCountApp() {
		return e2eCountApp;
	}
	public void setE2eCountApp(String e2eCountApp) {
		this.e2eCountApp = e2eCountApp;
	}
	public String getLeg1Acheive() {
		return leg1Acheive;
	}
	public void setLeg1Acheive(String leg1Acheive) {
		this.leg1Acheive = leg1Acheive;
	}
	public String getLeg1Target() {
		return leg1Target;
	}
	public void setLeg1Target(String leg1Target) {
		this.leg1Target = leg1Target;
	}
	public String getLeg1CountApp() {
		return leg1CountApp;
	}
	public void setLeg1CountApp(String leg1CountApp) {
		this.leg1CountApp = leg1CountApp;
	}
	public String getLeg2Acheive() {
		return leg2Acheive;
	}
	public void setLeg2Acheive(String leg2Acheive) {
		this.leg2Acheive = leg2Acheive;
	}
	public String getLeg2Target() {
		return leg2Target;
	}
	public void setLeg2Target(String leg2Target) {
		this.leg2Target = leg2Target;
	}
	public String getLeg2CountApp() {
		return leg2CountApp;
	}
	public void setLeg2CountApp(String leg2CountApp) {
		this.leg2CountApp = leg2CountApp;
	}
	public String getLeg3Acheive() {
		return leg3Acheive;
	}
	public void setLeg3Acheive(String leg3Acheive) {
		this.leg3Acheive = leg3Acheive;
	}
	public String getLeg3Target() {
		return leg3Target;
	}
	public void setLeg3Target(String leg3Target) {
		this.leg3Target = leg3Target;
	}
	public String getLeg3CountApp() {
		return leg3CountApp;
	}
	public void setLeg3CountApp(String leg3CountApp) {
		this.leg3CountApp = leg3CountApp;
	}
	public String getLeg4Acheive() {
		return leg4Acheive;
	}
	public void setLeg4Acheive(String leg4Acheive) {
		this.leg4Acheive = leg4Acheive;
	}
	public String getLeg4Target() {
		return leg4Target;
	}
	public void setLeg4Target(String leg4Target) {
		this.leg4Target = leg4Target;
	}
	public String getLeg4CountApp() {
		return leg4CountApp;
	}
	public void setLeg4CountApp(String leg4CountApp) {
		this.leg4CountApp = leg4CountApp;
	}
	
}
