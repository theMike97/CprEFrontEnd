/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Mike
 */
public class ActivityLog extends JTextPane {
    
    /**
     * Constructor calls super constructor
     */
    public ActivityLog() {
        super();
    }
    
    /**
     * Writes text to the log which does not preceed a newline character
     * @param line text to write
     */
    public void logWriteln(String line) {
        append(line, 0);
    }
    
    /**
     * Overwrites last line with new text
     * @param line text to write/overwrite
     */
    public void logOverwriteln(String line) {
        clearLine();
        logPrintln(line);
    }
    
    /**
     * Overwrites last line with new error text
     * @param line error text to write/overwrite
     */
    public void logErrOverwriteln(String line) {
        clearLine();
        logPrintErr(line);
    }
    
    /**
     * Clears the last line of text
     */
    public void clearLine() {
        try {
            Document doc = getDocument();
            String content = doc.getText(0, doc.getLength());
            int linebreak = content.lastIndexOf('\n');
            linebreak = (linebreak == -1) ? 0 : linebreak;
            doc.remove(linebreak, doc.getLength() - linebreak);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Clears the entire log
     */
    public void clear() {
        try {
            Document doc = getDocument();
            doc.remove(0, doc.getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Prints a line of text to the log (includes newline character)
     * @param line line of text to print
     */
    public void logPrintln(String line) {
        if (getDocument().getLength() > 0) {
            logWriteln("\n");
        }
        logWriteln(line);
    }
    
    /**
     * Prints a line of error text to the log (includes newline character)
     * @param err line of error text to print
     */
    public void logPrintErr(String err) {
        if (getDocument().getLength() > 0) {
            logWriteln("\n");
        }
        append(err, 1);
    }
    
    private void append(String s, int status) {
        try {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontFamily, "Lucida Console");
            aset = (status == 1) ? sc.addAttribute(aset, StyleConstants.Foreground, Color.red) : sc.addAttribute(aset, StyleConstants.Foreground, Color.black);
            
            Document doc = getDocument();
            doc.insertString(doc.getLength(), s, aset);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
    
}
