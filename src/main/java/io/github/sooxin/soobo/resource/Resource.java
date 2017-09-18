package io.github.sooxin.soobo.resource;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.sooxin.soobo.cli.model.CLIConfig;
import io.github.sooxin.soobo.core.io.FileInput;
import io.github.sooxin.soobo.core.io.FileOutput;

public class Resource {
	private static CLIConfig cliConfig;
	static {
		File file = new File("soobo-cli.json");
		if (!file.exists()) {
			System.out.println("> 正在创建 soobo-cli.json 配置文件...");
			Resource.loadSooboCLIResource();
			System.out.println("> soobo-cli.json 配置文件创建成功！");
		}
		Resource.cliConfig=new Gson().fromJson(new FileInput().getFileContent("soobo-cli.json"), CLIConfig.class);
	}

	public static void loadWebSiteResources() {

	}

	public static void loadProjectResource() {
		
	}

	public static void loadSooboCLIResource() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		new FileOutput().saveFile("soobo-cli.json", gson.toJson(new CLIConfig()));
	}

}
