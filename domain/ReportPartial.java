package domain;

public class ReportPartial extends Report {
    private int numberReport;
    private String resultsObtained;
    private int hoursCovered;
    private String observations;
    private String objective;
    private String methodology;

    public ReportPartial() {

    }

    public int getNumberReport () {
        return numberReport;
    }

    public void setNumberReport(int numberReport) {
        this.numberReport = numberReport;
    }

    public String getResultsObtained () {
        return resultsObtained;
    }

    public void setResultsObtained(String resultsObtained) {
        this.resultsObtained = resultsObtained;
    }

    public int getHoursCovered() {
        return hoursCovered;
    }

    public void setHoursCovered(int hoursCovered) {
        this.hoursCovered = hoursCovered;
    }

    public String getObservations () {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getObjective() { return objective; }

    public void setObjective(String objective) { this.objective = objective; }

    public String getMethodology() { return methodology; }

    public void setMethodology(String methodology) { this.methodology = methodology; }

}