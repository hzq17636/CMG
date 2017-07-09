package com.jz.crm.base.mappers.common;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class WhereFieldBuilder {
	private final String defaultValue = " WHERE 1=1 ";
	private StringBuilder where = new StringBuilder(defaultValue);// 查找属性名称

	public String build() {
		return where.append(" ").toString();
	}
    /**
     * 根据变量生成条件
     * 转换符
     * %s: 字符串类型，如："ljq"
     * %b: 布尔类型，如：true
     * %d: 整数类型(十进制)，如：99
     * %f: 浮点类型，如：99.99
     * %%: 百分比类型，如：％
     * %n: 换行符
     * @param where
     * @param values
     * @return
     */
	public WhereFieldBuilder addWhere(String where, Object[] values) {
		if (StringUtils.isEmpty(where)) {
			return this;
		}
		if (!ArrayUtils.isEmpty(values)) {
			Object[] escapeSqlValues = new Object[values.length];
			int i = 0;
			for (Object each : values) {
				if (each instanceof String) {
					escapeSqlValues[i++] = StringEscapeUtils.escapeSql(each.toString());
				} else {
					escapeSqlValues[i++] = each;
				}
			}
			where = String.format(where, escapeSqlValues);
		}
		this.where.append(" and ( ").append(where).append(" )");
		return this;
	}

	/**
	 * 判断是否有条件中是否有where 1=1
	 * @return
	 */
	public boolean hasAddWhere() {
		return !where.toString().equals(defaultValue);
	}

	public void reset() {
		where.setLength(0);
		where.append(defaultValue);
	}
	
	public static void main(String[] args) {
		String where = "a.feild='%2$s'";
		WhereFieldBuilder whereFieldBuilder = new WhereFieldBuilder();
		String phone = "1233";
		WhereFieldBuilder obj = whereFieldBuilder.addWhere(where, new Object[]{"111","222"});
		System.err.println(obj.where.toString());
	}
}