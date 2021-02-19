package com.shventurecapital.bebtea.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.shventurecapital.bebtea.config.StageManager;
import com.shventurecapital.bebtea.models.User;
import com.shventurecapital.bebtea.services.impl.UserService;
import com.shventurecapital.bebtea.views.FxmlView;

//import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class AdminController implements Initializable {
    
    @FXML
    private TableView<User> orderTable;
    
    @FXML
    private TableColumn<User, Long> colId;
    
    @FXML
    private TableColumn<User, String> colName;
    
    @FXML
    private TableColumn<User, String> colAddress;

    @FXML
    private TableColumn<User, String> colFlavor;
    
    @FXML
    private TableColumn<User, String> colSize;
    
    @FXML
    private TableColumn<User, Integer> colQuantity;

    @FXML
    private TableColumn<User, Double> colAmount;
    
    @FXML
    private TableColumn<User, String> colStatus;
    
    @FXML
    private TableColumn<User, Boolean> colEdit;

    @FXML
    private Label orderId;
    
    @FXML
    private Button btDelete;
    
    @FXML
    private ChoiceBox<String> status;

    @FXML
    private Button save;
    
    @FXML
    private Button btSupply;
    
     @FXML
    private Button btOrder;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private UserService userService;
    
    @FXML
    void Select(ActionEvent event) {
    }
    
        private ObservableList<User> userList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }


//    @FXML
//    void reset(ActionEvent event) {
//        clearFields();
//    }
        
    @FXML
    void openSupply(ActionEvent event) {
        stageManager.switchScene(FxmlView.SUPPLY);
    }
    
    @FXML
    void openOrder(ActionEvent event) {
        stageManager.switchScene(FxmlView.USER);
    }

    @FXML
    private void saveUser(ActionEvent event) {

            if (orderId.getText() == null || "".equals(orderId.getText())) {
                if (true) {

                    User admin = new User();
                    admin.setStatus(getStatus());

                    User newUser = userService.save(admin);

                    saveAlert(newUser);
                } 

            } else {
                User admin = userService.find(Long.parseLong(orderId.getText()));
                admin.setStatus(getStatus());
                User updatedUser = userService.update(admin);
                updateAlert(updatedUser);
            }

            clearFields();
            loadUserDetails();

    }

    @FXML
    private void deleteOrder(ActionEvent event) {
        List<User> admin = orderTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            userService.deleteInBatch(admin);
        }

        loadUserDetails();
    }

    private void clearFields() {
        orderId.setText(null);
        status.getValue();
    }

    private void saveAlert(User admin) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The status has been updated.");
        alert.showAndWait();
    }

    private void updateAlert(User admin) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Status has been updated.");
        alert.showAndWait();
    }

    public String getStatus(){
        return status.getSelectionModel().getSelectedItem();
    }
    

    /*
	 *  Set All userTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colFlavor.setCellValueFactory(new PropertyValueFactory<>("flavor"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEdit.setCellFactory(cellFactory);
    }
    
    Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory
            = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
        @Override
        public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
            final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            User supply = getTableView().getItems().get(getIndex());
                            updateUser(supply);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateUser(User admin) {
                    orderId.setText(Long.toString(admin.getId()));
                    status.setValue(admin.getStatus());
                }
            };
            return cell;
        }
    };
    /*
	 *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        userList.clear();
        userList.addAll(userService.findAll());

        orderTable.setItems(userList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        orderTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();
        // Add all users into table
        loadUserDetails();
        
        status.getItems().addAll("pending","on-going","delivering","received");
        status.setValue("on-going");
    }

    
}
