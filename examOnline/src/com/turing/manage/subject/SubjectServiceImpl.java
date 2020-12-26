package com.turing.manage.subject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.turing.dao.Dao;
import com.turing.dao.DaoImpl;

public class SubjectServiceImpl implements ISubjectService {

	Dao dao = new DaoImpl();

	@Override
	public List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from subject left join course on subject.course_id=course.course_id");
	}

	@Override
	public Map<String, Object> query(String subject_id) throws ClassNotFoundException, SQLException {
		String sql = "select * from subject left join course on subject.course_id=course.course_id where subject_id=?";
		return dao.executeQueryForMap(sql, new int[]{Types.VARCHAR}, new Object[]{subject_id});
	}

	@Override
	public List<Map<String, Object>> queryCourse() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from course");
	}

	@Override
	public void edit(String lessonId, String subject_name, String subject_type, String subject_A, String subject_B,
			String subject_C, String subject_D, String[] answerArr, String subject_remark,String subject_id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String subject_answer = "";
		for (String str : answerArr) {
			System.out.println(str);
			subject_answer+=(str+",");
			System.out.println(subject_answer);
		}
		
		String sql = "update subject set course_id=?,subject_name=?,subject_type=?,subject_A=?,subject_B=?,subject_C=?,subject_D=?,subject_answer=?,subject_remark=? where subject_id=?";
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		Object[] values = new Object[]{lessonId,subject_name,subject_type,subject_A,subject_B,subject_C,subject_D,subject_answer,subject_remark,subject_id};
		
		dao.executeUpdate(sql, types, values);
	}

	@Override
	public void add(String course_id, String subject_name, String subject_type, String subject_A, String subject_B,
			String subject_C, String subject_D, String[] answerArr, String subject_remark) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		String subject_answer = "";
		for (String str : answerArr) {
			System.out.println(str);
			subject_answer+=(str+",");
			System.out.println(subject_answer);
		}
		
		String sql = "insert into subject values('"+UUID.randomUUID().toString()+"',?,?,?,?,?,?,?,?,?)";
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		Object[] values = new Object[]{course_id,subject_name,subject_type,subject_A,subject_B,subject_C,subject_D,subject_answer,subject_remark};
		dao.executeUpdate(sql, types, values);
	}

	@Override
	public List<Map<String, Object>> queryCondition(String condition, String key) throws ClassNotFoundException, SQLException {
		
		String sql = "select * from course,subject where course.course_id=subject.course_id and "+condition+" like ?";
		
		if(condition.equals("%")){
			return dao.executeQueryForList("select * from subject left join course on subject.course_id=course.course_id");
		}
		
		if(key==null){
			key="";
		}
		
		int[] types = new int[]{Types.VARCHAR};
		Object[] values = new Object[]{"%"+key.trim()+"%"};
		
		return dao.executeQueryForList(sql, types, values);
	}

	@Override
	public void delete(String[] delIdArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		for (String str : delIdArray) {
			
			String sql01 = "delete from testpaper_list where subject_id = ?";
			String sql02 = "delete from subject where subject_id = ?";
			
			int[] types = new int[]{Types.VARCHAR};
			Object[] values = new Object[]{str};
			
			dao.executeUpdate(sql01, types, values);
			dao.executeUpdate(sql02, types, values);
		}
		
	}

	
	
}
