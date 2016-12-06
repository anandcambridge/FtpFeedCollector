package com.edi.ftp.feedcollector.repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.edi.ftp.feedcollector.domain.SourceFile;

public interface SourceFileRepository extends CrudRepository<SourceFile, Integer>{
	public List<SourceFile> findBySupplier(String supplier);
}
