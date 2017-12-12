package com.xy.cms.common;

import java.util.Vector;
import java.util.Hashtable;
import java.sql.*;
import javax.naming.Context;
import org.apache.log4j.Logger;
import java.util.Properties;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
public class PersistenceManager extends java.lang.Object {
	private static Logger logger = Logger.getLogger(PersistenceManager.class.getName());

	/**
	 * 此处插入方法说明�?创建日期�?2001-7-19 9:26:08)
	 */
	public PersistenceManager() {

		super();
		if (debugFlag) {

			// System.out.println(""); //$NON-NLS-1$
		}

	}

	// Hashtable of classes and their Persistence

	private static Hashtable pms = new Hashtable();

	public static java.util.Hashtable getPms() {
		return pms;
	}

	/**
	 * 此处插入方法说明�?创建日期�?2001-7-20 20:01:36) 方法说明�?<方法实现的业务�?�?参数�?<方法的参�?返回�?<返回�?
	 */
	public static void setPms(java.util.Hashtable newPms) {
		pms = newPms;
	}

	/**
	 * 此处插入方法说明�?创建日期�?2001-7-20 20:01:36) 方法说明�?<方法实现的业务�?�?参数�?<方法的参�?返回�?<返回�?
	 */
	public static void unRegister(Class key) {
		pms.remove(key);
	}

	public java.sql.Connection connection = null; // DB conntection

	private static javax.sql.DataSource dataSource = null; // DB datasource

	private static java.lang.String dataSourceName; // name of DB datasource

	private static java.lang.String dbPassword = null; // password
														// //$NON-NLS-1$

	private static java.lang.String dbUser = null; // user name //$NON-NLS-1$

	public java.sql.ResultSet resultSet = null;

	public java.sql.Statement statement = null;

	private static int connectionCount = 0;

	private static boolean debugFlag = false;

	private static boolean isFirstRun = true;

	private static String PWDFIELPATH = "datasource.properties"; //$NON-NLS-1$

	// public static String PWDFIELPATH = null;

	/*
	 * 
	 * 功能：从数据源中取得数据库连�?输入：要操作的数据对象，sql语句属�?集合 输出：处理结果串
	 * 
	 */
	public java.sql.Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {

				if (dbUser != null && dbPassword != null)
					connection = dataSource.getConnection(getDbUser(), getDbPassword());
				else
					connection = dataSource.getConnection();
				/*
				 * connection = dataSource.getConnection( getDbUser(),
				 * getDbPassword());
				 */
				if (debugFlag) {
					connectionCount++;
					logger.debug("connectionCount=" + connectionCount + "\t"); //$NON-NLS-1$
				}
				// connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return connection;
	}

