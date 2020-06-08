import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

/**
 * FileIO.java
 * @author Garrett McMichael
 * @version 1
 */
public class FileIO {
    private String dir = "src/";
    private String settingsName = "Settings.txt";


    /**
     * Returns a string containing all the values of the saved settings
     * @return A variable of type String
     */
    public String getSettings(){
        String settings = null;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dir + settingsName));
            settings = bufferedReader.readLine();
            bufferedReader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found while getting settings, creating new file.");
            createFile();
            settings = getSettings();
        }
        catch (Exception e){
            System.out.println("Error reading from file.");
            e.printStackTrace();
        }
        return settings;
    }

    /**
     * Writes the passed in settings to the specified file
     * @param settings A variable of type String
     */
    public void setSettings(String settings){
        String defaults = getDefaults();
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dir + settingsName));
            bufferedWriter.write(settings);
            bufferedWriter.newLine();
            bufferedWriter.write(defaults);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found while setting settings, creating new file.");
            createFile();
            setSettings(settings);
        }catch (Exception e){
            System.out.println("Error writing to file.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the default saved settings from the specified file
     * @return A variable of type String
     */
    public String getDefaults(){
        String defaults = null;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dir + settingsName));
            defaults = bufferedReader.readLine();
            defaults = bufferedReader.readLine();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found while getting defaults, creating defaults.");
            defaults = Canvas.getSettings();
        }
        catch (Exception e){
            System.out.println("Error reading from file.");
            e.printStackTrace();
        }
        return defaults;
    }

    /**
     * Creates a new file with the specified name and saves the settings from the Canvas class
     */
    private void createFile(){
        try {
            File file = new File(settingsName);
        } catch (Exception e){
            System.out.println("Error creating new file.");
            e.printStackTrace();
        }
        Canvas.saveSettings();
    }
}
