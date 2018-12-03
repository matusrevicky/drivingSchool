package sk.upjs.drivingSchool;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.drivingSchool.controllers.RegisterSceneController;

public class App extends Application {

	private static Stage primaryStage;

	public void start(final Stage primaryStage) throws Exception {
		App.primaryStage = primaryStage;
		Scene scene = loadStartupScene();

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Scene loadStartupScene() throws IOException {
		RegisterSceneController loginSceneController = new RegisterSceneController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registerScreen.fxml"));
		fxmlLoader.setController(loginSceneController);
		Parent parentPane = fxmlLoader.load();

		return new Scene(parentPane);
	}

	public static void switchScene(Object controller, String fxmlPath) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
			fxmlLoader.setController(controller);

			Parent parentPane = fxmlLoader.load();
			primaryStage.getScene().setRoot(parentPane);
		} catch (IOException e) {
			System.err.println("Failed switching to scene: " + fxmlPath + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void showModalWindow(Object controller, String fxml) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
			fxmlLoader.setController(controller);
			Parent rootPane = fxmlLoader.load();
			Scene scene = new Scene(rootPane);

			Stage dialog = new Stage();
			dialog.setScene(scene);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
