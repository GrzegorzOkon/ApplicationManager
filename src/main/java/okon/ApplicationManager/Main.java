package okon.ApplicationManager;

import okon.ApplicationManager.config.ProgramParamsReader;
import okon.ApplicationManager.config.LoggerParamsReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import javax.swing.*;
import java.awt.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Properties;

public class Main extends JDialog {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final Properties logProperties = LoggerParamsReader.readProperties(new File("./config/logger.properties"));
    private static final List<Program> programs = ProgramParamsReader.readConfigParams(new File("./config/programs.xml"));
    private static final String username = System.getProperty("user.name");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        if (isLoggerEnable()) {
            configureLogger();
        }
        initializeGUI();
    }

    public static boolean isLoggerEnable() {
        if (logProperties.getProperty("logger.enable").equals("true")) {
            return true;
        }
        return false;
    }

    private void configureLogger() {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File("config/log4j2.xml");
        context.setConfigLocation(file.toURI());
    }

    private void initializeGUI() {
        JFrame frame = new JFrame();
        int startHeigtForButtons = 156;
        int buttonHeight = 30;
        int spaceBetweenButtons = 10;

        frame.add(getImage("./img/Photo.jpg"));

        for (int i = 0; i < programs.size(); i++) {
            if (areButtonsInOneColumn(programs.size())) {
                final int j = i;
                JButton button = new JButton(programs.get(i).getAlias());
                button.setBounds(90, setStartingY(startHeigtForButtons, i, buttonHeight, spaceBetweenButtons), 300, buttonHeight);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Runtime.getRuntime().exec(programs.get(j).getFilename(), null, new File(programs.get(j).getPath()));
                            if (isLoggerEnable()) {
                                logger.info("User \"" + username + "\"" + " run program \"" + programs.get(j).getFilename() + "\"");
                            }
                            frame.dispose();
                        } catch (Exception ex) {
                            if (isLoggerEnable()) {
                                logger.error(ex.getMessage());
                            }
                            throw new AppException(ex);
                        }
                    }
                });
                frame.add(button);
            } else {
                final int j = i;
                JButton button = new JButton(programs.get(i).getAlias());
                if (i % 2 == 0) {
                    button.setBounds(30, setStartingY(startHeigtForButtons, i/2, buttonHeight, spaceBetweenButtons), 190, buttonHeight);
                } else {
                    button.setBounds(256, setStartingY(startHeigtForButtons, i/2, buttonHeight, spaceBetweenButtons), 190, buttonHeight);
                }
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Runtime.getRuntime().exec(programs.get(j).getFilename(), null, new File(programs.get(j).getPath()));
                            if (isLoggerEnable()) {
                                logger.info("User \"" + username + "\"" + " run program \"" + programs.get(j).getFilename() + "\"");
                            }
                            frame.dispose();
                        } catch (Exception ex) {
                            if (isLoggerEnable()) {
                                logger.error(ex.getMessage());
                            }
                            throw new AppException(ex);
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Wyb\u00F3r systemu do zalogowania");
        frame.setVisible(true);

        if (isLoggerEnable()) {
            logger.info("The application is started for user \"" + username + "\"");
        }
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
}