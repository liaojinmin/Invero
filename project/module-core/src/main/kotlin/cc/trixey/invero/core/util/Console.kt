package cc.trixey.invero.core.util

import org.bukkit.command.CommandSender
import taboolib.common.platform.function.console
import taboolib.platform.util.asLangText
import taboolib.platform.util.bukkitPlugin
import java.io.File

/**
 * Invero
 * cc.trixey.invero.core.util.Console
 *
 * @author Arasple
 * @since 2023/1/18 14:32
 */
fun isDebugEnable(): Boolean {
    return File(bukkitPlugin.dataFolder, "dev").exists()
}

fun debug(message: String) {
    if (isDebugEnable())
        println("§c[Invero] §7${message.replace('&', '§')}")
}

inline fun <T, R> T.letCatching(block: (T) -> R): R? {
    return printCatching { block(this) }.getOrNull()
}

inline fun <R> printCatching(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        e.prettyPrint()
        Result.failure(e)
    }
}

fun Throwable.prettyPrint() {
    println(console().cast<CommandSender>().asLangText("throwable-print"))
    println("§c$localizedMessage")
    stackTrace
        .filter { "taboolib" in it.toString() || "invero" in it.toString() }
        .forEach { println(" §8${it.toString().split("//").getOrNull(1)}") }
}