package org.yawl.schedulingservice.client.places;

import com.google.gwt.core.client.GWT;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import javax.inject.Inject;
import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import org.yawl.schedulingservice.client.presenter.CenterTabPanelPreseter;
import org.yawl.schedulingservice.client.presenter.SettingsPresenter;

/**
 *
 * @author kay
 */
@Singleton
public class SettingsPlace extends ProvidedPresenterPlace<SettingsPresenter> {

        public static String NAME = "settings";
        private Provider<CenterTabPanelPreseter> centerPresenter;

        @Inject
        public SettingsPlace(Provider<SettingsPresenter> provider, Provider<CenterTabPanelPreseter> centProvider) {
                super(provider);
                centerPresenter = centProvider;
        }

        @Override
        public String getName() {
                return NAME;
        }

        @Override
        protected void preparePresenter(PlaceRequest request, SettingsPresenter presenter) {
                GWT.log("Place <"+NAME+">: centerPresenter.get().onShowSettings();");
                centerPresenter.get().onShowSettings();
        }


}
