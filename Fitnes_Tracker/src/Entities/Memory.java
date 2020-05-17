package Entities;

import sample.XmlWriter;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;

@XmlRootElement()
public class Memory implements Serializable {
    private static ArrayList<User> users = new ArrayList<User>();;
    private static Memory memory;
    static final long serialVersionUID = -6039216354863297212L;
    private static String fileName = "src/memory.xml";



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setMemory(Memory memory) {
        Memory.memory = memory;
    }



    public static synchronized Memory getMemory(){
        File file = new File(fileName);
        if(file.exists() && !file.isDirectory())
            memory = (Memory)XmlWriter.getFromXml(fileName);

        if(memory == null)
            return memory = new Memory();
        else
            return memory;
    }

   /* public Memory(){

    }*/

    @XmlElementWrapper
    public ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        Memory.users = users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getById(int id){
        boolean success = false;
        User userToFind = null;
        for(User user : users)
            if(user.getId() == id) {
                userToFind = user;
            }
        if(userToFind != null)
            return userToFind;
        return null;
    }

    public static void refreshUsersData(User user){
        deleteById(user.getId());
        addUser(user);
    }

    public static boolean deleteById(int id){
        boolean success = false;
        User userToFind = null;
        for(User user : users)
            if(user.getId() == id) {
                userToFind = user;
            }
        if(userToFind != null)
            success = users.remove(userToFind);
        return success;
    }

    public static void writeToDisk(){
        XmlWriter.saveToXml(memory, fileName);
    }


    @Override
    public String toString() {
        return "Memory{}" +
                "\n "+users.toString();
    }
}
