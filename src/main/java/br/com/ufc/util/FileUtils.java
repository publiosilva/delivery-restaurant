package br.com.ufc.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	public static void saveImage(String path, MultipartFile image) {
		File file = new File(path);

		try {
			org.apache.commons.io.FileUtils.writeByteArrayToFile(file, image.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
