package Models;

public class Configuration {
    
    private String path;
    private String path_destination;
    private String competition_id;
    private String competition_season;
    private String team;
    private Integer team_id;
    private Integer Id_event_type_pass;
    private Integer Id_event_type_starting_xi;
    private Integer Id_event_type_substitution;
    private Integer Id_event_type_red_card;
    private Integer Id_event_type_half_end;

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

    public Integer getId_event_type_pass() { return Id_event_type_pass; }

    public void setId_event_type_pass(Integer id_event_type_pass) {this.Id_event_type_pass = id_event_type_pass; }

    public Integer getId_event_type_starting_xi() { return Id_event_type_starting_xi; }

    public void setId_event_type_starting_xi(Integer id_event_type_starting_xi) { this.Id_event_type_starting_xi = id_event_type_starting_xi; }

    public Integer getId_event_type_substitution() { return Id_event_type_substitution; }

    public void setId_event_type_substitution(Integer id_event_type_substitution) { this.Id_event_type_substitution = id_event_type_substitution; }

    public Integer getId_event_type_red_card() { return Id_event_type_red_card; }

    public void setId_event_type_red_card(Integer id_event_type_red_card) { this.Id_event_type_red_card = id_event_type_red_card; }

    public Integer getId_event_type_half_end() { return Id_event_type_half_end; }

    public void setId_event_type_half_end(Integer Id_event_type_half_end) { this.Id_event_type_half_end = Id_event_type_half_end; }
}
