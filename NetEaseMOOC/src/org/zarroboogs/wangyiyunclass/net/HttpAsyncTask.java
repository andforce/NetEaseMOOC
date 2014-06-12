package org.zarroboogs.wangyiyunclass.net;

import java.util.List;

import org.zarroboogs.wangyiyunclass.javabean.CategoryTree;
import org.zarroboogs.wangyiyunclass.javabean.Cateory;
import org.zarroboogs.wangyiyunclass.javabean.Course;
import org.zarroboogs.wangyiyunclass.javabean.Lession;

import android.os.AsyncTask;

public class HttpAsyncTask extends AsyncTask<String, Integer, List<Course>> {

	public static interface OnLogInSuccess{
		public void onLogInSuccess(List<Course> categoryTrees);
	}
	
	private OnLogInSuccess mOnLogInSuccess;
	public HttpAsyncTask(OnLogInSuccess onLogInSuccess) {
		// TODO Auto-generated constructor stub
		mOnLogInSuccess = onLogInSuccess;
	}
	
	@Override
	protected List<Course> doInBackground(String... params) {
		// TODO Auto-generated method stub
		BroserContent mBroserContent = new BroserContent();

		HttpPostHelper mPostHelper = new HttpPostHelper();
		
		String userName = "86118@163.com";
		String passWord = "368c9b1f97fa706b31d0020d4e8cf888";
		String logInUrl = "http://reg.163.com/logins.jsp";
		
		boolean isSuccess = mPostHelper.loginNetEase(mBroserContent, logInUrl, userName, passWord);
		System.out.println("LogIn Success! ? : " + isSuccess);
		
		List<CategoryTree> categoryTree = mPostHelper.getCateoryTree(mBroserContent);
		
		
		Cateory mCateory = mPostHelper.getCateory(mBroserContent, 31001, 1, 24);
		
		List<Course> mCourses = mCateory.getCourses();
		
//===========================		
		Course haveLessionCourse = mPostHelper.getCourseLessoins(mBroserContent, mCourses.get(0));
		List<Lession> lessionsForCourse = haveLessionCourse.getLessions();
		
		for (Lession lession : lessionsForCourse) {
			System.out.println("[ID] " + lession.getId() + "[Name]" + lession.getLessonName() + "   [URL] " + lession.getVideoUrl());
		}
		
		Lession mLession = mPostHelper.getLessionVides(mBroserContent, lessionsForCourse.get(2));
		System.out.println("============================");
		System.out.println("[ID] " + mLession.getId() + "[Name]" + mLession.getLessonName() + "   [URL] " + mLession.getVideoUrl());
		
		String toaken = mPostHelper.getVideoAuthorityToken4Mobile(mBroserContent, mLession.getVideoUrl());
		
		System.out.println(mLession.getVideoUrl() + "?" + toaken);
		// =====================
		return mCourses;
	}
	
	@Override
	protected void onPostExecute(List<Course> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		this.mOnLogInSuccess.onLogInSuccess(result);
	}

}
