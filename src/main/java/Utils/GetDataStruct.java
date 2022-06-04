package Utils;

import Models.*;

import java.util.*;
import java.util.stream.Collectors;

public class GetDataStruct {

    public static Boolean setTeamIdConfiguration(Match mt, Configuration con){
        if(mt.getHome_team().getHome_team_name().toUpperCase().contains(con.getTeam().toUpperCase())){
            con.setTeam_id(mt.getHome_team().getHome_team_id());
            return true;
        } else if(mt.getAway_team().getAway_team_name().toUpperCase().contains(con.getTeam().toUpperCase())){
            con.setTeam_id(mt.getAway_team().getAway_team_id());
            return true;
        }
        return false;
    }

    public static Map<Integer, List<Event>> generateEventTypePass(List<Event> eventList, Configuration con){
        Map<Integer, List<Event>> eventMapTypePass = new HashMap<>();
        for(Event ev : filterList(eventList, con.getId_event_type_pass(), con.getTeam_id())){
            populateMapListEvent(eventMapTypePass, ev);
        }
        return eventMapTypePass;
    }

    public static Map<Integer, Map<Integer, sObject>> generateEventMapTypeStartingXI(List<Event> eventList, Configuration con){
        Map<Integer, Map<Integer, sObject>> eventMapTypeStartingXI = new HashMap<>();
        for(Event ev : filterList(eventList, con.getId_event_type_starting_xi(), con.getTeam_id())){
            Map<Integer, sObject> temp;
            if(eventMapTypeStartingXI.containsKey(ev.getMatch_id())){
                temp = eventMapTypeStartingXI.get(ev.getMatch_id());
                for(Lineup lin : ev.getTactics().getLineup()){
                    if(!temp.containsKey(lin.getPlayer().getId())){
                        temp.put(lin.getPlayer().getId(), lin.getPosition());
                    }
                }
            } else {
                temp = new HashMap<>();
                for(Lineup lin : ev.getTactics().getLineup()){
                    temp.put(lin.getPlayer().getId(), lin.getPosition());
                }
            }
            eventMapTypeStartingXI.put(ev.getMatch_id(), temp);
        }
        return eventMapTypeStartingXI;
    }

    public static Map<Integer, List<Event>> generateEventMapTypeSubstitution(List<Event> eventList, Configuration con){
        Map<Integer, List<Event>> eventMapTypeSubstitution = new HashMap<>();
        for(Event ev : filterList(eventList, con.getId_event_type_substitution(), con.getTeam_id())){
            populateMapListEvent(eventMapTypeSubstitution, ev);
        }
        return eventMapTypeSubstitution;
    }

    public static Map<Integer, List<Event>> generateEventMapTypeRedCard(List<Event> eventList, Configuration con){
        Map<Integer, List<Event>> eventMapTypeRedCard = new HashMap<>();
        for(Event ev : filterList(eventList, con.getId_event_type_red_card(), con.getTeam_id())){
            populateMapListEvent(eventMapTypeRedCard, ev);
        }
        return eventMapTypeRedCard;
    }

    public static Map<Integer, List<Event>> generateEventMapTypeHalfEnd(List<Event> eventList, Configuration con){
        Map<Integer, List<Event>> eventMapTypeHalfEnd = new HashMap<>();
        for(Event ev : filterList(eventList, con.getId_event_type_half_end(), con.getTeam_id())){
            populateMapListEvent(eventMapTypeHalfEnd, ev);
        }
        return eventMapTypeHalfEnd;
    }

    private static void populateMapListEvent(Map<Integer, List<Event>> eventMapTypeRedCard, Event ev) {
        List<Event> temp;
        if(eventMapTypeRedCard.containsKey(ev.getMatch_id())){
            temp = eventMapTypeRedCard.get(ev.getMatch_id());
        } else {
            temp = new LinkedList<>();
        }
        temp.add(ev);
        eventMapTypeRedCard.put(ev.getMatch_id(), temp);
    }

    public static Map<Integer, Map<Integer, Map<Integer, Integer>>> aggregatePlayerForMatchAndCountPass(Map<Integer, List<Event>> eventMapTypePass){
        Map<Integer, Map<Integer, Map<Integer, Integer>>> aggregatePlayer = new HashMap<>();
        //IdMatch, {IdPlayer, {IdRecipient, countPass}}
        for(Integer i : eventMapTypePass.keySet()){
            if(!aggregatePlayer.containsKey(i)){
                Map<Integer, Map<Integer, Integer>> temp1 = new HashMap<>();
                for(Event ev : eventMapTypePass.get(i).stream().filter(Event::isPass_completed).toList()){
                    Map<Integer, Integer> temp;
                    if(temp1.containsKey(ev.getPlayer().getId())){
                        temp = temp1.get(ev.getPlayer().getId());
                        if(temp.containsKey(ev.getPass().getRecipient().getId())){
                            Integer pass = temp.get(ev.getPass().getRecipient().getId());
                            pass++;
                            temp.put(ev.getPass().getRecipient().getId(), pass);
                        } else {
                            temp.put(ev.getPass().getRecipient().getId(), 1);
                        }
                    } else {
                        temp = new HashMap<>();
                        temp.put(ev.getPass().getRecipient().getId(), 1);
                    }
                    temp1.put(ev.getPlayer().getId(), temp);
                }
                aggregatePlayer.put(i, temp1);
            }
        }
        return aggregatePlayer;
    }

