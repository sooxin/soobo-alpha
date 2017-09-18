package io.github.sooxin.soobo.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.github.sooxin.soobo.core.dbconnector.DBConnector;
import io.github.sooxin.soobo.core.model.Article;
import io.github.sooxin.soobo.core.model.ArticleItem;
import io.github.sooxin.soobo.core.tool.SooboUtil;

public class ArticleDao {
	private Gson gson = new Gson();
	private SooboUtil sooboUtil = new SooboUtil();

	/**
	 * 根据给定的id查询一条Article信息
	 * 
	 * @param articleId
	 *            文章的id
	 * @return 对应id的文章Article实例
	 * @throws SQLException
	 */
	public Article selectArticle(int articleId) throws SQLException {
		Connection conn = DBConnector.getConnection();
		PreparedStatement psmt = conn.prepareStatement("select * from article where id=?");
		psmt.setInt(1, articleId);
		ResultSet rs = psmt.executeQuery();
		Article article = new Article();
		// if(rs.next)没查询到
		while (rs.next()) {
			article.setArticleId(rs.getInt("id"));
			article.setArticleTitle(rs.getString("title"));
			article.setAuthorName(rs.getString("author_name"));
			article.setCreatedDate(sooboUtil.formatStringToDate(rs.getString("created_date")));
			article.setUpdatedDate(sooboUtil.formatStringToDate(rs.getString("updated_date")));
			List<String> tags = gson.fromJson(rs.getString("tags"), new TypeToken<List<String>>() {
			}.getType());
			article.setArticleTags(tags);
			article.setArticleLink(rs.getString("article_link"));
			article.setPermaLink(rs.getString("perma_link"));
			article.setArticleSummary(rs.getString("summary"));
			article.setRawContent(rs.getString("raw_content"));
			article.setRenderedContent(rs.getString("rendered_content"));
			article.setFilePath(rs.getString("file_path"));
			article.setHashValue(rs.getString("hash_value"));
			article.setPreArticle(gson.fromJson(rs.getString("pre_article"), ArticleItem.class));
			article.setNextAticle(gson.fromJson(rs.getString("next_article"), ArticleItem.class));
		}

		rs.close();
		psmt.close();
		conn.close();

		return article;
	}

	public void insert(Article article) throws SQLException {
		Connection conn = DBConnector.getConnection();

		String sql = "insert into"
				+ " article(title,author_name,created_date,updated_date,tags,article_link,perma_link,summary,raw_content,rendered_content,file_path,hash_value,pre_article,next_article)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement psmt = conn.prepareStatement(sql);

		psmt.setString(1, article.getArticleTitle());
		psmt.setString(2, article.getAuthorName());
		psmt.setString(3, sooboUtil.formatDateToString(article.getCreatedDate()));
		psmt.setString(4, sooboUtil.formatDateToString(article.getUpdatedDate()));
		psmt.setString(5, gson.toJson(article.getArticleTags()));
		psmt.setString(6, article.getArticleLink());
		psmt.setString(7, article.getPermaLink());
		psmt.setString(8, article.getArticleSummary());
		psmt.setString(9, article.getRawContent());
		psmt.setString(10, article.getRenderedContent());
		psmt.setString(11, article.getFilePath());
		psmt.setString(12, article.getHashValue());
		psmt.setString(13, gson.toJson(article.getPreArticle()));
		psmt.setString(14, gson.toJson(article.getNextAticle()));

		psmt.executeUpdate();

		psmt.close();
		conn.close();
	}

	public void updateArticle(Article article) throws SQLException {
		Connection conn = DBConnector.getConnection();

		String sql = "update article set"
				+ " title=?,author_name=?,created_date=?,updated_date=?,tags=?,article_link=?,perma_link=?,summary=?,raw_content=?,rendered_content=?,file_path=?,hash_value=?,pre_article=?,next_article=?"
				+ " where id=?";
		PreparedStatement psmt = conn.prepareStatement(sql);

		psmt.setString(1, article.getArticleTitle());
		psmt.setString(2, article.getAuthorName());
		psmt.setString(3, sooboUtil.formatDateToString(article.getCreatedDate()));
		psmt.setString(4, sooboUtil.formatDateToString(article.getUpdatedDate()));
		psmt.setString(5, gson.toJson(article.getArticleTags()));
		psmt.setString(6, article.getArticleLink());
		psmt.setString(7, article.getPermaLink());
		psmt.setString(8, article.getArticleSummary());
		psmt.setString(9, article.getRawContent());
		psmt.setString(10, article.getRenderedContent());
		psmt.setString(11, article.getFilePath());
		psmt.setString(12, article.getHashValue());
		psmt.setString(13, gson.toJson(article.getPreArticle()));
		psmt.setString(14, gson.toJson(article.getNextAticle()));

		psmt.setInt(15, article.getArticleId());

		psmt.executeUpdate();

		psmt.close();
		conn.close();
	}

	public void deleteArticle(String articleId) throws SQLException {
		Connection conn = DBConnector.getConnection();
		String sql = "delete from article where id=?";

		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, articleId);

		psmt.executeUpdate();

