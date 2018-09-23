package Controller;

import ControllerView.*;
import Model.TipologiaEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;

public class CambiaView {

    private Map<String,String> risorseForm;
    private Map<TipologiaEnum,String> eventiSpecificiFormMap; // EnumMap
    private AnchorPane formCorrente;
    private Node formAggiuntivo;

    public CambiaView(AnchorPane formCorrente){
        this.formCorrente=formCorrente;
        // Carico tutti i form in un HashMap<String,Resource>
        risorseForm=new HashMap<>();
        caricaRisorseForm();
    }

    private void caricaRisorseForm() {
        risorseForm.put("creaEvento","../FXMLView/inserisciEventoPane.fxml");
        risorseForm.put("tipoEvento","../FXMLView/tipoEventoPane.fxml");
    }

    private FXMLLoader caricaFormDaRisorsa(String risorsa) {
            pulisciForm();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource((risorseForm.get(risorsa))));
            return fxmlLoader;
    }

    public void pulisciForm() {
        formCorrente.getChildren().clear();
    }

    /************************************************
     *                                              *
     *      CREAZIONE FORM INSERISCI EVENTO         *
     *                                              *
     * **********************************************
     */


    public void mostraFormInserisciEvento(){
        FXMLLoader loader = caricaFormDaRisorsa("creaEvento");
        try {
            InserisciEventoController ief=new InserisciEventoController(this);
            loader.setController(ief);
            Node form = loader.load();
            formCorrente.getChildren().add(form);

        } catch( Exception e ) { e.printStackTrace(); }
    }

    public void mostraFormTipoEvento() {
        try {
           FXMLLoader loader = caricaFormDaRisorsa("tipoEvento");
           loader.setController(new SelezioneTipoEventoForm(this));
           Node form = loader.load();
           formCorrente.getChildren().add(form);
       } catch(Exception e) { e.printStackTrace(); }
    }

    public void mostraFormCercaEvento() {
        caricaFormDaRisorsa("cercaEvento");
    }

    public void mostraFormGestioneClienti() {
        caricaFormDaRisorsa("gestioneClienti");
    }

    public void mostraFormGestioneDipendeti() {
        caricaFormDaRisorsa("gestioneDipedenti");
    }

    public void mostraStaticheMenu() {
    }


}
