package sheepindev.projectares.event;

import net.java.games.input.Keyboard;
import net.minecraft.client.GameSettings;
import net.minecraft.client.KeyboardListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;
import sheepindev.projectares.network.EvasionPacket;
import sheepindev.projectares.network.ProjectAresPacketHandler;

import static sheepindev.projectares.enums.EvasionPerkEnum.*;
import static sheepindev.projectares.util.RegistryHelper.prefix;
import static sheepindev.projectares.util.PerkItemHelper.isPerkItemAndHasPerk;

public class EvasionPerkEventHandlerClient {

    private int[] timers = new int[3]; //0 = left, 1 = right, 2 = back
    private boolean[] wasPressedLastFrame = new boolean[3];

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!event.player.world.isRemote()) return; //clientside only

        for (int i = 0; i < timers.length; i++) {
            if (timers[i] > 0) {
                timers[i]--;
                System.out.println(timers[i] + " " + event.player.world.getGameTime());
            }
        }

        ClientPlayerEntity player = (ClientPlayerEntity) event.player;

        GameSettings settings = Minecraft.getInstance().gameSettings;

        if (settings.keyBindLeft.isKeyDown()) processMovementInformation(LEFT, player);
        else wasPressedLastFrame[LEFT] = false;

        if (settings.keyBindRight.isKeyDown()) processMovementInformation(RIGHT, player);
        else wasPressedLastFrame[RIGHT] = false;

        if (settings.keyBindBack.isKeyDown()) processMovementInformation(BACK, player);
        else wasPressedLastFrame[BACK] = false;
    }

    private void processMovementInformation(int direction, ClientPlayerEntity player) {
        if (!isPerkItemAndHasPerk(player.getHeldItemMainhand(),prefix("evasion"))) return;

        if (wasPressedLastFrame[direction]) return; //only on frame first pressed

        boolean flag = (float)player.getFoodStats().getFoodLevel() > 6.0F || player.abilities.allowFlying;
        System.out.println("processing");

        System.out.println(player.movementInput.jump || player.movementInput.sneaking || player.isSprinting() || !flag || player.isHandActive());

        if (timers[direction] == 0) {
            System.out.println("starting cooldown");
            timers[direction] = 7;
        } else if (!player.isOnGround() || player.movementInput.jump || player.movementInput.sneaking || player.isSprinting() || !flag || player.isHandActive()) {
            timers[direction] = 0;
        } else {
            EvasionPacket packet = new EvasionPacket(player.getEntityId(), direction);
            ProjectAresPacketHandler.INSTANCE.sendToServer(packet);
        }

        wasPressedLastFrame[direction] = true;
    }
}
