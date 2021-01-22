package Controlador;

import Recursos.Alertas;
import Task.AppTask;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AppControlador implements Initializable {

    public TextField tfValorInicial, tfValorFinal;
    public Button btIniciar, btParar, btCancelar;
    public ProgressBar pbProgreso;
    public Label lbProgreso;
    public CheckBox cbCuentaAtras;

    private AppTask task;
    private boolean pausado;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfValorInicial.requestFocus();
        modoInicial(true);
        pausado = false;
    }



    @FXML
    public void iniciar(Event event) {

        boolean iniciarCuentaAtras = cbCuentaAtras.isSelected();

        if (iniciarCuentaAtras) {

        }

        int valorInicial, valorFinal;

        valorInicial = Integer.parseInt(tfValorInicial.getText());
        valorFinal = Integer.parseInt(tfValorFinal.getText());

        if (valorFinal <= valorInicial && !iniciarCuentaAtras) {
            Alertas.mostrarError("El valor inicial no puede ser mayor que el valor final");
            modoError();
            return;
        }

        modoEjecucion(true);

        task = new AppTask(valorInicial, valorFinal);

        task.stateProperty().addListener((observableValue, viejoEstado, nuevoEstado) -> {

            if (nuevoEstado == Worker.State.CANCELLED) {
                lbProgreso.setText("Tarea CANCELADA");
                modoInicial(true);
                pbProgreso.setStyle("-fx-accent: red;");
            }

            if (nuevoEstado == Worker.State.RUNNING) {
                pbProgreso.setStyle("-fx-accent: blue;");
                pbProgreso.progressProperty().unbind();
                pbProgreso.progressProperty().bind(task.progressProperty());
            }

            if (nuevoEstado == Worker.State.SUCCEEDED) {
                Alertas.mostrarInformacion("Finalizado con éxito!!");
                pbProgreso.setStyle("-fx-accent: green;");
                modoInicial(true);
            }

        });

        task.messageProperty().addListener((observableValue, viejoValor, nuevoValor) ->  {
            lbProgreso.setText(nuevoValor);
        });

        new Thread(task).start();


    }

    @FXML
    public void parar(Event event) {

        if (!task.isPausado()) {
            task.setPausado(true);
            btParar.setText("Reanudar");
        } else if (task.isPausado()) {
            task.setPausado(false);
            btParar.setText("Parar");
        }

    }

    @FXML
    public void cancelar(Event event) {

        task.cancel();

    }


    public void modoError() {
        tfValorFinal.setText("");
        tfValorInicial.setText("");
        tfValorInicial.requestFocus();
    }

    public void modoInicial(boolean verdadero) {
        btParar.setDisable(verdadero);
        btCancelar.setDisable(verdadero);

        btIniciar.setDisable(!verdadero);

        tfValorInicial.setText("");
        tfValorFinal.setText("");
    }

    public void modoEjecucion(boolean verdadero) {
        btIniciar.setDisable(verdadero);

        btCancelar.setDisable(!verdadero);
        btParar.setDisable(!verdadero);
    }

}
