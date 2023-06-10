package org.flickit.flickitassessmentcore.adapter.out.persistence.answeroptionimpact;

import org.flickit.flickitassessmentcore.application.port.out.LoadAnswerOptionImpactsByAnswerOptionPort;
import org.flickit.flickitassessmentcore.domain.AnswerOptionImpact;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Set;

@Component
public class AnswerOptionImpactPersistenceAdapter implements LoadAnswerOptionImpactsByAnswerOptionPort {
    @Override
    public Set<AnswerOptionImpact> findAnswerOptionImpactsByAnswerOption(Long answerOptionId) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(10))
            .messageConverters(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "https://api.example.com/data";
        ResponseEntity<Set<AnswerOptionImpact>> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Set<AnswerOptionImpact>>() {}
        );
        Set<AnswerOptionImpact> responseBody = responseEntity.getBody();
        return responseBody;
    }

    /*
     * TODO:
     *  - must complete this class with true data
     * */
}