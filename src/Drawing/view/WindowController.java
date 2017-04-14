package Drawing.view;

import Drawing.model.DrawingAsync;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 * Created by M4teo on 30.03.2017.
 */
public class WindowController
{
    @FXML
    private TextField numberofPointsTxtFld;

    @FXML
    private Canvas canvas;

    @FXML
    private Button startBtn;

    @FXML
    private Button stopBtn;

    @FXML
    private ProgressBar calculationProgressBar;

    @FXML
    private Label ProgressTxtLabel;

    @FXML
    private Label resultLabel;

    private DrawingAsync aDrawingAsync;

    private int numberOfPoints = 0;

    private int numberOfPointsInArea = 0;

    private int MAX = 8;

    private int MIN = -8;

    @FXML
    private void handleStartBtnAction()
    {
        numberOfPoints = Integer.parseInt(numberofPointsTxtFld.getText());
        startBtn.setDisable(true);
        stopBtn.setDisable(false);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        aDrawingAsync = new DrawingAsync(gc, numberOfPoints);

        aDrawingAsync.setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent event)
            {
                numberOfPointsInArea = (int)aDrawingAsync.getValue();
                startBtn.setDisable(false);
                stopBtn.setDisable(true);
                resultLabel.setText(Double.toString((double)numberOfPointsInArea / (double) numberOfPoints * Math.pow(MAX - MIN, 2)));
            }
        });

        aDrawingAsync.setOnCancelled(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent event)
            {
                startBtn.setDisable(false);
                stopBtn.setDisable(true);
                resultLabel.setText("Programme aborted");
            }
        });

        calculationProgressBar.progressProperty().bind(aDrawingAsync.progressProperty());
        new Thread(aDrawingAsync).start();

}

    @FXML
    private void handleStopButtonAction()
    {
        stopBtn.setDisable(true);
        startBtn.setDisable(false);
        aDrawingAsync.cancel();
    }
}
