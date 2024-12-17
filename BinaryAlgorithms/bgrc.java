import java.util.ArrayList;

public class bgrc {
    enum Bit {
        ZERO, ONE
    }

    public static final String[] NAMES = {"Gomer", "Fleek", "Elmer", "Dietz", "Crizz", "Berty", "Alfie"};
    public static final String[] HEADINGS = {"Index", "Gray Code", "Players", "Action"};
    public static final int[] COLUMN_WIDTHS = new int[HEADINGS.length];

    static class GrayCodeList {
        private final ArrayList<ArrayList<Boolean>> grayCodes;

        GrayCodeList() {
            grayCodes = new ArrayList<>();
        }

        void addCode(Bit[] bits) {
            ArrayList<Boolean> code = new ArrayList<>();
            for (Bit bit : bits) {
                code.add(bit == Bit.ONE);
            }
            grayCodes.add(code);
        }

        int size() {
            return grayCodes.size();
        }

        Bit[] getCode(int index) {
            ArrayList<Boolean> code = grayCodes.get(index);
            Bit[] bits = new Bit[code.size()];
            for (int i = 0; i < code.size(); i++) {
                bits[i] = code.get(i) ? Bit.ONE : Bit.ZERO;
            }
            return bits;
        }

        void prepend(Bit bit) {
            boolean value = bit == Bit.ONE;
            for (ArrayList<Boolean> code : grayCodes) {
                code.add(0, value);
            }
        }

        void merge(GrayCodeList other) {
            grayCodes.addAll(other.grayCodes);
        }

        void display() {
            for (int i = 0; i < grayCodes.size(); i++) {
                ArrayList<Boolean> current = grayCodes.get(i);

                // Print index
                System.out.print(i + spaces(COLUMN_WIDTHS[0] - String.valueOf(i).length()));

                // Print gray code
                String gray = buildGrayString(current);
                System.out.print(gray + spaces(COLUMN_WIDTHS[1] - gray.length()));

                // Print players
                String players = i == 0 ? "SILENT STAGE" : getPlayers(gray);
                System.out.print(players + spaces(COLUMN_WIDTHS[2] - players.length()));

                // Print action
                String action = i > 0 ? getAction(grayCodes.get(i - 1), current) : "";
                System.out.println(action);
            }
        }

        private String buildGrayString(ArrayList<Boolean> code) {
            StringBuilder grayCode = new StringBuilder();
            for (Boolean bit : code) {
                grayCode.append(bit ? '1' : '0');
            }
            return grayCode.toString();
        }

        private String getPlayers(String grayCode) {
            StringBuilder players = new StringBuilder();
            for (int i = 0; i < grayCode.length(); i++) {
                if (grayCode.charAt(i) == '1') {
                    if (players.length() > 0) players.append(" & ");
                    players.append(NAMES[i]);
                }
            }
            return players.toString();
        }

        private String getAction(ArrayList<Boolean> previous, ArrayList<Boolean> current) {
            StringBuilder action = new StringBuilder();
            for (int i = 0; i < current.size(); i++) {
                if (!current.get(i).equals(previous.get(i))) {
                    if (action.length() > 0) action.append(", ");
                    action.append(NAMES[i]).append(current.get(i) ? " Joins" : " Fades");
                }
            }
            return action.toString();
        }
    }

    public static void main(String[] args) {
        // Print table headings
        for (int i = 0; i < HEADINGS.length; i++) {
            String heading = HEADINGS[i] + (i == 2 ? spaces(39) : spaces(5));
            System.out.print(heading);
            COLUMN_WIDTHS[i] = heading.length();
        }
        System.out.println();

        // Generate and display Gray codes
        generateGrayCodes(7).display();
    }

    static GrayCodeList generateGrayCodes(int n) {
        GrayCodeList codes = new GrayCodeList();
        if (n == 1) {
            codes.addCode(new Bit[]{Bit.ZERO});
            codes.addCode(new Bit[]{Bit.ONE});
        } else {
            GrayCodeList firstHalf = generateGrayCodes(n - 1);
            GrayCodeList secondHalf = new GrayCodeList();

            for (int i = firstHalf.size() - 1; i >= 0; i--) {
                secondHalf.addCode(firstHalf.getCode(i));
            }

            firstHalf.prepend(Bit.ZERO);
            secondHalf.prepend(Bit.ONE);
            firstHalf.merge(secondHalf);

            codes = firstHalf;
        }
        return codes;
    }

    static String spaces(int count) {
        return " ".repeat(Math.max(0, count));
    }
}
