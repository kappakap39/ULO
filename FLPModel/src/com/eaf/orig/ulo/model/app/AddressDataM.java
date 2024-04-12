package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class AddressDataM implements Serializable,Cloneable{
	public AddressDataM(){
		super();
	}
//	@Deprecated
//	public class ADDRESS_TYPE{
//		public static final String CURRENT = "01";
//		public static final String WORK = "02";
//		public static final String DOCUMENT = "03";
//		public static final String NATION = "04";
//		public static final String VAT = "05";
//		public static final String MAIN = "09";
//		public static final String CURRENT_CARDLINK = "06";
//		public static final String WORK_CARDLINK = "07";
//		public static final String DOCUMENT_CARDLINK = "08";
//	}
	private String addressId;	//ORIG_PERSONAL_ADDRESS.ADDRESS_ID(VARCHAR2)
	private String personalId;	//ORIG_PERSONAL_ADDRESS.PERSONAL_ID(VARCHAR2)
	private String building;	//ORIG_PERSONAL_ADDRESS.BUILDING(VARCHAR2)
	private String soi;	//ORIG_PERSONAL_ADDRESS.SOI(VARCHAR2)
	private String mobile;	//ORIG_PERSONAL_ADDRESS.MOBILE(VARCHAR2)
	private String address;	//ORIG_PERSONAL_ADDRESS.ADDRESS(VARCHAR2)
	private String department;	//ORIG_PERSONAL_ADDRESS.DEPARTMENT(VARCHAR2)
	private BigDecimal residem;	//ORIG_PERSONAL_ADDRESS.RESIDEM(NUMBER)
	private String country;	//ORIG_PERSONAL_ADDRESS.COUNTRY(VARCHAR2)
	private String phone2;	//ORIG_PERSONAL_ADDRESS.PHONE2(VARCHAR2)
	private int seq;	//ORIG_PERSONAL_ADDRESS.SEQ(NUMBER)
	private String adrsts;	//ORIG_PERSONAL_ADDRESS.ADRSTS(VARCHAR2)
	private String tambol;	//ORIG_PERSONAL_ADDRESS.TAMBOL(VARCHAR2)
	private BigDecimal province;	//ORIG_PERSONAL_ADDRESS.PROVINCE(NUMBER)
	private String floor;	//ORIG_PERSONAL_ADDRESS.FLOOR(VARCHAR2)
	private String road;	//ORIG_PERSONAL_ADDRESS.ROAD(VARCHAR2)
	private String companyTitle;	//ORIG_PERSONAL_ADDRESS.COMPANY_TITLE(VARCHAR2)
	private String fax;	//ORIG_PERSONAL_ADDRESS.FAX(VARCHAR2)
	private String moo;	//ORIG_PERSONAL_ADDRESS.MOO(VARCHAR2)
	private BigDecimal rents;	//ORIG_PERSONAL_ADDRESS.RENTS(NUMBER)
	private String companyName;	//ORIG_PERSONAL_ADDRESS.COMPANY_NAME(VARCHAR2)
	private String zipcode;	//ORIG_PERSONAL_ADDRESS.ZIPCODE(VARCHAR2)
	private String phone1;	//ORIG_PERSONAL_ADDRESS.PHONE1(VARCHAR2)
	private String branchName;	//ORIG_PERSONAL_ADDRESS.BRANCH_NAME(VARCHAR2)
	private String addressType;	//ORIG_PERSONAL_ADDRESS.ADDRESS_TYPE(VARCHAR2)
	private String ext1;	//ORIG_PERSONAL_ADDRESS.EXT1(VARCHAR2)
	private String address1;	//ORIG_PERSONAL_ADDRESS.ADDRESS1(VARCHAR2)
	private String address2;	//ORIG_PERSONAL_ADDRESS.ADDRESS2(VARCHAR2)
	private String address3;	//ORIG_PERSONAL_ADDRESS.ADDRESS3(VARCHAR2)
	private String state;	//ORIG_PERSONAL_ADDRESS.STATE(VARCHAR2)
	private String buildingType;	//ORIG_PERSONAL_ADDRESS.BUILDING_TYPE(VARCHAR2)
	private String provinceDesc;	//ORIG_PERSONAL_ADDRESS.PROVINCE_DESC(VARCHAR2)
	private String room;	//ORIG_PERSONAL_ADDRESS.ROOM(VARCHAR2)
	private String branchType;	//ORIG_PERSONAL_ADDRESS.BRANCH_TYPE(VARCHAR2)
	private BigDecimal residey;	//ORIG_PERSONAL_ADDRESS.RESIDEY(NUMBER)
	private String vatRegistration;	//ORIG_PERSONAL_ADDRESS.VAT_REGISTRATION(VARCHAR2)
	private String amphur;	//ORIG_PERSONAL_ADDRESS.AMPHUR(VARCHAR2)
	private String ext2;	//ORIG_PERSONAL_ADDRESS.EXT2(VARCHAR2)
	private String vatCode;	//ORIG_PERSONAL_ADDRESS.VAT_CODE(VARCHAR2)
	private String createBy;	//ORIG_PERSONAL_ADDRESS.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_PERSONAL_ADDRESS.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_PERSONAL_ADDRESS.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_PERSONAL_ADDRESS.UPDATE_DATE(DATE)
	private String vilapt;	//ORIG_PERSONAL_ADDRESS.VILAPT(VARCHAR2)
	private String addressFormat;	//ORIG_PERSONAL_ADDRESS.ADDRESS_FORMAT(VARCHAR2)
	private String addressIdRef;//ADDRESS_ID_REF
	private String phoneIdRef;//PHONE_ID_REF
	private String residenceProvince; //ORIG_PERSONAL_ADDRESS.RESIDENCE_PROVINCE(VARCHAR2)
	
	private String editFlag;
	
	public String getVilapt() {
		return vilapt;
	}
	public void setVilapt(String vilapt) {
		this.vilapt = vilapt;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getSoi() {
		return soi;
	}
	public void setSoi(String soi) {
		this.soi = soi;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public BigDecimal getResidem() {
		return residem;
	}
	public void setResidem(BigDecimal residem) {
		this.residem = residem;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getAdrsts() {
		return adrsts;
	}
	public void setAdrsts(String adrsts) {
		this.adrsts = adrsts;
	}
	public String getTambol() {
		return tambol;
	}
	public void setTambol(String tambol) {
		this.tambol = tambol;
	}
	public BigDecimal getProvince() {
		return province;
	}
	public void setProvince(BigDecimal province) {
		this.province = province;
	}
	public String getProvinceDesc() {
		return provinceDesc;
	}
	public void setProvinceDesc(String provinceDesc) {
		this.provinceDesc = provinceDesc;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCompanyTitle() {
		return companyTitle;
	}
	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getMoo() {
		return moo;
	}
	public void setMoo(String moo) {
		this.moo = moo;
	}
	public BigDecimal getRents() {
		return rents;
	}
	public void setRents(BigDecimal rents) {
		this.rents = rents;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getVatCode() {
		return vatCode;
	}
	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public BigDecimal getResidey() {
		return residey;
	}
	public void setResidey(BigDecimal residey) {
		this.residey = residey;
	}
	public String getAmphur() {
		return amphur;
	}
	public void setAmphur(String amphur) {
		this.amphur = amphur;
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
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBuildingType() {
		return buildingType;
	}
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}
	public String getBranchType() {
		return branchType;
	}
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	public String getVatRegistration() {
		return vatRegistration;
	}
	public void setVatRegistration(String vatRegistration) {
		this.vatRegistration = vatRegistration;
	}
	public String getAddressFormat() {
		return addressFormat;
	}
	public void setAddressFormat(String addressFormat) {
		this.addressFormat = addressFormat;
	}
	public String getAddressIdRef() {
		return addressIdRef;
	}
	public void setAddressIdRef(String addressIdRef) {
		this.addressIdRef = addressIdRef;
	}
	public String getPhoneIdRef() {
		return phoneIdRef;
	}
	public void setPhoneIdRef(String phoneIdRef) {
		this.phoneIdRef = phoneIdRef;
	}
	public String getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}
	public String getResidenceProvince() {
		return residenceProvince;
	}
	public void setResidenceProvince(String residenceProvince) {
		this.residenceProvince = residenceProvince;
	}
	@Override
	public String toString() {
		return "AddressDataM [addressId=" + addressId + ", personalId="
				+ personalId + ", building=" + building + ", soi=" + soi
				+ ", mobile=" + mobile + ", address=" + address
				+ ", department=" + department + ", residem=" + residem
				+ ", country=" + country + ", phone2=" + phone2 + ", seq="
				+ seq + ", adrsts=" + adrsts + ", tambol=" + tambol
				+ ", province=" + province + ", floor=" + floor + ", road="
				+ road + ", companyTitle=" + companyTitle + ", fax=" + fax
				+ ", moo=" + moo + ", rents=" + rents + ", companyName="
				+ companyName + ", zipcode=" + zipcode + ", phone1=" + phone1
				+ ", branchName=" + branchName + ", addressType=" + addressType
				+ ", ext1=" + ext1 + ", address1=" + address1 + ", address2="
				+ address2 + ", address3=" + address3 + ", state=" + state
				+ ", buildingType=" + buildingType + ", provinceDesc="
				+ provinceDesc + ", room=" + room + ", branchType="
				+ branchType + ", residey=" + residey + ", vatRegistration="
				+ vatRegistration + ", amphur=" + amphur + ", ext2=" + ext2
				+ ", vatCode=" + vatCode + ", createBy=" + createBy
				+ ", createDate=" + createDate + ", updateBy=" + updateBy
				+ ", updateDate=" + updateDate + ", vilapt=" + vilapt
				+ ", addressFormat=" + addressFormat + ", addressIdRef="
				+ addressIdRef + ", phoneIdRef=" + phoneIdRef + ", editFlag="
				+ editFlag + "]";
	}	
	
	
	
}
