import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class FileHandler {
    public static ArrayList<RoadVolume> loadVolumeData(String filename) throws FileNotFoundException, ParseException {
        ArrayList<RoadVolume> volumeData = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        SimpleDateFormat volumeDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        // Skip the header line
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",");

            Date date = volumeDateFormat.parse(values[0]);  // Parsing as MM/dd/yyyy for volume data
            String time = values[1];
            int volumeSensor1 = Integer.parseInt(values[2]);
            int volumeSensor2 = Integer.parseInt(values[3]);
            int volumeSensor3 = Integer.parseInt(values[4]);
            int volumeSensor4 = Integer.parseInt(values[5]);
            RoadVolume volume = new RoadVolume(date, time, volumeSensor1, volumeSensor2, volumeSensor3, volumeSensor4);
            volumeData.add(volume);
        }

        scanner.close();
        return volumeData;
    }

    public static ArrayList<RoadSpeed> loadSpeedData(String filename) throws FileNotFoundException, ParseException {
        ArrayList<RoadSpeed> speedData = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        SimpleDateFormat speedDateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Updated to match yyyy-MM-dd format for speed data

        // Skip the header line
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",");
            Date date = speedDateFormat.parse(values[0]);  // Parsing as yyyy-MM-dd for speed data
            String time = values[1];
            double speedSensor1 = Double.parseDouble(values[2]);
            double speedSensor2 = Double.parseDouble(values[3]);
            RoadSpeed speed = new RoadSpeed(date, time, speedSensor1, speedSensor2);
            speedData.add(speed);
        }

        scanner.close();
        return speedData;
    }

    public static void writeRoadSectionData(ArrayList<RoadSection> sections) {
        try (FileWriter writer = new FileWriter("Road_Section_Data.csv")) {
            writer.write("Date,Time,Volume_Sensor_2003,Volume_Sensor_2004,Volume_Sensor_2005,Volume_Sensor_2006,Speed_Sensor_2282,Speed_Sensor_2293,Volume_Total,Volume_Avg,Speed_Avg\n");
            for (RoadSection section : sections) {
                writer.write(section.getFileData() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
