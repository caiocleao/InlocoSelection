import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui extends JFrame {

	private JPanel contentPane;
	private JTextField pathField;
	private JTable table;
	MainApp appInstance;
	List dataFromFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setTitle("Testing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel pathLabel = new JLabel("FilePath:");
		pathLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		pathField = new JTextField();
		pathField.setHorizontalAlignment(SwingConstants.LEFT);
		pathField.setColumns(10);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			
			/*
			 * 
			 * Event handler for click on startButton.
			 * 
			*/
			public void actionPerformed(ActionEvent arg0) {
				
				if ( appInstance == null || ( appInstance != null && !appInstance.running ) ) {
				
					appInstance = new MainApp ();
					
					try {
			
						dataFromFile = appInstance.readFile(pathField.getText());
						if ( dataFromFile.size > 0 ) {
							// There is something on the file:
							DefaultTableModel dtm = (DefaultTableModel) table.getModel();
							dtm.setRowCount(dataFromFile.size+2);
							
							Node aux = dataFromFile.header;
							int currentLine = 0;
							while ( aux != null ) {
								
								
								dtm.setValueAt(aux.url, currentLine, 0);
								dtm.setValueAt(aux.totalRequests, currentLine, 1);
								dtm.setValueAt(aux.srResult, currentLine, 2);
								dtm.setValueAt(aux.sfrResult, currentLine, 3);
								dtm.setValueAt("Not Enough Requests", currentLine, 4);
								currentLine++;
								aux = aux.right;
								
							}
								
							appInstance.start();
							
						}
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
			}
		});
		
		JButton btnBrowse = new JButton("browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choose your file");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				if ( fc.showOpenDialog(btnBrowse) == JFileChooser.APPROVE_OPTION ) {
					//
				}
				
				String filePathString = fc.getSelectedFile().getAbsolutePath();
				pathField.setText(filePathString);
				
			}
		});
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			
			/* 
			 *  Action Handler for Stop button. 
			*/
			
			public void actionPerformed(ActionEvent arg0) {
				
				if ( appInstance != null ) {
					appInstance.running = false;
				}
				
			}
		});
		
		JButton btnUpdateTrable = new JButton("Update Trable");
		btnUpdateTrable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				Node aux = dataFromFile.header;
				int currentLine = 0;
				while ( aux != null ) {
					
					dtm.setValueAt(aux.url, currentLine, 0);
					dtm.setValueAt(aux.totalRequests, currentLine, 1);
					
					double roundedSr = Math.round(  (aux.srResult/aux.totalRequests) * 100 );
					double roundedSfr = Math.round( (aux.sfrResult/aux.totalRequests) * 100 );
					dtm.setValueAt(roundedSr + "%" , currentLine, 2);
					dtm.setValueAt(roundedSfr + "%", currentLine, 3);
					
					System.out.println(roundedSfr + " aux -> " + aux.getSfr());
					
					if ( roundedSfr >= aux.getSr() ) {
						dtm.setValueAt("SRO Met ( Fast )", currentLine, 4);
					} else if ( roundedSr >= aux.getSr() ) {
						dtm.setValueAt("SRO Met", currentLine, 4);
					} else  {
						dtm.setValueAt("SRO Not Met", currentLine, 4);
					}
					
					
					currentLine++;
					aux = aux.right;
					
				}
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(26, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnUpdateTrable)
							.addGap(18)
							.addComponent(btnStop)
							.addGap(18)
							.addComponent(btnStart)
							.addGap(51))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 734, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(pathLabel, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(pathField, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
									.addGap(55)
									.addComponent(btnBrowse)))
							.addGap(28))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(pathLabel)
						.addComponent(btnBrowse)
						.addComponent(pathField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStart)
						.addComponent(btnStop)
						.addComponent(btnUpdateTrable))
					.addContainerGap())
		);
		
		JLabel lblUrlList = new JLabel("URL List");
		
		JLabel lblSr = new JLabel("SR");
		
		JLabel lblSfr = new JLabel("SFR");
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"url", "total requests", "SR", "SFR", "Status"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		
		JLabel lblTotalRequests = new JLabel("Total Requests");
		
		JLabel lblStatus = new JLabel("Status");
		GroupLayout gl_infoPanel = new GroupLayout(infoPanel);
		gl_infoPanel.setHorizontalGroup(
			gl_infoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(12)
					.addComponent(lblUrlList)
					.addPreferredGap(ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
					.addComponent(lblTotalRequests)
					.addGap(56)
					.addComponent(lblSr)
					.addGap(52)
					.addComponent(lblSfr)
					.addGap(74)
					.addComponent(lblStatus)
					.addGap(80))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 691, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_infoPanel.setVerticalGroup(
			gl_infoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUrlList)
						.addComponent(lblTotalRequests)
						.addComponent(lblSr)
						.addComponent(lblSfr, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStatus))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		infoPanel.setLayout(gl_infoPanel);
		contentPane.setLayout(gl_contentPane);
	}
}
