package Controller;

import Model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
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

public class Controller2 implements Initializable {

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
    private TitledPane paneNucleos;

    @FXML
    private TableView<Processo> tableMasterNucleos;

    @FXML
    private TableColumn<Processo, String> tableColunaNomeNucleos;

    @FXML
    private TableColumn<Processo, Integer> tableColunaPIDNucleos;

    @FXML
    private TableColumn<Processo, Status> tableColunaStatusNucleos;

    @FXML
    private TableColumn<Processo, Prioridade> tableColunaPrioridadeNucleos;

    @FXML
    private TableColumn<Processo, ?> tableColunaExecNucleos;

    @FXML
    private TableColumn<Processo, Integer> tableColunaExecTotalNucleos;

    @FXML
    private TableColumn<Processo, Integer> tableColunaExecRestanteNucleos;

    @FXML
    private TableColumn<Processo, String> tableColunaDescrNucleos;

    @FXML
    private TitledPane paneAptos;

    @FXML
    private TableView<Processo> tableMasterAptos;

    @FXML
    private TableColumn<Processo, String> tableColunaNomeAptos;

    @FXML
    private TableColumn<Processo, Integer> tableColunaPIDAptos;

    @FXML
    private TableColumn<Processo, Status> tableColunaStatusAptos;

    @FXML
    private TableColumn<Processo, Prioridade> tableColunaPrioridadeAptos;

    @FXML
    private TableColumn<Processo, ?> tableColunaExecAptos;

    @FXML
    private TableColumn<Processo, Integer> tableColunaExecTotalAptos;

    @FXML
    private TableColumn<Processo, Integer> tableColunaExecRestanteAptos;

    @FXML
    private TableColumn<Processo, String> tableColunaDescrAptos;

    @FXML
    private TitledPane paneTerminados;

    @FXML
    private TableView<Processo> tableMasterTerminados;

    @FXML
    private TableColumn<Processo, String> tableColunaNomeTerm;

    @FXML
    private TableColumn<Processo, Integer> tableColunaPIDTerm;

    @FXML
    private TableColumn<Processo, Status> tableColunaStatusTerm;

    @FXML
    private TableColumn<Processo, Prioridade> tableColunaPrioridadeTerm;

    @FXML
    private TableColumn<Processo, ?> tableColunaExecTerm;

    @FXML
    private TableColumn<Processo, Integer> tableColunaExecTotalTerm;

    @FXML
    private TableColumn<Processo, Integer> tableColunaExecRestanteTerm;

    @FXML
    private TableColumn<Processo, String> tableColunaDescrTerm;

    @FXML
    private TitledPane paneMemoria;

    @FXML
    private TableView<?> tableMasterMemoria;

    @FXML
    private TableColumn<?, ?> tableColunaIDMemoria;

    @FXML
    private TableColumn<?, ?> tableColunaSpace;

    @FXML
    private TableColumn<?, ?> tableColunaSpaceTotal;

    @FXML
    private TableColumn<?, ?> tableColunaSpaceUsado;

    //Lista do TableView de procesos nos núcleos
    ObservableList<Processo> processosNucleo;
    //Lista do TableView de procesos aptos
    ObservableList<Processo> processosAptos;
    //Lista do TableView de processos terminados
    ObservableList<Processo> processosTerminados;

    //Processador
    Processador processador;

    //Timer
    Timer tableAtualizador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        comboBoxAlgoritmo.
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

