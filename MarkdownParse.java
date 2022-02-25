// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        try {
            String oob = markdown.substring(0, 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.toString());
            System.exit(0);
        }
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = markdown.indexOf("[", currentIndex);

            if (markdown.indexOf("!") == 0) {
                break;
            }


            if(nextOpenBracket == -1){
                break;
            }
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            int openParen = markdown.indexOf("(", nextCloseBracket);
            int closeParen;
            if (markdown.contains(")")){
                closeParen = markdown.indexOf(")", openParen);
            } else {
                closeParen = markdown.length();
            }



            if(closeParen==-1){
                return toReturn;
            }
            if(markdown.substring(closeParen-1,closeParen).compareTo("(")==0){
                closeParen = markdown.indexOf(")", closeParen+1);
            }
            String toAdd = markdown.substring(openParen + 1, closeParen);

            while (toAdd.contains("(") || toAdd.contains(")") || toAdd.contains("`")) {
                if (toAdd.contains("`")) {
                    String left = toAdd.substring(0, toAdd.indexOf("`"));
                    String right = toAdd.substring(toAdd.indexOf("`")+1);
                    toAdd = left + right;
                } else if (toAdd.contains(")")) {
                    String left = toAdd.substring(0, toAdd.indexOf(")"));
                    String right = toAdd.substring(toAdd.indexOf(")")+1);
                    toAdd = left + right;
                } else if (toAdd.contains("(")) {
                    String left = toAdd.substring(0, toAdd.indexOf("("));
                    String right = toAdd.substring(toAdd.indexOf("(")+1);
                    toAdd = left + right;
                }
            }

            toReturn.add(toAdd); // hi
            currentIndex = closeParen + 1;
        }

        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of("report-test1.md");
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}