package com.avalant.wm.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Task<T> implements Serializable,Cloneable{
	String taskId;
	String status;
	Object data;
	
}
