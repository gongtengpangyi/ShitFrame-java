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
 * name = :name 3)分页查询由pager来控制； 翻译器将shitQL语句翻译为对应的sql语句，同时将占位符键值对转化为list
 * 
 * @author GongTengPangYi
 *
 */
public class ShitQLTranslator {
	/**
	 * 占位符的正则
	 */
	private static final Pattern placeHolderPattern = Pattern.compile(":[&]*[a-z|A-Z|0-9]*");

	/**
	 * 匹配引号内的内容
	 */
	private static final Pattern quotesPattern = Pattern.compile("\'[^\']*\'");

	/**
	 * 条件语句的外键匹配正则
	 */
	private static final String foreignPatternWhereStr = ".[a-z|A-Z]*\\s*=\\s*[^\\s&^,]*";

	/**
	 * 非条件语句的外键匹配正则
	 */
	private static final String foreignPatternOtherStr = ".[a-z|A-Z]*\\s*";

	/**
	 * 类名后缀匹配正则
	 */
	private static final String classNamePatternStr = ".[a-z|A-Z|.]*";

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
		classNamePattern = Pattern.compile(packageName + classNamePatternStr);
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
		this.pretreatment();
		this.translatePlaceHolder();
		this.translateTable();
		this.translatePager();
		this.sql = this.sql + ";";
	}

	/**
	 * 对字符串做个预处理
	 */
	private void pretreatment() {
		/**
		 * 为了避免SQL语句字符值中的内容影响后续匹配，将其全部变化为&&开头的占位符
		 */
		Matcher mat = quotesPattern.matcher(sql);
		int i = 0;
		while (mat.find()) {
			String key = "&&pre" + i++;
			String value = mat.group();
			sql = sql.replace(value, ":" + key);
			value = value.substring(1, value.length() - 1);
			paramMap.put(key, value);
		}
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
	 *            变量
	 * @param fieldAnnotation
	 *            数据库字段信息
	 * @param dbFieldName
	 *            数据库字段名
	 * @param fieldName
	 *            变量名
	 * @throws ShitDBTranslateException
	 *             翻译出错
	 */
	private void translateForeignKey(Field field, ShitDBField fieldAnnotation, String dbFieldName, String fieldName)
			throws ShitDBTranslateException {
		String key = getForeignPatternWhereStr(fieldName);
		if (key != null && !key.equals("")) {
			/**
			 * 获取外键链接的类名和对应的数据表注解信息
			 */
			String foreignClassName = fieldAnnotation.foreignClass();
			ShitDBTable foreignAnnotation = field.getType().getAnnotation(ShitDBTable.class);
			if (foreignAnnotation == null) {
				throw new ShitDBTranslateException("类:" + foreignClassName + "没有数据表注解");
			}
			/**
			 * 拼接出子select语句
			 */
			String childShitQL = "select " + foreignAnnotation.primaryKey() + " from " + foreignClassName + " where ";
			String childSql = new ShitQLTranslator(foreignClassName.substring(0, foreignClassName.lastIndexOf(".")),
					childShitQL, null, null).getSql();
			String replaceKey = dbFieldName + "=(" + childSql.substring(0, childSql.length() - 1)
					+ key.substring(fieldName.length() + 1) + ")";
			sql = sql.replace(key, replaceKey);
			return;
		}
		/**
		 * 若上述拼接不出，说明不是在条件语句中，类似insert into xxx.xxx.Trade(user.id) values
		 * (:userId);
		 */
		Pattern pat = Pattern.compile(fieldName + foreignPatternOtherStr);
		Matcher mat = pat.matcher(sql);
		if (mat.find()) {
			key = mat.group();
			sql = sql.replace(key, fieldAnnotation.name());
			return;
		}
	}

	/**
	 * 匹配类似于 user.name=:userName这样的语句
	 * 
	 * @param fieldName
	 *            字段名，如上面说的user
	 * @return 匹配结果
	 */
	private String getForeignPatternWhereStr(String fieldName) {
		String key = "";
		Pattern pat = Pattern.compile(fieldName + foreignPatternWhereStr);
		Matcher mat = pat.matcher(sql);
		if (mat.find()) {
			/**
			 * 匹配成功
			 */
			key = mat.group();
		}
		return key;
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
