package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BankStatementDataM;
import com.eaf.orig.ulo.model.app.ClosedEndFundDataM;
import com.eaf.orig.ulo.model.app.FinancialInstrumentDataM;
import com.eaf.orig.ulo.model.app.FixedAccountDataM;
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.KVIIncomeDataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDataM;
import com.eaf.orig.ulo.model.app.OtherIncomeDataM;
import com.eaf.orig.ulo.model.app.PayrollDataM;
import com.eaf.orig.ulo.model.app.PayrollFileDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PreviousIncomeDataM;
import com.eaf.orig.ulo.model.app.SalaryCertDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDataM;
import com.eaf.orig.ulo.model.app.TaweesapDataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public class OrigIncomeCategoryDAOImpl extends OrigObjectDAO implements
		OrigIncomeCategoryDAO {
	public OrigIncomeCategoryDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigIncomeCategoryDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigIncomeCategory(IncomeCategoryDataM incomeCategoryM)
			throws ApplicationException {
		if(incomeCategoryM instanceof ClosedEndFundDataM) {
			ClosedEndFundDataM clsEndFundM = (ClosedEndFundDataM) incomeCategoryM;
			OrigClosedEndFundDAO clsFundDAO = ORIGDAOFactory.getClosedEndFundDAO(userId);
			clsFundDAO.createOrigClosedEndFundM(clsEndFundM);
		} 
		else if(incomeCategoryM instanceof FinancialInstrumentDataM) {
			OrigFinInstrumentDAO finInstrumentDAO = ORIGDAOFactory.getFinInstrumentDAO(userId);
			FinancialInstrumentDataM finInstrumentM =(FinancialInstrumentDataM) incomeCategoryM;
			finInstrumentDAO.createOrigFinInstrumentM(finInstrumentM);
		}
		else if(incomeCategoryM instanceof FixedAccountDataM) {
			OrigFixedAccountDAO fixAcctDAO = ORIGDAOFactory.getFixedAccountDAO(userId);
			FixedAccountDataM fixAcctM = (FixedAccountDataM) incomeCategoryM;
			fixAcctDAO.createOrigFixedAccountM(fixAcctM);
		}
		else if(incomeCategoryM instanceof FixedGuaranteeDataM) {
			OrigFixedGuaranteeDAO fixGuaranteeDAO = ORIGDAOFactory.getFixedGuaranteeDAO(userId);
			FixedGuaranteeDataM fixGuaranteeM = (FixedGuaranteeDataM)incomeCategoryM;
			fixGuaranteeDAO.createOrigFixedGuaranteeM(fixGuaranteeM);
		}
		else if(incomeCategoryM instanceof KVIIncomeDataM) {
			OrigKVIIncomeDAO kviDAO = ORIGDAOFactory.getKVIIncomeDAO(userId);
			KVIIncomeDataM kviIncomeM = (KVIIncomeDataM) incomeCategoryM;
			kviDAO.createOrigKVIIncomeM(kviIncomeM);
		}
		else if(incomeCategoryM instanceof MonthlyTawi50DataM) {
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO(userId);
			MonthlyTawi50DataM monthlyTawiM = (MonthlyTawi50DataM) incomeCategoryM;
			monthlyDAO.createOrigMonthlyTawiM(monthlyTawiM);
		}
		else if(incomeCategoryM instanceof OpenedEndFundDataM) {
			OrigOpenEndFundDAO opnEndFundDAO = ORIGDAOFactory.getOpenEndFundDAO(userId);
			OpenedEndFundDataM opnEndFundM = (OpenedEndFundDataM) incomeCategoryM;
			opnEndFundDAO.createOrigOpenEndFundM(opnEndFundM);
		}
		else if(incomeCategoryM instanceof PayrollDataM) {
			OrigPayrollDAO payrollDAO = ORIGDAOFactory.getPayrollDAO(userId);
			PayrollDataM payrollM = (PayrollDataM) incomeCategoryM;
			payrollDAO.createOrigPayrollM(payrollM);
		}
		else if(incomeCategoryM instanceof PayrollFileDataM) {
			OrigPayrollFileDAO payrollFileDAO = ORIGDAOFactory.getPayrollFileDAO(userId);
			PayrollFileDataM payrollFileM = (PayrollFileDataM) incomeCategoryM;
			payrollFileDAO.createOrigPayrollFileM(payrollFileM);
		}
		else if(incomeCategoryM instanceof PayslipDataM) {
			OrigPayslipDAO payslipDAO = ORIGDAOFactory.getPayslipDAO(userId);
			PayslipDataM payslipM = (PayslipDataM) incomeCategoryM;
			payslipDAO.createOrigPayslipM(payslipM);
		}
		else if(incomeCategoryM instanceof PreviousIncomeDataM) {
			OrigPreviousIncomeDAO prevIncomeDAO = ORIGDAOFactory.getPreviousIncomeDAO(userId);
			PreviousIncomeDataM prevIncomeM = (PreviousIncomeDataM) incomeCategoryM;
			prevIncomeDAO.createOrigPreviousIncomeM(prevIncomeM);
		}
		else if(incomeCategoryM instanceof SalaryCertDataM) {
			OrigSalaryCertDAO salaryCertDAO = ORIGDAOFactory.getSalaryCertDAO(userId);
			SalaryCertDataM salaryCertM = (SalaryCertDataM) incomeCategoryM;
			salaryCertDAO.createOrigSalaryCertM(salaryCertM);
		}
		else if(incomeCategoryM instanceof BankStatementDataM) {
			OrigBankStatementDAO statementDAO = ORIGDAOFactory.getBankStatementDAO(userId);
			BankStatementDataM bankStatementM = (BankStatementDataM) incomeCategoryM;
			statementDAO.createOrigBankStatementM(bankStatementM);
		}
		else if(incomeCategoryM instanceof SavingAccountDataM) {
			OrigSavingAccountDAO savAcctDAO = ORIGDAOFactory.getSavingAccountDAO(userId);
			SavingAccountDataM savAcctM = (SavingAccountDataM) incomeCategoryM;
			savAcctDAO.createOrigSavingAccountM(savAcctM);
		}
		else if(incomeCategoryM instanceof TaweesapDataM) {
			OrigTaweesapDAO taweesapDAO = ORIGDAOFactory.getTaweesapDAO(userId);
			TaweesapDataM taweesapM = (TaweesapDataM) incomeCategoryM;
			taweesapDAO.createOrigTaweesapM(taweesapM);
		}
		else if(incomeCategoryM instanceof YearlyTawi50DataM) {
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO(userId);
			YearlyTawi50DataM yearlyTawiM = (YearlyTawi50DataM) incomeCategoryM;
			yearlyTawiDAO.createOrigYearlyTawiM(yearlyTawiM);
		}
		else if(incomeCategoryM instanceof OtherIncomeDataM) {
			OrigOtherIncomeDAO OtherIncomeDAO = ORIGDAOFactory.getOtherIncomeDAO(userId);
			OtherIncomeDataM othIncomeM = (OtherIncomeDataM) incomeCategoryM;
			OtherIncomeDAO.createOrigOtherIncomeM(othIncomeM);
		}
		
		

	}

	@Override
	public void deleteOrigIncomeCategory(String incomeId, String incomeType) 
			throws ApplicationException {
		String className = IncomeTypeUtility.getClassNameByIncomeType(incomeType);
		if(ClosedEndFundDataM.class.getName().equals(className)) {
			OrigClosedEndFundDAO clsFundDAO = ORIGDAOFactory.getClosedEndFundDAO();
			clsFundDAO.deleteOrigClosedEndFundM(incomeId, null);
		}
		else if(FinancialInstrumentDataM.class.getName().equals(className)) {
			OrigFinInstrumentDAO finInstrumentDAO = ORIGDAOFactory.getFinInstrumentDAO();
			finInstrumentDAO.deleteOrigFinInstrumentM(incomeId, null);
		}
		else if(FixedAccountDataM.class.getName().equals(className)) {
			OrigFixedAccountDAO fixAcctDAO = ORIGDAOFactory.getFixedAccountDAO();
			fixAcctDAO.deleteOrigFixedAccountM(incomeId, null);
		}
		else if(FixedGuaranteeDataM.class.getName().equals(className)) {
			OrigFixedGuaranteeDAO fixGuaranteeDAO = ORIGDAOFactory.getFixedGuaranteeDAO();
			fixGuaranteeDAO.deleteOrigFixedGuaranteeM(incomeId, null);
		}
		else if(KVIIncomeDataM.class.getName().equals(className)) {
			OrigKVIIncomeDAO kviDAO = ORIGDAOFactory.getKVIIncomeDAO();
			kviDAO.deleteOrigKVIIncomeM(incomeId, null);
		}
		else if(MonthlyTawi50DataM.class.getName().equals(className)) {
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO();
			monthlyDAO.deleteOrigMonthlyTawiM(incomeId, null);
			
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO();
			yearlyTawiDAO.deleteOrigYearlyTawiM(incomeId, null);
		}
		else if(OpenedEndFundDataM.class.getName().equals(className)) {
			OrigOpenEndFundDAO opnEndFundDAO = ORIGDAOFactory.getOpenEndFundDAO();
			opnEndFundDAO.deleteOrigOpenEndFundM(incomeId, null);
		}
		else if(PayrollDataM.class.getName().equals(className)) {
			OrigPayrollDAO payrollDAO = ORIGDAOFactory.getPayrollDAO();
			payrollDAO.deleteOrigPayrollM(incomeId, null);
		}
		else if(PayrollFileDataM.class.getName().equals(className)) {
			OrigPayrollFileDAO payrollFileDAO = ORIGDAOFactory.getPayrollFileDAO();
			payrollFileDAO.deleteOrigPayrollFileM(incomeId, null);
		}
		else if(PayslipDataM.class.getName().equals(className)) {
			OrigPayslipDAO payslipDAO = ORIGDAOFactory.getPayslipDAO();
			payslipDAO.deleteOrigPayslipM(incomeId, null);
		}
		else if(PreviousIncomeDataM.class.getName().equals(className)) {
			OrigPreviousIncomeDAO prevIncomeDAO = ORIGDAOFactory.getPreviousIncomeDAO();
			prevIncomeDAO.deleteOrigPreviousIncomeM(incomeId, null);
		}
		else if(SalaryCertDataM.class.getName().equals(className)) {
			OrigSalaryCertDAO salaryCertDAO = ORIGDAOFactory.getSalaryCertDAO();
			salaryCertDAO.deleteOrigSalaryCertM(incomeId, null);
		}
		else if(SavingAccountDataM.class.getName().equals(className)) {
			OrigSavingAccountDAO savAcctDAO = ORIGDAOFactory.getSavingAccountDAO();
			savAcctDAO.deleteOrigSavingAccountM(incomeId, null);
		}
		else if(TaweesapDataM.class.getName().equals(className)) {
			OrigTaweesapDAO taweesapDAO = ORIGDAOFactory.getTaweesapDAO();
			taweesapDAO.deleteOrigTaweesapM(incomeId, null);
		}
		else if(YearlyTawi50DataM.class.getName().equals(className)) {
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO();
			monthlyDAO.deleteOrigMonthlyTawiM(incomeId, null);
			
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO();
			yearlyTawiDAO.deleteOrigYearlyTawiM(incomeId, null);
		}
		else if(BankStatementDataM.class.getName().equals(className)) {
			OrigBankStatementDAO statementDAO = ORIGDAOFactory.getBankStatementDAO();
			statementDAO.deleteOrigBankStatementM(incomeId, null);
		}
		else if(OtherIncomeDataM.class.getName().equals(className)) {
			OrigOtherIncomeDAO otherIncomeDAO = ORIGDAOFactory.getOtherIncomeDAO();
			otherIncomeDAO.deleteOrigOtherIncomeM(incomeId, null);
		}
	}
	@Override
	public ArrayList<IncomeCategoryDataM> loadOrigIncomeCategory(
			String incomeId, String incomeType, Connection conn)
			throws ApplicationException {
		if(Util.empty(incomeId) || Util.empty(incomeType)) {
			return null;
		}
		
		ArrayList<IncomeCategoryDataM> allIncomeList = new ArrayList<IncomeCategoryDataM>();
		String className = IncomeTypeUtility.getClassNameByIncomeType(incomeType);
		
		if(ClosedEndFundDataM.class.getName().equals(className)) {
			OrigClosedEndFundDAO clsFundDAO = ORIGDAOFactory.getClosedEndFundDAO();
			ArrayList<ClosedEndFundDataM> clsEndList = clsFundDAO.loadOrigClosedEndFundM(incomeId, incomeType,conn);
			if(!Util.empty(clsEndList)) {
				allIncomeList.addAll(clsEndList);
			}
		}
		else if(FinancialInstrumentDataM.class.getName().equals(className)) {
			OrigFinInstrumentDAO finInstrumentDAO = ORIGDAOFactory.getFinInstrumentDAO();
			ArrayList<FinancialInstrumentDataM> finInstrumentList = finInstrumentDAO.loadOrigFinInstrumentM(incomeId, incomeType,conn);
			if(!Util.empty(finInstrumentList)) {
				allIncomeList.addAll(finInstrumentList);
			}
		}
		else if(FixedAccountDataM.class.getName().equals(className)) {
			OrigFixedAccountDAO fixAcctDAO = ORIGDAOFactory.getFixedAccountDAO();
			ArrayList<FixedAccountDataM> fixAcctList = fixAcctDAO.loadOrigFixedAccountM(incomeId, incomeType,conn);
			if(!Util.empty(fixAcctList)) {
				allIncomeList.addAll(fixAcctList);
			}
		}
		else if(FixedGuaranteeDataM.class.getName().equals(className)) {
			OrigFixedGuaranteeDAO fixGuaranteeDAO = ORIGDAOFactory.getFixedGuaranteeDAO();
			ArrayList<FixedGuaranteeDataM> fixGuaranteeList = fixGuaranteeDAO.loadOrigFixedGuaranteeM(incomeId, incomeType,conn);
			if(!Util.empty(fixGuaranteeList)) {
				allIncomeList.addAll(fixGuaranteeList);
			}
		}
		else if(KVIIncomeDataM.class.getName().equals(className)) {
			OrigKVIIncomeDAO kviDAO = ORIGDAOFactory.getKVIIncomeDAO();
			ArrayList<KVIIncomeDataM> kviList = kviDAO.loadOrigKVIIncomeM(incomeId, incomeType,conn);
			if(!Util.empty(kviList)) {
				allIncomeList.addAll(kviList);
			}
		}
		else if(MonthlyTawi50DataM.class.getName().equals(className)) {
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO();
			ArrayList<MonthlyTawi50DataM> monthlyTawiList = monthlyDAO.loadOrigMonthlyTawiM(incomeId,incomeType,conn);
			if(!Util.empty(monthlyTawiList)) {
				allIncomeList.addAll(monthlyTawiList);
			}
			
			String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO();
			ArrayList<YearlyTawi50DataM> yearlyList = yearlyTawiDAO.loadOrigYearlyTawiM(incomeId,INC_TYPE_YEARLY_50TAWI,conn);
			if(!Util.empty(yearlyList)) {
				allIncomeList.addAll(yearlyList);
			}
		}
		else if(OpenedEndFundDataM.class.getName().equals(className)) {
			OrigOpenEndFundDAO opnEndFundDAO = ORIGDAOFactory.getOpenEndFundDAO();
			ArrayList<OpenedEndFundDataM> opnEndFundList = opnEndFundDAO.loadOrigOpenEndFundM(incomeId, incomeType,conn);
			if(!Util.empty(opnEndFundList)) {
				allIncomeList.addAll(opnEndFundList);
			}
		}
		else if(PayrollDataM.class.getName().equals(className)) {
			OrigPayrollDAO payrollDAO = ORIGDAOFactory.getPayrollDAO();
			ArrayList<PayrollDataM> payrollList = payrollDAO.loadOrigPayrollM(incomeId, incomeType,conn);
			if(!Util.empty(payrollList)) {
				allIncomeList.addAll(payrollList);
			}
		}
		else if(PayrollFileDataM.class.getName().equals(className)) {
			OrigPayrollFileDAO payrollFileDAO = ORIGDAOFactory.getPayrollFileDAO();
			ArrayList<PayrollFileDataM> payrollFileList = payrollFileDAO.loadOrigPayrollFileM(incomeId, incomeType,conn);
			if(!Util.empty(payrollFileList)) {
				allIncomeList.addAll(payrollFileList);
			}
		}
		else if(PayslipDataM.class.getName().equals(className)) {
			OrigPayslipDAO payslipDAO = ORIGDAOFactory.getPayslipDAO();
			ArrayList<PayslipDataM> payslipList = payslipDAO.loadOrigPayslipM(incomeId, incomeType,conn);
			if(!Util.empty(payslipList)) {
				allIncomeList.addAll(payslipList);
			}
		}
		else if(PreviousIncomeDataM.class.getName().equals(className)) {
			OrigPreviousIncomeDAO prevIncomeDAO = ORIGDAOFactory.getPreviousIncomeDAO();
			ArrayList<PreviousIncomeDataM> prevIncomeList = prevIncomeDAO.loadOrigPreviousIncomeM(incomeId, incomeType,conn);
			if(!Util.empty(prevIncomeList)) {
				allIncomeList.addAll(prevIncomeList);
			}
		}
		else if(SalaryCertDataM.class.getName().equals(className)) {
			OrigSalaryCertDAO salaryCertDAO = ORIGDAOFactory.getSalaryCertDAO();
			ArrayList<SalaryCertDataM> salaryCertList = salaryCertDAO.loadOrigSalaryCertM(incomeId, incomeType,conn);
			if(!Util.empty(salaryCertList)) {
				allIncomeList.addAll(salaryCertList);
			}
		}
		else if(BankStatementDataM.class.getName().equals(className)) {
			OrigBankStatementDAO statementDAO = ORIGDAOFactory.getBankStatementDAO();
			ArrayList<BankStatementDataM> statementList = statementDAO.loadOrigBankStatementM(incomeId, incomeType,conn);
			if(!Util.empty(statementList)) {
				allIncomeList.addAll(statementList);					
			}
		}
		else if(SavingAccountDataM.class.getName().equals(className)) {
			OrigSavingAccountDAO savAcctDAO = ORIGDAOFactory.getSavingAccountDAO();
			ArrayList<SavingAccountDataM> savAcctList = savAcctDAO.loadOrigSavingAccountM(incomeId, incomeType,conn);
			if(!Util.empty(savAcctList)) {
				allIncomeList.addAll(savAcctList);
			}
		}
		else if(TaweesapDataM.class.getName().equals(className)) {
			OrigTaweesapDAO taweesapDAO = ORIGDAOFactory.getTaweesapDAO();
			ArrayList<TaweesapDataM> taweesapList = taweesapDAO.loadOrigTaweesapM(incomeId, incomeType,conn);
			if(!Util.empty(taweesapList)) {
				allIncomeList.addAll(taweesapList);
			}
		}
		else if(YearlyTawi50DataM.class.getName().equals(className)) {
			String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO();
			ArrayList<MonthlyTawi50DataM> monthlyTawiList = monthlyDAO.loadOrigMonthlyTawiM(incomeId, INC_TYPE_MONTHLY_50TAWI,conn);
			if(!Util.empty(monthlyTawiList)) {
				allIncomeList.addAll(monthlyTawiList);
			}
			
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO();
			ArrayList<YearlyTawi50DataM> yearlyList = yearlyTawiDAO.loadOrigYearlyTawiM(incomeId, incomeType,conn);
			if(!Util.empty(yearlyList)) {
				allIncomeList.addAll(yearlyList);
			}
		}
		else if(OtherIncomeDataM.class.getName().equals(className)) {
			OrigOtherIncomeDAO otherIncomeDAO = ORIGDAOFactory.getOtherIncomeDAO();
			ArrayList<OtherIncomeDataM> otherIncomeList= otherIncomeDAO.loadOrigOtherIncomeM(incomeId,conn);
			if(!Util.empty(otherIncomeList)) {
				allIncomeList.addAll(otherIncomeList);
			}
		}
		
		
		
		//Reset Seq for all
		if(!Util.empty(allIncomeList)) {
			int count = 0;
			for(IncomeCategoryDataM incomeCateg : allIncomeList) {
				incomeCateg.setSeq(++count);
			}
		}
		return allIncomeList;
	}
	@Override
	public ArrayList<IncomeCategoryDataM> loadOrigIncomeCategory(String incomeId, String incomeType)
			throws ApplicationException {
		if(Util.empty(incomeId) || Util.empty(incomeType)) {
			return null;
		}
		
		ArrayList<IncomeCategoryDataM> allIncomeList = new ArrayList<IncomeCategoryDataM>();
		String className = IncomeTypeUtility.getClassNameByIncomeType(incomeType);
		
		if(ClosedEndFundDataM.class.getName().equals(className)) {
			OrigClosedEndFundDAO clsFundDAO = ORIGDAOFactory.getClosedEndFundDAO();
			ArrayList<ClosedEndFundDataM> clsEndList = clsFundDAO.loadOrigClosedEndFundM(incomeId, incomeType);
			if(!Util.empty(clsEndList)) {
				allIncomeList.addAll(clsEndList);
			}
		}
		else if(FinancialInstrumentDataM.class.getName().equals(className)) {
			OrigFinInstrumentDAO finInstrumentDAO = ORIGDAOFactory.getFinInstrumentDAO();
			ArrayList<FinancialInstrumentDataM> finInstrumentList = finInstrumentDAO.loadOrigFinInstrumentM(incomeId, incomeType);
			if(!Util.empty(finInstrumentList)) {
				allIncomeList.addAll(finInstrumentList);
			}
		}
		else if(FixedAccountDataM.class.getName().equals(className)) {
			OrigFixedAccountDAO fixAcctDAO = ORIGDAOFactory.getFixedAccountDAO();
			ArrayList<FixedAccountDataM> fixAcctList = fixAcctDAO.loadOrigFixedAccountM(incomeId, incomeType);
			if(!Util.empty(fixAcctList)) {
				allIncomeList.addAll(fixAcctList);
			}
		}
		else if(FixedGuaranteeDataM.class.getName().equals(className)) {
			OrigFixedGuaranteeDAO fixGuaranteeDAO = ORIGDAOFactory.getFixedGuaranteeDAO();
			ArrayList<FixedGuaranteeDataM> fixGuaranteeList = fixGuaranteeDAO.loadOrigFixedGuaranteeM(incomeId, incomeType);
			if(!Util.empty(fixGuaranteeList)) {
				allIncomeList.addAll(fixGuaranteeList);
			}
		}
		else if(KVIIncomeDataM.class.getName().equals(className)) {
			OrigKVIIncomeDAO kviDAO = ORIGDAOFactory.getKVIIncomeDAO();
			ArrayList<KVIIncomeDataM> kviList = kviDAO.loadOrigKVIIncomeM(incomeId, incomeType);
			if(!Util.empty(kviList)) {
				allIncomeList.addAll(kviList);
			}
		}
		else if(MonthlyTawi50DataM.class.getName().equals(className)) {
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO();
			ArrayList<MonthlyTawi50DataM> monthlyTawiList = monthlyDAO.loadOrigMonthlyTawiM(incomeId, incomeType);
			if(!Util.empty(monthlyTawiList)) {
				allIncomeList.addAll(monthlyTawiList);
			}
			
			String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO();
			ArrayList<YearlyTawi50DataM> yearlyList = yearlyTawiDAO.loadOrigYearlyTawiM(incomeId, INC_TYPE_YEARLY_50TAWI);
			if(!Util.empty(yearlyList)) {
				allIncomeList.addAll(yearlyList);
			}
		}
		else if(OpenedEndFundDataM.class.getName().equals(className)) {
			OrigOpenEndFundDAO opnEndFundDAO = ORIGDAOFactory.getOpenEndFundDAO();
			ArrayList<OpenedEndFundDataM> opnEndFundList = opnEndFundDAO.loadOrigOpenEndFundM(incomeId, incomeType);
			if(!Util.empty(opnEndFundList)) {
				allIncomeList.addAll(opnEndFundList);
			}
		}
		else if(PayrollDataM.class.getName().equals(className)) {
			OrigPayrollDAO payrollDAO = ORIGDAOFactory.getPayrollDAO();
			ArrayList<PayrollDataM> payrollList = payrollDAO.loadOrigPayrollM(incomeId, incomeType);
			if(!Util.empty(payrollList)) {
				allIncomeList.addAll(payrollList);
			}
		}
		else if(PayrollFileDataM.class.getName().equals(className)) {
			OrigPayrollFileDAO payrollFileDAO = ORIGDAOFactory.getPayrollFileDAO();
			ArrayList<PayrollFileDataM> payrollFileList = payrollFileDAO.loadOrigPayrollFileM(incomeId, incomeType);
			if(!Util.empty(payrollFileList)) {
				allIncomeList.addAll(payrollFileList);
			}
		}
		else if(PayslipDataM.class.getName().equals(className)) {
			OrigPayslipDAO payslipDAO = ORIGDAOFactory.getPayslipDAO();
			ArrayList<PayslipDataM> payslipList = payslipDAO.loadOrigPayslipM(incomeId, incomeType);
			if(!Util.empty(payslipList)) {
				allIncomeList.addAll(payslipList);
			}
		}
		else if(PreviousIncomeDataM.class.getName().equals(className)) {
			OrigPreviousIncomeDAO prevIncomeDAO = ORIGDAOFactory.getPreviousIncomeDAO();
			ArrayList<PreviousIncomeDataM> prevIncomeList = prevIncomeDAO.loadOrigPreviousIncomeM(incomeId, incomeType);
			if(!Util.empty(prevIncomeList)) {
				allIncomeList.addAll(prevIncomeList);
			}
		}
		else if(SalaryCertDataM.class.getName().equals(className)) {
			OrigSalaryCertDAO salaryCertDAO = ORIGDAOFactory.getSalaryCertDAO();
			ArrayList<SalaryCertDataM> salaryCertList = salaryCertDAO.loadOrigSalaryCertM(incomeId, incomeType);
			if(!Util.empty(salaryCertList)) {
				allIncomeList.addAll(salaryCertList);
			}
		}
		else if(BankStatementDataM.class.getName().equals(className)) {
			OrigBankStatementDAO statementDAO = ORIGDAOFactory.getBankStatementDAO();
			ArrayList<BankStatementDataM> statementList = statementDAO.loadOrigBankStatementM(incomeId, incomeType);
			if(!Util.empty(statementList)) {
				allIncomeList.addAll(statementList);					
			}
		}
		else if(SavingAccountDataM.class.getName().equals(className)) {
			OrigSavingAccountDAO savAcctDAO = ORIGDAOFactory.getSavingAccountDAO();
			ArrayList<SavingAccountDataM> savAcctList = savAcctDAO.loadOrigSavingAccountM(incomeId, incomeType);
			if(!Util.empty(savAcctList)) {
				allIncomeList.addAll(savAcctList);
			}
		}
		else if(TaweesapDataM.class.getName().equals(className)) {
			OrigTaweesapDAO taweesapDAO = ORIGDAOFactory.getTaweesapDAO();
			ArrayList<TaweesapDataM> taweesapList = taweesapDAO.loadOrigTaweesapM(incomeId, incomeType);
			if(!Util.empty(taweesapList)) {
				allIncomeList.addAll(taweesapList);
			}
		}
		else if(YearlyTawi50DataM.class.getName().equals(className)) {
			String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO();
			ArrayList<MonthlyTawi50DataM> monthlyTawiList = monthlyDAO.loadOrigMonthlyTawiM(incomeId, INC_TYPE_MONTHLY_50TAWI);
			if(!Util.empty(monthlyTawiList)) {
				allIncomeList.addAll(monthlyTawiList);
			}
			
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO();
			ArrayList<YearlyTawi50DataM> yearlyList = yearlyTawiDAO.loadOrigYearlyTawiM(incomeId, incomeType);
			if(!Util.empty(yearlyList)) {
				allIncomeList.addAll(yearlyList);
			}
		}
		else if(OtherIncomeDataM.class.getName().equals(className)) {
			OrigOtherIncomeDAO otherIncomeDAO = ORIGDAOFactory.getOtherIncomeDAO();
			ArrayList<OtherIncomeDataM> otherIncomeList=	otherIncomeDAO.loadOrigOtherIncomeM(incomeId);
			if(!Util.empty(otherIncomeList)) {
				allIncomeList.addAll(otherIncomeList);
			}
		}
		
		
		
		//Reset Seq for all
		if(!Util.empty(allIncomeList)) {
			int count = 0;
			for(IncomeCategoryDataM incomeCateg : allIncomeList) {
				incomeCateg.setSeq(++count);
			}
		}
		return allIncomeList;
	}

	@Override
	public void saveUpdateOrigIncomeCategory(IncomeCategoryDataM incomeCategoryM)
			throws ApplicationException {
		
		if(incomeCategoryM instanceof ClosedEndFundDataM) {
			ClosedEndFundDataM clsEndFundM = (ClosedEndFundDataM) incomeCategoryM;
			OrigClosedEndFundDAO clsFundDAO = ORIGDAOFactory.getClosedEndFundDAO(userId);
			clsFundDAO.saveUpdateOrigClosedEndFundM(clsEndFundM);
		}
		else if(incomeCategoryM instanceof FinancialInstrumentDataM) {
			OrigFinInstrumentDAO finInstrumentDAO = ORIGDAOFactory.getFinInstrumentDAO(userId);
			FinancialInstrumentDataM finInstrumentM =(FinancialInstrumentDataM) incomeCategoryM;
			finInstrumentDAO.saveUpdateOrigFinInstrumentM(finInstrumentM);
		}
		else if(incomeCategoryM instanceof FixedAccountDataM) {
			OrigFixedAccountDAO fixAcctDAO = ORIGDAOFactory.getFixedAccountDAO(userId);
			FixedAccountDataM fixAcctM = (FixedAccountDataM) incomeCategoryM;
			fixAcctDAO.saveUpdateOrigFixedAccountM(fixAcctM);
		}
		else if(incomeCategoryM instanceof FixedGuaranteeDataM) {
			OrigFixedGuaranteeDAO fixGuaranteeDAO = ORIGDAOFactory.getFixedGuaranteeDAO(userId);
			FixedGuaranteeDataM fixGuaranteeM = (FixedGuaranteeDataM)incomeCategoryM;
			fixGuaranteeDAO.saveUpdateOrigFixedGuaranteeM(fixGuaranteeM);
		}
		else if(incomeCategoryM instanceof KVIIncomeDataM) {
			OrigKVIIncomeDAO kviDAO = ORIGDAOFactory.getKVIIncomeDAO(userId);
			KVIIncomeDataM kviIncomeM = (KVIIncomeDataM) incomeCategoryM;
			kviDAO.saveUpdateOrigKVIIncomeM(kviIncomeM);
		}
		else if(incomeCategoryM instanceof MonthlyTawi50DataM) {
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO(userId);
			MonthlyTawi50DataM monthlyTawiM = (MonthlyTawi50DataM) incomeCategoryM;
			monthlyDAO.saveUpdateOrigMonthlyTawiM(monthlyTawiM);
		}
		else if(incomeCategoryM instanceof OpenedEndFundDataM) {
			OrigOpenEndFundDAO opnEndFundDAO = ORIGDAOFactory.getOpenEndFundDAO(userId);
			OpenedEndFundDataM opnEndFundM = (OpenedEndFundDataM) incomeCategoryM;
			opnEndFundDAO.saveUpdateOrigOpenEndFundM(opnEndFundM);
		}
		else if(incomeCategoryM instanceof PayrollDataM) {
			OrigPayrollDAO payrollDAO = ORIGDAOFactory.getPayrollDAO(userId);
			PayrollDataM payrollM = (PayrollDataM) incomeCategoryM;
			payrollDAO.saveUpdateOrigPayrollM(payrollM);
		}
		else if(incomeCategoryM instanceof PayrollFileDataM) {
			OrigPayrollFileDAO payrollFileDAO = ORIGDAOFactory.getPayrollFileDAO(userId);
			PayrollFileDataM payrollFileM = (PayrollFileDataM) incomeCategoryM;
			payrollFileDAO.saveUpdateOrigPayrollFileM(payrollFileM);
		}
		else if(incomeCategoryM instanceof PayslipDataM) {
			OrigPayslipDAO payslipDAO = ORIGDAOFactory.getPayslipDAO(userId);
			PayslipDataM payslipM = (PayslipDataM) incomeCategoryM;
			payslipDAO.saveUpdateOrigPayslipM(payslipM);
		}
		else if(incomeCategoryM instanceof PreviousIncomeDataM) {
			OrigPreviousIncomeDAO prevIncomeDAO = ORIGDAOFactory.getPreviousIncomeDAO(userId);
			PreviousIncomeDataM prevIncomeM = (PreviousIncomeDataM) incomeCategoryM;
			prevIncomeDAO.saveUpdateOrigPreviousIncomeM(prevIncomeM);
		}
		else if(incomeCategoryM instanceof SalaryCertDataM) {
			OrigSalaryCertDAO salaryCertDAO = ORIGDAOFactory.getSalaryCertDAO(userId);
			SalaryCertDataM salaryCertM = (SalaryCertDataM) incomeCategoryM;
			salaryCertDAO.saveUpdateOrigSalaryCertM(salaryCertM);
		}
		else if(incomeCategoryM instanceof BankStatementDataM) {
			OrigBankStatementDAO statementDAO = ORIGDAOFactory.getBankStatementDAO(userId);
			BankStatementDataM bankStatementM = (BankStatementDataM) incomeCategoryM;
			statementDAO.saveUpdateOrigBankStatementM(bankStatementM);
		}
		else if(incomeCategoryM instanceof SavingAccountDataM) {
			OrigSavingAccountDAO savAcctDAO = ORIGDAOFactory.getSavingAccountDAO(userId);
			SavingAccountDataM savAcctM = (SavingAccountDataM) incomeCategoryM;
			savAcctDAO.saveUpdateOrigSavingAccountM(savAcctM);
		}
		else if(incomeCategoryM instanceof TaweesapDataM) {
			OrigTaweesapDAO taweesapDAO = ORIGDAOFactory.getTaweesapDAO(userId);
			TaweesapDataM taweesapM = (TaweesapDataM) incomeCategoryM;
			taweesapDAO.saveUpdateOrigTaweesapM(taweesapM);
		}
		else if(incomeCategoryM instanceof YearlyTawi50DataM) {
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO(userId);
			YearlyTawi50DataM yearlyTawiM = (YearlyTawi50DataM) incomeCategoryM;
			yearlyTawiDAO.saveUpdateOrigYearlyTawiM(yearlyTawiM);
		}
		else if(incomeCategoryM instanceof OtherIncomeDataM) {
			OrigOtherIncomeDAO otherIncomeDAO = ORIGDAOFactory.getOtherIncomeDAO(userId);
			OtherIncomeDataM otherIncomeM = (OtherIncomeDataM) incomeCategoryM;
			otherIncomeDAO.saveUpdateOrigOtherIncomeM(otherIncomeM);
		}
		
		
		
	}

	@Override
	public void deleteNotInKeyIncomeCategory(IncomeInfoDataM incomeDataM)
			throws ApplicationException {
		String incomeType = incomeDataM.getIncomeType();
		String className = IncomeTypeUtility.getClassNameByIncomeType(incomeType);
		ArrayList<IncomeCategoryDataM> incomeCategoryList = incomeDataM.getAllIncomes();
		if(ClosedEndFundDataM.class.getName().equals(className)) {
			//Close End Fund
			OrigClosedEndFundDAO clsFundDAO = ORIGDAOFactory.getClosedEndFundDAO();
			clsFundDAO.deleteNotInKeyClosedEndFund(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(FinancialInstrumentDataM.class.getName().equals(className)) {
			//FinInstrument 
			OrigFinInstrumentDAO finInstrumentDAO = ORIGDAOFactory.getFinInstrumentDAO();
			finInstrumentDAO.deleteNotInKeyFinInstrument(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(FixedAccountDataM.class.getName().equals(className)) {
			//Fixed Account
			OrigFixedAccountDAO fixAcctDAO = ORIGDAOFactory.getFixedAccountDAO();
			fixAcctDAO.deleteNotInKeyFixedAccount(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(FixedGuaranteeDataM.class.getName().equals(className)) {
			// Fixed Guarantee
			OrigFixedGuaranteeDAO fixGuaranteeDAO = ORIGDAOFactory.getFixedGuaranteeDAO();
			fixGuaranteeDAO.deleteNotInKeyFixedGuarantee(incomeCategoryList, incomeDataM.getIncomeId());
		}	
		else if(KVIIncomeDataM.class.getName().equals(className)) {
			// KVIIncome
			OrigKVIIncomeDAO kviDAO = ORIGDAOFactory.getKVIIncomeDAO();
			kviDAO.deleteNotInKeyKVIIncome(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(MonthlyTawi50DataM.class.getName().equals(className)) {
			ArrayList<IncomeCategoryDataM> monthlyList = incomeDataM.getAllIncomeCategoryByType(incomeType);
			// Monthly Tawi
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO();
			monthlyDAO.deleteNotInKeyMonthlyTawi(monthlyList, incomeDataM.getIncomeId());
			
			String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
			ArrayList<IncomeCategoryDataM> yearlyList = incomeDataM.getAllIncomeCategoryByType(INC_TYPE_YEARLY_50TAWI);
			//Yearly 50Tawi
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO();
			yearlyTawiDAO.deleteNotInKeyYearlyTawi(yearlyList, incomeDataM.getIncomeId());
		}
		else if(OpenedEndFundDataM.class.getName().equals(className)) {
			// OpenEnd Fund
			OrigOpenEndFundDAO opnEndFundDAO = ORIGDAOFactory.getOpenEndFundDAO();
			opnEndFundDAO.deleteNotInKeyOpenEndFund(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(PayrollDataM.class.getName().equals(className)) {
			//Payroll
			OrigPayrollDAO payrollDAO = ORIGDAOFactory.getPayrollDAO();
			payrollDAO.deleteNotInKeyPayroll(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(PayrollFileDataM.class.getName().equals(className)) {
			//Payroll
			OrigPayrollFileDAO payrollFileDAO = ORIGDAOFactory.getPayrollFileDAO();
			payrollFileDAO.deleteNotInKeyPayrollFile(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(PayslipDataM.class.getName().equals(className)) {
			//Payslip
			OrigPayslipDAO payslipDAO = ORIGDAOFactory.getPayslipDAO();
			payslipDAO.deleteNotInKeyPayslip(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(PreviousIncomeDataM.class.getName().equals(className)) {
			//Previous Income
			OrigPreviousIncomeDAO prevIncomeDAO = ORIGDAOFactory.getPreviousIncomeDAO();
			prevIncomeDAO.deleteNotInKeyPreviousIncome(incomeCategoryList, incomeDataM.getIncomeId());
		}	
		else if(SalaryCertDataM.class.getName().equals(className)) {
			// Salary Cert
			OrigSalaryCertDAO salaryCertDAO = ORIGDAOFactory.getSalaryCertDAO();
			salaryCertDAO.deleteNotInKeySalaryCert(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(BankStatementDataM.class.getName().equals(className)) {
			//Bank Statement
			OrigBankStatementDAO statementDAO = ORIGDAOFactory.getBankStatementDAO();
			statementDAO.deleteNotInKeyBankStatement(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(SavingAccountDataM.class.getName().equals(className)) {
			//Saving Account
			OrigSavingAccountDAO savAcctDAO = ORIGDAOFactory.getSavingAccountDAO();
			savAcctDAO.deleteNotInKeySavingAccount(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(TaweesapDataM.class.getName().equals(className)) {
			//Taweesap
			OrigTaweesapDAO taweesapDAO = ORIGDAOFactory.getTaweesapDAO();
			taweesapDAO.deleteNotInKeyTaweesap(incomeCategoryList, incomeDataM.getIncomeId());
		}
		else if(YearlyTawi50DataM.class.getName().equals(className)) {
			String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
			ArrayList<IncomeCategoryDataM> monthlyList = incomeDataM.getAllIncomeCategoryByType(INC_TYPE_MONTHLY_50TAWI);
			// Monthly Tawi
			OrigMonthlyTawiDAO monthlyDAO = ORIGDAOFactory.getMonthlyTawiDAO();
			monthlyDAO.deleteNotInKeyMonthlyTawi(monthlyList, incomeDataM.getIncomeId());
			
			ArrayList<IncomeCategoryDataM> yearlyList = incomeDataM.getAllIncomeCategoryByType(incomeType);
			//Yearly 50Tawi
			OrigYearlyTawiDAO yearlyTawiDAO = ORIGDAOFactory.getYearlyTawiDAO();
			yearlyTawiDAO.deleteNotInKeyYearlyTawi(yearlyList, incomeDataM.getIncomeId());
		}
		else if(OtherIncomeDataM.class.getName().equals(className)) {
			//Other Income
			OrigOtherIncomeDAO otherIncomeDAO = ORIGDAOFactory.getOtherIncomeDAO();
			ArrayList<OtherIncomeDataM> otherIncomeList = new ArrayList<OtherIncomeDataM>();
			if(!Util.empty(incomeCategoryList)){
				for(IncomeCategoryDataM incomeCategoryItem:incomeCategoryList){
					if(incomeCategoryItem instanceof OtherIncomeDataM){
						otherIncomeList.add((OtherIncomeDataM)incomeCategoryItem);
					}
				}
			}
			otherIncomeDAO.deleteNotInKeyOtherIncome( otherIncomeList, incomeDataM.getIncomeId());		
			
		}	
		
	}

}