        //Configurando TableCell Núcleos
        tableColunaNomeNucleos.setCellValueFactory(new PropertyValueFactory<Processo, String>("Nome"));
        tableColunaPIDNucleos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("Id"));
        tableColunaStatusNucleos.setCellValueFactory(new PropertyValueFactory<Processo, Status>("Status"));
        tableColunaPrioridadeNucleos.setCellValueFactory(new PropertyValueFactory<Processo, Prioridade>("Prioridade"));
        tableColunaExecTotalNucleos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoTotal"));
        tableColunaExecRestanteNucleos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoRestante"));
        tableColunaDescrNucleos.setCellValueFactory(new PropertyValueFactory<Processo, String>("Descrição"));
        //Configurando TableCell processos aptos
        tableColunaNomeAptos.setCellValueFactory(new PropertyValueFactory<Processo, String>("Nome"));
        tableColunaPIDAptos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("Id"));
        tableColunaStatusAptos.setCellValueFactory(new PropertyValueFactory<Processo, Status>("Status"));
        tableColunaPrioridadeAptos.setCellValueFactory(new PropertyValueFactory<Processo, Prioridade>("Prioridade"));
        tableColunaExecTotalAptos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoTotal"));
        tableColunaExecRestanteAptos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoRestante"));
        tableColunaDescrAptos.setCellValueFactory(new PropertyValueFactory<Processo, String>("Descrição"));
        //Configurando TableCell processos terminados
        tableColunaNomeTerm.setCellValueFactory(new PropertyValueFactory<Processo, String>("Nome"));
        tableColunaPIDTerm.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("Id"));
        tableColunaStatusTerm.setCellValueFactory(new PropertyValueFactory<Processo, Status>("Status"));
        tableColunaPrioridadeTerm.setCellValueFactory(new PropertyValueFactory<Processo, Prioridade>("Prioridade"));
        tableColunaExecTotalTerm.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoTotal"));
        tableColunaExecRestanteTerm.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoRestante"));
        tableColunaDescrTerm.setCellValueFactory(new PropertyValueFactory<Processo, String>("Descrição"));
        //Timer atualizador do tableview, 1s em 1s
        tableAtualizador = new Timer();
    }

    public void igniteEmul(ActionEvent actionEvent) {
        String algoritmo = comboBoxAlgoritmo.getValue().toString();
        switch (algoritmo) {
            case "SJF":
                processador = new SJF(Integer.parseInt(textProcessadores.getText()), Integer.parseInt(textProcessos.getText()), 0);
                break;
            case "Round Robin":
                processador = new RR(Integer.parseInt(textProcessadores.getText()), Integer.parseInt(textProcessos.getText()), Integer.parseInt(textQuantum.getText()));
                break;
            case "Fila de Prioridade":
                processador = new FilaPrioridade(Integer.parseInt(textProcessadores.getText()), Integer.parseInt(textProcessos.getText()), Integer.parseInt(textQuantum.getText()));
                break;
        }
        //Bindando lista de processos contidos no núcleo a TableView dos núcleos
        processosNucleo = FXCollections.observableArrayList(processador.getProcessosNucleo());
        tableMasterNucleos.setItems(processosNucleo);
        //Bindando lista de processos contidos na lista de aptos a TableView dos aptos
        processosAptos = FXCollections.observableArrayList(processador.getProcessosAptos());
        tableMasterAptos.setItems(processosAptos);
        //Bindando lista de processos contidos no núcleo a TableView dos terminados
        processosTerminados = FXCollections.observableArrayList(processador.getProcessosTerminados());
        tableMasterTerminados.setItems(processosTerminados);

        //Inicializando atualizador da tabela
        tableAtualizador.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO Extrair esse código daqui
                processosNucleo = FXCollections.observableArrayList(processador.getProcessosNucleo());
                tableMasterNucleos.setItems(processosNucleo);
                tableMasterNucleos.refresh();

                processosAptos = FXCollections.observableArrayList(processador.getProcessosAptos());
                tableMasterAptos.setItems(processosAptos);
                tableMasterAptos.refresh();

                processosTerminados = FXCollections.observableArrayList(processador.getProcessosTerminados());
                tableMasterTerminados.setItems(processosTerminados);
                tableMasterTerminados.refresh();

            }
        }, 1000, 100);
    }

    public void addProcesso(ActionEvent actionEvent) {
        int idProcesso = processador.getMaiorId() + 1;
        Processo novo = new Processo(idProcesso);
        novo.setNovo(true);
        processador.addNovoProcesso(novo);
//        processosTable = FXCollections.observableArrayList(processador.getTodosProcessos());
//        tableMasterNucleos.setItems(processosTable);
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
