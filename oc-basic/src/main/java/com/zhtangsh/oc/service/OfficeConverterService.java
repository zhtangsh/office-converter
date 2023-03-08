package com.zhtangsh.oc.service;

import com.zhtangsh.oc.config.MineTypeConstant;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfficeConverterService {

  @Value("${oc.office.home}")
  private String officeHome;

  @Value("${oc.office.host}")
  private String host;

  @Value("${oc.office.ports}")
  private String portList;

  @Value("${oc.office.task-execution-timeout}")
  private Integer taskExecutionTimeout;

  @Value("${oc.office.task-queue-timeout}")
  private Integer taskQueueTimeout;

  @Value("${oc.office.max-tasks-per-process}")
  private Integer maxTasksPerProcess;

  private LocalOfficeManager officeManager;

  public OfficeManager getOfficeManager() throws OfficeException {
    if (this.officeManager == null) {
      int[] ports = Arrays.stream(this.portList.split(","))
          .mapToInt(Integer::parseInt)
          .toArray();
      this.officeManager = LocalOfficeManager.builder().install()
          .hostName(this.host)
          .portNumbers(ports)
          .officeHome(this.officeHome)
          .taskExecutionTimeout(this.taskExecutionTimeout * 60 * 1000L)
          .taskQueueTimeout(this.taskQueueTimeout * 60 * 60 * 1000L)
          .maxTasksPerProcess(this.maxTasksPerProcess)
          .build();
      this.officeManager.start();
    }
    return this.officeManager;
  }

  public void toPdf(
      Workbook workbook,
      HttpServletResponse response
  ) {
    try {
      OutputStream outputStream = response.getOutputStream();
      // write to baos
      ByteArrayOutputStream tmpBAOS = new ByteArrayOutputStream();
      workbook.write(tmpBAOS);
      InputStream bais = new ByteArrayInputStream(tmpBAOS.toByteArray());
      // convert to pdf
      OfficeManager manager = this.getOfficeManager();
      LocalConverter.make(manager)
          .convert(bais)
          .as(DefaultDocumentFormatRegistry.XLSX)
          .to(response.getOutputStream())
          .as(DefaultDocumentFormatRegistry.PDF)
          .execute();
      // write to response output stream
      String fileName = "result.pdf";
      response.setCharacterEncoding("UTF-8");
      response.setContentType(MineTypeConstant.pdf);
      response.setHeader(
          "Content-Disposition",
          "attachment;filename=" +
              new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
      outputStream.flush();
      outputStream.close();
    } catch (IOException | OfficeException e) {
      throw new RuntimeException("Fail to convert xlsx to pdf", e);
    }
  }

  public void toPdf(
      XWPFDocument document,
      HttpServletResponse response
  ) {
    try {
      OutputStream outputStream = response.getOutputStream();
      // write to baos
      ByteArrayOutputStream tmpBAOS = new ByteArrayOutputStream();
      document.write(tmpBAOS);
      InputStream bais = new ByteArrayInputStream(tmpBAOS.toByteArray());
      // convert to pdf
      OfficeManager manager = this.getOfficeManager();
      LocalConverter.make(manager)
          .convert(bais)
          .as(DefaultDocumentFormatRegistry.DOCX)
          .to(response.getOutputStream())
          .as(DefaultDocumentFormatRegistry.PDF)
          .execute();
      // write to response output stream
      String fileName = "result.pdf";
      response.setCharacterEncoding("UTF-8");
      response.setContentType(MineTypeConstant.pdf);
      response.setHeader(
          "Content-Disposition",
          "attachment;filename=" +
              new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
      outputStream.flush();
      outputStream.close();
    } catch (IOException | OfficeException e) {
      throw new RuntimeException("Fail to convert docx to pdf", e);
    }
  }

  public void toPdf(
      InputStream inputStream,
      HttpServletResponse response,
      DocumentFormat sourceFormat
  ) {
    try {
      OutputStream outputStream = response.getOutputStream();
      // convert to pdf
      OfficeManager manager = this.getOfficeManager();
      LocalConverter.make(manager)
          .convert(inputStream)
          .as(sourceFormat)
          .to(outputStream)
          .as(DefaultDocumentFormatRegistry.PDF)
          .execute();
      // write to response
      String fileName = "result.pdf";
      response.setCharacterEncoding("UTF-8");
      response.setContentType(MineTypeConstant.pdf);
      response.setHeader(
          "Content-Disposition",
          "attachment;filename=" +
              new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
      outputStream.flush();
      outputStream.close();
    } catch (IOException | OfficeException e) {
      throw new RuntimeException("Fail to convert to pdf", e);
    }
  }
}
