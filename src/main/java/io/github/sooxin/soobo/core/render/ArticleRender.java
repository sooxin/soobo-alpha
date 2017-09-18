package io.github.sooxin.soobo.core.render;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.sooxin.soobo.core.dao.ArticleDao;
import io.github.sooxin.soobo.core.io.FileInput;
import io.github.sooxin.soobo.core.io.FileOutput;
import io.github.sooxin.soobo.core.model.Article;
import io.github.sooxin.soobo.core.model.ArticleItem;
import io.github.sooxin.soobo.core.model.WebSiteConfig;
import io.github.sooxin.soobo.core.render.renderer.BeetlRenderer;
import io.github.sooxin.soobo.core.render.renderer.MarkdownRenderer;
import io.github.sooxin.soobo.core.tool.SooboUtil;

/**
 * 文章渲染类
 * 
 * @author Sooxin
 *
 */
public class ArticleRender {
	private MarkdownRenderer markdownRenderer = new MarkdownRenderer();
	private BeetlRenderer beetlRenderer = new BeetlRenderer();
	private WebSiteConfig webSiteConfig = WebSiteConfig.getParsedWebSiteConfig();
	private ArticleDao articleDao = new ArticleDao();
	private FileInput fileInput = new FileInput();
	private FileOutput fileOutput = new FileOutput();

	/**
	 * 将 markdown 文件渲染到模板，存储到数据库，并写出为 html 网页
	 * 
	 * @param templatePath
	 *            模板路径
	 * @param articlePath
	 *            markdown 文章路径
	 */
	public void render(String templatePath, String articlePath) {
		String markdownFile = fileInput.getFileContent(articlePath); // 读取 markdown 文章内容
		String template = fileInput.getFileContent(templatePath); // 读取文章模板

		Article article = markdownRenderer.getParsedResult(markdownFile); // 渲染 markdown 文章
		// 完善 article 对象属性
		String realUrl = webSiteConfig.getUrl();
		if (realUrl.equals("./website")) {
			realUrl = webSiteConfig.getWebPagesStoredPath().replace("\\", "/");
		}
		article.setPermaLink(
				realUrl + "/posts/" + SooboUtil.getParsedDatePath(article.getCreatedDate()).replace("\\", "/") + "/"
						+ article.getArticleLink() + ".html");

		// 新建一个 Map 用于保存将传入到前端模板中的对象
		Map<String, Object> modelsMap = new HashMap<String, Object>();
		modelsMap.put("article", article); // 存入article 对象
		// URI 处理类
		URIProcessor processor = new DefaultURIProcessor();
		modelsMap.put("URI", processor);
		String articlePage = beetlRenderer.render(template, modelsMap); // 根据传入对象进行前端渲染，并返回渲染结果

		// 继续完善 article 对象属性，并存入数据库
		List<ArticleItem> articleItemList = null;
		try {
			// 下面一行测试tets
			articleDao.checkTable();
			articleItemList = articleDao.getLimitArticleItemList(1, 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (articleItemList.size() != 0) {
			article.setPreArticle(articleItemList.get(0));
			int preArticleId = articleItemList.get(0).getId();
			ArticleItem currentArticleItem = ArticleItem.parseArticleToArticleItem(article);
			Article preArticle = null;
			try {
				preArticle = articleDao.selectArticle(preArticleId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preArticle.setNextAticle(currentArticleItem);
			try {
				articleDao.updateArticle(preArticle);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			articleDao.insert(article);
		} catch (SQLException e) {
			System.out.println("数据库插入失败！");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileOutput.saveFile(webSiteConfig.getWebPagesStoredPath() + File.separator + "posts\\"
				+ SooboUtil.getParsedDatePath(article.getCreatedDate()) + File.separator + article.getArticleLink()
				+ ".html", articlePage);
	}
}
