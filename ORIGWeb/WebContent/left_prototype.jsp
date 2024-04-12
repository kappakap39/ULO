<%@page contentType="text/html;charset=TIS620"%>   
      <TABLE cellSpacing=0 cellPadding=0 width=150 align=center bgColor=#f5f5f5 
      border=0>
        <TBODY>
        <TR>
          <TD width="5%"></TD>
          <TD colSpan=2><FONT class=font2>Go To Menu:(Alt+M)</FONT></TD></TR></TR>
        <TR>
          <TD width="5%"></TD>
          <TD colSpan=2><INPUT class=textbox style="TEXT-TRANSFORM: uppercase" 
            accessKey=m maxLength=15 size=15 name=go> <INPUT class=button_text onclick=linkToMenu() type=button value=GO name=button4> 
          </TD></TR></TR>
        <TR bgColor=#7c7c7c>
          <TD width="2%"></TD>
          <TD colSpan=2><FONT class=font4 color=#ffffff><B>Data Entry&nbsp;<FONT color=red>(1)</FONT></B></FONT></TD>
        </TR>
        <TR class="tabOn"  id="tdTab1" onClick="chgTab(this);">
          <TD width="2%"></TD>
          <TD colSpan=2 align="left">
          <A href="FrontController?page=DE00_SCREEN"><FONT class=font4 color=#ffffff>DE101:รับงาน</FONT></A></TD>
        </TR>
        <TR  class="tabOff"  id="tdTab2" onClick="chgTab(this);">
          <TD width="2%"></TD>
          <TD colSpan=2 align="left"><A href="FrontController?page=DE01_1_SCREEN"><FONT class=font2>DE102:งานใหม่(mail in)</FONT></A></TD>
        </TR>
        <TR  class="tabOff"  id="tdTab2" onClick="chgTab(this);">
          <TD width="2%"></TD>
          <TD colSpan=2 align="left"><A href="FrontController?page=DE01_2_SCREEN"><FONT class=font2>DE103:งานใหม่(LAN#)</FONT></A></TD>
        </TR>
        <TR  class="tabOff"  id="tdTab2" onClick="chgTab(this);">
          <TD width="2%"></TD>
          <TD colSpan=2 align="left"><A href="FrontController?page=DE01_3_SCREEN"><FONT class=font2>DE104:งานใหม่(image)</FONT></A></TD>
        </TR>
		<TR  class="tabOff"  id="tdTab3" onClick="chgTab(this);">
			<TD width="2%"></TD>
			<TD colspan="2" align="left"><A href="FrontController?page=CS_SCREEN"><FONT class=font2>CA101:รับงาน</FONT></A></TD>
		</TR>		
		<TR  class="tabOff"  id="tdTab3" onClick="chgTab(this);">
			<TD width="2%"></TD>
			<TD colspan="2" align="left"><A href="FrontController?page=CS1_SCREEN"><FONT class=font2>CS101:Card Maintenance</FONT></A></TD>
		</TR>				
		
		<TR  class="tabOff" id="tdTab4" onClick="chgTab(this);">
          <TD width="3%"></TD>
          <TD colSpan=2 align="left"><A href="http://192.168.0.199:9080/XenozWeb/FrontController?action=Menu_Show&amp;handleForm=N&amp;menuSequence=99"><FONT 
            class=font2>EX03:ออกจากระบบ</FONT></A></TD>
        </TR>
      </TBODY>
    </TABLE>
<script language="JavaScript">
    function chgTab(curObj,eleId){
	if(document.getElementById){			
		document.getElementById("tdTab1").className = "tabOff";
		document.getElementById("tdTab2").className = "tabOff";
		document.getElementById("tdTab3").className = "tabOff";
		document.getElementById("tdTab4").className = "tabOff";
		
		curObj.className = "tabOn";
		
	}//end if	
}//--
</script>