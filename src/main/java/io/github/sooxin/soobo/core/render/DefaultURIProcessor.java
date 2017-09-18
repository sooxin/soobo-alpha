package io.github.sooxin.soobo.core.render;

public class DefaultURIProcessor implements URIProcessor {
	private static String rootLink; // 整个网站父目录链接
	private static String indexDirLink; // 首页文件夹目录链接
	private static String archiveDirLink; // 归档文件夹父目录链接
	private static String aboutDirLink; // 关于文件夹目录链接
	private static String tagDirLink; // 标签文件夹父目录链接
	private static String workDirLink;
	private static String cssDirLink;
	private static String jsDirLink;
	private static String imgDirLink;
	
	
	static {
		rootLink="https://sooxin.github.io";
		indexDirLink=rootLink+"/home";
		archiveDirLink=rootLink+"/archives";
		tagDirLink=rootLink+"/tags";
		aboutDirLink=rootLink+"/about";
		workDirLink=rootLink+"/works";
		
		cssDirLink=rootLink+"/assets/css";
		jsDirLink=rootLink+"/assets/js";
		imgDirLink=rootLink+"/assets/img";
	}

	public String getRootLink() {
		return rootLink;
	}

	public String getIndexDirLink() {
		return indexDirLink;
	}

	public String getArchiveDirLink() {
		return archiveDirLink;
	}

	public String getTagDirLink() {
		return tagDirLink;
	}

	public String getAboutDirLink() {
		return aboutDirLink;
	}

	public String getWorkDirLink() {
		return workDirLink;
	}

	public String getCssDirLink() {
		return cssDirLink;
	}

	public String getImgDirLink() {
		return imgDirLink;
	}

	public String getJsDirLink() {
		return jsDirLink;
	}

}
