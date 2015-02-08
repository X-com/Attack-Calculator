package attackCalc;

public class Calculator {
	private int STR = 100;
	
	private double attack;
	private double defence;
	
	private int attackStat;
	private int attackStatUA;
	private int attackStatMC;
	private double armorPen;
	private double attackWeight;
	private double attackDamage;
	private double belief;
	
	private int defenceStat;
	private int defenceStatUA;
	private int defenceStatMC;
	private double defenceWeight;
	private double movementWeight;
	
	private double advantage = 1;
	
	private double projectedStat;
	
	private int b12Q;
	private int swordQ;
	
	private int soakArmor;
	private int absorbArmor;
	
	private double resultingDef = 0;
	private double armorDamage = 0;
	private double armorPenDmg = 0;
	private double dmgTotal = 0;
	private double aboveDMG = 0;
	private double pureDMG = 0;
	private double projected = 0;
	private double projectedTarget = 0;
	
	public Calculator(){
	}
	
	public void setPunch(){
		attackDamage = 50*Math.sqrt((double)STR / 10);
		attackWeight = 0.75;
		armorPen = 0.3;
		attackStat = attackStatUA;
	}
	
	public void setKHTO(){
		attackDamage = 50*Math.sqrt((double)STR * 2 / 10);
		attackWeight = 1;
		armorPen = 0.3;
		attackStat = attackStatUA;
	}
	
	public void setStrangle(){
		attackDamage = 50*Math.sqrt((double)STR * 1.5 / 10);
		attackWeight = 0.8;
		armorPen = 0.3;
		attackStat = attackStatUA;
	}
	
	public void setSting(){
		attackDamage = 300*Math.sqrt(Math.sqrt((double)STR * (double)swordQ) / 10);
		attackWeight = 1;
		armorPen = 0;
		attackStat = attackStatMC;
	}
	
	public void setValorSword(){
		attackDamage = 300*Math.sqrt(Math.sqrt((double)STR * (double)swordQ) / 10);
		attackWeight = 1.75;
		armorPen = 0;
		attackStat = attackStatMC;
	}
	
	public void setValorB12(){
		attackDamage = 500*Math.sqrt(Math.sqrt((double)STR * (double)b12Q) / 10);
		attackWeight = 1.75;
		armorPen = 0;
		attackStat = attackStatMC;
	}
	
	public void setChop(){
		attackDamage = 500*Math.sqrt(Math.sqrt((double)STR * (double)b12Q) / 10);
		attackWeight = 1;
		armorPen = 0;
		attackStat = attackStatMC;
	}
	
	public void setCleave(){
		attackDamage = 500*Math.sqrt(Math.sqrt((double)STR * (double)b12Q) / 10);
		attackWeight = 1.5;
		armorPen = 0.15;
		attackStat = attackStatMC;
	}
	
	public void setNeutral(){
		defenceWeight = 1;
		defenceStat = defenceStatUA;
		projectedStat = attackStatUA;
	}
	
	public void setBloodlust(){
		defenceWeight = 0.5;
		defenceStat = defenceStatUA;
		projectedStat = attackStatUA;
	}
	
	public void setShield(){
		defenceWeight = 1.25;
		defenceStat = defenceStatMC;
		projectedStat = attackStatMC;

	}
	
	public void setMovement(boolean move){
		if(move){
			defenceStat = defenceStatUA;
			projectedStat = attackStatUA;
			if(defenceWeight > 1) defenceWeight = 1;
			movementWeight = 4;
		}else{
			movementWeight = 1;
		}
	}
	
	public void setUAatt(String s){
		try{
			attackStatUA = Integer.parseInt(s);
		}catch(Exception e){
			attackStatUA = 10;
		}
	}
	
	public void setMCatt(String s){
		try{
			attackStatMC = Integer.parseInt(s);
		}catch(Exception e){
			attackStatMC = 10;
		}
	}
	
	public void setB12att(String s){
		try{
			b12Q = Integer.parseInt(s);
		}catch(Exception e){
			b12Q = 10;
		}
	}
	
	public void setSwordatt(String s){
		try{
			swordQ = Integer.parseInt(s);
		}catch(Exception e){
			swordQ = 10;
		}
	}
	
	public void setStrenthatt(String s){
		try{
			STR = Integer.parseInt(s);
		}catch(Exception e){
			STR = 10;
		}
	}
	
