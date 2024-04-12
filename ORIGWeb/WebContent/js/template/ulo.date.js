/**
 * Pipe
 */
/**New Function Date Format #SeptemWi*/
function DestoryDateEngine(entity){
	$(entity).unbind();
}
function DateEngine(entity){
	/**This Function Used For Event At keyDown To Validate*/
	$(entity).keypress(function(event){
		if(event.keyCode == 8 ||event.keyCode == 46){
			 return;
		}
		var value = String.fromCharCode(event.keyCode);
		if(value.match("[0-9]")||event.keyCode == 47){		
			if($(this).val().length == 2 || $(this).val().length == 5){	
				event.preventDefault();
				$(this).val((event.keyCode == 47)?$(this).val()+'/': $(this).val()+'/'+value);			
				$(this).focus();
				return;
			}
			if(event.keyCode == 47 && $(this).val().length == 1){
			   event.preventDefault();			   
			   $(this).val('0'+$(this).val()+'/');
			   $(this).focus();
			   return;
			}
			if(event.keyCode == 47 && $(this).val().length == 4){
			   event.preventDefault();			   
			   $(this).val($(this).val().substr(0,3)+'0'+$(this).val().substr(3,1)+'/');
			   $(this).focus();
			   return;
			}
			if(event.keyCode == 47){
				event.preventDefault();
				$(this).focus();
				return;
			}
			var $dateVal = $(this).val()+value;
			if($dateVal.length > 9) return;
			var mDay = $dateVal.substr(0,2);
			var mMonth = $dateVal.substr(3,2);		
			var mSplit1 = $dateVal.substr(2,1);
			var mSplit2 = $dateVal.substr(5,1);			
			if(mSplit1 != null && mSplit1 != '' && mSplit1 != '/'){
				event.preventDefault();	
				$(this).val('');
				return;
			}			
			if(mSplit2 != null && mSplit2 != '' && mSplit2 != '/'){
				event.preventDefault();	
				$(this).val('');
				return;
			}
			if(mDay.length == 2 && (mDay == 0 || mDay > 31)){
				event.preventDefault();	
				$(this).val(''); $(this).focus();
				return;
			}
			if((mDay.length == 2 && mMonth.length == 2 && mMonth == 0)||mMonth > 12){
				event.preventDefault();	
				$(this).val(mDay+'/');		
				$(this).focus();
				return;
			}
		}else{
			event.preventDefault();
		}		
	});	
	/**This Function Used For Event At blur To Validate*/
	$(entity).blur(function (){
		var obj = $(this);
		var objRegExp = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/;
		if($(this).val() == null || $(this).val() == '') return;		
		if($(this).val().length == 10){
			if(!objRegExp.test($(this).val())){				
				$('.boxy-wrapper').remove();$('.boxy-modal-blackout').remove();
				 Boxy.alert(DATE_ERROR_MSG , function(){obj.select();obj.focus();$('.boxy-wrapper').remove();$('.boxy-modal-blackout').remove();}, {title: 'Alert Message'});
			}			
			var mDay =  parseInt($(this).val().substr(0,2),10);
			var mMonth =  parseInt($(this).val().substr(3,2),10);
			var mYear = parseInt($(this).val().substr(6,4))-543;
			var allDays = [31,28,31,30,31,30,31,31,30,31,30,31];			
			var isLeapYear = (mYear % 4 == 0 && (mYear % 100 != 0 || mYear % 400 == 0));
			allDays[1] = (isLeapYear)? 29 : 28;			
			if (mMonth >= 1 && mMonth <= 12){
				if (mDay <= allDays[mMonth-1] && mDay > 0){
					/**Optional Function class Check Current Date*/
					if($(this).hasClass('currentDate')){
						var dt = new Date(mYear, mMonth-1, mDay);
						var today = new Date();
						if((dt.getDate() != mDay) || (dt.getMonth() != mMonth-1) || (dt.getFullYear()!=mYear) || (dt>today)){
							Boxy.alert(DATE_ERROR_MSG , function(){obj.select();obj.focus();$('.boxy-wrapper').remove();$('.boxy-modal-blackout').remove();}, {title: 'Alert Message'});							
							return;
						}
					}
					/*** fix in case Date != current Date*/
					if($(this).hasClass('notCurrentDate')){
						var dt = new Date(mYear+543, mMonth-1, mDay);
						var today = new Date();
						if((dt.getDate() != mDay) || (dt.getMonth() != mMonth-1) || (dt.getFullYear()!=mYear+543) || (dt>today) || dt.getFullYear()==today.getFullYear() && dt.getDate()==today.getDate()&&dt.getMonth()==today.getMonth()){
							Boxy.alert(DATE_ERROR_MSG , function(){obj.select();obj.focus();$('.boxy-wrapper').remove();$('.boxy-modal-blackout').remove();}, {title: 'Alert Message'});							
							return;
						}
					}
					/*** fix in case BirthDate BC.*/
					if($(this).hasClass('birthDate')){
						var dt = new Date(mYear+543, mMonth-1, mDay);
						var today = new Date();
						if((dt.getDate() != mDay) || (dt.getMonth() != mMonth-1) || (dt.getFullYear()!=mYear+543) || (dt>today) || dt.getFullYear()==today.getFullYear() && dt.getDate()==today.getDate()&&dt.getMonth()==today.getMonth()){
							Boxy.alert(BIRTH_DATE_ERROR_MSG , function(){obj.select();obj.focus();$('.boxy-wrapper').remove();$('.boxy-modal-blackout').remove();}, {title: 'Alert Message'});							
							return;
						}
					}
					
					/*** fix in case Expire Date*/
					if($(this).hasClass('expDate')){
						var dt = new Date(mYear, mMonth-1, mDay);
						var today = new Date();
						if((dt.getDate() < today.getDate()) || (dt.getMonth() < today.getMonth()) || (dt.getFullYear()<today.getFullYear())){
							Boxy.alert(EXP_DATE_ERROR_MSG, function(){obj.select();obj.focus();$('.boxy-wrapper').remove();$('.boxy-modal-blackout').remove();}, {title: 'Alert Message'});							
							return;
						}
					}
					
					/*** fix in case position duration Date*/
//					#septemwi comment change to manual funcation for validate
//					if($(this).hasClass('posDuration')){
//						var dtSinceDay =  parseInt($('#ser_position_duration').val().substr(0,2),10);
//						var dtSinceMonth =  parseInt($('#ser_position_duration').val().substr(3,2),10);
//						var dtSinceYear = parseInt($('#ser_position_duration').val().substr(6,4))-543;
//						var dt = new Date(mYear, mMonth-1, mDay);
//						var dtSince = new Date(dtSinceYear,dtSinceMonth-1,dtSinceDay);
//						//if((dt.getDate() < dtSince.getDate()) ||(dt.getFullYear()<dtSince.getFullYear())){
//						//alert("dt.getDate()"+dt.getDate() + "dtSince.getDate() ="+dtSince.getDate());
//						var validate=false;
//						if(dt.getFullYear()>dtSince.getFullYear()){
//							validate=true;
//						}else if(dt.getFullYear()==dtSince.getFullYear()){
//						     if(dt.getMonth()>dtSince.getMonth()){
//							     validate=true;
//						      }else if(dt.getMonth()==dtSince.getMonth()){
//						    	  if(dt.getDate()>=dtSince.getDate() ){
//							        validate =true;
//						    	  } 
//						      }
//						}			 		
//						if(!validate){
//							Boxy.alert(POS_DUR_DATE_MSG, function(){obj.select();obj.val('');$('.boxy-wrapper').remove();$('.boxy-modal-blackout').remove();}, {title: 'Alert Message'});							
//							return;
//						}
//						
//					}
					return;
				}						
			}
			$('.boxy-wrapper').remove();
			$('.boxy-modal-blackout').remove();
			Boxy.alert(DATE_ERROR_MSG , function(){obj.select();obj.focus(); $('.boxy-wrapper').remove();$('.boxy-modal-blackout').remove();}, {title: 'Alert Message'});
		}else{
			$('.boxy-wrapper').remove();
			$('.boxy-modal-blackout').remove();
			Boxy.alert(DATE_ERROR_MSG , function(){obj.select();obj.focus();$('.boxy-wrapper').remove();$('.boxy-modal-blackout').remove();}, {title: 'Alert Message'});
		}
	});
}

