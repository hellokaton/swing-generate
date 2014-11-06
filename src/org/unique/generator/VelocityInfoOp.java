package org.unique.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * 模板操作类
 * @ClassName: CodeGenerator
 * @author AndrewWen
 * @date 2013-1-14 下午5:25:30
 */
public class VelocityInfoOp {

	public static void generatorCode(String templateFile, Map<String, Object> contextMap, String path, String fileName) {
		//设置模板载入路径
		VelocityContext context = new VelocityContext();

		//获取模板引擎
		VelocityEngine ve = new VelocityEngine();

		//模板文件所在的路径
		String vPath = System.getProperty("user.dir") + "\\template";

		//设置参数
		ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, vPath);

		//处理中文问题
		ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");

		ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");

		//初始化模板
		ve.init();

		//设置变量值
		for (Object key : contextMap.keySet()) {
			context.put(key.toString(), contextMap.get(key));
		}

		Template template = null;

		try {
			template = ve.getTemplate(templateFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringWriter sw = new StringWriter();

		if (template != null){
			template.merge(context, sw);
		}

		//生成目录
		File pathTemp = new File(path);
		if (!pathTemp.exists()) {
			pathTemp.mkdirs();
		}

		/**
		 * 文件写入流
		 */
		writeFile(pathTemp + "/" + fileName, sw.toString());
	}

	/**
	 * 写入文件
	 * @Title: writeFile 
	 * @param filePathAndName
	 * @param fileContent
	 */
	public static void writeFile(String filePathAndName, String fileContent) {
		try {
			File f = new File(filePathAndName);
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(fileContent);
			writer.close();
		} catch (Exception e) {
			System.out.println("写文件内容操作出错");
			e.printStackTrace();
		}
	}

}
