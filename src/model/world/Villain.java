package model.world;

import java.util.ArrayList;


public class Villain extends Champion implements Damageable{

	public Villain(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	
	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(int i=0; i < targets.size(); i++){
			
			Champion x = targets.get(i);
			double ratio = x.getCurrentHP()/x.getMaxHP();
			
			if(ratio<0.3){
				x.setCondition(Condition.KNOCKEDOUT);
				x.setCurrentHP(0);
			}
			
		}
	}
		
}

	
	

