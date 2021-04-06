package sheepindev.projectares.perk;

import java.util.HashMap;

public class PerkRegistry {
    protected static HashMap<String, Perk> Perks;

    public static void RegisterPerk(String id, Perk perk) {
        Perks.put(id, perk);
    }

    public static Perk GetPerk(String id) {
        if (Perks == null) return new Perk();
        return Perks.get(id);
    }

    public static void Init() {
        Perks = new HashMap<>();
        Register();
    }

    public static void Register() {
        RegisterPerk("rampage", new RampagePerk());
        RegisterPerk("vampirism", new VampirismPerk());
        RegisterPerk("thrill_of_the_kill", new ThrillPerk());
        RegisterPerk("top_heavy", new TopHeavyPerk());
        RegisterPerk("extended_blade", new ReachPerk());
    }
}
