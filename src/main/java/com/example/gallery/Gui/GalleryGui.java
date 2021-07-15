package com.example.gallery.Gui;

import com.example.gallery.Model.Image;
import com.example.gallery.Repository.ImageRepository;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.List;

@Route("gallery")
public class GalleryGui extends VerticalLayout {

    private ImageRepository imageUpader;

    @Autowired
    public GalleryGui(ImageRepository imageUpader) {
        this.imageUpader = imageUpader;
        List<Image> all = imageUpader.findAll();
        all.stream().forEach(element -> {
            com.vaadin.flow.component.html.Image image =
                    new com.vaadin.flow.component.html.Image(element.getImageAdress(), "brak");
            add(image);
        } );
    }
}
