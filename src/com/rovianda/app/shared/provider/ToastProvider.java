package com.rovianda.app.shared.provider;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.css.PseudoClass;
import javafx.util.Duration;

public class ToastProvider {


    public static void showToastSuccess(String message, int duration){
        JFXSnackbar toast = new JFXSnackbar(ModalProvider.currentContainer);
        toast.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(message), Duration.millis(duration), new PseudoClass() {
            @Override
            public String getPseudoClassName() {
                return "toast-success";
            }
        }));
    }

    public static void showToastError(String message, int duration){
        JFXSnackbar toast = new JFXSnackbar(ModalProvider.currentContainer);
        toast.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(message), Duration.millis(duration), new PseudoClass() {
            @Override
            public String getPseudoClassName() {
                return "toast-error";
            }
        }));
    }

    public static void showToastInfo(String message, int duration){
        JFXSnackbar toast = new JFXSnackbar(ModalProvider.currentContainer);
        toast.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(message), Duration.millis(duration), null));
    }

}
