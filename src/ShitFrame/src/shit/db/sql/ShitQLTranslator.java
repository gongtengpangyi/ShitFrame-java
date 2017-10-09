package shit.db.sql;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shit.db.exception.ShitDBTranslateException;
import shit.db.query.ShitDBPager;
import shit.db.table.ShitDBField;
import shit.db.table.ShitDBTable;
import shit.helper.ShitReflectHelper;

/**
 * ShitQL翻译器，ShitQL的语法： 1)将SQL的表名替代为类名，如select * from
 * com.example.model.User.class 2)将SQL的字段名替代为变量名，且提供冒号形式占位符，并传入占位符键值对，如where
 * name = :name 3)分页查询由pager来控制
 * 
 * @author GongTengPangYi
 *
 */
public class ShitQLTranslator {
	/**
	 * 占位符的正则
	 */
	private static final Pattern placeHolderPattern = Pattern.compile(":[a-z|A-Z]*");

	/**
	 * 类名的正则表达式
	 */
	private Pattern classNamePattern;

	/**
	 * 原始数据
	 */
	private String shitQL;
	private Map<String, Serializable> paramMap;
	private ShitDBPager pager;

	/**
	 * 生成的数据
	 */
	private String sql;
	private List<Serializable> paramList;

	/**
	 * 构造器
	 * 
	 * @param shitQL
	 *            shitQL
	 * @param paramMap
	 *            占位符参数键值对
	 * @param pager
	 *            分页控制器
	 * @throws ShitDBTranslateException
	 *             翻译错误
	 */
	public ShitQLTranslator(String packageName, String shitQL, Map<String, Serializable> paramMap, ShitDBPager pager)
			throws ShitDBTranslateException {
		super();
		classNamePattern = Pattern.compile(packageName + ".[a-z|A-Z|.]*");
		this.shitQL = shitQL;
		this.paramMap = paramMap;
		this.pager = pager;
		this.translate();
	}

	/**
	 * 获取SQL
	 * 
	 * @return SQL语句
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * 获取参数列表
	 * 
	 * @return 参数列表
	 */
	public List<Serializable> getParamList() {
		return paramList;
	}

	/**
	 * 设置ShitQL
	 * 
	 * @param shitQL
	 */
	public void setShitQL(String shitQL) {
		this.shitQL = shitQL;
	}

	/**
	 * 设置参数键值对
	 * 
	 * @param paramMap
	 */
	public void setParamMap(Map<String, Serializable> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 设置分页控制器
	 * 
	 * @param pager
	 */
	public void setPager(ShitDBPager pager) {
		this.pager = pager;
	}

	/**
	 * 执行翻译过程
	 * 
	 * @throws ShitDBTranslateException
	 *             翻译错误
	 */
	private void translate() throws ShitDBTranslateException {
		this.paramList = new ArrayList<>();
		this.sql = this.shitQL;
		this.translatePlaceHolder();
		this.translateTable();
		this.translatePager();
		this.sql = this.sql + ";";
	}

	/**
	 * 翻译占位符
	 * 
	 * @throws ShitDBTranslateException
	 *             翻译错误
	 */
	private void translatePlaceHolder() throws ShitDBTranslateException {
		Matcher mat = placeHolderPattern.matcher(sql);
		while (mat.find()) {
			// 遍历占位符将其替换为? ，并且将值压入list中
			String key = mat.group();
			sql = sql.replace(key, "?");
			if (paramMap == null) {
				throw new ShitDBTranslateException("键值对未加入占位符：" + key);
			}
			Serializable param = paramMap.get(key.substring(1));
			if (param == null) {
				throw new ShitDBTranslateException("键值对未加入占位符：" + key);
			}
			paramList.add(param);
		}
	}

	/**
	 * 翻译表名
	 * 
	 * @throws ShitDBTranslateException
	 *             翻译错误
	 */
	private void translateTable() throws ShitDBTranslateException {
		Matcher mat = classNamePattern.matcher(sql);
		if (mat.find()) {
			String className = mat.group();
			try {
				Class<?> clazz = Class.forName(className);
				ShitDBTable tableAnnotation = clazz.getAnnotation(ShitDBTable.class);
				if (tableAnnotation == null) {
					throw new ShitDBTranslateException("此类：" + className + "没有注解数据表");
				}
				sql = sql.replace(className, tableAnnotation.name());
				Field[] fields = ShitReflectHelper.findFields(clazz, true);
				for (Field field : fields) {
					this.translateField(field);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new ShitDBTranslateException("不存在此类：" + className);
			}
		}
	}

	/**
	 * 字段翻译
	 * 
	 * @param field
	 *            翻译SQL中的字段内容
	 * @throws ShitDBTranslateException
	 */
	private void translateField(Field field) throws ShitDBTranslateException {
		ShitDBField fieldAnnotation = field.getAnnotation(ShitDBField.class);
		if (fieldAnnotation == null) {
			return;
		}
		String dbFieldName = fieldAnnotation.name();
		String fieldName = field.getName();
		if (fieldAnnotation.foreignClass().equals("")) {
			while (sql.indexOf(fieldName) > 0) {
				sql = sql.replace(fieldName, dbFieldName);
			}
		} else {
			translateForeignKey(field, fieldAnnotation, dbFieldName, fieldName);
		}
	}

	/**
	 * 翻译外键字段
	 * 
	 * @param field
	 * @param fieldAnnotation
	 * @param dbFieldName
	 * @param fieldName
	 * @throws ShitDBTranslateException
	 */
	private void translateForeignKey(Field field, ShitDBField fieldAnnotation, String dbFieldName, String fieldName)
			throws ShitDBTranslateException {
		Pattern pat = Pattern.compile(fieldName + ".[a-z|A-Z]*\\s*=\\s*[^\\s]*");
		Matcher mat = pat.matcher(sql);
		String key = "";
		if (mat.find()) {
			 key = mat.group();
		}
		String foreignClassName = fieldAnnotation.foreignClass();
		ShitDBTable foreignAnnotation = field.getType().getAnnotation(ShitDBTable.class);
		if (foreignAnnotation == null) {
			throw new ShitDBTranslateException("类:" + foreignClassName + "没有数据表注解");
		}
		String childShitQL = "select " + foreignAnnotation.primaryKey() + " from " + foreignClassName + " where ";
		String childSql = new ShitQLTranslator(foreignClassName.substring(0, foreignClassName.lastIndexOf(".")),
				childShitQL, null, null).getSql();
		String replaceKey = dbFieldName + "=(" + childSql.substring(0, childSql.length()-1)
		+ key.substring(fieldName.length()+1) + ")";
		sql = sql.replace(key, replaceKey);
	}

	/**
	 * 翻译页码
	 */
	private void translatePager() {
		if (pager != null) {
			int start = (pager.getPagerIndex() - 1) * pager.getPagerSize();
			sql = sql + " limit " + start + "," + pager.getPagerSize();
		}
	}

}
