import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * TagCloudGenerator generates an HTML file displaying a tag cloud from an input
 * text file.
 *
 * @author Layan Abdallah & Oak Hodous
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
    }

    /**
     * Generates the header of the HTML file.
     *
     * @param out
     *            output stream
     * @requires <pre> out.is.open </pre>
     * @ensures <pre> out.is.open and output.content = #out.content *
     * [tag cloud headers] </pre>
     */
    private static void indexHeaders(SimpleWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("\t<title>Tag Cloud</title>");
        out.println(
                "\t<link href=\"tagcloud.css\" type=\"text/css\" rel=\"stylesheet\">");
        out.println(
                "\t<link href=\"tagcloud.css\" type=\"text/css\" rel=\"stylesheet\">");
        out.println("</head>");
        out.println("<body>");
        out.println("\t<h2>Tag Cloud</h2>");
        out.println("\t<hr>");
        out.println();
    }

    /**
     * Reads input and returns a map of repeated words and their counts.
     *
     * @param in
     *            input stream
     * @return a map containing words and their counts
     */
    private static Map<String, Integer> repeatedWords(SimpleReader in) {
        Map<String, Integer> map = new Map1L<>();

        while (!in.atEOS()) {
            String line = in.nextLine();
            String[] words = line.split("[ \t\n\r,-.!?\\[\\]';:/()]+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    String lowerCaseWord = word.toLowerCase();
                    if (map.hasKey(lowerCaseWord)) {
                        map.replaceValue(lowerCaseWord,
                                map.value(lowerCaseWord) + 1);
                    } else {
                        map.add(lowerCaseWord, 1);
                    }
                }
            }
        }
        return map;
    }

    /**
     * Outputs an HTML tag cloud with words and their counts.
     *
     * @param map
     *            a map containing words and their counts
     * @param out
     *            output stream
     * @requires <pre> out.is.open </pre>
     * @ensures <pre> out.content = #out.content * [print out words in tag cloud
     * format] </pre>
     */
    private static void tagCloud(Map<String, Integer> map, SimpleWriter out) {
        out.println("<div class=\"tagcloud\">");

        //sorted queue of map pairs based on counts
        Queue<Map.Pair<String, Integer>> sortedWords = createSortedQueue(map);

        //calculate font sizes for tag cloud
        int maxCount = map.value(map.iterator().next().key());
        int minCount = Integer.MAX_VALUE;
        for (Map.Pair<String, Integer> entry : map) {
            int count = entry.value();
            maxCount = Math.max(maxCount, count);
            minCount = Math.min(minCount, count);
        }

        while (sortedWords.length() > 0) {
            Map.Pair<String, Integer> entry = sortedWords.dequeue();
            String word = entry.key();
            int count = entry.value();

            //calculates font size based on count
            double fontSize = calculateFontSize(count, minCount, maxCount);

            //outputs word with correct font size
            out.println("<span style=\"font-size: " + fontSize + "px\">" + word
                    + "</span>");
        }

        out.println("</div>");
    }

    /**
     * Calculates the font size for a word in the tag cloud.
     *
     * @param count
     *            the count of occurrences of the word
     * @param minCount
     *            the minimum count among all words
     * @param maxCount
     *            the maximum count among all words
     * @return the font size for the word
     */
    private static double calculateFontSize(int count, int minCount,
            int maxCount) {

        final int minFontSize = 11;
        final int maxFontSize = 48;
        double relativeSize = ((double) count - minCount)
                / (maxCount - minCount);
        return minFontSize + relativeSize * (maxFontSize - minFontSize);
    }

    /**
     * Creates and returns a sorted queue of words from the given map.
     *
     * @param map
     *            the map containing words and their counts
     * @requires map is not modified during the execution of this
     * @return a queue of words sorted into alphabetical order
     */
    private static Queue<Map.Pair<String, Integer>> createSortedQueue(
            Map<String, Integer> map) {
        Queue<Map.Pair<String, Integer>> queue = new Queue1L<>();

        for (Map.Pair<String, Integer> entry : map) {
            queue.enqueue(entry);
        }

        Comparator<Map.Pair<String, Integer>> countComparator = new CountComparator();
        Comparator<Map.Pair<String, Integer>> wordComparator = new WordComparator();
        queue.sort(countComparator);
        queue.sort(wordComparator);

        return queue;
    }

    /**
     * A comparator for sorting words by their counts in descending order.
     */
    private static class CountComparator
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> pair1,
                Map.Pair<String, Integer> pair2) {

            //sorts by count in descending order
            return pair2.value() - pair1.value();
        }
    }

    /**
     * A comparator for sorting words alphabetically.
     */
    private static class WordComparator
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> pair1,
                Map.Pair<String, Integer> pair2) {

            //sorts alphabetically
            return pair1.key().compareTo(pair2.key());
        }
    }

    /**
     * Main method that reads input and generates the tag cloud HTML file.
     *
     * @param args
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //prompts user for input and output file names
        out.println("Enter the name of an input file: ");
        String inputFile = in.nextLine();
        SimpleReader input = new SimpleReader1L(inputFile);

        out.println("Enter the name of the output HTML file: ");
        String outputFile = in.nextLine();
        SimpleWriter output = new SimpleWriter1L(outputFile);

        //generates HTML headers for output file
        indexHeaders(output);

        //process input file, count words, and generate tag cloud
        Map<String, Integer> map = repeatedWords(input);
        tagCloud(map, output);

        in.close();
        out.close();
        input.close();
        output.close();
    }
}
