package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.zerock.datie_boot.gpt.GptRequest;
import org.zerock.datie_boot.gpt.GptResponse;

@RestController
@RequestMapping("/chat")
public class GptController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @PostMapping("/request")
    public String chat(@RequestParam(name = "prompt")String prompt){
        GptRequest request = new GptRequest(model, prompt);
        System.out.println("prompt : " + prompt);
        System.out.println("request : " + request);
        GptResponse gptResponse = template.postForObject(apiURL, request, GptResponse.class);
        System.out.println("Response : " + gptResponse);

        if (gptResponse == null || gptResponse.getChoices() == null || gptResponse.getChoices().isEmpty()) {
            return "No response from GPT API";
        }

        return gptResponse.getChoices().get(0).getMessage().getContent();
    }
}
