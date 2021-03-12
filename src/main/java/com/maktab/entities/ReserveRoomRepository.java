package com.maktab.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReserveRoomRepository {
    private static final List<ReserveRoom> rooms = new ArrayList<>();

    public void addRoom(ReserveRoom room) {
        rooms.add(room);
    }

    public void addRoom(List<ReserveRoom> rooms) {
        ReserveRoomRepository.rooms.addAll(rooms);
    }

    public void deleteRoom(ReserveRoom room) {
        rooms.remove(room);
    }

    public Optional<ReserveRoom> getRoomByReserveCode(int reserveCode) {
        return rooms.stream().filter(room -> room.getReserveCode() == reserveCode).findFirst();
    }

    public List<ReserveRoom> getRoomByNationalCode(String nationalCode) {
        return rooms.stream().filter(room -> room.getNationalCode().equals(nationalCode)).collect(Collectors.toList());
    }

    public String getFullNameByNationalCode(String nationalCode) {
        Optional<ReserveRoom> optionalRoom =  rooms.stream().filter(room -> room.getNationalCode().equals(nationalCode)).findFirst();
        if (optionalRoom.isPresent()) {
            return optionalRoom.get().getFullName();
        }
        else {
            return "";
        }
    }

    public void clearRooms() {
        rooms.clear();
    }

    public boolean validate(String nationalCode, int reserveCode) {
        return rooms.stream().anyMatch(room -> room.getNationalCode().equals(nationalCode) &&
                room.getReserveCode() == reserveCode);
    }
}
