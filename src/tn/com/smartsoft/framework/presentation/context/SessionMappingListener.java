package tn.com.smartsoft.framework.presentation.context;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import tn.com.smartsoft.commons.ref.ReferenceMap;
import tn.com.smartsoft.commons.ref.ReferenceType;

/**
 * Holds a Map of HttpSessions for sharing between the portlet and servlet. The Map uses weak references to the HttpSession objects to ensure this class does not cause any memory leaks if a session is
 * not removed from the map at the appropriate time.
 */ 
public class SessionMappingListener implements HttpSessionListener {
	 
	private static final Map<String, HttpSession> sessionMap = new ReferenceMap<String, HttpSession>(ReferenceType.STRONG, ReferenceType.WEAK);
	
	/**
	 * Gets the session with the specified ID.
	 */
	public static HttpSession getSession(final String sid) {
		return sessionMap.get(sid);
	}
	
	public static Iterator<String> keySession() {
		return sessionMap.keySet().iterator();
	}
	
	public static boolean containsSession(Object key) {
		return sessionMap.containsKey(key);
	}
	
	/**
	 * Stores a session.
	 */
	public static void setSession(final HttpSession session) {
		sessionMap.put(session.getId(), session);
		
	}
	
	/**
	 * Removes a session with the specified ID.
	 */
	public static HttpSession removeSession(final String sid) {
		return sessionMap.remove(sid);
	}
	
	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		setSession(event.getSession());
	}
	
	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		removeSession(event.getSession().getId());
	}
}
