package io.github.sooxin.soobo.core.render;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import io.github.sooxin.soobo.core.dao.ArticleDao;
import io.github.sooxin.soobo.core.io.FileInput;
import io.github.sooxin.soobo.core.io.FileOutput;
import io.github.sooxin.soobo.core.model.Article;
import io.github.sooxin.soobo.core.model.WebSiteConfig;
import io.github.sooxin.soobo.core.render.renderer.BeetlRenderer;
import io.github.sooxin.soobo.core.tool.SooboUtil;

/**
 * 首页渲染类
 * 
 * @author Sooxin
 *
 */
public class IndexRender {
	private ArticleDao articleDao = new ArticleDao();
	private WebSiteConfig webSiteConfig = WebSiteConfig.getParsedWebSiteConfig();

	/**
	 * 将首页渲染到模板并输出为 html 网页文件
	 * 
	 * @param indexTemplatePath
	 *            首页模板路径
	 */
	public void render(String indexTemplatePath) {
		String indexTemplate = new FileInput().getFileContent(indexTemplatePath);
		int articleNum = 0; // 文章总数
		try {
			articleNum = articleDao.getArticleNum();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// URI 处理类
		URIProcessor processor = new DefaultURIProcessor();
		
		int totalPageNum = 0; // 总页数
		if (articleNum % webSiteConfig.getPerIndexPage() == 0) {
			totalPageNum = articleNum / webSiteConfig.getPerIndexPage();
		} else {
			totalPageNum = articleNum / webSiteConfig.getPerIndexPage() + 1;
		}
		SooboUtil.deleteAllFilesInDirectory(new File(webSiteConfig.getWebPagesStoredPath() + File.separator + "home"));
		for (int i = 0; i < totalPageNum; i++) {
			List<Article> list = null;
			HashMap<String, Object> models = new HashMap<String, Object>();

			models.put("URI", processor);

			try {
				list = articleDao.getLimitArticlesList(webSiteConfig.getPerIndexPage(),
						i * webSiteConfig.getPerIndexPage());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			models.put("articlesList", list);
			String siteUrl = webSiteConfig.getUrl();
			if (siteUrl.equals("./website")) {
				siteUrl = webSiteConfig.getWebPagesStoredPath().replace("\\", "/");
			}
			models.put("siteUrl", siteUrl + "/home");
			models.put("currentPageNum", i + 1);
			models.put("totalPageNum", totalPageNum);
			models.put("webSiteConfig", webSiteConfig);
			BeetlRenderer beetlRenderer = new BeetlRenderer();
			String renderedIndexPage = beetlRenderer.render(indexTemplate, models);
			new FileOutput().saveFile(webSiteConfig.getWebPagesStoredPath() + File.separator + "home" + File.separator
					+ "index" + (i + 1) + ".html", renderedIndexPage);
		}
		SooboUtil.copyFile(
				new File(webSiteConfig.getWebPagesStoredPath() + File.separator + "home" + File.separator
						+ "index1.html"),
				new File(webSiteConfig.getWebPagesStoredPath() + File.separator + "index.html"));
	}

}
