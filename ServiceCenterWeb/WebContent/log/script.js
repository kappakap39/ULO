function UI() {
	
	// Input Object Element
	//var target_url 			= $("#target_url")[0];
	var ref_code 			= $("#ref_code")[0];
	var transaction_id 		= $("#transaction_id")[0];
	var id_no 				= $("#id_no")[0];
	var decision_service 	= $("#decision_service")[0];
	var service_id 			= $("#service_id")[0];

	var search_btn = $("#search_btn")[0];
	var clear_btn = $("#clear_btn")[0];
	var clear_table_btn = $("#clear_table_btn")[0];
	var modal_title = $("#modal_title")[0];
	var jsonContent = $("#json")[0];
	
	var alertMsg = "";
	
	var decisionPoints = [
	    "EDC",
		"EDV1",
		"EDV2",
		"EFA",
		"EPA",
		"EPB1",
		"EPB2",
		"EPV",
		"INCOME_SERVICE",
		"POST_APPROVAL_SERVICE",
		"VERIFICATION_SERVICE"
	];
	
	var serviceIds = [
		"APPLICANT",
		"ASYNC_DECISION_QUEUE",
		"ASYNC_DECISION_REQ",
		"BOL",
		"BScore",
		"CIS0368I01",
		"CVRS1312I01",
		"Cardlink",
		"CheckKPLDuplicate",
		"CoBrand",
		"CompanyGroup",
		"CurrentAccount",
		"DecisionService",
		"FixAccount",
		"FraudApplicant",
		"FraudCompany",
		"HRIS",
		"KAsset",
		"KBANK1211I01",
		"KBANK1550I01",
		"KBankSalary",
		"KCBS",
		"KOC",
		"LPM",
		"LeadByIdentNo",
		"Payroll",
		"SAFE",
		"SOLO",
		"SavingAccount",
		"StableIncome",
		"TCBLoan",
		"TrustedCompany",
		"WFInquire",
		"Wealth"
	];
	
	this.init = function() {
		$(decision_service).append( "<option value=''>--- Please Select ---</option>" );
		$.each(decisionPoints, function(index, value){
			$(decision_service).append($("<option></option>")
							.attr("value", value)
								.text(value) ); 
		});
		
		$(service_id).append( "<option value=''>--- Please Select ---</option>" );
		$.each(serviceIds, function(index, value){
			$(service_id).append($("<option></option>")
							.attr("value", value)
								.text(value) ); 
		});

		$('div.progress').hide();
	}

	$(search_btn).on("click", function() {
		search();
	});
	
	$(clear_btn).on("click", function() {
		$(ref_code).val("");
		$(transaction_id).val("");	
		$(id_no).val(""); 			
		$(decision_service).val("");
		$(service_id).val("");
		removeProgressbar();
	});
	
	$(clear_table_btn).on("click", function() {
		var tableResult = $("#table-result").find('tbody')[0];
		$(tableResult).empty();
	});

	/* $(ref_code).on("change", function() {
		if ( $(ref_code).val() != "" ) {
			enebledBox( decision_service );
		}
	});

	$(decision_service).on("change", function() {
		if ( $(ref_code).val() == "" || $(transaction_id).val() == "" || $(id_no).val() == "") {
			$(decision_service).prop('disabled', true);
		}
	});

	var enebledObj = function( obj ) {
		$(obj).removeAttr('disabled');
	} */
	
	var disabledObj = function( obj ) {
		$(obj).prop('disabled', true);
	}
	
	var enebledObj = function( obj ) {
		$(obj).removeAttr('disabled');
	}

	var createProgressbar = function() {
		var divProgress = $('div.progress')[0];
		if ( undefined != divProgress ) {
			$(divProgress).show();
			$(divProgress).append('<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>');
		}
	}

	var removeProgressbar = function() {
		var divProgress = $('div.progress')[0];
		if ( undefined != divProgress ) {
			$(divProgress).hide();
			$(divProgress).empty();
		}
	}

	var search = function () {
		
		if ( validate() ) {
		
			disabledObj(search_btn);
		
			var jsonInput = getJson();
			
			console.log( "Json input : " + JSON.stringify(jsonInput) );
			
			createProgressbar();

			$.ajax({
				xhr: function () {
					var xhr = new window.XMLHttpRequest();
					//Upload Progress
					xhr.upload.addEventListener("progress", function (evt) {
						if (evt.lengthComputable) {
							var percentComplete = ( (evt.loaded / evt.total) * 100 ) - 1; 
							$('div.progress > div.progress-bar').css({ "width": percentComplete + "%" }); 
						} 
					}, false);
					//Download progress
					xhr.addEventListener("progress", function (evt) {
						if (evt.lengthComputable) {
							var percentComplete = ( (evt.loaded / evt.total) *100 ) - 1;
							$("div.progress > div.progress-bar").css({ "width": percentComplete + "%" }); 
						} 
					}, false);
					return xhr;
				},
				type: "POST",
				url: contextPath + "/monitorTransaction",
				contentType: 'application/json',
				data: JSON.stringify( jsonInput ),
				success: function(data) {
					var tableResult = $("#table-result").find('tbody')[0];
					$(tableResult).empty();
					if ( null != data ) {
						if ( undefined != data.DataSegment ) {
							if ( null != data.DataSegment.datas && data.DataSegment.datas.length > 0 ) {
								console.log("data segment size : " + data.DataSegment.datas.length);
								for( var i = 0 ; i < data.DataSegment.datas.length ; i++ ) {
									var item = data.DataSegment.datas[i];
									if ( null != item ) {
										
										$(tableResult).append("<tr class='data-row' name='" + item.transactionLogId + "'>");
										var resultBody = $(tableResult).find("tr[name='" + item.transactionLogId + "']")[0];
										
										// $(resultBody).append("<td class='transaction-log-id'>" + item.transactionLogId + "</td>");
										$(resultBody).append("<td class='decision-point'>" + item.param1 + "</td>");
										$(resultBody).append("<td class='ref-code'>" + item.refCode + "</td>");
										$(resultBody).append("<td class='transaction-id'>" + item.transactionId + "</td>");		
										$(resultBody).append("<td class='service-id'>" + item.serviceId + "</td>");
										$(resultBody).append("<td class='activity-type'>" + item.activityType + "</td>");
										$(resultBody).append("<td class='param2'>" + ( item.param2 == null ? "" : item.param2 ) + "</td>");	
										$(resultBody).append("<td class='terminal-type'>" + item.terminalType + "</td>");
										$(resultBody).append("<td class='create-date'>" + item.createDate + "</td>");
										
										$(resultBody).append("<td class='detail'><a href='#' class='more_detail' name='" + item.transactionLogId + "'>Detail</a></td>");
										
										$(tableResult).append("</tr>");

									}	
								}
								
								var resultBody = $(tableResult).find("tr.data-row");
								$.each(resultBody, function(index, names){
									var detailEle = $(resultBody).find("td.detail a")[index];
									$(detailEle).on("click", function() {
										$("#exampleModalLong").modal({
											keyboard: false
										});
										var name = $(detailEle).attr('name');
										getDetailByTransactionLog( name );
									});
								});

								$("div.progress > div.progress-bar").css({ "width": "100%" });
								
							} else {
								$(tableResult).append("<tr>");
								$(tableResult).append("<td colspan='8' align='center'> Data Not Found </td>");
								$(tableResult).append("</tr>");
							}
							
						} else {
							$(tableResult).append("<tr>");
							$(tableResult).append("<td colspan='8' align='center'> Data Not Found </td>");
							$(tableResult).append("</tr>");
						}
					} else {
						$(tableResult).append("<tr>");
						$(tableResult).append("<td colspan='8' align='center'> Data Not Found </td>");
						$(tableResult).append("</tr>");
					}
					enebledObj(search_btn);
					removeProgressbar();
				},
				error: function (error) {
					var tableResult = $("#table-result").find('tbody')[0];
					$(tableResult).empty();
					
					$(tableResult).append("<tr>");
					$(tableResult).append("<td colspan='8' align='center'> Data Not Found </td>");
					$(tableResult).append("</tr>");
					enebledObj(search_btn);
					removeProgressbar();
				}
			});
			
		} else {
			alert( alertMsg );
			enebledObj(search_btn);
		}	
		
	}
	
	var validate = function() {
		
		alertMsg = "";
		
		if ( $(ref_code).val() == "" 
		  && $(transaction_id).val() == "" 	
		  && $(id_no).val() == "" 			
		  && $(decision_service).val() == "" ) {
			alertMsg = "Pleas fill at least 1 condition !";
			return false;
		}
		
		if ( ( $(decision_service).val() != "" || $(service_id).val() != "" )
			&& ( $(ref_code).val() == "" 
			  && $(transaction_id).val() == "" 
			  && $(id_no).val() == "" ) ) {
			alertMsg = "Pleas fill at least 1 other condition !";
			return false;
		}	
		
		return true;
	}
	
	var getDetailByTransactionLog = function(name) {

		var jsonInput = {};
		
		if ( undefined != name ) {
			jsonInput.transactionLogId = name;
		}
		
		console.log("get Transaction Log Id input : " + JSON.stringify(jsonInput));
		
		$.ajax({
			type: "POST",
			url: contextPath + "/monitorBlobData",
			contentType: 'application/json',
			data: JSON.stringify( jsonInput ),
			success: function(data) {
				if ( null != data ) {
					$(modal_title).empty();
					$(jsonContent).empty();
					$(jsonContent).css("overflow", "");
					
					$(modal_title).append("<b>Transaction Log Result : </b>")
									 .append( name );
					
					if ( undefined != data.DataSegment ) {
						if ( null != data.DataSegment.datas && data.DataSegment.datas.length > 0 ) {
							for( var i = 0 ; i < data.DataSegment.datas.length ; i++ ) {
								var item = data.DataSegment.datas[i];
								if ( null != item.binaryData ) {
									var stringTmp = item.binaryData.split(/\n/);
									if ( null != stringTmp && stringTmp.length > 0 ) {
										for( var i = 0 ; i < stringTmp.length ; i++ ) {
											var contentData = stringTmp[i];
											if ( i == ( stringTmp.length - 1) ) {
												try {
													$(jsonContent).append("<pre>" + JSON.stringify( JSON.parse(contentData), undefined, 2 ) + "</pre>");
												} catch ( err ) {
													$(jsonContent).append( htmlEntities(contentData) );
												}
											} else {
												if ( "" != contentData ) {
													$(jsonContent).append( contentData + "<br/>");
												}
											}	
										}
									}
								}
								
								/*if ( null != item ) {
									$(jsonContent).append( item.binaryData );
								} else {
									$(jsonContent).append( JSON.stringify( {}, undefined, 2) );
								}*/
							}
						} else {
							$(jsonContent).append( JSON.stringify( {}, undefined, 2) );
						}
					} else {
						$(jsonContent).append( JSON.stringify( {}, undefined, 2) );
					}
				}	
			}
		});
		
	}
	
	function htmlEntities(str) {
		var htmlString = String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
		return htmlString;
	}

	var getJson = function () {
		
		var json = {};

		if ( $(ref_code).val() != "" ) {
			json.refCode = $(ref_code).val();
		}

		if ( $(transaction_id).val() != "" ) {
			json.transactionId = $(transaction_id).val();
		}

		if ( $(id_no).val() != "" ) {
			json.idNo = $(id_no).val();
		}

		if ( $(decision_service).val() != "" ) {
			json.decisionService = $(decision_service).val();
		}
		
		if ( $(service_id).val() != "" ) {
			json.serviceId = $(service_id).val();
		}

		return json;
	}
}	

var ui = new UI();
ui.init();