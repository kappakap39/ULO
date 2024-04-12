<%@ page contentType=" application/vnd.ms-excel;charset=windows-874"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>

<html>
<head>
<meta http-equiv="content-language" content="th" />
<meta http-equiv="content-type" content="text/html; charset=windows-874" />
</head>
<title>General Enquiry</title>
<body>


<table border='1'>
	<tr>
		
		<th>Update</th>
		<th>Date&Time</th>
		<th>Application No.</th>
		<th>Thai First Name/Company Name</th>
		<th>Thai Last Name</th>
		<th>Citizen ID.</th>
		<th>Loan Type</th>
		<th>Last User ID</th>
		<th>Main Customer's / Guarantor's Type</th>
		<th>Dealer Code / Name</th>
		<th>Source of Customer</th>
		<th>CMR Code/Name</th>
		<th>Personal Type</th>
		<th>Application Status</th>
		<th>Role</th>
		<th>Contract No.</th>
	</tr>


	<%Vector resultVec = (Vector) request.getSession().getAttribute("resultSearchVec");
            ORIGUtility utility = new ORIGUtility();
            ORIGCacheUtil cacheUtil = new ORIGCacheUtil();

            String cmrCode;
            String dealerCode;
            String srcofcus;
            String customerType;
            String loanType;
            String mktCode;
			

			String contactNo;

            for (int i = 1; i < resultVec.size(); i++) {
                Vector elementList = (Vector) resultVec.get(i);
                cmrCode = cacheUtil.getORIGMasterDisplayNameDataM("UserName", (String) elementList.elementAt(15));
                //System.out.println("elementList.elementAt(8) = "+elementList.elementAt(10));
                dealerCode = cacheUtil.getORIGMasterDisplayNameDataM((String) elementList.elementAt(10), (String) elementList.elementAt(10));
                srcofcus = cacheUtil.getORIGCacheDisplayNameDataMByType((String) elementList.elementAt(11), "SRCOFCST");
                customerType = cacheUtil.getORIGMasterDisplayNameDataM("CustomerType", (String) elementList.elementAt(9));
                loanType = cacheUtil.getORIGMasterDisplayNameDataM("Product", (String) elementList.elementAt(7));
                mktCode = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer", (String) elementList.elementAt(17));
                
                contactNo = cacheUtil.getORIGMasterDisplayNameDataM("ContactNo", (String) elementList.elementAt(18));
                %>
                
	<tr bgcolor="#F4F4F4">
		<td align="center"><%=(utility.getPriorityDescription((String) elementList.elementAt(1)))%></td>
		<td align="center"><%=(((String) elementList.elementAt(2)))%></td>
		<td align="center"><%=((String)elementList.elementAt(3))%></td>
		<td>&nbsp;<%=(elementList.elementAt(4))%></td>
		<td>&nbsp;<%=(elementList.elementAt(5))%></td>
		<td align="center">ID <%=(elementList.elementAt(6))%></td>
		<td>&nbsp;<%=(loanType)%></td>
		<td>&nbsp;<%=(elementList.elementAt(8))%></td>
		<td>&nbsp;<%=(customerType)%></td>
		<td>&nbsp;<%=(dealerCode)%></td>
		<td>&nbsp;<%=(srcofcus)%></td>
		<td>&nbsp;<%=(mktCode)%></td>
		<td align="center"><%=(utility.getPersonalTypeDesc((String) elementList.elementAt(16)))%></td>
		<td>&nbsp;<%=(elementList.elementAt(13))%></td>
		<td align="center"><%=(ORIGUtility.getRoleByJobState((String) elementList.elementAt(14)))%></td>
		<td align="center"><%=contactNo%></td>
	</tr>
	<%}%>

</table>
</body>

</html>
