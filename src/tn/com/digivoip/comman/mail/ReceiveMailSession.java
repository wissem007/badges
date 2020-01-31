package tn.com.digivoip.comman.mail;

import java.util.Hashtable;
import java.util.Map;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import com.sun.mail.pop3.POP3Folder;

/** Encapsulates email receiving session. Prepares and receives message(s). Some methods do not work on POP3 servers. */
public class ReceiveMailSession{

	protected static final String	DEFAULT_FOLDER	= "INBOX";
	protected final Session			session;
	protected final Store			store;
	static {
		MailSystem.mailSystem.defineJavaMailSystemProperties();
	}
	protected Folder				folder;

	/** Creates new mail session. */
	public ReceiveMailSession(Session session, Store store) {
		this.session = session;
		this.store = store;
	}
	public void close() {
		closeFolderIfOpened();
		try {
			store.close();
		} catch (MessagingException mex) {
			throw new MailException(mex);
		}
	}
	// ---------------------------------------------------------------- folders
	protected void closeFolderIfOpened() {
		if (folder != null) {
			try {
				folder.close(true);
			} catch (MessagingException ignore) {}
		}
	}
	/** Returns list of all folders. You can use these names in {@link #useFolder(String)} method. */
	public String[] getAllFolders() {
		Folder[] folders;
		try {
			folders = store.getDefaultFolder().list("*");
		} catch (MessagingException msex) {
			throw new MailException("Unable to connect to folder", msex);
		}
		String[] folderNames = new String[folders.length];
		for (int i = 0; i < folders.length; i++) {
			Folder folder = folders[i];
			folderNames[i] = folder.getFullName();
		}
		return folderNames;
	}
	/** Returns the number of deleted messages. */
	public int getDeletedMessageCount() {
		if (folder == null || !folder.isOpen()) {
			useDefaultFolder();
		}
		try {
			return folder.getDeletedMessageCount();
		} catch (MessagingException mex) {
			throw new MailException(mex);
		}
	}
	// ---------------------------------------------------------------- message count
	/** Returns number of messages. */
	public int getMessageCount() {
		if (folder == null || !folder.isOpen()) {
			useDefaultFolder();
		}
		try {
			return folder.getMessageCount();
		} catch (MessagingException mex) {
			throw new MailException(mex);
		}
	}
	/** Returns the number of new messages. */
	public int getNewMessageCount() {
		if (folder == null) {
			useDefaultFolder();
		}
		try {
			return folder.getNewMessageCount();
		} catch (MessagingException mex) {
			throw new MailException(mex);
		}
	}
	public Map<String, Integer> getUid() {
		try {
			Map<String, Integer> uids = new Hashtable<String, Integer>();
			if (getMessageCount() != 0) {
				FetchProfile fp = new FetchProfile();
				fp.add(UIDFolder.FetchProfileItem.UID);
				folder.fetch(null, fp);
				Message[] msgs = folder.getMessages();
				for (int i = 0; i < msgs.length; i++) {
					Message message = msgs[i];
					uids.put(((POP3Folder) folder).getUID(message), new Integer(i));
				}
			}
			return uids;
		} catch (MessagingException e) {
			throw new MailException("Unable to fetch messages", e);
		}
	}
	/** Returns the number of unread messages. */
	public int getUnreadMessageCount() {
		if (folder == null) {
			useDefaultFolder();
		}
		try {
			return folder.getUnreadMessageCount();
		} catch (MessagingException mex) {
			throw new MailException(mex);
		}
	}
	// ---------------------------------------------------------------- receive emails
	/** Opens session. */
	public void open() {
		try {
			store.connect();
		} catch (MessagingException msex) {
			throw new MailException("Unable to open session", msex);
		}
	}
	public boolean isOpen() {
		return store.isConnected();
	}
	public ReceivedEmail[] receive(EmailFilter filter, Flags flagsToSet) {
		if (folder == null || !folder.isOpen()) {
			useDefaultFolder();
		}
		Message[] messages;
		try {
			if (filter == null) {
				messages = folder.getMessages();
			} else {
				messages = folder.search(filter.getSearchTerm());
			}
			if (messages.length == 0) { return null; }
			ReceivedEmail[] emails = new ReceivedEmail[messages.length];
			for (int i = 0; i < messages.length; i++) {
				Message msg = messages[i];
				if (flagsToSet != null) {
					msg.setFlags(flagsToSet, true);
				}
				emails[i] = new ReceivedEmail(msg, ((POP3Folder) folder).getUID(msg));
				if (flagsToSet == null && emails[i].isSeen() == false) {
					msg.setFlag(Flags.Flag.SEEN, false);
				}
			}
			return emails;
		} catch (MessagingException msex) {
			throw new MailException("Unable to fetch messages", msex);
		}
	}
	/** Receives all emails. Messages are not modified. However, servers do may set SEEN flag anyway, so we force messages to remain unseen. */
	public ReceivedEmail[] receiveEmail() {
		return receive(null, null);
	}
	/** Receives all emails that matches given {@link EmailFilter filter}. Messages are not modified. However, servers do may set SEEN flag anyway, so we force messages to remain unseen. */
	public ReceivedEmail[] receiveEmail(EmailFilter emailFilter) {
		return receive(emailFilter, null);
	}
	public ReceivedEmail receiveEmail(int index) {
		try {
			if (index > getMessageCount()) { throw new MailException("Unable to fetch messages index=" + index); }
			Message message = folder.getMessages()[index];
			return new ReceivedEmail(message, ((POP3Folder) folder).getUID(message));
		} catch (MessagingException e) {
			throw new MailException("Unable to fetch messages", e);
		}
	}
	public ReceivedEmail[] receiveEmailAndDelete() {
		return receiveEmailAndDelete(null);
	}
	public ReceivedEmail[] receiveEmailAndDelete(EmailFilter emailFilter) {
		Flags flags = new Flags();
		flags.add(Flags.Flag.SEEN);
		flags.add(Flags.Flag.DELETED);
		return receive(emailFilter, flags);
	}
	/** Receives all emails and mark all messages as 'seen' (ie 'read'). */
	public ReceivedEmail[] receiveEmailAndMarkSeen() {
		return receiveEmailAndMarkSeen(null);
	}
	/** Receives all emails that matches given {@link EmailFilter filter} and mark them as 'seen' (ie 'read'). */
	public ReceivedEmail[] receiveEmailAndMarkSeen(EmailFilter emailFilter) {
		Flags flags = new Flags();
		flags.add(Flags.Flag.SEEN);
		return receive(emailFilter, flags);
	}
	/** Opens default folder: INBOX. */
	public void useDefaultFolder() {
		closeFolderIfOpened();
		useFolder(ReceiveMailSession.DEFAULT_FOLDER);
	}
	/** Opens new folder and closes previously opened folder. */
	public void useFolder(String folderName) {
		closeFolderIfOpened();
		try {
			folder = store.getFolder(folderName);
		} catch (MessagingException msex) {
			throw new MailException("Unable to connect to folder: " + folderName, msex);
		}
		try {
			folder.open(Folder.READ_WRITE);
		} catch (MessagingException ignore) {
			try {
				folder.open(Folder.READ_ONLY);
			} catch (MessagingException msex) {
				throw new MailException("Unable to open folder: " + folderName, msex);
			}
		}
	}
}
