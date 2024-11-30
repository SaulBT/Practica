/**
 * Autor: Saúl Barragán Torres
 * Fecha de creación: 14/06/2024
 * Fecha de última modificación: 16/06/2024
 * Autor de la última modificación: Saúl Barragán Torres
 * Versión: 1.0
 */

/**
 * _CONTENIDO_
 * Permite el acceso a los profesores y determina si se trata de un coordinador.
 * 
 * realizarVerificacion
 * irMenuPrincipal
 * validarCampos
 */
package sistemacoilvic.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sistemacoilvic.modelo.dao.AutenticacionDAO;
import sistemacoilvic.modelo.pojo.ProfesorUV;
import sistemacoilvic.utilidades.Constantes;
import sistemacoilvic.utilidades.Utils;

public class FXMLWIniciarSesionController implements Initializable {

    ProfesorUV profesorSesion;
    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private PasswordField tfContrasenia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

    @FXML
    private void clicBtnIniciarSesion(ActionEvent event) {
            realizarVerificacion();
    }
    
    private void realizarVerificacion(){
        if(validarCampos()){
            HashMap<String,Object> respuesta = AutenticacionDAO.verificarLogin(Integer.parseInt(tfNumeroPersonal.getText()), tfContrasenia.getText());
            boolean isError = (boolean)respuesta.get(Constantes.KEY_ERROR);
            if (!isError){
                boolean isLogin = (boolean)respuesta.get("login");
                if (isLogin){
                    ProfesorUV profesor = (ProfesorUV)respuesta.get("profesor");
                    Utils.mostrarAlertaSimple("Bienvenido(a)", "" + respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.INFORMATION);
                    irMenuPrincipal(profesor);
                } else {
                    Utils.mostrarAlertaSimple("Error de verificación", "" + respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
                }
            } else {
                Utils.mostrarAlertaSimple("Error", "" + respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
            }
        }
    }
    
    private void irMenuPrincipal(ProfesorUV profesor){
        try {
            Stage escenarioPrincipal = (Stage)tfNumeroPersonal.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/FXMLMenuPrincipal.fxml");
            Parent root = loader.load();
            FXMLMenuPrincipalController controlador = loader.getController();
            controlador.inicializarValores(profesor);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Sistema COIL-VIC - Menú principal");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } 
    }
    
    private boolean validarCampos(){
        boolean esVacio = false;
        boolean noEsNumPersonal = false;
        if(tfNumeroPersonal.getText().isEmpty()){
            esVacio = true;
        }
        if(tfContrasenia.getText().isEmpty()){
            esVacio = true;
        }
        if(!Utils.verificarSoloNumeros(tfNumeroPersonal.getText())){
            noEsNumPersonal = true;
        }
        
        if(esVacio){
            Utils.mostrarAlertaSimple("Campos vacíos", Constantes.KEY_CAMPO_VACIO, Alert.AlertType.WARNING);
            return false;
        } else if(noEsNumPersonal){
            Utils.mostrarAlertaSimple("Datos inválidos", "El número de personal consta de sólo numeros, verifique su información", Alert.AlertType.WARNING);
            return false;
        }else{
            return true;
        }
    }

    @FXML
    private void btnClicSalir(ActionEvent event) {
        ((Stage)tfNumeroPersonal.getScene().getWindow()).close();
    }

    @FXML
    private void enterNumeroPersonal(KeyEvent event) {
        if(event.getCode().toString().equals("ENTER")){
            realizarVerificacion();
        }
    }

    @FXML
    private void enterContrasenia(KeyEvent event) {
        if(event.getCode().toString().equals("ENTER")){
            realizarVerificacion();
        }
    }
    
}
