package model.effects;

import model.abilities.*;
import model.world.Champion;

public class PowerUp extends Effect {
	

	public PowerUp(int duration) {
		super("PowerUp", duration, EffectType.BUFF);
		
	}
	
	public void apply(Champion c) {
		for(int i = 0; i < c.getAbilities().size(); i++){
			if(c.getAbilities().get(i).getClass()==DamagingAbility.class){
				DamagingAbility x = (DamagingAbility) (c.getAbilities().get(i));
				x.setDamageAmount((int) (x.getDamageAmount()*1.2));
			}
			else if(c.getAbilities().get(i).getClass()==HealingAbility.class){
				HealingAbility y = (HealingAbility) (c.getAbilities().get(i));
				y.setHealAmount((int) (y.getHealAmount()*1.2));
			}
		}
	}

	
	public void remove(Champion c) {
		for(int i = 0; i < c.getAbilities().size(); i++){
			if(c.getAbilities().get(i).getClass()==DamagingAbility.class){
				DamagingAbility x = (DamagingAbility) (c.getAbilities().get(i));
				x.setDamageAmount((int) (x.getDamageAmount()/1.2));
			}
			else if(c.getAbilities().get(i).getClass()==HealingAbility.class){
				HealingAbility y = (HealingAbility) (c.getAbilities().get(i));
				y.setHealAmount((int) (y.getHealAmount()/1.2));
			}
		}
	}
}
