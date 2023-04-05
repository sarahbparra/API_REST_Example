package com.example.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;


@Component
public class FileDownloadUtil {

    //Clase encargada de devolver en PostMan el archivo (file) que hemos subido
    //Solo busca ficheros no manda nada. 
    private Path foundFile;

    public Resource getFileAsResource(String fileCode) throws IOException {

        Path dirPath = Paths.get("Files-Upload");

        //Le pasas una carpeta, el path tiene una carpeta?
        //Files te deja ver la carpeta, y luego utiliza un lambda. 
        //Suponiendo que todos son ficheros pues todo bien.
        //Si la lamda lleva llave de apertura y cierre necesita terminar con el return. 

        Files.list(dirPath).forEach(file -> {
            if(file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;

                return ;
            }
        });

        //Al encontrar el file, se returna la urlresource. 
        //Aquí ya se está devolviendo el nombre del archivo y todo bien. 
        //El fichero no se ha mandado todavía, hay que ponerlo en el controller. 

        if(foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }

    
}
