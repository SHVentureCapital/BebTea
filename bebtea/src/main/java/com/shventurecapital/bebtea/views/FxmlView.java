package com.shventurecapital.bebtea.views;

import java.util.ResourceBundle;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
public enum FxmlView {

    USER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Order.fxml";
        }
    }, ADMIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("admin.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Admin.fxml";
        }
    }, SUPPLY {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("supply.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Supplies.fxml";
        }
    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
