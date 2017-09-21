/*     */ import java.lang.reflect.Field;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.enchantments.EnchantmentTarget;
/*     */ import org.bukkit.enchantments.EnchantmentWrapper;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util$EnchantGlow
/*     */   extends EnchantmentWrapper
/*     */ {
/* 246 */   private static Enchantment glow = null;
/*     */   private String name;
/*     */   
/*     */   public static void addGlow(ItemStack itemstack) {
/* 250 */     itemstack.addEnchantment(getGlow(), 1);
/*     */   }
/*     */   
/*     */   public static Enchantment getGlow() {
/* 254 */     if (glow != null)
/* 255 */       return glow;
/* 256 */     Field field = null;
/*     */     try {
/* 258 */       field = Enchantment.class.getDeclaredField("acceptingNew");
/*     */     } catch (NoSuchFieldException|SecurityException e) {
/* 260 */       e.printStackTrace();
/* 261 */       return glow;
/*     */     }
/* 263 */     field.setAccessible(true);
/*     */     try {
/* 265 */       field.set(null, Boolean.valueOf(true));
/*     */     } catch (IllegalArgumentException|IllegalAccessException e) {
/* 267 */       e.printStackTrace();
/*     */     }
/*     */     try {
/* 270 */       glow = new EnchantGlow(Enchantment.values().length + 100);
/*     */     } catch (Exception e) {
/* 272 */       glow = Enchantment.getByName("Glow");
/*     */     }
/* 274 */     if (Enchantment.getByName("Glow") == null)
/* 275 */       Enchantment.registerEnchantment(glow);
/* 276 */     return glow;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 281 */     return this.name;
/*     */   }
/*     */   
/*     */   public Enchantment getEnchantment()
/*     */   {
/* 286 */     return Enchantment.getByName("Glow");
/*     */   }
/*     */   
/*     */   public int getMaxLevel()
/*     */   {
/* 291 */     return 1;
/*     */   }
/*     */   
/*     */   public int getStartLevel()
/*     */   {
/* 296 */     return 1;
/*     */   }
/*     */   
/*     */   public EnchantmentTarget getItemTarget()
/*     */   {
/* 301 */     return EnchantmentTarget.ALL;
/*     */   }
/*     */   
/*     */   public boolean canEnchantItem(ItemStack item)
/*     */   {
/* 306 */     return true;
/*     */   }
/*     */   
/*     */   public boolean conflictsWith(Enchantment other)
/*     */   {
/* 311 */     return false;
/*     */   }
/*     */   
/*     */   public Util$EnchantGlow(int i) {
/* 315 */     super(i);
/* 316 */     this.name = "Glow";
/*     */   }
/*     */ }


/* Location:              c:\users\strenghtypvp\Desktop\RiversideTokens.jar!\Util$EnchantGlow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */