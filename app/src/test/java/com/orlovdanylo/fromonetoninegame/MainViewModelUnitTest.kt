package com.orlovdanylo.fromonetoninegame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.orlovdanylo.fromonetoninegame.presentation.game.GameViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelUnitTest {
    private lateinit var viewModel: GameViewModel
    private lateinit var startModels: List<GameModel>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @Before
    fun before() {
        viewModel = GameViewModel()

        startModels = GameMode.Classic().convertToGameModelsList()

        viewModel.initGame(true)
    }

    @Test
    fun testFirstRound() {

        assertEquals(startModels, viewModel.gameModels.value!!)
        assertEquals(null, viewModel.selectedModel.value)

        // 1 Tap
        viewModel.tap(0)
        assertEquals(viewModel.gameModels.value!!.first(), viewModel.selectedModel.value)

        viewModel.tap(1)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(startModels, viewModel.gameModels.value!!)

        // 2 Tap
        viewModel.tap(0)
        assertEquals(viewModel.gameModels.value!!.first(), viewModel.selectedModel.value)

        viewModel.tap(2)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(startModels, viewModel.gameModels.value!!)

        // 3 Tap
        viewModel.tap(0)
        assertEquals(viewModel.gameModels.value!!.first(), viewModel.selectedModel.value)

        viewModel.tap(9)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![0].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![9].isCrossed)

        /*
            # 2 3 4 5 6 7 8 9   |   00 01 02 03 04 05 06 07 08
            # 1 1 2 1 3 1 4 1   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 9   |   18 19 20 21 22 23 24 25 26
         */

        // 4 Tap
        viewModel.tap(17)
        assertEquals(viewModel.gameModels.value!![17], viewModel.selectedModel.value)

        viewModel.tap(19)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(false, viewModel.gameModels.value!![17].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![19].isCrossed)

        // 5 Tap
        viewModel.tap(8)
        assertEquals(viewModel.gameModels.value!![8], viewModel.selectedModel.value)

        viewModel.tap(11)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(false, viewModel.gameModels.value!![8].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![11].isCrossed)

        // 6 Tap
        viewModel.tap(1)
        assertEquals(viewModel.gameModels.value!![1], viewModel.selectedModel.value)

        viewModel.tap(12)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(false, viewModel.gameModels.value!![1].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![12].isCrossed)

        // 7 Tap
        viewModel.tap(8)
        assertEquals(viewModel.gameModels.value!![8], viewModel.selectedModel.value)

        viewModel.tap(19)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(false, viewModel.gameModels.value!![8].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![19].isCrossed)

        // 8 Tap
        viewModel.tap(8)
        assertEquals(viewModel.gameModels.value!![8], viewModel.selectedModel.value)

        viewModel.tap(26)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(false, viewModel.gameModels.value!![8].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![26].isCrossed)

        // 9 Tap
        viewModel.tap(0)
        assertEquals(null, viewModel.selectedModel.value)

        // 10 Tap
        viewModel.tap(8)
        assertEquals(startModels[8], viewModel.selectedModel.value)

        viewModel.tap(10)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![8].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![10].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 1 3 1 4 1   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 9   |   18 19 20 21 22 23 24 25 26
         */

        viewModel.tap(26)
        assertEquals(startModels[26], viewModel.selectedModel.value)

        viewModel.tap(17)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![17].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![26].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 1 3 1 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 #   |   18 19 20 21 22 23 24 25 26
         */

        viewModel.tap(1)
        assertEquals(startModels[1], viewModel.selectedModel.value)

        viewModel.tap(19)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(false, viewModel.gameModels.value!![1].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![19].isCrossed)

        viewModel.tap(13)
        assertEquals(startModels[13], viewModel.selectedModel.value)

        viewModel.tap(24)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(false, viewModel.gameModels.value!![13].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![24].isCrossed)

        assertEquals(6, viewModel.gameModels.value!!.count { it.isCrossed })
    }

    @Test
    fun testSecondRound() {
        testFirstRound()

        viewModel.updateNumbers()

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 1 3 1 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 #   |   18 19 20 21 22 23 24 25 26
            2 3 4 5 6 7 8 1 2   |   27 28 29 30 31 32 33 34 35
            1 3 1 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 8 1               |   45 46 47
         */

        assertEquals(48, viewModel.gameModels.value!!.size)
        assertEquals(6, viewModel.gameModels.value!!.count { it.isCrossed })
        assertEquals(42, viewModel.gameModels.value!!.count { !it.isCrossed })

        // Tap 1
        viewModel.tap(28)
        assertEquals(viewModel.gameModels.value!![28], viewModel.selectedModel.value)

        viewModel.tap(37)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![28].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![37].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 1 3 1 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 #   |   18 19 20 21 22 23 24 25 26
            2 # 4 5 6 7 8 1 2   |   27 28 29 30 31 32 33 34 35
            1 # 1 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 8 1               |   45 46 47
         */

        // Tap 2
        viewModel.tap(1)
        assertEquals(viewModel.gameModels.value!![1], viewModel.selectedModel.value)

        viewModel.tap(46)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(false, viewModel.gameModels.value!![1].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![46].isCrossed)

        // Tap 3
        viewModel.tap(24)
        assertEquals(viewModel.gameModels.value!![24], viewModel.selectedModel.value)

        viewModel.tap(33)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![24].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![33].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 1 3 1 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 # 1 #   |   18 19 20 21 22 23 24 25 26
            2 # 4 5 6 7 # 1 2   |   27 28 29 30 31 32 33 34 35
            1 # 1 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 8 1               |   45 46 47
         */

        // Tap 4
        viewModel.tap(23)
        assertEquals(viewModel.gameModels.value!![23], viewModel.selectedModel.value)

        viewModel.tap(25)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![23].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![25].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 1 3 1 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # 4 5 6 7 # 1 2   |   27 28 29 30 31 32 33 34 35
            1 # 1 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 8 1               |   45 46 47
         */

        // Tap 5
        viewModel.tap(14)
        assertEquals(viewModel.gameModels.value!![14], viewModel.selectedModel.value)

        viewModel.tap(32)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![14].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![32].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 1 # 1 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # 4 5 6 # # 1 2   |   27 28 29 30 31 32 33 34 35
            1 # 1 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 8 1               |   45 46 47
         */

        // Tap 6
        viewModel.tap(13)
        assertEquals(viewModel.gameModels.value!![13], viewModel.selectedModel.value)

        viewModel.tap(15)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![13].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![15].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # 4 5 6 # # 1 2   |   27 28 29 30 31 32 33 34 35
            1 # 1 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 8 1               |   45 46 47
         */

        // Tap 7
        viewModel.tap(20)
        assertEquals(viewModel.gameModels.value!![20], viewModel.selectedModel.value)

        viewModel.tap(29)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![20].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![29].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 # 1 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # 1 2   |   27 28 29 30 31 32 33 34 35
            1 # 1 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 8 1               |   45 46 47
         */

        // Tap 8
        viewModel.tap(11)
        assertEquals(viewModel.gameModels.value!![11], viewModel.selectedModel.value)

        viewModel.tap(38)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![11].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![38].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # # 2 # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 # 1 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # 1 2   |   27 28 29 30 31 32 33 34 35
            1 # # 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 8 1               |   45 46 47
         */

        // Tap 9
        viewModel.tap(19)
        assertEquals(viewModel.gameModels.value!![19], viewModel.selectedModel.value)

        viewModel.tap(21)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![19].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![21].isCrossed)

        /*
            # 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # # 2 # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 # # # 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # 1 2   |   27 28 29 30 31 32 33 34 35
            1 # # 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 8 1               |   45 46 47
         */

        // Tap 10
        viewModel.tap(1)
        assertEquals(viewModel.gameModels.value!![1], viewModel.selectedModel.value)

        viewModel.tap(46)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![1].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![46].isCrossed)

        /*
            # # 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # # 2 # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 # # # 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # 1 2   |   27 28 29 30 31 32 33 34 35
            1 # # 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            1 # 1               |   45 46 47
         */

        // Tap 11
        viewModel.tap(45)
        assertEquals(viewModel.gameModels.value!![45], viewModel.selectedModel.value)

        viewModel.tap(47)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![45].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![47].isCrossed)

        /*
            # # 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # # 2 # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 # # # 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # 1 2   |   27 28 29 30 31 32 33 34 35
            1 # # 4 5 1 6 1 7   |   36 37 38 39 40 41 42 43 44
            # # #               |   45 46 47
         */

        // Tap 12
        viewModel.tap(34)
        assertEquals(viewModel.gameModels.value!![34], viewModel.selectedModel.value)

        viewModel.tap(43)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![34].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![43].isCrossed)

        /*
            # # 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # # 2 # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 # # # 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # # 2   |   27 28 29 30 31 32 33 34 35
            1 # # 4 5 1 6 # 7   |   36 37 38 39 40 41 42 43 44
            # # #               |   45 46 47
         */

        // Tap 13
        viewModel.tap(7)
        assertEquals(viewModel.gameModels.value!![7], viewModel.selectedModel.value)

        viewModel.tap(12)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![7].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![12].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00 01 02 03 04 05 06 07 08
            # # # # # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 # # # 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # # 2   |   27 28 29 30 31 32 33 34 35
            1 # # 4 5 1 6 # 7   |   36 37 38 39 40 41 42 43 44
            # # #               |   45 46 47
         */

        assertEquals(30, viewModel.gameModels.value!!.count { it.isCrossed })
        assertEquals(18, viewModel.gameModels.value!!.count { !it.isCrossed })

        // Tap 14
        viewModel.tap(2)
        assertEquals(viewModel.gameModels.value!![2], viewModel.selectedModel.value)

        viewModel.tap(2)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(false, viewModel.gameModels.value!![2].isCrossed)
    }

    @Test
    fun testThirdRound() {
        testSecondRound()

        viewModel.updateNumbers()

        /*
            # # 3 4 5 6 7 # #   |   00 01 02 03 04 05 06 07 08
            # # # # # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 # # # 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # # 2   |   27 28 29 30 31 32 33 34 35
            1 # # 4 5 1 6 # 7   |   36 37 38 39 40 41 42 43 44
            # # # 3 4 5 6 7 4   |   45 46 47 48 49 50 51 52 53
            5 7 2 5 6 2 1 4 5   |   54 55 56 57 58 59 60 61 62
            1 6 7               |   63 64 65
         */

        assertEquals(66, viewModel.gameModels.value!!.size)
        assertEquals(30, viewModel.gameModels.value!!.count { it.isCrossed })
        assertEquals(36, viewModel.gameModels.value!!.count { !it.isCrossed })

        viewModel.tap(48)
        assertEquals(viewModel.gameModels.value!![48], viewModel.selectedModel.value)

        viewModel.tap(44)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![48].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![44].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00 01 02 03 04 05 06 07 08
            # # # # # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 # # # 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # # 2   |   27 28 29 30 31 32 33 34 35
            1 # # 4 5 1 6 # #   |   36 37 38 39 40 41 42 43 44
            # # # # 4 5 6 7 4   |   45 46 47 48 49 50 51 52 53
            5 7 2 5 6 2 1 4 5   |   54 55 56 57 58 59 60 61 62
            1 6 7               |   63 64 65
         */

        viewModel.tap(42)
        assertEquals(viewModel.gameModels.value!![42], viewModel.selectedModel.value)

        viewModel.tap(49)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![42].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![49].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00 01 02 03 04 05 06 07 08
            # # # # # # # 4 #   |   09 10 11 12 13 14 15 16 17
            5 # # # 7 # # # #   |   18 19 20 21 22 23 24 25 26
            2 # # 5 6 # # # 2   |   27 28 29 30 31 32 33 34 35
            1 # # 4 5 1 # # #   |   36 37 38 39 40 41 42 43 44
            # # # # # 5 6 7 4   |   45 46 47 48 49 50 51 52 53
            5 7 2 5 6 2 1 4 5   |   54 55 56 57 58 59 60 61 62
            1 6 7               |   63 64 65
         */
    }

    @Test
    fun testFourthRound() {
        testThirdRound()

        viewModel.updateNumbers()

        /*
            # # 3 4 5 6 7 # #   |   00  01  02  03  04  05  06  07  08
            # # # # # # # 4 #   |   09  10  11  12  13  14  15  16  17
            5 # # # 7 # # # #   |   18  19  20  21  22  23  24  25  26
            2 # # 5 6 # # # 2   |   27  28  29  30  31  32  33  34  35
            1 # # 4 5 1 # # #   |   36  37  38  39  40  41  42  43  44
            # # # # # 5 6 7 4   |   45  46  47  48  49  50  51  52  53
            5 7 2 5 6 2 1 4 5   |   54  55  56  57  58  59  60  61  62
            1 6 7 3 4 5 6 7 4   |   63  64  65  66  67  68  69  70  71
            5 7 2 5 6 2 1 4 5   |   72  73  74  75  76  77  78  79  80
            1 5 6 7 4 5 7 2 5   |   81  82  83  84  85  86  87  88  89
            6 2 1 4 5 1 6 7     |   90  91  92  93  94  95  96  97
         */

        assertEquals(98, viewModel.gameModels.value!!.size)
        assertEquals(34, viewModel.gameModels.value!!.count { it.isCrossed })
        assertEquals(64, viewModel.gameModels.value!!.count { !it.isCrossed })

        viewModel.tap(58)
        assertEquals(viewModel.gameModels.value!![58], viewModel.selectedModel.value)

        viewModel.tap(67)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![58].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![67].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00  01  02  03  04  05  06  07  08
            # # # # # # # 4 #   |   09  10  11  12  13  14  15  16  17
            5 # # # 7 # # # #   |   18  19  20  21  22  23  24  25  26
            2 # # 5 6 # # # 2   |   27  28  29  30  31  32  33  34  35
            1 # # 4 5 1 # # #   |   36  37  38  39  40  41  42  43  44
            # # # # # 5 6 7 4   |   45  46  47  48  49  50  51  52  53
            5 7 2 5 # 2 1 4 5   |   54  55  56  57  58  59  60  61  62
            1 6 7 3 # 5 6 7 4   |   63  64  65  66  67  68  69  70  71
            5 7 2 5 6 2 1 4 5   |   72  73  74  75  76  77  78  79  80
            1 5 6 7 4 5 7 2 5   |   81  82  83  84  85  86  87  88  89
            6 2 1 4 5 1 6 7     |   90  91  92  93  94  95  96  97
         */

        viewModel.tap(76)
        assertEquals(viewModel.gameModels.value!![76], viewModel.selectedModel.value)

        viewModel.tap(85)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![76].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![85].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00  01  02  03  04  05  06  07  08
            # # # # # # # 4 #   |   09  10  11  12  13  14  15  16  17
            5 # # # 7 # # # #   |   18  19  20  21  22  23  24  25  26
            2 # # 5 6 # # # 2   |   27  28  29  30  31  32  33  34  35
            1 # # 4 5 1 # # #   |   36  37  38  39  40  41  42  43  44
            # # # # # 5 6 7 4   |   45  46  47  48  49  50  51  52  53
            5 7 2 5 # 2 1 4 5   |   54  55  56  57  58  59  60  61  62
            1 6 7 3 # 5 6 7 4   |   63  64  65  66  67  68  69  70  71
            5 7 2 5 # 2 1 4 5   |   72  73  74  75  76  77  78  79  80
            1 5 6 7 # 5 7 2 5   |   81  82  83  84  85  86  87  88  89
            6 2 1 4 5 1 6 7     |   90  91  92  93  94  95  96  97
         */

        viewModel.tap(94)
        assertEquals(viewModel.gameModels.value!![94], viewModel.selectedModel.value)

        viewModel.tap(40)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![94].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![40].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00  01  02  03  04  05  06  07  08
            # # # # # # # 4 #   |   09  10  11  12  13  14  15  16  17
            5 # # # 7 # # # #   |   18  19  20  21  22  23  24  25  26
            2 # # 5 6 # # # 2   |   27  28  29  30  31  32  33  34  35
            1 # # 4 # 1 # # #   |   36  37  38  39  40  41  42  43  44
            # # # # # 5 6 7 4   |   45  46  47  48  49  50  51  52  53
            5 7 2 5 # 2 1 4 5   |   54  55  56  57  58  59  60  61  62
            1 6 7 3 # 5 6 7 4   |   63  64  65  66  67  68  69  70  71
            5 7 2 5 # 2 1 4 5   |   72  73  74  75  76  77  78  79  80
            1 5 6 7 # 5 7 2 5   |   81  82  83  84  85  86  87  88  89
            6 2 1 4 # 1 6 7     |   90  91  92  93  94  95  96  97
         */

        viewModel.tap(80)
        assertEquals(viewModel.gameModels.value!![80], viewModel.selectedModel.value)

        viewModel.tap(89)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![80].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![89].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00  01  02  03  04  05  06  07  08
            # # # # # # # 4 #   |   09  10  11  12  13  14  15  16  17
            5 # # # 7 # # # #   |   18  19  20  21  22  23  24  25  26
            2 # # 5 6 # # # 2   |   27  28  29  30  31  32  33  34  35
            1 # # 4 # 1 # # #   |   36  37  38  39  40  41  42  43  44
            # # # # # 5 6 7 4   |   45  46  47  48  49  50  51  52  53
            5 7 2 5 # 2 1 4 5   |   54  55  56  57  58  59  60  61  62
            1 6 7 3 # 5 6 7 4   |   63  64  65  66  67  68  69  70  71
            5 7 2 5 # 2 1 4 #   |   72  73  74  75  76  77  78  79  80
            1 5 6 7 # 5 7 2 #   |   81  82  83  84  85  86  87  88  89
            6 2 1 4 # 1 6 7     |   90  91  92  93  94  95  96  97
         */

        viewModel.tap(65)
        assertEquals(viewModel.gameModels.value!![65], viewModel.selectedModel.value)

        viewModel.tap(66)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![65].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![66].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00  01  02  03  04  05  06  07  08
            # # # # # # # 4 #   |   09  10  11  12  13  14  15  16  17
            5 # # # 7 # # # #   |   18  19  20  21  22  23  24  25  26
            2 # # 5 6 # # # 2   |   27  28  29  30  31  32  33  34  35
            1 # # 4 # 1 # # #   |   36  37  38  39  40  41  42  43  44
            # # # # # 5 6 7 4   |   45  46  47  48  49  50  51  52  53
            5 7 2 5 # 2 1 4 5   |   54  55  56  57  58  59  60  61  62
            1 6 # # # 5 6 7 4   |   63  64  65  66  67  68  69  70  71
            5 7 2 5 # 2 1 4 #   |   72  73  74  75  76  77  78  79  80
            1 5 6 7 # 5 7 2 #   |   81  82  83  84  85  86  87  88  89
            6 2 1 4 # 1 6 7     |   90  91  92  93  94  95  96  97
         */

        viewModel.tap(56)
        assertEquals(viewModel.gameModels.value!![56], viewModel.selectedModel.value)

        viewModel.tap(74)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![56].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![74].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00  01  02  03  04  05  06  07  08
            # # # # # # # 4 #   |   09  10  11  12  13  14  15  16  17
            5 # # # 7 # # # #   |   18  19  20  21  22  23  24  25  26
            2 # # 5 6 # # # 2   |   27  28  29  30  31  32  33  34  35
            1 # # 4 # 1 # # #   |   36  37  38  39  40  41  42  43  44
            # # # # # 5 6 7 4   |   45  46  47  48  49  50  51  52  53
            5 7 # 5 # 2 1 4 5   |   54  55  56  57  58  59  60  61  62
            1 6 # # # 5 6 7 4   |   63  64  65  66  67  68  69  70  71
            5 7 # 5 # 2 1 4 #   |   72  73  74  75  76  77  78  79  80
            1 5 6 7 # 5 7 2 #   |   81  82  83  84  85  86  87  88  89
            6 2 1 4 # 1 6 7     |   90  91  92  93  94  95  96  97
         */

        viewModel.tap(57)
        assertEquals(viewModel.gameModels.value!![57], viewModel.selectedModel.value)

        viewModel.tap(75)
        assertEquals(null, viewModel.selectedModel.value)
        assertEquals(true, viewModel.gameModels.value!![57].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![75].isCrossed)

        /*
            # # 3 4 5 6 7 # #   |   00  01  02  03  04  05  06  07  08
            # # # # # # # 4 #   |   09  10  11  12  13  14  15  16  17
            5 # # # 7 # # # #   |   18  19  20  21  22  23  24  25  26
            2 # # 5 6 # # # 2   |   27  28  29  30  31  32  33  34  35
            1 # # 4 # 1 # # #   |   36  37  38  39  40  41  42  43  44
            # # # # # 5 6 7 4   |   45  46  47  48  49  50  51  52  53
            5 7 # # # 2 1 4 5   |   54  55  56  57  58  59  60  61  62
            1 6 # # # 5 6 7 4   |   63  64  65  66  67  68  69  70  71
            5 7 # # # 2 1 4 #   |   72  73  74  75  76  77  78  79  80
            1 5 6 7 # 5 7 2 #   |   81  82  83  84  85  86  87  88  89
            6 2 1 4 # 1 6 7     |   90  91  92  93  94  95  96  97
         */
    }
}