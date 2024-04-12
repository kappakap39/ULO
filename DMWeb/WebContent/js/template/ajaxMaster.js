// ajaxMaster.js

// ##################################### AJAX main function ####################################

function ajax(url,handlerFunc,mode){
	var req = initRequest();
	req.onreadystatechange = function() {
		if (req.readyState == 4) {
			if (req.status == 200) {
				var xmlDoc = req.responseXML;
				if (xmlDoc.documentElement == null || !xmlDoc.documentElement.hasChildNodes() || xmlDoc.firstChild.nodeName == "parsererror") {
					//alert('Result XML Error.\n'+req.responseXML);
				} else {
					handlerFunc(xmlDoc);
				}
			} else if (req.status == 204){
				//clearTable();
			}
		}
	};
	var ran_number= Math.random()*4;
	url=url+'&a='+ran_number;
	//alert(url);
	req.open(mode, url, true);
	req.send(null);
}

function initRequest() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		isIE = true;
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
}

//######################################################Paste###################################################

function ajaxAddDetailHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var checker = items[0].getAttribute("value");
	if(checker == 'ERROR'){
		alert(items[0].firstChild.nodeValue);
	}else{
		var listValue = new Array();
		document.getElementById('trNRF').style.display = 'none';
		var value = items[0].getAttribute("value");
		listValue = value.split('|');
		var tenorName = items[0].firstChild.nodeValue;
		var productType = items[1].firstChild.nodeValue;
		var balloonRV = items[2].firstChild.nodeValue;
		var BalloonAmt = items[3].firstChild.nodeValue;
		var tbody = document.getElementById('tableData').getElementsByTagName("TBODY")[0];
		var newElem = document.createElement('<input name="chkBox" type="checkbox" value=""/>');
		newElem.value = value;
	    var row = document.createElement("TR");
	    row.setAttribute("align","center");
	    row.setAttribute("id",listValue[0]+listValue[1]);
	    var td1 = document.createElement("TD");
	    td1.setAttribute("align","center");
	    td1.setAttribute("width","4%");
	    td1.appendChild(newElem);
	    var td2 = document.createElement("TD");
	    td2.appendChild(document.createTextNode(tenorName));
		td2.setAttribute("align","center");
		td2.setAttribute("width","24%");
		td2.setAttribute("onmouseover", function(){setMouseOver(this);});
		td2.setAttribute("onmouseout", function(){setMouseOut(this);});
		td2.setAttribute("onclick", function(){showView(value);});
	    var td3 = document.createElement("TD");
	    td3.appendChild (document.createTextNode(productType));
		td3.setAttribute("align","center");
		td3.setAttribute("width","24%");
		var td4 = document.createElement("TD");
	    td4.appendChild (document.createTextNode(balloonRV));
		td4.setAttribute("align","center");
		td4.setAttribute("width","24%");
		var td5 = document.createElement("TD");
	    td5.appendChild (document.createTextNode(BalloonAmt));
		td5.setAttribute("align","center");
		td5.setAttribute("width","24%");
		row.appendChild(td1);
	    row.appendChild(td2);
	    row.appendChild(td3);
		row.appendChild(td4);
		row.appendChild(td5);
	    tbody.appendChild(row);
	    document.getElementById('saveFrameSub').style.display = 'none';
	    document.getElementById('hSetSessionList').value = 'N';
	}
}

function ajaxEditDetailHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var checker = items[0].getAttribute("value");
	if(checker == 'ERROR'){
		alert(items[0].firstChild.nodeValue);
	}else{
		document.getElementById('trNRF').style.display = 'none';
		var value = items[0].getAttribute("value");
		var tenorName = items[0].firstChild.nodeValue;
		var changeProduct = items[1].getAttribute("value");
		var productType = items[1].firstChild.nodeValue;
		var balloonRV = items[2].firstChild.nodeValue;
		var BalloonAmt = items[3].firstChild.nodeValue;
		var listValue = new Array();
		listValue = value.split('|');
		var nameTR = listValue[0]+listValue[1];
		if(changeProduct == 'Y'){
			if(listValue[1] == 'HP'){
				nameTR = listValue[0]+'LS';
			}else{
				nameTR = listValue[0]+'HP';
			}
		}
		var tbody = document.getElementById('tableData').getElementsByTagName("TBODY")[0];
		tbody.removeChild(document.getElementById(nameTR));
		var newElem = document.createElement('<input name="chkBox" type="checkbox" value=""/>');
		newElem.value = value;
	    var row = document.createElement("TR");
	    row.setAttribute("align","center");
	    row.setAttribute("id",listValue[0]+listValue[1]);
	    var td1 = document.createElement("TD");
	    td1.setAttribute("align","center");
	    td1.setAttribute("width","4%");
	    td1.appendChild(newElem);
	    var td2 = document.createElement("TD");
	    td2.appendChild(document.createTextNode(tenorName));
		td2.setAttribute("align","center");
		td2.setAttribute("width","24%");
		td2.setAttribute("onmouseover", function(){setMouseOver(this);});
		td2.setAttribute("onmouseout", function(){setMouseOut(this);});
		td2.setAttribute("onclick", function(){showView(value);});
	    var td3 = document.createElement("TD");
	    td3.appendChild (document.createTextNode(productType));
		td3.setAttribute("align","center");
		td3.setAttribute("width","24%");
		var td4 = document.createElement("TD");
	    td4.appendChild (document.createTextNode(balloonRV));
		td4.setAttribute("align","center");
		td4.setAttribute("width","24%");
		var td5 = document.createElement("TD");
	    td5.appendChild (document.createTextNode(BalloonAmt));
		td5.setAttribute("align","center");
		td5.setAttribute("width","24%");
		row.appendChild(td1);
	    row.appendChild(td2);
	    row.appendChild(td3);
		row.appendChild(td4);
		row.appendChild(td5);
	    tbody.appendChild(row);
	    document.getElementById('saveFrameSub').style.display = 'none';
	    document.getElementById('hSetSessionList').value = 'N';
	}
}

function ajaxDeleteDetailHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var tbody = document.getElementById('tableData').getElementsByTagName("TBODY")[0];
	for (var i = 0; i < items.length; i++){
		var itemValue=items[i].getAttribute("value");
		var itemDisplay=items[i].firstChild.nodeValue;
		if(itemValue != 'EMPTY_LIST'){
			tbody.removeChild(document.getElementById(itemValue));
		}else{
			document.getElementById('trNRF').style.display = '';
		}
	}
}

function ajaxLoadClassiHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var selected = items[0].firstChild.nodeValue;
	document.getElementById('cusType_search').options.length = 1;
	if(selected == 'BLANK'){
		selected = '';
	}
	for (var i = 0; i < items.length; i++){
		var itemValue=items[i].getAttribute("value");
		var itemDisplay=items[i].firstChild.nodeValue;
		if(itemDisplay=="null"){
			itemDisplay="";
		}else{
			document.getElementById('cusType_search').options[i] = new Option(itemDisplay,itemValue);		
		}
	}
	//document.getElementById('cusType_search').value = selected;
}

function ajaxAddInterestRateHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var checker = items[0].getAttribute("value");
	if(checker == 'ERROR'){
		alert(items[0].firstChild.nodeValue);
	}else{
		var listValue = new Array();
		document.getElementById('ir_nrf').style.display = 'none';
		var value = items[0].getAttribute("value");
		listValue = value.split('|');
		var rate = items[0].firstChild.nodeValue;
		var kindName = items[1].firstChild.nodeValue;
		var tbody = document.getElementById('tableDataRate').getElementsByTagName("TBODY")[0];
		var newElem = document.createElement('<input name="chkBoxRate" type="checkbox" value=""/>');
		newElem.value = value;
	    var row = document.createElement("TR");
	    row.setAttribute("align","center");
	    row.setAttribute("id",listValue[0]+listValue[1]);
	    var td1 = document.createElement("TD");
	    td1.setAttribute("align","center");
	    td1.setAttribute("width","8%");
	    td1.appendChild(newElem);
	    var td2 = document.createElement("TD");
	    td2.appendChild(document.createTextNode(rate));
		td2.setAttribute("align","center");
		td2.setAttribute("width","46%");
		td2.setAttribute("onmouseover", function(){setMouseOver(this);});
		td2.setAttribute("onmouseout", function(){setMouseOut(this);});
		td2.setAttribute("onclick", function(){showViewRate(value);});
	    var td3 = document.createElement("TD");
	    td3.appendChild (document.createTextNode(kindName));
		td3.setAttribute("align","center");
		td3.setAttribute("width","46%");
		row.appendChild(td1);
	    row.appendChild(td2);
	    row.appendChild(td3);
	    tbody.appendChild(row);
	    document.getElementById('rateFrame').style.display = 'none';
	    document.getElementById('hCheckDupRate').value = 'Y';
	}
	var irNrf = document.getElementById('ir_nrf').style.display;
	if(irNrf == 'none'){
		document.getElementById('delete_rate').disabled = false;
		document.getElementById('edit_rate').disabled = false;
	}
}

function ajaxEditInterestRateHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var listValue = new Array();
	document.getElementById('ir_nrf').style.display = 'none';
	var value = items[0].getAttribute("value");
	listValue = value.split('|');
	var rate = items[0].firstChild.nodeValue;
	var kindName = items[1].firstChild.nodeValue;
	var tbody = document.getElementById('tableDataRate').getElementsByTagName("TBODY")[0];
	var nameTR = listValue[0]+listValue[1];
	tbody.removeChild(document.getElementById(nameTR));
	var newElem = document.createElement('<input name="chkBoxRate" type="checkbox" value=""/>');
	newElem.value = value;
    var row = document.createElement("TR");
    row.setAttribute("align","center");
    row.setAttribute("id",listValue[0]+listValue[1]);
    var td1 = document.createElement("TD");
    td1.setAttribute("align","center");
    td1.setAttribute("width","8%");
    td1.appendChild(newElem);
    var td2 = document.createElement("TD");
    td2.appendChild(document.createTextNode(rate));
	td2.setAttribute("align","center");
	td2.setAttribute("width","46%");
	td2.setAttribute("onmouseover", function(){setMouseOver(this);});
	td2.setAttribute("onmouseout", function(){setMouseOut(this);});
	td2.setAttribute("onclick", function(){showViewRate(value);});
    var td3 = document.createElement("TD");
    td3.appendChild (document.createTextNode(kindName));
	td3.setAttribute("align","center");
	td3.setAttribute("width","46%");
	row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    tbody.appendChild(row);
    document.getElementById('rateFrame').style.display = 'none';
    document.getElementById('hCheckDupRate').value = 'Y';
}

function ajaxDeleteInterestRateHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var tbody = document.getElementById('tableDataRate').getElementsByTagName("TBODY")[0];
	for (var i = 0; i < items.length; i++){
		var itemValue=items[i].getAttribute("value");
		var itemDisplay=items[i].firstChild.nodeValue;
		if(itemValue != 'EMPTY_LIST'){
			tbody.removeChild(document.getElementById(itemValue));
			document.getElementById('hCheckDupRate').value = 'N';
		}else{
			document.getElementById('ir_nrf').style.display = '';
			document.getElementById('delete_rate').disabled = true;
			document.getElementById('edit_rate').disabled = true;
		}
	}
	document.getElementById('rateFrame').style.display = 'none';
}

function ajaxAddInterestDetailHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var checker = items[0].getAttribute("value");
	if(checker == 'ERROR'){
		alert(items[0].firstChild.nodeValue);
	}else{
		var listValue = new Array();
		document.getElementById('id_nrf').style.display = 'none';
		var value = items[0].getAttribute("value");
		listValue = value.split('|');
		var tier = items[0].firstChild.nodeValue;
		var terms = items[1].firstChild.nodeValue;
		var downPayment = items[2].firstChild.nodeValue;
		var mode = items[3].firstChild.nodeValue;
		var tbody = document.getElementById('tableDataDetail').getElementsByTagName("TBODY")[0];
		var newElem = document.createElement('<input name="chkBoxDetail" type="checkbox" value=""/>');
		newElem.value = value;
	    var row = document.createElement("TR");
	    row.setAttribute("align","center");
	    row.setAttribute("id",listValue[0]+listValue[1]+listValue[2]+listValue[3]);
	    var td1 = document.createElement("TD");
	    td1.setAttribute("align","center");
	    td1.setAttribute("width","4%");
	    td1.appendChild(newElem);
	    var td2 = document.createElement("TD");
	    td2.appendChild(document.createTextNode(tier));
		td2.setAttribute("align","center");
		td2.setAttribute("width","24%");
		td2.setAttribute("onmouseover", function(){setMouseOver(this);});
		td2.setAttribute("onmouseout", function(){setMouseOut(this);});
		td2.setAttribute("onclick", function(){showViewDetail(value);});
	    var td3 = document.createElement("TD");
	    td3.appendChild (document.createTextNode(terms));
		td3.setAttribute("align","center");
		td3.setAttribute("width","24%");
		var td4 = document.createElement("TD")
	    td4.appendChild (document.createTextNode(downPayment));
		td4.setAttribute("align","center");
		td4.setAttribute("width","24%");
		var td5 = document.createElement("TD");
		if(mode == 'B'){
			mode = 'Beginning';
		}else{
			mode = 'Ending';
		}
	    td5.appendChild (document.createTextNode(mode));
		td5.setAttribute("align","center");
		td5.setAttribute("width","24%");
		row.appendChild(td1);
	    row.appendChild(td2);
	    row.appendChild(td3);
		row.appendChild(td4);
		row.appendChild(td5);
	    tbody.appendChild(row);
	    document.getElementById('interestFrame').style.display = 'none';
	    document.getElementById('hCheckDupDetail').value = 'Y';
	}
	var idNrf = document.getElementById('id_nrf').style.display;
	if(idNrf == 'none'){
		document.getElementById('delete_interest').disabled = false;
		document.getElementById('edit_interest').disabled = false;
	}
}

function ajaxEditInterestDetailHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var checker = items[0].getAttribute("value");
	if(checker == 'ERROR'){
		alert('Operation Fail! Please Contract Admin');
	}else{
		document.getElementById('id_nrf').style.display = 'none';
		document.getElementById('interestFrame').style.display = 'none';
		document.getElementById('hCheckDupDetail').value = 'Y';
	}
}

function ajaxDeleteInterestDetailHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var tbody = document.getElementById('tableDataDetail').getElementsByTagName("TBODY")[0];
	for (var i = 0; i < items.length; i++){
		var itemValue=items[i].getAttribute("value");
		var itemDisplay=items[i].firstChild.nodeValue;
		if(itemValue != 'EMPTY_LIST'){
			tbody.removeChild(document.getElementById(itemValue));
			document.getElementById('hCheckDupDetail').value = 'N';
		}else{
			document.getElementById('id_nrf').style.display = '';
			document.getElementById('delete_interest').disabled = true;
			document.getElementById('edit_interest').disabled = true;
		}
	}
	document.getElementById('interestFrame').style.display = 'none';
}

function ajaxLoadInterestDetailHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var disabled = '';
	var checker = items[1].getAttribute("value");
	var row = document.getElementById('divRowRate');
	row.innerHTML = '<table cellpadding="0" cellspacing="0" width="80%" align="center" border="1" id="tableDataRate"></table>';
	if(checker == 'ERROR'){
		document.getElementById('rateFrame').style.display = 'none';
		document.getElementById('ir_nrf').style.display = '';
	}else{
		document.getElementById('ir_nrf').style.display = 'none';
		var tbody = document.getElementById('tableDataRate').getElementsByTagName("TBODY")[0];
		for (var i = 0; i < items.length; i++){
			var value = items[i].getAttribute("value");
			var kindName = items[i].firstChild.nodeValue;
		    if(value != 'MODE_LOAD'){
		    	var listValue = new Array();
				listValue = value.split('|');
				var rate = listValue[0];
				var newElem = document.createElement('<input name="chkBoxRate" type="checkbox" value="" '+disabled+'/>');
				newElem.value = value;
			    var row = document.createElement("TR");
			    row.setAttribute("align","center");
			    row.setAttribute("id",listValue[0]+listValue[1]);
			    var td1 = document.createElement("TD");
			    td1.setAttribute("align","center");
			    td1.setAttribute("width","8%");
			    td1.appendChild(newElem);
			    var td2 = document.createElement("TD");
			    td2.appendChild(document.createTextNode(rate));
				td2.setAttribute("align","center");
				td2.setAttribute("width","46%");
				td2.setAttribute("onmouseover", function(){setMouseOver(this);});
				td2.setAttribute("onmouseout", function(){setMouseOut(this);});
				td2.onclick = new Function("showViewRate('"+value+"');");//setAttribute("onclick", function(){showViewRate(value);});
			    var td3 = document.createElement("TD");
			    td3.appendChild (document.createTextNode(kindName));
				td3.setAttribute("align","center");
				td3.setAttribute("width","46%");
				row.appendChild(td1);
			    row.appendChild(td2);
			    row.appendChild(td3);
			    tbody.appendChild(row);
			}else{
				if(kindName == 'VIEW'){
					disabled = 'disabled';
					document.getElementById('add_rate').disabled = true;
					document.getElementById('edit_rate').disabled = true;
					document.getElementById('delete_rate').disabled = true;
				}else{
					document.getElementById('add_rate').disabled = false;
					document.getElementById('edit_rate').disabled = false;
					document.getElementById('delete_rate').disabled = false;
				}
			}
	    }
	    document.getElementById('rateFrame').style.display = 'none';
		document.getElementById('hCheckDupRate').value = 'Y';
	}
}

function ajaxLoadSubsidyHandlerFunc(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	document.getElementById('subsidy').options.length = 1;
	for (var i = 0; i < items.length; i++){
		var itemValue=items[i].getAttribute("value");
		var itemDisplay=items[i].firstChild.nodeValue;
		if(itemDisplay=="null"){
			itemDisplay="";
		}else{
			document.getElementById('subsidy').options[i+1] = new Option(itemDisplay,itemValue);		
		}
	}
}
///scoring
function ajaxLoadSpecificHandlerFunc(xmlDoc){
    
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var disabled = '';
	var checker = items[1].getAttribute("value");
	var row = document.getElementById('specificValue');
	row.innerHTML = '<table cellpadding="0" cellspacing="0" width="80%" align="center" border="1" id="tableDataRate"></table>';
	if(checker == 'ERROR'){
		document.getElementById('rateFrame').style.display = 'none';
		document.getElementById('ir_nrf').style.display = '';
	}else{
		document.getElementById('ir_nrf').style.display = 'none';
		var tbody = document.getElementById('tableDataRate').getElementsByTagName("TBODY")[0];
		for (var i = 0; i < items.length; i++){
			var value = items[i].getAttribute("value");
			var kindName = items[i].firstChild.nodeValue;
		    if(value != 'MODE_LOAD'){
		    	var listValue = new Array();
				listValue = value.split('|');
				var rate = listValue[0];
				var newElem = document.createElement('<input name="chkBoxRate" type="checkbox" value="" '+disabled+'/>');
				newElem.value = value;
			    var row = document.createElement("TR");
			    row.setAttribute("align","center");
			    row.setAttribute("id",listValue[0]+listValue[1]);
			    var td1 = document.createElement("TD");
			    td1.setAttribute("align","center");
			    td1.setAttribute("width","8%");
			    td1.appendChild(newElem);
			    var td2 = document.createElement("TD");
			    td2.appendChild(document.createTextNode(rate));
				td2.setAttribute("align","center");
				td2.setAttribute("width","46%");
				td2.setAttribute("onmouseover", function(){setMouseOver(this);});
				td2.setAttribute("onmouseout", function(){setMouseOut(this);});
				td2.onclick = new Function("showViewRate('"+value+"');");//setAttribute("onclick", function(){showViewRate(value);});
			    var td3 = document.createElement("TD");
			    td3.appendChild (document.createTextNode(kindName));
				td3.setAttribute("align","center");
				td3.setAttribute("width","46%");
				row.appendChild(td1);
			    row.appendChild(td2);
			    row.appendChild(td3);
			    tbody.appendChild(row);
			}else{
				if(kindName == 'VIEW'){
					disabled = 'disabled';
					document.getElementById('add_rate').disabled = true;
					document.getElementById('edit_rate').disabled = true;
					document.getElementById('delete_rate').disabled = true;
				}else{
					document.getElementById('add_rate').disabled = false;
					document.getElementById('edit_rate').disabled = false;
					document.getElementById('delete_rate').disabled = false;
				}
			}
	    }
	    document.getElementById('rateFrame').style.display = 'none';
		document.getElementById('hCheckDupRate').value = 'Y';
	}

}

