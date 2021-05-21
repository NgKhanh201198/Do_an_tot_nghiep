package nguyenkhanh.backend;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nguyenkhanh.backend.services.UploadFileService;

@SpringBootApplication
public class BackEndApplication implements CommandLineRunner {
	@Resource
	private UploadFileService uploadFileService;

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.init();
		
		uploadFileService.deleteByName("image-67ab21a9aeda428fa22683d82dd112e4.jpg");
	}

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

}
