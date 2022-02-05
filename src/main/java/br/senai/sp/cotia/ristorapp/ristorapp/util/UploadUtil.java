package br.senai.sp.cotia.ristorapp.ristorapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class UploadUtil {
	private String caminhoChave;
	private Credentials credentials;
	private Storage storage;
	private final String BUCKET_NAME = "ristoraapp.appspot.com";
	private final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME
			+ "/o/%s?alt=media";

	public UploadUtil() {
		
		try {
			caminhoChave = getClass().getResource("/chavefirebase.json").getPath();
			credentials = GoogleCredentials.fromStream(new FileInputStream(caminhoChave));
			storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String uploadFile(MultipartFile arquivo) throws IOException {
		// gera uma String aleatoria para o nome do arquivo
		String nomeArquivo = UUID.randomUUID().toString().concat(getExtension(arquivo.getOriginalFilename()));
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		storage.create(blobInfo, arquivo.getBytes());
		return String.format(DOWNLOAD_URL, URLEncoder.encode(nomeArquivo, StandardCharsets.UTF_8));
	}

	public String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
}
