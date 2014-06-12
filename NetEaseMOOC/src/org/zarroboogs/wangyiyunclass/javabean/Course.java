package org.zarroboogs.wangyiyunclass.javabean;

import java.util.ArrayList;
import java.util.List;

public class Course {

	private int id = 0;
	//private String courseUrl = ""; 		//地址：http://study.163.com/course/introduction/608006.htm#/courseDetail
	private String bigPhotoUrl = "";		//封面图，地址：http://img2.ph.126.net/LWqxzKqzqeugYmwqmGGWzw==/732397889501690867.png_223x124x1x95.png
	private String teacher = "";		//讲师
	private String title = "";			//课程名称
	
	private int lessionCount = 0;	//	共有多少课时，也可以叫有多少个视频
	
	private List<Lession> lessions = new ArrayList<Lession>();
	
	
	
	public List<Lession> getLessions() {
		return lessions;
	}
	public void setLessions(List<Lession> lessions) {
		this.lessions = lessions;
	}
	
	
	
	
	public int getLessionCount() {
		return lessionCount;
	}
	public void setLessionCount(int lessionCount) {
		this.lessionCount = lessionCount;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCourseUrl() {
		return "http://study.163.com/course/introduction/" + getId() + ".htm#/courseDetail";
	}
	
	
	public String getBigPhotoUrl() {
		return bigPhotoUrl;
	}
	public void setBigPhotoUrl(String bigPhotoUrl) {
		this.bigPhotoUrl = bigPhotoUrl;
	}
	
	public String getTeacher() {
		return teacher;
	}
	
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
