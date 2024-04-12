/**
 * CISIndivCust_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1044u01;

public class CISIndivCust_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public CISIndivCust_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new CISIndivCust();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_2_33) {
          ((CISIndivCust)value).setContactAddrObj((com.kasikornbank.eai.cis1044u01.ContactAddrObj)objValue);
          return true;}
        else if (qName==QName_2_34) {
          ((CISIndivCust)value).setContactsObj((com.kasikornbank.eai.cis1044u01.ContactsObj)objValue);
          return true;}
        else if (qName==QName_2_53) {
          ((CISIndivCust)value).setKYCObj((com.kasikornbank.eai.cis1044u01.KYCObj)objValue);
          return true;}
        else if (qName==QName_2_62) {
          ((CISIndivCust)value).setObsContactAddrObj((com.kasikornbank.eai.cis1044u01.ObsContactAddrObj)objValue);
          return true;}
        else if (qName==QName_2_63) {
          ((CISIndivCust)value).setObsOffclAddrObj((com.kasikornbank.eai.cis1044u01.ObsOffclAddrObj)objValue);
          return true;}
        else if (qName==QName_2_65) {
          ((CISIndivCust)value).setOffclAddrObj((com.kasikornbank.eai.cis1044u01.OffclAddrObj)objValue);
          return true;}
        else if (qName==QName_2_171) {
          ((CISIndivCust)value).setProfileObj((com.kasikornbank.eai.cis1044u01.ProfileObj)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_2_31 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "cnstSrcCode");
    private final static javax.xml.namespace.QName QName_2_52 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "incomRang");
    private final static javax.xml.namespace.QName QName_2_27 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "asstExclLndAmt");
    private final static javax.xml.namespace.QName QName_2_34 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "contactsObj");
    private final static javax.xml.namespace.QName QName_2_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "childCnt");
    private final static javax.xml.namespace.QName QName_2_80 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "thMidName");
    private final static javax.xml.namespace.QName QName_2_64 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "occCode");
    private final static javax.xml.namespace.QName QName_2_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "cnstFlg");
    private final static javax.xml.namespace.QName QName_2_72 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "religionCode");
    private final static javax.xml.namespace.QName QName_2_36 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "custTypeCode");
    private final static javax.xml.namespace.QName QName_2_35 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "custSgmtCode");
    private final static javax.xml.namespace.QName QName_2_37 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "depositCode");
    private final static javax.xml.namespace.QName QName_2_73 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "reverseFlag");
    private final static javax.xml.namespace.QName QName_2_43 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "engLstName");
    private final static javax.xml.namespace.QName QName_2_50 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "idCrdIssuDate");
    private final static javax.xml.namespace.QName QName_2_76 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "taxNum");
    private final static javax.xml.namespace.QName QName_2_54 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "marrStatCode");
    private final static javax.xml.namespace.QName QName_2_61 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_2_42 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "engFstName");
    private final static javax.xml.namespace.QName QName_2_32 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "cntctChnlMltCde");
    private final static javax.xml.namespace.QName QName_2_77 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "TFBBusTypeCode");
    private final static javax.xml.namespace.QName QName_2_71 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "raceCode");
    private final static javax.xml.namespace.QName QName_2_82 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "titleNameCode");
    private final static javax.xml.namespace.QName QName_2_55 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "menuAddCode");
    private final static javax.xml.namespace.QName QName_2_59 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "nmChgResnCode");
    private final static javax.xml.namespace.QName QName_2_84 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "validationFlag");
    private final static javax.xml.namespace.QName QName_2_70 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "prospectFlag");
    private final static javax.xml.namespace.QName QName_2_60 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "nmChgSeqNum");
    private final static javax.xml.namespace.QName QName_2_26 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "addrOffCntctSameCde");
    private final static javax.xml.namespace.QName QName_2_81 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "thTitle");
    private final static javax.xml.namespace.QName QName_2_49 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "idCrdExpDate");
    private final static javax.xml.namespace.QName QName_2_40 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "eduLevelCode");
    private final static javax.xml.namespace.QName QName_2_63 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "obsOffclAddrObj");
    private final static javax.xml.namespace.QName QName_2_58 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "nmChgDate");
    private final static javax.xml.namespace.QName QName_2_62 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "obsContactAddrObj");
    private final static javax.xml.namespace.QName QName_2_33 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "contactAddrObj");
    private final static javax.xml.namespace.QName QName_2_85 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "VIPCode");
    private final static javax.xml.namespace.QName QName_2_41 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "empCnt");
    private final static javax.xml.namespace.QName QName_2_57 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "nmChgBookNum");
    private final static javax.xml.namespace.QName QName_2_66 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "oldChildAgeCnt");
    private final static javax.xml.namespace.QName QName_2_65 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "offclAddrObj");
    private final static javax.xml.namespace.QName QName_2_87 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "youngChildAgeCnt");
    private final static javax.xml.namespace.QName QName_2_39 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "docTypeCode");
    private final static javax.xml.namespace.QName QName_2_75 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "svcBrchCode");
    private final static javax.xml.namespace.QName QName_2_28 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "brthDate");
    private final static javax.xml.namespace.QName QName_2_74 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "sexCode");
    private final static javax.xml.namespace.QName QName_2_38 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "docNum");
    private final static javax.xml.namespace.QName QName_2_53 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "KYCObj");
    private final static javax.xml.namespace.QName QName_2_44 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "engMidName");
    private final static javax.xml.namespace.QName QName_2_68 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "positionCode");
    private final static javax.xml.namespace.QName QName_2_47 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "frontReviewFlag");
    private final static javax.xml.namespace.QName QName_2_171 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "profileObj");
    private final static javax.xml.namespace.QName QName_2_51 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "idCrdIssuPlaceDesc");
    private final static javax.xml.namespace.QName QName_2_86 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "workStartDate");
    private final static javax.xml.namespace.QName QName_2_46 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "familyIncCode");
    private final static javax.xml.namespace.QName QName_2_45 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "engTitle");
    private final static javax.xml.namespace.QName QName_2_48 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "idCrdDesc");
    private final static javax.xml.namespace.QName QName_2_78 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "thFstName");
    private final static javax.xml.namespace.QName QName_2_79 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "thLstName");
    private final static javax.xml.namespace.QName QName_2_69 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "professCode");
    private final static javax.xml.namespace.QName QName_2_67 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "othProfessDesc");
    private final static javax.xml.namespace.QName QName_2_56 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "nationCode");
    private final static javax.xml.namespace.QName QName_2_83 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "typeCode");
}
