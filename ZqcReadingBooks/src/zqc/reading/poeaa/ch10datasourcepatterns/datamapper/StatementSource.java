package zqc.reading.poeaa.ch10datasourcepatterns.datamapper;

public interface StatementSource {

    String sql();
    Object[] parameters();
}