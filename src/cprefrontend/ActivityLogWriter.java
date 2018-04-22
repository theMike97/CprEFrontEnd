/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ActivityLogWriter {
    
    JTextPane log;
    
    public ActivityLogWriter() {
        log = Window.getActivityLog();
    }
    
    public void logWriteln(String line) {
        append(line, 0);
    }
    
    public void logOverwriteln(String line) {
        clearLine();
        logPrintln(line);
    }
    
    public void clearLine() {
        try {
            Document doc = log.getDocument();
            String content = doc.getText(0, doc.getLength());
            int linebreak = content.lastIndexOf('\n');
            linebreak = (linebreak == -1) ? 0 : linebreak;
            doc.remove(linebreak, doc.getLength() - linebreak);
        } catch (BadLocationException ex) {
            Logger.getLogger(ActivityLogWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logPrintln(String line) {
        if (log.getDocument().getLength() > 0) {
            logWriteln("\n");
        }
        logWriteln(line);
    }
    
    public void logPrintErr(String err) {
        if (log.getDocument().getLength() > 0) {
            logWriteln("\n");
        }
        append(err, 1);
    }
    
    private void append(String s, int status) {
        try {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontFamily, "Lucida Console");
            aset = (status == 1) ? sc.addAttribute(aset, StyleConstants.Foreground, Color.red) : sc.addAttribute(aset, StyleConstants.Foreground, Color.black);
            
            Document doc = log.getDocument();
            doc.insertString(doc.getLength(), s, aset);
        } catch (BadLocationException ex) {
            
        }
    }
    
}
