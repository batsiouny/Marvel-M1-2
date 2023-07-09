package model.world;

import java.util.ArrayList;

import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;



public class Hero extends Champion implements Damageable{

	public Hero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}
	
	public boolean checkDebuff(ArrayList<Effect> appliedeffects){
		for(int j=0; j<appliedeffects.size();j++){
			
			if(appliedeffects.get(j).getType()==EffectType.DEBUFF){
				return true;
			}
			
		}
		return false;
	}
	
	public void removeDebuff(ArrayList<Effect> appliedeffects, Champion x){
		for(int j=0; j<appliedeffects.size();j++){
			
			if(appliedeffects.get(j).getType()==EffectType.DEBUFF){
				appliedeffects.get(j).remove(x);
				appliedeffects.remove(j);
			}
		
		}
	}
	
	public void useLeaderAbility(ArrayList<Champion> targets){
		for(int i=0; i < targets.size(); i++){
			Champion x = targets.get(i);
			while(checkDebuff(x.getAppliedEffects())){
				removeDebuff(x.getAppliedEffects(), x);
			}
			Embrace embrace = new Embrace(2);
			x.getAppliedEffects().add(embrace);
			embrace.apply(x);
			
		}
	}
	
}