// ######################################## AJAX functions ########################################################

function ajaxAddDetail(handleFunc,mode,tenor,tenorName,productType,balloonRV,balloonAmount,deposit,serverWeb,setList){
	var uri= serverWeb + "/AddDetailSchemeServlet?mode="+mode+"&tenor="+tenor+"&tenorName="+tenorName+"&productType="+productType+"&balloonRV="+balloonRV+"&balloonAmount="+balloonAmount+"&deposit="+deposit+"&setList="+setList;
	ajax(uri, handleFunc , "GET");
}

function ajaxEditDetail(handleFunc,mode,tenor,tenorName,productType,balloonRV,balloonAmount,deposit,serverWeb,editProductType){
	var uri= serverWeb + "/EditDetailSchemeServlet?mode="+mode+"&tenor="+tenor+"&tenorName="+tenorName+"&productType="+productType+"&balloonRV="+balloonRV+"&balloonAmount="+balloonAmount+"&deposit="+deposit+"&editProductType="+editProductType;
	ajax(uri, handleFunc , "GET");
}

function ajaxDeleteDetail(handleFunc,listDelete,serverWeb){
	var uri= serverWeb + "/DeleteDetailSchemeServlet?listDelete="+listDelete;
	ajax(uri, handleFunc , "GET");
}

function ajaxLoadClassi(handleFunc,scoreCode,serverWeb,selected){
	var uri= serverWeb + "/LoadCustomerTypeServlet?scoreCode="+scoreCode+"&selected="+selected;
	ajax(uri, handleFunc , "GET");
}
function ajaxLoadSpecific(handleFunc,itemCode,serverWeb){
	var uri= serverWeb + "/LoadSpecificValueServlet?itemCode="+itemCode;
	ajax(uri, handleFunc , "GET");
}

function ajaxAddInterestRate(handleFunc,rate,interestKind,maxYear,subsidy,incComm,perComm,serverWeb,checkDup,kindName){
	var uri= serverWeb + "/AddInterestRateServlet?rate="+rate+"&interestKind="+interestKind+"&maxYear="+maxYear+"&subsidy="+subsidy+"&incComm="+incComm+"&perComm="+perComm+"&checkDup="+checkDup+"&kindName="+kindName;
	ajax(uri, handleFunc , "GET");
}

function ajaxEditInterestRate(handleFunc,rate,interestKind,maxYear,subsidy,incComm,perComm,serverWeb,kindName){
	var uri= serverWeb + "/EditInterestRateServlet?rate="+rate+"&interestKind="+interestKind+"&maxYear="+maxYear+"&subsidy="+subsidy+"&incComm="+incComm+"&perComm="+perComm+"&kindName="+kindName;
	ajax(uri, handleFunc , "GET");
}

