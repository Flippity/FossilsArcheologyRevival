package fossilsarcheology.server.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import fossilsarcheology.server.entity.ai.DinoAIAttackOnCollide;
import fossilsarcheology.server.entity.ai.DinoAIAvoidEntity;
import fossilsarcheology.server.entity.ai.DinoAIFollowOwner;
import fossilsarcheology.server.entity.ai.DinoAIHunt;
import fossilsarcheology.server.entity.ai.DinoAILookIdle;
import fossilsarcheology.server.entity.ai.DinoAIWander;
import fossilsarcheology.server.entity.ai.DinoAIWatchClosest;
import fossilsarcheology.server.entity.mob.test.DinoAIFeeder;
import fossilsarcheology.server.entity.mob.test.EntityNewPrehistoric;
import fossilsarcheology.server.enums.EnumPrehistoric;
import fossilsarcheology.server.enums.EnumPrehistoricAI;
import fossilsarcheology.server.handler.FossilAchievementHandler;

public class EntityDodo extends EntityNewPrehistoric {

    public EntityDodo(World world) {
        super(world, EnumPrehistoric.Dodo, 1, 1, 4, 10, 0.2, 0.25);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIPanic(this, 1D));
        this.tasks.addTask(4, new DinoAIAvoidEntity(this, 16.0F, 1D, 1D));
        this.tasks.addTask(5, new DinoAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(6, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new DinoAIFeeder(this, 16));
        this.tasks.addTask(8, new DinoAIWander(this, 1.0D));
        this.tasks.addTask(9, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new DinoAILookIdle(this));
        this.targetTasks.addTask(4, new DinoAIHunt(this, 200, false));
        this.setSize(0.8F, 0.7F);
        this.nearByMobsAllowed = 10;
        minSize = 0.5F;
        maxSize = 1F;
        teenAge = 2;
        developsResistance = false;
        breaksBlocks = false;
        hasTeenTexture = false;
    }

    @Override
    public int getAttackLength() {
        return 25;
    }

    @Override
    public void setSpawnValues() {
    }

    @Override
    public EnumPrehistoricAI.Activity aiActivityType() {

        return EnumPrehistoricAI.Activity.DIURINAL;
    }

    @Override
    public EnumPrehistoricAI.Attacking aiAttackType() {

        return EnumPrehistoricAI.Attacking.BASIC;
    }

    @Override
    public EnumPrehistoricAI.Climbing aiClimbType() {

        return EnumPrehistoricAI.Climbing.NONE;
    }

    @Override
    public EnumPrehistoricAI.Following aiFollowType() {

        return EnumPrehistoricAI.Following.NORMAL;
    }

    @Override
    public EnumPrehistoricAI.Jumping aiJumpType() {

        return EnumPrehistoricAI.Jumping.BASIC;
    }

    @Override
    public EnumPrehistoricAI.Response aiResponseType() {

        return EnumPrehistoricAI.Response.SCARED;
    }

    @Override
    public EnumPrehistoricAI.Stalking aiStalkType() {

        return EnumPrehistoricAI.Stalking.NONE;
    }

    @Override
    public EnumPrehistoricAI.Taming aiTameType() {

        return EnumPrehistoricAI.Taming.FEEDING;
    }

    @Override
    public EnumPrehistoricAI.Untaming aiUntameType() {

        return EnumPrehistoricAI.Untaming.NONE;
    }

    @Override
    public EnumPrehistoricAI.Moving aiMovingType() {

        return EnumPrehistoricAI.Moving.WALKANDGLIDE;
    }

    @Override
    public EnumPrehistoricAI.WaterAbility aiWaterAbilityType() {

        return EnumPrehistoricAI.WaterAbility.NONE;
    }

    @Override
    public boolean doesFlock() {
        return false;
    }

    @Override
    public Item getOrderItem() {
        return Items.stick;
    }

    @Override
    public int getAdultAge() {
        return 5;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 12 && this.getAttackTarget() != null) {
            this.attackEntityAsMob(this.getAttackTarget());
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (this.getAttackBounds().intersectsWith(entity.boundingBox)) {
            if (this.getAnimation() == NO_ANIMATION) {
                this.setAnimation(ATTACK_ANIMATION);
                return false;
            }

            if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 12) {
                IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.attackDamage);
                boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) iattributeinstance.getAttributeValue());
                if (entity.ridingEntity != null) {
                    if (entity.ridingEntity == this) {
                        entity.mountEntity(null);
                    }
                }
                return flag;
            }
        }
        return false;
    }

    @Override
    protected String getLivingSound() {
        return "fossil:dodo_living";
    }

    @Override
    protected String getHurtSound() {
        return "fossil:dodo_hurt";
    }

    @Override
    protected String getDeathSound() {
        return "fossil:dodo_death";
    }

    public int getMaxHunger() {
        return 50;
    }
    
	public void onDeath(DamageSource source) {
		if (source.getEntity() != null && source.getEntity() instanceof EntityPlayer) {
			((EntityPlayer) source.getEntity()).triggerAchievement(FossilAchievementHandler.deadDodo);
		}
	}
    
	@Override
	public boolean canBeRidden() {
		return false;
	}
}
