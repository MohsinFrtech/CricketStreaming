package com.dream.live.cricket.score.hd.streaming.date

class ScreenUtil {


    var fullSize=0
    fun dateFunction(valueParams: String): Triple<Array<String>,Array<String>,Array<String>> {
        val myValue = valueParams
        val mainArray: Array<String> = myValue.toCharArray().map { it.toString() }.toTypedArray()
        val mainArraySize = mainArray.size
        fullSize=mainArraySize
        val array1 = mainArray.copyOfRange(0, (mainArraySize + 1) / 3)
        val arr2 = mainArray.copyOfRange((mainArraySize + 1) / 3, mainArraySize)
        val array2 = arr2.copyOfRange(0, (arr2.size + 1) / 2)
        val array3 = arr2.copyOfRange((arr2.size + 1) / 2, arr2.size)
        return Triple(array1,array2,array3)
    }



    fun returnValueOfSize():Int{
        return fullSize
    }


    fun reMem():Int{
        return 1
    }

    fun reMem2():Int{
        return 10
    }
    fun reMem3():Int{
        return 11
    }
    fun reMem4():Int{
        return 12
    }

    fun reMem5():Int{
        return 13
    }

    fun reMem6():Int{
        return 14
    }
    fun reMem7():Int{
        return 15
    }
    fun reMem16():Int{
        return 16
    }

    fun reMem17():Int{
        return 17
    }

    fun reMem18():Int{
        return 18
    }
}