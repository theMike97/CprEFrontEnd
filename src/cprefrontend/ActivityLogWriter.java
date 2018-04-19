/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author Mike
 */
public class ActivityLogWriter {
    
    JTextArea log;
    
    public ActivityLogWriter() {
        log = Window.getActivityLog();
    }
    
    public void logWriteln(String line) {
        log.append(line);
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
            log.append(line);
    }
    
}
