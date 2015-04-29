/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieles.ssdd_practica2;

import com.flickr4java.flickr.*;
import com.flickr4java.flickr.photos.*;
import com.flickr4java.flickr.photosets.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main() {
        try {
            String API_KEY = "99ac5f2d435eece3bbc60be1314b031c";
            String SECRET = "528987d93fcc0f38";
            String PHOTOSET_ID = "72157652101412532";

            Flickr flickr = new Flickr(API_KEY, SECRET, new REST());
            Set<String> extras = new HashSet<>();
            extras.add("url_o");
            extras.add("original_format");
            PhotosetsInterface photosetsInterface = flickr.getPhotosetsInterface();
            PhotoList<Photo> photoList = photosetsInterface.getPhotos(PHOTOSET_ID, extras, 0, 0, 0);
            for (Photo p : photoList) {
                URL url = new URL(p.getOriginalSize().getSource());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                String fileName = "./DESCARGADAS/" + p.getTitle()
                        + "." + p.getOriginalFormat();
                OutputStream os = new FileOutputStream(fileName);
                byte[] b = new byte[2048];
                int length;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
            }
        } catch (FlickrException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
