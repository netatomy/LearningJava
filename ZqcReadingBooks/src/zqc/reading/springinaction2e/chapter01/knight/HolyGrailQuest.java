package zqc.reading.springinaction2e.chapter01.knight;


public class HolyGrailQuest implements Quest {

    public HolyGrailQuest() {
    }

    @Override
    public HolyGrail embark() throws QuestFailedException {

        return new HolyGrail();
    }

}
