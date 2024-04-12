
function KCC_FINAL_RESULTActionJS(element,mode,fieldname){
	try{
		var FIRST_SUPFIXS =element.split("_");
		var FIRST_SUPFIX =FIRST_SUPFIXS[FIRST_SUPFIXS.length - 1];  
		var FIRST_FIELD_NAME_FINAL_CREDIT ='KCC_FINAL_CREDIT_LIMIT_'+FIRST_SUPFIX;
		
		var FIRST_FINAL_RESULT_VALUE =$('[name='+element+']').val();
		var FIRST_FINAL_CREDIT_VALUE = $('[name='+FIRST_FIELD_NAME_FINAL_CREDIT+']').val();
		var DEFUALT_SUFFIX_ID  = $('[name="SUFFIX_ID"]').val().split(","); 
		for(var i=0;i< DEFUALT_SUFFIX_ID.length;i++){ 
			var value = DEFUALT_SUFFIX_ID[i];
			var supfix = value.trim();
			
			var MAIN_FIELD_NAME_FINAL_RESULT ='KCC_FINAL_RESULT_'+supfix; 
			var MAIN_FIELD_NAME_FINAL_CREDIT = 'KCC_FINAL_CREDIT_LIMIT_'+supfix;		
			var SUB_FIELD_NAME_FINAL_RESULT = 'SUP_KCC_FINAL_RESULT_'+supfix;
			var SUB_FIELD_NAME_FINAL_CREDIT ='SUP_KCC_FINAL_CREDIT_LIMIT_'+supfix; 
			var FIELD_NAME_REC_DECISION = 'KCC_REC_DECISION_'+supfix; 
			var REC_DECISION_VALUE = $('[name="'+FIELD_NAME_REC_DECISION+'"]').val();
			 
			if(DECISION_FINAL_DECISION_APPROVE==FIRST_FINAL_RESULT_VALUE){
				targetDisplayHtml(element, MODE_EDIT,element);
				targetDisplayHtml(FIRST_FIELD_NAME_FINAL_CREDIT, MODE_EDIT,FIRST_FIELD_NAME_FINAL_CREDIT);
				
				if(""==$('[name="'+MAIN_FIELD_NAME_FINAL_RESULT+'"]').val() && REC_RESULT_REFER==REC_DECISION_VALUE){				
					displayHtmlElement(MAIN_FIELD_NAME_FINAL_RESULT,FIRST_FINAL_RESULT_VALUE);
					if(""==$('[name="'+MAIN_FIELD_NAME_FINAL_CREDIT+'"]').val() ||"0.00"==$('[name="'+MAIN_FIELD_NAME_FINAL_CREDIT+'"]').val()){
						displayHtmlElement(MAIN_FIELD_NAME_FINAL_CREDIT,FIRST_FINAL_CREDIT_VALUE);				
					}
				}
				
				if(""==$('[name="'+SUB_FIELD_NAME_FINAL_RESULT+'"]').val() &&  REC_RESULT_REFER==REC_DECISION_VALUE){
					displayHtmlElement(SUB_FIELD_NAME_FINAL_RESULT,FIRST_FINAL_RESULT_VALUE);	
					if(""==$('[name="'+SUB_FIELD_NAME_FINAL_CREDIT+'"]').val() ||"0.00"==$('[name="'+SUB_FIELD_NAME_FINAL_CREDIT+'"]').val()){
						displayHtmlElement(SUB_FIELD_NAME_FINAL_CREDIT,FIRST_FINAL_CREDIT_VALUE);				
					}
				}
			}else if(DECISION_FINAL_DECISION_REJECT==FIRST_FINAL_RESULT_VALUE){
				
				targetDisplayHtml(FIRST_FIELD_NAME_FINAL_CREDIT, MODE_VIEW,FIRST_FIELD_NAME_FINAL_CREDIT);
				displayHtmlElement(FIRST_FIELD_NAME_FINAL_CREDIT,'0.00');
				if(""==$('[name="'+MAIN_FIELD_NAME_FINAL_RESULT+'"]').val() && REC_RESULT_REFER==REC_DECISION_VALUE){
					displayHtmlElement(MAIN_FIELD_NAME_FINAL_RESULT,FIRST_FINAL_RESULT_VALUE);
					displayHtmlElement(MAIN_FIELD_NAME_FINAL_CREDIT,'0.00');
					targetDisplayHtml(MAIN_FIELD_NAME_FINAL_CREDIT, MODE_VIEW,MAIN_FIELD_NAME_FINAL_CREDIT);
				}
				
				if(""==$('[name="'+SUB_FIELD_NAME_FINAL_RESULT+'"]').val() && REC_RESULT_REFER==REC_DECISION_VALUE){
					displayHtmlElement(SUB_FIELD_NAME_FINAL_RESULT,FIRST_FINAL_RESULT_VALUE);
					displayHtmlElement(SUB_FIELD_NAME_FINAL_CREDIT,'0.00');
					targetDisplayHtml(SUB_FIELD_NAME_FINAL_CREDIT, MODE_VIEW,SUB_FIELD_NAME_FINAL_CREDIT);
				}	
			}
		}
	}catch(exception){
		errorException(exception);
	}
}


