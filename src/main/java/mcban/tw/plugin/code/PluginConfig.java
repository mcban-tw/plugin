package mcban.tw.plugin.code;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public final class PluginConfig {
    public final boolean banEnable;
    public final @NotNull String serverId;
    public final @NotNull Component kickMessage;
    public final @NotNull String apiUrl;
    public final boolean exceptionDenied;

    public PluginConfig(@NotNull FileConfiguration config) {
        banEnable = config.getBoolean("ban-enable", true);
        serverId = config.getString("server-id", "example.com");
        kickMessage = MiniMessage.miniMessage().deserialize(config.getString("kick-message", "<red>您已被「麥塊伺服玩家封禁共享名單」封鎖</red>"));
        apiUrl = config.getString("api-url", "https://mcban.tw/api/query?server=%server%&player=%player%");
        exceptionDenied = config.getBoolean("exception-denied", true);
    }
}
