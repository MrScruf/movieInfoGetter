package com.krupizde.main;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class Form {

	private JFrame frame;
	private JFileChooser fileChooser = null;
	PrintStream printOut;
	OutputStream out;
	JTextArea textArea;
	MyDDListener listener = null;
	ArrayList<File> loadedFiles = null;
	JList<File> list;
	DefaultListModel<File> listModel = null;
	private JButton btnDelete;
	private JScrollPane scrollPane;
	/**
	 * Create the application.
	 */
	public Form() {
		setPrintStream();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		loadedFiles = new ArrayList<File>();
		frame = new JFrame();
		frame.setBounds(100, 100, 1364, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		textArea = new JTextArea();		
		textArea.setEditable(false);
		textArea.setAutoscrolls(true);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(12, 90, 985, 570);
		scrollPane.setAutoscrolls(true);
		frame.getContentPane().add(scrollPane);		
		
		listModel = new DefaultListModel<File>();
		listener = new MyDDListener(loadedFiles, listModel);
		new DropTarget(textArea, listener);	
		
		list = new JList<File>(listModel);
		list.setBounds(1009, 90, 325, 466);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		frame.getContentPane().add(list);
		
		
		
		JButton btnNewButton = new JButton("Browse files");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f = chooseFile();
				if(f!=null && f.getName().contains(".txt")) {
					System.out.println("Choosed file: " + f.getAbsolutePath());
					loadedFiles.add(f);
					listModel.addElement(f);
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(12, 13, 200, 50);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Starting ...");
				LoadingThread lt = new LoadingThread(loadedFiles);
				lt.start();
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnStart.setBounds(224, 13, 200, 50);
		frame.getContentPane().add(btnStart);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File selected = list.getSelectedValue();
				loadedFiles.remove(selected);
				listModel.removeElement(selected);
				System.out.println("Removed: "+selected);
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.setBounds(1009, 569, 145, 55);
		frame.getContentPane().add(btnDelete);
		
		

		fileChooser = new JFileChooser();
		fileChooser.setLocation(50, 50);
		fileChooser.setSize(600, 600);
		
	}

	private File chooseFile() {
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			return selectedFile;
		}
		return null;
	}
	private void setPrintStream() {
		out = new OutputStream() {
			@Override
			// Override the write(int) method to append the character to the JTextArea
			public void write(int b) throws IOException {
				textArea.append(String.valueOf((char)b));
				if((char)b == '\n') {
					textArea.update(textArea.getGraphics());
				}
			}
		};

		// Create the PrintStream that System.out will be set to.
		// Make sure autoflush is true.
		printOut = new PrintStream(out, true);

		// Set the system output to the PrintStream
		System.setOut(printOut);

	}

}
