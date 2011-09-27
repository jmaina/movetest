package org.yawl.schedulingservice.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.ParameterTokenFormatter;
import net.customware.gwt.presenter.client.place.PlaceManager;
import net.customware.gwt.presenter.client.place.TokenFormatter;
import org.yawl.schedulingservice.client.places.BasicPlaceManager;
import org.yawl.schedulingservice.client.places.EditSettingPlace;
import org.yawl.schedulingservice.client.places.SettingsPlace;
import org.yawl.schedulingservice.client.places.ShowCasesPlace;
import org.yawl.schedulingservice.client.presenter.LoginPresenter;
import org.yawl.schedulingservice.client.presenter.RootPresenter;
import org.yawl.schedulingservice.client.presenter.CenterTabPanelPreseter;
import org.yawl.schedulingservice.client.presenter.NewSettingPresenter;
import org.yawl.schedulingservice.client.presenter.NewSettingValuePresenter;
import org.yawl.schedulingservice.client.presenter.SettingPresenter;
import org.yawl.schedulingservice.client.presenter.SettingsPresenter;
import org.yawl.schedulingservice.client.presenter.TaskPresenter;
import org.yawl.schedulingservice.client.presenter.TasksPresenter;
import org.yawl.schedulingservice.client.view.LoginView;
import org.yawl.schedulingservice.client.view.MainView;
import org.yawl.schedulingservice.client.view.CenterTabPanelView;
import org.yawl.schedulingservice.client.view.NewSettingGrpView;
import org.yawl.schedulingservice.client.view.NewSettingValueView;
import org.yawl.schedulingservice.client.view.ScheduleView;
import org.yawl.schedulingservice.client.view.SettingView;
import org.yawl.schedulingservice.client.view.SettingsView;
import org.yawl.schedulingservice.client.view.TaskView;
import org.yawl.schedulingservice.client.view.TasksView;
import org.yawl.schedulingservice.client.wizard.CreateTaskContext;
import org.yawl.schedulingservice.client.wizard.CreateTaskWizard;
import org.yawl.schedulingservice.client.wizard.PageEnterParameters;
import org.yawl.schedulingservice.client.wizard.PageEnterSpec;
import org.yawl.schedulingservice.client.wizard.PageSchedule;
import org.yawl.schedulingservice.client.wizard.PageSelectConfig;
import org.yawl.schedulingservice.client.wizard.PageSelectSpec;

/**
 *
 * @author kay
 */
public class SchedulerModule extends AbstractGinModule {

        @Override
        protected void configure() {
                bind(LoginPresenter.Display.class).to(LoginView.class).in(Singleton.class);
                bind(LoginPresenter.class).in(Singleton.class);
                bind(RootPresenter.class).in(Singleton.class);
                bind(RootPresenter.Display.class).to(MainView.class).in(Singleton.class);
                bind(CenterTabPanelPreseter.class).asEagerSingleton();
                bind(CenterTabPanelPreseter.Display.class).to(CenterTabPanelView.class).in(Singleton.class);

                bind(TasksPresenter.class).in(Singleton.class);
                bind(TasksPresenter.Display.class).to(TasksView.class).in(Singleton.class);

                bind(TaskPresenter.class).in(Singleton.class);
                bind(TaskPresenter.Display.class).to(TaskView.class).in(Singleton.class);

                bind(SettingsPresenter.class).in(Singleton.class);
                bind(SettingsPresenter.Display.class).to(SettingsView.class);

                bind(NewSettingPresenter.class).in(Singleton.class);
                bind(NewSettingPresenter.Display.class).to(NewSettingGrpView.class).in(Singleton.class);

                bind(SettingPresenter.class).in(Singleton.class);
                bind(SettingPresenter.Display.class).to(SettingView.class).in(Singleton.class);

                bind(NewSettingValuePresenter.class).asEagerSingleton();
                bind(NewSettingValuePresenter.Display.class).to(NewSettingValueView.class).in(Singleton.class);


                //Wizards
                bind(ScheduleView.class).in(Singleton.class);
                bind(PageSchedule.class).in(Singleton.class);
                bind(PageEnterSpec.class).in(Singleton.class);
                bind(PageEnterParameters.class).in(Singleton.class);
                bind(CreateTaskWizard.class).in(Singleton.class);
                bind(CreateTaskContext.class).in(Singleton.class);
                bind(PageSelectConfig.class).in(Singleton.class);
                bind(PageSelectSpec.class).in(Singleton.class);


                //Places
                bind(ShowCasesPlace.class).in(Singleton.class);
                bind(PlaceManager.class).to(BasicPlaceManager.class).asEagerSingleton();
                bind(SettingsPlace.class).in(Singleton.class);
                bind(EventBus.class).to(DefaultEventBus.class);
                bind(TokenFormatter.class).to(ParameterTokenFormatter.class);
                bind(EditSettingPlace.class).in(Singleton.class);


        }
}
