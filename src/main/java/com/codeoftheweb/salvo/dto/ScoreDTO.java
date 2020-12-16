package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Score;
import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreDTO {
    private Score score;

    public ScoreDTO() { // Default constructor
    }

    public ScoreDTO(Score score) { // Constructor

        this.score = score;
    }

    public Map<String,Object> makeScoreDTO(Score score) {
        Map<String,Object> dto = new LinkedHashMap<>();
        dto.put("player", score.getPlayer().getId());
        dto.put("score", score.getScore());
        return dto;
    }

    public Score getScore() {  // Getters & Setters
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
