package com.eaf.orig.shared.ejb;

import java.math.BigDecimal;
import java.util.Vector;

import javax.ejb.EJBException;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ChangeNameDataM;
import com.eaf.orig.shared.model.OtherNameDataM;
import com.eaf.orig.shared.xrules.dao.ExtBlacklistDAO;
import com.eaf.orig.shared.xrules.dao.ExtExistingCustomerDAO;
import com.eaf.orig.shared.xrules.dao.exception.ExtExistingCustomerException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesBlacklistDataM;
import com.eaf.xrules.shared.model.XRulesDataM;
import com.eaf.xrules.shared.model.XRulesExistcustDataM;
import com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM;

/**
 * Bean implementation class for Enterprise Bean: XRulesEXTManager
 */
public class XRulesEXTManagerBean implements javax.ejb.SessionBean {
    private javax.ejb.SessionContext mySessionCtx;

    private static Logger log = Logger.getLogger(XRulesEXTManagerBean.class);

    /**
     * getSessionContext
     */
    public javax.ejb.SessionContext getSessionContext() {
        return mySessionCtx;
    }

    /**
     * setSessionContext
     */
    public void setSessionContext(javax.ejb.SessionContext ctx) {
        mySessionCtx = ctx;
    }

    /**
     * ejbCreate
     */
    public void ejbCreate() throws javax.ejb.CreateException {
    }

    /**
     * ejbActivate
     */
    public void ejbActivate() {
    }

    /**
     * ejbPassivate
     */
    public void ejbPassivate() {
    }

    /**
     * ejbRemove
     */
    public void ejbRemove() {
    }

    public Vector getBlacklist(Object xrulesObject) {
        try {
            XRulesDataM xrulesDataM = (XRulesDataM) xrulesObject;
            //Vector result = OrigExintDAOFactory.getExtBlacklistDAO()
            //
            //		.getBlacklist(xrulesDataM);
            //call Dao from Ejb
            Vector result = new Vector();
            String cmpCde = xrulesDataM.getCmpCde();
            String mainAppIdNo = xrulesDataM.getIdNo();
            String mainAppfirstName = xrulesDataM.getThaiFname();
            String mainappLastName = xrulesDataM.getThaiLname();
            String mainappHouseId = xrulesDataM.getHouseId();
            String mainappCustomerType=xrulesDataM.getCustomerType();
            log.debug("ExtBlacklistDAOImpl-->get Blacklist ");
            log.debug("cmpcde= " + cmpCde);
            log.debug("idNo " + mainAppIdNo);
            log.debug("firstName " + mainAppfirstName);
            log.debug("lastName " + mainappLastName);
            log.debug("houseId " + mainappHouseId);
            log.debug("customerType " + mainappCustomerType);
            ExtBlacklistDAO extBlackListDao = ORIGDAOFactory.getExtBlacklistDAO();
            Vector resultMainApp = extBlackListDao.getBlacklist(cmpCde, mainAppIdNo,
                    mainAppfirstName, mainappLastName,mainappHouseId,mainappCustomerType);
            result.addAll(resultMainApp);
            Vector vChangeName = xrulesDataM.getChangeNameVect();
            if (vChangeName != null && vChangeName.size() > 0) {
                log.debug("blacklist Change Name");
                for (int i = 0; i < vChangeName.size(); i++) {
                    if (result.size() >= XRulesConstant.limitALLResult) {
                        break;
                    }
                    ChangeNameDataM changeName = (ChangeNameDataM) vChangeName
                            .get(i);
                    //log.debug("Change Name idNo " + changeName.getIdNo());
                    log.debug("Change Name firstName "
                            + changeName.getOldName());
                    log.debug("Change Name lastName "
                            + changeName.getOldSurName());
                    Vector vChangeNameBlacklistResult = extBlackListDao
                            .getBlacklist(cmpCde, mainAppIdNo,
                                    changeName.getOldName(), changeName
                                            .getOldSurName(),mainappHouseId,mainappCustomerType);                     
                    if (vChangeNameBlacklistResult != null) {
                        result.addAll(vChangeNameBlacklistResult);
                    }
                }
            }
            Vector vOtherName = xrulesDataM.getOtherNameVect();
            if (vOtherName != null && vOtherName.size() > 0) {
                log.debug("blacklist Other");
                for (int i = 0; i < vOtherName.size(); i++) {
                    if (result.size() >= XRulesConstant.limitALLResult) {
                        break;
                    }
                    OtherNameDataM otherName = (OtherNameDataM) vOtherName
                            .get(i);
                    log.debug("Other Name idNo " + otherName.getCitizenId());
                    log.debug("Other Name firstName " + otherName.getName());
                    log.debug("Other Name lastName " + otherName.getLastName());
                    Vector vOtherNameBlacklistResult = extBlackListDao
                            .getBlacklist(cmpCde, otherName.getCitizenId(),
                                    otherName.getName(), otherName
                                            .getLastName(),"",mainappCustomerType);
                    if (vOtherNameBlacklistResult != null) {
                        result.addAll(vOtherNameBlacklistResult);
                    }
                }

            }
            Vector vBlacklistResult=new Vector();
            for(int i=0;i<result.size();i++){
                XRulesBlacklistDataM prmxRulesBlacklistDataM=(XRulesBlacklistDataM)result.get(i);
                prmxRulesBlacklistDataM.setSeq(i);
                if(i>XRulesConstant.limitALLResult){
                     break;
                }
                vBlacklistResult.add(prmxRulesBlacklistDataM);                    
            }
            /*if (result.size() > XRulesConstant.limitALLResult) {
                log.debug("Result over Limit " + result.size());
                while (result.size() > XRulesConstant.limitALLResult) {
                    result.remove((result.size() - 1));
                }
            }*/
            
            return result;
        } catch (Exception e) {
             log.error("Error:", e);
            throw new EJBException(e.getLocalizedMessage());
        }
    }

