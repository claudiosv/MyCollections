package main.it.unibz.MyCollections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.image.*;

/**
 * Created by claudio on 28/05/2017.
 */
public class RecordView {
    public VBox box()
    {
        ObservableList<Record> data =
                FXCollections.observableArrayList(
                        new Record("Jacob")
                );
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
        TableView table = new TableView();
        table.setEditable(true);

        TableColumn<Record, ImageView> imageCol = new TableColumn<Record, ImageView>("Image");
        imageCol.setCellValueFactory(
                new PropertyValueFactory<>("image"));

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


        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, companyNameCol, addressCol, telephoneCol, emailCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
        return vbox;
    }
}