function displayTextboxDateEDIT(appendTo,elementID,position,otherclass){
	var HTML  = ' <table cellSpacing="0" cellPadding="0"><tbody><tr><td>';
		HTML += ' <input id="'+elementID+'" class="textboxCalendar '+otherclass+' sensitive"';
		HTML += ' name="'+elementID+'" maxLength="10" value="" size="20" type=" text"></td>';
		HTML += ' <td vAlign="middle">&nbsp;<img class="date_trigger" onclick="popUpCalendar(this,\''+elementID+'\',\'dd/mm/yyyy\',\'\',\'\',\'\',\''+position+'\')"';
		HTML += ' src="images/calendar.gif" width="21" height="21"></td></tr></tbody></table>';
	$('#'+appendTo).html(HTML);
	DestoryDateEngine('#'+elementID);
	DateEngine('#'+elementID);
	$('#'+elementID).blur(function(){
		var year = $('#'+elementID).val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#'+elementID).val('');
			$('#'+elementID).focus();
			$('#'+elementID).setCursorToTextEnd();
		}
	});	
}
function displayTextboxDateVIEW(appendTo,elementID){
	var HTML = '<input name="'+elementID+'" class="textboxCalendar textboxReadOnly" id="'+elementID+'" type=" text" size="10" maxLength="10" readOnly="" value=""/>';
	$('#'+appendTo).html(HTML);
	DestoryDateEngine('#'+elementID);
}

