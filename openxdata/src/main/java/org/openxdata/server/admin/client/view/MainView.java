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
package org.openxdata.server.admin.client.view;

import org.openxdata.server.admin.client.Context;
import org.openxdata.server.admin.client.controller.facade.MainViewControllerFacade;
import org.openxdata.server.admin.client.listeners.AppEventListener;
import org.openxdata.server.admin.client.listeners.LogoutListener;
import org.openxdata.server.admin.client.locale.OpenXdataText;
import org.openxdata.server.admin.client.locale.TextConstants;
import org.openxdata.server.admin.client.permissions.util.RolesListUtil;
import org.openxdata.server.admin.client.tools.MobileInstaller;
import org.openxdata.server.admin.client.util.Utilities;
import org.openxdata.server.admin.client.view.images.OpenXDataImages;
import org.openxdata.server.admin.client.view.widget.OpenXDataStackPanel;
import org.openxdata.server.admin.client.view.widget.factory.OpenXDataWidgetFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;

/**
 * Application Main View.
 * <p>
 * This view deals with setting up of the various contained views, switching
 * between them, and calling methods onto them.
 * </p>
 * 
 * @author daniel
 * @author Angel
 * 
 */
public class MainView extends Composite implements ResizeHandler,
        AppEventListener {
	
	/** Logout listener for this sessions */
	private LogoutListener logoutListener;
	
	/** OpenXdata Stack Panel to hold Tree Views. */
	private OpenXDataStackPanel openXdataStackPanel;
	
	/** Handle to <tt>Widget Factory.</tt> */
	private OpenXDataWidgetFactory widgetFactory;
	
	/**
	 * Constructs an instance of this class with a <code>Logout Listener</code>
	 * 
	 * @param logoutListener
	 *            <code>Logout Listener for this class</code>.
	 */
	public MainView(LogoutListener logoutListener) {
		this.logoutListener = logoutListener;
	}
	
	/**
	 * Initializes the <tt>MainView widgets and layout.</tt>
	 */
	public void initializeMainViewWidgets() {
		setUp();
		setupViewController();
	}
	
	/**
	 * Initializes the widgets to be bound to the main view with Permission
	 * Resolver Object to handle permissions.
	 */
	private void setUp() {
		
		// Add the Views to the Main Panel
		bindViewsToPanel();
		
		Window.addWindowClosingHandler(new ClosingHandler() {
			
			@Override
			public void onWindowClosing(ClosingEvent event) {
				onLogout();
				
			}
		});
	}
	
	/**
	 * Binds tree views to the panel
	 */
	private void bindViewsToPanel() {
		OpenXDataImages images = GWT.create(OpenXDataImages.class);
		
		// Stack Panel to organize MainView
		openXdataStackPanel = widgetFactory.getOpenXdataStackPanel();
		
		openXdataStackPanel.add(
		        widgetFactory.getStudiesTreeView(),
		        Utilities.createHeaderHTML(images.studies(),
		                OpenXdataText.get(TextConstants.STUDIES)), true);
		
		openXdataStackPanel.add(
		        widgetFactory.getUsersTreeView(),
		        Utilities.createHeaderHTML(images.users(),
		                OpenXdataText.get(TextConstants.USERS)), true);
		
		openXdataStackPanel.add(
		        widgetFactory.getRolesTreeView(),
		        Utilities.createHeaderHTML(images.roles(),
		                OpenXdataText.get(TextConstants.ROLES)), true);
		
		openXdataStackPanel.add(
		        widgetFactory.getTasksTreeView(),
		        Utilities.createHeaderHTML(images.tasks(),
		                OpenXdataText.get(TextConstants.TASKS)), true);
		
		openXdataStackPanel.add(
		        widgetFactory.getSettingsTreeView(),
		        Utilities.createHeaderHTML(images.settings(),
		                OpenXdataText.get(TextConstants.SETTINGS)), true);
		
		openXdataStackPanel.add(
		        widgetFactory.getReportsTreeView(),
		        Utilities.createHeaderHTML(images.reports(),
		                OpenXdataText.get(TextConstants.REPORTS)), true);

                openXdataStackPanel.add(widgetFactory.getWFTreeView(),
                         Utilities.createHeaderHTML(images.reports(),
		                "WorkFlows"), true);
		
		openXdataStackPanel.setWidth("100%");
		
		widgetFactory.getHorizontalSplitPanel().setLeftWidget(openXdataStackPanel);
		
		widgetFactory.getVerticalPanel().add(
		        widgetFactory.getHorizontalSplitPanel());
		
		DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.EM);
		dockPanel.add(widgetFactory.getVerticalPanel());
		
		Utilities.maximizeWidget(dockPanel);
		initWidget(dockPanel);
	}
	
	/**
	 * Builds the Main View Controller
	 */
	private void setupViewController() {
		
		MainViewControllerFacade.prepareMainViewController(

		widgetFactory.getMainView(), widgetFactory.getStudiesTreeView(),
		        widgetFactory.getUsersTreeView(),
		        widgetFactory.getRolesTreeView(),
		        widgetFactory.getTasksTreeView(),
		        widgetFactory.getSettingsTreeView(),
		        widgetFactory.getReportsTreeView(),
		        widgetFactory.getStudyView(), widgetFactory.getUserView(),
		        widgetFactory.getRoleView(), widgetFactory.getReportView());
	}
	
	/**
	 * Sets the <tt>Widget Factory.</tt>
	 * 
	 * @param widgetFactory
	 *            <tt>Widget Factory to set.</tt>
	 */
	public void setWidgetFactory(OpenXDataWidgetFactory widgetFactory) {
		this.widgetFactory = widgetFactory;
	}
	
	@Override
	public void onLogout() {
		logoutListener.onLogout();
	}
	
	@Override
	public void onShowAboutInfo() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onShowHelpContents() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onShowOptions() {
		
	}
	
	/**
	 * Resets the size of the <tt>MainView.</tt>
	 */
	public void resize(int width, int height) {
		widgetFactory.getHorizontalSplitPanel().setSize(width + "px",
		        (height - 50) + "px");
		
		int shortcutHeight = height - openXdataStackPanel.getAbsoluteTop();// 8;
		if (shortcutHeight < 1)
			shortcutHeight = 1;
		
		openXdataStackPanel.setHeight(shortcutHeight + "px");
	}
	
	@Override
	public void mobileInstaller() {
		if (RolesListUtil.getPermissionResolver().isExtraPermission(
		        "Perm_Mobile_Installer")) {
			new MobileInstaller().center();
		}
	}
	
	@Override
	public void changeLocale(String locale) {
		if (RolesListUtil.getPermissionResolver().isExtraPermission(
		        "Perm_Change_Locale")) {
			Context.setLocale(locale);
			((StudyView) widgetFactory.getStudyView()).changeLocale(locale);
		} else {
			Window.alert("You do not have sufficient prviledges to change locales");
		}
	}
	
	@Override
	public void onResize(ResizeEvent event) {
		int width = event.getWidth();
		int height = event.getHeight();
		resize(width, height);
	}
}
