public class KNNObject implements Comparable<KNNObject> {
    private String decisionAttribjute;
    private Double euklidesDistance;

    public KNNObject(String decisionAttribjute, Double euklidesDistance) {
        this.decisionAttribjute = decisionAttribjute;
        this.euklidesDistance = euklidesDistance;
    }

    public String getDecisionAttribjute() {
        return decisionAttribjute;
    }

    public void setDecisionAttribjute(String decisionAttribjute) {
        this.decisionAttribjute = decisionAttribjute;
    }

    public Double getEuklidesDistance() {
        return euklidesDistance;
    }

    public void setEuklidesDistance(Double euklidesDistance) {
        this.euklidesDistance = euklidesDistance;
    }

    @Override
    public int compareTo(KNNObject o) {
        return (int)(this.euklidesDistance - o.euklidesDistance);
    }
}
