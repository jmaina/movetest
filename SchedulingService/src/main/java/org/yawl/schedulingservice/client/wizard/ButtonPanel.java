/*
 * Copyright 2009 Hasan Turksoy.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.yawl.schedulingservice.client.wizard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author hturksoy
 * 
 */
public class ButtonPanel extends HorizontalPanel {

	private static final int DEFAULT_BUTTON_COUNT = 5;
	private static final int CUSTOM_BUTTON_COUNT = 3;

	public enum ButtonType {
		HelpButton(1), BackButton(2), NextButton(4), FinishButton(8), CancelButton(16), CustomButton1(32), CustomButton2(64), CustomButton3(128);

		public int value;
		
		ButtonType(int value)
		{
			this.value = value;
		}
		
		public int value() {
			return value;
		}
	}

	private String[] ButtonTexts = { "N/A", "Help", "Back", "Next", "Finish", "Cancel", 
			"CustomButton1", "CustomButton2", "CustomButton3" };

	// should be in-sync with ButtonType enum
	private enum DefaultButtonInfo {

		HelpButton(ButtonType.HelpButton, "Help"), BackButton(ButtonType.BackButton, "Back"), NextButton(
				ButtonType.NextButton, "Next"), FinishButton(ButtonType.FinishButton, "Finish"), CancelButton(ButtonType.CancelButton, "Cancel");

		private ButtonType type;
		private String text;

		DefaultButtonInfo(ButtonType type, String text) {
			this.type = type;
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public class ButtonLayout {

		private int layout = 0;	// no button

		public ButtonLayout add(ButtonType buttonType) {
			layout |= buttonType.value();
			return this;
		}

		public boolean isButtonInLayout(ButtonType buttonType) {
			int result = layout & buttonType.value();
			return result != 0;
		}
	}

	private ButtonLayout buttonLayout;

	private Button[] buttons;

	/**
	 * 
	 */
	private void init() {
		// create default buttons
		DefaultButtonInfo[] defaultButtonInfos = DefaultButtonInfo.values();
		buttons = new Button[defaultButtonInfos.length];
		for (int buttonCounter = 0; buttonCounter < defaultButtonInfos.length; buttonCounter++) {
			Button btn = new Button(defaultButtonInfos[buttonCounter].text);
			btn.setVisible(false);	// no button visible initially
			this.add(btn);
			buttons[buttonCounter] = btn;
		}
	}

	/**
	 * show only the buttons in current layout - hide others
	 */
	private void applyButtonLayout() {
		for(ButtonType buttonType : ButtonType.values()) {
			showButton(buttonType, buttonLayout.isButtonInLayout(buttonType));
		}
	}

	public ButtonLayout getButtonLayout() {
		return buttonLayout;
	}

	public void setButtonLayout(ButtonLayout buttonLayout) {
		this.buttonLayout = buttonLayout;
		
		applyButtonLayout();
	}

	public ButtonPanel() {
		super();

		init();
	}

	public void enableButton(ButtonType buttonType, boolean enable) {
		if (buttonLayout.isButtonInLayout(buttonType)) {
			buttons[buttonType.ordinal()].setEnabled(enable);
		} else {
			GWT.log("enable button : button layout is not including a button of type : " + buttonType, null);
		}
	}

	public void showButton(ButtonType buttonType, boolean show) {
		if (buttonLayout.isButtonInLayout(buttonType)) {
			buttons[buttonType.ordinal()].setVisible(show);
		} else {
			GWT.log("show button : button layout is not including a button of type : " + buttonType, null);
		}
	}

	/**
	 * @param nextbutton
	 * @param nextButtonHandler
	 */
	public void addButtonClickHandler(ButtonType buttonType, ClickHandler handler) {
		buttons[buttonType.ordinal()].addClickHandler(handler);
	}
}
