package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import org.cobogw.gwt.user.client.ui.VerticalTabPanel;
import org.yawl.schedulingservice.client.presenter.CenterTabPanelPreseter;

/**
 *
 * @author kay
 */
public class CenterTabPanelView extends Composite implements CenterTabPanelPreseter.Display {

        private int currentSelected = -1;
        private List<BeforeSelectionHandler<Integer>> beforeSelectionHandlers = new ArrayList<BeforeSelectionHandler<Integer>>();
        private boolean programatticChange;

        @Override
        public void selectIndex(int i) {
                programatticChange = true;
                if (currentSelected != i)
                        tabPanel.selectTab(i);
                programatticChange = false;
        }

        interface WestViewUiBinder extends UiBinder<Widget, CenterTabPanelView> {
        }
        WestViewUiBinder binder = GWT.create(WestViewUiBinder.class);
        @UiField
        VerticalPanel vPanel;
        VerticalTabPanel tabPanel;

        public CenterTabPanelView() {
                binder.createAndBindUi(this);
                vPanel.setSpacing(5);
                tabPanel = new VerticalTabPanel();
                tabPanel.setSize("80%", "100%");
                vPanel.add(tabPanel);

                tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {

                        @Override
                        public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
                                   currentSelected = event.getItem();
                                if (!programatticChange) {
                                        for (BeforeSelectionHandler<Integer> beforeSelectionHandler : beforeSelectionHandlers) {
                                                beforeSelectionHandler.onBeforeSelection(event);
                                        }
                                }
                        }
                });
        }

        @Override
        public void addLink(String link, WidgetDisplay display) {
                tabPanel.add(display.asWidget(), link);

        }

        @Override
        public Widget asWidget() {
                return vPanel;
        }

        public void setMainDisplay(WidgetDisplay display) {
        }

        @Override
        public void addBeforeSelectionHandler(BeforeSelectionHandler<Integer> handler) {
                beforeSelectionHandlers.add(handler);
        }
}
