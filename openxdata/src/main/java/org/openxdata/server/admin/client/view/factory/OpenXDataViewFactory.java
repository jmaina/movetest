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

import org.openxdata.server.admin.client.controller.facade.MainViewControllerFacade;
import org.openxdata.server.admin.client.listeners.AppEventListener;
import org.openxdata.server.admin.client.listeners.LogoutListener;
import org.openxdata.server.admin.client.permissions.UIViewLabels;
import org.openxdata.server.admin.client.permissions.util.RolesListUtil;
import org.openxdata.server.admin.client.util.Utilities;
import org.openxdata.server.admin.client.view.MainView;
import org.openxdata.server.admin.client.view.ReportView;
import org.openxdata.server.admin.client.view.RoleView;
import org.openxdata.server.admin.client.view.SettingView;
import org.openxdata.server.admin.client.view.StudyView;
import org.openxdata.server.admin.client.view.TaskView;
import org.openxdata.server.admin.client.view.UserView;
import org.openxdata.server.admin.client.view.bar.OpenXDataMenuBar;
import org.openxdata.server.admin.client.view.bar.OpenXDataNotificationBar;
import org.openxdata.server.admin.client.view.bar.OpenXDataToolBar;
import org.openxdata.server.admin.client.view.contextmenu.OpenXDataContextMenu;
import org.openxdata.server.admin.client.view.mapping.RolePermissionMapView;
import org.openxdata.server.admin.client.view.mapping.UserFormMapView;
import org.openxdata.server.admin.client.view.mapping.UserReportGroupMapView;
import org.openxdata.server.admin.client.view.mapping.UserReportMapView;
import org.openxdata.server.admin.client.view.mapping.UserRoleMapView;
import org.openxdata.server.admin.client.view.mapping.UserStudyMapView;
import org.openxdata.server.admin.client.view.treeview.ReportsTreeView;
import org.openxdata.server.admin.client.view.treeview.RolesTreeView;
import org.openxdata.server.admin.client.view.treeview.SettingsTreeView;
import org.openxdata.server.admin.client.view.treeview.StudiesTreeView;
import org.openxdata.server.admin.client.view.treeview.TasksTreeView;
import org.openxdata.server.admin.client.view.treeview.UsersTreeView;
import org.openxdata.server.admin.client.view.treeview.WorkFlowTreeView;
import org.openxdata.server.admin.client.view.treeview.listeners.ContextMenuInitListener;
import org.openxdata.server.admin.client.view.widget.OpenXDataLabel;
import org.openxdata.server.admin.client.view.widget.OpenXDataStackPanel;
import org.openxdata.server.admin.client.view.widget.factory.OpenXDataWidgetFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.openxdata.server.admin.client.view.WorkFlowView;

/**
 * Default implementation of the {@link OpenXDataWidgetFactory}.
 * 
 * <p>
 * The <code>Composites</code> returned from this factory are too abstract to be
 * implemented AS IS. In some cases, you might be required to cast to known
 * <tt>types</tt> before implementing custom behavior.
 * </p>
 * 
 * @version 1.0
 * 
 * @author Angel
 * 
 */
public class OpenXDataViewFactory implements OpenXDataWidgetFactory {
	
	/** MainView for the application. */
	private MainView mainView;
	/** Label for showing notifications to the <tt>User.</tt> */
	private Label notificationLabel;
	/** Widget for listing of Users */
	private UsersTreeView usersTreeView;
	/** Widget for listing of roles */
	private RolesTreeView rolesTreeView;
	/** Vertical Panel to align <tt>Widgets.</tt> */
	private VerticalPanel verticalPanel;
	/** Widget for listing of tasks */
	private TasksTreeView tasksTreeView;
	/** Widget for displaying studies, forms and versions in a tree view format. */
	private StudiesTreeView studiesTreeView;
	/** Widget for listing of reports */
	private ReportsTreeView reportsTreeView;
	/** Decorated Tab Panel to hold tabs. */
	private DecoratedTabPanel decoratedPanel;
	/** The main openXDataToolBar. */
	private OpenXDataToolBar openxdataToolBar;
	/** The main menu bar factory that creates the menu bar. */
	private OpenXDataMenuBar openxdataMenuBar;
	/** Widget for listing of settings */
	private SettingsTreeView settingsTreeView;
	/** Widget for displaying task properties */
	private TaskView taskView;
	/** Widget for displaying settings properties */
	private SettingView settingView;
	/** Widget for displaying User properties */
	private UserView userView;
	/** Widget for displaying Role properties */
	private RoleView roleView;
	/** Widget for displaying Report properties */
	private ReportView reportView;
	/** Widget for displaying Study properties */
	private StudyView studyView;
	/** OpenXdata Stack Panel to hold Tree Views. */
	private OpenXDataStackPanel openXdataStackPanel;
	/** HorizontalSplitPanel to align main widgets. */
	private HorizontalSplitPanel horizontalSplitClient;
	/** Widget for Mapping Permissions to Roles */
	private RolePermissionMapView userRolePermissionMapView;
	/** Widget for Mapping Forms to Users */
	private UserFormMapView userFormMapView;
	/** Widget for Mapping Report Groups to Users */
	private UserReportGroupMapView userReportGroupMapView;
	/** Widget for Mapping Reports to Users */
	private UserReportMapView userReportMapView;
	/** Widget for Mapping Roles to Users */
	private UserRoleMapView userRoleMapView;
	/** Widget for Mapping Studies to Users */
	private UserStudyMapView userStudyMapView;
	
