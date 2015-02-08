package attackCalc;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import java.util.prefs.Preferences;

public class Viewer implements ItemListener, ActionListener, AdjustmentListener{
	private JFrame frame = new JFrame("Haven combat calculator 1.4");
	Calculator calc;
	private Preferences prefs;
	
	private Scrollbar scrollAttack = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 101);
	private Scrollbar scrollDefence = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 101);
	private Scrollbar scrollAdvantage = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 101);
	private Scrollbar scrollBelief = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 11);
	
	private JLabel lblAttack = new JLabel("Attack bar:");
	private JLabel lblDefence = new JLabel("Defence bar:");
	private JLabel lblAdvantage = new JLabel("Advantage:");
	private JLabel lblBeleif = new JLabel("Peace/Marsh:");
	
	private JTextField UATextAttack = new JTextField();
	private JTextField MCTextAttack = new JTextField();
	private JTextField B12Text = new JTextField();
	private JTextField SwordText = new JTextField();
	private JTextField strenthText = new JTextField();
	
	private JTextField UATextDefence = new JTextField();
	private JTextField MCTextDefence = new JTextField();
	private JTextField soakText = new JTextField();
	private JTextField absorbText = new JTextField();
	
	private JRadioButton punchAttack = new JRadioButton("Punch");
	private JRadioButton khtoAttack = new JRadioButton("KHTO");
	private JRadioButton strangleAttack = new JRadioButton("Strangle");
	private JRadioButton stingAttack = new JRadioButton("Sting");
	private JRadioButton valorousAttackSword = new JRadioButton("SS Valor");
	private JRadioButton valorousAttack = new JRadioButton("B12 Valor");
	private JRadioButton chopAttack = new JRadioButton("Chop");
	private JRadioButton cleaveAttack = new JRadioButton("Cleave");
	
	private JRadioButton neutralStance = new JRadioButton("Neutral Stance");
	private JRadioButton bloodlustStance = new JRadioButton("Bloodlust Stance");
	private JRadioButton shieldStance = new JRadioButton("Shield Stance");
	private JCheckBox movement = new JCheckBox("Movement Hit");
	
	private JLabel lblDefenceTot = new JLabel("Defence: ");
	private JLabel lblPen = new JLabel("Armor Penetration: ");
	private JLabel lblArmorDMG = new JLabel("Armor Damage: ");
	private JLabel lblRedDMG = new JLabel("Above Armor Damage: ");
	private JLabel lblTotDMG = new JLabel("Total Red Damage: ");
	private JLabel lblProjected1 = new JLabel("Projected Enemy UA/MC: ");
	private JLabel lblProjected2 = new JLabel("You Hitting The Target: ");
	private JLabel lblProjected3 = new JLabel("Target Hittin You: ");
	private JLabel lblDMG = new JLabel("DMG: ");
	private JLabel lblAC = new JLabel("AC: ");
	private JLabel lblDestroy = new JLabel("Destroy: ");
	
	private JButton saveButton = new JButton("Save");
	
	private KeyAdapter key = new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            char caracter = e.getKeyChar();
            if((caracter < '0') || (caracter > '9')){
                e.consume();
            }
        }
    };
	
	public Viewer(int x, int y, Calculator cl){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(x, y);
	    frame.add(viewerPannel());
	    frame.pack();
	    frame.setResizable(false);
	    
	    calc = cl;
	    prefs = Preferences.userRoot().node(this.getClass().getName());
	    
	    loadData();
	    updateInfo();
	    loadLabels();
	    
	    frame.setVisible(true);
	}
	
	private JPanel viewerPannel(){
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.setPreferredSize(new Dimension(440, 600));
		
		panelMain.add(titleLabel(), BorderLayout.NORTH);
		panelMain.add(mainMeat(), BorderLayout.CENTER);
		panelMain.add(copyright(), BorderLayout.SOUTH);
		
		return panelMain;
	}
	
	private JLabel copyright(){
		JLabel titleLable = new JLabel("Haven Combat Calculator by Xcom v1.4");
		titleLable.setHorizontalAlignment(SwingConstants.RIGHT);
		titleLable.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		
		return titleLable;
	}
	
	private JLabel titleLabel(){
		JLabel titleLable = new JLabel("Combat Calculator");
		titleLable.setHorizontalAlignment(SwingConstants.CENTER);
		
		return titleLable;
	}
	
	private JPanel mainMeat(){
		JPanel panel = new JPanel(null);
		
		panel.add(resultPanel(20, 30));
		panel.add(scrollBars(20, 230));
		panel.add(statPanel(300, 30));
		panel.add(buttonPanelAtt(20, 390));
	    panel.add(buttonPanelDef(130, 390));
	    panel.add(buttonPanel(320,470) );
	    panel.add(descriptionPanel(130,530) );
	    
		
		return panel;
	}
	
	void loadLabels(){
		float adv = ((float)scrollAdvantage.getValue() / 10) - 5;
		lblAdvantage.setText( String.format("Advantage: %.1f", adv ) );
		
		lblAttack.setText( String.format("Attack bar: %s", scrollAttack.getValue() ) );
		lblDefence.setText( String.format("Defence bar: %s", scrollDefence.getValue() ) );
		lblBeleif.setText( String.format("Peace/Marsh: %s", scrollBelief.getValue() - 5 ) );
	}
	
	private JPanel descriptionPanel(int x, int y){
		JPanel descPanel = new JPanel(null);
		descPanel.setBounds(x, y, 300, 60);
		
		JLabel lblline1 = new JLabel("All calculations based on soldier sword or B12.");
		lblline1.setBounds(0, 0, 300, 20);
		JLabel lblline2 = new JLabel("Movement always takes defenders UA into account.");
		lblline2.setBounds(0, 20, 300, 20);
		//JLabel lblline3 = new JLabel("text 3");
		//lblline3.setBounds(0, 40, 300, 20);
	    
		descPanel.add(lblline1);
		descPanel.add(lblline2);
		//descPanel.add(lblline3);
		
	    return descPanel;
	}
	
	private JPanel buttonPanel(int x, int y){
		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBounds(x, y, 80, 40);
		
		saveButton.setBounds(0, 0, 80, 40);
	    saveButton.addActionListener(this);
	    buttonPanel.add(saveButton);
	    
	    return buttonPanel;
	}
	
	private JPanel scrollBars(int x, int y){
		JPanel scrollPanel = new JPanel(null);
		scrollPanel.setBounds(x, y, 250, 150);
		
		
		scrollAttack.setBounds(0, 20, 250, 20);
		scrollDefence.setBounds(0, 70, 250, 20);
		scrollAdvantage.setBounds(0, 120, 250, 20);
		
		scrollPanel.add(scrollAttack);
		scrollPanel.add(scrollDefence);
		scrollPanel.add(scrollAdvantage);
		
	    lblAttack.setBounds(100, 0, 250, 20);
	    lblDefence.setBounds(100, 50, 250, 20);
	    lblAdvantage.setBounds(100, 100, 250, 20);
	    
	    scrollPanel.add(lblAttack);
	    scrollPanel.add(lblDefence);
	    scrollPanel.add(lblAdvantage);
	    
	    scrollAttack.addAdjustmentListener(this);
	    scrollDefence.addAdjustmentListener(this);
	    scrollAdvantage.addAdjustmentListener(this);
	    //scrollDefence.getHorizontalScrollBar().addAdjustmentListener(this);
	    //scrollAttack.getVerticalScrollBar().addAdjustmentListener(this);
		
		return scrollPanel;
	}
	
	private JPanel statPanel(int x, int y){
		JPanel statPanel = new JPanel(null);
		statPanel.setBounds(x, y, 120, 400);
		
		statPanel.add(statPanelAttack(0,0) );
		statPanel.add(statPanelDefence(0,250) );
		
		return statPanel;
	}
	
	private JPanel statPanelAttack(int x, int y){
		JPanel statPanelAttack = new JPanel(null);
		statPanelAttack.setBounds(x, y, 120, 240);
		statPanelAttack.setBorder(BorderFactory.createTitledBorder("Attacker Stats"));
		
		JLabel lblUA = new JLabel("UA");
		lblUA.setBounds(10, 20, 40, 20);
		statPanelAttack.add(lblUA);
		
		JLabel lblMC = new JLabel("MC");
		lblMC.setBounds(60, 20, 40, 20);
		statPanelAttack.add(lblMC);
		
		
		UATextAttack.setBounds(10, 40, 40, 23);
		statPanelAttack.add(UATextAttack);
		
		MCTextAttack.setBounds(60, 40, 40, 23);
		statPanelAttack.add(MCTextAttack);
		
		JLabel weaponQ = new JLabel("Weapon Q");
		weaponQ.setBounds(25, 70, 60, 20);
		statPanelAttack.add(weaponQ);
		
		JLabel lblB12 = new JLabel("B12");
		lblB12.setBounds(10, 90, 40, 20);
		statPanelAttack.add(lblB12);
		
		JLabel lblSword = new JLabel("S.Sword");
		lblSword.setBounds(60, 90, 60, 20);
		statPanelAttack.add(lblSword);
		
		B12Text.setBounds(10, 110, 40, 23);
		statPanelAttack.add(B12Text);
		
		SwordText.setBounds(60, 110, 40, 23);
		statPanelAttack.add(SwordText);
		
		JLabel lblStrenth = new JLabel("Strenth");
		lblStrenth.setBounds(35, 140, 60, 20);
		statPanelAttack.add(lblStrenth);
		
		strenthText.setBounds(35, 160, 40, 23);
		statPanelAttack.add(strenthText);
		
		lblBeleif.setBounds(10, 190, 95, 20);
		scrollBelief.setBounds(10, 210, 100, 20);
		
		statPanelAttack.add(lblBeleif);
		statPanelAttack.add(scrollBelief);
		
		scrollBelief.addAdjustmentListener(this);
		
		UATextAttack.addActionListener(this);
		MCTextAttack.addActionListener(this);
		B12Text.addActionListener(this);
		SwordText.addActionListener(this);
		strenthText.addActionListener(this);
		
		UATextAttack.setDocument(new JTextFieldLimit(5));
		MCTextAttack.setDocument(new JTextFieldLimit(5));
		B12Text.setDocument(new JTextFieldLimit(4));
		SwordText.setDocument(new JTextFieldLimit(4));
		strenthText.setDocument(new JTextFieldLimit(5));
		
		UATextAttack.addKeyListener(key);
		MCTextAttack.addKeyListener(key);
		B12Text.addKeyListener(key);
		SwordText.addKeyListener(key);
		strenthText.addKeyListener(key);
	    
		return statPanelAttack;
	}
	
	private JPanel statPanelDefence(int x, int y){
		JPanel statPanelDefence = new JPanel(null);
		statPanelDefence.setBounds(x, y, 120, 140);
		statPanelDefence.setBorder(BorderFactory.createTitledBorder("Defender Stats"));
		
		/*JLabel lblAttackStat = new JLabel("Defender Stats");
		lblAttackStat.setBounds(0, 0, 100, 20);
		statPanelDefence.add(lblAttackStat);*/
		
		JLabel lblUA = new JLabel("UA");
		lblUA.setBounds(10, 20, 40, 20);
		statPanelDefence.add(lblUA);
		
		JLabel lblMC = new JLabel("MC");
		lblMC.setBounds(60, 20, 40, 20);
		statPanelDefence.add(lblMC);
		
		UATextDefence.setBounds(10, 40, 40, 23);
		statPanelDefence.add(UATextDefence);
		
		MCTextDefence.setBounds(60, 40, 40, 23);
		statPanelDefence.add(MCTextDefence);
		
		JLabel weaponQ = new JLabel("Armor soak/absorb");
		weaponQ.setBounds(10, 70, 100, 20);
		statPanelDefence.add(weaponQ);
		
		JLabel lblSoak = new JLabel("Soak");
		lblSoak.setBounds(10, 90, 40, 20);
		statPanelDefence.add(lblSoak);
		
		JLabel lblAbsorb = new JLabel("Absorb");
		lblAbsorb.setBounds(60, 90, 60, 20);
		statPanelDefence.add(lblAbsorb);
		
		
		soakText.setBounds(10, 110, 40, 23);
		statPanelDefence.add(soakText);
		
		absorbText.setBounds(60, 110, 40, 23);
		statPanelDefence.add(absorbText);
		
		UATextDefence.addActionListener(this);
		MCTextDefence.addActionListener(this);
		soakText.addActionListener(this);
		absorbText.addActionListener(this);
		
		UATextDefence.setDocument(new JTextFieldLimit(5));
		MCTextDefence.setDocument(new JTextFieldLimit(5));
		soakText.setDocument(new JTextFieldLimit(4));
		absorbText.setDocument(new JTextFieldLimit(4));
		
		UATextDefence.addKeyListener(key);
		MCTextDefence.addKeyListener(key);
		soakText.addKeyListener(key);
		absorbText.addKeyListener(key);
	    
		return statPanelDefence;
	}
	
	private JPanel buttonPanelAtt(int x, int y){
		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBounds(x, y, 100, 190);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Attack Type"));
	    
	    ButtonGroup buttonGroupAtt = new ButtonGroup();
	    
	    punchAttack.setBounds(10, 20, 80, 20);
	    punchAttack.setSelected(true);
	    khtoAttack.setBounds(10, 40, 80, 20);
	    strangleAttack.setBounds(10, 60, 80, 20);
	    stingAttack.setBounds(10, 80, 80, 20);
	    valorousAttackSword.setBounds(10, 100, 80, 20);
	    valorousAttack.setBounds(10, 120, 80, 20);
	    chopAttack.setBounds(10, 140, 80, 20);
	    cleaveAttack.setBounds(10, 160, 80, 20);
	    
	    buttonGroupAtt.add(punchAttack);
	    buttonGroupAtt.add(khtoAttack);
	    buttonGroupAtt.add(strangleAttack);
	    buttonGroupAtt.add(stingAttack);
	    buttonGroupAtt.add(valorousAttackSword);
	    buttonGroupAtt.add(valorousAttack);
	    buttonGroupAtt.add(chopAttack);
	    buttonGroupAtt.add(cleaveAttack);
	    
	    buttonPanel.add(punchAttack);
	    buttonPanel.add(khtoAttack);
	    buttonPanel.add(strangleAttack);
	    buttonPanel.add(stingAttack);
	    buttonPanel.add(valorousAttackSword);
	    buttonPanel.add(valorousAttack);
	    buttonPanel.add(chopAttack);
	    buttonPanel.add(cleaveAttack);
	    
	    punchAttack.addItemListener(this);
	    khtoAttack.addItemListener(this);
	    strangleAttack.addItemListener(this);
	    stingAttack.addItemListener(this);
	    valorousAttackSword.addItemListener(this);
	    valorousAttack.addItemListener(this);
	    chopAttack.addItemListener(this);
	    cleaveAttack.addItemListener(this);
	    
	    return buttonPanel;
	}
	
	private JPanel buttonPanelDef(int x, int y){
		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBounds(x, y, 140, 110);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Opponents stance"));
		
	    neutralStance.setBounds(10, 20, 120, 20);
	    neutralStance.setSelected(true);
	    bloodlustStance.setBounds(10, 40, 125, 20);
	    shieldStance.setBounds(10, 60, 120, 20);
	    movement.setBounds(10, 80, 120, 20);
	    
	    ButtonGroup buttonGroupDef = new ButtonGroup();
	    
	    buttonGroupDef.add(neutralStance);
	    buttonGroupDef.add(bloodlustStance);
	    buttonGroupDef.add(shieldStance);
	    
	    buttonPanel.add(neutralStance);
	    buttonPanel.add(bloodlustStance);
	    buttonPanel.add(shieldStance);
	    buttonPanel.add(movement);
	    
	    neutralStance.addItemListener(this);
	    bloodlustStance.addItemListener(this);
	    shieldStance.addItemListener(this);
	    movement.addItemListener(this);
	    
	    return buttonPanel;
	}
	
	private JPanel resultPanel(int x, int y){
		JPanel resultPanel = new JPanel(null);
		resultPanel.setBounds(x, y, 270, 190);
		
		resultPanel.setBorder(BorderFactory.createTitledBorder("Attack Calculation"));
		
		/*JLabel lblTitle = new JLabel("Attack Calculation");
		lblTitle.setBounds(0, 0, 120, 20);
		resultPanel.add(lblTitle);*/
		
		lblDefenceTot.setBounds(10, 20, 200, 20);
		resultPanel.add(lblDefenceTot);
		
		lblPen.setBounds(10, 40, 200, 20);
		resultPanel.add(lblPen);
		
		lblArmorDMG.setBounds(10, 60, 200, 20);
		resultPanel.add(lblArmorDMG);
		
		lblRedDMG.setBounds(10, 80, 200, 20);
		resultPanel.add(lblRedDMG);
		
		lblTotDMG.setBounds(10, 100, 200, 20);
		resultPanel.add(lblTotDMG);
		
		lblProjected1.setBounds(50, 120, 200, 20);
		resultPanel.add(lblProjected1);
		
		lblProjected2.setBounds(50, 140, 200, 20);
		resultPanel.add(lblProjected2);
		
		lblProjected3.setBounds(50, 160, 200, 20);
		resultPanel.add(lblProjected3);
		
		
		lblDMG.setBounds(180, 40, 70, 20);
		resultPanel.add(lblDMG);
		
		lblAC.setBounds(180, 60, 70, 20);
		resultPanel.add(lblAC);
		
		lblDestroy.setBounds(180, 80, 70, 20);
		resultPanel.add(lblDestroy);
		
		return resultPanel;
	}
	
	public void updateDamage(){
		int pure = calc.getPureDMG();
		int result = calc.getResultingDef();
		if(result > 0)
			lblDefenceTot.setText( String.format("Defence: %s", result ) );
		else
			lblDefenceTot.setText( String.format("Defence: %s    Hit: %s", result, pure) );
		
		lblPen.setText( String.format("Armor Penetration: %s", calc.getArmorPen() ) );
		lblArmorDMG.setText( String.format("Armor Damage: %s", calc.getArmorDamage() ) );
		lblRedDMG.setText( String.format("Above Armor Damage: %s", calc.getAboveDMG() ) );
		lblTotDMG.setText( String.format("Total Red Damage: %s", calc.getTotalDMG() ) );
		lblProjected2.setText( String.format("You Hitting The Target: %s", calc.getProjectedTarget() ) );
		lblProjected3.setText( String.format("Target Hittin You: %s", calc.getProjected() ) );
		
		lblDMG.setText( String.format("DMG: %s", calc.getDMG() ) );
		lblAC.setText( String.format("AC: %s", calc.getAC() ) );
		lblDestroy.setText( String.format("Destroy: %s", calc.getDestroy() ) );
	}
	
	public void updateInfo(){
		checkScrollbars();
		setAttackValues();
		setDefenceValues();
		checkAttackerState();
		checkDefenderState();
		calc.calculate();
		updateDamage();
	}
	
	public void itemStateChanged(ItemEvent e) {
		updateInfo();
	}
	
	private void checkAttackerState(){
		if(punchAttack.isSelected()){
			calc.setPunch();
		}else if(khtoAttack.isSelected()) {
			calc.setKHTO();
		}else if(strangleAttack.isSelected()) {
			calc.setStrangle();
		}else if(stingAttack.isSelected()) {
			calc.setSting();
		}else if(valorousAttackSword.isSelected()) {
			calc.setValorSword();
		}else if(valorousAttack.isSelected()) {
			calc.setValorB12();
		}else if(chopAttack.isSelected()) {
			calc.setChop();
		}else if(cleaveAttack.isSelected()) {
			calc.setCleave();
		}
	}
	
	void checkDefenderState(){
		if(neutralStance.isSelected()){
			calc.setNeutral();
		}else if(bloodlustStance.isSelected()) {
			calc.setBloodlust();
		}else if(shieldStance.isSelected()) {
			calc.setShield();
		}
		
		calc.setMovement(movement.isSelected());
	}
	
	public void actionPerformed(ActionEvent e) {
		Object event = e.getSource();
		if(saveButton == event){
			saveAllFields();
		}
		updateInfo();
	}
	
	private void setAttackValues(){
		calc.setUAatt(UATextAttack.getText() );
		calc.setMCatt(MCTextAttack.getText() );
		calc.setB12att(B12Text.getText() );
		calc.setSwordatt(SwordText.getText() );
		calc.setStrenthatt(strenthText.getText() );
	}
	
	private void setDefenceValues(){
		calc.setUAdef(UATextDefence.getText() );
		calc.setMCdef(MCTextDefence.getText() );
		calc.setSoak(soakText.getText() );
		calc.setAbsorb(absorbText.getText() );
	}
	
	public void adjustmentValueChanged(AdjustmentEvent evt){
		Scrollbar s = (Scrollbar)evt.getAdjustable();
		
		if(s == scrollAttack){
			lblAttack.setText( String.format("Attack bar: %s", s.getValue() ) );
		}else if(s == scrollDefence){
			lblDefence.setText( String.format("Defence bar: %s", s.getValue() ) );
		}else if(s == scrollAdvantage){
			//lblAdvantage.setText( String.format("Advantage: %s", s.getValue() - 5 ) );
			float adv = ((float)scrollAdvantage.getValue() / 10) - 5;
			lblAdvantage.setText( String.format("Advantage: %.1f", adv ) );
		}else if(s == scrollBelief){
			lblBeleif.setText( String.format("Peace/Marsh: %s", s.getValue() - 5 ) );
		}
		
		updateInfo();
	}
	
	public void checkScrollbars(){
		calc.setAttack(scrollAttack.getValue() );
		calc.setDefence(scrollDefence.getValue() );
		calc.setAdvantage((float)scrollAdvantage.getValue() / 10 );
		calc.setBeleif(scrollBelief.getValue() );
	}
	
	private void saveAllFields(){
		prefs.put("UAatt", UATextAttack.getText() );
		prefs.put("MCatt", MCTextAttack.getText() );
		prefs.put("B12att", B12Text.getText() );
		prefs.put("Swordatt", SwordText.getText() );
		prefs.put("STRatt", strenthText.getText() );
		
		prefs.put("UAdef", UATextDefence.getText() );
		prefs.put("MCdef", MCTextDefence.getText() );
		prefs.put("soakdef", soakText.getText() );
		prefs.put("absdef", absorbText.getText() );
		
		prefs.putInt("att", scrollAttack.getValue() );
		prefs.putInt("def", scrollDefence.getValue() );
		prefs.putInt("adv", scrollAdvantage.getValue() );
		prefs.putInt("belif", scrollBelief.getValue() );
	}
	
	private void loadData(){
		UATextAttack.setText(prefs.get("UAatt", "10") );
		MCTextAttack.setText(prefs.get("MCatt", "10") );
		B12Text.setText(prefs.get("B12att", "10") );
		SwordText.setText(prefs.get("Swordatt", "10") );
		strenthText.setText(prefs.get("STRatt", "10") );
		
		UATextDefence.setText(prefs.get("UAdef", "10") );
		MCTextDefence.setText(prefs.get("MCdef", "10") );
		soakText.setText(prefs.get("soakdef", "10") );
		absorbText.setText(prefs.get("absdef", "10") );
		
		scrollAttack.setValue(prefs.getInt("att", 100));
		scrollDefence.setValue(prefs.getInt("def", 100));
		scrollAdvantage.setValue(prefs.getInt("adv", 50) );
		scrollBelief.setValue(prefs.getInt("belif", 5));
	}
	
	public class JTextFieldLimit extends PlainDocument {
		private int limit;
		
		JTextFieldLimit(int limit){
			super();
			this.limit = limit;
		}
		
		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}
		
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;
			
			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}
}
