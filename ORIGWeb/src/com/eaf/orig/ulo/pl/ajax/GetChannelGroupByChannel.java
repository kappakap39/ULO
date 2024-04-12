package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;


import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class GetChannelGroupByChannel implements AjaxDisplayGenerateInf {

	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		String ChannelNo = request.getParameter("channel");
		String channelGroup = PLORIGEJBService.getORIGDAOUtilLocal().getChannelGroupByChannel(ChannelNo);
		JsonObjectUtil jsonObj = new JsonObjectUtil();
		jsonObj.CreateJsonObject("saleChannel", channelGroup);
		return jsonObj.returnJson();
	}

}
