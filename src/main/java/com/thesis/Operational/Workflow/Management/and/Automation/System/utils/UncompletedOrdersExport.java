package com.thesis.Operational.Workflow.Management.and.Automation.System.utils;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Order;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class UncompletedOrdersExport {

    private XSSFWorkbook workbook;

    private XSSFSheet sheet;

    private CellStyle defaultCellStyle;

    public UncompletedOrdersExport() throws IOException {

        InputStream in = new ClassPathResource("/templates/reports/Uncompleted_Orders.xlsx").getInputStream();
        workbook = new XSSFWorkbook(in);
        in.close();
        sheet = workbook.getSheetAt(0);
        this.defaultCellStyle = sheet.getRow(1).getCell(0).getCellStyle();
    }

    public void export(List<Order> uncompletedOrders){
        int rowCount = 1;

        for (Order order : uncompletedOrders){
            Row row = sheet.createRow(rowCount);

            Cell orderId = row.createCell(0);
            Cell office = row.createCell(1);
            Cell status = row.createCell(2);
            Cell creationDate = row.createCell(3);
            Cell orderCell = row.createCell(4);
            Cell total = row.createCell(5);

            setDefaultCellValue(orderId, order.getId());
            setDefaultCellValue(status, order.getStatus().toString());
            setDefaultCellValue(creationDate, order.getCreationDate().toString());
            setDefaultCellValue(orderCell, getOrderString(order.getBasket()));
            setDefaultCellValue(total, order.getTotalCost());

            rowCount++;
        }
    }

    private String getOrderString(Map<Product, Integer> basket){
        StringBuilder orderString = new StringBuilder();
        for (Product product : basket.keySet()){
            orderString.append(product.getName()).append(" x ").append(basket.get(product)).append("\n");
        }

        return orderString.toString();
    }

    private void setDefaultCellValue(Cell cell, String value) {

        cell.setCellStyle(defaultCellStyle);
        cell.setCellValue(value);
    }

    private void setDefaultCellValue(Cell cell, Long value) {

        cell.setCellStyle(defaultCellStyle);
        cell.setCellValue(value);
    }

    private void setDefaultCellValue(Cell cell, double value) {

        cell.setCellStyle(defaultCellStyle);
        cell.setCellValue(value);
    }

    public ByteArrayResource base() throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        byte[] bytes = outputStream.toByteArray();

        return new ByteArrayResource(bytes);

    }

    public ByteArrayResource base64() throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        byte[] bytes = outputStream.toByteArray();

        return new ByteArrayResource(Base64.getEncoder().encode(bytes));

    }
}
