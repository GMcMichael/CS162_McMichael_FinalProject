import processing.core.PApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerListModel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * UserInterface.java
 * @author Garrett McMichael
 * @version 1
 */
public class UserInterface {
    private final static String[] OPTIONS = new String[] {"--present", "Canvas"};

    private JFrame frame;
    private JPanel panel;
    private JButton start, setDefault;
    private static int width = 600;
    private static int height = 840;
    private int offset = 10;
    private static int labelOffset = 30;//change this to affect the end size of JFrame window
    private ArrayList<SpinnerModel> spinnerModels = new ArrayList<SpinnerModel>();
    private String[] labels = new String[] {"Width", "Height", "Humans", "Zombies", "Minimum Humans", "Maximum Humans", "Minimum Zombies", "Maximum Zombies",
                                            "Minimum Food", "Maximum Food", "Minimum Water", "Maximum Water", "Food Regen Amount", "Water Regen Amount",
                                            "Food Starting Amount", "Water Starting Amount", "Food Max Size", "Water Max Size", "Human Resource Use Timer",
                                            "Human Base Resource Use", "Human Size Change Resource Use", "Human Zombie Seek Size", "Max Speed", "Zombie Detection Range",
                                            "Human Color", "Zombie Color", "Food Color", "Water Color"};
    private String[] windowLabels = new String[] {"Width", "Height", "Label Offset"};
    private int[] windowSettings;
    private int[] coNumbers;
    private float[] soloNumbers;
    private ArrayList<String> colorNames = new ArrayList<String>(Arrays.asList("Black", "Blue", "Cyan", "Dark Grey", "Gray", "Light Grey", "Green", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"));
    private ArrayList<Color> colors = new ArrayList<Color> (Arrays.asList(Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.lightGray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow));
    private String[] defaultColors = new String[] {"Blue", "Red", "Yellow", "Cyan"};

    /**
     * UserInterfaces' Constructor
     */
    public UserInterface(){
        getSettings();
        setNumbers();
        frame = new JFrame("CS162_McMichael_FinalProject");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(offset, offset, offset, offset));
        panel.setLayout(new GridLayout(labels.length + windowLabels.length + 3, 2));//labels.length+3 extends the grid to be as big as I need it to be
        panel.add(new JLabel("Window Settings: "));
        panel.add(new JLabel("Click start to begin or close this window to exit"));
        ArrayList<SpinnerModel> tempSpinnerModels = new ArrayList<SpinnerModel>();
        for (int i = 0; i < windowLabels.length; i++) {
            SpinnerModel newModel = new SpinnerNumberModel();
            newModel.setValue(windowSettings[i]);
            ((SpinnerNumberModel)newModel).setMinimum(0);
            tempSpinnerModels.add(newModel);
            addLabeledSpinner(windowLabels[i], newModel);
        }
        spinnerModels.clear();
        panel.add(new JLabel("Simulation Settings: "));
        panel.add(new JLabel("Leave settings at 0 for random/default values"));
        for (int i = 0; i < coNumbers.length; i++) {
            SpinnerModel newModel = new SpinnerNumberModel();
            newModel.setValue(coNumbers[i]);
            addLabeledSpinner(labels[i], newModel);
        }
        for (int i = 0; i < soloNumbers.length; i++) {
            SpinnerModel newModel = new SpinnerNumberModel();
            newModel.setValue(soloNumbers[i]);
            addLabeledSpinner(labels[i+coNumbers.length], newModel);
        }
        for (int i = 0; i < 4; i++) {
            SpinnerModel newModel = new SpinnerListModel(colorNames);
            newModel.setValue(colorNames.get(colors.indexOf(Canvas.getColors(i))));
            addLabeledSpinner(labels[i+coNumbers.length+soloNumbers.length], newModel);
        }

        spinnerModels.addAll(tempSpinnerModels);

        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSettings();
                Canvas.saveSettings();
                PApplet.main(OPTIONS);
                frame.setVisible(false);
            }
        });
        setDefault = new JButton("Defaults");
        setDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDefaultSettings();
            }
        });
        panel.add(start);
        panel.add(setDefault);

        frame.add(panel);
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Sets the Arrays' numbers after they have been initialized
     */
    private void setNumbers(){
        soloNumbers = new float[] {Canvas.getFoodBaseRegenAmount(), Canvas.getWaterBaseRegenAmount(), Canvas.getFoodStartAmount(), Canvas.getWaterStartAmount(), Canvas.getFoodMaxSize(),
                Canvas.getWaterMaxSize(), Canvas.getUseTimerMax(), Canvas.getBaseResourceUse(), Canvas.getSizeResourceUse(), Canvas.getZombieSeekSize(), Canvas.getMaxSpeed(),
                Canvas.getZombieDetectionRange()};
        coNumbers = new int[] {Canvas.getWidth(), Canvas.getHeight(), 0, 0, Canvas.getMinHumans(), Canvas.getMaxHumans(), Canvas.getMinZombies(), Canvas.getMaxZombies(), Canvas.getMinFood(), Canvas.getMaxFood(),
                Canvas.getMinWater(), Canvas.getMaxWater()};
        height = labels.length*labelOffset;
        windowSettings = new int[] {width, height, labelOffset};
    }

    /**
     * Adds the SpinerModel passed in to the JPanel
     * @param label A variable of type String
     * @param model A variable of type SpinnerModel
     */
     private void addLabeledSpinner(String label, SpinnerModel model) {
         spinnerModels.add(model);
         JLabel l = new JLabel(label);
         panel.add(l);

         JSpinner spinner = new JSpinner(model);
         l.setLabelFor(spinner);
         panel.add(spinner);
    }

    /**
     * Called when the user clicks start
     * Sets all the settings from the user
     */
    private void setSettings(){
        for (int i = 0; i < 4; i++) {
            setSettingsMethods(i, ((SpinnerNumberModel) spinnerModels.get(i)).getNumber().intValue());
        }
        for (int i = 4; i < coNumbers.length; i+=2) {
            int[] minMax = checkSettings(((SpinnerNumberModel) spinnerModels.get(i)).getNumber().intValue(), ((SpinnerNumberModel) spinnerModels.get(i+1)).getNumber().intValue());
            setSettingsMethods(i, minMax[0]);
            setSettingsMethods(i+1, minMax[1]);
        }
        for (int i = 0; i < soloNumbers.length; i++) {
            int num = i + coNumbers.length;
            setSettingsMethods(num, ((SpinnerNumberModel) spinnerModels.get(num)).getNumber().intValue());
        }
        for (int i = 0; i < defaultColors.length; i++) {
            int num = i + coNumbers.length + soloNumbers.length;
            setSettingsMethods(num, colorNames.indexOf(spinnerModels.get(num).getValue()));
        }
        for (int i = 0; i < windowLabels.length; i++) {
            int num = i + coNumbers.length + soloNumbers.length + defaultColors.length;
            setSettingsMethods(num, ((SpinnerNumberModel) spinnerModels.get(num)).getNumber().intValue());
        }
    }

    /**
     * Takes the variable from the user and sets it in the Canvas class
     * Called from SetSettings
     * @param num A variable of type int
     * @param settingNum A variable of type int
     */
    private void setSettingsMethods(int num, int settingNum){
        if(settingNum < 0) settingNum = 0;
        switch (num){
            case 0:
                Canvas.setWidth(settingNum);
                break;
            case 1:
                Canvas.setHeight(settingNum);
                break;
            case 2:
                Canvas.setHumans(settingNum);
                break;
            case 3:
                Canvas.setZombies(settingNum);
                break;
            case 4:
                Canvas.setMinHumans(settingNum);
                break;
            case 5:
                Canvas.setMaxHumans(settingNum);
                break;
            case 6:
                Canvas.setMinZombies(settingNum);
                break;
            case 7:
                Canvas.setMaxZombies(settingNum);
                break;
            case 8:
                Canvas.setMinFood(settingNum);
                break;
            case 9:
                Canvas.setMaxFood(settingNum);
                break;
            case 10:
                Canvas.setMinWater(settingNum);
                break;
            case 11:
                Canvas.setMaxWater(settingNum);
                break;
            case 12:
                Canvas.setFoodBaseRegenAmount(settingNum);
                break;
            case 13:
                Canvas.setWaterBaseRegenAmount(settingNum);
                break;
            case 14:
                Canvas.setFoodStartAmount(settingNum);
                break;
            case 15:
                Canvas.setWaterStartAmount(settingNum);
                break;
            case 16:
                Canvas.setFoodMaxSize(settingNum);
                break;
            case 17:
                Canvas.setWaterMaxSize(settingNum);
                break;
            case 18:
                Canvas.setUseTimerMax(settingNum);
                break;
            case 19:
                Canvas.setBaseResourceUse(settingNum);
                break;
            case 20:
                Canvas.setSizeResourceUse(settingNum);
                break;
            case 21:
                Canvas.setZombieSeekSize(settingNum);
                break;
            case 22:
                Canvas.setMaxSpeed(settingNum);
                break;
            case 23:
                Canvas.setZombieDetectionRange(settingNum);
                break;
            case 24:
                Canvas.setHumanColor(colors.get(settingNum));
                break;
            case 25:
                Canvas.setZombieColor(colors.get(settingNum));
                break;
            case 26:
                Canvas.setFoodColor(colors.get(settingNum));
                break;
            case 27:
                Canvas.setWaterColor(colors.get(settingNum));
                break;
            case 28:
                width = settingNum;
                break;
            case 29:
                height = settingNum;
                break;
            case 30:
                labelOffset = settingNum;
                break;
        }
    }

    /**
     * Checks the variables passed to make sure the min isn't < 0, and the max isn't less than the min
     * @param min A variable of type int
     * @param max A variable of type int
     * @return An array of type int[]
     */
    private int[] checkSettings(int min, int max){
        if(min < 0) min = 0;
        if(max < min) max = min;
        return new int[] {min, max};
    }

    /**
     * Creates a new FileIO object and gets the saved settings
     */
    private void getSettings(){
        FileIO fileIO = new FileIO();
        String[] newSettings = fileIO.getSettings().split(" ");

        for (int i = 0; i < newSettings.length; i++) {
            setSettingsMethods(i, Integer.parseInt(newSettings[i]));
        }
    }

    /**
     * Creates a new FileIO object and gets the default settings
     */
    private void getDefaultSettings(){
        FileIO fileIO = new FileIO();
        String[] newSettings = fileIO.getDefaults().split(" ");

        for (int i = 0; i < newSettings.length; i++) {
            try{
                ((SpinnerNumberModel)spinnerModels.get(i)).setValue(Integer.parseInt(newSettings[i]));
            } catch (Exception e){
                System.out.println("Error casting spinnerModels to SpinnerNumber");
                try{
                    ((SpinnerListModel)spinnerModels.get(0)).setValue(colors.get(Integer.parseInt(newSettings[i])));
                } catch (Exception ex){
                    System.out.println("Error casting spinnerModels to SpinnerList");
                }
            }
        }
    }

    /**
     * Returns the width of the UI window
     * @return A variable of type int
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Returns the height of the UI window
     * @return A variable of type int
     */
    public static int getHeight() {
        return height;
    }

    /**
     * Returns the label offset for the UI window
     * @return A variable of type int
     */
    public static int getLabelOffset() {
        return labelOffset;
    }
}
