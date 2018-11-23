package gt.creeperface.nukkit.scoreboardapi.scoreboard

import gt.creeperface.nukkit.scoreboardapi.packet.SetScorePacket
import gt.creeperface.nukkit.scoreboardapi.packet.data.ScoreInfo
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import it.unimi.dsi.fastutil.longs.LongOpenHashSet

/**
 * @author CreeperFace
 */
class Objective(val name: String, val criteria: ObjectiveCriteria) {

    private val scores = Long2ObjectOpenHashMap<Score>()

    private val modified = LongOpenHashSet()
    private val renamed = LongOpenHashSet()

    var needResend = false
        private set

    private var cachedPacket: SetScorePacket? = null

    var displayName = ""
        set(value) {
            if (value == field) { //nothing changed
                return
            }

            field = value
            needResend = true
        }

    fun setScore(id: Long, name: String, score: Int) {
        val old = scores[id]

        scores[id] = Score(id, name, score)
        clearCache()

        if (old != null) {
            if (old.name != name) {
                renamed.add(id)
            } else if (old.value == score) {
                return //nothing changed
            }
        }

        modified.add(id)
    }

    fun getScore(id: Long): Score? = scores.get(id)

    fun getAllScores() = Long2ObjectOpenHashMap(scores)

    fun resetScore(id: Long) {
        val score = scores.remove(id)
        renamed.remove(id)

        score?.let {
            modified.add(id)
        }
    }

    fun resetAllScores() {
        renamed.clear()
        modified.clear()

        modified.addAll(scores.keys)
        scores.clear()
    }

    fun getChanges(): List<SetScorePacket> {
        if(modified.isEmpty()) {
            return emptyList()
        }

        val setList = mutableListOf<ScoreInfo>()
        val removeList = mutableListOf<ScoreInfo>()

        modified.forEach { id ->
            if(scores.containsKey(id)) {
                val score = getScore(id)

                score?.let {
                    setList.add(ScoreInfo(id, this.name, score.value, score.name))
                }
            } else {
                removeList.add(ScoreInfo(id, this.name, 0, ""))
            }
        }

        renamed.forEach { id ->
            if (scores.containsKey(id)) {
                val score = getScore(id)

                score?.let {
                    removeList.add(ScoreInfo(id, this.name, score.value, score.name))
                }
            }
        }

        val packets = mutableListOf<SetScorePacket>()

        if (removeList.isNotEmpty()) {
            packets.add(SetScorePacket(SetScorePacket.Action.REMOVE, removeList))
        }

        if(setList.isNotEmpty()) {
            packets.add(SetScorePacket(SetScorePacket.Action.SET, setList))
        }

        return packets
    }

    fun getScorePacket(): SetScorePacket? {
        cachedPacket?.let {
            return it
        }

        if(scores.isEmpty()) {
            return null
        }

        val infos = mutableListOf<ScoreInfo>()

        scores.forEach { id, score ->
            infos.add(ScoreInfo(id, this.name, score.value, score.name))
        }

        val pk = SetScorePacket(SetScorePacket.Action.SET, infos)
        pk.encode()
        pk.isEncoded = true

        this.cachedPacket = pk
        return pk
    }

    fun resetChanges() {
        modified.clear()
        needResend = false
    }

    fun clearCache() {
        cachedPacket = null
    }
}