package com.eaf.orig.ulo.pa;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;

@SuppressWarnings("serial")
public class InstructionMemoPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(InstructionMemoPopupForm.class);
	private String subformId = "INSTRUCTION_MEMO_POPUP";	

	@Override
	public Object getObjectForm() throws Exception
	{
		return super.getObjectForm();
	}
	
	@Override
	public String processForm() 
	{
		return super.processForm();
	}
	
}
