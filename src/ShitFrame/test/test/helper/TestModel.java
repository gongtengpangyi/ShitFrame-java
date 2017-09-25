package test.helper;

import java.util.Date;

public class TestModel extends TestFather{
	private Date time;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String testChild(String name) {
		System.out.println(name);
		return "testChild";
	}
	
	public static String testChildStatic(String name) {
		System.out.println(name);
		return "testChildStatic";
	}

	public TestModel(Date time) {
		super();
		this.time = time;
	}

	public TestModel() {
		super();
		this.time = new Date();
	}

	@Override
	public String toString() {
		return "TestModel [time=" + time + ", name=" + name + ", getParam1()=" + getParam1() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
}
