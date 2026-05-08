
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JInternalFrame;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import java.util.HashMap;
import java.util.Map;

public class Searchupdatedelete extends JInternalFrame {

	  private static final long serialVersionUID = 1L;

	    private JPanel contentPane;

	    // CAMPOS
	    private JTextField txtBusqueda;

	    private JTextField txtprefijo;
	    private JTextField txttomo;
	    private JTextField txtasiento;
	    private JTextField txtcedula;
	    private JTextField txtnombre1;
	    private JTextField txtnombre2;
	    private JTextField txtapellido1;
	    private JTextField txtapellido2;
	    private JTextField txtapellidoc;
	    private JTextField txtuac;
	    private JTextField txtgenero;
	    private JComboBox<String> cbEstadoCivil;
	    private JDateChooser dateChooserFecha;  
	    private JComboBox<ItemCombo> cbProvincia;
	    private JComboBox<ItemCombo> cbDistrito;
	    private JComboBox<ItemCombo> cbCorregimiento;
	    private JTextField txtcomunidad;
	    private JTextField txtcalle;
	    private JTextField txtcasa;
	    private JTextField txttelefono;
	    private JTextField txtcorreo;

	    public static JTable table;

	    // BASE DE DATOS
	    public static final String url = "jdbc:mysql://127.0.0.1:3308/personal?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

	    public static final String usuario = "d32026";
	    public static final String contraseña = "123";

	    public static Connection con = null;
	    public static PreparedStatement s;
	    public static ResultSet rs;

	    public static DefaultTableModel model = new DefaultTableModel();

