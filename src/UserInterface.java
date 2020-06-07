import processing.core.PApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.BorderFactory;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

//todo settings for: humans, zombie, food, and water color

public class UserInterface {
    private final static String[] OPTIONS = new String[] {"--present", "Canvas"};

    private JFrame frame;
    private JPanel panel;
    private JButton start;
    private final int offset = 10;
    private int labelOffset = 40;//change this to affect the end size of JFrame window
    private ArrayList<SpinnerModel> spinnerModels = new ArrayList<SpinnerModel>();
    private String[] labels = new String[] {"Width", "Height", "Humans", "Zombies", "Minimum Humans", "Maximum Humans", "Minimum Zombies", "Maximum Zombies",
                                            "Minimum Food", "Maximum Food", "Minimum Water", "Maximum Water", "Food Regen Amount", "Water Regen Amount",
                                            "Food Starting Amount", "Water Starting Amount", "Food Max Size", "Water Max Size", "Human Resource Use Timer",
                                            "Human Base Resource Use", "Human Size Change Resource Use", "Human Zombie Seek Size", "Max Speed", "Zombie Detection Range"};
    private int[] coNumbers = new int[] {Canvas.getWidth(), Canvas.getHeight(), 0, 0, Canvas.getMinHumans(), Canvas.getMaxHumans(), Canvas.getMinZombies(), Canvas.getMaxZombies(), Canvas.getMinFood(), Canvas.getMaxFood(),
                                        Canvas.getMinWater(), Canvas.getMaxWater()};
    private float[] soloNumbers = new float[] {Canvas.getFoodBaseRegenAmount(), Canvas.getWaterBaseRegenAmount(), Canvas.getFoodStartAmount(), Canvas.getWaterStartAmount(), Canvas.getFoodMaxSize(),
                                                Canvas.getWaterMaxSize(), Canvas.getUseTimerMax(), Canvas.getBaseResourceUse(), Canvas.getSizeResourceUse(), Canvas.getZombieSeekSize(), Canvas.getMaxSpeed(),
                                                Canvas.getZombieDetectionRange()};

    public UserInterface(){
        frame = new JFrame("CS162_McMichael_FinalProject");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(offset, offset, offset, offset));
        panel.setLayout(new GridLayout(labels.length+2, 2));//labels.length+3 extends the grid to be as big as I need it to be//todo change to +3 when I add window settings
        //panel.add(new JLabel("Window Settings: "));
        panel.add(new JLabel("Click start to begin or close this window to exit"));
        //Add Window Settings here (like changing labelOffset)//todo need file saving to add window settings
        //panel.add(new JLabel("Simulation Settings: "));
        panel.add(new JLabel("Leave settings at 0 for random/default values"));
        for (int i = 0; i < coNumbers.length; i++) {
            SpinnerModel newModel = new SpinnerNumberModel();
            newModel.setValue(coNumbers[i]);
            addLabeledSpinner(labels[i], newModel);
            spinnerModels.add(newModel);
        }
        for (int i = 0; i < soloNumbers.length; i++) {
            SpinnerModel newModel = new SpinnerNumberModel();
            newModel.setValue(soloNumbers[i]);
            addLabeledSpinner(labels[i+coNumbers.length], newModel);
            spinnerModels.add(newModel);
        }

        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSettings();
                PApplet.main(OPTIONS);
                frame.setVisible(false);
            }
        });
        panel.add(start);

        frame.add(panel);
        frame.setPreferredSize(new Dimension(600, labels.length*labelOffset));
        frame.pack();
        frame.setVisible(true);
    }

     private void addLabeledSpinner(String label, SpinnerModel model) {
        JLabel l = new JLabel(label);
        panel.add(l);

        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        panel.add(spinner);
    }

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
    }

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
        }
    }

    private int[] checkSettings(int min, int max){
        if(min < 0) min = 0;
        if(max < min) max = min;
        return new int[] {min, max};
    }

}
