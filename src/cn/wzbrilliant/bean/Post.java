package cn.wzbrilliant.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * 帖子
 *
 * @author ice
 *
 * @date 2015年8月23日
 */
public class Post implements Serializable {
	
	/**
	 * 所属贴吧
	 */
	private String barName;

	/**
	 * 所属贴吧在数据库中的id
	 */
	private String barId;
	
	/**
	 * 回复数量
	 */
	private int replyNumber;
	
	private String title;
	private String author;
	private Date date;
	private String url;
	private String id;

	public Post() {
		super();
	}

	public Post(String id, String title, String author, String barName, String barId, int replyNumber, Date date, String url) {
		super();
		this.barName = barName;
		this.replyNumber = replyNumber;
		this.title = title;
		this.author = author;
		this.date = date;
		this.url = url;
		this.id = id;
		this.barId=barId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getBarName() {
		return barName;
	}

	public void setBarName(String barName) {
		this.barName = barName;
	}

	public int getReplyNumber() {
		return replyNumber;
	}

	public void setReplyNumber(int replyNumber) {
		this.replyNumber = replyNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBarId() {
		return barId;
	}

	public void setBarId(String barId) {
		this.barId = barId;
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
