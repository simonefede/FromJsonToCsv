package Models;

public class Event {
    
    private Integer match_id;
    private String id;
    private sObject type;
    private sObject possession_team;
    private sObject play_pattern;
    private sObject team;
    private sObject player;
    private sObject position;
    private Double[] location;
    private Double duration;
    private String[] related_events;
    private Tactic tactics;
    private Pass pass;
    private int period;
    private int minute;
    private int second;
    private Substitution substitution;
    private sObject player_starting_role;
    private sObject recipient_starting_role;
    private Boolean pass_completed;

    public Integer getMatch_id() {
        return this.match_id;
    }

    public void setMatch_id(Integer match_id) {
        this.match_id = match_id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public sObject getType() {
        return this.type;
    }

    public void setType(sObject type) {
        this.type = type;
    }

    public sObject getPossession_team() {
        return this.possession_team;
    }

    public void setPossession_team(sObject possession_team) {
        this.possession_team = possession_team;
    }

    public sObject getPlay_pattern() {
        return this.play_pattern;
    }

    public void setPlay_pattern(sObject play_pattern) {
        this.play_pattern = play_pattern;
    }

    public sObject getTeam() {
        return this.team;
    }

    public void setTeam(sObject team) {
        this.team = team;
    }

    public sObject getPlayer() {
        return this.player;
    }

    public void setPlayer(sObject player) {
        this.player = player;
    }

    public sObject getPosition() {
        return this.position;
    }

    public void setPosition(sObject position) {
        this.position = position;
    }

    public Double[] getLocation() {
        return this.location;
    }

    public void setLocation(Double[] location) {
        this.location = location;
    }

    public Double getDuration() {
        return this.duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String[] getRelated_events() {
        return this.related_events;
    }

    public void setRelated_events(String[] related_events) {
        this.related_events = related_events;
    }

    public Tactic getTactics() {
        return this.tactics;
    }

    public void setTactics(Tactic tactics) {
        this.tactics = tactics;
    }

    public Pass getPass() {
        return this.pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public int getPeriod() { return period; }

    public void setPeriod(int period) { this.period = period; }

    public int getMinute() { return minute; }

    public void setMinute(int minute) { this.minute = minute; }

    public int getSecond() { return second; }

    public void setSecond(int second) { this.second = second; }

    public Substitution getSubstitution() { return substitution; }

    public void setSubstitution(Substitution substitution) { this.substitution = substitution; }

    public sObject getPlayer_starting_role() {
        return this.player_starting_role;
    }

    public void setPlayer_starting_role(sObject player_starting_role) {
        this.player_starting_role = player_starting_role;
    }

    public sObject getRecipient_starting_role() {
        return this.recipient_starting_role;
    }

    public void setRecipient_starting_role(sObject recipient_starting_role) {
        this.recipient_starting_role = recipient_starting_role;
    }

    public Boolean isPass_completed() {
        return this.pass_completed;
    }

    public Boolean getPass_completed() {
        return this.pass_completed;
    }

    public void setPass_completed(Boolean pass_completed) {
        this.pass_completed = pass_completed;
    }

    @Override
    public String toString(){
        StringBuilder dataBuilder = new StringBuilder();
        appendFieldValue(dataBuilder, String.valueOf(match_id));
        appendFieldValue(dataBuilder, id);
        appendFieldValue(dataBuilder, String.valueOf(player.getId()));
        appendFieldValue(dataBuilder, player.getName());
        appendFieldValue(dataBuilder, player_starting_role.getName());
        appendFieldValue(dataBuilder, pass_completed && pass.getRecipient() != null && pass.getRecipient().getId() != null ? String.valueOf(pass.getRecipient().getId()) : "");
        appendFieldValue(dataBuilder, pass_completed && pass.getRecipient() != null && pass.getRecipient().getName() != null ? pass.getRecipient().getName() : "");
        appendFieldValue(dataBuilder, pass_completed && pass.getRecipient() != null && recipient_starting_role.getName() != null ? recipient_starting_role.getName() : "");
        appendFieldValue(dataBuilder, String.valueOf(pass_completed));
        return dataBuilder.toString();
    }

    private void appendFieldValue(StringBuilder dataBuilder, String fieldValue) {
        if(fieldValue != null) {
            dataBuilder.append(fieldValue).append(",");
        } else {
            dataBuilder.append("").append(",");
        }
    }

}
