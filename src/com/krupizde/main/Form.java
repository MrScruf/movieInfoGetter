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
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
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
	/**
	 * Create the application.
	 */
	public Form() {
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

		textArea = new JTextArea();
		textArea.setBounds(12, 90, 985, 570);
		frame.getContentPane().add(textArea);
		frame.setVisible(true);
		textArea.setEditable(false);
		textArea.setAutoscrolls(true);
		setPrintStream();		
		
		listModel = new DefaultListModel<File>();
		
		listener = new MyDDListener(loadedFiles, listModel);
		
		list = new JList<File>(listModel);
		list.setBounds(1009, 90, 325, 466);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		frame.getContentPane().add(list);
		
		
		
		 new DropTarget(textArea, listener);
		
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
				try {
					startLoading();
				} catch (IOException e1) {
					System.out.println("Error occured");
					System.out.println(e1.getStackTrace());
				}
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
	StringBuilder sb = new StringBuilder();
	private void setPrintStream() {
		out = new OutputStream() {
			@Override
			// Override the write(int) method to append the character to the JTextArea
			public void write(int b) throws IOException {
				if (b == '\r')
				     return;

				  if (b == '\n') {
				     final String text = sb.toString() + "\n";
				     SwingUtilities.invokeLater(new Runnable() {
				        // except this is queued onto the event thread.
				        public void run() {
				           textArea.append(text);
				        }
				     });
				     sb.setLength(0);

				     return;
				  }

				  sb.append((char) b);
			}
		};

		// Create the PrintStream that System.out will be set to.
		// Make sure autoflush is true.
		printOut = new PrintStream(out, true);

		// Set the system output to the PrintStream
		System.setOut(printOut);

	}
	
	private void startLoading() throws IOException {
		InfoGetter ig = new InfoGetter();
		FileLoader fl = new FileLoader();
		ArrayList<Movie> loaded = new ArrayList<Movie>();
		for(File f : loadedFiles) {
			System.out.println("Loading file: " + f.getName());
			loaded.addAll(fl.loadMovies(f.getAbsolutePath()));
		}
		for(Movie m : loaded) {
			System.out.println("______________________________________________________");
			System.out.println("Starting movie: "+ m.getName());
			ig.getMovieInfo(m);
		}
	}
}
