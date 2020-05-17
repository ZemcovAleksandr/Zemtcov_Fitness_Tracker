package sample;

import Entities.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

import static Entities.Exercise_Type.*;


public class Fitness_Tracker  {
    static Memory memory = Memory.getMemory();
    static User currentUser;



    public static void main(String[] args) {
       // fillMemory();  // метод заполнения тестовыми данными
        mainMenu();
    }

    /* главное меню
    выбор аккаунта
     */
    public static void mainMenu(){
        currentUser = null;
        showUserList();
        System.out.println("Введите id чтобы выбрать пользователя или " +
                "\n введите \"новый\" чтобы создать нового" +
                "\n введите \"выбрать id\" чтобы войти в учетную запись " +
                "\n введите \"удалить id\" чтобы удалить пользователя" +
                "\n введите \"выход\" чтобы выйти из программы");
        String input = new Scanner(System.in).nextLine();
        String[] inputString = input.split(" "); // добавляет ввод в массив строк. Новый элемент через пробел

        if (inputString[0].equalsIgnoreCase("новый")) {
            createUser();
        } else if((inputString.length == 2)&&(inputString[0].startsWith("выбрать"))&&(inputString[1].matches("[-+]?\\d+"))){
            int pickId = Integer.parseInt(inputString[1]);
            currentUser = memory.getById(pickId);
            if(currentUser != null)
                goIntoAccount();
            else
                System.out.println("Пользователь не найден");
        } else if((inputString.length == 2)&&(inputString[0].startsWith("удалить"))&&(inputString[1].matches("[-+]?\\d+"))) { //
            int deleteId = Integer.parseInt(inputString[1]);
            if(memory.deleteById(deleteId))
                System.out.println("Пользователь успешно удален");
            else
                System.out.println("Пользователь не найден");
            mainMenu();
        } else if ((inputString.length > 0)&&(inputString[0].equalsIgnoreCase("выход"))) {
            safeAndExit();
        } else {
            System.out.println("Команда не распознана");
            mainMenu();
        }
    }

    public static void showUserList(){
        ArrayList<User> userArrayList = memory.getUsers();
        System.out.format("%2s%-2s%-10s%-10s%n", "id", "  ", "Имя","Последняя тренеровка");
        for (User user : userArrayList)
            System.out.format("%2d%-2s%-10s%-10s%n", user.getId(), ") ",user.getName(), user.getLast_training());
    }

    public static void createUser(){
        ArrayList<User> userArrayList = memory.getUsers();
        System.out.println("Введите имя пользователя" );
        int newId=1;
        String newName = new Scanner(System.in).nextLine();

// сохраняем все id, и перебираем их в цикле чтобы найти не занятый
        HashSet<Integer> idSet = new HashSet<>();
        for (User user : userArrayList)
            idSet.add(user.getId());
        while (idSet.contains(new Integer(newId)))
            newId++;
        userArrayList.add(new User(newId, newName));
        mainMenu();
    }

    /* личное меню для пользователя*/
    public static void goIntoAccount(){
        showUsersData();
        System.out.println("Введите \"1\" чтобы начать упражнение отжимания" +
                "\nвведите \"2\" чтобы начать упражнение скакалка" +
                "\nвведите \"3\" чтобы начать упражнение приседания" +
                "\nвведите \"назад\" чтобы вернуться к списку пользователей");
        String input = new Scanner(System.in).nextLine();

        if (input.equalsIgnoreCase("1")) {
            startExercise(Push_up);
        } else if(input.equalsIgnoreCase("2")){
            startExercise(Jump_rope);
        } else if(input.equalsIgnoreCase("3")){ //
            startExercise(Squatting);
        } else if (input.equalsIgnoreCase("назад")) {
            memory.refreshUsersData(currentUser);
            mainMenu();
        } else {
            System.out.println("Команда не распознана");
            goIntoAccount();
        }
    }

