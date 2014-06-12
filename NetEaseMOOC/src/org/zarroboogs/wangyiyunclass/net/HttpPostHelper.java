package org.zarroboogs.wangyiyunclass.net;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.zarroboogs.wangyiyunclass.javabean.CategoryTree;
import org.zarroboogs.wangyiyunclass.javabean.Cateory;
import org.zarroboogs.wangyiyunclass.javabean.Course;
import org.zarroboogs.wangyiyunclass.javabean.Lession;
import org.zarroboogs.wangyiyunclass.utils.CookieUtils;
import org.zarroboogs.wangyiyunclass.utils.CourseHeader;
import org.zarroboogs.wangyiyunclass.utils.CourseUrls;
import org.zarroboogs.wangyiyunclass.utils.CourseUtils;
import org.zarroboogs.wangyiyunclass.utils.HTMLDecoder;
import org.zarroboogs.wangyiyunclass.utils.PatternUtils;

public class HttpPostHelper {

	public boolean loginNetEase(BroserContent broserContent, String url, String name,
			String password) {
		HttpClient httpClient = broserContent.getHttpClient();
		Header[] loginHeaders = {
				new BasicHeader("Accept", CourseHeader.Accept),
				new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"),
				new BasicHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,zh-TW;q=0.2"),
				new BasicHeader("Cache-Control", "max-age=0"),
				new BasicHeader("Connection", "keep-alive"),
				new BasicHeader("Content-Type","application/x-www-form-urlencoded"),
				new BasicHeader("Host", "reg.163.com"),
				new BasicHeader("Origin", "http://study.163.com"),
				new BasicHeader("Referer", "http://study.163.com/"),
				new BasicHeader("User-Agent", CourseHeader.User_Agent), };

		List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
		loginParams.add(new BasicNameValuePair("product", "study"));
		loginParams.add(new BasicNameValuePair("url",
				"http://study.163.com?from=study"));
		loginParams.add(new BasicNameValuePair("savelogin", "1"));
		loginParams.add(new BasicNameValuePair("domains", "163.com"));
		loginParams.add(new BasicNameValuePair("type", "0"));
		loginParams.add(new BasicNameValuePair("append", "1"));
		loginParams.add(new BasicNameValuePair("username", name));
		loginParams.add(new BasicNameValuePair("password", password));

		HttpPost logInPost = HttpPostFactory.createHttpPost(url, loginHeaders,loginParams);

		HttpResponse logInResponse = null;
		boolean isSuccess = false;
		try {
			logInResponse = httpClient.execute(logInPost);
			if (logInResponse.getStatusLine().getStatusCode() == 200) {
				isSuccess = true;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSuccess = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}
	
	public Cateory getCateory(BroserContent broserContent,
			int courseCategoryID, int pageNumber, int wantCourseCount){
		
		HttpClient httpClient = broserContent.getHttpClient();
		CookieStore cookieStore = broserContent.getCookieStore();
		
		String allString = getCourseList(httpClient, cookieStore, courseCategoryID, pageNumber, wantCourseCount);
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		System.out.println("00000000000000000000000000000000" + HTMLDecoder.UnicodeToString(allString));
//		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		List<Course> courses = CourseUtils.getCourses(HTMLDecoder.UnicodeToString(allString));
		
		Cateory mCateory = new Cateory();
		mCateory.setCourses(courses);
		
		int totalCount = PatternUtils.cateoryTotalCount(allString);
		mCateory.setTotalCount(totalCount);
		
		mCateory.setPageNo(pageNumber);
		
		return mCateory;
	}
	
	public List<CategoryTree> getCateoryTree(BroserContent broserContent){
		
		HttpClient httpClient = broserContent.getHttpClient(); 
		CookieStore cookieStore = broserContent.getCookieStore();
		String allString = getCategoryTreeString(httpClient, cookieStore);
		
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//System.out.println("00000000000000000000000000000000" + HTMLDecoder.UnicodeToString(allString));
		//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		List<CategoryTree> trees = CourseUtils.getCategoryTree(HTMLDecoder.UnicodeToString(allString));
//		CategoryTree categoryTree = CourseUtils.getCategoryTree(allString);
		
		return trees;
	}
	
	
	public Course getCourseLessoins(BroserContent broserContent,Course course){
		HttpClient httpClient = broserContent.getHttpClient();
		CookieStore cookieStore = broserContent.getCookieStore();
		
		String courseDetail = getCourseDetail(httpClient, cookieStore, course);
		
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("00000000000000000000000000000000" + HTMLDecoder.UnicodeToString(courseDetail));
		//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				
		
		
		List<Lession> lessionsForCourse = CourseUtils.getLessions(course, HTMLDecoder.UnicodeToString(courseDetail));
		course.setLessions(lessionsForCourse);
		return course;
	}
	
	
	public Lession getLessionVides(BroserContent broserContent,Lession lession){
		HttpClient httpClient = broserContent.getHttpClient();
		CookieStore cookieStore = broserContent.getCookieStore();
		String videoString = getVideoInfo(httpClient, cookieStore,lession.getCourseID(),lession.getId());
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		System.out.println("00000000000000000000000000000000" + videoString);
//		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		lession.setFlvHdUrl(PatternUtils.lessionflvHdUrl(videoString));
		lession.setFlvSdUrl(PatternUtils.lessionflvSdUrl(videoString));
		lession.setFlvShdUrl(PatternUtils.lessionflvShdUrl(videoString));
		lession.setVideoHDUrl(PatternUtils.lessionvideoHDUrl(videoString));
		lession.setVideoSHDUrl(PatternUtils.lessionvideoSHDUrl(videoString));
		
		lession.setVideoUrl(PatternUtils.lessionvideoUrl(videoString));
		lession.setVideoImgUrl(PatternUtils.lessionvideoImgUrl(videoString));
		
		return lession;
		
	}
	
	public String getVideoAuthorityToken4Mobile(BroserContent broserContent,String videoUrl){
		HttpClient httpClient = broserContent.getHttpClient();
		CookieStore cookieStore = broserContent.getCookieStore();
		
		String videoToaken = getVideoAuthorityToken4MobileResponse(httpClient, cookieStore, videoUrl);
		return PatternUtils.getVideoToaken(videoToaken);
		
	}
	
	private String getVideoAuthorityToken4MobileResponse(HttpClient httpClient, CookieStore cookieStore,String videoUrl){
		
		String httpSessionId = CookieUtils.getHttpSessionId(cookieStore);
		
		//============获取所有的实用课程
		Header[] allCourseHeaders = {
				new BasicHeader("Accept", "*/*"),
				new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"),
				new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,zh-TW;q=0.2"),
				new BasicHeader("Cache-Control", "max-age=0"),
				new BasicHeader("Connection", "keep-alive"),
				new BasicHeader("Content-Type", "text/plain"),
				new BasicHeader("Host", "study.163.com"),
				new BasicHeader("Origin", "http://study.163.com"),
				new BasicHeader("Referer", "http://study.163.com/find.htm"),
				new BasicHeader("User-Agent",CourseHeader.User_Agent),
		};
		
		List<NameValuePair> allCourceParam = new ArrayList<NameValuePair>();
		allCourceParam.add(new BasicNameValuePair("callCount", "1"));
		allCourceParam.add(new BasicNameValuePair("scriptSessionId", "${scriptSessionId}190"));
		allCourceParam.add(new BasicNameValuePair("httpSessionId", httpSessionId));
		
		allCourceParam.add(new BasicNameValuePair("c0-scriptName", "VideoBean"));
		allCourceParam.add(new BasicNameValuePair("c0-methodName", "getVideoAuthorityToken4MobileBrowser"));
		
		allCourceParam.add(new BasicNameValuePair("c0-id","0"));
		allCourceParam.add(new BasicNameValuePair("c0-param0", "string:" + URLEncoder.encode(videoUrl)));
		allCourceParam.add(new BasicNameValuePair("batchId", "757817"));
		
		HttpPost allCoursePost = HttpPostFactory.createHttpPost(CourseUrls.VideoToaken, allCourseHeaders, allCourceParam);
		HttpResponse postResult = null;
		try {
			postResult = httpClient.execute(allCoursePost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseUtils.getResponseLines(false, postResult);
	}
	
	
	
	/**
	 * @param httpClient
	 * @param cookieStore
	 * @param courseCategoryID 大分类的ID，比如：IT与互联网分类的ID是31001
	 * @param pageNumber 页码
	 * @param wantCourseCount 想要获取的课程的数量，默认是24
	 * @return
	 */
	private String getCourseList(HttpClient httpClient, CookieStore cookieStore, 
			int courseCategoryID, int pageNumber, int wantCourseCount){
		
		String httpSessionId = CookieUtils.getHttpSessionId(cookieStore);
		
		//============获取所有的实用课程
		Header[] allCourseHeaders = {
				new BasicHeader("Accept", "*/*"),
				new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"),
				new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,zh-TW;q=0.2"),
				new BasicHeader("Cache-Control", "max-age=0"),
				new BasicHeader("Connection", "keep-alive"),
				new BasicHeader("Content-Type", "text/plain"),
				new BasicHeader("Host", "study.163.com"),
				new BasicHeader("Origin", "http://study.163.com"),
				new BasicHeader("Referer", "http://study.163.com/find.htm"),
				new BasicHeader("User-Agent",CourseHeader.User_Agent),
		};
		
		List<NameValuePair> allCourceParam = new ArrayList<NameValuePair>();
		allCourceParam.add(new BasicNameValuePair("callCount", "1"));
		allCourceParam.add(new BasicNameValuePair("scriptSessionId", "${scriptSessionId}190"));
		allCourceParam.add(new BasicNameValuePair("httpSessionId", httpSessionId));
		allCourceParam.add(new BasicNameValuePair("c0-scriptName", "CourseBean"));
		allCourceParam.add(new BasicNameValuePair("c0-methodName", "getCourseList"));
		allCourceParam.add(new BasicNameValuePair("c0-id","0"));
		allCourceParam.add(new BasicNameValuePair("c0-param0", "number:" + pageNumber));
		allCourceParam.add(new BasicNameValuePair("c0-param1", "number:" + wantCourseCount));
		allCourceParam.add(new BasicNameValuePair("c0-param2", "number:-1"));
		allCourceParam.add(new BasicNameValuePair("c0-param3", "string:" + courseCategoryID));
		allCourceParam.add(new BasicNameValuePair("c0-param4", "number:-1"));
		allCourceParam.add(new BasicNameValuePair("c0-param5", "number:0"));
		allCourceParam.add(new BasicNameValuePair("batchId", "757817"));
		
		HttpPost allCoursePost = HttpPostFactory.createHttpPost(CourseUrls.GetCourseList, allCourseHeaders, allCourceParam);
		HttpResponse postResult = null;
		try {
			postResult = httpClient.execute(allCoursePost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseUtils.getResponseLines(false, postResult);
	}
	
	
	private String getCategoryTreeString(HttpClient httpClient, CookieStore cookieStore){
		
		String httpSessionId = CookieUtils.getHttpSessionId(cookieStore);
		
		//============获取所有的实用课程
		Header[] allCourseHeaders = {
				new BasicHeader("Accept", "*/*"),
				new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"),
				new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,zh-TW;q=0.2"),
				new BasicHeader("Cache-Control", "max-age=0"),
				new BasicHeader("Connection", "keep-alive"),
				new BasicHeader("Content-Type", "text/plain"),
				new BasicHeader("Host", "study.163.com"),
				new BasicHeader("Origin", "http://study.163.com"),
				new BasicHeader("Referer", "http://study.163.com/find.htm"),
				new BasicHeader("User-Agent",CourseHeader.User_Agent),
		};
		
		List<NameValuePair> allCourceParam = new ArrayList<NameValuePair>();
		allCourceParam.add(new BasicNameValuePair("callCount", "1"));
		allCourceParam.add(new BasicNameValuePair("scriptSessionId", "${scriptSessionId}190"));
		allCourceParam.add(new BasicNameValuePair("httpSessionId", httpSessionId));
		
		allCourceParam.add(new BasicNameValuePair("c0-scriptName", "CategoryBean"));
		allCourceParam.add(new BasicNameValuePair("c0-methodName", "getFilterRecommendCategoryTree"));
		
		allCourceParam.add(new BasicNameValuePair("c0-id","0"));
		allCourceParam.add(new BasicNameValuePair("c0-param0", "number:1"));
		allCourceParam.add(new BasicNameValuePair("batchId", "757817"));
		
		HttpPost allCoursePost = HttpPostFactory.createHttpPost(CourseUrls.CategroyTree, allCourseHeaders, allCourceParam);
		HttpResponse postResult = null;
		try {
			postResult = httpClient.execute(allCoursePost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseUtils.getResponseLines(false, postResult);
	}
	
	
	public String getCourseDetail(HttpClient httpClient, CookieStore cookieStore, Course course){
		String httpSessionId = CookieUtils.getHttpSessionId(cookieStore);
		
		Header[] headers = {
				new BasicHeader("Accept", "*/*"),
				new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"),
				new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,zh-TW;q=0.2"),
				new BasicHeader("Cache-Control", "max-age=0"),
				new BasicHeader("Connection", "keep-alive"),
				new BasicHeader("Content-Type", "text/plain"),
				new BasicHeader("Host", "study.163.com"),
				new BasicHeader("Origin", "http://study.163.com"),
				new BasicHeader("Referer", "http://study.163.com/find.htm"),
				new BasicHeader("User-Agent",CourseHeader.User_Agent),
		};
		
		
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("callCount", "1"));
		postParams.add(new BasicNameValuePair("scriptSessionId", "${scriptSessionId}190"));
		postParams.add(new BasicNameValuePair("httpSessionId", httpSessionId));
		postParams.add(new BasicNameValuePair("c0-scriptName", "PlanNewBean"));
		postParams.add(new BasicNameValuePair("c0-methodName", "getPlanCourseDetail"));
		postParams.add(new BasicNameValuePair("c0-id","0"));
		postParams.add(new BasicNameValuePair("c0-param0", "string:" + course.getId()));
		postParams.add(new BasicNameValuePair("c0-param1", "string:0"));
		postParams.add(new BasicNameValuePair("c0-param2", "null:null"));
		postParams.add(new BasicNameValuePair("batchId", "905319"));
		
		
		HttpPost postrequest = HttpPostFactory.createHttpPost(CourseUrls.CourseDetail, headers, postParams);
		HttpResponse mHttpResponse = null;
		try {
			mHttpResponse = httpClient.execute(postrequest);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ResponseUtils.getResponseLines(false, mHttpResponse);
	}

	
	
	public String getVideoInfo(HttpClient httpClient, CookieStore cookieStore, int courseID, int lessionID){
		
		String httpSessionId = CookieUtils.getHttpSessionId(cookieStore);
		
		// getVideoInfo
		Header[] getVideoInfoHeaders = {
				new BasicHeader("Accept", "*/*"),
				new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"),
				new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,zh-TW;q=0.2"),
				new BasicHeader("Cache-Control", "max-age=0"),
				new BasicHeader("Connection", "keep-alive"),
				new BasicHeader("Content-Type", "text/plain"),
				new BasicHeader("Host", "study.163.com"),
				new BasicHeader("Origin", "http://study.163.com"),
				new BasicHeader("Referer", "http://study.163.com/find.htm"),
				new BasicHeader("User-Agent",CourseHeader.User_Agent),
		};
		List<NameValuePair> getVideoInfoParams = new ArrayList<NameValuePair>();
		getVideoInfoParams.add(new BasicNameValuePair("callCount", "1"));
		getVideoInfoParams.add(new BasicNameValuePair("scriptSessionId", "${scriptSessionId}190"));
		getVideoInfoParams.add(new BasicNameValuePair("httpSessionId", httpSessionId));
		getVideoInfoParams.add(new BasicNameValuePair("c0-scriptName", "LessonLearnBean"));
		getVideoInfoParams.add(new BasicNameValuePair("c0-methodName", "getVideoLearnInfo"));
		getVideoInfoParams.add(new BasicNameValuePair("c0-id","0"));
		getVideoInfoParams.add(new BasicNameValuePair("c0-param0", "string:" + lessionID));
		getVideoInfoParams.add(new BasicNameValuePair("c0-param1", "string:" + courseID));
		getVideoInfoParams.add(new BasicNameValuePair("batchId", "905319"));
		
		
		HttpPost getVideoInfoHttpPost = HttpPostFactory.createHttpPost(CourseUrls.GetVideoInfo, getVideoInfoHeaders, getVideoInfoParams);
		HttpResponse mHttpResponse = null;
		try {
			mHttpResponse = httpClient.execute(getVideoInfoHttpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ResponseUtils.getResponseLines(false, mHttpResponse);
	}
	
	
	
}
