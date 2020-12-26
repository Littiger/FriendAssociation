package com.turing.manage.examinee;

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
 * @desc   班级模块的M层实现类
 * @author Littiger
 * @time   2019年12月2日 下午5:48:08
 */
public class ExamineeServiceImpl implements IExamineeService {
Dao  dao=new DaoImpl();
	@Override
	public List<Map<String, Object>> queryExaminee(String classs_id,String key) throws ClassNotFoundException, SQLException {
		
		if (classs_id==null) 
		{
		 classs_id="";	
		}
		
		if (key==null) 
		{
			key="";	
		}
		
		
		String sql=" select * from examinee where classs_id like  ?  and examinee_name like ? ";
		
		int [] types=new int [2];
		types[0]=Types.VARCHAR;
		types[1]=Types.VARCHAR;
		
		Object []values=new Object[2];
		values[0]="%"+classs_id+"%";
		values[1]="%"+key.trim()+"%";
		
		return dao.executeQueryForList(sql, types, values);
		
	}
	@Override
	public void save(String classs_name) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		String sql="insert into classs values(?,?,?)";
		
		 int []types=new int[3];
		 types[0]=Types.VARCHAR;
		 types[1]=Types.VARCHAR;
		 types[2]=Types.VARCHAR;
		 
		 Object []values=new Object[3];
		 values[0]=UUID.randomUUID().toString();
		 values[1]=classs_name;
		 values[2]=Dates.CurrentYMDHSMTime();
		dao.executeUpdate(sql, types, values);
	}
	@Override
	public Map<String, Object> queryClasssById(String classs_id) throws ClassNotFoundException, SQLException {
		
		return dao.executeQueryForMap(" select * from classs where classs_id=? ", new int []{Types.VARCHAR}, new Object[]{classs_id});
	}
	@Override
	public void update(String classs_id, String classs_name) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
      dao.executeUpdate("update classs set classs_name=? where classs_id=?", new int []{Types.VARCHAR,Types.VARCHAR},new Object[]{classs_name,classs_id});		
	}
	
	@Override
	public void deleteByIdArray(String[] delIdArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		for(int i =0;i<delIdArray.length;i++)
		{
	    //1.先根据准考证号在成绩表（grade）中删除该准考证号对应的成绩信息
			String sql1=" delete from grade where examinee_id=? ";

		//2.根据准考证号在考生信息表中删除考生的信息
			String sql2="  delete from examinee where examinee_id=?  ";
			
			dao.executeUpdate(sql1, new int []{Types.VARCHAR}, new Object[]{delIdArray[i]});
			dao.executeUpdate(sql2, new int []{Types.VARCHAR}, new Object[]{delIdArray[i]});
		}
		
		
		
		
	}
	@Override
	public List<Map<String, Object>> queryClasss() throws ClassNotFoundException, SQLException {
		String sql="  select * from classs  ";
		return dao.executeQueryForList(sql);
	}
	
	
	@Override
	public void saveByImportExcel(List<String> columnValuesList) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		List<Map<String, Object>> list_examinee_ids = dao.executeQueryForList("select * from examinee where examinee_id=?",new int[]{Types.VARCHAR},new Object []{columnValuesList.get(0)});
		if (list_examinee_ids.size()<=0) 
		{
			String  sql="  insert into examinee values(?,?,?,?,?,?,?,?,?,?)  ";
			int [] types=new int[10];
			Object []values=new Object[10];
			types[0]=Types.VARCHAR;
			types[1]=Types.VARCHAR;
			types[2]=Types.VARCHAR;
			types[3]=Types.VARCHAR;
			types[4]=Types.VARCHAR;
			types[5]=Types.VARCHAR;
			types[6]=Types.VARCHAR;
			types[7]=Types.VARCHAR;
			types[8]=Types.VARCHAR;
			types[9]=Types.VARCHAR;
			
			values[0]=columnValuesList.get(0);
			values[1]=columnValuesList.get(1);
			values[2]=columnValuesList.get(2);
			values[3]=columnValuesList.get(3);
			values[4]=columnValuesList.get(4);
			values[5]=columnValuesList.get(5);
			values[6]=columnValuesList.get(6);
			values[7]=columnValuesList.get(7);
			values[8]=columnValuesList.get(8);
			values[9]=columnValuesList.get(9);

			dao.executeUpdate(sql, types, values);
	}
	}

}
