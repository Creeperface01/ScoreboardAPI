package gt.creeperface.nukkit.scoreboardapi

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.plugin.PluginBase
import cn.nukkit.utils.TextFormat
import gt.creeperface.nukkit.scoreboardapi.packet.RemoveObjectivePacket
import gt.creeperface.nukkit.scoreboardapi.packet.SetDisplayObjectivePacket
import gt.creeperface.nukkit.scoreboardapi.packet.SetScorePacket
import gt.creeperface.nukkit.scoreboardapi.scoreboard.*

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
//        server.pluginManager.registerEvents(this, this) //test
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player

        val scoreboard = FakeScoreboard()
        val obj = Objective("test", ObjectiveCriteria("dummy", true))

        val dobj = DisplayObjective(
                obj,
                ObjectiveSortOrder.DESCENDING,
                ObjectiveDisplaySlot.SIDEBAR
        )

        obj.displayName = TextFormat.YELLOW.toString() + "Game" + TextFormat.WHITE + "Team"
        obj.setScore(1, "${TextFormat.RED} ---------------  ", 6)
        obj.setScore(2, "cau", 5)
        obj.setScore(3, "cau 1", 4)
        obj.setScore(4, "cau 2", 3)
        obj.setScore(5, "cau 3", 2)
        obj.setScore(6, "cau 4", 1)

        scoreboard.objective = dobj

        server.scheduler.scheduleDelayedTask(this, {
            scoreboard.spawnTo(p)
        }, 60)
    }
}