package org.unique.generator.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * properties file util
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public class PropUtil {
	
	private static final Logger logger = Logger.getLogger(PropUtil.class);
	/**
	 * //TODO 根据文件名读取properties文件
	 * 
	 * @param resourceName
	 * @return
	 */
	public static Properties getProperty(String resourceName) {
		InputStream in = null;
		Properties props = new Properties();
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
			if (in != null) {
				props.load(in);
			}
		} catch (IOException e) {
			logger.error("加载属性文件出错！", e);
		} finally {
			close(in);
		}
		return props;
	}

	public static String getPropertyFromFile(final Map<String, String> map, final String key){
		String result = "";
		if(null != map && !map.isEmpty()){
			if(map.containsKey(key)){
				return map.get(key).trim();
			}
		}
		return result;
	}
	/**
	 * //TODO 根据文件名读取map数据
	 * 
	 * @param resourceName
	 * @return
	 */
	public static Map<String, String> getPropertyMap(String resourceName) {
		InputStream in = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
			if (null == in) {
				throw new RuntimeException("the properties file [" + resourceName + "] not found!");
			}
			Properties prop = new Properties();
			prop.load(in);
			Set<Entry<Object, Object>> set = prop.entrySet();
			Iterator<Map.Entry<Object, Object>> it = set.iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				map.put(entry.getKey().toString(), entry.getValue().toString());
			}
			logger.info("Loading properties file from class path resource [" + resourceName + "]");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(in);
		}
		return map;
	}

	/**
	 * //TODO 根据property对象获取map格式数据
	 * 
	 * @param prop
	 * @return
	 */
	public static Map<String, String> getPropertyMap(Properties prop) {
		Map<String, String> map = new HashMap<String, String>();
		Set<Entry<Object, Object>> set = prop.entrySet();
		Iterator<Map.Entry<Object, Object>> it = set.iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			map.put(entry.getKey().toString(), entry.getValue().toString());
		}
		return map;
	}
	
	public static void close(Closeable closeable){
		if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
