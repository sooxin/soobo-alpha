package io.github.sooxin.soobo.core.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.sooxin.soobo.core.io.FileInput;
import io.github.sooxin.soobo.core.io.FileOutput;

public class SooboUtil {

	public Date getParsedDatetime(String date) {
		String newDate = date.trim() + " 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 验证输入的日期是否正确
		Date d = null;
		try {
			d = sdf.parse(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	public Date formatStringToDate(String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public String formatDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 获取 markdown 文件中除 YAML front-matter 的正文内容
	 * 
	 * @param mdFile
	 *            有 YAML front-matter 的 markdown 文本
	 * @return markdown 文件的正文，即无 YAML front-matter 内容
	 */
	public static String getMdFileContent(String mdFile) {
		int firstIndex = mdFile.indexOf("---");
		if (firstIndex == -1) {
			return mdFile;
		}
		int secondIndex = mdFile.indexOf("---", firstIndex + 3);
		if (secondIndex == -1) {
			return mdFile;
		}
		return mdFile.substring(secondIndex + 3).trim();
	}

	public static String getParsedDatePath(Date date) {
		DateFormat dateFormat = DateFormat.getDateInstance();
		return dateFormat.format(date).replace("-", File.separator);
	}

	public static void copyFolder(File srcFolder, File destFolder) {
		/* ++异常判断++ */
		// 遍历原文件夹
		for (File f : srcFolder.listFiles()) {
			if (f.isFile()) {
				// System.out.println("copy "+f.getAbsolutePath()+" to
				// "+destFolder.getAbsoluteFile()+File.separator+f.getName());
				SooboUtil.copyFile(f, new File(destFolder.getAbsoluteFile() + File.separator + f.getName()));
				continue;
			}
			File newDestFolder = new File(destFolder.getAbsoluteFile() + File.separator + f.getName());
			// System.out.println("新建文件夹 "+newDestFolder);
			newDestFolder.mkdirs();
			SooboUtil.copyFolder(f, newDestFolder);
		}

	}

	public static void copyFile(File srcFile, File destFile) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 删除指定文件夹下的所有文件和文件夹，但不删除该指定的文件夹
	 * 
	 * @param dir
	 *            指定的文件夹
	 */
	public static void deleteAllFilesInDirectory(File dir) {
		if (!dir.exists()) {
			System.out.println("# " + dir.getAbsolutePath() + "文件夹并不存在，无需删除！");
		}
		if (!dir.isDirectory()) {
			System.out.println("# " + dir.getAbsolutePath() + "不是文件夹！");
			return;
		}
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				SooboUtil.deleteAllFilesInDirectory(file);
			}
			file.delete();
		}
	}

	// private static void traverseFiles(File root) {
	// }
}