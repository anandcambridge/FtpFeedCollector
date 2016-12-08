package com.edi.ftp.feedcollector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edi.ftp.feedcollector.domain.SourceFile;
import com.edi.ftp.feedcollector.repositories.SourceFileRepository;
import com.edi.ftp.feedcollector.repositories.SourceStatusRepository;

@Component
public class LocalFeedCollector {
	private static final String PROCESS1_STARTED = "PROCESS1_STARTED";
	private SourceFileRepository sourceFileRepository;
	private SourceStatusRepository sourceStatusRepository;
	List<SourceFile> sourceFileLst = new ArrayList<>();
	Map<String, SourceFile> sourceFileMap;
    private static final Logger log = LoggerFactory.getLogger(LocalFeedCollector.class);

	@Autowired
	public void setSourceFileRepository(SourceFileRepository sourceFileRepository) {
		this.sourceFileRepository = sourceFileRepository;
	}

	@Autowired
	public void setSourceStatusRepository(SourceStatusRepository sourceStatusRepository) {
		this.sourceStatusRepository = sourceStatusRepository;
	}
    
    @Scheduled(cron = "${local.feed.schedule.cron}")
	public void run() {
		System.out.println("***run starts" + LocalDateTime.now().toString());
		Map<String, File> filesInFolder = null;
		try {

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
