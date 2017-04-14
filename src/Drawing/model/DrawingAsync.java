package Drawing.model;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by M4teo on 30.03.2017.
 */

public class DrawingAsync extends Task
{
    private GraphicsContext aGraphicsContext;
    int MAX = 8;
    int MIN = -8;
    private double x, y;
    private int positivePointsCounter =0;
    private int numberOfPoints;

    public DrawingAsync(GraphicsContext aGraphicsContext, int numberOfPoints)
    {
        this.aGraphicsContext = aGraphicsContext;
        this.numberOfPoints = numberOfPoints;
    }

    @Override
    protected Object call() throws Exception
    {
        aGraphicsContext.setFill(Color.WHITE);
        aGraphicsContext.fillRect(0, 0, aGraphicsContext.getCanvas().getWidth(), aGraphicsContext.getCanvas().getHeight());

        BufferedImage bufferedImage = new BufferedImage((int)aGraphicsContext.getCanvas().getWidth(),
                                                        (int)aGraphicsContext.getCanvas().getHeight(),
                                                         BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < numberOfPoints; i++)
        {
            Random rand = new Random();

            x = MIN + (MAX - MIN) * rand.nextDouble();
            y = MIN + (MAX - MIN) * rand.nextDouble();

            if(Equation.calc(x, y))
            {
                bufferedImage.setRGB(rescaleValues(x, MIN, MAX, 0, aGraphicsContext.getCanvas().getWidth()),
                                     rescaleValues(-y, MIN, MAX, 0, aGraphicsContext.getCanvas().getHeight()),
                                     java.awt.Color.RED.getRGB());

                positivePointsCounter++;
            }

            else
                bufferedImage.setRGB(rescaleValues(x, MIN, MAX, 0,aGraphicsContext.getCanvas().getWidth()),
                                     rescaleValues(-y, MIN, MAX, 0, aGraphicsContext.getCanvas().getHeight()),
                                     java.awt.Color.BLACK.getRGB());

            if(i%1000 == 0)
            {
                aGraphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
                updateProgress(i,numberOfPoints);
            }

            if(isCancelled())
                break;
        }

        aGraphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
        updateProgress(numberOfPoints,numberOfPoints);
        return positivePointsCounter;
    }

    private int rescaleValues(double x, double minA, double maxA, double minB, double maxB)
    {
        return (int)((maxB-minB)*(x-minA)/(maxA-minA)+minB);
    }
}
