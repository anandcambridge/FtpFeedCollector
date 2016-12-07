package com.edi.ftp.feedcollector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edi.ftp.feedcollector.domain.SourceFile;
import com.edi.ftp.feedcollector.repositories.SourceFileRepository;
import com.edi.ftp.feedcollector.repositories.SourceStatusRepository;

@Service
public class LocalFeedCollector implements Runnable {
	private static final String PROCESS1_STARTED = "PROCESS1_STARTED";
	private static SourceFileRepository sourceFileRepository;
	private static SourceStatusRepository sourceStatusRepository;
	List<SourceFile> sourceFileLst = new ArrayList<>();
	Map<String, SourceFile> sourceFileMap;

	@Autowired
	public void setSourceFileRepository(SourceFileRepository sourceFileRepository) {
		LocalFeedCollector.sourceFileRepository = sourceFileRepository;
	}

	@Autowired
	public void setSourceStatusRepository(SourceStatusRepository sourceStatusRepository) {
		LocalFeedCollector.sourceStatusRepository = sourceStatusRepository;
	}
	
	@Override
	public void run() {
//		List<File> filesInFolder = null;
		Map<String, File> filesInFolder = null;
		try {
//			filesInFolder = Files.walk(Paths.get("C:/temp/edi/rawfile/from"))
//			        .filter(Files::isRegularFile)
//			        .map(Path::toFile)
//			        .collect(Collectors.toList());

			filesInFolder = Files.walk(Paths.get("C:/temp/edi/rawfile/from"))
	        .filter(Files::isRegularFile)
	        .map(Path::toFile)
	        .collect(Collectors.toMap(File::getName, Function.identity()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(Arrays.toString(filesInFolder.keySet().toArray()));
		System.out.println("**done");
		try {
			sourceFileLst = sourceFileRepository.findBySupplier("MSTAR");
			sourceFileMap = sourceFileLst.parallelStream().collect(Collectors.toMap(SourceFile::getFileName, Function.identity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		filesInFolder.forEach((k, v) -> {
			processFiles(k);
		});
	}


	private void processFiles(String k) {
		try {
		if(sourceFileMap.get(k) == null) {
			SourceFile sourceFile = new SourceFile();
			sourceFile.setFileName(k);
			sourceFile.setSupplier("MSTAR");
			sourceFile.setSourceStatus(sourceStatusRepository.findByDescription(PROCESS1_STARTED));
			sourceFileRepository.save(sourceFile);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
