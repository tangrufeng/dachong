package com.jcyt.lottery.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogicException extends RuntimeException
{
	  private static final long serialVersionUID = -7986113335124405047L;
	  
	  private static final Logger logger = LoggerFactory.getLogger(LogicException.class);

	  private String code;
	  
	  public LogicException(String message,String code)
	  {
	    super(message);
	  }

	  public synchronized Throwable fillInStackTrace()
	  {
	    return null;
	  }

	  public void printStackTrace()
	  {
	    logger.info(getMessage());
	  }

	  public void printStackTrace(PrintStream s)
	  {
	    logger.info(getMessage());
	  }

	  public void printStackTrace(PrintWriter s)
	  {
	    logger.info(getMessage());
	  }

	  public void setStackTrace(StackTraceElement[] stackTrace)
	  {
	    logger.info(getMessage());
	  }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	  
	  
	}