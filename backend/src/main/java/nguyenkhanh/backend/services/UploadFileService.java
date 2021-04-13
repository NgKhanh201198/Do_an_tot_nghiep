package nguyenkhanh.backend.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import nguyenkhanh.backend.exception.NotFoundException;

@Service
public class UploadFileService {
	private final Path root = Paths.get("uploads/");

	public void save(MultipartFile file, String uuidImage) {
		try {
			Files.copy(file.getInputStream(), this.root.resolve(uuidImage));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root))
					.map(path -> this.root.relativize(path));
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename).normalize();
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new NotFoundException("File not found " + filename);
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	public void init() {
		try {
			if (!(Files.isDirectory(root))) {
				Files.createDirectory(root);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}
}
