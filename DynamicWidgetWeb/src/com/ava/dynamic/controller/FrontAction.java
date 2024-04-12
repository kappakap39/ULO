package com.ava.dynamic.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ava.dynamic.exception.DashboardException;
import com.ava.dynamic.model.Owner;
import com.ava.dynamic.service.OwnerService;
import com.ava.dynamic.service.UserDetailService;
import com.eaf.core.ulo.common.util.FormatUtil;
import com.eaf.orig.profile.model.UserDetailM;

@SessionAttributes({"owner","otherMembers","userM"})
@Controller
public class FrontAction {
	private static final transient Logger logger = LogManager.getLogger(FrontAction.class);
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private UserDetailService userDetailService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String service(HttpServletRequest request, Model model,Principal principal) {
		String username = principal.getName();
		if(username == null || username.isEmpty()){
			logger.debug("Current username is not presented in WAS Security principle, redirecting to logout!");
			return "redirect:/login";
		}
		UserDetailM user = (UserDetailM)request.getSession(true).getAttribute("userM");
		logger.debug("request : "+user);
		user = userDetailService.getWidgetUser(username,user);
		if(user == null){
			throw new DashboardException("ERROR 500","Unable to find Dashboard UserM Information associated with user " + username+
					". Please verify that the user is authorized to view dashboard.");
		}
		String time = FormatUtil.getTime(user.getLastLogonDate(), FormatUtil.Format.HHMMSS);
		String date = FormatUtil.getDate(user.getLastLogonDate(), FormatUtil.Format.dd_MMM_yyyy,FormatUtil.TH);
		String displayDate = "Last Login"+" "+time+" | "+date;
		user.setDisplayDate(displayDate);
		model.addAttribute("userM", user);//Set UserM to session
		logger.debug("displayDate  : "+displayDate);
		logger.debug("Loading widget module for userM  : "+user);
		
		//Load Grid owner data
		Owner owner = ownerService.getOwnerById(username);
		if(owner == null){
			throw new DashboardException("ERROR 500","Unable to find Dashboard Owner Information associated with user " + username+
					". Please verify that the user is authorized to view dashboard.");
		}
		model.addAttribute("owner", owner);//Set owner to session
		model.addAttribute("otherMembers", owner.getOtherTeamMember());//Set owner to session
		logger.debug("Loading widget module for owner  : "+owner);

		String gridId = owner.getDefaultGridId();
		if(gridId == null){
			throw new DashboardException("ERROR 500","Unable to find Dashboard associated with user " + username+
					". Please verify that the user is authorized to view dashboard.");
		}
		
		String nextAction = "";
		nextAction = "redirect:/grid/" + gridId + "?teamId=" + owner.getTeamId();
		logger.debug("nextAction >> " + nextAction);
		return nextAction;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String viewOtherTeamMemberDashboard(@RequestParam(required=true) String userId, Model model,Principal principal) {
		String username = userId;
		logger.debug("User ID : "+username);
		if(username == null || username.isEmpty()){
			throw new DashboardException("ERROR 500","Current username is not presented in Request parameter! Please go back and pick another one.");
//			return "redirect:/login";
		}
		
		//Load Grid owner data
		Owner owner = ownerService.getOwnerById(username);
		if(owner == null){
			throw new DashboardException("ERROR 500","Unable to find Dashboard Owner Information associated with user " + username+
					". Please verify that the user is authorized to view dashboard.");
		}
		model.addAttribute("owner", owner);//Set owner to session
		logger.debug("Loading widget module for owner  : "+owner);
		
		String gridId = owner.getDefaultGridId();
		if(gridId == null){
			throw new DashboardException("ERROR 500","Unable to find Dashboard associated with user " + username+
					". Please verify that the user is authorized to view dashboard.");
		}
		
		String nextAction = "";
		nextAction = "redirect:/grid/" + gridId + "?teamId=" + owner.getTeamId();
		logger.debug("nextAction >> " + nextAction);
		return nextAction;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			logout(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		String msg = request.getParameter("msg");
		if(msg != null && msg.equalsIgnoreCase("ERROR")){
			model.addAttribute("msg","Ldap authentication error!");

		}

		try {
			response.sendRedirect("/ORIGWeb/logout.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/ORIGWeb/logout.jsp";
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// invalidate session
		if (request.getSession(false) != null) {
			request.getSession(false).invalidate();
		}
		// remove SSO cookies
		request.logout();
		
		logger.debug("Logout current user sucessfully!");
	}

//	private UserDetailM getWidgetUser(String userName) {
//		UserDetailM userM = new UserDetailM();
//		userM.setUserName(userName);
//		userM.setLastLogonDate(ApplicationDate.getTimestamp());
//		try {
//			UserDetailDAO userDao = Factory.getUserDetailDAO();
//			userDao.setUserTeam(userM);
//			userDao.setGridId(userM);
//			userDao.setAuthGrid(userM);
//		} catch (Exception e) {
//			logger.fatal("ERROR", e);
//		}
//		return userM;
//	}

//	public static boolean auth(HttpServletRequest request) {
//		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("WidgetUser");
//		return (null != userM && null != userM.getUserName() && !"".equals(userM.getUserName()));
//	}
}
