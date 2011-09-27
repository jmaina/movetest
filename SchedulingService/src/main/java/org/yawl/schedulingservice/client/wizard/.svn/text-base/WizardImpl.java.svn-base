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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;

import com.google.gwt.core.client.GWT;

/**
 * @author hturksoy
 * 
 */
class WizardImpl {

	private LinkedHashMap<Integer, WizardPage> pages;
	private int newPageId = WizardConstants.INVALID_PAGE_ID;
	private int startId = WizardConstants.INVALID_PAGE_ID;
	private int currentId = WizardConstants.INVALID_PAGE_ID;
	private Stack<Integer> history;

	WizardImpl() {
		super();

		pages = new LinkedHashMap<Integer, WizardPage>();
		history = new Stack<Integer>();
	}

	/**
	 * @return
	 */
	boolean hasPages() {
		return pages.size() > 0;
	}

	/**
	 * @return
	 */
	int newPageId() {
		return newPageId + 1;
	}

	int acceptNewPageId() {
		++newPageId;
		if (startId == WizardConstants.INVALID_PAGE_ID)
			startId = newPageId;
		return newPageId;
	}

	/**
	 * @param id
	 * @param page
	 */
	void setPage(int id, WizardPage page) {
		if(pages.containsKey(id))
			GWT.log("overriding page id " + id + " with a new page", null);
		pages.put(id, page);
	}

	/**
	 * @param id
	 */
	void setStartId(int id) {
		startId = id;
	}

	/**
	 * @return
	 */
	WizardPage gotoStartPage() {
		final WizardPage startPage = gotoPage(startId);
		return startPage;
	}

	/**
	 * @param pageId
	 * @return
	 */
	private WizardPage gotoPage(int pageId) {

		if(!pages.containsKey(pageId))
			GWT.log("no page exists with requested id : " + pageId, null);

		// make current page invisible
		if (pages.containsKey(currentId)) {
			WizardPage currentPage = pages.get(currentId);
			currentPage.setVisible(false);
		}

		WizardPage page = pages.get(pageId);
		currentId = pageId;
		page.setVisible(true);
		return page;
	}

	/**
	 * 
	 */
	WizardPage gotoNext() {

		// TODO check for not being the last page
		int nextId = nextId();
		GWT.log("next : nextId : " + nextId, null);

		history.push(currentId);
		final WizardPage nextPage = gotoPage(nextId);
		return nextPage;
	}

	/**
	 * Finds out which page to show when the user clicks Next button. First
	 * calls {@link WizardPage#nextId()} method to make a page specify/customize
	 * next page to be shown.
	 * 
	 * @return The ID of the next page, or {@link WizardConstants#INVALID_PAGE_ID} if no page follows.
	 */
	int nextId() {
		return nextIdOf(currentId);
	}

	int nextIdOf(int pageId) {
		final WizardPage page = getPage(pageId);
		if (page == null)
			return WizardConstants.INVALID_PAGE_ID;

		int nextId = page.nextId();
		if (nextId == WizardConstants.INVALID_PAGE_ID) // no next page id returned from the given page
		{
			/*
			 * page returned nothing - go to the default next page next page is:
			 * page inserted after the given page. so iterate untill finding
			 * given page then get the page id coming after as our next page
			 * id.
			 */
			final Set<Integer> pageKeys = pages.keySet();
			for (Iterator<Integer> it = pageKeys.iterator(); it.hasNext();) {
				if (pageId == it.next()) { // found given page - get the page which comes after this
					if (it.hasNext())
						nextId = it.next();
					break;
				}
			}
		}
		return nextId;
	}

	/**
	 * @return
	 */
	WizardPage gotoBack() {
		if (isHistoryEmpty()) {
			GWT.log("can not go back since no page exists in history", null);
			return null;
		}

		int prevId = history.peek();
		GWT.log("back : prevId : " + prevId, null);

		final WizardPage prevPage = gotoPage(prevId);
		history.pop(); // TODO history gotoPage'de handle edilebilir mi?
		return prevPage;
	}

	/**
	 * @return
	 */
	boolean isHistoryEmpty() {
		return history.isEmpty();
	}

	/**
	 * @return
	 */
	boolean canContinue() {
		return nextId() != WizardConstants.INVALID_PAGE_ID;
	}

	int getCurrentPageId() {
		return currentId;
	}

	/**
	 * Gets page with the given ID.
	 * 
	 * @param pageId ID of the requested page
	 */
	WizardPage getPage(int pageId) {
		return pages.get(pageId);
	}
}
