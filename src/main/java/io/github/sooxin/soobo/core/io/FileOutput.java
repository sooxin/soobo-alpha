package io.github.sooxin.soobo.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileOutput {
	public void saveFile(String targetPath, String data) {
		// File targetFile = new File(new File(targetPath).getAbsolutePath());
		File targetFile = new File(targetPath);
		// ############################
		// ####留待处理异常，虽然几乎不会出现 #
		// ############################
		if (targetFile.isDirectory()) {
			System.out.println("# 目标文件应该为文件而非目录！");
		}

		if ((targetFile.getParentFile() != null) && (!targetFile.getParentFile().exists())) {
			targetFile.getParentFile().mkdirs();
		}

		FileOutputStream fos = null;
		OutputStreamWriter writer = null;
		try {
			fos = new FileOutputStream(targetFile);
			writer = new OutputStreamWriter(fos, "utf-8");
			writer.write(data);
			writer.flush();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				fos.close();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("> " + targetPath + " 数据写入完毕！");

	}

}