	public javax.sql.DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 此处插入方法说明�?创建日期�?2001-7-20 20:01:36) 方法说明�?<方法实现的业务�?�?参数�?<方法的参�?返回�?<返回�?
	 */
	public java.lang.String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * 此处插入方法说明�?创建日期�?2001-7-20 20:01:36) 方法说明�?<方法实现的业务�?�?参数�?<方法的参�?返回�?<返回�?
	 */
	public java.lang.String getDbPassword() {
		return dbPassword;
	}

	/**
	 * 此处插入方法说明�?创建日期�?2001-7-20 20:01:36) 方法说明�?<方法实现的业务�?�?参数�?<方法的参�?返回�?<返回�?
	 */
	public static java.lang.String getDbUser() {
		return dbUser;
	}

	/**
	 * 
	 * 功能：设置数据源�?初始化数据源
	 * 
	 */

	public static void init() {
		if (PWDFIELPATH == null)
			return;
		Properties config = new Properties();
		Hashtable param = new Hashtable();
		// firstRun();
		try {
			// java.io.File file = new java.io.File(PWDFIELPATH);
			// FileInputStream inputStream = new FileInputStream(file);
			InputStream inputStream = PersistenceManager.class.getClassLoader().getResourceAsStream(PWDFIELPATH);
			config.load(inputStream);
			inputStream.close();
			if (config.get("INITIAL_CONTEXT_FACTORY") != null)
				param.put(Context.INITIAL_CONTEXT_FACTORY, config.get("INITIAL_CONTEXT_FACTORY"));
			if (config.getProperty("PROVIDER_URL") != null)
				param.put(Context.PROVIDER_URL, config.getProperty("PROVIDER_URL"));
			if (config.get("DATA_SOURCE_NAME") != null)
				dataSourceName = (String) config.get("DATA_SOURCE_NAME");
			if (config.get("USER") != null)
				dbUser = (String) config.get("USER");
			if (config.get("PASSWORD") != null)
				dbPassword = (String) config.get("PASSWORD");
			if (config.get("DEBUG") != null) {
				String debug = (String) config.get("DEBUG");
				if (debug.equalsIgnoreCase("TRUE"))
					debugFlag = true;
			}
			// System.out.println("DataSourceName = " + dataSourceName);
			javax.naming.Context ctx = null;
			if (!param.isEmpty())
				ctx = new javax.naming.InitialContext(param);
			else
				ctx = new javax.naming.InitialContext();
			dataSource = (javax.sql.DataSource) ctx.lookup(dataSourceName);

		} catch (Throwable theException) {
			theException.printStackTrace();
		}
	}

	/*
	 * 
	 * 功能：设置数据源
	 * 
	 */
	public static void setDataSource(javax.sql.DataSource newDataSource) {
		// PersistenceManager permanager = new PersistenceManager();
		// permanager.dataSource = newDataSource;
		PersistenceManager.dataSource = newDataSource;
	}

	public static void setDataSourceName(java.lang.String newDataSourceName) {
		// PersistenceManager permanager = new PersistenceManager();
		// permanager.dataSourceName = newDataSourceName;
		PersistenceManager.dataSourceName = newDataSourceName;
	}

	public static void setDbPassword(java.lang.String newDbPassword) {
		dbPassword = newDbPassword;
	}

	public static void setDbUser(java.lang.String newDbUser) {
		dbUser = newDbUser;
	}

	public java.sql.ResultSet getResultSet() {
		return resultSet;
	}

	public java.sql.Statement getStatement() {
		return statement;
	}

	public void setConnection(java.sql.Connection newConnection) {
		connection = newConnection;
	}

	public void setResultSet(java.sql.ResultSet newResultSet) {
		resultSet = newResultSet;
	}

	public void setStatement(java.sql.Statement newStatement) {
		statement = newStatement;
	}

	/*
	 * 
	 * 功能：关闭数据库资源
	 * 
	 */
	public void close() {

		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
				connection = null;
				if (debugFlag) {
					connectionCount--;
					System.out.print("connectionCount=" + connectionCount + "\t"); //$NON-NLS-1$
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * 功能：执行存储过�?
	 * 
	 */
	public String executeProcedure(java.lang.String sql) {
		int result;
		String err = ""; //$NON-NLS-1$

		try {
			java.sql.Connection connection = getConnection();
			if (statement == null)
				statement = connection.createStatement();
			result = statement.executeUpdate(sql);

		} catch (SQLException e) {
			rollback();
			err = err + e.toString();
			e.printStackTrace();
			logger.debug("Error SQL = " + sql);
		}
		return err;
	}

	/*
	 * 
	 * 功能：执行sql查询语句
	 * 
	 */
	public ResultSet executeQuery(String sql) {

		String err = ""; //$NON-NLS-1$
		try {
			java.sql.Connection connection = getConnection();
			if (statement == null)
				statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

		} catch (SQLException e) {

			err = err + e.toString();
			e.printStackTrace();
			logger.debug("Error SQL = " + sql);
		}
		return resultSet;
	}

	/*
	 * 
	 * 功能：将数据访问类和实体对象注册映射，放入哈希表�?
	 * 
	 */

	public static void register(Class key, Class type, String hashtbl) {
		if (hashtbl.toLowerCase().equals("pms")) { //$NON-NLS-1$
			pms.put(key, type);
		}

	}

	public static void setInstallpath(java.lang.String newInstallpath) {

	}

	public void rollback() {
		// String err = null;
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public PreparedStatement pstmt = null;

	public String executePStmt(PreparedStatement pstmt) {

		String err = ""; //$NON-NLS-1$

		try {

			// result = statement.executeUpdate(sql);
			pstmt.execute();

		} catch (SQLException e) {
			err = err + e.toString();
		}
		return err;
	}

	/**
	 * 此处插入方法说明�?创建日期�?2001-9-9 10:43:42)
	 * 
	 * @return java.sql.PreparedStatement
	 */
	public java.sql.PreparedStatement getPstmt() {
		/*
		 * java.sql.Connection connection = getConnection();
		 * 
		 * statement = connection.prepareStatement();
		 */

		return pstmt;
	}

	/**
	 * 此处插入方法说明�?创建日期�?2001-9-9 10:43:42)
	 * 
	 * @param newPstmt
	 *            java.sql.PreparedStatement
	 */
	public void setPstmt(java.sql.PreparedStatement newPstmt) {
		pstmt = newPstmt;
	}

	/*
	 * 
	 * 功能：执行sql批处�?
	 * 
	 */
	public String exectueBatch(String[] batchSql) {
		int result;
		String err = null;
		// String sql = null;
		boolean autoflag;

		try {
			java.sql.Connection connection = getConnection();
			autoflag = connection.getAutoCommit();
			DatabaseMetaData dbMetaData = connection.getMetaData();
			if (dbMetaData.supportsBatchUpdates()) {
				connection.setAutoCommit(false);
				statement = connection.createStatement();

				for (int i = 0; i < batchSql.length; i++) {
					if (batchSql[i] != null) {
						statement.addBatch(batchSql[i]);
						// System.out.println(batchSql[i]);
					}
				}
				int[] nResults = statement.executeBatch();
				connection.commit();
				connection.setAutoCommit(autoflag);
			}
		} catch (SQLException e) {
			rollback();
			err = e.toString();
			for (int i = 0; i < batchSql.length; i++)
				logger.debug("Error SQL[" + i + "] = " + batchSql[i]);

		}

		return err;

	}


	public Vector transactionVct = new Vector();

	/*
	 * 
	 * 功能：事务提交方�?
	 * 
	 */
	public String commit() {

		int vctSize = transactionVct.size();
		String[] trasactionArray = new String[vctSize];
		for (int i = 0; i < vctSize; i++) {
			if (transactionVct.elementAt(i) != null) {
				trasactionArray[i] = (String) transactionVct.elementAt(i);
			} else {
				continue;
			}
		}
		String err = null;
		err = exectueBatch(trasactionArray);

		// close the connection to database

		close();
		// clear the Vector
		transactionVct.clear();
		return err;
	}

	public Vector delSQLfromTransaction(int position) {
		transactionVct.removeElementAt(position);

		return transactionVct;
	}

	public Vector delSQLfromTransaction(String strsql) {
		transactionVct.removeElement(strsql);

		return transactionVct;
	}

	public String executeDelete(java.lang.String sql, boolean autocommit) {
		// PersistenceManager permanager = new PersistenceManager();
		// Vector vct = new Vector();
		String result = null;
		result = executeProcedure(sql, autocommit);

		return result;

	}

	public String executeInsert(java.lang.String sql, boolean autocommit) {
		// PersistenceManager permanager = new PersistenceManager();
		String result = null;
		result = executeProcedure(sql, autocommit);
		return result;
	}

	/*
	 * 
	 * 功能：执行存储过�?
	 * 
	 */
	public String executeProcedure(java.lang.String sql, boolean autocommit) {
		System.out.println("sys.....");
		int result;
		String returnvalue = ""; //$NON-NLS-1$
		// Vector vct = new Vector();
		if (autocommit == true) // process sql without transacation process!
		{
			try {
				java.sql.Connection connection = getConnection();
				if (statement == null)
					statement = connection.createStatement();
				result = statement.executeUpdate(sql);
				//
				System.out.println("result2:"+result);
				returnvalue=""+result;
				

			} catch (SQLException e) {
				returnvalue = returnvalue + e.toString();
				rollback();
				e.printStackTrace();
			}
		} else // return a sql that should be processed by transaction process!
		{
			returnvalue = sql;
		}

		return returnvalue;
	}

	/*
	 * 
	 * 功能：执行更�?
	 * 
	 */
	public String executeUpdate(String sql, boolean autocommit) {
		// PersistenceManager permanager = new PersistenceManager();
		// Vector vct = new Vector();
		String result = null;
		result = executeProcedure(sql, autocommit);
		return result;
	}

	public Vector putSQL2Transaction(String strsql) {
		transactionVct.addElement(strsql);

		return transactionVct;

	}

	public Vector putSQL2Transaction(String strsql, Vector vct) {
		// transactionVct.addElement(strsql);
		vct.addElement(strsql);

		return vct;

	}

	public String executeDelete(java.lang.String sql) {
		// PersistenceManager permanager = new PersistenceManager();
		String error = null;
		error = executeProcedure(sql);

		return error;

	}

	public String executeInsert(java.lang.String sql) {

		// PersistenceManager permanager = new PersistenceManager();
		String error = null;
		error = executeProcedure(sql);
		return error;

	}

	public String executeUpdate(String sql)
	// throws java.sql.SQLException
	{
		// PersistenceManager permanager = new PersistenceManager();
		String error = null;
		error = executeProcedure(sql);
		return error;
	}

	// 初始化代码表

	static {
		//
		// //不要改变下列程序的顺�?
		// //清空Hashtable
		//
		// pms.clear();
		//
		// SystemInit.getCodemap().clear();
		// //JDBC2.0连接初始�?
		init();
		//
		// //重新建立�?��Hashtabless
		// initPms();
		//
		// //SystemInit sysinit =new SystemInit();
		// SystemInit.initCodeMap();
		// SystemInit.initDriverCodeMap();
		//   
		// // 初始化错误代码列�?
		// ErrorMap.initErrorMap();
		//             
	}


	/**
	 * 
	 * 功能：返回查询的结果�?
	 * 
	 */

	public ResultSet executeQuery(String sql, boolean writeable) {

		String err = ""; //$NON-NLS-1$
		try {
			java.sql.Connection connection = getConnection();
			// if (statement == null)
			if (writeable = true) {
				pstmt = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

				resultSet = pstmt.executeQuery();
			} else {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
			}

		} catch (SQLException e) {

			err = err + e.toString();
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * 
	 * 功能：第�?��运行，读取数据库访问属�?
	 * 
	 */

	private static void firstRun() {
		/*
		 * if (!isFirstRun) return;
		 */

		try {
			InputStream in = new BufferedInputStream(new FileInputStream("datasource.properties"));
			Properties p = new Properties();
			p.load(in);

			// System.out.println("bbbbb");
			// System.out.println("1="+p.getProperty("datasourcename"));
			// System.out.println("2="+p.getProperty("user"));
			// System.out.println("3="+p.getProperty("password"));
			setDataSourceName(p.getProperty("datasourcename"));
			setDbUser(p.getProperty("user"));
			setDbPassword(p.getProperty("password"));
			String debug = (String) p.getProperty("debug");
			if (debug != null && debug.equalsIgnoreCase("true"))
				debugFlag = true;
			p.clear();
			in.close();
			isFirstRun = false;
		} catch (Throwable e) {
			e.printStackTrace();
			isFirstRun = false;
			debugFlag = false;
		} finally {

		}
	}

	/**
	 * 回收方法
	 */
	protected void finalize() {
		try {
			super.finalize();
		} catch (Throwable theException) {
			// theException.printStackTrace();
		}
	}
}