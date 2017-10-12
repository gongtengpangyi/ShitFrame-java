package test.db;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import shit.db.exception.ShitDBTranslateException;
import shit.db.query.ShitDBPager;
import shit.db.sql.ShitQLBuilder;
import shit.db.sql.ShitQLDelete;
import shit.db.sql.ShitQLSameModel;
import shit.db.sql.ShitQLSave;
import shit.db.sql.ShitQLTranslator;
import shit.db.sql.ShitQLUpdate;
import test.db.model.Trade;
import test.db.model.User;

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
				" o where o.id=1 and o.name=:name and o.user.id=',' order by o.id";		
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
	public void test4() {
		Trade trade = new Trade();
		trade.setCount(1);
		trade.setCutTime(new Date());
		trade.setDiscount((float) 0.7);
		trade.setName("xxxx");
		User user = new User();
		user.setId((long) 1);
		trade.setUser(user);
		try {
			ShitQLBuilder save = new ShitQLSave(trade).buildShitQL();
			String shitQL = save.getShitQL();
			Map<String, Serializable> params = save.getParamMap();
			System.out.println(shitQL);
			System.out.println(params);
			ShitQLTranslator translator = new ShitQLTranslator("test.db.model", shitQL, params, null);
			System.out.println(translator.getSql());
			System.out.println(translator.getParamList());
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test5() {
		Trade trade = new Trade();
		trade.setCount(1);
		trade.setCutTime(new Date());
		trade.setDiscount((float) 0.7);
		trade.setName("xxxx");
		trade.setId((long) 1);
		User user = new User();
		user.setId((long) 1);
		trade.setUser(user);
		try {
			Map<String, Serializable> whereParam = new HashMap<>();
			whereParam.put("id", 1);
			ShitQLBuilder update = new ShitQLUpdate(trade).buildShitQL();
			String shitQL = update.getShitQL();
			Map<String, Serializable> params = update.getParamMap();
			System.out.println(shitQL);
			System.out.println(params);
			ShitQLTranslator translator = new ShitQLTranslator("test.db.model", shitQL, params, null);
			System.out.println(translator.getSql());
			System.out.println(translator.getParamList());
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void test6() {
		Trade trade = new Trade();
		trade.setId((long) 1);
		try {
			Map<String, Serializable> whereParam = new HashMap<>();
			whereParam.put("name", "xxx");
			ShitQLBuilder delete = new ShitQLDelete(trade, "where name=:name", whereParam).buildShitQL();
			String shitQL = delete.getShitQL();
			Map<String, Serializable> params = delete.getParamMap();
			System.out.println(shitQL);
			System.out.println(params);
			ShitQLTranslator translator = new ShitQLTranslator("test.db.model", shitQL, params, null);
			System.out.println(translator.getSql());
			System.out.println(translator.getParamList());
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test7() {
		Trade trade = new Trade();
		trade.setCount(1);
		trade.setCutTime(new Date());
		trade.setDiscount((float) 0.7);
		trade.setName("xxxx");
		User user = new User();
		user.setId((long) 1);
		trade.setUser(user);
		try {
			ShitQLBuilder sameModel = new ShitQLSameModel(trade).buildShitQL();
			String shitQL = sameModel.getShitQL();
			Map<String, Serializable> params = sameModel.getParamMap();
			System.out.println(shitQL);
			System.out.println(params);

		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		}
		
	}
}
