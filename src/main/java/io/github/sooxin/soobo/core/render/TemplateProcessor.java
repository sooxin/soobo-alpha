package io.github.sooxin.soobo.core.render;

import io.github.sooxin.soobo.core.io.FileInput;

public class TemplateProcessor {
	public void process(String templatePath) {
		String rawTemplate=new FileInput().getFileContent(templatePath);
	}
	
	public void processAll() {}
}
