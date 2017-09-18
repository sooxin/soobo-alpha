package io.github.sooxin.soobo.cli.entry;

import java.io.File;
import java.io.StringReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import io.github.sooxin.soobo.cli.model.CLIConfig;
import io.github.sooxin.soobo.cli.model.WebSite;
import io.github.sooxin.soobo.core.dao.ArticleDao;
import io.github.sooxin.soobo.core.io.FileInput;
import io.github.sooxin.soobo.core.io.FileOutput;
import io.github.sooxin.soobo.core.model.WebSiteConfig;
import io.github.sooxin.soobo.core.render.ArchiveRender;
import io.github.sooxin.soobo.core.render.ArticleRender;
import io.github.sooxin.soobo.core.render.IndexRender;
import io.github.sooxin.soobo.core.tool.SooboUtil;

public class CLI {
	private FileInput input = new FileInput();
	private FileOutput output = new FileOutput();
	private ArticleRender articleRender = new ArticleRender();
	private IndexRender indexRender = new IndexRender();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	// 只编译一篇文章
	public void generate(String mdFilePath) {
		System.out.println("> 正在准备编译文章...");
		long startTime = System.currentTimeMillis();

		WebSiteConfig webSiteConfig = WebSiteConfig.getParsedWebSiteConfig();
		String templatePath = webSiteConfig.getProjectRootPath() + File.separator + "template" + File.separator
				+ webSiteConfig.getThemeName() + File.separator + "article.html";
		articleRender.render(templatePath, mdFilePath);

		long endTime = System.currentTimeMillis();
		System.out.println("> 文章编译结束,耗时" + (endTime - startTime) + "ms");
	}

	// 编译一篇文章，并生成首页，归档页等
	public void generateAll(String mdFilePath) {
		System.out.println("> 正在准备编译文章...");
		long startTime = System.currentTimeMillis();

		generate(mdFilePath);
		generateIndex();
		generateArchive();

		long endTime = System.currentTimeMillis();
		System.out.println("> 文章编译结束,耗时" + (endTime - startTime) + "ms");
	}

	public void generateIndex() {
		WebSiteConfig webSiteConfig = WebSiteConfig.getParsedWebSiteConfig();
		indexRender.render(webSiteConfig.getProjectRootPath() + File.separator + "template" + File.separator
				+ webSiteConfig.getThemeName() + File.separator + "index.html");
	}

	// 初始化一个文件夹为项目文件夹
	/* + 差错检测，出现重名的项目应当提示 + */
	public void init(String dirPath) {
		System.out.println("> 正在初始化...");
		long startTime = System.currentTimeMillis();

		File projectRoot = new File(dirPath);
		if (!projectRoot.exists()) {
			projectRoot.mkdir();
		}
		if (!projectRoot.isDirectory()) {
			System.out.println("> " + dirPath + "不是一个文件夹！请检查！\n程序已退出！");
			System.exit(0);
		}
		File srcFolder = new File("RESOURCE\\projectResouce");
		SooboUtil.copyFolder(srcFolder, projectRoot);

		System.out.println("> 正在添加项目到配置文件 soobo-cli-config.json...");
		// 读取和初始化 soobo 配置文件
		CLIConfig cliConfig = CLIConfig.getParsedCLIConfig();
		cliConfig.getProjects().put(projectRoot.getName(), projectRoot.getAbsolutePath());
		output.saveFile("soobo-cli-config.json", gson.toJson(cliConfig));
		// 读取和初始化 webSiteConfig 配置文件
		JsonReader jsonReader = new JsonReader(new StringReader(
				input.getFileContent(projectRoot.getAbsolutePath() + File.separator + "webSiteConfig.json")));
		jsonReader.setLenient(true);
		WebSiteConfig webSiteConfig = gson.fromJson(jsonReader, WebSiteConfig.class);
		webSiteConfig.setWebPagesStoredPath(projectRoot.getAbsolutePath() + File.separator + "website");
		webSiteConfig.setProjectRootPath(projectRoot.getAbsolutePath());
		output.saveFile(projectRoot.getAbsolutePath() + File.separator + "webSiteConfig.json",
				gson.toJson(webSiteConfig));
		System.out.println("> 项目 " + projectRoot.getName() + " 添加完毕!");

		long endTime = System.currentTimeMillis();
		System.out.println("> 初始化结束,耗时" + (endTime - startTime) + "ms");

	}

	public void use(String projectName) {
		CLIConfig cliConfig = CLIConfig.getParsedCLIConfig();
		String projectPath = cliConfig.getProjects().get(projectName);
		if (projectPath == null) {
			System.out.println("# 该项目不存在，请检查项目名称！");
			System.exit(0);
		}
		cliConfig.setCurrentProjectName(projectName);
		output.saveFile("soobo-cli-config.json", gson.toJson(cliConfig));
		System.out.println("> 已切换为项目" + projectName + ",项目路径为：" + projectPath);
	}

	public void generateArchive() {
		ArchiveRender archiveRender = new ArchiveRender();
		WebSiteConfig webSiteConfig = WebSiteConfig.getParsedWebSiteConfig();
		archiveRender.render(webSiteConfig.getProjectRootPath() + File.separator + "template" + File.separator
				+ "BeeBlackLovesWhite" + File.separator + "archive.html");
	}

	public void test() {

	}

	public static void main(String[] args) throws ParseException {
		CLI cli = new CLI();

		Options options = new Options();
		options.addOption("gen", "generate", true, "仅编译一篇文章");
		options.addOption("ga", "generateAll", true, "编译一篇文章并生成相应的归档、首页等网页文件");
		options.addOption("init", true, "新建一个项目，指定的目录为项目根目录（目录名为项目名）");
		options.addOption("use", true, "使用某个项目");
		options.addOption("index", false, "生成首页");
		options.addOption("arc", "archive", false, "生成归档页面");
		options.addOption("test", true, "test1");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		if (cmd.hasOption("gen")) {
			cli.generate(cmd.getOptionValue("gen"));
		} else if (cmd.hasOption("ga")) {
			cli.generateAll(cmd.getOptionValue("ga"));
		} else if (cmd.hasOption("init")) {
			cli.init(cmd.getOptionValue("init"));
		} else if (cmd.hasOption("use")) {
			cli.use(cmd.getOptionValue("use"));
		} else if (cmd.hasOption("index")) {
			cli.generateIndex();
		} else if (cmd.hasOption("arc")) {
			cli.generateArchive();
		} else if (cmd.hasOption("test")) {
			cli.generateArchive();
		} else {
			System.out.println("others");
		}

	}

}
