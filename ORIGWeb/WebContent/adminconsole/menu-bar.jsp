<!-- Start Menu Section -->

<script language="JavaScript">

function logoutProcess(){
	if(confirm('Logout !')){
		document.logoutForm.submit();
	}
}

function menuClick(indexPage){
	alert('Underconstruction !!!');
}

function onCurrentMenuOver(obj){
	obj.style.cursor='hand';
}

function onMenuOver(obj){
	obj.style.color = '#ffffff';
	obj.style.cursor='hand';
}

function onMenuOut(obj){
	obj.style.color = '#003399';
}
</script>

		  
		  	<table width="100%" border="1" bordercolor="#003399">
              <tr align="left"> 
                <td bgcolor="#000033">
					&nbsp;<font class="menu-text-link" onmouseout="onMenuOut(this)" onmouseover="onMenuOver(this)" onclick="menuClick('1')">Home</font>&nbsp;<font class="menu-text">|</font>&nbsp;
					<font class="menu-text-link" onmouseout="onMenuOut(this)" onmouseover="onMenuOver(this)" onclick="menuClick('2')">Queue Monitor</font>&nbsp;<font class="menu-text">|</font>&nbsp;
					<font class="menu-text-link" onmouseout="onMenuOut(this)" onmouseover="onMenuOver(this)" onclick="menuClick('3')">Assign Application</font>&nbsp;<font class="menu-text">|</font>&nbsp;
					<font class="menu-text-link" onmouseout="onMenuOut(this)" onmouseover="onMenuOver(this)" onclick="menuClick('4')">Batch Application</font>&nbsp;<font class="menu-text">|</font>&nbsp;
					<font class="menu-text-link" onmouseout="onMenuOut(this)" onmouseover="onMenuOver(this)" onclick="menuClick('5')">Interface</font>&nbsp;<font class="menu-text">|</font>&nbsp;
					<font class="menu-text-link" onmouseout="onMenuOut(this)" onmouseover="onMenuOver(this)" onclick="menuClick('6')">Refresh Cache</font>&nbsp;<font class="menu-text">|</font>&nbsp;
					<font class="menu-text-link" onmouseout="onMenuOut(this)" onmouseover="onMenuOver(this)" onclick="menuClick('7')">User Signed On</font>&nbsp;<font class="menu-text">|</font>&nbsp;
					<font class="menu-text-link" onmouseout="onMenuOut(this)" onmouseover="onMenuOver(this)" onclick="logoutProcess()">Log Out</font>&nbsp;<font class="menu-text">|</font>&nbsp;
				</td>
              </tr>
            </table>

<FORM METHOD=POST ACTION="/NaosWeb/ibm_security_logout" NAME="logoutForm">
	<INPUT TYPE="HIDDEN" name="logoutExitPage" VALUE="adminconsole/">
</FORM>

<!-- End Menu Section -->
	  