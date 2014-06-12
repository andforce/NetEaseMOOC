package org.zarroboogs.wangyiyunclass.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

	public static String getVideoToaken(String dwr){
		String regex = "key:\"(.*?)\"";
		
		String tmp = matchString(dwr, regex);
		String result = tmp.split("\"")[1];
		return "key=" + result;
	}
	public static List<String> categoryIDs(String dwr){
		String regex = "(id=)\\d+";
		
		List<String> tmp = matcher(dwr, regex);
		List<String> result = new ArrayList<String>();
		for(String id : tmp){
			result.add(id.split("=")[1]);
		}
		return result;
	}
	
	public static List<String> categoryMarks(String dwr){
		String regex = "mark=\\d";
		
		List<String> tmp = matcher(dwr, regex);
		List<String> result = new ArrayList<String>();
		for(String id : tmp){
			result.add(id.split("=")[1]);
		}
		return result;
	}
	
	public static List<String> categoryNames(String dwr){
		String regex = "name=\".*?\"";
		
		List<String> tmp = matcher(dwr, regex);
		List<String> result = new ArrayList<String>();
		for(String id : tmp){
			result.add(id.split("\"")[1]);
		}
		return result;
	}
	
	public static List<String> categoryParentId(String dwr){
		String regex = "parentId=(.*?);";
		
		List<String> tmp = matcher(dwr, regex);
		List<String> result = new ArrayList<String>();
		for(String id : tmp){
			result.add(id.split(";")[0]);
		}
		
		List<String> parents = new ArrayList<String>();
		for (String parent : result) {
			parents.add(parent.split("=")[1]);
		}
		return parents;
	}
	
	
	public static int cateoryTotalCount(String dwr){
		String regex = "(totalCount:)\\d*";
		
		List<String> tmp = matcher(dwr, regex);
		int totalCount = Integer.valueOf(tmp.get(0).split(":")[1]);
		return totalCount;
	}
	
	
	
	
	public static List<String> bigPhotoUrl(String dwr) {
		String regex = "(bigPhotoUrl=\")(http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		List<String> tmp = matcher(dwr, regex);
		List<String> result = new ArrayList<String>();
		
		for(String bpl : tmp){
			result.add(bpl.split("\"")[1]);
		}
		
		return result;
	}
	
	
	public static List<String> courseIDs(String dwr){
		//(id=)\d+
		String regex = "(id=)\\d+";
		
		List<String> tmp = matcher(dwr, regex);
		List<String> result = new ArrayList<String>();
		for(String id : tmp){
			result.add(id.split("=")[1]);
		}
		
		return result;
	}
	
	public static List<String> courseNames(String dwr){
		//(name=)"(.*?)"
		String regex = "(name=)\"(.*?)\"";
		List<String> tmp = matcher(dwr, regex);
		List<String> result = new ArrayList<String>();
		for(String name : tmp){
			result.add(name.split("\"")[1]);
		}
		return result;
	}
	
	public static List<String> lessionDescriptions(String dwr){
		String regex = "(description=)\"(.*?)\"";
		
		//description="六级考试最后30天备考时间安排，建昆老师最高效权威的六级真题做题方法。"
		List<String> descriptions = matcher(dwr, regex);
		
		List<String> result = new ArrayList<String>();
		
		for(String tmp : descriptions){
			String des = tmp.split("\"")[1];
			result.add(des);
		}
		return result;
	}
	
	public static List<String> lessonNames(String dwr){
		// (lessonName=)\"(.*?)\"
		String regex = "(lessonName=)\"(.*?)\"";
		
		List<String> tmp = matcher(dwr, regex);
		List<String> result = new ArrayList<String>();
		for (String lessionName : tmp) {
			result.add(lessionName.split("\"")[1]);
		}
		return result;
	}
	
	public static List<String> lessionIDs(String dwr){
		// (id=)\d+;\w\d+.(learnMark)
		String tmpRegex = "(id=)\\d+;\\w\\d+.(learnMark)";
		List<String> tmpList = matcher(dwr, tmpRegex);
		String tmpStrings = "";
		for(String tmp : tmpList){
			tmpStrings = tmpStrings + tmp;
		}
		
		String regex = "(id=)\\d+";
		List<String> ids = matcher(tmpStrings, regex);
		
		List<String> result = new ArrayList<String>();
		for(String id: ids){
			result.add(id.split("=")[1]);
		}
		return result;
	}
	public static String lessionflvHdUrl(String dwr){
		String regex = "(flvHdUrl:)\"(.*?)\"";
		
		String tmp = matchString(dwr, regex);
		if ("".equals(tmp)) {
			return "";
		}else{
			return tmp.split("\"")[1];
		}
	}
	
	public static String lessionflvSdUrl(String dwr){
		String regex = "(flvSdUrl:)\"(.*?)\"";
		String tmp = matchString(dwr, regex);
		if ("".equals(tmp)) {
			return "";
		}else{
			return tmp.split("\"")[1];
		}
	}
	
	public static String lessionflvShdUrl(String dwr){
		String regex = "(flvShdUrl:)\"(.*?)\"";
		String tmp = matchString(dwr, regex);
		if ("".equals(tmp)) {
			return "";
		}else{
			return tmp.split("\"")[1];
		}
	}
	public static String lessionvideoHDUrl(String dwr){
		String regex = "(videoHDUrl:)\"(.*?)\"";
		String tmp = matchString(dwr, regex);
		if ("".equals(tmp)) {
			return "";
		}else{
			return tmp.split("\"")[1];
		}
	}
	public static String lessionvideoSHDUrl(String dwr){
		String regex = "(videoSHDUrl:)\"(.*?)\"";
		String tmp = matchString(dwr, regex);
		if ("".equals(tmp)) {
			return "";
		}else{
			return tmp.split("\"")[1];
		}
	}
	public static String lessionvideoUrl(String dwr){
		String regex = "(videoUrl:)\"(.*?)\"";
		String tmp = matchString(dwr, regex);
		if ("".equals(tmp)) {
			return "";
		}else{
			return tmp.split("\"")[1];
		}
	}
	public static String lessionvideoImgUrl(String dwr){
		String regex = "(videoImgUrl:)\"(.*?)\"";
		String tmp = matchString(dwr, regex);
		if ("".equals(tmp)) {
			return "";
		}else{
			return tmp.split("\"")[1];
		}
	}
	public static String matchString(String dwr,String regex){
		//
		Pattern mPattern = Pattern.compile(regex);
		Matcher m = mPattern.matcher(dwr);
		if (m.find()) {
			return m.group();
		}else{
			return "";
		}
	}
	
	private static List<String> matcher(String dwr,String regex) {
		Pattern mPattern = Pattern.compile(regex);
		Matcher m = mPattern.matcher(dwr);
		List<String> mList = new ArrayList<String>();
		while (m.find()) {
			mList.add(m.group());
		}
		return mList;
	}
}
