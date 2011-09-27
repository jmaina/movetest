package org.yawl.schedulingservice.server;

import com.google.inject.Singleton;
import java.util.List;

import javax.servlet.ServletException;

import org.openxdata.server.admin.model.Setting;
import org.openxdata.server.admin.model.SettingGroup;
import org.springframework.web.context.WebApplicationContext;
import org.yawl.schedulingservice.client.service.SettingService;

/**
 * Default Implementation for the <code>SettingService Interface.</code>
 */
@Singleton
public class SettingServiceImpl extends OxdPersistentRemoteService implements SettingService {

	private static final long serialVersionUID = -7213691819930913724L;
	private org.openxdata.server.service.SettingService settingService;
	
	public SettingServiceImpl() {}
	
	@Override
	public void init() throws ServletException {
		super.init();
		WebApplicationContext ctx = getApplicationContext();
		settingService = (org.openxdata.server.service.SettingService)ctx.getBean("settingService");
	}

	@Override
	public void deleteSetting(Setting setting) {
		settingService.deleteSetting(setting);
	}

	@Override
	public void deleteSettingGroup(SettingGroup settingGroup) {
		settingService.deleteSettingGroup(settingGroup);
	}

	@Override
	public String getSetting(String name) {
		return settingService.getSetting(name);
	}

	@Override
	public List<SettingGroup> getSettings() {
		return settingService.getSettings();
	}

	@Override
	public void saveSetting(Setting setting) {
		settingService.saveSetting(setting);
	}

	@Override
	public void saveSettingGroup(SettingGroup settingGroup) {
		settingService.saveSettingGroup(settingGroup);
	}

	@Override
	public SettingGroup getSettingGroup(String name) {
		return settingService.getSettingGroup(name);
	}

}
