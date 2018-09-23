package edu.uta.cse.proggen.ui;


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
