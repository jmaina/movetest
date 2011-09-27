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

import java.util.HashMap;
import java.util.Map;

/**
 * @author hturksoy
 * 
 */
class WizardPageImpl {

	private Wizard wizard;
	private final WizardPage page;
	private Map<String, WizardField> fields;
	private boolean explicitlyFinal;
	private boolean completeState;

	WizardPageImpl(final WizardPage wizardPage) {
		super();
		
		page = wizardPage;
		explicitlyFinal = false;
		completeState = false;
		fields = new HashMap<String, WizardField>();
	}

	Map<String, WizardField> getFields() {
		return fields;
	}

	Wizard getWizard() {
		return wizard;
	}

	/**
	 * @param wizard
	 */
	void setWizard(Wizard wizard) {
		this.wizard = wizard;
	}

	boolean isExplicitlyFinal() {
		return explicitlyFinal;
	}

	void setExplicitlyFinal(boolean explicitlyFinal) {
		this.explicitlyFinal = explicitlyFinal;
	}

	void checkCompleteChanged() {
		boolean newState = page.isComplete();
		if (newState != completeState) {
			completeState = newState;
			onCompleteChanged();
		}
	}

	void onCompleteChanged()
	{
		wizard.updateButtonStates();
	}

	boolean isFinalPage() {
		if (isExplicitlyFinal())
			return true;
		
		int nextId = WizardConstants.INVALID_PAGE_ID;
		if (wizard.getCurrentPage() == page)
			nextId = getWizard().nextId();
		else
			nextId = page.nextId();

		return nextId == WizardConstants.INVALID_PAGE_ID;
	}
}
