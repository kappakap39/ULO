function ADD_PHONE_VER_STATUS_YActionJS(){
	try{
		var handleForm ='Y';
		var str ='';
		var className ='com.eaf.orig.ulo.app.view.form.subform.verifyhr.manual.ContactCustomerHRSubForm';
		var subformId=$('input[name="groupSubformId"]').val();
		var flag =true;
		var PHONE_CHECK='';
	    $('input[type=radio]:checked').each(function(index) {		 
			var	PHONE_NO=$(this).closest('tr').find($('input[name="PHONE_NO"]')).val();
			var PHONE_NO_OTH=$(this).closest('tr').find($('input[name="PHONE_NUMBER_OTH"]')).val();
			var CONTACT_TYPE=$('input[name="CONTACT_TYPE_OFFICE_PHONE"]:checked').val();
			var EXT=$(this).closest('tr').find($('input[name="EXT"]')).val();
			var REMARK=$('input[name="REMARK"]').serialize();
			var PHONE_VER_STATUS ="Y";
		
			if(PHONE_NO !=undefined && PHONE_NO!=""){
				str+='&PHONE_NO='+PHONE_NO;
				PHONE_CHECK=PHONE_NO;
				flag =false;
			}
			if(PHONE_NO_OTH !=undefined && PHONE_NO_OTH!=""){
				str+='&PHONE_NO='+PHONE_NO_OTH;
				PHONE_CHECK=PHONE_NO_OTH;
				flag =false;
			}
			str+='&CONTACT_TYPE='+CONTACT_TYPE;
			str+='&'+REMARK;
			str+='&PHONE_VER_STATUS='+PHONE_VER_STATUS;
			if(EXT!=undefined){
				str+="&EXT="+EXT;
				console.log("EXT : "+EXT);
			}
	  });
	  if(flag){
		  alertBox(ERROR_SELECT_PHONE);
	  }else{ confirmBox(MSG_CONFIRM_PHONE,function(choice) {
		if (choice == "Y") {
			if(CustomerHRManadatory(PHONE_CHECK)){
				createRow(className,str,refreshSubFormAll,subformId,handleForm);
			}
			}
	  	});
	  }	
	}catch(exception){
		errorException(exception);
	}	
}
function  CustomerHRManadatory(PHONE_NO){
	try{
		if(PHONE_NO!=undefined && null != PHONE_NO && PHONE_NO!=''){
			 return true;
		}	
		else{
			 alertBox(ERROR_INVALID_PHONE);
			 return false;
		}
	}catch(exception){
		errorException(exception);
	}
	
}

function ADD_PHONE_VER_STATUS_NActionJS(){
	try{
		var handleForm ='Y';
		var str ='';
		var className ='com.eaf.orig.ulo.app.view.form.subform.verifyhr.manual.ContactCustomerHRSubForm';
		var subformId=$('input[name="groupSubformId"]').val();
		var flag =true;
		var PHONE_CHECK='';
			  $('input[type=radio]:checked').each(function(index) {  
				  var	PHONE_NO=$(this).closest('tr').find($('input[name="PHONE_NO"]')).val();
					var PHONE_NO_OTH=$(this).closest('tr').find($('input[name="PHONE_NUMBER_OTH"]')).val();
					var CONTACT_TYPE=$('input[name="CONTACT_TYPE_OFFICE_PHONE"]:checked').val();
					var EXT=$(this).closest('tr').find($('input[name="EXT"]')).val();
					var REMARK=$('input[name="REMARK"]').serialize();
					var PHONE_VER_STATUS ="N";
					if(PHONE_NO !=undefined && PHONE_NO!=""){
						str+='&PHONE_NO='+PHONE_NO;
						PHONE_CHECK=PHONE_NO;
						flag =false;
					}
					if(PHONE_NO_OTH !=undefined && PHONE_NO_OTH!=""){
						str+='&PHONE_NO='+PHONE_NO_OTH;
						PHONE_CHECK=PHONE_NO_OTH;
						flag =false;
					}
					str+='&CONTACT_TYPE='+CONTACT_TYPE;
					str+='&'+REMARK;
					str+='&PHONE_VER_STATUS='+PHONE_VER_STATUS;
					if(EXT!=undefined){
						str+="&EXT="+EXT;
						console.log("EXT : "+EXT);
					}
					
			  });
			  if(flag){
				  alertBox(ERROR_SELECT_PHONE);
			  }
			  
			  else{ confirmBox(MSG_CONFIRM_PHONE,function(choice) {
					if (choice == "Y") {
						if( CustomerHRManadatory(PHONE_CHECK)){
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
		refreshSubForm('CONTACT_CUSTOMER_HR_SUBFORM','');
		refreshSubForm('IDENTIFY_QUESTION_SUBFORM','');
	}catch(exception){
		errorException(exception);
	}
}