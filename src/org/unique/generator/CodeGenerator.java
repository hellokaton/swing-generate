package org.unique.generator;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.unique.generator.model.Database;
import org.unique.generator.model.Table;

/**
 * 代码生成类
 * 
 * @ClassName: DomainGenerator
 * @author AndrewWen
 * @date 2013-1-14 下午8:45:30
 */
public class CodeGenerator {

	//是否去掉模块前面的编号
	public static String prefix = "";
	private String classPackage;
	private String author;
	private String codePath;
	private String classDriver;
	private String url;
	private String username;
	private String password;
	private String sourcePath;
	private String contact;

	public CodeGenerator() {

	}
	
	public CodeGenerator(String driver, String url, String userName, String passWord, 
			String classPackage, String author, String contact, String prefix_, String codePath) {
		this.classPackage = classPackage;
		this.author = author;
		this.codePath = codePath;
		this.classDriver = driver;
		this.url = url;
		this.username = userName;
		this.password = passWord;
		prefix = prefix_;
		this.contact = contact;
		sourcePath = codePath + File.separator + "src/";
	}

	public boolean generator() {

		/**
		 * 数据库
		 */
		Database databaseBean = null;

		/**
		 * 数据库表
		 */
		List<Table> tableList = null;
		
		try {
			String schema = url.substring(url.lastIndexOf("/") + 1);
			//System.out.println(schema);
			databaseBean = new DatabaseInfoOp(classDriver, url, username, password, schema).getDbInfo();
			tableList = databaseBean.getTableList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		/**
		 * 遍历生成代码
		 */
		for (Table table : tableList) {

			table.setPackageName(this.classPackage);
			
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("table", table);
			map.put("contact", contact);
			map.put("author", this.author);

			System.out.println("生成表：" + table.getTableName());
			
			VelocityInfoOp.generatorCode("model.vm", map, this.sourcePath + table.getPackagePath() + "/model", table.getClassName() + ".java");
			VelocityInfoOp.generatorCode("service.vm", map, this.sourcePath + table.getPackagePath() + "/service", table.getClassName() + "Service.java");
			VelocityInfoOp.generatorCode("serviceImpl.vm", map, this.sourcePath + table.getPackagePath() + "/service/impl", table.getClassName() + "ServiceImpl.java");
			VelocityInfoOp.generatorCode("controller.vm", map, this.sourcePath + table.getPackagePath() + "/controller", table.getClassName() + "Controller.java");
		}

		System.out.println("***************代码生成完成******************");
		System.out.println("代码路径：" + this.codePath);
		System.out.println("包：" + this.classPackage);
		System.out.println("作者：" + this.author);
		System.out.println("********************************************");
		return true;
	}
	
}
