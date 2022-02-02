package main.java;//Import Event Library
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
// import java.security.cert.PKIXRevocationChecker.Option;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.JTextComponent;
//Import Swing library 
import javax.swing.*;
import java.awt.*;

// Class Repplace.......................
public class Replace implements ActionListener{

    JFrame frame;
    JLabel to_replace , with_replace , caseSensitivLabel;
    JTextField word_to_replace , word_with_replace;
    JButton replace , cancel; 
    String getted_String;
    String to_replace_word=null; 
    String replace_with=null; 
    // Constructor
    Replace(String getString){

        this.getted_String =getString;
        caseSensitivLabel = new JLabel("Enter Corrent Word, Case Sensitive");
        caseSensitivLabel.setBounds(30, 10, 220, 30);
     
        to_replace = new JLabel("New Word");
        to_replace.setBounds(40, 40, 220, 30);
        word_to_replace = new JTextField(50);
        word_to_replace.setBounds(40,  120, 220, 25);

        with_replace = new JLabel("Replace With");
        with_replace.setBounds(40, 95, 220, 30);
        word_with_replace = new JTextField(50);
        word_with_replace.setBounds(40, 70, 220, 25);

        replace = new JButton("Replace");
        replace.addActionListener(this);
        // replace.addKeyListener(this);
        replace.setBounds(40, 150, 100, 30);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setBounds(160, 150, 100, 30);


        frame = new JFrame("Replace");
        frame.add(caseSensitivLabel);
        frame.add(to_replace);
        frame.add(word_to_replace);
        frame.add(with_replace);
        frame.add(word_with_replace);
        frame.add(replace);
        frame.add(cancel);

        frame.setSize(300,250);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);;
        frame.setVisible(true);

    }
    public String replace (){return replace(getted_String, to_replace_word, replace_with);}
    //Replace Method
    public String replace( String newString ,String wordToReplace, String wordToReplaceWith) {
        if (!wordFound(wordToReplace, newString))   return null; 
        else                                        return newString.replaceAll(wordToReplace, wordToReplaceWith); 
    }

    //WordFound Method
    private boolean wordFound( String wordtoCheck , String wordToCheckIn) {
       if(wordToCheckIn.contains(wordtoCheck)){     return true;
       }
       else                                         return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == replace){
           
            to_replace_word = word_to_replace.getText();
            replace_with = word_with_replace.getText();

            System.out.println("Empty:" + to_replace_word);
            if ( to_replace_word.isEmpty() || replace_with.isEmpty() ){
                JOptionPane.showMessageDialog(new JFrame("Error"), "You Left Empty SomeWhere!" );
            }
            else if (getted_String.isEmpty()){
                JOptionPane.showMessageDialog(new JFrame("Error!"), "Error, File is Empty!");
            }
            else{
                System.out.println("Original String is : \n" + getted_String);
                System.out.println("Modified String is : \n" + replace(getted_String, to_replace_word, replace_with));;
                if(replace(getted_String,to_replace_word,replace_with)==null){
                    JOptionPane.showMessageDialog(new JFrame(), "Can't Replace, Word not found!");
                }
                else{
                Notepad.textArea.setText(replace(getted_String, to_replace_word, replace_with));
                frame.dispose();
                }  
            }
        }
       else if (e.getSource()==cancel){
           frame.dispose();
       }
    } 
}
