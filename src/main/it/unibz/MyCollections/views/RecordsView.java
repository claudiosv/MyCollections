package main.it.unibz.MyCollections.views;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.it.unibz.MyCollections.*;
import main.it.unibz.MyCollections.exceptions.RecordNotFoundException;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View to manage records. This is the central view,
 * which builds a pane containing all the controls such
 * as a tableview and necessary buttons to add records, search, etc.
 * The purpose of this view is to allow users to easily manage
 * his/her record(s).
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class RecordsView {
    /**
     * Holds logger for this class.
     */
    private static final Logger logger = Logger.getLogger(RecordsView.class.getName());

    /**
     * Holds the {@link Record}s in a JavaFX friendly observable List.
     */
    private ObservableList<Record> data;

    /**
     * Builds a pane that contains all the main controls of the application
     * record editing functionality.
     *
     * @param parentStage Stage from which constructor is called.
     * @return Pane that contains all the main controls of the application.
     * @author Claudio Spiess
     */
    public Pane box(Login parentStage) {
        logger.entering(getClass().getName(), "box");
        parentStage.primaryStage.setTitle("MyCollections");
        data = FXCollections.observableArrayList();
        try {
            logger.log(Level.INFO, "Loading records into ObservableList");
            data.addAll(DatabaseSession.getInstance().getRecords(Session.getInstance().getActiveUser().getId()));
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
        button.setGraphic(new ImageView(new Image("address-book-plus.png")));
        button.setPrefSize(110, 25);

        final Button buttonSearch = new Button("Search");
        buttonSearch.setFont(new Font("Arial", 13));
        buttonSearch.setGraphic(new ImageView(new Image("magnifier.png")));
        buttonSearch.setPrefSize(110, 25);
        final Button cancelSearch = new Button("Cancel");
        buttonSearch.setOnAction(event -> {
            logger.log(Level.INFO, "Search button clicked");
            SearchView search = new SearchView(parentStage.primaryStage);
            RecordSearchQuery query = search.show();
            try {
                if (query.toLikeQuery().trim().length() > 0) {
                    data.setAll(DatabaseSession.getInstance().searchRecords(query, Session.getInstance().getActiveUser().getId()));
                    cancelSearch.setVisible(true);
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "SQL error loading records", ex);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "IO error loading records", ex);
            }
        });


        cancelSearch.setFont(new Font("Arial", 13));
        cancelSearch.setGraphic(new ImageView(new Image("magnifier-minus.png")));
        cancelSearch.setPrefSize(110, 25);
        cancelSearch.setVisible(false);
        cancelSearch.setOnAction(event -> {
            cancelSearch.setVisible(false);
            try {
                logger.log(Level.INFO, "Loading records into ObservableList");
                data.setAll(DatabaseSession.getInstance().getRecords(Session.getInstance().getActiveUser().getId()));
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "SQL error loading records", ex);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "IO error loading records", ex);
            }
        });
        hbox.getChildren().addAll(label, button, buttonSearch, cancelSearch);

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
            rowData.setDefaultImage();
            rowData.setOwnerUserId(Session.getInstance().getActiveUser().getId());
            RecordView view = new AddRecordView(rowData, parentStage.primaryStage);

            Record newData = view.show();
            if (newData != null && !newData.isEmpty()) {
                try {
                    data.add(DatabaseSession.getInstance().insertRecord(rowData));
                    table.refresh();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "SQL error loading records", ex);
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, "IO error loading records", ex);
                } catch (RecordNotFoundException ex) {
                    logger.log(Level.SEVERE, "Inserted record not found", ex);
                } catch (UserNotFoundException ex) {
                    logger.log(Level.SEVERE, "User not found", ex);
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
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "Firstname edited");
                    Record record = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    record.setFirstName(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);

                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        TableColumn<Record, String> lastNameCol = new TableColumn<Record, String>("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "lastName edited");
                    Record record = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    record.setLastName(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);

                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        TableColumn<Record, String> companyNameCol = new TableColumn<Record, String>("Company");
        companyNameCol.setCellValueFactory(
                new PropertyValueFactory<>("companyName"));
        companyNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        companyNameCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "companyName edited");
                    Record record = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    record.setCompanyName(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);

                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });


        TableColumn<Record, String> addressCol = new TableColumn<Record, String>("Address");
        addressCol.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "address edited");
                    Record record = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    record.setAddress(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);

                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        TableColumn<Record, String> telephoneCol = new TableColumn<Record, String>("Telephone");
        telephoneCol.setCellValueFactory(
                new PropertyValueFactory<>("telephoneNumber"));
        telephoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        telephoneCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "telephoneNumber edited");
                    Record record = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    record.setTelephoneNumber(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);

                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        TableColumn<Record, String> emailCol = new TableColumn<Record, String>("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("emailAddress"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(
                t -> {
                    logger.log(Level.INFO, "emailAddress edited");
                    Record record = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    record.setEmailAddress(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateRecord(record);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);

                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }
                });

        table.setOnKeyPressed(keyEvent -> {
            final Record selectedItem = (Record) table.getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    logger.log(Level.INFO, "Delete key pressed");

                    try {
                        DatabaseSession.getInstance().deleteRecord(selectedItem.getRecordId());
                        data.remove(selectedItem);
                        table.refresh();
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (RecordNotFoundException ex) {
                        logger.log(Level.SEVERE, "Inserted record not found", ex);
                    }

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

                try {
                    DatabaseSession.getInstance().deleteRecord(row.getItem().getRecordId());
                    table.getItems().remove(row.getItem());
                } catch (SQLException ex) {
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

            final MenuItem addMenuItem = new MenuItem("Add Record");
            addMenuItem.setGraphic(new ImageView(new Image("clipboard-sign.png")));
            addMenuItem.setOnAction(event ->
            {
            });

            final MenuItem editMenuItem = new MenuItem("Add Record");
            editMenuItem.setGraphic(new ImageView(new Image("clipboard-sign.png")));
            editMenuItem.setOnAction(event ->
            {
            });


            contextMenu.getItems().addAll(copyMenuItem, removeMenuItem);

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
                            logger.log(Level.SEVERE, "SQL error updating record", ex);
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
        parentStage.getMenuBar().getFileMenu().getImportDataMenuItem().setOnAction(event -> {
            logger.log(Level.INFO, "Opening import data view");
            ImportDataView dataView = new ImportDataView(parentStage.primaryStage);
            List<Record> importedRecords = dataView.show();
            for (Record record : importedRecords) {
                try {
                    record.setOwnerUserId(Session.getInstance().getActiveUser().getId());
                    data.add(DatabaseSession.getInstance().insertRecord(record));
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "SQL error updating record", ex);
                } catch (UserNotFoundException ex) {
                    logger.log(Level.SEVERE, "Updated record not found", ex);
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, "IO Exception", ex);
                } catch (RecordNotFoundException ex) {
                    logger.log(Level.SEVERE, "Updated record not found", ex);
                }
            }

        });

        parentStage.getMenuBar().getFileMenu().getExportDataMenuItem().setOnAction(event ->
        {
            logger.log(Level.INFO, "Opening export data view");
            new ExportDataView(parentStage.primaryStage, data);
        });

        return vbox;
    }
}
