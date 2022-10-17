package by.beltelecom.todolist.utilities;

import java.util.List;
import java.util.Random;

public class Randomizer {

    private static final Random random = new Random(); // Random;
    private static final List<String> randomStrings = List.of("лекарь", "сексология", "трепальщица", "сумматор", "лизол", "трансформаторостроение", "хорёк",
            "жонглирование", "окропление", "чемерица", "скалолазание", "рисование", "электрокар", "медоварня", "текстология", "хор", "подмостка", "меньшевик", "интеллигент", "ремонтантность", "припев", "жалейщик", "эмпирей", "кармазин", "любое", "планктон", "камбоджиец", "гастролёр", "присутствующий", "карат", "туркестанка", "бельмо", "мирабилит", "цензорство", "нечёткость", "торкрет", "фистулография", "срезальщик", "слоновщик", "кожа", "прижигание", "тутор", "старение", "пиодермия", "курлыкание", "детва", "двусемянка", "железняк", "пруссак",
            "махинация"); // List of strings;

    public static String randomStringFromList() {
        return randomStrings.get(random.nextInt(randomStrings.size()-1));
    }

    public static String randomSentence(int aNumberOfWords) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<aNumberOfWords; i++)
            sb.append(Randomizer.randomStringFromList()).append(" ");
        return sb.toString();
    }

}
