package shit.db.sql;

import java.io.Serializable;
import java.lang.reflect.Field;

import shit.db.exception.ShitDBTranslateException;
import shit.db.table.ShitDBField;
import shit.db.table.ShitDBTable;
import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;

/**
 * Save操作的ShitQL生成器， 将model对象转化为save语句，其中save的值由model对象来确定
 * 
 * @author GongTengPangYi
 *
 */
public class ShitQLSave extends ShitQLBuilder {

	public ShitQLSave() {
		super();
	}

	public ShitQLSave(Serializable model) {
		super(model);
	}

	@Override
	public ShitQLSave buildShitQL() throws ShitDBTranslateException {
		if (model == null) {
			throw new ShitDBTranslateException("没有待存储的model");
		}
		shitQLBuilder.append("insert into ");
		shitQLBuilder.append(model.getClass().getName()).append(" (");
		this.buildValueShitQL();
		return this;
	}

	/**
	 * 生成insert语句后面的value
	 * 
	 * @throws ShitDBTranslateException
	 */
	private void buildValueShitQL() throws ShitDBTranslateException {
		StringBuilder valueBuilder = new StringBuilder(") values (");
		Field[] fields = ShitReflectHelper.findFields(model.getClass(), true);
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
					 * 如果非外键
					 */
					shitQLBuilder.append(fieldName).append(",");
					valueBuilder.append(":").append(fieldName).append(",");
					paramMap.put(fieldName, value);
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
					shitQLBuilder.append(fieldName + "." + foreignTableKey).append(",");
					valueBuilder.append(":").append(dbField.name()).append(",");
					Serializable id = (Serializable) ShitReflectHelper.getValue(value, foreignTableKey, true);
					if (id == null) {
						throw new ShitDBTranslateException("外键未赋值:" + foreignClassName);
					}
					paramMap.put(dbField.name(), id);
				}
			} catch (ShitReflectException e) {
				e.printStackTrace();
				throw new ShitDBTranslateException("错误的变量值：" + fieldName);
			}
		}
		/**
		 * 去掉两个字符串最后的逗号并拼接
		 */
		valueBuilder.delete(valueBuilder.length() - 1, valueBuilder.length());
		shitQLBuilder.delete(shitQLBuilder.length() - 1, shitQLBuilder.length()).append(valueBuilder).append(")");
	}

}
