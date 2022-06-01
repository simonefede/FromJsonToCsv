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
        for(Event ev : filterList(eventList, 30, con.getTeam_id())){
            List<Event> temp;
            if(eventMapTypePass.containsKey(ev.getMatch_id())){
                temp = eventMapTypePass.get(ev.getMatch_id());
            } else {
                temp = new LinkedList<>();
            }
            temp.add(ev);
            eventMapTypePass.put(ev.getMatch_id(), temp);
        }
        return eventMapTypePass;
    }

    public static Map<Integer, Map<Integer, sObject>> generateEventMapTypeStartingXI(List<Event> eventList, Configuration con){
        Map<Integer, Map<Integer, sObject>> eventMapTypeStartingXI = new HashMap<>();
        for(Event ev : filterList(eventList, 35, con.getTeam_id())){
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
        List<Event> temp = eventList.stream().filter(event -> event.getType().getId().equals(filterType)).toList();
        if(filterType == 30) return temp.stream().filter(event -> event.getTeam().getId().equals(filterTeam)).collect(Collectors.toList());
        else if(filterType == 35) return temp.stream().filter(event -> event.getTeam().getId().equals(filterTeam)).collect(Collectors.toList());
        return null;
    }
}
