package org.zarroboogs.wangyiyunclass.javabean;

import java.util.ArrayList;
import java.util.List;

public class Cateory {
	
	private int totalCount = 0;
	private int totalPage = 0;
	private int pageNo = 0;
	private List<Course> mCourses = new ArrayList<Course>();
	
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<Course> getCourses() {
		return mCourses;
	}

	public void setCourses(List<Course> mCourses) {
		this.mCourses = mCourses;
	}
	
}
