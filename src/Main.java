import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*****************************************************************************************************

 M03_REP_03: The Quiz

 Description: This game called The Quiz consists of a knowledge challenge where the user who participates
 answers questions in various categories and difficulties.

 Autor: Roger Alonso
 Cicle Formatiu Grau Superior DAM1A - Centre d'Estudis Politecnics 2023-2024

 ******************************************************************************************************/

public class Main {
    public static void main(String[] args) {

        //Declarations
        boolean exit;
        int menu;
        int category;
        String user;

        //Main
        exit = false;
        user = getUser();
        do {
            menu = getMenuOption();

            switch (menu){
                case 1:
                    //Loading screen
                    loadingScreen();

                    //Choose category
                    category = getCategoryOption();

                    switch (category){

                        case 1:
                            playGame(getNumberOfQuestions(), arrayUsedDeclaration(), arrayAnswersDeclaration("src/resources/AnswersEasy.txt"), arrayQuestionsDeclaration("src/resources/QuestionsGriegos.txt"), user, "src/resources/EstadisticasGriegos.txt");
                            break;

                        case 2:
                            playGame(getNumberOfQuestions(), arrayUsedDeclaration(), arrayAnswersDeclaration("src/resources/AnswersEasy.txt"), arrayQuestionsDeclaration("src/resources/QuestionsSuperheroes.txt"), user, "src/resources/EstadisticasSuperheroes.txt");
                            break;

                        case 3:
                            playGame(getNumberOfQuestions(), arrayUsedDeclaration(), arrayAnswersDeclaration("src/resources/AnswersEasy.txt"), arrayQuestionsDeclaration("src/resources/QuestionsVideojuegos.txt"), user, "src/resources/EstadisticasVideojuegos.txt");
                            break;

                        case 4:
                            playGame(getNumberOfQuestions(), arrayUsedDeclaration(), arrayAnswersDeclaration("src/resources/AnswersEasy.txt"),  arrayQuestionsDeclaration("src/resources/QuestionsFutbol.txt"), user, "src/resources/EstadisticasFutbol.txt");
                            break;

                        case 5:
                            playGameHard(getNumberOfQuestions(), arrayUsedDeclaration(), arrayAnswersDeclarationTecnologia(), arrayAnswersDeclarationTecnologiaBoolean(), arrayQuestionsDeclaration("src/resources/QuestionsTecnologia.txt"), user, "src/resources/EstadisticasTecnologia.txt");
                            break;
                    }
                    break;

                case 2:
                    //Exit
                    exit = exit();
                    break;

                default:
                    //Incorrect
                    System.out.println("Incorrect option");
                    break;

            }
        } while (!exit);
    }

    private static String getUser() {
        System.out.println("Insertar nombre de usuario: ");
        return Teclat.llegirString();
    }

