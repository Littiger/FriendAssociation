package com.quifeng.dao.monitor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @desc   温度监控模块Dao
 * @author JZH
 * @time   2021年1月29日
 */
public class monitorDao {
	Dao dao = new DaoImpl();
	
	/**
	 * 添加温湿度
	 * @param d
	 * @param h
	 * @param m
	 * @param temps
	 * @param huty
	 * @param meantemps
	 * @param meanhuty
	 * @return
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int addTemp(String d, String h, String m, String temps, String huty) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		System.out.println(Double.parseDouble(temps));
		System.out.println(Double.parseDouble(huty));
		return dao.executeUpdate("insert into temper values(0,?,?,?,?,?,0)",
				new int[]{
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR
				},
				new Object[]{
						d,
						h,
						m,
						temps,
						huty
				});
	}

	
	
	/**
	 * 修改平均温湿度
	 * @param d
	 * @param h
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int upDateMean(String d, String h, String meantemps, String meanhuty) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update hourtemper set meantemps=?,meanhuty=? where day=? and hour=?",
				new int[]{
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR
				}, 
				new Object[]{
						meantemps,
						meanhuty,
						d,
						h
				});
	}


	/**
	 * 添加平均温度
	 * @param d
	 * @param h
	 * @param meantemps
	 * @param meanhuty
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int addMeanTemp(String d, String h, String meantemps, String meanhuty) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("insert into hourtemper values(0,?,?,?,?,0)",
				new int[]{
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR
				},
				new Object[]{
						d,
						h,
						meantemps,
						meanhuty
				});
	}


	/**
	 * 获取实时温湿度
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> getData() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from temper ORDER BY concat(day,hour,minute) desc LIMIT 0,1");
	}


	/**
	 * 获取有数据DAY序号
	 * @param NowTime
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> getDay(String NowTime) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from temper where day <= ? GROUP BY day ORDER BY day desc limit 0,30",
				new int[]{
						Types.VARCHAR
				},
				new Object[]{
						NowTime
				});
	}


	/**
	 * 根据 天 获取平均信息
	 * @param date
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> getStatData(String date) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from hourtemper where day=? ORDER BY hour asc",
				new int[]{
						Types.VARCHAR
				},
				new Object[]{
						date
				});
	}

	
}
