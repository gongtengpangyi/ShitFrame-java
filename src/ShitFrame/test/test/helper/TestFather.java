package test.helper;

public class TestFather {
	private String param1;

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}
	
	public String testFather(String name) {
		System.out.println(name);
		return "testFather";
	}
	
	public static String testFatherStatic(String name) {
		System.out.println(name);
		return "testFatherStatic";
	}

	@Override
	public String toString() {
		return "TestFather [param1=" + param1 + "]";
	}
	
}
