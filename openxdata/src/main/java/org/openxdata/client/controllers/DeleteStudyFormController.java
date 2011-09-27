/*
 *  Licensed to the OpenXdata Foundation (OXDF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The OXDF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, 
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and limitations under the License.
 *
 *  Copyright 2010 http://www.openxdata.org.
 */
package org.openxdata.client.controllers;

import org.openxdata.client.AppMessages;
import org.openxdata.client.RefreshableEvent;
import org.openxdata.client.RefreshablePublisher;
import org.openxdata.client.views.DeleteStudyFormView;
import org.openxdata.server.admin.model.FormDef;
import org.openxdata.server.admin.model.FormDefVersion;
import org.openxdata.server.admin.model.StudyDef;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;

/**
 * @author Angel
 *
 */
public class DeleteStudyFormController extends Controller {
	
	AppMessages appMessages = GWT.create(AppMessages.class);
	
	private DeleteStudyFormView deleteStudyFormView;
	
	public static final EventType DELETESTUDYFORM = new EventType();
	
	public DeleteStudyFormController(){
		super();
		
		registerEventTypes(DELETESTUDYFORM);
	}

	@Override
	public void handleEvent(AppEvent event) {
    	GWT.log("DeleteStudyFormController : handleEvent");
        EventType type = event.getType();
        if (type == DELETESTUDYFORM) {
        	deleteStudyFormView = new DeleteStudyFormView(this);
            forwardToView(deleteStudyFormView, event);
        }
		
	}
	
	public void delete(StudyDef study) {
		// TODO: implement delete
		// FIXME: on success
		RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.DELETE, study));
		deleteStudyFormView.cancel();
	}
	
	public void delete(FormDef form) {
		// TODO: implement delete
		// FIXME: on success
		RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.DELETE, form));
		deleteStudyFormView.cancel();
	}
	
	public void delete(FormDefVersion formVersion) {
		// TODO: implement delete
		// FIXME: on success
		RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.DELETE, formVersion));
		deleteStudyFormView.cancel();
	}
}
