package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Ship;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShipDTO {
    private Ship ship;

    public ShipDTO() { // Default constructor
    }

    public ShipDTO(Ship ship) { // Constructor
        this.ship = ship;
    }

    public Map<String, Object> makeShipDto(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", ship.getType());
        dto.put("locations", ship.getLocations());
        return dto;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
