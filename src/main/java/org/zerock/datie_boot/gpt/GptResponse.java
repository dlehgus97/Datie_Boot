package org.zerock.datie_boot.gpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.datie_boot.dto.GptMessageDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GptResponse {
    private List<Choice> choices;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private GptMessageDTO message;

    }
}