package com.crossballbox.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.crossballbox.dao.UserDAO;
import com.crossballbox.dao.UserInfoDAO;
import com.crossballbox.model.UserInfo;
import com.crossballbox.model.UserProgress;
import com.crossballbox.service.ImageService;
import com.crossballbox.util.ConfigurationUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private ConfigurationUtils configurationUtils;

	@Autowired
	private UserInfoDAO userInfoDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private AdminController adminController;

	@Autowired
	private ImageService imageService;

	@RequestMapping(value = "/upload_image", method = RequestMethod.POST)
	@ResponseBody
	public void uploadUserImage(Model model,
			@RequestParam(value = "uploadfile", required = true) MultipartFile uploadfile,
			@RequestParam(value = "userId", required = true) int userId) throws JSONException {

		// obrisi ako vec postoji
		String fileExtension = uploadfile.getOriginalFilename().split("\\.")[1];
		File dstFile = new File(
				configurationUtils.getFileDownloadPath() + File.separator + userId + "." + fileExtension);
		try {
			if (uploadfile.isEmpty()) {
				logger.error("Empty file path");
			}
			System.out.println("file extension: " + fileExtension);
			if (("jpg").equals(fileExtension) || ("png").equals(fileExtension) || ("gif").equals(fileExtension)) {
				
				File jpgFile;
				if("jpg".equals(fileExtension)){
					jpgFile = convertMultipartFileToFile(uploadfile);
				}else{
					jpgFile = imageService.convertPngToJpg(convertMultipartFileToFile(uploadfile));
				}
				copyFile(jpgFile, dstFile);
				UserInfo userInfo = userInfoDAO.findById(userId);
				if (userInfo == null) {
					userInfo = new UserInfo(userId);
					userInfo.setUser(userDAO.findById(userId));
					userDAO.findById(userId).setUserInfo(userInfo);
					List<UserProgress> list = new ArrayList<UserProgress>();
				} else {
				}
				String dstRelativePath = null;
				if (dstFile.getCanonicalPath().contains(configurationUtils.getFileDownloadPath().toString())) {
					dstRelativePath = dstFile.getCanonicalPath().substring(
							dstFile.getCanonicalPath().lastIndexOf(configurationUtils.getFileDownloadPath().toString())
									+ 1);
				}
				logger.info("dstRelativePath: " + dstFile.getCanonicalPath());
				logger.info("dstRelativePath1: " + dstRelativePath);
				userInfo.setImagePath(dstRelativePath);
				userInfoDAO.save(userInfo);
				final HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.IMAGE_JPEG);

				// delete from folder
			} else {
				logger.error("Bad file format for user profile image!");
			}

		} catch (Exception e) {
			logger.error("Failed to upload image!");
			System.out.println(e.getMessage());
		}
		model.addAttribute("imagePath", "../" + dstFile.getPath());
		model.addAttribute("reload", "true");
		adminController.updateUserInfo(model, Integer.toString(userId));
	}

	private File convertMultipartFileToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists()) {
			//TODO: ispravi, kad nema kreiran fajl!
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}

}
