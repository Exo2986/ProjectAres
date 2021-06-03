package sheepindev.projectares.util;

public class ProjectAresConstants {
    //NBT
    public static final String NBT_TAG_NAME_PERK_LIST = "perks_list";
    public static final String NBT_TAG_NAME_PERK_COUNT = "num_perks";
    public static final String NBT_TAG_NAME_PERK_KEY = "perk_id";

    public static final String NBT_TAG_NAME_SOUL_CAPACITOR_CHARGE = "perk_soul_capacitor_charge";

    public static final String NBT_TAG_NAME_EYE_PERK = "perk_eye";

    public static final String NBT_TAG_NAME_FINAL_STAND_COOLDOWN = "perk_final_stand_cooldown";

    public static final String NBT_TAG_NAME_CHORUS_IMBUED_COOLDOWN = "perk_chorus_imbued_cooldown";

    //Cooldowns
    public static final int COOLDOWN_FINAL_STAND = 6000; //5 minutes
    public static final int COOLDOWN_CHORUS_IMBUED = 3600; //3 minutes

    //Strings
    public static final String MOD_ID = "projectares";

    //Networking
    public static final String PROTOCOL_VERSION = "1";

    public static final byte EXTENDED_REACH_PACKET_ID = 1;
    public static final byte EVASION_PACKET_ID = 2;
    public static final byte BASIC_PARTICLE_EMITTER_PACKET_ID = 3;
}
