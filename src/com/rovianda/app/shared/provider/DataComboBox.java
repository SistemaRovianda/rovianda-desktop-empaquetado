package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXComboBox;
import com.rovianda.app.shared.models.*;
import com.rovianda.app.shared.service.packaging.ServicePackaging;
import com.rovianda.app.shared.service.productCatalog.ServiceProductCatalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.util.StringConverter;
import java.util.List;
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
            ToastProvider.showToastSuccess("Productos obtenidos",1500);
            if(productsTask.getValue().size() >0)
                products.addAll(productsTask.getValue());
            else
                ToastProvider.showToastInfo("No existen productos por el momento",1500);
        });

        productsTask.setOnFailed(event -> {
            ToastProvider.showToastError(event.getSource().getException().getMessage(),1500);
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

    public static void fillProductsCatalogReturn (JFXComboBox<ProductCatalogReturn> comboBox){
        ObservableList<ProductCatalogReturn> products = FXCollections.observableArrayList();
        Task <List<ProductCatalogReturn>> productsTask = new Task<List<ProductCatalogReturn>>() {
            @Override
            protected List<ProductCatalogReturn> call() throws Exception {
                return ServiceProductCatalog.getProductsCatalogReturns();
            }
        };
        Thread thread = new Thread(productsTask);
        thread.setDaemon(true);
        thread.start();
        productsTask.setOnSucceeded(e->{
            ToastProvider.showToastSuccess("Productos obtenidos",1500);
            if(productsTask.getValue().size() >0)
                products.addAll(productsTask.getValue());
            else
                ToastProvider.showToastInfo("No existen productos por el momento",1500);
        });

        productsTask.setOnFailed(event -> {
            ToastProvider.showToastError(event.getSource().getException().getMessage(),1500);
        });

        comboBox.setItems(products);
        comboBox.setConverter(new StringConverter<ProductCatalogReturn>() {
            @Override
            public String toString(ProductCatalogReturn object) {
                return object.getName();
            }

            @Override
            public ProductCatalogReturn fromString(String string) {
                return null;
            }
        });

    }

    public static void fillAreas(JFXComboBox <Area> comboBox){
        ObservableList<Area> areas = FXCollections.observableArrayList();
        areas.addAll(new Area("MOLIENDA","Molienda"),
                     new Area("INJECCIONTENDERIZADO","Inyeccion/tenderizado"),
                     new Area("DESCONGELAMIENTO","Descongelamiento"),
                     new Area("EMBUTIDO","Embutido"),
                     new Area("ACONDICIONAMIENTO","Acondicionamiento")
                );
        comboBox.setItems(areas);
        comboBox.setConverter(new StringConverter<Area>() {
            @Override
            public String toString(Area object) {
                return object.getAreaTag();
            }

            @Override
            public Area fromString(String string) {
                return null;
            }
        });

    }

    public static void fillOptions(JFXComboBox <OptionOrder> comboBox){
        ObservableList<OptionOrder> options = FXCollections.observableArrayList();
        options.addAll( new OptionOrder("No", false), new OptionOrder("Si",true) );
        comboBox.setItems(options);
        comboBox.setConverter(new StringConverter<OptionOrder>() {
            @Override
            public String toString(OptionOrder object) {
                return object.getTag();
            }

            @Override
            public OptionOrder fromString(String string) {
                return null;
            }
        });
    }



    public static void fillPresentationsById(JFXComboBox<ProductPresentation> comboBox, int productId){
        ToastProvider.showToastInfo("Obteniendo presentaciones del producto",1500);
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
            ToastProvider.showToastInfo("Presentaciones obtenidas",1500);
            if(presentationsTask.getValue().size() >0){
                presentations.addAll(presentationsTask.getValue());
                comboBox.setDisable(false);
            }
            else
                ToastProvider.showToastInfo("El producto no cuenta con presentaciones",1500);
        });
        presentationsTask.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),1500);
        });
        comboBox.setItems(presentations);
        comboBox.setConverter(new StringConverter<ProductPresentation>() {
            @Override
            public String toString(ProductPresentation object) {
                return " "+object.getPresentation()+" "+object.getTypePresentation();
            }

            @Override
            public ProductPresentation fromString(String string) {
                return null;
            }
        });
    }

    public static void fillPresentationsReturnsById(JFXComboBox<PresentationReturn> comboBox, int productId){
        ToastProvider.showToastInfo("Obteniendo presentaciones del producto",1500);
        ObservableList<PresentationReturn> presentations = FXCollections.observableArrayList();
        Task <List<PresentationReturn>> presentationsTask = new Task<List<PresentationReturn>>() {
            @Override
            protected List<PresentationReturn> call() throws Exception {
                return ServicePackaging.getPresentationsReturnById(productId);
            }
        };
        Thread thread = new Thread(presentationsTask);
        thread.setDaemon(true);
        thread.start();
        presentationsTask.setOnSucceeded(e->{
            ToastProvider.showToastInfo("Presentaciones obtenidas",1500);
            if(presentationsTask.getValue().size() >0){
                presentations.addAll(presentationsTask.getValue());
                comboBox.setDisable(false);
            }
            else
                ToastProvider.showToastInfo("El producto no cuenta con presentaciones",1500);
        });
        presentationsTask.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),1500);
        });
        comboBox.setItems(presentations);
        comboBox.setConverter(new StringConverter<PresentationReturn>() {
            @Override
            public String toString(PresentationReturn object) {
                return " "+object.getPresentation()+" "+object.getPresentationType();
            }

            @Override
            public PresentationReturn fromString(String string) {
                return null;
            }
        });

    }
}
