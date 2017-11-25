package de.genetic.bot.simulation

import java.awt.Point


class Simulation(width: Int, height: Int) {
    private val map : Map = Map(width, height)
    private var placedBombs = 0
    private var killScore = 0

    fun addPlayer(point: Point) {
        map.addPlayer(Player(point))
    }

    fun addZombies(points : List<Point>) {
        for (point in points) {
            map.addZombie(Zombie(point))
        }
    }
    fun update(placeBomb : Boolean) {
        if (placeBomb) {
            map.addBomb(Bomb(map.player.position))
            placedBombs++
        }
    }

    fun update(position : Point) : Boolean {
        updatePlayer(position)
        return update()
    }

    private fun update() : Boolean {
        if (!isGameOver()) {
            updateBomb()
            updateZombies()
            return true
        }
        return false
    }
    private fun updatePlayer(position: Point) {
        if (map.isInMap(position)) {
            map.player.move(position)
        }
    }

    private fun isGameOver() : Boolean {
        return map.player.isDead || map.zombies.isEmpty()
    }

    private fun updateBomb() {
        val iterator = map.bombs.iterator()
        while (iterator.hasNext()) {
            val bomb = iterator.next()
            if (bomb.isExploded) {
               iterator.remove()
            } else {
                bomb.tick()
                if (bomb.countdown <= 0) {
                    bomb.explode(map.player, map.zombies)
                }
            }
        }
    }

    private fun updateZombies() {
        var killedZombies = 0
        val iterator = map.zombies.iterator()
        while (iterator.hasNext()) {
            val zombie = iterator.next()
            if (zombie.isDead) {
                iterator.remove()
                killedZombies++
            } else {
                zombie.move(map.player.position)
                if (zombie.position == map.player.position) {
                    zombie.hit(map.player)
                }
            }
        }
        killScore += killedZombies * (killedZombies * 100)
    }

    private fun getScore(): Int {
        return map.player.health / 10 * (map.player.health * 10 - placedBombs * 20 + killScore)
    }

    fun getSimulationState(): SimulationState {
        return SimulationState(map, getScore())
    }

    fun getSimulationStateString(): String {
        val sb = StringBuilder()

        sb.append(map.player.toString())

        sb.append(map.zombies.size)
        sb.append("\n")
        for (z in map.zombies) {
            sb.append(z.toString())
        }

        sb.append(map.bombs.size)
        sb.append("\n")
        for (b in map.bombs) {
            sb.append(b.toString())
        }

        sb.append(getScore())
        sb.append("\n")

        return sb.toString()
    }
}