package com.ava.dynamic.controller;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ava.dynamic.exception.DashboardException;
import com.ava.dynamic.model.Owner;
import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.WidgetCache;
import com.ava.dynamic.model.grid.Grid;
import com.ava.dynamic.model.widget.Chart;
import com.ava.dynamic.model.widget.StatBox;
import com.ava.dynamic.model.widget.TeamPerformance;
import com.ava.dynamic.repo.WidgetDAO;
import com.ava.dynamic.service.ChartService;
import com.ava.dynamic.service.DashboardService;
import com.ava.dynamic.service.GridService;
import com.ava.dynamic.service.OwnerService;
import com.ava.dynamic.service.TeamPerformanceService;
import com.ava.dynamic.util.JSONUtil;
import com.ava.dynamic.util.ObjectUtils;

@SessionAttributes({"owner"})
@Controller
public class DashBoardController {
	static final transient Logger logger = LogManager.getLogger(DashBoardController.class);
	static final AtomicLong idCounter = new AtomicLong();
	@Autowired
	private ChartService chartService;
	@Autowired
	private TeamPerformanceService teamPerfService;
	@Autowired
	private DashboardService dashboardService;
	@Autowired
	private WidgetDAO widgetDAO;
	@Autowired
	private GridService gridService;
	@Autowired
	private OwnerService ownerService;
	
	@ModelAttribute("owner")
	private Owner initOwner(){
		return new Owner();
	}
	
	@RequestMapping(value="/grid/{gridId}", method = RequestMethod.GET)
	public String viewGridByCode(@PathVariable("gridId") String gridId,
			Model model,
			HttpServletRequest request,
			@ModelAttribute("owner") Owner owner,//Represents current dashboard viewer
			@RequestParam(value="mode", required = false) String mode, //Configure view mode
			@RequestParam(value="teamId", required = false) String teamId,//Team ID represents dashboard Owner
			Map<String,String> params){
		if(owner == null || owner.getUserId() == null){
			return "redirect:/login";
		}
		if(teamId == null){
			teamId = owner.getTeamId();//Case compare
		}
		
		//Refer to ST000000000086 : Enable delegate mode so current owner can view other members' dashboard
		
		logger.debug("gridId >> "+gridId);
		logger.debug("teamId >> "+teamId);
		String userName = owner.getUserId();

		
		logger.debug("userName >> "+userName);
		Grid grid = getGrid(gridId,mode,userName,teamId);
		if(grid != null){
			/*Team id will not be valid until dashboard owner leave landing page.(Default Page when accessed by ULO)
			 * When team id is valid, it will retrieve Team name from Database.
			 * */
			String firstParameterHolder = teamId==null?owner.getTeamName():ownerService.getTeamNameById(teamId);
			grid.updateTitleByHolder(firstParameterHolder);
			
			//Set navigation
			owner.getNavigation().addItem(request.getRequestURL().toString(), request.getQueryString(), grid.getTitle());
		}
		model.addAttribute("grid",grid);

		return "grid";
	}
	private Grid getGrid(String gridId,String mode,String username,String teamId){
		Long id = null;
		try{
			id = NumberUtils.parseNumber(gridId, Long.class);
		}catch(Exception e){
			logger.debug("Cannot parse code to number! getting grid by Code instead");
		}		
		Grid grid = null;
		if(id == null){
			grid = WidgetCache.getGridByCode(gridId);
		}else{
			grid = WidgetCache.getGridById(id);			
		}
		grid = ObjectUtils.cloneObject(grid);
		boolean prototypeMode = false;
		if(mode != null && mode.equalsIgnoreCase("prototype")){
			prototypeMode = true;
		}
		
		String postionLevel = grid.getPositionLevel();
		if(teamId != null && postionLevel != null && !postionLevel.isEmpty() && !grid.getDynamicItems()){//if teamId is presented, meaning, current viewer is trying to view data owned by another team(lower level)			
			Owner tempOwner = ownerService.getDelegateOwnerByTeamId(teamId, postionLevel);//get someone's username in the team to load data(literally anyone)
			if(tempOwner == null){
				throw new DashboardException("ERROR 500", "Unable to find Representative username for TeamID "+teamId+" and PositionID "+postionLevel
							+". Please verify that MS_USER_TEAM and MS_TEAM have been configured properly!");
			}
			username = tempOwner.getUserId();
		}
		logger.debug("prototypeMode = "+prototypeMode);
		gridService.loanGridData(grid,prototypeMode,username,teamId);
		if(logger.isDebugEnabled()){
			logger.debug("Grid = "+JSONUtil.toJson(grid));
		}		
		return grid;
	}
	
	/*@RequestMapping(value="/grid/service/refresh", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> refresh(){
		logger.debug("test refresh");
		ResponseEntity<String> response = null;
		try {
			dashboardService.refreshDashboardData();
			response = new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}*/
	
	@RequestMapping(value="/grid/chart/load-data", method = RequestMethod.POST)
	@ResponseBody
	public Widget getWidget(@RequestBody Chart widget){
		//dashboardService.refreshDashboardData(widget.getDhbType());
		logger.debug("update chart");
		gridService.updateWidgetData(widget);
		return widget;
	}
	
	@RequestMapping(value="/grid/team/load-data", method = RequestMethod.POST)
	@ResponseBody
	public Widget getWidget(@RequestBody TeamPerformance widget){
		//dashboardService.refreshTeamPerformanceData(widget.getTypeClass());
		logger.debug("update team");
		gridService.updateWidgetData(widget);
		return widget;
	}

	@RequestMapping(value="/grid/statbox/load-data", method = RequestMethod.POST)
	@ResponseBody
	public Widget getWidget(@RequestBody StatBox widget){
//		logger.debug("run refresh dashboard");
//		dashboardService.refreshDashboardData(widget.getDhbType());
		logger.debug("update statbox");
		gridService.updateWidgetData(widget);
		return widget;
	}
}