function SUP_KCC_FINAL_RESULTActionJS(element,mode,fieldname){
	try{
		var  supfixs =element.split("_");
		var supfix =supfixs[supfixs.length - 1];
		var SUB_FIELD_NAME_FINAL_CREDIT ='SUP_KCC_FINAL_CREDIT_LIMIT_'+supfix;
		if(DECISION_FINAL_DECISION_REJECT==$('[name='+element+']').val()){
			displayHtmlElement(SUB_FIELD_NAME_FINAL_CREDIT,'0.00');
			targetDisplayHtml(SUB_FIELD_NAME_FINAL_CREDIT, MODE_VIEW,SUB_FIELD_NAME_FINAL_CREDIT);		
		}else{
			targetDisplayHtml(SUB_FIELD_NAME_FINAL_CREDIT, MODE_EDIT,SUB_FIELD_NAME_FINAL_CREDIT);
		}
	}catch(exception){
		errorException(exception);
	}
}

function KCC_FINAL_RESULTInitJS(element,mode,fieldname){
	try{
		var FIRST_SUPFIXS =element.split("_");
		var FIRST_SUPFIX =FIRST_SUPFIXS[FIRST_SUPFIXS.length - 1];  
		var FIRST_FIELD_NAME_FINAL_CREDIT ='KCC_FINAL_CREDIT_LIMIT_'+FIRST_SUPFIX;
		
		if(DECISION_FINAL_DECISION_REJECT==$('[name="'+element+'"]').val()){
			targetDisplayHtml(FIRST_FIELD_NAME_FINAL_CREDIT, MODE_VIEW,FIRST_FIELD_NAME_FINAL_CREDIT);
		}
	}catch(exception){
		errorException(exception);
	}
}

function SUP_KCC_FINAL_RESULTInitJS(element,mode,fieldname){
	try{
		var  supfixs =element.split("_");
		var supfix =supfixs[supfixs.length - 1];
		var SUB_FIELD_NAME_FINAL_CREDIT ='SUP_KCC_FINAL_CREDIT_LIMIT_'+supfix;
		if(DECISION_FINAL_DECISION_REJECT==$('[name='+element+']').val()){
			targetDisplayHtml(SUB_FIELD_NAME_FINAL_CREDIT, MODE_VIEW,SUB_FIELD_NAME_FINAL_CREDIT);		
		} 
	}catch(exception){
		errorException(exception);
	}
}

function KEC_FINAL_RESULTInitJS(element,mode,fieldname){
	try{
		var SUPFIXS =element.split("_");
		var SUPFIX =SUPFIXS[SUPFIXS.length - 1];  
		var KEC_FIELD_NAME_FINAL_CREDIT ='KEC_FINAL_CREDIT_LIMIT_'+SUPFIX;
		var KEC_FINAL_RESULT_VALUE =$('[name="'+element+'"]').val();
		
		if(DECISION_FINAL_DECISION_REJECT==KEC_FINAL_RESULT_VALUE){
			targetDisplayHtml(KEC_FIELD_NAME_FINAL_CREDIT, MODE_VIEW,KEC_FIELD_NAME_FINAL_CREDIT);
			
		} 
	}catch(exception){
		errorException(exception);
	}	 
}
function KEC_FINAL_RESULTActionJS(element,mode,fieldname){
	try{
		var subformId ='DECISION_SUBFORM';
		refreshSubForm(subformId,'Y');	 
	}catch(exception){
		errorException(exception);
	}
}
function KPL_FINAL_RESULTInitJS(){
	try{
		var sel = $("#KPL_FINAL_RESULT");
		//remove Cancel option From DropdownList
		sel[0].selectize.removeOption('Cancel');
	}catch(exception){
		errorException(exception);
	}	 
}
