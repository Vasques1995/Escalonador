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
    private ComboBox<?> comboBoxMemoria;

    @FXML
    private TextField textTamMemoria;

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
    private TableColumn<Processo, Integer> tableColunaEspaçoNucleos;

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
    private TableColumn<Processo, Integer> tableColunaEspaçoAptos;

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
    private TableColumn<Processo, Integer> tableColunaEspaçoTerminados;

    @FXML
    private TitledPane paneMemoria;

    @FXML
    private TableView<Bloco> tableMasterMemoria;

    @FXML
    private TableColumn<Bloco, Integer> tableColunaIDMemoria;

    @FXML
    private TableColumn<Bloco, ?> tableColunaSpace;

    @FXML
    private TableColumn<Bloco, Integer> tableColunaSpaceTotal;

    @FXML
    private TableColumn<Bloco, Integer> tableColunaSpaceUsado;

    @FXML
    private TableColumn<Processo, Integer> tableColunaIDProcesso;

    //Lista do TableView de procesos nos núcleos
    ObservableList<Processo> processosNucleo;
    //Lista do TableView de procesos aptos
    ObservableList<Processo> processosAptos;
    //Lista do TableView de processos terminados
    ObservableList<Processo> processosTerminados;
    //Lista do TableView de Blocos da Memória
    ObservableList<Bloco> blocosMemoria;

    //Processador
    Processador processador;

    //Timer
    Timer tableAtualizador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Desabilitar o Quantum caso o algoritmo selecionado seja o SJF
        textQuantum.disableProperty().bind((comboBoxAlgoritmo.valueProperty().isNull()).or(Bindings.createBooleanBinding(() -> comboBoxAlgoritmo.valueProperty().getValue().equals("SJF"), comboBoxAlgoritmo.valueProperty())));
        //TODO Desabilitar botão de iniciar caso nem todos os inputs tenham sido preenchidos
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
        textTamMemoria.setTextFormatter(new TextFormatter<>(change -> {
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
        tableColunaEspaçoNucleos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("QtdBytes"));
        //Configurando TableCell processos aptos
        tableColunaNomeAptos.setCellValueFactory(new PropertyValueFactory<Processo, String>("Nome"));
        tableColunaPIDAptos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("Id"));
        tableColunaStatusAptos.setCellValueFactory(new PropertyValueFactory<Processo, Status>("Status"));
        tableColunaPrioridadeAptos.setCellValueFactory(new PropertyValueFactory<Processo, Prioridade>("Prioridade"));
        tableColunaExecTotalAptos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoTotal"));
        tableColunaExecRestanteAptos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoRestante"));
        tableColunaDescrAptos.setCellValueFactory(new PropertyValueFactory<Processo, String>("Descrição"));
        tableColunaEspaçoAptos.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("QtdBytes"));
        //Configurando TableCell processos terminados
        tableColunaNomeTerm.setCellValueFactory(new PropertyValueFactory<Processo, String>("Nome"));
        tableColunaPIDTerm.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("Id"));
        tableColunaStatusTerm.setCellValueFactory(new PropertyValueFactory<Processo, Status>("Status"));
        tableColunaPrioridadeTerm.setCellValueFactory(new PropertyValueFactory<Processo, Prioridade>("Prioridade"));
        tableColunaExecTotalTerm.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoTotal"));
        tableColunaExecRestanteTerm.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("TempoRestante"));
        tableColunaDescrTerm.setCellValueFactory(new PropertyValueFactory<Processo, String>("Descrição"));
        tableColunaEspaçoTerminados.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("QtdBytes"));
        //Configurando TableCell blocos
        tableColunaIDMemoria.setCellValueFactory(new PropertyValueFactory<Bloco, Integer>("Identificador"));
        tableColunaSpaceTotal.setCellValueFactory(new PropertyValueFactory<Bloco, Integer>("EspaçoTotal"));
        tableColunaSpaceUsado.setCellValueFactory(new PropertyValueFactory<Bloco, Integer>("EspaçoUsado"));
        //Timer atualizador do tableview, 1s em 1s
        tableAtualizador = new Timer();
    }

    public void igniteEmul(ActionEvent actionEvent) {
        String algoritmo = comboBoxAlgoritmo.getValue().toString();
        int tamMemoria = Integer.parseInt(textTamMemoria.getText());
        System.out.println("DEBUG\nTamanho da Memória: " +  tamMemoria + "\nAlgoritmo de memória: " + comboBoxMemoria.getValue().toString());
        switch (algoritmo) {
            case "SJF":
                processador = new SJF(Integer.parseInt(textProcessadores.getText()), Integer.parseInt(textProcessos.getText()), 0, tamMemoria);
                break;
            case "Round Robin":
                processador = new RR(Integer.parseInt(textProcessadores.getText()), Integer.parseInt(textProcessos.getText()), Integer.parseInt(textQuantum.getText()), tamMemoria);
                break;
            case "Fila de Prioridade":
//                processador = new FilaPrioridade(Integer.parseInt(textProcessadores.getText()), Integer.parseInt(textProcessos.getText()), Integer.parseInt(textQuantum.getText(), Integer.parseInt(tamMemoria)));
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
        //Bindando lista de blocos da memória
        blocosMemoria = FXCollections.observableArrayList(processador.getMemória().getBlocos());
        tableMasterMemoria.setItems(blocosMemoria);

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

                blocosMemoria = FXCollections.observableArrayList(processador.getMemória().getBlocos());
                tableMasterMemoria.setItems(blocosMemoria);
                tableMasterMemoria.refresh();

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
