package Controller;

import Model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    @FXML
    private VBox vBoxTela;

    @FXML
    private HBox hBoxTopo;

    @FXML
    private ComboBox<?> comboBoxAlgoritmo;

    @FXML
    private TextField textProcessadores;

    @FXML
    private TextField textProcessos;

    @FXML
    private TextField textQuantum;

    @FXML
    private Button btnIniciar;

    @FXML
    private Button btnNovoProcesso;

    @FXML
    private TableView<Processo> tableMaster;

    @FXML
    private TableColumn<Processo, String> tableColunaNome;

    @FXML
    private TableColumn<Processo, Integer> tableColunaPID;

    @FXML
    private TableColumn<Processo, Status> tableColunaStatus;

    @FXML
    private TableColumn<Processo, Prioridade> tableColunaPrioridade;

    @FXML
    private TableColumn<Processo, ?> tableColunaExec;

    @FXML
    private TableColumn<Processo, Integer> tableColunaExecTotal;

    @FXML
    private TableColumn<Processo, Integer> tableColunaExecRestante;

    @FXML
    private TableColumn<Processo, String> tableColunaDescr;

    //Lista do TableView
    ObservableList<Processo> processosTable;

    //Processador
    Processador processador;

    //Timer
    Timer tableAtualizador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Desabilitar o Quantum caso o algoritmo selecionado seja o SJF
        textQuantum.disableProperty().bind((comboBoxAlgoritmo.valueProperty().isNull()).or(Bindings.createBooleanBinding(() -> comboBoxAlgoritmo.valueProperty().getValue().equals("SJF"), comboBoxAlgoritmo.valueProperty())));
        //Aceitando somente números
        //TODO Validação de verdade
        textProcessadores.setTextFormatter(new TextFormatter<>(change -> {
            //noinspection Duplicates
            change.setText(change.getText().replaceAll("[^0-9]", ""));
            return change;
        }));

        textProcessos.setTextFormatter(new TextFormatter<>(change -> {
            change.setText(change.getText().replaceAll("[^0-9]", ""));
            return change;
        }));

        textQuantum.setTextFormatter(new TextFormatter<>(change -> {
            change.setText(change.getText().replaceAll("[^0-9]", ""));
            return change;
        }));

        //Configurando TableCell
        tableColunaNome.setCellValueFactory(new PropertyValueFactory<Processo, String>("Nome"));
        tableColunaPID.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("Id"));
        tableColunaStatus.setCellValueFactory(new PropertyValueFactory<Processo, Status>("Status"));
        tableColunaPrioridade.setCellValueFactory(new PropertyValueFactory<Processo, Prioridade>("Prioridade"));
        tableColunaExecTotal.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoTotal"));
        tableColunaExecRestante.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoRestante"));
        tableColunaDescr.setCellValueFactory(new PropertyValueFactory<Processo, String>("Descrição"));
        //Timer atualizador do tableview, 1s em 1s
        tableAtualizador = new Timer();
    }

    public void igniteEmul(ActionEvent actionEvent) {
        String algoritmo = comboBoxAlgoritmo.getValue().toString();
        switch (algoritmo) {
            case "SJF":
//                processador = new SJF(Integer.parseInt(textProcessadores.getText()), Integer.parseInt(textProcessos.getText()), 0);
                break;
            case "Round Robin":
//                processador = new RR(Integer.parseInt(textProcessadores.getText()), Integer.parseInt(textProcessos.getText()), Integer.parseInt(textQuantum.getText()));
                break;
            case "Fila de Prioridade":
//                processador = new FilaPrioridade(Integer.parseInt(textProcessadores.getText()), Integer.parseInt(textProcessos.getText()), Integer.parseInt(textQuantum.getText()));
                break;
        }
        //Bindando lista de processos do processador a tableView
        processosTable = FXCollections.observableArrayList(processador.getTodosProcessos());
        tableMaster.setItems(processosTable);

        //Inicializando atualizador da tabela
        tableAtualizador.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO Extrair esse código daqui
                processosTable = FXCollections.observableArrayList(processador.getTodosProcessos());
                tableMaster.setItems(processosTable);
                tableMaster.refresh();
            }
        }, 1000, 100);
    }

    public void addProcesso(ActionEvent actionEvent) {
        int idProcesso = processador.getMaiorId() + 1;
        Processo novo = new Processo(idProcesso);
        novo.setNovo(true);
        processador.addNovoProcesso(novo);
        processosTable = FXCollections.observableArrayList(processador.getTodosProcessos());
        tableMaster.setItems(processosTable);
        //Iluminar processo novo
//            tableMaster.getItems().get(tableMaster.getItems().indexOf(novo));
    }


//        //Todo Em mundo ideal conseguir pausar todas as Threads
//        public void pausar (ActionEvent actionEvent){
//            processador.pause();
//        }
//        //Todo Resumir todas as Threads
//        public void resumir (ActionEvent actionEvent){
//            Thread.currentThread().interrupt();
//        }
//        //Todo Fechar de vez todas as Threads
//        public void parar (ActionEvent actionEvent){
//            Thread.currentThread().interrupt();
//        }
}
