package Utils;

import Models.Configuration;
import Models.Event;
import Models.Match;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParseJson {
    public static List<Match> parseMatches(Configuration con) {
        try {
            JsonArray array = JsonParser.parseReader(new FileReader(con.getPath()+"\\data\\matches\\"+con.getCompetition_id()+"\\"+con.getCompetition_season()+".json")).getAsJsonArray();
            Gson gson = new Gson();
            Type matchListType = new TypeToken<ArrayList<Match>>(){}.getType();
            List<Match> temp = gson.fromJson(array.toString(), matchListType);
            for(Match mt : temp){
                mt.setScore(mt.getHome_score(), mt.getAway_score());
            }
            return temp.stream().filter(match -> match.getHome_team().getHome_team_name().toUpperCase().contains(con.getTeam().toUpperCase()) || match.getAway_team().getAway_team_name().toUpperCase().contains(con.getTeam().toUpperCase())).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Event> parseEvents(Configuration con, Integer match_id) {
        try {
            JsonArray array = JsonParser.parseReader(new FileReader(con.getPath()+"\\data\\events\\"+match_id+".json")).getAsJsonArray();
            Gson gson = new Gson();
            Type eventListType = new TypeToken<ArrayList<Event>>(){}.getType();
            List<Event> temp = gson.fromJson(array.toString(), eventListType);
            for(Event ev : temp){
                ev.setMatch_id(match_id);
            }
            return temp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
