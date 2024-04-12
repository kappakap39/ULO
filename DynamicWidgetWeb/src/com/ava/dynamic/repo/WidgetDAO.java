package com.ava.dynamic.repo;

import java.util.List;

import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.grid.Grid;

public interface WidgetDAO {

	List<Widget> getWidgetByGridItemId(Long gridItemId);

	int saveWidgets(List<Widget> widgets) throws Exception;

	void loadWidgetData(Widget widget, String username, String teamName, Grid grid) throws IllegalStateException;

	void loadWidgetData(Widget widget) throws IllegalStateException;
}
