package okon.ApplicationManager;

import okon.ApplicationManager.config.ConfigParamsReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class ApplicationManager extends JDialog {
    final static List<Program> programs = ConfigParamsReader.readConfigParams(new File("./config/config.xml"));

    public ApplicationManager() {
        initialize();
    }

    private void initialize() {
        JFrame frame = new JFrame();
        int startHeigtForButtons = 156;
        int buttonHeight = 30;
        int spaceBetweenButtons = 10;

        frame.add(getImage("./img/Photo.jpg"));

        for (int i = 0; i < programs.size(); i++) {
            if (areButtonsInOneColumn(programs.size())) {
                final int j = i;
                JButton button = new JButton(programs.get(i).getAlias());
                button.setBounds(90, setStartingY(startHeigtForButtons, i, buttonHeight, spaceBetweenButtons), 300, buttonHeight);//x axis, y axis, width, height
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Runtime.getRuntime().exec(programs.get(j).getFilename(), null, new File(programs.get(j).getPath()));
                            frame.dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                frame.add(button);
            } else {
                final int j = i;
                JButton button = new JButton(programs.get(i).getAlias());
                if (i % 2 == 0) {
                    button.setBounds(30, setStartingY(startHeigtForButtons, i/2, buttonHeight, spaceBetweenButtons), 190, buttonHeight);//x axis, y
                } else {
                    button.setBounds(256, setStartingY(startHeigtForButtons, i/2, buttonHeight, spaceBetweenButtons), 190, buttonHeight);//x axi
                }
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Runtime.getRuntime().exec(programs.get(j).getFilename(), null, new File(programs.get(j).getPath()));
                            frame.dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                frame.add(button);
            }
        }

        frame.setResizable(false);
        frame.setSize(486, countGUIHeight(frame) + 60);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Wybor systemu do zalogowania");
    }

    private JLabel getImage(String path) {
        ImageIcon image = new ImageIcon(path);
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.red));
        imageLabel.setBounds(20, 20, 436, 116);

        return imageLabel;
    }

    private boolean areButtonsInOneColumn(int buttons) {
        if (buttons <= 8 ) {
            return true;
        }
        return false;
    }

    private int setStartingY(int startHeigtForButtons, int index, int buttonHeight, int spaceBetweenButtons) {
        if (index == 0) {
            return startHeigtForButtons;
        } else {
            return startHeigtForButtons + index * (buttonHeight + spaceBetweenButtons);
        }
    }

    private boolean isLessThanMinimum(int height) {
        return height < 300 ? true : false;
    }

    private int countGUIHeight(JFrame frame) {
        int result = 0;
        for (Component component : frame.getContentPane().getComponents()) {
            int height = 0;
            height = component.getBounds().y + component.getBounds().height;
            if (height > result) {
                result = height;
            }
        }

        if (isLessThanMinimum(result)) {
            result = 300;
        }
        return result;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ApplicationManager();
            }
        });
    }
}