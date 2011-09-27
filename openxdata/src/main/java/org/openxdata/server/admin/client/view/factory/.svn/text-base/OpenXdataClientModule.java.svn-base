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
package org.openxdata.server.admin.client.view.factory;

import com.google.inject.Singleton;
import org.openxdata.server.admin.client.view.TaskView;
import org.openxdata.server.admin.client.view.bar.OpenXDataNotificationBar;
import org.openxdata.server.admin.client.view.treeview.TasksTreeView;
import org.openxdata.server.admin.client.view.widget.factory.OpenXDataWidgetFactory;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import org.openxdata.server.admin.client.view.ParametersView;

/**
 * Binds the classes and providers using a Guice module.
 * 
 * @author Angel
 *
 */
public class OpenXdataClientModule extends AbstractGinModule {

	/* (non-Javadoc)
	 * @see com.google.gwt.inject.client.AbstractGinModule#configure()
	 */
	@Override
	protected void configure() {
		
		// Bindings
		bind(TaskView.class).in(Singleton.class);

		bind(TasksTreeView.class).in(Singleton.class);
		
		bind(OpenXDataNotificationBar.class).in(Singleton.class);
		
		bind(OpenXDataWidgetFactory.class).to(OpenXDataViewFactory.class).in(Singleton.class);

                bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

                bind(ParametersView.class).in(Singleton.class);

	}
}
