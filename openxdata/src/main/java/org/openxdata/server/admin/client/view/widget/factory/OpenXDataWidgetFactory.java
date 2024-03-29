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
package org.openxdata.server.admin.client.view.widget.factory;

import com.google.gwt.event.shared.EventBus;
import org.openxdata.server.admin.client.listeners.LogoutListener;
import org.openxdata.server.admin.client.permissions.UIViewLabels;
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
import org.openxdata.server.admin.client.view.factory.OpenXDataViewFactory;
import org.openxdata.server.admin.client.view.mapping.RolePermissionMapView;
import org.openxdata.server.admin.client.view.mapping.UserFormMapView;
import org.openxdata.server.admin.client.view.mapping.UserReportGroupMapView;
import org.openxdata.server.admin.client.view.mapping.UserReportMapView;
import org.openxdata.server.admin.client.view.mapping.UserRoleMapView;
import org.openxdata.server.admin.client.view.mapping.UserStudyMapView;
import org.openxdata.server.admin.client.view.treeview.OpenXDataBaseTreeView;
import org.openxdata.server.admin.client.view.treeview.ReportsTreeView;
import org.openxdata.server.admin.client.view.treeview.RolesTreeView;
import org.openxdata.server.admin.client.view.treeview.SettingsTreeView;
import org.openxdata.server.admin.client.view.treeview.StudiesTreeView;
import org.openxdata.server.admin.client.view.treeview.TasksTreeView;
import org.openxdata.server.admin.client.view.treeview.UsersTreeView;
import org.openxdata.server.admin.client.view.treeview.listeners.ContextMenuInitListener;
import org.openxdata.server.admin.client.view.widget.OpenXDataStackPanel;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.openxdata.server.admin.client.view.WorkFlowView;
import org.openxdata.server.admin.client.view.treeview.WorkFlowTreeView;

/**
 * Abstracts away the concrete implementation of the 
 * <tt>{@link OpenXDataViewFactory} by providing a common access <tt>interface.</tt>
 * 
 * @author Angel
 *
 */
public interface OpenXDataWidgetFactory {
	
	/**
	 * Retrieves the <tt>StudiesTreeView</tt>
	 * 
	 * @return Instance of {@link StudiesTreeView}
	 */
	StudiesTreeView getStudiesTreeView();
	
	/**
	 * Retrieves the <tt>UsersTreeView</tt>
	 * 
	 * @return Instance of {@link UsersTreeView}
	 */
	UsersTreeView getUsersTreeView();
	
	/**
	 * Retrieves the <tt>RolesTreeView</tt>
	 * 
	 * @return Instance of {@link RolesTreeView}
	 */
	RolesTreeView getRolesTreeView();
	
	/**
	 * Retrieves the <tt>TasksTreeView</tt>
	 * 
	 * @return Instance of {@link TasksTreeView}
	 */
	TasksTreeView getTasksTreeView();
	
	/**
	 * Retrieves the <tt>SettingsTreeView</tt>
	 * 
	 * @return Instance of {@link SettingsTreeView}
	 */
	SettingsTreeView getSettingsTreeView();
	
	/**
	 * Retrieves the <tt>ReportsTreeView</tt>
	 * 
	 * @return Instance of {@link ReportsTreeView}
	 */
	ReportsTreeView getReportsTreeView();
	
	/**
	 * Retrieves the <tt>StudyView</tt>
	 * 
	 * @return Instance of {@link StudyView}
	 */
	StudyView getStudyView();
	
	/**
	 * Retrieves the <tt>UserView</tt>
	 * 
	 * @return Instance of {@link UserView}
	 */
	UserView getUserView();
	
	/**
	 * Retrieves the <tt>RoleView</tt>
	 * 
	 * @return Instance of {@link RoleView}
	 */
	RoleView getRoleView();
	
	/**
	 * Retrieves the <tt>TaskView</tt>
	 * 
	 * @return Instance of {@link TaskView}
	 */
	TaskView getTaskView();
	
	/**
	 * Retrieves the <tt>SettingView</tt>
	 * 
	 * @return Instance of {@link SettingView}
	 */
	SettingView getSettingView();
	
	/**
	 * Retrieves the <tt>SettingView</tt>
	 * 
	 * @return Instance of {@link SettingView}
	 */
	ReportView getReportView();
	
