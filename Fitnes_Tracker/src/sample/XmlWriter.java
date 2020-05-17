package sample;

import Entities.Day;
import Entities.Exercise;
import Entities.Memory;
import Entities.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Duration;
import java.time.LocalDate;
import java.io.File;
import java.util.ArrayList;

import static Entities.Exercise_Type.Push_up;


public class XmlWriter {

    public static void main(String[] args) {
        String fileName = "src/memory.xml";




        // сохраняем
        /*saveToXml(memory, fileName);

        // восстанавливаем объект из XML файла
        Memory unmarshUser = getFromXml(fileName);
        if (unmarshUser != null) {
            System.out.println(unmarshUser.toString());
        }*/

    }



    // сохраняем объект в XML файл
    public static void saveToXml(Memory user, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(Memory.class);
            Marshaller marshaller = context.createMarshaller();

            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(user, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    // восстанавливаем объект из XML файла
    public static Memory getFromXml(String filePath) {
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(Memory.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (Memory) un.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


}
