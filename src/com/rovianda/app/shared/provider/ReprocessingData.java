package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.rovianda.app.shared.models.Method;
import com.rovianda.app.shared.models.Reprocessing;
import com.rovianda.app.shared.service.reprocessing.ServiceReprocessing;
import javafx.concurrent.Task;

public class ReprocessingData {

    public static void registerReprocessing(Reprocessing reprocessing, JFXButton button, JFXSpinner spinner, Method method) {

        System.out.println(reprocessing.toString());
        if (reprocessing.getAllergen().equals("")
                || reprocessing.getDate().equals("")
                || reprocessing.getLotId().equals("")
                || reprocessing.getWeight() <= 0) {
            ToastProvider.showToastError("Falta un valor para registrar el reproceso", 2000);

        } else {
            ModalProvider.showModal("Una vez guardada la información no se podrá editar, ¿Estás seguro que deseas guardar el reproceso?", () -> {
                button.setDisable(true);
                spinner.setVisible(true);

                ToastProvider.showToastInfo("Registrando reproceso", 2000);
                Task<String> registerReprocessingTask = new Task<String>() {
                    String message;

                    @Override
                    protected String call() throws Exception {
                        try {
                            if (ServiceReprocessing.registerReprocessing(reprocessing))

                                message = "success";
                        } catch (Exception e) {
                            message = e.getMessage();
                        }

                        return message;
                    }
                };
                Thread thread = new Thread(registerReprocessingTask);
                thread.setDaemon(true);
                thread.start();
                registerReprocessingTask.setOnSucceeded(e -> {
                    button.setDisable(false);
                    spinner.setVisible(false);
                    if (registerReprocessingTask.getValue().equals("success")) {
                        method.method();
                    } else {
                        ToastProvider.showToastError(registerReprocessingTask.getValue(), 3000);
                    }
                });
            });


        }
    }


}
