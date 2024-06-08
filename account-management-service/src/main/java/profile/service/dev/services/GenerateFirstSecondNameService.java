package profile.service.dev.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GenerateFirstSecondNameService {

    private final WebClient client;

    public List<String> get() throws IllegalStateException, IllegalArgumentException {
        Mono<String[]> result = client.get()
            .uri(uriBuilder -> uriBuilder.path("/word")
                .queryParam("number", 2)
                .build())
            .retrieve()
            .bodyToMono(String[].class);

        return result.blockOptional()
            .map(this::capitalizeWords)
            .orElseThrow(() -> new IllegalStateException("no words found"));
    }

    private List<String> capitalizeWords(String[] words) throws IllegalArgumentException {
        if (words == null || words.length == 0) {
            throw new IllegalArgumentException("words cannot be null or empty");
        }

        return Arrays.stream(words)
            .map(this::capitalizeWord)
            .collect(Collectors.toList());
    }

    private String capitalizeWord(String word) {
        return word.substring(0, 1)
            .toUpperCase() + word.substring(1)
            .toLowerCase();
    }
}
