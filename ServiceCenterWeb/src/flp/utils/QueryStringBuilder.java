package flp.utils;

public class QueryStringBuilder {

    private static final String PREFIX_SELECT = "SELECT ";
    private static final String PREFIX_FROM = "FROM ";
    private static final String PREFIX_WHERE = "WHERE ";
    private static final String PREFIX_GROUP_BY = "GROUP BY ";
    private static final String PREFIX_ORDER_BY = "ORDER BY ";
    private static final String PREFIX_FETCH = "FETCH ";

    private String select;
    private String from;
    private String where;
    private String groupBy;
    private String orderBy;
    private String fetch;

    public QueryStringBuilder() {
        select = PREFIX_SELECT;
        from = PREFIX_FROM;
        where = PREFIX_WHERE;
        groupBy = PREFIX_GROUP_BY;
        orderBy = PREFIX_ORDER_BY;
        fetch = PREFIX_FETCH;
    }

    public QueryStringBuilder select(String selectString) {
        select += selectString + ",";
        return this;
    }

    public QueryStringBuilder from(String fromString) {
        from += fromString + ",";
        return this;
    }

    public QueryStringBuilder where(String whereString) {
        where += whereString;
        return this;
    }
    public QueryStringBuilder andWhere(String whereString) {
        where += " AND " + whereString;
        return this;
    }
    public QueryStringBuilder orWhere(String whereString) {
        where += " OR " + whereString;
        return this;
    }
    public QueryStringBuilder optionalAndWhere(String whereString) {
        if (whereString != null && !whereString.isEmpty()) {
            where += " AND " + whereString;
        }
        return this;
    }
    public QueryStringBuilder optionalOrWhere(String whereString) {
        if (whereString != null && !whereString.isEmpty()) {
            where += " OR " + whereString;
        }
        return this;
    }

    public QueryStringBuilder groupBy(String groupByString) {
        groupBy += groupByString + ",";
        return this;
    }

    public QueryStringBuilder orderBy(String orderByString) {
        orderBy += orderByString + ",";
        return this;
    }

    public QueryStringBuilder fetch(String fetchString) {
        fetch += fetchString;
        return this;
    }

    public String build() {
        if (select.equals(PREFIX_SELECT)) {
            select = "";
        } else {
            select = select.substring(0, select.length() - 1);
        }
        if (from.equals(PREFIX_FROM)) {
            from = "";
        } else {
            from = from.substring(0, from.length() - 1);

        }
        if (where.equals(PREFIX_WHERE)) {
            where = "";
        }
        if (groupBy.equals(PREFIX_GROUP_BY)) {
            groupBy = "";
        } else {
            groupBy = groupBy.substring(0, groupBy.length() - 1);
        }
        if (orderBy.equals(PREFIX_ORDER_BY)) {
            orderBy = "";
        } else {
            orderBy = orderBy.substring(0, orderBy.length() - 1);
        }
        if (fetch.equals(PREFIX_FETCH)) {
            fetch = "";
        }
        return select + " " + from + " " + where + " " + groupBy + " " + orderBy + " " + fetch;
    }

}
