package com.bank.bank.Services;

import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.UserEntity;
import com.bank.bank.Repositories.CustomerRepository;
import com.bank.bank.Repositories.UserRepository;
import com.bank.bank.ResourceNotFoundException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PdfGenerationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public byte[] createPdfWithDatabaseData(String afm) throws IOException {
        Optional<Customer> user = customerRepository.findByAfm(afm);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("Customer not found with AFM: " + afm);
        }

        Customer user1 = user.get();
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, 750); // Position of the title
                contentStream.showText("Customer's Information Report with AFM "+user1.getAfm());
                contentStream.endText();

                // Draw a line below headers
                contentStream.setLineWidth(1f);
                contentStream.moveTo(25, 730);
                contentStream.lineTo(550, 730);
                contentStream.stroke();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, 700);
                contentStream.showText("First Name: ");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(String.valueOf(user1.getFirstName()));
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(225, 700);
                contentStream.showText("Email: ");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(String.valueOf(user1.getEmail()));
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, 670);
                contentStream.showText("Last Name: ");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(String.valueOf(user1.getLastName()));
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(225, 670);
                contentStream.showText("Debt: ");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(String.valueOf(user1.getLoanDebt()));
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, 640);
                contentStream.showText("Birth Date: ");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(String.valueOf(user1.getBirthDate()));
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(225, 640);
                contentStream.showText("Savings: ");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(String.valueOf(user1.getMoneyDeposited()));
                contentStream.endText();

            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        }
    }
}

