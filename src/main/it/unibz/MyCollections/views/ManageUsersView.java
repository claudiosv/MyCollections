package main.it.unibz.MyCollections.views;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.it.unibz.MyCollections.DatabaseSession;
import main.it.unibz.MyCollections.User;

/**
 * Created by claudio on 31/05/2017.
 */
public class ManageUsersView {
    public ManageUsersView(Stage parentStage) {
        Stage dialog = new Stage();
        dialog.setTitle("Manage Users");
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 250, 200);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        TableView usersTable = new TableView<User>();
        usersTable.setEditable(true);

        TableColumn<User, ImageView> imageCol = new TableColumn<User, ImageView>("Image");
        imageCol.setCellValueFactory(
                new PropertyValueFactory<User, ImageView>("imageView"));
        imageCol.setPrefWidth(53);

        TableColumn<User, String> userNameCol = new TableColumn<User, String>("Username");
        userNameCol.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        userNameCol.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        userNameCol.setOnEditCommit(
                t -> {
                    User user = ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    user.setUsername(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateUser(user);
                    } catch (Exception ex) {
                        ex.printStackTrace(); //TODO: logger
                    }
                });

        TableColumn<User, Boolean> isAdminCol = new TableColumn<User, Boolean>("Admin");
        isAdminCol.setCellValueFactory(
                new PropertyValueFactory<>("isAdmin"));
        isAdminCol.setCellFactory(CheckBoxTableCell.forTableColumn(isAdminCol));
        isAdminCol.setOnEditCommit(
                t -> {
                    User user = ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    );
                    user.setAdmin(Boolean.valueOf(t.getNewValue()));
                    try {
                        DatabaseSession.getInstance().updateUser(user);
                    } catch (Exception ex) {
                        ex.printStackTrace(); //TODO: logger
                    }
                });

        usersTable.setRowFactory(new Callback<TableView<User>, TableRow<User>>() {
            @Override
            public TableRow<User> call(TableView<User> tableView) {
                final TableRow<User> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("Remove");
                removeMenuItem.setGraphic(new ImageView(new Image("minus-button.png")));
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        usersTable.getItems().remove(row.getItem());
                        try {
                            DatabaseSession.getInstance().deleteUser(row.getItem().getId());
                        } catch (Exception ex) { //TODO: logger
                        }
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
                        User userData = row.getItem();
                        EditUserView view = new EditUserView(userData, parentStage);
                        User newRec = view.show();
                        if (!newRec.isEmpty()) {
                            row.setItem(newRec);
                            usersTable.refresh();
                            try {
                                DatabaseSession.getInstance().updateUser(row.getItem());
                            } catch (Exception ex) {
                                ex.printStackTrace(); //TODO: logger
                            }
                        }
                    }
                });

                return row;
            }
        });
        ObservableList<User> data = FXCollections.observableArrayList();

        try {
            data =
                    FXCollections.observableArrayList(DatabaseSession.getInstance().getAllUsers());
        } catch (Exception ex) {
            ex.printStackTrace(); //TODO: logger
        }

        userNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        usersTable.setItems(data);
        usersTable.getColumns().addAll(imageCol, userNameCol, isAdminCol);
        grid.add(usersTable, 0, 0);


        Button btn = new Button("Close");
        btn.setGraphic(new ImageView(new Image("cross-button.png")));
        btn.setOnAction((event) -> {
            dialog.hide();
        });

        Button btnAddUser = new Button("Add User");
        btnAddUser.setGraphic(new ImageView(new Image("user-plus.png")));
        btnAddUser.setOnAction((event) -> {
            User user = new User();
            AddUserView addUser = new AddUserView(user, parentStage);
            usersTable.getItems().add(addUser.show());
        });


        HBox hbBtn = new HBox(10);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btn, btnAddUser);

        grid.add(hbBtn, 0, 4, 2, 1);


        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);
        dialog.showAndWait();
    }

}
