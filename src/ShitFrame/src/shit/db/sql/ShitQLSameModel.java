package shit.db.sql;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import shit.db.exception.ShitDBTranslateException;
import shit.db.table.ShitDBField;
import shit.db.table.ShitDBTable;
import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;

/**
 * 生成查找参数几乎相同的model的where语句 在少数情况下我们会将判断的条件放在model对象中，而这里将model对象的内参值转化为条件语句
 * 但是这里不支持浮点类型和时间
 * 
 * @author GongTengPangYi
 *
 */
public class ShitQLSameModel extends ShitQLBuilder {

	public ShitQLSameModel(Serializable model) {
		super(model);
	}

	@Override
	public ShitQLBuilder buildShitQL() throws ShitDBTranslateException {
		shitQLBuilder.append(" where ");
		Class<?> clazz = model.getClass();
		Field[] fields = ShitReflectHelper.findFields(clazz, true);
		for (Field field : fields) {
			ShitDBField dbField = field.getAnnotation(ShitDBField.class);
			Class<?> type = field.getType();
			if (dbField == null || type.equals(Float.class) || type.equals(Double.class) || type.equals(Date.class)) {
				continue;
			}
			String fieldName = field.getName();
			try {
				Serializable value = (Serializable) ShitReflectHelper.getValue(model, fieldName, true);
				if (value == null) {
					continue;
				}
				if (dbField.foreignClass().equals("")) {
					/**
					 * 如果非外键
					 */
					shitQLBuilder.append(fieldName).append("=:").append(fieldName).append(" and ");
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
					shitQLBuilder.append(fieldName + "." + foreignTableKey).append("=:").append(dbField.name())
							.append(" and ");
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
		shitQLBuilder.delete(shitQLBuilder.length() - 4, shitQLBuilder.length()).append(" order by ")
				.append(clazz.getAnnotation(ShitDBTable.class).primaryKey()).append(" desc");
		return this;
	}

}