    public Vector getExitingCustomerInprogress(Object xrulesObject) {
        try {
            XRulesDataM xrulesDataM = (XRulesDataM) xrulesObject;
            Vector result =new Vector(); 
            ExtExistingCustomerDAO extExistingCustomerInprogressDAO = ORIGDAOFactory.getExtExistingCustomerDAO();
            Vector resultExistingInprogressOrig = extExistingCustomerInprogressDAO.getExisitingCustomerInprogressOrig(xrulesDataM);
            if(resultExistingInprogressOrig!=null&&resultExistingInprogressOrig.size()>0){
               for(int i=0;i<resultExistingInprogressOrig.size();i++){
                   XRulesExistcustInprogressDataM  prmXRulesExistcustInprogressDataM  =(XRulesExistcustInprogressDataM)resultExistingInprogressOrig.get(i);
                  if(OrigConstant.INSTALLMENT_TYPE_STEP.equals(prmXRulesExistcustInprogressDataM.getInstallmentFlag())){
                      prmXRulesExistcustInprogressDataM.setInstallment(extExistingCustomerInprogressDAO.getInstallmentApplicationInprogressOrig(prmXRulesExistcustInprogressDataM.getApplicationRecordId()));
                  }
                  result.add(prmXRulesExistcustInprogressDataM);
               }                               
               // result.addAll(resultExistingInprogressOrig);  
            }
            /*Vector resultExistingInprogressEXTHire=extExistingCustomerInprogressDAO.getExisitingCustomerInprogressEXTHire(xrulesDataM);
            if(resultExistingInprogressEXTHire!=null&&resultExistingInprogressEXTHire.size()>0){
                for(int i=0;i<resultExistingInprogressEXTHire.size();i++){
                    XRulesExistcustInprogressDataM  prmXRulesExistcustInprogressDataM  =(XRulesExistcustInprogressDataM)resultExistingInprogressEXTHire.get(i);                   
                   prmXRulesExistcustInprogressDataM.setInstallment(extExistingCustomerInprogressDAO.getInstallmentApplicationInprogress(prmXRulesExistcustInprogressDataM.getApplicationNo()));                   
                   result.add(prmXRulesExistcustInprogressDataM);
                }     
              //result.addAll(resultExistingInprogressEXTHire);  
            }
            Vector resultExistingInprogressEXTGuarantor=extExistingCustomerInprogressDAO.getExisitingCustomerInprogressEXTGuarantor(xrulesDataM);
            if(resultExistingInprogressEXTGuarantor!=null&&resultExistingInprogressEXTGuarantor.size()>0){
              //result.addAll(resultExistingInprogressEXTGuarantor);  
              for(int i=0;i<resultExistingInprogressEXTGuarantor.size();i++){
                  XRulesExistcustInprogressDataM  prmXRulesExistcustInprogressDataM  =(XRulesExistcustInprogressDataM)resultExistingInprogressEXTGuarantor.get(i);                  
                 prmXRulesExistcustInprogressDataM.setInstallment(extExistingCustomerInprogressDAO.getInstallmentApplicationInprogressOrig(prmXRulesExistcustInprogressDataM.getApplicationNo()));                 
                 result.add(prmXRulesExistcustInprogressDataM);
              }    
            }*/
            return result;
        } catch (Exception e) {
            log.error("Error:", e);
            throw new EJBException(e.getLocalizedMessage());
        }
    }
    public Vector getExitingCustomerBooked(Object xrulesObject) {
        /*try {
            XRulesDataM xrulesDataM = (XRulesDataM) xrulesObject;
            Vector result =new Vector();
            ExtExistingCustomerDAO extExistingCustomerBookedDAO=OrigExintDAOFactory.getExtExistingCustomerDAO();
            Vector resultExistingBookHire =extExistingCustomerBookedDAO.getExisitingCustomerBookedHire(xrulesDataM);
            if(resultExistingBookHire!=null&&resultExistingBookHire.size()>0){
                for(int i=0;i<resultExistingBookHire.size();i++){
                    XRulesExistcustDataM  prmXRulesExistcustDataM  =(XRulesExistcustDataM)resultExistingBookHire.get(i);                   
                    prmXRulesExistcustDataM.setInstallment(extExistingCustomerBookedDAO.getInstallmentApplicationBooked(prmXRulesExistcustDataM.getContractNo()));                   
                    result.add(prmXRulesExistcustDataM);
                }     
                
                //result.addAll(resultExistingBookHire);    
            }
            Vector resultExistingBookGuarantor =extExistingCustomerBookedDAO.getExisitingCustomerBookedGuarantor(xrulesDataM);
            if(resultExistingBookGuarantor!=null&&resultExistingBookGuarantor.size()>0){
           
                for(int i=0;i<resultExistingBookGuarantor.size();i++){
                    XRulesExistcustDataM  prmXRulesExistcustDataM  =(XRulesExistcustDataM)resultExistingBookGuarantor.get(i);                   
                    prmXRulesExistcustDataM.setInstallment(extExistingCustomerBookedDAO.getInstallmentApplicationBooked(prmXRulesExistcustDataM.getContractNo()));                   
                    result.add(prmXRulesExistcustDataM);
                }   
                //result.addAll(resultExistingBookGuarantor);    
            }             
            return result;
        } catch (Exception e) {
            log.error("Error:", e);
            throw new EJBException(e.getLocalizedMessage());
        }*/
    	return null;
    }
    public BigDecimal getStepInstallmentAVGOrig(String applicationRecordId){
        BigDecimal result=null;
        try {
            ExtExistingCustomerDAO extExistingCustomerInprogressDAO = ORIGDAOFactory.getExtExistingCustomerDAO();
            result= extExistingCustomerInprogressDAO.getInstallmentApplicationInprogressOrig(applicationRecordId);
        } catch (ExtExistingCustomerException e) {
            log.error("Error:", e);
            throw new EJBException(e.getLocalizedMessage());
        }                
        return result;       
    }
    public Vector getExitingCustomerBookedSurname(Object xrulesObject) {
    	return null;
        /*try {
            int limitResult = XRulesConstant.limitResult;
            XRulesDataM xrulesDataM = (XRulesDataM) xrulesObject;
            Vector result =new Vector();
            ExtExistingCustomerDAO extExistingCustomerBookedDAO=OrigExintDAOFactory.getExtExistingCustomerDAO();
            Vector resultExistingBookHire =extExistingCustomerBookedDAO.getExisitingCustomerBookedHireMatchSurname(xrulesDataM);
            if(resultExistingBookHire!=null&&resultExistingBookHire.size()>0){
                for(int i=0;i<resultExistingBookHire.size();i++){
                    if(i>limitResult){break;}
                    XRulesExistcustDataM  prmXRulesExistcustDataM  =(XRulesExistcustDataM)resultExistingBookHire.get(i);                   
                    prmXRulesExistcustDataM.setInstallment(extExistingCustomerBookedDAO.getInstallmentApplicationBooked(prmXRulesExistcustDataM.getContractNo()));                   
                    result.add(prmXRulesExistcustDataM);
                }     
                
                //result.addAll(resultExistingBookHire);    
            }
            Vector resultExistingBookGuarantor =extExistingCustomerBookedDAO.getExisitingCustomerBookedGuarantorMatchSurname(xrulesDataM);
            if(resultExistingBookGuarantor!=null&&resultExistingBookGuarantor.size()>0){
           
                for(int i=0;i<resultExistingBookGuarantor.size();i++){
                    if(i>limitResult){break;}
                    XRulesExistcustDataM  prmXRulesExistcustDataM  =(XRulesExistcustDataM)resultExistingBookGuarantor.get(i);                   
                    prmXRulesExistcustDataM.setInstallment(extExistingCustomerBookedDAO.getInstallmentApplicationBooked(prmXRulesExistcustDataM.getContractNo()));                   
                    result.add(prmXRulesExistcustDataM);
                }   
                //result.addAll(resultExistingBookGuarantor);    
            }             
            return result;
        } catch (Exception e) {
            log.error("Error:", e);
            throw new EJBException(e.getLocalizedMessage());
        }*/
    }
    public Vector getExitingCustomerInprogressSurname(Object xrulesObject) {
    	return null;
        /*try {
            int limitResult = XRulesConstant.limitResult;
            XRulesDataM xrulesDataM = (XRulesDataM) xrulesObject;
            Vector result =new Vector(); 
            ExtExistingCustomerDAO extExistingCustomerInprogressDAO=OrigExintDAOFactory.getExtExistingCustomerDAO();
            Vector resultExistingInprogressOrig =extExistingCustomerInprogressDAO.getExisitingCustomerInprogressOrigMatchSurname(xrulesDataM);
            if(resultExistingInprogressOrig!=null&&resultExistingInprogressOrig.size()>0){
               for(int i=0;i<resultExistingInprogressOrig.size();i++){
                   if(i>limitResult){break;}
                   XRulesExistcustInprogressDataM  prmXRulesExistcustInprogressDataM  =(XRulesExistcustInprogressDataM)resultExistingInprogressOrig.get(i);
                  if(OrigConstant.INSTALLMENT_TYPE_STEP.equals(prmXRulesExistcustInprogressDataM.getInstallmentFlag())){
                      prmXRulesExistcustInprogressDataM.setInstallment(extExistingCustomerInprogressDAO.getInstallmentApplicationInprogressOrig(prmXRulesExistcustInprogressDataM.getApplicationRecordId()));
                  }
                  result.add(prmXRulesExistcustInprogressDataM);
               }                               
               // result.addAll(resultExistingInprogressOrig);  
            }
            Vector resultExistingInprogressEXTHire=extExistingCustomerInprogressDAO.getExisitingCustomerInprogressEXTHireMatchSurname(xrulesDataM);
            if(resultExistingInprogressEXTHire!=null&&resultExistingInprogressEXTHire.size()>0){
                for(int i=0;i<resultExistingInprogressEXTHire.size();i++){
                    if(i>limitResult){break;}
                    XRulesExistcustInprogressDataM  prmXRulesExistcustInprogressDataM  =(XRulesExistcustInprogressDataM)resultExistingInprogressEXTHire.get(i);                   
                   prmXRulesExistcustInprogressDataM.setInstallment(extExistingCustomerInprogressDAO.getInstallmentApplicationInprogress(prmXRulesExistcustInprogressDataM.getApplicationNo()));                   
                   result.add(prmXRulesExistcustInprogressDataM);
                }     
              //result.addAll(resultExistingInprogressEXTHire);  
            }
            Vector resultExistingInprogressEXTGuarantor=extExistingCustomerInprogressDAO.getExisitingCustomerInprogressEXTGuarantorMatchSurname(xrulesDataM);
            if(resultExistingInprogressEXTGuarantor!=null&&resultExistingInprogressEXTGuarantor.size()>0){
              //result.addAll(resultExistingInprogressEXTGuarantor);  
              for(int i=0;i<resultExistingInprogressEXTGuarantor.size();i++){
                  if(i>limitResult){break;}
                  XRulesExistcustInprogressDataM  prmXRulesExistcustInprogressDataM  =(XRulesExistcustInprogressDataM)resultExistingInprogressEXTGuarantor.get(i);                  
                 prmXRulesExistcustInprogressDataM.setInstallment(extExistingCustomerInprogressDAO.getInstallmentApplicationInprogressOrig(prmXRulesExistcustInprogressDataM.getApplicationNo()));                 
                 result.add(prmXRulesExistcustInprogressDataM);
              }    
            }
            return result;
        } catch (Exception e) {
            log.error("Error:", e);
            throw new EJBException(e.getLocalizedMessage());
        }*/
    }
}
