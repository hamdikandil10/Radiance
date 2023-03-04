/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.ktebi.entities.Reclamation;
import tn.esprit.ktebi.entities.ReponseReclamation;
import tn.esprit.ktebi.services.ServiceReclamation;
import tn.esprit.ktebi.services.ServiceReponse;

/**
 * FXML Controller class
 *
 * @author Dell 6540
 */
public class ReponseReclamationController implements Initializable {

    @FXML
    private TableView<Reclamation> Table;    
    
    @FXML
    private TableColumn<Reclamation, LocalDate> colDate;
    
    @FXML
    private TableColumn<Reclamation, String> colContenu;    
    
    @FXML
    private TableColumn<Reclamation, String> ColId;

    @FXML
    private Button btnmod;

    @FXML
    private TextField txtRec;

    @FXML
    private TextField txtRep;
    
    @FXML
    private TextField id_rec;
    
    @FXML
    private TextField txtRech;
    
    @FXML
    private TextField txtNom;

    @FXML
    private Button btnDate;
    List<Reclamation> rec = new ArrayList<>();          
    ServiceReclamation sr = new ServiceReclamation();
    ServiceReponse srep = new ServiceReponse();
    
    ObservableList<Reclamation> list;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Table.setOnMouseClicked(t->{
            if(t.getClickCount() ==1){
                Integer index = Table.getSelectionModel().getSelectedIndex();
                txtRec.setText(Table.getItems().get(index).getContenu());
                id_rec.setText(String.valueOf(Table.getItems().get(index).getEtat()));
            }
        });
        AfficheRec();
        chercherReclamation();
    }
    
        void chercherReclamation(){

             //// Code Recherche
             try {
            rec=sr.selectAll();     
            list = FXCollections.observableArrayList(rec);     
            Table.setItems(list);
            FilteredList<Reclamation> listeFilter = new FilteredList<>(list, l-> true);
               txtRech.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    listeFilter.setPredicate(reclamation-> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCase = newValue.toLowerCase();
                        if (reclamation.getEtat().toLowerCase().contains(lowerCase)) {
                            return true;
                        }else

                        return false;
                    });
                });               
                SortedList<Reclamation> sortedData = new SortedList<>(listeFilter);
                sortedData.comparatorProperty().bind(Table.comparatorProperty());
                Table.setItems(sortedData);
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void AfficheRec() {
                 List<Reclamation> rec = new ArrayList<>();
        try {
            rec =sr.selectAll();
        } catch (SQLException ex) {
            Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        list= FXCollections.observableList(rec);            
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        colContenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        ColId.setCellValueFactory(new PropertyValueFactory<>("etat"));
        Table.setItems(list);      
}    
     @FXML
    void ReponseReclamtion(ActionEvent event) throws IOException {
        if (txtRep.getText().equals("")) {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Controle de saisie");
            al.setHeaderText("Erreur de saisie !");
            al.setContentText("Le contenu est vide !");
            al.show();
        }else{
            
            Integer index = Table.getSelectionModel().getSelectedIndex();
            Reclamation rec = new Reclamation(Table.getItems().get(index).getId());
            ReponseReclamation rep = new ReponseReclamation(txtRep.getText(),rec,LocalDate.now());            
            ServiceReclamation sr = new ServiceReclamation();
            try {
                srep.createOne(rep);
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Succés");
                al.setHeaderText("Reponse envoyé");
                al.show();
                AfficheRec();
                txtRep.setText("");
                id_rec.setText("");
                txtRec.setText("");
            } catch (SQLException ex) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setHeaderText("Erreur Interne");
                al.setContentText(ex.getMessage());
                al.show();
            }
            
        }
    } 
    void TrierDate(){
             List<Reclamation> listrec = new ArrayList<>();
        try {
            listrec = sr.selectAllOrderByDate();
        } catch (SQLException ex) {
            Logger.getLogger(ReponseReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<Reclamation> data = FXCollections.observableArrayList(listrec);
        Table.setItems(data);
    }
    @FXML
    void TrierParDate(ActionEvent event) {
                TrierDate();

    }    
}
