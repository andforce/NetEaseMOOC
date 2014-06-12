package org.zarroboogs.wangyiyunclass.javabean;

import java.util.ArrayList;
import java.util.List;

public class Course {

	private int id = 0;
	//private String courseUrl = ""; 		//��ַ��http://study.163.com/course/introduction/608006.htm#/courseDetail
	private String bigPhotoUrl = "";		//����ͼ����ַ��http://img2.ph.126.net/LWqxzKqzqeugYmwqmGGWzw==/732397889501690867.png_223x124x1x95.png
	private String teacher = "";		//��ʦ
	private String title = "";			//�γ�����
	
	private int lessionCount = 0;	//	���ж��ٿ�ʱ��Ҳ���Խ��ж��ٸ���Ƶ
	
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
