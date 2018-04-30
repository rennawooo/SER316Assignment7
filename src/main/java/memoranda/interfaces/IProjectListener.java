package main.java.memoranda.interfaces;

/*$Id: ProjectListener.java,v 1.3 2004/01/30 12:17:41 alexeya Exp $*/
public interface IProjectListener {
  void projectChange(IProject prj, INoteList nl, ITaskList tl, IResourcesList rl);
  void projectWasChanged();
}