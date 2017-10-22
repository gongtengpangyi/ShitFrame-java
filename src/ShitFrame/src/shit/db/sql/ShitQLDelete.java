package shit.db.sql;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import shit.db.exception.ShitDBTranslateException;
import shit.db.table.ShitDBTable;
import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;

/**
 * 删除语句生成器，将model对象转化为delete语句 delete的条件可以根据shitQL来确定，如果shitQL不存在则根据model的主键确定
 * 
 * @author GongTengPangYi
 *
 */
public class ShitQLDelete extends ShitQLBuilder {
	/**
	 * 后方条件语句
	 */
	private String shitQLWhere;

	/**
	 * 后方条件语句的键值对
	 */
	private Map<String, Serializable> whereParamMap;

	public ShitQLDelete() {
		super();
	}

	public ShitQLDelete(Serializable model) {
		super(model);
	}

	/**
	 * 全部参数的构造器
	 * 
	 * @param model
	 *            数据模型
	 * @param shitQLWhere
	 *            条件语句
	 * @param whereParamMap
	 *            条件语句键值对
	 */
	public ShitQLDelete(Serializable model, String shitQLWhere, Map<String, Serializable> whereParamMap) {
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
	public ShitQLDelete setShitQLWhere(String shitQLWhere, Map<String, Serializable> whereParamMap) {
		this.shitQLWhere = shitQLWhere;
		this.whereParamMap = whereParamMap;
		return this;
	}

	@Override
	public ShitQLBuilder buildShitQL() throws ShitDBTranslateException {
		if (model == null) {
			throw new ShitDBTranslateException("没有待删除的model");
		}
		shitQLBuilder.append("delete from ").append(model.getClass().getName()).append(" ");
		/**
		 * 获取数据表信息
		 */
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
		shitQLBuilder.append(shitQLWhere);
		paramMap.putAll(whereParamMap);
		return this;
	}

}
