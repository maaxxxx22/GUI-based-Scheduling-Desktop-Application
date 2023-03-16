package controller;


import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import dto.UserDTO;
import util.AppLogger;
import util.AppUtil;


import java.time.ZoneId;
import java.util.*;



/**
 * The class Login for Login UI
 */
public class Login {
    @FXML
    public Label lblTitle;


    @FXML
    public ImageView imgUsername;


    @FXML
    public TextField txtUsername;


    @FXML
    public ImageView imgPassword;


    @FXML
    public PasswordField txtPassword;


    /*@FXML
    public Label lblLocation;*/
    @FXML
    public ImageView imgLocation;


    @FXML
    public Label lblLocationValue;


    @FXML
    public Button btnLogin;


    @FXML
    public Label lblLogin;


    private ResourceBundle resourceBundle;


    @FXML

/**
 *
 * Initialize
 *
 */
    public void initialize() {

        resourceBundle = ResourceBundle.getBundle("i18n/i18n", Locale.getDefault());

        lblTitle.setText(resourceBundle.getString("login_title"));
        lblLocationValue.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
        btnLogin.setText(resourceBundle.getString("login"));
        Image imLocation = new Image(getClass().getResourceAsStream("/images/location.png"));
        imgLocation.setImage(imLocation);
        imgLocation.setAccessibleText("Location");
        imgLocation.setAccessibleHelp("Location");
        Tooltip tooltipLocation = new Tooltip("Location");
        Tooltip.install(imgLocation, tooltipLocation);
        Image imUsername = new Image(getClass().getResourceAsStream("/images/users.png"));
        imgUsername.setImage(imUsername);
        imgUsername.setAccessibleText(resourceBundle.getString("username"));
        imgUsername.setAccessibleHelp(resourceBundle.getString("username"));
        Tooltip tooltipUsername = new Tooltip(resourceBundle.getString("username"));
        Tooltip.install(imgUsername, tooltipUsername);
        Image imPassword = new Image(getClass().getResourceAsStream("/images/lock.png"));
        imgPassword.setImage(imPassword);
        imgPassword.setAccessibleHelp(resourceBundle.getString("password"));
        imgPassword.setAccessibleText(resourceBundle.getString("password"));
        Tooltip tooltipPassword = new Tooltip(resourceBundle.getString("password"));
        Tooltip.install(imgPassword, tooltipPassword);
    }


    @FXML
    protected void loginButtonClick(ActionEvent event) {
        clearLoginError();
        if (validate()) {
            UserDTO user = new UserDao().validateUser(txtUsername.getText(),txtPassword.getText());
            if(user != null) {
                AppUtil.loginUser = user;
                AppLogger.info("Successful Login");
                AppUtil.routeTo(event, "appointment", "Appointments");
            }
            else {
                setLoginError("invalid_credentials", null);
                AppLogger.info("Invalid Credentials");
            }
        }
    }


    /**
     *
     * Validate
     *
     * @return Boolean
     */
    private Boolean validate() {
        if (txtUsername.getText().isEmpty()) {
            setLoginError("username_required", "Username is required");
            return false;
        } else if (txtPassword.getText().isEmpty()) {
            setLoginError("password_required", "Password is required");
            return false;
        }

        return true;
    }


    /**
     *
     * Sets the login error
     *
     * @param key  the key
     */
    private void setLoginError(String key,String logMessage) {
        lblLogin.setText(resourceBundle.getString(key));
        if(logMessage != null) {
            AppLogger.error("Error in Login Page with details :: " + logMessage);
        }
    }


    /**
     *
     * Clear login error
     *
     */
    private void clearLoginError() {
        lblLogin.setText("");
    }
}