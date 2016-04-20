package net.ddns.hnmirror.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STORIES")
public class Story implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4048886174806712623L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "item_id")
	private Long itemId;

	@Column(name = "title")
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "text")
	private String text;

	@Column(name = "url")
	private String url;

	@Column(name = "type")
	private String type;

	@Column(name = "time")
	private Long time;

	@Column(name = "score")
	private Long score;

	public Story() {
		itemId = null;
		title = null;
		author = null;
		text = null;
		url = null;
		type = null;
		time = null;
		score = null;
	}

	public Story(Long itemId, String title, String author, String text, String url, String type, Long time,
			Long score) {
		this.itemId = itemId;
		this.title = title;
		this.author = author;
		this.text = text;
		this.url = url;
		this.type = type;
		this.time = time;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	//
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

}
