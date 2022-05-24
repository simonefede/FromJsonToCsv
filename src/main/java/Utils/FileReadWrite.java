package Utils;

import Models.Configuration;
import Models.Event;
import Models.Match;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class FileReadWrite {

    public static Configuration readFromFile(String nomeFile) {
        FileReader r;
        BufferedReader br;
        Configuration con = new Configuration();
        try {
            r = new FileReader(nomeFile);
            br = new BufferedReader(r);
            String riga;
            while((riga = br.readLine())!=null) {
                String[] fields = riga.split(";");
                if(fields[0].compareTo("Path")==0) {
                    con.setPath(fields[1]);
                } else if(fields[0].compareTo("Path_destination")==0) {
                    con.setPath_destination(fields[1]);
                } else if(fields[0].compareTo("Competition_id")==0) {
                    con.setCompetition_id(fields[1]);
                } else if(fields[0].compareTo("Competition_season")==0) {
                    con.setCompetition_season(fields[1]);
                } else if(fields[0].compareTo("Team")==0) {
                    con.setTeam(fields[1]);
                }
            }
            br.close();
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void writeToCsv(List<Match> matchList, Map<Integer, List<Event>> eventMapTypePass, Map<Integer, Map<Integer, Map<Integer, Integer>>> aggregatePlayer, String header, String path_destination, String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(path_destination+"\\"+fileName+".csv");
        writer.println(header);
        if(matchList != null){
            for (Match sample : matchList) {
                writer.println(sample.toString());
            }
        }
        if(matchList == null && eventMapTypePass != null && aggregatePlayer == null){
            for(Integer key : eventMapTypePass.keySet()){
                for (Event sample : eventMapTypePass.get(key)) {
                    writer.println(sample.toString());
                }
            }
        }
        if(matchList == null && eventMapTypePass != null && aggregatePlayer != null){
            for(Integer key : eventMapTypePass.keySet()){
                if(aggregatePlayer.containsKey(key)){
                    for(Event ev : eventMapTypePass.get(key).stream().filter(Event::isPass_completed).toList()){
                        if(aggregatePlayer.get(key).containsKey(ev.getPlayer().getId())){
                            if(aggregatePlayer.get(key).get(ev.getPlayer().getId()).containsKey(ev.getPass().getRecipient().getId())){
                                writer.println(ev.getMatch_id()+","+ev.getPlayer().getId()+","+ev.getPlayer().getName()+","+ev.getPlayer_starting_role().getName()+","+ev.getPass().getRecipient().getId()+","+ev.getPass().getRecipient().getName()+","+ev.getRecipient_starting_role().getName()+","+aggregatePlayer.get(key).get(ev.getPlayer().getId()).get(ev.getPass().getRecipient().getId()));
                                aggregatePlayer.get(key).get(ev.getPlayer().getId()).remove(ev.getPass().getRecipient().getId());
                            }
                        }
                    }
                }
            }
        }
        writer.close();
    }
}
