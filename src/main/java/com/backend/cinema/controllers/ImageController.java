package com.backend.cinema.controllers;

import com.backend.cinema.domain.Info;
import com.backend.cinema.domain.Product;
import com.backend.cinema.services.PhotoService;
import com.backend.cinema.services.ProductService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ImageController {

    private final ProductService productService;


    public ImageController(@Autowired PhotoService imageService, @Autowired ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products/getimage/{id}")
    public void downloadImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        Product product = productService.findById(Long.valueOf(id));
        if (product.getInfo() != null) {
            Info info = product.getInfo();

            if (product.getInfo().getPhoto() != null) {
                byte[] byteArray = new byte[info.getPhoto().length];
                int i = 0;
                for (Byte wrappedByte : info.getPhoto()) {
                    byteArray[i++] = wrappedByte;
                }
                response.setContentType("image/jpeg");
                InputStream is = new ByteArrayInputStream(byteArray);
                try {
                    IOUtils.copy(is, response.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}