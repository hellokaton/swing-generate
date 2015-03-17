package org.unique.generator.model;

import org.unique.generator.StringUtil;

/**
 * 列信息实体
 * @ClassName: ColumnBean 
 * @author AndrewWen
 * @date 2013-1-14 下午5:01:03
 */
public class Column {
	
	/**
	 * 数据库中列名称
	 */
	private String columnName;
	
	/**
	 * 生成实体字段名称
	 */
	private String fieldName;
	/**
	 * 生成实体字段类型
	 */
	private String fieldType;
	/**
	 * get方法
	 */
	private String fieldGetMethod;
	/**
	 * set方法
	 */
	private String fieldSetMehtod;
	
	/**
	 * 列数据类型
	 */
	private int columnType;
	
	/**
	 * 列注释
	 */
	private String columnComment;
	
	/**
	 * 是否主键
	 */
	private boolean isPrimary = false;
	/**
	 * 是否自动递增
	 */
	private boolean isAutoIncrement = false;
	/**
	 * 是否允许空
	 */
	private boolean isNullAble=false;

	/**
	 * 是否是外键
	 */
	private boolean isForeignKey=false;

	public boolean isForeignKey() {
		return isForeignKey;
	}

	public void setForeignKey(boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public boolean isNullAble() {
		return isNullAble;
	}

	public void setNullAble(boolean isNullAble) {
		this.isNullAble = isNullAble;
	}
	
	public String getFieldName() {
		this.fieldName = StringUtil.getClassLower(StringUtil.getDomainColumnName(this.columnName));
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	
	public String getFieldType() {
		this.fieldType = StringUtil.getColumnType(this.columnType);
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldGetMethod() {
		if(null != this.fieldName){
			this.fieldGetMethod = StringUtil.getGetMethod(this.fieldName);
		} else {
			if(null != this.columnName){
				this.fieldGetMethod = StringUtil.getGetMethod(this.columnName);
			}
		}
		return fieldGetMethod;
	}

	public void setFieldGetMethod(String fieldGetMethod) {
		this.fieldGetMethod = fieldGetMethod;
	}

	public String getFieldSetMehtod() {
		if(null != this.fieldName){
			this.fieldSetMehtod = StringUtil.getSetMethod(this.fieldName);
		} else {
			if(null != this.columnName){
				this.fieldSetMehtod = StringUtil.getSetMethod(this.columnName);
			}
		}
		return fieldSetMehtod;
	}

	public void setFieldSetMehtod(String fieldSetMehtod) {
		this.fieldSetMehtod = fieldSetMehtod;
	}
	
}
