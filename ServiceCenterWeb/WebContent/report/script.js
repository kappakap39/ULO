function ReportTransaction() {

	var ref_code 		= $("#ref_code")[0];
	var transaction_id 	= $("#transaction_id")[0];
	var start_date 		= $("#start_date")[0];
	var end_date 		= $("#end_date")[0];
	
	var generate_report = $("#generate_report")[0];
	var clear_btn 		= $("#clear_btn")[0];
	
	var reportData = {};
	var msg = "";
	
	var divTab = $("div.tab-result")[0];
    var resultArea = $("#result-area")[0];
    var WARNING_THRESHOLD = 3;

    var ARRAY_HEADER_CONTENT = [
        "Function Name"
      , "Min"
      , "Max"
      , "Average"
      , "85%"
      , "90%"
      , "95%"
      , "Pass"
      , "Fail"
      , "Total"
    ]

    var ARRAY_HEADER_WARNING = [
        "No."
      , "Transaction ID"
      , "Function Name"
    ]
	
	this.init = function () {
		
		$('div.progress').hide();
		
		$(start_date).datetimepicker({
			locale: 'th',
			defaultDate: "now",
			format: 'DD-MM-YYYY HH:mm:ss',
			icons: {
                time: 'fa fa-clock',
                date: 'fa fa-calendar',
                up: 'fa fa-chevron-up',
                down: 'fa fa-chevron-down',
                previous: 'fa fa-chevron-left',
                next: 'fa fa-chevron-right',
                today: 'fa fa-crosshairs',
                clear: 'fa fa-trash',
                close: 'fa fa-times'
			}
        });
		
		$(end_date).datetimepicker({
			locale: 'th',
			format: 'DD-MM-YYYY HH:mm:ss',
			icons: {
                time: 'fa fa-clock',
                date: 'fa fa-calendar',
                up: 'fa fa-chevron-up',
                down: 'fa fa-chevron-down',
                previous: 'fa fa-chevron-left',
                next: 'fa fa-chevron-right',
                today: 'fa fa-crosshairs',
                clear: 'fa fa-trash',
                close: 'fa fa-times'
			}
		});
		
	}
	
	$(generate_report).on("click", function() {
		generate();
	});
	
	$(clear_btn).on("click", function() {
		$(ref_code).val("");
		$(transaction_id).val("");	
		$(start_date).val(""); 			
		$(end_date).val("");
		removeProgressbar();
	});
	
	var createProgressbar = function() {
		var divProgress = $('div.progress')[0];
		if ( undefined != divProgress ) {
			$(divProgress).show();
			$(divProgress).append('<div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 100%"></div>');
		}
	}

	var removeProgressbar = function() {
		var divProgress = $('div.progress')[0];
		if ( undefined != divProgress ) {
			$(divProgress).hide();
			$(divProgress).empty();
		}
	}
	
	var generate = function () {
		
		if ( validation() ) {
			
			var jsonInput = getJson();
			
			console.log( "Json input : " + JSON.stringify(jsonInput) );
			
			createProgressbar();
			
			$.ajax({
				type: "POST",
				url: contextPath + "/reportTransaction",
				contentType: 'application/json',
				data: JSON.stringify( jsonInput ),
				success: function(data) {
					if ( null != data ) {
						if ( null != data ) {
							reportData = data;
							initialTab();
						}
					} else {
						console.log(" data not found ");
					}
					removeProgressbar();
				},
				error: function (error) {
					console.log(" Error : " + error);
					var tableResult = $("#table-result").find('tbody')[0];
					$(tableResult).empty();
					removeProgressbar();
				}
			});
		} else {
			alert( msg );
		}
		
	}
	
	var getJson = function () {
		
		var json = {};
		
		if ( $(ref_code).val() != "" ) {
			json.refCode = $(ref_code).val();
		}
		
		if ( $(transaction_id).val() != "" ) {
			json.transactionId = $(transaction_id).val();
		}
		
		if ( $(start_date).val() != "" ) {
			json.startDate = $(start_date).val();
		}
		
		if ( $(end_date).val() != "" ) {
			json.endDate = $(end_date).val();
		}
		
		return json;
		
	}
	
	var validation = function() {
		if ( $(start_date).val() == "" ) {
			msg = "Please input Start Date!";
			return false;
		}
		if ( $(start_date).val() == "" && $(end_date).val() == "" ) {
			msg = "Please input Start date and End date!";
			return false;
		}
		if ( $(start_date).val() == "" && $(end_date).val() != "" ) {
			msg = "Please input Start date!";
			return false;
		}
		return true;
	}
	
	var initialTab = function () {

        if ( undefined != divTab ) {
        	$(divTab).empty();
        	$(divTab).append("<ul class='nav nav-tabs' style='cursor: default'>");
            var listItem = $(divTab).find("ul.nav")[0];
            for ( var action in reportData.statMap ) {
                $(listItem).append("<li class='nav-item' name='" + action + "'>");
                var item = $(listItem).find("li[name='" + action + "']")[0];
                if ( reportData.statMap.hasOwnProperty(action) ) {
                    $(item).append("<a class='nav-link' name='" + action + "'>" + action + "</a>");
                }
                $(listItem).append("</li>");
            }
            $(listItem).append("<li class='nav-item' name='warning'>");
            var item = $(listItem).find("li[name='warning']")[0];
            $(item).append("<a class='nav-link' name='warning'> Warning </a>");

            $(divTab).append("</ul>");

            var navTabs = $(divTab).find("ul > li.nav-item");
            $.each(navTabs, function(index, names){
                var tab = $(navTabs).find("a")[index];
                $(tab).on("click", function() {
                    removeTabActive();
                    setActive( this.name );
                    getData( this.name );
                });
            });

            var firstItem = $(divTab).find("li.nav-item > a")[0];
            setActive( $(firstItem)[0].name );
            getData( $(firstItem)[0].name );
        }

    }

    var setActive = function ( name ) {
        var item = $(divTab).find("li.nav-item[name='" + name + "'] > a")[0];
        if ( undefined != item ) {
            $( item ).attr("class", "nav-link active");
        }
    }

    var removeTabActive = function ( name ) {
        var navTabs = $(divTab).find("ul > li.nav-item");
        $.each(navTabs, function(index, names){
            var tab = $(navTabs).find("a")[index];
            $(tab).attr("class", "nav-link");
        });
    }

    var getData = function ( name ) {
        
        $(resultArea).empty();
        
        if ( name != "warning" ) {
            var statTypeEntrys = reportData.statMap[name];
            for ( var statType in statTypeEntrys ) {
                var count = 0;
                var titleEntrys = statTypeEntrys[statType];
                $(resultArea).append("<table class='table table-sm' name='" + statType + "'>");
                var tableObj = $(resultArea).find("table[name='" + statType + "']")[0];
                // Create Header
                $(tableObj).append("<thead>");
                var header = $(tableObj).find("thead");
                    $(header).append("<tr>");
                    var headerRow = $(header).find("tr")[0];
                        $(headerRow).append("<th colspan='"+ ARRAY_HEADER_CONTENT.length +"' style='text-align: center;background-color: #72ABFF;'>" + statType + "</th>");
                    $(header).append("</tr>");
                    $(header).append("<tr>");
                    var headerRow = $(header).find("tr")[1];
                        for (var header in ARRAY_HEADER_CONTENT) {
                            $(headerRow).append("<th style='background-color: #CED5E1;'>" + ARRAY_HEADER_CONTENT[header] + "</th>");
                        }
                    $(header).append("</tr>");
                $(tableObj).append("</thead>");

                // Create Table body
                $(tableObj).append("<tbody>");
                var count = 0 ;
                for (var titleEntry in titleEntrys) {
                    var responseStat = titleEntrys[titleEntry].responseStat;
                    var failCount = Number(responseStat.totalCount) - Number(responseStat.passCount);
                    var isWarn = Number(responseStat.p95) >= WARNING_THRESHOLD ? true : false;
                    var body = $(tableObj).find("tbody");
                    if ( isWarn ) {
                        $(body).append("<tr style='background-color: #F08080; color: white;'>");
                    } else {
                        $(body).append("<tr>");
                    }
                    var bodyRow = $(body).find("tr")[count];
                        $(bodyRow).append("<td>" + responseStat.title + "</td>");
                        $(bodyRow).append("<td>" + responseStat.min + "</td>");
                        $(bodyRow).append("<td>" + responseStat.max + "</td>");
                        $(bodyRow).append("<td>" + responseStat.avg + "</td>");
                        $(bodyRow).append("<td>" + responseStat.p85 + "</td>");
                        $(bodyRow).append("<td>" + responseStat.p90 + "</td>");
                        $(bodyRow).append("<td>" + responseStat.p95 + "</td>");
                        $(bodyRow).append("<td>" + responseStat.passCount + "</td>");
                        $(bodyRow).append("<td>" + failCount + "</td>");
                        $(bodyRow).append("<td>" + responseStat.totalCount + "</td>");
                    $(body).append("</tr>");
                    count = count + 1;

                }    
                $(tableObj).append("</tbody>");
                $(resultArea).append("</table></br>");
            } 
        } else {
            var warningTransactionIdMaps = reportData.warningTransactionIdMap;

            $(resultArea).append("<table class='table table-sm' name='warning'>");
            var tableObj = $(resultArea).find("table[name='warning']")[0];
            // Create Header
            $(tableObj).append("<thead>");
            var header = $(tableObj).find("thead");
                $(header).append("<tr>");
                var headerRow = $(header).find("tr")[0];
                    for (var header in ARRAY_HEADER_WARNING) {
                        $(headerRow).append("<th style='background-color: #CED5E1;'>" + ARRAY_HEADER_WARNING[header] + "</th>");
                    }
                $(header).append("</tr>");
            $(tableObj).append("</thead>");

            // Create Table body
            $(tableObj).append("<tbody>");
            var runningNo = 1;
            for (var warningTransactionIdMap in warningTransactionIdMaps) {
                var activityTypeList = warningTransactionIdMaps[warningTransactionIdMap];
                for (var activityType in activityTypeList) {
                    var body = $(tableObj).find("tbody");
                    $(body).append("<tr>");
                    var bodyRow = $(body).find("tr")[runningNo-1];
                        $(bodyRow).append("<td>" + runningNo + "</td>");
                        $(bodyRow).append("<td>" + warningTransactionIdMap + "</td>");
                        $(bodyRow).append("<td>" + activityTypeList[activityType] + "</td>");
                    $(body).append("</tr>");
                    runningNo = runningNo + 1;
                }
            }
            $(tableObj).append("</tbody>");
            $(resultArea).append("</table></br>");
        }
    }
}

var reportTransaction = new ReportTransaction();
reportTransaction.init();