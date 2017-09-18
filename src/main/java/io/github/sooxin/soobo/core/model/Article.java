package io.github.sooxin.soobo.core.model;

import java.util.Date;
import java.util.List;

public class Article {
	private int articleId; // 文章编号
	private String articleTitle; // 文章标题
	private String authorName; // 作者姓名
	private Date createdDate; // 文章创建时间
	private Date updatedDate; // 上次更新时间
	private List<String> articleTags; // 文章标签
	private String articleLink; // 文章链接
	private String permaLink; // 文章永久链接链接
	private String articleSummary; // 文章摘要
	private String rawContent; // 文章原始 markdown 内容
	private String renderedContent; // 经渲染后的文章内容（html格式）
	private String hashValue; // 文章类容的哈希值
	private String filePath; // 文章路径
	private ArticleItem preArticle; // 前一篇文章
	private ArticleItem nextAticle; // 后一片文章

	public Article() {}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
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

	public List<String> getArticleTags() {
		return articleTags;
	}

	public void setArticleTags(List<String> articleTags) {
		this.articleTags = articleTags;
	}

	public String getArticleLink() {
		return articleLink;
	}

	public void setArticleLink(String articleLink) {
		this.articleLink = articleLink;
	}

	public String getPermaLink() {
		return permaLink;
	}

	public void setPermaLink(String permaLink) {
		this.permaLink = permaLink;
	}

	public String getArticleSummary() {
		return articleSummary;
	}

	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}

	public String getRawContent() {
		return rawContent;
	}

	public void setRawContent(String rawContent) {
		this.rawContent = rawContent;
	}

	public String getRenderedContent() {
		return renderedContent;
	}

	public void setRenderedContent(String renderedContent) {
		this.renderedContent = renderedContent;
	}

	public String getHashValue() {
		return hashValue;
	}

	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ArticleItem getPreArticle() {
		return preArticle;
	}

	public void setPreArticle(ArticleItem preArticle) {
		this.preArticle = preArticle;
	}

	public ArticleItem getNextAticle() {
		return nextAticle;
	}

	public void setNextAticle(ArticleItem nextAticle) {
		this.nextAticle = nextAticle;
	}

	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", articleTitle=" + articleTitle + ", preArticle=" + preArticle
				+ ", nextAticle=" + nextAticle + "]";
	}
	
	

}
