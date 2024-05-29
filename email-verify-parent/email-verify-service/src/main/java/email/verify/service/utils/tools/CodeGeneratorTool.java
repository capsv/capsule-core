package email.verify.service.utils.tools;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

@Component
public class CodeGeneratorTool {

    private static final Random RANDOM = new Random();
    private static final int CODE_LENGTH = 4;

    public int generate() {
        Set<Integer> digits = IntStream.generate(() -> RANDOM.nextInt(10))
            .distinct()
            .limit(CODE_LENGTH)
            .boxed()
            .collect(Collectors.toSet());

        String codeString = digits.stream()
            .map(String::valueOf)
            .collect(Collectors.joining());

        try {
            return Integer.parseInt(codeString);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Error generating code", e);
        }
    }
}
