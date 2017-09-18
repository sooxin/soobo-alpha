package io.github.sooxin.soobo.core.render.renderer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.ext.ins.InsExtension;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.HtmlRenderer;

import io.github.sooxin.soobo.core.model.Article;
import io.github.sooxin.soobo.core.tool.SooboUtil;

public class MarkdownRenderer {
	// 加载 commonmark 扩展
	List<Extension> yamlExtentions = Arrays.asList(YamlFrontMatterExtension.create()); // YAML front-matter扩展
	List<Extension> tablesExtensions = Arrays.asList(TablesExtension.create()); // 表格扩展
	List<Extension> autoLinkExtensions = Arrays.asList(AutolinkExtension.create()); // 自动链接扩展
	List<Extension> headingAnchorExtensions = Arrays.asList(HeadingAnchorExtension.create()); // 标题锚点扩展
	List<Extension> strikeThroughExtensions = Arrays.asList(StrikethroughExtension.create()); // 删除线扩展
	List<Extension> insExtensions = Arrays.asList(InsExtension.create()); // 下划线扩展

	public Node getDocument(String mdContent) {
		// 配置扩展
		org.commonmark.parser.Parser parser = org.commonmark.parser.Parser.builder().extensions(yamlExtentions)
				.extensions(tablesExtensions).extensions(autoLinkExtensions).extensions(headingAnchorExtensions)
				.extensions(strikeThroughExtensions).extensions(insExtensions).build();
		// 开始解析和渲染markdown文本，并生成Article实例
		Node document = parser.parse(mdContent);
		return document;
	}
	
//	public String getRenderedHtmlContent(Node document) {
//		
//		
//	}

	public Article getParsedResult(String str) {
		org.commonmark.parser.Parser parser = org.commonmark.parser.Parser.builder().extensions(yamlExtentions)
				.extensions(tablesExtensions).extensions(autoLinkExtensions).extensions(headingAnchorExtensions)
				.extensions(strikeThroughExtensions).extensions(insExtensions).build();
		
		HtmlRenderer renderer = HtmlRenderer.builder().extensions(yamlExtentions).extensions(tablesExtensions)
				.extensions(autoLinkExtensions).extensions(headingAnchorExtensions).extensions(strikeThroughExtensions)
				.extensions(insExtensions).build();
		// 开始解析和渲染markdown文本，并生成Article实例
		Node document = parser.parse(str);
		Article article=new Article();
		article.setRawContent(str);
		article.setRenderedContent(renderer.render(document)); // 解析文章markdown文本

		YamlFrontMatterVisitor visitor = new YamlFrontMatterVisitor(); // 解析Yaml-front-matter信息
		document.accept(visitor);
		yamlFrontMatterParser(article, visitor.getData());

		return article;
	}

	/**
	 * 根据YAML front-matter信息设置文章article信息
	 * 
	 * @param data
	 *            文章源文件
	 */
	public void yamlFrontMatterParser(Article article, Map<String, List<String>> data) {
		System.out.println("> YAML解析开始...");
		long startTime = System.currentTimeMillis();
		List<String> tempArticleTitle = data.get("article-title");
		List<String> tempAuthorName = data.get("author-name");
		List<String> tempCreatedDate = data.get("created-date");
		List<String> tempUpdatedDate = data.get("updated-date");
		List<String> tempArticleLink = data.get("article-link");
		List<String> tempArticleSummary = data.get("article-summary");
		List<String> tempArticleTags = data.get("article-tags");
		SooboUtil tool = new SooboUtil();
		// 空值处理
		if (tempArticleTitle != null) {
			article.setArticleTitle(tempArticleTitle.get(0).trim());
		} else {
			article.setArticleTitle("untitled");
		}
		if (tempAuthorName != null) {
			article.setAuthorName(tempAuthorName.get(0).trim());
		} else {
			article.setAuthorName("unknown author");
		}
		if (tempCreatedDate != null) {
			article.setCreatedDate(tool.getParsedDatetime(tempCreatedDate.get(0).trim()));
		} else {
			article.setCreatedDate(new Date());
		}
		if (tempUpdatedDate != null) {
			article.setUpdatedDate(tool.getParsedDatetime(tempUpdatedDate.get(0).trim()));
		} else {
			article.setUpdatedDate(new Date());
		}
		if (tempArticleLink != null) {
			article.setArticleLink(tempArticleLink.get(0).trim().replace(" ", "-"));
		} else {
			article.setArticleLink("utitled-article");
		}
		if (tempArticleSummary != null) {
			article.setArticleSummary(tempArticleSummary.get(0).trim());
		} else {
			article.setArticleSummary("there's no summaries!");
		}
		if (tempArticleTags != null) {
			article.setArticleTags(tempArticleTags);
		} else {
			// DO NOTHING...
		}
		long endTime = System.currentTimeMillis();
		System.out.println("> YAML解析结束,耗时" + (endTime - startTime) + "ms");
	}

}
