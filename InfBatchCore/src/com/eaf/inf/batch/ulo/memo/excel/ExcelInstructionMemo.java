package com.eaf.inf.batch.ulo.memo.excel;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.excel.ExcelMapper;
import com.eaf.core.ulo.common.excel.MySheet;
import com.eaf.inf.batch.ulo.memo.model.DebitInterestDataM;
import com.eaf.inf.batch.ulo.memo.model.InstructionMemoDataM;

public class ExcelInstructionMemo extends ExcelMapper {
	private InstructionMemoDataM instructionMemo = null;
	public ExcelInstructionMemo(String inputFilePath, String outputFilePath,InstructionMemoDataM instructionMemo) throws FileNotFoundException {
		super(inputFilePath, outputFilePath);
		this.instructionMemo = instructionMemo;
	}

	@Override
	protected void processAction() throws Exception{
		super.processAction();
		MySheet sheet = new MySheet(workbook.getSheetAt(0));
		// 1
		sheet.setCellValue(6, "F", instructionMemo.getApplicationGroupNo());
		// 2
		sheet.setCellValue(8, "F", instructionMemo.getDearTo());
		// 3
		sheet.setCellValue(10, "AD",Formatter.display(InfBatchProperty.getDate(), Formatter.TH, Formatter.Format.ddMMyyyy));
		// 4
		sheet.setCursor(15, "V").setCellsByPattern(instructionMemo.getTariffCode(),"_,_,-,_,_,-,_,_,_,_,-,_,_,_");
		// 5
		sheet.setCellValue(17, "F", instructionMemo.getProductName());
		// 5.1
		sheet.setCellValue(17, "V", Formatter.formatTh(instructionMemo.getApplyDate()));
		// 6
		sheet.setCursor(19, "I").setCellsByPattern(instructionMemo.getIdNo(),"_,-,_,_,_,_,-,_,_,_,_,_,-,_,_,-,_");
		// 7
		sheet.setCellValue(21, "F", instructionMemo.getOwnerName());
		// 7.1
		sheet.setCellValue(23, "J", instructionMemo.getAdministrativeBranch());
		// 8
		sheet.setCellValue(26, "J", instructionMemo.getAdministrativeBranchOrg());
		// 9
		sheet.setCellValue(29, "J", instructionMemo.getRateType());
		// 10
		sheet.setCellValue(29, "AA", instructionMemo.getSignSpread());
		// 11
		sheet.setCellValue(30, "J", instructionMemo.getPrimaryAccName());
		// 12
		sheet.setCellValue(30, "AA", instructionMemo.getSecondaryAccName());
		// 13
		sheet.setCellValue(32, "J", instructionMemo.getTerm());
		// 14
		sheet.setCursor(34, "J").setCellsByPattern(instructionMemo.getAccountNo(),"_,_,_,-,_,-,_,_,_,_,_,-,_");
		// 15
		sheet.setCellValue(39, "J", instructionMemo.getFinality());
		// 16
		sheet.setCellValue(39, "Z", instructionMemo.getCnaeCode());
		// 17
		sheet.setCellValue(41, "J", instructionMemo.getSpecialProject());
		// 18
		sheet.setCellValue(43, "J", Formatter.formatCurrency(instructionMemo.getLoanAmt()));
		// 19
		Date firstInstallmentDate = calcFirstInstallmentDate(instructionMemo.getFirstInstallmentDate());
		sheet.setCellValue(46, "J", getFirstInstallmentDate(firstInstallmentDate));
		// 20
		sheet.setCellValue(46, "Z", nextMonth(firstInstallmentDate));
		// 21
		sheet.setCellValue(49, "Z",Formatter.formatCurrency(instructionMemo.getMonthlyInstallment()));
		sheet.setCellValue(52, "Z",Formatter.formatCurrency(instructionMemo.getMonthlyInstallment()));
		sheet.setCellValue(54, "Z",Formatter.formatCurrency(instructionMemo.getMonthlyInstallment()));
		// 22
		logger.debug("FirstInterestDate : " + instructionMemo.getFirstInterestDate());
		sheet.setCellValue(57, "J", Formatter.getDay(instructionMemo.getFirstInterestDate()));
		// 23
		sheet.setCellValue(57, "Z", Formatter.getMonthName(instructionMemo.getFirstInterestDate()));

		// 24-27 List
		List<DebitInterestDataM> debitInterests = instructionMemo.getDebitInterests();
		if (null != debitInterests) {
			for (int i = 0, size = debitInterests.size(), row = 62; i < size; i++, row++) {
				DebitInterestDataM debitInterest = debitInterests.get(i);
				sheet.setCellValue(row, "L", debitInterest.getFixedInterestRate())
						.setCellValue(row, "U", debitInterest.getReferenceRate())
						.setCellValue(row, "AD", debitInterest.getSpreadRate());
			}
		}
		// 28 List
		List<String> feeAmounts = instructionMemo.getFinalFeeAmt();
		if (null != feeAmounts) {
			for (int i = 0, size = feeAmounts.size(), row = 69; i < size; i++, row++) {
				String feeAmount = feeAmounts.get(i);
				sheet.setCursor(row, "AA").setCellValue(feeAmount).down();
			}
		}
		// 29
		sheet.setCellValue(73, "I", Formatter.formatCurrency(instructionMemo.getDisbursementAmt()));
		// 30
		sheet.setCursor(78, "S").setCellsByPattern(instructionMemo.getAccountNo(),"_,_,_,-,_,-,_,_,_,_,_,-,_");
		// 31 formatCurrency
		List<BigDecimal> feeAmountOther = instructionMemo.getFinalFeeAmtOther();
		int index = 0;
		try {
			sheet.setCellValue(89, "K", Formatter.formatCurrency(feeAmountOther.get(index++)));
		} catch (IndexOutOfBoundsException ex) {
		}
		// 32
		try {
			sheet.setCellValue(90, "M", Formatter.formatCurrency(feeAmountOther.get(index++)));
		} catch (IndexOutOfBoundsException ex) {
		}
		// 33
		try {
			sheet.setCellValue(90, "Y", Formatter.formatCurrency(feeAmountOther.get(index++)));
		} catch (IndexOutOfBoundsException ex) {
		}
		// 34
		try {
			sheet.setCellValue(91, "K", Formatter.formatCurrency(feeAmountOther.get(index++)));
		} catch (IndexOutOfBoundsException ex) {
		}
		// 35
		try {
			sheet.setCellValue(92, "K", Formatter.formatCurrency(feeAmountOther.get(index++)));
		} catch (IndexOutOfBoundsException ex) {
		}

	}

	private static Date calcFirstInstallmentDate(Date firstInstallmentDate) {
		if (null == firstInstallmentDate) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(firstInstallmentDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day >= 1 && day <= 15) {
			cal.add(Calendar.MONTH, 1);
		} else {
			cal.add(Calendar.MONTH, 2);
		}
		cal.set(Calendar.DAY_OF_MONTH, 2);
		return cal.getTime();
	}
	private static String getFirstInstallmentDate(Date firstInstallmentDate) {
		if (null == firstInstallmentDate) {
			return null;
		}
		return new SimpleDateFormat("dd MMMM", Locale.US).format(firstInstallmentDate.getTime());
	}
	private static String nextMonth(Date date) {
		if (null == date) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat("MMMM", Locale.US).format(date.getTime());
	}
}
