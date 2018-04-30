/**
 * CurrentProject.java
 * Created on 13.02.2003, 13:16:52 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *
 */
package main.java.memoranda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import main.java.memoranda.interfaces.INoteList;
import main.java.memoranda.interfaces.IProject;
import main.java.memoranda.interfaces.IProjectListener;
import main.java.memoranda.interfaces.IResourcesList;
import main.java.memoranda.interfaces.ITaskList;
import main.java.memoranda.ui.AppFrame;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Storage;

/**
 *
 */
/*$Id: CurrentProject.java,v 1.6 2005/12/01 08:12:26 alexeya Exp $*/
public class CurrentProject {

    private static IProject _project = null;
    private static ITaskList _tasklist = null;
    private static INoteList _notelist = null;
    private static IResourcesList _resources = null;
    private static Vector projectListeners = new Vector();

        
    static {
        String prjId = (String)Context.get("LAST_OPENED_PROJECT_ID");
        if (prjId == null) {
            prjId = "__default";
            Context.put("LAST_OPENED_PROJECT_ID", prjId);
        }
        //ProjectManager.init();
        _project = ProjectManager.getProject(prjId);
		
		if (_project == null) {
			// alexeya: Fixed bug with NullPointer when LAST_OPENED_PROJECT_ID
			// references to missing project
			_project = ProjectManager.getProject("__default");
			if (_project == null) 
				_project = (IProject)ProjectManager.getActiveProjects().get(0);						
            Context.put("LAST_OPENED_PROJECT_ID", _project.getID());
			
		}		
		
        _tasklist = CurrentStorage.get().openTaskList(_project);
        _notelist = CurrentStorage.get().openNoteList(_project);
        _resources = CurrentStorage.get().openResourcesList(_project);
        AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();                                               
            }
        });
    }
        

    public static IProject get() {
        return _project;
    }

    public static ITaskList getTaskList() {
            return _tasklist;
    }

    public static INoteList getNoteList() {
            return _notelist;
    }
    
    public static IResourcesList getResourcesList() {
            return _resources;
    }

    public static void set(IProject project) {
        if (project.getID().equals(_project.getID())) return;
        ITaskList newtasklist = CurrentStorage.get().openTaskList(project);
        INoteList newnotelist = CurrentStorage.get().openNoteList(project);
        IResourcesList newresources = CurrentStorage.get().openResourcesList(project);
        notifyListenersBefore(project, newnotelist, newtasklist, newresources);
        _project = project;
        _tasklist = newtasklist;
        _notelist = newnotelist;
        _resources = newresources;
        notifyListenersAfter();
        Context.put("LAST_OPENED_PROJECT_ID", project.getID());
    }

    public static void addProjectListener(IProjectListener pl) {
        projectListeners.add(pl);
    }

    public static Collection getChangeListeners() {
        return projectListeners;
    }

    private static void notifyListenersBefore(IProject project, INoteList nl, ITaskList tl, IResourcesList rl) {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((IProjectListener)projectListeners.get(i)).projectChange(project, nl, tl, rl);
            /*DEBUGSystem.out.println(projectListeners.get(i));*/
        }
    }
    
    private static void notifyListenersAfter() {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((IProjectListener)projectListeners.get(i)).projectWasChanged();            
        }
    }

    public static void save() {
        Storage storage = CurrentStorage.get();

        storage.storeNoteList(_notelist, _project);
        storage.storeTaskList(_tasklist, _project); 
        storage.storeResourcesList(_resources, _project);
        storage.storeProjectManager();
    }
    
    public static void free() {
        _project = null;
        _tasklist = null;
        _notelist = null;
        _resources = null;
    }
}
