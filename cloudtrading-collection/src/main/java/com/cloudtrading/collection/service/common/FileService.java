package com.cloudtrading.collection.service.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.cloudtrading.collection.common.AppContext;
import com.google.gson.JsonObject;
import com.huisa.common.exception.Errorcode;
import com.huisa.common.exception.ServiceException;
import com.huisa.common.util.DateCoverd;
import com.huisa.common.util.ImageUtils;

public class FileService {

	public static JsonObject saveFile(byte[] fileBytes, String directory)
			throws ServiceException {
		try {
			String typedir = directory + "/";
			String date = DateCoverd.toString(new Date(), "yyyyMMdd") + "/";
			String newfilename = UUID.randomUUID().toString().replace("-", "");
			String parenturi = typedir + date
					+ newfilename.substring(newfilename.length() - 2) + "/";
			String suffix = ".jpg";
			newfilename = newfilename + suffix;
			// uri
			String fileuri = parenturi + newfilename;
			// 父目录
			String parentpath = AppContext.getFilepath() + parenturi;
			File parent = new File(parentpath);
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// 文件地址
			String filepath = AppContext.getFilepath() + fileuri;
			// 保存文件
			FileUtils.writeByteArrayToFile(new File(filepath), fileBytes);
			JsonObject reponsedata = new JsonObject();
			reponsedata.addProperty("image_uri", fileuri);
			return reponsedata;
		} catch (Exception e) {
			throw new ServiceException(Errorcode.CODE_JAVA, "upload faild!", e);
		}
	}

	// private static final int circleImageSize = 1280;
	// private static final int maxCircleImageScale = 2;
	private static final int previewImageWidth = 300;

	public static JsonObject saveTalkImage(byte[] imageBytes)
			throws ServiceException {
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(
					imageBytes));

			byte[] previewImageBytes = imageBytes;
			int width = image.getWidth();
			int height = image.getHeight();
			if (image.getWidth() > previewImageWidth
					|| image.getHeight() > previewImageWidth) {
				if (image.getWidth() > image.getHeight()) {
					width = previewImageWidth;
					height = previewImageWidth * image.getHeight()
							/ image.getWidth();
				} else {
					height = previewImageWidth;
					width = previewImageWidth * image.getWidth()
							/ image.getHeight();
				}
				previewImageBytes = ImageUtils.scaleImage(imageBytes, width,
						height, Image.SCALE_SMOOTH, null);
			}
			// 确定图片的存储路径
			String typedir = "talk_image/";
			String date = DateCoverd.toString(new Date(), "yyyyMMdd") + "/";
			String filename = UUID.randomUUID().toString().replace("-", "");
			String parenturi = typedir + date
					+ filename.substring(filename.length() - 2) + "/";
			String suffix = ".jpg";
			String previewFilename = filename + "_small" + suffix;
			filename = filename + suffix;
			// 父目录
			String parentpath = AppContext.getFilepath() + parenturi;
			File parent = new File(parentpath);
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// uri
			String fileuri = parenturi + filename;
			String previewFileuri = parenturi + previewFilename;
			// 文件地址
			String filepath = AppContext.getFilepath() + fileuri;
			String previewFilepath = AppContext.getFilepath() + previewFileuri;
			// 保存文件
			FileUtils.writeByteArrayToFile(new File(filepath), imageBytes);
			FileUtils.writeByteArrayToFile(new File(previewFilepath),
					previewImageBytes);
			JsonObject reponsedata = new JsonObject();
			reponsedata.addProperty("image_uri", fileuri);
			reponsedata.addProperty("small_image_uri", previewFileuri);
			return reponsedata;
		} catch (Exception e) {
			throw new ServiceException(Errorcode.CODE_JAVA, "upload faild!", e);
		}
	}
}
