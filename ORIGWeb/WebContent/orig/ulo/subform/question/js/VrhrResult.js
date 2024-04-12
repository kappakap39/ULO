function VER_HR_RESULTActionJS(){
	try{
		var subformId ='IDENTIFY_QUESTION_SUBFORM';
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}