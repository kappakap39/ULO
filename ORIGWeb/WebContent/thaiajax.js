function thaiajax(){
	this.xmlhttp;
	this.formElement;
	this.method;
	this.requestFile;
	this.URLString;
	this.innerDivID;
	this.onCompletion = function() { };
	this.onProcess = function() { };
	this.loading = function(v){
		var v;
		if(v==1){
			document.getElementById("loading").style.visibility = "visible";
		}else{
			document.getElementById("loading").style.visibility = "hidden";
		}
	}
	
	this.ConnXmlHttp = function(){
		try{
			xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			try{
				isIE = true;
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){
				xmlhttp = false;
			}
		}
	
		if(!xmlhttp && document.createElement){
			xmlhttp = new XMLHttpRequest();
		}
		
		return xmlhttp;
	}
	
	this.getRequestBody = function(myForm) {
		var aParams = new Array();  
			for (var i=0 ; i < myForm.elements.length; i++) {
            	var sParam = encodeURIComponent(myForm.elements[i].name);
               	sParam += "=";
               	sParam += encodeURIComponent(myForm.elements[i].value);
               	aParams.push(sParam);
           	}     
           	return aParams.join("&");        
	}
	
	this.loadXMLDoc = function() {
	
		var self = this;
		this.xmlhttp = this.ConnXmlHttp();

		
		if (this.method == "GET") {
			var totalurlstring = this.requestFile + "?" + this.URLString;
			this.xmlhttp.open(this.method, totalurlstring, true);
		}else{
			this.URLString = this.getRequestBody(this.formElement);
			this.xmlhttp.open(this.method,this.requestFile, true);
		}
		if (this.method == "POST"){
  			try {
				this.xmlhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded')  
			} catch (e) {}
		}
		this.xmlhttp.onreadystatechange = function(){
			if(self.xmlhttp.readyState==4&&self.xmlhttp.responseText){
				self.loading(0);
				self.onProcess();
				if (self.xmlhttp.status == 200) {
					self.response = self.xmlhttp.responseText;
					self.onCompletion();
					
				}
			}else{
				self.loading(1);
				self.onProcess();
			}
		}
		this.xmlhttp.send(this.URLString);
	}
}

var ajaxObjects = new Array();

function g(namex){
	if (document.getElementById)
		return document.getElementById(namex);
	else if (document.all)
		return document.all[namex];
	else 
		return null;
}

function getData(index,requestFile,URLString,innerDivID){
	
	var index; var requestFile; var URLString; var innerDivID;
	ajaxObjects[index] = new thaiajax();
	ajaxObjects[index].method="GET";
	ajaxObjects[index].requestFile=requestFile;
	ajaxObjects[index].URLString=URLString;
	ajaxObjects[index].innerDivID=innerDivID;
	ajaxObjects[index].onCompletion = function(){ showData(index,innerDivID); };
	ajaxObjects[index].loadXMLDoc();
}


function showData(index,innerDivID){	
	
	document.getElementById(innerDivID).innerHTML = ajaxObjects[index].response;
}

function getContent(){
	getData('getEntries','data.html','','divEntries');
}


