package ru.ancndz.bandmod.item;

import java.util.List;

import javax.annotation.Nullable;

import ru.ancndz.bandmod.mod.MainClassMod;
import ru.ancndz.bandmod.regis.SoundHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBand extends Item
{
    private final int healTime; // 50 25 12
    private final int healAmount; // power 0 1 2
    private final int maxHeal; // 10 15 20
    private final int MaxItemUseDuration; // 40 80 120 - item use time
    private long lastWorldTime;
    
    public ItemBand(String name, int maxStackSize, int healTime, int amount, int maxHP, int duration) {
        this.setRegistryName(MainClassMod.MODID, name);
        this.setUnlocalizedName(name);
        this.setMaxStackSize(maxStackSize);
        this.setCreativeTab(MainClassMod.tabMain);
        //this.setHasSubtypes(true);
        
        this.healTime = healTime;
        this.healAmount = amount;
		this.maxHeal = maxHP;
		this.MaxItemUseDuration = duration;
		this.lastWorldTime = 0;
    }
	
    public int gethealAmount() { return this.healAmount; }
	public int getMaxhp() { return this.maxHeal;}
	public int getHealTime() { return this.healTime; }
	public int getMaxItemUseDuration() { return this.MaxItemUseDuration; }
	////////////
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		ItemStack stack = playerIn.getHeldItem(handIn);
		ItemBand item = (ItemBand) stack.getItem();
		int hp = item.getMaxhp();
		SoundEvent sound;
		
		switch(hp) {
			case 15: sound = SoundHandler.MEDIUM_BANDAGE_USE;  break;
			case 20: sound = SoundHandler.LARGE_BANDAGE_USE;  break;
			default: sound = SoundHandler.SMALL_BANDAGE_USE; break;
		}
		
		if (worldIn.getWorldTime() - this.lastWorldTime > 40 || this.lastWorldTime == 0) {
			worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, sound,
					SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F); 
		} 
	
		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    { this.lastWorldTime = worldIn.getWorldTime(); }
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		ItemBand item = (ItemBand) stack.getItem();
		int hp = item.getMaxhp();
		
		if (player.getHealth() < hp) {
			player.addPotionEffect( new PotionEffect(Potion.getPotionById(10), 
					(int) (hp - player.getHealth()) * item.getHealTime(), item.gethealAmount()));
			
			worldIn.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, 
					SoundHandler.BANDAGE_AFTERUSE, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
			
			this.lastWorldTime = worldIn.getWorldTime();
			player.getHeldItem(EnumHand.MAIN_HAND).shrink(1);	
		}
		
        return stack;
    }
	
	public EnumAction getItemUseAction(ItemStack stack)
    {
		return EnumAction.BOW;
    }

	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		tooltip.add("Need " + this.MaxItemUseDuration / 20 + " seconds to bandage.");
		super.addInformation(stack, worldIn, tooltip, flagIn);
    }
	
	
	/*
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (tab == MainClassMod.tabMain)
		{
		    for (EnumBands type : EnumBands.values())
		    {
		        items.add(new ItemStack(this, 1, type.ordinal()));
		    }
		}
	} */
	
}