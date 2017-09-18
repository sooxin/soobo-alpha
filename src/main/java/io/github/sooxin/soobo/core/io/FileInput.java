package io.github.sooxin.soobo.core.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileInput {

	public String getFileContent(String sourcePath) {
		File sourceFile = new File(sourcePath);
		if (!sourceFile.exists()) {
			System.out.println("# 源地址 "+sourcePath+" 不存在！");
			return "";
		}
		if (sourceFile.isDirectory()) {
			System.out.println("# 源地址不应该是一个文件夹而该是一个确切的文件");
			return "";
		}

		// 读取文件文本
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("# 未能找到指定文件！");
			e.printStackTrace();
		}
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "utf8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("# 编码出错，请重试！");
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer stringBuffer = new StringBuffer();
		String str = "";
		try {
			while ((str = bufferedReader.readLine()) != null) {
				stringBuffer.append(str).append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("# 读取指定文件出错！");
			e.printStackTrace();
		}
		try {
			inputStreamReader.close();
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stringBuffer.toString();
	}

}
