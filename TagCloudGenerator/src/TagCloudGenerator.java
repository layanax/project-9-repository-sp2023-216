import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;
import components.utilities.Reporter;

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
     * @param n
     *            user-defined value for the number of words to include in tag
     *            cloud
     * @requires <pre> out.is.open </pre>
     * @ensures <pre> out.is.open and output.content = #out.content *
     * [tag cloud headers] </pre>
     */
    private static void indexHeaders(SimpleWriter out, String inName, int n) {
//        out.println("<!DOCTYPE html>");
//        out.println("<html>");
//        out.println("<head>");
//        out.println("\t<title>Tag Cloud</title>");
//        out.println(
//                "\t<link href=\"http://web.cse.ohio-state.edu/software/2231/web-"
//                        + "sw2/assignments/projects/tag-cloud-generator/data/"
//                        + "tagcloud.css\" rel = \"stylesheet\" type = \"text/css\">");
//        out.println(
//                "\t<link href=\"tagcloud.css\" type=\"text/css\" rel=\"stylesheet\">");
//        //Style tags
//        out.println("<style>");
//        out.println(".rdp {\r\n" + "  --rdp-cell-size: 40px;\r\n"
//                + "  --rdp-accent-color: #0000ff;\r\n"
//                + "  --rdp-background-color: #e7edff;\r\n"
//                + "  --rdp-accent-color-dark: #3003e1;\r\n"
//                + "  --rdp-background-color-dark: #180270;\r\n"
//                + "  --rdp-outline: 2px solid var(--rdp-accent-color); /* "
//                + "Outline border for focused elements */\r\n"
//                + "  --rdp-outline-selected: 2px solid rgba(0, 0, 0, 0.75); /* "
//                + "Outline border for focused _and_ selected elements */\r\n"
//                + "\r\n" + "  margin: 1em;\r\n" + "}\r\n" + "\r\n"
//                + "/* Hide elements for devices that are not screen readers */\r\n"
//                + ".rdp-vhidden {\r\n" + "  box-sizing: border-box;\r\n"
//                + "  padding: 0;\r\n" + "  margin: 0;\r\n"
//                + "  background: transparent;\r\n" + "  border: 0;\r\n"
//                + "  -moz-appearance: none;\r\n"
//                + "  -webkit-appearance: none;\r\n" + "  appearance: none;\r\n"
//                + "  position: absolute !important;\r\n" + "  top: 0;\r\n"
//                + "  width: 1px !important;\r\n"
//                + "  height: 1px !important;\r\n"
//                + "  padding: 0 !important;\r\n"
//                + "  overflow: hidden !important;\r\n"
//                + "  clip: rect(1px, 1px, 1px, 1px) !important;\r\n"
//                + "  border: 0 !important;\r\n" + "}\r\n" + "\r\n"
//                + "/* Buttons */\r\n" + ".rdp-button_reset {\r\n"
//                + "  appearance: none;\r\n" + "  position: relative;\r\n"
//                + "  margin: 0;\r\n" + "  padding: 0;\r\n"
//                + "  cursor: default;\r\n" + "  color: inherit;\r\n"
//                + "  outline: none;\r\n" + "  background: none;\r\n"
//                + "  font: inherit;\r\n" + "\r\n"
//                + "  -moz-appearance: none;\r\n"
//                + "  -webkit-appearance: none;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-button {\r\n" + "  border: 2px solid transparent;\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-button[disabled] {\r\n"
//                + "  opacity: 0.25;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-button:not([disabled]) {\r\n" + "  cursor: pointer;\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-button:focus:not([disabled]),\r\n"
//                + ".rdp-button:active:not([disabled]) {\r\n"
//                + "  color: inherit;\r\n" + "  border: var(--rdp-outline);\r\n"
//                + "  background-color: var(--rdp-background-color);\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-button:hover:not([disabled]) {\r\n"
//                + "  background-color: var(--rdp-background-color);\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-months {\r\n"
//                + "  display: flex;\r\n" + "}\r\n" + "\r\n" + ".rdp-month {\r\n"
//                + "  margin: 0 1em;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-month:first-child {\r\n" + "  margin-left: 0;\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-month:last-child {\r\n"
//                + "  margin-right: 0;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-table {\r\n" + "  margin: 0;\r\n"
//                + "  max-width: calc(var(--rdp-cell-size) * 7);\r\n"
//                + "  border-collapse: collapse;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-with_weeknumber .rdp-table {\r\n"
//                + "  max-width: calc(var(--rdp-cell-size) * 8);\r\n"
//                + "  border-collapse: collapse;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-caption {\r\n" + "  display: flex;\r\n"
//                + "  align-items: center;\r\n"
//                + "  justify-content: space-between;\r\n" + "  padding: 0;\r\n"
//                + "  text-align: left;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-multiple_months .rdp-caption {\r\n"
//                + "  position: relative;\r\n" + "  display: block;\r\n"
//                + "  text-align: center;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-caption_dropdowns {\r\n" + "  position: relative;\r\n"
//                + "  display: inline-flex;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-caption_label {\r\n" + "  position: relative;\r\n"
//                + "  z-index: 1;\r\n" + "  display: inline-flex;\r\n"
//                + "  align-items: center;\r\n" + "  margin: 0;\r\n"
//                + "  padding: 0 0.25em;\r\n" + "  white-space: nowrap;\r\n"
//                + "  color: currentColor;\r\n" + "  border: 0;\r\n"
//                + "  border: 2px solid transparent;\r\n"
//                + "  font-family: inherit;\r\n" + "  font-size: 140%;\r\n"
//                + "  font-weight: bold;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-nav {\r\n" + "  white-space: nowrap;\r\n" + "}\r\n"
//                + "\r\n"
//                + ".rdp-multiple_months .rdp-caption_start .rdp-nav {\r\n"
//                + "  position: absolute;\r\n" + "  top: 50%;\r\n"
//                + "  left: 0;\r\n" + "  transform: translateY(-50%);\r\n"
//                + "}\r\n" + "\r\n"
//                + ".rdp-multiple_months .rdp-caption_end .rdp-nav {\r\n"
//                + "  position: absolute;\r\n" + "  top: 50%;\r\n"
//                + "  right: 0;\r\n" + "  transform: translateY(-50%);\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-nav_button {\r\n"
//                + "  display: inline-flex;\r\n" + "  align-items: center;\r\n"
//                + "  justify-content: center;\r\n"
//                + "  width: var(--rdp-cell-size);\r\n"
//                + "  height: var(--rdp-cell-size);\r\n"
//                + "  padding: 0.25em;\r\n" + "  border-radius: 100%;\r\n"
//                + "}\r\n" + "\r\n" + "/* ---------- */\r\n"
//                + "/* Dropdowns  */\r\n" + "/* ---------- */\r\n" + "\r\n"
//                + ".rdp-dropdown_year,\r\n" + ".rdp-dropdown_month {\r\n"
//                + "  position: relative;\r\n" + "  display: inline-flex;\r\n"
//                + "  align-items: center;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-dropdown {\r\n" + "  appearance: none;\r\n"
//                + "  position: absolute;\r\n" + "  z-index: 2;\r\n"
//                + "  top: 0;\r\n" + "  bottom: 0;\r\n" + "  left: 0;\r\n"
//                + "  width: 100%;\r\n" + "  margin: 0;\r\n"
//                + "  padding: 0;\r\n" + "  cursor: inherit;\r\n"
//                + "  opacity: 0;\r\n" + "  border: none;\r\n"
//                + "  background-color: transparent;\r\n"
//                + "  font-family: inherit;\r\n" + "  font-size: inherit;\r\n"
//                + "  line-height: inherit;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-dropdown[disabled] {\r\n" + "  opacity: unset;\r\n"
//                + "  color: unset;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-dropdown:focus:not([disabled]) + .rdp-caption_label,\r\n"
//                + ".rdp-dropdown:active:not([disabled]) + .rdp-caption_label {\r\n"
//                + "  border: var(--rdp-outline);\r\n"
//                + "  border-radius: 6px;\r\n"
//                + "  background-color: var(--rdp-background-color);\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-dropdown_icon {\r\n"
//                + "  margin: 0 0 0 5px;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-head {\r\n" + "  border: 0;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-head_row,\r\n" + ".rdp-row {\r\n"
//                + "  height: 100%;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-head_cell {\r\n" + "  vertical-align: middle;\r\n"
//                + "  text-transform: uppercase;\r\n"
//                + "  font-size: 0.75em;\r\n" + "  font-weight: 700;\r\n"
//                + "  text-align: center;\r\n" + "  height: 100%;\r\n"
//                + "  height: var(--rdp-cell-size);\r\n" + "  padding: 0;\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-tbody {\r\n" + "  border: 0;\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-tfoot {\r\n" + "  margin: 0.5em;\r\n"
//                + "}\r\n" + "\r\n" + ".rdp-cell {\r\n"
//                + "  width: var(--rdp-cell-size);\r\n" + "  height: 100%;\r\n"
//                + "  height: var(--rdp-cell-size);\r\n" + "  padding: 0;\r\n"
//                + "  text-align: center;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-weeknumber {\r\n" + "  font-size: 0.75em;\r\n" + "}\r\n"
//                + "\r\n" + ".rdp-weeknumber,\r\n" + ".rdp-day {\r\n"
//                + "  display: flex;\r\n" + "  overflow: hidden;\r\n"
//                + "  align-items: center;\r\n"
//                + "  justify-content: center;\r\n"
//                + "  box-sizing: border-box;\r\n"
//                + "  width: var(--rdp-cell-size);\r\n"
//                + "  max-width: var(--rdp-cell-size);\r\n"
//                + "  height: var(--rdp-cell-size);\r\n" + "  margin: 0;\r\n"
//                + "  border: 2px solid transparent;\r\n"
//                + "  border-radius: 100%;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-day_today:not(.rdp-day_outside) {\r\n"
//                + "  font-weight: bold;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-day_selected:not([disabled]),\r\n"
//                + ".rdp-day_selected:focus:not([disabled]),\r\n"
//                + ".rdp-day_selected:active:not([disabled]),\r\n"
//                + ".rdp-day_selected:hover:not([disabled]) {\r\n"
//                + "  color: white;\r\n"
//                + "  background-color: var(--rdp-accent-color);\r\n" + "}\r\n"
//                + "\r\n" + ".rdp-day_selected:focus:not([disabled]) {\r\n"
//                + "  border: var(--rdp-outline-selected);\r\n" + "}\r\n"
//                + "\r\n"
//                + ".rdp:not([dir='rtl']) .rdp-day_range_start:not(.rdp-day_range_end) "
//                + "{\r\n" + "  border-top-right-radius: 0;\r\n"
//                + "  border-bottom-right-radius: 0;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp:not([dir='rtl']) .rdp-day_range_end:not(.rdp-day_range_start) "
//                + "{\r\n" + "  border-top-left-radius: 0;\r\n"
//                + "  border-bottom-left-radius: 0;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp[dir='rtl'] .rdp-day_range_start:not(.rdp-day_range_end) {\r\n"
//                + "  border-top-left-radius: 0;\r\n"
//                + "  border-bottom-left-radius: 0;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp[dir='rtl'] .rdp-day_range_end:not(.rdp-day_range_start) {\r\n"
//                + "  border-top-right-radius: 0;\r\n"
//                + "  border-bottom-right-radius: 0;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-day_range_end.rdp-day_range_start {\r\n"
//                + "  border-radius: 100%;\r\n" + "}\r\n" + "\r\n"
//                + ".rdp-day_range_middle {\r\n" + "  border-radius: 0;\r\n"
//                + "}\r\n" + "");
//        out.println("</style>");
//        out.println("</head>");
//        out.println("<body>");
//        out.println("\t<h2>Top " + n + " words in " + inName + " </h2>");
//        out.println("\t<hr>");
//        out.println();

        out.println("<!DOCTYPE html>\n<html>\n<head>");
        out.println("\t<title>Tag Cloud</title>");
        out.println(
                "\t<link href=\"http://web.cse.ohio-state.edu/software/2231/web-"
                        + "sw2/assignments/projects/tag-cloud-generator/data/"
                        + "tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println(
                "\t<link href=\"tagcloud.css\" type=\"text/css\" rel=\"stylesheet\">");
        out.println("\t<style>");
        out.println(
                ".rdp {--rdp-cell-size: 40px;--rdp-accent-color: #0000ff;--rdp-"
                        + "background-color: #e7edff;--rdp-accent-color-dark: "
                        + "#3003e1;--rdp-background-color-dark: #180270;--rdp-outline: "
                        + "2px solid var(--rdp-accent-color);--rdp-outline-selected: "
                        + "2px solid rgba(0, 0, 0, 0.75);margin: 1em;}");
        out.println(".rdp-vhidden {box-sizing: border-box;padding: 0;margin: 0;"
                + "background: transparent;border: 0;-moz-appearance: none;-webkit"
                + "-appearance: none;appearance: none;position: absolute !important;"
                + "top: 0;width: 1px !important;height: 1px !important;padding: 0 "
                + "!important;overflow: hidden !important;clip: rect(1px, 1px, 1px, 1px)"
                + " !important;border: 0 !important;}");
        // Add the rest of the CSS rules here
        out.println("</style>");
        out.println("</head>\n<body>");
        out.println(
                "\t<h2>Top " + n + " words in " + inName + " </h2>\n\t<hr>\n");

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
     * @param n
     *            user-defined value for the number of words to include in tag
     *            cloud
     * @requires <pre> out.is.open </pre>
     * @ensures <pre> out.content = #out.content * [print out words in tag cloud
     * format] </pre>
     */
    private static void tagCloud(Map<String, Integer> map, SimpleWriter out,
            int n) {
        out.println("<div class=\"cdiv\">");
        out.println("<p class = \"cbox\">");

        //sorted queue of map pairs based on counts

        //calculate font sizes for tag cloud
        int maxCount = map.value(map.iterator().next().key());
        int minCount = Integer.MAX_VALUE;
        for (Map.Pair<String, Integer> entry : map) {
            int count = entry.value();
            maxCount = Math.max(maxCount, count);
            minCount = Math.min(minCount, count);
        }

        Queue<Map.Pair<String, Integer>> sortedWords = createSortedQueue(map,
                n);

        while (sortedWords.length() > 0) {
            Map.Pair<String, Integer> entry = sortedWords.dequeue();
            String word = entry.key();
            int count = entry.value();

            //calculates font size based on count
            int fontSize = calculateFontSize(count, minCount, maxCount);

            //outputs word with correct font size
            out.println("<span style=\"cursor:default\" class = \" f" + fontSize
                    + "\" title = \"count: " + entry.value() + "\">" + word
                    + "</span>");
        }
        out.println("</p>");
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
    private static int calculateFontSize(int count, int minCount,
            int maxCount) {

        final int minFontSize = 11;
        final int maxFontSize = 48;

        double relativeSize = ((double) count - minCount)
                / (maxCount - minCount);
        int font = (int) Math.ceil(
                relativeSize + relativeSize * (maxFontSize - minFontSize));

        return font;
    }

    /**
     * Creates and returns a sorted queue of words from the given map.
     *
     * @param n
     *            user-defined value for the number of words to include in tag
     *            cloud
     * @param map
     *            the map containing words and their counts
     * @requires map is not modified during the execution of this
     * @return a queue of words sorted into alphabetical order
     */
    private static Queue<Map.Pair<String, Integer>> createSortedQueue(
            Map<String, Integer> map, int n) {
        Comparator<Pair<String, Integer>> countOrder = new CountComparator();
        SortingMachine<Map.Pair<String, Integer>> countSort;
        countSort = new SortingMachine2<Map.Pair<String, Integer>>(countOrder);
        while (map.size() > 0) {
            countSort.add(map.removeAny());
        }
        countSort.changeToExtractionMode();

        Comparator<Pair<String, Integer>> alphabeticalOrder = new WordComparator();
        SortingMachine<Map.Pair<String, Integer>> letterSort;
        letterSort = new SortingMachine2<Map.Pair<String, Integer>>(
                alphabeticalOrder);

        if (countSort.size() > 0) {
            Map.Pair<String, Integer> maxPair = countSort.removeFirst();
            letterSort.add(maxPair);
        }

        int topCounter = 0;
        while (topCounter < n && countSort.size() >= 2) {
            Map.Pair<String, Integer> wordAndCount = countSort.removeFirst();
            letterSort.add(wordAndCount);
            topCounter++;
        }

        letterSort.changeToExtractionMode();
        Queue<Map.Pair<String, Integer>> queue = new Queue2<Map.Pair<String, Integer>>();

        while (letterSort.size() > 0) {
            queue.enqueue(letterSort.removeFirst());
        }

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
        out.print("Enter the name of an input file: ");
        String inputFile = in.nextLine();
        SimpleReader input = new SimpleReader1L(inputFile);
        Reporter.assertElseFatalError(input.isOpen(), "invalid input file");

        out.print("Enter the name of the output HTML file: ");
        String outputFile = in.nextLine();
        SimpleWriter output = new SimpleWriter1L(outputFile);

        out.print("Enter the number of words to include in the Tag Cloud: ");
        int n = in.nextInteger();

        Reporter.assertElseFatalError(n > 0,
                "Number of words must be positive (n > 0).");

        //generates HTML headers for output file
        indexHeaders(output, inputFile, n);

        //process input file, count words, and generate tag cloud
        Map<String, Integer> map = repeatedWords(input);
        tagCloud(map, output, n);

        in.close();
        out.close();
        input.close();
        output.close();
    }
}
