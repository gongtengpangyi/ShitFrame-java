package test.db.model;

import shit.db.table.ShitDBField;
import shit.db.table.ShitDBTable;

@ShitDBTable(name="T_USER", primaryKey="id")
public class User extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6344167477616952549L;
	@ShitDBField(name = "C_ACCOUNT", length = 50)
	private String account;
	
	@ShitDBField(name = "C_PASSWORD", length=50)
	private String password;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [account=" + account + ", password=" + password + "]";
	}

	

}
