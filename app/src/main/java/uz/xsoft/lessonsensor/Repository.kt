package uz.xsoft.lessonsensor

interface Repository {
    fun loadMap()

    fun getMapByLevel(level: Int): Array<Array<Int>>
}