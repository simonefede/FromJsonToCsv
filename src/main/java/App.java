import java.util.*;

import Models.*;
import Utils.GetDataStruct;

import static Utils.FileReadWrite.*;
import static Utils.ParseJson.*;

public class App {
    static final long startTime = System.currentTimeMillis();
    static final String headerTable1 = "match_id,score,team_id_home,team_name_home,team_id_away,team_name_away";
    static final String headerTable2 = "match_id,event_id,player_id,player_name,player_starting_role,recipient_player_id,recipient_player_name,recipient_starting_role,pass_completed";
    static final String headerTable3 = "match_id,player_id,player_name,starting_role,recipient_player_id,recipient_player_name,recipient_ starting_role,total_passes_to";

    public static void main(String[] args) throws Exception {

        Configuration con = readFromFile("src/main/resources/Configuration.txt");

        List<Match> matchList = parseMatches(con);
        if(matchList != null){
            if(!GetDataStruct.setTeamIdConfiguration(matchList.get(0), con)){
                exitWithError("Error getting team_id");
            }
            System.out.println("# match to analyze: "+matchList.size());
        } else {
            exitWithError("Error getting matchList");
        }

        writeToCsv(matchList, null, null, headerTable1, con.getPath_destination(), "table1");

        List<Event> eventList = new LinkedList<>();

        for(Match mt : matchList){
            if(mt.getMatch_id() != null) {
                List<Event> eventTemp = parseEvents(con, mt.getMatch_id());
                if(eventTemp!=null){
                    eventList.addAll(eventTemp);
                }
            }
        }

        System.out.println("# events to analyze: "+eventList.size());

        Map<Integer, List<Event>> eventMapTypePass = GetDataStruct.generateEventTypePass(eventList, con);
        System.out.println("# events with type = pass to analyze: "+eventMapTypePass.values().stream()
                                                                                    .mapToInt(Collection::size)
                                                                                    .sum());
        Map<Integer, Map<Integer, sObject>> eventMapTypeStartingXI = GetDataStruct.generateEventMapTypeStartingXI(eventList, con);

        GetDataStruct.setPassCompletedAndPosition(eventMapTypePass, eventMapTypeStartingXI);

        writeToCsv(null, eventMapTypePass, null, headerTable2, con.getPath_destination(), "table2");

        Map<Integer, Map<Integer, Map<Integer, Integer>>> aggregatePlayer = GetDataStruct.aggregatePlayerForMatchAndCountPass(eventMapTypePass);

        writeToCsv(null, eventMapTypePass, aggregatePlayer, headerTable3, con.getPath_destination(), "table3");

        exitWithoutError();
    }

    private static void exitWithError(String error){
        System.out.println(error);
        System.exit(1);
    }

    private static void exitWithoutError(){
        long endTime = System.currentTimeMillis();
        long seconds = (endTime - startTime);
        System.out.println("Program run in " + seconds + " milliseconds");
        System.exit(0);
    }

}
