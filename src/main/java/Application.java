import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Mateusz on 2016-06-15.
 */
public class Application {

    public static void main(String[] args) {
        LocalTime before = LocalTime.now();
        //writeFileContainingRandomWords("C:/workspace/text.txt", 40000000, 2, 10);
        //writeLetterOccurence("C:/workspace/text.txt", "C:/workspace/result.txt");
        writeLetterOccurenceFromDistinctWords("C:/workspace/text.txt", "C:/workspace/distinctResult.txt");
        LocalTime after = LocalTime.now();

        System.out.println("overall:   " + Duration.between(before, after).toString());

        System.out.println("max memory: " + Runtime.getRuntime().maxMemory());
        System.out.println("total memory: " + Runtime.getRuntime().totalMemory());
    }

    public static void writeFileContainingRandomWords(String filename, int numberOfWords, int minLength, int maxLength) {
        Random random = new Random();

        try (PrintWriter writer = new PrintWriter(filename)) {
            for (int i = 0; i < numberOfWords; i++) {
                StringBuilder wordBuilder = new StringBuilder(10);
                int wordLength = random.nextInt(maxLength - minLength) + minLength;
                for (int j = 0; j < wordLength; j++) {
                    wordBuilder.append((char) (random.nextInt(26) + 'A'));
                }
                writer.println(wordBuilder.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeLetterOccurence(String inputFilename, String outputFilename) {
        Map<String, Integer> result = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))) {
            reader.lines()
                    .forEach(line -> Stream.of(line.split(""))
                            .forEach(letter -> result.merge(letter, 1, Integer::sum)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeFileFromMap(result, outputFilename);
    }

    public static void writeFileFromMap(Map<?, ?> map, String outputFilename) {
        try (PrintWriter writer = new PrintWriter(outputFilename)) {
            map.entrySet().stream()
                    .forEach(entry -> writer.println(entry.getKey() + "=" + entry.getValue()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeLetterOccurenceFromDistinctWords(String inputFilename, String outputFilename) {

        try (PrintWriter writer = new PrintWriter("C:/workspace/resultDistinct.txt"); final Stream<String> lines = Files.lines(Paths.get("C:/workspace/text.txt"))
                .distinct()) {
            lines.forEach(line -> writer.println(line));
            //System.out.println(lines.count());
        } catch (IOException e) {
            e.printStackTrace();
        }


//        final String tempFilename = "C:/workspace/temp.txt";
//        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename)); PrintWriter writer = new PrintWriter(tempFilename)) {
//            reader.lines().forEach();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
