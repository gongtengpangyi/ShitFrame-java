package test.db;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BlobTest {
	@Test
	public void test1() {
		Map<String, Object> map = new HashMap<>();
		byte[] b = {};
		map.put("", b);
	}
}
