/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.enchantments.EnchantmentTarget;
/*     */ import org.bukkit.enchantments.EnchantmentWrapper;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ 
/*     */ public class Util
/*     */ {
/*     */   public static ItemStack createHead(String owner, String name, String... lore)
/*     */   {
/*  20 */     ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
/*  21 */     SkullMeta meta = (SkullMeta)item.getItemMeta();
/*  22 */     meta.setOwner(owner);
/*  23 */     meta.setDisplayName(name);
/*  24 */     List<String> l = new ArrayList();
/*  25 */     String[] arrayOfString; int j = (arrayOfString = lore).length; for (int i = 0; i < j; i++) { String s = arrayOfString[i];
/*  26 */       l.add(s); }
/*  27 */     meta.setLore(l);
/*  28 */     item.setItemMeta(meta);
/*  29 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material mat, int amt, int durability, String name, List<String> lore) {
/*  33 */     ItemStack item = new ItemStack(mat, amt);
/*  34 */     ItemMeta meta = item.getItemMeta();
/*  35 */     meta.setDisplayName(name);
/*  36 */     meta.setLore(lore);
/*  37 */     if (durability != 0)
/*  38 */       item.setDurability((short)durability);
/*  39 */     item.setItemMeta(meta);
/*  40 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material mat, int amt, String name, List<String> lore) {
/*  44 */     return createItem(mat, amt, 0, name, lore);
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material mat, int amt, int durability, String name, String... lore) {
/*  48 */     List<String> l = new ArrayList();
/*  49 */     String[] arrayOfString; int j = (arrayOfString = lore).length; for (int i = 0; i < j; i++) { String s = arrayOfString[i];
/*  50 */       l.add(s); }
/*  51 */     return createItem(mat, amt, durability, name, l);
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material mat, int amt, String name, String... lore) {
/*  55 */     return createItem(mat, amt, 0, name, lore);
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material mat, String name, String... lore) {
/*  59 */     return createItem(mat, 1, 0, name, lore);
/*     */   }
/*     */   
/*     */   public static void remove(Inventory inv, Material mat, int amt) {
/*  63 */     int amount = 0;
/*  64 */     ItemStack[] arrayOfItemStack; int j = (arrayOfItemStack = inv.getContents()).length; for (int i = 0; i < j; i++) { ItemStack i = arrayOfItemStack[i];
/*  65 */       if ((i != null) && (i.getType() == mat))
/*  66 */         amount += i.getAmount(); }
/*  67 */     inv.remove(mat);
/*  68 */     if (amount > amt)
/*  69 */       inv.addItem(new ItemStack[] { new ItemStack(mat, amount - amt) });
/*     */   }
/*     */   
/*     */   public static String caps(String string) {
/*  73 */     String[] list = string.split("_");
/*  74 */     String s = "";
/*  75 */     String[] arrayOfString1; int j = (arrayOfString1 = list).length; for (int i = 0; i < j; i++) { String st = arrayOfString1[i];
/*  76 */       s = s + st.substring(0, 1).toUpperCase() + st.substring(1).toLowerCase(); }
/*  77 */     return s;
/*     */   }
/*     */   
/*     */   public static boolean isInt(String s) {
/*     */     try {
/*  82 */       Integer.parseInt(s);
/*  83 */       return true;
/*     */     } catch (NumberFormatException e) {}
/*  85 */     return false;
/*     */   }
/*     */   
/*     */   public static String color(String s)
/*     */   {
/*  90 */     return org.bukkit.ChatColor.translateAlternateColorCodes('&', s);
/*     */   }
/*     */   
/*     */   public static List<String> color(List<String> list) {
/*  94 */     List<String> colored = new ArrayList();
/*  95 */     for (String s : list)
/*  96 */       colored.add(color(s));
/*  97 */     return colored;
/*     */   }
/*     */   
/*     */   public static Material getMaterial(String s)
/*     */   {
/* 102 */     if (Material.getMaterial(s) != null)
/* 103 */       return Material.getMaterial(s);
/* 104 */     if ((isInt(s)) && (Material.getMaterial(Integer.parseInt(s)) != null))
/* 105 */       return Material.getMaterial(Integer.parseInt(s));
/* 106 */     if (Material.matchMaterial(s) != null)
/* 107 */       return Material.matchMaterial(s);
/* 108 */     if (Material.valueOf(s.toUpperCase()) != null)
/* 109 */       return Material.valueOf(s.toUpperCase());
/* 110 */     return null;
/*     */   }
/*     */   
/*     */   public static List<Integer> getBorder(int size) {
/* 114 */     switch (size) {
/*     */     case 54: 
/* 116 */       return Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(26), Integer.valueOf(27), Integer.valueOf(35), Integer.valueOf(36), Integer.valueOf(44), Integer.valueOf(45), Integer.valueOf(46), Integer.valueOf(47), Integer.valueOf(48), Integer.valueOf(49), Integer.valueOf(50), Integer.valueOf(51), Integer.valueOf(52), Integer.valueOf(53) });
/*     */     case 45: 
/* 118 */       return Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(26), Integer.valueOf(27), Integer.valueOf(35), Integer.valueOf(36), Integer.valueOf(37), Integer.valueOf(38), Integer.valueOf(39), Integer.valueOf(40), Integer.valueOf(41), Integer.valueOf(42), Integer.valueOf(43), Integer.valueOf(44) });
/*     */     case 36: 
/* 120 */       return Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(26), Integer.valueOf(27), Integer.valueOf(28), Integer.valueOf(29), Integer.valueOf(30), Integer.valueOf(31), Integer.valueOf(32), Integer.valueOf(33), Integer.valueOf(34), Integer.valueOf(35) });
/*     */     case 27: 
/* 122 */       return Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(21), Integer.valueOf(22), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(26) });
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   public static int getTotalExperience(Player player)
/*     */   {
/* 129 */     int exp = Math.round(getExpAtLevel(player.getLevel()) * player.getExp());
/* 130 */     int currentLevel = player.getLevel();
/* 131 */     while (currentLevel > 0) {
/* 132 */       currentLevel--;
/* 133 */       exp += getExpAtLevel(currentLevel);
/*     */     }
/* 135 */     if (exp < 0)
/* 136 */       exp = Integer.MAX_VALUE;
/* 137 */     return exp;
/*     */   }
/*     */   
/*     */   public static int getExpAtLevel(int level) {
/* 141 */     if (level > 29)
/* 142 */       return 62 + (level - 30) * 7;
/* 143 */     if (level > 15)
/* 144 */       return 17 + (level - 15) * 3;
/* 145 */     return 17;
/*     */   }
/*     */   
/*     */   public static boolean isArmour(Material m) {
/* 149 */     return Enchantment.PROTECTION_ENVIRONMENTAL.canEnchantItem(new ItemStack(m));
/*     */   }
/*     */   
/*     */   public static boolean isDiamond(Material m) {
/* 153 */     return m.toString().contains("DIAMOND");
/*     */   }
/*     */   
/*     */   public static boolean isGold(Material m) {
/* 157 */     return m.toString().contains("GOLD");
/*     */   }
/*     */   
/*     */   public static boolean isIron(Material m) {
/* 161 */     return m.toString().contains("IRON");
/*     */   }
/*     */   
/*     */   public static boolean isLeather(Material m) {
/* 165 */     return m.toString().contains("LEATHER");
/*     */   }
/*     */   
/*     */   public static boolean isChain(Material m) {
/* 169 */     return m.toString().contains("CHAIN");
/*     */   }
/*     */   
/*     */   public static boolean isSword(Material m) {
/* 173 */     return m.toString().contains("SWORD");
/*     */   }
/*     */   
/*     */   public static boolean isAxe(Material m) {
/* 177 */     return m.toString().endsWith("_AXE");
/*     */   }
/*     */   
/*     */   public static boolean isPickaxe(Material m) {
/* 181 */     return m.toString().contains("PICKAXE");
/*     */   }
/*     */   
/*     */   public static boolean isWeapon(Material m) {
/* 185 */     return Enchantment.DAMAGE_ALL.canEnchantItem(new ItemStack(m));
/*     */   }
/*     */   
/*     */   public static boolean isTool(Material m) {
/* 189 */     return Enchantment.DIG_SPEED.canEnchantItem(new ItemStack(m));
/*     */   }
/*     */   
/*     */   public static String getName(EntityType e) {
/* 193 */     if (e.equals(EntityType.PIG_ZOMBIE))
/* 194 */       return "Zombie Pigman";
/* 195 */     if (!e.toString().contains("_"))
/* 196 */       return e.toString().substring(0, 1).toUpperCase() + e.toString().substring(1).toLowerCase();
/* 197 */     String[] split = e.toString().split("_");
/* 198 */     String name = "";
/* 199 */     String[] arrayOfString1; int j = (arrayOfString1 = split).length; for (int i = 0; i < j; i++) { String s = arrayOfString1[i];
/* 200 */       name = name + s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() + " "; }
/* 201 */     return name.trim();
/*     */   }
/*     */   
/*     */   public static EntityType getEntity(String e) {
/* 205 */     if (e.equalsIgnoreCase("Zombie Pigman"))
/* 206 */       return EntityType.PIG_ZOMBIE;
/* 207 */     e = e.replaceAll(" ", "_");
/* 208 */     if (!e.contains("_"))
/* 209 */       return EntityType.valueOf(e.toUpperCase());
/* 210 */     String[] split = e.toString().split(" ");
/* 211 */     String name = "";
/* 212 */     String[] arrayOfString1; int j = (arrayOfString1 = split).length; for (int i = 0; i < j; i++) { String s = arrayOfString1[i];
/* 213 */       name = name + s.toUpperCase() + "_"; }
/* 214 */     return EntityType.valueOf(name.substring(0, name.length() - 1));
/*     */   }
/*     */   
/*     */   public static enum Pane {
/* 218 */     WHITE(0), 
/* 219 */     ORANGE(1), 
/* 220 */     MAGENTA(2), 
/* 221 */     LIGHT_BLUE(3), 
/* 222 */     YELLOW(4), 
/* 223 */     LIME(5), 
/* 224 */     PINK(6), 
/* 225 */     GRAY(7), 
/* 226 */     LIGHT_GRAY(8), 
/* 227 */     CYAN(9), 
/* 228 */     PURPLE(10), 
/* 229 */     BLUE(11), 
/* 230 */     BROWN(12), 
/* 231 */     GREEN(13), 
/* 232 */     RED(14), 
/* 233 */     BLACK(15);
/*     */     
/*     */     private int value;
/*     */     
/* 237 */     private Pane(int value) { this.value = value; }
/*     */     
/*     */     public int value()
/*     */     {
/* 241 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EnchantGlow extends EnchantmentWrapper {
/* 246 */     private static Enchantment glow = null;
/*     */     private String name;
/*     */     
/*     */     public static void addGlow(ItemStack itemstack) {
/* 250 */       itemstack.addEnchantment(getGlow(), 1);
/*     */     }
/*     */     
/*     */     public static Enchantment getGlow() {
/* 254 */       if (glow != null)
/* 255 */         return glow;
/* 256 */       Field field = null;
/*     */       try {
/* 258 */         field = Enchantment.class.getDeclaredField("acceptingNew");
/*     */       } catch (NoSuchFieldException|SecurityException e) {
/* 260 */         e.printStackTrace();
/* 261 */         return glow;
/*     */       }
/* 263 */       field.setAccessible(true);
/*     */       try {
/* 265 */         field.set(null, Boolean.valueOf(true));
/*     */       } catch (IllegalArgumentException|IllegalAccessException e) {
/* 267 */         e.printStackTrace();
/*     */       }
/*     */       try {
/* 270 */         glow = new EnchantGlow(Enchantment.values().length + 100);
/*     */       } catch (Exception e) {
/* 272 */         glow = Enchantment.getByName("Glow");
/*     */       }
/* 274 */       if (Enchantment.getByName("Glow") == null)
/* 275 */         Enchantment.registerEnchantment(glow);
/* 276 */       return glow;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 281 */       return this.name;
/*     */     }
/*     */     
/*     */     public Enchantment getEnchantment()
/*     */     {
/* 286 */       return Enchantment.getByName("Glow");
/*     */     }
/*     */     
/*     */     public int getMaxLevel()
/*     */     {
/* 291 */       return 1;
/*     */     }
/*     */     
/*     */     public int getStartLevel()
/*     */     {
/* 296 */       return 1;
/*     */     }
/*     */     
/*     */     public EnchantmentTarget getItemTarget()
/*     */     {
/* 301 */       return EnchantmentTarget.ALL;
/*     */     }
/*     */     
/*     */     public boolean canEnchantItem(ItemStack item)
/*     */     {
/* 306 */       return true;
/*     */     }
/*     */     
/*     */     public boolean conflictsWith(Enchantment other)
/*     */     {
/* 311 */       return false;
/*     */     }
/*     */     
/*     */     public EnchantGlow(int i) {
/* 315 */       super();
/* 316 */       this.name = "Glow";
/*     */     }
/*     */   }
/*     */ }


/* Location:              c:\users\strenghtypvp\Desktop\RiversideTokens.jar!\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */