package Paquete;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Fabian_Montoya
 */
public class NewUser extends JDialog implements ActionListener, KeyListener {

    Connection conexion;
    ResultSet resultado;
    Statement sentencia;

    JLabel LTitulo, LSub, LSub2, LSub3, LNombres, LApellidos, LDocumento, LDireccion, LCelular, LTelefono, LComentario;
    JTextField TNombres, TApellidos, TDocumento, TDireccion, TCelular, TTelefono, TComentario;
    JButton BGuardar, BReiniciar;

    String Nombre, Apellido, Documento, Telefono, Celular, Direccion, Comentario;
    int LimiteNombre = 60, LimiteApellido = 60, LimiteDocumento = 20, LimiteDireccion = 100, LimiteCelular = 11, LimiteTelefono = 8, LimiteComentario = 400;

    public NewUser(Frame parent, boolean modal) {
        super(parent, modal);
        try {
            setTitle("Ingresar Nuevo Cliente - ARTURO 1.0.5");
            setLayout(null);
            Image icon = new ImageIcon(getClass().getResource("/Imagenes/Users.png")).getImage();
            setIconImage(icon);

            //------
            Textos(); //Es Textos, verificar que se cancele la apertura de la ventana dentro del catch.
            Cajas();
            Botones();

            setSize(400, 450);
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al abrir la ventana.\n\nPor favor consulte con su desarrollador.\n\n Pulse ACEPTAR para ver el error.", "¡ERROR! - NewUser.java", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "\n\nPulse ACEPTAR, la acción se cancelará.", "¡ERROR! - NewUser.java", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void Textos() {
        LTitulo = new JLabel("INGRESO NUEVO CLIENTE");
        LTitulo.setFont(LTitulo.getFont().deriveFont(14f));
        LTitulo.setBounds(105, 2, 300, 20);

        LSub = new JLabel("Ingrese todos los datos solicitados y luego pulse INSERTAR");
        LSub.setBounds(25, 25, 440, 20);

        LSub2 = new JLabel("para guardar el cliente.");
        LSub2.setBounds(125, 40, 440, 20);

        LSub3 = new JLabel("* Campo obligatorio.");
        LSub3.setBounds(15, 63, 440, 20);
        LSub3.setFont(LTitulo.getFont().deriveFont(8f));

        LNombres = new JLabel("Nombre(s)*:");
        LNombres.setBounds(15, 90, 440, 20);

        LApellidos = new JLabel("Apellidos*:");
        LApellidos.setBounds(15, 120, 440, 20);

        LDocumento = new JLabel("Número Documento*:");
        LDocumento.setBounds(15, 150, 440, 20);

        LDireccion = new JLabel("Dirección Domicilio:");
        LDireccion.setBounds(15, 180, 440, 20);

        LCelular = new JLabel("Número Celular:");
        LCelular.setBounds(15, 210, 440, 20);

        LTelefono = new JLabel("Número Teléfono:");
        LTelefono.setBounds(15, 240, 440, 20);

        LComentario = new JLabel("Comentario:");
        LComentario.setBounds(15, 270, 440, 20);

        add(LTitulo);
        add(LSub);
        add(LSub2);
        add(LSub3);
        add(LNombres);
        add(LApellidos);
        add(LDocumento);
        add(LDireccion);
        add(LCelular);
        add(LTelefono);
        add(LComentario);
    }

    public void Cajas() {
        TNombres = new JTextField("");
        TNombres.setBounds(140, 90, 170, 20);
        TNombres.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        TNombres.addKeyListener(this);

        TApellidos = new JTextField("");
        TApellidos.setBounds(140, 120, 170, 20);
        TApellidos.addKeyListener(this);

        TDocumento = new JTextField("");
        TDocumento.setBounds(140, 150, 170, 20);
        TDocumento.addKeyListener(this);

        TDireccion = new JTextField("");
        TDireccion.setBounds(140, 180, 170, 20);
        TDireccion.addKeyListener(this);

        TCelular = new JTextField("");
        TCelular.setBounds(140, 210, 170, 20);
        TCelular.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        TCelular.addKeyListener(this);

        TTelefono = new JTextField("");
        TTelefono.setBounds(140, 240, 170, 20);
        TTelefono.addKeyListener(this);

        TComentario = new JTextField("");
        TComentario.setBounds(87, 270, 298, 20);
        TComentario.addKeyListener(this);

        add(TNombres);
        add(TApellidos);
        add(TDocumento);
        add(TDireccion);
        add(TCelular);
        add(TTelefono);
        add(TComentario);
    }

    public void Botones() {
        BGuardar = new JButton("Insertar");
        BGuardar.setBounds(82, 330, 100, 20);
        BGuardar.addActionListener(this);
        add(BGuardar);

        BReiniciar = new JButton("Reiniciar");
        BReiniciar.setBounds(200, 330, 100, 20);
        BReiniciar.addActionListener(this);
        add(BReiniciar);
    }

    public void Insertar(String nombre, String apellido, String documento, String direccion, String celular, String telefono, String comentario) {
        nombre = Nombre;
        apellido = Utilidades.Encriptar(Apellido);
        documento = Utilidades.Encriptar(Documento);
        direccion = Utilidades.Encriptar(Direccion);
        celular = Utilidades.Encriptar(Celular);
        telefono = Utilidades.Encriptar(Telefono);
        comentario = Utilidades.Encriptar(Comentario);

        String sql = "INSERT INTO CLIENTES (NOMBRES_CLI, APELLIDOS_CLI, DOCUMENTO_CLI, DIRECCION_CLI, CELULAR_CLI, TELEFONO_CLI, COMENTARIOS) VALUES ('" + nombre + "', '" + apellido + "', '" + documento + "', '" + direccion + "', '" + celular + "', '" + telefono + "', '" + comentario + "');";
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/UsuariosDB", "administrador", "ADMUsuarios");
            sentencia = conexion.createStatement();

            sentencia.execute(sql);
            conexion.close();

            JOptionPane.showMessageDialog(null, "Se ha guardado a " + Nombre + " " + Apellido + " con el documento número " + Documento + " correctamente.\nPara ver los cambios efectuados recuerda actualizar la tabla en la página principal.", "Confirmación - Ingreso exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al Insertar en la Base de Datos.\n\nPor favor consulte con su desarrollador.\nPulse ACEPTAR para ver el error.", "Form1 - ¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "", "NewUser.java - ¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int consultardocumento(String Documento) {
        int valido = 1;
        String CadenaDocumento = "";

        Documento = Utilidades.Encriptar(Documento);

        String sql = "SELECT documento_cli FROM CLIENTES WHERE documento_cli = '" + Documento + "'";
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/UsuariosDB", "administrador", "ADMUsuarios");
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            while (resultado.next() != false) {
                CadenaDocumento = resultado.getString("documento_cli");
                valido = 0;
            }
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al consultar el documento en la Base de Datos.\n\nPor favor consulte con su desarrollador.\nPulse ACEPTAR para ver el error.", "Form1 - ¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "", "NewUser.java - ¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }

        return valido;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == BReiniciar) {
            int ax2 = JOptionPane.showConfirmDialog(null, "El formulario se reiniciara\n¿Desea continuar?", "Confirmar - Reiniciar formulario", JOptionPane.YES_NO_OPTION);

            if (ax2 == JOptionPane.YES_OPTION) {
                TNombres.setText("");
                TApellidos.setText("");
                TDocumento.setText("");
                TTelefono.setText("");
                TCelular.setText("");
                TDireccion.setText("");
                TComentario.setText("");
                TNombres.requestFocus();

            } else if (ax2 == JOptionPane.NO_OPTION) {

            }
        }

        if (ae.getSource() == BGuardar) {

            Nombre = TNombres.getText().toUpperCase();
            Apellido = TApellidos.getText().toUpperCase();
            Documento = TDocumento.getText().toUpperCase();
            Direccion = TDireccion.getText().toUpperCase();
            Celular = TCelular.getText().toUpperCase();
            Telefono = TTelefono.getText().toUpperCase();
            Comentario = TComentario.getText();

            if (Nombre.length() != 0 && Apellido.length() != 0 && Documento.length() != 0) {
                int ax = JOptionPane.showConfirmDialog(null, "Los datos que usted digito fueron: \n- Nombre: " + Nombre + ".\n- Apellido: " + Apellido + ".\n- Documento: " + Documento + ".\n- Dirección: " + Direccion + ".\n- Número celular: " + Celular + ".\n- Número de teléfono: " + Telefono + ".\n- Comentario: " + Comentario + "\n\n ¿Son correctos?, confirme para guardar el cliente.", "Confirmación - Ingreso nuevo cliente", JOptionPane.YES_NO_OPTION);

                if (ax == JOptionPane.YES_OPTION) {
                    int documentovalido = consultardocumento(Documento);
                    if (documentovalido == 1) {
                        Insertar(Nombre, Apellido, Documento, Direccion, Celular, Telefono, Comentario);
                    } else if (documentovalido == 0) {
                        JOptionPane.showMessageDialog(null, "Ya existe un usuario con el documento digitado.", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                        TDocumento.requestFocus();
                    }
                } else if (ax == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "Ingreso cancelado, rectifique sus datos.");
                }
            } else {
                if (Nombre.length() == 0) {
                    JOptionPane.showMessageDialog(null, "El campo NOMBRES es obligatorio\nPor favor revice.", "¡ERROR! - Campo incompleto", JOptionPane.ERROR_MESSAGE);
                    TNombres.requestFocus();
                } else if (Apellido.length() == 0) {
                    JOptionPane.showMessageDialog(null, "El campo APELLIDOS es obligatorio\nPor favor revice.", "¡ERROR! - Campo incompleto", JOptionPane.ERROR_MESSAGE);
                    TApellidos.requestFocus();
                } else if (Documento.length() == 0) {
                    JOptionPane.showMessageDialog(null, "El campo DOCUMENTO es obligatorio\nPor favor revice.", "¡ERROR! - Campo incompleto", JOptionPane.ERROR_MESSAGE);
                    TDocumento.requestFocus();
                }

            }

        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        char c = ke.getKeyChar();

        if (ke.getSource() == TNombres) {
            if (Character.isDigit(c)) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "El campo solo admite letras, no números.", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            //como vamos a convertir todo a mayúsculas, entonces solo checamos si los caracteres son 
            //minusculas
            if (ke.getKeyChar() >= 'a' && ke.getKeyChar() <= 'z') {
                //si lo son, entonces lo reemplazamos por su respectivo en mayúscula en el mismo evento
                // (esto se logra por que en java todas las variables son pasadas por referencia)
                ke.setKeyChar((char) (((int) ke.getKeyChar()) - 32));
            }

            if (((int) ke.getKeyChar()) == 39) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de este carácter.\n\nIntente con uno diferente.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }

            if (TNombres.getText().length() == LimiteNombre - 1) {
                ke.consume();
            }

        }

        if (ke.getSource() == TApellidos) {
            if (Character.isDigit(c)) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "El campo solo admite letras, no números.", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            //como vamos a convertir todo a mayúsculas, entonces solo checamos si los caracteres son 
            //minusculas
            if (ke.getKeyChar() >= 'a' && ke.getKeyChar() <= 'z') {
                //si lo son, entonces lo reemplazamos por su respectivo en mayúscula en el mismo evento
                // (esto se logra por que en java todas las variables son pasadas por referencia)
                ke.setKeyChar((char) (((int) ke.getKeyChar()) - 32));
            }

            if (((int) ke.getKeyChar()) == 39) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de este carácter.\n\nIntente con uno diferente.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }

            if (TApellidos.getText().length() == LimiteApellido - 1) {
                ke.consume();
            }
        }

        if (ke.getSource() == TDireccion) {
            if (ke.getKeyChar() >= 'a' && ke.getKeyChar() <= 'z') {
                //si lo son, entonces lo reemplazamos por su respectivo en mayúscula en el mismo evento
                // (esto se logra por que en java todas las variables son pasadas por referencia)
                ke.setKeyChar((char) (((int) ke.getKeyChar()) - 32));
            }

            if (((int) ke.getKeyChar()) == 39) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de este carácter.\n\nIntente con uno diferente.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }

            if (TDireccion.getText().length() == LimiteDireccion - 1) {
                ke.consume();
            }

        }

        if (ke.getSource() == TDocumento) {
            if (Character.isLetter(c)) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "El campo solo admite números, no letras.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            } else if (((int) ke.getKeyChar()) >= 33 && ((int) ke.getKeyChar()) <= 47 || ((int) ke.getKeyChar()) >= 58) { // && ((int) ke.getKeyChar()) <= 58 || ((int) ke.getKeyChar()) >= 123 && ((int) ke.getKeyChar()) <= 163 || ((int) ke.getKeyChar()) >= 166 && ((int) ke.getKeyChar()) <= 255) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de carácteres especiales.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            } else if (((int) ke.getKeyChar()) == 32) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "Este campo no admite Espacios.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }

            if (TDocumento.getText().length() == LimiteDocumento - 1) {
                ke.consume();
            }
        }

