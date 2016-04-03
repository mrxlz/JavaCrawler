package cn.wzbrilliant.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * 回复
 *
 * @author ice
 *
 * @date 2015年8月23日
 */
public class Comment implements Serializable {
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 楼层数
	 */
	private int floor;
	
	/**
	 * 所属帖子
	 */
	private String postUrl;
	
	/**
	 * 所属帖子在数据库中的id
	 */
	private String postId;
	
	private String author;
	
	/**
	 * 发帖时间
	 */
	private Date date;
	private String id;
//	private int replyNum;
//	private List<CommentReply> cmtReply;

	public Comment() {
		super();
	}

	public Comment(String id, String author,String content, int floor,String postUrl, String postId, Date date) {
		super();
		this.content = content;
		this.floor = floor;
		this.postUrl = postUrl;
		this.author = author;
		this.date = date;
		this.id = id;
		this.postId = postId;
//		this.replyNum = replyNum;
//		this.cmtReply = cmtReply;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
	
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

//	public int getReplyNum() {
//		return replyNum;
//	}
//
//	public void setReplyNum(int replyNum) {
//		this.replyNum = replyNum;
//	}

//	public List<CommentReply> getCmtReply() {
//		return cmtReply;
//	}
//
//	public void setCmtReply(List<CommentReply> cmtReply) {
//		this.cmtReply = cmtReply;
//	}

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
