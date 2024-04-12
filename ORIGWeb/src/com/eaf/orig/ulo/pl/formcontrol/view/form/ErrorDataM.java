package com.eaf.orig.ulo.pl.formcontrol.view.form;
import java.io.Serializable;

public class ErrorDataM implements Serializable,Cloneable{
	public ErrorDataM(){
		super();
	}
	public ErrorDataM(String id,String message){
		super();
		this.id = id;
		this.message = message;
	}
	private String id;
	private String message;
	
	public String getId(){
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
