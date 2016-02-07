package catwalks.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import catwalks.CatwalksMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class Logs {
    private static Logs instance = null;

    public static long prevTicks = -1;
    private Logger logger;

    public static boolean debugMode = CatwalksMod.developmentEnvironment;
    public static boolean doLogging = false;

    private Logs() {
        logger = LogManager.getLogger();
        instance = this;
    }

    private static Logs getInstance() {
        if (instance == null) {
            instance = new Logs();
        }
        return instance;
    }

    public static void error(String message, Object... args) {
        getInstance().logger.log(Level.ERROR, String.format(message, args));
    }

    public static void log(World world, TileEntity te, String message, Object... args) {
        if (doLogging) {
            long ticks = world.getTotalWorldTime();
            if (ticks != prevTicks) {
                prevTicks = ticks;
                getInstance().logger.log(Level.INFO, "=== Time " + ticks + " ===");
            }
            String id = te.getPos().getX() + "," + te.getPos().getY() + "," + te.getPos().getZ() + ": ";
            getInstance().logger.log(Level.INFO, id + String.format(message, args));
        }
    }

    public static void log(String message, Object... args) {
        getInstance().logger.log(Level.INFO, String.format(message, args));
    }

    public static void debug(String message, Object... args) {
        if (debugMode) {
            getInstance().logger.log(Level.INFO, String.format(message, args));
        }
    }

    public static void message(EntityPlayer player, String message, Object... args) {
        player.addChatComponentMessage(new ChatComponentText(String.format(message, args)));
    }

    public static void warn(EntityPlayer player, String message, Object... args) {
        player.addChatComponentMessage(new ChatComponentText(String.format(message, args)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
    }
}