	private EventBus eventBus;
        private WorkFlowView workFlowView;
        private WorkFlowTreeView wfTreeView;
	
	/** Constructs an instance of this <tt>class.</tt> */
	public OpenXDataViewFactory() {
	}
	
	@Override
	public StudiesTreeView getStudiesTreeView() {
		if (studiesTreeView == null) {
			studiesTreeView = new StudiesTreeView(this);
		}
		return studiesTreeView;
	}
	
	@Override
	public SettingsTreeView getSettingsTreeView() {
		if (settingsTreeView == null) {
			settingsTreeView = new SettingsTreeView(this);
		}
		
		return settingsTreeView;
	}
	
	@Override
	public UsersTreeView getUsersTreeView() {
		if (usersTreeView == null) {
			usersTreeView = new UsersTreeView(this);
		}
		
		return usersTreeView;
	}
	
	@Override
	public RolesTreeView getRolesTreeView() {
		if (rolesTreeView == null) {
			rolesTreeView = new RolesTreeView(this);
		}
		
		return rolesTreeView;
	}
	
	@Override
	public ReportsTreeView getReportsTreeView() {
		if (reportsTreeView == null) {
			reportsTreeView = new ReportsTreeView(this);
		}
		
		return reportsTreeView;
	}
	
	@Override
	public TasksTreeView getTasksTreeView() {
		if (tasksTreeView == null) {
			tasksTreeView = new TasksTreeView(this);
		}
		
		return tasksTreeView;
	}
	
	@Override
	public TaskView getTaskView() {
		if (taskView == null) {
			taskView = injector.getTaskView();
			taskView.setEventBus(Utilities.widgetFactory.getEventBus());
			
		}
		return taskView;
	}
	
	@Override
	public SettingView getSettingView() {
		if (settingView == null) {
			settingView = new SettingView( this);
		}
		
		return settingView;
	}
	
	@Override
	public UserView getUserView() {
		if (userView == null) {
			userView = new UserView( this);
		}
		
		return userView;
	}
	
	@Override
	public RoleView getRoleView() {
		if (roleView == null) {
			roleView = new RoleView( this);
		}
		
		return roleView;
	}
	
	@Override
	public ReportView getReportView() {
		if (reportView == null) {
			reportView = new ReportView( this);
		}
		
		return reportView;
	}
	
	@Override
	public StudyView getStudyView() {
		if (studyView == null) {
			studyView = new StudyView( this);
		}
		
		return studyView;
	}
	
	@Override
	public MainView getMainView(LogoutListener logoutListener) {
		if (mainView == null) {
			
			// Construct a new MainView
			mainView = MainViewFactory.createMainView(logoutListener);
			
			mainView.setWidgetFactory(this);
			
			// Initialize the widgets on the MainView
			mainView.initializeMainViewWidgets();
		}
		
		return mainView;
	}
	
	@Override
	public OpenXDataMenuBar getOpenXDataMenuBar() {
		if (openxdataMenuBar == null) {
			if (RolesListUtil.getInstance().isAdmin()) {
				
				// Construct an administrative Menu Bar.
				openxdataMenuBar = new OpenXDataMenuBar();
				openxdataMenuBar.constructMenuBarInstanceOfAdministratorUser();
				openxdataMenuBar.setApplicationEventListener((AppEventListener) mainView);
				
			} else {
				
				// Construct a Menu Bar according to User permissions.
				openxdataMenuBar = new OpenXDataMenuBar();
				openxdataMenuBar.constructMenuBarInstanceOfUserWithPermissions();
				openxdataMenuBar.setApplicationEventListener((AppEventListener) mainView);
			}
		}
		
		return openxdataMenuBar;
	}
	
