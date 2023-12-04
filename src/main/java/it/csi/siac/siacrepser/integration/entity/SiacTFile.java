/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "siac_t_file")
public class SiacTFile extends SiacTEnteBase {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIAC_T_FILE_FILEID_GENERATOR", allocationSize=1, sequenceName="SIAC_T_FILE_FILE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIAC_T_FILE_FILEID_GENERATOR")
	@Column(name="file_id")
	private Integer fileId;

	private byte[] file;

	@Column(name="file_code")
	private String fileCode;

	@Column(name="file_name")
	private String fileName;

	@Column(name="file_note")
	private String fileNote;

	@Column(name="file_size")
	private BigDecimal fileSize;

	@Column(name="file_type")
	private String fileType;

	//bi-directional many-to-one association to SiacDFileTipo
	@ManyToOne
	@JoinColumn(name="file_tipo_id")
	private SiacDFileTipo siacDFileTipo;

	public SiacTFile() {
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public byte[] getFile() {
		return this.file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileCode() {
		return this.fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNote() {
		return this.fileNote;
	}

	public void setFileNote(String fileNote) {
		this.fileNote = fileNote;
	}

	public BigDecimal getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(BigDecimal fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public SiacDFileTipo getSiacDFileTipo() {
		return this.siacDFileTipo;
	}

	public void setSiacDFileTipo(SiacDFileTipo siacDFileTipo) {
		this.siacDFileTipo = siacDFileTipo;
	}

	@Override
	public Integer getUid() {
		return fileId;
	}

	@Override
	public void setUid(Integer uid) {
		this.fileId = uid;
	}

}