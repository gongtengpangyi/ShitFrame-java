package shit.db.sql;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import shit.db.exception.ShitDBTranslateException;

/**
 * 基本ShitQL语句生成器
 * 
 * @author GongTengPangYi
 *
 */
public abstract class ShitQLBuilder {
	/**
	 * 无参数构造器
	 */
	public ShitQLBuilder() {
		super();
	}

	/**
	 * 带model参数的构造器
	 * 
	 * @param model
	 */
	public ShitQLBuilder(Serializable model) {
		this();
		this.setModel(model);
	}

	/**
	 * 需要存储的model对象
	 */
	protected Serializable model;

	/**
	 * ShitQL语句构造器
	 */
	protected StringBuilder shitQLBuilder;

	/**
	 * 参数键值对
	 */
	protected Map<String, Serializable> paramMap;

	/**
	 * 设置model
	 * 
	 * @param model
	 * @return 自身
	 */
	public ShitQLBuilder setModel(Serializable model) {
		this.model = model;
		this.shitQLBuilder = new StringBuilder();
		this.paramMap = new HashMap<>();
		return this;
	}

	/**
	 * 获取shitQL语句
	 * 
	 * @return
	 */
	public String getShitQL() {
		return shitQLBuilder.toString();
	}

	/**
	 * 获取键值对
	 * 
	 * @return
	 */
	public Map<String, Serializable> getParamMap() {
		return this.paramMap;
	}

	/**
	 * 生成insert的shitQL语句
	 * 
	 * @return 自身
	 * @throws ShitDBTranslateException
	 */
	public abstract ShitQLBuilder buildShitQL() throws ShitDBTranslateException;
}
