package main.it.unibz.MyCollections.views;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.it.unibz.MyCollections.DatabaseHandler;
import main.it.unibz.MyCollections.Record;
import main.it.unibz.MyCollections.RecordSearchQuery;

/**
 * Created by claudio on 28/05/2017.
 */
public class RecordsView {
    public Pane box(Stage parentStage) {
        ObservableList<Record> data =
                FXCollections.observableArrayList();
        try {
            data.addAll(DatabaseHandler.getInstance().getAllRecords());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        RowConstraints c = new RowConstraints();
        c.setPercentHeight(10);
        grid.getRowConstraints().add(c);
        c = new RowConstraints();
        c.setPercentHeight(90);
        grid.getRowConstraints().add(c);


        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(100);
        grid.getColumnConstraints().add(c1);
        //c = new RowConstraints();
        //c.setPercentHeight(90);
       // grid.getRowConstraints().add(c);


        HBox hbox = new HBox(10);

        grid.setPadding(new Insets(10, 10, 10, 10));
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
        //grid.add(label, 0, 0, 1, 1);

        final Button button = new Button("Add Record");
        button.setFont(new Font("Arial", 13));
        button.setGraphic(new ImageView(new Image("plus-button.png")));
        button.setPrefSize(110, 25);
        //grid.add(button, 1, 0, 1, 1);

        final Button buttonSearch = new Button("Search");
        buttonSearch.setFont(new Font("Arial", 13));
        buttonSearch.setGraphic(new ImageView(new Image("magnifier.png")));
        buttonSearch.setPrefSize(110, 25);
        buttonSearch.setOnAction(event -> {
            SearchView search = new SearchView(parentStage);
            RecordSearchQuery query = search.show();
            try {
                data.setAll(DatabaseHandler.getInstance().searchRecords(query));
            }catch (Exception ex){ex.printStackTrace();}
        });
        //grid.add(buttonSearch, 2, 0, 1, 1);
        hbox.getChildren().addAll(label, button, buttonSearch);
        grid.add(hbox, 0, 0);
        TableView table = new TableView();
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 0, 10));
        VBox.setVgrow(table, Priority.ALWAYS);
        vbox.getChildren().add(table);

        grid.add(vbox, 0, 1);
        table.setEditable(true);
        button.setOnAction((event -> {
            Record rowData = new Record();
            RecordView view = new AddRecordView(rowData, parentStage);
            Record newData = view.show();
            if (newData != null && !newData.isEmpty()) {

                try {
                    data.add(DatabaseHandler.getInstance().insertRecord(rowData));
                    table.refresh();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));
        TableColumn<Record, ImageView> imageCol = new TableColumn<Record, ImageView>("Image");
        imageCol.setCellValueFactory(
                new PropertyValueFactory<Record, ImageView>("imageView"));
        imageCol.setPrefWidth(53);

        TableColumn<Record, String> firstNameCol = new TableColumn<Record, String>("First Name");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        firstNameCol.setOnEditCommit(
                t -> {
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setFirstName(t.getNewValue());
                    try {
                        DatabaseHandler.getInstance().updateRecord(record);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

        TableColumn<Record, String> lastNameCol = new TableColumn<Record, String>("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        lastNameCol.setOnEditCommit(
                t -> {
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setLastName(t.getNewValue());
                    try {
                        DatabaseHandler.getInstance().updateRecord(record);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

        TableColumn<Record, String> companyNameCol = new TableColumn<Record, String>("Company");
        companyNameCol.setCellValueFactory(
                new PropertyValueFactory<>("companyName"));
        companyNameCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        companyNameCol.setOnEditCommit(
                t -> {
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setCompanyName(t.getNewValue());
                    try {
                        DatabaseHandler.getInstance().updateRecord(record);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });


        TableColumn<Record, String> addressCol = new TableColumn<Record, String>("Address");
        addressCol.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        addressCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        addressCol.setOnEditCommit(
                t -> {
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setAddress(t.getNewValue());
                    try {
                        DatabaseHandler.getInstance().updateRecord(record);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

        TableColumn<Record, String> telephoneCol = new TableColumn<Record, String>("Telephone");
        telephoneCol.setCellValueFactory(
                new PropertyValueFactory<>("telephoneNumber"));
        telephoneCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        telephoneCol.setOnEditCommit(
                t -> {
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setTelephoneNumber(t.getNewValue());
                    try {
                        DatabaseHandler.getInstance().updateRecord(record);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

        TableColumn<Record, String> emailCol = new TableColumn<Record, String>("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("emailAddress"));
        emailCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        emailCol.setOnEditCommit(
                t -> {
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setEmailAddress(t.getNewValue());
                    try {
                        DatabaseHandler.getInstance().updateRecord(record);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

        table.setRowFactory(new Callback<TableView<Record>, TableRow<Record>>() {
            @Override
            public TableRow<Record> call(TableView<Record> tableView) {
                final TableRow<Record> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("Remove");
                removeMenuItem.setGraphic(new ImageView(new Image("minus-button.png")));
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        table.getItems().remove(row.getItem());
                        DatabaseHandler.getInstance().deleteRecord(row.getItem().getRecordId());
                    }
                });

                final MenuItem copyMenuItem = new MenuItem("Copy");
                copyMenuItem.setGraphic(new ImageView(new Image("clipboard-sign.png")));
                copyMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ClipboardContent content = new ClipboardContent();
                        content.putString(row.getItem().toString());
                        Clipboard.getSystemClipboard().setContent(content);
                    }
                });


                contextMenu.getItems().addAll(copyMenuItem, removeMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(contextMenu)
                );
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Record rowData = row.getItem();
                        RecordView view = new EditRecordView(rowData, parentStage);
                        Record newRec = view.show();
                        if (!newRec.isEmpty()) {
                            row.setItem(newRec);
                            table.refresh();
                            try {
                                DatabaseHandler.getInstance().updateRecord(row.getItem());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

                return row;
            }
        });

        table.setItems(data);
        table.getColumns().addAll(imageCol, firstNameCol, lastNameCol, companyNameCol, addressCol, telephoneCol, emailCol);


        return grid;
    }
}
