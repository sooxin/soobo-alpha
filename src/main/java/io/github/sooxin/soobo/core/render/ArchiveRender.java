package io.github.sooxin.soobo.core.render;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import io.github.sooxin.soobo.core.dao.ArticleDao;
import io.github.sooxin.soobo.core.io.FileInput;
import io.github.sooxin.soobo.core.io.FileOutput;
import io.github.sooxin.soobo.core.model.ArticleItem;
import io.github.sooxin.soobo.core.model.WebSiteConfig;
import io.github.sooxin.soobo.core.processor.DefaultURIProcessor;
import io.github.sooxin.soobo.core.processor.URIProcessor;
import io.github.sooxin.soobo.core.render.renderer.BeetlRenderer;
import io.github.sooxin.soobo.core.tool.SooboUtil;

public class ArchiveRender {
	private WebSiteConfig webSiteConfig = WebSiteConfig.getParsedWebSiteConfig();

	public void render(String templatePath) {
		int perPageNum = webSiteConfig.getPerArchivePage();
		String archiveTemplate = new FileInput().getFileContent(templatePath);

		ArticleDao articleDao = new ArticleDao();
		int articleNum = 0; // 文章总数
		try {
			articleNum = articleDao.getArticleNum();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int totalPageNum = 0; // 总页数
		if (articleNum % perPageNum == 0) {
			totalPageNum = articleNum / perPageNum;
		} else {
			totalPageNum = articleNum / perPageNum + 1;
		}
		// URI 处理类
		URIProcessor processor = new DefaultURIProcessor();
		WebSiteConfig webSiteConfig = WebSiteConfig.getParsedWebSiteConfig();
		SooboUtil.deleteAllFilesInDirectory(
				new File(webSiteConfig.getWebPagesStoredPath() + File.separator + "archives"));
		for (int i = 0; i < totalPageNum; i++) {
			List<ArticleItem> list = null;
			HashMap<String, Object> models = new HashMap<String, Object>();

			try {
				list = articleDao.getLimitArticleItemListOrderByCreatedDate(perPageNum, i * perPageNum);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			models.put("articleItemList", list);

			models.put("URI", processor);

			String siteUrl = webSiteConfig.getUrl();
			if (siteUrl.equals("./website")) {
				siteUrl = webSiteConfig.getWebPagesStoredPath().replace("\\", "/");
			}
			models.put("siteUrl", siteUrl + "/archives");
			models.put("currentPageNum", i + 1);
			models.put("totalPageNum", totalPageNum);
			models.put("webSiteConfig", webSiteConfig);
			BeetlRenderer beetlRenderer = new BeetlRenderer();
			String renderedIndexPage = beetlRenderer.render(archiveTemplate, models);
			new FileOutput().saveFile(webSiteConfig.getWebPagesStoredPath() + File.separator + "archives"
					+ File.separator + "archive" + (i + 1) + ".html", renderedIndexPage);
		}

	}
}
