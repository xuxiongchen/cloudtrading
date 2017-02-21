package com.cloudtrading.collection.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
	public static void input(String str, String path) throws IOException {

		File txt = new File(path);
		if (!txt.exists()) {
			txt.createNewFile();
		}
		byte bytes[] = new byte[512];
		bytes = str.getBytes(); // 新加的
		int b = str.length(); // 改
		FileOutputStream fos = new FileOutputStream(txt);
		fos.write(bytes, 0, b);
		fos.close();
	}

	public static  void TextToFile(final String strFilename, final String strBuffer) {
		try {
			// 创建文件对象
			File fileText = new File(strFilename);
			if(!fileText.getParentFile().exists()){
				fileText.getParentFile().mkdirs();
			}
			if (!fileText.exists()) {
				fileText.createNewFile();
			}
			// 向文件写入对象写入信息
			FileWriter fileWriter = new FileWriter(fileText);

			// 写文件
			fileWriter.write(strBuffer);
			// 关闭
			fileWriter.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}
}
