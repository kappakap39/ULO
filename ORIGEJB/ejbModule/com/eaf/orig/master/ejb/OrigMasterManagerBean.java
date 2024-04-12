package com.eaf.orig.master.ejb;

import java.util.Date;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.master.shared.model.CarBlacklistM;
import com.eaf.orig.master.shared.model.ExceptActionM;
import com.eaf.orig.master.shared.model.FicoM;
import com.eaf.orig.master.shared.model.FieldIdM;
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.master.shared.model.HolidayM;
import com.eaf.orig.master.shared.model.ListBoxMasterM;
import com.eaf.orig.master.shared.model.MandatoryM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.master.shared.model.PolicyRulesDataM;
import com.eaf.orig.master.shared.model.QueueTimeOutM;
import com.eaf.orig.master.shared.model.RunningParamM;
import com.eaf.orig.master.shared.model.ScoreM;
import com.eaf.orig.master.shared.model.UserTeamM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.OrigMasterAppScoreDAO;
import com.eaf.orig.shared.dao.OrigMasterApprovAuthorDAO;
import com.eaf.orig.shared.dao.OrigMasterCarBlacklistDAO;
import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.shared.dao.OrigMasterExceptActDAO;
import com.eaf.orig.shared.dao.OrigMasterFICODAO;
import com.eaf.orig.shared.dao.OrigMasterFieldIdDAO;
import com.eaf.orig.shared.dao.OrigMasterGenParamDAO;
import com.eaf.orig.shared.dao.OrigMasterHolidayDAO;
import com.eaf.orig.shared.dao.OrigMasterListBoxDAO;
import com.eaf.orig.shared.dao.OrigMasterMandFieldDAO;
import com.eaf.orig.shared.dao.OrigMasterPolicyRulesDAO;
import com.eaf.orig.shared.dao.OrigMasterQTimeDAO;
import com.eaf.orig.shared.dao.OrigMasterRunParamDAO;
import com.eaf.orig.shared.dao.OrigMasterSLADAO;
import com.eaf.orig.shared.dao.OrigMasterUserDetailDAO;
import com.eaf.orig.shared.dao.OrigMasterUserTeamDAO;
import com.eaf.orig.shared.dao.OrigPolicyVersionMDAO;
import com.eaf.orig.shared.dao.WorkflowUtilDAO;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.SLADataM;

/**
 * Bean implementation class for Enterprise Bean: OrigMasterManager
 */
//@Stateless
public class OrigMasterManagerBean implements OrigMasterManager{
	
	@EJB ORIGGeneratorManager generatorManager;
	
	Logger logger = Logger.getLogger(OrigMasterManagerBean.class); 
	
	/* FICO Menu */
	
