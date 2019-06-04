package com.github.icarohs7.unoxandroidarch.extensions

import khronos.toDate
import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeExtensionsKtTest {
    @Test
    fun `should convert string to date`() {
        //Given
        fun shureConvert(date: String, format: String): Date =
                SimpleDateFormat(format, Locale.getDefault()).parse(date)

        val s1 = "1997-05-09 14:00:00"
        val s2 = "1997-05-09"
        val s3 = "14:00:00"
        val s4 = "25/03/1974 12:00:00"
        val s5 = "25/12/2018"
        val s6 = "17:00"
        val s7 = "25/12/2015 15:00"


        //When
        val d1 = s1.asDate()
        val d2 = s2.asDate()
        val d3 = s3.asDate()
        val d4 = s4.asDate()
        val d5 = s5.asDate()
        val d6 = s6.asDate()
        val d7 = s7.asDate("MM/dd/yyyy HH:mm")

        //Then
        shureConvert(s1, DateFormat.REMOTE_DATETIME) shouldEqual d1
        shureConvert(s2, DateFormat.REMOTE_DATE) shouldEqual d2
        shureConvert(s3, DateFormat.REMOTE_TIME) shouldEqual d3
        shureConvert(s4, DateFormat.BR_DATETIME) shouldEqual d4
        shureConvert(s5, DateFormat.BR_DATE) shouldEqual d5
        shureConvert(s6, DateFormat.SHORT_TIME) shouldEqual d6
        shureConvert(s7, "MM/dd/yyyy HH:mm") shouldEqual d7
    }

    @Test
    fun `get year of date`() {
        //Given
        val d1 = "25/03/1974".asDate("dd/MM/yyyy")
        val d2 = "12/23/2018".asDate("MM/dd/yyyy")
        val d3 = "2000-01-02 15:00:00".asDate("yyyy-MM-dd HH:mm:ss")
        //When
        val y1 = d1.yearValue
        val y2 = d2.yearValue
        val y3 = d3.yearValue
        //Then
        y1 shouldEqual "1974"
        y2 shouldEqual "2018"
        y3 shouldEqual "2000"
    }

    @Test
    fun `get month of date`() {
        //Given
        val d1 = "25/03/1974".asDate("dd/MM/yyyy")
        val d2 = "12/23/2018".asDate("MM/dd/yyyy")
        val d3 = "2000-01-02 15:00:00".asDate("yyyy-MM-dd HH:mm:ss")
        //When
        val m1 = d1.monthValue
        val m2 = d2.monthValue
        val m3 = d3.monthValue
        //Then
        m1 shouldEqual "03"
        m2 shouldEqual "12"
        m3 shouldEqual "01"
    }

    @Test
    fun `get day of date`() {
        //Given
        val d1 = "25/03/1974".asDate("dd/MM/yyyy")
        val d2 = "12/23/2018".asDate("MM/dd/yyyy")
        val d3 = "2000-01-02 15:00:00".asDate("yyyy-MM-dd HH:mm:ss")
        //When
        val day1 = d1.dayValue
        val day2 = d2.dayValue
        val day3 = d3.dayValue
        //Then
        day1 shouldEqual "25"
        day2 shouldEqual "23"
        day3 shouldEqual "02"
    }

    @Test
    fun `should convert date to day number`() {
        //Given
        val d1 = "2018-01-01 12:00" to "yyyy-MM-dd HH:mm"
        val d2 = "09/05/1997" to "dd/MM/yyyy"
        val d3 = "25/03 12:00" to "dd/MM HH:mm"
        fun Pair<String, String>.convert(): Date = first.toDate(second)

        //When
        val e1 = d1.convert().dayNumber
        val e2 = d2.convert().dayNumber
        val e3 = d3.convert().dayNumber

        //Then
        e1 shouldEqual 1
        e2 shouldEqual 9
        e3 shouldEqual 25
    }

    @Test
    fun `should calculate time left`() {
        //Given
        val t1 = 0L
        val t2 = 900_000L
        val t3 = 1_800_000L
        val t4 = 3_600_000L
        val t5 = 7_200_000L
        val t6 = 144_000_000L
        val t7 = 1_000L
        val t8 = 10_000L

        //When
        val e1 = t1.toTimeLeft()
        val e2 = t2.toTimeLeft()
        val e3 = t3.toTimeLeft()
        val e4 = t4.toTimeLeft()
        val e5 = t5.toTimeLeft()
        val e6 = t6.toTimeLeft()
        val e7 = t7.toTimeLeft()
        val e8 = t8.toTimeLeft()

        //Then
        e1 shouldEqual ""
        e2 shouldEqual "15:00"
        e3 shouldEqual "30:00"
        e4 shouldEqual "01:00:00"
        e5 shouldEqual "02:00:00"
        e6 shouldEqual "40:00:00"
        e7 shouldEqual "01"
        e8 shouldEqual "10"
    }
}