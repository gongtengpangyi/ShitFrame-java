package test.db.model;

import java.util.Date;

import shit.db.table.ShitDBField;
import shit.db.table.ShitDBTable;

@ShitDBTable(name = "TRADE", primaryKey="id")
public class Trade extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8753022799366433760L;

	@ShitDBField(name = "NAME", length = 255)
	private String name;
	@ShitDBField(name = "DISCOUNT")
	private Float discount;
	@ShitDBField(name = "CUTTIME")
	private Date cutTime;
	@ShitDBField(name = "PRICE")
	private Float price;
	@ShitDBField(name = "COUNT", length = 11)
	private Integer count;
	@ShitDBField(name = "USERID", length=11, foreignClass="test.db.model.User")
	private User user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Date getCutTime() {
		return cutTime;
	}

	public void setCutTime(Date cutTime) {
		this.cutTime = cutTime;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Trade [name=" + name + ", discount=" + discount + ", cutTime=" + cutTime + ", price=" + price
				+ ", count=" + count + ", user=" + user.toString() + "]";
	}

}
