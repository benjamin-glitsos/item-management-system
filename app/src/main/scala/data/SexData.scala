object SexData extends Seeder {
    val seedCount: Int = 2

    val data: Seq[Sex] = Seq(
        Sex(id, "Male"),
        Sex(id, "Female")
    )
}
