/**
 * Pipe
 */
$(document).ready(function(){
	
	// FOR DISPLAY_MODE_VIEW
	var OCC_DISPLAY_MODE = $('#occ_displayMode').val();
	if(OCC_DISPLAY_MODE == DISPLAY_MODE_VIEW){
		$('#radio_table tr').each(function (){
			$(this).find("input[id='applicant_radio']:checked").removeAttr("disabled");
		});
	}
	
	// FOR DISPLAY_MODE_EDIT
	if(OCC_DISPLAY_MODE == DISPLAY_MODE_EDIT){
		validateOccupation();
			
		$('#occ_occupation_type').change(function(){
			displayOccupationText();
		});
		
		$('#occ_business_type').change(function(){
			displayBusinessText();
		});
		
		$('#occ_source_other_income').change(function(){
			displaySourceOtherIncomeText();
		});
		
		$('#occ_savings').blur(function(){
			if($('input[name=applicant_radio]:checked').val() == SAVEING_INCOME_TYPE){
				CaculateSaving();
			}		
		});
		
		$('input[id=applicant_radio').change(function(){
			var applicant_radio = $(this).val();
			$('.radiocheck').remove();
			var applicant_tagid = "#man_radio_"+applicant_radio;
			$(applicant_tagid).append('<font color="#ff0000" class="radiocheck">*</font>');
			var applicant_class = ".applicant_radio_"+applicant_radio;
			$('.salaryInput').val('0.00');
			$(applicant_class).focus();
			if(applicant_radio == '02'){
				$('#man_radio_02-1').append('<font color="#ff0000" class="radiocheck">*</font>');
			}	
			EnableLoanIncome();
			try{
				processCardFaceByEvent(this);
			}catch(e){}
		});
			
//		#septem comment
//		PopUpTextBoxDescFieldIDEngine('#occ_workplaceTitle2');			
//		PopUpTextBoxDescFieldIDEngine('#occ_old_workplaceTitle');
		
		SensitiveRadioButton('applicant_radio');
		
	}
	
});

function validateOccupation(){
	$('#occ_old_service_years').blur(function(){
		var year = Number($(this).val());
		if(year < 0){
			alertMassageSelection(CHECK_YEAR_ALERT,'occ_old_service_years');
		}
	});	
	$('#occ_old_service_month').blur(function(){
		var month = Number($(this).val());
		if(month>11 || month<0){
			alertMassageSelection(CHECK_MONTH_ALERT,'occ_old_service_month');
		}
	});	
	$('#occ_service_month').blur(function(){
		var month = Number($(this).val());
		if(month>11 || month<0){
			alertMassageSelection(CHECK_MONTH_ALERT,'occ_service_month');
		}
	});
}

function CaculateSaving(){
	var sal = parseInt(RemoveCommaStr($('#salary').val()));
	if(sal == parseInt(0)){
		var saving = $('#occ_savings').val();
		var savingNum = parseInt(RemoveCommaStr(saving));
		var salary = (savingNum * 5) / 100;
		$('#salary').val(addCommaString(salary.toString()));
	}
}

function ChageSelectBoxFocusTextBox(fieldSelect,fieldFocus){
	$(fieldSelect).change(function(e){
		$(fieldFocus).focus();
		$(fieldFocus).setCursorToTextEnd();
	});
	$(fieldFocus).focus(function(e){
		if($(this).val() == ''){
			$(this).focus();
			$(this).setCursorToTextEnd();
		}
	});
}

function displayOccupationText(){
	var occupation_type = $('#occ_occupation_type').val();
//	other occupation_type = 18
	if(occupation_type == '18'){
		$('#occ_occupation_type_text').val('');
		$('#occ_occupation_type_text').removeClass('textboxReadOnly');
		$('#occ_occupation_type_text').attr("readonly", false);
		$("#occ_occupation_type_text").focus();
	}else{
		$('#occ_occupation_type_text').val('');
		$('#occ_occupation_type_text').addClass('textboxReadOnly');
		$('#occ_occupation_type_text').attr("readonly", true);
	}
}

function displayBusinessText(){
	var business_type = $('#occ_business_type').val();
//	other business_type = 25
	if(business_type == '25'){
		$('#occ_business_type_text').val('');
		$('#occ_business_type_text').removeClass('textboxReadOnly');
		$('#occ_business_type_text').attr("readonly", false);
		$("#occ_business_type_text").focus();
	}else{
		$('#occ_business_type_text').val('');
		$('#occ_business_type_text').addClass('textboxReadOnly');
		$('#occ_business_type_text').attr("readonly", true);
	}
}

function displaySourceOtherIncomeText(){
	var source_other_income = $('#occ_source_other_income').val();
//	other source_other_income = 04
	if(source_other_income == '04'){
		$('#occ_other_income_text').val('');
		$('#occ_other_income_text').removeClass('textboxReadOnly');
		$('#occ_other_income_text').attr("readonly", false);
		$("#occ_other_income_text").focus();
	}else{
		$('#occ_other_income_text').val('');
		$('#occ_other_income_text').addClass('textboxReadOnly');
		$('#occ_other_income_text').attr("readonly", true);
	}
}

function monthRange(field){
	var value = $('#'+field).val();
	if(value.length!=0){
		if(value>11 || value<1){
			alertMassageFocus(MONTH_INPUT, field);
		}
	}
}
function yearRange(field){
	var value = $('#'+field).val();
	if(value.length!=0){
		if(value>11 || value<1){
			alertMassageFocus(YEAR_INPUT, field);
		}
	}
}

function LoadSavingPopup(scriptAction,displayMode){
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+scriptAction;		
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	var title = SAVINGS;	 
	$(".plan-dialog").remove();	
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	var buttonArray;	
	if(displayMode == DISPLAY_MODE_VIEW){
		buttonArray = {"Close": function() {   closeDialog();}} ;
	}else{
		buttonArray = {"Save": function() {displaysaving();},
		    			"Close": function() {closeDialog();}
					   };
	}	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(frameModule)
    		},
		    close: function(){
		    	closeDialog();
		    },
		    buttons: buttonArray
	});	
   $dialog.dialog("open");
}

function calSummaryWealth(){
	var wealth_kank = new Number((document.appFormName.occ_wealth_bank.value).replace(/,/g,''));
	var wealth_non_kank = new Number((document.appFormName.occ_wealth_ext_bank.value).replace(/,/g,''));	
	if(wealth_kank==null){
		wealth_kank=0;
	}
	if(wealth_non_kank==null){
		wealth_kank=0;
	}
	var summary = (wealth_kank) + (wealth_non_kank);
	$('#occ_summary_wealth').val(new Number(summary));
	addComma(document.appFormName.occ_summary_wealth);
	addDecimalFormat(document.appFormName.occ_summary_wealth);
}
