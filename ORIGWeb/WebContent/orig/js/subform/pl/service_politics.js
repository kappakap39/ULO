/**
 * @Pipe
 */
$(document).ready(function(){
	
	var displaymode_servicepolitics = $('#displaymode_servicepolitics').val();
	
	if(displaymode_servicepolitics == DISPLAY_MODE_EDIT){
		
		$('#have_related').change(function(){
			displayRelationFlag();
		});
		
		PopUpTextBoxDescFieldIDEngine('#sertitleThai');	
		
		$('#ser_position_duration').blur(function(){
			var year = $('#ser_position_duration').val().substr(6,10);
			if(year < 2400 && year.length!=0){
				alertMassage(FORMAT_DATE_ERROR);
				$('#ser_position_duration').val('');
				$('#ser_position_duration').focus();
				$('#ser_position_duration').setCursorToTextEnd();
			}
		});
		$('#ser_to_years').blur(function(){
			var year = $('#ser_to_years').val().substr(6,10);
			if(year < 2400 && year.length!=0){
				alertMassage(FORMAT_DATE_ERROR);
				$('#ser_to_years').val('');
				$('#ser_to_years').focus();
				$('#ser_to_years').setCursorToTextEnd();
			}
		});
				
		compareMoreThanDateLogic('ser_position_duration','ser_to_years','');
		compareLessThanDateLogic('ser_to_years','ser_position_duration',POS_DUR_DATE_MSG);
		
	}	
});

function displayRelationFlag(){
	var have_related = $('#have_related').val();
	if(have_related == 'Y'){
		var role = $('#currentrole_servicepolitics').val();
		if(role != DF_REJECT){
			$('.mandatory_have_related').html('*');
		}
		$('#sertitleThai_popup').removeAttr('disabled');
		
		$('#sertitleThai').val('');
		$('#sertitleThai').removeClass('textbox-code-view');
		$('#sertitleThai').addClass('textbox-code');
		$('#sertitleThai').removeAttr('readOnly');
		
		$('#ser_name_th').val('');
		$('#ser_name_th').removeClass('textboxReadOnly');
		$('#ser_name_th').removeAttr('readOnly');
		
		$('#ser_title_thai').val('');
		
		$('#ser_surname_thai').val('');
		$('#ser_surname_thai').removeClass('textboxReadOnly');
		$('#ser_surname_thai').removeAttr('readOnly');		
		
		$('#ser_position').val('');
		$('#ser_position').removeClass('textboxReadOnly');
		$('#ser_position').removeAttr('readOnly');		
		
		$('#ser_relation').val('');
		$('#ser_relation').removeClass('textboxReadOnly');
		$('#ser_relation').removeAttr('readOnly');
				
		displayTextboxDateEDIT('tr_ser_position_duration','ser_position_duration','right','posDuration');
		displayTextboxDateEDIT('tr_ser_to_years','ser_to_years','left','posDuration');
		
		$('#ser_position_duration').val('');
		$('#ser_position_duration').removeClass('textboxReadOnly');
		$('#ser_position_duration').removeAttr('readOnly');
		
		$('#ser_to_years').val('');
		$('#ser_to_years').removeClass('textboxReadOnly');
		$('#ser_to_years').removeAttr('readOnly');

		compareMoreThanDateLogic('ser_position_duration','ser_to_years',POS_DUR_DATE_MSG);
		compareLessThanDateLogic('ser_to_years','ser_position_duration',POS_DUR_DATE_MSG);
		
	}else{
		$('#sertitleThai_popup').attr('disabled','disabled');		
		$('.mandatory_have_related').html('');
		
		$('#sertitleThai').val('');
		$('#sertitleThai').removeClass('textbox-code');
		$('#sertitleThai').addClass('textbox-code-view');
		$('#sertitleThai').attr('readOnly','readonly');
		
		$('#ser_name_th').val('');
		$('#ser_name_th').addClass('textboxReadOnly');
		$('#ser_name_th').attr('readOnly','readonly');
		
		$('#ser_title_thai').val('');
		
		$('#ser_surname_thai').val('');
		$('#ser_surname_thai').addClass('textboxReadOnly');
		$('#ser_surname_thai').attr('readOnly','readonly');		
		
		$('#ser_position').val('');
		$('#ser_position').addClass('textboxReadOnly');
		$('#ser_position').attr('readOnly','readonly');
		
		$('#ser_relation').val('');
		$('#ser_relation').addClass('textboxReadOnly');
		$('#ser_relation').attr('readOnly','readonly');
		
		displayTextboxDateVIEW('tr_ser_position_duration','ser_position_duration');
		displayTextboxDateVIEW('tr_ser_to_years','ser_to_years');
		
		$('#ser_position_duration').val('');
		$('#ser_position_duration').addClass('textboxReadOnly');
		$('#ser_position_duration').attr('readOnly','readonly');
		
		$('#ser_to_years').val('');
		$('#ser_to_years').addClass('textboxReadOnly');
		$('#ser_to_years').attr('readOnly','readonly');
		
	}
}



