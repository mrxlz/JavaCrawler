package wz.dom;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jUtils {
	private static SAXReader reader;
	private static File file ;
	private static Document document;
	
	static{
		try {
			reader=new SAXReader();
			file=new File("pageinfo.xml");
			document=reader.read(file);
		} catch (DocumentException e) {
			System.err.println("读取pageinfo.xml出错");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取要爬取页面的贴吧名字
	 * @return 所要爬取的贴吧名的String数组
	 */
	@SuppressWarnings("unchecked")
	public static String[] getPostBarName(){
		
		Element root=document.getRootElement();
		List<Element> name=root.elements("name");
		String postBarName[]=new String[name.size()];
		
		return postBarName;
	}
	
	/**
	 * 更新xml文件中所爬取贴吧的名称
	 */
	public static void updateXml(){
		
	}

}
