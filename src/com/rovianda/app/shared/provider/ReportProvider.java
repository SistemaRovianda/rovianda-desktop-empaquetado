package com.rovianda.app.shared.provider;

import com.rovianda.app.shared.service.report.ReportService;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

}
