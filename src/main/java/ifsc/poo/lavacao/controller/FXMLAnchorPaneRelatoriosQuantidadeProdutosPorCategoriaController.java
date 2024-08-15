package ifsc.poo.lavacao.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import ifsc.poo.lavacao.model.database.Database;
import ifsc.poo.lavacao.model.database.DatabaseFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLAnchorPaneRelatoriosQuantidadeProdutosPorCategoriaController implements Initializable {

    @FXML
    private TableView<String> tableViewProdutos;
    @FXML
    private TableColumn<String, Integer> tableColumnProdutoCodigo;
    @FXML
    private TableColumn<String, String> tableColumnProdutoNome;
    @FXML
    private TableColumn<String, Double> tableColumnProdutoPreco;
    @FXML
    private TableColumn<String, Integer> tableColumnProdutoQuantidade;
    @FXML
    private TableColumn<String, String> tableColumnProdutoCategoria;
    @FXML
    private Button buttonImprimir;
    @FXML
    private ComboBox comboBoxCategorias;
    
    private List<String> listCategorias;
    private List<String> listProdutos;
    private ObservableList<String> observableListCategorias;
    private ObservableList<String> observableListProdutos;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getConnection("mysql");
    private final Connection connection = database.connect();
 //   private final CategoriaDAO categoriaDAO = new CategoriaDAO();
 //   private final ProdutoDAO produtoDAO = new ProdutoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  //      categoriaDAO.setConnection(connection);
   //     listCategorias = categoriaDAO.listar();
        observableListCategorias = FXCollections.observableArrayList(listCategorias);
        comboBoxCategorias.setItems(observableListCategorias);
    }    
    
    public void carregarTableViewProdutos() {
     //   produtoDAO.setConnection (connection);
        
        tableColumnProdutoCodigo.setCellValueFactory(new PropertyValueFactory<>("cdProduto"));
        tableColumnProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnProdutoPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tableColumnProdutoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnProdutoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

     //   Categoria categoria = (Categoria)this.comboBoxCategorias.getSelectionModel().getSelectedItem();
     //   listProdutos = produtoDAO.listarPorCategoria(categoria);

        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        tableViewProdutos.setItems(observableListProdutos);
    }
    
    public void handleImprimir() throws JRException{
        HashMap filtro = new HashMap();
    //    Categoria categoria = (Categoria)this.comboBoxCategorias.getSelectionModel().getSelectedItem();
        
    //    filtro.put("cdCategoria", categoria.getCdCategoria());

        URL url = getClass().getResource("/javafxmvc/relatorios/JAVAFXMVCRelatorioProdutosPorCategoria.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }
    
}
