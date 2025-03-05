package expanded.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Registers {
    public static Enchantment RECOIL = new RecoilEnchantment();
    public static Enchantment DRAW_WEIGHT = new DrawWeightEnchantment();
    public static Enchantment REELING = new ReelingEnchantment();
    public static Enchantment FROST_ASPECT = new FrostAspectEnchantment();
    public static Enchantment POISON_ASPECT = new PoisonAspectEnchantment();
    public static Enchantment CHAOS_ASPECT = new ChaosAspectEnchantment();
    public static Enchantment WITHER_ASPECT = new WitherAspectEnchantment();
    public static Enchantment POISON_BURST = new PoisonBurstEnchantment();
    public static Enchantment SOUL_SHARPNESS = new SoulSharpnessEnchantment();
    public static Enchantment LAUNCHING = new LaunchingEnchantment();
    public static Enchantment FROST_BURST = new FrostBurstEnchantment();
    public static Enchantment CHAOS_BURST = new ChaosBurstEnchantment();
    public static Enchantment FLAME_BURST = new FlameBurstEnchantment();
    public static Enchantment WITHERING_BURST = new WitherBurstEnchantment();
    public static Enchantment EXPLOSIVE_BURST = new ExplosiveBurstEnchantment();
    public static Enchantment PULLING = new PullingEnchantment();
    public static Enchantment COURAGE = new CourageEnchantment();
    public static Enchantment COWARDICE = new CowardiceEnchantment();
    public static Enchantment EXPLODING_ARROWS = new ExplodingArrowsEnchantment();
    public static Enchantment HEALTH_BOOST = new HealthBoostEnchantment();
    public static Enchantment SWIFT_FEET = new SwiftFeetEnchantment();

    public static Enchantment NIGHTVISION = new NightVisionEnchantment();
    public static Enchantment LIFESTEAL = new LifeStealEnchantment();
    public static Enchantment OMNISCIENCE  = new OmniscienceEnchantment();
    public static Enchantment STEPPING  = new SteppingEnchantment();
    public static Enchantment HEATSEEKING  = new HeatSeekingEnchantment();
    public static Enchantment WEIGHTLESS_ARROWS  = new WeightlessArrowsEnchantment();
    public static Enchantment THUNDER_GOD  = new ThunderGodEnchantment();
    public static Enchantment TURTLE  = new TurtleEnchantment();


    public static void Register(){
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "recoil"), RECOIL);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "launching"), LAUNCHING);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "draw_weight"), DRAW_WEIGHT);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "reeling"), REELING);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "frost_aspect"), FROST_ASPECT);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "poison_aspect"), POISON_ASPECT);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "chaos_aspect"), CHAOS_ASPECT);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "wither_aspect"), WITHER_ASPECT);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "soul_sharpness"), SOUL_SHARPNESS);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "poison_burst"), POISON_BURST);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "flame_burst"), FLAME_BURST);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "frost_burst"), FROST_BURST);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "withering_burst"), WITHERING_BURST);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "chaos_burst"), CHAOS_BURST);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "explosive_burst"), EXPLOSIVE_BURST);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "pulling"), PULLING);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "courage"), COURAGE);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "cowardice"), COWARDICE);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "exploding_arrows"), EXPLODING_ARROWS);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "health_boost"), HEALTH_BOOST);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "swift_feet"), SWIFT_FEET);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "nightvision"), NIGHTVISION);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "lifesteal"), LIFESTEAL);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "omniscience"), OMNISCIENCE);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "stepping"), STEPPING);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "heatseeking"),    HEATSEEKING);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "weightless_arrows"),    WEIGHTLESS_ARROWS);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "thunder_god"), THUNDER_GOD);
        Registry.register(Registries.ENCHANTMENT, new Identifier("expanded-enchantments", "turtle"), TURTLE);
        
        
        

        

    }
}
/* TODO
STUFF TO ADD
Spiked (for a shield, creatures whose mlee attacks are blocked by the shield have a chance to be damaged)
Discovery (for pickaxe, gives a small chance for stone to drop coal, copper, and iron, and a small chance for deep slate to drop redstone, lapis, and a very small chance of diamonds)
Gleaming (gives a chance to drop xp from blocks broken)
Nether's bane (deals more damage to nether mobs)
End's bane (deals more damage to end mobs) 
harvesting (fortune for hoes)
breserker (deal more damage the lower your health is, chest)
unscathed (deal more damage when at full health, but less at lower health, chest)
well nourished (hunger degrades slower, chest)
intangable (legs, when taking damage has a chance to teleport you like chorus fruit and make you immune to all damage for one second)
hardhat (take less damage when hitting blocks with an elytra, head)
persisting (this item cannot be killed except by the void nor can it despawn)
multi-hook (adds multiple hooks from the fishing rod)
misanthropy (deal more damage to players, illagers, wiches, villagers, and endermen)
collosis slayer (deal more damage to bosses)
decapitation (axe, has a chance to insta kill your enemy with chance varying based on enemy max hp)
quick learner (head, gain more xp)
toolswaping (atotool, chest)
sniper (more damage the farther you are from your target, bow)
shotgun (much more damage close up, but less damage father away, crossbow)
waterlord (trident, gives water breathing)
stasis (leggings, when you are below a certain health, has a chance to stop/slow time around you for a few seconds damages the leggings greatly)
slowing (shield, when the shield is raised time slows down a bit. damges the shield when this happens)
*/