	public void setUAdef(String s){
		try{
			defenceStatUA = Integer.parseInt(s);
		}catch(Exception e){
			defenceStatUA = 10;
		}
	}
	
	public void setMCdef(String s){
		try{
			defenceStatMC = Integer.parseInt(s);
		}catch(Exception e){
			defenceStatMC = 10;
		}
	}
	
	public void setSoak(String s){
		try{
			soakArmor = Integer.parseInt(s);
		}catch(Exception e){
			soakArmor = 10;
		}
	}
	
	public void setAbsorb(String s){
		try{
			absorbArmor = Integer.parseInt(s);
		}catch(Exception e){
			absorbArmor = 10;
		}
	}
	
	public void setAttack(int i){
		attack = (double)((double)i/100);
	}
	
	public void setDefence(int i){
		defence = (double)((double)i/100);
	}
	
	public void setAdvantage(float i){
		if(i <= 5)
			advantage = 0.5 + i *.1;
		else if(i == 6)
			advantage = 1;
		else
			advantage = 1 + (i - 5) * 0.2;
	}
	
	public void setBeleif(int i){
		belief = i * 0.04 + 0.8;
	}
	
	public int getResultingDef(){
		return (int)resultingDef;
	}
	
	public int getArmorDamage(){
		return (int)armorDamage;
	}
	
	public int getArmorPen(){
		return (int)armorPenDmg;
	}
	
	public int getTotalDMG(){
		return (int)dmgTotal;
	}
	
	public int getAboveDMG(){
		return (int)aboveDMG;
	}
	
	public int getPureDMG(){
		return (int)pureDMG;
	}
	
	public int getProjected(){
		return (int)projected;
	}
	
	public int getProjectedTarget(){
		return (int)projectedTarget;
	}
	
	public int getDMG(){
		return (int)(attackDamage * belief);
	}
	
	public int getAC(){
		return soakArmor + absorbArmor;
	}
	
	public int getDestroy(){
		return (int)(Math.sqrt(STR) );
	}
	
	public void calculate(){
		double Aweight = (double)((double)attackStat * (double)attackWeight * (double)advantage * (double)movementWeight);
		double Dweight = (double)((double)defenceStat * (double)defenceWeight);
		double weight = (Math.sqrt(Aweight / Dweight) / 2);
		double weightedAttack = attack * weight;
		double attackedDefence = defence - weightedAttack;
		double damagePers = 0;
		
		if(attackedDefence < 0){
			attackedDefence = 0;
			damagePers = (weightedAttack - defence ) / weightedAttack;
		}
		
		if(damagePers > 0){
			double dmg = damagePers * attackDamage * belief;
			double soaked = dmg - (double)soakArmor;
			double aboveArmorDMG = dmg - (double)soakArmor - (double)absorbArmor;
			
			if(aboveArmorDMG < 0 ) aboveArmorDMG = 0;
			
			if(soaked > 0){
				armorDamage = soaked;
				
				if(armorDamage > absorbArmor){
					armorDamage = (double)absorbArmor;
				}
			}else{
				armorDamage = 0;
			}
			
			if(armorPen > 0){
				double AC = (double)soakArmor + (double)absorbArmor;
				if(dmg > AC){
					armorPenDmg = AC * (double)armorPen;
				}else{
					armorPenDmg = dmg * (double)armorPen;
				}
			}else{
				armorPenDmg = 0;
			}
			
			pureDMG = dmg;
			aboveDMG = aboveArmorDMG;
			dmgTotal = armorPenDmg + aboveArmorDMG;
		}else{
			armorDamage = 0;
			armorPenDmg = 0;
			dmgTotal = 0;
			aboveDMG = 0;
			pureDMG = 0;
		}
		
		try{
			double result = Math.pow( (((1 - defence) * 2 ) / attack), 2);
			double def = result * (double)projectedStat * (double)defenceWeight;
			double att = (double)attackWeight * (double)advantage * (double)movementWeight;
			projected =  def / att;
		}catch(Exception e){
			projected = 0;
		}
		
		try{
			double result = Math.pow( (((1 - defence) * 2 ) / attack), 2);
			double def = result * (double)projectedStat * (double)defenceWeight;
			projectedTarget =  Aweight / ( result * (double)defenceWeight );
		}catch(Exception e){
			projectedTarget = 0;
		}
		
		resultingDef = Math.ceil(attackedDefence * 100);
	}
}