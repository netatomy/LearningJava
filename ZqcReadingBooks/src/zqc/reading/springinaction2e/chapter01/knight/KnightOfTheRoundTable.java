package zqc.reading.springinaction2e.chapter01.knight;

public class KnightOfTheRoundTable implements Knight {

    private String name;
    private Quest  quest;
    private Minstrel minstrel;

    public KnightOfTheRoundTable(String name) {

        this.name = name;
    }

    public void setQuest(Quest quest) {

        this.quest = quest;
    }
    
    public void setMinstrel(Minstrel minstrel){
        this.minstrel = minstrel;
    }

    // @Override
    public HolyGrail embarkOnQuest() throws QuestFailedException {
        HolyGrail grail = quest.embark();
        return grail;
    }

    @Override
    public String getName() {

        return name;
    }
}
