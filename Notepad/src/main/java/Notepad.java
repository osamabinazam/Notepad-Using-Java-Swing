package main.java;//Import Event Library
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.*;
// import java.security.cert.PKIXRevocationChecker.Option;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;

import org.w3c.dom.Text;

//Import Swing library 
import javax.swing.*;
import java.awt.*;

public class Notepad implements ActionListener,MenuListener{

    JFrame frame ;
    Container c ;
    JMenuBar top_menu;
    JMenu file , edit , help;
    JMenuItem newfile , openfile, find_word , replace_word, save , save_as, exit, undo , redo;
    JMenuItem cut,copy,paste,select_all,rHighlight;
    public static JTextArea textArea;
    JScrollPane scroll_pane;
    Font font1,  font2 , font3;
    File file_container;
    String copyPasteText;
    UndoAndRedo stack_of_string = new UndoAndRedo();
    //Contructor 
    Notepad(String title){
        font1 = new Font ("Arial", Font.PLAIN,20);
        font2 =  new Font("Arial" , Font.ITALIC, 12);
        font3 = new Font ("Arial", Font.ROMAN_BASELINE,15);

        frame = new JFrame(title);
        top_menu = new JMenuBar();
        textArea = new JTextArea();
        textArea.setVisible(true);
        textArea.setFont(font1);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scroll_pane = new JScrollPane(textArea);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //All Menu for MenuBar 
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Others");

        //Items for File Menu
        newfile = new JMenuItem("New");
        openfile = new JMenuItem("Open");
        save = new JMenuItem("Save");
        save_as = new JMenuItem("Save as");
        exit = new JMenuItem("Exit");
 
        //Items for Edit Menu 
        replace_word = new JMenuItem("Replace");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("paste");
        select_all = new JMenuItem("Select all");
       
        //Items for Others Menu
        find_word = new JMenuItem("Find");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        rHighlight = new JMenuItem("Remove Highlights");

        //Setting Font of MenuItems
        newfile.setFont(font2);
        openfile.setFont(font2);
        save.setFont(font2);
        save_as.setFont(font2);
        exit.setFont(font2);
        find_word.setFont(font2);
        replace_word.setFont(font2);
        undo.setFont(font2);
        redo.setFont(font2);
        cut.setFont(font2);
        copy.setFont(font2);
        paste.setFont(font2);
        select_all.setFont(font2);
        rHighlight.setFont(font2);

        //Setting Action Listner and KeyStroke to the MenuItems 
        newfile.addActionListener(this);
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        openfile.addActionListener(this);
        openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        save.addActionListener(this);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save_as.addActionListener(this);
        save_as.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.CTRL_MASK));
        exit.addActionListener(this);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        find_word.addActionListener(this);
        find_word.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        replace_word.addActionListener(this);
        replace_word.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        undo.addActionListener(this);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK
        ));
        redo.addActionListener(this);
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        select_all.addActionListener(this);
        select_all.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        rHighlight.addActionListener(this);

        //Adding MenuItem to file Menu
        file.add(newfile);
        file.add(openfile);
        file.add(save);
        file.add(save_as);
        file.add(exit);
        //Adding MenuItem to edit Menu
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(select_all);
        edit.add(replace_word);

        help.add(find_word);
        help.add(undo);
        help.add(redo);
        help.add(rHighlight);

        file.setFont(font3);
        edit.setFont(font3);
        help.setFont(font3);

        //Adding Menu to Manubar
        top_menu.add(file);
        // top_menu.addSep
        top_menu.add(edit);
        // top_menu.setFont(font1);
        top_menu.add(help);
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        frame.setIconImage(icon);

        // scroll_pane.add(textArea);
        frame.setJMenuBar(top_menu);
        frame.add(scroll_pane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700 , 480);
        frame.setLocationRelativeTo(null);;
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser file_chooser = new JFileChooser();
        if(e.getSource()==newfile){
            System.out.println("New File Menu Item Clicked");
            frame.setTitle("Untitled.txt - NotePad");
            textArea.setText("");
            file_container = null;
        }
        else if(e.getSource()==openfile){
            
            int request = file_chooser.showDialog(null, "Open");
            if (request==JFileChooser.APPROVE_OPTION){
                try{
                    File newFile = file_chooser.getSelectedFile();
                //    frame.(newFile.getAbsolutePath());
                    openFile(newFile.getAbsolutePath());
                    frame.setTitle(newFile.getName() + " - NotePad");
                }
                catch(Exception ex ){}
            }
        }
        if(e.getSource()==save){
          
            try {
             if (file_container==null){
//                 System.out.println("Testing...");
                 file_chooser.setSelectedFile(new File ("Untitled.txt"));
                 
                 int returned_valule = file_chooser.showSaveDialog(null);
                 if (returned_valule == file_chooser.APPROVE_OPTION){
                     File newFile = file_chooser.getSelectedFile();
                     saveFileAtDirectory(newFile.getAbsolutePath());
                     frame.setTitle(newFile.getName() + " - Notepad");
                     file_container = newFile;
                 }
             }
             else{
                 FileWriter file_writer = new FileWriter(file_container);
                 file_writer.write(textArea.getText());
                 frame.setTitle(file_container.getName() + " - Notepad");
                 file_writer.close();
             }
            } catch (Exception ex) {
                //TODO: handle exception
            }
         }
        else if(e.getSource()==save_as){

            if (file_container!=null){
                file_chooser.setCurrentDirectory(file_container);
                file_chooser.setSelectedFile(file_container);
            }
            else {
                file_chooser.setSelectedFile(new File ("Untitled.txt"));
            }

            int returned_valule = file_chooser.showSaveDialog(null);
            if(returned_valule == file_chooser.APPROVE_OPTION){
                try{
                    File newfile = file_chooser.getSelectedFile();
                    saveFileAtDirectory (newfile.getAbsolutePath());
                    frame.setTitle(newfile.getName()+" - Notepad");
                    file_container = newfile;
                }
                catch(IOException ex){}
            }
        }
        else if (e.getSource()==cut){

             copyPasteText = textArea.getSelectedText();
            textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
        }
        else if (e.getSource() ==  select_all){ textArea.selectAll();}
        else if (e.getSource() == copy){        copyPasteText = textArea.getSelectedText();}
        else if (e.getSource() == paste){       textArea.insert(copyPasteText, textArea.getCaretPosition());}
        else if(e.getSource()==find_word){      new Find();}
        //Replace Menu Logic
        else if(e.getSource()==replace_word){
            //Navigating to Replace Class
            new Replace(textArea.getText());
        }
        else if(e.getSource()==undo){
            
            String text="";
            try{
            text = textArea.getSelectedText();
            textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
            if (text==null)     JOptionPane.showMessageDialog(new JFrame (), "Error! File is empty.");
            else {              stack_of_string.Push(text);}
        }
        catch (Exception ex){   JOptionPane.showMessageDialog(new JFrame(), "Error!. Please Select Something.");}
        }
        else if(e.getSource()==redo){   textArea.insert(stack_of_string.Pop(), textArea.getCaretPosition());}
        else if(e.getSource()==exit){
            int returned_value = isModified();
            System.out.println(returned_value);
            if (returned_value ==1){
                try {
                    FileWriter file_writer = new FileWriter(file_container);
                    file_writer.write(textArea.getText());
                    frame.setTitle(file_container.getName() + " - Notepad");
                    file_writer.close();
                }
                catch (IOException ex){}
            }
            else
                System.exit(1);
            System.exit(0);
        }
        else if (e.getSource()==rHighlight){
            Highlighter highlighter = Notepad.textArea.getHighlighter();
            highlighter.removeAllHighlights();
        }
    }

    //Method Check Whether File is Saved or not.
    public int isModified(){
        int x = JOptionPane.showConfirmDialog(new JFrame(),"The text in the has Changed\nDo you want to save it ?","NotePad", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
    
        // int x=JOptionPane.showConfirmDialog(this, "The text in the "+getTitle()+" has changed\nDo you want to save it", "Notepad", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
        if(x==JOptionPane.YES_OPTION) return 1;
        else if(x==JOptionPane.NO_OPTION) return 2;
        else return 3;
    }


//  Open File Method 
    public void openFile (String filename) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(
        new FileInputStream(filename)));
        String  data;
        textArea.setText("");
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        while((data = reader.readLine() )!= null){
            textArea.setText(textArea.getText() + data + "\n");
        }
        frame.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
        reader.close();;
    }//End of Open Method

