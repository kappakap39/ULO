/*
    Author        :   Tan Ling	Wee
    Copyright     :   (C) 2002 Hutchison 3G
    Description   :   
    email         :   fuushikaden@yahoo.com
    written on    :   2 Dec 2001
    lastupdated   :   23 June 2002
*/

	var fixedX = -1  ;   // x position (-1 if to appear below control)
	var fixedY = -1	;		// y position (-1 if to appear below control)
	var startAt = 1	;		// 0 - sunday ; 1 - monday
	var showWeekNumber = 0	;    // 0 - don't show; 1 - show
	var showToday = 1;		// 0 - don't show; 1 - show
	var imgDir = "en_US/images/calendar/";	// directory for images ... e.g. var imgDir="/img/"

	var gotoString = "Go To Current Month";
	var todayString = "Today is";
	var weekString = "Wk";
	var scrollLeftMessage = "Click to scroll to previous month. Hold mouse button to scroll automatically.";
	var scrollRightMessage = "Click to scroll to next month. Hold mouse button to scroll automatically.";
	var selectMonthMessage = "Click to select a month.";
	var selectYearMessage = "Click to select a year.";
	var selectDateMessage = "Select [date] as date."; // do not replace [date], it will be replaced by date.

	var	crossobj, crossMonthObj, crossYearObj, monthSelected, yearSelected, dateSelected, omonthSelected, oyearSelected, odateSelected, monthConstructed, yearConstructed, intervalID1, intervalID2, timeoutID1, timeoutID2, ctlToPlaceValue, ctlNow, dateFormat, nStartingYear, ctlDay, ctlMonth, ctlYear, yearLen, monthLen;

	var	bPageLoaded=false;
	var	ie=document.all;
	var	dom=document.getElementById;
	var $IE = (getInternetExplorerVersion()== 9)? true : false;
	var	ns4=document.layers;
	var	today =	new	Date();
	var	dateNow	 = today.getDate();
	var	monthNow = today.getMonth();
	var	yearNow	 = today.getYear();
	var	imgsrc = new Array("drop1.gif","drop2.gif","left1.gif","left2.gif","right1.gif","right2.gif");
	var	img	= new Array();
	var bShow = false;
	var $era = 'BE'; //Wichaya default era to AD

    /* hides <select> and <applet> objects (for IE only) */
    function hideElement( elmID, overDiv ){
    	
      if(ie){
        for( var i = 0; i < document.all.tags(elmID).length; i++ ){
          obj = document.all.tags( elmID )[i];
          if( !obj || !obj.offsetParent ){
            continue;
          }
      
          // Find the element's offsetTop and offsetLeft relative to the BODY tag.
          objLeft   = obj.offsetLeft;
          objTop    = obj.offsetTop;
          objParent = obj.offsetParent;
          
          while( objParent.tagName.toUpperCase() != "BODY" ){        	
            objLeft  += objParent.offsetLeft;
            objTop   += objParent.offsetTop;
            objParent = objParent.offsetParent;
          }
      
          objHeight = obj.offsetHeight;
          objWidth = obj.offsetWidth;          
         
          if(( overDiv.offsetLeft + overDiv.offsetWidth ) <= objLeft );
          else if(( overDiv.offsetTop + overDiv.offsetHeight ) <= objTop );
          else if( overDiv.offsetTop >= ( objTop + objHeight ));
          else if( overDiv.offsetLeft >= ( objLeft + objWidth ));
          else{
            obj.style.visibility = "hidden";
          }
        }
      }      
    }
     
    /*
    * unhides <select> and <applet> objects (for IE only)
    */
    function showElement( elmID ){

      if(ie){
        for(var i = 0; i < document.all.tags(elmID).length; i++ ){
          obj = document.all.tags( elmID )[i];          
          if( !obj || !obj.offsetParent){
            continue;
          }        
          obj.style.visibility = "";
        }
      }
    }

	function HolidayRec (d, m, y, desc){
		this.d = d;
		this.m = m;
		this.y = y;
		this.desc = desc;
	}

	var HolidaysCounter = 0;
	var Holidays = new Array();

	function addHoliday (d, m, y, desc)	{
		Holidays[HolidaysCounter++] = new HolidayRec ( d, m, y, desc );
	}

	if (dom){
		for	(var i=0;i<imgsrc.length;i++){
			img[i] = new Image;
			img[i].src = imgDir + imgsrc[i];
		}
		document.write ("<div onclick='bShow=true' id='calendar' style='left:0px;top:0px;z-index:+999;position:absolute;visibility:hidden;'>" +
				"<table	width="+((showWeekNumber==1)?265:235)+" " +
						"style='font-family:arial;font-size:11px;border-width:1px;border-style:solid;border-color:#a0a0a0;font-family:arial; font-size:11px}' bgcolor='#ffffff'>" +
						"<tr bgcolor='#3AA764'><td><table width='"+((showWeekNumber==1)?263:233)+"'>" +
								"<tr><td style='padding:2px;font-family:arial; font-size:11px;'>" +
								"<font color='#ffffff'><B><span id='caption'></span></B></font></td>" +
								"<td align=right><a href=\"javascript:hideCalendar();clearDate()\">" +
								"<IMG SRC='"+imgDir+"close.gif' WIDTH='15px' HEIGHT='13px' BORDER='0' ALT='Close the Calendar'></a></td></tr>" +
										"</table></td></tr><tr><td style='padding:2px' bgcolor=#ffffff><span id='content'></span></td></tr>");
			
		if(showToday==1){
			document.write ("<tr bgcolor=#f0f0f0><td style='padding:0px' align=center><span id='lblToday' style='font-family:arial;font-size:11px;color:#9E0B0E;'></span></td></tr>");
		}
			
		document.write ("</table></div><div id='selectMonth' style='z-index:+999;position:absolute;visibility:hidden;'></div><div id='selectYear' style='z-index:+999;position:absolute;visibility:hidden;'></div>");
	}

	var	monthName =	new	Array("January","February","March","April","May","June","July","August","September","October","November","December");
	if (startAt==0){
		dayName = new Array	("Sun","Mon","Tue","Wed","Thu","Fri","Sat");
	}else{
		dayName = new Array	("Mon","Tue","Wed","Thu","Fri","Sat","Sun");
	}
	var	styleAnchor="text-decoration:none;color:#303030;font-family:arial;font-size:11px;";
	var	styleLightBorder="border-style:solid;border-width:1px;border-color:#a0a0a0;";

	function swapImage(srcImg, destImg){
		/*if (ie)	{ document.getElementById(srcImg).setAttribute("src",imgDir + destImg) }*/
	}

	function init()	{
		if (!ns4){
			
			if (!ie || $IE) { yearNow += 1900;}
			crossobj=(dom)?document.getElementById("calendar").style : ie? document.all.calendar : document.calendar;
			hideCalendar();

			crossMonthObj=(dom)?document.getElementById("selectMonth").style : ie? document.all.selectMonth	: document.selectMonth;

			crossYearObj=(dom)?document.getElementById("selectYear").style : ie? document.all.selectYear : document.selectYear;

			monthConstructed=false;
			yearConstructed=false;

			if (showToday==1){
				document.getElementById("lblToday").innerHTML =	todayString + " <a onmousemove='window.status=\""+gotoString+"\"' onmouseout='window.status=\"\"' title='"+gotoString+"' style='"+styleAnchor+"' href='javascript:monthSelected=monthNow;yearSelected=yearNow;constructCalendar();'>"+dayName[(today.getDay()-startAt==-1)?6:(today.getDay()-startAt)]+", " + dateNow + " " + monthName[monthNow].substring(0,3)	+ "	" +	yearNow	+ "</a>";
			}

			sHTML1="<span id='spanLeft'	style='border-style:solid;border-width:1px;border-color:#A0A0A0;cursor:pointer' onmouseover='swapImage(\"changeLeft\",\"left2.gif\");this.style.borderColor=\"#88AAFF\";window.status=\""+scrollLeftMessage+"\"' onclick='javascript:decMonth()' onmouseout='clearInterval(intervalID1);swapImage(\"changeLeft\",\"left1.gif\");this.style.borderColor=\"#A0A0A0\";window.status=\"\"' onmousedown='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"StartDecMonth()\",500)'	onmouseup='clearTimeout(timeoutID1);clearInterval(intervalID1)'>&nbsp<IMG id='changeLeft' SRC='"+imgDir+"left1.gif' BORDER=0>&nbsp</span>&nbsp;";
			sHTML1+="<span id='spanRight' style='border-style:solid;border-width:1px;border-color:#A0A0A0;cursor:pointer' onmouseover='swapImage(\"changeRight\",\"right2.gif\");this.style.borderColor=\"#88AAFF\";window.status=\""+scrollRightMessage+"\"' onmouseout='clearInterval(intervalID1);swapImage(\"changeRight\",\"right1.gif\");this.style.borderColor=\"#A0A0A0\";window.status=\"\"' onclick='incMonth()' onmousedown='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"StartIncMonth()\",500)'	onmouseup='clearTimeout(timeoutID1);clearInterval(intervalID1)'>&nbsp<IMG id='changeRight' SRC='"+imgDir+"right1.gif'	BORDER=0>&nbsp</span>&nbsp";
			sHTML1+="<span id='spanMonth' style='border-style:solid;border-width:1px;border-color:#A0A0A0;cursor:pointer' onmouseover='swapImage(\"changeMonth\",\"drop2.gif\");this.style.borderColor=\"#88AAFF\";window.status=\""+selectMonthMessage+"\"' onmouseout='swapImage(\"changeMonth\",\"drop1.gif\");this.style.borderColor=\"#A0A0A0\";window.status=\"\"' onclick='popUpMonth()'></span>&nbsp;";
			sHTML1+="<span id='spanYear' style='border-style:solid;border-width:1px;border-color:#A0A0A0;cursor:pointer' onmouseover='swapImage(\"changeYear\",\"drop2.gif\");this.style.borderColor=\"#88AAFF\";window.status=\""+selectYearMessage+"\"'	onmouseout='swapImage(\"changeYear\",\"drop1.gif\");this.style.borderColor=\"#A0A0A0\";window.status=\"\"'	onclick='popUpYear()'></span>&nbsp;";
			
			document.getElementById("caption").innerHTML  =	sHTML1;

			bPageLoaded=true;
		}
	}

	function hideCalendar()	{
		crossobj.visibility="hidden";
		if (crossMonthObj != null){crossMonthObj.visibility="hidden";}
		if (crossYearObj !=	null){crossYearObj.visibility="hidden";}

	    showElement( 'SELECT' );
		showElement( 'APPLET' );
	}

	function clearDate(){
		if(!udf(ctlToPlaceValue)){
			ctlToPlaceValue.value="";
		}
	}

	function padZero(num) {
		return (num	< 10)? '0' + num : num ;
	}

	function constructDate(d,m,y){
		sTmp = dateFormat;
		sTmp = sTmp.replace	("dd","<e>");
		sTmp = sTmp.replace	("d","<d>");
		sTmp = sTmp.replace	("<e>",padZero(d));
		sTmp = sTmp.replace	("<d>",d);
		sTmp = sTmp.replace	("mmm","<o>");
		sTmp = sTmp.replace	("mm","<n>");
		sTmp = sTmp.replace	("m","<m>");
		sTmp = sTmp.replace	("<m>",m+1);
		sTmp = sTmp.replace	("<n>",padZero(m+1));
		sTmp = sTmp.replace	("<o>",monthName[m]);
		return sTmp.replace ("yyyy",y);
	}

	function closeCalendar() {
            hideCalendar();
            /**Fix Bug Cannot Close Canlendar #SeptemWi*/
            if(!ctlToPlaceValue.disabled){
//		    	ctlToPlaceValue.value =	constructDate(dateSelected,monthSelected,yearSelected+543);
//		    	ctlToPlaceValue.focus();
		    	$(document.getElementById(ctlToPlaceValue)).val(constructDate(dateSelected,monthSelected,convertToExternalUseYear(yearSelected,$era)));
		    	$(document.getElementById(ctlToPlaceValue)).focus();
		    	$('#'+ctlToPlaceValue).setCursorToTextEnd();
	    	}
            dateVal = ctlToPlaceValue.value;
            if(ctlDay!=null) {
               var dzTmp = parseInt(dateSelected);
               if (dzTmp && dzTmp<10 && dateFormat.indexOf('mm')!=-1) {
                  dzTmp = "0" + dzTmp;
               }
               ctlDay.value = dzTmp;
			   //alert(dzTmp);
           }
           if(ctlMonth!=null) {
               if (ctlMonth.value==11) {
      	           ctlMonth.value =  monthSelected;
               } else {
                   mozTmp = parseInt(monthSelected + 1);
                   if (mozTmp>0 && mozTmp<10 && dateFormat.indexOf('mm')!=-1) {
                       mozTmp = "0" + mozTmp;
                   }
                   ctlMonth.value = mozTmp;
				   //alert(mozTmp);
               }
           }
           if(ctlYear!=null) {
        	  ctlYear.value  =  yearSelected ;
			  //alert(yearSelected);
           }


			//calculate for age/period
		   var nowDate = new Date();
		   nowY = nowDate.getYear();
		   if(nowY < 1900){
			   //getYear return 00-99, mean in range 1900-1999
			   nowY += 1900;
			}
		   nowM = nowDate.getMonth()+1;
		   nowD = nowDate.getDate();

			selY = parseInt(yearSelected);
			selM = parseInt(monthSelected+1);
			selD = dzTmp;
			nowY = nowY-selY;
			nowM = nowM-selM;
			nowD = nowD-selD;
			
			if( nowD > 25 ){
				//ceil for a month
				nowM = nowM+1;
			}

			if( nowY < 0 ){
				//wrong input year
				return;
			}else{
				if( nowM < 0 ){
					if( nowY == 0 ){
						//wrong input month at year = now year
						return;
					}
					nowY = nowY-1;
					nowM = 12+nowM;
				}

				if( yearLen != null ){
					yearLen.value = nowY;
				}

			   if( monthLen != null ){
				   monthLen.value = nowM;
			   }
			}
	}


	/*** Month Pulldown	***/

	function StartDecMonth(){
		intervalID1=setInterval("decMonth()",80);
	}

	function StartIncMonth(){
		intervalID1=setInterval("incMonth()",80);
	}

	function incMonth () {
		monthSelected++;
		if (monthSelected>11) {
			monthSelected=0;
			yearSelected++;
		}
		constructCalendar();
	}

	function decMonth () {
		monthSelected--;
		if (monthSelected<0) {
			monthSelected=11;
			yearSelected--;
		}
		constructCalendar();
	}

	function constructMonth() {
		popDownYear();
		if (!monthConstructed) {
			sHTML =	"";
			for	(var i=0; i<12;	i++) {
				sName =	monthName[i];
				if (i==monthSelected){
					sName =	"<B>" +	sName +	"</B>";
				}
				sHTML += "<tr><td id='m" + i + "' style='font-family:arial; font-size:11px;' onmouseover='this.style.backgroundColor=\"#F1DAAF\"' onmouseout='this.style.backgroundColor=\"\"' style='cursor:pointer' onclick='monthConstructed=false;monthSelected=" + i + ";constructCalendar();popDownMonth();event.cancelBubble=true'>&nbsp;" + sName + "&nbsp;</td></tr>";
			}

			document.getElementById("selectMonth").innerHTML = "<table width=70	style='font-family:arial; font-size:11px; border-width:1px; border-style:solid; border-color:#a0a0a0;' bgcolor='#FFFFDD' cellspacing=0 onmouseover='clearTimeout(timeoutID1)'	onmouseout='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"popDownMonth()\",100);event.cancelBubble=true'>" +	sHTML +	"</table>";

			monthConstructed=true;
		}
	}

	function popUpMonth() {
		constructMonth();
		crossMonthObj.visibility = (dom||ie)? "visible"	: "show";
		if(!$IE){
			crossMonthObj.left = parseInt(crossobj.left) + 50;
			crossMonthObj.top =	parseInt(crossobj.top) + 26;
		}else{
			crossMonthObj.left = (parseInt(crossobj.left) + 50)+'px';
			crossMonthObj.top =	(parseInt(crossobj.top) + 26)+'px';
		}
		hideElement( 'SELECT', document.getElementById("selectMonth") );
		hideElement( 'APPLET', document.getElementById("selectMonth") );			
	}

	function popDownMonth()	{
		crossMonthObj.visibility= "hidden";
	}

	/*** Year Pulldown ***/

	function incYear() {
		for	(var i=0; i<7; i++){
			newYear	= (i+nStartingYear)+1;
			if (newYear==yearSelected){ txtYear =	"&nbsp;<B>"	+ newYear +"/"+(convertToExternalUseYear(newYear,$era))+"</B>&nbsp;" ;}
			else{ txtYear =	"&nbsp;" + newYear + "/"+(newYear + 543)+"&nbsp;" ;}
			document.getElementById("y"+i).innerHTML = txtYear;
		}
		nStartingYear ++;
		bShow=true;
	}

	function decYear() {
		for	(var i=0; i<7; i++){
			newYear	= (i+nStartingYear)-1;
			if (newYear==yearSelected){ txtYear =	"&nbsp;<B>"	+ newYear +"/"+(convertToExternalUseYear(newYear,$era))+"</B>&nbsp;"; }
			else{ txtYear =	"&nbsp;" + newYear + "/"+(newYear + 543)+"&nbsp;" ;}
			document.getElementById("y"+i).innerHTML = txtYear;
		}
		nStartingYear --;
		bShow=true;
	}

	function selectYear(nYear) {
		yearSelected=parseInt(nYear+nStartingYear);
		yearConstructed=false;
		constructCalendar();
		popDownYear();
	}

	function constructYear() {
		popDownMonth();
		sHTML =	"";
		if(!yearConstructed){
			
			sHTML =	"<tr><td align='center'	style='font-family:arial; font-size:11px;' onmouseover='this.style.backgroundColor=\"#F1DAAF\"' onmouseout='clearInterval(intervalID1);this.style.backgroundColor=\"\"' style='cursor:pointer'	onmousedown='clearInterval(intervalID1);intervalID1=setInterval(\"decYear()\",30)' onmouseup='clearInterval(intervalID1)'>-</td></tr>";

			j =	0;
			nStartingYear =	yearSelected-3;
			for	(var i=(yearSelected-3); i<=(yearSelected+3); i++) {
				sName =	i;
				if (i==yearSelected){
					sName =	"<B>" +	sName +	"/"+(convertToExternalUseYear(sName,$era))+"</B>";
				}else{
					sName =	sName +	"/"+(convertToExternalUseYear(sName,$era));
				}

				sHTML += "<tr><td id='y" + j + "' style='font-family:arial; font-size:11px;' onmouseover='this.style.backgroundColor=\"#F1DAAF\"' onmouseout='this.style.backgroundColor=\"\"' style='cursor:pointer' onclick='selectYear("+j+");event.cancelBubble=true'>&nbsp;" + sName + "&nbsp;</td></tr>";
				j ++;
			}

			sHTML += "<tr><td align='center' style='font-family:arial; font-size:11px;' onmouseover='this.style.backgroundColor=\"#F1DAAF\"' onmouseout='clearInterval(intervalID2);this.style.backgroundColor=\"\"' style='cursor:pointer' onmousedown='clearInterval(intervalID2);intervalID2=setInterval(\"incYear()\",30)'	onmouseup='clearInterval(intervalID2)'>+</td></tr>";

			document.getElementById("selectYear").innerHTML	= "<table width=44 style='font-family:arial; font-size:11px; border-width:1px; border-style:solid; border-color:#a0a0a0;'	bgcolor='#FFFFDD' onmouseover='clearTimeout(timeoutID2)' onmouseout='clearTimeout(timeoutID2);timeoutID2=setTimeout(\"popDownYear()\",100)' cellspacing=0>"	+ sHTML	+ "</table>";

			yearConstructed	= true;
		}
	}

	function popDownYear() {
		clearInterval(intervalID1);
		clearTimeout(timeoutID1);
		clearInterval(intervalID2);
		clearTimeout(timeoutID2);
		crossYearObj.visibility= "hidden";
	}

	function popUpYear() {
		var	leftOffset;
		constructYear();
		crossYearObj.visibility	= (dom||ie)? "visible" : "show";
		leftOffset = parseInt(crossobj.left) + document.getElementById("spanYear").offsetLeft;
		if(ie){
			leftOffset += 6;
		}
		if(!$IE){
			crossYearObj.left =	leftOffset;
			crossYearObj.top = parseInt(crossobj.top) +	26;
		}else{
			crossYearObj.left =	(leftOffset)+'px';
			crossYearObj.top = (parseInt(crossobj.top) +26)+'px';
		}
	}

	/*** calendar ***/
   function WeekNbr(n) {
      // Algorithm used:
      // From Klaus Tondering's Calendar document (The Authority/Guru)
      // hhtp://www.tondering.dk/claus/calendar.html
      // a = (14-month) / 12
      // y = year + 4800 - a
      // m = month + 12a - 3
      // J = day + (153m + 2) / 5 + 365y + y / 4 - y / 100 + y / 400 - 32045
      // d4 = (J + 31741 - (J mod 7)) mod 146097 mod 36524 mod 1461
      // L = d4 / 1460
      // d1 = ((d4 - L) mod 365) + L
      // WeekNumber = d1 / 7 + 1
 
      year = n.getFullYear();
      month = n.getMonth() + 1;
      if (startAt == 0) {
         day = n.getDate() + 1;
      }
      else {
         day = n.getDate();
      }
 
      a = Math.floor((14-month) / 12);
      y = year + 4800 - a;
      m = month + 12 * a - 3;
      b = Math.floor(y/4) - Math.floor(y/100) + Math.floor(y/400);
      J = day + Math.floor((153 * m + 2) / 5) + 365 * y + b - 32045;
      d4 = (((J + 31741 - (J % 7)) % 146097) % 36524) % 1461;
      L = Math.floor(d4 / 1460);
      d1 = ((d4 - L) % 365) + L;
      week = Math.floor(d1/7) + 1;
 
      return week;
   }

	function constructCalendar () {
		var aNumDays = Array (31,0,31,30,31,30,31,31,30,31,30,31);

		var dateMessage;
		var	startDate =	new	Date (yearSelected,monthSelected,1);
		var endDate;

		if (monthSelected==1){
			endDate	= new Date (yearSelected,monthSelected+1,1);
			endDate	= new Date (endDate	- (24*60*60*1000));
			numDaysInMonth = endDate.getDate();
		}else{
			numDaysInMonth = aNumDays[monthSelected];
		}

		datePointer	= 0;
		dayPointer = startDate.getDay() - startAt;
		
		if (dayPointer<0){
			dayPointer = 6;
		}

		sHTML =	"<table	 border=0 style='font-family:verdana;font-size:10px;'><tr>";

		if (showWeekNumber==1){
			sHTML += "<td width=27><b>" + weekString + "</b></td><td width=1 rowspan=7 bgcolor='#d0d0d0' style='padding:0px'><img src='"+imgDir+"divider.gif' width=1></td>";
		}

		for	(var i=0; i<7; i++)	{
			sHTML += "<td width='27' align='right' style='font-family:arial;font-size:11px;'><B>"+ dayName[i]+"</B></td>";
		}
		sHTML +="</tr><tr>";
		
		if (showWeekNumber==1){
			sHTML += "<td align=right>" + WeekNbr(startDate) + "&nbsp;</td>";
		}

		for	( var i=1; i<=dayPointer;i++ ){
			sHTML += "<td>&nbsp;</td>";
		}
	
		for	( var datePointer=1; datePointer<=numDaysInMonth; datePointer++ ){
			dayPointer++;
			sHTML += "<td align=right>";
			sStyle=styleAnchor;
			if ((datePointer==odateSelected) &&	(monthSelected==omonthSelected)	&& (yearSelected==oyearSelected))
			{ sStyle+=styleLightBorder; }

			sHint = "";
			for (var k=0;k<HolidaysCounter;k++){
				if ((parseInt(Holidays[k].d)==datePointer)&&(parseInt(Holidays[k].m)==(monthSelected+1))){
					if ((parseInt(Holidays[k].y)==0)||((parseInt(Holidays[k].y)==yearSelected)&&(parseInt(Holidays[k].y)!=0))){
						sStyle+="background-color:#FFDDDD;";
						sHint+=sHint==""?Holidays[k].desc:"\n"+Holidays[k].desc;
					}
				}
			}

			var regexp= /\"/g;
			sHint=sHint.replace(regexp,"&quot;");

			dateMessage = "onmousemove='window.status=\""+selectDateMessage.replace("[date]",constructDate(datePointer,monthSelected,yearSelected))+"\"' onmouseout='window.status=\"\"' ";

			if ((datePointer==dateNow)&&(monthSelected==monthNow)&&(yearSelected==yearNow))
			{ sHTML += "<b><a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' href='javascript:dateSelected="+datePointer+";closeCalendar();'><font color=#ff0000>&nbsp;" + datePointer + "</font>&nbsp;</a></b>";}
			else if	(dayPointer % 7 == (startAt * -1)+1)
			{ sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' href='javascript:dateSelected="+datePointer + ";closeCalendar();'>&nbsp;<font color=#909090>" + datePointer + "</font>&nbsp;</a>" ;}
			else
			{ sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' href='javascript:dateSelected="+datePointer + ";closeCalendar();'>&nbsp;" + datePointer + "&nbsp;</a>" ;}

			sHTML += "";
			if ((dayPointer+startAt) % 7 == startAt) { 
				sHTML += "</tr><tr>"; 
				if ((showWeekNumber==1)&&(datePointer<numDaysInMonth)){
					sHTML += "<td align=right>" + (WeekNbr(new Date(yearSelected,monthSelected,datePointer+1))) + "&nbsp;</td>";
				}
			}
		}
		
		document.getElementById("content").innerHTML   = sHTML;
		document.getElementById("spanMonth").innerHTML = "&nbsp;" +	monthName[monthSelected] + "&nbsp;<IMG id='changeMonth' SRC='"+imgDir+"drop1.gif' WIDTH='12' HEIGHT='10' BORDER=0>";
		document.getElementById("spanYear").innerHTML =	"&nbsp;" + yearSelected+"/"+(yearSelected + 543)+ "&nbsp;<IMG id='changeYear' SRC='"+imgDir+"drop1.gif' WIDTH='12' HEIGHT='10' BORDER=0>";
	}


	function popUpCalendar4Len(ctl,	ctl2, format, dayDObj, monthMObj, yearYObj,yLenObj,mLenObj, posi) {
		yearLen = yLenObj;
		monthLen = mLenObj;
		popUpCalendar(ctl,	ctl2, format, dayDObj, monthMObj, yearYObj, posi) ;
	}

	function popUpCalendar(ctl,	ctl2, format, dayDObj, monthMObj, yearYObj, posi, era) {
		var	leftpos=0;
		var	toppos=0;
		/** If era is null set default to Buddhist era **/
		if(era == null || era == ''){
			$era = 'BE';
			era = 'BE';
		}else{
			$era = 'AD';
			era = 'AD';
		}
	
		if (bPageLoaded){			
			if (crossobj.visibility == "hidden"){
				ctlToPlaceValue	= ctl2;
				dateFormat = format;
				ctlDay = dayDObj;
				ctlMonth = monthMObj;
				ctlYear = yearYObj;

				formatChar = " ";
				aFormat	= dateFormat.split(formatChar);
				if (aFormat.length<3){
					formatChar = "/";
					aFormat	= dateFormat.split(formatChar);
					if (aFormat.length<3){
						formatChar = ".";
						aFormat	= dateFormat.split(formatChar);
						if (aFormat.length<3){
							formatChar = "-";
							aFormat	= dateFormat.split(formatChar);
							if (aFormat.length<3){
								// invalid date	format
								formatChar="";
							}
						}
					}
				}

				tokensChanged =	0;
				/**Fix Bug Connot Open Calendar #SeptemWi get Value Date*/
				if (formatChar	!= ""){
					// use user's date					
//					aData =	ctl2.value.split(formatChar);
					aData =	document.getElementById(ctl2).value.split(formatChar);

					for	(var i=0;i<3;i++){						
						if ((aFormat[i]=="d") || (aFormat[i]=="dd")){
							dateSelected = parseInt(aData[i], 10);
							tokensChanged ++;
						}else if((aFormat[i]=="m") || (aFormat[i]=="mm")){
							monthSelected =	parseInt(aData[i], 10) - 1;
							tokensChanged ++;
						}else if (aFormat[i]=="yyyy"){
							yearSelected = parseInt(aData[i], 10);
							if(!isNaN(yearSelected)){
								yearSelected = convertToInternalUseYear(yearSelected,era);
							}
							tokensChanged ++;
						}else if (aFormat[i]=="mmm"){
							for	(var j=0; j<12;	j++){
								if (aData[i]==monthName[j]){
									monthSelected=j;
									tokensChanged ++;
								}
							}
						}
					}
				}

				if ((tokensChanged!=3)||isNaN(dateSelected)||isNaN(monthSelected)||isNaN(yearSelected)){
					dateSelected = dateNow;
					monthSelected =	monthNow;
					yearSelected = yearNow;
				}

				odateSelected=dateSelected;
				omonthSelected=monthSelected;
				oyearSelected=yearSelected;
				/**Fix Bug Canlendar Connot See When Used Image Viewers Future #SeptemWi*/
				var imgLayout= document.getElementById('image-layout');
				var image = (imgLayout == null)? 0 : imgLayout.offsetWidth;
				aTag = ctl;
				do {
					aTag = aTag.offsetParent;
					leftpos	+= aTag.offsetLeft;
					toppos += aTag.offsetTop;
				} while(aTag.tagName!="BODY");
				/**#Septemwi Fix Bug IE9 Cannot Display At Element Textbox*/
				if($IE){
					var crossLeft = 0;
					var crossTop = 0;					
					if(posi=="left"){						
						crossLeft =	(fixedX==-1? ctl.offsetLeft-(ctl.offsetWidth + 195)+leftpos-image:fixedX);					
						crossTop = (fixedY==-1 ? ctl.offsetTop + toppos + ctl.offsetHeight + 2 : fixedY);
					}else{ 
						crossLeft = (fixedX==-1 ? ctl.offsetLeft+leftpos-image :fixedX);
						crossTop = (fixedY==-1 ? ctl.offsetTop + toppos + ctl.offsetHeight + 2 : fixedY);
					}
					crossobj.left  = crossLeft+'px';
					crossobj.top = crossTop+'px';
				}else{					
					if(posi=="left"){
						crossobj.left =	(fixedX==-1? ctl.offsetLeft-(ctl.offsetWidth + 195)+leftpos-image:fixedX);					
						crossobj.top = (fixedY==-1 ? ctl.offsetTop + toppos + ctl.offsetHeight + 2 : fixedY);
					}else{ 
						crossobj.left = (fixedX==-1 ? ctl.offsetLeft+leftpos-image :fixedX);
						crossobj.top = (fixedY==-1 ? ctl.offsetTop + toppos + ctl.offsetHeight + 2 : fixedY);
					}
				}
				
				constructCalendar (1, monthSelected, yearSelected);
				crossobj.visibility=(dom||ie)? "visible" : "show";
				
				hideElement( 'SELECT', document.getElementById("calendar") );
				hideElement( 'APPLET', document.getElementById("calendar") );			

				bShow = true;
			}else{
				hideCalendar();
				if (ctlNow!=ctl) {popUpCalendar(ctl, ctl2, format, dayDObj, monthMObj, yearYObj, posi, era);}
			}
			ctlNow = ctl;
		}
	}

	document.onkeypress = function hidecal1 () { 
		if (event.keyCode==27){
			hideCalendar();
		}
	};
	document.onclick = function hidecal2 () { 		
		if (!bShow){
			hideCalendar();
		}
		bShow = false;
	};

	function udf(x){
		x=""+x;
		if(x=="undefined")return true;
		else return false;
	}
	
	/*** Internal source code use AD ***/
	function convertToInternalUseYear(year, cEra){
		if(cEra == 'BE'){
			year = year-543;
		}
		return year;
	}
	
	function convertToExternalUseYear(year, cEra){
		if(cEra == 'BE'){
			year = year+543;
		}
		return year;
	}

	init();
