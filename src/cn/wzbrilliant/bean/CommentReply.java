package cn.wzbrilliant.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

public class CommentReply implements Serializable {
	private String id;
	private String content;
	private String author;
	private Date date;
	private String commentId;

	public CommentReply() {
		super();
	}

	public CommentReply(String id, String content, String author, Date date, String commentId) {
		super();
		this.id = id;
		this.content = content;
		this.author = author;
		this.date = date;
		this.commentId = commentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		commentId = commentId;
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