    /* вывод последних тренеровок пользователя */
    public static void showUsersData(){
        ArrayList<Day> daysArrayList = currentUser.getDaysList();
        System.out.print("Пользователь "+currentUser.getName()+"   id: " +currentUser.getId()+
                "\nДень\t\t\tИзрасходавано каллорий\n");
        if (daysArrayList.size() == 0)
            System.out.print(" Тренеровки еще не начаты! \nВыберите упражнение для старта\n");

        // определяет сколько последних дней выводить на экран
        final int showDays = 5;
        int startFrom = (daysArrayList.size()-1) - showDays;
        if(startFrom<0)
            startFrom = 0;
        for (int i=startFrom; i<=(daysArrayList.size()-1); i++ )
            System.out.print(daysArrayList.get(i).getDate().toString()+"\t\t\t"+ daysArrayList.get(i).getTotal_Calories()+"\n");
    }

    /* метод выполнения упражнения*/
    public static void startExercise(Exercise_Type exercise_type) {
        System.out.println("Упражнение " + exercise_type + " начато");
        Exercise currentExercise = new Exercise(exercise_type);
        long start = (long) (System.currentTimeMillis() / 1000);
        long durationSeconds = 0;

        System.out.println("Введите \"с\" чтобы закончить упражнение");
        String input = "";
        do {
            input = new Scanner(System.in).nextLine();
        } while (input.equalsIgnoreCase("c"));
        long duration = (long) (System.currentTimeMillis() / 1000) - start;
        currentExercise.setDuration(duration);
        System.out.println("Упражнение " + exercise_type + " окончено. Продолжительность " + duration);

        Day currentDay;
        Day lastDay;
        // если есть сохраненные дни у пользователя
        if (currentUser.getDaysArrayList().size() > 0){
            lastDay = currentUser.getDaysArrayList().get(currentUser.getDaysArrayList().size() - 1);
            // если последний сохраненный день - сегодня
            if ((lastDay.getDate().getDayOfYear() == LocalDate.now().getDayOfYear())
                && (lastDay.getDate().getYear() == LocalDate.now().getYear()))
            {
                currentDay = lastDay;
                currentDay.addExercise(currentExercise);
            } else {
                currentDay = new Day(LocalDate.now());
                currentDay.addExercise(currentExercise);
                currentUser.addDay(currentDay);
            }
        } else {
            currentDay = new Day(LocalDate.now());
            currentDay.addExercise(currentExercise);
            currentUser.addDay(currentDay);
        }

        goIntoAccount();
    }

    /* выход из программы с сохранением данных*/
    public static void safeAndExit(){
        memory.writeToDisk();
        System.exit(0);
    }


    /* метод заполнения тестовыми данными*/
    public static void fillMemory(){
        // объект Exercise с какими-то данными
        Exercise exercise = new Exercise();
        exercise.setDuration(Duration.ofHours(1).getSeconds());
        exercise.setExercise_consumption(1500);
        exercise.setType(Push_up);

        // список Exercises
        ArrayList<Exercise> exercisesArrayList = new ArrayList<>();
        exercisesArrayList.add(exercise);

        // день
        Day day = new Day();
        day.setDate(LocalDate.now().minusDays(1));
        day.setExercises(exercisesArrayList);
        day.setTotal_Calories(2000);

        ArrayList<Day> daysArrayList = new ArrayList<>();
        daysArrayList.add(day);

        User user = new User(1,"Vasya");
        user.setStart_of_trainings(LocalDate.now().minusDays(5));
        user.setLast_training(LocalDate.now());
        user.setDaysArrayList(daysArrayList);


        memory.addUser(user);
        memory.addUser(new User(2,"Peter"));
        User user3 = new User(3,"Kate");
        user3.getDaysList().add(new Day(LocalDate.now().minusDays(4), 1400));
        user3.getDaysList().add(new Day(LocalDate.now().minusDays(3), 560));
        user3.getDaysList().add(new Day(LocalDate.now().minusDays(2), 870));
        user3.getDaysList().add(new Day(LocalDate.now().minusDays(1), 320));
        memory.addUser(user3);
        memory.addUser(new User(4,"Steve"));
        memory.addUser(new User(5,"Alan"));
        memory.addUser(new User(6,"Julia"));
        memory.addUser(new User(7,"Mary"));
        memory.addUser(new User(8,"Elene"));
    }
}
