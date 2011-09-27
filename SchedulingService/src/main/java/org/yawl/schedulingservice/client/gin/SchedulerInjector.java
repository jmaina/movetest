
package org.yawl.schedulingservice.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.yawl.schedulingservice.client.presenter.LoginPresenter;

/**
 *
 * @author kay
 */
@GinModules(SchedulerModule.class)
public interface  SchedulerInjector extends Ginjector {

        public LoginPresenter getLoginPresenter();

}
