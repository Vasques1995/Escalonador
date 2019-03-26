package Controller;

import Model.Processo;
import Model.SJF;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SO.fxml"));
        primaryStage.setTitle("Hello JavaFX");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

//        ArrayList<Processo> processos = new ArrayList<>();
//        for (int r = 0; r < 10; r++) {
//            Processo processo = new Processo(r);
//            processos.add(processo);
//            System.out.println(processo.toString());
//        }
//        System.out.println("--------------------------------------------");
//        Collections.sort(processos);
//        for (int v = 0; v < 10; v++) {
//            System.out.println(processos.get(v).toString());
//        }
    }
}
//Fonte de ajuda https://github.com/douglasrafael/Escalonador
