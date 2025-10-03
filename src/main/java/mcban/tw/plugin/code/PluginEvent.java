package mcban.tw.plugin.code;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static net.kyori.adventure.text.Component.translatable;

public final class PluginEvent implements Listener {
    private final @NotNull PluginMain main;

    public PluginEvent(@NotNull PluginMain _main) {
        main = _main;
    }

    /**
     * @param event 玩家登入
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(@NotNull PlayerLoginEvent event) {
        if (!main.banEnable()) {
            return;
        }
        try {
            if (main.isBanned(event.getPlayer())) {
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, main.impConfig().kickMessage);
            }
        } catch (IOException ex) {
            main.getLogger().warning("Failed to check ban status: " + ex.getMessage());
            if (main.impConfig().exceptionDenied) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, translatable("multiplayer.disconnect.invalid_player_data")); // 無效的玩家資料
            }
        }
    }
}
