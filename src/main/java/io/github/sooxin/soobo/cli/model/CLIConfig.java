package io.github.sooxin.soobo.cli.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.sooxin.soobo.core.io.FileInput;
import io.github.sooxin.soobo.core.io.FileOutput;

public class CLIConfig {
	private Map<String, String> projects=new HashMap<String, String>(); // 项目名与项目地址
	private String defaultProjectName=""; // 默认项目名
	private String currentProjectName=""; // 当前项目名
	// 单个项目文件默认存储文件夹，如 index.html 都存在 home 中的属性

	public CLIConfig() {
	}
	
	// 好像没有必要存在
	public static CLIConfig getDefaultCLIConfig() {
		CLIConfig cliConfig = new CLIConfig();
		cliConfig.setProjects(new HashMap<String, String>());
		cliConfig.setDefaultProjectName("");
		cliConfig.setCurrentProjectName("");
		return cliConfig;
	}
	
	public static CLIConfig getParsedCLIConfig() {
		System.out.println("> 正在检查 soobo-cli-config.json 配置文件...");
		File file=new File("./soobo-cli-config.json");
		Gson gson=new GsonBuilder().setPrettyPrinting().create();
		if(!file.exists()||!file.isFile()) {
			System.out.println("> 正在初始化 soobo-cli-config.json 配置文件...");
			CLIConfig cliConfig= new CLIConfig();
			new FileOutput().saveFile(file.getAbsolutePath(), gson.toJson(cliConfig));
			System.out.println("> soobo-cli-config.json 初始化结束！");
			System.out.println("> 配置文件检查完毕！");
			return cliConfig;
		}
		return gson.fromJson(new FileInput().getFileContent("soobo-cli-config.json"), CLIConfig.class);
	}
	
	public static void checkCLIConfig() {
		System.out.println("> 正在检查 soobo-cli-config.json 配置文件...");
		File file=new File("./soobo-cli-config.json");
		if(!file.exists()||!file.isFile()) {
			System.out.println("> 正在初始化 soobo-cli-config.json 配置文件...");
			new FileOutput().saveFile(file.getAbsolutePath(), new GsonBuilder().setPrettyPrinting().create().toJson(new CLIConfig()));
			System.out.println("> soobo-cli-config.json 初始化结束！");
		}
		System.out.println("> 配置文件检查完毕！");
	}

	public Map<String, String> getProjects() {
		return projects;
	}

	public void setProjects(Map<String, String> projects) {
		this.projects = projects;
	}

	public String getDefaultProjectName() {
		return defaultProjectName;
	}

	public void setDefaultProjectName(String defaultProjectName) {
		this.defaultProjectName = defaultProjectName;
	}

	public String getCurrentProjectName() {
		return currentProjectName;
	}

	public void setCurrentProjectName(String currentProjectName) {
		this.currentProjectName = currentProjectName;
	}

}
