package com.backend.cinema.services;

import com.backend.cinema.domain.Info;
import com.backend.cinema.domain.Product;
import com.backend.cinema.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoServiceImpl implements PhotoService {
    ProductRepository productRepository;

    @Autowired
    public PhotoServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void savePhotoFile(Product product, MultipartFile file) {
        try {


            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0; for (byte b : file.getBytes()){
                byteObjects[i++] = b; }

            Info info = product.getInfo();
            if (info == null) {
                info = new Info();
            }

            info.setProduct(product);
            product.setInfo(info);

            if (byteObjects.length > 0){
                info.setPhoto(byteObjects);
            }

            productRepository.save(product);
        }
        catch (IOException e) {
        }

    }
}
