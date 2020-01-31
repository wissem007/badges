package tn.com.smartsoft.iap.administration.referentiel.file.beans;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class FileBean extends DataBusinessBean {

	private static final long serialVersionUID = 1L;
	private Long fileId;
	private String name;
	private String contentType;
	private Long sizeData;
	private byte[] binaryData;
	private String titre;
	private boolean isView = false;

	public FileBean() {
		super();
	}

	public FileBean(String contentType, byte[] binaryData, Long sizeData) {
		super();
		this.contentType = contentType;
		this.binaryData = binaryData;
		this.sizeData = sizeData;
	}

	public Serializable getId() {
		return this.getFileId();
	}

	public void setId(Long id) {
		this.setFileId(id);
	}

	public Long getFileId() {
		return this.fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSizeData() {
		return this.sizeData;
	}

	public void setSizeData(Long sizeData) {
		this.sizeData = sizeData;
	}

	public byte[] getBinaryData() {
		return this.binaryData;
	}

	public void setBinaryData(byte[] binaryData) {
		this.binaryData = binaryData;
	}

	public String getTitre() {
		return this.titre;
	}

	public InputStream getInputStream() {
		if (binaryData == null)
			return new ByteArrayInputStream(new byte[] {});
		return new ByteArrayInputStream(binaryData);
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}

	public String getExt() {
		if (name != null && name.lastIndexOf(".") > 0)
			return name.substring(name.lastIndexOf(".") + 1, name.length());
		return this.name;
	}

	public void setExt(String ext) {
	}

	public static FileBean createGetFile(Object bean, String name) {
		FileBean photo = new FileBean();
		photo.setBinaryData((byte[]) BeanObjectUtils.getPropertyValue(bean, name + "Bdata"));
		photo.setContentType((String) BeanObjectUtils.getPropertyValue(bean, name + "Ctype"));
		photo.setSizeData((Long) BeanObjectUtils.getPropertyValue(bean, name + "Sdata"));
		photo.setName("Sélectionnez un fichier");
		return photo;
	}

	public static FileBean createSetFile(Object bean, FileBean file, String name) {
		if (file == null)
			file = new FileBean();
		BeanObjectUtils.setPropertyValue(bean, name + "Bdata", file.getBinaryData());
		BeanObjectUtils.setPropertyValue(bean, name + "Ctype", file.getContentType());
		BeanObjectUtils.setPropertyValue(bean, name + "Sdata", file.getSizeData());
		return file;
	}
}