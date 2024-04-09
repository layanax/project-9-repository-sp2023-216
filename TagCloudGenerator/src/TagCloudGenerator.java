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
 * WordCounter generates an HTML file displaying word counts from an input text
 * file.
 *
 * @author Layan Abdallah
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
     * @ensures <pre> out.is.open and output.content = #out.content * [word
     * counter headers] </pre>
     */
    private static void indexHeaders(SimpleWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("\t<title>Word Counter</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("\t<h2>Word Counter</h2>");
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

        //map to store words and their respective counts
        Map<String, Integer> map = new Map1L<>();

        //read input line by line until end of stream
        while (!in.atEOS()) {
            String line = in.nextLine();
            String word = "";
            //iterate through each character in the line
            for (char c : line.toCharArray()) {
                if (Character.isLetter(c) || c == '\'') {
                    //append letters to current word
                    word += c; //Avoid string concatenation in loops
                } else if (!word.isEmpty()) {
                    //if a non-word character, process current word
                    if (map.hasKey(word)) {
                        map.replaceValue(word, map.value(word) + 1);
                    } else {
                        map.add(word, 1);
                    }
                    //reset current word
                    word = "";
                }
            }

            //process last word if line ends with a word
            if (!word.isEmpty()) {
                if (map.hasKey(word)) { //This portion of code can be simplified
                    map.replaceValue(word, map.value(word) + 1);
                } else {
                    map.add(word, 1);
                }
            }
        }
        return map;
    }

    /**
     * Outputs an HTML table row with a word and its corresponding count.
     *
     * @param word
     *            the word displayed in the HTML table cell
     * @param counts
     *            the count of occurrences of the word to be displayed in the
     *            HTML table cell
     * @param out
     *            output stream
     * @requires out.is.open
     * @ensures <pre> out.content = #out.content * [print out words and counts
     * in a table] </pre>]
     */
    private static void pageOfWords(String word, Integer counts,
            SimpleWriter out) {
        out.println("<tr><td>" + word + "</td><td>" + counts + "</td></tr>");
    }

    /**
     * Outputs an HTML page with words and their counts in alphabetical order.
     *
     * @param map
     *            a map containing words and their counts
     * @param out
     *            output stream
     * @updates out
     * @requires <pre> out.is.open and out.content = #out.content * [print out
     *           words in alphabetical order and counts in a table]</pre>
     */
    private static void wordsAlphabetical(Map<String, Integer> map,
            SimpleWriter out) {

        //start HTML table
        out.println("<table border=\"1\">");
        out.println("<tr><th>Word</th><th>Count</th></tr>");

        //create sorted queue of words
        Queue<String> sortedWords = createSortedQueue(map);

        //iterate through sorted words and output HTML row for each unique word
        while (sortedWords.length() > 0) {
            String word = sortedWords.dequeue();
            Integer counts = map.value(word);
            pageOfWords(word, counts, out);
        }

        //end HTML table
        out.println("</table>");
        out.println();
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Creates and returns a sorted queue of words from the given map.
     *
     * @param map
     *            the map containing words and their counts
     * @requires map is not modified during the execution of this
     * @ensures the returned queue contains terms in alphabetical order
     * @return a queue of words sorted into alphabetical order
     */
    private static Queue<String> createSortedQueue(Map<String, Integer> map) {
        Queue<String> queue = new Queue1L<>();

        //enqueue all words into queue
        for (Map.Pair<String, Integer> entry : map) {
            queue.enqueue(entry.key());
        }

        //sort queue using case-insensitive comparator
        Comparator<String> cs = new StringCaseInsensitiveComparator();
        queue.sort(cs);

        return queue;
    }

    /**
     * A case-insensitive comparator for strings.
     */
    private static class StringCaseInsensitiveComparator
            implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }
    }

    /**
     * Main method that reads input and generates the word count HTML file.
     *
     * @param args
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Users should be able to name their output file with any name, and without any assumed extension

        //prompt user for name of input file
        out.println("Enter the name of an input file: ");
        String inputFile = in.nextLine();
        SimpleReader input = new SimpleReader1L(inputFile);

        //prompt user for name of folder for the output file
        out.println("Enter the name of a folder for the file to be saved: ");
        String folder = in.nextLine();
        SimpleWriter output = new SimpleWriter1L(folder + "/output.html");

        //generate HTML headers for output file
        indexHeaders(output);

        //process input file, count words, and generate alphabetical order
        Map<String, Integer> map = repeatedWords(input);
        wordsAlphabetical(map, output);

        in.close();
        out.close();
        input.close();
        output.close();
    }
}
