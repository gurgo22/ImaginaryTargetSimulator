package its;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author KGL
 */
public class ITS extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/its/test.fxml"));
        Parent root = loader.load();

        Scene baseScene = new Scene(root);

        String css = this.getClass().getResource("base.css").toExternalForm();
        baseScene.getStylesheets().add(css);

        //primaryStage.setMaximized(true);
        primaryStage.setScene(baseScene);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
