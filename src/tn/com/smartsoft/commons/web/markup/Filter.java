package tn.com.smartsoft.commons.web.markup;

public interface Filter {
	public Filter addAttribute(String name, Object attribute);

	public Filter removeAttribute(String name);

	public boolean hasAttribute(String key);

	public String process(String to_process);

	public String getInfo();
}
