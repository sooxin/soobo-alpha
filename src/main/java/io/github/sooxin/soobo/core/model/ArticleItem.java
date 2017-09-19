package io.github.sooxin.soobo.core.model;

import java.util.Date;
import java.util.List;

public class ArticleItem {
	private int id; // 文章 id
	private Date createdDate; // 文章创建日期
	private Date updatedDate; // 文章最近更新日期
	private String articleTitle; // 文章标题
	private String authorName; // 文章作者
	private String permaLink; // 文章链接
	private String summary; // 文章摘要
	private List<String> tags; // 文章标签
	
	public ArticleItem() {};
	
	public ArticleItem(int id, Date createdDate, Date updatedDate, String articleTitle, String authorName,
			String permaLink, String summary, List<String> tags) {
		super();
		this.id = id;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.articleTitle = articleTitle;
		this.authorName = authorName;
		this.permaLink = permaLink;
		this.summary = summary;
		this.tags = tags;
	}
	
	public static ArticleItem parseArticleToArticleItem(Article article) {
		ArticleItem articleItem=new ArticleItem(article.getArticleId(), article.getCreatedDate(), article.getUpdatedDate(), article.getArticleTitle(), article.getAuthorName(), article.getPermaLink(), article.getArticleSummary(), article.getArticleTags());
		return articleItem;
	}	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getPermaLink() {
		return permaLink;
	}
	public void setPermaLink(String permaLink) {
		this.permaLink = permaLink;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
}
