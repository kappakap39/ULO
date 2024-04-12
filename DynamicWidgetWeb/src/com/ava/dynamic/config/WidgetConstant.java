package com.ava.dynamic.config;

public class WidgetConstant {
	public static class WidgetClass{
		public static final String CHART = "Chart";
		public static final String BOX = "Box";
		public static final String TEAM_PERFORMANCE = "TeamPerformance";
	}
	
	public static final class OnlineStatus{
		public static final String ONLINE = "O";
		public static final String OFFLINE = "F";
	}
	
	public static class WidgetCode{
		public static final String SOLID_GAUGE_CHART = "CH_SG";
		public static final String BAR_CHART = "CH_BC";
		public static final String SINGLE_STATISTICS_BOX = "CH_SSB";
		public static final String DOUBLE_STATISTICS_BOX = "CH_DSB";
		public static final String STACKED_BAR = "CH_SB";
		public static final String STACKED_AND_GROUPED_BAR = "CH_SGB";
		public static final String TEXT_AND_BOX = "CH_TNB";
		public static final String CIRCLE_CHART = "CH_CB";
		public static final String BASIC_LINE_CHART = "CH_BL";
		public static final String BOX = "CT_BOX";
		public static final String TEAM_PERFORMANCE = "CT_TF";
		public static final String TEXT_AND_NUMBER_BUTTON = "CT_TNNBT";
		public static final String TEXT_AND_TEXT_BUTTON = "CT_TNTBT";
		public static final String MULTI_CIRCLE_CHART = "CH_MCC";
	}
	public static class WidgeType{
		public static final Long SOLID_GAUGE_CHART = 1L;
		public static final Long BAR_CHART = 2L;
		public static final Long SINGLE_STATISTICS_BOX = 3L;
		public static final Long DOUBLE_STATISTICS_BOX = 13L;
		public static final Long STACKED_BAR = 4L;
		public static final Long TEXT_AND_BOX = 5L;
		public static final Long CIRCLE_CHART = 6L;
		public static final Long BASIC_LINE_CHART = 7L;
	}
	
	public static final String SELECT_SQL = " SELECT W.ID AS ID," +
			" T.CODE AS CODE," +
			" T.NAME AS NAME," +
			" T.DESCRIPTION AS DESCRIPTION," +
			" T.CLASS AS CLASS," +
			" T.VIEW_PATH AS VIEW_PATH," +
			" T.FRAGMENT AS FRAGMENT," +
			" W.TYPE_ID AS TYPE_ID," +
			" W.TITLE AS TITLE," +
			" W.YAXIS_TITLE AS YAXIS_TITLE," +
			" W.YAXIS_MIN AS YAXIS_MIN," +
			" W.YAXIS_MAX AS YAXIS_MAX," +
			" W.DATA_SUFFIX AS DATA_SUFFIX," +
			" W.TOOLTIP_SUFFIX AS TOOLTIP_SUFFIX," +
			" W.GRID_ITEM_ID AS GRID_ITEM_ID," +
			" W.WIDTH AS WIDTH," +
			" W.HEIGHT AS HEIGHT," +
			" W.CSS_CLASS AS CSS_CLASS," +
			" W.CUSTOM_STYLE AS CUSTOM_STYLE," +
			" W.SEQ AS SEQ," +
			" W.INTERVAL_TIME AS INTERVAL_TIME," +
			" T.SELECT_SEQ AS SELECT_SEQ," +
			" T.DHB_TYPE AS DHB_TYPE" +
			" FROM WIDGET W JOIN CF_WIDGET_TYPE T ON W.TYPE_ID = T.ID ";
	public static final int MaxContainerNodeDepth = 5;
	
	/**Contains Database Sequence Name
	 * @author TOP
	 *
	 */
	public static final class SEQ{
		public static final String GRID_ID = "GRID_ID";
		public static final String GRID_ITEM_ID = "GRID_ITEM_ID";
		public static final String WIDGET_ID = "WIDGET_ID";
	}
	public class SystemName{
		public static final String ORIG = "ORIG";
		public static final String DASHBOARD = "DASHBOARD";
	}
}
