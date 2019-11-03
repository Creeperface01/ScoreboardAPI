package gt.creeperface.nukkit.scoreboardapi

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import gt.creeperface.nukkit.scoreboardapi.scoreboard.*

internal class Test(private val api: ScoreboardAPI) : Listener {

    init {
        api.server.pluginManager.registerEvents(this, api)
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player

        val scoreboard = FakeScoreboard()
        val obj = Objective("scoreboard", ObjectiveCriteria("dummy", false))
        val dobj = DisplayObjective(obj, ObjectiveSortOrder.DESCENDING, ObjectiveDisplaySlot.SIDEBAR)
        obj.displayName = "  §fＭｉｎｅ   "
        obj.setScore(1, "§lsite.ru", 1)
        obj.setScore(2, " ", 2)
        obj.setScore(3, "§lKills: §8>>§7 null", 3)
        obj.setScore(4, "§lBalance §8>>§7 " + Math.random() * 100, 4)
        obj.setScore(5, "§l§bStats ", 5)
        obj.setScore(6, "  ", 6)
        obj.setScore(7, "§lGroup §8>>§7 " + Math.random() * 100, 7)
        obj.setScore(8, "§lNickname §8>>§7 " + p.name, 8)
        obj.setScore(9, "§l§bPlayer", 9)
        obj.setScore(10, "   ", 10)

        scoreboard.objective = dobj

        scoreboard.addPlayer(p)

        var time = 2
        api.server.scheduler.scheduleRepeatingTask(api, {
            when (time) {
                2 -> {
                    obj.setScore(4, "§lBalance §8>>§7 " + Math.random() * 100, 4)
                    obj.setScore(1, "§lsite.ru", 1)
                    scoreboard.update()
                }
                1 -> {
                    obj.setScore(4, "§lBalance §8>>§7 " + Math.random() * 100, 4)
                    obj.setScore(1, "§l§9site.ru", 1)
                    scoreboard.update()
                }
                0 -> {
                    obj.setScore(4, "§lBalance §8>>§7 " + Math.random() * 100, 4)
                    obj.setScore(1, "§l§dsite.ru", 1)
                    scoreboard.update()
                }
            }
            time = 2
        }, 40)
    }
}