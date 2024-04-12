<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../../css/ulo-left-style.css" rel="stylesheet" type="text/css" />
<script language="javascript">
function toggleMenu() {
	window.parent.toggleMenu();
}
function forceHide() {
	window.parent.forceHide();
}
</script>
</head>
<body onload="forceHide()" rightmargin="0" topmargin="0" leftmargin="0" bottommargin="0" class="htme-body">
<div style="width: 100%; height: 100%;" id="navMenuHidden">
<table height="100%" width="100%" cellspacing="0" cellpadding="0">
	<tbody>
		<tr>
			<td width="100%" valign="top" align="center" class="bg">
				<table width="100%" cellspacing="0" cellpadding="2">
					<tbody>
						<tr>
							<td width="100%" align="center"><a href="javascript:toggleMenu();"><img border="0" src="images/menu_expand.gif"/></a></td>
						</tr>
					</tbody>
				</table>
			</td>			
		</tr>
	</tbody>
</table>
</div>
</body>
</html>

