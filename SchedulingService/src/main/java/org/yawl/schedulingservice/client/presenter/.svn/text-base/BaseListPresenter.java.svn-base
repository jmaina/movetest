package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import java.util.List;
import net.customware.gwt.presenter.client.EventBus;
import org.openxdata.server.admin.model.Editable;
import org.yawl.schedulingservice.client.event.LoadRequetEvent;

/**
 *
 * @author kay
 */
public abstract class BaseListPresenter<T extends Editable, D extends BaseListDisplay<T>> extends WidgetPresenterAdapter<D> {

        private final Class<T> clazz;
        private T currentItem;


        public BaseListPresenter(D display, EventBus eventBus, Class<T> clazz) {
                super(display, eventBus);
                this.clazz = clazz;
                init();

        }

        private void init() {
                display.getBtnNew().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                onNew();
                        }
                });

                display.btnRefresh().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                loadItems(true);
                        }
                });

                LoadRequetEvent.addHandler(eventBus, new LoadRequetEvent.Handler<T>() {

                        @Override
                        public void onLoadRequest() {
                                loadItems(false);
                        }

                        @Override
                        public void onRefresh() {
                                loadItems(true);
                        }
                }).forClass(this.clazz);
                display.addSelectionHandler(new SelectionHandler<T>() {

                        @Override
                        public void onSelection(SelectionEvent<T> event) {
                                T selectedItem = event.getSelectedItem();
                                currentItem = selectedItem;
                                onItemSelected(selectedItem);
                        }
                });

        }

        protected abstract void loadItems(boolean reload);

        protected abstract void onNew();

        protected abstract void onItemSelected(T item);

        public T getCurrentItem() {
                return currentItem;
        }

        public T getItemWithID(int i, List<T> items){
                for (T item : items) {
                      if(item.getId() == i)
                              return item;
                }
                return  null;
        }
        
}
