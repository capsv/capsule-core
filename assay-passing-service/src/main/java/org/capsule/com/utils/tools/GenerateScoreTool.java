package org.capsule.com.utils.tools;

//TODO это костыль, этот сервис должен общаться с нейросетью, которая определяет эмоциональный окрас сообщения и в зависимости от эмоции присваивает ранг
public final class GenerateScoreTool {

    public static int generate(String assay){
        int length = assay.length();
        int score = 1;
        if (length > 0) {
            score = (int) Math.ceil((double) length / 50);
        }
        return Math.min(score, 10);
    }
}
