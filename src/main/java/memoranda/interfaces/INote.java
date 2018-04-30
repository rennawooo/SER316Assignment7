/**
 * Note.java
 * Created on 11.02.2003, 17:05:27 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.interfaces;

import main.java.memoranda.date.CalendarDate;
/**
 * 
 */
/*$Id: Note.java,v 1.4 2004/09/30 17:19:52 ivanrise Exp $*/
public interface INote {
    
    CalendarDate getDate();
    
    String getTitle();
    void setTitle(String s);
    
	String getId();
	void setId(String s);
	
    boolean isMarked();
    void setMark(boolean mark);
        
    IProject getProject();
}