	/**
	 * Retrieves the <tt>MainView</tt>
	 * 
	 * @return Instance of {@link MainView}
	 */
	MainView getMainView();
	
	/**
	 * Retrieves the <tt>RolePermissionMapView</tt>
	 * 
	 * @return Instance of {@link RolePermissionMapView}
	 */
	RolePermissionMapView getRolePermissionMapView();
	
	/**
	 * Retrieves the <tt>UserFormMapView</tt>
	 * 
	 * @return Instance of {@link UserFormMapView}
	 */
	UserFormMapView getUserFormMapView();
	
	/**
	 * Retrieves the <tt>UserReportGroupMapView</tt>
	 * 
	 * @return Instance of {@link UserReportGroupMapView}
	 */
	UserReportGroupMapView getUserReportGroupMapView();
	
	/**
	 * Retrieves the <tt>UserReportMapView</tt>
	 * 
	 * @return Instance of {@link UserReportMapView}
	 */
	UserReportMapView getUserReportMapView();
	
	/**
	 * Retrieves the <tt>UserRoleMapView</tt>
	 * 
	 * @return Instance of {@link UserRoleMapView}
	 */
	UserRoleMapView getUserRoleMapView();
	
	/**
	 * Retrieves the <tt>UserStudyMapView</tt>
	 * 
	 * @return Instance of {@link UserStudyMapView}
	 */
	UserStudyMapView getUserStudyMapView();
	
	/**
	 * Retrieves the <tt>Decorated Panel object</tt> that has been configured for this session.
	 * 
	 * @return Instance of {@link DecoratedTabPanel}
	 */
	DecoratedTabPanel getDecoratedPanel();
	
	/**
	 * Retrieves the <tt>OpenXdata Stack Panel object</tt> that has been configured for this session.
	 * 
	 * @return Instance of {@link OpenXDataStackPanel}
	 */
	OpenXDataStackPanel getOpenXdataStackPanel();
	
	/**
	 * Retrieves the <tt>Horizontal Split Panel object</tt> that has been configured for this session.
	 * 
	 * @return Instance of {@link HorizontalSplitPanel}
	 */
	HorizontalSplitPanel getHorizontalSplitPanel();
	
	/**
	 * Retrieves the <tt>Vertical Panel object</tt> that has been configured for this session.
	 * 
	 * @return Instance of {@link VerticalPanel}
	 */
	VerticalPanel getVerticalPanel();
	
	/**
	 * Retrieves the <tt>Menu Bar</tt> that has been configured for this session.
	 * 
	 * @return Instance of {@link OpenXDataMenuBar}
	 */
	OpenXDataMenuBar getOpenXDataMenuBar();
	
	/**
	 * Retrieves the <tt>Tool Bar</tt> that has been configured for this session.
	 * 
	 * @return Instance of {@link OpenXDataMenuBar}
	 */
	OpenXDataToolBar getOpenXDataToolBar();

	/**
	 * Retrieves the <tt>Notification Label</tt> that has been configured for this session.
	 * 
	 * @return Instance of {@link Label}
	 */
	Label getNotificationLabel();
	
	/**
	 * Retrieves the <tt>MainView</tt> configured for this session with a <tt>LogoutListener.</tt>
	 * 
	 * @param logoutListener the <tt>LogoutListener</tt> for the <tt>MainView.</tt>
	 * 
	 * @return Instance of {@link MainView}
	 */
	MainView getMainView(LogoutListener logoutListener);
	
	/**
	 * Retrieves the <tt>Notification Bar</tt> configured for this session.
	 * @return instance of {@link OpenXDataNotificationBar}
	 */
	OpenXDataNotificationBar getNotificationBar();
	
	/**
	 * Retrieves the <tt>Context Menu</tt> configured for a particular {@link OpenXDataBaseTreeView}.
	 * 
	 * @param contextMenuListener <tt>Context Menu Listener</tt> that will handle events on the <tt>Context Menu.</tt>
	 * @param labels Labels to bind to the <tt>Context Menu.</tt>
	 * @param treeViewName Name of the <tt>Tree View</tt> where we shall bind the <tt>Context Menu.</tt>
	 * 
	 * @return instance of {@link PopupPanel}
	 */
	PopupPanel getContextMenu(ContextMenuInitListener contextMenuListener, UIViewLabels labels, String treeViewName);

        EventBus getEventBus();

        WorkFlowTreeView getWFTreeView();

        WorkFlowView getWorkFlowView();
}
