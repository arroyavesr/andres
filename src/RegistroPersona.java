package Proyecto1;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.toedter.calendar.JDateChoose;
public class RegistroPersona extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTextField txtCedula, txtTomo, txtAsiento, txtNombre1, txtNombre2, txtApellido1, txtApellido2,
            txtApellidoc, txtFechaNac, txtComunidad, txtCalle, txtCasa, txtTelefono, txtCorreo;
    private JDateChooser dateChooser;
    private JComboBox<String> cbPrefijo, cbGenero, cbEstadoCivil, cbProvincia, cbDistrito, cbCorregimiento, cbUac;

    private final String url = "jdbc:mysql://127.0.0.1:3307/personal?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private final String usuario = "d32026";
    private final String contraseña = "123";

    private java.sql.Connection con;

    private final Map<String, String[]> distritosPorProvincia = new LinkedHashMap<>();
    private final Map<String, String[]> corregimientosPorDistrito = new HashMap<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RegistroPersona frame = new RegistroPersona();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RegistroPersona() {
        setTitle("Registro de Personas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 650);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(72, 181, 204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        inicializarDatosUbicacion();
        crearComponentes();
        cargarEventos();
    }

    private void crearComponentes() {
        int x1 = 20, x2 = 180, y = 20, h = 25, gap = 32;

        agregarEtiqueta("Prefijo", x1, y);
        cbPrefijo = new JComboBox<>(new String[] {
            "1","2","3","4","5","6","7","8","9","10","11","12","13","E(Extranjero)","N(Nacionalizado)","X(casos especiales)","P(por nacimiento)","O(registros administrativos)","R(Residente)","T(Temporal)"
        });
        cbPrefijo.setBounds(180, 21, 200, h);
        contentPane.add(cbPrefijo);
        y += gap;

        agregarEtiqueta("Tomo", x1, y);
        txtTomo = new JTextField();
        txtTomo.setBounds(180, 52, 200, h);
        validarSoloNumeros(txtTomo, 4);
        contentPane.add(txtTomo);
        y += gap;

        agregarEtiqueta("Asiento", x1, y);
        txtAsiento = new JTextField();
        txtAsiento.setBounds(180, 84, 200, h);
        validarSoloNumeros(txtAsiento, 5);
        contentPane.add(txtAsiento);
        y += gap;

        agregarEtiqueta("Cédula", x1, y);
        txtCedula = new JTextField();
        txtCedula.setBounds(180, 116, 200, h);
        txtCedula.setEditable(false);
        contentPane.add(txtCedula);
        y += gap;

        agregarEtiqueta("Nombre 1", x1, y);
        txtNombre1 = new JTextField();
        txtNombre1.setBounds(180, 148, 200, h);
        validarSoloLetras(txtNombre1, 15);
        contentPane.add(txtNombre1);
        y += gap;

        agregarEtiqueta("Nombre 2", x1, y);
        txtNombre2 = new JTextField();
        txtNombre2.setBounds(180, 180, 200, h);
        validarSoloLetras(txtNombre2, 15);
        contentPane.add(txtNombre2);
        y += gap;

        agregarEtiqueta("Apellido 1", x1, y);
        txtApellido1 = new JTextField();
        txtApellido1.setBounds(180, 212, 200, h);
        validarSoloLetras(txtApellido1, 15);
        contentPane.add(txtApellido1);
        y += gap;

        agregarEtiqueta("Apellido 2", x1, y);
        txtApellido2 = new JTextField();
        txtApellido2.setBounds(180, 244, 200, h);
        validarSoloLetras(txtApellido2, 15);
        contentPane.add(txtApellido2);
        y += gap;

        agregarEtiqueta("Apellido c", x1, y);
        txtApellidoc = new JTextField();
        txtApellidoc.setBounds(180, 308, 200, h);
        validarSoloLetras(txtApellidoc, 15);
        contentPane.add(txtApellidoc);
        y += gap;

        // UAC
        agregarEtiqueta("UAC(usa apellido de casad@)", x1, y);
        cbUac = new JComboBox<>(new String[] { "Seleccione...", "Sí", "No" });
        cbUac.setBounds(180, 276, 62, 25);
        contentPane.add(cbUac);
        y += gap;

        // DERECHA
        agregarEtiqueta("Género", 450, 20);
        cbGenero = new JComboBox<>(new String[] { "M", "F" });
        cbGenero.setBounds(600, 20, 200, h);
        contentPane.add(cbGenero);

        agregarEtiqueta("Estado civil", 450, 52);
        cbEstadoCivil = new JComboBox<>(new String[] { "Soltero", "Casado", "Viudo", "Divorciado" });
        cbEstadoCivil.setBounds(600, 52, 200, h);
        contentPane.add(cbEstadoCivil);

        agregarEtiqueta("Fecha nac.", 450, 84);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(600, 84, 200, h);
        dateChooser.setDateFormatString("yyyy-MM-dd"); // formato visual
        contentPane.add(dateChooser);


        agregarEtiqueta("Provincia", 450, 116);
        cbProvincia = new JComboBox<>();
        cbProvincia.setBounds(600, 116, 200, h);
        contentPane.add(cbProvincia);

        agregarEtiqueta("Distrito", 450, 148);
        cbDistrito = new JComboBox<>();
        cbDistrito.setBounds(600, 148, 200, h);
        contentPane.add(cbDistrito);

        agregarEtiqueta("Corregimiento", 450, 180);
        cbCorregimiento = new JComboBox<>();
        cbCorregimiento.setBounds(600, 180, 200, h);
        contentPane.add(cbCorregimiento);

        agregarEtiqueta("Comunidad", 450, 212);
        txtComunidad = new JTextField();
        txtComunidad.setBounds(600, 212, 200, h);
        contentPane.add(txtComunidad);

        agregarEtiqueta("Calle", 450, 244);
        txtCalle = new JTextField();
        txtCalle.setBounds(600, 244, 200, h);
        contentPane.add(txtCalle);

        agregarEtiqueta("Casa", 450, 276);
        txtCasa = new JTextField();
        txtCasa.setBounds(600, 276, 200, h);
        contentPane.add(txtCasa);

        agregarEtiqueta("Teléfono", 450, 308);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(600, 308, 200, h);
        validarTelefono(txtTelefono);
        contentPane.add(txtTelefono);

        agregarEtiqueta("Correo", 450, 340);
        txtCorreo = new JTextField();
        txtCorreo.setBounds(600, 340, 200, h);
        contentPane.add(txtCorreo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(467, 430, 120, 30);
        contentPane.add(btnGuardar);

        JButton btnConectar = new JButton("Conectar");
        btnConectar.setBounds(600, 430, 120, 30);
        contentPane.add(btnConectar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(730, 430, 120, 30);
        contentPane.add(btnLimpiar);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(859, 430, 80, 30);
        contentPane.add(btnSalir);

        btnConectar.addActionListener(e -> conectar());
        btnGuardar.addActionListener(e -> guardar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    // MÉTODOS 
    private void agregarEtiqueta(String txt, int x, int y) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Tahoma", Font.PLAIN, 12));
        l.setBounds(x, y, 180, 25);
        contentPane.add(l);
    }

    private void validarSoloNumeros(JTextField field, int maxLen) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || field.getText().length() >= maxLen) {
                    e.consume();
                }
            }
        });
    }

    private void validarSoloLetras(JTextField field, int maxLen) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != ' ') e.consume();
                if (field.getText().length() >= maxLen) e.consume();
            }
        });
    }

    

    private void inicializarDatosUbicacion() {}
    private void llenarProvinciasDesdeBD() {
        cbProvincia.removeAllItems();

        try {
            String sql = "SELECT nombre_provincia FROM provincia";
            var st = con.createStatement();
            var rs = st.executeQuery(sql);

            while (rs.next()) {
                cbProvincia.addItem(rs.getString("nombre_provincia"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void llenarDistritosDesdeBD(String provincia) {
        cbDistrito.removeAllItems();

        try {
            String sql = "SELECT d.nombre_distrito " +
                         "FROM distrito d " +
                         "JOIN provincia p ON d.codigo_provincia = p.codigo_provincia " +
                         "WHERE p.nombre_provincia = ?";

            var ps = con.prepareStatement(sql);
            ps.setString(1, provincia);

            var rs = ps.executeQuery();

            while (rs.next()) {
                cbDistrito.addItem(rs.getString("nombre_distrito"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void conectar() {
        try {
            con = DriverManager.getConnection(url, usuario, contraseña);
            JOptionPane.showMessageDialog(this, "Conectado correctamente");

            llenarProvinciasDesdeBD(); // ← IMPORTANTE

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error de conexión");
            e.printStackTrace();
        }
    }
    private void llenarCorregimientosDesdeBD(String distrito) {
        cbCorregimiento.removeAllItems();

        try {
            String sql = "SELECT c.nombre_corregimiento " +
                         "FROM corregimiento c " +
                         "JOIN distrito d ON c.codigo_distrito = d.codigo_distrito " +
                         "WHERE d.nombre_distrito = ?";

            var ps = con.prepareStatement(sql);
            ps.setString(1, distrito);

            var rs = ps.executeQuery();

            while (rs.next()) {
                cbCorregimiento.addItem(rs.getString("nombre_corregimiento"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardar() {

        if (con == null) {
            JOptionPane.showMessageDialog(this, "Primero debes conectar a la base de datos");
            return;
        }

        try {
        	String sql = "INSERT INTO datos (" +
        		    "prefijo, tomo, asiento, cedula, nombre1, nombre2, apellido1, apellido2, apellidoc, uac, " +
        		    "genero, estado_civil, fecha_nac, provincia, distrito, corregimiento, comunidad, calle, casa, telefono, correo" +
        		    ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";;

            var ps = con.prepareStatement(sql);

            ps.setString(1, (String) cbPrefijo.getSelectedItem());
            ps.setString(2, txtTomo.getText());
            ps.setString(3, txtAsiento.getText());
            ps.setString(4, txtCedula.getText());
            ps.setString(5, txtNombre1.getText());
            ps.setString(6, txtNombre2.getText());
            ps.setString(7, txtApellido1.getText());
            ps.setString(8, txtApellido2.getText());
            ps.setString(9, txtApellidoc.getText());
            ps.setString(10, (String) cbUac.getSelectedItem());

            ps.setString(11, (String) cbGenero.getSelectedItem());
            ps.setString(12, (String) cbEstadoCivil.getSelectedItem());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(13, sdf.format(dateChooser.getDate()));

            ps.setString(14, (String) cbProvincia.getSelectedItem());
            ps.setString(15, (String) cbDistrito.getSelectedItem());
            ps.setString(16, (String) cbCorregimiento.getSelectedItem());

            ps.setString(17, txtComunidad.getText());
            ps.setString(18, txtCalle.getText());
            ps.setString(19, txtCasa.getText());
            ps.setString(20, txtTelefono.getText());
            ps.setString(21, txtCorreo.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Datos guardados correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar");
            e.printStackTrace();
        }
    }
    private void limpiar() {

        txtCedula.setText("");
        txtTomo.setText("");
        txtAsiento.setText("");

        txtNombre1.setText("");
        txtNombre2.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        txtApellidoc.setText("");

        txtFechaNac.setText("");
        txtComunidad.setText("");
        txtCalle.setText("");
        txtCasa.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");

        cbPrefijo.setSelectedIndex(0);
        cbGenero.setSelectedIndex(0);
        cbEstadoCivil.setSelectedIndex(0);
        cbUac.setSelectedIndex(0);

        cbProvincia.setSelectedIndex(-1);
        cbDistrito.removeAllItems();
        cbCorregimiento.removeAllItems();
    }
    private void actualizarCedula() {

        String prefijo = (String) cbPrefijo.getSelectedItem();
        String tomo = txtTomo.getText();
        String asiento = txtAsiento.getText();

        if (prefijo == null) prefijo = "";

        // Extraer solo la letra si es tipo "E(Extranjero)"
        if (prefijo.contains("(")) {
            prefijo = prefijo.substring(0, 1);
        }

        String cedula = "";

        if (!prefijo.isEmpty()) {
            cedula += prefijo;
        }

        if (!tomo.isEmpty()) {
            cedula += "-" + tomo;
        }

        if (!asiento.isEmpty()) {
            cedula += "-" + asiento;
        }

        txtCedula.setText(cedula);
    }
    private void validarTelefono(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = field.getText();

                // Bloquear si ya tiene 9 caracteres (1234-5678)
                if (text.length() >= 9) {
                    e.consume();
                    return;
                }

                // Solo permitir números
                if (!Character.isDigit(c)) {
                    e.consume();
                    return;
                }

                // Insertar automáticamente el guion después de 4 dígitos
                if (text.length() == 4) {
                    field.setText(text + "-");
                }
            }
        });
    }
    private void cargarEventos() {

        // Provincias → Distritos
        cbProvincia.addActionListener(e -> {
            String provincia = (String) cbProvincia.getSelectedItem();
            if (provincia != null) {
                llenarDistritosDesdeBD(provincia);
            }
        });

        // Distritos → Corregimientos
        cbDistrito.addActionListener(e -> {
            String distrito = (String) cbDistrito.getSelectedItem();
            if (distrito != null) {
                llenarCorregimientosDesdeBD(distrito);
            }
        });

        // ACTUALIZAR CÉDULA AUTOMÁTICAMENTE
        cbPrefijo.addActionListener(e -> actualizarCedula());

        txtTomo.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { actualizarCedula(); }
            public void removeUpdate(DocumentEvent e) { actualizarCedula(); }
            public void changedUpdate(DocumentEvent e) { actualizarCedula(); }
        });

        txtAsiento.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { actualizarCedula(); }
            public void removeUpdate(DocumentEvent e) { actualizarCedula(); }
            public void changedUpdate(DocumentEvent e) { actualizarCedula(); }
        });
    }
}