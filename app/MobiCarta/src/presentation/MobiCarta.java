/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import domain.Address;
import domain.Client;
import domain.FileIO;
import domain.ProductsListManager;
import domain.ProfileManager;
import domain.RecommendationManager;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.contactless.ContactlessException;
import javax.microedition.contactless.DiscoveryManager;
import javax.microedition.contactless.TargetListener;
import javax.microedition.contactless.TargetProperties;
import javax.microedition.contactless.TargetType;
import javax.microedition.contactless.ndef.NDEFMessage;
import javax.microedition.contactless.ndef.NDEFRecordListener;
import javax.microedition.contactless.ndef.NDEFRecordType;
import javax.microedition.contactless.ndef.NDEFTagConnection;
import javax.microedition.io.Connector;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author Sergio
 */
public class MobiCarta extends MIDlet implements CommandListener, NDEFRecordListener, TargetListener {
    
    private boolean midletPaused = false;

    private Alert alert;
    private Gauge gauge;
    private Command exit, cancel;
    private Form searching;
    private boolean nfcStart = false;
//<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
private Form profile;
private StringItem stringItem;
private StringItem stringItem1;
private TextField txtFZipCode;
private TextField txtFTown;
private TextField txtFStreet;
private TextField txtFNumber;
private TextField txtFName;
private TextField txtFSurname;
private TextField txtFState;
private TextField txtFDNI;
private Form ordersList;
private Alert success;
private Alert error;
private Form main;
private StringItem stringItem2;
private ImageItem imageItem;
private StringItem stringItem3;
private Alert subtractElement;
private Alert checkpoint;
private Form bill;
private Form Opening;
private StringItem sItemOpening;
private ImageItem imageItem1;
private Form recommendations;
private Command exitProfileCommand;
private Command saveCommand;
private Command exitOLCommand;
private Command sendCommand;
private Command exitPSCommand;
private Command exitPECommand;
private Command exitMainCommand;
private Command exitOLECommand;
private Command exitOLSCommand;
private Command cancelSubtractCommand;
private Command ProfileCommand;
private Command exitCommand;
private Command backCommand;
private Command exitBillCommand;
private Command exitOpeningCommand;
private Command backCommand1;
private Command recommendationCommand;
private Font font;
private Image image;
private Ticker recTicker;
//</editor-fold>//GEN-END:|fields|0|
    /**
     * The MobiCarta constructor.
     */
    public MobiCarta() {
    }

//<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
//</editor-fold>//GEN-END:|methods|0|
//<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
/**
 * Initializes the application.
 * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
 */
private void initialize () {//GEN-END:|0-initialize|0|0-preInitialize
        addNFCListener();
//GEN-LINE:|0-initialize|1|0-postInitialize

}//GEN-BEGIN:|0-initialize|2|
//</editor-fold>//GEN-END:|0-initialize|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
/**
 * Performs an action assigned to the Mobile Device - MIDlet Started point.
 */
public void startMIDlet () {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
//GEN-LINE:|3-startMIDlet|1|3-postAction
        removeNDEFListener();
        if (!nfcStart)
            getDisplay().setCurrent(getMain());
}//GEN-BEGIN:|3-startMIDlet|2|
//</editor-fold>//GEN-END:|3-startMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
/**
 * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
 */
public void resumeMIDlet () {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
}//GEN-BEGIN:|4-resumeMIDlet|2|
//</editor-fold>//GEN-END:|4-resumeMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
/**
 * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
 * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
 * @param nextDisplayable the Displayable to be set
 */
public void switchDisplayable (Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
Display display = getDisplay ();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
if (alert == null) {
display.setCurrent (nextDisplayable);
} else {
display.setCurrent (alert, nextDisplayable);
}//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
}//GEN-BEGIN:|5-switchDisplayable|2|
//</editor-fold>//GEN-END:|5-switchDisplayable|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
/**
 * Called by a system to indicated that a command has been invoked on a particular displayable.
 * @param command the Command that was invoked
 * @param displayable the Displayable where the command was invoked
 */
public void commandAction (Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
 // write pre-action user code here
if (displayable == Opening) {//GEN-BEGIN:|7-commandAction|1|90-preAction
if (command == exitOpeningCommand) {//GEN-END:|7-commandAction|1|90-preAction
 // write pre-action user code here
exitMIDlet ();//GEN-LINE:|7-commandAction|2|90-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|3|96-preAction
} else if (displayable == bill) {
if (command == exitBillCommand) {//GEN-END:|7-commandAction|3|96-preAction
 // write pre-action user code here
exitMIDlet ();//GEN-LINE:|7-commandAction|4|96-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|5|42-preAction
} else if (displayable == error) {
if (command == exitPECommand) {//GEN-END:|7-commandAction|5|42-preAction
 // write pre-action user code here
switchDisplayable (null, getProfile ());//GEN-LINE:|7-commandAction|6|42-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|7|76-preAction
} else if (displayable == main) {
if (command == ProfileCommand) {//GEN-END:|7-commandAction|7|76-preAction
Client client = ProfileManager.loadClient(this);
if (!client.getDNI().equals("")) {
    getTxtFDNI().setString(client.getDNI());
    getTxtFName().setString(client.getName());
    getTxtFSurname().setString(client.getSurname());
    getTxtFStreet().setString(client.getAddress().getStreet());
    getTxtFNumber().setString(client.getAddress().getNumber());
    getTxtFZipCode().setString(String.valueOf(client.getAddress().getZipCode()));
    getTxtFTown().setString(client.getAddress().getTown());
    getTxtFState().setString(client.getAddress().getState());
    Display.getDisplay(this).setCurrent(getProfile());
}
else {
    Alert alert1 = new Alert("Perfil no encontrado", "Complete el siguiente formulario con sus datos", null, AlertType.WARNING);
    Display.getDisplay(this).setCurrent(alert1, getProfile());   
}    
//GEN-LINE:|7-commandAction|8|76-postAction
 // write post-action user code here
} else if (command == exitMainCommand) {//GEN-LINE:|7-commandAction|9|49-preAction
 // write pre-action user code here
exitMIDlet ();//GEN-LINE:|7-commandAction|10|49-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|11|22-preAction
} else if (displayable == ordersList) {
if (command == exitOLCommand) {//GEN-END:|7-commandAction|11|22-preAction
 // write pre-action user code here
exitMIDlet ();//GEN-LINE:|7-commandAction|12|22-postAction
 // write post-action user code here
} else if (command == recommendationCommand) {//GEN-LINE:|7-commandAction|13|99-preAction

switchDisplayable (null, getRecommendations ());//GEN-LINE:|7-commandAction|14|99-postAction
 // write post-action user code here
} else if (command == sendCommand) {//GEN-LINE:|7-commandAction|15|24-preAction
sendOrder("");
//GEN-LINE:|7-commandAction|16|24-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|17|81-preAction
} else if (displayable == profile) {
if (command == backCommand) {//GEN-END:|7-commandAction|17|81-preAction
 // write pre-action user code here
switchDisplayable (null, getMain ());//GEN-LINE:|7-commandAction|18|81-postAction
 // write post-action user code here
} else if (command == exitProfileCommand) {//GEN-LINE:|7-commandAction|19|17-preAction
 // write pre-action user code here
exitMIDlet ();//GEN-LINE:|7-commandAction|20|17-postAction
 // write post-action user code here
} else if (command == saveCommand) {//GEN-LINE:|7-commandAction|21|19-preAction
Address address = new Address(getTxtFStreet().getString(), getTxtFNumber().getString(),
        Integer.parseInt(getTxtFZipCode().getString()), getTxtFTown().getString(),
        getTxtFState().getString());
Client client = new Client(getTxtFDNI().getString(), getTxtFName().getString(),
        getTxtFSurname().getString(), address);

if (ProfileManager.saveProfile(client))
    getDisplay().setCurrent(getSuccess(), getProfile());
else
    getDisplay().setCurrent(getError(), getProfile());
//GEN-LINE:|7-commandAction|22|19-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|23|103-preAction
} else if (displayable == recommendations) {
if (command == backCommand1) {//GEN-END:|7-commandAction|23|103-preAction
 // write pre-action user code here
switchDisplayable (null, getOrdersList ());//GEN-LINE:|7-commandAction|24|103-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|25|64-preAction
} else if (displayable == subtractElement) {
if (command == cancelSubtractCommand) {//GEN-END:|7-commandAction|25|64-preAction
 // write pre-action user code here
switchDisplayable (null, getOrdersList ());//GEN-LINE:|7-commandAction|26|64-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|27|44-preAction
} else if (displayable == success) {
if (command == exitPSCommand) {//GEN-END:|7-commandAction|27|44-preAction
 // write pre-action user code here
switchDisplayable (null, getProfile ());//GEN-LINE:|7-commandAction|28|44-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|29|7-postCommandAction
}//GEN-END:|7-commandAction|29|7-postCommandAction
else if (command == exit) {
    exitMIDlet();
}
else if (command == cancel) {
    exitMIDlet();
}
}//GEN-BEGIN:|7-commandAction|30|
//</editor-fold>//GEN-END:|7-commandAction|30|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: profile ">//GEN-BEGIN:|14-getter|0|14-preInit
/**
 * Returns an initiliazed instance of profile component.
 * @return the initialized component instance
 */
public Form getProfile () {
if (profile == null) {//GEN-END:|14-getter|0|14-preInit
 // write pre-init user code here
profile = new Form ("PERFIL DEL CLIENTE", new Item[] { getStringItem (), getTxtFDNI (), getTxtFName (), getTxtFSurname (), getStringItem1 (), getTxtFStreet (), getTxtFNumber (), getTxtFZipCode (), getTxtFTown (), getTxtFState () });//GEN-BEGIN:|14-getter|1|14-postInit
profile.addCommand (getExitProfileCommand ());
profile.addCommand (getSaveCommand ());
profile.addCommand (getBackCommand ());
profile.setCommandListener (this);//GEN-END:|14-getter|1|14-postInit
 // write post-init user code here
}//GEN-BEGIN:|14-getter|2|
return profile;
}
//</editor-fold>//GEN-END:|14-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: ordersList ">//GEN-BEGIN:|15-getter|0|15-preInit
/**
 * Returns an initiliazed instance of ordersList component.
 * @return the initialized component instance
 */
public Form getOrdersList () {
if (ordersList == null) {//GEN-END:|15-getter|0|15-preInit
 // write pre-init user code here
ordersList = new Form ("Lista de productos:");//GEN-BEGIN:|15-getter|1|15-postInit
ordersList.setTicker (getRecTicker ());
ordersList.addCommand (getExitOLCommand ());
ordersList.addCommand (getSendCommand ());
ordersList.addCommand (getRecommendationCommand ());
ordersList.setCommandListener (this);//GEN-END:|15-getter|1|15-postInit
 // write post-init user code here
}//GEN-BEGIN:|15-getter|2|
return ordersList;
}
//</editor-fold>//GEN-END:|15-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitProfileCommand ">//GEN-BEGIN:|16-getter|0|16-preInit
/**
 * Returns an initiliazed instance of exitProfileCommand component.
 * @return the initialized component instance
 */
public Command getExitProfileCommand () {
if (exitProfileCommand == null) {//GEN-END:|16-getter|0|16-preInit
 // write pre-init user code here
exitProfileCommand = new Command ("Salir", Command.EXIT, 0);//GEN-LINE:|16-getter|1|16-postInit
 // write post-init user code here
}//GEN-BEGIN:|16-getter|2|
return exitProfileCommand;
}
//</editor-fold>//GEN-END:|16-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: saveCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
/**
 * Returns an initiliazed instance of saveCommand component.
 * @return the initialized component instance
 */
public Command getSaveCommand () {
if (saveCommand == null) {//GEN-END:|18-getter|0|18-preInit
 // write pre-init user code here
saveCommand = new Command ("Guardar", Command.OK, 0);//GEN-LINE:|18-getter|1|18-postInit
 // write post-init user code here
}//GEN-BEGIN:|18-getter|2|
return saveCommand;
}
//</editor-fold>//GEN-END:|18-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitOLCommand ">//GEN-BEGIN:|21-getter|0|21-preInit
/**
 * Returns an initiliazed instance of exitOLCommand component.
 * @return the initialized component instance
 */
public Command getExitOLCommand () {
if (exitOLCommand == null) {//GEN-END:|21-getter|0|21-preInit
 // write pre-init user code here
exitOLCommand = new Command ("Salir", Command.EXIT, 0);//GEN-LINE:|21-getter|1|21-postInit
 // write post-init user code here
}//GEN-BEGIN:|21-getter|2|
return exitOLCommand;
}
//</editor-fold>//GEN-END:|21-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: sendCommand ">//GEN-BEGIN:|23-getter|0|23-preInit
/**
 * Returns an initiliazed instance of sendCommand component.
 * @return the initialized component instance
 */
public Command getSendCommand () {
if (sendCommand == null) {//GEN-END:|23-getter|0|23-preInit
 // write pre-init user code here
sendCommand = new Command ("Enviar", Command.OK, 2);//GEN-LINE:|23-getter|1|23-postInit
 // write post-init user code here
}//GEN-BEGIN:|23-getter|2|
return sendCommand;
}
//</editor-fold>//GEN-END:|23-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem ">//GEN-BEGIN:|29-getter|0|29-preInit
/**
 * Returns an initiliazed instance of stringItem component.
 * @return the initialized component instance
 */
public StringItem getStringItem () {
if (stringItem == null) {//GEN-END:|29-getter|0|29-preInit
 // write pre-init user code here
stringItem = new StringItem ("", "Datos personales:");//GEN-BEGIN:|29-getter|1|29-postInit
stringItem.setFont (getFont ());//GEN-END:|29-getter|1|29-postInit
 // write post-init user code here
}//GEN-BEGIN:|29-getter|2|
return stringItem;
}
//</editor-fold>//GEN-END:|29-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem1 ">//GEN-BEGIN:|31-getter|0|31-preInit
/**
 * Returns an initiliazed instance of stringItem1 component.
 * @return the initialized component instance
 */
public StringItem getStringItem1 () {
if (stringItem1 == null) {//GEN-END:|31-getter|0|31-preInit
 // write pre-init user code here
stringItem1 = new StringItem ("", "Direcci\u00F3n:");//GEN-BEGIN:|31-getter|1|31-postInit
stringItem1.setFont (getFont ());//GEN-END:|31-getter|1|31-postInit
 // write post-init user code here
}//GEN-BEGIN:|31-getter|2|
return stringItem1;
}
//</editor-fold>//GEN-END:|31-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtFStreet ">//GEN-BEGIN:|32-getter|0|32-preInit
/**
 * Returns an initiliazed instance of txtFStreet component.
 * @return the initialized component instance
 */
public TextField getTxtFStreet () {
if (txtFStreet == null) {//GEN-END:|32-getter|0|32-preInit
 // write pre-init user code here
txtFStreet = new TextField ("Calle:", "", 32, TextField.ANY);//GEN-LINE:|32-getter|1|32-postInit
 // write post-init user code here
}//GEN-BEGIN:|32-getter|2|
return txtFStreet;
}
//</editor-fold>//GEN-END:|32-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtFNumber ">//GEN-BEGIN:|33-getter|0|33-preInit
/**
 * Returns an initiliazed instance of txtFNumber component.
 * @return the initialized component instance
 */
public TextField getTxtFNumber () {
if (txtFNumber == null) {//GEN-END:|33-getter|0|33-preInit
 // write pre-init user code here
txtFNumber = new TextField ("N\u00FAmero:", "", 32, TextField.ANY);//GEN-LINE:|33-getter|1|33-postInit
 // write post-init user code here
}//GEN-BEGIN:|33-getter|2|
return txtFNumber;
}
//</editor-fold>//GEN-END:|33-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtFZipCode ">//GEN-BEGIN:|34-getter|0|34-preInit
/**
 * Returns an initiliazed instance of txtFZipCode component.
 * @return the initialized component instance
 */
public TextField getTxtFZipCode () {
if (txtFZipCode == null) {//GEN-END:|34-getter|0|34-preInit
 // write pre-init user code here
txtFZipCode = new TextField ("C\u00F3digo postal:", "", 32, TextField.ANY);//GEN-LINE:|34-getter|1|34-postInit
 // write post-init user code here
}//GEN-BEGIN:|34-getter|2|
return txtFZipCode;
}
//</editor-fold>//GEN-END:|34-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtFTown ">//GEN-BEGIN:|35-getter|0|35-preInit
/**
 * Returns an initiliazed instance of txtFTown component.
 * @return the initialized component instance
 */
public TextField getTxtFTown () {
if (txtFTown == null) {//GEN-END:|35-getter|0|35-preInit
 // write pre-init user code here
txtFTown = new TextField ("Localidad:", "", 32, TextField.ANY);//GEN-LINE:|35-getter|1|35-postInit
 // write post-init user code here
}//GEN-BEGIN:|35-getter|2|
return txtFTown;
}
//</editor-fold>//GEN-END:|35-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtFState ">//GEN-BEGIN:|36-getter|0|36-preInit
/**
 * Returns an initiliazed instance of txtFState component.
 * @return the initialized component instance
 */
public TextField getTxtFState () {
if (txtFState == null) {//GEN-END:|36-getter|0|36-preInit
 // write pre-init user code here
txtFState = new TextField ("Provincia:", "", 32, TextField.ANY);//GEN-LINE:|36-getter|1|36-postInit
 // write post-init user code here
}//GEN-BEGIN:|36-getter|2|
return txtFState;
}
//</editor-fold>//GEN-END:|36-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtFDNI ">//GEN-BEGIN:|37-getter|0|37-preInit
/**
 * Returns an initiliazed instance of txtFDNI component.
 * @return the initialized component instance
 */
public TextField getTxtFDNI () {
if (txtFDNI == null) {//GEN-END:|37-getter|0|37-preInit
 // write pre-init user code here
txtFDNI = new TextField ("DNI:", "", 32, TextField.ANY);//GEN-LINE:|37-getter|1|37-postInit
 // write post-init user code here
}//GEN-BEGIN:|37-getter|2|
return txtFDNI;
}
//</editor-fold>//GEN-END:|37-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtFName ">//GEN-BEGIN:|38-getter|0|38-preInit
/**
 * Returns an initiliazed instance of txtFName component.
 * @return the initialized component instance
 */
public TextField getTxtFName () {
if (txtFName == null) {//GEN-END:|38-getter|0|38-preInit
 // write pre-init user code here
txtFName = new TextField ("Nombre:", "", 32, TextField.ANY);//GEN-LINE:|38-getter|1|38-postInit
 // write post-init user code here
}//GEN-BEGIN:|38-getter|2|
return txtFName;
}
//</editor-fold>//GEN-END:|38-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtFSurname ">//GEN-BEGIN:|39-getter|0|39-preInit
/**
 * Returns an initiliazed instance of txtFSurname component.
 * @return the initialized component instance
 */
public TextField getTxtFSurname () {
if (txtFSurname == null) {//GEN-END:|39-getter|0|39-preInit
 // write pre-init user code here
txtFSurname = new TextField ("Apellidos:", "", 32, TextField.ANY);//GEN-LINE:|39-getter|1|39-postInit
 // write post-init user code here
}//GEN-BEGIN:|39-getter|2|
return txtFSurname;
}
//</editor-fold>//GEN-END:|39-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: font ">//GEN-BEGIN:|30-getter|0|30-preInit
/**
 * Returns an initiliazed instance of font component.
 * @return the initialized component instance
 */
public Font getFont () {
if (font == null) {//GEN-END:|30-getter|0|30-preInit
 // write pre-init user code here
font = Font.getFont (Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);//GEN-LINE:|30-getter|1|30-postInit
 // write post-init user code here
}//GEN-BEGIN:|30-getter|2|
return font;
}
//</editor-fold>//GEN-END:|30-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitPECommand ">//GEN-BEGIN:|41-getter|0|41-preInit
/**
 * Returns an initiliazed instance of exitPECommand component.
 * @return the initialized component instance
 */
public Command getExitPECommand () {
if (exitPECommand == null) {//GEN-END:|41-getter|0|41-preInit
 // write pre-init user code here
exitPECommand = new Command ("Salir", Command.EXIT, 0);//GEN-LINE:|41-getter|1|41-postInit
 // write post-init user code here
}//GEN-BEGIN:|41-getter|2|
return exitPECommand;
}
//</editor-fold>//GEN-END:|41-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitPSCommand ">//GEN-BEGIN:|43-getter|0|43-preInit
/**
 * Returns an initiliazed instance of exitPSCommand component.
 * @return the initialized component instance
 */
public Command getExitPSCommand () {
if (exitPSCommand == null) {//GEN-END:|43-getter|0|43-preInit
 // write pre-init user code here
exitPSCommand = new Command ("Salir", Command.EXIT, 0);//GEN-LINE:|43-getter|1|43-postInit
 // write post-init user code here
}//GEN-BEGIN:|43-getter|2|
return exitPSCommand;
}
//</editor-fold>//GEN-END:|43-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: main ">//GEN-BEGIN:|47-getter|0|47-preInit
/**
 * Returns an initiliazed instance of main component.
 * @return the initialized component instance
 */
public Form getMain () {
if (main == null) {//GEN-END:|47-getter|0|47-preInit
 // write pre-init user code here
main = new Form ("\u00A1Bienvenido a MobiCarta!", new Item[] { getStringItem2 (), getImageItem (), getStringItem3 () });//GEN-BEGIN:|47-getter|1|47-postInit
main.addCommand (getExitMainCommand ());
main.addCommand (getProfileCommand ());
main.setCommandListener (this);//GEN-END:|47-getter|1|47-postInit
 // write post-init user code here
}//GEN-BEGIN:|47-getter|2|
return main;
}
//</editor-fold>//GEN-END:|47-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitMainCommand ">//GEN-BEGIN:|48-getter|0|48-preInit
/**
 * Returns an initiliazed instance of exitMainCommand component.
 * @return the initialized component instance
 */
public Command getExitMainCommand () {
if (exitMainCommand == null) {//GEN-END:|48-getter|0|48-preInit
 // write pre-init user code here
exitMainCommand = new Command ("Salir", Command.EXIT, 0);//GEN-LINE:|48-getter|1|48-postInit
 // write post-init user code here
}//GEN-BEGIN:|48-getter|2|
return exitMainCommand;
}
//</editor-fold>//GEN-END:|48-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem2 ">//GEN-BEGIN:|52-getter|0|52-preInit
/**
 * Returns an initiliazed instance of stringItem2 component.
 * @return the initialized component instance
 */
public StringItem getStringItem2 () {
if (stringItem2 == null) {//GEN-END:|52-getter|0|52-preInit
 // write pre-init user code here
stringItem2 = new StringItem ("", "S\u00E1quele partido a la tecnolog\u00EDa NFC de su m\u00F3vil");//GEN-LINE:|52-getter|1|52-postInit
 // write post-init user code here
}//GEN-BEGIN:|52-getter|2|
return stringItem2;
}
//</editor-fold>//GEN-END:|52-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitOLSCommand ">//GEN-BEGIN:|56-getter|0|56-preInit
/**
 * Returns an initiliazed instance of exitOLSCommand component.
 * @return the initialized component instance
 */
public Command getExitOLSCommand () {
if (exitOLSCommand == null) {//GEN-END:|56-getter|0|56-preInit
 // write pre-init user code here
exitOLSCommand = new Command ("Exit", Command.EXIT, 0);//GEN-LINE:|56-getter|1|56-postInit
 // write post-init user code here
}//GEN-BEGIN:|56-getter|2|
return exitOLSCommand;
}
//</editor-fold>//GEN-END:|56-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitOLECommand ">//GEN-BEGIN:|58-getter|0|58-preInit
/**
 * Returns an initiliazed instance of exitOLECommand component.
 * @return the initialized component instance
 */
public Command getExitOLECommand () {
if (exitOLECommand == null) {//GEN-END:|58-getter|0|58-preInit
 // write pre-init user code here
exitOLECommand = new Command ("Exit", Command.EXIT, 0);//GEN-LINE:|58-getter|1|58-postInit
 // write post-init user code here
}//GEN-BEGIN:|58-getter|2|
return exitOLECommand;
}
//</editor-fold>//GEN-END:|58-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelSubtractCommand ">//GEN-BEGIN:|63-getter|0|63-preInit
/**
 * Returns an initiliazed instance of cancelSubtractCommand component.
 * @return the initialized component instance
 */
public Command getCancelSubtractCommand () {
if (cancelSubtractCommand == null) {//GEN-END:|63-getter|0|63-preInit
 // write pre-init user code here
cancelSubtractCommand = new Command ("Cancelar", Command.CANCEL, 0);//GEN-LINE:|63-getter|1|63-postInit
 // write post-init user code here
}//GEN-BEGIN:|63-getter|2|
return cancelSubtractCommand;
}
//</editor-fold>//GEN-END:|63-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: error ">//GEN-BEGIN:|26-getter|0|26-preInit
/**
 * Returns an initiliazed instance of error component.
 * @return the initialized component instance
 */
public Alert getError () {
if (error == null) {//GEN-END:|26-getter|0|26-preInit
 // write pre-init user code here
error = new Alert ("PERFIL NO GUARDADO", "Fallo al escribir el perfil en el fichero", null, AlertType.ERROR);//GEN-BEGIN:|26-getter|1|26-postInit
error.addCommand (getExitPECommand ());
error.setCommandListener (this);
error.setTimeout (Alert.FOREVER);//GEN-END:|26-getter|1|26-postInit
 // write post-init user code here
}//GEN-BEGIN:|26-getter|2|
return error;
}
//</editor-fold>//GEN-END:|26-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: success ">//GEN-BEGIN:|27-getter|0|27-preInit
/**
 * Returns an initiliazed instance of success component.
 * @return the initialized component instance
 */
public Alert getSuccess () {
if (success == null) {//GEN-END:|27-getter|0|27-preInit
 // write pre-init user code here
success = new Alert ("PERFIL ALMACENADO", "El perfil ha sido guardado satisfactoriamente", null, AlertType.CONFIRMATION);//GEN-BEGIN:|27-getter|1|27-postInit
success.addCommand (getExitPSCommand ());
success.setCommandListener (this);
success.setTimeout (Alert.FOREVER);//GEN-END:|27-getter|1|27-postInit
 // write post-init user code here
}//GEN-BEGIN:|27-getter|2|
return success;
}
//</editor-fold>//GEN-END:|27-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: subtractElement ">//GEN-BEGIN:|62-getter|0|62-preInit
/**
 * Returns an initiliazed instance of subtractElement component.
 * @return the initialized component instance
 */
public Alert getSubtractElement () {
if (subtractElement == null) {//GEN-END:|62-getter|0|62-preInit
 // write pre-init user code here
subtractElement = new Alert ("ELIMINAR ELEMENTO", "Seleccione el producto que desea decrementar", null, null);//GEN-BEGIN:|62-getter|1|62-postInit
subtractElement.addCommand (getCancelSubtractCommand ());
subtractElement.setCommandListener (this);
subtractElement.setTimeout (Alert.FOREVER);//GEN-END:|62-getter|1|62-postInit
 // write post-init user code here
}//GEN-BEGIN:|62-getter|2|
return subtractElement;
}
//</editor-fold>//GEN-END:|62-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: imageItem ">//GEN-BEGIN:|71-getter|0|71-preInit
/**
 * Returns an initiliazed instance of imageItem component.
 * @return the initialized component instance
 */
public ImageItem getImageItem () {
if (imageItem == null) {//GEN-END:|71-getter|0|71-preInit
 // write pre-init user code here
imageItem = new ImageItem ("", getImage (), ImageItem.LAYOUT_CENTER | Item.LAYOUT_TOP | Item.LAYOUT_BOTTOM | Item.LAYOUT_VCENTER | ImageItem.LAYOUT_NEWLINE_BEFORE, "<Missing Image>");//GEN-LINE:|71-getter|1|71-postInit
 // write post-init user code here
}//GEN-BEGIN:|71-getter|2|
return imageItem;
}
//</editor-fold>//GEN-END:|71-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image ">//GEN-BEGIN:|72-getter|0|72-preInit
/**
 * Returns an initiliazed instance of image component.
 * @return the initialized component instance
 */
public Image getImage () {
if (image == null) {//GEN-END:|72-getter|0|72-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|72-getter|1|72-@java.io.IOException
image = Image.createImage ("/ico2.png");
} catch (java.io.IOException e) {//GEN-END:|72-getter|1|72-@java.io.IOException
e.printStackTrace ();
}//GEN-LINE:|72-getter|2|72-postInit
 // write post-init user code here
}//GEN-BEGIN:|72-getter|3|
return image;
}
//</editor-fold>//GEN-END:|72-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem3 ">//GEN-BEGIN:|74-getter|0|74-preInit
/**
 * Returns an initiliazed instance of stringItem3 component.
 * @return the initialized component instance
 */
public StringItem getStringItem3 () {
if (stringItem3 == null) {//GEN-END:|74-getter|0|74-preInit
 // write pre-init user code here
stringItem3 = new StringItem ("", "Acerque su m\u00F3vil a una etiqueta para iniciar la acci\u00F3n...");//GEN-LINE:|74-getter|1|74-postInit
 // write post-init user code here
}//GEN-BEGIN:|74-getter|2|
return stringItem3;
}
//</editor-fold>//GEN-END:|74-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: ProfileCommand ">//GEN-BEGIN:|75-getter|0|75-preInit
/**
 * Returns an initiliazed instance of ProfileCommand component.
 * @return the initialized component instance
 */
public Command getProfileCommand () {
if (ProfileCommand == null) {//GEN-END:|75-getter|0|75-preInit
 // write pre-init user code here
ProfileCommand = new Command ("Perfil", Command.ITEM, 0);//GEN-LINE:|75-getter|1|75-postInit
 // write post-init user code here
}//GEN-BEGIN:|75-getter|2|
return ProfileCommand;
}
//</editor-fold>//GEN-END:|75-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|80-getter|0|80-preInit
/**
 * Returns an initiliazed instance of backCommand component.
 * @return the initialized component instance
 */
public Command getBackCommand () {
if (backCommand == null) {//GEN-END:|80-getter|0|80-preInit
 // write pre-init user code here
backCommand = new Command ("Volver", Command.BACK, 0);//GEN-LINE:|80-getter|1|80-postInit
 // write post-init user code here
}//GEN-BEGIN:|80-getter|2|
return backCommand;
}
//</editor-fold>//GEN-END:|80-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: checkpoint ">//GEN-BEGIN:|84-getter|0|84-preInit
/**
 * Returns an initiliazed instance of checkpoint component.
 * @return the initialized component instance
 */
public Alert getCheckpoint () {
if (checkpoint == null) {//GEN-END:|84-getter|0|84-preInit
 // write pre-init user code here
checkpoint = new Alert ("Registro de usuarios", "Registro efectuado satisfactoriamente", null, AlertType.CONFIRMATION);//GEN-BEGIN:|84-getter|1|84-postInit
checkpoint.setTimeout (3000);//GEN-END:|84-getter|1|84-postInit
 // write post-init user code here
}//GEN-BEGIN:|84-getter|2|
return checkpoint;
}
//</editor-fold>//GEN-END:|84-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|85-getter|0|85-preInit
/**
 * Returns an initiliazed instance of exitCommand component.
 * @return the initialized component instance
 */
public Command getExitCommand () {
if (exitCommand == null) {//GEN-END:|85-getter|0|85-preInit
 // write pre-init user code here
exitCommand = new Command ("Salir", Command.EXIT, 0);//GEN-LINE:|85-getter|1|85-postInit
 // write post-init user code here
}//GEN-BEGIN:|85-getter|2|
return exitCommand;
}
//</editor-fold>//GEN-END:|85-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Opening ">//GEN-BEGIN:|88-getter|0|88-preInit
/**
 * Returns an initiliazed instance of Opening component.
 * @return the initialized component instance
 */
public Form getOpening () {
if (Opening == null) {//GEN-END:|88-getter|0|88-preInit
 // write pre-init user code here
Opening = new Form ("Estimado cliente:", new Item[] { getImageItem1 (), getSItemOpening () });//GEN-BEGIN:|88-getter|1|88-postInit
Opening.addCommand (getExitOpeningCommand ());
Opening.setCommandListener (this);//GEN-END:|88-getter|1|88-postInit
 // write post-init user code here
}//GEN-BEGIN:|88-getter|2|
return Opening;
}
//</editor-fold>//GEN-END:|88-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: imageItem1 ">//GEN-BEGIN:|92-getter|0|92-preInit
/**
 * Returns an initiliazed instance of imageItem1 component.
 * @return the initialized component instance
 */
public ImageItem getImageItem1 () {
if (imageItem1 == null) {//GEN-END:|92-getter|0|92-preInit
 // write pre-init user code here
imageItem1 = new ImageItem ("", getImage (), ImageItem.LAYOUT_CENTER | Item.LAYOUT_TOP | Item.LAYOUT_BOTTOM | Item.LAYOUT_VCENTER, "<Missing Image>");//GEN-LINE:|92-getter|1|92-postInit
 // write post-init user code here
}//GEN-BEGIN:|92-getter|2|
return imageItem1;
}
//</editor-fold>//GEN-END:|92-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: sItemOpening ">//GEN-BEGIN:|93-getter|0|93-preInit
/**
 * Returns an initiliazed instance of sItemOpening component.
 * @return the initialized component instance
 */
public StringItem getSItemOpening () {
if (sItemOpening == null) {//GEN-END:|93-getter|0|93-preInit
 // write pre-init user code here
sItemOpening = new StringItem ("", "");//GEN-LINE:|93-getter|1|93-postInit
 // write post-init user code here
}//GEN-BEGIN:|93-getter|2|
return sItemOpening;
}
//</editor-fold>//GEN-END:|93-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitOpeningCommand ">//GEN-BEGIN:|89-getter|0|89-preInit
/**
 * Returns an initiliazed instance of exitOpeningCommand component.
 * @return the initialized component instance
 */
public Command getExitOpeningCommand () {
if (exitOpeningCommand == null) {//GEN-END:|89-getter|0|89-preInit
 // write pre-init user code here
exitOpeningCommand = new Command ("Salir", Command.EXIT, 0);//GEN-LINE:|89-getter|1|89-postInit
 // write post-init user code here
}//GEN-BEGIN:|89-getter|2|
return exitOpeningCommand;
}
//</editor-fold>//GEN-END:|89-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: bill ">//GEN-BEGIN:|94-getter|0|94-preInit
/**
 * Returns an initiliazed instance of bill component.
 * @return the initialized component instance
 */
public Form getBill () {
if (bill == null) {//GEN-END:|94-getter|0|94-preInit
 // write pre-init user code here
bill = new Form ("Factura:");//GEN-BEGIN:|94-getter|1|94-postInit
bill.addCommand (getExitBillCommand ());
bill.setCommandListener (this);//GEN-END:|94-getter|1|94-postInit
 // write post-init user code here
}//GEN-BEGIN:|94-getter|2|
return bill;
}
//</editor-fold>//GEN-END:|94-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitBillCommand ">//GEN-BEGIN:|95-getter|0|95-preInit
/**
 * Returns an initiliazed instance of exitBillCommand component.
 * @return the initialized component instance
 */
public Command getExitBillCommand () {
if (exitBillCommand == null) {//GEN-END:|95-getter|0|95-preInit
 // write pre-init user code here
exitBillCommand = new Command ("Exit", Command.EXIT, 0);//GEN-LINE:|95-getter|1|95-postInit
 // write post-init user code here
}//GEN-BEGIN:|95-getter|2|
return exitBillCommand;
}
//</editor-fold>//GEN-END:|95-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: recommendations ">//GEN-BEGIN:|100-getter|0|100-preInit
/**
 * Returns an initiliazed instance of recommendations component.
 * @return the initialized component instance
 */
public Form getRecommendations () {
if (recommendations == null) {//GEN-END:|100-getter|0|100-preInit
 // write pre-init user code here
recommendations = new Form ("Recomendaciones:");//GEN-BEGIN:|100-getter|1|100-postInit
recommendations.addCommand (getBackCommand1 ());
recommendations.setCommandListener (this);//GEN-END:|100-getter|1|100-postInit
 // write post-init user code here
}//GEN-BEGIN:|100-getter|2|
return recommendations;
}
//</editor-fold>//GEN-END:|100-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: recommendationCommand ">//GEN-BEGIN:|98-getter|0|98-preInit
/**
 * Returns an initiliazed instance of recommendationCommand component.
 * @return the initialized component instance
 */
public Command getRecommendationCommand () {
if (recommendationCommand == null) {//GEN-END:|98-getter|0|98-preInit
 // write pre-init user code here
recommendationCommand = new Command ("Recomendaciones", Command.ITEM, 1);//GEN-LINE:|98-getter|1|98-postInit
 // write post-init user code here
}//GEN-BEGIN:|98-getter|2|
return recommendationCommand;
}
//</editor-fold>//GEN-END:|98-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand1 ">//GEN-BEGIN:|102-getter|0|102-preInit
/**
 * Returns an initiliazed instance of backCommand1 component.
 * @return the initialized component instance
 */
public Command getBackCommand1 () {
if (backCommand1 == null) {//GEN-END:|102-getter|0|102-preInit
 // write pre-init user code here
backCommand1 = new Command ("Atr\u00E1s", Command.BACK, 0);//GEN-LINE:|102-getter|1|102-postInit
 // write post-init user code here
}//GEN-BEGIN:|102-getter|2|
return backCommand1;
}
//</editor-fold>//GEN-END:|102-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: recTicker ">//GEN-BEGIN:|105-getter|0|105-preInit
/**
 * Returns an initiliazed instance of recTicker component.
 * @return the initialized component instance
 */
public Ticker getRecTicker () {
if (recTicker == null) {//GEN-END:|105-getter|0|105-preInit
 // write pre-init user code here
recTicker = new Ticker ("");//GEN-LINE:|105-getter|1|105-postInit
 // write post-init user code here
}//GEN-BEGIN:|105-getter|2|
return recTicker;
}
//</editor-fold>//GEN-END:|105-getter|2|

public Gauge getGauge() {
    if (gauge == null)
        gauge = new Gauge("Conectando...", false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING);
    return gauge;
}

    /* Metodo para registrar el Listener NFC y capturar sus errores */
    public void addNFCListener(){
        try {
            DiscoveryManager.getInstance().addNDEFRecordListener(this, new NDEFRecordType(NDEFRecordType.MIME, "app/checkpoint"));
            DiscoveryManager.getInstance().addNDEFRecordListener(this, new NDEFRecordType(NDEFRecordType.MIME, "app/bill-request"));
            DiscoveryManager.getInstance().addNDEFRecordListener(this, new NDEFRecordType(NDEFRecordType.MIME, "app/product"));
            DiscoveryManager.getInstance().addTargetListener((TargetListener)this, TargetType.NDEF_TAG);
            DiscoveryManager.getInstance().addTargetListener((TargetListener)this, TargetType.RFID_TAG);
        } catch (IllegalStateException ex) {
        } catch (ContactlessException ex) {
        }
    }

    /* Metodo para eliminar el Listener NFC y capturar sus errores */
    public void removeNFCListener(){
        try {
            DiscoveryManager.getInstance().removeTargetListener((TargetListener)this, TargetType.NDEF_TAG);
            DiscoveryManager.getInstance().removeTargetListener((TargetListener)this, TargetType.RFID_TAG);
        } catch (IllegalStateException ex) {
        }
    }
    
    public void removeNDEFListener() {
        DiscoveryManager.getInstance().removeNDEFRecordListener(this, new NDEFRecordType(NDEFRecordType.MIME, "app/checkpoint"));
        DiscoveryManager.getInstance().removeNDEFRecordListener(this, new NDEFRecordType(NDEFRecordType.MIME, "app/bill-request"));
        DiscoveryManager.getInstance().removeNDEFRecordListener(this, new NDEFRecordType(NDEFRecordType.MIME, "app/product"));
    }

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay() {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet();
        } else {
            initialize();
            startMIDlet();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
        removeNFCListener();
    }

    public void targetDetected(TargetProperties[] tps) {
        NDEFTagConnection connection = null;
        try {
            connection = (NDEFTagConnection)Connector.open(tps[0].getUrl());
            tagProcessing(((NDEFTagConnection)connection).readNDEF());
        } catch (Exception e) { }
        finally {
            try {
                connection.close();
            } catch (IOException ex) { }
        }
    }

    public void recordDetected(NDEFMessage ndefm) {
        nfcStart = true;
        removeNDEFListener();
        tagProcessing(ndefm);
    }
    
    private void tagProcessing(NDEFMessage message) {
        String type = message.getRecord(0).getRecordType().getName().toString();
        String content = new String(message.getRecord(0).getPayload());
        if (type.equals("app/checkpoint")) {
            removeNFCListener();
            clientRegister(content);
        }
        else if (type.equals("app/bill-request")) {
            removeNFCListener();
            billRequest(content);
        }
        else if (type.equals("app/product")) {
            if (getDisplay().getCurrent() == getOrdersList())
                addProduct(content);
            else if (getDisplay().getCurrent() == getSubtractElement())
                subtractProduct(content);
            else
                newOrder(content);
        }
        else if (type.equals("app/send-order")) {
            if (getDisplay().getCurrent() == getOrdersList() && ProductsListManager.productsList.size() > 0) {
                removeNFCListener();
                sendOrder(content);
            }
        }
        else if (type.equals("app/subtract-product")) {
            if (getDisplay().getCurrent() == getOrdersList() && ProductsListManager.productsList.size() > 0)
                getDisplay().setCurrent(getSubtractElement());
        }
    }
    
    private void clientRegister(String receiver) {
        if (!FileIO.loadFile(ProfileManager.path).equals("")) {
            if (ProfileManager.sendProfile(receiver, this))
                connecting("Registrando cliente");
            else
                showAlert("Usuario no registrado", "Se produjo un error en la conexión Bluetooth", AlertType.ERROR);
        }
        else {
            Alert alert1 = new Alert("Perfil no encontrado", "Complete el siguiente formulario con sus datos", null, AlertType.WARNING);
            Display.getDisplay(this).setCurrent(alert1, getProfile());
        }
    }
    
    private void billRequest(String bar) {
        if (ProductsListManager.billRequest(bar, this))
            connecting("Solicitando factura");
        else
            showAlert("Solicitud no enviada", "Se produjo un error al enviar la solicitud de facturación", AlertType.ERROR);
    }
    
    private void newOrder(String product) {
        RecommendationManager.loadRecommendation(this);
        getDisplay().setCurrent(getOrdersList());
        String n = ProductsListManager.addProduct(product);
        getOrdersList().append(new StringItem(product, n));
    }
    
    private void addProduct(String product) {
        String n = ProductsListManager.addProduct(product);
        if (n.substring(0, 2).equals("1 "))
            getOrdersList().append(new StringItem(product, n));
        else
            for (int i = 0; i < getOrdersList().size(); i++)
                if (((StringItem)getOrdersList().get(i)).getLabel().toString().equals(product)) {
                    ((StringItem)getOrdersList().get(i)).setText(String.valueOf(n));
                    break;
                }
    }
    
    private void subtractProduct(String product) {
        int n = ProductsListManager.removeProduct(product);
        if (n != -1) {
            if (n == 0) {
                for (int i = 0; i < getOrdersList().size(); i++)
                    if (((StringItem)getOrdersList().get(i)).getLabel().toString().equals(product)) {
                        getOrdersList().delete(i);
                        getDisplay().setCurrent(getOrdersList());
                        break;
                    }
            }
            else {
                for (int i = 0; i < getOrdersList().size(); i++)
                    if (((StringItem)getOrdersList().get(i)).getLabel().toString().equals(product)) {
                        ((StringItem)getOrdersList().get(i)).setText(String.valueOf(n));
                        getDisplay().setCurrent(getOrdersList());
                        break;
                    }
            }
        }
    }
    
    private void sendOrder(String bar) {
        if (ProductsListManager.sendOrder(bar, 1, this))
            connecting("Enviando pedido");
        else
            this.showAlert("Pedido no enviado", "Se produjo un error en el envío del pedido", AlertType.ERROR);
    }
    
    private void connecting(String msg) {
        searching = new Form(msg);
        gauge = new Gauge("Conectando...", false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING);
        searching.append(gauge);
        cancel = new Command("Cancelar", Command.ITEM, 1);
        searching.addCommand(cancel);
        searching.setCommandListener(this);
        Display.getDisplay(this).setCurrent(searching);
    }
    
    public void showAlert(String title, String message, AlertType type) {
        alert = new Alert(title, message, null, type);
        exit = new Command("Salir", Command.EXIT, 1);
        alert.addCommand(exit);
        alert.setCommandListener(this);
        alert.setTimeout(5000);
        Display.getDisplay(this).setCurrent(alert);
    }
}
