package org.capsule.com.utils.tools;

//TODO это костыль, этот сервис должен общаться с нейросетью, которая определяет эмоциональный окрас сообщения и в зависимости от эмоции присваивает ранг
public final class GenerateRatingTool {

    public static int generate(String assay){
        int length = assay.length();
        int rating = 1;
        if (length > 0) {
            rating = (int) Math.ceil((double) length / 100);
        }
        return Math.min(rating, 10);
    }
}
