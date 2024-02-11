import java.util.Collections;
import java.util.List;

public class Draw {
    private final int drawNumber;
    private final List<Integer> winningNumbers;
    private final List<Integer> specialNumbers;
    private final String drawDate;

    public Draw(int drawNumber, List<Integer> winningNumbers, List<Integer> specialNumbers, String drawDate) {
        this.drawNumber = drawNumber;
        this.winningNumbers = winningNumbers;
        this.specialNumbers = specialNumbers;
        this.drawDate = drawDate;
    }

    public int getDrawNumber() {
        return drawNumber;
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public List<Integer> getSpecialNumbers() {
        return specialNumbers;
    }

    public void printDraw() {
        StringBuilder sb = new StringBuilder();
        sb.append("Draw ").append(drawNumber).append(":  ");

        // Sort winning numbers
        Collections.sort(winningNumbers);
        // Append winning numbers
        Utils.appendNumbers(sb, winningNumbers);

        sb.append("| ");

        // Sort special numbers
        Collections.sort(specialNumbers);
        // Append special numbers
        Utils.appendNumbers(sb, specialNumbers);

        sb.append("   ").append(drawDate);

        Utils.printMessage(sb.toString());
    }
}