//  Save At Any Directory Method
    public void saveFileAtDirectory (String filename) throws IOException{
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream output = new DataOutputStream(new FileOutputStream(filename));
        output.writeBytes(textArea.getText());
        output.close();
        frame.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
    }//End Of The Method
    // Driven Method
    public static void main(String[] args) {    new Notepad("NotePad");}

    @Override
    public void menuSelected(MenuEvent ml)
   {
       if(ml.getSource()==edit)
       {
        //    JTextComponent disp ;
        // if(disp.getSelectedText()==null)
        //    {
        //        cut.setEnabled(false);
        //        copy.setEnabled(false);
        //    }
        //    else
        //    {
        //        cut.setEnabled(true);
        //        copy.setEnabled(true);
        //    }
       }
   }
    @Override
    public void menuDeselected(MenuEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void menuCanceled(MenuEvent e) {
        // TODO Auto-generated method stub
        
    }
}
//Find Option Class 
class Find implements ActionListener {

    JFrame frame;
    JTextField textField;
    JButton find_Button,cancel_Button;
    JLabel label;

    Find(){
        frame = new JFrame("Find");
        frame.setLayout(null);
        
        textField =  new JTextField(30);
        textField.setVisible(true);;
        // textField.setBounds(30, 40, 200, 200);;
        textField.setBounds(30, 40, 210, 30);

        find_Button = new JButton("Find");
        find_Button.setBounds(40, 80, 85, 30);
        find_Button.addActionListener(this);

        cancel_Button = new JButton("Cancel");
        cancel_Button.setBounds(140, 80, 85, 30);
        cancel_Button.addActionListener(this);
     
        frame.add(cancel_Button);
        frame.add(find_Button);
        frame.add(textField);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(270, 200);
        frame.setLocationRelativeTo(null);;
        frame.setResizable(false);
        frame.setVisible(true);
    }


