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
import java.util.ArrayList;

/*
What to modify:
Done ------ Height and width of screen (leave at 0 to be normal, have a minimum and maybe maximum)
Done ------ Humans and zombies (leave at 0 to be random)
Done ------ min/max humans, zombies
Done ------ min/max food, and water
food/water baseRegenAmount, startingAmount, and maxSize
Human useTimerMax
Human baseResourceUse, sizeResourceUse, and zombieSeekSize
Creature interactionRange and maxSpeed
Zombie detectionRange
humans, zombie, food, and water color
 */

public class UserInterface {
    private final static String[] OPTIONS = new String[] {"--present", "Canvas"};

    private JFrame frame;
    private JPanel panel;
    private JButton start;
    private final int offset = 10;
    private int labelOffset = 40;//change this to affect the end size of JFrame window
    private ArrayList<SpinnerModel> spinnerModels = new ArrayList<SpinnerModel>();
    private String[] labels = new String[] {"Width", "Height", "Humans", "Zombies", "Minimum Humans", "Maximum Humans", "Minimum Zombies", "Maximum Zombies",
                                            "Minimum Food", "Maximum Food", "Minimum Water", "Maximum Water"};
    private int[] numbers = new int[] {Canvas.getWidth(), Canvas.getHeight(), 0, 0, Canvas.getMinHumans(), Canvas.getMaxHumans(), Canvas.getMinZombies(), Canvas.getMaxZombies(), Canvas.getMinFood(), Canvas.getMaxFood(),
                                        Canvas.getMinWater(), Canvas.getMaxWater()};

    public UserInterface(){
        frame = new JFrame("CS162_McMichael_FinalProject");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(offset, offset, offset, offset));
        panel.setLayout(new GridLayout(labels.length+2, 2));//labels.length+3 extends the grid to be as big as I need it to be//todo change to +3 when i add window settings
        //panel.add(new JLabel("Window Settings: "));
        panel.add(new JLabel("Click start to begin or close this window to exit"));
        //Add Window Settings here (like changing labelOffset)//todo need file saving to add window settings
        //panel.add(new JLabel("Simulation Settings: "));
        panel.add(new JLabel("Leave settings at 0 for random/default values"));
        for (int i = 0; i < numbers.length; i++) {
            SpinnerModel newModel = new SpinnerNumberModel();
            newModel.setValue(numbers[i]);
            addLabeledSpinner(labels[i], newModel);
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
        for (int i = 0; i < spinnerModels.size(); i+=2) {//todo STOPPED HERE Finished adding all the minimum and maximum settings so now I need a new for loop for creating the normal settings
            //System.out.println(spinnerModels.get(i).getClass().getTypeName());
            if((spinnerModels.get(i).getClass().getTypeName().equals("javax.swing.SpinnerNumberModel")) && (spinnerModels.get(i+1).getClass().getTypeName().equals("javax.swing.SpinnerNumberModel"))){
                int[] minMax = checkSettings(((SpinnerNumberModel) spinnerModels.get(i)).getNumber().intValue(), ((SpinnerNumberModel) spinnerModels.get(i+1)).getNumber().intValue());
                setSettingsMethods(i, minMax[0]);
                setSettingsMethods(i+1, minMax[1]);
            }
        }
    }

    private void setSettingsMethods(int num, int settingNum){//todo ask teacher about this approach since its 3 am and it may not be the best
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
        }
    }

    private int[] checkSettings(int min, int max){
        if(min < 0) min = 0;
        if(max < min) max = min;
        return new int[] {min, max};
    }

}
