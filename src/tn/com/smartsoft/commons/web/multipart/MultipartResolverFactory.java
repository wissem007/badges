package tn.com.smartsoft.commons.web.multipart;

import tn.com.smartsoft.commons.web.multipart.commons.CommonsMultipartResolver;

public class MultipartResolverFactory {
	public static MultipartResolver create() {
		return new CommonsMultipartResolver();
	}
}
