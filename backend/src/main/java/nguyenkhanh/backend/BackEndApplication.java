package nguyenkhanh.backend;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nguyenkhanh.backend.services.UploadFileService;
import nguyenkhanh.backend.services.impl.UserServiceImpl;

@SpringBootApplication
public class BackEndApplication implements CommandLineRunner {
	@Resource
	private UploadFileService uploadFileService;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.init();
	}

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

}
