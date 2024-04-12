/**
 * Pipe
 */
$(document).ready(function (){
		$("Tr.ResultOdd").hover(
	    	 function(){$(this).addClass("ResultOdd-haver");},
	    	 function(){$(this).removeClass("ResultOdd-haver");}
	    );
		$("Tr.ResultEven").hover(
	    	 function(){$(this).addClass("ResultEven-haver");},
	    	 function(){$(this).removeClass("ResultEven-haver");}
		);
		
		$('#checkbox1').change(function(){checksavingAllBox();});
				
		//if($('#plSavingPopupDisplayMode').val()=="VIEW"){
		//	
		//}
});

function addsaving(){
	$('#saving_pop_err_div').html('');
	var obj = [];
	
	if($('#sav_type').val()==""){
		obj.push('<div>'+REQUIRE_SAVING_TYPE+'</div>');
	}
	if($('#sav_bankname').val()==""){
		obj.push('<div>'+REQUIRE_BANK_NAME+'</div>');
	}
	if($('#sav_fund').val()==""){
		obj.push('<div>'+REQUIRE_ACCOUNT_NO+'</div>');
	}
	if($('#sav_balance').val()==0.0){
		obj.push('<div>'+REQUIRE_AVG_SUMMARY+'</div>');
	}
	if(obj != null && obj.length >0){
		$.map(obj, function(item){
			$('#saving_pop_err_div').append(item);
		});
		return false;
	}
	
	var dataString = "action=SavePLSaving&handleForm=N&"+$("div#plan-dialog :input").serialize();
	dataString+="&actionType=add";  
	// 	alert(dataString);
		$.post('FrontController',dataString
				,function(data,status,xhr) {
			var result="";
			$.map(data, function(item){				
				if(item.id=="savingresult"){
				 result=item.value;
				 htmlDisplayElementById(result,"plSavingResult");
			    }else if(item.id=="saving_summary"){
					 result=item.value;
					 htmlDisplayElementById(result,"saving_summary");
				}//else if(item.id=="h_saving_val"){
				//	 result=item.value;
				//	 htmlDisplayElementById(result,"h_saving_val");
				//}
			});
			    
			       // alert(data);
			   });
		$('#sav_type').val('');
		$('#sav_bankname').val('');
		$('#sav_fund').val('');
		$('#sav_balance').val('');
	
}
function displaysaving(){
var h_saving = $('#saving_summary').text();
$('#occ_savings').val(h_saving); 
try{
	if($('input[name=applicant_radio]:checked').val() == SAVEING_INCOME_TYPE){
		CaculateSaving();
	}
}catch(e){
	
}
var dataString = "action=SavePLSaving&handleForm=N&"+"actionType=save";
//	alert(dataString);
	$.post('FrontController',dataString
			,function(data,status,xhr) {
//		var result="";
		$.map(data, function(item){				
//			if(item.id=="h_saving_val"){
//			  result=item.value;
//			 htmlDisplayElementById(result,"h_saving_val");
//				
//		    }//else if(item.id="saving_summary"){
		    //	alert(item.value);
		   // }			
		});		    
	 });	 
	closeDialog();
}

function checksavingAllBox(){
	if($('#checkbox1').is(':checked')){
		$('#savingresult tr').each(function(){
			$(':checkbox').attr('checked',true);
		});
	}else{
		$('#savingresult tr').each(function(){
			$(':checkbox').attr('checked',false);
		});
	}
	
}
function removeSaving(){
	var removeChecked=false;
	var checkBoxApp=document.getElementsByName("saving");
	if(checkBoxApp!=null&&checkBoxApp.length>0){
		for( var i=0;i<checkBoxApp.length;i++){
			if(checkBoxApp[i].checked){
				removeChecked = true;
				break;
			}
		}
	}
	//$('#checkbox1').is(':checked')
	if( removeChecked){
		if(confirm(PL_SAVING_CONFIRM)){
		//$('#savingresult tr').each(function(){$('tr[id*=saving_index').remove();});
		//$('#savingresult').append('<tr id="noSavingRecord" class="ResultNotFound ResultEven"><td align="center"colspan="5">No record found</td></tr>');
		//$('#saving_summary').html('0.00');
		//$('#checkbox1').attr('checked',false);
			var dataString = "action=SavePLSaving&handleForm=N&"+$("#plSavingResult *").serialize();
			dataString+="&actionType=delete";
			//alert(dataString);
			$.post('FrontController',dataString
					,function(data,status,xhr) {
				var result="";
				$.map(data, function(item){				
					if(item.id=="savingresult"){
					 result=item.value;
					 htmlDisplayElementById(result,"plSavingResult");
				    }else if(item.id=="saving_summary"){
						 result=item.value;
						 htmlDisplayElementById(result,"saving_summary");
					}
				});
			 });		
		}
		
	}else{
		alert(PL_SAVING_NOT_SELECT);
		/* $('#savingresult tr').each(function(){
			if($(this).find('td #saving').is(':checked')==true){
				var subtractVal = $(this).find('td#saving_avg_current').html();
				var saving_sum_val = $('#saving_summary').text();
				ajaxDisplayElementJson('GetSummarySaving','0','&saving_sum_val='+saving_sum_val+'&subtractVal='+subtractVal);
				$(this).remove();
			}
		});
		
			var saving_row = $('#savingresult tr').length;
			if(saving_row==1){$('#savingresult').append('<tr id="noSavingRecord" class="ResultNotFound ResultEven"><td align="center"colspan="5">No record found</td></tr>');}
	   */	
	 }
}
TextBoxCurrencyEngine($('#sav_balance'));