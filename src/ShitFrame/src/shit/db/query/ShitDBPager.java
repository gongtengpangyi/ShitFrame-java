package shit.db.query;


/**
 * 分页信息，当需要分页查询的时候，需要把分页的信息存入这个类的对象中再用query进行查询
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBPager {
	/**
	 * 页长度
	 */
	private Integer pagerSize;

	/**
	 * 查询页码
	 */
	private Integer pagerIndex;

	/**
	 * 基本构造函数
	 */
	public ShitDBPager() {
		super();
	}

	/**
	 * 带参数构造函数
	 * 
	 * @param pagerSize
	 *            页长度
	 * @param pagerIndex
	 *            页码
	 */
	public ShitDBPager(Integer pagerSize, Integer pagerIndex) {
		super();
		this.pagerSize = pagerSize;
		this.pagerIndex = pagerIndex;
	}

	/**
	 * 获取页长度
	 * 
	 * @return
	 */
	public Integer getPagerSize() {
		return pagerSize != null ? pagerSize : 0;
	}

	/**
	 * 设置页长度,从1开始计数
	 * 
	 * @param pagerSize
	 */
	public void setPagerSize(Integer pagerSize) {
		this.pagerSize = pagerSize;
	}

	/**
	 * 获取页码
	 * 
	 * @return
	 */
	public Integer getPagerIndex() {
		return pagerIndex != null ? pagerIndex : 1;
	}

	/**
	 * 设置页码
	 * 
	 * @param pagerIndex
	 */
	public void setPagerIndex(Integer pagerIndex) {
		this.pagerIndex = pagerIndex;
	}

}
