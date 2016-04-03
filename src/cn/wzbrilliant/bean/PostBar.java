package cn.wzbrilliant.bean;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 贴吧
 *
 * @author ice
 *
 * @date 2015年8月23日
 */
public class PostBar implements Serializable {
	
	private String id;
	private String name;
	private String url;

	public PostBar() {
		super();
	}

	public PostBar(String id, String name, String url) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 根据字段名获取字段值
	 * @param fieldName 字段名
	 * @return 字段值
	 */
	private Object getFieldValue(String fieldName){
		Object obj=null;
		try {
			Field field=this.getClass().getDeclaredField(fieldName);
			obj=field.get(this);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 获取对象字段值的object数组
	 * @param fieldsName 索要获取字段的字段名
	 * @return 指定字段值的Object数组
	 */
	public Object[] toArray(String[] fieldsName){
		int size=fieldsName.length;
		Object[] objs=new Object[size];
		for(int i=0;i<size;i++){
			objs[i]=this.getFieldValue(fieldsName[i]);
		}
		return objs;
	}

}
