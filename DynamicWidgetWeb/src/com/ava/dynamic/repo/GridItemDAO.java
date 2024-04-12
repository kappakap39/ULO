package com.ava.dynamic.repo;

import java.util.List;

import com.ava.dynamic.model.grid.GridItem;

public interface GridItemDAO {

	void saveGridItem(List<GridItem> items) throws Exception;

	int deleteItemsByGridId(Long gridId);

	/**Count how many groups of widgets are there in Summary Table. Assuming first widget of the group 
	 * represents a grid item.
	 * 
	 * @param positionLevel
	 * @param username
	 * @return Number of grid item that should be created
	 */
	int countDynamicItemQuantity(String positionLevel, String username);

	String getDynamicItemHeader(String positionLevel, String username, String groupId);

	List<GridItem> getDynamicGridItemByTeamId(String teamId, String positionId);

}
