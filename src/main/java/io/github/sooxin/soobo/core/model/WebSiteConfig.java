package io.github.sooxin.soobo.core.model;

import java.io.File;

import com.google.gson.Gson;

import io.github.sooxin.soobo.cli.model.CLIConfig;
import io.github.sooxin.soobo.core.io.FileInput;

public class WebSiteConfig {
	private String url; // 网站网址（前缀）
	private String title; // 网站标题
	private String subTitle; // 网站副标题
	private String description; // 网站描述
	private String ownerName; // 网站所有人名称
	private int perIndexPage; // 首页每页显示文章数量
	private int perArchivePage; // 归档页每页文章数量
	private String themeName; // 网站使用主题名称
	private String projectRootPath; // 该项目根目录
	private String webPagesStoredPath; // 静态网页存储路径
	
	/**
	 * 默认构造器
	 */
	public WebSiteConfig() {
		this.url = "";
		this.title = "test titcle";
		this.subTitle = "test subTitle";
		this.description = "this is soobo , which is a static webpage generator written in Java!";
		this.ownerName = "soobo";
		this.perIndexPage = 10;
		this.perArchivePage=15;
		this.themeName = "BlackLovesWhite";
		this.projectRootPath="."+File.separator;
		this.webPagesStoredPath="."+File.separator+"website";
		
	}
	/**
	 * 根据网站配置文件的内容生成 WebSiteConfig 实例
	 * @param configContent	网站配置文件内容，配置文件应为 json 格式
	 */
	public WebSiteConfig(String configPath) {
		Gson gson = new Gson();
		WebSiteConfig config = gson.fromJson(new FileInput().getFileContent(configPath), WebSiteConfig.class);
		this.url = config.getUrl();
		this.title = config.getTitle();
		this.subTitle = config.getSubTitle();
		this.description = config.getDescription();
		this.ownerName = config.getOwnerName();
		this.perArchivePage=config.getPerArchivePage();
		this.perIndexPage = config.getPerIndexPage();
		this.themeName = config.getThemeName();
		this.projectRootPath=config.getProjectRootPath();
		this.webPagesStoredPath=config.getWebPagesStoredPath();
	}

	public WebSiteConfig(String url, String ownerName, int perIndexPage) {
		this.url = url;
		this.ownerName = ownerName;
		this.perIndexPage = perIndexPage;
	}
	public WebSiteConfig(String url, String title, String subTitle, String description, String ownerName,
			int perIndexPage, int perArchivePage, String themeName, String projectRootPath, String webPagesStoredPath) {
		super();
		this.url = url;
		this.title = title;
		this.subTitle = subTitle;
		this.description = description;
		this.ownerName = ownerName;
		this.perIndexPage = perIndexPage;
		this.perArchivePage = perArchivePage;
		this.themeName = themeName;
		this.projectRootPath = projectRootPath;
		this.webPagesStoredPath = webPagesStoredPath;
	}
	
	public static WebSiteConfig getParsedWebSiteConfig() {
		CLIConfig cliConfig=CLIConfig.getParsedCLIConfig();
		String webSiteConfigPath=cliConfig.getProjects().get(cliConfig.getCurrentProjectName())+File.separator+"webSiteConfig.json";
		return new Gson().fromJson(new FileInput().getFileContent(webSiteConfigPath), WebSiteConfig.class);
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getPerIndexPage() {
		return perIndexPage;
	}

	public void setPerIndexPage(int perIndexPage) {
		this.perIndexPage = perIndexPage;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public String getWebPagesStoredPath() {
		return webPagesStoredPath;
	}
	public void setWebPagesStoredPath(String webPagesStoredPath) {
		this.webPagesStoredPath = webPagesStoredPath;
	}
	public int getPerArchivePage() {
		return perArchivePage;
	}
	public void setPerArchivePage(int perArchivePage) {
		this.perArchivePage = perArchivePage;
	}
	public String getProjectRootPath() {
		return projectRootPath;
	}
	public void setProjectRootPath(String projectRootPath) {
		this.projectRootPath = projectRootPath;
	}

}
