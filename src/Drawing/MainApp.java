package Drawing;
/**
 * Created by M4teo on 20.03.2017.
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane WindowLayout;

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Drawing");

        initializeWindow();
    }

   public void initializeWindow()
   {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Window.fxml"));
            WindowLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(WindowLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}