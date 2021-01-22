package Task;

import Recursos.Alertas;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;

public class AppTask extends Task<Void> {

    private int valorInicial, valorFinal;
    private boolean pausado;
    private boolean iniciarCuentaAtras;

    public AppTask(int valorInicial, int valorFinal, boolean iniciarCuentaAtras) {
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.iniciarCuentaAtras = iniciarCuentaAtras;
        pausado = false;
    }

    public boolean isPausado() {
        return pausado;
    }
    public void setPausado(boolean pausado) {
        this.pausado = pausado;
    }




    @Override
    protected Void call() throws Exception {

        if (!iniciarCuentaAtras) {

            cuentaNormal();

        } else {

            cuentaAtras();

        }

        return null;
    }

    public void cuentaAtras() {

        int porcentaje;
        double progreso = valorFinal;

        for (int i = valorInicial; i >= valorFinal ; i--) {


            while (pausado) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateMessage("Tarea PAUSADA");
            }

            if (isCancelled()) {
                break;
            }

            porcentaje = (int) (progreso * 100 / valorInicial);

            if (porcentaje >= 50 && porcentaje < 75) {
                updateMessage("Cuenta atrás -> " + i + "\n" + porcentaje + " %" + "\n" + "Vas por el 50 %" );
            } else if (porcentaje >= 75) {
                updateMessage("Cuenta atrás -> " + i + "\n" + porcentaje + " %" + "\n" + "Vas por el 75 %" );
            } else {
                updateMessage("Cuenta atrás -> " + i + "\n" + porcentaje + " %" );
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            progreso++;

            updateProgress(progreso,valorInicial);


        }

        updateMessage("Finalizado");
        updateProgress(1,1);

    }

    public void cuentaNormal() {

        int porcentaje;

        for (int i = valorInicial; i <= valorFinal ; i++) {


            while (pausado) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateMessage("Tarea PAUSADA");
            }

            if (isCancelled()) {
                break;
            }

            porcentaje = i * 100 / valorFinal;

            if (porcentaje >= 50 && porcentaje < 75) {
                updateMessage(i + " de " + valorFinal + "\n" + porcentaje + " %" + "\n" + "Vas por el 50 %" );
            } else if (porcentaje >= 75) {
                updateMessage(i + " de " + valorFinal + "\n" + porcentaje + " %" + "\n" + "Vas por el 75 %" );
            } else {
                updateMessage(i + " de " + valorFinal + "\n" + porcentaje + " %" );
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            updateProgress(i,valorFinal);


        }

        updateMessage("Finalizado");
        updateProgress(1,1);

    }

}
