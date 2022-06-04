package Models;

public class Match {
    
    private Integer match_id;
    private Home_Team home_team;
    private Away_Team away_team;
    private int home_score;
    private int away_score;
    private String score;
    private int end_first_time_minutes;
    private int end_first_time_seconds;
    private String end_first_time;
    private String end_second_time;
    private int end_second_time_seconds;
    private int end_second_time_minutes;
    private String total_minutes;

    public Integer getMatch_id() {
        return this.match_id;
    }

    public void setMatch_id(Integer match_id) {
        this.match_id = match_id;
    }

    public Home_Team getHome_team() {
        return this.home_team;
    }

    public void setHome_team(Home_Team home_team) {
        this.home_team = home_team;
    }

    public Away_Team getAway_team() {
        return this.away_team;
    }

    public void setAway_team(Away_Team away_team) {
        this.away_team = away_team;
    }

    public int getHome_score() {
        return this.home_score;
    }

    public void setHome_score(int home_score) {
        this.home_score = home_score;
    }

    public int getAway_score() {
        return this.away_score;
    }

    public void setAway_score(int away_score) {
        this.away_score = away_score;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(Integer home_score, Integer away_score) {
        this.score = home_score+"-"+away_score;
    }

    public int getEnd_first_time_minutes() { return end_first_time_minutes; }

    public void setEnd_first_time_minutes(int end_first_time_minutes) { this.end_first_time_minutes = end_first_time_minutes; }
    public int getEnd_first_time_seconds() { return end_first_time_seconds; }

    public void setEnd_first_time_seconds(int end_first_time_seconds) { this.end_first_time_seconds = end_first_time_seconds; }

    public int getEnd_second_time_seconds() { return end_second_time_seconds; }

    public void setEnd_second_time_seconds(int end_second_time_seconds) { this.end_second_time_seconds = end_second_time_seconds; }

    public int getEnd_second_time_minutes() { return end_second_time_minutes; }

    public void setEnd_second_time_minutes(int end_second_time_minutes) { this.end_second_time_minutes = end_second_time_minutes; }
    public String getEnd_first_time() { return end_first_time; }

    public void setEnd_first_time(String end_first_time) { this.end_first_time = end_first_time; }

    public String getEnd_second_time() { return end_second_time; }

    public void setEnd_second_time(String end_second_time) { this.end_second_time = end_second_time; }

    public String getTotal_minutes(String total_minutes) { return total_minutes; }

    public void setTotal_minutes(String total_minutes) { this.total_minutes = total_minutes; }

    @Override
    public String toString(){
        StringBuilder dataBuilder = new StringBuilder();
        appendFieldValue(dataBuilder, String.valueOf(match_id));
        appendFieldValue(dataBuilder, score);
        appendFieldValue(dataBuilder, String.valueOf(home_team.getHome_team_id()));
        appendFieldValue(dataBuilder, home_team.getHome_team_name());
        appendFieldValue(dataBuilder, String.valueOf(away_team.getAway_team_id()));
        appendFieldValue(dataBuilder, away_team.getAway_team_name());
        appendFieldValue(dataBuilder, end_first_time);
        appendFieldValue(dataBuilder, end_second_time);
        appendFieldValue(dataBuilder, total_minutes);
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
