package shit.db.query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBResultException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.table.ShitDBField;
import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;

/**
 * 将查询结果变化为类,对于一般的查询操作适用
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBResultModel implements ShitDBResult<List<Serializable>> {
	/**
	 * 结果要生成的类
	 */
	private Class<?> modelClazz;
	
	/**
	 * 查询器
	 */
	private ShitDBQuery query;

	public ShitDBResultModel(Class<?> modelClazz, ShitDBQuery query) {
		super();
		this.modelClazz = modelClazz;
		this.query = query;
	}

	@Override
	public List<Serializable> analysis(ResultSet resultSet) throws ShitDBResultException, ShitDBConfigureException, ShitDBTranslateException {
		List<Serializable> list = new ArrayList<>();
		try {
			while (resultSet.next()) {
				/**
				 * 遍历所有的结果集，初始化一个model对象并取出所有的字段
				 */
				Field[] fields = ShitReflectHelper.findFields(modelClazz, true);
				Class<?>[] clazzs = {};
				Serializable model = (Serializable) ShitReflectHelper.newInstance(modelClazz, clazzs);
				for (Field field : fields) {
					/**
					 * 遍历所有字段，取出对应的数据表字段信息
					 */
					ShitDBField dbField = field.getAnnotation(ShitDBField.class);
					if (dbField == null) {
						/**
						 * 如果没有注解则直接进入下一个循环
						 */
						continue;
					}
					String foreignClassName = dbField.foreignClass();
					String value = resultSet.getString(dbField.name());
					if (value == null) {
						continue;
					}
					if (foreignClassName != null && !foreignClassName.equals("")) {
						/**
						 * 外键
						 */
						Class<?> foreignClass = field.getType();
						query.setModelClass(foreignClass);
						Serializable foreignModel = query.queryById(value);
						ShitReflectHelper.setNotStrCastValue(model, field.getName(),foreignModel, true);
					} else {
						/**
						 * 反射注入值
						 */
						ShitReflectHelper.setValue(model, field.getName(), value, true);
					}
				}
				/**
				 * 将model堆入list
				 */
				list.add(model);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ShitDBResultException(e);
		} catch (ShitReflectException e) {
			e.printStackTrace();
			throw new ShitDBResultException("类初始化错误");
		}
		return list;
	}

}
