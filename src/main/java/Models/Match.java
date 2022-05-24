package Models;

public class Match {
    
    private Integer match_id;
    private Home_Team home_team;
    private Away_Team away_team;
    private int home_score;
    private int away_score;
    private String score;

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

    @Override
    public String toString(){
        StringBuilder dataBuilder = new StringBuilder();
        appendFieldValue(dataBuilder, String.valueOf(match_id));
        appendFieldValue(dataBuilder, score);
        appendFieldValue(dataBuilder, String.valueOf(home_team.getHome_team_id()));
        appendFieldValue(dataBuilder, home_team.getHome_team_name());
        appendFieldValue(dataBuilder, String.valueOf(away_team.getAway_team_id()));
        appendFieldValue(dataBuilder, away_team.getAway_team_name());
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