	public String saveFicoM(FicoM ficoM){
		String result = null;
		String message = "";
		OrigMasterFICODAO ficoDAO;
		try {
			ficoDAO = OrigMasterDAOFactory.getOrigMasterFICODAO();
			ficoDAO.createModelOrigMasterFicoScore(ficoM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveFicoM bean error",e);
			throw new EJBException("Exception in saveFicoM >> ", e);
        }
		
		return result;
	}
	
	/* AppScore Menu */
	
	public String saveAppScore(ScoreM scoreM){
		String result = null;
		String message = "";
		OrigMasterAppScoreDAO appScoreDAO;
		try {
			appScoreDAO = OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
			appScoreDAO.createModelOrigMasterAppScore(scoreM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveAppScore bean error",e);
			throw new EJBException("Exception in saveAppScore >> ", e);
        }
		
		return result;
	}
	
	/* UserTeam Menu */
	
	public String deleteUserTeam(String[] userTeamIDToDelete){
		String result = null;
		String message = "";
		OrigMasterUserTeamDAO userTeamDAO;
		try {
			userTeamDAO = OrigMasterDAOFactory.getOrigMasterUserTeamDAO();
			userTeamDAO.deleteOrigMasterUserTeam(userTeamIDToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteUserTeam bean error",e);
			throw new EJBException("Exception in deleteUserTeam >> ", e);
        }
		
		return result;
	}
	
	public String updateUserTeamM(UserTeamM userTeamM){
		String result = null;
		String message = "";
		OrigMasterUserTeamDAO userTeamDAO;
		try {
			userTeamDAO = OrigMasterDAOFactory.getOrigMasterUserTeamDAO();
			userTeamDAO.updateOrigMasterUserTeamM(userTeamM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateUserTeamM bean error",e);
			throw new EJBException("Exception in updateUserTeamM >> ", e);
        }
		
		return result;
	}
	
	public UserTeamM selectUserTeamM(String teamIDEdit){
		UserTeamM userTeamM = new UserTeamM();
		String result = null;
		String message = "";
		OrigMasterUserTeamDAO userTeamDAO;
		try {
			userTeamDAO = OrigMasterDAOFactory.getOrigMasterUserTeamDAO();
			userTeamM = userTeamDAO.selectOrigMasterUserTeamM(teamIDEdit);
			
			logger.debug("userTeamM = "+userTeamM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectUserTeamM bean error",e);
			throw new EJBException("Exception in selectUserTeamM >> ", e);
        }
		
		return userTeamM;
	}
	
	public String saveUserTeam(UserTeamM userTeamM){
		String result = null;
		String message = "";
		OrigMasterUserTeamDAO userTeamDAO;
		try {
			userTeamDAO = OrigMasterDAOFactory.getOrigMasterUserTeamDAO();
			userTeamDAO.createModelOrigMasterUserTeamM(userTeamM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveUserTeam bean error",e);
			throw new EJBException("Exception in saveUserTeam >> ", e);
        }
		
		return result;
	}
	
	/* Holiday Menu */
	
	public boolean chkHvHolDate(Date holDate){
		String result = null;
		String message = "";
		OrigMasterHolidayDAO holidayDAO;
		try {
			holidayDAO = OrigMasterDAOFactory.getOrigMasterHolidayDAO();
			if(holidayDAO.hvHolDate(holDate)){
				return true;
			}
			else{
				return false;
			}
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("chkHvHolDate bean error",e);
			throw new EJBException("Exception in chkHvHolDate >> ", e);
        }
	}
	
	public String deleteHolDate(String[] holDateToDelete){
		String result = null;
		String message = "";
		OrigMasterHolidayDAO holidayDAO;
		try {
			holidayDAO = OrigMasterDAOFactory.getOrigMasterHolidayDAO();
			holidayDAO.deleteOrigMasterHoliday(holDateToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteHolDate bean error",e);
			throw new EJBException("Exception in deleteHolDate >> ", e);
        }
		
		return result;
	}
	
	public String updateHoliday(HolidayM holidayM){
		String result = null;
		String message = "";
		OrigMasterHolidayDAO holidayDAO;
		try {
			holidayDAO = OrigMasterDAOFactory.getOrigMasterHolidayDAO();
			holidayDAO.updateOrigMasterHolidayM(holidayM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateHoliday bean error",e);
			throw new EJBException("Exception in updateHoliday >> ", e);
        }
		
		return result;
	}
	
	public HolidayM selectHolMaster(Date holDate){
		HolidayM holidayM = new HolidayM();
		String result = null;
		String message = "";
		OrigMasterHolidayDAO holidayDAO;
		try {
			holidayDAO = OrigMasterDAOFactory.getOrigMasterHolidayDAO();
			holidayM = holidayDAO.selectOrigMasterHolidayM(holDate);
			
			logger.debug("holidayM = "+holidayM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectQTimeId bean error",e);
			throw new EJBException("Exception in selectQTimeId >> ", e);
        }
		
		return holidayM;
	}
	
	public String saveHoliday(HolidayM holidayM){
		String result = null;
		String message = "";
		OrigMasterHolidayDAO holidayDAO;
		try {
			holidayDAO = OrigMasterDAOFactory.getOrigMasterHolidayDAO();
			holidayDAO.createModelOrigMasterHolidayM(holidayM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveHoliday bean error",e);
			throw new EJBException("Exception in saveHoliday >> ", e);
        }
		
		return result;
	}
	
	/* QueueTime Menu */
	
	public String deleteQTime(String[] qTimeIDToDelete){
		String result = null;
		String message = "";
		OrigMasterQTimeDAO qTimeDAO;
		try {
			qTimeDAO = OrigMasterDAOFactory.getOrigMasterQTimeDAO();
			qTimeDAO.deleteOrigMasterQTimeOut(qTimeIDToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteQTime bean error",e);
			throw new EJBException("Exception in deleteQTime >> ", e);
        }
		
		return result;
	}
	
	public String updateQTimeOut(QueueTimeOutM qTimeOutM){
		String result = null;
		String message = "";
		OrigMasterQTimeDAO qTimeDAO;
		try {
			qTimeDAO = OrigMasterDAOFactory.getOrigMasterQTimeDAO();
			qTimeDAO.updateOrigMasterQTimeOutM(qTimeOutM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateQTimeOut bean error",e);
			throw new EJBException("Exception in updateQTimeOut >> ", e);
        }
		
		return result;
	}
	
	public QueueTimeOutM selectQTimeId(String qTimeID){
		QueueTimeOutM qTimeOutM = new QueueTimeOutM();
		String result = null;
		String message = "";
		OrigMasterQTimeDAO qTimeDAO;
		try {
			qTimeDAO = OrigMasterDAOFactory.getOrigMasterQTimeDAO();
			qTimeOutM = qTimeDAO.selectOrigMasterQTimeM(qTimeID);
			
			logger.debug("qTimeOutM = "+qTimeOutM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectQTimeId bean error",e);
			throw new EJBException("Exception in selectQTimeId >> ", e);
        }
		
		return qTimeOutM;
		
	}
	
	public String saveQTimeOut(QueueTimeOutM qTimeOutM){
		String result = null;
		String message = "";
		OrigMasterQTimeDAO qTimeDAO;
		try {
			qTimeDAO = OrigMasterDAOFactory.getOrigMasterQTimeDAO();
			qTimeDAO.createModelOrigMasterQTimeOutM(qTimeOutM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveQTimeOut bean error",e);
			throw new EJBException("Exception in saveQTimeOut >> ", e);
        }
		
		return result;
	}
	
	public boolean chkHvQTimeID(String qTimeID){
		String result = null;
		String message = "";
		OrigMasterQTimeDAO qTimeDAO;
		try {
			qTimeDAO = OrigMasterDAOFactory.getOrigMasterQTimeDAO();
			if(qTimeDAO.hvQTimeID(qTimeID)){
				return true;
			}
			else{
				return false;
			}
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("chkHvQTimeID bean error",e);
			throw new EJBException("Exception in chkHvQTimeID >> ", e);
        }
	}
	
	/* CarBlacklist Menu*/
	
	public String deleteCarBlacklist(int[] blackVehID_intToDelete){
		String result = null;
		String message = "";
		OrigMasterCarBlacklistDAO carBlacklistDAO;
		try {
			carBlacklistDAO = OrigMasterDAOFactory.getOrigMasterCarBlacklistDAO();
			carBlacklistDAO.deleteOrigMasterCarBlacklist(blackVehID_intToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteCarBlacklist bean error",e);
			throw new EJBException("Exception in deleteCarBlacklist >> ", e);
        }
		
		return result;
	}
	
	public String updateCarBlacklist(CarBlacklistM carBlacklistM){
		String result = null;
		String message = "";
		OrigMasterCarBlacklistDAO carBlacklistDAO;
		try {
			carBlacklistDAO = OrigMasterDAOFactory.getOrigMasterCarBlacklistDAO();
			carBlacklistDAO.updateOrigMasterCarBlacklistM(carBlacklistM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateCarBlacklist bean error",e);
			throw new EJBException("Exception in updateCarBlacklist >> ", e);
        }
		
		return result;
	}
	
	public CarBlacklistM selectCarBlacklist(int blackVehID){
		CarBlacklistM carBlacklistM = new CarBlacklistM();
		String result = null;
		String message = "";
		OrigMasterCarBlacklistDAO carBlacklistDAO;
		try {
			carBlacklistDAO = OrigMasterDAOFactory.getOrigMasterCarBlacklistDAO();
			carBlacklistM = carBlacklistDAO.selectOrigMasterCarBlacklistM(blackVehID);
			
			logger.debug("carBlacklistM = "+carBlacklistM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectCarBlacklist bean error",e);
			throw new EJBException("Exception in selectCarBlacklist >> ", e);
        }
		
		return carBlacklistM;
	}
	
	public String saveCarBlacklist(CarBlacklistM carBlacklistM){
		String result = null;
		String message = "";
		OrigMasterCarBlacklistDAO carBlacklistDAO;
		try {
			carBlacklistDAO = OrigMasterDAOFactory.getOrigMasterCarBlacklistDAO();
			carBlacklistDAO.createModelOrigMasterCarBlacklistM(carBlacklistM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveCarBlacklist bean error",e);
			throw new EJBException("Exception in saveCarBlacklist >> ", e);
        }
		
		return result;
	}
	
	/* ApprovAuthor Menu*/
	
	public boolean chkHvColumn(String grpNm, String lnTyp, String cusTyp){
		String result = null;
		String message = "";
		OrigMasterApprovAuthorDAO approvAuthorDAO;
		try {
			approvAuthorDAO = OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO();
			if(approvAuthorDAO.hvColumn(grpNm, lnTyp, cusTyp)){
				return true;
			}
			else{
				return false;
			}
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("chkHvColumn bean error",e);
			throw new EJBException("Exception in chkHvColumn >> ", e);
        }
	}
	
	public String deleteApprovAuthor(String[] appAuthorToDelete){
		String result = null;
		String message = "";
		OrigMasterApprovAuthorDAO approvAuthorDAO;
		try {
			approvAuthorDAO = OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO();
			approvAuthorDAO.deleteOrigMasterApprovAuthorM(appAuthorToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteApprovAuthor bean error",e);
			throw new EJBException("Exception in deleteApprovAuthor >> ", e);
        }
		
		return result;
	}
	
	public String updateApprovAuthor(ApprovAuthorM approvAuthorM){
		String result = null;
		String message = "";
		OrigMasterApprovAuthorDAO approvAuthorDAO;
		try {
			approvAuthorDAO = OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO();
			approvAuthorDAO.updateOrigMasterApprovAuthor(approvAuthorM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateApprovAuthor bean error",e);
			throw new EJBException("Exception in updateApprovAuthor >> ", e);
        }
		
		return result;
	}
	
	public ApprovAuthorM selectApprovAuthor(String grpNm, String lnTyp, String cusTyp){
		ApprovAuthorM approvAuthorM = new ApprovAuthorM();
		String result = null;
		String message = "";
		OrigMasterApprovAuthorDAO approvAuthorDAO;
		try {
			approvAuthorDAO = OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO();
			//approvAuthorM = approvAuthorDAO.selectOrigMasterApprovAuthor(grpNm, lnTyp, cusTyp);
			
			logger.debug("approvAuthorM = "+approvAuthorM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectApprovAuthor bean error",e);
			throw new EJBException("Exception in selectApprovAuthor >> ", e);
        }
		
		return approvAuthorM;
		
	}
	
	public String saveApprovAuthor(ApprovAuthorM approvAuthorM){
		String result = null;
		String message = "";
		OrigMasterApprovAuthorDAO approvAuthorDAO;
		try {
			approvAuthorDAO = OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO();
			approvAuthorDAO.createModelOrigMasterApprovAuthorM(approvAuthorM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveApprovAuthor bean error",e);
			throw new EJBException("Exception in saveApprovAuthor >> ", e);
        }
		
		return result;
	}
	
	/* MandField Menu*/
	
	public String updateMandatoryM(MandatoryM mandatoryM){
		String result = null;
		String message = "";
		OrigMasterMandFieldDAO mandFieldDAO;
		try {
			mandFieldDAO = OrigMasterDAOFactory.getOrigMasterMandFieldDAO();
			mandFieldDAO.updateOrigMasterMandatoryM(mandatoryM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateMandatoryM bean error",e);
			throw new EJBException("Exception in updateMandatoryM >> ", e);
        }
		
		return result;
	}
	
	public MandatoryM selectMandField(String frmNameIDEdit,String frmNameEdit,String cusTypeEdit){
		MandatoryM mandatoryM = new MandatoryM();
		String result = null;
		String message = "";
		OrigMasterMandFieldDAO mandFieldDAO;
		try {
			mandFieldDAO = OrigMasterDAOFactory.getOrigMasterMandFieldDAO();
			mandatoryM = mandFieldDAO.selectOrigMasterMandatoryM(frmNameIDEdit, frmNameEdit, cusTypeEdit);
			
			logger.debug("mandatoryM = "+mandatoryM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectMandField bean error",e);
			throw new EJBException("Exception in selectMandField >> ", e);
        }
		
		return mandatoryM;
	}
	
	/* ExceptAct Menu*/
	
	public String deleteExceptAct(String[] exceptIDToDelete){
		String result = null;
		String message = "";
		OrigMasterExceptActDAO exceptActDAO;
		try {
			exceptActDAO = OrigMasterDAOFactory.getOrigMasterExceptActDAO();
			exceptActDAO.deleteOrigMasterExceptActM(exceptIDToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteExceptAct bean error",e);
			throw new EJBException("Exception in deleteExceptAct >> ", e);
        }
		
		return result;
	}
	
	public String updateExceptAct(ExceptActionM exceptActM){
		String result = null;
		String message = "";
		OrigMasterExceptActDAO exceptActDAO;
		try {
			exceptActDAO = OrigMasterDAOFactory.getOrigMasterExceptActDAO();
			exceptActDAO.updateOrigMasterExceptActM(exceptActM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateExceptAct bean error",e);
			throw new EJBException("Exception in updateExceptAct >> ", e);
        }
		
		return result;
	}
	
	public ExceptActionM selectExceptAct(String exceptIDEdit){
		ExceptActionM exceptActM = new ExceptActionM();
		String result = null;
		String message = "";
		OrigMasterExceptActDAO exceptActDAO;
		try {
			exceptActDAO = OrigMasterDAOFactory.getOrigMasterExceptActDAO();
			exceptActM = exceptActDAO.selectOrigMasterExceptActM(exceptIDEdit);
			
			logger.debug("exceptActM = "+exceptActM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectExceptAct bean error",e);
			throw new EJBException("Exception in selectExceptAct >> ", e);
        }
		
		return exceptActM;
	}
	
	public String saveExceptAct(ExceptActionM exceptActM){
		String result = null;
		String message = "";
		OrigMasterExceptActDAO exceptActDAO;
		try {
			exceptActDAO = OrigMasterDAOFactory.getOrigMasterExceptActDAO();
			exceptActDAO.createModelOrigMasterExceptActM(exceptActM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveExceptAct bean error",e);
			throw new EJBException("Exception in saveExceptAct >> ", e);
        }
		
		return result;
	}
	
	/* GenParam Menu*/
	
	public boolean chkHvGenPrmCde(String genPrmCde){
		String result = null;
		String message = "";
		OrigMasterGenParamDAO genParamDAO;
		try {
			genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
			if(genParamDAO.hvGenPrmCde(genPrmCde)){
				return true;
			}
			else{
				return false;
			}
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("chkHvGenPrmCde bean error",e);
			throw new EJBException("chkHvGenPrmCde in chkHvFieldID >> ", e);
        }
	}
	
	public String deleteGenParam(String[] genParamToDelete){
		String result = null;
		String message = "";
		OrigMasterGenParamDAO genParamDAO;
		try {
			genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
			genParamDAO.deleteOrigMasterGenParamM(genParamToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteGenParam bean error",e);
			throw new EJBException("Exception in deleteGenParam >> ", e);
        }
		
		return result;
	}
	
	public String updateGenParam(GeneralParamM genParamM){
		String result = null;
		String message = "";
		OrigMasterGenParamDAO genParamDAO;
		try {
			genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
			genParamDAO.updateOrigMasterGenParamM(genParamM);
		} catch(Exception e){
            //result = false;
			message = e.toString(); 
			logger.error("updateGenParam bean error",e);
			throw new EJBException("Exception in updateGenParam >> ", e);
        }
		
		return result;
	}
	
	public GeneralParamM selectGenParamM(String genParamCde,String busID){
		GeneralParamM genParamM = new GeneralParamM();
		String result = null;
		String message = "";
		OrigMasterGenParamDAO genParamDAO;
		try {
			genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
			genParamM = genParamDAO.loadModelOrigMasterGenParamM(genParamCde,busID);
			
			logger.debug("genParamM = "+genParamM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectGenParamM bean error",e);
			throw new EJBException("Exception in selectGenParamM >> ", e);
        }
		
		return genParamM;
	}
	
	public String saveGenParam(GeneralParamM genParamM){
		String result = null;
		String message = "";
		OrigMasterGenParamDAO genParamDAO;
		try {
			genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
			genParamDAO.createModelOrigMasterGenParamM(genParamM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveGenParam bean error",e);
			throw new EJBException("Exception in saveGenParam >> ", e);
        }
		
		return result;
	}
	
	/* RunParam Menu*/
	
	public String updateRunParam(RunningParamM runParamM){
		String result = null;
		String message = "";
		OrigMasterRunParamDAO runParamDAO;
		try {
			runParamDAO = OrigMasterDAOFactory.getOrigMasterRunParamDAO();
			runParamDAO.updateOrigMasterRunParamM(runParamM);
		} catch(Exception e){
            //result = false;
			message = e.toString(); 
			logger.error("updateRunParam bean error",e);
			throw new EJBException("Exception in updateRunParam >> ", e);
        }
		
		return result;
	}
	
	public RunningParamM selectRunParam(String runPrmID, String runPrmType){
		RunningParamM runParamM = new RunningParamM();
		String result = null;
		String message = "";
		OrigMasterRunParamDAO runParamDAO;
		try {
			runParamDAO = OrigMasterDAOFactory.getOrigMasterRunParamDAO();
			runParamM = runParamDAO.selectOrigMasterRunParamM(runPrmID,runPrmType);
			
			logger.debug("runParamM = "+runParamM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectRunParam bean error",e);
			throw new EJBException("Exception in selectRunParam >> ", e);
        }
		
		return runParamM;
	}
	public String deleteRunParam(Vector runParamToDelete){
		String result = null;
		String message = "";
		OrigMasterRunParamDAO runParamDAO;
		try {
			runParamDAO = OrigMasterDAOFactory.getOrigMasterRunParamDAO();
			runParamDAO.deleteOrigMasterRunParamM(runParamToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteRunParam bean error",e);
			throw new EJBException("Exception in deleteRunParam >> ", e);
        }
		
		return result;
	}	 	
	/* FieldId Menu*/
	
	public boolean chkHvFieldID(int fieldID){
		String result = null;
		String message = "";
		OrigMasterFieldIdDAO fieldIdDAO;
		try {
			fieldIdDAO = OrigMasterDAOFactory.getOrigMasterFieldIdDAO();
			if(fieldIdDAO.hvFieldID(fieldID)){
				return true;
			}
			else{
				return false;
			}
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("chkHvFieldID bean error",e);
			throw new EJBException("Exception in chkHvFieldID >> ", e);
        }
	}
	
	public String deleteFieldId(int[] fieldIdToDelete){
		String result = null;
		String message = "";
		OrigMasterFieldIdDAO fieldIdDAO;
		try {
			fieldIdDAO = OrigMasterDAOFactory.getOrigMasterFieldIdDAO();
			fieldIdDAO.deleteOrigMasterFieldIdM(fieldIdToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteFieldId bean error",e);
			throw new EJBException("Exception in deleteFieldId >> ", e);
        }
		
		return result;
	}
	
	public String updateFieldId(FieldIdM fieldIdM){
		String result = null;
		String message = "";
		OrigMasterFieldIdDAO fieldIdDAO;
		try {
			fieldIdDAO = OrigMasterDAOFactory.getOrigMasterFieldIdDAO();
			fieldIdDAO.updateOrigMasterFieldIdM(fieldIdM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateFieldId bean error",e);
			throw new EJBException("Exception in updateFieldId >> ", e);
        }
		
		return result;
	}
	
	public FieldIdM selectFieldId(int fieldId){
		FieldIdM fieldIdM = new FieldIdM();
		String result = null;
		String message = "";
		OrigMasterFieldIdDAO fieldIdDAO;
		try {
			fieldIdDAO = OrigMasterDAOFactory.getOrigMasterFieldIdDAO();
			fieldIdM = fieldIdDAO.selectOrigMasterFieldIdM(fieldId);
			
			logger.debug("fieldIdM = "+fieldIdM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectFieldId bean error",e);
			throw new EJBException("Exception in selectFieldId >> ", e);
        }
		
		return fieldIdM;
	}
	
	public String saveFieldId(FieldIdM fieldIdM){
		String result = null;
		String message = "";
		OrigMasterFieldIdDAO fieldIdDAO;
		try {
			fieldIdDAO = OrigMasterDAOFactory.getOrigMasterFieldIdDAO();
			fieldIdDAO.createModelOrigMasterFieldIdM(fieldIdM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveFieldId bean error",e);
			throw new EJBException("Exception in saveFieldId >> ", e);
        }
		
		return result;
	}
	
	/* ListBox Menu*/
	
	public String deleteListBox(String[] listBoxToDelete){
		String result = null;
		String message = "";
		OrigMasterListBoxDAO listBoxDAO;
		try {
			listBoxDAO = OrigMasterDAOFactory.getOrigMasterListBoxDAO();
			listBoxDAO.deleteOrigMasterListBoxM(listBoxToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteListBox bean error",e);
			throw new EJBException("Exception in deleteListBox >> ", e);
        }
		
		return result;
	}
	
	public String updateListBox(ListBoxMasterM listBoxM){
		String result = null;
		String message = "";
		OrigMasterListBoxDAO listBoxDAO;
		try {
			listBoxDAO = OrigMasterDAOFactory.getOrigMasterListBoxDAO();
			listBoxDAO.updateOrigMasterListBoxM(listBoxM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateListBox bean error",e);
			throw new EJBException("Exception in updateListBox >> ", e);
        }
		
		return result;
	}
	
	public ListBoxMasterM selectListBox(String listID){
		ListBoxMasterM listBoxM = new ListBoxMasterM();
		String result = null;
		String message = "";
		OrigMasterListBoxDAO listBoxDAO;
		try {
			listBoxDAO = OrigMasterDAOFactory.getOrigMasterListBoxDAO();
			listBoxM = listBoxDAO.selectOrigMasterListBoxM(listID);
			
			logger.debug("listBoxM = "+listBoxM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectListBox bean error",e);
			throw new EJBException("Exception in selectListBox >> ", e);
        }
		
		return listBoxM;
	}
	
	public String saveListBox(ListBoxMasterM listBoxM){
		String result = null;
		String message = "";
		OrigMasterListBoxDAO listBoxDAO;
		try {
			listBoxDAO = OrigMasterDAOFactory.getOrigMasterListBoxDAO();
			listBoxDAO.createModelOrigMasterListBoxMasterM(listBoxM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveListBox bean error",e);
			throw new EJBException("Exception in saveListBox >> ", e);
        }
		
		return result;
	}
	
	/* UserDetail Menu*/
	
	public boolean chkHvUserName(String UserName){
		String result = null;
		String message = "";
		OrigMasterUserDetailDAO userDetailDAO;
		try {
			userDetailDAO = OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
			if(userDetailDAO.hvUsername(UserName)){
				return true;
			}
			else{
				return false;
			}
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteUserDetail bean error",e);
			throw new EJBException("Exception in saveUserDetail >> ", e);
        }
	}
	
	public String deleteUserDetail(String[] userDetailToDelete){
		String result = null;
		String message = "";
		OrigMasterUserDetailDAO userDetailDAO;
		try {
			userDetailDAO = OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
			userDetailDAO.deleteOrigMasterUserDetailM(userDetailToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deleteUserDetail bean error",e);
			throw new EJBException("Exception in saveUserDetail >> ", e);
        }
		
		return result;
	}
	
	public String updateUserDetail(UserDetailM userDetailM){
		String result = null;
		String message = "";
		OrigMasterUserDetailDAO userDetailDAO;
		try {
			userDetailDAO = OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
			userDetailDAO.updateOrigMasterUserDetailM(userDetailM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updateUserDetail bean error",e);
			throw new EJBException("Exception in saveUserDetail >> ", e);
        }
		
		return result;
	}
	
	public String saveUserDetail(UserDetailM userDetailM){
		String result = null;
		String message = "";
		OrigMasterUserDetailDAO userDetailDAO;
		try {
			userDetailDAO = OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
			userDetailDAO.createModelOrigMasterUserDetailM(userDetailM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveUserDetail bean error",e);
			throw new EJBException("Exception in saveUserDetail >> ", e);
        }
		
		return result;
	}
	
	public UserDetailM selectUserDetail(String userNameEdit){
		UserDetailM userDetailM = new UserDetailM();
		String result = null;
		String message = "";
		OrigMasterUserDetailDAO userDetailDAO;
		try {
			userDetailDAO = OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
			userDetailM = userDetailDAO.selectOrigMasterUserDetailM(userNameEdit);
			
			logger.debug("userDetailM = "+userDetailM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectUserDetail bean error",e);
			throw new EJBException("Exception in saveUserDetail >> ", e);
        }
		
		return userDetailM;
	}
	
	public Vector loadCriticalProcess(){
		Vector vAIID = new Vector();
		WorkflowUtilDAO workflowDAO = OrigMasterDAOFactory.getWorkflowUtilDAO();
		try {
			vAIID = workflowDAO.loadCriticalProcess();
		} catch (OrigApplicationMException e) {
			logger.error("##### loadCriticalProcess #####",e);
		}
		return vAIID;
	}
	
	public double updateBeforeSearchCriticalProcess() throws EJBException{
		double updatedRow = 0;
		WorkflowUtilDAO workflowDAO = OrigMasterDAOFactory.getWorkflowUtilDAO();
		try {
			updatedRow = workflowDAO.updateForSearchCriticalProcess();
		} catch (OrigApplicationMException e) {
			logger.error("##### loadCriticalProcess #####",e);
			throw new EJBException("Exception in updateBeforeSearchCriticalProcess >> ", e);
		}
		return updatedRow;
	}
	
	public String saveSLA(SLADataM dataM){
		String result = null;
		String message = "";
		OrigMasterSLADAO masterSLADAO;
		try {
			masterSLADAO = OrigMasterDAOFactory.getOrigMasterSLADAO();
			masterSLADAO.updateSLADataM(dataM);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("saveUserDetail bean error",e);
			throw new EJBException("Exception in saveUserDetail >> ", e);
        }
		
		return result;
	}
	
	private javax.ejb.SessionContext mySessionCtx;
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
	public String updatePolicyRules(PolicyRulesDataM policyRulesDataM){
		String result = null;
		String message = "";
		OrigMasterPolicyRulesDAO policyRulesDAO;
		try {
		    policyRulesDAO = OrigMasterDAOFactory.getOrigMasterPolicyRulesDAO();
		    policyRulesDAO.updateOrigMasterPolicyRulesDataM(policyRulesDataM);
		} catch(Exception e){
            //result = false;
			message = e.toString(); 
			logger.error("updatePolicyRules bean error",e);
			throw new EJBException("Exception in updatePolicyRules >> ", e);
        }
		
		return result;
	}
	public String deletePolicyRules(Vector policyRulesToDelete){
		String result = null;
		String message = "";
		OrigMasterPolicyRulesDAO policyRulesDAO;
		try {
		    policyRulesDAO = OrigMasterDAOFactory.getOrigMasterPolicyRulesDAO();
		    policyRulesDAO.deleteOrigMasterPolicyRulesDataM(policyRulesToDelete);
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("deletePolicyRules bean error",e);
			throw new EJBException("Exception in deletePolicyRules >> ", e);
        }
		
		return result;
	}	 
	public PolicyRulesDataM selectPolicyRules(String policyCode, String policyType){
		PolicyRulesDataM policyRulesDataM = new PolicyRulesDataM();
		String result = null;
		String message = "";
		OrigMasterPolicyRulesDAO policyRulesDAO;
		try {
		    policyRulesDAO = OrigMasterDAOFactory.getOrigMasterPolicyRulesDAO();
		    policyRulesDataM = policyRulesDAO.selectOrigMasterPolicyRulesDataM(policyCode,policyType);			
			logger.debug("policyRulesDataM = "+policyRulesDataM);
			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("selectRunParam bean error",e);
			throw new EJBException("Exception in selectRunParam >> ", e);
        }
		
		return policyRulesDataM;
	}
	public String updatePolicyVersion(OrigPolicyVersionDataM origPolicyVersionDataM){
		String result = null;
		String message = "";
		OrigPolicyVersionMDAO origPolicyVersionDAO;
		try {
		    logger.debug("updatePolicyVersion");
		    origPolicyVersionDAO = ORIGDAOFactory.getOrigPolicyVersionMDAO();
		    origPolicyVersionDAO.saveUpdateModelOrigPolicyVersionDataM(origPolicyVersionDataM);			 
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("updatePolicyVersion bean error",e);
			throw new EJBException("Exception in updatePolicyVersion >> ", e);
        }
		
		return result;
	}
	public String deletePolicyVersion(String[] origPolicyVersion)   {
		String result = null;
		String message = "";
		OrigPolicyVersionMDAO origPolicyVersionDAO;
		try {
		    origPolicyVersionDAO = ORIGDAOFactory.getOrigPolicyVersionMDAO();
		    origPolicyVersionDAO.deleteModelOrigPolicyVersionDataM(origPolicyVersion);			
		} catch(Exception e){
            //result = false;
			message = e.toString();
			logger.error("Delete PolicyvvErsion bean error",e);
			throw new EJBException("Exception in deletePolicyVersion >> ", e);
        }
		
		return result;
	}
}
