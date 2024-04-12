function CALCULATE_BTNActionJS()
{
	var amountLoanField = parseFloat($("[name='AMOUNT_LOAN_KPL']").val().replace(',',''));
	var minCL = $("[name='minCL']").val();
	//alert('minCL = ' + minCL + ' amountLoanField = ' + amountLoanField );
	
	if(minCL > 0 && amountLoanField < minCL)
	{
	    var msg1 = eval('MSG_CONFIRM_AMOUNT_LOAN_LT_MIN_CL');
	    var msg2 = eval('MSG_CONFIRM_PROCEED');
	    confirmBox(msg1 + " " + minCL + "</br>" + msg2,function(choice) {
	    	if (choice == "Y") 
	    	{
	    		calculateAction();
	    	}
	    });
	}
	else
	{
	    calculateAction();
	}
}

function clearInstallInterestField()
{
	$("[name='INSTALLMENT_AMT_KPL']").val('');
	$("[name='INT_RATE_KPL']").val('');
}

function calculateAction()
{
	try{
		$("[name='DIFF_REQ_CONTACT_RESULT_KPL']")[0].selectize.clear();
		
		var formId = $("#FormHandlerManager [name='formId']").val();
		var handleForm = 'Y';
		var validateForm = 'Y';
		
		$("[name='calcu']").val('Y');
		saveTabForm('calcu=Y',handleForm,validateForm);
		
	}catch(exception)
	{
		errorException(exception);
	}
}
