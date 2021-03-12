package com.maktab.entities;

import com.maktab.models.ReserveRoomDao;

import javax.persistence.*;

@Entity
public class ReserveRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String fullName;
    @Column
    private String nationalCode;
    @Column
    private String startDate;
    @Column
    private String endDate;
    @Column
    private int roomCapacity;
    @Column
    private int roomNumber; // start at 1
    @Column
    private int reserveCode; // contain 5 digits

    public ReserveRoom() {

    }

    public ReserveRoom(String fullName, String nationalCode, String startDate, String endDate, int roomCapacity) {
        this.fullName = fullName;
        this.nationalCode = nationalCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomCapacity = roomCapacity;
        this.roomNumber = ReserveRoomDao.getMaxRoomNumber() + 1;
        this.reserveCode = ReserveRoomDao.getMaxReserveCode() + 1;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getReserveCode() {
        return reserveCode;
    }
}