        if (ke.getSource() == TCelular) {
            if (Character.isLetter(c)) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "El campo solo admite números, no letras.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            } else if (((int) ke.getKeyChar()) >= 33 && ((int) ke.getKeyChar()) <= 47 || ((int) ke.getKeyChar()) >= 58) { // && ((int) ke.getKeyChar()) <= 58 || ((int) ke.getKeyChar()) >= 123 && ((int) ke.getKeyChar()) <= 163 || ((int) ke.getKeyChar()) >= 166 && ((int) ke.getKeyChar()) <= 255) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de carácteres especiales.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            } else if (((int) ke.getKeyChar()) == 32) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "Este campo no admite Espacios.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }

            if (TCelular.getText().length() == LimiteCelular - 1) {
                ke.consume();
            }

        }

        if (ke.getSource() == TTelefono) {
            if (Character.isLetter(c)) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "El campo solo admite números, no letras.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            } else if (((int) ke.getKeyChar()) >= 33 && ((int) ke.getKeyChar()) <= 47 || ((int) ke.getKeyChar()) >= 58) { // && ((int) ke.getKeyChar()) <= 58 || ((int) ke.getKeyChar()) >= 123 && ((int) ke.getKeyChar()) <= 163 || ((int) ke.getKeyChar()) >= 166 && ((int) ke.getKeyChar()) <= 255) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de carácteres especiales.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            } else if (((int) ke.getKeyChar()) == 32) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "Este campo no admite Espacios.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }
            if (TTelefono.getText().length() == LimiteTelefono - 1) {
                ke.consume();
            }
        }

        if (ke.getSource() == TComentario) {
            if (((int) ke.getKeyChar()) == 39) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de este carácter.\n\nIntente con uno diferente.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }
            if (TComentario.getText().length() == LimiteComentario - 1) {
                ke.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();

        if (key == KeyEvent.VK_F1) {
            JOptionPane.showMessageDialog(null, "• Los campos Nombres, Apellidos y Documento son obligatorios para poder guardar el nuevo cliente.\nSi no posees alguno de los datos:\n- En Nombre y/o Apellido puedes dejar un espacio en blanco.\n- En Documento escribe el número cero (0), luego podrás modificarlo.\n\n• A excepción de los comentarios, todos los campos se escribirán en mayúsculas automáticamente,sino \nlo hacen al instante, a la hora de confirmar el registro rectifica que todos los datos estén en mayúsculas.\n\n• El botón Reiniciar limpiará todos los campos del formulario dejándolos completamente en blanco.\n\n• Al pulsar Insertar deberás confirmar que los datos ingresados estén correctos, luego el sistema\nte confirmará el registro del nuevo cliente o un error de ser el caso.\n\n• Luego de ingresar un nuevo cliente, debes pulsar 'Actualizar Tabla' en la ventana principal para poder ver \nreflejados los nuevos cambios.\n\nAl pulsar ACEPTAR esta ventana se cerrará.", "Ayuda Nuevo Cliente - ARTURO 1.0.5", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    public static void main(String args[]) {
        /*JDialog aplicacion = new NewUser();
         aplicacion.setSize(400, 500);
         aplicacion.setResizable(false);
         aplicacion.setLocationRelativeTo(null);
         aplicacion.setVisible(true);*/

    }

}
