package ibar.task.ecommerce.products.dao;

public class QueryBuilder {
    String baseQuery;
    String whereQuery;
    String groupByQuery;
    String orderByQuery;
    String havingQuery;
    Long pageSize;
    Long pageNumber;

    public QueryBuilder(String baseQuery) {
        this.baseQuery = baseQuery;
    }

    public QueryBuilder where(String addedQuery) {
        if (whereQuery == null) {
            this.whereQuery = " where " + addedQuery;
        } else {
            whereQuery += "and " + addedQuery;
        }
        return this;
    }

    public QueryBuilder groupBy(String addedQuery) {
        if (groupByQuery == null) {
            groupByQuery = " group by " + addedQuery;
        } else {
            groupByQuery += ", " + addedQuery;
        }
        return this;
    }

    public QueryBuilder orderBy(String addedQuery) {
        if (orderByQuery == null) {
            orderByQuery = " order by " + addedQuery;
        } else {
            orderByQuery += ", " + addedQuery;
        }
        return this;
    }
    
    public QueryBuilder having(String addedQuery){
    	if(havingQuery == null){
    		havingQuery = " having " + addedQuery;
    	}
    	else{
    		havingQuery += " and " + addedQuery;
    	}
    	return this;
    }
    
    public QueryBuilder setPageSize(Long pageSize){
    	this.pageSize = pageSize;
    	return this;
    }
    
    public QueryBuilder setPageNumber(Long pageNumber){
    	this.pageNumber = pageNumber;
    	return this;
    }

    private String getMainQuery(){
    	return baseQuery + (whereQuery == null ? "" : whereQuery) + (groupByQuery == null ? "" : groupByQuery) + (havingQuery == null ? "" : havingQuery) + (orderByQuery == null ? "" : orderByQuery);
    }

    public String getQuery() {
        if(pageSize != null){
        	if(pageNumber == null) {
        		pageNumber = 1L;
        	}
        	return getPagedQuery();
        }
        return getMainQuery();
    }
    
    private String getPagedQuery(){
    	long startRow = (pageNumber - 1) * pageSize + 1;
    	long endRow = pageNumber * pageSize;
    	return "select tt.* from ( select t.*, rownum r from(" + getMainQuery() + ") t) tt where tt.r >= " + startRow + " and tt.r <= " + endRow;
    }
}
