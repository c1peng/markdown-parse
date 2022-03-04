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


            String toAdd = markdown.substring(openParen + 1, closeParen).trim();

            if (toAdd.contains("\n")) {
                closeParen = markdown.indexOf("\n", openParen);
                toAdd = markdown.substring(openParen + 1, closeParen).trim();
            }

            while (toAdd.contains("(") || toAdd.contains(")") || toAdd.contains("`")) {
                if (toAdd.contains("`")) {
                    toAdd = toAdd.replace("`", "");
                } else if (toAdd.contains(")")) {
                    toAdd = toAdd.replace(")", "");
                } else if (toAdd.contains("(")) {
                    toAdd = toAdd.replace("(", "");
                }
            }

            toReturn.add(toAdd); // hi
            currentIndex = closeParen + 1;
        }

        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		for (String arg : args) {
            Path fileName = Path.of(arg);
            String contents = Files.readString(fileName);
            ArrayList<String> links = getLinks(contents);
            System.out.println(links);
        }
    }
}