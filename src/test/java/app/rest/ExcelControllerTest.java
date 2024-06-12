package app.rest;

import app.models.Result;
import app.services.ExcelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ExcelControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ExcelService excelService;

    @Mock
    private ResultController resultController;

    @InjectMocks
    private ExcelController excelController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(excelController).build();
    }

    @Test
    public void testDownloadExcel() throws Exception {

        Result result1 = new Result();
        Result result2 = new Result();
        List<Result> results = Arrays.asList(result1, result2);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5});

        when(resultController.findAll()).thenReturn(new ResponseEntity<>(results, HttpStatus.OK));

        when(excelService.resultsToExcel(anyList())).thenReturn(byteArrayInputStream);

        mockMvc.perform(get("/admin/excel/download/results"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=results.xlsx"))
                .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andExpect(content().bytes(new byte[] {1, 2, 3, 4, 5}));
    }

    @Test
    public void testDownloadExcelNoResults() throws Exception {

        when(resultController.findAll()).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/excel/download/results"))
                .andExpect(status().isNotFound());
    }
}
