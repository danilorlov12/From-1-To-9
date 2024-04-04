package com.orlovdanylo.fromonetoninegame.tip

import com.orlovdanylo.fromonetoninegame.TipController
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TipUnitTest {

    @Before
    fun before() {

    }

    @Test
    fun testSimpleCases() {
        val case1 = GameCase.Case1()
        val pair1 = TipController(case1.convertToGameModels()).fetchAvailablePair()
        assertEquals(case1.response, pair1)

        val case2 = GameCase.Case2()
        val pair2 = TipController(case2.convertToGameModels()).fetchAvailablePair()
        assertEquals(case2.response, pair2)

        val case3 = GameCase.Case3()
        val pair3 = TipController(case3.convertToGameModels()).fetchAvailablePair()
        assertEquals(case3.response, pair3)

        val case4 = GameCase.Case4()
        val pair4 = TipController(case4.convertToGameModels()).fetchAvailablePair()
        assertEquals(case4.response, pair4)

        val case5 = GameCase.Case5()
        val pair5 = TipController(case5.convertToGameModels()).fetchAvailablePair()
        assertEquals(case5.response, pair5)

        val case6 = GameCase.Case6()
        val pair6 = TipController(case6.convertToGameModels()).fetchAvailablePair()
        assertEquals(case6.response, pair6)
    }

    @Test
    fun testMediumCases() {
        val case7 = GameCase.Case7()
        val pair7 = TipController(case7.convertToGameModels()).fetchAvailablePair()
        assertEquals(case7.response, pair7)

        val case8 = GameCase.Case8()
        val pair8 = TipController(case8.convertToGameModels()).fetchAvailablePair()
        assertEquals(case8.response, pair8)

        val case9 = GameCase.Case9()
        val pair9 = TipController(case9.convertToGameModels()).fetchAvailablePair()
        assertEquals(case9.response, pair9)

        val case10 = GameCase.Case10()
        val pair10 = TipController(case10.convertToGameModels()).fetchAvailablePair()
        assertEquals(case10.response, pair10)
    }
}