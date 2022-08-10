package com.example.download1.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 读取.properties配置文件的内容至Map中
 */
public class PropertiesUtil {

	/**
	 * 读取.properties配置文件的内容至Map
	 * @param propertiesFile 配置文件名称（不包含文件后缀名.properties等）
	 * @return
	 * @throws EfmpxIOException 
	 */
	public static Map read(String propertiesFile)  {

		try {			
			if (!(propertiesFile.indexOf("properties") >0))
			{
				propertiesFile = propertiesFile + ".properties";
			}
			InputStream inStream =Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile);
			Properties p = new  Properties();
			p.load(inStream);
			
			Map map =  properties2map(p);
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将属性文件转为map
	 * @param prop
	 * @return
	 */
	public static Map properties2map(Properties prop){
		Map map = new HashMap();
		Enumeration enu = prop.keys();
		while (enu.hasMoreElements()) {
			Object obj = enu.nextElement();
			Object objv = prop.get(obj);
			map.put(obj, objv);
		}
		return map;
	}

}
