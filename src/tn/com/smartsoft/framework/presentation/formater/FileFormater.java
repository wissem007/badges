package tn.com.smartsoft.framework.presentation.formater;

import java.io.IOException;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.web.multipart.MultipartFile;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

public class FileFormater extends Formater {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getAsString(Object target) {
		if (target == null)
			return "";
		FileBean fileBean = (FileBean) target;
		return fileBean.getName();
	}

	public Object getAsObject(Object target, Class<?> targetClass) {
		if (target == null)
			return "";
		MultipartFile multipartFile = (MultipartFile) target;
		FileBean fileBean = new FileBean();
		try {
			fileBean.setBinaryData(multipartFile.getBytes());
			fileBean.setContentType(multipartFile.getContentType());
			fileBean.setName(multipartFile.getOriginalFilename());
			fileBean.setSizeData(multipartFile.getSize());
			fileBean.setTitre(multipartFile.getName());
			return fileBean;
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	public boolean validate(Object target, Class<?> targetClass) {
		return target instanceof MultipartFile;
	}
}
