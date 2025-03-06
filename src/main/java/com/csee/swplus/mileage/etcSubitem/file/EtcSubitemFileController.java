//package com.csee.swplus.mileage.etcSubitem.file;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.net.URLEncoder;
//
//@Controller
//@RequestMapping("/api/mileage/file")
//public class EtcSubitemFileController {
//    @GetMapping("/download")
//    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        try {
//            String fileName = request.getParameter("filename");
//            String filePath = "/home/mileage0523/uploads";
//            File dFile = new File(filePath, fileName);
//            int fSize = (int)dFile.length();
//
//            if(fSize > 0) {
//                String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileName, "UTF-8");
//                response.setContentType("application/octet-stream; charset=utf-8");
//                response.setHeader("Content-Disposition", encodedFilename);
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }
//}
