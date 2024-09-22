package com.bank.bank.Services;

import com.bank.bank.Entities.Employee;
import com.bank.bank.Entities.Reservation;
import com.bank.bank.Entities.Slot;
import com.bank.bank.Repositories.EmployeeRepository;
import com.bank.bank.Repositories.ReservationRepository;
import com.bank.bank.Repositories.SlotRepository;
import com.bank.bank.ResourceNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotsRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private SlotRepository slotRepository;

    // Create a new slot for an employee
    public Slot createSlotForEmployee(String employeeId, LocalDateTime slotTime) {
        Employee employee = employeeRepository.findByAfm(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfNextDay = now.plusDays(1).toLocalDate().atStartOfDay();


        if (slotTime.isBefore(startOfNextDay)) {
            throw new ResourceNotFoundException("Slot time must be at least one day after.");
        }

        boolean slotExists = slotRepository.existsByEmployeeAfmAndSlotTime(employeeId, slotTime);

        if (slotExists) {
            throw new ResourceNotFoundException("Slot for this employee at this time already exists.");
        }
        Slot slot = new Slot(slotTime, employee);
        return slotsRepository.save(slot);
    }

    // Get all slots for a specific employee
    public List<Slot> getSlotsForEmployee(String employeeId) {
        return employeeRepository.findByAfm(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"))
                .getSlots();
    }
    @Scheduled(cron = "0 0 * * * ?")
    public void deleteExpiredSlotsAndReservations() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        List<Slot> expiredSlots = slotRepository.findBySlotTimeBefore(now);
        List<Reservation> expiredReservations =reservationRepository.selectExpiredReservation(now);

        // Save expired slots to Excel file
        if (!expiredSlots.isEmpty()) {
            saveSlotsReservationsToExcel(expiredSlots,expiredReservations);
        }

        // 1. Delete reservations for slots that have passed the current time.
        reservationRepository.deleteReservationsForExpiredSlots(now);

        // 2. Delete slots that have passed the current time.
        slotRepository.deleteBySlotTimeBefore(now);
    }

    private void saveSlotsReservationsToExcel(List<Slot> slots,List<Reservation> reservations) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Expired Slots and Reservations");

        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);

        Row headerRow1 = sheet.createRow(0);
        Cell slotIdHeader1 = headerRow1.createCell(0);
        slotIdHeader1.setCellValue("Slot Expired");
        slotIdHeader1.setCellStyle(boldStyle);

        slotIdHeader1 = headerRow1.createCell(3);
        slotIdHeader1.setCellValue("Reservations Expired");
        slotIdHeader1.setCellStyle(boldStyle);

        // Create the header row
        Row headerRow = sheet.createRow(1);
        Cell slotIdHeader = headerRow.createCell(0);
        slotIdHeader.setCellValue("Slot ID");
        slotIdHeader.setCellStyle(boldStyle);

        slotIdHeader = headerRow.createCell(1);
        slotIdHeader.setCellValue("Slot Time");
        slotIdHeader.setCellStyle(boldStyle);

        slotIdHeader = headerRow.createCell(3);
        slotIdHeader.setCellValue("Reservation ID");
        slotIdHeader.setCellStyle(boldStyle);

        slotIdHeader = headerRow.createCell(4);
        slotIdHeader.setCellValue("Customer AFM");
        slotIdHeader.setCellStyle(boldStyle);

        slotIdHeader = headerRow.createCell(5);
        slotIdHeader.setCellValue("Employee AFM");
        slotIdHeader.setCellStyle(boldStyle);

        slotIdHeader = headerRow.createCell(6);
        slotIdHeader.setCellValue("Slot Id");
        slotIdHeader.setCellStyle(boldStyle);

        sheet.setColumnWidth(0, 6000); // Adjust the first column width
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000); // Adjust the first column width
        sheet.setColumnWidth(5, 6000);
        sheet.setColumnWidth(6, 6000); // Adjust the first column width

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Fill the sheet with slot data
        int rowNum = 2;
        for (Slot slot : slots) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(slot.getId());
            row.createCell(1).setCellValue(slot.getSlotTime().format(formatter1));
        }


        rowNum = 2;
        for (Reservation reservation : reservations) {
            Row row = sheet.getRow(rowNum++);
            row.createCell(3).setCellValue(reservation.getId());
            row.createCell(4).setCellValue(reservation.getCustomer().getAfm());
            row.createCell(5).setCellValue(reservation.getEmployee().getAfm());
            row.createCell(6).setCellValue(reservation.getSlot().getId().toString());
        }

        // Write the output to a file
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        // Generate a valid filename
        String fileName = "C:/Users/Chris/Desktop/expired_slots_" + formattedDateTime + ".xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }

        // Closing the workbook
        workbook.close();
    }

}
