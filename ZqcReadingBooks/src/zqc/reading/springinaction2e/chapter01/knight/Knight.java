package zqc.reading.springinaction2e.chapter01.knight;

public interface Knight {

    Object embarkOnQuest() throws QuestFailedException;

    String getName();

}