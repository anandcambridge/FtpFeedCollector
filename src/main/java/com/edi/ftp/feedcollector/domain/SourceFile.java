package com.edi.ftp.feedcollector.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SourceFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String fileName;
    private int status;
    private double fileSize;
    private String fileSource;
    private String supplier;
    private LocalDateTime createDt;
    private LocalDateTime lastModifiedDt;
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileSource() {
		return fileSource;
	}
	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public LocalDateTime getCreateDt() {
		return createDt;
	}
	public void setCreateDt(LocalDateTime createDt) {
		this.createDt = createDt;
	}
	public LocalDateTime getLastModifiedDt() {
		return lastModifiedDt;
	}
	public void setLastModifiedDt(LocalDateTime lastModifiedDt) {
		this.lastModifiedDt = lastModifiedDt;
	}
    
}
