package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipTest {
    ClassLoader cl = ZipTest.class.getClassLoader();

    @Test
    void csvZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("zipExample.zip")) {
            ZipInputStream zip = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().equals("results_report_03052023_1042.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zip));
                    List<String[]> list = csvReader.readAll();
                    Assertions.assertArrayEquals((new String[]{"Олимпиада:", "Проверка 26.04"}), list.get(0));

                }
            }
        }

    }

    @Test
    void xlsxZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("zipExample.zip")) {

            ZipInputStream zip = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().equals("ratingList_02052023_1108.xlsx")) {
                    XLS xls = new XLS(zip);
                    Assertions.assertEquals("ID уч.", xls.excel.getSheetAt(0).getRow(0).getCell(1).toString());
                }
            }

        }
    }

    @Test
    void pdfZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("zipExample.zip")) {

            ZipInputStream zip = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().equals("dms.pdf")) {
                    PDF pdf = new PDF(zip);
                    Assertions.assertEquals("DYurev@VSK.RU", pdf.author);
                }


            }
        }
    }
}
