package com.zto.model;

public class Page {
	private int records;// 总过有多少个文档
	private int total;// 总页数
	private int page;// 当前页数
	private int pageSize;// 每页显示多少文档
	private int startIndex;// 开始的文档索引数

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getTotal() {
		if (records % pageSize == 0) {
			total = records / pageSize;
		} else {
			total = records / pageSize + 1;
		}
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartIndex() {
		startIndex = (page - 1) * pageSize;
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	@Override
	public String toString() {
		return "Page [records=" + records + ", total=" + total + ", page="
				+ page + ", pageSize=" + pageSize + ", startIndex="
				+ startIndex + "]";
	}

}
