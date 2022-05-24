package Models;

public class Pass {
    
    private sObject recipient;
    private Double length;
    private Double angle;
    private sObject height;
    private Double[] end_location;
    private sObject outcome;
    private sObject body_part;

    public sObject getRecipient() {
        return this.recipient;
    }

    public void setRecipient(sObject recipient) {
        this.recipient = recipient;
    }

    public Double getLength() {
        return this.length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getAngle() {
        return this.angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public sObject getHeight() {
        return this.height;
    }

    public void setHeight(sObject height) {
        this.height = height;
    }

    public Double[] getEnd_location() {
        return this.end_location;
    }

    public void setEnd_location(Double[] end_location) {
        this.end_location = end_location;
    }

    public sObject getOutcome() {
        return this.outcome;
    }

    public void setOutcome(sObject outcome) {
        this.outcome = outcome;
    }

    public sObject getBody_part() {
        return this.body_part;
    }

    public void setBody_part(sObject body_part) {
        this.body_part = body_part;
    }

}