function ajaxDeleteInterestRate(handleFunc,listDelete,serverWeb){
	var uri= serverWeb + "/DeleteInterestRateServlet?listDelete="+listDelete;
	ajax(uri, handleFunc , "GET");
}

function ajaxAddInterestDetail(handleFunc,tier,terms,downPayment,mode,serverWeb,checkDup,termsName,downPaymentName){
	var uri= serverWeb + "/AddInterestDetailServlet?tier="+tier+"&terms="+terms+"&downPayment="+downPayment+"&mode="+mode+"&checkDup="+checkDup+"&termsName="+termsName+"&downPaymentName="+downPaymentName;
	ajax(uri, handleFunc , "GET");
}

function ajaxEditInterestDetail(handleFunc,tier,terms,downPayment,mode,serverWeb,termsName,downPaymentName){
	var uri= serverWeb + "/EditInterestDetailServlet?tier="+tier+"&terms="+terms+"&downPayment="+downPayment+"&mode="+mode+"&termsName="+termsName+"&downPaymentName="+downPaymentName;
	ajax(uri, handleFunc , "GET");
}

function ajaxDeleteInterestDetail(handleFunc,listDelete,serverWeb){
	var uri= serverWeb + "/DeleteInterestDetailServlet?listDelete="+listDelete;
	ajax(uri, handleFunc , "GET");
}

function ajaxLoadInterestDetail(handleFunc,value,serverWeb,modeLoad){
	var uri= serverWeb + "/LoadInterestDetailServlet?value="+value+"&modeLoad="+modeLoad;
	ajax(uri, handleFunc , "GET");
}

function ajaxLoadSubsidy(handleFunc,start_date,end_date,serverWeb){
	var uri= serverWeb + "/LoadSubsidyServlet?start_date="+start_date+"&end_date="+end_date;
	ajax(uri, handleFunc , "GET");
}

function retrieveScheme(scheme_code){
	ajaxRetrieveScheme(setSchemeData,scheme_code);
}

function ajaxRetrieveScheme(setSchemeData,scheme_code){
	var uri= "/ORIGWeb/RetrieveTierInfoForSchemeServlet?scheme_code="+scheme_code;
	ajax(uri, setSchemeData , "POST");
}

function setSchemeData(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	var checker = items[0].getAttribute("value");
	if(checker == 'ERROR'){
		alert(items[0].firstChild.nodeValue);
	}else{
		var value = items[0].firstChild.nodeValue;
		var listValue = new Array();
		listValue = value.split('|');
		document.getElementById('first_interest_rate').value = listValue[0];
		document.getElementById('first_tier_term').value = listValue[1];
		document.getElementById('second_interest_rate').value = listValue[2];
		document.getElementById('second_tier_term').value = listValue[3];
		document.getElementById('third_interest_rate').value = listValue[4];
		document.getElementById('third_tier_term').value = listValue[5];
		document.getElementById('forth_interest_rate').value = listValue[6];
		document.getElementById('forth_tier_term').value = listValue[7];		
	}
}

//######################################## Function call Ajax ####################################################
//Balloon
function addDetail(mode,tenor,tenorName,productType,balloonRV,balloonAmount,deposit,serverWeb,setList){
	ajaxAddDetail(ajaxAddDetailHandlerFunc,mode,tenor,tenorName,productType,balloonRV,balloonAmount,deposit,serverWeb,setList);
}

function editDetail(mode,tenor,tenorName,productType,balloonRV,balloonAmount,deposit,serverWeb,editProductType){
	ajaxEditDetail(ajaxEditDetailHandlerFunc,mode,tenor,tenorName,productType,balloonRV,balloonAmount,deposit,serverWeb,editProductType);
}

