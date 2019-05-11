package main;

import engine.Character;
import engine.DataHandler;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CharacterSheetGUI extends JFrame {

    public static final Font SectionTitleFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    public static final Border SectionBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.LOWERED),
            BorderFactory.createEmptyBorder(10, 10, 10, 10));

    private final JFileChooser fileChooser = new JFileChooser();

    private final Random rand = new Random();

    private RightClickListener rightClickListener = new RightClickListener();

    private JPanel contentPane;
    private HeaderPanel headerPanel;
    private CharacterValuesHolder characterValuesHolder;
    private TraitsPanel traitsPanel;

    private Character character;
    private DataHandler dataHandler;

    public int GetSignedIntValue(String signedString) {
        if (signedString.length() < 2)
        {
            return 0;
        }

        boolean isPositive = signedString.startsWith("+");
        int integerValue = Integer.parseInt(signedString.substring(1));
        return isPositive ? integerValue : -integerValue;
    }


    /**
     * Create the frame.
     */
    public CharacterSheetGUI() {
        InitializeData();
        InitializeGUI();
        AddPanels();
        //UpdateAllFields();
    }
    private void InitializeData() {
        this.dataHandler = new DataHandler();

        //TODO automatically check for last used file...
        //if (this.dataHandler.CheckSavedData()) {
        //this.character = this.dataHandler.ReadData();
        //} else {
        this.character = new Character();
        //}
    }

    private void UpdateAllFields() {
        headerPanel.UpdateFields();
        characterValuesHolder.UpdateFields();
        traitsPanel.UpdateFields();
    }

    private void InitializeGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(0, 0, 1600, 900);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
    }

    private void AddPanels() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;

        JPanel holdingPanel = new JPanel(new GridBagLayout());

        constraints.weighty = 1;
        constraints.gridwidth = 3;
        headerPanel = new HeaderPanel();
        holdingPanel.add(headerPanel, constraints);

        constraints.weighty = 4;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        characterValuesHolder = new CharacterValuesHolder();
        holdingPanel.add(characterValuesHolder, constraints);

        constraints.gridx = 2;
        traitsPanel = new TraitsPanel();
        holdingPanel.add(traitsPanel, constraints);

        // Scrollpane which holds the body of the form.
        JScrollPane scrollPane = new JScrollPane(holdingPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Initialize top toolbar
        //contentPane.add(new TopMenu("Tools",  dataHandler), BorderLayout.NORTH);
        //contentPane.add(new MenuBar(dataHandler), BorderLayout.NORTH);
        this.setJMenuBar(new MenuBar(dataHandler));
    }

    private class MenuBar extends JMenuBar implements ActionListener {
        private DataHandler dataHandler;
        public MenuBar(DataHandler dataHandler) {
            super();
            this.dataHandler = dataHandler;

            InitializeMenuBar();
            AddButtons();
        }

        private void InitializeMenuBar() {
            this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,
                    Color.BLACK));
        }

        private void AddButtons() {
            VerticalMenu fileMenu = new VerticalMenu("File");

            JMenuItem saveButton = new JMenuItem("Save");
            saveButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
            saveButton.setActionCommand("Save");
            saveButton.addActionListener(this);
            fileMenu.add(saveButton);

            JMenuItem loadButton = new JMenuItem("Load");
            loadButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
            loadButton.setActionCommand("Load");
            loadButton.addActionListener(this);
            fileMenu.add(loadButton);

            JMenuItem importButton = new JMenuItem("Import");
            importButton.setActionCommand("Import");
            importButton.addActionListener(this);
            fileMenu.add(importButton);

            this.add(fileMenu);

            JMenuItem rollButton = new JMenuItem("Roll");
            rollButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
            rollButton.setActionCommand("Roll");
            rollButton.addActionListener(this);

            rollButton.setMaximumSize(new Dimension(rollButton.getPreferredSize().width, Integer.MAX_VALUE));
            this.add(rollButton);

            JMenuItem infoButton = new JMenuItem("Info");
            infoButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
            infoButton.setActionCommand("Info");
            infoButton.addActionListener(this);

            infoButton.setMaximumSize(new Dimension(infoButton.getPreferredSize().width, Integer.MAX_VALUE));
            this.add(infoButton);

            //this.add(Box.createHorizontalGlue());
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getActionCommand().equals("Save")) {
                // Save file chooser
                fileChooser.setSelectedFile(new File(character.getName().concat("Save.bin")));
                int returnVal = fileChooser.showSaveDialog(contentPane);

                // If selected we save the file
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File saveFile = fileChooser.getSelectedFile();
                    this.dataHandler.WriteData(character, saveFile);
                }
            } else if (ae.getActionCommand().equals("Load")) {
                // Open file chooser
                int returnVal = fileChooser.showOpenDialog(contentPane);

                // If selected we read the file
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    character = this.dataHandler.ReadData(file);
                    UpdateAllFields();
                }

                //} else if (ae.getActionCommand().equals("Import")) {
                //int returnVal = fileChooser.showOpenDialog(contentPane);
                //if (returnVal == JFileChooser.APPROVE_OPTION) {
                //    File file = fileChooser.getSelectedFile();
                //    this.dataHandler.ImportData(file);
                //}
            }
        }


        private class VerticalMenu extends JMenu{

            public VerticalMenu(String label) {
                super(label);
                JPopupMenu pm = getPopupMenu();
                pm.setLayout(new BoxLayout(pm, BoxLayout.PAGE_AXIS));
            }
        }
    }

    private class HeaderPanel extends JPanel implements DocumentListener {
        private JTextField characterNameTextField;
        private JTextField levelTextField;
        private CharacterDetailsPanel charDetailsPanel;

        public HeaderPanel() {
            InitializePanel();
            AddComponents();
        }

        private void InitializePanel() {
            this.setLayout(new GridBagLayout());
        }

        private void AddComponents() {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.weightx = 1;
            JLabel characterNameLabel = new JLabel("Character Name");
            this.add(characterNameLabel, constraints);

            characterNameTextField = new JTextField(15);
            constraints.gridx = 1;
            this.add(characterNameTextField, constraints);

            Font charNameFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
            characterNameTextField.setFont(charNameFont);
            characterNameTextField.getDocument().putProperty("owner", characterNameTextField);
            characterNameTextField.getDocument().putProperty("charAttr", "CharName");
            characterNameTextField.getDocument().addDocumentListener(this);

            JLabel levelLabel = new JLabel("Level");
            constraints.gridx = 0; constraints.gridy = 1;
            constraints.anchor = GridBagConstraints.LINE_END;
            this.add(levelLabel, constraints);

            levelTextField = new JTextField(3);
            constraints.gridx = 1; constraints.anchor = GridBagConstraints.LINE_START;
            this.add(levelTextField, constraints);

            AbstractDocument levelDoc = (AbstractDocument) levelTextField.getDocument();
            levelDoc.addDocumentListener(this);
            levelDoc.putProperty("owner", levelTextField);
            levelDoc.putProperty("charAttr", "Level");

            NumericalFilter numFilter = new NumericalFilter();
            numFilter.setMaxCharacters(3);
            levelDoc.setDocumentFilter(numFilter);

            charDetailsPanel = new CharacterDetailsPanel();
            constraints.gridx = 2; constraints.gridy = 0; constraints.gridheight = 2;
            constraints.anchor = GridBagConstraints.CENTER;
            this.add(charDetailsPanel, constraints);
        }

        public void UpdateFields() {
            this.characterNameTextField.setText(character.getName());
            this.levelTextField.setText(Integer.toString(character.getLevel()));
            this.charDetailsPanel.UpdateFields();
        }

        private void SetCharacterProperty(AbstractDocument e) {
            JTextComponent owner = (JTextComponent) e.getProperty("owner");
            String charAttr = (String) e.getProperty("charAttr");

            switch (charAttr) {
                case "CharName":
                    character.setName(owner.getText());
                    break;
                case "Level":
                    if (owner.getText().length() > 0) {
                        character.setLevel(Integer.parseInt(owner.getText()));
                    } else {
                        character.setLevel(0);
                    }
                    break;
            }
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            SetCharacterProperty((AbstractDocument) e.getDocument());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            SetCharacterProperty((AbstractDocument) e.getDocument());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }

        private class CharacterDetailsPanel extends JPanel implements DocumentListener {
            // Goes within header - contains character information
            private JComboBox<Character.Classes> classDropDown;
            private JTextField backgroundTextField;
            private JTextField playerNameTextField;
            private JComboBox<Character.Races> raceDropDown;
            private JFormattedTextField expPointsTextField;

            public CharacterDetailsPanel() {
                InitializePanel();
                AddComponents();
            }

            private void InitializePanel() {
                this.setLayout(new GridBagLayout());
                this.setBorder(SectionBorder);

            }

            private void AddComponents() {
                GridBagConstraints constraints = new GridBagConstraints();
                JLabel classLabel = new JLabel("Class");
                this.add(classLabel, constraints);

                classDropDown = new JComboBox<>(Character.Classes.values());
                classDropDown.addActionListener((ActionEvent e) ->
                        character.setLotrClass((Character.Classes) classDropDown.getSelectedItem()));

                this.add(classDropDown, constraints);

                JLabel backgroundLabel = new JLabel("Background");
                constraints.gridx = 2;
                this.add(backgroundLabel, constraints);

                backgroundTextField = new JTextField(10);

                // Fix drop down to samuip size as background.
                classDropDown.setPreferredSize(backgroundTextField.getPreferredSize());

                backgroundTextField.getDocument().addDocumentListener(this);
                backgroundTextField.getDocument().putProperty("owner", backgroundTextField);
                backgroundTextField.getDocument().putProperty("charValue", "Background");
                constraints.gridx = 3;
                this.add(backgroundTextField, constraints);

                JLabel playerNameLabel = new JLabel("Player Name");
                constraints.gridx = 4;
                this.add(playerNameLabel, constraints);

                playerNameTextField = new JTextField(10);
                playerNameTextField.getDocument().addDocumentListener(this);
                playerNameTextField.getDocument().putProperty("owner", playerNameTextField);
                playerNameTextField.getDocument().putProperty("charValue", "PlayerName");
                constraints.gridx = 5;
                this.add(playerNameTextField, constraints);

                JLabel raceLabel = new JLabel("Race");
                constraints.gridx = 0;
                constraints.gridy = 1;
                this.add(raceLabel, constraints);

                raceDropDown = new JComboBox<>(Character.Races.values());
                raceDropDown.addActionListener((ActionEvent e) ->
                        character.setRace((Character.Races) raceDropDown.getSelectedItem()));
                raceDropDown.setPreferredSize(backgroundTextField.getPreferredSize());
                constraints.gridx = 1;
                this.add(raceDropDown, constraints);

                JLabel alignmentLabel = new JLabel("Alignment");
                constraints.gridx = 2;
                this.add(alignmentLabel, constraints);

                JLabel expPointsLabel = new JLabel("Experience Points");
                constraints.gridx = 3;
                this.add(expPointsLabel, constraints);

                // Create exp text field
                expPointsTextField = new JFormattedTextField();
                expPointsTextField.setPreferredSize(backgroundTextField.getPreferredSize());
                AbstractDocument expPointsDoc = ((AbstractDocument) expPointsTextField.getDocument());
                expPointsDoc.addDocumentListener(this);
                expPointsDoc.putProperty("owner", expPointsTextField);
                expPointsDoc.putProperty("charValue", "ExpPoints");

                NumericalFilter numFilter = new NumericalFilter();
                numFilter.setMinValue(0);
                expPointsDoc.setDocumentFilter(numFilter);

                constraints.gridx = 5;
                this.add(expPointsTextField, constraints);
            }

            public void UpdateFields() {
                this.classDropDown.setSelectedItem(character.getLotrClass());
                this.backgroundTextField.setText(character.getBackground());
                this.raceDropDown.setSelectedItem(character.getRace());
                this.expPointsTextField.setText(Integer.toString(character.getExperiencePoints()));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                SetCharacterValue(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                SetCharacterValue(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private boolean SetCharacterValue(DocumentEvent e) {

                JTextField owner = (JTextField) e.getDocument().getProperty("owner");
                String charValue = (String) e.getDocument().getProperty("charValue");

                switch (charValue) {
                    case "Background":
                        character.setBackground(owner.getText());
                        break;
                    case "ExpPoints":
                        try {
                            character.setExperiencePoints(Integer.parseInt(owner.getText()));
                        } catch (NumberFormatException ex) {
                            Toolkit.getDefaultToolkit().beep();
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        }
    }

    private class CharacterValuesHolder extends JPanel implements DocumentListener {
        // Contains panels for attributes, skills, saving throws etc.
        private AttributePanel attrPanel;
        private SavingThrowsPanel savingThrowsPanel;
        private InputBox inspirationInputBox;
        private InputBox proficiencyBonus;
        private InputBox passiveWisInputBox;

        private JTextArea profAndLangTextArea;

        private final Font defaultFieldFont = UIManager.getLookAndFeelDefaults().getFont("TextField.font");
        private final Font defaultLabelFont = UIManager.getLookAndFeelDefaults().getFont("Label.font");
        private final Font boldFieldFont = new Font(defaultFieldFont.getFontName(), Font.BOLD, defaultFieldFont.getSize());
        private final Font boldLabelFont = new Font(defaultLabelFont.getFontName(), Font.BOLD, defaultLabelFont.getSize());

        public CharacterValuesHolder() {

            this.InitializePanel();
            this.AddComponents();
        }

        private void InitializePanel() {
            this.setLayout(new GridBagLayout());
        }

        private void AddComponents() {
            GridBagConstraints constraints = new GridBagConstraints();
            attrPanel = new AttributePanel();

            constraints.gridx = 0;
            constraints.gridy = 0;
            this.add(attrPanel, constraints);

            JPanel rightHoldingPanel = new JPanel();
            rightHoldingPanel.setLayout(new GridBagLayout());

            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.weightx = 1;
            this.add(rightHoldingPanel, constraints);

            NumericalFilter numFilter = new NumericalFilter();
            numFilter.setMaxCharacters(2);

            NumericalFilter signedFilter = new NumericalFilter();
            signedFilter.setNeedsSign(true); signedFilter.setMaxCharacters(3);

            inspirationInputBox = new InputBox("Inspiration", numFilter);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.anchor = GridBagConstraints.LINE_START;
            rightHoldingPanel.add(inspirationInputBox, constraints);

            proficiencyBonus = new InputBox("Proficiency Bonus", signedFilter);
            constraints.gridy += 1;
            rightHoldingPanel.add(proficiencyBonus, constraints);

            savingThrowsPanel = new SavingThrowsPanel();
            constraints.gridy += 1;
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            rightHoldingPanel.add(savingThrowsPanel, constraints);

            JPanel bottomHoldingPanel = new JPanel();
            bottomHoldingPanel.setLayout(new BoxLayout(bottomHoldingPanel, BoxLayout.Y_AXIS));

            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 2;
            constraints.anchor = GridBagConstraints.CENTER;
            this.add(bottomHoldingPanel, constraints);


            passiveWisInputBox = new InputBox("Passive Wisdom (Perception)", numFilter);
            bottomHoldingPanel.add(passiveWisInputBox);

            profAndLangTextArea = new JTextArea(4, 20);
            profAndLangTextArea.setLineWrap(true);
            profAndLangTextArea.setWrapStyleWord(true);
            profAndLangTextArea.setMinimumSize(new Dimension(profAndLangTextArea.getPreferredSize()));

            AbstractDocument langDoc = (AbstractDocument) profAndLangTextArea.getDocument();
            langDoc.addDocumentListener(this);
            langDoc.putProperty("owner", profAndLangTextArea);
            langDoc.putProperty("charAttr","ProfAndLang");

            JScrollPane profAndLangScrollPane = new JScrollPane(profAndLangTextArea);
            profAndLangScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            bottomHoldingPanel.add(profAndLangScrollPane);

            JLabel profAndLangLabel = new JLabel("Other Proficiencies & Languages");
            profAndLangLabel.setFont(SectionTitleFont);
            bottomHoldingPanel.add(profAndLangLabel);

        }

        public void UpdateFields() {
            attrPanel.UpdateFields();
        }

        private void SetCharacterPropertyByName(AbstractDocument e) {
            JTextComponent textComp = (JTextComponent) e.getProperty("owner");
            String charAttr = (String) e.getProperty("charAttr");

            String textValue = textComp.getText();
            int signedValue = 0;

            //TODO Quite hacky
            if (!charAttr.equals("ProfAndLang")) {
                signedValue = GetSignedIntValue(textValue);
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            SetCharacterPropertyByName((AbstractDocument) e.getDocument());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            SetCharacterPropertyByName((AbstractDocument) e.getDocument());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            SetCharacterPropertyByName((AbstractDocument) e.getDocument());
        }

        private class InputBox extends JPanel {

            private JTextField inputBox;
            private String labelText;

            public InputBox(String labelText, NumericalFilter filter) {
                this.labelText = labelText;

                InitializePanel();
                AddComponents(filter);
            }

            private void InitializePanel() {
                this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            }

            private void AddComponents(NumericalFilter filter) {
                inputBox = new JTextField();
                this.add(inputBox);
                inputBox.setColumns(3);
                inputBox.setMaximumSize(new Dimension(inputBox.getPreferredSize()));

                AbstractDocument doc = (AbstractDocument) inputBox.getDocument();
                doc.setDocumentFilter(filter);
                doc.addDocumentListener(CharacterValuesHolder.this);
                doc.putProperty("owner", inputBox);
                doc.putProperty("charAttr", labelText);

                JLabel boxLabel = new JLabel(labelText);
                this.add(boxLabel);
            }
        }

        private class AttributePanel extends JPanel implements DocumentListener {
            private AttributeBox strBox;
            private AttributeBox dexBox;
            private AttributeBox conBox;
            private AttributeBox intBox;
            private AttributeBox wisBox;
            private AttributeBox chaBox;

            public AttributePanel() {
                this.InitializePanel();
                this.AddComponents();
            }

            private void InitializePanel() {
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.setBorder(SectionBorder);
            }

            private void AddComponents() {
                strBox = new AttributeBox("Strength", 1, 2);
                strBox.addMouseListener(rightClickListener);
                this.add(strBox);

                this.add(Box.createRigidArea(new Dimension(1, 40)));

                dexBox = new AttributeBox("Dexterity", 1, 2);
                dexBox.addMouseListener(rightClickListener);
                this.add(dexBox);

                this.add(Box.createRigidArea(new Dimension(1, 40)));

                conBox = new AttributeBox("Constitution", 1, 2);
                conBox.addMouseListener(rightClickListener);
                this.add(conBox);

                this.add(Box.createRigidArea(new Dimension(1, 40)));

                intBox = new AttributeBox("Intelligence", 1, 2);
                intBox.addMouseListener(rightClickListener);
                this.add(intBox);

                this.add(Box.createRigidArea(new Dimension(1, 40)));

                wisBox = new AttributeBox("Wisdom", 1, 2);
                wisBox.addMouseListener(rightClickListener);
                this.add(wisBox);

                this.add(Box.createRigidArea(new Dimension(1, 40)));

                chaBox = new AttributeBox("Charisma", 1, 2);
                chaBox.addMouseListener(rightClickListener);
                this.add(chaBox);
            }

            private void SetAttributeByName(String attrName, int newValue) {
                switch(attrName) {
                    case "Strength":
                        character.setStrength(newValue);
                        break;
                    case "StrengthBonus":
                        character.setStrBonus(newValue);
                        break;
                    case "Agility":
                        character.setAgility(newValue);
                        break;
                    case "AgilityBonus":
                        character.setAgiBonus(newValue);
                        break;
                    case "Constitution":
                        character.setConstitution(newValue);
                        break;
                    case "ConstitutionBonus":
                        character.setConBonus(newValue);
                        break;
                    case "Intelligence":
                        character.setIntelligence(newValue);
                        break;
                    case "IntelligenceBonus":
                        character.setIntBonus(newValue);
                        break;
                    case "Health":
                        character.setHealth(newValue);
                        break;
                    case "HealthBonus":
                        character.setHthBonus(newValue);
                        break;
                    case "Charisma":
                        character.setCharisma(newValue);
                        break;
                    case "CharismaBonus":
                        character.setChaBonus(newValue);
                    default:
                        break;
                    case "Intuition":
                        character.setIntuition(newValue);
                        break;
                    case "IntuitionBonus":
                        character.setInnBonus(newValue);
                        break;
                }
            }

            public void UpdateFields() {
                strBox.attrField.setText(Integer.toString(character.getStrength()));
                strBox.attrArea.setText(Integer.toString(character.getStrBonus()));

                dexBox.attrField.setText(Integer.toString(character.getAgility()));
                dexBox.attrArea.setText(Integer.toString(character.getAgiBonus()));

                conBox.attrField.setText(Integer.toString(character.getConstitution()));
                conBox.attrArea.setText(Integer.toString(character.getConBonus()));

                intBox.attrField.setText(Integer.toString(character.getIntelligence()));
                intBox.attrArea.setText(Integer.toString(character.getIntBonus()));

                wisBox.attrField.setText(Integer.toString(character.getHealth()));
                wisBox.attrArea.setText(Integer.toString(character.getHthBonus()));

                chaBox.attrField.setText(Integer.toString(character.getCharisma()));
                chaBox.attrArea.setText(Integer.toString(character.getChaBonus()));
                chaBox.attrField.setText(Integer.toString(character.getIntuition()));
                chaBox.attrArea.setText(Integer.toString(character.getInnBonus()));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {

                JTextComponent ownerField = (JTextComponent) e.getDocument().getProperty("owner");
                String attributeName = (String) e.getDocument().getProperty("attribute");

                // Bonus fields are signed
                if (attributeName.endsWith("Bonus"))
                {
                    SetAttributeByName(attributeName, GetSignedIntValue(ownerField.getText()));
                }
                else {
                    SetAttributeByName(attributeName, Integer.parseInt(ownerField.getText()));
                }

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private class AttributeBox extends JPanel implements Rollable {
                private String attrString;

                private JTextArea attrArea;
                private JTextField attrField;

                public AttributeBox(String attrString, int rows, int cols) {
                    this.attrString = attrString;
                    this.InitializePanel();
                    this.AddComponents(rows, cols);
                }

                private void InitializePanel() {
                    this.setLayout(new GridBagLayout());
                }

                private void AddComponents(int rows, int cols) {
                    GridBagConstraints constraints = new GridBagConstraints();
                    constraints.gridx = 0;
                    constraints.gridy = 0;
                    constraints.weightx = 1;
                    constraints.anchor = GridBagConstraints.CENTER;

                    JLabel attrLabel = new JLabel(this.attrString);
                    this.add(attrLabel, constraints);

                    attrArea = new JTextArea(rows, cols);
                    attrArea.setOpaque(false);
                    constraints.gridy = 1;

                    AbstractDocument areaDoc = (AbstractDocument) attrArea.getDocument();
                    NumericalFilter signedFilter = new NumericalFilter();
                    signedFilter.setNeedsSign(true); signedFilter.setMaxCharacters(3);
                    areaDoc.setDocumentFilter(signedFilter);

                    areaDoc.putProperty("owner", attrArea);
                    areaDoc.putProperty("attribute", this.attrString.concat("Bonus"));
                    areaDoc.addDocumentListener(AttributePanel.this);

                    Font attrFont = new Font(Font.SANS_SERIF, Font.BOLD, 38);
                    attrArea.setFont(attrFont);
                    this.add(attrArea, constraints);

                    attrField = new JTextField((3));
                    AbstractDocument fieldDoc = (AbstractDocument) attrField.getDocument();
                    NumericalFilter numFilter = new NumericalFilter();
                    numFilter.setMinValue(0);
                    fieldDoc.setDocumentFilter(numFilter);

                    fieldDoc.putProperty("owner", attrField);
                    fieldDoc.putProperty("attribute", this.attrString);
                    fieldDoc.addDocumentListener(AttributePanel.this);

                    constraints.gridy = 2;
                    this.add(attrField, constraints);
                }

                @Override
                public int[] GetRoll() {
                    return new int[] { GetSignedIntValue(attrArea.getText()) };
                }
            }
        }

        private class SavingThrowsPanel extends JPanel {
            private CheckBoxPanel strCheckBox;
            private CheckBoxPanel dexCheckBox;
            private CheckBoxPanel conCheckBox;
            private CheckBoxPanel intCheckBox;
            private CheckBoxPanel wisCheckBox;
            private CheckBoxPanel chaCheckBox;

            public SavingThrowsPanel() {
                InitializePanel();
                AddComponents();
            }

            private void InitializePanel() {
                this.setLayout(new GridBagLayout());
                this.setBorder(SectionBorder);
            }

            private void AddComponents() {
                GridBagConstraints constraints = new GridBagConstraints();
                strCheckBox = new CheckBoxPanel("Strength", "Throw");
                dexCheckBox = new CheckBoxPanel("Dexterity", "Throw");
                conCheckBox = new CheckBoxPanel("Constitution", "Throw");
                intCheckBox = new CheckBoxPanel("Intelligence", "Throw");
                wisCheckBox = new CheckBoxPanel("Wisdom", "Throw");
                chaCheckBox = new CheckBoxPanel("Charisma", "Throw");

                strCheckBox.addMouseListener(rightClickListener);
                dexCheckBox.addMouseListener(rightClickListener);
                conCheckBox.addMouseListener(rightClickListener);
                intCheckBox.addMouseListener(rightClickListener);
                wisCheckBox.addMouseListener(rightClickListener);
                chaCheckBox.addMouseListener(rightClickListener);

                constraints.gridx = 0;
                constraints.gridy = 0;
                constraints.anchor = GridBagConstraints.LINE_START;
                constraints.weightx = 1;
                this.add(strCheckBox, constraints);
                constraints.gridy += 1;
                this.add(dexCheckBox, constraints);
                constraints.gridy += 1;
                this.add(conCheckBox, constraints);
                constraints.gridy += 1;
                this.add(intCheckBox, constraints);
                constraints.gridy += 1;
                this.add(wisCheckBox, constraints);
                constraints.gridy += 1;
                this.add(chaCheckBox, constraints);
                constraints.gridy += 1;

                JLabel savingThrowsLabel = new JLabel("Saving Throws");
                savingThrowsLabel.setFont(SectionTitleFont);
                constraints.gridy += 1;
                constraints.anchor = GridBagConstraints.CENTER;
                this.add(savingThrowsLabel, constraints);
            }


        }

        private class CheckBoxPanel extends JPanel implements ItemListener, Rollable {
            private JCheckBox checkBox;
            private JTextField textField;
            private JLabel label;
            private String identifier;

            public CheckBoxPanel(String labelText, String suffix) {
                this.InitializePanel();
                this.AddComponents(labelText, suffix);
            }

            private void InitializePanel() {
                this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            }

            private void AddComponents(String labelText, String suffix) {
                checkBox = new JCheckBox();
                this.add(checkBox);
                checkBox.setSelected(false);
                checkBox.addItemListener(this);

                textField = new JTextField();
                this.add(textField);
                textField.setColumns(3);

                AbstractDocument fieldDoc = (AbstractDocument) textField.getDocument();
                NumericalFilter signedFilter = new NumericalFilter();
                signedFilter.setNeedsSign(true); signedFilter.setMaxCharacters(3);
                fieldDoc.setDocumentFilter(signedFilter);

                this.identifier = labelText.concat(suffix);
                fieldDoc.putProperty("charAttr", this.identifier);
                fieldDoc.putProperty("owner",textField);

                // Has to work with skills and saving throw fields
                fieldDoc.addDocumentListener(CharacterValuesHolder.this);

                label = new JLabel(labelText);
                this.add(label);
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    textField.setFont(boldFieldFont);
                    label.setFont(boldLabelFont);
                } else {
                    textField.setFont(defaultFieldFont);
                    label.setFont(defaultLabelFont);
                }
            }

            public int[] GetRoll() {
                return new int [] { GetSignedIntValue(textField.getText()) };
            }
        }
    }

    private class TraitsPanel extends JPanel {

        private TitleUnderTextArea personalityTraitsArea;
        private TitleUnderTextArea idealsArea;
        private TitleUnderTextArea bondsArea;
        private TitleUnderTextArea flawsArea;
        private TitleUnderTextArea featAndTraitsArea;

        public TraitsPanel() {
            this.InitializePanel();
            this.AddComponents();
        }

        private void InitializePanel() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setBorder(SectionBorder);
        }

        private void AddComponents() {
            personalityTraitsArea = new TitleUnderTextArea("Personality Traits", 6, 25);
            idealsArea = new TitleUnderTextArea("Ideals", 5, 25);
            bondsArea = new TitleUnderTextArea("Bonds", 5, 25);
            flawsArea = new TitleUnderTextArea("Flaws", 5, 25);
            featAndTraitsArea = new TitleUnderTextArea("Features & Traits", 10, 25);

            this.add(personalityTraitsArea);
            this.add(idealsArea);
            this.add(bondsArea);
            this.add(flawsArea);
            this.add(featAndTraitsArea);
        }

        public void UpdateFields() {
            this.personalityTraitsArea.setTextBody(character.getPersonalityTraits());
            this.idealsArea.setTextBody(character.getIdeals());
            this.bondsArea.setTextBody(character.getBonds());
            this.flawsArea.setTextBody(character.getFlaws());
            this.featAndTraitsArea.setTextBody(character.getFeatureTraits());
        }

        private class TitleUnderTextArea extends JPanel implements DocumentListener {

            public void setTextBody(String body) {
                this.textArea.setText(body);
            }

            private JTextArea textArea;

            public TitleUnderTextArea(String labelText, int rows, int cols) {
                this.InitializePanel();
                this.AddComponents(labelText, rows, cols);
            }

            private void InitializePanel() {
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            }

            private void AddComponents(String labelText, int rows, int cols) {
                textArea = new JTextArea(rows, cols);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setMinimumSize(new Dimension(textArea.getPreferredSize()));
                textArea.getDocument().addDocumentListener(this);
                textArea.getDocument().putProperty("owner", textArea);
                textArea.getDocument().putProperty("trait", labelText);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setHorizontalScrollBarPolicy(
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                this.add(scrollPane);

                JLabel label = new JLabel(labelText);
                label.setFont(SectionTitleFont);
                this.add(label);
                label.setAlignmentX(CENTER_ALIGNMENT);

                this.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                SetCharTrait(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                SetCharTrait(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                SetCharTrait(e);
            }

            private void SetCharTrait(DocumentEvent e) {
                JTextArea textArea = (JTextArea) e.getDocument().getProperty("owner");
                String traits = (String) e.getDocument().getProperty("trait");
                try {
                    switch (traits) {
                        case "Personality Traits":
                            character.setPersonalityTraits(textArea.getText());
                            break;
                        case "Ideals":
                            character.setIdeals(textArea.getText());
                            break;
                        case "Bonds":
                            character.setBonds(textArea.getText());
                            break;
                        case "Flaws":
                            character.setFlaws(textArea.getText());
                            break;
                        case "Features & Traits":
                            character.setFeatureTraits(textArea.getText());
                            break;
                        default:
                            throw new Exception("Label not set correctly");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private class RightClickListener extends MouseAdapter {

        public void mousePressed(MouseEvent e){
            if (e.isPopupTrigger()) {
                CreatePopup(e);
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                CreatePopup(e);
            }
        }

        private void CreatePopup(MouseEvent e) {
            int[] rollVals = ((Rollable) e.getSource()).GetRoll();

            if (rollVals.length > 1) {
                int bonusVal = rollVals[0];
                int rollTotal = rollVals[1];

                RightClickPopUp menu = new RightClickPopUp(bonusVal, rollTotal);
                menu.show(e.getComponent(), e.getX(), e.getY());
            } else if (rollVals.length > 0) {
                int bonusVal = rollVals[0];

                RightClickPopUp menu = new RightClickPopUp(bonusVal);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }

            // For now provide no popup if a roll isn't available.
        }

        class RightClickPopUp extends JPopupMenu {
            JMenuItem anItem;

            public RightClickPopUp() {
                anItem = new JMenuItem();
                add(anItem);
            }

            public RightClickPopUp(int bonus) {
                anItem = new JMenuItem(new RollAction(bonus));
                add(anItem);
            }

            public RightClickPopUp(int bonus, int total) {
                anItem = new JMenuItem(new RollAction(bonus, total));
                add(anItem);
            }
        }

        private class RollAction extends AbstractAction {

            private int bonus = 0;
            private int rollTotal = 20;

            public RollAction() {
                super("Roll");
                putValue(MNEMONIC_KEY, KeyEvent.VK_R);
                putValue(SHORT_DESCRIPTION, "Roll the selected attribute.");
            }

            public RollAction(int bonus) {
                super("Roll");

                this.bonus = bonus;
                putValue(MNEMONIC_KEY, KeyEvent.VK_R);
                putValue(SHORT_DESCRIPTION, "Roll the selected attribute.");
            }

            public RollAction(int bonus, int total) {
                super("Roll");

                this.bonus = bonus;
                this.rollTotal = total;
                putValue(MNEMONIC_KEY, KeyEvent.VK_R);
                putValue(SHORT_DESCRIPTION, "Roll the selected attribute.");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                RollAttribute(this.rollTotal, this.bonus);
            }

            private void RollAttribute(int rollValue, int bonus) {
                int roll = rand.nextInt(rollValue) + 1;
                int total = roll + bonus;

                JOptionPane.showMessageDialog(contentPane, String.format("Roll value is: %d + %d = %d", roll, bonus, total));
            }
        }
    }
}