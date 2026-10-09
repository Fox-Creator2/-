package com.example.model

enum class EmojiCategory(val displayNameRu: String, val icon: String) {
    ALL("Все смайлы", "🌟"),
    SMILES("Эмоции и лица", "😀"),
    ANIMALS("Животные", "🐱"),
    FOOD("Еда и вкусняшки", "🍕"),
    MAGIC_SPACE("Космос и магия", "🚀"),
    HEARTS("Любовь и чувства", "💖")
}

data class EmojiItem(
    val emoji: String,
    val nameRu: String,
    val nameEn: String,
    val category: EmojiCategory
)

object EmojiRepository {
    val allEmojis: List<EmojiItem> = listOf(
        // SMILES
        EmojiItem("😀", "Широкая улыбка", "Grinning Face", EmojiCategory.SMILES),
        EmojiItem("😃", "Радость", "Smiling Face with Big Eyes", EmojiCategory.SMILES),
        EmojiItem("😄", "Смех и веселье", "Grinning Face with Smiling Eyes", EmojiCategory.SMILES),
        EmojiItem("😁", "Сияющая улыбка", "Beaming Face", EmojiCategory.SMILES),
        EmojiItem("😆", "Задорный смех", "Grinning Squinting Face", EmojiCategory.SMILES),
        EmojiItem("🥹", "Слёзы умиления", "Face Holding Back Tears", EmojiCategory.SMILES),
        EmojiItem("😅", "Неловкая улыбка", "Grinning Face with Sweat", EmojiCategory.SMILES),
        EmojiItem("😂", "Слёзы от смеха", "Face with Tears of Joy", EmojiCategory.SMILES),
        EmojiItem("🤣", "Катаюсь со смеху", "Rolling on the Floor Laughing", EmojiCategory.SMILES),
        EmojiItem("😊", "Тёплая улыбка", "Smiling Face with Smiling Eyes", EmojiCategory.SMILES),
        EmojiItem("😇", "Ангельское настроение", "Smiling Face with Halo", EmojiCategory.SMILES),
        EmojiItem("🙂", "Спокойная улыбка", "Slightly Smiling Face", EmojiCategory.SMILES),
        EmojiItem("🙃", "Всё вверх дном", "Upside-Down Face", EmojiCategory.SMILES),
        EmojiItem("😉", "Подмигивание", "Winking Face", EmojiCategory.SMILES),
        EmojiItem("😌", "Полный дзен", "Relieved Face", EmojiCategory.SMILES),
        EmojiItem("😍", "Влюблённые глаза", "Heart-Eyes", EmojiCategory.SMILES),
        EmojiItem("🥰", "Окружён любовью", "Smiling Face with Hearts", EmojiCategory.SMILES),
        EmojiItem("😘", "Воздушный поцелуй", "Face Blowing a Kiss", EmojiCategory.SMILES),
        EmojiItem("😋", "Пальчики оближешь", "Face Savoring Food", EmojiCategory.SMILES),
        EmojiItem("😜", "Хулиганская улыбка", "Winking Face with Tongue", EmojiCategory.SMILES),
        EmojiItem("🤪", "Безумное веселье", "Zany Face", EmojiCategory.SMILES),
        EmojiItem("🤓", "Гений и эрудит", "Nerd Face", EmojiCategory.SMILES),
        EmojiItem("😎", "На полном стиле", "Smiling Face with Sunglasses", EmojiCategory.SMILES),
        EmojiItem("🤩", "В полном восторге", "Star-Struck", EmojiCategory.SMILES),
        EmojiItem("🥳", "Вечеринка и праздник", "Partying Face", EmojiCategory.SMILES),
        EmojiItem("😏", "Хитрый прищур", "Smirking Face", EmojiCategory.SMILES),
        EmojiItem("🤔", "Глубокие размышления", "Thinking Face", EmojiCategory.SMILES),
        EmojiItem("🫡", "Так точно!", "Saluting Face", EmojiCategory.SMILES),
        EmojiItem("🤗", "Дружеские обнимашки", "Hugging Face", EmojiCategory.SMILES),
        EmojiItem("🤫", "Секрет и тайна", "Shushing Face", EmojiCategory.SMILES),
        EmojiItem("🤭", "Хихиканье в кулачок", "Face with Hand Over Mouth", EmojiCategory.SMILES),
        EmojiItem("🫣", "Подглядывание", "Face with Peeking Eye", EmojiCategory.SMILES),
        EmojiItem("🤯", "Взрыв мозга", "Exploding Head", EmojiCategory.SMILES),
        EmojiItem("🤠", "Ковбой прерий", "Cowboy Hat Face", EmojiCategory.SMILES),
        EmojiItem("🥳", "Салют и поздравления", "Celebration", EmojiCategory.SMILES),
        EmojiItem("🤖", "Дружелюбный робот", "Robot", EmojiCategory.SMILES),
        EmojiItem("👻", "Привидение Бу!", "Ghost", EmojiCategory.SMILES),
        EmojiItem("👾", "Ретро-монстрик", "Alien Monster", EmojiCategory.SMILES),

        // ANIMALS
        EmojiItem("🐶", "Преданный щенок", "Puppy Dog", EmojiCategory.ANIMALS),
        EmojiItem("🐱", "Милый котёнок", "Cat Face", EmojiCategory.ANIMALS),
        EmojiItem("🐭", "Любопытный мышонок", "Mouse Face", EmojiCategory.ANIMALS),
        EmojiItem("🐹", "Запасливый хомяк", "Hamster", EmojiCategory.ANIMALS),
        EmojiItem("🐰", "Пушистый кролик", "Rabbit Face", EmojiCategory.ANIMALS),
        EmojiItem("🦊", "Хитрая лисичка", "Fox", EmojiCategory.ANIMALS),
        EmojiItem("🐻", "Добрый мишка", "Bear", EmojiCategory.ANIMALS),
        EmojiItem("🐼", "Бамбуковая панда", "Panda", EmojiCategory.ANIMALS),
        EmojiItem("🐨", "Уютная коала", "Koala", EmojiCategory.ANIMALS),
        EmojiItem("🐯", "Смелый тигрёнок", "Tiger Face", EmojiCategory.ANIMALS),
        EmojiItem("🦁", "Царь зверей", "Lion", EmojiCategory.ANIMALS),
        EmojiItem("🐮", "Добрая коровка", "Cow Face", EmojiCategory.ANIMALS),
        EmojiItem("🐸", "Царевна-лягушка", "Frog", EmojiCategory.ANIMALS),
        EmojiItem("🐵", "Озорная обезьянка", "Monkey Face", EmojiCategory.ANIMALS),
        EmojiItem("🦄", "Сказочный единорог", "Unicorn", EmojiCategory.ANIMALS),
        EmojiItem("🐧", "Пингвин во фраке", "Penguin", EmojiCategory.ANIMALS),
        EmojiItem("🦉", "Мудрая сова", "Owl", EmojiCategory.ANIMALS),
        EmojiItem("🦋", "Порхающая бабочка", "Butterfly", EmojiCategory.ANIMALS),
        EmojiItem("🐝", "Трудолюбивая пчёлка", "Honeybee", EmojiCategory.ANIMALS),
        EmojiItem("🐞", "Божья коровка на удачу", "Lady Beetle", EmojiCategory.ANIMALS),
        EmojiItem("🐬", "Игривый дельфин", "Dolphin", EmojiCategory.ANIMALS),
        EmojiItem("🐳", "Добрый синий кит", "Spouting Whale", EmojiCategory.ANIMALS),
        EmojiItem("🐙", "Морской осьминог", "Octopus", EmojiCategory.ANIMALS),
        EmojiItem("🦔", "Лесной ёжик", "Hedgehog", EmojiCategory.ANIMALS),
        EmojiItem("🦥", "Безмятежный ленивец", "Sloth", EmojiCategory.ANIMALS),
        EmojiItem("🦦", "Милая выдра", "Otter", EmojiCategory.ANIMALS),

        // FOOD
        EmojiItem("🍕", "Горячая пицца", "Pizza", EmojiCategory.FOOD),
        EmojiItem("🍔", "Сочный бургер", "Hamburger", EmojiCategory.FOOD),
        EmojiItem("🍟", "Хрустящая картошка", "French Fries", EmojiCategory.FOOD),
        EmojiItem("🌮", "Мексиканское тако", "Taco", EmojiCategory.FOOD),
        EmojiItem("🍣", "Свежие суши", "Sushi", EmojiCategory.FOOD),
        EmojiItem("🍜", "Ароматный рамен", "Steaming Bowl", EmojiCategory.FOOD),
        EmojiItem("🍦", "Сливочное мороженое", "Soft Ice Cream", EmojiCategory.FOOD),
        EmojiItem("🍩", "Глазированный пончик", "Doughnut", EmojiCategory.FOOD),
        EmojiItem("🍪", "Хрустящее печенье", "Cookie", EmojiCategory.FOOD),
        EmojiItem("🎂", "Праздничный торт", "Birthday Cake", EmojiCategory.FOOD),
        EmojiItem("🧁", "Сладкий капкейк", "Cupcake", EmojiCategory.FOOD),
        EmojiItem("🍫", "Плитка шоколада", "Chocolate Bar", EmojiCategory.FOOD),
        EmojiItem("🍓", "Спелая клубника", "Strawberry", EmojiCategory.FOOD),
        EmojiItem("🍉", "Летний арбуз", "Watermelon", EmojiCategory.FOOD),
        EmojiItem("🍒", "Сладкая вишня", "Cherries", EmojiCategory.FOOD),
        EmojiItem("🥑", "Спелый авокадо", "Avocado", EmojiCategory.FOOD),
        EmojiItem("🍿", "Вкусный попкорн", "Popcorn", EmojiCategory.FOOD),
        EmojiItem("🥞", "Пышные блинчики", "Pancakes", EmojiCategory.FOOD),
        EmojiItem("🧇", "Бельгийские вафли", "Waffle", EmojiCategory.FOOD),
        EmojiItem("🧋", "Бабл-ти с шариками", "Bubble Tea", EmojiCategory.FOOD),

        // MAGIC & SPACE
        EmojiItem("🚀", "Космическая ракета", "Rocket Launch", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🛸", "Летающая тарелка НЛО", "Flying Saucer", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🪐", "Планета с кольцами", "Ringed Planet", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🌍", "Наша прекрасная Земля", "Globe Showing Europe-Africa", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🌕", "Яркое полнолуние", "Full Moon", EmojiCategory.MAGIC_SPACE),
        EmojiItem("⭐", "Золотая звезда", "Star", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🌟", "Сияющая звезда", "Glowing Star", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🌠", "Падающая звезда желаний", "Shooting Star", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🌌", "Млечный Путь", "Milky Way Galaxy", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🌈", "Радужный мост", "Rainbow", EmojiCategory.MAGIC_SPACE),
        EmojiItem("⚡", "Мощная молния", "High Voltage", EmojiCategory.MAGIC_SPACE),
        EmojiItem("✨", "Волшебные искры", "Sparkles", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🪄", "Магическая палочка", "Magic Wand", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🔮", "Магический шар предсказаний", "Crystal Ball", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🔥", "Яркий огонь", "Fire", EmojiCategory.MAGIC_SPACE),
        EmojiItem("💎", "Драгоценный алмаз", "Gem Stone", EmojiCategory.MAGIC_SPACE),
        EmojiItem("☀️", "Теплое солнышко", "Sun", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🎉", "Хлопушка с конфетти", "Party Popper", EmojiCategory.MAGIC_SPACE),
        EmojiItem("🎆", "Праздничный фейерверк", "Fireworks", EmojiCategory.MAGIC_SPACE),

        // HEARTS & LOVE
        EmojiItem("💖", "Сверкающее сердечко", "Sparkling Heart", EmojiCategory.HEARTS),
        EmojiItem("❤️", "Алое сердце", "Red Heart", EmojiCategory.HEARTS),
        EmojiItem("🧡", "Оранжевое тепло", "Orange Heart", EmojiCategory.HEARTS),
        EmojiItem("💛", "Золотое сердце", "Yellow Heart", EmojiCategory.HEARTS),
        EmojiItem("💚", "Зелёная гармония", "Green Heart", EmojiCategory.HEARTS),
        EmojiItem("💙", "Синяя глубина", "Blue Heart", EmojiCategory.HEARTS),
        EmojiItem("💜", "Фиолетовая магия", "Purple Heart", EmojiCategory.HEARTS),
        EmojiItem("🤍", "Чистое сердце", "White Heart", EmojiCategory.HEARTS),
        EmojiItem("💕", "Два любящих сердца", "Two Hearts", EmojiCategory.HEARTS),
        EmojiItem("💞", "Кружащиеся сердца", "Revolving Hearts", EmojiCategory.HEARTS),
        EmojiItem("💓", "Биение сердца", "Beating Heart", EmojiCategory.HEARTS),
        EmojiItem("💗", "Растущая любовь", "Growing Heart", EmojiCategory.HEARTS),
        EmojiItem("💘", "Стрела Амура", "Heart with Arrow", EmojiCategory.HEARTS),
        EmojiItem("💝", "Сердце в подарок", "Heart with Ribbon", EmojiCategory.HEARTS),
        EmojiItem("💐", "Роскошный букет", "Bouquet of Flowers", EmojiCategory.HEARTS),
        EmojiItem("🌸", "Цветущая сакура", "Cherry Blossom", EmojiCategory.HEARTS)
    )

    fun getRandomEmoji(category: EmojiCategory = EmojiCategory.ALL): EmojiItem {
        val pool = if (category == EmojiCategory.ALL) {
            allEmojis
        } else {
            allEmojis.filter { it.category == category }
        }
        return pool.randomOrNull() ?: allEmojis.first()
    }

    val defaultEmoji: EmojiItem = EmojiItem(
        emoji = "👋",
        nameRu = "Привет, Мир!",
        nameEn = "Hello, World!",
        category = EmojiCategory.ALL
    )
}
