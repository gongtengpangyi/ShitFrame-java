package shit.toolspring;

import java.io.Serializable;

/**
 * 用于Hibernate的分页查询的单元
 * @author ASUS
 *
 */
public class Pager implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6343415824589059307L;
	// private Integer pageCount=0;
	private int pageSize = 10;
	private int pageIndex = 1;
	private int recordCount = 0;

	public int getPageCount() {
		return new Double(Math.ceil((recordCount + 0.0) / pageSize)).intValue();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getStart() {
		return (pageIndex - 1) * pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