    private boolean wordFound( String wordtoCheck , String wordToCheckIn) {
        if(wordToCheckIn.contains(wordtoCheck)){
            return true;
        }
        else
             return false;
     }

     public ArrayList find (String wordToFindin , String wordToFind){
         ArrayList<Integer> list = new ArrayList<Integer>();
         Boolean flag = true;
         int index = 0;
         for (int i=0; i< wordToFindin.length(); i++){
                flag = true;
                if (wordToFindin.charAt(i) == wordToFind.charAt(0)){
                    index = i+1;
                    for (int j =1 ; j<wordToFind.length(); ++j, ++index){
                              if (wordToFindin.charAt(index) != wordToFind.charAt(j)){
                                flag = false; 
                                break;
                        }
                    }
                    if (flag)
                        list.add(i);
                }
         }
         return list;
     }
   
    @Override
    public void actionPerformed(ActionEvent e)  {
       if (e.getActionCommand() == "Find"){
           System.out.println("Find Button CLicked");
           String getted_data = textField.getText();
           System.out.println("Find Text Field Data : " +getted_data );
            
            // System.out.println(getted_data );
            if(getted_data.isEmpty())
                    JOptionPane.showMessageDialog(new JFrame(), "You have left empty somewhere!");
            else {
                try{
                    System.out.println("try Catch Block At Find ");
                    System.out.println("Word Find : " + wordFound(getted_data, Notepad.textArea.getText()));
                if (wordFound(getted_data, Notepad.textArea.getText())){

                    Highlighter highlighter = Notepad.textArea.getHighlighter();
                    highlighter.removeAllHighlights();
                    HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                    ArrayList <Integer> list = new ArrayList<>() ;
                    System.out.println("Before Find Method");

                    list  = find(Notepad.textArea.getText(), getted_data);              //Find Method Call

                    System.out.println("After Find Method");
                    int end_index = getted_data.length();

                    System.out.println(list.size());
                    for (int i=0; i< list.size(); i++){
                        highlighter.addHighlight(list.get(i), list.get(i)+end_index,painter);
                    }

                    // Highlighter highlighter = Notepad.textArea.getHighlighter();
                    // HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                    // int position1 = getted_data.indexOf(getted_data);
                    // int position2= position1+ getted_data.length();

                    // highlighter.addHighlight(position1, position2, painter);

                    // // highlighter.removeHighlight(tag);
                    frame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(), "Word not found!");
                }
            }catch(Exception ex){}
        }
        }
       else if (e.getActionCommand() == "Cancel"){
       		frame.dispose();
       
        }        
     }
}