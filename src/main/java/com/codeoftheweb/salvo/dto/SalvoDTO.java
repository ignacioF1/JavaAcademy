package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Salvo;
import java.util.LinkedHashMap;
import java.util.Map;

public class SalvoDTO {
    private Salvo salvo;

    public SalvoDTO() { // Default constructor
    }

    public SalvoDTO(Salvo salvo) { // Constructor
        this.salvo = salvo;
    }

    public Map<String, Object> makeSalvoDto(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", salvo.getTurn());
        dto.put("player", salvo.getGamePlayer().getPlayer().getId());
        dto.put("locations", salvo.getLocations());
        return dto;
    }

    public Salvo getSalvo() {  // Getters & Setters
        return salvo;
    }

    public void setSalvo(Salvo salvo) {
        this.salvo = salvo;
    }
}
