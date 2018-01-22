package test.db.model;

import java.io.Serializable;

import shit.db.table.ShitDBField;

public class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6714926759766383904L;

	@ShitDBField(name="C_ID", length=11)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
