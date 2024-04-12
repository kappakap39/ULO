package com.eaf.inf.batch.ulo.memo.excel;

import java.io.FileNotFoundException;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.excel.ExcelMapper;
import com.eaf.core.ulo.common.excel.MySheet;
import com.eaf.inf.batch.ulo.memo.model.InstructionMemoDataM;

public class ExcelInstructionMemoClose extends ExcelMapper {
    private InstructionMemoDataM instructionMemo = null;
    public ExcelInstructionMemoClose(String inputFilePath, String outputFilePath, InstructionMemoDataM instructionMemo) throws FileNotFoundException {
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
		sheet.setCellValue(8, "AD", Formatter.display(InfBatchProperty.getDate(), Formatter.TH, Formatter.Format.ddMMyyyy));
		// 4
		sheet.setCellValue(15, "J", instructionMemo.getIdNo());
		// 5
		sheet.setCellValue(17, "E", instructionMemo.getOwnerName());
		// 6
		sheet.setCellValue(17, "W", instructionMemo.getOldAccountNo());
		// 7
		sheet.setCellValue(28, "H", instructionMemo.getOldAccountNo());
		// 8
		sheet.setCellValue(28, "Y", Formatter.display(InfBatchProperty.getDate(), Formatter.TH, Formatter.Format.ddMMyyyy));
		// 9
		sheet.setCellValue(30, "Y", instructionMemo.getPaymentReason());
		// 10
		sheet.setCellValue(32, "Y", instructionMemo.getBlAccountNo());
		// 11
		// User input
    }
}
