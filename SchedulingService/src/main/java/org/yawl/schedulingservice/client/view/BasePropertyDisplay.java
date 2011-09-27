package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import org.yawl.schedulingservice.client.util.WidgetUtil;

/**
 *
 * @author kay
 */
public abstract class BasePropertyDisplay implements WidgetDisplay {

        protected FlexTable table = new FlexTable();
        protected DecoratedTabPanel tabs = new DecoratedTabPanel();
        private List<WidgetDisplay> displays = new ArrayList<WidgetDisplay>();

        public interface Interface extends WidgetDisplay {

                public void addGeneralChangeHadler(ValueChangeHandler<String> handler);

                public void addTab(WidgetDisplay display, String tabTitle);

                public void removeTab(WidgetDisplay display);

                public void showNoPermissionView();

                public void disableAll();
        }

        public BasePropertyDisplay() {
        }

        protected void init(String title) {
                tabs.add(table, title);
                WidgetUtil.maxWidget(table);
                FlexCellFormatter cellFormatter = table.getFlexCellFormatter();
                cellFormatter.setWidth(0, 0, "20%");
                table.getRowFormatter().removeStyleName(0, "FlexTable-Header");
                WidgetUtil.maxWidget(tabs);
                tabs.selectTab(0);
                setUpKeyHandlers();
        }

        public void addGeneralChangeHadler(ValueChangeHandler<String> handler) {
                for (Widget widget : table) {
                        if (widget instanceof TextBox && !(widget instanceof PasswordTextBox)) {
                                TextBox textBox = (TextBox) widget;
                                textBox.addValueChangeHandler(handler);
                        }
                }
        }

        public void addTab(WidgetDisplay display, String tabTitle) {
                if (displays.contains(display))
                        return;
                displays.add(display);
                tabs.add(display.asWidget(), tabTitle);
        }

        public void removeTab(WidgetDisplay display) {
                tabs.remove(display.asWidget());
                displays.remove(display);
        }

        @Override
        public Widget asWidget() {
                return tabs;
        }

        public void showNoPermissionView() {
                table.clear();
                table.removeAllRows();
                table.setWidget(0, 0, new Label(" You Have No Permission"));
                FlexCellFormatter cellFormatter = table.getFlexCellFormatter();
                cellFormatter.setWidth(0, 0, "20%");
                table.setStyleName("cw-FlexTable");
                WidgetUtil.maxWidget(table);
                tabs.add(table, "AccessDenied");
                WidgetUtil.maxWidget(tabs);
                tabs.selectTab(0);
                tabs.setWidth("100%");
                opOnTextBox(new TextBoxOp() {

                        @Override
                        public void operateOn(TextBox textBox) {
                                textBox.setEnabled(false);
                        }
                });
        }

        protected void setUpKeyHandlers() {
                addFocusHandlers();
                KeyDownHandler keyDownHandler = new KeyDownHandler() {

                        @Override
                        public void onKeyDown(KeyDownEvent event) {
                                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
                                        setFocusOnNextWidget();
                        }
                };

                for (Widget widget : table) {
                        if (widget instanceof TextBox) {
                                ((TextBox) widget).addKeyDownHandler(keyDownHandler);
                        }
                }
        }

        protected void setFocusOnNextWidget() {
                boolean setFocusOnNext = false;
                for (Widget widget : table) {
                        if (setFocusOnNext && widget instanceof TextBox) {
                                TextBox textBox = (TextBox) widget;
                                textBox.setFocus(setFocusOnNext);
                                return;
                        }
                        if (widget == currFocus) {
                                GWT.log("Focus found");
                                setFocusOnNext = true;
                        }
                }
        }
        private TextBox currFocus;

        protected void addFocusHandlers() {
                for (Widget widget : table) {
                        if (widget instanceof TextBox) {
                                final TextBox textBox = (TextBox) widget;
                                FocusHandler focusHandler = new FocusHandler() {

                                        @Override
                                        public void onFocus(FocusEvent event) {
                                                currFocus = textBox;
                                        }
                                };
                                textBox.addFocusHandler(focusHandler);
                        }
                }
        }

        public void clearTextBoxes() {
                for (Widget object : table) {
                        if (object instanceof TextBox) {
                                TextBox txtBox = (TextBox) object;
                                txtBox.setText("");
                        }
                }
        }

        public void opOnTextBox(TextBoxOp op) {
                for (Widget widget : table) {
                        if (widget instanceof TextBox)
                                op.operateOn((TextBox) widget);
                }
        }

        protected interface TextBoxOp {

                public void operateOn(TextBox textBox);
        }

        public void disableAll() {
                opOnTextBox(new TextBoxOp() {

                        @Override
                        public void operateOn(TextBox textBox) {
                                textBox.setEnabled(false);
                        }
                });
        }
        private HashMap<String, Widget> properties = new HashMap<String, Widget>();

        public TextBox addTextProperty(String name) {
                int rowCount = table.getRowCount();
                table.setWidget(rowCount, 0, new Label(name));
                TextBox textBox = (TextBox) properties.get(name);
                if (textBox == null)
                        textBox = new TextBox();
                table.setWidget(rowCount, 1, textBox);
                textBox.setWidth("100%");
                return textBox;
        }

        public SimpleFilterListBox addListProperty(String name, HashMap<String, String> values) {
                int rowCount = table.getRowCount();
                table.setText(rowCount, 0, name);
                SimpleFilterListBox lstBox = (SimpleFilterListBox) properties.get(name);
                if (lstBox == null) {
                        lstBox = new SimpleFilterListBox();
                        lstBox.setVisibleItemCount(1);

                }
                Set<Entry<String, String>> entrySet = values.entrySet();
                for (Entry<String, String> entry : entrySet) {
                        lstBox.addItem(entry.getKey(), entry.getValue());
                }
                table.setWidget(rowCount, 1, lstBox);
                lstBox.setWidth("100%");
                return lstBox;
        }

        public void reset() {
                table.removeAllRows();
                properties.clear();
        }
}
