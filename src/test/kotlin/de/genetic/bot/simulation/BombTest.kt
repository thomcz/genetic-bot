package de.genetic.bot.simulation

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.awt.Point

class BombTest {
    private lateinit var bomb : Bomb

    @Before
    fun init() {
        bomb = Bomb(Point(10,10))
    }

    @Test
    fun tickTest() {
        val before = bomb.countdown
        bomb.tick()
        val after = bomb.countdown
        assertEquals(before - 1, after)
    }
}