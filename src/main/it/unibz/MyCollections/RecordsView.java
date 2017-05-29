package main.it.unibz.MyCollections;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.image.*;
import javafx.util.Callback;

/**
 * Created by claudio on 28/05/2017.
 */
public class RecordsView {
    public VBox box()
    {
        ObservableList<Record> data =
                FXCollections.observableArrayList(new Record("John"));
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 0, 10));
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
        grid.add(label, 0, 0, 1, 1);

        final Button button = new Button("Add Record");
        button.setFont(new Font("Arial", 13));
        button.setGraphic(new ImageView(new Image("plus-circle.png", 16, 16, true, true)));
       button.setPrefSize(110, 25);
        grid.add(button, 1, 0, 1, 1);

        TableView table = new TableView();
        grid.add(table, 0, 1, 2, 1);
        table.setEditable(true);

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
                    ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setFirstName(t.getNewValue());
                });

        TableColumn<Record, String> lastNameCol = new TableColumn<Record, String>("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        lastNameCol.setOnEditCommit(
                t -> {
                    ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setLastName(t.getNewValue());
                });

        TableColumn<Record, String> companyNameCol = new TableColumn<Record, String>("Company");
        companyNameCol.setCellValueFactory(
                new PropertyValueFactory<>("companyName"));
        companyNameCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        companyNameCol.setOnEditCommit(
                t -> {
                    ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCompanyName(t.getNewValue());
                });


        TableColumn<Record, String> addressCol = new TableColumn<Record, String>("Address");
        addressCol.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        addressCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        addressCol.setOnEditCommit(
                t -> {
                    ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAddress(t.getNewValue());
                });

        TableColumn<Record, String> telephoneCol = new TableColumn<Record, String>("Telephone");
        telephoneCol.setCellValueFactory(
                new PropertyValueFactory<>("telephone"));
        telephoneCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        telephoneCol.setOnEditCommit(
                t -> {
                    ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setTelephoneNumber(t.getNewValue());
                });

        TableColumn<Record, String> emailCol = new TableColumn<Record, String>("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        emailCol.setOnEditCommit(
                t -> {
                    ((Record) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setEmailAddress(t.getNewValue());
                });

        table.setRowFactory(new Callback<TableView<Record>, TableRow<Record>>() {
            @Override
            public TableRow<Record> call(TableView<Record> tableView) {
                final TableRow<Record> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("Remove");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        table.getItems().remove(row.getItem());
                    }
                });
                contextMenu.getItems().add(removeMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );
                return row ;
            }
        });
        //data.add(new Record());
        table.setItems(data);
        table.getColumns().addAll(imageCol, firstNameCol, lastNameCol, companyNameCol, addressCol, telephoneCol, emailCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        //vbox.setPadding(new Insets(10, 10, 0, 10));
        vbox.getChildren().addAll(grid);
        return vbox;
    }
}
