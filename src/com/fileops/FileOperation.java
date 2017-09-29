package com.fileops;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.criptography.AESCripto;

/**
 * 
 * @author Belal Ansari
 *
 */
public class FileOperation {

	File sourceFile;
	int sizeOfFiles = 1024 * 16;// 1kb
	byte[] buffer = new byte[sizeOfFiles];
	String key;
	int partCount = 0;
	File org = new File("C:\\Users\\msys\\Desktop\\Newfolder\\final.txt");

	public FileOperation(String filePath, String key) {
		if (filePath == null || filePath.isEmpty()) {
			throw new RuntimeException("Null or blank value not alllowed ...");
		}
		if (key == null || key.isEmpty()) {
			throw new RuntimeException("Null or blank key not alllowed ...");
		}
		this.key = key;
		this.sourceFile = new File(filePath);
	}

	public void decriptContent(File tempFile) throws Exception {
		StringBuffer fileContent = new StringBuffer();
		try (BufferedReader in = new BufferedReader(new FileReader(tempFile))) {

			AESCripto aes1 = new AESCripto(this.key);
			String line = null;
			while ( (line = in.readLine()) != null) {
					fileContent.append(line);
			}
			File decrpted = new File(tempFile.getParent()+"//decripted.mp4");
			
			Files.write(Paths.get(decrpted.getAbsolutePath()),
					aes1.decript(fileContent.toString().trim()).getBytes());
		}
	}
	/**
	 * 
	 * @param tempFile
	 * @throws Exception
	 */
	public File encriptContent(File tempFile) throws Exception {
		StringBuffer fileContent = new StringBuffer();
		File created = null;
		try (BufferedReader in = new BufferedReader(new FileReader(tempFile))) {
			AESCripto aes1 = new AESCripto(this.key);
			String line;
			while ((line = in.readLine()) != null) {
				fileContent.append(line);
				
			}
			String filePartName = "encriped" + System.currentTimeMillis();
			created = new File(tempFile.getParent()+"\\"+filePartName);
			
			Files.write(Paths.get(created.getAbsolutePath()),
					aes1.encript(fileContent.toString()).getBytes());
		}
		return created;
	}

	public void splitFile() throws Exception {
		partCount = 0;
		try (FileInputStream fis = new FileInputStream(sourceFile);
				BufferedInputStream bis = new BufferedInputStream(fis)) {
			Integer bytesAmount = 1;
			while ((bytesAmount = bis.read(buffer)) > 0) {
				File temp = new File(sourceFile.getParent() + "\\splitted",
						"temp");
				try (FileOutputStream out = new FileOutputStream(temp)) {
					out.write(buffer, 0, bytesAmount);
				}
				encriptContent(temp);
			}
		}
	}

	public void assemble(String sourcePath, String destinationPath)
			throws Exception {
		partCount = 0;
		String fileName = "temp";
		File f = null;
		try {
			org = new File(destinationPath + "\\original.txt");
			while (true) {
				String filePartName2 = String.format("%o3_%s", partCount++,
						fileName);
				System.out.println("Processing file " + filePartName2);
				f = new File(sourcePath + filePartName2);
				try (FileInputStream fis1 = new FileInputStream(f);
						BufferedInputStream bis1 = new BufferedInputStream(fis1)) {
					int bytesAmount = 1;
					File tempFile = new File(destinationPath + "as.txt");
					while ((bytesAmount = bis1.read(buffer)) > 0) {
						try (FileOutputStream out = new FileOutputStream(
								tempFile)) {
							out.write(buffer);
						}
					}
					decriptContent(tempFile);
				}
			}
		} catch (FileNotFoundException io) {
			io.printStackTrace();
			System.exit(0);
		}
	}

	public static void main(String args[]) throws Exception {
		FileOperation obj = new FileOperation(
				"C:\\Users\\msys\\Desktop\\NewFolder\\test.txt",
				"belal@1234343434");
		// obj.splitFile();
		// obj.assemble("C:\\Users\\msys\\Desktop\\NewFolder\\splitted\\",
		// "C:\\Users\\msys\\Desktop\\NewFolder\\appended\\");

		File c = obj.encriptContent(new File(
			"C:\\Users\\msys\\Desktop\\NewFolder\\Golmaal.mp4"));
		obj.decriptContent(c);
	}

}
