package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class CardDataM implements Serializable,Cloneable{
	public CardDataM(){
		super();
	}
	public CardDataM(String referId,String uniqueId){
		super();
		this.loanId = referId;
		this.cardId = uniqueId;
	}
	public void init(String referId,String uniqueId){
		this.loanId = referId;
		this.cardId = uniqueId;
	}
	public class ApplicationCardType{
		public static final String BORROWER = "B";//Primary Card
		public static final String SUPPLEMENTARY = "S";
	}
	private String cardId;	//ORIG_CARD.CARD_ID(VARCHAR2)
	private String loanId;	//ORIG_CARD.LOAN_ID(VARCHAR2)
	private String personalId;	//ORIG_CARD.PERSONAL_ID(VARCHAR2)
	private String cardType;	//ORIG_CARD.CARD_TYPE(VARCHAR2)
	private String mainCardPhoneNo;	//ORIG_CARD.MAIN_CARD_PHONE_NO(VARCHAR2)
	private String campaignCode;	//ORIG_CARD.CAMPAIGN_CODE(VARCHAR2)
	private String cardNo;	//ORIG_CARD.CARD_NO(VARCHAR2)
	private String chassisNo;	//ORIG_CARD.CHASSIS_NO(VARCHAR2)
	private String membershipNo;	//ORIG_CARD.MEMBERSHIP_NO(VARCHAR2)
	private BigDecimal seq;	//ORIG_CARD.SEQ(NUMBER)
	private String mainCardNo;	//ORIG_CARD.MAIN_CARD_NO(VARCHAR2)
	private String cgaApplyChannel;	//ORIG_CARD.CGA_APPLY_CHANNEL(VARCHAR2)
	private String gangNo;	//ORIG_CARD.GANG_NO(VARCHAR2)
	private String cgaCode;	//ORIG_CARD.CGA_CODE(VARCHAR2)
	private String hashingCardNo;	//ORIG_CARD.HASHING_CARD_NO(VARCHAR2)
	private String referralCardNo;	//ORIG_CARD.REFERRAL_CARD_NO(VARCHAR2)
	private String applicationType;	//ORIG_CARD.APPLICATION_TYPE(VARCHAR2)
	private BigDecimal noAppInGang;	//ORIG_CARD.NO_APP_IN_GANG(NUMBER)
	private String percentLimitMaincard;	//ORIG_CARD.PERCENT_LIMIT_MAINCARD(VARCHAR2)
	private String cardFee;	//ORIG_CARD.CARD_FEE(VARCHAR2)
	private String mainCardHolderName;	//ORIG_CARD.MAIN_CARD_HOLDER_NAME(VARCHAR2)
	private String createBy;	//ORIG_CARD.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_CARD.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_CARD.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_CARD.UPDATE_DATE(DATE)	
	private String plasticCode; //ORIG_CARD.CARD_FACE(VARCHAR2)
	private String cardLevel; //ORIG_CARD.CARD_LEVEL(VARCHAR2)
	private String cardLinkCardCode; //ORIG_CARD.CARDLINK_CARD_CODE(VARCHAR2)
	private String requestCardType; //ORIG_CARD.REQUEST_CARD_TYPE(VARCHAR2)
	private String cardNoMark; //ORIG_CARD.CARD_NO_MARK(VARCHAR2)
	private String flpCardType; //ORIG_CARD.FLP_CARD_TYPE(VARCHAR2)
	private String recommendCardCode; //ORIG_CARD.RECOMMEND_CARD_CODE(VARCHAR2)
	private String cardlinkCustId; //ORIG_CARD.CARDLINK_CUST_ID(VARCHAR2)
	private String cardNoEncrypted; //ORIG_CARD.CARD_NO_ENCRYPTED2(VARCHAR2)
	private String maxEligibleCardType;//ORIG_CARD.MAX_ELIGIBLE_CARD_TYPE (VARCHAR2)
	private String maxEligibleCardLevel;//ORIG_CARD.MAX_ELIGIBLE_CARD_LEVEL	(VARCHAR2)	
	private String completeFlag;//ORIG_CARD.COMPLETE_FLAG (VARCHAR2)
	private WisdomDataM wisdom;
		
	
	public String getCompleteFlag() {
		return completeFlag;
	}
	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}
	public String getPlasticCode() {
		return plasticCode;
	}
	public void setPlasticCode(String plasticCode) {
		this.plasticCode = plasticCode;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getMainCardPhoneNo() {
		return mainCardPhoneNo;
	}
	public void setMainCardPhoneNo(String mainCardPhoneNo) {
		this.mainCardPhoneNo = mainCardPhoneNo;
	}
	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getChassisNo() {
		return chassisNo;
	}
	public void setChassisNo(String chassisNo) {
		this.chassisNo = chassisNo;
	}
	public String getMembershipNo() {
		return membershipNo;
	}
	public void setMembershipNo(String membershipNo) {
		this.membershipNo = membershipNo;
	}
	public BigDecimal getSeq() {
		return seq;
	}
	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}
	public String getMainCardNo() {
		return mainCardNo;
	}
	public void setMainCardNo(String mainCardNo) {
		this.mainCardNo = mainCardNo;
	}
	public String getCgaApplyChannel() {
		return cgaApplyChannel;
	}
	public void setCgaApplyChannel(String cgaApplyChannel) {
		this.cgaApplyChannel = cgaApplyChannel;
	}
	public String getGangNo() {
		return gangNo;
	}
	public void setGangNo(String gangNo) {
		this.gangNo = gangNo;
	}
	public String getCgaCode() {
		return cgaCode;
	}
	public void setCgaCode(String cgaCode) {
		this.cgaCode = cgaCode;
	}
	public String getHashingCardNo() {
		return hashingCardNo;
	}
	public void setHashingCardNo(String hashingCardNo) {
		this.hashingCardNo = hashingCardNo;
	}
	public String getReferralCardNo() {
		return referralCardNo;
	}
	public void setReferralCardNo(String referralCardNo) {
		this.referralCardNo = referralCardNo;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public BigDecimal getNoAppInGang() {
		return noAppInGang;
	}
	public void setNoAppInGang(BigDecimal noAppInGang) {
		this.noAppInGang = noAppInGang;
	}
	public String getPercentLimitMaincard() {
		return percentLimitMaincard;
	}
	public void setPercentLimitMaincard(String percentLimitMaincard) {
		this.percentLimitMaincard = percentLimitMaincard;
	}
	public String getCardFee() {
		return cardFee;
	}
	public void setCardFee(String cardFee) {
		this.cardFee = cardFee;
	}
	public String getMainCardHolderName() {
		return mainCardHolderName;
	}
	public void setMainCardHolderName(String mainCardHolderName) {
		this.mainCardHolderName = mainCardHolderName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}	
	public String getCardLinkCardCode() {
		return cardLinkCardCode;
	}
	public void setCardLinkCardCode(String cardLinkCardCode) {
		this.cardLinkCardCode = cardLinkCardCode;
	}
	public String getRequestCardType() {
		return requestCardType;
	}
	public void setRequestCardType(String requestCardType) {
		this.requestCardType = requestCardType;
	}
	public String getCardNoMark() {
		return cardNoMark;
	}
	public void setCardNoMark(String cardNoMark) {
		this.cardNoMark = cardNoMark;
	}
	public WisdomDataM getWisdom() {
		return wisdom;
	}
	public void setWisdom(WisdomDataM wisdom) {
		this.wisdom = wisdom;
	}
	public String getFlpCardType() {
		return flpCardType;
	}
	public void setFlpCardType(String flpCardType) {
		this.flpCardType = flpCardType;
	}
	public String getRecommendCardCode() {
		return recommendCardCode;
	}
	public void setRecommendCardCode(String recommendCardCode) {
		this.recommendCardCode = recommendCardCode;
	}	
	public String getCardLevel() {
		return cardLevel;
	}
	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}
	public String getCardlinkCustId() {
		return cardlinkCustId;
	}
	public void setCardlinkCustId(String cardlinkCustId) {
		this.cardlinkCustId = cardlinkCustId;
	}
	public String getCardNoEncrypted() {
		return cardNoEncrypted;
	}
	public void setCardNoEncrypted(String cardNoEncrypted) {
		this.cardNoEncrypted = cardNoEncrypted;
	}
	public String getMaxEligibleCardType() {
		return maxEligibleCardType;
	}
	public void setMaxEligibleCardType(String maxEligibleCardType) {
		this.maxEligibleCardType = maxEligibleCardType;
	}
	public String getMaxEligibleCardLevel() {
		return maxEligibleCardLevel;
	}
	public void setMaxEligibleCardLevel(String maxEligibleCardLevel) {
		this.maxEligibleCardLevel = maxEligibleCardLevel;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardDataM [cardId=");
		builder.append(cardId);
		builder.append(", loanId=");
		builder.append(loanId);
		builder.append(", personalId=");
		builder.append(personalId);
		builder.append(", cardType=");
		builder.append(cardType);
		builder.append(", mainCardPhoneNo=");
		builder.append(mainCardPhoneNo);
		builder.append(", campaignCode=");
		builder.append(campaignCode);
		builder.append(", cardNo=");
		builder.append(cardNo);
		builder.append(", chassisNo=");
		builder.append(chassisNo);
		builder.append(", membershipNo=");
		builder.append(membershipNo);
		builder.append(", seq=");
		builder.append(seq);
		builder.append(", mainCardNo=");
		builder.append(mainCardNo);
		builder.append(", cgaApplyChannel=");
		builder.append(cgaApplyChannel);
		builder.append(", gangNo=");
		builder.append(gangNo);
		builder.append(", cgaCode=");
		builder.append(cgaCode);
		builder.append(", hashingCardNo=");
		builder.append(hashingCardNo);
		builder.append(", referralCardNo=");
		builder.append(referralCardNo);
		builder.append(", applicationType=");
		builder.append(applicationType);
		builder.append(", noAppInGang=");
		builder.append(noAppInGang);
		builder.append(", percentLimitMaincard=");
		builder.append(percentLimitMaincard);
		builder.append(", cardFee=");
		builder.append(cardFee);
		builder.append(", mainCardHolderName=");
		builder.append(mainCardHolderName);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", plasticCode=");
		builder.append(plasticCode);
		builder.append(", cardLevel=");
		builder.append(cardLevel);
		builder.append(", cardLinkCardCode=");
		builder.append(cardLinkCardCode);
		builder.append(", requestCardType=");
		builder.append(requestCardType);
		builder.append(", cardNoMark=");
		builder.append(cardNoMark);
		builder.append(", flpCardType=");
		builder.append(flpCardType);
		builder.append(", recommendCardCode=");
		builder.append(recommendCardCode);
		builder.append(", cardlinkCustId=");
		builder.append(cardlinkCustId);
		builder.append(", cardNoEncrypted=");
		builder.append(cardNoEncrypted);
		builder.append(", maxEligibleCardType=");
		builder.append(maxEligibleCardType);
		builder.append(", maxEligibleCardLevel=");
		builder.append(maxEligibleCardLevel);
		builder.append(", wisdom=");
		builder.append(wisdom);
		builder.append(", completeFlag=");
		builder.append(completeFlag);
		builder.append("]");
		return builder.toString();
	}
	
}
