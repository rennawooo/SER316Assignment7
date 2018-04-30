package main.java.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.util.Collections;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import main.java.memoranda.CurrentNote;
import main.java.memoranda.CurrentProject;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.interfaces.INote;
import main.java.memoranda.interfaces.INoteList;
import main.java.memoranda.interfaces.INoteListener;
import main.java.memoranda.interfaces.IProject;
import main.java.memoranda.interfaces.IProjectListener;
import main.java.memoranda.interfaces.IResourcesList;
import main.java.memoranda.interfaces.ITaskList;
import main.java.memoranda.util.Configuration;

/*$Id: NotesList.java,v 1.9 2005/05/05 16:19:16 ivanrise Exp $*/
public class NotesList extends JList {

    public static final int EMPTY = 0;    
    public static final int ALL = 1;
    public static final int BOOKMARKS = 2;

    private Vector notes = null;
    boolean sortOrderDesc = false;

    int _type = ALL;

    public NotesList(int type) {
        super();
		if(Configuration.get("NOTES_SORT_ORDER").toString().equalsIgnoreCase("true")) {
			sortOrderDesc = true;
		}
        _type = type;
        this.setFont(new java.awt.Font("Dialog", 0, 11));
        this.setModel(new NotesListModel());
        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                updateUI();
            }
        });
		
        CurrentNote.addNoteListener(new INoteListener() {
            public void noteChange(INote n, boolean toSaveCurrentNote) {
                updateUI();
            }
        });

        CurrentProject.addProjectListener(new IProjectListener() {
            public void projectChange(IProject p, INoteList nl, ITaskList tl, IResourcesList rl) {
            }
            public void projectWasChanged() {
                update();
            }
        });
        this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    public NotesList() {
        this(ALL);
    }

    public void update() {
        if (_type != EMPTY) {
            update(CurrentProject.getNoteList());
		}
        else {
			update(new Vector());
		}
    }

    public void update(INoteList nl) {
        if (_type == ALL)
            notes = (Vector) nl.getAllNotes();
        else
            notes = (Vector) nl.getMarkedNotes();
        
//        Util.debug("No. of notes in noteList " + notes.size());
        //NotesVectorSorter.sort(notes);
		Collections.sort(notes);
		if (sortOrderDesc) {
			Collections.reverse(notes);		    
		}
        updateUI();
    }

    public void update(Vector ns) {
        notes = ns;
        // NotesVectorSorter.sort(notes);
		Collections.sort(notes);
		if (sortOrderDesc) {
			Collections.reverse(notes);		    
		}		
        updateUI();
    }

    public INote getNote(int index){
        return (INote) notes.get(index);
    }
    
    void invertSortOrder() {
        sortOrderDesc = !sortOrderDesc;
    }


    /*$Id: NotesList.java,v 1.9 2005/05/05 16:19:16 ivanrise Exp $*/
public class NotesListModel extends AbstractListModel {

        public NotesListModel() {
            update();
        }

        public Object getElementAt(int i) {
            INote note = (INote)notes.get(i);
            return note.getDate().getShortDateString() + " " + note.getTitle();
        }

        public int getSize() {
            return notes.size();
        }

    }

    ImageIcon bookmarkIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/star8.png"));

    public ListCellRenderer getCellRenderer() {
        return new DefaultListCellRenderer()  {

     public Component getListCellRendererComponent(
       JList list,
       Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // the list and the cell have the focus
     {
         JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
         String s = value.toString();
         label.setText(s);
         //Note currentNote = CurrentProject.getNoteList().getActiveNote();
		 INote currentNote = CurrentNote.get();
         if (currentNote != null) {
            if (getNote(index).getId().equals(currentNote.getId()))
                label.setFont(label.getFont().deriveFont(Font.BOLD));
         }
         if (getNote(index).isMarked())
            label.setIcon(bookmarkIcon);
         //setIcon();
       /*if (isSelected) {
             setBackground(list.getSelectionBackground());
           setForeground(list.getSelectionForeground());
       }
         else {
           setBackground(list.getBackground());
           setForeground(list.getForeground());
       }
       setEnabled(list.isEnabled());
       setFont(list.getFont());
         setOpaque(true);*/
         label.setToolTipText(s);
         return label;
     }
    };

 }


}