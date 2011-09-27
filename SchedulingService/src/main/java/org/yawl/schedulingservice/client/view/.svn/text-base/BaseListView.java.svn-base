package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import org.cobogw.gwt.user.client.ui.Button;
import org.cobogw.gwt.user.client.ui.ButtonBar;
import org.openxdata.server.admin.model.Editable;
import org.yawl.schedulingservice.client.presenter.BaseListDisplay;
import org.yawl.schedulingservice.client.util.WidgetUtil;

/**
 *
 * @author kay
 */
public abstract class BaseListView<T extends Editable> implements BaseListDisplay<T>, HasSelectionHandlers<T> {

        /*Buttons*/
        protected Button btnDelete = new Button("Delete");
        protected Button btnEdit = new Button("Edit");
        protected Button btnNew = new Button("New");
        protected Button btnStart = new Button("Start");
        private int checkBoxCol = -1;

        /*Maps*/
        protected HashMap<Integer, T> rowTaskMap = new HashMap<Integer, T>();
        private HashMap<CheckBox, T> checkBoxMap = new HashMap<CheckBox, T>();
        SelectionHandler<T> handler;
        /*UiFields*/
        @UiField
        Button btnRefresh;
        @UiField(provided = true)
        ButtonBar btnBar;
        @UiField
        HTMLPanel sp;
        @UiField
        ScrollPanel scrollPanel;
        @UiField
        VerticalPanel vp;
        @UiField
        FlexTable table;

        protected void setUp() {
                initializeTable();

                WidgetUtil.maxWidget(table);
                table.addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                onCellClicked(event);
                        }
                });
        }

        @Override
        public void addItem(T item) {
                int rowCount = table.getRowCount();
                int col4ChkBox = render(rowCount, item);
                if (requiresCheckBox()) {
                        CheckBox bx = new CheckBox();
                        table.setWidget(rowCount, col4ChkBox, bx);
                        checkBoxMap.put(bx, item);
                        checkBoxCol = col4ChkBox;
                }
                rowTaskMap.put(rowCount, item);
                table.getRowFormatter().addStyleName(rowCount, "dir");
        }

        @Override
        public T getSelectedItem() {
                List<T> selectedItems = getSelectedItems();
                if (selectedItems.isEmpty())
                        return null;
                else
                        return selectedItems.get(0);
        }

        @Override
        public HasClickHandlers btnDelete() {
                return btnDelete;
        }

        @Override
        public HasClickHandlers getBtnNew() {
                return btnNew;
        }

        @Override
        public void clear() {
                table.removeAllRows();
                rowTaskMap.clear();
                checkBoxMap.clear();
                initializeTable();
        }

        private Widget currentWidget;

        @Override
        public void switchToView(WidgetDisplay display) {
                sp.remove(vp);
                if (currentWidget != display.asWidget()) {
                        GWT.log("Adding Show Tasks Widget to simple panel");
                        sp.add(display.asWidget());
                        currentWidget = display.asWidget();
                }
        }

        @Override
        public Widget asWidget() {
                return sp;
        }

        private void initializeTable() {
                String[] header = getHeader();
                for (int i = 0; i < header.length; i++) {
                        String string = header[i];
                        table.setText(0, i, string);
                }
                if (requiresCheckBox()) {
                        table.setText(0, header.length, "");
                }
                table.getRowFormatter().addStyleName(0, "FlexTable-Header");
        }

        private void onCellClicked(ClickEvent event) {
                Cell cellForEvent = table.getCellForEvent(event);
                if(cellForEvent == null) return;
                int rowIndex = cellForEvent.getRowIndex();
                int cellIndex = cellForEvent.getCellIndex();
                if (rowIndex == 0 || cellIndex == checkBoxCol || getSkipCols().contains(cellIndex))
                        return;
                T taskDef = rowTaskMap.get(rowIndex);

                SelectionEvent.fire(this, taskDef);

        }

        @Override
        public List<T> getSelectedItems() {
                List<T> taskDefs = new ArrayList<T>();
                Set<Entry<CheckBox, T>> entrySet = checkBoxMap.entrySet();
                for (Entry<CheckBox, T> entry : entrySet) {
                        if (entry.getKey().getValue() != null && entry.getKey().getValue()) {
                                taskDefs.add(entry.getValue());
                        }
                }
                return taskDefs;
        }

        protected int getRowForItem(T task) {
                Set<Entry<Integer, T>> entrySet = rowTaskMap.entrySet();
                int row = -1;
                for (Entry<Integer, T> entry : entrySet) {
                        if (entry.getValue().getId() == task.getId()) {
                                row = entry.getKey();
                                break;
                        }
                }
                return row;
        }

        @Override
        public void resetView() {
                if (currentWidget != null) {
                        sp.remove(currentWidget);
                        sp.add(vp);
                }
                currentWidget = null;
        }

        @Override
        public HasClickHandlers btnRefresh() {
                return btnRefresh;
        }

        public HasClickHandlers btnEdit() {
                return btnEdit;
        }

        public HasClickHandlers btnStart() {
                return btnStart;
        }

        @Override
        public void fireEvent(GwtEvent<?> event) {
                handler.onSelection((SelectionEvent<T>) event);
        }

        @Override
        public HandlerRegistration addSelectionHandler(SelectionHandler<T> handler) {
                this.handler = handler;
                return null;
        }

        protected abstract String[] getHeader();

        protected abstract int render(int i, T item);

        protected abstract boolean requiresCheckBox();

        protected List<Integer> getSkipCols(){
                return Collections.EMPTY_LIST;
        }
}
