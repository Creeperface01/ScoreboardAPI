package gt.creeperface.nukkit.scoreboardapi.packet.data

import gt.creeperface.nukkit.scoreboardapi.scoreboard.Scoreboard

/**
 * @author CreeperFace
 */
data class ScoreInfo(val scoreId: Long, val objective: String, val score: Int, val name: String)