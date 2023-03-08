package com.zhtangsh.oc.controller;

import com.zhtangsh.oc.service.OfficeConverterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/oc/office/convert")
@Api(value = "Endpoints to convert input to pdf", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfficeConvertController {

  @Autowired
  private OfficeConverterService officeConverterService;

  @ApiOperation(value = "convert word to pdf", notes = "convert word to pdf")
  @PostMapping("/word/toPdf")
  public void wordToPdf(
      MultipartFile file,
      HttpServletResponse response
  ) throws IOException {
    this.officeConverterService
        .toPdf(file.getInputStream(), response, DefaultDocumentFormatRegistry.DOCX);
  }

  @ApiOperation(value = "convert excel to pdf", notes = "convert excel to pdf")
  @PostMapping("/excel/toPdf")
  public void excelToPdf(
      MultipartFile file,
      HttpServletResponse response
  ) throws IOException {
    this.officeConverterService
        .toPdf(file.getInputStream(), response, DefaultDocumentFormatRegistry.XLSX);
  }
}
