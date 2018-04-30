/**
 * NoteList.java
 * Created on 21.02.2003, 15:40:46 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.interfaces;
import java.util.Collection;

import main.java.memoranda.date.CalendarDate;
/**
 * 
 */
/*$Id: NoteList.java,v 1.5 2004/10/07 21:33:36 ivanrise Exp $*/
public interface INoteList {
    
    Collection getAllNotes();
    
    Collection getMarkedNotes();

    Collection getNotesForPeriod(CalendarDate startDate, CalendarDate endDate);

    INote getNoteForDate(CalendarDate date);
    
    INote createNoteForDate(CalendarDate date);
    
//    void removeNoteForDate(CalendarDate date);
	void removeNote(CalendarDate date, String id);

    INote getActiveNote();
    
    nu.xom.Document getXMLContent();

}
