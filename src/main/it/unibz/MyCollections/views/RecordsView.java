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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;
import main.it.unibz.MyCollections.*;
import main.it.unibz.MyCollections.exceptions.RecordNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** View to manage records.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class RecordsView {
    private static final Logger logger = Logger.getLogger(RecordsView.class.getName());
    public ObservableList<Record> data;
    /** Instantiates this records view.
     *
     * @author Claudio Spiess
     * @param parentStage  Stage from which constructor is called.
     */
    public Pane box(Login parentStage) {
        logger.entering(getClass().getName(), "box");
        data = FXCollections.observableArrayList();
        try {
            logger.log(Level.INFO, "Loading records into ObservableList");
            data.addAll(DatabaseSession.getInstance().getRecords(Session.getActiveUser().getId()));
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL error loading records", ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "IO error loading records", ex);
        }

        HBox hbox = new HBox(10);
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        final Button button = new Button("Add Record");
        button.setFont(new Font("Arial", 13));
        button.setGraphic(new ImageView(new Image("plus-button.png")));
        button.setPrefSize(110, 25);

        final Button buttonSearch = new Button("Search");
        buttonSearch.setFont(new Font("Arial", 13));
        buttonSearch.setGraphic(new ImageView(new Image("magnifier.png")));
        buttonSearch.setPrefSize(110, 25);
        buttonSearch.setOnAction(event -> {
            logger.log(Level.INFO, "Search button clicked");
            SearchView search = new SearchView(parentStage.primaryStage);
            RecordSearchQuery query = search.show();
            try {
                data.setAll(DatabaseSession.getInstance().searchRecords(query, Session.getActiveUser().getId()));
            } catch (SQLException ex) {
                //TODO: add dialogs for these errors
                logger.log(Level.SEVERE, "SQL error loading records", ex);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "IO error loading records", ex);
            }
        });
        hbox.getChildren().addAll(label, button, buttonSearch);

        TableView table = new TableView();
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        VBox.setVgrow(table, Priority.ALWAYS);
        vbox.getChildren().addAll(hbox, table);

        table.setEditable(true);
        button.setOnAction(event -> {
            logger.log(Level.INFO, "Add record button clicked");
            Record rowData = new Record();
            RecordView view = new AddRecordView(rowData, parentStage.primaryStage);
            Record newData = view.show();
            if (newData != null && !newData.isEmpty()) {
                try {
                    data.add(DatabaseSession.getInstance().insertRecord(rowData));
                    table.refresh();
                } catch (SQLException ex) {
                    //TODO: add dialogs for these errors
                    logger.log(Level.SEVERE, "SQL error loading records", ex);
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, "IO error loading records", ex);
                } catch (RecordNotFoundException ex) {
                    logger.log(Level.SEVERE, "Inserted record not found", ex);
                }
            }
        });
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
                    logger.log(Level.INFO, "Firstname edited");
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setFirstName(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        //TODO: add dialogs for these errors
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, "IO error loading records", ex);
                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        TableColumn<Record, String> lastNameCol = new TableColumn<Record, String>("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        lastNameCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "lastName edited");
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setLastName(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        //TODO: add dialogs for these errors
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, "IO error loading records", ex);
                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        TableColumn<Record, String> companyNameCol = new TableColumn<Record, String>("Company");
        companyNameCol.setCellValueFactory(
                new PropertyValueFactory<>("companyName"));
        companyNameCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        companyNameCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "companyName edited");
                    Record record = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    record.setCompanyName(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        //TODO: add dialogs for these errors
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, "IO error loading records", ex);
                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });


        TableColumn<Record, String> addressCol = new TableColumn<Record, String>("Address");
        addressCol.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        addressCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        addressCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "address edited");
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setAddress(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        //TODO: add dialogs for these errors
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, "IO error loading records", ex);
                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        TableColumn<Record, String> telephoneCol = new TableColumn<Record, String>("Telephone");
        telephoneCol.setCellValueFactory(
                new PropertyValueFactory<>("telephoneNumber"));
        telephoneCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        telephoneCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "telephoneNumber edited");
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setTelephoneNumber(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        //TODO: add dialogs for these errors
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, "IO error loading records", ex);
                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        TableColumn<Record, String> emailCol = new TableColumn<Record, String>("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("emailAddress"));
        emailCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        emailCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "emailAddress edited");
                    Record record = ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    record.setEmailAddress(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        //TODO: add dialogs for these errors
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, "IO error loading records", ex);
                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        table.setOnKeyPressed(keyEvent -> {
                final Record selectedItem = (Record) table.getSelectionModel().getSelectedItem();

                if (selectedItem != null) {
                    if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                        logger.log(Level.INFO, "Delete key pressed");
                        data.removeAll(selectedItem);
                        table.refresh();
                    }
                }
        });

        table.setRowFactory(tableView -> {
                final TableRow<Record> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("Remove");
                removeMenuItem.setGraphic(new ImageView(new Image("minus-button.png")));
                removeMenuItem.setOnAction(event -> {
                        logger.log(Level.INFO, "Removing item (context menu)");
                        table.getItems().remove(row.getItem());
                        try {
                            DatabaseSession.getInstance().deleteRecord(row.getItem().getRecordId());
                        } catch (SQLException ex) {
                            //TODO: add dialogs for these errors
                            logger.log(Level.SEVERE, "SQL error loading records", ex);
                        } catch (RecordNotFoundException ex) {
                            logger.log(Level.SEVERE, "Inserted record not found", ex);
                        }
                });

                final MenuItem copyMenuItem = new MenuItem("Copy");
                copyMenuItem.setGraphic(new ImageView(new Image("clipboard-sign.png")));
                copyMenuItem.setOnAction(event ->
                {
                    logger.log(Level.INFO, "Copying item (context menu)");
                    ClipboardContent content = new ClipboardContent();
                    content.putString(row.getItem().toString());
                    Clipboard.getSystemClipboard().setContent(content);
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
                        logger.log(Level.INFO, "Editing item (context menu)");
                        Record rowData = row.getItem();
                        RecordView view = new EditRecordView(rowData, parentStage.primaryStage);
                        Record newRec = view.show();
                        if (!newRec.isEmpty()) {
                            row.setItem(newRec);
                            table.refresh();
                            try {
                                DatabaseSession.getInstance().updateRecord(row.getItem());
                            } catch (SQLException ex) {
                                //TODO: add dialogs for these errors
                                logger.log(Level.SEVERE, "SQL error updating record", ex);
                            } catch (IOException ex) {
                                logger.log(Level.SEVERE, "IO error updating record", ex);
                            } catch (RecordNotFoundException ex) {
                                logger.log(Level.SEVERE, "Updated record not found", ex);
                            }
                        }
                    }
                });
                return row;
        });

        table.setItems(data);
        table.getColumns().addAll(imageCol, firstNameCol, lastNameCol, companyNameCol, addressCol, telephoneCol, emailCol);
        parentStage.importData.setOnAction(event -> {
            logger.log(Level.INFO, "Opening import data view");
            ImportDataView dataView = new ImportDataView(parentStage.primaryStage);
            data.addAll(dataView.show());
        });

        parentStage.exportData.setOnAction(event ->
        {
            logger.log(Level.INFO, "Opening export data view");
            new ExportDataView(parentStage.primaryStage, data);
        });

        parentStage.menuFile.getItems().add(1, parentStage.summaryData);
        parentStage.menuFile.getItems().add(2, parentStage.exportData);
        parentStage.menuFile.getItems().add(3, parentStage.importData);
        if (Session.getActiveUser().isAdmin()) parentStage.menuFile.getItems().add(4, parentStage.manageUsers);

        return vbox;
    }
}
