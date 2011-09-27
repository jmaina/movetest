package org.openxdata.modules.workflows.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import org.openxdata.modules.workflows.server.handler.GetSpecActionHandler;
import org.openxdata.modules.workflows.server.handler.GetStudiesActionHandler;
import org.openxdata.modules.workflows.server.handler.ParseSpecActionHandler;
import org.openxdata.modules.workflows.shared.rpc.GetSpecsAction;
import org.openxdata.modules.workflows.shared.rpc.GetStudiesAction;
import org.openxdata.modules.workflows.shared.rpc.ParseSpecAction;

public class ActionHandlerConfigModule extends ActionHandlerModule
{

    @Override
    protected void configureHandlers()
    {
	bindHandler(ParseSpecAction.class,ParseSpecActionHandler.class);
	bindHandler(GetSpecsAction.class,GetSpecActionHandler.class);
	bindHandler(GetStudiesAction.class,GetStudiesActionHandler.class);

    }

}
