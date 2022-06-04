## Getting Started

Download the statsbomb repository containing the data to be analyzed, which you can find [here](https://github.com/statsbomb/open-data). 

Open the "Configuration.txt" present at the following path: `src/main/resources`

Proceed with the modification of the "Configuration.txt" file (the separator is the semicolon and is used for reading the file):

- `Path`: path where you downloaded data from statsbomb (double \\ as a folder separator)
- `Path_destination`: path where you want to receive the output csv
- `Competition_id`: id of the competition to be processed on (37 = FA Women's Super League) 
- `Competition_season`: id of the season to be processed on (90 = 2020/2021)
- `Team`: name of the team on which to perform the analyzes (not case sensitive)
- The last five fields: `Id_event_type_pass`, `Id_event_type_starting_xi`, `Id_event_type_substitution`, `Id_event_type_red_card`, `Id_event_type_half_end` will only need to be changed if they are changed by statsbomb

## Folder Structure

The workspace contains two folders by default, where:

- `src/main/java/`: the folder to maintain sources
- `src/main/resources/`: the folder for configurations

## How to run program

After modifying the "Configuration.txt" file run the "App.java" file present at the following path: `src/main/java`

