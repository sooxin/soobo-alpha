package io.github.sooxin.soobo.resource;

import io.github.sooxin.soobo.cli.model.CLIConfig;
import io.github.sooxin.soobo.core.model.WebSiteConfig;
/**
 * 配置资源类（暂时未使用）
 * @author Sooxin
 *
 */
public class Resource {
	private static WebSiteConfig webSiteConfig; // 网站配置
	private static CLIConfig cliConfig;  // CLI 工具配置
	
	/**
	 * 获取网站配置单例
	 * @return	网站配置单例
	 */
	public WebSiteConfig getWebSiteConfigInstance() {
		if(webSiteConfig==null) {
			webSiteConfig=WebSiteConfig.getParsedWebSiteConfig();
		}
		return webSiteConfig;
	}
	/**
	 * 获取 CLI 配置单例
	 * @return	 CLI 配置单例
	 */
	public CLIConfig getCLIConfigInstance() {
		if(cliConfig==null) {
			CLIConfig.getParsedCLIConfig();
		}
		return cliConfig;
	}

}
