import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Contacts {

	private JFrame frame;
	private JTextField contactFname;
	private JTextField contactLname;
	private JTextField contactTel;
	private JTextField contactEmail;
	private JTextField contactAddr1;
	private JLabel lblAddress_1;
	private JTextField contactAddr2;
	private JLabel lblCity;
	private JTextField contactCity;
	private JLabel lblPostcode;
	private JTextField contactPostcode;
	private JLabel lblContactType;
	private JTextField contactHBTel;

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JButton btnNewButton_1;
	private JTable contactsTable;
	private JScrollPane scrollPane;
	String selectedContactType;

	void openConnection(String sql) {
		try {

			String url = "jdbc:mysql://mycontactsdb.cdearxhtuknc.us-east-1.rds.amazonaws.com:3306/MyContactsDB";
			con = DriverManager.getConnection(url, "admin", "Kosiso889");
			pst = con.prepareStatement(sql);

		} catch (SQLException | HeadlessException ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	public void showTableData(String contactType) {
		selectedContactType = contactType;
		try {

			String sql = contactType == "Business" ? "SELECT * FROM MyContactsDB.business_contacts"
					: "SELECT * FROM MyContactsDB.personal_contacts";
			openConnection(sql);
			rs = pst.executeQuery();
			contactsTable.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException | HeadlessException ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Contacts window = new Contacts();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Contacts() {
		initialize();
		showTableData("Business");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1041, 585);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setBounds(27, 30, 78, 16);
		frame.getContentPane().add(lblNewLabel);

		contactFname = new JTextField();
		contactFname.setBounds(100, 25, 130, 26);
		frame.getContentPane().add(contactFname);
		contactFname.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(27, 64, 78, 16);
		frame.getContentPane().add(lblLastName);

		contactLname = new JTextField();
		contactLname.setColumns(10);
		contactLname.setBounds(100, 59, 130, 26);
		frame.getContentPane().add(contactLname);

		JLabel lblTel = new JLabel("Tel.");
		lblTel.setBounds(27, 98, 78, 16);
		frame.getContentPane().add(lblTel);

		contactTel = new JTextField();
		contactTel.setColumns(10);
		contactTel.setBounds(100, 93, 130, 26);
		frame.getContentPane().add(contactTel);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(27, 133, 78, 16);
		frame.getContentPane().add(lblEmail);

		contactEmail = new JTextField();
		contactEmail.setColumns(10);
		contactEmail.setBounds(100, 128, 130, 26);
		frame.getContentPane().add(contactEmail);

		JLabel lblAddress = new JLabel("Address 1");
		lblAddress.setBounds(27, 166, 78, 16);
		frame.getContentPane().add(lblAddress);

		contactAddr1 = new JTextField();
		contactAddr1.setColumns(10);
		contactAddr1.setBounds(100, 161, 130, 26);
		frame.getContentPane().add(contactAddr1);

		lblAddress_1 = new JLabel("Address 2");
		lblAddress_1.setBounds(27, 199, 78, 16);
		frame.getContentPane().add(lblAddress_1);

		contactAddr2 = new JTextField();
		contactAddr2.setColumns(10);
		contactAddr2.setBounds(100, 194, 130, 26);
		frame.getContentPane().add(contactAddr2);

		lblCity = new JLabel("City");
		lblCity.setBounds(27, 232, 78, 16);
		frame.getContentPane().add(lblCity);

		contactCity = new JTextField();
		contactCity.setColumns(10);
		contactCity.setBounds(100, 227, 130, 26);
		frame.getContentPane().add(contactCity);

		lblPostcode = new JLabel("Postcode");
		lblPostcode.setBounds(27, 265, 78, 16);
		frame.getContentPane().add(lblPostcode);

		contactPostcode = new JTextField();
		contactPostcode.setColumns(10);
		contactPostcode.setBounds(100, 260, 130, 26);
		frame.getContentPane().add(contactPostcode);

		lblContactType = new JLabel("Contact Type");
		lblContactType.setBounds(27, 298, 96, 16);
		frame.getContentPane().add(lblContactType);

		JComboBox<String> contactType = new JComboBox<String>();
		contactType.setModel(new DefaultComboBoxModel<String>(new String[] { "Business", "Personal" }));
		contactType.setBounds(121, 294, 109, 27);
		frame.getContentPane().add(contactType);

		JLabel lblPersonalbusinessCont = new JLabel("Home/Bus. Tel.");
		lblPersonalbusinessCont.setBounds(27, 331, 109, 16);
		frame.getContentPane().add(lblPersonalbusinessCont);

		contactHBTel = new JTextField();
		contactHBTel.setColumns(10);
		contactHBTel.setBounds(131, 326, 99, 26);
		frame.getContentPane().add(contactHBTel);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String selectedType = contactType.getSelectedItem().toString();

					String insertBusinessContactQuery = "INSERT INTO `MyContactsDB`.`business_contacts` (`contactFname`, `contactLname`, `contactTel`, `contactEmail`, `contactAddr1`, `contactAddr2`, `contactCity`, `contactPostcode`, `contactBusinessTel`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
					String insertPersonalContactQuery = "INSERT INTO `MyContactsDB`.`personal_contacts` (`contactFname`, `contactLname`, `contactTel`, `contactEmail`, `contactAddr1`, `contactAddr2`, `contactCity`, `contactPostcode`, `contactHomeTel`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
					String sql = selectedType == "Personal" ? insertPersonalContactQuery : insertBusinessContactQuery;

					openConnection(sql);

					pst.setString(1, contactFname.getText());
					pst.setString(2, contactLname.getText());
					pst.setString(3, contactTel.getText());
					pst.setString(4, contactEmail.getText());
					pst.setString(5, contactAddr1.getText());
					pst.setString(6, contactAddr2.getText());
					pst.setString(7, contactCity.getText());
					pst.setString(8, contactPostcode.getText());
					pst.setString(9, contactHBTel.getText());
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Added Successfully!");
					showTableData(selectedType);
				} catch (SQLException | HeadlessException ex) {
					JOptionPane.showMessageDialog(null, ex);
				}

			}
		});
		btnNewButton.setBounds(19, 364, 218, 29);
		frame.getContentPane().add(btnNewButton);

		btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idToBeDeleted = contactsTable.getValueAt(contactsTable.getSelectedRow(), 0).toString();
					String sql = selectedContactType == "Personal"
							? "DELETE FROM `MyContactsDB`.`personal_contacts` WHERE `contactID` = ?"
							: "DELETE FROM `MyContactsDB`.`business_contacts` WHERE `contactID` = ?";

					openConnection(sql);
					pst.setString(1, idToBeDeleted);
					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Deleted Successfully!");
					showTableData(selectedContactType);

				} catch (SQLException | HeadlessException | ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null,
							ex instanceof ArrayIndexOutOfBoundsException ? "Select a contact to be deleted!" : ex);
				}
			}
		});
		btnNewButton_1.setBounds(259, 364, 117, 29);
		frame.getContentPane().add(btnNewButton_1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(269, 68, 748, 284);
		frame.getContentPane().add(scrollPane);

		contactsTable = new JTable();

		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				try {

					TableCellListener tcl = (TableCellListener) e.getSource();
//				        System.out.println("Row   : " + tcl.getRow());
//				        System.out.println("Column: " + tcl.getColumn());
//				        System.out.println("Old   : " + tcl.getOldValue());
//				        System.out.println("New   : " + tcl.getNewValue());

					int editedColumn = tcl.getColumn();

					if (editedColumn != 0) {
						String contactID = contactsTable.getValueAt(contactsTable.getSelectedRow(), 0).toString();
						String contactFname = contactsTable.getValueAt(contactsTable.getSelectedRow(), 1).toString();
						String contactLname = contactsTable.getValueAt(contactsTable.getSelectedRow(), 2).toString();
						String contactTel = contactsTable.getValueAt(contactsTable.getSelectedRow(), 3).toString();
						String contactEmail = contactsTable.getValueAt(contactsTable.getSelectedRow(), 4).toString();
						String contactAddr1 = contactsTable.getValueAt(contactsTable.getSelectedRow(), 5).toString();
						String contactAddr2 = contactsTable.getValueAt(contactsTable.getSelectedRow(), 6).toString();
						String contactCity = contactsTable.getValueAt(contactsTable.getSelectedRow(), 7).toString();
						String contactPostcode = contactsTable.getValueAt(contactsTable.getSelectedRow(), 8).toString();
						String contactHBTel = contactsTable.getValueAt(contactsTable.getSelectedRow(), 9).toString();

						String updatePersonalContactQuery = "UPDATE `MyContactsDB`.`personal_contacts` SET `contactFname` = ?, `contactLname` = ?, `contactTel` = ?, `contactEmail` = ?, `contactAddr1` = ?, `contactAddr2` =?, `contactCity` =?, `contactPostcode` = ?, `contactHomeTel` = ? WHERE `contactID` = ?;";
						String updateBusinessContactQuery = "UPDATE `MyContactsDB`.`business_contacts` SET `contactFname` = ?, `contactLname` = ?, `contactTel` = ?, `contactEmail` = ?, `contactAddr1` = ?, `contactAddr2` =?, `contactCity` =?, `contactPostcode` = ?, `contactBusinessTel` = ? WHERE `contactID` = ?;";

						String sql = selectedContactType == "Personal" ? updatePersonalContactQuery
								: updateBusinessContactQuery;
						openConnection(sql);
						pst.setString(1, contactFname);
						pst.setString(2, contactLname);
						pst.setString(3, contactTel);
						pst.setString(4, contactEmail);
						pst.setString(5, contactAddr1);
						pst.setString(6, contactAddr2);
						pst.setString(7, contactCity);
						pst.setString(8, contactPostcode);
						pst.setString(9, contactHBTel);
						pst.setString(10, contactID);
						pst.executeUpdate();

						JOptionPane.showMessageDialog(null, "Updated Successfully!");
					}

				} catch (SQLException | HeadlessException ex) {
					JOptionPane.showMessageDialog(null, ex);
				}

			}
		};

		TableCellListener tcl = new TableCellListener(contactsTable, action);

		scrollPane.setViewportView(contactsTable);

		JButton btnNewButton_2 = new JButton("Business Contacts");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTableData("Business");
			}
		});
		btnNewButton_2.setBounds(259, 25, 143, 29);
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Personal Contacts");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTableData("Personal");
			}
		});
		btnNewButton_3.setBounds(410, 25, 143, 29);
		frame.getContentPane().add(btnNewButton_3);
	}
}
