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

        

    }
}
/* 
**The ones I have currently  are **
Frost aspect
Poison aspect 
Wither aspect
Chaos aspect (has a chance of withering, freezing, igniting, or poisoning an enemy)
Reeling (for bows/crossbows, pulls a creature toward you when you hit them)
Draw weight (for crossbows, increases draw time but increases damage)
Recoil (for crossbows throws you backward when you shoot)
Poison burst (all of these burst enchantments have a chance to trigger every time you hit an enemy, when they do, they apply an effect to all creatures in a 15 by 15 by 15 cube around the creature, and the creature)
Flame burst 
Wither burst 
Frost burst 
Chaos burst
Soul sharpness (this causes an enemy to be withered and frozen when you hit it and the effects get worse the more creatures you kill with the weapon in a row, however, if you don't kill anything for awhile it gives you weakness and stops giving negative effects to creatures you hit. (can be fixed by killing more things with it))
Launching (for bows/crossbows, shoots the hit creature a few block into the air)

**These ones I have not done yet, but I am planning to add them**
Spiked (for a shield, when bringing up your shield it would damage and knock back all creatures in front of you)
Discovery (for pickaxe, gives a small chance for stone to drop coal, copper, and iron, and a small chance for deep slate to drop redstone, lapis, and a very small chance of diamonds)
Nether's bane (deals more damage to nether mobs)
End's bane (deals more damage to end mobs) */