package com.eaf.orig.master.ejb;

import java.util.Date;
import java.util.Vector;

import javax.ejb.Local;

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
import com.eaf.orig.shared.model.SLADataM;
/**
 * Remote interface for Enterprise Bean: OrigMasterManager
 */
@Local
public interface OrigMasterManager {
	//UserDetail Menu
	public String saveUserDetail(UserDetailM userDetailM);
	public UserDetailM selectUserDetail(String userNameEdit) ;
	public String updateUserDetail(UserDetailM userDetailM) ;
	public String deleteUserDetail(String[] userDetailToDelete) ;
	public boolean chkHvUserName(String UserName) ;
	
	//ListBox Menu
	public String saveListBox(ListBoxMasterM listBoxM);
	public ListBoxMasterM selectListBox(String listID);
	public String updateListBox(ListBoxMasterM listBoxM);
	public String deleteListBox(String[] listBoxToDelete);
	
	//FieldId Menu
	public String saveFieldId(FieldIdM fieldIdM);
	public FieldIdM selectFieldId(int fieldId);
	public String updateFieldId(FieldIdM fieldIdM);
	public String deleteFieldId(int[] fieldIdToDelete);
	public boolean chkHvFieldID(int fieldID);
	
	//	RunParam Menu
	public RunningParamM selectRunParam(String runPrmID, String runPrmType);
	public String updateRunParam(RunningParamM runParamM);
//	public String deleteFieldId(int[] fieldIdToDelete);
	
	//	GenParam Menu
	public String saveGenParam(GeneralParamM genParamM);
	public GeneralParamM selectGenParamM(String genParamCde,String busID);
	public String updateGenParam(GeneralParamM genParamM);
	public String deleteGenParam(String[] genParamToDelete);
	public boolean chkHvGenPrmCde(String genPrmCde);
	
	//	ExceptAct Menu
	public String saveExceptAct(ExceptActionM exceptActM);
	public ExceptActionM selectExceptAct(String exceptIDEdit);
	public String updateExceptAct(ExceptActionM exceptActM);
	public String deleteExceptAct(String[] exceptIDToDelete);
	
	//	MandField Menu
	public MandatoryM selectMandField(String frmNameIDEdit,String frmNameEdit,String cusTypeEdit);
	public String updateMandatoryM(MandatoryM mandatoryM);
	
	//	ApprovAuthor Menu
	public String saveApprovAuthor(ApprovAuthorM approvAuthorM);
	public ApprovAuthorM selectApprovAuthor(String grpNm, String lnTyp, String cusTyp);
	public String updateApprovAuthor(ApprovAuthorM approvAuthorM);
	public String deleteApprovAuthor(String[] appAuthorToDelete);
	public boolean chkHvColumn(String grpNm, String lnTyp, String cusTyp);
	
	//	CarBlacklist Menu
	public String saveCarBlacklist(CarBlacklistM carBlacklistM);
	public CarBlacklistM selectCarBlacklist(int blackVehID);
	public String updateCarBlacklist(CarBlacklistM carBlacklistM);
	public String deleteCarBlacklist(int[] blackVehID_intToDelete);
	
	// QueueTime Menu
	public boolean chkHvQTimeID(String qTimeID);
	public String saveQTimeOut(QueueTimeOutM qTimeOutM);
	public QueueTimeOutM selectQTimeId(String qTimeID);
	public String updateQTimeOut(QueueTimeOutM qTimeOutM);
	public String deleteQTime(String[] qTimeIDToDelete);
	
	// Holiday Menu
	public boolean chkHvHolDate(Date holDate);
	public String saveHoliday(HolidayM holidayM);
	public HolidayM selectHolMaster(Date holDate);
	public String updateHoliday(HolidayM holidayM);
	public String deleteHolDate(String[] holDateToDelete);
	
	// UserTeam Menu
//	public boolean chkHvHolDate(Date holDate);
	public String saveUserTeam(UserTeamM userTeamM);
	public UserTeamM selectUserTeamM(String teamIDEdit);
	public String updateUserTeamM(UserTeamM userTeamM);
	public String deleteUserTeam(String[] userTeamIDToDelete);
	
	// AppScore Menu
	public String saveAppScore(ScoreM scoreM);
	
	// FICO Menu
	public String saveFicoM(FicoM ficoM);
	
	public Vector loadCriticalProcess();
	public double updateBeforeSearchCriticalProcess();
	public String saveSLA(SLADataM dataM);          
    public String deleteRunParam(Vector runParamToDelete);
	public PolicyRulesDataM selectPolicyRules(String policyCode, String policyType);
	public String deletePolicyRules(Vector policyRulesToDelete);
	public String updatePolicyRules(PolicyRulesDataM policyRulesDataM);
    public String deletePolicyVersion(String[] origPolicyVersion);
    public String updatePolicyVersion(OrigPolicyVersionDataM origPolicyVersionDataM);
}
