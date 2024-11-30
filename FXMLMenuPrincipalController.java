/**
 * Autor: Saúl Barragán Torres
 * Fecha de creación: 13/06/2024
 * Fecha de última modificación: 14/06/2024
 * Autor de la última modificación: Maximiliano Soto Jiménez
 * Versión: 0.1
 */

/**
 * _CONTENIDO_
 * Despliega las opciones de acuerdo si el profesorUV es coordinador o no.
 * 
 */

package sistemacoilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemacoilvic.controlador.colaboracioncoil.FXMLWRegistrarColaboracionCOILController;
import sistemacoilvic.controlador.colaboracioncoil.FXMLWRevisarPeriodoColaboracionCOILController;
import sistemacoilvic.controlador.numeralia.FXMLWNumeraliaController;
import sistemacoilvic.controlador.ofertasuv.FXMLWOfertarColaboracionController;
import sistemacoilvic.controlador.ofertasuv.edicion.FXMLWAdministrarOfertasUVController;
import sistemacoilvic.controlador.profesorexterno.FXMLWConsultarProfesorExternoController;
import sistemacoilvic.controlador.profesoruv.FXMLWConsultarProfesoresController;
import sistemacoilvic.controlador.universidades.FXMLWAdministrarUniversidadesController;
import sistemacoilvic.modelo.pojo.ProfesorUV;
import sistemacoilvic.utilidades.Utils;

public class FXMLMenuPrincipalController implements Initializable {
    ProfesorUV profesorSesion;

    @FXML
    private Label lbBienvenido;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidoPaterno;
    @FXML
    private Label lbApellidoMaterno;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbNumeroPersonal;
    @FXML
    private Button btnConsultarProfesoresUV;
    @FXML
    private Button btnConsultarProfesoresExternos;
    @FXML
    private Button btnConsultarUniversidades;
    @FXML
    private Button btnConsultarNumeralia;
    @FXML
    private Button btnRegistrarOfertaExterna;
    @FXML
    private Button btnRegistrarProfesorExterno;
    @FXML
    private Button btnEditarProfesorExterno;
    @FXML
    private Label lbEsCoordinador;
    @FXML
    private Button btnRegistrarColaboracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void inicializarValores(ProfesorUV profesorSesion){
        this.profesorSesion = profesorSesion;
        cargarDatosProfesor(profesorSesion.isEsCoordinador());
        determinarVisibilidadBotones();
    }
    
    private void cargarDatosProfesor(boolean esCoordinador){
        if(esCoordinador){
            lbEsCoordinador.setText("Coordinadora");
        }else{
            lbEsCoordinador.setText("Profesor UV");
        }
        lbBienvenido.setText(lbBienvenido.getText() + " " + profesorSesion.getNombre());
        lbNombre.setText(lbNombre.getText() + " " + profesorSesion.getNombre());
        lbApellidoPaterno.setText(lbApellidoPaterno.getText() + " "+ profesorSesion.getApellidoPaterno());
        lbApellidoMaterno.setText(lbApellidoMaterno.getText() + " " + profesorSesion.getApellidoMaterno());
        lbTitulo.setText(lbTitulo.getText() + " " + profesorSesion.getGrado());
        lbNumeroPersonal.setText(lbNumeroPersonal.getText() + " " + profesorSesion.getNumeroDePersonal());
    }
    
    private void determinarVisibilidadBotones(){
        if(profesorSesion.isEsCoordinador()){
            btnConsultarProfesoresUV.setVisible(true);
            btnConsultarProfesoresExternos.setVisible(true);
            btnConsultarUniversidades.setVisible(true);
            btnConsultarNumeralia.setVisible(true);
            btnRegistrarOfertaExterna.setVisible(true);
            btnRegistrarProfesorExterno.setVisible(true);
            btnEditarProfesorExterno.setVisible(true);
            btnRegistrarColaboracion.setVisible(false);
        } else {
            btnConsultarProfesoresUV.setVisible(false);
            btnConsultarProfesoresExternos.setVisible(false);
            btnConsultarUniversidades.setVisible(false);
            btnConsultarNumeralia.setVisible(false);
            btnRegistrarOfertaExterna.setVisible(false);
            btnRegistrarProfesorExterno.setVisible(false);
            btnEditarProfesorExterno.setVisible(false);
        }
    }

    @FXML
    private void clicBtnRegistrarOferta(ActionEvent event) {
        irRegistrarOferta(profesorSesion);
    }
    
    private void irRegistrarOferta(ProfesorUV profesor){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertasuv/FXMLWOfertarColaboracion.fxml");
            Parent root = loader.load();
            FXMLWOfertarColaboracionController controlador = loader.getController();
            controlador.inicializarValores(profesor.getIdProfesorUV());
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Ofertar colaboracion");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utils.mostrarAlertaSimple("Error inseperado", "Lo sentimos, no se puede registrar una colaboracion. Por favor reporta el problema.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnVerMisOfertas(ActionEvent event) {
        irConsultarMisOfertas(profesorSesion);
    }
    
    private void irConsultarMisOfertas(ProfesorUV profesor){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertasuv/edicion/FXMLWAdministrarOfertasUV.fxml");
            Parent root = loader.load();
            FXMLWAdministrarOfertasUVController controlador = loader.getController();
            controlador.inicializarValores(profesor.getIdProfesorUV());
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Administrar ofertas");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utils.mostrarAlertaSimple("Error inseperado", "Lo sentimos, no se pueden ver tus ofertas. Por favor reporta el problema.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void clicBtnRegistrarColaboracion(ActionEvent event) {
        irRegistrarColaboracion();
    }
    
    private void irRegistrarColaboracion(){
        try {
            Stage escenarioPrincipal = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboracioncoil/FXMLWRegistrarColaboracionCOIL.fxml");
            Parent root = loader.load();
            FXMLWRegistrarColaboracionCOILController controlador = loader.getController();
            controlador.inicializarValores(profesorSesion);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Sistema COIL-VIC - Menú principal");
            escenarioPrincipal.initModality(Modality.APPLICATION_MODAL);
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @FXML
    private void clicBtnRevisarColaboraciones(ActionEvent event) {
        irConsultarColaboracion();
    }
    
    private void irConsultarColaboracion(){
        try {
            Stage escenarioPrincipal = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboracioncoil/FXMLWRevisarPeriodoColaboracionCOIL.fxml");
            Parent root = loader.load();
            FXMLWRevisarPeriodoColaboracionCOILController controlador = loader.getController();
            controlador.inicializarValores(profesorSesion, null);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Revisar Colaboraciones");
            escenarioPrincipal.initModality(Modality.APPLICATION_MODAL);
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    @FXML
    private void clicBtnConsultarProfesoresUV(ActionEvent event) {
        irConsultarProfesoresUV();
    }
    
    private void irConsultarProfesoresUV(){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/profesoruv/FXMLWConsultarProfesores.fxml");
            Parent root = loader.load();
            FXMLWConsultarProfesoresController controlador = loader.getController();
            controlador.inicializarValores();
            Scene escenaConsultar = new Scene(root);
            escenario.setTitle("Consultar Profesores UV");
            escenario.setScene(escenaConsultar);
            escenario.initModality(Modality.NONE);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utils.mostrarAlertaSimple("Error inseperado", "Lo sentimos, no se pueden ver los profesores UV. Por favor reporta el problema.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void clicBtnConsultarProfesoresExternos(ActionEvent event) {
        irConsultaProfesoresExternos();
    }

    private void irConsultaProfesoresExternos(){
            try {
            Stage escenarioEditarProfesor = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/profesorexterno/FXMLWConsultarProfesorExterno.fxml");
            Parent root = loader.load();
            FXMLWConsultarProfesorExternoController controlador = loader.getController();
            controlador.inicializarValores(false, false, null, null);
            Scene escenaEditar = new Scene(root);
            escenarioEditarProfesor.setTitle("Seleccionar Profesor");
            escenarioEditarProfesor.setScene(escenaEditar);
            escenarioEditarProfesor.initModality(Modality.NONE);
            escenarioEditarProfesor.showAndWait();
        } catch (IOException e) {
            Utils.mostrarAlertaSimple("Error", "Lo sentimos, no se pueden ver los profesores externo. Por favor reporta el problema.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void clicBtnConsultarUniversidades(ActionEvent event) {
        irConsultarUniversidades();
    }
    
    private void irConsultarUniversidades(){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/universidades/FXMLWAdministrarUniversidades.fxml");
            Parent root = loader.load();
            FXMLWAdministrarUniversidadesController controlador = loader.getController();
            controlador.inicializarValores();
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Registrar colaboracion");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utils.mostrarAlertaSimple("Error inseperado", "Lo sentimos, el registro de universidades no está disponible. Por favor reporta el problema.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnConsultarNumeralia(ActionEvent event) {
        irConsultarNumeralia();
    }
    
    private void irConsultarNumeralia(){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/numeralia/FXMLWNumeralia.fxml");
            Parent root = loader.load();
            FXMLWNumeraliaController controlador = loader.getController();
            controlador.inicializarValores();
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Consultar Numeralia");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ioex) {
            ioex.printStackTrace();
            Utils.mostrarAlertaSimple("Error", "No se puede consultar la numeralia", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void clicBtnRegistrarOfertaExterna(ActionEvent event) {
        irRegistrarOfertaExterna();
    }
    
    private void irRegistrarOfertaExterna(){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertasexternas/FXMLWRegistrarOfertaExterna.fxml");
            Parent root = loader.load();
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Registrar colaboracion");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utils.mostrarAlertaSimple("Error inseperado", "Lo sentimos, el registro de oferta externa no está disponible. Por favor reporta el problema.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void clicBtnEditarProfesorExterno(ActionEvent event) {
        irEditarProfesorExterno();
    }
    
    private void irEditarProfesorExterno(){
        try {
            Stage escenarioMenu = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/profesorexterno/FXMLWEditarProfesorExterno.fxml");
            Parent root = loader.load();
            Scene escenaMenu = new Scene(root);
            escenarioMenu.setScene(escenaMenu);
            escenarioMenu.setTitle("Editar Profesor Externo");
            escenarioMenu.initModality(Modality.APPLICATION_MODAL);
            escenarioMenu.showAndWait();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Utils.mostrarAlertaSimple("Error", "Lo sentimos, no se puede editar el profesor externo en este momento. Por favor reporta el problema.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void clicBtnRegistrarProfesorExterno(ActionEvent event) {
        irRegistrarProfesorExterno();
    }
    
    private void irRegistrarProfesorExterno(){
        try {
            Stage escenarioMenu = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/profesorexterno/FXMLWRegistrarProfesorExterno.fxml");
            Parent root = loader.load();
            Scene escenaMenu = new Scene(root);
            escenarioMenu.setScene(escenaMenu);
            escenarioMenu.setTitle("Registrar Profesor Externo");
            escenarioMenu.initModality(Modality.APPLICATION_MODAL);
            escenarioMenu.showAndWait();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Utils.mostrarAlertaSimple("Error", "Lo sentimos, no se puede registrar profesores externo en este momento. Por favor reporta el problema.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void clicBtnCerrarSesion(ActionEvent event) {
        try {
            Stage escenarioPrincipal = (Stage)lbBienvenido.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/FXMLWIniciarSesion.fxml");
            Parent root = loader.load();
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Iniciar sesión");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } 
    }

    @FXML
    private void clicBtnVerOfertasExternas(ActionEvent event) {
        try {
            Stage escenarioMenu = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertasexternas/FXMLWOfertasExternas.fxml");
            Parent root = loader.load();
            Scene escenaMenu = new Scene(root);
            escenarioMenu.setScene(escenaMenu);
            escenarioMenu.setTitle("Ofertas Externas");
            escenarioMenu.initModality(Modality.APPLICATION_MODAL);
            escenarioMenu.showAndWait();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Utils.mostrarAlertaSimple("Error", "Error al cargar la vista: ", Alert.AlertType.ERROR);
        }
    }    
}
