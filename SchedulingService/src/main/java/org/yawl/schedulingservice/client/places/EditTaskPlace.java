package org.yawl.schedulingservice.client.places;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Provider;
import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import org.openxdata.server.admin.model.TaskDef;
import org.yawl.schedulingservice.client.presenter.CenterTabPanelPreseter;
import org.yawl.schedulingservice.client.presenter.TasksPresenter;

/**
 *
 * @author kay
 */
public class EditTaskPlace extends ProvidedPresenterPlace<TasksPresenter> {

        public static String NAME = "edittask";
        public static String PARAM_ID = "id";
        private final Provider<CenterTabPanelPreseter> centerPresenter;

        @Inject
        public EditTaskPlace(Provider<TasksPresenter> provider, Provider<CenterTabPanelPreseter> centerPresenter) {
                super(provider);
                this.centerPresenter = centerPresenter;

        }

        @Override
        public String getName() {
             return NAME;
        }

        @Override
        protected void preparePresenter(PlaceRequest request, TasksPresenter presenter) {
                String parameter = request.getParameter(PARAM_ID, null);
                    GWT.log("preparePresenter() Parameter : "+parameter);
                int parseInt = Integer.parseInt(parameter);
                presenter.onEditPlaceRequest(parseInt);
                centerPresenter.get().getDisplay().selectIndex(0);
        }

        @Override
        protected PlaceRequest prepareRequest(PlaceRequest request, TasksPresenter presenter) {

                TaskDef id = presenter.getSelectTask();
                String parameter = request.getParameter(PARAM_ID, null);
                GWT.log("prepareRequest() Parameter : "+parameter);
                request = request.with(PARAM_ID, String.valueOf(id.getId()));
                return request;
        }
}
