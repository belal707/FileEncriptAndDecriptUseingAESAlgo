package com.block;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.criptography.AESCripto;

class FileSplit {
	/**
	 * 
	 * @param sourcePath
	 * @param destinationPath
	 * @param baseFileName
	 * @param newFileName
	 * @throws IOException
	 */
	public void appendFile(String sourcePath, String destinationPath,
			String baseFileName, String newFileName) throws IOException {
		int partCount = 0;
		if (newFileName == null) {
			newFileName = "default.txt";
		}
		File newFile = new File(destinationPath, newFileName);
		String fileName = baseFileName;
		int sizeOfFiles = 1024 * 1024;// 10MB
		byte[] buffer = new byte[sizeOfFiles];
		File f = null;
		while (true) {
			String filePartName2 = String.format("%o3_%s", partCount++,
					fileName);
			f = new File(sourcePath + filePartName2);
			try (FileInputStream fis = new FileInputStream(f);
					BufferedInputStream bis = new BufferedInputStream(fis)) {
				int bytesAmount = 1;
				while ((bytesAmount = bis.read(buffer)) > 0) {
					try (FileOutputStream out = new FileOutputStream(newFile,
							true)) {
						out.write(buffer);
					}
				}
			} catch (FileNotFoundException io) {
				io.printStackTrace();
				System.exit(0);
			}

		}
	}
	/**
	 * 
	 * @param f
	 */
	public void splitFile(File f) {
		try {
			int partCount = 0;
			int sizeOfFiles = 1024 * 1024 * 1;// 1MB
			byte[] buffer = new byte[sizeOfFiles];
			String fileName = "dump";
			// try-with-resources to ensure closing stream
			try (FileInputStream fis = new FileInputStream(f);
					BufferedInputStream bis = new BufferedInputStream(fis)) {
				Integer bytesAmount = 1;
				while ((bytesAmount = bis.read(buffer)) > 0) {
					// write each chunk of data into separate file with
					// different number in name
					String filePartName = String.format("%o3_%s", partCount++,
							fileName);
					File newFile = new File(f.getParent() + "\\splitted",
							filePartName);
					
					AESCripto aes1 = new AESCripto("belal@1234343434");
					while(bis.available() !=0)
					{
						DataInputStream  dis = new DataInputStream(bis);
						System.out.println(dis.readLine());
					}
					try (FileOutputStream out = new FileOutputStream(newFile)) {
						
						out.write(buffer, 0, bytesAmount);
					}
					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}

public class FileSplitMain {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		FileSplit split = new FileSplit();
		split.splitFile((new File("C:\\Users\\msys\\Desktop\\NewFolder\\test.txt")));
		//split.appendFile("C:\\Users\\msys\\Desktop\\NewFolder\\splitted\\", "C:\\Users\\msys\\Desktop\\NewFolder\\appended\\", "dump", "belal3.txt");
	}

}
