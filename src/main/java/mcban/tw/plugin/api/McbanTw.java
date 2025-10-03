package mcban.tw.plugin.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import mcban.tw.plugin.code.PluginMain;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public interface McbanTw {
    static @NotNull McbanTw I() {
        return PluginMain.imp();
    }

    /**
     * @return 插件當前是否啟用封鎖中。
     */
    boolean banEnable();

    /**
     * 取得配置文件中所定義的伺服器編號。
     * @return 伺服器編號。
     */
    @NotNull String serverId();

    /**
     * 向 API 請求查詢該玩家是否已被選擇性封鎖。
     * @param playerProfile 玩家檔案。
     * @return 是否在 API 內已被封鎖。
     */
    default boolean isBanned(@NotNull PlayerProfile playerProfile) throws IOException {
        UUID uuid = playerProfile.getId();
        if (uuid == null) {
            throw new NullPointerException("PlayerProfile UUID is null");
        }
        return isBanned(uuid);
    }
    /**
     * 向 API 請求查詢該玩家是否已被選擇性封鎖。
     * @param player 玩家。
     * @return 是否在 API 內已被封鎖。
     */
    default boolean isBanned(@NotNull OfflinePlayer player) throws IOException {
        return isBanned(player.getUniqueId());
    }
    /**
     * 向 API 請求查詢該玩家是否已被選擇性封鎖。
     * @param player 玩家 UUID。
     * @return 是否在 API 內已被封鎖。
     */
    boolean isBanned(@NotNull UUID player) throws IOException;
}
