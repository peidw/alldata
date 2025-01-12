package com.platform.spark.dao;

import com.platform.spark.domain.SessionRandomExtract;

/**
 * session随机抽取模块DAO接口
 * @author AllDataDC
 *
 */
public interface ISessionRandomExtractDAO {

	/**
	 * 插入session随机抽取
	 * @param sessionAggrStat 
	 */
	void insert(SessionRandomExtract sessionRandomExtract);
	
}
