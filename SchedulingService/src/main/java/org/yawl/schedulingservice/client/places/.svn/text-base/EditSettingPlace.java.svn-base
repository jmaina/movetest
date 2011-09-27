package org.yawl.schedulingservice.client.places;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Provider;
import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import org.openxdata.server.admin.model.SettingGroup;
import org.yawl.schedulingservice.client.presenter.CenterTabPanelPreseter;
import org.yawl.schedulingservice.client.presenter.SettingPresenter;
import org.yawl.schedulingservice.client.presenter.SettingsPresenter;

/**
 *
 * @author kay
 */
public class EditSettingPlace extends ProvidedPresenterPlace<SettingPresenter> {

        public static String NAME = "editsetting";
        public static String PARAM_ID = "id";
        private final Provider<SettingsPresenter> settingsPresenter;
        private final Provider<CenterTabPanelPreseter> centerPresenter;

        @Inject
        public EditSettingPlace(Provider<SettingPresenter> provider,Provider<SettingsPresenter> settingsPresenter,Provider<CenterTabPanelPreseter> centerPresenter) {
                super(provider);
                this.settingsPresenter = settingsPresenter;
                this.centerPresenter = centerPresenter;
        }

        @Override
        public String getName() {
                return NAME;
        }

        @Override
        protected void preparePresenter(PlaceRequest request, SettingPresenter presenter) {
                String parameter = request.getParameter(PARAM_ID, null);
                int settingID = Integer.parseInt(parameter);
                settingsPresenter.get().onEditPlaceRequest(settingID);
                centerPresenter.get().getDisplay().selectIndex(1);

        }

        @Override
        protected PlaceRequest prepareRequest(PlaceRequest request, SettingPresenter presenter) {
                SettingGroup currentItem = settingsPresenter.get().getCurrentItem();
                String param = request.getParameter(PARAM_ID, null);
                GWT.log("EditSettingPlace.prepareRequest() Parameter : " + param);
                request = request.with(PARAM_ID, String.valueOf(currentItem.getId()));
                return request;
        }
}
