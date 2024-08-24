package org.zerock.datie_boot.gpt;

import lombok.Data;
import org.zerock.datie_boot.dto.GptMessageDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class GptRequest {
    private String model;
    private List<GptMessageDTO> messages;

    public GptRequest(String model, String prompt) {
        this.model = model;
        this.messages =  new ArrayList<>();
        this.messages.add(new GptMessageDTO("user", prompt));
    }
}