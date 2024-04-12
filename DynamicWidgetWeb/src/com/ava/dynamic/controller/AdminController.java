package com.ava.dynamic.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ava.dynamic.model.AjaxResponse;
import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.WidgetCache;
import com.ava.dynamic.model.WidgetProperties;
import com.ava.dynamic.model.grid.Grid;
import com.ava.dynamic.model.grid.GridItem;
import com.ava.dynamic.service.GridService;
import com.ava.dynamic.util.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class AdminController {
	static final transient Logger log = LogManager.getLogger(AdminController.class);
	@Autowired
	private GridService gridService;
	
	@RequestMapping("/admin")
	public String adminHome(Model model) {
		Collection<Grid> gridCol = WidgetCache.getAllGrid();
		List<Grid> grids = new ArrayList<Grid>(gridCol);
		if(grids != null){
			Collections.sort(grids, new Comparator<Grid>(){
				@Override
				public int compare(Grid o1, Grid o2) {
					return (int)(o1.getId() - o2.getId());
				}				
			});
		}
		model.addAttribute("widget_props", WidgetProperties.propMap);
		model.addAttribute("grids", grids);
		model.addAttribute("menu", WidgetCache.getMenuById(-1L));// Default admin menu
		return "admin/module/adm_dashboard";
	}
	
	@RequestMapping("/admin/refreshCache")
	public @ResponseBody
	AjaxResponse adminUi() {
		AjaxResponse resp = new AjaxResponse();
		gridService.refreshGridCache();
		return resp;
	}
	
	@RequestMapping("/admin/test")
	public String adminTest() {
		return "test_tm";
	}

	@RequestMapping("/admin/dashboard")
	public String adminDashboard(Model model) {
		Collection<Grid> grids = WidgetCache.getAllGrid();
		model.addAttribute("grids", grids);
		model.addAttribute("menu", WidgetCache.getMenuById(-1L));// Default admin menu
		return "admin/module/adm_dashboard";
	}

	@RequestMapping("/admin/dashboard/{id}")
	public String adminEditDashboard(Model model, @PathVariable Long id) {
		model.addAttribute("widget_props", WidgetProperties.propMap);
		model.addAttribute("grid", WidgetCache.getGridById(id));
		model.addAttribute("menu", WidgetCache.getMenuById(-1L));// Default admin menu
		return "admin/module/adm_edit_grid";
	}

	@RequestMapping(value = "/admin/content/{contentName}", method = RequestMethod.GET)
	public String adminContent() {
		return "ajax_template :: content";
	}

	@RequestMapping(value = "/admin/ajax/grid", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResponse submitGrid(@RequestParam Map<String, String> requestParams) {
		String json = requestParams.get("json");
		log.debug("Request json = " + json);
		AjaxResponse response = new AjaxResponse();
		Gson gson = new Gson();
		Grid grid = gson.fromJson(json, Grid.class);
		log.debug("grid = " + grid);
		if (grid != null){
			try{
				gridService.saveGrid(grid);
				response.setResultCode("S");
				response.setResultDesc("Successfully");
			}catch(Exception e){
				response.setResultCode("F");
				response.setResultDesc(e.getLocalizedMessage());
			}

		}else{
			response.setResultCode("F");
			response.setResultDesc("Fail! Request json is not defined.");
		}
		return response;
	}
	
	@RequestMapping(value = "/admin/ajax/grid", method = RequestMethod.DELETE)
	public @ResponseBody
	AjaxResponse deleteGrid(@RequestParam Map<String, String> requestParams) {
		String id = requestParams.get("id");		
		log.debug("Delete grid id = " + id);
		AjaxResponse response = new AjaxResponse();
		try{
			response.setResultCode("S");
			response.setResultDesc("Admin permission is requred to delete grid!");
		}catch(Exception e){
			response.setResultCode("F");
			response.setResultDesc("Fail to delete Grid! "+e.getLocalizedMessage());
		}		
		return response;
	}

	@RequestMapping(value = "/admin/ajax/allgrids", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResponse submitOnlyGrid(@RequestParam Map<String, String> requestParams) {
		String json = requestParams.get("json");
		log.debug("Request json = " + json);
		AjaxResponse response = new AjaxResponse();
		Gson gson = new Gson();
		List<Grid> grids = gson.fromJson(json, new TypeToken<List<Grid>>() {
		}.getType());
		log.debug("grid = " + grids);
		if(grids != null && !grids.isEmpty()){
			try{
				for(Grid grid : grids){
					if(grid.getDirty() == null || !grid.getDirty()){
						continue;
					}
					if(grid.getDisplayMenu() == null){
						grid.setDisplayMenu(false);
					}
					log.debug("Saving grid : "+grid);
					gridService.saveOnlyGrid(grid);
				}
				response.setResultCode("S");
				response.setResultDesc("Successfully");
			}catch (Exception e) {
				response.setResultCode("F");
				response.setResultDesc(e.getLocalizedMessage());
				log.error("Error saving Grid : "+e.getLocalizedMessage());
			}
		}else{
			response.setResultCode("F");
			response.setResultDesc("Fail! Request json is not defined.");
		}
		return response;
	}
	
	@RequestMapping(value = "/admin/ajax/grid/clone/{code}", method = RequestMethod.GET)
	public @ResponseBody
	AjaxResponse cloneGrid(@PathVariable("code") String code,@RequestParam Map<String, String> requestParams) {
		String json = requestParams.get("json");
		log.debug("Id to clone = " + code);
		AjaxResponse response = new AjaxResponse();
		Long id = null;
		try{
			id = NumberUtils.parseNumber(code, Long.class);
		}catch(Exception e){
			log.debug("Cannot parse code to number! getting grid by Code instead");
		}				
		Grid grid = null;
		if(id == null){
			grid = WidgetCache.getGridByCode(code);
		}else{
			grid = WidgetCache.getGridById(id);			
		}
		grid = ObjectUtils.cloneObject(grid);		
		if(grid != null){
			grid.setId(0L);
			List<GridItem> items = grid.getItems();
			if(items!= null && !items.isEmpty()){
				for(GridItem item: items){
					item.setGridId(0L);
					item.setId(0L);
					List<Widget> widgets = item.getWidgetList();
					if(widgets != null && !widgets.isEmpty()){
						for(Widget widget : widgets){
							widget.setId(0L);
						}
					}else{
						log.warn("Widgets is null!");
					}
				}
			}else{
				log.warn("Grid items are null!");
			}
		}else{
			log.warn("Grid not found");
		}		
		if(grid != null){
			try{
				gridService.saveGrid(grid);
				response.setResultCode("S");
				response.setResultDesc("Successfully");
			}catch(Exception e){
				response.setResultCode("F");
				response.setResultDesc(e.getLocalizedMessage());
			}
		}else{
			response.setResultCode("F");
			response.setResultDesc("Fail! Request json is not defined.");
		}		
		return response;		
	}
}
