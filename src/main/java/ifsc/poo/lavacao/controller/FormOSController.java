package ifsc.poo.lavacao.controller;

import ifsc.poo.lavacao.model.dao.OsDAO;
import ifsc.poo.lavacao.model.dao.ServicoDAO;
import ifsc.poo.lavacao.model.dao.VeiculoDAO;
import ifsc.poo.lavacao.model.domain.*;
import ifsc.poo.lavacao.utils.FormatadorDeTextoBuilder;
import ifsc.poo.lavacao.utils.Regex;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormOSController extends FormController<OrdemDeServico, OsDAO> implements Initializable {

    public VBox itemsBox;
    public Button btnAdicionarServico;
    public HBox buscaBox;

    @FXML
    private TextField buscaPlaca;
    @FXML
    private ComboBox<Veiculo> selectVeiculo;


    @FXML
    private TextField placa;
    @FXML
    private TextField cor;
    @FXML
    private TextField categoria;
    @FXML
    private TextField modelo;
    @FXML
    private TextField pontuacao;
    @FXML
    private TextField cliente;
    @FXML
    private TextField marca;
    @FXML
    private TextField observacoes;


    @FXML
    private ComboBox<Servico> selectServico;
    @FXML
    private TextField observacaoServico;
    @FXML
    private TextField valorServico;
    @FXML
    private TextField valorOriginal;


    @FXML
    private TableView<ItemOS> tableServicos;
    @FXML
    private TableColumn<ItemOS, String> tbServico;
    @FXML
    private TableColumn<ItemOS, String> tbValorOriginal;
    @FXML
    private TableColumn<ItemOS, String> tbObservacoes;
    @FXML
    private TableColumn<ItemOS, String> tableValorFinal;


    @FXML
    private TextField inputDesconto;
    @FXML
    private TextField total;
    @FXML
    private ComboBox<EStatus> selectStatus;

    @FXML
    private Button btnSalvar;


    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    private final FormatadorDeTextoBuilder formatador = new FormatadorDeTextoBuilder();


    private final List<TextField> dadosVeiculoTextField = new ArrayList<>();

    @FXML
    public void handleSelecionar(ActionEvent actionEvent) {
        Veiculo veiculoSelecionado = veiculoDAO.getById(selectVeiculo.getValue().getId());

        model = new OrdemDeServico();
        model.setVeiculo(veiculoSelecionado);
        model.setAgenda(LocalDate.now());
        model.setStatus(EStatus.ABERTA);

        loadServicos();
        populateFieldsFromModel();

        itemsBox.setDisable(false);
        btnSalvar.setDisable(false);
    }


    @FXML
    public void handleStatusChange(ActionEvent actionEvent) {
        this.model.setStatus(selectStatus.getValue());
    }

    private void loadStatus() {
        selectStatus.setItems(FXCollections.observableArrayList(EStatus.values()));
    }

    @FXML
    public void handleAdicionarServico(ActionEvent actionEvent) {
        // Validar Dados
        if(selectServico.getValue() == null) return;

        // Construir ItemOS
        ItemOS itemOS = buildNewItemOS();

        // Setar na OS
        model.add(itemOS);
        // Atualizar view da tabela...
        tableServicos.getItems().add(itemOS);

        // Recalcular
        if(inputDesconto.getText() != null & !inputDesconto.getText().isBlank())
            model.setDesconto(Double.parseDouble(inputDesconto.getText()));
        model.calcularTotal();
        total.setText("R$ %.2f".formatted(model.getTotal()));

        // Limpar inputs
        var svcSelecionado = selectServico.getValue();
        selectServico.getItems().remove(svcSelecionado);
        selectServico.setValue(null);
        observacaoServico.setText(null);
        valorOriginal.setText(null);
        valorServico.setText(null);
        btnAdicionarServico.setDisable(true);

    }

    @Override
    public void setModel(OrdemDeServico model) {
        this.model = dao.getByIdEager(model.getId());
        if(model.getVeiculo() != null) {
            loadServicos();
            populateFieldsFromModel();
        }
    }


    @FXML
    public void servicoSelected(ActionEvent actionEvent) {
        if(selectServico.getValue() != null) {
            var valor = "R$ %.2f".formatted(selectServico.getValue().getValor());
            this.valorOriginal.setText(valor);
            this.valorServico.setText(valor);
            btnAdicionarServico.setDisable(false);
        }
    }


    @FXML
    public void handleBuscar(ActionEvent actionEvent) {
        List<Veiculo> veiculos = veiculoDAO.getByPlaca(buscaPlaca.getText());
        ObservableList<Veiculo> observableList = FXCollections.observableArrayList(veiculos);
        this.selectVeiculo.setItems(observableList);
        this.selectVeiculo.setDisable(false);
        dadosVeiculoTextField.forEach(tf -> tf.setText(null));
        itemsBox.setDisable(true);
        btnSalvar.setDisable(true);
    }


    @Override
    protected boolean validarForm() {
        if(model.getItems().isEmpty()) return false;
        return true;
    }

    @Override
    protected boolean buildModel() {
        if(inputDesconto.getText() != null & !inputDesconto.getText().isBlank())
            model.setDesconto(Double.parseDouble(inputDesconto.getText()));
        return true;
    }

    @Override
    protected void populateFieldsFromModel() {

        if (this.editModel) {
            //Veiculo veiculoEager = veiculoDAO.getById(model.getVeiculo().getId());
            //model.setVeiculo(veiculoEager);
            model = dao.getByIdEager(model.getId());
            buscaBox.setDisable(true);
            // Se aberta pode adicionar serviços
            if(model.getStatus().equals(EStatus.ABERTA)) {
                btnSalvar.setDisable(false);
                selectStatus.setDisable(false);
                itemsBox.setDisable(false);
            }
            // Se cancelada pode editar status
            else if(model.getStatus().equals(EStatus.CANCELADA)){
                btnSalvar.setDisable(false);
                selectStatus.setDisable(false);
                itemsBox.setDisable(true);
            }
            // Se fechada, não pode nada
            else {
                itemsBox.setDisable(true);
                btnSalvar.setDisable(true);
                selectStatus.setDisable(true);
            }

            for(ItemOS itemOS : model.getItems()) {
                selectServico.getItems().removeIf(c -> c.getId() == itemOS.getServico().getId());
            }
        }

        this.placa.setText(model.getVeiculo().getPlaca());
        this.cor.setText(model.getVeiculo().getCor().getNome());
        this.categoria.setText(model.getVeiculo().getModelo().getCategoria().name());
        this.cliente.setText(model.getVeiculo().getCliente().getNome());
        this.marca.setText(model.getVeiculo().getModelo().getMarca().getNome());
        this.modelo.setText(model.getVeiculo().getModelo().getDescricao());
        this.pontuacao.setText(String.valueOf(model.getVeiculo().getCliente().getPontuacao().getQuantidade()));
        this.observacoes.setText(model.getVeiculo().getObservacoes());

        setTableServicos();

        inputDesconto.setText(String.valueOf(model.getDesconto()));
        total.setText("R$ %.2f".formatted(model.getTotal()));
        selectStatus.setValue(model.getStatus());



    }

    @Override
    protected void onInitializeAction() {
        this.veiculoDAO.setConnection(connection);
        this.servicoDAO.setConnection(connection);
        this.selectVeiculo.setDisable(true);
        setConverterForComboBoxVeiculo();
        setConverterForComboBoxServico();
        buildDadosVeiculoTextFieldList();
        itemsBox.setDisable(true);
        btnSalvar.setDisable(true);
        setTextFormatters();
        setCellFactoryTableItemsOS();
        loadStatus();
        setFocusLostHandle();
    }

    private void setFocusLostHandle() {
        inputDesconto.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) { // focus lost
                if (inputDesconto.getText() != null && !inputDesconto.getText().isBlank())
                    model.setDesconto(Double.parseDouble(inputDesconto.getText()));
                else model.setDesconto(0);
                model.calcularTotal();
                total.setText("R$ %.2f".formatted(model.getTotal()));
            }
        });
    }

    private void loadServicos() {
        List<Servico> servicoList = servicoDAO.getByCategoria(model.getVeiculo().getModelo().getCategoria());
        ObservableList<Servico> observableList = FXCollections.observableArrayList(servicoList);
        selectServico.setItems(observableList);
    }

    private void buildDadosVeiculoTextFieldList() {
        this.dadosVeiculoTextField.addAll(List.of(
            cliente, placa, pontuacao, marca, modelo, observacoes, cor, categoria
        ));
    }

    private void setTableServicos() {
        tableServicos.setItems(FXCollections.observableArrayList(model.getItems()));
    }

    private void setConverterForComboBoxVeiculo() {
        selectVeiculo.setConverter(new StringConverter<Veiculo>() {
            @Override
            public String toString(Veiculo veiculo) {
                if(veiculo != null)
                    return veiculo.getPlaca();
                return null;
            }

            @Override
            public Veiculo fromString(String s) {
                return selectVeiculo.getValue();
            }
        });
    }

    private void setConverterForComboBoxServico() {
        selectServico.setConverter(new StringConverter<Servico>() {
            @Override
            public String toString(Servico servico) {
                if(servico != null) {
                    return servico.getDescricao() + " - " + servico.getCategoria().name();
                }
                return null;
            }

            @Override
            public Servico fromString(String s) {
                return selectServico.getValue();
            }
        });
    }

    @Override
    protected OsDAO getDAO() {
        return new OsDAO();
    }

    private void setTextFormatters() {
        valorServico.setTextFormatter(formatador.fromRegex(Regex.DECIMAL_POSITIVO));
        inputDesconto.setTextFormatter(formatador.fromRegex(Regex.DECIMAL_POSITIVO));
    }

    private ItemOS buildNewItemOS () {
        ItemOS itemOS = new ItemOS();
        itemOS.setServico(selectServico.getValue());
        itemOS.setObservacoes(observacaoServico.getText());
        if(valorServico.getText() != null & !valorServico.getText().isBlank())
            itemOS.setValorServico(Integer.parseInt(valorServico.getText()));
        return itemOS;
    }

    private void setCellFactoryTableItemsOS() {
        tbObservacoes.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        tbServico.setCellValueFactory(data -> {
            Servico servico = data.getValue().getServico();
            return new SimpleStringProperty(servico.getDescricao() + " - " + servico.getCategoria().name());
        });
        tbValorOriginal.setCellValueFactory(data ->
            new SimpleStringProperty("R$ %.2f".formatted(data.getValue().getServico().getValor())));
        tableValorFinal.setCellValueFactory(data ->
            new SimpleStringProperty("R$ %.2f".formatted(data.getValue().getValorServico())));
    }


    public void handleCtxtMenuRemoverItem(ActionEvent actionEvent) {
        ItemOS itemSelecionado
                = tableServicos.getSelectionModel().getSelectedItem();
        model.remove(itemSelecionado);
        tableServicos.getItems().remove(itemSelecionado);
        this.total.setText("R$ %.2f".formatted(model.getTotal()));
        // Voltar Item selecionado para a tabela de Serviços
        selectServico.getItems().add(itemSelecionado.getServico());
    }

    @Override
    protected void salvar() {
        if (editModel) {
            var response = dao.update(model);
            this.modelUpdated = true;
            updateModel(response);
        } else {
            OrdemDeServico response = dao.create(model);
            this.modelUpdated = true;
            updateModel(response);
        }
    }

}
