package com.project.controller.user;

import com.project.firebase.StorageStrategy;
import com.project.utils.ProductImageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "productOfUser")
@RequestMapping("user")
public class ProductController {

    @Autowired
    private StorageStrategy storageStrategy;

    @GetMapping("/product/upload")
    public String getUploadFile(Model model){
        model.addAttribute("productImage",new ProductImageBean());
        return "upload";
    }

    @PostMapping("/product/save/upload")
    public String saveUploadFile(@ModelAttribute ProductImageBean productImage){
        try {
            String fileName = storageStrategy.saveImage(productImage.getImage1(), "product");

            StringBuilder imageUrl = new StringBuilder();

            if (fileName != null || fileName.trim().length() != 0) {
                String tokens = StringUtils.substringBeforeLast(fileName, ".");
                imageUrl.append("https://firebasestorage.googleapis.com/v0/b/shoes-project-8adf8.appspot.com/o/");
                imageUrl.append("product%2F");
                imageUrl.append(fileName);
                imageUrl.append("?alt=media&token=");
                imageUrl.append(tokens);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/product/upload";
    }

}
