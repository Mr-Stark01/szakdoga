package com.szakdoga.game.network.DTO;

import com.szakdoga.game.units.Unit;

public record UnitDTO(
    float speed,
    float health,
    float damage,
    int price,
    int PreviousX,
    int PreviousY,
    int NextX,
    int NextY,
    float deltaX,
    float deltaY,
    float distance) {
  public UnitDTO(Unit unit) {
      this(unit.getSpeed(),
              unit.getHealth(),
              unit.getDamage(),
              unit.getPrice(),
              unit.getPreviousX(),
              unit.getPreviousY(),
              unit.getNextX(),
              unit.getNextY(),
              unit.getDeltaX(),
              unit.getDeltaY(),
              unit.getDistance());
  }
}
