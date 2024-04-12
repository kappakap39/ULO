<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet" type="text/css">

<TITLE>testAjax.jsp</TITLE>
<script src="prototype-1.6.0.2.js"></script>

<script>
	function test(){
		var d = $('myDiv');
		//alert(d.innerHTML);
		d.hide();
		d.show();
		d.addClassName('active');
	 data ='test'	;
	 var sdata = Object.toQueryString(data);
	 new Ajax.Request('printResult.jsp',
  {
    method:'get',
    parameters: sdata,
    onSuccess: function(transport){
      var response = transport.responseText || "no response text";
      //alert("Success! \n\n" + response);
       d.innerHTML=response;
    },
    onFailure: function(){ alert('Something went wrong...') }
  });

		
	}
</script>
</HEAD>
<BODY>  
	<div id="myDiv">
		<p>This is a paragraph</p>
	</div>
	<div id="myOtherDiv">
		<p>This is another paragraph</p>
	</div>
    <input type="text" name="txtTest" >
	<input type="button" value="Test" onclick="test();"/><br/> 
</BODY>
</HTML>
