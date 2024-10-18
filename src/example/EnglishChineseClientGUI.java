package example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnglishChineseClientGUI extends JFrame {
    private JTextArea outputArea;
    private JTextField inputField;
    private JButton translateButton;
    private JButton exampleButton;

    public EnglishChineseClientGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("English-Chinese Translator");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Setup output area for displaying translations
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createTitledBorder("Translation Output"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(new JScrollPane(outputArea), gbc);

        // Setup input field for user to enter text
        inputField = new JTextField();
        inputField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8;
        add(inputField, gbc);

        // Setup translate button
        translateButton = new JButton("Translate");
        translateButton.setFont(new Font("Arial", Font.BOLD, 16));
        translateButton.setPreferredSize(new Dimension(120, 40));
        translateButton.setBackground(Color.BLUE);
        translateButton.setForeground(Color.WHITE);
        translateButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(translateButton, gbc);

        // Setup example button
        exampleButton = new JButton("Get Example");
        exampleButton.setFont(new Font("Arial", Font.BOLD, 16));
        exampleButton.setPreferredSize(new Dimension(120, 40));
        exampleButton.setBackground(Color.GREEN);
        exampleButton.setForeground(Color.WHITE);
        exampleButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(exampleButton, gbc);

        // Add action listeners to buttons
        translateButton.addActionListener(new TranslateAction());
        exampleButton.addActionListener(new ExampleAction());

        // Make the window visible and set it to be居中
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private class TranslateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String wordToTranslate = inputField.getText().trim();
            if (!wordToTranslate.isEmpty()) {
                translateWord(wordToTranslate);
            }
        }

        private void translateWord(String word) {
            try {
                cn.com.webxml.EnglishChinese service = new cn.com.webxml.EnglishChinese();
                cn.com.webxml.EnglishChineseSoap englishChineseSoap = service.getEnglishChineseSoap();
                cn.com.webxml.ArrayOfString translatedResult = englishChineseSoap.translatorString(word);
                StringBuilder result = new StringBuilder("Translation:\n");
                for (String translation : translatedResult.getString()) {
                    result.append(translation).append("\n");
                }
                outputArea.setText(result.toString());
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class ExampleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String wordToTranslate = inputField.getText().trim();
            if (!wordToTranslate.isEmpty()) {
                getExampleSentences(wordToTranslate);
            }
        }

        private void getExampleSentences(String word) {
            try {
                cn.com.webxml.EnglishChinese service = new cn.com.webxml.EnglishChinese();
                cn.com.webxml.EnglishChineseSoap englishChineseSoap = service.getEnglishChineseSoap();
                cn.com.webxml.ArrayOfString exampleResult = englishChineseSoap.translatorSentenceString(word);
                StringBuilder result = new StringBuilder("Example Sentences:\n");
                for (String example : exampleResult.getString()) {
                    result.append(example).append("\n");
                }
                outputArea.setText(result.toString());
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EnglishChineseClientGUI::new);
    }
}