	@Override
	public OpenXDataToolBar getOpenXDataToolBar() {
		if (openxdataToolBar == null) {
			if (RolesListUtil.getInstance().isAdmin()) {
				
				openxdataToolBar = new OpenXDataToolBar();	
				openxdataToolBar.setUp();
				openxdataToolBar.instanceOfAdminUser();
				
				// Set Application Event Listener.
				openxdataToolBar.setApplicationEventListener((AppEventListener) mainView);
			} else {
				
				openxdataToolBar = new OpenXDataToolBar();	
				openxdataToolBar.setUp();
				openxdataToolBar.instanceOfUserPermissions();
				
				// Set Application Event Listener.
				openxdataToolBar.setApplicationEventListener((AppEventListener) mainView);
			}
		}
		
		return openxdataToolBar;
	}
	
	@Override
	public VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			
			verticalPanel.setWidth("100%");
			
			// Menu Bar
			verticalPanel.add(getOpenXDataMenuBar());
			
			// Notification Bar
			verticalPanel.add(getNotificationBar());
			
			// Tool Bar
			verticalPanel.add(getOpenXDataToolBar());
			
		}
		
		return verticalPanel;
	}
	
	private static final OpenXDataWidgetGinInjector injector = GWT
	        .create(OpenXDataWidgetGinInjector.class);
	
	@Override
	public OpenXDataNotificationBar getNotificationBar() {
		
		OpenXDataNotificationBar notificationBar = injector
		        .getNotificationBar();
		
		notificationBar.setUp();
		
		return notificationBar;
	}
	
	@Override
	public Label getNotificationLabel() {
		if (notificationLabel == null)
			notificationLabel = new OpenXDataLabel(" ");
		
		return notificationLabel;
	}
	
	@Override
	public HorizontalSplitPanel getHorizontalSplitPanel() {
		if (horizontalSplitClient == null) {
			horizontalSplitClient = new HorizontalSplitPanel();
			
			horizontalSplitClient.setSplitPosition("20%");
			horizontalSplitClient.setRightWidget(getStudyView());
		}
		
		return horizontalSplitClient;
	}
	
	@Override
	public RolePermissionMapView getRolePermissionMapView() {
		if (userRolePermissionMapView == null) {
			userRolePermissionMapView = new RolePermissionMapView(getEventBus());
			userRolePermissionMapView.setWidgetFactory(this);
		}
		
		return userRolePermissionMapView;
	}
	
	@Override
	public UserFormMapView getUserFormMapView() {
		if (userFormMapView == null) {
			userFormMapView =  new UserFormMapView(getEventBus());
			userFormMapView.setWidgetFactory(this);
		}
		
		return userFormMapView;
	}
	
	@Override
	public UserReportGroupMapView getUserReportGroupMapView() {
		if (userReportGroupMapView == null) {
                        userReportGroupMapView =  new UserReportGroupMapView(getEventBus());
			userReportGroupMapView.setWidgetFactory(this);
		}
		
		return userReportGroupMapView;
	}
	
	@Override
	public UserReportMapView getUserReportMapView() {
		if (userReportMapView == null) {
			userReportMapView = new  UserReportMapView(getEventBus());
			userReportMapView.setWidgetFactory(this);
		}
		
		return userReportMapView;
	}
	
	@Override
	public UserRoleMapView getUserRoleMapView() {
		if (userRoleMapView == null) {
			userRoleMapView = new UserRoleMapView(getEventBus());
			userRoleMapView.setWidgetFactory(this);
		}
		
		return userRoleMapView;
	}
	
	@Override
	public UserStudyMapView getUserStudyMapView() {
		if (userStudyMapView == null) {
			userStudyMapView = new UserStudyMapView(getEventBus());
			userStudyMapView.setWidgetFactory(this);
		}
		
		return userStudyMapView;
	}
	
	@Override
	public DecoratedTabPanel getDecoratedPanel() {
		if (decoratedPanel == null)
			decoratedPanel = new DecoratedTabPanel();
		
		return decoratedPanel;
	}
	
	@Override
	public OpenXDataStackPanel getOpenXdataStackPanel() {
		if (openXdataStackPanel == null)
			openXdataStackPanel = new OpenXDataStackPanel(
			        MainViewControllerFacade.getInstance());
		
		return openXdataStackPanel;
		
	}
	
	@Override
	public MainView getMainView() {
		return mainView;
	}
	
	@Override
	public PopupPanel getContextMenu(ContextMenuInitListener contextMenuListener, UIViewLabels contextMenuLabels,
	        String treeViewName) {
		return new OpenXDataContextMenu().createContextMenu(contextMenuListener, contextMenuLabels, treeViewName);
	}
	
	@Override
	public EventBus getEventBus() {
		if (eventBus == null)
			eventBus = injector.getEventBus();
		return eventBus;
	}

        @Override
        public WorkFlowView getWorkFlowView(){
                if(workFlowView == null)
                        workFlowView = new WorkFlowView( this);
                return workFlowView;
        }

        @Override
        public WorkFlowTreeView getWFTreeView() {
                if(wfTreeView == null )
                        wfTreeView = new WorkFlowTreeView(this);
                return wfTreeView;
        }


}
