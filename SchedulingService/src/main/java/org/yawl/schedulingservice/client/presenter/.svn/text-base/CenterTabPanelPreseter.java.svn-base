package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.inject.Inject;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceManager;
import net.customware.gwt.presenter.client.place.PlaceRevealedEvent;
import net.customware.gwt.presenter.client.place.PresenterPlace;
import net.customware.gwt.presenter.client.place.TokenFormatter;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import org.openxdata.server.admin.model.SettingGroup;
import org.openxdata.server.admin.model.TaskDef;
import org.yawl.schedulingservice.client.event.LoadRequetEvent;
import org.yawl.schedulingservice.client.places.SettingsPlace;
import org.yawl.schedulingservice.client.places.ShowCasesPlace;

/**
 *
 * @author kay
 */
public class CenterTabPanelPreseter extends WidgetPresenterAdapter<CenterTabPanelPreseter.Display> {

        private final TokenFormatter formatter;
        private final TasksPresenter taskPresenter;
        private final SettingsPresenter settingsPresenter;
        private final PlaceManager manager;
        private final ShowCasesPlace showCasesPlace;
        private final SettingsPlace settingPlace;
        private boolean manualSelection = false;

        public void onShowCases() {
                GWT.log("Manual Selection onShowCases() = true");
                display.selectIndex(0);
                taskPresenter.getDisplay().resetView();

        }

        public void onShowSettings() {
                GWT.log("Manual Selection onShowSettings() = true");
                settingsPresenter.getDisplay().resetView();
                display.selectIndex(1);
        }

        public interface Display extends WidgetDisplay {

                public void addLink(String link, WidgetDisplay display);

                public void selectIndex(int i);

                public void addBeforeSelectionHandler(BeforeSelectionHandler<Integer> handler);
        }

        @Inject
        public CenterTabPanelPreseter(Display display, EventBus eventBus, TokenFormatter formatter,
                TasksPresenter taskPresenter,
                SettingsPresenter settingsPresenter,
                PlaceManager manager,
                ShowCasesPlace showCasesPlace,
                SettingsPlace settingPlace) {
                super(display, eventBus);
                this.formatter = formatter;
                this.taskPresenter = taskPresenter;
                this.settingsPresenter = settingsPresenter;
                this.manager = manager;
                this.showCasesPlace = showCasesPlace;
                this.settingPlace = settingPlace;
                bind();




        }

        @Override
        protected void onBind() {
                super.onBind();
                display.addLink("All Tasks", taskPresenter.getDisplay());
                display.addLink("Settings", settingsPresenter.getDisplay());

                display.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {

                        @Override
                        public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
                                onTabSelected(event);
                        }
                });
        }
        PresenterPlace place = null;

        private void onTabSelected(BeforeSelectionEvent<Integer> event) {
                Integer item = event.getItem();
                GWT.log("onTabSelected() manualeseltion = " + manualSelection);
                switch (item) {
                        case 0:
                                eventBus.fireEvent(new LoadRequetEvent<TaskDef>(TaskDef.class));

                                GWT.log("Firing PlaceRevealed: ShowCasesPlace");
                                eventBus.fireEvent(new PlaceRevealedEvent(showCasesPlace));
                                taskPresenter.getDisplay().resetView();
                                break;
                        case 1:
                                eventBus.fireEvent(new LoadRequetEvent<SettingGroup>(SettingGroup.class));

                                GWT.log("Firing PlaceRevealed: SettingsPlace");
                                eventBus.fireEvent(new PlaceRevealedEvent(settingPlace));

                                settingsPresenter.getDisplay().resetView();
                                break;
                }


        }
}
