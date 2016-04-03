package cn.wzbrilliant.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * 读取xml配置文件工具类
 *
 * @author ice
 *
 * @date 2015年8月3日
 */
public class Dom4jUtils {
	private static Document document;

	static {
		try {
			SAXReader reader = new SAXReader();
			File file = new File(".\\config.xml");
			document = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取指定节点的参数值
	 * @param node
	 * @return
	 */
	public static String getConfig(String nodeName) {
		Node node=document.selectSingleNode("//"+nodeName);
		return node.getText();
	}

	public static String getConfig(String fatherNodeName, String nodeName) {
		Node node=document.selectSingleNode("//"+fatherNodeName+"/"+nodeName);
		return node.getText();
	}
	
	
	
}
