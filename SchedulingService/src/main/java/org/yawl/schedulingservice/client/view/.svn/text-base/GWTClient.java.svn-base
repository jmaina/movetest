package org.yawl.schedulingservice.client.view;

import java.util.*;

import com.google.gwt.user.client.ui.FlexTable;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

public class GWTClient implements EntryPoint, HistoryListener {

        private Table table;

        private void getData() {
                ArrayList values = new ArrayList();

                values.add(new Person("A", "H", "E", "6"));
                values.add(new Person("B", "G", "E", "19"));
                values.add(new Person("C", "F", "A", "17"));
                values.add(new Person("D", "E", "A", "17"));

                table.setSource(new SimpleDataSource((Person[]) values.toArray(new Person[values.size()])));
        }

        public void onModuleLoad() {
                table = new Table(null, "myTable");
                table.setStyleName("myTable");
                RootPanel.get().add(table);
                getData();

                table.addTableListener(new TableListener() {

                        public void onCellClicked(SourcesTableEvents sender,
                                int row, int cell) {
                                History.newItem("" + row);
                        }
                });
                History.addHistoryListener(this);
        }

        public void onHistoryChanged(String historyToken) {
                table.onCellClicked(table, Integer.parseInt(historyToken), 0);
        }
}

class Person {

        public String firstName;
        public String lastName;
        public String company;
        public String counts;

        public Person() {
        }

        public Person(String firstName, String lastName, String company, String counts) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.company = company;
                this.counts = counts;
        }
}

class Table extends FlexTable implements TableListener {

        private String headerStyle;
        private String selectedStyle;
        private TableDataSource source;
        private int selectedRow;

        public Table() {
                this.setCellPadding(1);
                this.setCellSpacing(0);
                this.setWidth("100%");
                // this.selectedStyle = stylePrefix + "-selected";
                this.headerStyle = "FlexTable-Header";
                this.addTableListener(this);
                sinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONMOUSEOVER | Event.ONMOUSEOUT);

        }

        public Table(TableDataSource source, String stylePrefix) {
                super();
                this.setCellPadding(1);
                this.setCellSpacing(0);
                this.setWidth("100%");
                this.selectedStyle = stylePrefix + "-selected";
                this.headerStyle = stylePrefix + "-header";
                this.addTableListener(this);
                this.setSource(source);

                sinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONMOUSEOVER | Event.ONMOUSEOUT);
        }

        @Override
        public void onBrowserEvent(Event event) {
                Element td = getEventTargetCell(event);

                if (td == null)
                        return;
                Element tr = DOM.getParent(td);
                switch (DOM.eventGetType(event)) {
                        case Event.ONMOUSEDOWN: {
                                DOM.setStyleAttribute(tr, "backgroundColor", "#ffce00");
                                onRowClick(tr);
                                break;
                        }
                        case Event.ONMOUSEUP: {
                                DOM.setStyleAttribute(tr, "backgroundColor", "#ffffff");
                                break;
                        }
                        case Event.ONMOUSEOVER: {
                                DOM.setStyleAttribute(tr, "backgroundColor", "#ffce00");
                                onRowRollover(tr);
                                break;
                        }
                        case Event.ONMOUSEOUT: {
                                DOM.setStyleAttribute(tr, "backgroundColor", "#ffffff");
                                break;
                        }
                }

        }

        public void onRowClick(Element event) {
        }

        public void onRowRollover(Element event) {
        }

        @Override
        public void onCellClicked(SourcesTableEvents sender,
                int row, int cell) {
                this.getRowFormatter().removeStyleName(selectedRow, selectedStyle);
                if ((source.getHeaderRow() == null) || (row > 0)) {
                        this.getRowFormatter().addStyleName(row, selectedStyle);
                        selectedRow = row;
                }
        }

        public void setSource(TableDataSource source) {
                for (int i = this.getRowCount(); i > 0; i--) {
                        this.removeRow(0);
                }
                if (source == null) {
                        return;
                }

                int row = 0;
                String[] headers = source.getHeaderRow();
                if (headers != null) {
                        for (int i = 0; i < headers.length; i++) {
                                this.setText(row, i, headers[i]);
                        }
                        this.getRowFormatter().addStyleName(row, headerStyle);
                        row++;
                }
                int rows = source.getRowCount();
                for (int i = 0; i < rows; i++) {
                        Widget[] widgetRow = source.getWidgetRow(row);
                        String[] values = source.getRow(i);
                        for (int col = 0; col < values.length; col++) {
                                if (values != null)
                                        this.setText(row, col, values[col]);
                                else
                                        this.setWidget(row, col, widgetRow[col]);
                        }
                        row++;
                }
                this.selectedRow = 0;
                this.source = source;
        }
}

class SimpleDataSource implements TableDataSource {

        private String[] mHeaders;
        private String[][] data;

        public SimpleDataSource(Person[] people) {
                super();
                String[] headers = {"First Name", "Last Name", "Company", "Counts"};
                this.mHeaders = headers;
                this.data = new String[people.length][4];
                for (int i = 0; i < people.length; i++) {
                        Person p = people[i];
                        this.data[i][0] = p.firstName;
                        this.data[i][1] = p.lastName;
                        this.data[i][2] = p.company;
                        this.data[i][3] = p.counts;
                }
        }

        @Override
        public int getRowCount() {
                return data.length;
        }

        @Override
        public String[] getRow(int i) {
                return data[i];
        }

        @Override
        public String[] getHeaderRow() {
                return mHeaders;
        }

        @Override
        public Widget[] getWidgetRow(int row) {
                return null;
        }
}

interface TableDataSource {

        public String[] getHeaderRow();

        public int getRowCount();

        public String[] getRow(int row);

        public Widget[] getWidgetRow(int row);
}
