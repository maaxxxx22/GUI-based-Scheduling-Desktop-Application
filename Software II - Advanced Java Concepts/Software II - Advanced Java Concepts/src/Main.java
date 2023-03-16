import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.AppLogger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 *
 * Application entry point
 *
 * @author Okunta Braide
 */
public class Main extends Application {
    /**
 *
 * Start
 *
 * @param stage  the stage
 * @throws   IOException
 */
    @Override
    public void start(Stage stage) throws IOException {
        AppLogger.initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/ui/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/i18n", Locale.getDefault());

        stage.setTitle(resourceBundle.getString("app_title"));
        stage.setScene(scene);
        stage.show();
    }


    /**
     *
     * Main
     *
     * @param args  the args
     */
    public static void main(String[] args) {
        launch();
    }
}
