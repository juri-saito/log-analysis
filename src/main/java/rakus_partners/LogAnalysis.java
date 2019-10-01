package rakus_partners;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogAnalysis {

    private static final String LOG_FILE = "log.txt";

    public static void main(String[] args) {
        LogAnalysis logAnalysis = new LogAnalysis();
        logAnalysis.execute();
    }

    public void execute() {

        List<LogItem> logItemList = createBaseLogItemList();

        List<String> lines = readLog();

        for (String line : lines) {
            String[] items = line.split("\t");
            if (items.length != 3) continue;
            String[] dateParts = items[0].split(" ");
            if (dateParts.length != 2) continue;            // ここでヘッダ行はスキップされる
            String[] timeParts = dateParts[1].split(":");
            if (timeParts.length != 3) continue;
            try {
                int minutesFromZero = 60 * Integer.parseInt(timeParts[0]) + Integer.parseInt(timeParts[1]);
                int index = minutesFromZero / 5;
//                System.out.println(index + "," + items[2]);
                int responsTime = Integer.parseInt(items[2]);
                LogItem logItem = logItemList.get(index);
                logItem.addResponse(responsTime);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
        }

        int i = 0;
        for (LogItem logItem : logItemList) {
            System.out.println(logItem);
            i++;
        }


    }

    /**
     * ログファイルをいっきに読み込みリストに格納.
     *
     * @return
     */
    private List<String> readLog() {
        try {
            return Files.lines(Paths.get(LOG_FILE), StandardCharsets.UTF_8).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 集計結果を格納するデータ構造を用意する.
     *
     * @return
     */
    private List<LogItem> createBaseLogItemList() {
        List<LogItem> baseLogItemList = new ArrayList<>();
        for (int i = 0; i < 60 * 24; i+=5) {
            int hour = i / 60;
            int minutes = i - (hour * 60);
            baseLogItemList.add(new LogItem(String.format("%02d", hour) + ":" + String.format("%02d", minutes)));
        }
        return baseLogItemList;
    }
}