	    // MAIN
	    public static void main(String[] args) {

	        EventQueue.invokeLater(() -> {

	            try {
	            	
	            	conectarbase();
	            	
	                Searchupdatedelete frame = new Searchupdatedelete();
	               
	                frame.setVisible(true);

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	    }
	    class ItemCombo {

	        private String codigo;
	        private String nombre;

	        public ItemCombo(String codigo, String nombre) {
	            this.codigo = codigo;
	            this.nombre = nombre;
	        }

	        public String getCodigo() {
	            return codigo;
	        }

	        @Override
	        public String toString() {
	            return nombre;
	        }
	    }
	    private void seleccionarCombo(JComboBox<ItemCombo> combo, String nombre) {

	        for (int i = 0; i < combo.getItemCount(); i++) {

	            ItemCombo item = combo.getItemAt(i);

	            if (item.toString().equals(nombre)) {

	                combo.setSelectedIndex(i);
	                break;
	            }
	        }
	    }

	    // CONSTRUCTOR
	    public Searchupdatedelete() {
	        super("Edición / Búsqueda / Eliminación", true, true, true, true);
	        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
	        getContentPane().setLayout(null);
	        setPreferredSize(new Dimension(1200, 800));
	        getContentPane().setBackground(SystemColor.activeCaption);
	        
	        // Conexión automática
	        conectarbase();
	       
	        
	        
	        JLabel lblBusqueda = new JLabel("Buscar:");
	        lblBusqueda.setFont(new Font("Tahoma", Font.BOLD, 12));
	        lblBusqueda.setBounds(20, 20, 80, 20);
	        add(lblBusqueda);

	        txtBusqueda = new JTextField();
	        txtBusqueda.setBounds(90, 20, 180, 25);
	        add(txtBusqueda);

	        
	        JButton btnNuevo = new JButton("Nuevo");
	        btnNuevo.setBounds(300, 20, 120, 25);
	        add(btnNuevo);

	        JButton btnBuscar = new JButton("Buscar");
	        btnBuscar.setBounds(430, 20, 120, 25);
	        add(btnBuscar);

	        JButton btnActualizar = new JButton("Actualizar");
	        btnActualizar.setBounds(560, 20, 120, 25);
	        add(btnActualizar);

	        JButton btnEliminar = new JButton("Eliminar");
	        btnEliminar.setBounds(690, 20, 120, 25);
	        add(btnEliminar);

	        JButton btnLimpiar = new JButton("Limpiar");
	        btnLimpiar.setBounds(820, 20, 120, 25);
	        add(btnLimpiar);

	        // Fila 1
	        agregarLabel("Prefijo", 20, 60);
	        agregarLabel("Tomo", 150, 60);
	        agregarLabel("Asiento", 280, 60);
	        agregarLabel("Cédula", 410, 60);
	        agregarLabel("Nombre1", 540, 60);
	        agregarLabel("Nombre2", 670, 60);
	        agregarLabel("Apellido1", 800, 60);
	        agregarLabel("Apellido2", 930, 60);
	        agregarLabel("ApellidoC", 1060, 60);

	        txtprefijo = crearCampo(20, 80);
	        validarSoloNumeros(txtprefijo,2);
	        txttomo = crearCampo(150, 80);
	        validarSoloNumeros(txttomo,4);
	        txtasiento = crearCampo(280, 80);
	        validarSoloNumeros(txtasiento,5);
	        txtcedula = crearCampo(410, 80);
	        limitarCaracteres(txtcedula,11);
	        txtnombre1 = crearCampo(540, 80);
	        validarSoloLetras(txtnombre1,15);
	        txtnombre2 = crearCampo(670, 80);
	        validarSoloLetras(txtnombre2,15);
	        txtapellido1 = crearCampo(800, 80);
	        validarSoloLetras(txtapellido1,15);
	        txtapellido2 = crearCampo(930, 80);
	        validarSoloLetras(txtapellido2,15);
	        txtapellidoc = crearCampo(1060, 80);
	        validarSoloLetras(txtapellidoc,15);

	        // Fila 2
	        agregarLabel("UAC", 20, 120);
	        agregarLabel("Genero", 150, 120);
	        agregarLabel("Estado Civil", 280, 120);
	        agregarLabel("Fecha Nac", 410, 120);
	        agregarLabel("Provincia", 540, 120);
	        agregarLabel("Distrito", 670, 120);
	        agregarLabel("Corregimiento", 800, 120);
	        agregarLabel("Comunidad", 930, 120);
	        agregarLabel("Calle", 1060, 120);
	    

	        txtuac = crearCampo(20, 140);
	        validarSoloLetras(txtuac, 2);
	        txtgenero = crearCampo(150, 140);
	        validarSoloLetras(txtgenero, 2);
	        cbEstadoCivil = new JComboBox<>();

	        cbEstadoCivil.addItem("Soltero");
	        cbEstadoCivil.addItem("Casado");
	        cbEstadoCivil.addItem("Viudo");
	        cbEstadoCivil.addItem("Divorciado");

	        cbEstadoCivil.setBounds(280, 140, 120, 25);

	        add(cbEstadoCivil);
	        dateChooserFecha = new JDateChooser();
	        dateChooserFecha.setBounds(410, 140, 120, 25);
	        dateChooserFecha.setDateFormatString("yyyy-MM-dd");
	        add(dateChooserFecha);;
	        
	        cbProvincia = new JComboBox<>();
	        cbProvincia.setBounds(540, 140, 120, 25);
	        add(cbProvincia);

	        cbDistrito = new JComboBox<>();
	        cbDistrito.setBounds(670, 140, 120, 25);
	        add(cbDistrito);

	        cbCorregimiento = new JComboBox<>();
	        cbCorregimiento.setBounds(800, 140, 120, 25);
	        add(cbCorregimiento);
	        
	        conectarbase();

	        cargarProvincias();
	        
	        txtcomunidad = crearCampo(930, 140);
	        limitarCaracteres(txtcomunidad,30);
	        txtcalle = crearCampo(1060, 140);
	        limitarCaracteres(txtcalle,15);
	       

	        // Fila 3
	        agregarLabel("Teléfono", 20, 180);
	        agregarLabel("Correo", 280, 180);
	        agregarLabel("Casa", 540, 180);    

	        txttelefono = crearCampo(20, 200);
	        validarTelefono(txttelefono);
	        txtcorreo = crearCampo(280, 200);
	        limitarCaracteres(txtcorreo,30);
	        txtcasa = crearCampo(540, 200);
	        limitarCaracteres(txtcasa,6);

	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBounds(10, 250, 1180, 380);
	        add(scrollPane);

	        table = new JTable();
	        scrollPane.setViewportView(table);
	        cargardatos();
	    
	        btnNuevo.addActionListener(e -> insertarDatos());
	        btnBuscar.addActionListener(e -> buscarDatos());
	        btnActualizar.addActionListener(e -> actualizarDatos());
	        btnEliminar.addActionListener(e -> eliminarDatos());
	        btnLimpiar.addActionListener(e -> limpiarCampos());
	        cbProvincia.addActionListener(e -> {

	            ItemCombo prov =
	                (ItemCombo) cbProvincia.getSelectedItem();

	            if (prov != null) {

	                cargarDistritos(prov.toString());
	            }
	        });

	        cbDistrito.addActionListener(e -> {

	            ItemCombo dist =
	                (ItemCombo) cbDistrito.getSelectedItem();

	            if (dist != null) {

	                cargarCorregimientos(dist.toString());
	            }
	        });

	        // Selección de fila
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        table.getSelectionModel().addListSelectionListener(e -> {
	            int fila = table.getSelectedRow();
	            if (fila != -1) {
	                txtprefijo.setText(model.getValueAt(fila, 0) != null ? model.getValueAt(fila, 0).toString() : "");
	                txttomo.setText(model.getValueAt(fila, 1) != null ? model.getValueAt(fila, 1).toString() : "");
	                txtasiento.setText(model.getValueAt(fila, 2) != null ? model.getValueAt(fila, 2).toString() : "");
	                txtcedula.setText(model.getValueAt(fila, 3) != null ? model.getValueAt(fila, 3).toString() : "");
	                txtnombre1.setText(model.getValueAt(fila, 4) != null ? model.getValueAt(fila, 4).toString() : "");
	                txtnombre2.setText(model.getValueAt(fila, 5) != null ? model.getValueAt(fila, 5).toString() : "");
	                txtapellido1.setText(model.getValueAt(fila, 6) != null ? model.getValueAt(fila, 6).toString() : "");
	                txtapellido2.setText(model.getValueAt(fila, 7) != null ? model.getValueAt(fila, 7).toString() : "");
	                txtapellidoc.setText(model.getValueAt(fila, 8) != null ? model.getValueAt(fila, 8).toString() : "");
	                txtuac.setText(model.getValueAt(fila, 9) != null ? model.getValueAt(fila, 9).toString() : "");
	                txtgenero.setText(model.getValueAt(fila, 10) != null ? model.getValueAt(fila, 10).toString() : "");
	                cbEstadoCivil.setSelectedItem(model.getValueAt(fila, 11) != null ? model.getValueAt(fila, 11).toString() : "");
	                try {
	                    String fechaStr = model.getValueAt(fila, 12).toString();
	                    if (fechaStr != null && !fechaStr.isEmpty()) {
	                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                        Date fecha = sdf.parse(fechaStr);
	                        dateChooserFecha.setDate(fecha);
	                    }
	                } catch (Exception ex) {
	                    dateChooserFecha.setDate(null);
	                }
	                String provincia =
	                	    model.getValueAt(fila, 13).toString();

	                	seleccionarCombo(cbProvincia, provincia);

	                	cargarDistritos(provincia);

	                	String distrito =
	                	    model.getValueAt(fila, 14).toString();

	                	seleccionarCombo(cbDistrito, distrito);

	                	cargarCorregimientos(distrito);

	                	String corregimiento =
	                	    model.getValueAt(fila, 15).toString();

	                	seleccionarCombo(cbCorregimiento, corregimiento);
	                txtcomunidad.setText(model.getValueAt(fila, 16) != null ? model.getValueAt(fila, 16).toString() : "");
	                txtcalle.setText(model.getValueAt(fila, 17) != null ? model.getValueAt(fila, 17).toString() : "");
	                txtcasa.setText(model.getValueAt(fila, 18) != null ? model.getValueAt(fila, 18).toString() : "");
	                txttelefono.setText(model.getValueAt(fila, 19) != null ? model.getValueAt(fila, 19).toString() : "");
	                txtcorreo.setText(model.getValueAt(fila, 20) != null ? model.getValueAt(fila, 20).toString() : "");
	                if (con == null) {
	                    conectarbase();
	                }
	                cargardatos();
	            }
	        });
	    }
	    // LABELs
	    private void agregarLabel(String txt, int x, int y) {
	        JLabel label = new JLabel(txt);
	        label.setFont(new Font("Tahoma", Font.BOLD, 11));
	        label.setBounds(x, y, 120, 15);
	        add(label);                    
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
	    private void limitarCaracteres(JTextField field, int maxLen) {
	        field.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyTyped(KeyEvent e) {
	                if (field.getText().length() >= maxLen) {
	                    e.consume(); 
	                }
	            }
	        });
	    }
	    private void validarTelefono(JTextField field) {
	        field.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyTyped(KeyEvent e) {
	                char c = e.getKeyChar();
	                String text = field.getText();

	                // 
	                if (text.length() >= 9) {
	                    e.consume();
	                    return;
	                }

	                // 
	                if (!Character.isDigit(c)) {
	                    e.consume();
	                    return;
	                }

	                // 
	                if (text.length() == 4) {
	                    field.setText(text + "-");
	                }
	            }
	        });
	    }

