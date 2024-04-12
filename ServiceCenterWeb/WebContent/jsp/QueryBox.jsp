<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NameClassPair"%>
<%@page import="javax.naming.NamingEnumeration"%>
<html lang="en">
<head>
<script type="text/javascript" src="/ServiceCenterWeb/js/pace.js"></script>
<%
	InitialContext ctx = new InitialContext();
	NamingEnumeration<NameClassPair> list = ctx.list("jdbc");
	String databaseList = "";
	while (list.hasMore()) 
	{
		String db = list.next().getName();
		if(db != null)
		{
			databaseList += "<option value='jdbc/" + db + "'>jdbc/" +  db + "</option>";
		}
	}
%>
<script>
		function queryDatabase()
		{
			var selectedDb = $('#selectedDb').find(":selected").val();
			if(!(selectedDb))
			{
				alert('Please select database.');
			}
			else
			{
				Pace.block();
				$.ajax({
					url : "/ServiceCenterWeb/QueryBox",
					data : {database:$("#selectedDb").val(),
							query:$("#query").val(),
							maxRow:$("#maxRow").val(),
							mode:'query'
							},
					type : "post",
					success : function(data){
						$("#queryResults").empty();
						var obj = $.parseJSON(data);
						if(obj.jsonRs)
						{$("#queryResults").html(obj.jsonRs);}
						else
						{$("#queryResults").html('No results found.');}
					},
					complete: function(data){
						Pace.unblockFlag = true;
			            Pace.unblock(); 
			        }
				});
			}
		}
</script>
<script>
		function displayDiv(ele)
		{
			var divId = 'div' + ele.id;
			var div = $('#' + divId);
		    div.toggle();
		}
</script>
</head>

<body>
	<Strong>QueryBox</Strong>
	<form class="form-inline well">
		<table>
			<tr>
				<td>Database :
					<select id="selectedDb">
						<option value="" selected disabled>Select Database...</option>
						<%=databaseList%>
					</select>
				</td>
			</tr>
			<tr>
				<td>Max Row : <input id="maxRow" type="text" size="4" value="500"></td>
			</tr>
			<tr>
				<td>
					<textarea id="query" rows="12" cols="150"></textarea>
				</td>
			</tr>
		</table>
	</form>
	<button type="button" class="btn btn-info" onclick="queryDatabase()">Execute Query</button>
	<br><br>
	<div id="queryResults">
	</div>
</body>
</html>