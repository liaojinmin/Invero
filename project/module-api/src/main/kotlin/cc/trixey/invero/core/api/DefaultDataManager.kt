package cc.trixey.invero.core.api

import cc.trixey.invero.common.api.DataManager
import cc.trixey.invero.common.api.InveroSettings
import cc.trixey.invero.common.util.prettyPrint
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.expansion.*
import taboolib.module.lang.sendLang
import java.io.File
import java.util.*

/**
 * Invero
 * cc.trixey.invero.core.api.DefaultDataManager
 *
 * @author Arasple
 * @since 2023/2/1 17:20
 */
class DefaultDataManager : DataManager {

    private val globalDataDatabase by lazy {
        val randUUID = UUID.randomUUID()
        playerDataContainer[randUUID] = DataContainer("@global_data", playerDatabase!!)
        playerDataContainer[randUUID]!!
    }

    private val type by lazy {
        DataManager.Type
            .values()
            .find { it.name.equals(InveroSettings.databaseType, true) } ?: DataManager.Type.SQLITE
    }

    init {
        runCatching {
            when (type) {
                DataManager.Type.SQLITE -> setupPlayerDatabase(
                    File(InveroSettings.pluginFolder, "data/invero_data.db")
                )

                DataManager.Type.SQL -> setupPlayerDatabase(
                    InveroSettings.sqlSection ?: error("No valid sql section configurated")
                )
            }
        }.onFailure {
            it.prettyPrint()
            console().sendLang("database-errored", type.name)
        }.onSuccess {
            console().sendLang("database-connected", type.name)
        }.getOrNull()
    }

    override fun getDatabaseType(): DataManager.Type {
        return type
    }

    override fun getGlobalData(): DataContainer {
        return globalDataDatabase
    }

    override fun getPlayerData(player: Player): DataContainer {
        return player.getDataContainer()
    }

    companion object {


        @Awake(LifeCycle.ACTIVE)
        fun init() {
            PlatformFactory.registerAPI<DataManager>(DefaultDataManager())
        }

        @SubscribeEvent
        fun e(e: PlayerJoinEvent) {
            e.player.setupDataContainer()
        }

        @SubscribeEvent
        fun e(e: PlayerQuitEvent) {
            e.player.releaseDataContainer()
        }

    }

}