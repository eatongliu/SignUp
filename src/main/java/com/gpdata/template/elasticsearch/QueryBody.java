package com.gpdata.template.elasticsearch;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by acer_liuyutong on 2017/5/25.
 */
public class QueryBody implements Serializable{

	private String id;
	private String name;
	private Date createDate;
	private Date dateTimestamp;
	private String education;
	private String yangqi;
	private String beijing;
	private String guoqi;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDateTimestamp() {
		return dateTimestamp;
	}

	public void setDateTimestamp(Date dateTimestamp) {
		this.dateTimestamp = dateTimestamp;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getYangqi() {
		return yangqi;
	}

	public void setYangqi(String yangqi) {
		this.yangqi = yangqi;
	}

	public String getBeijing() {
		return beijing;
	}

	public void setBeijing(String beijing) {
		this.beijing = beijing;
	}

	public String getGuoqi() {
		return guoqi;
	}

	public void setGuoqi(String guoqi) {
		this.guoqi = guoqi;
	}

	public QueryBody(String id, String name, Date createDate, Date dateTimestamp, String education, String yangqi, String beijing, String guoqi) {
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.dateTimestamp = dateTimestamp;
		this.education = education;
		this.yangqi = yangqi;
		this.beijing = beijing;
		this.guoqi = guoqi;
	}

	@Override
	public String toString() {
		return "QueryBody{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", createDate=" + createDate +
				", dateTimestamp=" + dateTimestamp +
				", education='" + education + '\'' +
				", yangqi='" + yangqi + '\'' +
				", beijing='" + beijing + '\'' +
				", guoqi='" + guoqi + '\'' +
				'}';
	}
}
