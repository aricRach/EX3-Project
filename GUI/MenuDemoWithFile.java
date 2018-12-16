package GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

public class MenuDemoWithFile extends JFrame {

    static final long serialVersionUID = 1L;
    JMenuBar wholeMenuBar;
    JMenu fileMenu, editMenu;
    JMenuItem openItem, saveItem, copyItem, pasteItem;
    JTextField textField;
    int textFieldSize = 10;

    public MenuDemoWithFile() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setBounds(200, 200, 350, 200);
        initComponents();
        createGui();
        this.setVisible(true);
    }

    public void initComponents() {
        //set menu bar
        wholeMenuBar = new JMenuBar();
        setJMenuBar(wholeMenuBar);
        wholeMenuBar.setVisible(true);
        //set file menu	with open, save
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        //CTRL_MASK: The control modifier. An indicator that the control key was held down during the event.
        //openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,  ActionEvent.CTRL_MASK));
        fileMenu.add(openItem);
        saveItem = new JMenuItem("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(saveItem);
        wholeMenuBar.add(fileMenu);
        // set edit menu with copy paste
        editMenu = new JMenu("Edit");
        copyItem = new JMenuItem("Copy");
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
        editMenu.add(copyItem);
        pasteItem = new JMenuItem("Paste");
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
        editMenu.add(pasteItem);
        wholeMenuBar.add(editMenu);
        //text field
        textField = new JTextField(textFieldSize);
        this.add(textField);
    }

    public void createGui() {
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("open choozen");
                readFileDialog();
            }
        });
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText("save choozen");
                writeFileDialog();
            }
        });
        // edit menu
        copyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textField.setText("copy choozen");
            }
        });
        pasteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textField.setText("paste choozen");
            }
        });
    }

    public void readFileDialog() {
        //		try read from the file
        FileDialog fd = new FileDialog(this, "Open text file", FileDialog.LOAD);
        fd.setFile("*.txt");
        fd.setDirectory("C:\\Users\\Elizabeth\\Desktop\\Temp");
        fd.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
        fd.setVisible(true);
        String folder = fd.getDirectory();
        String fileName = fd.getFile();
        try {
            FileReader fr = new FileReader(folder + fileName);
            BufferedReader br = new BufferedReader(fr);
            String str = new String();
            for (int i = 0; str != null; i = i + 1) {
                str = br.readLine();
                if (str != null) {
                    System.out.println(str);
                }
            }
            br.close();
            fr.close();
        } catch (IOException ex) {
            System.out.print("Error reading file " + ex);
            System.exit(2);
        }
    }

    public void writeFileDialog() {
        //		 try write to the file
        FileDialog fd = new FileDialog(this, "Save the text file", FileDialog.SAVE);
        fd.setFile("*.txt");
        fd.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
        fd.setVisible(true);
        String folder = fd.getDirectory();
        String fileName = fd.getFile();
        try {
            FileWriter fw = new FileWriter(folder + fileName);
            PrintWriter outs = new PrintWriter(fw);
            outs.println("Why Java?\n"
                    + "1. Java is small and beautiful\n"
                    + "2. Java is object oriented\n"
                    + "3. Java supports Internet\n"
                    + "4. Java is platform independent\n"
                    + "5. Java has libraries");
            outs.close();
            fw.close();
        } catch (IOException ex) {
            System.out.print("Error writing file  " + ex);
        }

    }

    public static void main(String[] args) {
        new MenuDemoWithFile();
    }
}
