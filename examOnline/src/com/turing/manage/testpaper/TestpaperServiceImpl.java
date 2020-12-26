package com.turing.manage.testpaper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.turing.dao.Dao;
import com.turing.dao.DaoImpl;
import com.turing.utils.Dates;

/**
 * @desc   试卷管理模块的M层的实现类
 * @author Littiger
 * @time   2020-12-01
 */
public class TestpaperServiceImpl implements ITestpaperService {

	Dao dao=new DaoImpl();

	@Override
	public List<Map<String, Object>> queryTestpaper() throws ClassNotFoundException, SQLException {
		String sql = "SELECT t.*,c.course_name FROM testpaper t LEFT JOIN course c ON t.course_id=c.course_id  order by t.testpaper_state,t.testpaper_time_begin  desc  ";

		return dao.executeQueryForList( sql );
	}

	@Override
	public List<Map<String, Object>> queryClasss() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from classs");
	}

	@Override
	public List<Map<String, Object>> queryCourse() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from course");
	}

	@Override
	public void save(String testpaper_name, String testpaper_radio_num, String testpaper_check_num,
			String testpaper_time_use, String classs_id, String course_id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql="   insert into testpaper values(?,?,?,?,?,?,?,?,?)  ";
		
		
		int[] types = new int[9];
		types[0] = Types.VARCHAR;
		types[1] = Types.VARCHAR;
		types[2] = Types.VARCHAR;
		types[3] = Types.VARCHAR;
		types[4] = Types.INTEGER;
		types[5] = Types.INTEGER;
		types[6] = Types.INTEGER;
		types[7] = Types.VARCHAR;
		types[8] = Types.INTEGER;
		
		
		Object[] values = new Object[9];
		values[0] = UUID.randomUUID().toString();
		values[1] = classs_id;
		values[2] = course_id;
		values[3] = testpaper_name;
		values[4] = testpaper_radio_num;
		values[5] = testpaper_check_num;
		values[6] = testpaper_time_use;
		values[7]=Dates.CurrentYMDHSMTime();
		values[8]=1;//默认是该试卷没用到考试
		dao.executeUpdate(sql, types, values);
	}

	@Override
	public void createTestPaperById(String id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		//0.根据试卷id删除已经产生在明细表中的数据	
		dao.executeUpdate("delete from testpaper_list where testpaper_id=?", new int[]{Types.VARCHAR}, new Object[]{id});
			
		//1.根据试卷的id查询出整套试卷的内容（但是不是具体的题目啊）
			Map<String, Object>  map=dao.executeQueryForMap(" select * from testpaper where testpaper_id=?  ", new int []{Types.VARCHAR}, new Object[]{id});
			//1.1获取单选题的个数
			int testpaper_radio_num=(Integer) map.get("testpaper_radio_num");
			//1.2获取多选题的个数
			int testpaper_check_num=(Integer) map.get("testpaper_check_num");
			//1.3获取课程的id:course_id
			String course_id = (String)map.get("course_id");


		    //2.根据课程的id查询出指定数量的单选题
			String sql="  select * from subject where subject_type='单选题' and course_id=?  ORDER BY RAND() limit 0, ?  ";
			List<Map<String, Object>> list_radio=dao.executeQueryForList(sql, new int []{Types.VARCHAR,Types.VARCHAR}, new Object[]{course_id,testpaper_radio_num});
			//2.1将查询获取到的list_radio(单选题)循环生成且插入到试卷明细表中
			for(int i =0;i<list_radio.size();i++)
			{
				String  sql2="  insert into  testpaper_list values(?,?,?) ";
				int [] types=new int [3];
				types[0]=Types.VARCHAR;
				types[1]=Types.VARCHAR;
				types[2]=Types.VARCHAR;
				
				Object []values=new Object[3];
				values[0]=UUID.randomUUID().toString();
				values[1]=id;//就是将传递过来的试卷的id赋值给列testpaper_id
				values[2]=list_radio.get(i).get("subject_id");
				
				dao.executeUpdate(sql2, types, values);
			}
			
			//3.根据课程的id查询出指定数量的多选题：并实现循环插入到试卷明细表中
			String sql3=" select * from subject where  subject_type='多选题'  and course_id=? order by RAND() limit 0,? ";
			List<Map<String, Object>> list_check = dao.executeQueryForList(sql3, new  int []{Types.VARCHAR,Types.VARCHAR}, new Object[]{course_id,testpaper_check_num});
			
			for (int i = 0; i <list_check.size(); i++)
			{
				String sql4=" insert  into testpaper_list values(?,?,?) ";
				int [] types=new int [3];
				types[0]=Types.VARCHAR;
				types[1]=Types.VARCHAR;
				types[2]=Types.VARCHAR;
				Object [] values=new Object[3];
				values[0]=UUID.randomUUID().toString();
				values[1]=id;
				values[2]=list_check.get(i).get("subject_id");
				dao.executeUpdate(sql4, types, values);
			}
			
			//4.更新试卷的状态为2，意思为已经 生成好题目的试卷
			String  sql5="    update testpaper set  testpaper_state='2' where testpaper_id=?  ";
			dao.executeUpdate(sql5, new int []{Types.VARCHAR}, new Object[]{id});
					
	}

	@Override
	public Map<String, Object> queryTestPaperById(String id) throws ClassNotFoundException, SQLException {
		  String  sql="   select * from testpaper where testpaper_id=?  ";
			
			return dao.executeQueryForMap(sql, new int []{Types.VARCHAR}, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> queryTestPaperRadioInfo(String id) throws ClassNotFoundException, SQLException {
		 String sql="SELECT  s.* FROM SUBJECT s WHERE subject_id   IN  (SELECT subject_id FROM testpaper_list WHERE testpaper_id=?)  AND subject_type='单选题'";
			
			return dao.executeQueryForList(sql, new int []{Types.VARCHAR}, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> queryTestPaperCheckInfo(String id) throws ClassNotFoundException, SQLException {
		String sql="SELECT  s.* FROM SUBJECT s WHERE subject_id   IN  (SELECT subject_id FROM testpaper_list WHERE testpaper_id=?)  AND subject_type='多选题'";
		
		return dao.executeQueryForList(sql, new int []{Types.VARCHAR}, new Object[]{id});
	}

	@Override
	public void updateByCanTest(String id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao.executeUpdate("  update testpaper set testpaper_state=3 where testpaper_id=?  ", new int[]{Types.VARCHAR}, new Object[]{id});		
	}

	@Override
	public void updateByFinishTest(String id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao.executeUpdate("  update testpaper set testpaper_state=4 where testpaper_id=?  ", new int[]{Types.VARCHAR}, new Object[]{id});
		
	}

	@Override
	public void delete(String[] delIdArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		for (String str : delIdArray) {
			
			String sql01 = "delete from testpaper_list where testpaper_id=?";
			String sql02 = "delete from testpaper where testpaper_id=?";
			
			
			dao.executeUpdate(sql01, new int[]{Types.VARCHAR}, new Object[]{str});
			dao.executeUpdate(sql02, new int[]{Types.VARCHAR}, new Object[]{str});
		}
		
	}

	@Override
	public Map<String, Object> queryOne(String testpaper_id) throws ClassNotFoundException, SQLException {
		String sql = "select * from testpaper where testpaper_id = ?";
		int[] types = new int[]{Types.VARCHAR};
		Object[] values = new Object[]{testpaper_id};
		return dao.executeQueryForMap(sql, types, values);
	}

	@Override
	public void edit(String testpaper_id, String testpaper_name, String testpaper_radio_num, String testpaper_check_num,
			String testpaper_time_use, String course_id, String classs_id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "update testpaper set testpaper_name = ?,testpaper_radio_num=?,testpaper_check_num=?,testpaper_time_use=?,course_id=?,classs_id=? where testpaper_id='"+testpaper_id+"'";
		int[] types = new int[]{Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		Object[] values = new Object[]{testpaper_name, testpaper_radio_num, testpaper_check_num,testpaper_time_use, course_id, classs_id};
		dao.executeUpdate(sql, types, values);
	}

	


}