function deleteDetail(listDelete,serverWeb){
	ajaxDeleteDetail(ajaxDeleteDetailHandlerFunc,listDelete,serverWeb);
}
//ScoreClassification
function loadClassi(scoreCode,serverWeb,selected){
	ajaxLoadClassi(ajaxLoadClassiHandlerFunc,scoreCode,serverWeb,selected);
}
//Scoring
function loadSpecific(itemCode,serverWeb){
	ajaxLoadSpecific(ajaxLoadSpecificHandlerFunc,itemCode,serverWeb);
}
//Interest
function addInterestRate(rate,interestKind,maxYear,subsidy,incComm,perComm,serverWeb,checkDup,kindName){
	ajaxAddInterestRate(ajaxAddInterestRateHandlerFunc,rate,interestKind,maxYear,subsidy,incComm,perComm,serverWeb,checkDup,kindName);
}

function editInterestRate(rate,interestKind,maxYear,subsidy,incComm,perComm,serverWeb,kindName){
	ajaxEditInterestRate(ajaxEditInterestRateHandlerFunc,rate,interestKind,maxYear,subsidy,incComm,perComm,serverWeb,kindName);
}

function deleteInterestRate(listDelete,serverWeb){
	ajaxDeleteInterestRate(ajaxDeleteInterestRateHandlerFunc,listDelete,serverWeb);
}

function addInterestDetail(tier,terms,downPayment,mode,serverWeb,checkDup,termsName,downPaymentName){
	ajaxAddInterestDetail(ajaxAddInterestDetailHandlerFunc,tier,terms,downPayment,mode,serverWeb,checkDup,termsName,downPaymentName);
}

function editInterestDetail(tier,terms,downPayment,mode,serverWeb,termsName,downPaymentName){
	ajaxEditInterestDetail(ajaxEditInterestDetailHandlerFunc,tier,terms,downPayment,mode,serverWeb,termsName,downPaymentName);
}

function deleteInterestDetail(listDelete,serverWeb){
	ajaxDeleteInterestDetail(ajaxDeleteInterestDetailHandlerFunc,listDelete,serverWeb);
}

function loadInterestDetail(value,serverWeb,modeLoad){
	ajaxLoadInterestDetail(ajaxLoadInterestDetailHandlerFunc,value,serverWeb,modeLoad);
}

function loadSubsidyAjax(start_date,end_date,serverWeb){
	ajaxLoadSubsidy(ajaxLoadSubsidyHandlerFunc,start_date,end_date,serverWeb);
}

function displayInnerHtml(xmlDoc){
	var root = xmlDoc.documentElement;
	var items = root.childNodes;
	for (var i = 0; i < items.length; i++){
		if(items[i].tagName == "item"){
			var itemname=items[i].getAttribute("name");
			var itemvalue=items[i].getAttribute("value");
			var replaceObj =  document.getElementById(itemname);
			//alert(replaceObj.type);
			
			try{
				if(replaceObj.type == "text" || replaceObj.type == "hidden"){		
					replaceObj.value = itemvalue;		
				}else if(replaceObj.type == "select-one"){
					for(var j = 0; j < replaceObj.options.length; ++j){
						if(replaceObj.options[j].value == itemvalue){
							replaceObj.selectedIndex = j;
							break;
						}
					}
				}else if(replaceObj.type == "radio"){
					//alert(replaceObj.length);
					//var radioLength = replaceObj.length;
					var obj = eval("document.appFormName."+replaceObj.name);
					var radioLength = obj.length;			
					//alert(radioLength);
					if(radioLength == undefined){
						if(replaceObj.value == itemvalue){
							replaceObj.checked = true;
						}
					}else{
						for(var j = 0; j < radioLength; ++j){
							//alert(itemvalue);
							//alert(replaceObj[j].value);
							if(obj[j].value == itemvalue){
								obj[j].checked = true;
								break;
							}
						}
					}
				}else{
					replaceObj.innerHTML = itemvalue;
				}
			}catch(err){}
		}
	}
}
//###################################################################################################################