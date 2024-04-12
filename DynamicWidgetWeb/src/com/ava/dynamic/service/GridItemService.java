package com.ava.dynamic.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.grid.Grid;
import com.ava.dynamic.model.grid.GridItem;
import com.ava.dynamic.repo.GridItemDAO;
import com.ava.dynamic.util.ObjectUtils;

@Service
public class GridItemService {
	private static Logger log = LogManager.getLogger(GridItemService.class);
	private static int WIDTH_LIMIT = 12;
	@Autowired
	private GridItemDAO gridItemDAO;
	
	/**Dynamic grid item is self-expandable widget, depends on its size and its representative width.
	 * 
	 * @param grid
	 * @param username
	 */
	public void constructDynamicGridItem(Grid grid, String username){
		if(grid == null){
			throw new NullPointerException("Error! Grid is null while constructing dynamic items!");
		}
		List<GridItem> items = grid.getItems();
		if(items == null|| items.size() < 1 || items.get(0) == null){
			throw new IllegalArgumentException("Error! Unable to find First grid item while constructing dynamic items!" +
					" First element of grid Items is an essential part where its structure will be replicated" +
					" and filled with widget data.");
		}
		
		int quantity = gridItemDAO.countDynamicItemQuantity(grid.getPositionLevel(), username);
		log.debug("Dynamic Items counted by Position level and username : "+quantity);
		if(quantity < 1){
			return;
		}else if(quantity > 20){
			quantity = 20;
		}
		
		//Start Logic
		GridItem prototypeItem = items.get(0);
		items.clear();
		int protoWidth = prototypeItem.getWidth();
		int protoHeight = prototypeItem.getHeight();
		int protoMaxItemPerLine = WIDTH_LIMIT/protoWidth;
		int offset = Math.max((WIDTH_LIMIT - protoWidth * quantity)/2,0);//Case too much space
		for(int i = 0; i < quantity; i++){
			GridItem item = ObjectUtils.cloneObject(prototypeItem);
			
			//Set automatic position
			item.setId((long)i+1l);//Group start from 1
			item.setPosX(i%protoWidth * protoWidth + offset);
			item.setPosY(i/protoMaxItemPerLine * protoHeight);
			item.setTitle(gridItemDAO.getDynamicItemHeader(grid.getPositionLevel(), username, String.valueOf(item.getId())));
			
			//Set associated children
			List<Widget> widgets = item.getWidgetList();
			if(widgets == null){
				throw new IllegalArgumentException("Error! Widgets associated with item "+item.getId()+" is null!");
			}
			for(int j = 0, size = widgets.size(); j<size; j++){
				Widget widget = widgets.get(j);
				if(widget == null)continue;
				widget.setId(item.getId() * ((long)j+1l));//For HTML tag uniqueness
				widget.setDynamicSeq(i+1);//For data loading
			}
			
			//add item
			items.add(item);
		}
		
	}
	
	public void constructDynamicGridItemByTeamId(Grid grid, String teamId){
		if(grid == null){
			throw new NullPointerException("Error! Grid is null while constructing dynamic items!");
		}
		List<GridItem> items = grid.getItems();
		if(items == null|| items.size() < 1 || items.get(0) == null){
			throw new IllegalArgumentException("Error! Unable to find First grid item while constructing dynamic items!" +
					" First element of grid Items is an essential part where its structure will be replicated" +
					" and filled with widget data.");
		}
		
		List<GridItem> tempItems = gridItemDAO.getDynamicGridItemByTeamId(teamId, grid.getPositionLevel());
		if(tempItems == null || tempItems.isEmpty()){
			log.debug("constructDynamicGridItemByTeamId() No team member found for team id : "+teamId);
		}
		
		int quantity = tempItems.size();
		log.debug("Dynamic Items counted by Position level and teamId : "+quantity);
		if(quantity < 1){
			return;
		}else if(quantity > 20){
			quantity = 20;
		}
		
		//Start Logic
		GridItem prototypeItem = items.get(0);
		items.clear();
		int protoWidth = prototypeItem.getWidth();
		int protoHeight = prototypeItem.getHeight();
		int protoMaxItemPerLine = WIDTH_LIMIT/protoWidth;
		int offset = Math.max((WIDTH_LIMIT - protoWidth * quantity)/2,0);//Case too much space
		for(int i = 0; i < quantity; i++){
			GridItem item = ObjectUtils.cloneObject(prototypeItem);
			GridItem tempItem = tempItems.get(i);
			
			//Set automatic position
			item.setId((long)i+1l);//Group start from 1
			item.setPosX(i*protoWidth % WIDTH_LIMIT + offset);
			item.setPosY(i/protoMaxItemPerLine * protoHeight);
			item.setTitle(tempItem.getTitle());
			item.setTeamId(tempItem.getTeamId());
			item.setOwner(tempItem.getOwner());
			
			//Set associated children
			List<Widget> widgets = item.getWidgetList();
			if(widgets == null){
				throw new IllegalArgumentException("Error! Widgets associated with item "+item.getId()+" is null!");
			}
			for(int j = 0, size = widgets.size(); j<size; j++){
				Widget widget = widgets.get(j);
				if(widget == null)continue;
				widget.setId(item.getId() * ((long)j+1l));//For HTML tag uniqueness
				widget.setDynamicSeq(i+1);//For data loading
				
				//Set query criteria
				widget.setOwner(item.getOwner());
				widget.setPositionLevel(grid.getPositionLevel());
			}
			
			//add item
			items.add(item);
		}
		
	}
}
