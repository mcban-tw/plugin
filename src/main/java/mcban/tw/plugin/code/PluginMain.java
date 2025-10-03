package mcban.tw.plugin.code;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mcban.tw.plugin.api.McbanTw;
import mcban.tw.plugin.code.command.CmdCompleter;
import mcban.tw.plugin.code.command.CmdExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

public final class PluginMain extends JavaPlugin implements McbanTw {
    private static final @NotNull Gson GSON = new Gson();
    private static @Nullable PluginMain IMP;

    private @Nullable PluginConfig config;

    @Override
    public void onEnable() {
        IMP = this;
        saveDefaultConfig();
        reloadConfig();
        PluginCommand command;
        command = Bukkit.getPluginCommand("reload");
        if (command == null) {
            throw new RuntimeException("No registration command 'reload' in plugin.yml");
        }
        command.setExecutor(new CmdExecutor(this));
        command.setTabCompleter(new CmdCompleter());
        Bukkit.getPluginManager().registerEvents(new PluginEvent(this), this);
    }

    @Override
    public void onDisable() {
        IMP = null;
    }

    public static @NotNull PluginMain imp() {
        return requireNonNull(IMP);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        config = new PluginConfig(getConfig());
    }

    public @NotNull PluginConfig impConfig() {
        return requireNonNull(config);
    }

    @Override
    public boolean banEnable() {
        return impConfig().banEnable;
    }

    @Override
    public @NotNull String serverId() {
        return impConfig().serverId;
    }

    @Override
    public boolean isBanned(@NotNull UUID player) throws IOException {
        URI uri = URI.create(impConfig().apiUrl
                .replace("%server%", serverId())
                .replace("%player%", player.toString()));
        URLConnection connection = uri.toURL().openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setDoInput(true);
        connection.setDoOutput(false);
        JsonObject result;
        try (InputStreamReader input = new InputStreamReader(connection.getInputStream(), UTF_8)) {
            result = GSON.fromJson(input, JsonObject.class);
        }
        return result.get("banned").getAsBoolean();
    }
}
