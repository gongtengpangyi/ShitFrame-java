package test.db.model;

import shit.db.table.ShitDBField;
import shit.db.table.ShitDBTable;

@ShitDBTable(name="USER", primaryKey="id")
public class User extends Entity {

	@Override
	public String toString() {
		return "User [name=" + name + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6344167477616952549L;
	@ShitDBField(name = "NAME", length = 255)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
