package com.rovianda.app.shared.provider;

import com.rovianda.app.shared.models.Method;
import com.rovianda.app.shared.service.report.ReportService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class ReportProvider {
    public static Pane currentContainer;

    private static File file ;

    public static void  buildReport(){
        String filename = "reporte_"+TableViewRegister.packingId+"_"+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Solo PDF","*.pdf");
        fileChooser.setInitialFileName(filename);
        fileChooser.getExtensionFilters().add(extension);
        fileChooser.setTitle("Guardar reporte "+filename);
        file = fileChooser.showSaveDialog(currentContainer.getScene().getWindow());
        ToastProvider.showToastInfo("Guardando reporte",1000);
        Task <InputStream> getReport = new Task<InputStream>() {
            @Override
            protected InputStream call() throws Exception {
                return ReportService.getReport(TableViewRegister.packingId);
            }
        };
        Thread thread = new Thread(getReport);
        thread.setDaemon(true);
        thread.start();
        getReport.setOnSucceeded(e->{
            ToastProvider.showToastInfo("Espere guardando reporte", 1000);
            try {
                InputStream in = getReport.getValue();
                OutputStream out = new FileOutputStream(file);
                byte[] buff = new byte[in.available()];  // how much of the blob to read/write at a time
                int len = 0;
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
                out.close();
                ToastProvider.showToastSuccess("Reporte descargado exitosamente",1500);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        getReport.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),1500);
            System.out.println(e.getSource().getException().getMessage());
        });

    }

    public static void  buildReportReturn(Method method){

        String filename = "reporte_"+ReturnProductProvider.id+"_"+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Solo PDF","*.pdf");
        fileChooser.setInitialFileName(filename);
        fileChooser.getExtensionFilters().add(extension);
        fileChooser.setTitle("Guardar reporte "+filename);
        file = fileChooser.showSaveDialog(currentContainer.getScene().getWindow());
        ToastProvider.showToastInfo("Guardando reporte",1000);
        Task <InputStream> getReport = new Task<InputStream>() {
            @Override
            protected InputStream call() throws Exception {
                return ReportService.getReportReturns(ReturnProductProvider.id);
            }
        };
        Thread thread = new Thread(getReport);
        thread.setDaemon(true);
        thread.start();
        getReport.setOnSucceeded(e->{
            method.method();
            ToastProvider.showToastInfo("Espere guardando reporte", 1000);
            ReturnProductProvider.id = new Long(0);
            try {
                InputStream in = getReport.getValue();
                OutputStream out = new FileOutputStream(file);
                byte[] buff = new byte[in.available()];  // how much of the blob to read/write at a time
                int len = 0;
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
                out.close();
                ToastProvider.showToastSuccess("Reporte descargado exitosamente",1500);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        getReport.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),1500);
            System.out.println(e.getSource().getException().getMessage());
        });

    }

    public static void  buildReportDelivered(final int deliveredId){
        ToastProvider.showToastInfo("Obteniendo ticket",1000);
        Task <InputStream> getReport = new Task<InputStream>() {
            @Override
            protected InputStream call() throws Exception {
                return ReportService.getReportDelivery(deliveredId);
            }
        };
        Thread thread = new Thread(getReport);
        thread.setDaemon(true);
        thread.start();
        getReport.setOnSucceeded(e->{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        PDDocument document = PDDocument.load(getReport.getValue());
                        PrintService myPrinterService = findPrintService("");
                        PrinterJob job = PrinterJob.getPrinterJob();
                        job.setPageable(new PDFPageable(document));
                        job.setPrintService(myPrinterService);
                        job.print();
                        document.close();
                    } catch (Exception exception) {
                        ToastProvider.showToastError("No se encontro impresora", 1500);
                    }
                }});
            ToastProvider.showToastInfo("Espere guardando reporte", 1000);
            String filename = "reporte_entrega_a_vendedor" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Solo PDF", "*.pdf");
            fileChooser.setInitialFileName(filename);
            fileChooser.getExtensionFilters().add(extension);
            fileChooser.setTitle("Guardar reporte " + filename);
            file = fileChooser.showSaveDialog(currentContainer.getScene().getWindow());
            try {
                InputStream in = getReport.getValue();
                OutputStream out = new FileOutputStream(file);
                byte[] buff = new byte[in.available()];  // how much of the blob to read/write at a time
                int len = 0;
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
                out.close();
                ToastProvider.showToastSuccess("Reporte descargado exitosamente",1500);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        /*getReport.setOnSucceeded(e->{

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            PDDocument document = PDDocument.load(getReport.getValue());
                            PrintService myPrinterService = findPrintService("");
                            PrinterJob job = PrinterJob.getPrinterJob();
                            job.setPageable(new PDFPageable(document));
                            job.setPrintService(myPrinterService);
                            job.print();
                            document.close();
                        } catch (Exception exception) {
                            ToastProvider.showToastError("No se encontro impresora",1500);
                        }

                        if(getReport.getValue()!=null) {
                            String filename = "reporte_entrega_a_vendedor" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            FileChooser fileChooser = new FileChooser();
                            FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Solo PDF", "*.pdf");
                            fileChooser.setInitialFileName(filename);
                            fileChooser.getExtensionFilters().add(extension);
                            fileChooser.setTitle("Guardar reporte " + filename);
                            file = fileChooser.showSaveDialog(currentContainer.getScene().getWindow());
                            try{
                                System.out.println("Size bytes:"+getReport.getValue());
                                InputStream in = getReport.getValue();
                                OutputStream out = new FileOutputStream(file);
                                byte[] buff = new byte[in.available()];  // how much of the blob to read/write at a time
                                int len = 0;
                                while ((len = in.read(buff)) != -1) {
                                    out.write(buff, 0, len);
                                }
                                out.close();
                                ToastProvider.showToastSuccess("Reporte descargado exitosamente", 1500);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                });


        });*/

        getReport.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),1500);
            System.out.println(e.getSource().getException().getMessage());
        });


    }

    public static void  buildReportReprocessing(Method method){

        String filename = "reporte_reproceso_"+ReprocessingData.idReport+"_"+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Solo PDF","*.pdf");
        fileChooser.setInitialFileName(filename);
        fileChooser.getExtensionFilters().add(extension);
        fileChooser.setTitle("Guardar reporte de reproceso "+filename);
        file = fileChooser.showSaveDialog(currentContainer.getScene().getWindow());
        ToastProvider.showToastInfo("Guardando reporte de reproceso",1000);
        Task <InputStream> getReport = new Task<InputStream>() {
            @Override
            protected InputStream call() throws Exception {
                return ReportService.getReportReprocessing(ReprocessingData.idReport);
            }
        };
        Thread thread = new Thread(getReport);
        thread.setDaemon(true);
        thread.start();
        getReport.setOnSucceeded(e->{
            method.method();
            ToastProvider.showToastInfo("Espere guardando reporte", 1000);
            ReturnProductProvider.id = new Long(0);
            try {
                InputStream in = getReport.getValue();
                OutputStream out = new FileOutputStream(file);
                byte[] buff = new byte[in.available()];  // how much of the blob to read/write at a time
                int len = 0;
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
                out.close();
                ToastProvider.showToastSuccess("Reporte descargado exitosamente",1500);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        getReport.setOnFailed(e->{
            ToastProvider.showToastError(e.getSource().getException().getMessage(),1500);
            System.out.println(e.getSource().getException().getMessage());
        });

    }

    private static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getSupportedDocFlavors().toString().contains("pdf")){
                return printService;
            }
        }
        return null;
    }

}
