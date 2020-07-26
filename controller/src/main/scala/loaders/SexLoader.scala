object SexLoader extends Seeder {
    val seedCount: Int = 2

    def data(): Seq[Sex] = {
        Seq(
            Sex(id, "Male"),
            Sex(id, "Female")
        )
    }
}
