package cn.howxu.sqj.common.item;

import cn.howxu.sqj.SylviaQingJiao;
import com.brandon3055.draconicevolution.client.render.item.RenderModularSword;
import com.brandon3055.draconicevolution.entity.GuardianCrystalEntity;
import com.brandon3055.draconicevolution.entity.guardian.DraconicGuardianEntity;
import com.brandon3055.draconicevolution.entity.guardian.DraconicGuardianPartEntity;
import com.brandon3055.draconicevolution.init.DEDamage;
import com.brandon3055.draconicevolution.items.equipment.ModularStaff;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/4/8 13:26
 */
public class Sword extends SwordItem {
    public Sword(Properties properties) {
        super(new Stlvia(), new Properties().rarity(Rarity.EPIC));
    }

    // Anim
    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
        ItemStack item = p_77659_2_.getItemInHand(p_77659_3_);
        InteractionHand otherhand = p_77659_3_ == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;

        ItemStack otheritem = p_77659_2_.getItemInHand(otherhand);

        if (otheritem.canPerformAction(net.neoforged.neoforge.common.ItemAbilities.SHIELD_BLOCK) && !p_77659_2_.getCooldowns().isOnCooldown(otheritem.getItem())) {
            return InteractionResultHolder.fail(item);
        } else {
            p_77659_2_.startUsingItem(p_77659_3_);
            return InteractionResultHolder.consume(item);
        }
    }

    public static float getUseTime(int ticks) {
        float f = (float) ticks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) f = 1.0F;
        return f;
    }

    // core
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        // server 判定
        if (!level.isClientSide()) {
            if (level.dimension() == Level.END && livingEntity instanceof Player player && level instanceof ServerLevel serverLevel){
                AABB boundingBox = AABB.ofSize(player.position(), 1024, 1024, 128);
                List<Entity> sphereEntities = serverLevel.getEntitiesOfClass(
                        Entity.class,
                        boundingBox,
                        entity -> entity instanceof DraconicGuardianEntity || entity instanceof GuardianCrystalEntity
                );
                sphereEntities.forEach(entity -> {
                    var lighting = EntityType.LIGHTNING_BOLT.create(level);
                    lighting.setPos(entity.position());
                    serverLevel.addFreshEntity(lighting);
                    dragonKill(entity,player,serverLevel);
                });
            }
        }else{
            // client消息
            if (level.dimension() != Level.END && livingEntity instanceof Player player){
                player.displayClientMessage(Component.translatable(SylviaQingJiao.MODID + ".unusable").withStyle(ChatFormatting.GREEN),true);
            }
        }
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!player.level().isClientSide()) {
            dragonKill(entity,player,player.level());
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable(SylviaQingJiao.MODID + ".tip.1").withStyle(ChatFormatting.RED));
        tooltipComponents.add(Component.translatable(SylviaQingJiao.MODID + ".tip.2").withStyle(ChatFormatting.DARK_PURPLE));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    private static void dragonKill(Entity entity, Player player,Level level) {
        if (entity instanceof DraconicGuardianEntity) {
            DraconicGuardianEntity draconicGuardian = (DraconicGuardianEntity) entity;
            draconicGuardian.setShieldPower(0);
            draconicGuardian.attackEntityPartFrom(draconicGuardian.getDragonParts()[2], getSource(level,player), Float.POSITIVE_INFINITY);
            // draconicGuardian.kill();
            draconicGuardian.setHealth(0);
        } else if (entity instanceof GuardianCrystalEntity) {
            GuardianCrystalEntity crystal = (GuardianCrystalEntity) entity;
            crystal.setShieldPower(0);
            crystal.hurt(getSource(level,player), Float.POSITIVE_INFINITY);
        } else if (entity instanceof DraconicGuardianPartEntity) {
            DraconicGuardianPartEntity draconicGuardian = (DraconicGuardianPartEntity) entity;
            DraconicGuardianEntity dragon = draconicGuardian.dragon;
            dragon.setShieldPower(0);
            dragon.attackEntityPartFrom(dragon.dragonPartHead, getSource(level,player), Float.POSITIVE_INFINITY);
            dragon.setHealth(0);
            GuardianCrystalEntity crystal = dragon.closestGuardianCrystal;
            if (crystal != null) {
                crystal.kill();
            }
        }
    }

    private static DamageSource getSource(Level level, @Nullable Entity attacker) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DEDamage.CHAOTIC_ARROW), attacker);
    }

}

class Stlvia implements Tier {

    @Override
    public int getUses() {
        return 1024;
    }

    @Override
    public float getSpeed() {
        return 1.2f;
    }

    @Override
    public float getAttackDamageBonus() {
        return 69.0f;
    }

    @Override
    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_IRON_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.of(new ItemLike[]{Items.IRON_INGOT});
    }
}
