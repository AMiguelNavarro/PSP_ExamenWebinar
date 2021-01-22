package Task;

import javafx.concurrent.Task;

public class CuentaAtrasTask extends Task<Void> {

    private int valorInicial, valorFinal;
    private boolean pausado;

    public CuentaAtrasTask(int valorInicial, int valorFinal) {
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
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

        int porcentaje;

        for (int i = valorInicial; i >= valorFinal ; i--) {


            while (pausado) {
                Thread.sleep(1000);
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

            Thread.sleep(1000);

            updateProgress(i,valorFinal);


        }

        updateMessage("Finalizado");
        updateProgress(1,1);

        return null;
    }

}
