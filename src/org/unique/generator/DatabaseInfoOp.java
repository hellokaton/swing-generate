package org.unique.generator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unique.generator.model.Column;
import org.unique.generator.model.Database;
import org.unique.generator.model.Table;

/**
 * 数据库信息获得操作类
 * 
 * @ClassName: DatabaseInfoOp
 * @author Andrew.Wen
 * @date 2013-1-10 下午3:56:23
 */
public class DatabaseInfoOp {

	Connection conn = null;

	private String classDriver;
	private String url;
	private String username;
	private String password;
	private String schema;

	public DatabaseInfoOp(String classDriver, String url, String username, String password) {
		super();
		this.classDriver = classDriver;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public DatabaseInfoOp(String classDriver, String url, String username, String password, String schema) {
		super();
		this.classDriver = classDriver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.schema = schema;
	}

	public Connection getConnectionByJDBC() {
		try {
			// 装载驱动包类
			Class.forName(classDriver);
			// 加载驱动
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			System.out.println("装载驱动包出现异常!请查正！");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("链接数据库发生异常!");
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获得表数据
	 * 
	 * @Title: getDbInfo
	 * @return
	 * @throws SQLException
	 */
	public Database getDbInfo(String tableNamePattern) throws SQLException {

		Database databaseBean = new Database();

		// 表队列
		List<Table> tableList = new ArrayList<Table>();

		// 初始化数据库
		getConnectionByJDBC();

		// 获取数据库信息
		DatabaseMetaData dbmd = conn.getMetaData();

		databaseBean.setDatabaseProductName(dbmd.getDatabaseProductName());

		// 获得数据库表
		ResultSet rs = dbmd.getTables(null, null, tableNamePattern, new String[] { "TABLE", "VIEW" });

		// String tableName = "";
		while (rs.next()) {
			Table table = new Table();

			table.setTableName(rs.getString("TABLE_NAME"));
			table.setTableComment(rs.getString("REMARKS"));
			table.setTableSchem(rs.getString(1));

			// 设置列信息
			ResultSet rscol = dbmd.getColumns(null, null, table.getTableName(), null);

			Column tempColumn;

			while (rscol.next()) {
				tempColumn = new Column();
				tempColumn.setColumnName(rscol.getString("COLUMN_NAME"));
				tempColumn.setColumnType(Integer.parseInt(rscol.getString("DATA_TYPE")));

				String remarks = rscol.getString("REMARKS");
				if (remarks.length() < 1)
					remarks = "";
				tempColumn.setColumnComment(remarks);
				tempColumn.setAutoIncrement(rscol.getString("IS_AUTOINCREMENT").equals("YES"));
				tempColumn.setNullAble(rscol.getString("IS_AUTOINCREMENT").equals("YES"));

				// 添加列到表中
				table.getColumnList().add(tempColumn);

			}

			// 设置主键列
			ResultSet rsPrimary = dbmd.getPrimaryKeys(null, null, table.getTableName());
			while (rsPrimary.next()) {
				if (rsPrimary.getString("COLUMN_NAME") != null) {

					for (int i = 0; i < table.getColumnList().size(); i++) {
						Column coltemp = table.getColumnList().get(i);
						if (coltemp.getColumnName().equals(rsPrimary.getString("COLUMN_NAME"))) {
							coltemp.setPrimary(true);
						}
					}

				}
			}

			// 设置外键列
			ResultSet rsFPrimary = dbmd.getImportedKeys(null, null, table.getTableName());
			while (rsFPrimary.next()) {

				for (int i = 0; i < table.getColumnList().size(); i++) {
					Column coltemp = table.getColumnList().get(i);
					if (coltemp.getColumnName().equals(rsFPrimary.getString("FKCOLUMN_NAME"))) {
						coltemp.setForeignKey(true);
					}
				}
			}
			tableList.add(table);
		}
		databaseBean.setTableList(tableList);
		return databaseBean;
	}

	public Database getDbInfo() throws SQLException {

		Database databaseBean = new Database();

		// 表队列
		List<Table> tableList = new ArrayList<Table>();

		// 初始化数据库
		getConnectionByJDBC();

		// 获取数据库信息
		DatabaseMetaData dbmd = conn.getMetaData();

		databaseBean.setDatabaseProductName(dbmd.getDatabaseProductName());

		// 获得数据库表
		ResultSet rs = dbmd.getTables(null, this.schema, null, new String[] { "TABLE", "VIEW" });

		// String tableName = "";
		while (rs.next()) {
			Table table = new Table();

			table.setTableName(rs.getString("TABLE_NAME"));
			table.setTableComment(rs.getString("REMARKS"));
			table.setTableSchem(rs.getString(1));

			// 设置列信息
			ResultSet rscol = dbmd.getColumns(null, null, table.getTableName(), null);

			Column tempColumn;

			while (rscol.next()) {
				tempColumn = new Column();
				tempColumn.setColumnName(rscol.getString("COLUMN_NAME"));
				tempColumn.setColumnType(Integer.parseInt(rscol.getString("DATA_TYPE")));

				String remarks = rscol.getString("REMARKS");
				if (remarks.length() < 1)
					remarks = "";
				tempColumn.setColumnComment(remarks);
				tempColumn.setAutoIncrement(rscol.getString("IS_AUTOINCREMENT").equals("YES"));
				tempColumn.setNullAble(rscol.getString("IS_AUTOINCREMENT").equals("YES"));

				// 添加列到表中
				table.getColumnList().add(tempColumn);

			}

			// 设置主键列
			ResultSet rsPrimary = dbmd.getPrimaryKeys(null, null, table.getTableName());
			while (rsPrimary.next()) {
				if (rsPrimary.getString("COLUMN_NAME") != null) {

					for (int i = 0; i < table.getColumnList().size(); i++) {
						Column coltemp = table.getColumnList().get(i);
						if (coltemp.getColumnName().equals(rsPrimary.getString("COLUMN_NAME"))) {
							coltemp.setPrimary(true);
						}
					}

				}
			}

			// 设置外键列
			ResultSet rsFPrimary = dbmd.getImportedKeys(null, null, table.getTableName());
			while (rsFPrimary.next()) {

				for (int i = 0; i < table.getColumnList().size(); i++) {
					Column coltemp = table.getColumnList().get(i);
					if (coltemp.getColumnName().equals(rsFPrimary.getString("FKCOLUMN_NAME"))) {
						//System.out.println("FKCOLUMN_NAME "+rsFPrimary.getString("FKCOLUMN_NAME"));
						coltemp.setForeignKey(true);
					}
				}
			}
			tableList.add(table);

		}

		databaseBean.setTableList(tableList);
		return databaseBean;
	}
	
}
