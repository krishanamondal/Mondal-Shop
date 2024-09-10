package com.mondal.mondal_shop.service.image;

import com.mondal.mondal_shop.dto.ImageDto;
import com.mondal.mondal_shop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);
    void updateImageById(MultipartFile file,Long imageId);
}