    public static void setPassCompletedAndPosition(Map<Integer, List<Event>> eventMapTypePass, Map<Integer, Map<Integer, sObject>> eventMapTypeStartingXI){
        for(Integer key : eventMapTypePass.keySet()){
            for(Event ev : eventMapTypePass.get(key)){
                if(ev.getPass() != null && ev.getPass().getRecipient() != null && // pass completed
                        (ev.getPass().getOutcome() == null || (
                                ev.getPass().getOutcome() != null && ev.getPass().getOutcome().getId() != 9 &&       // interception
                                        ev.getPass().getOutcome().getId() != 75 &&      // pass out
                                        ev.getPass().getOutcome().getId() != 76 &&      // pass offside
                                        ev.getPass().getOutcome().getId() != 77))){     // unknown

                    if(eventMapTypeStartingXI.containsKey(ev.getMatch_id())){
                        playerInStartXI(eventMapTypeStartingXI, ev);
                        if(eventMapTypeStartingXI.get(ev.getMatch_id()).containsKey(ev.getPass().getRecipient().getId())){
                            ev.setRecipient_starting_role(eventMapTypeStartingXI.get(ev.getMatch_id()).get(ev.getPass().getRecipient().getId()));
                        } else {
                            sObject s = new sObject();
                            s.setName("Not in StartingXI");
                            ev.setRecipient_starting_role(s);
                        }
                    }
                    ev.setPass_completed(true);
                } else { // pass not completed
                    if(eventMapTypeStartingXI.containsKey(ev.getMatch_id())){
                        playerInStartXI(eventMapTypeStartingXI, ev);
                    }
                    ev.setPass_completed(false);
                }
            }
        }
    }

    public static void setMinutesAndSecondsInMatch(List<Match> matchList, Map<Integer, List<Event>> eventMapTypeHalfEnd){
        for(Match mt : matchList){
            if(eventMapTypeHalfEnd.containsKey(mt.getMatch_id())){
                for(Event ev : eventMapTypeHalfEnd.get(mt.getMatch_id())){
                    if(ev.getPeriod()==1){
                        mt.setEnd_first_time_minutes(ev.getMinute());
                        mt.setEnd_first_time_seconds(ev.getSecond());
                        mt.setEnd_first_time(mt.getEnd_first_time_minutes()+":"+String.format("%02d", mt.getEnd_first_time_seconds()));
                    } else if(ev.getPeriod()==2){
                        mt.setEnd_second_time_minutes(ev.getMinute());
                        mt.setEnd_second_time_seconds(ev.getSecond());
                        mt.setEnd_second_time(mt.getEnd_second_time_minutes()+":"+String.format("%02d", mt.getEnd_second_time_seconds()));
                    }
                    int total_minutes;
                    int total_seconds;
                    if(mt.getEnd_first_time_seconds() + mt.getEnd_second_time_seconds() > 60) {
                        total_minutes = mt.getEnd_first_time_minutes()+mt.getEnd_second_time_minutes()-44;
                        total_seconds = mt.getEnd_first_time_seconds() + mt.getEnd_second_time_seconds()-60;
                    } else {
                        total_minutes = mt.getEnd_first_time_minutes()+mt.getEnd_second_time_minutes()-45;
                        total_seconds = mt.getEnd_first_time_seconds() + mt.getEnd_second_time_seconds();
                    }
                    mt.setTotal_minutes(total_minutes+":"+String.format("%02d", total_seconds));
                }
            }
        }
    }

    private static void playerInStartXI(Map<Integer, Map<Integer, sObject>> eventMapTypeStartingXI, Event ev) {
        if(eventMapTypeStartingXI.get(ev.getMatch_id()).containsKey(ev.getPlayer().getId())){
            ev.setPlayer_starting_role(eventMapTypeStartingXI.get(ev.getMatch_id()).get(ev.getPlayer().getId()));
        } else {
            sObject s = new sObject();
            s.setName("Not in StartingXI");
            ev.setPlayer_starting_role(s);
        }
    }

    private static List<Event> filterList(List<Event> eventList, Integer filterType, Integer filterTeam){
        return eventList.stream().filter(event -> event.getType().getId().equals(filterType)).toList()
                .stream().filter(event -> event.getTeam().getId().equals(filterTeam)).collect(Collectors.toList());
    }
}