	    private JTextField crearCampo(int x, int y) {
	        JTextField txt = new JTextField();
	        txt.setBounds(x, y, 120, 25);
	        add(txt);                      
	        return txt;
	    }
	    

	    // CONECTAR
	    public static void conectarbase() {
	        try {
	            if (con == null || con.isClosed()) {
	                con = DriverManager.getConnection(url, usuario, contraseña);
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error al conectar:\n" + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    // CARGAR TODOS LOS DATOS
	    public void cargardatos() {
	        try {
	            if (con == null || con.isClosed()) {
	                con = DriverManager.getConnection(url, usuario, contraseña);
	            }

	            model.setRowCount(0);
	            model.setColumnCount(0);
	            
	            model.addColumn("Prefijo");
	            model.addColumn("Tomo");
	            model.addColumn("Asiento");
	            model.addColumn("Cedula");
	            model.addColumn("Nombre1");
	            model.addColumn("Nombre2");
	            model.addColumn("Apellido1");
	            model.addColumn("Apellido2");
	            model.addColumn("ApellidoC");
	            model.addColumn("UAC");
	            model.addColumn("Genero");
	            model.addColumn("EstadoCivil");
	            model.addColumn("FechaNac");
	            model.addColumn("Provincia");
	            model.addColumn("Distrito");
	            model.addColumn("Corregimiento");
	            model.addColumn("Comunidad");
	            model.addColumn("Calle");
	            model.addColumn("Casa");
	            model.addColumn("Telefono");
	            model.addColumn("Correo");

	            table.setModel(model);

	            String sql =
	            	    "SELECT d.prefijo, d.tomo, d.asiento, d.cedula, " +
	            	    "d.nombre1, d.nombre2, d.apellido1, d.apellido2, " +
	            	    "d.apellidoc, d.uac, d.genero, d.estado_civil, " +
	            	    "d.fecha_nac, " +
	            	    "p.nombre_provincia, " +
	            	    "di.nombre_distrito, " +
	            	    "c.nombre_corregimiento, " +
	            	    "d.comunidad, d.calle, d.casa, d.telefono, d.correo " +
	            	    "FROM datos d " +
	            	    "LEFT JOIN provincia p ON d.provincia = p.codigo_provincia " +
	            	    "LEFT JOIN distrito di ON d.distrito = di.codigo_distrito " +
	            	    "LEFT JOIN corregimiento c ON d.corregimiento = c.codigo_corregimiento";

	            	s = con.prepareStatement(sql);
	            rs = s.executeQuery();
	            while (rs.next()) {

	                String datos[] = new String[21];

	                for (int i = 0; i < 21; i++) {
	                    datos[i] = rs.getString(i + 1);
	                }

	                // CONVERTIR INICIAL A TEXTO
	                switch (datos[11]) {

	                    case "S":
	                        datos[11] = "Soltero";
	                        break;

	                    case "C":
	                        datos[11] = "Casado";
	                        break;

	                    case "V":
	                        datos[11] = "Viudo";
	                        break;

	                    case "D":
	                        datos[11] = "Divorciado";
	                        break;
	                }

	                model.addRow(datos);
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    // BUSCAR
	    public void buscarDatos() {
	        if (!verificarConexion()) return;
	        
	        try {
	            model.setRowCount(0);
	            String busqueda = txtBusqueda.getText().trim();
	            
	            if (busqueda.isEmpty()) {
	                cargardatos();
	                return;
	            }
	            
	            String sql = "SELECT * FROM datos WHERE " +
	                         "cedula LIKE ? OR nombre1 LIKE ? OR nombre2 LIKE ? OR " +
	                         "apellido1 LIKE ? OR apellido2 LIKE ?";
	            
	            s = con.prepareStatement(sql);
	            String parametro = "%" + busqueda + "%";
	            
	            for (int i = 1; i <= 5; i++) {
	                s.setString(i, parametro);
	            }
	            
	            rs = s.executeQuery();
	            
	            boolean encontrado = false;
	            while (rs.next()) {
	                String[] datos = new String[21];
	                for (int i = 0; i < 21; i++) {
	                    datos[i] = rs.getString(i + 1);
	                }
	                model.addRow(datos);
	                encontrado = true;
	            }
	            
	            if (!encontrado) {
	                JOptionPane.showMessageDialog(this, "No se encontraron resultados");
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    // INSERTAR
	    public void insertarDatos() {
	        if (!verificarConexion()) return;
	        
	        try {
	            String sql = "INSERT INTO datos VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	            s = con.prepareStatement(sql);
	            
	            s.setString(1, txtprefijo.getText().trim());
	            s.setString(2, txttomo.getText().trim());
	            s.setString(3, txtasiento.getText().trim());
	            s.setString(4, txtcedula.getText().trim());
	            s.setString(5, txtnombre1.getText().trim());
	            s.setString(6, txtnombre2.getText().trim());
	            s.setString(7, txtapellido1.getText().trim());
	            s.setString(8, txtapellido2.getText().trim());
	            s.setString(9, txtapellidoc.getText().trim());
	            s.setString(10, txtuac.getText().trim());
	            s.setString(11, txtgenero.getText().trim());
	            String estado = (String) cbEstadoCivil.getSelectedItem();

	            String inicial = "";

	            switch (estado) {
	                case "Soltero":
	                    inicial = "S";
	                    break;

	                case "Casado":
	                    inicial = "C";
	                    break;

	                case "Viudo":
	                    inicial = "V";
	                    break;

	                case "Divorciado":
	                    inicial = "D";
	                    break;
	            }

	            s.setString(12, inicial);
	            if (dateChooserFecha.getDate() != null) {
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                s.setString(13, sdf.format(dateChooserFecha.getDate()));
	            } else {
	                s.setString(13, null);
	            }
	            ItemCombo provincia =
	            	    (ItemCombo) cbProvincia.getSelectedItem();

	            	ItemCombo distrito =
	            	    (ItemCombo) cbDistrito.getSelectedItem();

	            	ItemCombo corregimiento =
	            	    (ItemCombo) cbCorregimiento.getSelectedItem();

	            	s.setString(14, provincia.getCodigo());
	            	s.setString(15, distrito.getCodigo());
	            	s.setString(16, corregimiento.getCodigo());
	            s.setString(17, txtcomunidad.getText().trim());
	            s.setString(18, txtcalle.getText().trim());
	            s.setString(19, txtcasa.getText().trim());
	            s.setString(20, txttelefono.getText().trim());
	            s.setString(21, txtcorreo.getText().trim());

	            s.executeUpdate();
	            JOptionPane.showMessageDialog(this, "Registro insertado correctamente");
	            cargardatos();
	            limpiarCampos();
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, "Error al insertar: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    // ACTUALIZAR
	    public void actualizarDatos() {
	    	if (!verificarConexion()) return;
	        try {

	            String sql =
	                    "UPDATE datos SET " +
	                    "prefijo=?, tomo=?, asiento=?, nombre1=?, nombre2=?, apellido1=?, apellido2=?, apellidoc=?, uac=?, genero=?, estado_civil=?, fecha_nac=?, provincia=?, distrito=?, corregimiento=?, comunidad=?, calle=?, casa=?, telefono=?, correo=? " +
	                    "WHERE cedula=?";

	            s = con.prepareStatement(sql);

	            s.setString(1, txtprefijo.getText());
	            s.setString(2, txttomo.getText());
	            s.setString(3, txtasiento.getText());
	            s.setString(4, txtnombre1.getText());
	            s.setString(5, txtnombre2.getText());
	            s.setString(6, txtapellido1.getText());
	            s.setString(7, txtapellido2.getText());
	            s.setString(8, txtapellidoc.getText());
	            s.setString(9, txtuac.getText());
	            s.setString(10, txtgenero.getText());

	            String estado = (String) cbEstadoCivil.getSelectedItem();

	            String inicial = "";

	            switch (estado) {

	                case "Soltero":
	                    inicial = "S";
	                    break;

	                case "Casado":
	                    inicial = "C";
	                    break;

	                case "Viudo":
	                    inicial = "V";
	                    break;

	                case "Divorciado":
	                    inicial = "D";
	                    break;
	            }

	            s.setString(11, inicial);

	            if (dateChooserFecha.getDate() != null) {

	                SimpleDateFormat sdf =
	                    new SimpleDateFormat("yyyy-MM-dd");

	                s.setString(12,
	                    sdf.format(dateChooserFecha.getDate()));

	            } else {

	                s.setString(12, null);
	            }

	            ItemCombo provincia =
	                (ItemCombo) cbProvincia.getSelectedItem();

	            ItemCombo distrito =
	                (ItemCombo) cbDistrito.getSelectedItem();

	            ItemCombo corregimiento =
	                (ItemCombo) cbCorregimiento.getSelectedItem();

	            s.setString(13, provincia.getCodigo());
	            s.setString(14, distrito.getCodigo());
	            s.setString(15, corregimiento.getCodigo());

	            s.setString(16, txtcomunidad.getText());
	            s.setString(17, txtcalle.getText());
	            s.setString(18, txtcasa.getText());
	            s.setString(19, txttelefono.getText());
	            s.setString(20, txtcorreo.getText());

	            s.setString(21, txtcedula.getText());

	            s.executeUpdate();

	            JOptionPane.showMessageDialog(null, "Registro actualizado");

	            cargardatos();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 // Cargar Provincias
	    private void cargarProvincias() {

	        try {

	            cbProvincia.removeAllItems();

	            cbProvincia.addItem(
	                new ItemCombo("", "")
	            );

	            var st = con.createStatement();

	            var rs = st.executeQuery(
	                "SELECT codigo_provincia, nombre_provincia " +
	                "FROM provincia ORDER BY nombre_provincia"
	            );

	            while (rs.next()) {

	                cbProvincia.addItem(

	                    new ItemCombo(

	                        rs.getString("codigo_provincia"),
	                        rs.getString("nombre_provincia")
	                    )
	                );
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	   
	
	   
	    // Cargar Distritos según Provincia
	    private void cargarDistritos(String provincia) {

	        try {

	            cbDistrito.removeAllItems();

	            String sql =
	                "SELECT d.codigo_distrito, d.nombre_distrito " +
	                "FROM distrito d " +
	                "JOIN provincia p " +
	                "ON d.codigo_provincia = p.codigo_provincia " +
	                "WHERE p.nombre_provincia = ? " +
	                "ORDER BY d.nombre_distrito";

	            var ps = con.prepareStatement(sql);

	            ps.setString(1, provincia);

	            var rs = ps.executeQuery();

	            while (rs.next()) {

	                cbDistrito.addItem(

	                    new ItemCombo(

	                        rs.getString("codigo_distrito"),
	                        rs.getString("nombre_distrito")
	                    )
	                );
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Cargar Corregimientos según Distrito
	    private void cargarCorregimientos(String distrito) {

	        try {

	            cbCorregimiento.removeAllItems();

	            String sql =
	                "SELECT c.codigo_corregimiento, c.nombre_corregimiento " +
	                "FROM corregimiento c " +
	                "JOIN distrito d " +
	                "ON c.codigo_distrito = d.codigo_distrito " +
	                "WHERE d.nombre_distrito = ? " +
	                "ORDER BY c.nombre_corregimiento";

	            var ps = con.prepareStatement(sql);

	            ps.setString(1, distrito);

	            var rs = ps.executeQuery();

	            while (rs.next()) {

	                cbCorregimiento.addItem(

	                    new ItemCombo(

	                        rs.getString("codigo_corregimiento"),
	                        rs.getString("nombre_corregimiento")
	                    )
	                );
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    // ELIMINAR
	    public void eliminarDatos() {
	    	if (!verificarConexion()) return;
	        try {

	            s = con.prepareStatement("DELETE FROM datos WHERE cedula=?");

	            s.setString(1, txtcedula.getText());

	            s.executeUpdate();

	            JOptionPane.showMessageDialog(null, "Registro eliminado");

	            cargardatos();

	            limpiarCampos();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // LIMPIAR
	    public void limpiarCampos() {

	        txtprefijo.setText("");
	        txttomo.setText("");
	        txtasiento.setText("");
	        txtcedula.setText("");
	        txtnombre1.setText("");
	        txtnombre2.setText("");
	        txtapellido1.setText("");
	        txtapellido2.setText("");
	        txtapellidoc.setText("");
	        txtuac.setText("");
	        txtgenero.setText("");
	        cbEstadoCivil.removeAllItems();
	        dateChooserFecha.setDate(null);
	        cbProvincia.setSelectedIndex(-1);
	        cbDistrito.removeAllItems();
	        cbCorregimiento.removeAllItems();
	        txtcomunidad.setText("");
	        txtcalle.setText("");
	        txtcasa.setText("");
	        txttelefono.setText("");
	        txtcorreo.setText("");
	        txtBusqueda.setText("");
	    }
	       // Verificar y reconectar si es necesario
	    private boolean verificarConexion() {
	        try {
	            if (con == null || con.isClosed()) {
	                con = DriverManager.getConnection(url, usuario, contraseña);
	            }
	            return true;
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, 
	                "Error de conexión a la base de datos:\n" + e.getMessage(), 
	                "Error de Conexión", JOptionPane.ERROR_MESSAGE);
	            e.printStackTrace();
	            return false;
	        }
	    }
	}
