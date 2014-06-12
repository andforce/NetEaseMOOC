package org.zarroboogs.wangyiyunclass.utils;

import java.util.ArrayList;
import java.util.List;

import org.zarroboogs.wangyiyunclass.javabean.CategoryTree;
import org.zarroboogs.wangyiyunclass.javabean.Course;
import org.zarroboogs.wangyiyunclass.javabean.Lession;

public class CourseUtils {

	public static List<Course> getCourses(String dwr){
		List<Course> result = new ArrayList<Course>();
		
		List<String> courseBigCoverPics = PatternUtils.bigPhotoUrl(dwr);
		List<String> courseIDs = PatternUtils.courseIDs(dwr);
		List<String> courseNames = PatternUtils.courseNames(dwr);
		
		
		for(int i = 0; i < courseBigCoverPics.size(); i++ ){
			Course course = new Course();
			
			int id = Integer.valueOf(courseIDs.get(i));
			course.setId(id);

			String urlString = courseBigCoverPics.get(i);
			course.setBigPhotoUrl(urlString);
			
			String name = courseNames.get(i);
			course.setTitle(name);
			
			result.add(course);
		}
		
		return result;
	}
	
	public static List<CategoryTree> getCategoryTree(String dwr){
		List<CategoryTree> categoryTrees = new ArrayList<CategoryTree>();
		
		List<String> IDs = PatternUtils.categoryIDs(dwr);
		List<String> marks = PatternUtils.categoryMarks(dwr);
		List<String> parentIds = PatternUtils.categoryParentId(dwr);
		List<String> names = PatternUtils.categoryNames(dwr);
		
		for (int i = 0; i < IDs.size(); i++) {
			CategoryTree item = new CategoryTree();
			item.setId(Integer.valueOf(IDs.get(i)));
			item.setMark(Integer.valueOf(marks.get(i)));
			item.setParentId(Integer.valueOf(parentIds.get(i)));
			item.setName(names.get(i));
			categoryTrees.add(item);
		}
		return categoryTrees;
	}
	
	
	public static List<Lession> getLessions(Course corse,String dwr){
	
		List<Lession> result = new ArrayList<Lession>();
		
		List<String> ids = PatternUtils.lessionIDs(dwr);
		List<String> names = PatternUtils.lessonNames(dwr);
		List<String> descripions = PatternUtils.lessionDescriptions(dwr);
		for (int i = 0; i < ids.size(); i++) {
			Lession lession = new Lession();
			
			lession.setId(Integer.valueOf(ids.get(i)));
			lession.setLessonName(names.get(i));
			lession.setDescription(descripions.get(i));
			lession.setCourseID(corse.getId());
			
			result.add(lession);
		}
		
		return result; 
	}
}
