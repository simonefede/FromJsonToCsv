package Models;

public class Configuration {
    
    private String path;
    private String path_destination;
    private String competition_id;
    private String competition_season;
    private String team;
    private Integer team_id;

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath_destination() {
        return this.path_destination;
    }

    public void setPath_destination(String path_destination) {
        this.path_destination = path_destination;
    }

    public String getCompetition_id() {
        return this.competition_id;
    }

    public void setCompetition_id(String competition_id) {
        this.competition_id = competition_id;
    }

    public String getCompetition_season() {
        return this.competition_season;
    }

    public void setCompetition_season(String competition_season) {
        this.competition_season = competition_season;
    }

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Integer getTeam_id() { return team_id; }

    public void setTeam_id(Integer team_id) { this.team_id = team_id; }

}
