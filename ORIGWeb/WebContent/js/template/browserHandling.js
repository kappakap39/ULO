/**
 * Wichaya Ch.
 */

var myclose = false;

function confirmClose()
{
    if (event.clientY < 0)
    {
        event.returnValue = 'You have closed the browser. Do you want to logout from your application?';
        setTimeout('myclose=false',10);
        myclose=true;
    }
}

function handleOnClose()
{
    if (myclose==true) 
    {
//      the url of your logout page which invalidate session on logout 
//      location.replace('/ORIGWeb/logout.jsp');
//    	window.open(CONTEXT_PATH_ORIG+'/logout.jsp','','width=0,height=0,toolbar=0,menubar=0,location=0,status=1,scrollbars=0,resizable=0,left=0,top=0');
    	var dataString = "className=Logout&packAge=0&returnType=0";
    	jQuery.ajax({
    		type :"POST",
    		url :"AjaxDisplayServlet",
    		data :dataString,
    		async :false,	
    		dataType: "json",
    		success : function(response ,status ,xhr){
    			window.open(CONTEXT_PATH_ORIG+'/logout.jsp','','width=0,height=0,toolbar=0,menubar=0,location=0,status=1,scrollbars=0,resizable=0,left=0,top=0');    		
    		},
    		error : function(response){
    			window.open(CONTEXT_PATH_ORIG+'/logout.jsp','','width=0,height=0,toolbar=0,menubar=0,location=0,status=1,scrollbars=0,resizable=0,left=0,top=0');
    		}
    	});
    	
    }
}