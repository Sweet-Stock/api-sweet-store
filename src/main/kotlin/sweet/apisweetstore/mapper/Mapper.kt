package sweet.apisweetstore.mapper

interface Mapper<T, U> {

    fun map(t: T): U

}