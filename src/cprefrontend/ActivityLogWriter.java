/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

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
    
    public void logOverwriteln(String line) throws BadLocationException {
        clearLine();
        logPrintln(line);
    }
    
    public void clearLine() throws BadLocationException {
        Document doc = log.getDocument();
        String content = doc.getText(0, doc.getLength());
        int linebreak = content.lastIndexOf('\n');
        linebreak = (linebreak == -1) ? 0 : linebreak;
        doc.remove(linebreak, doc.getLength() - linebreak);
    }
    
    public void logPrintln(String line) {
        if (log.getDocument().getLength() == 0) {
            logWriteln(line);
        } else {
            log.append("\n" + line);
        }
    }
    
}
