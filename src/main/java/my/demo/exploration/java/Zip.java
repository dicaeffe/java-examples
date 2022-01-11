package my.demo.exploration.java;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Zip {


	private static final String DIR_PROJ = "C:\\ws\\java-examples";
	private static final String DIR_TEST = DIR_PROJ+"\\docs\\test";
	private static final String DIR_TEST_FINAL = DIR_PROJ+"\\docs\\test\\zip";


	
	public static void main(String[] args) {
		log.info("============> main START");
		List<File> files = Arrays.asList(
										new File(filePath(DIR_TEST,"document.pdf")),
										new File(filePath(DIR_TEST,"metadata.json")),
										new File(filePath(DIR_TEST,"test.png")));

		log.info("============> files={}", files);
		
		log.info("============> ------------------------------");
		
		log.info("============> -----zipped------------------------");
		log.info("============> zipToFile(file -> file)");
		zipToFile(DIR_TEST_FINAL+"\\zipped.zip", files);							//OK
		log.info("============> unzipToFile(file -> file)");
		unzipToFile(DIR_TEST_FINAL+"\\zipped.zip", DIR_TEST_FINAL+"\\unzipped");	//OK
		
		
		log.info("============> -----rezipped----------------------");
		log.info("============> zipToByte(file -> byte[])");
		byte[] zipArr = zipToByte(files);
		log.info("============> unzipToFile(byte[] -> file)");
		unzipToFile(zipArr, DIR_TEST_FINAL+"\\unzippedbyte");
		

		
		
		log.info("============> -----FLOW6----------------------------");
		log.info("============> zipToByte(file -> byte[]) [TMP]");
		byte[] zipFile = zipToByte(files);
		log.info("============> unzipToByte(byte[] -> byte[])");
//		List<byte[]> fileList = unzipToByte(zipFile);
		log.info("============> zipToByte(byte[] -> byte[])");
//		byte[] zipFile2 = zipToByte(fileList);
		
		log.info("============> main END");
	}
	
	private static void unzipToFile(byte[] zipArr, String destDirPath) {
		// Use a try-with-resources to instantiate the ZipFile class: this will be used to retrieve a Java Stream
		try (
				ByteArrayInputStream bais = new ByteArrayInputStream(zipArr);
				ZipInputStream zis = new ZipInputStream(bais);
						) {

			ZipEntry zipEntry = zis.getNextEntry();
			byte[] buffer = new byte[1024];
			
			// Use a try-with-resources to instantiate the stream for the buffered read of the input file
			try (
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(destDirPath + File.separator + zipEntry.getName()));
					) {
				
				int bytes = 0;
				while ((bytes = zis.read(buffer)) > 0) {
					bos.write(buffer, 0, bytes);
					bos.flush();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static byte[] zipToByte(List<File> files) {
		// Use a try-with-resources to instantiate the stream for the buffered read of the zip output file
		try (
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream zos = new ZipOutputStream(baos);
				) {

			// Loop over file list
			files.forEach(file -> {
				byte[] buffer = new byte[1024];

				// Use a try-with-resources to instantiate the stream for the buffered read of the input file
				try (
						FileInputStream fis = new FileInputStream(file);
						BufferedInputStream bis = new BufferedInputStream(fis);) {

					ZipEntry zipEntry = new ZipEntry(file.getName());
					zos.putNextEntry(zipEntry);
					int bytes = 0;
					while ((bytes = bis.read(buffer)) > 0) {
						zos.write(buffer, 0, bytes);
						zos.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			return baos.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}
	
	
	/** Tested. */
	private static void unzipToFile(String zipFilePath, String destDirPath) {
		// Use a try-with-resources to instantiate the ZipFile class: this will be used to retrieve a Java Stream
		try (
				ZipFile zipFile = new ZipFile(zipFilePath)) {
			Stream<? extends ZipEntry> stream = zipFile.stream();
			
			stream.forEach(zipEntry -> {

				// Use a try-with-resources to instantiate the stream for the buffered read of the input file
				try (
						InputStream inputStream = zipFile.getInputStream(zipEntry);
						BufferedInputStream bis = new BufferedInputStream(inputStream);
						BufferedOutputStream bos = new BufferedOutputStream(
								new FileOutputStream(destDirPath + File.separator + zipEntry.getName()));) {

					bis.transferTo(bos);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/** Tested. */
	private static void zipToFile(String zipFilePath, byte[] file, String entryName) {
		// Use a try-with-resources to instantiate the stream for the buffered read of the zip output file
		try (
				FileOutputStream fos = new FileOutputStream(zipFilePath);
				ZipOutputStream zos = new ZipOutputStream(fos);
				BufferedOutputStream bos = new BufferedOutputStream(zos);
				) {
			
			// Loop over file list
//			files.forEach(file -> {
//				byte[] buffer = new byte[1024];

				// Use a try-with-resources to instantiate the stream for the buffered read of the input file
				try (
						ByteArrayInputStream bais = new ByteArrayInputStream(file);
//						ZipInputStream zis = new ZipInputStream(bais);
						BufferedInputStream bis = new BufferedInputStream(bais);
						) {
					
					ZipEntry zipEntry = new ZipEntry(entryName);
					zos.putNextEntry(zipEntry);
					bis.transferTo(zos);
//					int bytes = 0;
//					while ((bytes = zis.read(buffer)) > 0) {
//						bos.write(buffer, 0, bytes);
//						bos.flush();
//					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
//			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/** Tested. */
	private static void zipToFile(String zipFilePath, List<File> files) {
		// Use a try-with-resources to instantiate the stream for the buffered read of the zip output file
		try (
				FileOutputStream fos = new FileOutputStream(zipFilePath);
				ZipOutputStream zos = new ZipOutputStream(fos);
				BufferedOutputStream bos = new BufferedOutputStream(zos);
				) {
			
			// Loop over file list
			files.forEach(file -> {
				byte[] buffer = new byte[1024];

				// Use a try-with-resources to instantiate the stream for the buffered read of the input file
				try (
						FileInputStream fis = new FileInputStream(file);
						BufferedInputStream bis = new BufferedInputStream(fis);
						) {
					
					ZipEntry zipEntry = new ZipEntry(file.getName());
					zos.putNextEntry(zipEntry);
					bis.transferTo(zos);
//					int bytes = 0;
//					while ((bytes = zis.read(buffer)) > 0) {
//						bos.write(buffer, 0, bytes);
//						bos.flush();
//					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static String filePath(String targetDir, String fileName) {
		return targetDir+"\\"+fileName;
	}

}