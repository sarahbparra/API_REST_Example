package com.example.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.lang3.RandomStringUtils;

//Anotación para que spring cree los beans a partir de esta clase. 
//Si no se anota, no se podrá inyectar un objeto de esta clase más tarde. 
@Component

public class FileUploadUtil {
    
    public  String saveFile(String fileName, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get("Files-Upload");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileCode = RandomStringUtils.randomAlphanumeric(8);

        //Try with resources. Lo bueno es que lo que se ponga en (), cuando el 
        //try sale de ambito, se cierra automáticamente. 
        //De esta manera el archivo de multiPart mandado, es accesible y se encuentra abierto
        //para leer el contenido
        //No se quiere dejar cerrado, esto también permite que no se use finally
        //Los recursos que se pueden manejar son los que implementan la interface 
        //closeable. 
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }


        return fileCode;
    }
}
