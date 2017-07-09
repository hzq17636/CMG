package com.jz.crm.base.mappers.common;

import java.util.HashMap;
import java.util.Map;

public class Criteria {
	public void setForUpdate(){
		addParameter(FORUPDATE, " FOR UPDATE ");
	}
	
	public boolean hasAddWhere(){
		return whereBuilder.hasAddWhere();
	}
	
	public void addWhere(String where, Object[] values){
		whereBuilder.addWhere(where, values);
	}
	
	public void addAscOrder(String filed){
		orderBuilder.addAscOrder(filed);
	}

	public void addDescOrder(String filed){
		orderBuilder.addDescOrder(filed);
	}
	
	public void setSumField(String sumField){
		addParameter(SUMFIELD, sumField);
	}

	public void setLimit(int limit){
		addParameter(LIMIT, " limit " + limit+ " ");
	}
	
	public void addParameter(String key, String value){
		paraMap.put(key, value);
	}
	
	public Map<String, String> getAnswer(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(WHERE, whereBuilder.build());
		map.put(ORDER, orderBuilder.build());
		map.putAll(paraMap);
		return map;
	}
	
	public void reset(){
		paraMap.clear();
		whereBuilder.reset();
		orderBuilder.reset();
	}
	/*public function*/
		public static WhereFieldBuilder newWhereBuilder(){
			return new WhereFieldBuilder();
		}
		public static OrderFieldBuilder newOrderBuilder(){
			return new OrderFieldBuilder();
		}
	/*END - public function*/
		
	/*private property*/
		private Map<String, String> paraMap = new HashMap<String, String>(10);
		private WhereFieldBuilder whereBuilder = newWhereBuilder();
		private OrderFieldBuilder orderBuilder = newOrderBuilder();
	/*END - private property*/
		
	/*public final property*/
		public static final String WHERE = "where";
		public static final String ORDER = "order";
		public static final String SUMFIELD = "sumField";
		public static final String LIMIT = "limit";
		public static final String FORUPDATE= "for_update";
	/*END - public final property*/
	public void setPageNo(int start,int limit){
		addParameter(LIMIT, " limit " + start+ ","+limit);
	}
}
