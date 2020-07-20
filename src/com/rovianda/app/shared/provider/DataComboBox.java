package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXComboBox;
import com.rovianda.app.shared.models.OutputLots;
import com.rovianda.app.shared.models.ProductCatalog;
import com.rovianda.app.shared.models.ProductPresentation;
import com.rovianda.app.shared.service.packaging.ServicePackaging;
import com.rovianda.app.shared.service.productCatalog.ServiceProductCatalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Observable;

public class DataComboBox {

    public static void FillProductCatalog(JFXComboBox<ProductCatalog> comboBox){
        ObservableList<ProductCatalog> products = FXCollections.observableArrayList();
        Task <List<ProductCatalog>> productsTask = new Task<List<ProductCatalog>>() {
            @Override
            protected List<ProductCatalog> call() throws Exception {
                return ServiceProductCatalog.getProductsCatalog();
            }
        };
        Thread thread = new Thread(productsTask);
        thread.setDaemon(true);
        thread.start();
        productsTask.setOnSucceeded(e->{
            products.addAll(productsTask.getValue());
        });
        comboBox.setItems(products);
        comboBox.setConverter(new StringConverter<ProductCatalog>() {
            @Override
            public String toString(ProductCatalog object) {
                return object.getName();
            }

            @Override
            public ProductCatalog fromString(String string) {
                return null;
            }
        });
    }

    public static  void fillLotsById(JFXComboBox<OutputLots> comboBox, int productId){
        ObservableList<OutputLots> lots = FXCollections.observableArrayList();
        Task <List<OutputLots>> lotsTask = new Task<List<OutputLots>>() {
            @Override
            protected List<OutputLots> call() throws Exception {
                return ServiceProductCatalog.getOutputLotsById(productId);
            }
        };
        Thread thread = new Thread(lotsTask);
        thread.setDaemon(true);
        thread.start();
        lotsTask.setOnSucceeded(e->{
            lots.addAll(lotsTask.getValue());
        });
        comboBox.setItems(lots);
        comboBox.setConverter(new StringConverter<OutputLots>() {
            @Override
            public String toString(OutputLots object) {
                return object.getLotId();
            }

            @Override
            public OutputLots fromString(String string) {
                return null;
            }
        });
    }

    public static void fillPresentationsById(JFXComboBox<ProductPresentation> comboBox, int productId){
        ObservableList<ProductPresentation> presentations = FXCollections.observableArrayList();
        Task <List<ProductPresentation>> presentationsTask = new Task<List<ProductPresentation>>() {
            @Override
            protected List<ProductPresentation> call() throws Exception {
                return ServicePackaging.getPresentationsById(productId);
            }
        };
        Thread thread = new Thread(presentationsTask);
        thread.setDaemon(true);
        thread.start();
        presentationsTask.setOnSucceeded(e->{
            presentations.addAll(presentationsTask.getValue());
        });
        comboBox.setItems(presentations);
        comboBox.setConverter(new StringConverter<ProductPresentation>() {
            @Override
            public String toString(ProductPresentation object) {
                return object.getTypePresentation();
            }

            @Override
            public ProductPresentation fromString(String string) {
                return null;
            }
        });
    }
}
