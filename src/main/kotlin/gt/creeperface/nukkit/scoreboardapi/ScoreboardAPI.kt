package gt.creeperface.nukkit.scoreboardapi

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.plugin.PluginBase
import cn.nukkit.utils.TextFormat
import gt.creeperface.nukkit.scoreboardapi.packet.RemoveObjectivePacket
import gt.creeperface.nukkit.scoreboardapi.packet.SetDisplayObjectivePacket
import gt.creeperface.nukkit.scoreboardapi.packet.SetScorePacket
import gt.creeperface.nukkit.scoreboardapi.scoreboard.ObjectiveCriteria
import gt.creeperface.nukkit.scoreboardapi.scoreboard.ObjectiveDisplaySlot
import gt.creeperface.nukkit.scoreboardapi.scoreboard.ObjectiveSortOrder
import gt.creeperface.nukkit.scoreboardapi.scoreboard.SimpleScoreboard
import java.util.*

/**
 * @author CreeperFace
 */
class ScoreboardAPI : PluginBase(), Listener {

    override fun onLoad() {
        server.network.registerPacket(RemoveObjectivePacket.NETWORK_ID, RemoveObjectivePacket::class.java)
        server.network.registerPacket(SetDisplayObjectivePacket.NETWORK_ID, SetDisplayObjectivePacket::class.java)
        server.network.registerPacket(SetScorePacket.NETWORK_ID, SetScorePacket::class.java)
    }

    override fun onEnable() {
        this.server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player

        val sb = builder().build()
        sb.setDisplayName("${TextFormat.GREEN}Test Scoreboard")
        sb.setScore(1, "${TextFormat.RED} ---------------  ", 1)
        sb.setScore(2, "test 2", 2)
        sb.setScore(3, "test 3", 3)
        sb.setScore(4, "test 4", 4)
        sb.setScore(5, "test 5", 5)
        sb.setScore(6, "test 6", 6)

        sb.addPlayer(p)
    }

    companion object {

        @JvmStatic
        fun builder() = Builder()

        class Builder internal constructor() {

            private var criteria = ObjectiveCriteria("dummy", false)

            private var displaySlot = ObjectiveDisplaySlot.SIDEBAR

            private var sortOrder = ObjectiveSortOrder.ASCENDING

            private var objectiveName = UUID.randomUUID().toString()

            fun setCriteria(criteria: ObjectiveCriteria): Builder {
                this.criteria = criteria

                return this
            }

            fun setDisplaySlot(displaySlot: ObjectiveDisplaySlot): Builder {
                this.displaySlot = displaySlot

                return this
            }

            fun setSortOrder(sortOrder: ObjectiveSortOrder): Builder {
                this.sortOrder = sortOrder

                return this
            }

            fun setObjectiveName(objectiveName: String): Builder {
                this.objectiveName = objectiveName

                return this
            }

            fun build(): SimpleScoreboard {
                return SimpleScoreboard(displaySlot, sortOrder, criteria, objectiveName)
            }
        }
    }
}