package edu.uta.cse.proggen.ui;

/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2002.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id: LICENSE,v 1.8 2004/02/09 03:33:38 ian Exp $
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java 
 * language and environment is gratefully acknowledged.
 * 
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


public class RugratUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RugratUI(String path) {

		setTitle("Rugrat");

		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		setMaximumSize(DimMax);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		JPanel jsp1 = new JPanel();
		JPanel jsp2 = new JPanel();
	

		// JScrollPane scrollFrame = new JScrollPane(jsp1);
		jsp1.setAutoscrolls(true);
		showFiles(jsp1, jsp2,path);
		jsp1.setBackground(Color.WHITE);
		jsp2.setBackground(Color.LIGHT_GRAY);

		JTextArea fileContent = new JTextArea("");
		fileContent.setEditable(false);
		fileContent.setSize(jsp2.getMaximumSize());

		jsp2.add(fileContent);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jsp1, jsp2);

		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.1);
		getContentPane().add(splitPane);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * FileTree sp = new FileTree();
	 * sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); sp.setVisible(true);
	 * 
	 * }
	 */

	private void showFiles(JPanel jsp1,JPanel jsp2, String path) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(path);

		addChilds(root, path);

		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		JTree tree = new JTree(treeModel);

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
				TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
				if (tp != null) {

					Object elements[] = tp.getPath();
					String value="";
					for (int i = 0, n = elements.length; i < n; i++) {

						value += elements[i] + "\\";
					}
					value=value.substring(0, value.length() - 1);
					File f= new File(value);
					if(f.isFile()) {
						Scanner scanner = null;
						try {
							scanner = new Scanner( f );
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String text = scanner.useDelimiter("\\A").next();
						scanner.close();
						jsp2.setBackground(Color.WHITE);
						JTextArea fileContent = (JTextArea) jsp2.getComponent(0);
						fileContent.setText(text);
						
					}else {
						JTextArea fileContent = (JTextArea) jsp2.getComponent(0);
						fileContent.setText("");
						jsp2.setBackground(Color.LIGHT_GRAY);
					}

				}

			}
		});
		jsp1.add(tree);

	}

	protected File[] getListFiles(String Path) {
		File file = new File(Path);
		return file.listFiles();
	}

	private void addChilds(DefaultMutableTreeNode rootNode, String path) {
		File[] files = this.getListFiles(path);
		for (File file : files) {
			if (file.isDirectory()) {
				DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(file.getName());
				addChilds(subDirectory, file.getAbsolutePath());
				rootNode.add(subDirectory);
			} else {
				rootNode.add(new DefaultMutableTreeNode(file.getName()));

			}
		}
	}

}
