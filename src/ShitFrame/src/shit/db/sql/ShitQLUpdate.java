package shit.db.sql;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import shit.db.exception.ShitDBTranslateException;
import shit.db.table.ShitDBField;
import shit.db.table.ShitDBTable;
import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;

/**
 * 更新语句的生成器，生成model对象对应的update语句，
 * 其中update的值由model对象的变量确定，被update的数据的条件由shitQL语句和对应键值对决定
 * 如果没有shitQL语句则默认用model对象的主键来更新
 * 
 * @author GongTengPangYi
 *
 */
public class ShitQLUpdate extends ShitQLBuilder {
	/**
	 * 后方条件语句
	 */
	private String shitQLWhere;

	/**
	 * 后方条件语句的键值对
	 */
	private Map<String, Serializable> whereParamMap;

	public ShitQLUpdate() {
		super();
	}

	public ShitQLUpdate(Serializable model) {
		super(model);
	}

	/**
	 * 全部参数的构造器
	 * 
	 * @param model
	 *            更新的数据模型
	 * @param shitQLWhere
	 *            条件语句
	 * @param whereParamMap
	 *            条件语句键值对
	 */
	public ShitQLUpdate(Serializable model, String shitQLWhere, Map<String, Serializable> whereParamMap) {
		super(model);
		this.setShitQLWhere(shitQLWhere, whereParamMap);
	}

	/**
	 * 设置后方条件
	 * 
	 * @param shitQLWhere
	 *            shitQL条件语句
	 * @param whereParamMap
	 *            条件语句的键值对
	 * @return 本身
	 */
	public ShitQLUpdate setShitQLWhere(String shitQLWhere, Map<String, Serializable> whereParamMap) {
		this.shitQLWhere = shitQLWhere;
		this.whereParamMap = whereParamMap;
		return this;
	}

	@Override
	public ShitQLBuilder buildShitQL() throws ShitDBTranslateException {
		if (model == null) {
			throw new ShitDBTranslateException("没有待更新的model");
		}
		shitQLBuilder.append("update ");
		shitQLBuilder.append(model.getClass().getName()).append(" set ");
		this.buildValueShitQL();
		return this;
	}

	/**
	 * @throws ShitDBTranslateException
	 * 
	 */
	private void buildValueShitQL() throws ShitDBTranslateException {
		Field[] fields = ShitReflectHelper.findFields(model.getClass(), true);
		ShitDBTable dbTable = model.getClass().getAnnotation(ShitDBTable.class);
		if (dbTable == null) {
			throw new ShitDBTranslateException(model.getClass().getName() + "没有数据表注解");
		}
		/**
		 * 如果条件语句为空就根据主键自动去生成
		 */
		if (shitQLWhere == null) {
			String primaryKey = dbTable.primaryKey();
			try {
				Serializable keyValue = (Serializable) ShitReflectHelper.getValue(model, primaryKey, true);
				if (keyValue == null) {
					throw new ShitDBTranslateException("主键未赋值");
				}
				shitQLWhere = "where " + primaryKey + "=:" + primaryKey;
				whereParamMap = new HashMap<>(1);
				whereParamMap.put(primaryKey, keyValue);
			} catch (ShitReflectException e) {
				e.printStackTrace();
				throw new ShitDBTranslateException("主键未赋值");
			}
		}
		for (Field field : fields) {
			/**
			 * 获取数据库字段信息
			 */
			ShitDBField dbField = field.getAnnotation(ShitDBField.class);
			if (dbField == null) {
				continue;
			}
			/**
			 * 获取变量名
			 */
			String fieldName = field.getName();
			try {
				/**
				 * 获取value的值
				 */
				Serializable value = (Serializable) ShitReflectHelper.getValue(model, fieldName, true);
				if (value == null) {
					/**
					 * 如果参数为空，要么跳过要么抛出异常
					 */
					if (dbField.notNull()) {
						throw new ShitDBTranslateException("参数" + fieldName + "不可为空");
					}
					continue;
				}
				if (dbField.foreignClass().equals("")) {
					/**
					 * 如果非外键，为了避免和后方条件语句冲突加上&符号
					 */
					shitQLBuilder.append(fieldName).append("=").append(":&").append(fieldName).append(",");
					paramMap.put("&" + fieldName, value);
				} else {
					/**
					 * 获取外键类信息
					 */
					String foreignClassName = dbField.foreignClass();
					Class<?> foreignClass = field.getType();
					ShitDBTable dbForeignTable = foreignClass.getAnnotation(ShitDBTable.class);
					if (dbForeignTable == null) {
						throw new ShitDBTranslateException("类" + foreignClassName + "没有注解数据表");
					}
					/**
					 * 参数名变为类似于user.id这样子的
					 */
					String foreignTableKey = dbForeignTable.primaryKey();
					Serializable id = (Serializable) ShitReflectHelper.getValue(value, foreignTableKey, true);
					if (id == null) {
						throw new ShitDBTranslateException("外键未赋值:" + foreignClassName);
					}
					shitQLBuilder.append(fieldName + "." + foreignTableKey).append("=").append(":&")
							.append(dbField.name()).append(",");
					paramMap.put("&" + dbField.name(), id);
				}
			} catch (ShitReflectException e) {
				e.printStackTrace();
				throw new ShitDBTranslateException("错误的变量值：" + fieldName);
			}
		}
		shitQLBuilder.delete(shitQLBuilder.length() - 1, shitQLBuilder.length()).append(" ").append(shitQLWhere);
		paramMap.putAll(whereParamMap);
	}

}
