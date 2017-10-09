package test.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import shit.db.exception.ShitDBTranslateException;
import shit.db.query.ShitDBPager;
import shit.db.sql.ShitQLTranslator;
import test.db.model.Trade;

public class TranslatorTest {
	@Test
	public void test1() {
		Pattern pat = Pattern.compile("[a-z|A-Z|.]*.class");
		String sql = "select * from com.example.model.Model.class";
		Matcher mat = pat.matcher(sql);
		if (mat.find()) {
			System.out.println(mat.group());
		}
	}
	
	@Test
	public void test2() {
		String shitQL = "select * from " + Trade.class.getName() + 
				" o where o.id=1 and o.name=:name order by o.id";
		Map<String, Serializable> params = new HashMap<>(1);
		params.put("name", "xxx");
		ShitDBPager pager = new ShitDBPager(3, 2);
		try {
			ShitQLTranslator translator = new ShitQLTranslator("test.db.model", shitQL, params, pager);
			System.out.println(translator.getSql());
			System.out.println(translator.getParamList());
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test3() {
		String shitQL = "select * from " + Trade.class.getName() + 
				" o where o.id=1 and o.name=:name and o.user.id=1 order by o.id";
		Map<String, Serializable> params = new HashMap<>(1);
		params.put("name", "xxx");
		ShitDBPager pager = new ShitDBPager(3, 2);
		try {
			ShitQLTranslator translator = new ShitQLTranslator("test.db.model", shitQL, params, pager);
			System.out.println(translator.getSql());
			System.out.println(translator.getParamList());
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		}
		
	}
}
