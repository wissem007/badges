package tn.com.digivoip.comman.exception;

import java.util.EventListener;

public interface IExceptionListener extends EventListener{

	public void exceptionOccured(Exception e);
}
