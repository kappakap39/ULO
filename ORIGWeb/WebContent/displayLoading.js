// displayLoading.js

var gPosx = 0;
	var gPosy = 0;
	
	function setPosXY(x,y){
	//alert(x+"<>"+y);
		gPosx = x;
		gPosy = y;
	}
	function displayLoading(message){
		hideDropdownList();
		
		var topOffset = 1;
		if (window.pageYOffset != null) { /* moz and safari */
			pos = window.pageYOffset;
			ph = document.documentElement.scrollHeight;
			pw = document.documentElement.scrollWidth;
			if (document.body.scrollHeight > document.documentElement.scrollHeight) {
				ph = document.body.scrollHeight;
				pw = document.body.scrollWidth;
			}
		} else if (document.documentElement.scrollTop > document.body.scrollTop) { /* ie, catch if Standards compliance mode */
			pos = document.documentElement.scrollTop;
			ph = document.documentElement.scrollHeight;
			pw = document.documentElement.scrollWidth;
			if (document.documentElement.clientHeight > document.documentElement.scrollHeight) {
				ph = document.documentElement.clientHeight;
			}
		} else if (document.body != null) { /* if IE 5.5 */
			pos = document.body.scrollTop;
			ph = document.body.scrollHeight;
			pw = document.body.scrollWidth;
			if (document.documentElement.scrollHeight > document.body.scrollHeight) {
				ph = document.documentElement.scrollHeight;
			}
	
			ph = ph + pos + topOffset; /* fix box model */
		}
		
		var posx = 0;
		var posy = 0;
		if (!e) var e = window.event;
		try{
			if (e.pageX || e.pageY) 	{
				posx = e.pageX;
				posy = e.pageY;
			}
			else if (e.clientX || e.clientY) 	{
				posx = e.clientX + document.body.scrollLeft
					+ document.documentElement.scrollLeft;
				posy = e.clientY + document.body.scrollTop
					+ document.documentElement.scrollTop;
			}
		}catch(er){
			posx = gPosx;
			posy = gPosy;
		}
		
		if(message==null || "" == message){
			message = "Loading...";
		}
		
		var LR = '<DIV id="blockDiv" align="center" style="position:absolute; visibility:visible; width:'+ pw +'; height:'+ ph +'; background: green;"><table cellSpacing=0 cellPadding=0 width="100%" height="100%" align="center" border="0" ><tr align="center" valign="middle"><td>&nbsp;</td></tr></table></DIV>';
		var Loading = '<div align="justify" style="position:absolute; visibility:visible; left: '+posx+'px; top: '+posy+'px; background-color: white; border:1px; border-color: black; border-style: solid; vertical-align: middle;" ><IMG src="../../en_US/images/loading1.gif" align="center">&nbsp;&nbsp;'+message+'&nbsp;&nbsp;</div>';
		
		document.body.insertAdjacentHTML("afterBegin",LR);
		
		var div = document.getElementById("blockDiv");
		div.style.setAttribute("filter", "alpha(opacity=20);");
		document.body.insertAdjacentHTML("beforeEnd",Loading);
	}
	function hideDropdownList(){
		var theForm = document.getElementById("appFormName");
		if(theForm == null){
			theForm = document.getElementById("resultForm");
		}
		for (var i=0; i < theForm.elements.length; i++) {
	            var theNode = theForm.elements[i];
				if (theNode.type!==undefined) {
					switch(theNode.type.toLowerCase()) {
						case "select-one":
						//alert("type = "+theNode.type+" : name = "+theNode.name+" : value = "+theNode.value);
						theNode.style.visibility = "hidden";
						break;
					}
				}
	        }
	      
	}