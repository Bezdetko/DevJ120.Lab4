
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bezdetk0@mail.ru
 */
class customFileFilter extends javax.swing.filechooser.FileFilter {
        
    String extension  ;  // расширение файла
    String description;  // описание типа файлов

    public customFileFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }
        
        
    @Override
    public boolean accept(File file) {
        if(file != null) {
            if (file.isDirectory())
                return true;
            if( extension == null )
                return (extension.length() == 0);
            return file.getName().endsWith(extension);
        }
        return false;
    }
    @Override
    public String getDescription() {
        return description;
   }

}
