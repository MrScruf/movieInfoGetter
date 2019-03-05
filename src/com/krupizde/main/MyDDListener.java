/**
 * 
 */
package com.krupizde.main;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * @author Krupicka
 *
 */
public class MyDDListener implements DropTargetListener {

	private ArrayList<File> returnFiles = null;
	private DefaultListModel<File> model;

	public MyDDListener(ArrayList<File> files, DefaultListModel<File> model) {
		returnFiles = files;
		this.model = model;
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		System.out.println("Drop here");

	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void drop(DropTargetDropEvent event) {
		event.acceptDrop(DnDConstants.ACTION_COPY);
		Transferable transferable = event.getTransferable();
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		for (DataFlavor flavor : flavors) {
			if (flavor.isFlavorJavaFileListType()) {

				// Get all of the dropped files
				List<File> files = null;
				try {
					files = (List<File>) transferable.getTransferData(flavor);
				} catch (UnsupportedFlavorException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Loop them through

				for (File file : files) {
					if (file.getName().contains(".txt")) {
						System.out.println("Dropped file: " + file);
						returnFiles.add(file);
						model.addElement(file);
					}

				}

			}
		}
		// Inform that the drop is complete
		event.dropComplete(true);
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

}