    // Functions
    private static void loadingScreen(){
        //Void function that shows a loading screen
        System.out.println("        Loading...");
        for (int i = 0; i <= 24; i++) {
            System.out.print("*");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("\n");
        }

        System.out.println(ANSI_GREEN + "Loading Complete!\n" + ANSI_RESET);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static int questionAnswerFunctionHard(int index, String[][] arrayAnswersHardString)  {
        //Return char answer
        char answer;
        do {
            for (int i = 0; i < 3; i++ ){
                System.out.println(arrayAnswersHardString[index][i]);
            }
            answer = Character.toUpperCase(Teclat.llegirChar());
        } while (!(answer == 'A' || answer == 'B' || answer == 'C'));

        if (answer == 'A') return 0;
        else if (answer == 'B') return 1;
        else return 2;
    }
    private static int questionAnswerResultHard(int correctAnswers, int answer, Boolean[][] arrayAnswersHardBoolean, int index) {
        //Return if the answer is correct or not
        if (arrayAnswersHardBoolean[index][answer]){
            correctAnswers ++;
            System.out.println(ANSI_GREEN + "Respuesta correcta" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Respuesta incorrecta" + ANSI_RESET);
        }

        return correctAnswers;
    }
    private static void playGameHard(int questions, Boolean[] arrayUsed, String[][] arrayAnswersHardString, Boolean[][] arrayAnswersHardBoolean, List<String> arrayQuestions, String user,String ruta) {
        //Execute game
        int index;
        int answer;
        int correctAnswers = 0;


        for (int i = 0; i < questions; i++) {

            //Generate a question if isn't used yet and print it
            index = generateQuestion(arrayUsed, arrayQuestions);

            //Return the answer char
            answer = questionAnswerFunctionHard(index, arrayAnswersHardString);

            //Check if the answer is correct
            correctAnswers = questionAnswerResultHard(correctAnswers, answer, arrayAnswersHardBoolean, index);
        }

        //Show the total of correct answers
        System.out.println("\n\nCorrect answers " + ((correctAnswers*100)/questions) + " %.");

        registrarEstadisticas(user, ruta, correctAnswers, questions - correctAnswers);

    }
    private static Boolean[][] arrayAnswersDeclarationTecnologiaBoolean() {

        return new Boolean[][] {
                {false,true,false},
                {true,false,false},
                {false,false,true},
                {true,false,false},
                {false,true,false},

                {true,false,false},
                {false,true,false},
                {false,false,true},
                {true,false,false},
                {false,true,false},

                {true,false,false},
                {false,true,false},
                {false,false,true},
                {true,false,false},
                {false,true,false},

                {true,false,false},
                {false,true,false},
                {false,false,true},
                {true,false,false},
                {false,true,false},
        };
    }
    private static String[][] arrayAnswersDeclarationTecnologia() {
        Path path = Paths.get("src/resources/AnswersHard.txt");
        List<String> lineas = null;
        String[][] options = null;

        try {
            lineas = Files.readAllLines(path);
            options = new String[lineas.size()][3];
            for (int i = 0; i < lineas.size(); i++){
                String[] optionLinea = lineas.get(i).split(",");
                for (int j = 0; j < optionLinea.length; j++) {
                    options[i][j] = optionLinea[j];
                }
            }
        } catch (IOException e){
            System.out.println("Error de lectura");
        }
        return options;
    }
    private static List<String> arrayQuestionsDeclaration(String ruta) {

        Path path = Paths.get(ruta);
        List<String> questions = new ArrayList<>();
        try {
            questions = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Error de lectura.");
        }
        return questions;
    }
    private static List<Boolean> arrayAnswersDeclaration(String ruta) {
        Path path = Paths.get(ruta);
        List<Boolean> answers = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(path);
            for (String linea : lineas){
                answers.add(Boolean.parseBoolean(linea));
            }
        } catch (IOException e){
            System.out.println("Error de lectura");
        }
        return answers;
    }
    private static void playGame(int questions, Boolean[] arrayUsed, List<Boolean> arrayAnswers, List<String> arrayQuestions, String user, String ruta) {
        int index;
        boolean answer;
        int correctAnswers = 0;


        for (int i = 0; i < questions; i++) {

            //Generate a question if isn't used yet and print it
            index = generateQuestion(arrayUsed, arrayQuestions);

            //Return the answer boolean
            answer = questionAnswerFunction();

            //Check if the answer is correct
            correctAnswers = questionAnswerResult(arrayAnswers.get(index), answer, correctAnswers);
        }

        //Show the total of correct answers
        System.out.println("\n\nCorrect answers " + ((correctAnswers*100)/questions) + " %.");

        registrarEstadisticas(user, ruta, correctAnswers, questions - correctAnswers);

    }

    private static void registrarEstadisticas(String user, String ruta, int correctas, int incorrectas) {
        Path path = Paths.get(ruta);
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        String info = "Usuario: " + user + " Fecha: " + fechaHoraActual.getDayOfMonth() + "/" + fechaHoraActual.getMonth()+ "/" + fechaHoraActual.getYear() + " Hora: " + fechaHoraActual.format(DateTimeFormatter.ofPattern("HH:mm"))  + " Respuestas Correctas: " + correctas + " Respuestas Incorrectas: " + incorrectas + "\n";
        try {
            Files.writeString(path, info, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error de escritura.");
        }
    }

    private static int questionAnswerResult(Boolean arrayAnswer, boolean answer, int correctAnswer) {
        boolean aux = arrayAnswer == answer;

        if (aux) {
            System.out.println(ANSI_GREEN + "Correct answer" + ANSI_RESET);
            correctAnswer += 1;
        } else {
            System.out.println(ANSI_RED + "Incorrect answer" + ANSI_RESET);
        }
        return correctAnswer;
    }
    private static boolean exit() {
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.print(ANSI_YELLOW + "   ______                     __           __                     __\n");
        System.out.print(ANSI_YELLOW + "  / ____/  ____   ____   ____/ /          / /_    __  __  ___    / /\n");
        System.out.print(ANSI_GREEN + " / / __   / __ \\ / __ \\ / __  /          / __ \\  / / / / / _ \\  / / \n");
        System.out.print(ANSI_GREEN + "/ /_/ /  / /_/ // /_/ // /_/ /          / /_/ / / /_/ / /  __/ /_/  \n");
        System.out.print(ANSI_YELLOW + "\\____/   \\____/ \\____/ \\__,_/          /_.___/  \\__, /  \\___/ (_)   \n");
        System.out.print(ANSI_YELLOW + "                                               /____/               ");

        return true;
    }
    private static boolean questionAnswerFunction()  {
        //Return boolean answer
        String answer;
        do {
            System.out.println("Answer (" +  ANSI_GREEN + "yes" + ANSI_RESET + "/" + ANSI_RED + "no" + ANSI_RESET + ")? ");
            answer = Teclat.llegirString().toLowerCase();
        } while (!answer.equals("no") && !answer.equals("yes"));

        return answer.equals("yes");
    }
    private static int generateQuestion(Boolean[] arrayUsed, List<String> arrayQuestions) {
        //This function generate a random question if isn't used yet
        int index;
        do {
            index = (int) (Math.random() * 20);
        } while (arrayUsed[index]);

        System.out.println(arrayQuestions.get(index));
        arrayUsed[index] = true;

        return index;
    }
    private static Boolean[] arrayUsedDeclaration() {
        return new Boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    }
    private static int getNumberOfQuestions() {
        //Number of questions in each game
        int questions = 0;
        while (questions < 5 || questions > 20) {
            System.out.println("How many questions do you want? ");
            questions = Teclat.llegirInt();
        }
        return questions;
    }
    private static int getCategoryOption() {
        //Return the option switched by the user
        int categoria;

        System.out.println(ANSI_YELLOW + "\n      Categoria" + ANSI_RESET);
        System.out.println("====================");
        System.out.println(ANSI_YELLOW + "1.- " + ANSI_RESET + "Mitologia griega (Fácil)");
        System.out.println(ANSI_YELLOW + "2.- " + ANSI_RESET + "Superheroes (Fácil)");
        System.out.println(ANSI_YELLOW + "3.- " + ANSI_RESET + "Videojuegos (Intermedio)");
        System.out.println(ANSI_YELLOW + "4.- " + ANSI_RESET + "Fútbol (Intermedio)");
        System.out.println(ANSI_YELLOW + "5.- " + ANSI_RESET + "Tecnología (Difícil)");

        categoria = Teclat.llegirInt();

        return categoria;
    }
    private static int getMenuOption() {
        //Return the option switched by the user
        int menu;

        System.out.println(ANSI_YELLOW + "\n      Menu" + ANSI_RESET);
        System.out.println("================");
        System.out.println(ANSI_YELLOW + "1.-" + ANSI_RESET + " Start game");
        System.out.println(ANSI_YELLOW + "2.-" + ANSI_RESET + " Exit");

        menu = Teclat.llegirInt();

        return menu;
    }
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
}
