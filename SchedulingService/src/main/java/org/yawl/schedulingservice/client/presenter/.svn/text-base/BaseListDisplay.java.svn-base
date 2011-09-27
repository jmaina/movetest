package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import java.util.List;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

/**
 *
 * @author kay
 */
public interface BaseListDisplay<T> extends WidgetDisplay {

        public void addItem(T item);

        public T getSelectedItem();

        public HasClickHandlers btnDelete();

        public HasClickHandlers getBtnNew();

        public HasClickHandlers btnRefresh();

        public void clear();

        public HandlerRegistration addSelectionHandler(SelectionHandler<T> handler);

        public void switchToView(WidgetDisplay display);

        public void resetView();

        public List<T> getSelectedItems();

}