function compareLessThanDateLogic(field01,field02,message){
	$('#'+field01).blur(function(){
		var objRegExp = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/;
		if($(this).val() == null || $(this).val() == ''){
			$('#'+field01).val('');
		}		
		if($(this).val().length == 10){
			if(!objRegExp.test($(this).val())){	
				$('#'+field01).val('');
				return;
			}
			var mDay =  parseInt($(this).val().substr(0,2),10);
			var mMonth =  parseInt($(this).val().substr(3,2),10);
			var mYear = parseInt($(this).val().substr(6,4))-543;
			var allDays = [31,28,31,30,31,30,31,31,30,31,30,31];			
			var isLeapYear = (mYear % 4 == 0 && (mYear % 100 != 0 || mYear % 400 == 0));
			allDays[1] = (isLeapYear)? 29 : 28;			
			if (mMonth >= 1 && mMonth <= 12){
				if (mDay <= allDays[mMonth-1] && mDay > 0){
					var dtSinceDay =  parseInt($('#'+field02).val().substr(0,2),10);
					var dtSinceMonth =  parseInt($('#'+field02).val().substr(3,2),10);
					var dtSinceYear = parseInt($('#'+field02).val().substr(6,4))-543;
					var dt = new Date(mYear, mMonth-1, mDay);
					var dtSince = new Date(dtSinceYear,dtSinceMonth-1,dtSinceDay);
					var validate=false;
					if(dt.getFullYear()>dtSince.getFullYear()){
						validate=true;
					}else if(dt.getFullYear()==dtSince.getFullYear()){
					     if(dt.getMonth()>dtSince.getMonth()){
						     validate=true;
					      }else if(dt.getMonth()==dtSince.getMonth()){
					    	  if(dt.getDate()>=dtSince.getDate() ){
						        validate =true;
					    	  } 
					      }
					}			 		
					if(!validate){
						if(message != ''){
							Boxy.alert(message, function(){
									$('#'+field01).val('');
									$('.boxy-wrapper').remove();
									$('.boxy-modal-blackout').remove();
							}, {title: 'Alert Message'});	
						}else{
							$('#'+field01).val('');
						}
						return;
					}
				}
			}
		}else{
			$('#'+field01).val('');
		}		
	});
}
function compareMoreThanDateLogic(field01,field02,message){
	$('#'+field01).blur(function(){
		var objRegExp = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/;
		if($(this).val() == null || $(this).val() == ''){
			$('#'+field01).val('');
			$('#'+field02).val('');
		}		
		if($(this).val().length == 10){
			if(!objRegExp.test($(this).val())){	
				$('#'+field01).val('');
				$('#'+field02).val('');
				return;
			}
			var mDay =  parseInt($(this).val().substr(0,2),10);
			var mMonth =  parseInt($(this).val().substr(3,2),10);
			var mYear = parseInt($(this).val().substr(6,4))-543;
			var allDays = [31,28,31,30,31,30,31,31,30,31,30,31];			
			var isLeapYear = (mYear % 4 == 0 && (mYear % 100 != 0 || mYear % 400 == 0));
			allDays[1] = (isLeapYear)? 29 : 28;			
			if (mMonth >= 1 && mMonth <= 12){
				if (mDay <= allDays[mMonth-1] && mDay > 0){
					if($('#'+field02).val() == ''){
						return;
					}
					var dtSinceDay =  parseInt($('#'+field02).val().substr(0,2),10);
					var dtSinceMonth =  parseInt($('#'+field02).val().substr(3,2),10);
					var dtSinceYear = parseInt($('#'+field02).val().substr(6,4))-543;
					var dt = new Date(mYear, mMonth-1, mDay);
					var dtSince = new Date(dtSinceYear,dtSinceMonth-1,dtSinceDay);
					var validate=false;
					if(dt.getFullYear()<dtSince.getFullYear()){
						validate=true;
					}else if(dt.getFullYear()==dtSince.getFullYear()){
					     if(dt.getMonth()<dtSince.getMonth()){
						     validate=true;
					      }else if(dt.getMonth()==dtSince.getMonth()){
					    	  if(dt.getDate()<=dtSince.getDate() ){
						        validate =true;
					    	  } 
					      }
					}			 		
					if(!validate){
						if(message != ''){
							Boxy.alert(message, function(){
									$('#'+field02).val('');
									$('.boxy-wrapper').remove();
									$('.boxy-modal-blackout').remove();
							}, {title: 'Alert Message'});	
						}else{
							$('#'+field02).val('');
						}
						return;
					}
				}
			}
		}else{
			$('#'+field01).val('');
			$('#'+field02).val('');
		}		
	});
}
