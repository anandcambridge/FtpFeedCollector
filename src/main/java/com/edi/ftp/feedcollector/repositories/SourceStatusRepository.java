package com.edi.ftp.feedcollector.repositories;


import org.springframework.data.repository.CrudRepository;

import com.edi.ftp.feedcollector.domain.SourceStatus;

public interface SourceStatusRepository extends CrudRepository<SourceStatus, Integer>{
	public SourceStatus findByDescription(String description);
}
