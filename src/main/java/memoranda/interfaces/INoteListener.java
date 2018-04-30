package main.java.memoranda.interfaces;

public interface INoteListener {
  void noteChange(INote note, boolean toSaveCurrentNote);
}
