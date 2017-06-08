package main.it.unibz.MyCollections.views;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import main.it.unibz.MyCollections.DatabaseHandler;
import main.it.unibz.MyCollections.DatabaseSession;
import main.it.unibz.MyCollections.User;
import main.it.unibz.MyCollections.exceptions.UserAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View to manage users view. The purpose of this view
 * is to allow an administrator to easily edit
 * all users in the system, allowing the user to
 * change usernames, passwords, and administrator
 * status.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class ManageUsersView {
    /**
     * Holds logger for this class.
     */
    private static final Logger logger = Logger.getLogger(ManageUsersView.class.getName());

    /**
     * Instantiates this manage users view. Creates the necessary
     * controls to edit users and open the appropriate dialogs
     * for managing a user.
     *
     * @param parentStage Stage from which constructor is called.
     * @author Claudio Spiess
     */
    public ManageUsersView(Stage parentStage) {
        logger.entering(getClass().getName(), "ManageUsersView");
        Stage dialog = new Stage();
        dialog.getIcons().add(new Image("user-business.png"));
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
        userNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        userNameCol.setOnEditCommit(
                t -> {
                    User user = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    user.setUsername(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateUser(user);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (UserNotFoundException ex) {
                        logger.log(Level.SEVERE, "User not found", ex);
                    }
                });

        TableColumn<User, Boolean> isAdminCol = new TableColumn<User, Boolean>("Admin");
        //isAdminCol.setCellValueFactory(
        //        new PropertyValueFactory<>("isAdmin"));
        isAdminCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<User, Boolean> param) {
                try {
                    DatabaseSession.getInstance().updateUser(param.getValue());
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "SQL error loading records", ex);
                } catch (UserNotFoundException ex) {
                    logger.log(Level.SEVERE, "User not found", ex);
                }
                return param.getValue().isAdminProperty();
            }
        });
        isAdminCol.setCellFactory(CheckBoxTableCell.forTableColumn(isAdminCol));
        isAdminCol.setOnEditCommit(
                t -> {
                    User user = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    user.setAdmin(t.getNewValue());
                    try {
                        DatabaseSession.getInstance().updateUser(user);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (UserNotFoundException ex) {
                        logger.log(Level.SEVERE, "User not found", ex);
                    }
                });

        //noinspection unchecked
        usersTable.setRowFactory(tableView -> {
            final TableRow<User> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem removeMenuItem = new MenuItem("Remove");
            removeMenuItem.setGraphic(new ImageView(new Image("minus-button.png")));
            removeMenuItem.setOnAction(event -> {
                logger.log(Level.INFO, "Removing item (context menu)");

                try {
                    DatabaseSession.getInstance().deleteUser(row.getItem().getId());
                    usersTable.getItems().remove(row.getItem());
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "SQL error loading records", ex);
                } catch (UserNotFoundException ex) {
                    logger.log(Level.SEVERE, "User not found", ex);
                }
            });

            final MenuItem copyMenuItem = new MenuItem("Copy");
            copyMenuItem.setGraphic(new ImageView(new Image("clipboard-sign.png")));
            copyMenuItem.setOnAction(event -> {
                logger.log(Level.INFO, "Copying user (context menu)");
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
                    User userData = row.getItem();
                    UserView view = new EditUserView(userData, parentStage);
                    User newRec = view.show();
                    if (!newRec.isEmpty()) {
                        row.setItem(newRec);
                        usersTable.refresh();
                        try {
                            DatabaseSession.getInstance().updateUser(row.getItem());
                        } catch (SQLException ex) {
                            logger.log(Level.SEVERE, "SQL error updating user", ex);
                        } catch (UserNotFoundException ex) {
                            logger.log(Level.SEVERE, "Updated user not found", ex);
                        }
                    }
                }
            });

            return row;
        });
        ObservableList<User> data = FXCollections.observableArrayList();

        try {
            data =
                    FXCollections.observableArrayList(DatabaseSession.getInstance().getAllUsers());
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL error loading records", ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "IO error loading records", ex);
        }

        userNameCol.setStyle("-fx-alignment: CENTER-LEFT;");
        usersTable.setItems(data);
        usersTable.getColumns().addAll(imageCol, userNameCol, isAdminCol);
        grid.add(usersTable, 0, 0);

        Button btn = new Button("Close");
        btn.setGraphic(new ImageView(new Image("cross-button.png")));
        btn.setOnAction(event -> {
                    logger.log(Level.INFO, "Close button clicked");
                    dialog.hide();
                }
        );

        Button btnAddUser = new Button("Add User");
        btnAddUser.setGraphic(new ImageView(new Image("user-plus.png")));
        btnAddUser.setOnAction(event -> {
            logger.log(Level.INFO, "Add user button clicked");
            User user = new User();
            user.setDefaultImage();
            UserView addUser = new AddUserView(user, parentStage);
            user = addUser.show();
            if(!user.isEmpty()) {

                try
                {
                usersTable.getItems().add(DatabaseSession.getInstance().getUser(user.getUsername(), user.getPasswordHash()));
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "SQL error loading user", ex);
            }
                catch (UserNotFoundException ex) {
                    logger.log(Level.SEVERE, "User not found", ex);
                }
                catch (IOException ex) {
                    logger.log(Level.SEVERE, "IO error loading user", ex);
                }
            }
        });


        HBox hbBtn = new HBox(10);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btn, btnAddUser);

        grid.add(hbBtn, 0, 4, 2, 1);


        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);
        dialog.showAndWait();
        logger.exiting(getClass().getName(), "ManageUsersView");
    }

}