		psmt.close();
		conn.close();
	}

	public void checkTable() throws SQLException {
		Connection conn = DBConnector.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS `article` (" + "	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "	`title`	VARCHAR(20) NOT NULL," + "	`author_name`	VARCHAR(10)," + "	`created_date`	DATETIME,"
				+ "	`updated_date`	DATETIME," + "	`tags`	VARCHAR(30)," + "	`article_link`	VARCHAR(20),"
				+ "	`perma_link`	VARCHAR(40) NOT NULL," + "	`summary`	VARCHAR(300)," + "	`raw_content`	TEXT,"
				+ "	`rendered_content`	TEXT," + "	`file_path`	VARCHAR(30)," + "	`hash_value`	VARCHAR(45),"
				+ "	`pre_article`	VARCHAR(100)," + "	`next_article`	VARCHAR(100)" + ")";
		stmt.execute(sql);

	}

	/**
	 * 获取文章数量
	 * 
	 * @return 文章数量
	 * @throws SQLException
	 */
	public int getArticleNum() throws SQLException {
		Connection conn = DBConnector.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "select COUNT(id) from article";
		ResultSet rs = stmt.executeQuery(sql);
		int num = 0;
		while (rs.next()) {
			num = rs.getInt(1);
		}
		rs.close();
		stmt.close();
		conn.close();

		return num;
	}

	public List<Article> getLimitArticlesList(int limit, int offset) throws SQLException {
		Connection conn = DBConnector.getConnection();
		String sql = "SELECT * FROM article ORDER BY id DESC LIMIT ? OFFSET ?";
		PreparedStatement psmt = conn.prepareStatement(sql);

		psmt.setInt(1, limit);
		psmt.setInt(2, offset);

		ResultSet rs = psmt.executeQuery();
		List<Article> list = new ArrayList<Article>();

		while (rs.next()) {
			Article article = new Article();
			article.setArticleId(rs.getInt("id"));
			article.setArticleTitle(rs.getString("title"));
			article.setAuthorName(rs.getString("author_name"));
			article.setCreatedDate(sooboUtil.formatStringToDate(rs.getString("created_date")));
			article.setUpdatedDate(sooboUtil.formatStringToDate(rs.getString("updated_date")));
			List<String> tags = gson.fromJson(rs.getString("tags"), new TypeToken<List<String>>() {
			}.getType());
			article.setArticleTags(tags);
			article.setArticleLink(rs.getString("article_link"));
			article.setPermaLink(rs.getString("perma_link"));
			article.setArticleSummary(rs.getString("summary"));
			article.setRawContent(rs.getString("raw_content"));
			article.setRenderedContent(rs.getString("rendered_content"));
			article.setFilePath(rs.getString("file_path"));
			article.setHashValue(rs.getString("hash_value"));
			article.setPreArticle(gson.fromJson(rs.getString("pre_article"), ArticleItem.class));
			article.setNextAticle(gson.fromJson(rs.getString("next_article"), ArticleItem.class));
			list.add(article);
		}

		return list;
	}

	public List<ArticleItem> getLimitArticleItemList(int limit, int offset) throws SQLException {
		Connection conn = DBConnector.getConnection();
		String sql = "SELECT id,created_date,updated_date,title,author_name,perma_link,summary,tags FROM article ORDER BY id DESC LIMIT ? OFFSET ?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setInt(1, limit);
		psmt.setInt(2, offset);
		ResultSet rs = psmt.executeQuery();
		List<ArticleItem> list = new ArrayList<ArticleItem>();
		while (rs.next()) {
			ArticleItem articleItem = new ArticleItem();
			articleItem.setId(rs.getInt("id"));
			articleItem.setArticleTitle(rs.getString("title"));
			articleItem.setCreatedDate(sooboUtil.formatStringToDate(rs.getString("created_date")));
			articleItem.setUpdatedDate(sooboUtil.formatStringToDate(rs.getString("updated_date")));
			articleItem.setAuthorName(rs.getString("author_name"));
			articleItem.setPermaLink(rs.getString("perma_link"));
			articleItem.setSummary(rs.getString("summary"));
			List<String> tags = gson.fromJson(rs.getString("tags"), new TypeToken<List<String>>() {
			}.getType());
			articleItem.setTags(tags);
			list.add(articleItem);
		}
		return list;
	}
	public List<ArticleItem> getLimitArticleItemListOrderByCreatedDate(int limit, int offset) throws SQLException {
		Connection conn = DBConnector.getConnection();
		String sql = "SELECT id,created_date,updated_date,title,author_name,perma_link,summary,tags FROM article ORDER BY created_date DESC,id DESC LIMIT ? OFFSET ?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setInt(1, limit);
		psmt.setInt(2, offset);
		ResultSet rs = psmt.executeQuery();
		List<ArticleItem> list = new ArrayList<ArticleItem>();
		while (rs.next()) {
			ArticleItem articleItem = new ArticleItem();
			articleItem.setId(rs.getInt("id"));
			articleItem.setArticleTitle(rs.getString("title"));
			articleItem.setCreatedDate(sooboUtil.formatStringToDate(rs.getString("created_date")));
			articleItem.setUpdatedDate(sooboUtil.formatStringToDate(rs.getString("updated_date")));
			articleItem.setAuthorName(rs.getString("author_name"));
			articleItem.setPermaLink(rs.getString("perma_link"));
			articleItem.setSummary(rs.getString("summary"));
			List<String> tags = gson.fromJson(rs.getString("tags"), new TypeToken<List<String>>() {
			}.getType());
			articleItem.setTags(tags);
			list.add(articleItem);
		}
		return list;
	}
}
