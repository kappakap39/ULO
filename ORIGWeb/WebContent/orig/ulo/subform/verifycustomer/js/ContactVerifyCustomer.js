
function ADD_PHONE_VER_STATUS_CUS_YActionJS(){
	try{
		var handleForm ='Y';
		var str ='';
		var className ='com.eaf.orig.ulo.app.view.form.subform.verifycustomer.manual.ContactCustomerSubForm';
		var flag= true;
		var subformId=$('input[name="groupSubformId"]').val();
			  $('input[type=radio]:checked').each(function(index) {
					var	PHONE_NO=$(this).closest('tr').find($('input[name="PHONE_NO"]')).val();
					var	EXT=$(this).closest('tr').find($('input[name="EXT"]')).val();
					var CONTACT_TYPE= $('input[name="CONTACT_TYPE_OFFICE_PHONE"]:checked').val();
					var REMARK=$('input[name="REMARK"]').serialize();
					var PHONE_VER_STATUS ="Y";
					str+='&CONTACT_TYPE='+CONTACT_TYPE;
					str+='&'+REMARK;
					str+='&PHONE_VER_STATUS='+PHONE_VER_STATUS;
					if(EXT !=undefined){
						str+='&EXT='+EXT;
						flag= false;
					}
					if(PHONE_NO != undefined){
						str+='&PHONE_NO='+PHONE_NO;
						flag= false;
					}
					console.log(str);
					
			  });
			  if(flag){
				  alertBox(ERROR_SELECT_PHONE);
			  }
			  else{
			  confirmBox(MSG_CONFIRM_CONTARCT,function(choice) {
					if (choice == "Y") {
						if( CustomerHRManadatory()){
							createRow(className,str,refreshSubFormAll,subformId,handleForm);
						}
					}
				});
			  }
	}catch(exception){
		errorException(exception);
	}
}
function  CustomerHRManadatory(){
	try{
		var PHONE_NO = $('input[name="PHONE_NO"]').val();
		if(null != PHONE_NO){
			return true;
		}
	}catch(exception){
		errorException(exception);
	}
	return false;
}

function ADD_PHONE_VER_STATUS_CUS_NActionJS(){
	try{
		var handleForm ='Y';
		var str ='';
		var className ='com.eaf.orig.ulo.app.view.form.subform.verifycustomer.manual.ContactCustomerSubForm';
		var flag= true;
		var subformId=$('input[name="groupSubformId"]').val();
		  $('input[type=radio]:checked').each(function(index) {
				var	PHONE_NO=$(this).closest('tr').find($('input[name="PHONE_NO"]')).val();
				var	EXT=$(this).closest('tr').find($('input[name="EXT"]')).val();
				var CONTACT_TYPE=$(this).closest('.col-sm-6').find($('input[name="CONTACT_TYPE_OFFICE_PHONE"]')).val();
				var REMARK=$('input[name="REMARK"]').serialize();
				var PHONE_VER_STATUS ="N";
			
				str+='&CONTACT_TYPE='+CONTACT_TYPE;
				str+='&'+REMARK;
				str+='&PHONE_VER_STATUS='+PHONE_VER_STATUS;
			
				if(EXT !=undefined){
					str+='&EXT='+EXT;
					flag= false;
				}
				if(PHONE_NO != undefined){
					str+='&PHONE_NO='+PHONE_NO;
					flag= false;
				}
				
				
		  });
		  if(flag){
			  alertBox(ERROR_SELECT_PHONE);
		  }
		  else{
		  confirmBox(MSG_CONFIRM_CONTARCT,function(choice) {
				if (choice == "Y") {
					if( CustomerHRManadatory()){
						createRow(className,str,refreshSubFormAll,subformId,handleForm);
					}
				}
			});
		  }		
	}catch(exception){
		errorException(exception);
	}
}
function refreshSubFormAll(){
	try{
		var subformIdQuestion = 'IDENTIFY_QUESTION_CUSTOMER_SUBFORM';
		setPropertiesSubform(subformIdQuestion,refershMasterFormAfterSetProperties);
		
	}catch(exception){
		errorException(exception);
	}
}
function refershMasterFormAfterSetProperties(){
	var formId='VERIFY_CUSTOMER_FORM';
	refreshMasterForm(formId);
}

