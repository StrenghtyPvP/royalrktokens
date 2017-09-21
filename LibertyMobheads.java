/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ 
/*     */ public class LibertyMobheads extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener
/*     */ {
/*  25 */   Map<UUID, Integer> tokens = new java.util.HashMap();
/*  26 */   String s = "§c§l(§c!§l) §c";
/*     */   
/*     */   public void onEnable()
/*     */   {
/*  30 */     getServer().getPluginManager().registerEvents(this, this);
/*  31 */     getConfig().options().copyDefaults(true);
/*  32 */     saveDefaultConfig();
/*  33 */     getCommand("tokens").setExecutor(this);
/*  34 */     getCommand("tokenshop").setExecutor(this);
/*  35 */     getCommand("redeem").setExecutor(this);
/*  36 */     if (getConfig().isConfigurationSection("Tokens")) {
/*  37 */       for (String s : getConfig().getConfigurationSection("Tokens").getValues(false).keySet())
/*  38 */         this.tokens.put(UUID.fromString(s), Integer.valueOf(getConfig().getInt("Tokens." + s)));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  43 */     if (getConfig().isConfigurationSection("Tokens"))
/*  44 */       for (String s : getConfig().getConfigurationSection("Tokens").getValues(false).keySet())
/*  45 */         getConfig().set("Tokens." + s, null);
/*  46 */     for (UUID uuid : this.tokens.keySet())
/*  47 */       getConfig().set("Tokens." + uuid.toString(), this.tokens.get(uuid));
/*  48 */     saveConfig();
/*     */   }
/*     */   
/*     */   boolean isInt(String s) {
/*     */     try {
/*  53 */       Integer.parseInt(s);
/*  54 */       return true;
/*     */     } catch (NumberFormatException e) {}
/*  56 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   Material getMaterial(String s)
/*     */   {
/*  62 */     if (Material.getMaterial(s) != null)
/*  63 */       return Material.getMaterial(s);
/*  64 */     if ((isInt(s)) && (Material.getMaterial(Integer.parseInt(s)) != null))
/*  65 */       return Material.getMaterial(Integer.parseInt(s));
/*  66 */     if (Material.matchMaterial(s) != null)
/*  67 */       return Material.matchMaterial(s);
/*  68 */     if (Material.valueOf(s.toUpperCase()) != null)
/*  69 */       return Material.valueOf(s.toUpperCase());
/*  70 */     return null;
/*     */   }
/*     */   
/*     */   String color(String s) {
/*  74 */     return org.bukkit.ChatColor.translateAlternateColorCodes('&', s);
/*     */   }
/*     */   
/*     */   List<String> color(List<String> list) {
/*  78 */     List<String> lore = new java.util.ArrayList();
/*  79 */     for (String s : list)
/*  80 */       lore.add(color(s));
/*  81 */     return lore;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void place(BlockPlaceEvent e) {
/*  86 */     if ((e.getBlock().getType().equals(Material.SKULL_ITEM)) && (((SkullMeta)e.getItemInHand().getItemMeta()).getOwner().startsWith("MHF")))
/*  87 */       e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void mobKill(EntityDeathEvent e) {
/*  92 */     switch (e.getEntity().getType()) {
/*     */     case IRON_GOLEM: 
/*     */     case ITEM_FRAME: 
/*     */     case LEASH_HITCH: 
/*     */     case MAGMA_CUBE: 
/*     */     case MINECART: 
/*     */     case MINECART_COMMAND: 
/*     */     case MINECART_FURNACE: 
/*     */     case MINECART_HOPPER: 
/*     */     case MINECART_TNT: 
/*     */     case MUSHROOM_COW: 
/*     */     case PIG_ZOMBIE: 
/*     */     case PLAYER: 
/*     */     case PRIMED_TNT: 
/*     */     case SHEEP: 
/*     */     case SILVERFISH: 
/*     */     case WITHER_SKULL: 
/* 109 */       if (new java.util.Random().nextInt(10) == 0) {
/* 110 */         e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), 
/* 111 */           Util.createHead("MHF_" + Util.caps(e.getEntity().getType().toString()), 
/* 112 */           "§6§l" + Util.caps(e.getEntity().getType().toString()) + " Head", new String[] { "", 
/* 113 */           "§6§l* §e§lTYPE §7" + Util.caps(e.getEntity().getType().toString()), "", 
/* 114 */           "§7Use §n§o/redeem§7 to exchange this head for tokens.", "" }));
/*     */       }
/*     */       break;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void invClick(InventoryClickEvent e) {
/* 122 */     if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR)))
/* 123 */       return;
/* 124 */     Player p = (Player)e.getWhoClicked();
/* 125 */     int amt; if (e.getInventory().getTitle().equals("§6§lHeads Shop")) {
/* 126 */       e.setCancelled(true);
/* 127 */       if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE))
/* 128 */         return;
/* 129 */       String type = ((SkullMeta)e.getCurrentItem().getItemMeta()).getOwner().replace("MHF_", "");
/* 130 */       ItemStack head = Util.createHead("MHF_" + type, "§6§l" + type + " Head", new String[] { "", "§6§l* §e§lTYPE §7" + type, "", 
/* 131 */         "§7Use §n§o/redeem§7 to exchange this head for tokens.", "" });
/* 132 */       amt = 0;
/* 133 */       ItemStack[] arrayOfItemStack; int j = (arrayOfItemStack = p.getInventory().getContents()).length; for (int i = 0; i < j; i++) { ItemStack item = arrayOfItemStack[i];
/* 134 */         if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasDisplayName()) && 
/* 135 */           (head.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())))
/* 136 */           amt += item.getAmount(); }
/* 137 */       if (amt >= 10) {
/* 138 */         j = (arrayOfItemStack = p.getInventory().getContents()).length; for (i = 0; i < j; i++) { ItemStack item = arrayOfItemStack[i];
/* 139 */           if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasDisplayName()) && 
/* 140 */             (head.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())))
/* 141 */             p.getInventory().removeItem(new ItemStack[] { item }); }
/* 142 */         head.setAmount(amt % 10);
/* 143 */         p.getInventory().addItem(new ItemStack[] { head });
/* 144 */         if (this.tokens.containsKey(p.getUniqueId())) {
/* 145 */           this.tokens.put(p.getUniqueId(), Integer.valueOf(((Integer)this.tokens.get(p.getUniqueId())).intValue() + amt / 10));
/*     */         } else
/* 147 */           this.tokens.put(p.getUniqueId(), Integer.valueOf(amt / 10));
/* 148 */         p.sendMessage(this.s + "§eYou have sold §7" + amt / 10 * 10 + " §eof §6" + type + " Heads");
/*     */       } else {
/* 150 */         p.sendMessage("§e§l(§e!§l) §eYou do not have §710 Heads §eto sell.");
/* 151 */       } } else if (e.getInventory().getTitle().equals("§c§lToken Shop")) {
/* 152 */       e.setCancelled(true);
/* 153 */       if (e.getRawSlot() == 11) {
/* 154 */         p.closeInventory();
/* 155 */         Inventory inv = getServer().createInventory(null, 27, "§b§lKey Shop");
/* 156 */         for (int i = 0; i < inv.getSize(); i++)
/* 157 */           inv.setItem(i, Util.createItem(Material.STAINED_GLASS_PANE, 1, 7, " ", new String[0]));
/* 158 */         if (!getConfig().isConfigurationSection("Tokenshop.KeyShop")) {
/* 159 */           getConfig().set("Tokenshop.KeyShop.DefaultKey.Slot", Integer.valueOf(10));
/* 160 */           getConfig().set("Tokenshop.KeyShop.DefaultKey.Material", "TRIPWIRE_HOOK");
/* 161 */           getConfig().set("Tokenshop.KeyShop.DefaultKey.Amount", Integer.valueOf(1));
/* 162 */           getConfig().set("Tokenshop.KeyShop.DefaultKey.Durability", Integer.valueOf(0));
/* 163 */           getConfig().set("Tokenshop.KeyShop.DefaultKey.Price", Integer.valueOf(100));
/* 164 */           getConfig().set("Tokenshop.KeyShop.DefaultKey.Name", "&8(&aDefault Key&8)");
/* 165 */           getConfig().set("Tokenshop.KeyShop.DefaultKey.Lore", Arrays.asList(new String[] { "", "&7Click to buy this key", "" }));
/* 166 */           getConfig().set("Tokenshop.KeyShop.DefaultKey.Commands", Arrays.asList(new String[] { "crate gk {player} Default 1" }));
/* 167 */           saveConfig();
/*     */         }
/* 169 */         if (getConfig().isConfigurationSection("Tokenshop.KeyShop"))
/* 170 */           for (String s : getConfig().getConfigurationSection("Tokenshop.KeyShop").getValues(false).keySet())
/* 171 */             inv.setItem(getConfig().getInt("Tokenshop.KeyShop." + s + ".Slot"), 
/* 172 */               Util.createItem(getMaterial(getConfig().getString("Tokenshop.KeyShop." + s + ".Material")), 
/* 173 */               getConfig().getInt("Tokenshop.KeyShop." + s + ".Amount"), 
/* 174 */               getConfig().getInt("Tokenshop.KeyShop." + s + ".Durability"), 
/* 175 */               color(getConfig().getString("Tokenshop.KeyShop." + s + ".Name")), 
/* 176 */               color(getConfig().getStringList("Tokenshop.KeyShop." + s + ".Lore"))));
/* 177 */         inv.setItem(22, Util.createItem(Material.STONE_BUTTON, "§e§lBACK TO TOKEN SHOP", new String[0]));
/* 178 */         p.openInventory(inv);
/* 179 */       } else if (e.getRawSlot() == 13) {
/* 180 */         p.closeInventory();
/* 181 */         Inventory inv = getServer().createInventory(null, 27, "§b§lGkit Shop");
/* 182 */         for (int i = 0; i < inv.getSize(); i++)
/* 183 */           inv.setItem(i, Util.createItem(Material.STAINED_GLASS_PANE, 1, 7, " ", new String[0]));
/* 184 */         if (!getConfig().isConfigurationSection("Tokenshop.GkitShop")) {
/* 185 */           getConfig().set("Tokenshop.GkitShop.Bedrock.Slot", Integer.valueOf(10));
/* 186 */           getConfig().set("Tokenshop.GkitShop.Bedrock.Material", "BEDROCK");
/* 187 */           getConfig().set("Tokenshop.GkitShop.Bedrock.Amount", Integer.valueOf(10));
/* 188 */           getConfig().set("Tokenshop.GkitShop.Bedrock.Durability", Integer.valueOf(0));
/* 189 */           getConfig().set("Tokenshop.GkitShop.Bedrock.Price", Integer.valueOf(1000));
/* 190 */           getConfig().set("Tokenshop.GkitShop.Bedrock.Name", "&8(&7Bedrock&8)");
/* 191 */           getConfig().set("Tokenshop.GkitShop.Bedrock.Lore", Arrays.asList(new String[] { "", "&7Click to buy this item", "" }));
/* 192 */           getConfig().set("Tokenshop.GkitShop.Bedrock.Commands", Arrays.asList(new String[] { "give {player} bedrock 10" }));
/* 193 */           saveConfig();
/*     */         }
/* 195 */         if (getConfig().isConfigurationSection("Tokenshop.GkitShop"))
/* 196 */           for (String s : getConfig().getConfigurationSection("Tokenshop.GkitShop").getValues(false).keySet())
/* 197 */             inv.setItem(getConfig().getInt("Tokenshop.GkitShop." + s + ".Slot"), 
/* 198 */               Util.createItem(getMaterial(getConfig().getString("Tokenshop.GkitShop." + s + ".Material")), 
/* 199 */               getConfig().getInt("Tokenshop.GkitShop." + s + ".Amount"), 
/* 200 */               getConfig().getInt("Tokenshop.GkitShop." + s + ".Durability"), 
/* 201 */               color(getConfig().getString("Tokenshop.GkitShop." + s + ".Name")), 
/* 202 */               color(getConfig().getStringList("Tokenshop.GkitShop." + s + ".Lore"))));
/* 203 */         inv.setItem(22, Util.createItem(Material.STONE_BUTTON, "§e§lBACK TO TOKEN SHOP", new String[0]));
/* 204 */         p.openInventory(inv);
/* 205 */       } else if (e.getRawSlot() == 15) {
/* 206 */         p.closeInventory();
/* 207 */         Inventory inv = getServer().createInventory(null, 27, "§b§lRank Shop");
/* 208 */         for (int i = 0; i < inv.getSize(); i++)
/* 209 */           inv.setItem(i, Util.createItem(Material.STAINED_GLASS_PANE, 1, 7, " ", new String[0]));
/* 210 */         if (!getConfig().isConfigurationSection("Tokenshop.RankShop")) {
/* 211 */           getConfig().set("Tokenshop.RankShop.Bedrock.Slot", Integer.valueOf(10));
/* 212 */           getConfig().set("Tokenshop.RankShop.Bedrock.Material", "PAPER");
/* 213 */           getConfig().set("Tokenshop.RankShop.Bedrock.Amount", Integer.valueOf(1));
/* 214 */           getConfig().set("Tokenshop.RankShop.Bedrock.Durability", Integer.valueOf(0));
/* 215 */           getConfig().set("Tokenshop.RankShop.Bedrock.Price", Integer.valueOf(10000));
/* 216 */           getConfig().set("Tokenshop.RankShop.Bedrock.Name", "&8(&cZeus Rank&8)");
/* 217 */           getConfig().set("Tokenshop.RankShop.Bedrock.Lore", Arrays.asList(new String[] { "", "&7Click to buy this Rank", "" }));
/* 218 */           getConfig().set("Tokenshop.RankShop.Bedrock.Commands", Arrays.asList(new String[] { "pex user {player} group add zeus" }));
/* 219 */           saveConfig();
/*     */         }
/* 221 */         if (getConfig().isConfigurationSection("Tokenshop.RankShop"))
/* 222 */           for (String s : getConfig().getConfigurationSection("Tokenshop.RankShop").getValues(false).keySet())
/* 223 */             inv.setItem(getConfig().getInt("Tokenshop.RankShop." + s + ".Slot"), 
/* 224 */               Util.createItem(getMaterial(getConfig().getString("Tokenshop.RankShop." + s + ".Material")), 
/* 225 */               getConfig().getInt("Tokenshop.RankShop." + s + ".Amount"), 
/* 226 */               getConfig().getInt("Tokenshop.RankShop." + s + ".Durability"), 
/* 227 */               color(getConfig().getString("Tokenshop.RankShop." + s + ".Name")), 
/* 228 */               color(getConfig().getStringList("Tokenshop.RankShop." + s + ".Lore"))));
/* 229 */         inv.setItem(22, Util.createItem(Material.STONE_BUTTON, "§e§lBACK TO TOKEN SHOP", new String[0]));
/* 230 */         p.openInventory(inv);
/*     */       }
/* 232 */     } else if (e.getInventory().getTitle().equals("§b§lKey Shop")) {
/* 233 */       e.setCancelled(true);
/* 234 */       if (e.getRawSlot() == 22) {
/* 235 */         p.closeInventory();
/* 236 */         p.performCommand("tokenshop");
/* 237 */         return;
/*     */       }
/* 239 */       String path = null;
/* 240 */       if (getConfig().isConfigurationSection("Tokenshop.KeyShop"))
/* 241 */         for (String s : getConfig().getConfigurationSection("Tokenshop.KeyShop").getValues(false).keySet())
/* 242 */           if (getConfig().getInt("Tokenshop.KeyShop." + s + ".Slot") == e.getRawSlot())
/* 243 */             path = "Tokenshop.KeyShop." + s;
/* 244 */       if (path == null)
/* 245 */         return;
/* 246 */       if ((this.tokens.containsKey(p.getUniqueId())) && (((Integer)this.tokens.get(p.getUniqueId())).intValue() >= getConfig().getInt(path + ".Price"))) {
/* 247 */         for (String s : getConfig().getStringList(path + ".Commands"))
/* 248 */           getServer().dispatchCommand(getServer().getConsoleSender(), s.replace("{player}", p.getName()));
/* 249 */         this.tokens.put(p.getUniqueId(), Integer.valueOf(((Integer)this.tokens.get(p.getUniqueId())).intValue() - getConfig().getInt(path + ".Price")));
/* 250 */         p.sendMessage(this.s + "§eYou have purchased " + e.getCurrentItem().getItemMeta().getDisplayName() + " §efor §a" + 
/* 251 */           getConfig().getInt(new StringBuilder(String.valueOf(path)).append(".Price").toString()));
/*     */       } else {
/* 253 */         p.sendMessage(this.s + "§eYou don't have enough tokens for that.");
/* 254 */       } } else if (e.getInventory().getTitle().equals("§b§lGkit Shop")) {
/* 255 */       e.setCancelled(true);
/* 256 */       if (e.getRawSlot() == 22) {
/* 257 */         p.closeInventory();
/* 258 */         p.performCommand("tokenshop");
/* 259 */         return;
/*     */       }
/* 261 */       String path = null;
/* 262 */       if (getConfig().isConfigurationSection("Tokenshop.GkitShop"))
/* 263 */         for (String s : getConfig().getConfigurationSection("Tokenshop.GkitShop").getValues(false).keySet())
/* 264 */           if (getConfig().getInt("Tokenshop.GkitShop." + s + ".Slot") == e.getRawSlot())
/* 265 */             path = "Tokenshop.GkitShop." + s;
/* 266 */       if (path == null)
/* 267 */         return;
/* 268 */       if ((this.tokens.containsKey(p.getUniqueId())) && (((Integer)this.tokens.get(p.getUniqueId())).intValue() >= getConfig().getInt(path + ".Price"))) {
/* 269 */         for (String s : getConfig().getStringList(path + ".Commands"))
/* 270 */           getServer().dispatchCommand(getServer().getConsoleSender(), s.replace("{player}", p.getName()));
/* 271 */         this.tokens.put(p.getUniqueId(), Integer.valueOf(((Integer)this.tokens.get(p.getUniqueId())).intValue() - getConfig().getInt(path + ".Price")));
/* 272 */         p.sendMessage(this.s + "§eYou have purchased " + e.getCurrentItem().getItemMeta().getDisplayName() + " §efor §a" + 
/* 273 */           getConfig().getInt(new StringBuilder(String.valueOf(path)).append(".Price").toString()));
/*     */       } else {
/* 275 */         p.sendMessage(this.s + "§eYou don't have enough tokens for that.");
/* 276 */       } } else if (e.getInventory().getTitle().equals("§6§lRank Shop")) {
/* 277 */       e.setCancelled(true);
/* 278 */       String path = null;
/* 279 */       if (e.getRawSlot() == 22) {
/* 280 */         p.closeInventory();
/* 281 */         p.performCommand("tokenshop");
/* 282 */         return;
/*     */       }
/* 284 */       if (getConfig().isConfigurationSection(".RankShop"))
/* 285 */         for (String s : getConfig().getConfigurationSection("Tokenshop.RankShop").getValues(false).keySet())
/* 286 */           if (getConfig().getInt("Tokenshop.RankShop." + s + ".Slot") == e.getRawSlot())
/* 287 */             path = "Tokenshop.RankShop." + s;
/* 288 */       if (path == null)
/* 289 */         return;
/* 290 */       if ((this.tokens.containsKey(p.getUniqueId())) && (((Integer)this.tokens.get(p.getUniqueId())).intValue() >= getConfig().getInt(path + ".Price"))) {
/* 291 */         for (String s : getConfig().getStringList(path + ".Commands"))
/* 292 */           getServer().dispatchCommand(getServer().getConsoleSender(), s.replace("{player}", p.getName()));
/* 293 */         this.tokens.put(p.getUniqueId(), Integer.valueOf(((Integer)this.tokens.get(p.getUniqueId())).intValue() - getConfig().getInt(path + ".Price")));
/* 294 */         p.sendMessage(this.s + "§eYou have purchased " + e.getCurrentItem().getItemMeta().getDisplayName() + " §efor §a" + 
/* 295 */           getConfig().getInt(new StringBuilder(String.valueOf(path)).append(".Price").toString()));
/*     */       } else {
/* 297 */         p.sendMessage(this.s + "§eYou don't have enough tokens for that.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 303 */     if ((command.getName().equals("redeem")) && ((sender instanceof Player))) {
/* 304 */       Player p = (Player)sender;
/* 305 */       Inventory inv = getServer().createInventory(null, 45, "§6§lHeads Shop");
/* 306 */       java.util.Iterator localIterator = Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(26), Integer.valueOf(27), Integer.valueOf(35), Integer.valueOf(36), Integer.valueOf(37), Integer.valueOf(38), Integer.valueOf(39), Integer.valueOf(40), Integer.valueOf(41), Integer.valueOf(42), Integer.valueOf(43), Integer.valueOf(44) }).iterator();
/* 307 */       while (localIterator.hasNext())
/*     */       {
/* 306 */         Integer i = (Integer)localIterator.next();
/* 307 */         inv.setItem(i.intValue(), Util.createItem(Material.STAINED_GLASS_PANE, 1, 15, " ", new String[0])); }
/* 308 */       if (this.tokens.containsKey(p.getUniqueId())) {
/* 309 */         inv.setItem(4, Util.createItem(Material.DOUBLE_PLANT, "§e§l(§e!§l) §eYou have §7§n" + this.tokens.get(p.getUniqueId()) + " Tokens.", new String[0]));
/* 310 */         inv.setItem(40, Util.createItem(Material.DOUBLE_PLANT, "§e§l(§e!§l) §eYou have §7§n" + this.tokens.get(p.getUniqueId()) + " Tokens.", new String[0]));
/*     */       } else {
/* 312 */         inv.setItem(4, Util.createItem(Material.DOUBLE_PLANT, "§e§l(§e!§l) §eYou have §7§n0 Tokens.", new String[0]));
/* 313 */         inv.setItem(40, Util.createItem(Material.DOUBLE_PLANT, "§e§l(§e!§l) §eYou have §7§n0 Tokens.", new String[0]));
/*     */       }
/* 315 */       inv.setItem(11, Util.createHead("MHF_Zombie", "§2§lZombie Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", 
/* 316 */         "§6§l* §e§lREWARD §71 Token", "" }));
/* 317 */       inv.setItem(12, Util.createHead("MHF_Skeleton", "§f§lSkeleton Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", 
/* 318 */         "§6§l* §e§lREWARD §71 Token", "" }));
/* 319 */       inv.setItem(13, Util.createHead("MHF_Spider", "§8§lSpider Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", 
/* 320 */         "§6§l* §e§lREWARD §71 Token", "" }));
/* 321 */       inv.setItem(14, 
/* 322 */         Util.createHead("MHF_Blaze", "§6§lBlaze Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", "§6§l* §e§lREWARD §71 Token", "" }));
/* 323 */       inv.setItem(15, Util.createHead("MHF_PigZombie", "§d§lPigZombie Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", 
/* 324 */         "§6§l* §e§lREWARD §71 Token", "" }));
/* 325 */       inv.setItem(20, Util.createHead("MHF_Creeper", "§a§lCreeper Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", 
/* 326 */         "§6§l* §e§lREWARD §71 Token", "" }));
/* 327 */       inv.setItem(21, Util.createHead("MHF_Enderman", "§5§lEnderman Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", 
/* 328 */         "§6§l* §e§lREWARD §71 Token", "" }));
/* 329 */       inv.setItem(22, Util.createHead("MHF_CaveSpider", "§4§lCaveSpider Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", 
/* 330 */         "§6§l* §e§lREWARD §71 Token", "" }));
/* 331 */       inv.setItem(23, 
/* 332 */         Util.createHead("MHF_Witch", "§2§lWitch Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", "§6§l* §e§lREWARD §71 Token", "" }));
/* 333 */       inv.setItem(24, 
/* 334 */         Util.createHead("MHF_Cow", "§6§lCow Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", "§6§l* §e§lREWARD §71 Token", "" }));
/* 335 */       inv.setItem(29, 
/* 336 */         Util.createHead("MHF_Sheep", "§f§lSheep Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", "§6§l* §e§lREWARD §71 Token", "" }));
/* 337 */       inv.setItem(30, Util.createHead("MHF_Chicken", "§7§lChicken Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", 
/* 338 */         "§6§l* §e§lREWARD §71 Token", "" }));
/* 339 */       inv.setItem(31, 
/* 340 */         Util.createHead("MHF_Pig", "§d§lPig Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", "§6§l* §e§lREWARD §71 Token", "" }));
/* 341 */       inv.setItem(32, Util.createHead("MHF_MagmaCube", "§6§lMagmaCube Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", 
/* 342 */         "§6§l* §e§lREWARD §71 Token", "" }));
/* 343 */       inv.setItem(33, 
/* 344 */         Util.createHead("MHF_Slime", "§a§lSlime Head §7(Click to Sell)", new String[] { "", "§6§l* §e§lHEADS §710 Heads", "§6§l* §e§lREWARD §71 Token", "" }));
/* 345 */       p.openInventory(inv);
/* 346 */     } else if ((command.getName().equals("tokenshop")) && ((sender instanceof Player))) {
/* 347 */       Player p = (Player)sender;
/* 348 */       Inventory inv = getServer().createInventory(null, 27, "§6§lToken Shop");
/* 349 */       for (int i = 0; i < inv.getSize(); i++)
/* 350 */         inv.setItem(i, Util.createItem(Material.STAINED_GLASS_PANE, 1, 7, " ", new String[0]));
/* 351 */       inv.setItem(11, Util.createItem(Material.TRIPWIRE_HOOK, "§e§lKey Menu", new String[] { "§7Purchase Crate Keys by using", "§7your available Tokens!" }));
/* 352 */       inv.setItem(13, Util.createItem(Material.BEACON, "§e§lGkitellaneous Menu", new String[] { "§7Purchase more items by using", "§7your available Tokens!" }));
/* 353 */       inv.setItem(15, Util.createItem(Material.PAPER, "§e§lRank Menu", new String[] { "§7Purchase Ranks by using", "§7your available Tokens!" }));
/* 354 */       p.openInventory(inv);
/* 355 */     } else if (command.getName().equals("tokens")) {
/* 356 */       if (args.length == 0) {
/* 357 */         if ((sender instanceof Player)) {
/* 358 */           Player p = (Player)sender;
/* 359 */           if (this.tokens.containsKey(p.getUniqueId())) {
/* 360 */             p.sendMessage(this.s + "§eYou have §7" + this.tokens.get(p.getUniqueId()) + " §eTokens.");
/*     */           } else
/* 362 */             p.sendMessage(this.s + "§eYou have §70 §eTokens.");
/*     */         } else {
/* 364 */           sender.sendMessage(this.s + "§eYou have §70 §eTokens.");
/* 365 */         } } else if (args.length > 0) {
/* 366 */         if (args[0].equalsIgnoreCase("reload")) {
/* 367 */           reloadConfig();
/* 368 */           sender.sendMessage(this.s + "§eConfig.yml file reloaded");
/* 369 */         } else if ((args[0].equalsIgnoreCase("give")) && (sender.hasPermission("tokens.admin"))) {
/* 370 */           if ((args.length == 3) && (isInt(args[2]))) {
/* 371 */             int amount = Integer.parseInt(args[2]);
/* 372 */             Player c = getServer().getPlayer(args[1]);
/* 373 */             if (c != null) {
/* 374 */               if (this.tokens.containsKey(c.getUniqueId())) {
/* 375 */                 this.tokens.put(c.getUniqueId(), Integer.valueOf(((Integer)this.tokens.get(c.getUniqueId())).intValue() + amount));
/*     */               } else
/* 377 */                 this.tokens.put(c.getUniqueId(), Integer.valueOf(amount));
/* 378 */               sender.sendMessage(this.s + "§eGave §7" + c.getName() + " §7§n" + amount + " §eTokens");
/* 379 */               c.sendMessage(this.s + "§eGiven §7" + amount + " §eTokens");
/*     */             } else {
/* 381 */               sender.sendMessage(this.s + "§ePlayer is offline");
/*     */             }
/* 383 */           } else { sender.sendMessage(this.s + "§eUse §6/tokens give <player> <amount>");
/* 384 */           } } else if ((args[0].equalsIgnoreCase("take")) && (sender.hasPermission("tokens.admin"))) {
/* 385 */           if ((args.length == 3) && (isInt(args[2]))) {
/* 386 */             int amount = Integer.parseInt(args[2]);
/* 387 */             Player c = getServer().getPlayer(args[1]);
/* 388 */             if (c != null) {
/* 389 */               if ((this.tokens.containsKey(c.getUniqueId())) && (((Integer)this.tokens.get(c.getUniqueId())).intValue() - amount > 0)) {
/* 390 */                 this.tokens.put(c.getUniqueId(), Integer.valueOf(((Integer)this.tokens.get(c.getUniqueId())).intValue() - amount));
/*     */               } else
/* 392 */                 this.tokens.remove(c.getUniqueId());
/* 393 */               sender.sendMessage(this.s + "§eTaken " + c.getName() + "'s §7" + amount + "§e Tokens");
/* 394 */               c.sendMessage(this.s + "§eLost §7" + amount + "§e Tokens");
/*     */             } else {
/* 396 */               sender.sendMessage(this.s + "§ePlayer is offline");
/*     */             }
/* 398 */           } else { sender.sendMessage(this.s + "§eUse §6/tokens take <player> <amount>");
/* 399 */           } } else if ((args[0].equalsIgnoreCase("pay")) && ((sender instanceof Player))) {
/* 400 */           if ((args.length == 3) && (isInt(args[2]))) {
/* 401 */             Player p = (Player)sender;
/* 402 */             int amount = Integer.parseInt(args[2]);
/* 403 */             Player c = getServer().getPlayer(args[1]);
/* 404 */             if (c != null) {
/* 405 */               if ((this.tokens.containsKey(p.getUniqueId())) && (((Integer)this.tokens.get(p.getUniqueId())).intValue() >= amount)) {
/* 406 */                 this.tokens.put(p.getUniqueId(), Integer.valueOf(((Integer)this.tokens.get(p.getUniqueId())).intValue() - amount));
/* 407 */                 if (this.tokens.containsKey(c.getUniqueId())) {
/* 408 */                   this.tokens.put(c.getUniqueId(), Integer.valueOf(((Integer)this.tokens.get(c.getUniqueId())).intValue() + amount));
/*     */                 } else
/* 410 */                   this.tokens.put(c.getUniqueId(), Integer.valueOf(amount));
/* 411 */                 p.sendMessage(this.s + "§eYou paid §6" + c.getName() + " " + amount + " §eTokens");
/* 412 */                 c.sendMessage(this.s + "§6" + p.getName() + " §epaid you §6" + amount + " §eTokens");
/*     */               } else {
/* 414 */                 p.sendMessage(this.s + "§eYou don't have enough Tokens.");
/*     */               }
/* 416 */             } else sender.sendMessage(this.s + "§ePlayer is offline");
/*     */           } else {
/* 418 */             sender.sendMessage(this.s + "§eUse §6/tokens pay <player> <amount>");
/*     */           }
/* 420 */         } else { getServer().dispatchCommand(sender, "tokens");
/*     */         }
/*     */       } else
/* 423 */         getServer().dispatchCommand(sender, "tokens"); }
/* 424 */     return true;
/*     */   }
/*     */ }


/* Location:              c:\users\strenghtypvp\Desktop\RiversideTokens.jar!\LibertyMobheads.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */