package io.github.sooxin.soobo.core.render.renderer;

import java.io.IOException;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

/**
 * 前端渲染引擎 Beetl ，将传入的 model 根据模板渲染成 html 网页文件
 * 
 * @author Sooxin
 *
 */
public class BeetlRenderer {
	/**
	 * 根据传入的 map 集合和模板内容进行渲染，并返回渲染后的内容
	 * 
	 * @param template
	 *            模板文件内容
	 * @param models
	 *            存储了 model 的Map 对象
	 * @return
	 */
	public String render(String template, Map<String, Object> models) {
//		// ############test
//		System.out.println("模板：" + template);

		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		Configuration cfg = null;
		try {
			cfg = Configuration.defaultConfiguration();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		// FileInput 要关闭
		Template t = gt.getTemplate(template);
		t.binding(models);
		return t.render();

	}
}
