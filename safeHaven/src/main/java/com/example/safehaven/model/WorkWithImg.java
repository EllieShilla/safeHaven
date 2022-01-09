package com.example.safehaven.model;

import com.example.safehaven.dao.MainPhoto;
import com.example.safehaven.dao.Photo;
import com.example.safehaven.repo.MainPhotoRepo;
import com.example.safehaven.repo.PhotoRepo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WorkWithImg {

    public ArrayList<Photo> img(PhotoRepo photoRepo){
        ArrayList<Photo>img=new ArrayList<>();
        for(Photo i:photoRepo.findAll()){

            try (FileOutputStream stream = new FileOutputStream("..\\safeHaven\\src\\main\\resources\\static\\img\\"+i.getTitle())) {
                stream.write(i.getPhoto());
                img.add(new Photo(i.getTitle(), i.getApartment()));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public ArrayList<MainPhoto> imgFirst(MainPhotoRepo mainPhotoRepo) {
        ArrayList<MainPhoto> imgFirst = new ArrayList<>();
        for (MainPhoto i : mainPhotoRepo.findAll()) {

            try (FileOutputStream stream = new FileOutputStream("..\\safeHaven\\src\\main\\resources\\static\\img\\" + i.getTitle())) {
                stream.write(i.getPhoto());
                imgFirst.add(new MainPhoto(i.getTitle(), i.getApartment()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgFirst;
    }
}