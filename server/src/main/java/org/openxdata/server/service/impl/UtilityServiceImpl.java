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
package org.openxdata.server.service.impl;

import java.util.List;

import org.openxdata.server.admin.model.MobileMenuText;
import org.openxdata.server.dao.MobileMenuTextDAO;
import org.openxdata.server.service.UtilityService;
import org.openxdata.server.sms.WapPushSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation for <code>Utility Service</code>.
 * @author Angel
 *
 */
@Transactional
@Service("utilityService")
public class UtilityServiceImpl implements UtilityService {

	@Autowired
    private MobileMenuTextDAO mobileMenuTextDAO;
	
	public UtilityServiceImpl() {}
	
    /**
	 * @param settingDAO
	 * @param mobileMenuTextDAO
     * @param loggingEngine 
	 */
	public UtilityServiceImpl(MobileMenuTextDAO mobileMenuTextDAO) {
		this.mobileMenuTextDAO = mobileMenuTextDAO;
	}

	@Override
	@Transactional(readOnly=true)
	@Secured("Perm_View_MobileMenuText")
    public List<MobileMenuText> getMobileMenuText(String locale) {
        return mobileMenuTextDAO.getMobileMenuText(locale);
    }
	
    @Override
    @Secured("Perm_Add_MobileMenuText")
	public void saveMobileMenuText(List<MobileMenuText> mobileMenuText) {
        mobileMenuTextDAO.saveMobileMenuText(mobileMenuText);
    }

    @Override
    // FIXME: security?
    public Boolean installMobileApp(List<String> phonenos, String url,
    		String modemComPort, int modemBaudRate, String promptText) {
    	
    	return WapPushSms.sendMessages(phonenos, url, modemComPort, modemBaudRate, promptText);
    }

}
