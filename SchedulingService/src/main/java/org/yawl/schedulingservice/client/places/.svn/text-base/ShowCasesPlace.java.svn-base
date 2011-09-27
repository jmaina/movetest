package org.yawl.schedulingservice.client.places;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Provider;
import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import org.yawl.schedulingservice.client.presenter.CenterTabPanelPreseter;
import org.yawl.schedulingservice.client.presenter.RootPresenter;

/**
 *
 * @author kay
 */
public class ShowCasesPlace extends ProvidedPresenterPlace<RootPresenter> {

        public static String NAME = "listcases";
        private  Provider<CenterTabPanelPreseter> centerPresenter;

        @Inject
        public ShowCasesPlace(Provider<RootPresenter> provider, Provider<CenterTabPanelPreseter> centProvider) {
                super(provider);
                centerPresenter = centProvider;
        }

        @Override
        public String getName() {
                return NAME;
        }

        @Override
        protected void preparePresenter(PlaceRequest request, RootPresenter presenter) {
                 GWT.log("Place <"+NAME+">: centerPresenter.get().onShowCases();");
                        centerPresenter.get().onShowCases();
        }

        @Override
        public PlaceRequest createRequest() {
                PlaceRequest request = new PlaceRequest(getName());

                return super.createRequest();
        }


}
