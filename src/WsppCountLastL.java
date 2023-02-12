import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class WsppCountLastL {
    public static void main(String[] args) {
        try {
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        StandardCharsets.UTF_8
                ));
                try {
                    Map<String, List<PairIntInt>> words = new LinkedHashMap<>();
                    int lineIndex = 0;
                    StringBuilder temp = new StringBuilder();
                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        lineIndex++;
                        int indexInLine = 0;
                        for (int i = 0; i < line.length(); i++) {
                            char c = line.charAt(i);
                            boolean isCorrect = Character.isLetter(c) || c == '\'' ||
                                    Character.getType(c) == Character.DASH_PUNCTUATION;
                            if (isCorrect) {
                                temp.append(c);
                            }
                            if (!isCorrect || (i + 1) == line.length()) {
                                String tempString = temp.toString().toLowerCase();
                                if (tempString.length() > 0) {
                                    List<PairIntInt> list = words.get(tempString);
                                    if (list == null) {
                                        list = new ArrayList<>();
                                        words.put(tempString, list);
                                    }
                                    indexInLine++;
                                    list.add(new PairIntInt(lineIndex, indexInLine));
                                    temp = new StringBuilder();
                                }
                            }
                        }
                    }
                    List<Map.Entry<String, List<PairIntInt>>> wordsOrder = new ArrayList<>();
                    for (Map.Entry<String, List<PairIntInt>> mapEntry : words.entrySet()) {
                        wordsOrder.add(mapEntry);
                    }
                    Collections.sort(wordsOrder, new Comparator<Map.Entry<String, List<PairIntInt>>>() {
                        @Override
                        public int compare(Map.Entry<String, List<PairIntInt>> word1,
                                           Map.Entry<String, List<PairIntInt>> word2) {
                            return Integer.compare(word1.getValue().size(), word2.getValue().size());
                        }
                    });
                    for (Map.Entry<String, List<PairIntInt>> mapEntry : wordsOrder) {
                        List<PairIntInt> tempList = mapEntry.getValue();
                        writer.write(mapEntry.getKey() + " " + tempList.size());
                        for (int i = 0; i < tempList.size(); i++) {
                            if ((i + 1) != tempList.size() &&
                                    tempList.get(i + 1).getFirst() == tempList.get(i).getFirst()) {
                                continue;
                            }
                            writer.write(" " + tempList.get(i).getSecond());
                        }
                        writer.newLine();
                    }
                } finally {
                    writer.close();
                }
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Too few arguments: " + e.getMessage());
        }
    }
}