package org.yawl.schedulingservice.client.util;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.yawl.schedulingservice.client.view.ProgressDialog;
import org.yawl.schedulingservice.client.view.SimpleFilterListBox;

/**
 *
 * @author kay
 */
public class WidgetUtil {

        public static void maxWidget(Widget w) {
                w.setSize("100%", "100%");
        }

        public static void allowNumericOnly(TextBox textBox, boolean allowDecimal) {
                final boolean allowDecimalPoints = allowDecimal;
                textBox.addKeyPressHandler(new KeyPressHandler() {

                        @Override
                        public void onKeyPress(KeyPressEvent event) {
                                char charCode = event.getCharCode();
                                int keyCode1 = event.getNativeEvent().getKeyCode();
                                Object sender = event.getSource();
                                if ((!Character.isDigit(charCode))
                                        && (keyCode1 != KeyCodes.KEY_TAB)
                                        && (keyCode1 != KeyCodes.KEY_BACKSPACE)
                                        && (keyCode1 != KeyCodes.KEY_LEFT)
                                        && (keyCode1 != KeyCodes.KEY_UP)
                                        && (keyCode1 != KeyCodes.KEY_RIGHT)
                                        && (keyCode1 != KeyCodes.KEY_DOWN)) {

                                        if (charCode == '.' && allowDecimalPoints
                                                && !((TextBox) sender).getText().contains("."))
                                                return;
                                        ((TextBox) sender).cancelKey();
                                }
                        }
                });


        }
        private static int progressCount = 0;
        private static ProgressDialog dlg = new ProgressDialog();

        public static void showProgress() {
                progressCount++;
                dlg.setText("Processing...    ");
                // dlg.setWidth("150px");
                dlg.center();
        }

        public static void hideProgress() {
                if (progressCount > 0)
                        progressCount--;

                if (progressCount == 0)
                        dlg.hide();

        }

        public static void showMsg(String string) {
                Window.alert(string);
        }

        public static String getListBoxValue(ListBox listBox) {
                int idx = listBox.getSelectedIndex();
                if (idx == -1)
                        return null;
                return listBox.getItemText(idx);
        }

        public static String getListBoxValue(SimpleFilterListBox listBox) {
                int idx = listBox.getSelectedIndex();
                if (idx == -1)
                        return null;
                return listBox.getItemText(idx);
        }
}
