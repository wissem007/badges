package tn.com.smartsoft.framework.dao.definition;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DbConstraintDefinition implements IDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int PK_TYPE = 0;
	public static final int FK_TYPE = 1;

	private String name;
	private String schema;
	protected int type = 0;
	private Class<?> classException;
	private String onDeleteErrorId;
	private String onUpdateErrorId;
	private String errorId;

	public DbConstraintDefinition() {
		super();
		this.type = PK_TYPE;
	}

	public DbConstraintDefinition(String name, String schema, Class<?> classException, String onDeleteErrorId, String onUpdateErrorId) {
		super();
		this.name = name;
		this.schema = schema;
		this.type = FK_TYPE;
		this.classException = classException;
		this.onDeleteErrorId = onDeleteErrorId;
		this.onUpdateErrorId = onUpdateErrorId;
	}

	public DbConstraintDefinition(String name, String schema, Class<?> classException, String errorId) {
		super();
		this.name = name;
		this.schema = schema;
		this.type = PK_TYPE;
		this.classException = classException;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public boolean isPkType() {
		return type == PK_TYPE;
	}

	public Class<?> getClassException() {
		return classException;
	}

	public void setClassException(Class<?> classException) {
		this.classException = classException;
	}

	public String getOnDeleteErrorId() {
		return onDeleteErrorId;
	}

	public void setOnDeleteErrorId(String onDeleteErrorId) {
		this.onDeleteErrorId = onDeleteErrorId;
	}

	public String getOnUpdateErrorId() {
		return onUpdateErrorId;
	}

	public void setOnUpdateErrorId(String onUpdateErrorId) {
		this.onUpdateErrorId = onUpdateErrorId;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getId() {
		return getSchema() + "." + getName();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
