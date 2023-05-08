package guru.qa.parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;

public class FileParsingTest {
    private ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    void pdfParseTest() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File download = $("a[href*='junit-user-guide-5.9.3.pdf']").download();
        PDF pdf = new PDF(download);
        Assertions.assertEquals(
                "Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein",
                pdf.author
        );
    }


    @Test
    void xlsParseTest() throws Exception {
        Selenide.open("https://spb.hse.ru/ba/law/spiski_studentov");
        File download = $("a[href*='%D0%BF%D0%BE%D1%82%D0%BE%D0%BA%D0%B8%20%D0%BC%D0%B0%D0%B9%D0%BD%D0%BE%D1%80%D0%B0%20%D0%9C%D0%91.xls']").download();
        XLS xls = new XLS(download);
        System.out.println();
        Assertions.assertEquals("ФИО",
                xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()
        );

    }

    @Test
    void csvParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("qaguru.csv");
             InputStreamReader isr = new InputStreamReader(is)) {
            CSVReader csvReader = new CSVReader(isr);
            List<String[]> content = csvReader.readAll();
            Assertions.assertArrayEquals(new String[]{"Тучс", "JUnit5"}, content.get(1));

        }

    }

    @Test
    @Disabled
    void zipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("zipExample.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
                 ZipEntry entry;

                 while ((entry = zs.getNextEntry()) != null ) {
                     Assertions.assertEquals("444d905ec89da8d2d9ad15e36b0edbd9.jpg", entry.getName());
                 }

             }
}


}