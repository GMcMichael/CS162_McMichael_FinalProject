import processing.core.PApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
//import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
What to modify:
Height and width of screen (leave at 0 to be normal, have a minimum and maybe maximum)
Humans and zombies (leave at 0 to be random)
min/max humans, zombies, food, and water (leave at 0 to be normal)
resource use timer for humans
humans, zombie, food, and water color
food/water baseRegenAmount and maxSize
Creature interactionRange
Creature maxSpeed
Human baseResourceUse, sizeResourceUse, and zombieSeekSize
Zombie detectionRange
 */

public class UserInterface {
    private final static String[] OPTIONS = new String[] {"--present", "Canvas"};

    private JFrame frame;
    private JPanel panel;
    private JButton start;
    private final int offset = 10;
    private ArrayList<SpinnerModel> spinnerModels = new ArrayList<SpinnerModel>();

    public UserInterface(){//first just modify the width and height of screen
        frame = new JFrame("CS162_McMichael_FinalProject");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(offset, offset, offset, offset));
        panel.setLayout(new GridLayout(4, 2));
        int currWidth = Canvas.getWidth();
        int currHeight = Canvas.getHeight();
        SpinnerModel widthModel = new SpinnerNumberModel(currWidth, 500, 2000, 1);
        addLabeledSpinner("Width", widthModel);
        spinnerModels.add(widthModel);
        SpinnerModel heightModel = new SpinnerNumberModel(currHeight, 500, 2000, 1);
        addLabeledSpinner("Height", heightModel);
        spinnerModels.add(heightModel);

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
        frame.setPreferredSize(new Dimension(600, 400));
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
        Canvas.setWidth(((SpinnerNumberModel)spinnerModels.get(0)).getNumber().intValue());
        Canvas.setHeight(((SpinnerNumberModel)spinnerModels.get(1)).getNumber().intValue());
    }

}
