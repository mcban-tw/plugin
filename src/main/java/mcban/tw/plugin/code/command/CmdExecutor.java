package mcban.tw.plugin.code.command;

import mcban.tw.plugin.code.PluginMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.translatable;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

public final class CmdExecutor implements CommandExecutor {
    private final @NotNull PluginMain main;

    public CmdExecutor(@NotNull PluginMain _main) {
        main = _main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("mcban.tw.reload")) {
            sender.sendMessage(translatable("commands.help.failed", RED)); // 未知的指令或權限不足
            return true;
        }
        try {
            main.reloadConfig();
            sender.sendMessage(translatable("commands.reload.success")); // 重新載入中！
        } catch (Exception ex) {
            main.getLogger().warning("Failed to reload plugin: " + ex.getMessage());
            sender.sendMessage(translatable("commands.reload.failure")); // 重新載入失敗，保留原有資料！
        }
        return true;
    }
}
