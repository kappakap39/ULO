package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.ArrayList;
import com.eaf.orig.shared.constant.OrigConstant;

public class ButtonObj {
	
	private String role;
	private String jobState;
	private String mode;

	
	private String buttonName;
	private String buttonValue;
	private String buttonMode;
	private String buttonStyle;
	private String buttonJscript;
	
	
	private ButtonObj button1;
	private ButtonObj button2;
	private ButtonObj button3;
	
	private static String SubmitJscript="onclick=\"javascript:validateSubmitApp()\" ";
	private static String SaveJscript="onclick=\"javascript:validateSaveApp()\" ";
	private static String CancelJscript="onclick=\"javascript:cancelButton()\" ";
	
	public ButtonObj(String role, String jobState, String mode, String buttonStyle){
		this.role = role;
		this.jobState = jobState;
		this.mode = mode;
		this.buttonStyle = buttonStyle;

		button1 = new ButtonObj(this.role,this.jobState,this.mode,this.buttonStyle);
		button2 = new ButtonObj(this.role,this.jobState,this.mode,this.buttonStyle);
		button3 = new ButtonObj(this.role,this.jobState,this.mode,this.buttonStyle);
	}
	
	
	public ArrayList<ButtonObj> getButton(){
		ArrayList<ButtonObj> aButton = new ArrayList<ButtonObj>();
		
		if(!role.equalsIgnoreCase(OrigConstant.ROLE_PO)&&!role.equalsIgnoreCase(OrigConstant.ROLE_DF)&&!jobState.equals(OrigConstant.roleJobState.DF_REJECT)){
			button1.setButtonName("Submit");
			button1.setButtonMode(mode);
			button1.setButtonValue("Submit");
			button1.setButtonStyle(buttonStyle);
			button1.setButtonJscript(SubmitJscript);
			aButton.add(button1);
			
			button2.setButtonName("Save");
			button2.setButtonMode(mode);
			button2.setButtonValue("Save");
			button2.setButtonStyle(buttonStyle);
			button2.setButtonJscript(SaveJscript);
			aButton.add(button2);
			
			button3.setButtonName("Cancel");
			button3.setButtonMode(mode);
			button3.setButtonValue("Cancel");
			button3.setButtonStyle(buttonStyle);
			button3.setButtonJscript(CancelJscript);
			aButton.add(button3);

		}else{
			if(role.equalsIgnoreCase(OrigConstant.ROLE_PO)||role.equalsIgnoreCase(OrigConstant.ROLE_DF)&&!jobState.equals(OrigConstant.roleJobState.DF_REJECT)){
				button1.setButtonName("Submit");
				button1.setButtonMode(mode);
				button1.setButtonValue("Finish");
				button1.setButtonStyle(buttonStyle);
				button1.setButtonJscript(SubmitJscript);
				aButton.add(button1);
				
				button3.setButtonName("Cancel");
				button3.setButtonMode(mode);
				button3.setButtonValue("Cancel");
				button3.setButtonStyle(buttonStyle);
				button3.setButtonJscript(CancelJscript);
				aButton.add(button3);
			}else if(role.equalsIgnoreCase(OrigConstant.ROLE_DF)&&jobState.equals(OrigConstant.roleJobState.DF_REJECT)){

				button1.setButtonName("Save");
				button1.setButtonMode(mode);
				button1.setButtonValue("Save");
				button1.setButtonStyle(buttonStyle);
				button1.setButtonJscript(SaveJscript);
				aButton.add(button1);
				
				button2.setButtonName("Submit");
				button2.setButtonMode(mode);
				button2.setButtonValue("Submit");
				button2.setButtonStyle(buttonStyle);
				button2.setButtonJscript(SubmitJscript);
				aButton.add(button2);
				
				button3.setButtonName("Cancel");
				button3.setButtonMode(mode);
				button3.setButtonValue("Cancel");
				button3.setButtonStyle(buttonStyle);
				button3.setButtonJscript(CancelJscript);
				aButton.add(button3);
			}
			
		}
		return aButton;
	}


	public String getButtonName() {
		return buttonName;
	}


	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}


	public String getButtonValue() {
		return buttonValue;
	}


	public void setButtonValue(String buttonValue) {
		this.buttonValue = buttonValue;
	}


	public String getButtonMode() {
		return buttonMode;
	}


	public void setButtonMode(String buttonMode) {
		this.buttonMode = buttonMode;
	}


	public String getButtonStyle() {
		return buttonStyle;
	}


	public void setButtonStyle(String buttonStyle) {
		this.buttonStyle = buttonStyle;
	}


	public String getButtonJscript() {
		return buttonJscript;
	}


	public void setButtonJscript(String buttonJscript) {
		this.buttonJscript = buttonJscript;
	}

}
