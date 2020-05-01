package domain;

public class ReportPartial extends Report {
    private int numberReport;
    private String resultsObtained;
    private int hoursCovered;
    private String observations;

    public ReportPartial (String activities) {
        super (activities);
    }

    public int getNumberReport () {
        return numberReport;
    }

    public void setNumberReport (int numberReport) {
        this.numberReport = numberReport;
    }

    public String getResultsObtained () {
        return resultsObtained;
    }

    public void setResultsObtained (String resultsObtained) {
        this.resultsObtained = resultsObtained;
    }

    public int getHoursCovered() {
        return hoursCovered;
    }

    public void setHoursCovered (int hoursCovered) {
        this.hoursCovered = hoursCovered;
    }

    public String getObservations () {
        return observations;
    }

    public void setObservations (String observations) {
        this.observations = observations;
    }

}