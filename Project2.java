import java.io.*;

import java.text.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class Project2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<RoadVolume> volumeList = new ArrayList<>();
        ArrayList<RoadSpeed> speedList = new ArrayList<>();

        // Prompt for input files
        while (true) {
            System.out.println("Enter Path and Name of Volume and Speed Data File");
            String input = scanner.nextLine();
            String[] files = input.split(" ");

            if (files.length == 2) {
                String volumeFile = files[0];
                String speedFile = files[1];

                // Load Volume Data
                try {
                    volumeList = FileHandler.loadVolumeData(volumeFile);
                    System.out.println("Loading Volume Data");
                    System.out.println("Volume Data Loaded");
                    speedList = FileHandler.loadSpeedData(speedFile);
                    System.out.println("Loading Speed Data");
                    System.out.println("Speed Data Loaded");
                    break; // Exit loop if successful
                } catch (FileNotFoundException e) {
                	System.out.println("Loading Volume Data");
                    System.out.println("File not found: " + e.getMessage());
                } catch (ParseException e) {
                	System.out.println("Loading Volume Data");
                    System.out.println("Date parsing error: " + e.getMessage());
                } catch (NumberFormatException e) {
                	System.out.println("Loading Volume Data");
                    System.out.println("Number Parse Error: " + e.getMessage());
                }
            } 
        }
        // Create RoadSection objects
        ArrayList<RoadSection> roadSections = createRoadSections(volumeList, speedList);

        // Write Road Section Data
        FileHandler.writeRoadSectionData(roadSections);
        System.out.println("Road Section Data Created.");
    }


    public static ArrayList<RoadSection> createRoadSections(ArrayList<RoadVolume> volumeList, ArrayList<RoadSpeed> speedList) {
        ArrayList<RoadSection> roadSections = new ArrayList<>();
        SimpleDateFormat commonDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (RoadVolume volume : volumeList) {
            for (RoadSpeed speed : speedList) {
                // Convert dates to a common format before comparison
                String volumeDate = commonDateFormat.format(volume.getDate());
                String speedDate = commonDateFormat.format(speed.getDate());

                // Compare formatted dates and times
                if (volumeDate.equals(speedDate) && volume.getTime().equals(speed.getTime())) {
                    RoadSection section = new RoadSection(volume, speed);

                    // Calculate and set additional data
                    section.setVolumeTotal(section.calcVolumeTotal());
                    section.setVolumeAvg(section.calcVolumeAvg());
                    section.setSpeedAvg(section.calcSpeedAvg());

                    roadSections.add(section);
                    System.out.println("Match found, RoadSection created: " + section.getFileData());
                }
            }
        }

        // Debugging: Check if roadSections has any data
        System.out.println("Total RoadSections created: " + roadSections.size());
        return roadSections;
    }



       

}