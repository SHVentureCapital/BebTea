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
import com.shventurecapital.bebtea.models.Supply;
import com.shventurecapital.bebtea.services.impl.SupplyService;
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
public class SupplyController implements Initializable {
    @FXML
    private TableView<Supply> itemTable;
    
    @FXML
    private TableColumn<Supply, Long> colId;

    @FXML
    private TableColumn<Supply, String> colItem;

    @FXML
    private TableColumn<Supply, String> colSize;

    @FXML
    private TableColumn<Supply, Integer> colQuantity;
    
    @FXML
    private TableColumn<Supply, Boolean> colEdit;

     @FXML
    private Label itemId;

    @FXML
    private TextField item;

    @FXML
    private TextField size;

    @FXML
    private TextField quantity;

    @FXML
    private Button btAdd;

    @FXML
    private Button reset;

    @FXML
    private Button btDelete;
    
    @FXML
    private Button btAdmin;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private SupplyService supplyService;
    
    @FXML
    void Select(ActionEvent event) {
    }
    
        private ObservableList<Supply> userList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }


    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    void openAdmin(ActionEvent event) {
        stageManager.switchScene(FxmlView.ADMIN);
    }

    @FXML
    private void addItem(ActionEvent event) {

        if (validate("Product Name", getItem(), "(?:\\s*[a-zA-Z0-9,_\\.\\077\\0100\\*\\+\\&\\#\\'\\~\\;\\-\\!\\@\\;]{2,}\\s*)*")) {
            if (itemId.getText() == null || "".equals(itemId.getText())) {
                if (true) {

                    Supply supply = new Supply();
                    supply.setItem(getItem());
                    supply.setSize(getSize());
                    supply.setQuantity(getQuantity());
                    Supply newUser = supplyService.save(supply);
                    saveAlert(newUser);
                } 

            } else {
                Supply supply = supplyService.find(Long.parseLong(itemId.getText()));
                supply.setItem(getItem());
                supply.setSize(getSize());
                supply.setQuantity(getQuantity());
                Supply updatedUser = supplyService.update(supply);
                updateAlert(updatedUser);
            }

            clearFields();
            loadUserDetails();
        }
    }

    @FXML
    private void deleteItem(ActionEvent event) {
        List<Supply> supply = itemTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            supplyService.deleteInBatch(supply);
        }

        loadUserDetails();
    }

    private void clearFields() {
        itemId.setText(null);
        item.clear();
        size.clear();
        quantity.clear();
    }

    private void saveAlert(Supply supply) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Supply " + supply.getItem() + "\nSupplt Size:  " + supply.getSize() + "\nSupply Quantity: "+ supply.getQuantity() + " has been created and \n id is " + supply.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Supply supply) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Supply " + supply.getItem() + " " + supply.getSize() +""+ supply.getQuantity()+ " has been updated.");
        alert.showAndWait();
    }

    public String getItem() {
        return item.getText();
    }

    public String getSize() {
        return size.getText();
    }
    
    public String getQuantity(){
        return quantity.getText();
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
        colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Supply, Boolean>, TableCell<Supply, Boolean>> cellFactory
            = new Callback<TableColumn<Supply, Boolean>, TableCell<Supply, Boolean>>() {
        @Override
        public TableCell<Supply, Boolean> call(final TableColumn<Supply, Boolean> param) {
            final TableCell<Supply, Boolean> cell = new TableCell<Supply, Boolean>() {
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
                            Supply supply = getTableView().getItems().get(getIndex());
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

                private void updateUser(Supply supply) {
                    itemId.setText(Long.toString(supply.getId()));
                    item.setText(supply.getItem());
                    size.setText(supply.getSize());
                    quantity.setText(supply.getQuantity());
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
        userList.addAll(supplyService.findAll());

        itemTable.setItems(userList);
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

        itemTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();
        // Add all users into table
        loadUserDetails();;

    }
}
