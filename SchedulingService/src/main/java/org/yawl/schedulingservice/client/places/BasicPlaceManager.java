package org.yawl.schedulingservice.client.places;

import com.google.inject.Inject;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.DefaultPlaceManager;
import net.customware.gwt.presenter.client.place.TokenFormatter;

/**
 *
 * @author kay
 */
public class BasicPlaceManager extends DefaultPlaceManager {

        @Inject
        public BasicPlaceManager(EventBus eventBus, 
                TokenFormatter tokenFormatter,
                ShowCasesPlace place,
                EditTaskPlace editTaskPlace,
                SettingsPlace settingsPlace,
                EditSettingPlace settingPlace) {

                super(eventBus, tokenFormatter);
                registerPlace(place);
                registerPlace(editTaskPlace);
                registerPlace(settingsPlace);
                registerPlace(settingPlace);
        }


}
