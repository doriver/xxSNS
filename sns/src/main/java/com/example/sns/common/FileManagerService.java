package com.example.sns.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// 123123
public class FileManagerService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String FILE_UPLOAD_PATH = "D:\\devJava\\xxSNS\\app_images/";
//	private final String FILE_UPLOAD_PATH = "/upload/images/";
//	private final String FILE_UPLOAD_PATH = "/home/ec2-user/upload_images/";
	
	// 파일 업로드 
	public String saveFile(Long userId, MultipartFile file) {
		
		// 파일 이름 겹칩을 방지하기 위해서 사용자 별로 디렉토리를 만들어서 저장한다. 올린 시간별로 구분해서 저장
		String directoryName = userId + "_" + System.currentTimeMillis() + "/";
		//   43_21982838382/test.png          1970년 1월 1일이후로 지금 몇초가 지났는지
		
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		// 파일 저장할 디렉토리 생성
		File directory = new File(filePath);
		
		if(directory.mkdir() == false) {
			logger.error("[FileManagerService saveFile] 디렉토리 생성 실패");
			return null;
		}
		
		// 파일 저장
		byte[] bytes;
		try {
			bytes = file.getBytes(); // 파일을 바이트배열로 변경
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes); // byte 배열을 파일로 저장
		} catch (IOException e) {
			logger.error("[FileManagerService saveFile] 파일 생성 실패 ");
			e.printStackTrace();
			return null;
		}
				
		// 파일 접근 가능한 path 리턴  . src="/images/12_124141241/test.png"
		return "/images/" + directoryName + file.getOriginalFilename();
	}
	
	
	// 파일 삭제
	public void removeFile(String filePath) {
		// 삭제할 파일 경로
		// filePath : /images/10_21924214/test.png
		// 실제 파일이 저장된 경로 : D:\\web\\0415\\spring_test\\upload\\marondalgram\\images\\10_21924214\\test.png
		
		
		String realFilePath = FILE_UPLOAD_PATH + filePath.replace("/images/", "");
		
		Path path = Paths.get(realFilePath);
		// 파일지우기( 해당 파일이 있는지확인후 )
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("[FileManagerService removeFile] file delete fail ");
				e.printStackTrace();
			}
		}
		
		// 디렉토리(폴더) 지우고
		//D:\\web\\0415\\spring_test\\upload\\marondalgram\\images\\10_21924214
		path = path.getParent();
		
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("[FileManagerService removeFile] directory delete fail ");
				e.printStackTrace();
			}
		}
	}
}
